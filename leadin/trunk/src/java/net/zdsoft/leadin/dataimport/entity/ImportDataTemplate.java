/* 
 * @(#)ImportDataTemplate.java    Created on Aug 10, 2010
 * Copyright (c) 2010 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.leadin.dataimport.entity;

import java.util.Date;

/**
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: Aug 10, 2010 10:15:41 AM $
 */
public class ImportDataTemplate {
    private String id;
    private String unitId;
    private String initFile;// 配置文件
    private String objectName;// 导入文件的类型
    private String fieldName;// 字段名
    private Date creationTime; // 创建时间戳
    private Date modifyTime;

    // =================辅助字段=====================
    private int displayOrder;// 排序号

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUnitId() {
        return unitId;
    }

    public void setUnitId(String unitId) {
        this.unitId = unitId;
    }

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

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public Date getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public int getDisplayOrder() {
        return displayOrder;
    }

    public void setDisplayOrder(int displayOrder) {
        this.displayOrder = displayOrder;
    }

}
