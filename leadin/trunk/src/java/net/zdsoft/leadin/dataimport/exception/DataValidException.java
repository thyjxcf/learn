package net.zdsoft.leadin.dataimport.exception;

/**
 * Created by IntelliJ IDEA. User: yaozw Date: 2005-4-7 Time: 17:37:26 代表原始数据不合法
 */
public class DataValidException extends Exception {
    private static final long serialVersionUID = -1458559222575598332L;

    public DataValidException() {
        super();
    }

    public DataValidException(String message) {
        super(message);
    }

    public DataValidException(Throwable cause) {
        super(cause);
    }

    public DataValidException(String message, Throwable cause) {
        super(message, cause);
    }
}
