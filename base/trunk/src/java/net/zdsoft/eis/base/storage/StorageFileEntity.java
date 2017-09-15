/* 
 * @(#)PhotoEntity.java    Created on Jan 25, 2011
 * Copyright (c) 2011 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.eis.base.storage;

import java.io.File;
import java.io.IOException;

import net.zdsoft.eis.base.common.entity.StorageDir.StorageDirType;
import net.zdsoft.eis.base.photo.PhotoEntity;
import net.zdsoft.eis.frame.client.BaseEntity;
import net.zdsoft.keelcnet.config.BootstrapManager;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.json.annotations.JSON;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: Jan 25, 2011 4:17:01 PM $
 */
public abstract class StorageFileEntity extends BaseEntity {

    /**
     * Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 1599662532710857969L;
    @JsonIgnore
    private String dirId;// 存储目录id
    @JsonIgnore
    private String filePath;// 文件相对路径
    @JsonIgnore
    private String customFilePath;// 自定义的文件相对路径，包括文件名
    
    // ----------------------辅助--------------------
    //baseSysOptionService.getValue(SysOption.FILE_URL)+"/"+BootstrapManager.getStoreFolder()
    @JsonIgnore
    private String fileUrl; //存放store目录对应的url地址，部署的时候需要进行配置FILE.URL
    @JsonIgnore
    private String dirPath;
    @JsonIgnore
    private String objectUnitId;// 单位id
    @JsonIgnore
    private String downloadPath;//下载路径

    /**
     * 存储目录类型
     * 
     * @return
     */
    @JsonIgnore
    public abstract StorageDirType getStorageDirType();

    /**
     * 对象类型
     * 
     * @return
     */
    @JsonIgnore
    public abstract String getObjectType();
    
    @JsonIgnore
    public String getFileUrl() {
    	return fileUrl;
    }
    
    public void setFileUrl(String fileUrl) {
    	this.fileUrl = fileUrl;
    }

    public String getDirId() {
        return dirId;
    }

    public void setDirId(String dirId) {
        this.dirId = dirId;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getObjectUnitId() {
        return objectUnitId;
    }

    public void setObjectUnitId(String objectUnitId) {
        this.objectUnitId = objectUnitId;
    }

    public String getDirPath() {
        return dirPath;
    }

    public void setDirPath(String dirPath) {
        this.dirPath = dirPath;
    }

    public String getCustomFilePath() {
        return customFilePath;
    }

    public void setCustomFilePath(String customFilePath) {
        this.customFilePath = customFilePath;
    }

    @JsonIgnore
    @JSON(serialize=false)
    public File getFile() throws IOException {
        File f = StorageFileUtils.getFileLocalPath(this);
        
        // make sure we can write to destination
        if (f.exists()) {
            if (!f.canRead()) {
                String message = "无法读取文件 [" + f + "]!";
                throw new IOException(message);
            }

            return f;
        }
        throw new IOException("文件没有找到 [" + f + "]!");
    }

    /**
     * 取得文件的绝对物理路径
     * @return 如果找不到，则返回“”
     */
    @JsonIgnore
    public String getPhysicsFilePath(){
        File file = null;
        try {
            file = getFile();
        } catch (IOException e) {
            // e.printStackTrace();
        }
        boolean hasFile = file != null && file.exists();

        if (!hasFile) {
            if (this instanceof PhotoEntity) {
                PhotoEntity instance = ((PhotoEntity) this);
                if (instance.isShowDefault()) {
                    return instance.getDefaultFilePath();
                }
            }
            return "";
        }

        String filePath = file.getPath();
        return filePath;
    }
    
    /**
     * 得到照片的下载路径, 如果没有上传图片，则返回""
     * 
     * @param showDefault 如果没有图片，是否显示默认“无照片”图片
     * @return
     * @throws IOException
     */
    @JsonIgnore
    public String getDownloadPath() {

        String basePath = BootstrapManager.getBaseUrl();

        String filePath = getPhysicsFilePath();
        if (this instanceof PhotoEntity) {
            PhotoEntity instance = ((PhotoEntity) this);
            if (instance.isShowDefault() && filePath.equals(instance.getDefaultFilePath()) ) {
                return filePath;
            }
            if(StringUtils.isBlank(filePath)){
            	return "";
            }
        }
        
        downloadPath = basePath + "/common/downloadFile.action?filePath=" + filePath;
        return downloadPath;
    }
    
    /**
    * 得到照片的Http地址
    * @return
    */
    @JsonIgnore
    public String getDownloadUrl(){
    	return getFileUrl() + "/" + getFilePath();
    }
}
