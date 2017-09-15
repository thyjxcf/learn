package net.zdsoft.eis.system.desktop.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import org.apache.struts2.ServletActionContext;
import net.sf.json.JSONObject;
import net.zdsoft.eis.frame.action.SwfUploadAction;
import net.zdsoft.eis.frame.util.SwfUploadUtils;
import net.zdsoft.keel.util.DateUtils;
import net.zdsoft.keel.util.FileUtils;
import net.zdsoft.keel.util.ImageUtils;
import net.zdsoft.keel.util.ServletUtils;
import net.zdsoft.keel.util.URLUtils;
import net.zdsoft.keelcnet.config.BootstrapManager;
import net.zdsoft.keelcnet.util.GUIdGenerator;

/**
 * 
 *@author weixh
 *@since 2013-8-22
 */
@SuppressWarnings("serial")
public class ImageUploadAction extends SwfUploadAction {
	private boolean imgDisplay = false;
	protected int wid=568;
	protected int height=186;
	
	public boolean reload = true;
	private String bdStatUrl;
	private String usablePointsName;
	
	protected Map<String, Object> jsonMap = new HashMap<String, Object>();
	
	public String execute(){
		this.fileSizeLimit = "5MB";
		albumId = getUnitId();
		this.fileTypes = "*.gif;*.bmp;*.jpg;*.jpeg;*.png";
		this.uploadDirect = true;
		imgDisplay = false;
		return SUCCESS;
	}
	
	// 上传
	public String upload(){
		return super.upload();
	}
	
	// 删除
	public String deleteFile(){
		return super.deleteFile();
	}
	
	/**
	 * 上传处理图片
	 */
	protected void dealWithPhoto(File uploadedFile, String fileName)
			throws IOException {
		String picName = this.fileId+".jpg";
		// 文件保存路径 store/yyyyMMdd/albumId/fileId.jpg
		String dest = uploadedFile.getParentFile().getParent();
		// 等比例修改图片大小
		ImageUtils.changeOppositeSize(uploadedFile.getAbsolutePath(), dest + File.separator
				+ picName, wid, height);
//		SwfUploadUtils.deleteFile(uploadedFile);// 删除原初始文件
	}

	// 展现图片
	public String display(){
		String filePath = getDisplayImgFilePath();
		
		HttpServletResponse response = ServletActionContext.getResponse();

        try {
            String path = null;
            String filename = GUIdGenerator.getUUID();
            path = filePath;

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
        	log.error(e.getMessage(), e);
        }
		return NONE;
	}
	
	/**
	 * 得到需要展现的图片路径
	 * 默认:store/swfupload/yyyyMMdd/albumId/fileId.jpg
	 * @return
	 */
	protected String getDisplayImgFilePath(){
		return BootstrapManager.getStoreHome() + File.separator + SwfUploadUtils.SWF_UPLOAD_PATH
				+ File.separator
				+ DateUtils.date2String(new Date(), "yyyyMMdd") + File.separator
				+ albumId + File.separator + fileId+".jpg";
	}
	
	public boolean isImgDisplay() {
		return imgDisplay;
	}

	public void setImgDisplay(boolean imgDisplay) {
		this.imgDisplay = imgDisplay;
	}
	
	public String responseJSON() {
		return responseJSON(jsonMap);
	}
	
    private String responseJSON(Map<String, Object> jsonMap) {
		JSONObject jsonObject = new JSONObject();
		for (String key : jsonMap.keySet()) {
			jsonObject.put(key, jsonMap.get(key));
		}
		try {
			ServletUtils.print(getResponse(), jsonObject.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return NONE;
	}
    
 	public String getBdStatUrl() {
 		return bdStatUrl;
 	}

 	public boolean isReload() {
 		return reload;
 	}

 	public void setReload(boolean reload) {
 		this.reload = reload;
 	}
	
}
