/* 
 * @(#)BaseStorageDirService.java    Created on Dec 6, 2010
 * Copyright (c) 2010 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.eis.base.data.service;

import java.util.List;

import net.zdsoft.eis.base.common.entity.StorageDir;
import net.zdsoft.eis.base.common.service.StorageDirService;
import net.zdsoft.eis.frame.dto.PromptMessageDto;

/**
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: Dec 6, 2010 1:34:16 PM $
 */
public interface BaseStorageDirService extends StorageDirService {
    /**
     * 保存目录
     * 
     * @param storageDir
     * @return TODO
     */
    public PromptMessageDto saveStorageDir(StorageDir storageDir);

    /**
     * 删除目录
     * 
     * @param storageDirIds
     */
    public void deleteStorageDir(String[] storageDirIds);

    /**
     * 取所有目录
     * 
     * @return
     */
    public List<StorageDir> findStorageDirs();
}
