/* 
 * @(#)MonitoredDiskFileItem.java    Created on Jul 8, 2006
 * Copyright (c) 2006 ZDSoft Networks, Inc. All rights reserved.
 * $Header: /project/blog/src/net/zdsoft/blog/upload/MonitoredDiskFileItem.java,v 1.3 2006/10/20 09:23:12 yangm Exp $
 */
package net.zdsoft.leadin.upload;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;


import org.apache.commons.fileupload.disk.DiskFileItem;

/**
 * @author luxingmu
 * @version $Revision: 1.3 $, $Date: 2006/10/20 09:23:12 $
 */
public class MonitoredDiskFileItem extends DiskFileItem {
    /**
     * Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = -819046972305632323L;
    private MonitoredOutputStream mos = null;
    private OutputStreamListener listener;

    public MonitoredDiskFileItem(String fieldName, String contentType, boolean isFormField, String fileName, int sizeThreshold, File repository, OutputStreamListener listener)
    {
        super(fieldName, contentType, isFormField, fileName, sizeThreshold, repository);
        this.listener = listener;
    }

    public OutputStream getOutputStream() throws IOException
    {
        if (mos == null)
        {
            mos = new MonitoredOutputStream(super.getOutputStream(), listener);
        }
        return mos;
    }
}
