/* 
 * @(#)DividedTransactionService.java    Created on Sep 25, 2010
 * Copyright (c) 2010 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.leadin.common.service;

import java.sql.SQLException;
import java.util.List;


public interface DividedTransactionService {

    /**
     * 分批次保存且commit
     * 
     * @param sqls
     * @param batchSize
     * @return
     * @throws SQLException
     */
    public int dividedTransCommit(String[] sqls);

    /**
     * 分批次保存且commit
     * 
     * @param listOfArgs
     * @param bd
     * @return
     */
    public <E> int dividedTransCommit(DividedTransactionBusinessDispose<E> bd, List<E> listOfArgs);
    
    /**
     * 一次保存且commit
     * @param bd
     * @param listOfArgs
     * @return
     */
	public <E> int onceTransCommit(final DividedTransactionBusinessDispose<E> bd, final List<E> listOfArgs);

}
