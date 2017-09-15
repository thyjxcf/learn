package net.zdsoft.eis.base.common.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletOutputStream;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;

import net.zdsoft.eis.base.common.service.StorageDirService;
import net.zdsoft.eis.base.storage.StorageFileUtils;
import net.zdsoft.eis.frame.action.BaseAction;
import net.zdsoft.keel.util.FileUtils;
import net.zdsoft.keel.util.URLUtils;
import net.zdsoft.keelcnet.config.BootstrapManager;

/**
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: Dec 7, 2010 10:10:26 AM $
 */
public class DownloadFileAction extends BaseAction {

    /**
     * Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 7489861512040592589L;

    private String filename;
    private String filePath;
    
    private String dirId;
    private boolean isDefaultPhoto;
    
    private StorageDirService storageDirService;

    public String execute() throws Exception {
        response = ServletActionContext.getResponse();

        try {
            String path = null;
            if (StringUtils.isBlank(filename)) {
                filename = "";
                path = filePath;
            } else {
                path = BootstrapManager.getStoreHome() + File.separator + filePath + File.separator
                        + filename;
            }

            File storefile = new File(path);

            if (!storefile.exists()) {
                throw new FileNotFoundException(storefile.getPath());
            }

            if (log.isDebugEnabled()) {
                log.debug("读取文件: " + storefile.getAbsolutePath());
            }

            InputStream data = new FileInputStream(storefile);
            int fileSize = (int) storefile.length();
            String mimeType = this.getServletContext().getMimeType(storefile.getAbsolutePath());

            if ((mimeType == null) || mimeType.trim().equals("")) {
                mimeType = "application/unknown";
            } else if (mimeType.startsWith("text/html")) {
                mimeType = "application/unknown";
            }
            mimeType = "application/x-msdownload";

            response.setHeader("Content-Disposition", "attachment; filename="
                    + URLUtils.encode(filename, "UTF-8"));

            response.setContentType(mimeType);
            response.setContentLength(fileSize);
            FileUtils.serveFile(response, data);
        } catch (Exception e) {
            promptMessageDto.setErrorMessage("文件下载出错！\n错误信息[" + e.getMessage() + "]");
            return PROMPTMSG;
        }
        return null;
    }  
    
    public String showPicture() {
        boolean showError = false;
        if (StringUtils.isBlank(filePath) || StringUtils.isBlank(dirId)) {
            showError = true;
        }
        String dir = storageDirService.getDir(dirId);
        if (StringUtils.isBlank(dir)) {
            showError = true;
        }
        File picFile = new File(dir + File.separator + filePath);
        if (!picFile.exists() || showError) {
        	if(isDefaultPhoto){
        		picFile = new File(this.getClass().getClassLoader().getResource("/businessconf/defaultUserPhoto.png").getFile());
        	}else{
        		picFile = new File(this.getClass().getClassLoader().getResource("/businessconf/invalidatePic.jpg").getFile());
        	}
        }
        getResponse().setContentType("image/jpeg; charset=GBK");
        ServletOutputStream outputStream;
        try {
            outputStream = getResponse().getOutputStream();
            FileInputStream inputStream = new FileInputStream(picFile);
            byte[] buffer = new byte[1024];
            int i = -1;
            while ((i = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, i);
            }
            outputStream.flush();
            outputStream.close();
            inputStream.close();
            outputStream = null;
            return SUCCESS;
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return SUCCESS;

    }
    
    public String showPictureAdmin(){
    	return SUCCESS;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public void setStorageDirService(StorageDirService storageDirService) {
        this.storageDirService = storageDirService;
    }

    public void setDirId(String dirId) {
        this.dirId = dirId;
    }

	public String getFilePath() {
		return filePath;
	}

	public String getDirId() {
		return dirId;
	}

	public boolean isDefaultPhoto() {
		return isDefaultPhoto;
	}

	public void setDefaultPhoto(boolean isDefaultPhoto) {
		this.isDefaultPhoto = isDefaultPhoto;
	}

}
