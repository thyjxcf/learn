package net.zdsoft.leadin.exception;

/**
 * 字段错误异常
 * 
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: Dec 8, 2010 3:46:23 PM $
 */
public class FieldErrorException extends BusinessErrorException {
    private static final long serialVersionUID = 312349260684407320L;

    private String field;

    /**
     * 消息和字段
     * 
     * @param message 消息
     * @param field 字段
     */
    public FieldErrorException(String field, String message) {
        super(message);
        this.field = field;
    }

    /**
     * 取字段名
     * 
     * @return
     */
    public String getField() {
        return field;
    }
}
