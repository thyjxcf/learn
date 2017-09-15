package net.zdsoft.keelcnet.transaction;

import org.springframework.transaction.TransactionStatus;

import java.sql.Connection;

/*
 * @author Brave Tao
 * @since 2004-11-18
 * @version $Id: ITransactionCallback.java,v 1.1 2006/12/30 07:12:55 jiangl Exp $
 * @since
 */
public interface ITransactionCallback {
    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.transaction.support.TransactionCallback#doInTransaction(org.springframework.transaction.TransactionStatus)
     */
    Object doInTransaction(Connection connection, TransactionStatus status);
}
