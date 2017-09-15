package net.zdsoft.keelcnet.transaction;

import net.zdsoft.keelcnet.exception.InfrastructureException;



/*
 * <p>城域综合信息平台</p>
 * <p>CNet3.0</p>
 * <p>Copyright (c) 2003</p>
 * <p>Company: ZDSoft</p>
 * @author Brave Tao
 * @since 1.0
 * @version $Id: TransactionException.java,v 1.1 2006/12/30 07:12:55 jiangl Exp $
 */
public class TransactionException extends InfrastructureException {
    /**
     * Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = -3678858678022681277L;

    public TransactionException(Throwable cause) {
        super(cause);
    }

    public TransactionException(String msg) {
        super(msg);
    }

    public TransactionException(String msg, Throwable cause) {
        super(msg, cause);
    }
}

