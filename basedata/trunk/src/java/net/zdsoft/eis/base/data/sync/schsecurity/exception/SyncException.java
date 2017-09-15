package net.zdsoft.eis.base.data.sync.schsecurity.exception;


public class SyncException extends RuntimeException{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6812472903958084235L;
	
	public SyncException(String msg) {
		super(msg);
	}
	
	public SyncException(String msg, Throwable throwable) {
		super(msg, throwable);
	}
}
