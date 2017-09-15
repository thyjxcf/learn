package net.zdsoft.keelcnet.transaction.impl;

import net.zdsoft.keelcnet.transaction.ITransactionCallback;
import net.zdsoft.keelcnet.transaction.ITransactionManager;
import net.zdsoft.keelcnet.transaction.TransactionException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.InitializingBean;

import org.springframework.jdbc.datasource.ConnectionHolder;
import org.springframework.jdbc.datasource.ConnectionProxy;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DataSourceUtils;

import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.interceptor.DefaultTransactionAttribute;
import org.springframework.transaction.interceptor.TransactionAttribute;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import java.sql.Connection;

import javax.sql.DataSource;

/*
 * 如果是在JTA环境下，需要调整该配置，使之适应（去除connection的获得接口）
 *
 * <p>城域综合信息平台</p>
 * <p>CNet3.0</p>
 * <p>Copyright (c) 2003</p>
 * <p>Company: ZDSoft</p>
 * @author taoy
 * @since 1.0
 * @version $Id: LocalTransactionManager.java,v 1.1 2006/12/30 07:14:19 jiangl Exp $
 */
public class LocalTransactionManager implements ITransactionManager,
        InitializingBean {
    private Logger log = LoggerFactory.getLogger(getClass());
    private ThreadLocal<TransactionStatus> currentTransactionStatus = new ThreadLocal<TransactionStatus>();
    private DataSourceTransactionManager transactionManager;

    /**
     * @param transactionManager
     *            The transactionManager to set.
     */
    public final void setTransactionManager(
            DataSourceTransactionManager transactionManager) {
        this.transactionManager = transactionManager;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
     */
    public void afterPropertiesSet() throws Exception {
        if (transactionManager == null) {
            throw new IllegalArgumentException("transactionManager is null!");
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.zdsoft.cnet3.framework.persistence.transaction.ITransactionManager#execute(org.springframework.transaction.support.TransactionCallback)
     */
    public Object execute(ITransactionCallback action)
            throws TransactionException {
        final TransactionStatus status = transactionManager
                .getTransaction(new DefaultTransactionAttribute(
                        TransactionAttribute.PROPAGATION_REQUIRES_NEW));
        ConnectionHolder conHolder = (ConnectionHolder) TransactionSynchronizationManager
                .getResource(transactionManager.getDataSource());

        if (conHolder != null) {
            Object result = null;

            try {
                result = action.doInTransaction(createConnectionProxy(conHolder
                        .getConnection()), status);
            }
            catch (RuntimeException ex) {
                // transactional code threw application exception -> rollback
                rollbackOnException(status, ex);
                throw ex;
            }
            catch (Error err) {
                // transactional code threw error -> rollback
                rollbackOnException(status, err);
                throw err;
            }

            this.transactionManager.commit(status);

            return result;
        }
        else {
            throw new TransactionException(
                    "TransactionSynchronizationManager's ConnectionHolder is null");
        }
    }

    private Connection createConnectionProxy(Connection realConnection) {
        return (Connection) Proxy.newProxyInstance(ConnectionProxy.class
                .getClassLoader(), new Class[] { ConnectionProxy.class },
                new TransactionAwareInvocationHandler(realConnection,
                        transactionManager.getDataSource()));
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.zdsoft.cnet3.framework.persistence.transaction.ITransactionManager#connection()
     */
    public Connection connection() {
        final TransactionStatus status = transactionManager
                .getTransaction(new DefaultTransactionAttribute(
                        TransactionAttribute.PROPAGATION_REQUIRES_NEW));
        currentTransactionStatus.set(status);

        ConnectionHolder conHolder = (ConnectionHolder) TransactionSynchronizationManager
                .getResource(transactionManager.getDataSource());

        if (conHolder != null) {
            log.info("Getting connection");

            return createConnectionProxy(conHolder.getConnection());
        }
        else {
            throw new TransactionException(
                    "TransactionSynchronizationManager's ConnectionHolder is null");
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.zdsoft.cnet3.framework.persistence.transaction.ITransactionManager#beginTransaction()
     */
    public void beginTransaction() {
        log.info("Begin transaction");
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.zdsoft.cnet3.framework.persistence.transaction.ITransactionManager#rollback()
     */
    public void rollback() {
        try {
            transactionManager.rollback(currentTransactionStatus());
            log.info("Invoke transaction rollback");
        }
        finally {
            currentTransactionStatus.set(null);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.zdsoft.cnet3.framework.persistence.transaction.ITransactionManager#commit()
     */
    public void commit() {
        try {
            transactionManager.commit(currentTransactionStatus());
            log.info("Invoke transaction commit");
        }
        finally {
            currentTransactionStatus.set(null);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.zdsoft.cnet3.framework.persistence.transaction.ITransactionManager#releaseResource()
     */
    public void releaseResource() {
        TransactionStatus status = (TransactionStatus) currentTransactionStatus
                .get();

        if (status != null) {
            log
                    .error("Did not invoke commit() or rollback() method before releaseResource(), "
                            + "please recheck your code process!!!"
                                    .toUpperCase());
            log.warn("Invoke transaction rollback because of code error");

            try {
                transactionManager.rollback(status);
            }
            finally {
                currentTransactionStatus.set(null);
            }
        }
    }

    private TransactionStatus currentTransactionStatus() {
        TransactionStatus status = (TransactionStatus) currentTransactionStatus
                .get();

        if (status != null) {
            return status;
        }
        else {
            throw new TransactionException("No transaction status in scope");
        }
    }

    /**
     * Perform a rollback, handling rollback exceptions properly.
     * 
     * @param status
     *            object representing the transaction
     * @param ex
     *            the thrown application exception or error
     * @throws TransactionException
     *             in case of a rollback error
     */
    private void rollbackOnException(TransactionStatus status, Throwable ex)
            throws TransactionException {
        if (log.isDebugEnabled()) {
            log.debug(
                    "Initiating transaction rollback on application exception",
                    ex);
        }

        try {
            this.transactionManager.rollback(status);
        }
        catch (RuntimeException ex2) {
            log.error("Application exception overridden by rollback exception",
                    ex);
            throw ex2;
        }
        catch (Error err) {
            log.error("Application exception overridden by rollback error", ex);
            throw err;
        }
    }

    /**
     * Invocation handler that delegates close calls on JDBC Connections to
     * DataSourceUtils for being aware of thread-bound transactions.
     */
    private static class TransactionAwareInvocationHandler implements
            InvocationHandler {
        private static final String GET_TARGET_CONNECTION_METHOD_NAME = "getTargetConnection";
        private static final String CONNECTION_CLOSE_METHOD_NAME = "close";
        private final Connection target;
        private final DataSource dataSource;

        private TransactionAwareInvocationHandler(Connection target,
                DataSource dataSource) {
            this.target = target;
            this.dataSource = dataSource;
        }

        public Object invoke(Object proxy, Method method, Object[] args)
                throws Throwable {
            if (method.getName().equals(GET_TARGET_CONNECTION_METHOD_NAME)) {
                return this.target;
            }

            if (method.getName().equals(CONNECTION_CLOSE_METHOD_NAME)) {
                if (this.dataSource != null) {
                    DataSourceUtils.releaseConnection(this.target,
                            this.dataSource);
                }

                return null;
            }

            try {
                return method.invoke(this.target, args);
            }
            catch (InvocationTargetException ex) {
                throw ex.getTargetException();
            }
        }
    }
}
