/* 
 * @(#)UploadInfo.java    Created on Jul 8, 2006
 * Copyright (c) 2006 ZDSoft Networks, Inc. All rights reserved.
 * $Header: /project/blog/src/net/zdsoft/blog/upload/UploadInfo.java,v 1.3 2006/10/20 09:23:12 yangm Exp $
 */
package net.zdsoft.leadin.upload;

import java.io.Serializable;

/**
 * @author luxingmu
 * @version $Revision: 1.3 $, $Date: 2006/10/20 09:23:12 $
 */
public class UploadInfo implements Serializable {
    
	private static final long serialVersionUID = -4529961294682215607L;
	
	private long totalSize = 0;
    private long bytesRead = 0;
    private long elapsedTime = 0;
    private String status = "done";
    private int fileIndex = 0;

    public UploadInfo()
    {
    }

    public UploadInfo(int fileIndex, long totalSize, long bytesRead, long elapsedTime, String status)
    {
        this.fileIndex = fileIndex;
        this.totalSize = totalSize;
        this.bytesRead = bytesRead;
        this.elapsedTime = elapsedTime;
        this.status = status;
    }

    public String getStatus()
    {
        return status;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

    public long getTotalSize()
    {
        return totalSize;
    }

    public void setTotalSize(long totalSize)
    {
        this.totalSize = totalSize;
    }

    public long getBytesRead()
    {
        return bytesRead;
    }

    public void setBytesRead(long bytesRead)
    {
        this.bytesRead = bytesRead;
    }

    public long getElapsedTime()
    {
        return elapsedTime;
    }

    public void setElapsedTime(long elapsedTime)
    {
        this.elapsedTime = elapsedTime;
    }

    public boolean isInProgress()
    {
        return "progress".equals(status) || "start".equals(status);
    }

    public int getFileIndex()
    {
        return fileIndex;
    }

    public void setFileIndex(int fileIndex)
    {
        this.fileIndex = fileIndex;
    }
}
