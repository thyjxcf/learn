package net.zdsoft.leadin.exception;

public class SendMailException extends RuntimeException {
    private static final long serialVersionUID = -7369365866466905014L;

    public SendMailException() {
        super();
    }

    public SendMailException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * @param message
     */
    public SendMailException(String message) {
        super(message);
    }

    /**
     * @param cause
     */
    public SendMailException(Throwable cause) {
        super(cause);
    }

}
