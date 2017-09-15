package net.zdsoft.leadin.exception;

public class ServiceRollbackException extends Exception {

	private static final long serialVersionUID = -2199558652481548032L;

	public ServiceRollbackException() {
		super();
	}

	public ServiceRollbackException(String message) {
		super(message);
	}

	public ServiceRollbackException(String message, Throwable e) {
		super(message, e);
	}
}
