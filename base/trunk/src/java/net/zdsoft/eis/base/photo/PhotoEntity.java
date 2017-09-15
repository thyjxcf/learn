/* 
 * @(#)PhotoEntity.java    Created on Jan 26, 2011
 * Copyright (c) 2011 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.eis.base.photo;

import com.fasterxml.jackson.annotation.JsonIgnore;

import net.zdsoft.eis.base.common.entity.StorageDir.StorageDirType;
import net.zdsoft.eis.base.storage.StorageFileEntity;

/**
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: Jan 26, 2011 5:51:47 PM $
 */
public abstract class PhotoEntity extends StorageFileEntity {

    /**
     * Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 1599662532710857969L;

    private static final String NOPICTURE_URI = "/static/images/nopicture.png";

    // 界面显示的图片像素
    private final static int SMALL_IMAGE_WIDTH = 152; // 小图片的默认宽度
    private final static int SMALL_IMAGE_HEIGHT = 114; // 小图片的默认高度

    // 实际上传后保存的像素
    private final static int IMAGE_WIDTH = SMALL_IMAGE_WIDTH * 6; // 服务器上保存的图片的宽度
    private final static int IMAGE_HEIGHT = SMALL_IMAGE_HEIGHT * 6; // 服务器上保存的图片的高度

    private static String nopicture = null;

    public StorageDirType getStorageDirType() {
        return StorageDirType.PICTURE;
    }

    /**
     * 返回NOPicture的URI
     * 
     * @param context
     * @return String
     */
    @JsonIgnore
    public String getDefaultFilePath() {
        if (nopicture == null) {
//            nopicture = BootstrapManager.getBaseUrl() + NOPICTURE_URI;
        }
        return nopicture;
    }

    /**
     * 图片文件宽度，0表示不限制
     */
    @JsonIgnore
    public int getPhotoWidth() {
        return IMAGE_WIDTH;
    }

    /**
     * 图片文件高度，0表示不限制
     */
    @JsonIgnore
    public int getPhotoHeight() {
        return IMAGE_HEIGHT;
    }

    /**
     * 是否显示默认的照片
     */
    @JsonIgnore
    public boolean isShowDefault() {
        return false;
    }
}
