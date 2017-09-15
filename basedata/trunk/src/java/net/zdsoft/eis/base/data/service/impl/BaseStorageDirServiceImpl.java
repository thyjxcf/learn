/* 
 * @(#)BaseStorageDirServiceImpl.java    Created on Dec 6, 2010
 * Copyright (c) 2010 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.eis.base.data.service.impl;

import java.io.File;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import net.zdsoft.eis.base.common.entity.StorageDir;
import net.zdsoft.eis.base.common.service.impl.StorageDirServiceImpl;
import net.zdsoft.eis.base.data.dao.BaseStorageDirDao;
import net.zdsoft.eis.base.data.service.BaseStorageDirService;
import net.zdsoft.eis.frame.dto.PromptMessageDto;
import net.zdsoft.leadin.exception.FieldErrorException;

/**
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: Dec 6, 2010 1:34:33 PM $
 */
public class BaseStorageDirServiceImpl extends StorageDirServiceImpl implements
        BaseStorageDirService {
    private BaseStorageDirDao baseStorageDirDao;

    public void setBaseStorageDirDao(BaseStorageDirDao baseStorageDirDao) {
        this.baseStorageDirDao = baseStorageDirDao;
    }

    public PromptMessageDto saveStorageDir(StorageDir storageDir) {
    	PromptMessageDto promptMessageDto=new PromptMessageDto();
        if (storageDir.isActive()
                && baseStorageDirDao.isRepeatActiveDir(storageDir.getType(), storageDir.getId())) {
//            throw new FieldErrorException("active", "一种类型只能激活一个目录");
        	promptMessageDto.addFieldError("active", "一种类型只能激活一个目录");
        	promptMessageDto.setOperateSuccess(false);
        	return promptMessageDto;
        }

        String dir = storageDir.getDir();
        File file = new File(dir);
        if (!(file.getAbsolutePath().equals(file.getPath()))) {
//            throw new FieldErrorException("dir", "必须为绝对路径");
        	promptMessageDto.addFieldError("dir", "必须为绝对路径");
        	promptMessageDto.setOperateSuccess(false);
        	return promptMessageDto;
        }
        
        if (StringUtils.isBlank(storageDir.getId())) {
            baseStorageDirDao.insertStorageDir(storageDir);
        } else {
            baseStorageDirDao.updateStorageDir(storageDir);
        }
        
        clearCache();
        promptMessageDto.setOperateSuccess(true);
        return promptMessageDto;
    }

    public void deleteStorageDir(String[] storageDirIds) {
        baseStorageDirDao.deleteStorageDir(storageDirIds);
        clearCache();
    }

    public List<StorageDir> findStorageDirs() {
        return baseStorageDirDao.getStorageDirs();
    }

}
