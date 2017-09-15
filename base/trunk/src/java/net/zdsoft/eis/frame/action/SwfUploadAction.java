package net.zdsoft.eis.frame.action;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import net.zdsoft.eis.base.common.service.StorageDirService;
import net.zdsoft.eis.frame.util.SwfUploadUtils;
import net.zdsoft.keel.util.DateUtils;
import net.zdsoft.keel.util.ServletUtils;
import net.zdsoft.keelcnet.action.UploadFile;
import net.zdsoft.keelcnet.util.FileUtils;
import net.zdsoft.leadin.util.UUIDGenerator;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.dispatcher.multipart.MultiPartRequestWrapper;

public class SwfUploadAction extends PageSemesterAction {

	private static final long serialVersionUID = -7696899689283862820L;
	protected String albumId;
	protected String fileId;
	// 用户可自定义的参数
	protected String fileSizeLimit = "100MB";// 文件大小
	protected String fileTypes = "*.*";// 支持的文件类型
	protected int fileUploadLimit = 0;// 文件数量限制，0表示不限制
	protected boolean uploadDirect = true;
	protected String destDirPath;// 不包括storageDir的文件路径

	protected StorageDirService storageDirService;
	private static Object s = true;
	public static void main(String[] args) {
		if(Boolean.getBoolean((String)s)){
			System.out.println(11);
		}
	}

	public String upload() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String storageDir = storageDirService.getActiveStorageDir().getDir();
		if (request instanceof MultiPartRequestWrapper) {
			MultiPartRequestWrapper requestWrapper = (MultiPartRequestWrapper) request;
			Enumeration<String> e = requestWrapper.getFileParameterNames();
			if (e.hasMoreElements()) {
				String fieldName = e.nextElement();
				File uploadedFile = (((File[]) requestWrapper
						.getFiles(fieldName))[0]);
				String fileName = requestWrapper.getFileNames(fieldName)[0];
				
				if (StringUtils.isBlank(fileId)) {
					fileId = UUIDGenerator.getUUID();
				}
				String destDir = storageDir + File.separator
							+ SwfUploadUtils.SWF_UPLOAD_PATH
							+ File.separator
							+ DateUtils.date2String(new Date(), "yyyyMMdd") + File.separator
							+ albumId + File.separator + fileId;
				File destFile = new File(destDir);
				if(!destFile.exists()){
					destFile.mkdir();
				}
				try {
					if (!destFile.exists())
						destFile.mkdir();
					File toFile = new File(destFile.getAbsolutePath() + File.separator
							+ fileName);
					FileUtils.copyFile(uploadedFile, toFile);
					
					dealWithPhoto(toFile, fileName);
					// 这里的内容，会返回给js的回调函数uploadSuccess中的serverData参数
					ServletUtils.print(getResponse(), fileId);
				} catch (IOException e1) {
					log.error(e1.getMessage(), e1);
				}
			}
		}
		return null;
	}
	
	protected void dealWithPhoto(File uploadedFile, String fileName) throws IOException {
		
	}

	public UploadFile[] handleFileUpload() {
		String storageDir = storageDirService.getActiveStorageDir().getDir();
		File tempFile = new File(storageDir + File.separator
				+ SwfUploadUtils.SWF_UPLOAD_PATH
				+ File.separator
				+ DateUtils.date2String(new Date(), "yyyyMMdd") + File.separator
				+ albumId);
		File[] files = tempFile.listFiles();
		List<UploadFile> filelist = new ArrayList<UploadFile>();
		if (files != null) {
			for (int i = 0; i < files.length; i++) {
				if (!files[i].isDirectory()) {
					String fileName = files[i].getName();
					int slash = Math.max(fileName.lastIndexOf('/'),
							fileName.lastIndexOf('\\'));
					if (slash > -1) {
						fileName = fileName.substring(slash + 1);
					}
					UploadFile uploadfile = new UploadFile(fileName, files[i],
							null, fileName);
					filelist.add(uploadfile);
				} else {
					File[] secondfiles = files[i].listFiles();
					for (int j = 0; j < secondfiles.length; j++) {
						String fileName = secondfiles[j].getName();
						int slash = Math.max(fileName.lastIndexOf('/'),
								fileName.lastIndexOf('\\'));
						if (slash > -1) {
							fileName = fileName.substring(slash + 1);
						}
						UploadFile uploadfile = new UploadFile(fileName,
								secondfiles[j], null, fileName);
						filelist.add(uploadfile);
					}
				}
			}
		}
		return (UploadFile[]) filelist.toArray(new UploadFile[0]);
	}

	public String deleteFile() {
		String storageDir = storageDirService.getActiveStorageDir().getDir();
		if(StringUtils.isBlank(destDirPath)){
			destDirPath = SwfUploadUtils.SWF_UPLOAD_PATH
					+ File.separator
					+ DateUtils.date2String(new Date(), "yyyyMMdd") + File.separator
					+ albumId + File.separator + fileId;
		}
		File tempFile = new File(storageDir +  File.separator
				+ destDirPath);
		SwfUploadUtils.deleteFile(tempFile);
		return "message";
	}

	public String getAlbumId() {
		if (StringUtils.isBlank(albumId))
			albumId = UUIDGenerator.getUUID();
		return albumId;
	}

	public void setAlbumId(String albumId) {
		this.albumId = albumId;
	}

	public String getFileSizeLimit() {
		return fileSizeLimit;
	}

	public void setFileSizeLimit(String fileSizeLimit) {
		this.fileSizeLimit = fileSizeLimit;
	}

	public String getFileTypes() {
		return fileTypes;
	}

	public void setFileTypes(String fileTypes) {
		this.fileTypes = fileTypes;
	}

	public boolean isUploadDirect() {
		return uploadDirect;
	}

	public void setUploadDirect(boolean uploadDirect) {
		this.uploadDirect = uploadDirect;
	}

	public String getFileId() {
		return fileId;
	}

	public void setFileId(String fileId) {
		this.fileId = fileId;
	}

	public void setStorageDirService(StorageDirService storageDirService) {
		this.storageDirService = storageDirService;
	}

	public int getFileUploadLimit() {
		return fileUploadLimit;
	}

	public void setFileUploadLimit(int fileUploadLimit) {
		this.fileUploadLimit = fileUploadLimit;
	}

}
