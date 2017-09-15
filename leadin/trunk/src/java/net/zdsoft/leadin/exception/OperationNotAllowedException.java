package net.zdsoft.leadin.exception;

/**
 * 表示某个操作不被允许执行的异常类
 * @author zhaosf
 * @since 1.0
 * @version $Id: OperationNotAllowedException.java, v 1.0 2007-7-23 上午11:22:46 zhaosf Exp $
 */
public class OperationNotAllowedException extends BusinessErrorException {
    private static final long serialVersionUID = -8955974791310495290L;

    public OperationNotAllowedException(String message, Throwable cause) {
        super(message, cause);
    }
    
    /**
     * @param message
     */
    public OperationNotAllowedException(String message) {
        super(message);
    }
      
    /**
     * 消息和字段
     * @param message 消息
     * @param field 字段
     */
    public OperationNotAllowedException(String message, String field) {
        super(message , new Throwable(field));
    }   
}


