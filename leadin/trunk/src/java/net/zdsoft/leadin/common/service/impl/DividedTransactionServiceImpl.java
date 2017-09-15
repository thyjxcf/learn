/* 
 * @(#)DividedTransaction.java    Created on Sep 21, 2010
 * Copyright (c) 2010 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.leadin.common.service.impl;

import java.sql.SQLException;
import java.util.List;

import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import net.zdsoft.leadin.common.dao.SystemCommonDao;
import net.zdsoft.leadin.common.service.DividedTransactionBusinessDispose;
import net.zdsoft.leadin.common.service.DividedTransactionService;
import net.zdsoft.leadin.util.Assert;

/**
 * 记录数太多时分批提交
 * 
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: Sep 21, 2010 10:40:30 AM $
 */
public class DividedTransactionServiceImpl implements DividedTransactionService {
    private static final int BATCH_SIZE_DEFAULT = 300;// 默认批量提交记录数
    private int batchSize = BATCH_SIZE_DEFAULT;

    private TransactionTemplate transactionTemplate;

    public DividedTransactionServiceImpl() {
       
    }

    private SystemCommonDao systemCommonDao;

    public void setSystemCommonDao(SystemCommonDao systemCommonDao) {
        this.systemCommonDao = systemCommonDao;
    }

    public void setBatchSize(int batchSize) {
        this.batchSize = batchSize;
    }

    /**
     * 分事务提交
     * 
     * @param size
     * @param innerDispose
     */
    private int dividedTransCommit(int size, InnerDispose innerDispose) {
        int count = 0;
        int batchExecCount = (size % batchSize == 0) ? size / batchSize : size / batchSize + 1;

        for (int i = 0; i < batchExecCount; i++) {
            int from = batchSize * i;
            int to = 0;
            if (i == batchExecCount - 1) {
                to = size;
            } else {
                to = batchSize * (i + 1);
            }

            int batchResults = innerDispose.dispose(from, to);
            count += batchResults;
        }
        return count;
    }

    /**
     * 内部处理
     * 
     * @author zhaosf
     * @version $Revision: 1.0 $, $Date: Sep 21, 2010 11:21:42 AM $
     */
    private interface InnerDispose {
        public int dispose(int from, int to);
    }

    /**
     * 分批次保存且commit
     * 
     * @param sqls
     * @param batchSize
     * @return
     * @throws SQLException
     */
    public int dividedTransCommit(final String[] sqls) {
        if (sqls == null) {
            return 0;
        }

        return dividedTransCommit(sqls.length, new InnerDispose() {

            @Override
            public int dispose(int from, int to) {
                int len = to - from;
                final String[] destSql = new String[len];
                System.arraycopy(sqls, from, destSql, 0, len);

                int batchResults = transactionTemplate.execute(new TransactionCallback<Integer>() {

                    @Override
                    public Integer doInTransaction(TransactionStatus status) {
                        return systemCommonDao.batchUpdate(destSql);
                    }
                });
                return batchResults;
            }
        });
    }

    /**
     * 分批次保存且commit
     * 
     * @param listOfArgs
     * @param bd
     * @return
     */
    public <E> int dividedTransCommit(final DividedTransactionBusinessDispose<E> bd,
            final List<E> listOfArgs) {
        if (listOfArgs == null || listOfArgs.isEmpty()) {
            return 0;
        }

        int count = dividedTransCommit(listOfArgs.size(), new InnerDispose() {

            @Override
            public int dispose(int from, int to) {
                final List<E> batchListOfArgs = listOfArgs.subList(from, to);

                int batchResults = transactionTemplate.execute(new TransactionCallback<Integer>() {

                    @Override
                    public Integer doInTransaction(TransactionStatus status) {
                        return bd.saveDatas(batchListOfArgs);
                    }
                });
                return batchResults;
            }
        });
        return count;
    }
    
	/**
	 * 一次保存且commit
	 * 
	 * @param listOfArgs
	 * @param bd
	 * @return
	 */
	public <E> int onceTransCommit(final DividedTransactionBusinessDispose<E> bd, final List<E> listOfArgs) {
		int batchResults = transactionTemplate.execute(new TransactionCallback<Integer>() {
			@Override
			public Integer doInTransaction(TransactionStatus status) {
				return bd.saveDatas(listOfArgs);
			}
		});
		return batchResults;
	}

	public void setTransactionManager(PlatformTransactionManager transactionManager) {
		Assert.notNull(transactionManager, "The 'transactionManager' argument must not be null.");
		this.transactionTemplate = new TransactionTemplate(transactionManager);
		this.transactionTemplate
				.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
	}

    
}
