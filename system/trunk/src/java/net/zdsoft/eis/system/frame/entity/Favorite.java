package net.zdsoft.eis.system.frame.entity;

import net.zdsoft.eis.frame.client.BaseEntity;

// default package

/**
 * Favorite generated by MyEclipse - Hibernate Tools
 */

public class Favorite extends BaseEntity {

    // Fields

    /**
     * 
     */
    private static final long serialVersionUID = -2357022537360135917L;
    private String id;
    private String moduleId;
    private Integer orderNum;
    private String moduleName;
    private String moduleDescription;
    private Integer type;
    private String userId;
    private String picUrl;
    private String url;
    private String parentId;
    private Integer subSystem;  
    private String serverId;

    // ==================辅助字段 无===============
    


    /** default constructor */
    public Favorite() {
    }

    /** minimal constructor */
    public Favorite(String id, String moduleId, Integer orderNum,
            String moduleName, Integer type, String userId, String parentId) {
        this.id = id;
        this.moduleId = moduleId;
        this.orderNum = orderNum;
        this.moduleName = moduleName;
        this.type = type;
        this.userId = userId;
        this.parentId = parentId;        
    }

    /** full constructor */
    public Favorite(String id, String moduleId, Integer orderNum,
            String moduleName, String moduleDescription, Integer type,
            String userId, String picUrl, String url, String parentId,
            Integer subSystem) {
        this.id = id;
        this.moduleId = moduleId;
        this.orderNum = orderNum;
        this.moduleName = moduleName;
        this.moduleDescription = moduleDescription;
        this.type = type;
        this.userId = userId;
        this.picUrl = picUrl;
        this.url = url;
        this.parentId = parentId;
        this.subSystem = subSystem;
    }

    // Property accessors

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getModuleId() {
        return this.moduleId;
    }

    public void setModuleId(String moduleId) {
        this.moduleId = moduleId;
    }

    public Integer getOrderNum() {
        return this.orderNum;
    }

    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }

    public String getModuleName() {
        return this.moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public String getModuleDescription() {
        return this.moduleDescription;
    }

    public void setModuleDescription(String moduleDescription) {
        this.moduleDescription = moduleDescription;
    }

    public Integer getType() {
        return this.type;
    }

    public void setType(Integer type) {
        this.type = type;
    }


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPicUrl() {
        return this.picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getParentId() {
        return this.parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public Integer getSubSystem() {
        return this.subSystem;
    }

    public void setSubSystem(Integer subSystem) {
        this.subSystem = subSystem;
    }

    public String getServerId() {
        return serverId;
    }

    public void setServerId(String serverId) {
        this.serverId = serverId;
    }

}