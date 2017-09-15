/* 
 * @(#)PhotoServiceImpl.java    Created on Jan 26, 2011
 * Copyright (c) 2011 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.eis.base.storage;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.zdsoft.eis.base.common.entity.StorageDir;
import net.zdsoft.eis.base.common.service.StorageDirService;
import net.zdsoft.keel.util.DateUtils;
import net.zdsoft.keelcnet.action.UploadFile;
import net.zdsoft.leadin.exception.FileUploadFailException;
import net.zdsoft.leadin.util.Assert;
import net.zdsoft.leadin.util.UUIDGenerator;

/**
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: Jan 26, 2011 10:45:33 AM $
 */
public class StorageFileServiceImpl implements StorageFileService {
    private static final Logger log = LoggerFactory.getLogger(StorageFileServiceImpl.class);

    private StorageDirService storageDirService;

    public void setStorageDirService(StorageDirService storageDirService) {
        this.storageDirService = storageDirService;
    }

    public void saveFile(StorageFileEntity storageFileEntity, UploadFile uploadedfile)
            throws FileUploadFailException {
        saveFile(storageFileEntity, uploadedfile, true);
    }
    
    public void saveFile(StorageFileEntity storageFileEntity, UploadFile uploadedfile,
            boolean isDeleteUploadedFile) throws FileUploadFailException {
        Assert.notNull(storageFileEntity.getStorageDirType());
        Assert.notEmpty(storageFileEntity.getObjectType());
        Assert.notEmpty(storageFileEntity.getObjectUnitId());

        // 如果是更新，则删除原文件(如果在原位置进行更新，一是空间可能不够、二是文件名后缀还是要更新)
        if (StringUtils.isNotEmpty(storageFileEntity.getDirId())
                && StringUtils.isNotBlank(storageFileEntity.getFilePath())) {
            try {
                File file = storageFileEntity.getFile();
                if (file != null && file.exists()) {
                    file.delete();
                }
            } catch (IOException e) {
                log.error(e.getMessage(), e);
            }
        }

        // -----增加文件-------
        // 文件名
        String id = storageFileEntity.getId();
        if (StringUtils.isBlank(id)) {
            id = UUIDGenerator.getUUID();
            storageFileEntity.setId(id);
        }

        // 文件后缀
        String fileType = net.zdsoft.keel.util.FileUtils.getExtension(uploadedfile.getFileName());
        String randomName = id + "." + fileType;

        // 默认相对路径：类型/单位/年份/随机文件名（包括扩展名）
        String filePath = storageFileEntity.getCustomFilePath();
        if (StringUtils.isBlank(filePath)) {
            Date creationTime = new Date();
            String year = DateUtils.date2String(creationTime, "yyyy");

            filePath = storageFileEntity.getStorageDirType().getSubdirectory() + File.separator
                    + storageFileEntity.getObjectType() + File.separator
                    + storageFileEntity.getObjectUnitId() + File.separator + year;
        }
        storageFileEntity.setFilePath(filePath + File.separator + randomName);

        StorageDir storageDir = storageDirService.getActiveStorageDir(storageFileEntity
                .getStorageDirType());
        storageFileEntity.setDirId(storageDir.getId());
        storageFileEntity.setDirPath(storageDir.getDir());

        StorageFileUtils.storeFile(storageFileEntity, filePath, randomName, uploadedfile,isDeleteUploadedFile);

        if (log.isDebugEnabled()) {
            log.debug("保存文件: " + uploadedfile.getFileName());
        }

    }

    public void setDirPath(StorageFileEntity storageFileEntity) {
        storageFileEntity.setDirPath(storageDirService.getDir(storageFileEntity.getDirId()));
    }

}
