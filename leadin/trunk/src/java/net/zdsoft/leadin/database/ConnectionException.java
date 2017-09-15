package net.zdsoft.leadin.database;

import net.zdsoft.keelcnet.exception.InfrastructureException;


/* 
 * 数据库连接异常类
 * 
 * <p>ZDSoft学籍系统（stusys）V3.5</p>
 * @author Dongzk
 * @since 1.0
 * @version $Id: ConnectionException.java,v 1.2 2007/01/09 10:03:05 jiangl Exp $
 */
public class ConnectionException extends InfrastructureException {
    private static final long serialVersionUID = -8708093786174622876L;

    public ConnectionException(Throwable cause) {
        super(cause);
    }

    public ConnectionException(String msg) {
        super(msg);
    }

    public ConnectionException(String msg, Throwable cause) {
        super(msg, cause);
    }
}



