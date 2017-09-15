package net.zdsoft.eis.frame.util;

import java.io.File;

public class SwfUploadUtils {
	
	public static final String SWF_UPLOAD_PATH ="swfupload";
	
	/**
	 * 递归删除file
	 * 
	 * @param file
	 */
	public static void deleteFile(File file) {
		if (file.exists()) {
			if (file.isDirectory()) {
				File[] files = file.listFiles();
				for (int i = 0; i < files.length; i++) {
					deleteFile(files[i]);
				}
				file.delete();
			} else {
				file.delete();
			}
		}
	}
}
