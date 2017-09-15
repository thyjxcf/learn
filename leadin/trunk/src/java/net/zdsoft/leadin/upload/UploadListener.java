/* 
 * @(#)UploadListener.java    Created on Jul 8, 2006
 * Copyright (c) 2006 ZDSoft Networks, Inc. All rights reserved.
 * $Header: /project/blog/src/net/zdsoft/blog/upload/UploadListener.java,v 1.4 2006/10/20 09:23:12 yangm Exp $
 */
package net.zdsoft.leadin.upload;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author luxingmu
 * @version $Revision: 1.4 $, $Date: 2006/10/20 09:23:12 $
 */
public class UploadListener implements OutputStreamListener {
    protected Logger log = LoggerFactory.getLogger(UploadListener.class);
    
    private HttpServletRequest request;
    private long delay = 0;
    private long startTime = 0;
    private int totalToRead = 0;
    private int totalBytesRead = 0;
    private int totalFiles = -1;

    public UploadListener(HttpServletRequest request, long debugDelay)
    {
        this.request = request;
        this.delay = debugDelay;
        totalToRead = request.getContentLength();
        this.startTime = System.currentTimeMillis();
    }

    public void start()
    {
        totalFiles ++;
        updateUploadInfo("start");
    }

    public void bytesRead(int bytesRead)
    {
        totalBytesRead = totalBytesRead + bytesRead;
        updateUploadInfo("progress");

        try
        {
            Thread.sleep(delay);
        }
        catch (InterruptedException e)
        {
//            e.printStackTrace();
            log.error("读取文件失败",e);
        }
    }

    public void error(String message)
    {
        updateUploadInfo("error");
    }

    public void done()
    {
        updateUploadInfo("done");
    }

    private void updateUploadInfo(String status)
    {
        long delta = (System.currentTimeMillis() - startTime) / 1000;
        request.getSession().setAttribute("uploadInfo", new UploadInfo(totalFiles, totalToRead, totalBytesRead,delta,status));
    }
}
