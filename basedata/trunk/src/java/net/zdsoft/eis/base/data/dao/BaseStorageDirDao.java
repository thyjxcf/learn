/* 
 * @(#)BaseStorageDirDao.java    Created on Dec 6, 2010
 * Copyright (c) 2010 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.eis.base.data.dao;

import java.util.List;

import net.zdsoft.eis.base.common.entity.StorageDir;

/**
 * 
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: Dec 6, 2010 1:31:11 PM $
 */
public interface BaseStorageDirDao {

    /**
     * 新增目录
     * 
     * @param storageDir
     */
    public void insertStorageDir(StorageDir storageDir);

    /**
     * 删除目录
     * 
     * @param storageDirIds
     */
    public void deleteStorageDir(String[] storageDirIds);

    /**
     * 更新目录
     * 
     * @param storageDir
     */
    public void updateStorageDir(StorageDir storageDir);

    /**
     * 某种类型的激活目录是否有多个
     * 
     * @param type
     * @param id
     * @return
     */
    public boolean isRepeatActiveDir(int type, String id);

    /**
     * 取所有目录
     * 
     * @return
     */
    public List<StorageDir> getStorageDirs();

}
