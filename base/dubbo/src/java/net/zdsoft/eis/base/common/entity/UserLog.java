package net.zdsoft.eis.base.common.entity;

import net.zdsoft.keelcnet.entity.EntityObject;

import java.util.Date;

public class UserLog extends EntityObject {
    private static final long serialVersionUID = -4116008884785383180L;

    private Integer subSystem;
    private String userId;
    private String userName; // new
    private String description;
    private Date logTime;
    private Integer logType;
    private Integer flag;
    private String modId;
    private String unitid;

    // ==================辅助字段=================
    private String modName;
    private String[] arrayIds;

    public UserLog() {
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    public Integer getLogType() {
        return logType;
    }

    public void setLogType(Integer logType) {
        this.logType = logType;
    }

    public Integer getSubSystem() {
        return subSystem;
    }

    public void setSubSystem(Integer subSystem) {
        this.subSystem = subSystem;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Date getLogTime() {
        return logTime;
    }

    public void setLogTime(Date logTime) {
        this.logTime = logTime;
    }

    public String getModId() {
        return modId;
    }

    public void setModId(String modId) {
        this.modId = modId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUnitid() {
        return unitid;
    }

    public void setUnitid(String unitid) {
        this.unitid = unitid;
    }

    public String getModName() {
        return modName;
    }

    public void setModName(String modName) {
        this.modName = modName;
    }

    public String[] getArrayIds() {
        return arrayIds;
    }

    public void setArrayIds(String[] arrayIds) {
        this.arrayIds = arrayIds;
    }

}
