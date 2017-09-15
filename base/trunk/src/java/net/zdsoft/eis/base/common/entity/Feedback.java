package net.zdsoft.eis.base.common.entity;

import net.zdsoft.eis.frame.client.BaseEntity;

public class Feedback extends BaseEntity {
    private String topUnitName;
    private String systemVersion;
    private String accessDate;
    private String url;
    private String moduleName;
    private String uploadTime;
    private int accessCount;

    public String getTopUnitName() {
        return topUnitName;
    }

    public void setTopUnitName(String topUnitName) {
        this.topUnitName = topUnitName;
    }

    public String getSystemVersion() {
        return systemVersion;
    }

    public void setSystemVersion(String systemVersion) {
        this.systemVersion = systemVersion;
    }

    public String getAccessDate() {
        return accessDate;
    }

    public void setAccessDate(String accessDate) {
        this.accessDate = accessDate;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public String getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(String uploadTime) {
        this.uploadTime = uploadTime;
    }

    public int getAccessCount() {
        return accessCount;
    }

    public void setAccessCount(int accessCount) {
        this.accessCount = accessCount;
    }

}
