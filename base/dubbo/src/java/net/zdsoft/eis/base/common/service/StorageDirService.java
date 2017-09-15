/* 
 * @(#)StorageDirService.java    Created on Dec 6, 2010
 * Copyright (c) 2010 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.eis.base.common.service;

import java.util.Map;

import net.zdsoft.eis.base.common.entity.StorageDir;

/**
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: Dec 6, 2010 1:37:50 PM $
 */
public interface StorageDirService {
    /**
     * 更新内置公共目录
     * @param dir
     */
    public void updatePresetPublicDir(String dir);

    /**
     * 取存储目录路径
     * 
     * @param storageDirId
     * @return
     */
    public String getDir(String storageDirId);
    
    public Map<String, String> getDirMap(String[] dirIds);

    /**
     * 取存储目录信息
     * 
     * @param storageDirId
     * @return
     */
    public StorageDir getStorageDir(String storageDirId);

    /**
     * 取“公共”类型的激活目录，如果为空，则取系统内置公共目录
     * 
     * @param type
     * @return
     */
    public StorageDir getActiveStorageDir();

    /**
     * 取此类型的激活目录，如果找不到则调用 {@link getActiveStorageDir()}
     * 
     * @param type
     * @return
     */
    public StorageDir getActiveStorageDir(StorageDir.StorageDirType type);
    
    /**
     * 取“公共”类型的内置目录
     * 
     * @param type
     * @return
     */
    public StorageDir getPresetPublicStorageDir();

    /**
     * 取存储目录信息Map
     * 
     * @param storageDirIds
     * @return
     */
    public Map<String, StorageDir> getStorageDirs(String[] storageDirIds);
}
