package net.zdsoft.eis.frame.service.impl;

import java.io.File;
import java.util.Date;

import net.zdsoft.eis.base.common.service.StorageDirService;
import net.zdsoft.eis.frame.service.SwfUploadService;
import net.zdsoft.eis.frame.util.SwfUploadUtils;
import net.zdsoft.keel.util.DateUtils;

public class SwfUploadServiceImpl implements SwfUploadService {

	private StorageDirService storageDirService;

	public void setStorageDirService(StorageDirService storageDirService) {
		this.storageDirService = storageDirService;
	}

	@Override
	public void deleteUploadTempFile() {
		String storageDir = storageDirService.getActiveStorageDir().getDir();
		File tempFile = new File(storageDir
				+ File.separator
				+ SwfUploadUtils.SWF_UPLOAD_PATH
				+ File.separator
				+ DateUtils.date2String(DateUtils.addDay(new Date(), -1),
						"yyyyMMdd"));
		SwfUploadUtils.deleteFile(tempFile);
	}

}
