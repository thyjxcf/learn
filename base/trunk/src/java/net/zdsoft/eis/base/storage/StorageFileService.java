/* 
 * @(#)PhotoService.java    Created on Jan 26, 2011
 * Copyright (c) 2011 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.eis.base.storage;

import net.zdsoft.keelcnet.action.UploadFile;
import net.zdsoft.leadin.exception.FileUploadFailException;

/**
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: Jan 26, 2011 10:31:48 AM $
 */
public interface StorageFileService {
    /**
     * 
     * @param storageFileEntity
     * @param uploadedfile
     * @throws PhotoException
     */
    public void saveFile(StorageFileEntity storageFileEntity, UploadFile uploadedfile)
            throws FileUploadFailException;

    /**
     * 
     * @param storageFileEntity
     * @param uploadedfile
     * @param isDeleteUploadedFile 是否删除上传文件
     * @throws PhotoException
     */
    public void saveFile(StorageFileEntity storageFileEntity, UploadFile uploadedfile,
            boolean isDeleteUploadedFile) throws FileUploadFailException;
    
    
    /**
     * 设置路径
     * 
     * @param storageFileEntity
     */
    public void setDirPath(StorageFileEntity storageFileEntity);
    
}
