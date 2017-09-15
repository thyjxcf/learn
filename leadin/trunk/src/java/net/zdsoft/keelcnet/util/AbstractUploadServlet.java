package net.zdsoft.keelcnet.util;

import java.io.File;
import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.struts2.config.DefaultSettings;
import org.apache.struts2.dispatcher.multipart.JakartaMultiPartRequest;

/*
 * 基于Webwork2的文件上传基础类。通过定义抽象方法，交由子类处理上传的文件。
 * 
 * @author Brave Tao
 * @since 2004-9-17
 * @version $Id: AbstractUploadServlet.java,v 1.1 2006/12/30 07:15:39 jiangl Exp $
 * @since
 */
public abstract class AbstractUploadServlet extends HttpServlet {
    protected Logger log = LoggerFactory.getLogger(getClass());
    private JakartaMultiPartRequest multi;
    protected String saveDir;
    protected Integer maxSize;

    /*
     * (non-Javadoc)
     * 
     * @see javax.servlet.Servlet#init(javax.servlet.ServletConfig)
     */    
    public void init(ServletConfig config) throws ServletException {
        @SuppressWarnings("deprecation")
        DefaultSettings a = new DefaultSettings();
        String _saveDir = a.get("struts.multipart.saveDir").trim();
        saveDir = _saveDir;
        if (saveDir.equals("")) {
            File tempdir = (File) config.getServletContext().getAttribute(
                    "javax.servlet.context.tempdir");
            log
                    .warn("Unable to find 'struts.multipart.saveDir' property setting. Defaulting to javax.servlet.context.tempdir");

            if (tempdir != null) {
                saveDir = tempdir.toString();
            }
        }
        else {
            File multipartSaveDir = new File(saveDir);

            if (!multipartSaveDir.exists()) {
                multipartSaveDir.mkdir();
            }
        }

        @SuppressWarnings("deprecation")
        String maxSizeStr = a.get("struts.multipart.maxSize");

        if (maxSizeStr != null) {
            try {
                maxSize = new Integer(maxSizeStr);
            }
            catch (NumberFormatException e) {
                maxSize = new Integer(Integer.MAX_VALUE);
                log
                        .warn("Unable to format 'struts.multipart.maxSize' property setting. Defaulting to Integer.MAX_VALUE");
            }
        }
        else {
            maxSize = new Integer(Integer.MAX_VALUE);
            log
                    .warn("Unable to format 'struts.multipart.maxSize' property setting. Defaulting to Integer.MAX_VALUE");
        }
    }

    protected boolean isAuthenticated() {
        return false;
    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.servlet.http.HttpServlet#service(javax.servlet.http.HttpServletRequest,
     *      javax.servlet.http.HttpServletResponse)
     */
    protected void service(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest,
     *      javax.servlet.http.HttpServletResponse)
     */
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest,
     *      javax.servlet.http.HttpServletResponse)
     */
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        if (true == isAuthenticated()) {
            //boolean isMulti = MultiPartRequest.isMultiPart(request);
            boolean isMulti = (request.getContentType()).equalsIgnoreCase("multipart/form-data")?true:false;
            String retXml;

            if (true == isMulti) {
                multi = new JakartaMultiPartRequest();//request, saveDir, maxSize.intValue());
                multi.parse(request, saveDir);
                retXml = procUpload(request, response);
            }
            else {
                retXml = "It's not \"multipart/form-data\" data!";
            }

            if (retXml != null) {
                response.setContentLength(retXml.getBytes().length);
                response.getWriter().write(retXml);
            }
        }
        else {
            log.error("没有权限!");
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "没有权限!");
        }
    }

    protected abstract String procUpload(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException;

    /**
     * Returns an enumeration of the parameter names for uploaded files
     * 
     * @return an enumeration of the parameter names for uploaded files
     */
    public Enumeration<String> getFileParameterNames() {
        return multi.getFileParameterNames();
    }

    /**
     * Returns the content type(s) of the file(s) associated with the specified
     * field name (as supplied by the client browser), or <tt>null</tt> if no
     * files are associated with the given field name.
     * 
     * @param fieldName
     *            input field name
     * @return an array of content encoding for the specified input field name
     *         or <tt>null</tt> if no content type was specified.
     */
    public String[] getContentType(String fieldName) {
        return multi.getContentType(fieldName);
    }

    /**
     * Returns a {@link java.io.File} object for the filename specified or
     * <tt>null</tt> if no files
     * 
     * are associated with the given field name.
     * 
     * @param fieldName
     *            input field name
     * @return a File[] object for files associated with the specified input
     *         field name
     */
    public File[] getFile(String fieldName) {
        return multi.getFile(fieldName);
    }

    /**
     * Returns a String[] of file names for files associated with the specified
     * input field name
     * 
     * @param fieldName
     *            input field name
     * @return a String[] of file names for files associated with the specified
     *         input field name
     */
    public String[] getFileNames(String fieldName) {
        return multi.getFileNames(fieldName);
    }

    /**
     * Returns the file system name(s) of files associated with the given field
     * name or <tt>null</tt> if no files are associated with the given field
     * name.
     * 
     * @param fieldName
     *            input field name
     * @return the file system name(s) of files associated with the given field
     *         name
     */
    public String[] getFilesystemName(String fieldName) {
        return multi.getFilesystemName(fieldName);
    }

    /**
     * Returns the specified request parameter.
     * 
     * @param name
     *            the name of the parameter to get
     * @return the parameter or <tt>null</tt> if it was not found.
     */
    public String getParameter(String name) {
        return multi.getParameter(name);
    }

    /**
     * Returns an enumeration of String parameter names.
     * 
     * @return an enumeration of String parameter names.
     */
    public Enumeration<String> getParameterNames() {
        return multi.getParameterNames();
    }

    /**
     * Returns a list of all parameter values associated with a parameter name.
     * If there is only one parameter value per name the resulting array will be
     * of length 1.
     * 
     * @param name
     *            the name of the parameter.
     * @return an array of all values associated with the parameter name.
     */
    public String[] getParameterValues(String name) {
        return multi.getParameterValues(name);
    }
}
