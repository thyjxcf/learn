/* 
 * @(#)AttachmentAction.java    Created on Dec 7, 2010
 * Copyright (c) 2010 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.eis.base.attachment.action;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import net.zdsoft.eis.base.attachment.entity.Attachment;
import net.zdsoft.eis.base.attachment.service.AttachmentService;
import net.zdsoft.eis.frame.action.BaseAction;
import net.zdsoft.keel.util.FileUtils;
import net.zdsoft.keel.util.URLUtils;

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
                response.setHeader("Content-Disposition", "attachment; filename=\""
                        + URLUtils.encode(attachment.getFileName(), "UTF-8").replace("+", "%20")+ "\";");

                response.setContentType(mimeType);
                response.setContentLength(fileSize);

                if (log.isDebugEnabled()) {
                    StringBuffer sb = new StringBuffer();
                    sb.append("\nContent-disposition: attachment; filename=\""
                            + attachment.getFileName() + "\";");
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
            promptMessageDto.setErrorMessage("文件下载出错！\n错误信息[" + e.getMessage() + "]");
            return PROMPTMSG;
        }
        return NONE;
    }
 
    public void setAttachmentService(AttachmentService attachmentService) {
        this.attachmentService = attachmentService;
    }

    public void setAttachmentId(String attachmentId) {
        this.attachmentId = attachmentId;
    }

}
