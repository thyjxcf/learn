/* 
 * @(#)MonitoredProgressAction.java    Created on May 9, 2011
 * Copyright (c) 2011 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.leadin.struts;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import net.zdsoft.leadin.dataimport.action.LeadinActionSupport;
import net.zdsoft.leadin.upload.FileUploadListener;

/**
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: May 9, 2011 11:30:27 AM $
 */
public class MonitoredProgressAction extends LeadinActionSupport {

    /**
     * Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 8199676450555793024L;

    public void doStatus() throws IOException {
        HttpSession session = getRequest().getSession();
        HttpServletResponse response = ServletActionContext.getResponse();

        // Make sure the status response is not cached by the browser
        response.addHeader("Expires", "0");
        response.addHeader("Cache-Control", "no-store, no-cache, must-revalidate");
        response.addHeader("Cache-Control", "post-check=0, pre-check=0");
        response.addHeader("Pragma", "no-cache");
        response.setContentType("text/html;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");

        FileUploadListener.FileUploadStats fileUploadStats = (FileUploadListener.FileUploadStats) session
                .getAttribute("FILE_UPLOAD_STATS");
        if (fileUploadStats != null) {
            long bytesProcessed = fileUploadStats.getBytesRead();
            long sizeTotal = fileUploadStats.getTotalSize();
            long percentComplete = (long) Math
                    .floor(((double) bytesProcessed / (double) sizeTotal) * 100.0);
            long timeInSeconds = fileUploadStats.getElapsedTimeInSeconds();
            double uploadRate = bytesProcessed / (timeInSeconds + 0.00001);
            double estimatedRuntime = sizeTotal / (uploadRate + 0.00001);

            response.getWriter().println("<b>文件上传信息：</b><br/>");

            if (fileUploadStats.getBytesRead() != fileUploadStats.getTotalSize()) {
                response.getWriter().println(
                        "<div class=\"prog-border\"><div class=\"prog-bar\" style=\"width: "
                                + percentComplete + "%;\"></div></div>");
                response.getWriter().println(
                        "正在上传：已上传 " + bytesProcessed + " 字节； 文件大小 " + sizeTotal + " 字节 ("
                                + percentComplete + "%)； 上传速率 "
                                + (long) Math.round(uploadRate / 1024) + " KB <br/>");
                response.getWriter().println(
                        "运行时间：已上传 " + formatTime(timeInSeconds) + " ； 总时间 "
                                + formatTime(estimatedRuntime) + " ； 剩余 "
                                + formatTime(estimatedRuntime - timeInSeconds) + " <br/>");
            } else {
                if (!(fileUploadStats.isNone())) {
                    response.getWriter().println(
                            "已上传：" + bytesProcessed + " ； 文件大小 " + sizeTotal + " 字节<br/>");
                }
            }
        }

        if (fileUploadStats != null
                && fileUploadStats.getBytesRead() == fileUploadStats.getTotalSize()) {
            if (fileUploadStats.isDone()) {
                response.getWriter().println("文件上传完成。<br/>");
                // 为下次上传清空数据
                fileUploadStats.clear();

                sendCompleteResponse(response, null);

            }
        }

    }

    private String formatTime(double timeInSeconds) {
        long seconds = (long) Math.floor(timeInSeconds);
        long minutes = (long) Math.floor(timeInSeconds / 60.0);
        long hours = (long) Math.floor(minutes / 60.0);

        if (hours != 0) {
            return hours + "时 " + (minutes % 60) + "分 " + (seconds % 60) + "秒";
        } else if (minutes % 60 != 0) {
            return (minutes % 60) + "分 " + (seconds % 60) + "秒";
        } else {
            return (seconds % 60) + " 秒";
        }
    }

    private void sendCompleteResponse(HttpServletResponse response, String message)
            throws IOException {
        if (message == null) {
            response
                    .getOutputStream()
                    .print(
                            "<html><head><script type='text/javascript'>function killUpdate() { window.parent.killUpdate(''); }</script></head><body onload='killUpdate()'></body></html>");
        } else {
            response.getOutputStream().print(
                    "<html><head><script type='text/javascript'>function killUpdate() { window.parent.killUpdate('"
                            + message
                            + "'); }</script></head><body onload='killUpdate()'></body></html>");
        }
    }
}
