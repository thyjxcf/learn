/* 
 * @(#)StorageDir.java    Created on Dec 6, 2010
 * Copyright (c) 2010 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.eis.base.common.entity;

import java.util.ArrayList;
import java.util.List;

import net.zdsoft.eis.base.util.SUtils;
import net.zdsoft.eis.frame.client.BaseEntity;

import com.alibaba.fastjson.TypeReference;

/**
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: Dec 6, 2010 1:14:17 PM $
 */
public class StorageDir extends BaseEntity {

    /**
     * Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 4249101452933703328L;

    private int type;
    private boolean active;// 是否为激活
    private String dir;
    private boolean preset;// 是否为系统内置目录，不允许删除

    private StorageDirType dirType;
    
    
    public static StorageDir dc(String data) {
		return SUtils.dc(data, StorageDir.class);
	}
    public static List<StorageDir> dt(String data) {
		List<StorageDir> ts = SUtils.dt(data, new TypeReference<List<StorageDir>>() {
		});
		if (ts == null)
			ts = new ArrayList<StorageDir>();
		return ts;

	}
    public boolean isPublicDir() {
        return type == StorageDirType.PUBLIC.getValue();
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isPreset() {
        return preset;
    }

    public void setPreset(boolean preset) {
        this.preset = preset;
    }

    public String getDir() {
        return dir;
    }

    public void setDir(String dir) {
        this.dir = dir;
    }

    public StorageDirType getDirType() {
        return dirType;
    }

    public void setDirType(StorageDirType dirType) {
        this.dirType = dirType;
    }

    public static enum StorageDirType {
        PUBLIC(0), ATTACHMENT(1), PICTURE(2);

        private int value;

        StorageDirType(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

        public String getStringValue() {
            return String.valueOf(value);
        }

        public static StorageDirType valueOf(int value) {
            StorageDirType type = null;
            switch (value) {
            case 0:
                type = PUBLIC;
                break;
            case 1:
                type = ATTACHMENT;
                break;
            case 2:
                type = PICTURE;
                break;
            default:
                type = PUBLIC;
                break;
            }
            return type;
        }

        public String getDescription() {
            String desc = null;
            switch (this) {
            case PUBLIC:
                desc = "公共";
                break;
            case ATTACHMENT:
                desc = "附件";
                break;
            case PICTURE:
                desc = "图片";
                break;
            default:
                desc = "公共";
                break;
            }
            return desc;
        }

        /**
         * 获得子目录
         * 
         * @return
         */
        public String getSubdirectory() {
            String desc = null;
            switch (this) {
            case PUBLIC:
                desc = "public";
                break;
            case ATTACHMENT:
                desc = "attachment";
                break;
            case PICTURE:
                desc = "photo";
                break;
            default:
                desc = "public";
                break;
            }
            return desc;
        }

        @Override
        public String toString() {
            return getDescription();
        }
    }
    
}
