/* 
 * @(#)MonitoredDiskFileItemFactory.java    Created on Jul 8, 2006
 * Copyright (c) 2006 ZDSoft Networks, Inc. All rights reserved.
 * $Header: /project/blog/src/net/zdsoft/blog/upload/MonitoredDiskFileItemFactory.java,v 1.3 2006/10/20 09:23:12 yangm Exp $
 */
package net.zdsoft.leadin.upload;

import java.io.File;


import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;

/**
 * @author luxingmu
 * @version $Revision: 1.3 $, $Date: 2006/10/20 09:23:12 $
 */
public class MonitoredDiskFileItemFactory extends DiskFileItemFactory {
    private OutputStreamListener listener = null;

    public MonitoredDiskFileItemFactory(OutputStreamListener listener)
    {
        super();
        this.listener = listener;
    }

    public MonitoredDiskFileItemFactory(int sizeThreshold, File repository, OutputStreamListener listener)
    {
        super(sizeThreshold, repository);
        this.listener = listener;
    }

    public FileItem createItem(String fieldName, String contentType, boolean isFormField, String fileName)
    {
        return new MonitoredDiskFileItem(fieldName, contentType, isFormField, fileName, getSizeThreshold(), getRepository(), listener);
    }
}
