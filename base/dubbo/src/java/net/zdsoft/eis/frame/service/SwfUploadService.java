package net.zdsoft.eis.frame.service;

public interface SwfUploadService {
	//删除临时的上传文件 每天凌晨4点清理昨天的文件
	public void deleteUploadTempFile();
}
