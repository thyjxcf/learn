/* 
 * @(#)Attachment.java    Created on Dec 3, 2010
 * Copyright (c) 2010 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.eis.base.attachment.entity;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.zdsoft.eis.base.common.entity.StorageDir.StorageDirType;
import net.zdsoft.eis.base.storage.StorageFileEntity;
import net.zdsoft.keelcnet.action.UploadFile;
import net.zdsoft.keelcnet.config.BootstrapManager;
import net.zdsoft.leadin.common.entity.BusinessTask;

/**
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: Dec 3, 2010 10:23:23 AM $
 */
public class Attachment extends StorageFileEntity {
    /**
     * Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = -2420668061417681397L;

    private static final Logger log = LoggerFactory.getLogger(Attachment.class);

    private String objectType;
    private String fileName;
    private long fileSize;
    private String contentType;
    private String description;
    private String objectId;
    private String unitId;
    
    private String extName;
    //是否用于转换
    private int conStatus = BusinessTask.TASK_STATUS_NOT_NEED_HAND;// 状态
    private String resultMsg;
    //扩展用于转换后获取url地址
    private String swfUrl;

    public String getSwfUrl() {
		return swfUrl;
	}

	public void setSwfUrl(String swfUrl) {
		this.swfUrl = swfUrl;
	}
    private UploadFile upfile;

	public StorageDirType getStorageDirType() {
        return StorageDirType.ATTACHMENT;
    }

    public String getObjectType() {
        return objectType;
    }

    public void setObjectType(String objectType) {
        this.objectType = objectType;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public long getFileSize() {
        return fileSize;
    }

    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public String getUnitId() {
        return unitId;
    }

    public void setUnitId(String unitId) {
        this.unitId = unitId;
    }

    public InputStream getContentsAsStream() throws IOException {
        return new FileInputStream(getFile());
    }

    public String getDownloadPath() {
        String basePath = BootstrapManager.getBaseUrl();
        String url = basePath + "/common/downloadAttachment.action?attachmentId=" + this.id;

        log.info("附件URL：" + url);

        return url;
    }

	public UploadFile getUpfile() {
		return upfile;
	}

	public void setUpfile(UploadFile upfile) {
		this.upfile = upfile;
	}


    public String getUrlPath() {
        return getDownloadPath();
    }

	public String getExtName() {
		return extName;
	}

	public void setExtName(String extName) {
		this.extName = extName;
	}
	public String getResultMsg() {
		return resultMsg;
	}

	public void setResultMsg(String resultMsg) {
		this.resultMsg = resultMsg;
	}

	public int getConStatus() {
		return conStatus;
	}

	public void setConStatus(int conStatus) {
		this.conStatus = conStatus;
	}

    
}
