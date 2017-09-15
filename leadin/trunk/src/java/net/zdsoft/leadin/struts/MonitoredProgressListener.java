/* 
 * @(#)MonitoredProgressListener.java    Created on May 6, 2011
 * Copyright (c) 2011 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.leadin.struts;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.ProgressListener;

/**
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: May 6, 2011 3:33:57 PM $
 */
public class MonitoredProgressListener implements ProgressListener {
    private HttpSession session;
    private FileUploadStats fileUploadStats;

    public MonitoredProgressListener(HttpServletRequest request) {
        session = request.getSession();
        fileUploadStats = new FileUploadStats();
        
        request.getParameter("");
        session.setAttribute("currentUploadStatus", fileUploadStats);
    }

    @Override
    public void update(long bytesRead, long contentLength, int items) {
        fileUploadStats.setTotalSize(contentLength);
        fileUploadStats.setBytesRead(bytesRead);
        fileUploadStats.setCurrentItem(items);

        String status = FileUploadStats.STATUS_NONE;
        if (bytesRead == 0) {
            status = FileUploadStats.STATUS_START;
        } else if (bytesRead < contentLength) {
            status = FileUploadStats.STATUS_READING;
        } else if (bytesRead == contentLength) {
            status = FileUploadStats.STATUS_DONE;
        }
        fileUploadStats.setCurrentStatus(status);
    }

    public MonitoredProgressListener(long totalSize) {
        fileUploadStats.setTotalSize(totalSize);
    }

    public FileUploadStats getFileUploadStats() {
        return fileUploadStats;
    }

    public static class FileUploadStats {
        public static final String STATUS_NONE = "none";
        public static final String STATUS_START = "start";
        public static final String STATUS_READING = "reading";
        public static final String STATUS_DONE = "done";

        private long totalSize = 0;
        private long bytesRead = 0;
        private int currentItem = 0;
        private long startTime = System.currentTimeMillis();
        private String currentStatus = STATUS_NONE;

        public long getTotalSize() {
            return totalSize;
        }

        public void setTotalSize(long totalSize) {
            this.totalSize = totalSize;
        }

        public long getBytesRead() {
            return bytesRead;
        }

        public long getElapsedTimeInSeconds() {
            return (System.currentTimeMillis() - startTime) / 1000;
        }

        public String getCurrentStatus() {
            return currentStatus;
        }

        public void setCurrentStatus(String currentStatus) {
            this.currentStatus = currentStatus;
        }

        public void setBytesRead(long bytesRead) {
            this.bytesRead = bytesRead;
        }

        public void incrementBytesRead(int byteCount) {
            this.bytesRead += byteCount;
        }

        public int getCurrentItem() {
            return currentItem;
        }

        public void setCurrentItem(int currentItem) {
            this.currentItem = currentItem;
        }

        public void clear() {
            setBytesRead(0);
            setTotalSize(0);
            setCurrentStatus(STATUS_NONE);
        }

        public boolean isDone() {
            return STATUS_DONE.equals(currentStatus);
        }

        public boolean isNone() {
            return STATUS_NONE.equals(currentStatus);
        }
    }
}
