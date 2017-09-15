/* 
 * @(#)StorageDirServiceImpl.java    Created on Dec 6, 2010
 * Copyright (c) 2010 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.eis.base.common.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.zdsoft.eis.base.cache.BaseCacheConstants;
import net.zdsoft.eis.base.common.dao.StorageDirDao;
import net.zdsoft.eis.base.common.entity.StorageDir;
import net.zdsoft.eis.base.common.entity.StorageDir.StorageDirType;
import net.zdsoft.eis.base.common.service.StorageDirService;
import net.zdsoft.eis.frame.cache.DefaultCacheManager;

/**
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: Dec 6, 2010 1:38:00 PM $
 */
public class StorageDirServiceImpl extends DefaultCacheManager implements StorageDirService {
    private static final Logger log = LoggerFactory.getLogger(StorageDirServiceImpl.class);

    private StorageDirDao storageDirDao;

    public void setStorageDirDao(StorageDirDao storageDirDao) {
        this.storageDirDao = storageDirDao;
    }

    public void updatePresetPublicDir(String dir) {
        StorageDir storageDir = storageDirDao.getPresetStorageDir(StorageDirType.PUBLIC.getValue());
        storageDir.setDir(dir);

        // 如果没有找到激活的公共目录，则激活系统内置的公共目录
        StorageDir sd = getActiveStorageDirByType(StorageDirType.PUBLIC);
        if (null == sd || (sd.isPublicDir() && sd.isPreset())) {
            storageDir.setActive(true);
        }

        storageDirDao.updatePresetDir(storageDir);
        clearCache();
    }

    // -------------------缓存信息 begin------------------------

    public StorageDir getStorageDir(final String storageDirId) {
        return getEntityFromCache(new CacheEntityParam<StorageDir>() {

            public StorageDir fetchObject() {
                return storageDirDao.getStorageDir(storageDirId);
            }

            public String fetchKey() {
                return fetchCacheEntityKey() + storageDirId;
            }
        });
    }

    public StorageDir getActiveStorageDir() {
        StorageDir dir = getActiveStorageDirByType(StorageDirType.PUBLIC);
        if (null == dir) {
            dir = storageDirDao.getPresetStorageDir(StorageDirType.PUBLIC.getValue());
            updatePresetPublicDir(dir.getDir());
        }
        return dir;
    }

    public StorageDir getActiveStorageDir(final StorageDirType type) {
        StorageDir dir = getActiveStorageDirByType(type);
        return dir == null ? getActiveStorageDir() : dir;
    }

    protected StorageDir getActiveStorageDirByType(final StorageDirType type) {
        return getEntityFromCache(new CacheEntityParam<StorageDir>() {

            public StorageDir fetchObject() {
                return storageDirDao.getActiveStorageDir(type.getValue());
            }

            public String fetchKey() {
                return BaseCacheConstants.EIS_STORAGEDIR_ACTIVE_TYPE + type.getValue();
            }
        });
    }

    // -------------------缓存信息 end--------------------------

    public String getDir(String dirId) {
        String path = "";
        if (!(StringUtils.isEmpty(dirId))) {
            StorageDir storageDir = getStorageDir(dirId);
            if (null != storageDir) {
                path = storageDir.getDir();
            } else {
                log.error("根据dirId[" + dirId + "]找不到目录信息");
            }
        }
        return path;
    }
    
    public Map<String, String> getDirMap(String[] dirIds){
    	Map<String, StorageDir> dirMap = getStorageDirs(dirIds);
    	Map<String, String> dirPathMap = new HashMap<String, String>();
    	for(String id : dirIds){
    		if(dirMap.containsKey(id)){
    			dirPathMap.put(id, dirMap.get(id).getDir());
    		}
    	}
    	return dirPathMap;
    }

    public StorageDir getPresetPublicStorageDir() {
        return storageDirDao.getPresetStorageDir(StorageDirType.PUBLIC.getValue());
    }
    
    public Map<String, StorageDir> getStorageDirs(String[] storageDirIds) {
        return storageDirDao.getStorageDirs(storageDirIds);
    }

}
