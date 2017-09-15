package net.zdsoft.leadin.dataimport.exception;

/**
 * 字段错误的异常类
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: Aug 23, 2010 4:16:28 PM $
 */
public class ErrorFieldException extends Exception {
    private static final long serialVersionUID = -6115263191194665764L;

    public ErrorFieldException() {
        super();
    }

    public ErrorFieldException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * @param message
     */
    public ErrorFieldException(String message) {
        super(message);
    }

    /**
     * @param cause
     */
    public ErrorFieldException(Throwable cause) {
        super(cause);
    }

    /**
     * 消息和字段
     * 
     * @param message 消息
     * @param field 字段
     */
    public ErrorFieldException(String message, String field) {
        super(message, new Throwable(field));
    }

    /**
     * 取字段名
     * 
     * @return
     */
    public String getField() {
        String throwStr = "java.lang.Throwable: ";
        if (null != super.getCause()) {
            return (super.getCause()).toString().substring(throwStr.length());
        } else {
            return null;
        }
    }
}
