package net.zdsoft.leadin.exception;

public class FormatException extends Exception {

    /**
     * 
     */
    private static final long serialVersionUID = 8866385452585032628L;

    public FormatException() {
        super();
    }

    public FormatException(String message, Throwable cause) {
        super(message, cause);
    }

    public FormatException(String message) {
        super(message);
    }

    public FormatException(Throwable cause) {
        super(cause);
    }

    public FormatException(String message, String fieldName) {
        super(message, new Throwable(fieldName));
    }

    /**
     * 取字段名
     * 
     * @return
     */
    public String getField() {
        String throwStr = "java.lang.Throwable: ";
        return (super.getCause()).toString().substring(throwStr.length());
    }

}
