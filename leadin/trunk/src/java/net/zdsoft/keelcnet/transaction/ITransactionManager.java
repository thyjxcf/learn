package net.zdsoft.keelcnet.transaction;

import java.sql.Connection;

/*
 * <p>城域综合信息平台</p>
 * <p>CNet3.0</p>
 * <p>Copyright (c) 2003</p>
 * <p>Company: ZDSoft</p>
 * @author Brave Tao
 * @since 1.0
 * @version $Id: ITransactionManager.java,v 1.1 2006/12/30 07:12:55 jiangl Exp $
 */
public interface ITransactionManager {
    public static final String LOCALTRANSACTIONMANAGER = "localTransactionManager";

    /**
     * 获得连接
     * 
     * @return java.sql.Connection
     */
    public Connection connection();

    /**
     * @deprecated see execute(ITransactionCallback action) 开始事务
     */
    public void beginTransaction();

    /**
     * @deprecated see execute(ITransactionCallback action) 递交事务
     */
    public void commit();

    /**
     * @deprecated see execute(ITransactionCallback action) 回滚事务
     */
    public void rollback();

    /**
     * @deprecated see execute(ITransactionCallback action) 释放资源
     */
    public void releaseResource();

    /**
     * 调整接口，直接在该方法中执行事务处理
     * 
     * for example:
     * 
     * ITransactionManager tm = (ITransactionManager)
     * ContainerManager.getComponent(BeanID.localTransactionManager);
     * tm.execute(new ITransactionCallback() { public Object
     * doInTransaction(Connection connection, TransactionStatus status) { //
     * your code goes here return null; } });
     * 
     * @param action
     * @return
     * @throws TransactionException
     */
    abstract Object execute(ITransactionCallback action)
            throws TransactionException;
}
