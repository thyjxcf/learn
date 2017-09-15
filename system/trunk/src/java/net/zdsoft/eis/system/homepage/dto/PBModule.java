/* 
 * <p>ZDSoft学籍系统（stusys）V3.5</p>
 * @author zhangza
 * @since 1.0
 * @version $Id$
 */
package net.zdsoft.eis.system.homepage.dto;

import java.io.Serializable;

public class PBModule implements Serializable {
    private static final long serialVersionUID = 5257649440684468672L;
    
    private int moduleId ;
    private String version = null;
    private String fileList = null;
    private String fileUrl = null;
    private String mainFile = null;
    private String winName = null;
    private Integer width = null;
    private Integer height = null;
    private String isResize = null;
    private String pbd = null;
    private String key = null;
    private String actionParam = null;
    private String project = null;
    /**
     * @return the actionParam
     */
    public String getActionParam() {
        return actionParam;
    }
    /**
     * @param actionParam the actionParam to set
     */
    public void setActionParam(String actionParam) {
        this.actionParam = actionParam;
    }
    /**
     * @return the fileList
     */
    public String getFileList() {
        return fileList;
    }
    /**
     * @param fileList the fileList to set
     */
    public void setFileList(String fileList) {
        this.fileList = fileList;
    }
    /**
     * @return the fileUrl
     */
    public String getFileUrl() {
        return fileUrl;
    }
    /**
     * @param fileUrl the fileUrl to set
     */
    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    /**
     * @return the isResize
     */
    public String getIsResize() {
        return isResize;
    }
    /**
     * @param isResize the isResize to set
     */
    public void setIsResize(String isResize) {
        this.isResize = isResize;
    }
    /**
     * @return the key
     */
    public String getKey() {
        return key;
    }
    /**
     * @param key the key to set
     */
    public void setKey(String key) {
        this.key = key;
    }
    /**
     * @return the mainFile
     */
    public String getMainFile() {
        return mainFile;
    }
    /**
     * @param mainFile the mainFile to set
     */
    public void setMainFile(String mainFile) {
        this.mainFile = mainFile;
    }
    /**
     * @return the moduleId
     */
    public int getModuleId() {
        return moduleId;
    }
    /**
     * @param moduleId the moduleId to set
     */
    public void setModuleId(int moduleId) {
        this.moduleId = moduleId;
    }
    /**
     * @return the pbd
     */
    public String getPbd() {
        return pbd;
    }
    /**
     * @param pbd the pbd to set
     */
    public void setPbd(String pbd) {
        this.pbd = pbd;
    }
    /**
     * @return the project
     */
    public String getProject() {
        return project;
    }
    /**
     * @param project the project to set
     */
    public void setProject(String project) {
        this.project = project;
    }
    /**
     * @return the version
     */
    public String getVersion() {
        return version;
    }
    /**
     * @param version the version to set
     */
    public void setVersion(String version) {
        this.version = version;
    }

    /**
     * @return the winName
     */
    public String getWinName() {
        return winName;
    }
    /**
     * @param winName the winName to set
     */
    public void setWinName(String winName) {
        this.winName = winName;
    }
    /**
     * @return the height
     */
    public Integer getHeight() {
        return height;
    }
    /**
     * @param height the height to set
     */
    public void setHeight(Integer height) {
        this.height = height;
    }
    /**
     * @return the width
     */
    public Integer getWidth() {
        return width;
    }
    /**
     * @param width the width to set
     */
    public void setWidth(Integer width) {
        this.width = width;
    }
    
    
}
