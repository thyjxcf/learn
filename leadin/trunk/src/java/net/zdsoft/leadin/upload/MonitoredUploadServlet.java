/* 
 * @(#)MonitoredUploadServlet.java    Created on Jul 8, 2006
 * Copyright (c) 2006 ZDSoft Networks, Inc. All rights reserved.
 * $Header: /project/blog/src/net/zdsoft/blog/servlet/MonitoredUploadServlet.java,v 1.7 2006/10/20 09:23:13 yangm Exp $
 */
package net.zdsoft.leadin.upload;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.zdsoft.keel.util.ServletUtils;

import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MonitoredUploadServlet extends HttpServlet {

    /**
     * Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = -5804225907370980638L;
    protected Logger log = LoggerFactory.getLogger(MonitoredUploadServlet.class);

    /**
     * The doGet method of the servlet. <br>
     * 
     * This method is called when a form has its tag value method equals to get.
     * 
     * @param request
     *            the request send by the client to the server
     * @param response
     *            the response send by the server to the client
     * @throws ServletException
     *             if an error occurred
     * @throws IOException
     *             if an error occurred
     */
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        doPost(request, response);
    }

    /**
     * The doPost method of the servlet. <br>
     * 
     * This method is called when a form has its tag value method equals to
     * post.
     * 
     * @param request
     *            the request send by the client to the server
     * @param response
     *            the response send by the server to the client
     * @throws ServletException
     *             if an error occurred
     * @throws IOException
     *             if an error occurred
     */
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ServletUtils.setCharacterEncoding(request);
        UploadListener listener = new UploadListener(request, 30);

        // Create a factory for disk-based file items
        MonitoredDiskFileItemFactory factory = new MonitoredDiskFileItemFactory(
                listener);
        factory.setSizeThreshold(8192);
        // Create a new file upload handler
        ServletFileUpload upload = new ServletFileUpload(factory);

        // int totalFileSize = 1024*1024*5;
        // upload.setSizeMax(totalFileSize);

        try {
            // process uploads ..
            @SuppressWarnings("unchecked")
            List dataList = upload.parseRequest(request);
            request.getSession().setAttribute("uploadFiles", dataList);
        }
        catch (FileUploadException e) {
            log.error("文件上传失败", e);
            
            // e.printStackTrace(); // To change body of catch statement use
            // File | Settings | File Templates.
        }
    }

}
