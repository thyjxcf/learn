/* 
 * @(#)AttachmentAction.java    Created on Dec 7, 2010
 * Copyright (c) 2010 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.eis.base.attachment.action;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

import net.zdsoft.eis.base.attachment.entity.Attachment;
import net.zdsoft.eis.base.attachment.service.AttachmentService;
import net.zdsoft.eis.base.common.service.SystemIniService;
import net.zdsoft.eis.base.constant.BaseConstant;
import net.zdsoft.eis.frame.action.BaseAction;
import net.zdsoft.keel.util.FileUtils;
import net.zdsoft.keel.util.URLUtils;
import net.zdsoft.leadin.common.entity.BusinessTask;

/**
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: Dec 7, 2010 10:10:26 AM $
 */
public class AttachmentAction extends BaseAction {

    /**
     * Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 7489861512040592589L;

    private AttachmentService attachmentService;

    private String attachmentId;
    private String downloadName;//设定下载名称
    private boolean inline = false;//pdf在线预览
    
    private String resourceId;
    private int status; 
    
	private SystemIniService systemIniService;
	
	public void setSystemIniService(SystemIniService systemIniService) {
		this.systemIniService = systemIniService;
	}
    
    public String execute() throws Exception {
        response = getResponse();

        try {
            Attachment attachment = attachmentService.getAttachment(attachmentId);
            if (attachment == null) {
                promptMessageDto.setErrorMessage("附件没有找到！\n附件 ID [" + attachmentId + "]");
                return PROMPTMSG;
            }

            InputStream attachmentData = attachment.getContentsAsStream();
            if (attachmentData != null) {
                String mimeType = null;
                int fileSize = 0;

                try {
                    File file = attachment.getFile();
                    mimeType = this.getServletContext().getMimeType(file.getAbsolutePath());
                    fileSize = (int) file.length();
                } catch (IOException e) {
                    mimeType = attachment.getContentType();
                    fileSize = (int) attachment.getFileSize();
                }

                if ((mimeType == null) || mimeType.trim().equals("")) {
                    mimeType = "application/unknown";
                } else if (mimeType.startsWith("text/html")) {
                    mimeType = "application/unknown";
                }
                // mimeType = "application/x-msdownload";
              //HttpUtility.UrlEncode 在 Encode 的时候, 将空格转换成加号('+'), 在 Decode 的时候将加号转为空格, 但是浏览器是不能理解加号为空格的, 所以如果文件名包含了空格, 在浏览器下载得到的文件, 空格就变成了加号
                //downloadName:参数传入中，先用URLEncoder.encode(downloadName, "UTF8");
                if(StringUtils.isBlank(downloadName)){
                	downloadName=attachment.getFileName();
                }else{
                	//后缀名
                	String fileExt=FileUtils.getExtension(attachment.getFileName());
                	if(StringUtils.isNotBlank(fileExt)){
                		downloadName=downloadName+"."+fileExt;
                	}
                	
                }
                if(inline){
					response.setHeader("Content-Disposition", "inline;filename=\""
							+ URLUtils.encode(downloadName,
									"UTF-8").replace("+", "%20")
									+ "\";");
				}else{
					response.setHeader("Content-Disposition", "attachment; filename=\""
							+ URLUtils.encode(downloadName, "UTF-8").replace("+", "%20")+ "\";");
				}
                response.setContentType(mimeType);
                response.setContentLength(fileSize);

                if (log.isDebugEnabled()) {
                    StringBuffer sb = new StringBuffer();
                    sb.append("\nContent-disposition: attachment; filename=\""
                            + downloadName + "\";");
                    sb.append("\n        ContentType: " + attachment.getContentType());
                    sb.append("\n      ContentLength: " + attachment.getFileSize());
                    log.debug("\n附件下载：" + sb.toString());
                }

                FileUtils.serveFile(response, attachmentData);
            } else {
                promptMessageDto.setErrorMessage("附件没有找到！\n附件 ID [" + attachmentId + "]");
                return PROMPTMSG;
            }

        } catch (Exception e) {
        	e.printStackTrace();
            promptMessageDto.setErrorMessage("文件下载出错！\n错误信息[" + e.getMessage() + "]");
            return PROMPTMSG;
        }
        return NONE;
    }
    
    
    
    public void windowConverterAttachment() throws Exception{
    	if(StringUtils.isNotEmpty(resourceId)){
    		Attachment attachment = attachmentService.getAttachment(resourceId);
    		if(attachment!=null){
    			if(status==1){
    				attachment.setConStatus(BusinessTask.TASK_STATUS_SUCCESS);
    				attachment.setResultMsg("window转换服务转换成功");
    				String filePath = attachment.getFile().getAbsolutePath();
    		        String filePathNoPostfix = filePath.substring(0, filePath.lastIndexOf("."));
    		        String swfFilePath = filePathNoPostfix + ".swf";
//    		        String picFilePath = filePathNoPostfix + "_picture.jpg";
//    		        String previewPicFilePath = filePathNoPostfix + "_preview.jpg";
//    		        String defaultPdfPath = filePathNoPostfix + "_default.pdf";
//    		        String handPdfPath = filePathNoPostfix + "_hand.pdf";
    		        String pdfPath = filePathNoPostfix + ".pdf";
    		        System.out.println(pdfPath);
    		        String url = getWindowUrl()+"/download";
    		        Map<String,String> paramMap = new HashMap<String,String>();
    		        paramMap.put("fileType", "0");
    		        paramMap.put("resId", resourceId);
    		        paramMap.put("serverId", getServiceId());
    		        InputStream input =URLUtils.visitContent(url, paramMap, true);
    		        saveFile(input, pdfPath);
    		        paramMap.put("fileType", "1");
    		        InputStream swfInput =URLUtils.visitContent(url, paramMap, true);
    		        saveFile(swfInput, swfFilePath);
    			}else{
    				attachment.setConStatus(BusinessTask.TASK_STATUS_ERROR);
    				attachment.setResultMsg("window转换服务转换失败");
    			}
    			attachmentService.updateAttachment(attachment);
    		}
    	}
    }
    
	private Boolean saveFile(Object src, String filePath) throws Exception {
		if (src != null) {
			InputStream input = null;
			if (src instanceof File) {
				input = new FileInputStream((File) src);
			} else if (src instanceof String) {
				input = new ByteArrayInputStream(
						((String) src).getBytes("utf-8"));
			} else if (src instanceof InputStream) {
				input = (InputStream) src;
			}
			try {
				File file = new File(filePath);
				if(file.exists()){
					file.delete();
				}
				File fileDir = file.getParentFile();
				if(!fileDir.exists()){
					fileDir.mkdirs();
				}
				net.zdsoft.keelcnet.util.FileUtils.copyFile(input, file);
			} finally {
				if (input != null) {
					IOUtils.closeQuietly(input);
				}
			}
			return true;
		} else {
			return false;
		}
	}
    
    
    public void setAttachmentService(AttachmentService attachmentService) {
        this.attachmentService = attachmentService;
    }

    public void setAttachmentId(String attachmentId) {
        this.attachmentId = attachmentId;
    }

	public String getDownloadName() {
		return downloadName;
	}

	public void setDownloadName(String downloadName) {
		this.downloadName = downloadName;
	}

	public boolean isInline() {
		return inline;
	}

	public void setInline(boolean inline) {
		this.inline = inline;
	}

	public String getResourceId() {
		return resourceId;
	}

	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
	
	private String getWindowUrl(){
		String type =  systemIniService.getValue(BaseConstant.SYSTEM_CONVERTOR_WINDOW_URL);
		if (StringUtils.isNotBlank(type)) {
			return type;
		}else{
			return "0";
		}
	}
	
	private String getEisUrl(){
		String type =  systemIniService.getValue(BaseConstant.SYSTEM_CONVERTOR_EIS_URL);
		if (StringUtils.isNotBlank(type)) {
			return type;
		}else{
			return "0";
		}
	}
	
	private String getServiceId(){
		String type =  systemIniService.getValue(BaseConstant.SYSTEM_CONVERTOR_WINDOW_SERVER);
		if (StringUtils.isNotBlank(type)) {
			return type;
		}else{
			return "0";
		}
	}
}
