package net.zdsoft.leadin.exception;

/**
 * 说明：
 * 在文件上传过程中由于网络环境的复杂，可能出现文件上传失败的情况。
 * 在办公和公文系统中，当附件文件上传失败时，仍然在数据库中保存了与该文件相关联的消息、通知或公文记录，
 * 导致在用户页面上看到有附件，但下载文件时出错，不能下载。
 * 该异常类为处理该情况创建的，建议使用该类来封装IOException
 * 在一个事务中，如果有文件上传的流程，那么在遇到此异常，则整个事务都应该回滚
 * 一般情况下，service不需要捕获该异常，当该异常抛出，spring会回滚该service方法的事务
 * 
 * @author fangb
 */
public class FileUploadFailException extends RuntimeException {
    private static final long serialVersionUID = -2950086593806468366L;

    public FileUploadFailException(){
		
	}
	
	public FileUploadFailException(String msg){
		super(msg);
	}
	
	public FileUploadFailException(Exception e){
		super(e);
	}
	
	public FileUploadFailException(String msg,Exception e){
		super(msg,e);
	}
}
