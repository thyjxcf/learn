/* 
 * @(#)DataImportJob.java    Created on Jan 4, 2008
 * Copyright (c) 2006 ZDSoft Networks, Inc. All rights reserved.
 * $Header$
 */
package net.zdsoft.leadin.dataimport.entity;

import java.util.Date;

import org.apache.commons.lang.StringUtils;

import net.zdsoft.keelcnet.config.BootstrapManager;
import net.zdsoft.leadin.common.entity.BusinessTask;

public class ImportDataJob extends BusinessTask{   
    private String userId;
    private Date updatestamp; // 更新戳
    private String isDeleted; // 软删除    
    
    private String section; //学段
    private String objectName; //导入文件的类型
    private String importDependOn; //导入依据
    private String initFile; //配置文件路径
    private String errorFile;//错误文件
    
    private int jobPos;//位置


	public String getInitFile() {
        return initFile;
    }

    public void setInitFile(String initFile) {
        this.initFile = initFile;
    }

    public String getObjectName() {
        return objectName;
    }

    public void setObjectName(String objectName) {
        this.objectName = objectName;
    }

    public String getImportDependOn() {
        return importDependOn;
    }

    public void setImportDependOn(String importDependOn) {
        this.importDependOn = importDependOn;
    }

    public String getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(String isDeleted) {
        this.isDeleted = isDeleted;
    }

    public Date getUpdatestamp() {
        return updatestamp;
    }

    public void setUpdatestamp(Date updatestamp) {
        this.updatestamp = updatestamp;
    }
 
    public String getFileFullPath() {
    	String filePath = getFilePath();
        if (StringUtils.isEmpty(filePath))
            return filePath;
        return BootstrapManager.getStoreHome() + filePath;
    }
 
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }
    
    public String getErrorFile() {
        return errorFile;
    }

    public String getErrorFullFile() {
        if (StringUtils.isEmpty(errorFile))
            return errorFile;
        return BootstrapManager.getStoreHome() + errorFile;
    }
    
    public void setErrorFile(String errorFile) {
        this.errorFile = errorFile;
    }

    public int getJobPos() {
        return jobPos;
    }

    public void setJobPos(int jobPos) {
        this.jobPos = jobPos;
    }
  
}
