/* 
 * @(#)StorageDirDao.java    Created on Dec 6, 2010
 * Copyright (c) 2010 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.eis.base.common.dao;

import java.util.Map;

import net.zdsoft.eis.base.common.entity.StorageDir;

public interface StorageDirDao {
    /**
     * 更新内置目录
     * 
     * @param storageDir
     */
    public void updatePresetDir(StorageDir storageDir);

    /**
     * 取存储目录信息
     * 
     * @param storageDirId
     * @return
     */
    public StorageDir getStorageDir(String storageDirId);

    /**
     * 取此类型的默认目录
     * 
     * @param type
     * @return
     */
    public StorageDir getActiveStorageDir(int type);

    /**
     * 取此类型的预置目录
     * 
     * @param type
     * @return
     */
    public StorageDir getPresetStorageDir(int type);

    /**
     * 取存储目录信息Map
     * 
     * @param storageDirIds
     * @return
     */
    public Map<String, StorageDir> getStorageDirs(String[] storageDirIds);

}
