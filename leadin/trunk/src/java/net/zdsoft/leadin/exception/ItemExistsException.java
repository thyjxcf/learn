package net.zdsoft.leadin.exception;

/**
 * 表示某类事物存在的异常类.
 * <p>ZDSoft学籍系统（stusys）V3.5</p>
 * @author zhaosf
 * @since 1.0
 * @version $Id: ItemExistsException.java,v 1.3 2006/12/28 07:58:32 jiangl Exp $
 */
public class ItemExistsException extends RuntimeException {
    private static final long serialVersionUID = 312349260684407320L;

    public ItemExistsException() {
        super();
    }

    public ItemExistsException(String message, Throwable cause) {
        super(message, cause);
    }
    
    /**
     * @param message
     */
    public ItemExistsException(String message) {
        super(message);
    }

    /**
     * @param cause
     */
    public ItemExistsException(Throwable cause) {
        super(cause);
    }
      
    /**
     * 消息和字段
     * @param message 消息
     * @param field 字段
     */
    public ItemExistsException(String message, String field) {
        super(message , new Throwable(field));
    }
    
    /**
     * 取字段名
     * @return
     */
    public String getField() {
    	String throwStr = "java.lang.Throwable: "; 
    	if(null != super.getCause()){
    		return (super.getCause()).toString().substring(throwStr.length());
    	}else{
    		return null;
    	}
    }
}
