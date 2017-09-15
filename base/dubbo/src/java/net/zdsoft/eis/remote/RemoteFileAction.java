package net.zdsoft.eis.remote;

import java.io.File;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;
import net.zdsoft.eis.base.common.entity.StorageDir;
import net.zdsoft.eis.base.common.service.StorageDirService;
import net.zdsoft.eis.frame.action.BaseAction;
import net.zdsoft.keel.util.ServletUtils;
import net.zdsoft.keelcnet.util.FileUtils;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.dispatcher.multipart.MultiPartRequestWrapper;

public class RemoteFileAction extends BaseAction {
	private String fileName;
    private String dirPath;
    private StorageDirService storageDirService;

    public String remoteReceiveFile() throws Exception {
    	HttpServletRequest request = ServletActionContext.getRequest();
        if (request instanceof MultiPartRequestWrapper) {
            StorageDir dir = storageDirService.getActiveStorageDir();
            if (dir == null) {
                JSONObject json = new JSONObject();
                json.put("code", -1);
                json.put("message", "没有设置Store目录！");
                ServletUtils.print(getResponse(), json.toString());
                return SUCCESS;
            }
            MultiPartRequestWrapper requestWrapper = (MultiPartRequestWrapper) request;
            Enumeration<String> e = requestWrapper.getFileParameterNames();
            if (e.hasMoreElements()) {
                String fieldName = e.nextElement();
                File uploadedFile = (((File[]) requestWrapper.getFiles(fieldName))[0]);
                if(StringUtils.isBlank(fileName))
                	fileName = requestWrapper.getFileNames(fieldName)[0];
                File dirPathFile = new File(dir.getDir() + File.separator + dirPath);
                if (!dirPathFile.exists())
                    dirPathFile.mkdirs();
                File file = new File(dirPathFile, fileName);
                FileUtils.copyFile(uploadedFile, file);
            }
        }
        JSONObject json = new JSONObject();
        json.put("code", 1);
        json.put("message", "上传成功！");
        ServletUtils.print(getResponse(), json.toString());
        return SUCCESS;
    }

    public void setStorageDirService(StorageDirService storageDirService) {
        this.storageDirService = storageDirService;
    }
    public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getDirPath() {
		return dirPath;
	}

	public void setDirPath(String dirPath) {
		this.dirPath = dirPath;
	}

}
