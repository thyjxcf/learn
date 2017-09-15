package net.zdsoft.eis.remote;


/**
 * <Description> 传入参加解密异常<br> 
 *  
 * @author MCZ<br>
 * @version 1.0<br>
 * @taskId <br>
 * @CreateDate 2016年5月12日 <br>
 */
public class ParseException extends RuntimeException {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = -1187516993124229948L;

    /**
     * 构造函数
     * 
     * @param throwable 异常
     */
    public ParseException(Throwable throwable) {
        super(throwable);
    }

    /**
     * 构造函数
     * @param message 异常消息
     */
    public ParseException(String message) {
        super(message);
    }
    
    /**
     * 构造函数
     * @param message 异常消息
     * @param cause 堆栈异常
     */
    public ParseException(String message, Throwable cause) {
        super(message, cause);
    }

}
