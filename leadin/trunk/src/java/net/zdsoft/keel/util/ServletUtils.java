/* 
 * @(#)ServletUtils.java    Created on 2004-10-13
 * Copyright (c) 2004 ZDSoft Networks, Inc. All rights reserved.
 * $Id: ServletUtils.java,v 1.34 2008/07/31 11:34:01 huangwj Exp $
 */
package net.zdsoft.keel.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Servlet工具类.
 * 
 * @author liangxiao
 * @version $Revision: 1.34 $, $Date: 2008/07/31 11:34:01 $
 */
public final class ServletUtils {

    private static final String MULTIPART = "multipart/form-data";
    private static final String POST = "POST";
    private static final String P3P_HEADER = "CP=\"NOI CURa ADMa DEVa TAIa OUR BUS IND UNI COM NAV INT\"";

    private static Logger logger = LoggerFactory.getLogger(ServletUtils.class);

    private static String charSet = "UTF-8";

    private ServletUtils() {
    }

    /**
     * 设置字符集
     * 
     * @param charSet
     *            字符集
     */
    public static void setCharSet(String charSet) {
        ServletUtils.charSet = charSet;
    }

    /**
     * 清除http缓存
     * 
     * @param response
     *            http响应
     */
    public static void clearCache(HttpServletResponse response) {
        response.setHeader("Cache-Control", "no-store");
        response.setHeader("Pragma", "no-cache");
        response.setDateHeader("Expires", 0);
    }

    /**
     * 是否是POST请求
     * 
     * @param request
     *            http请求
     * @return 是true，否则false
     */
    public static boolean isPost(HttpServletRequest request) {
        return POST.equals(request.getMethod());
    }

    /**
     * 设置http请求的字符集为GBK
     * 
     * @param request
     *            http请求
     * @throws ServletException
     * @throws IOException
     */
    public static void setGBKEncoding(HttpServletRequest request)
            throws ServletException, IOException {
        if (request.getCharacterEncoding() == null) {
            request.setCharacterEncoding("GBK");
            logger.debug("request.setCharacterEncoding[GBK]");
        }
    }

    /**
     * 设置http请求的字符集
     * 
     * @param request
     *            http请求
     * @throws ServletException
     * @throws IOException
     */
    public static void setCharacterEncoding(HttpServletRequest request)
            throws ServletException, IOException {
        if (request.getCharacterEncoding() == null) {
            request.setCharacterEncoding(charSet);
            logger.debug("request.setCharacterEncoding[" + charSet + "]");
        }
    }

    /**
     * 是否是文件上传的http请求
     * 
     * @param request
     *            http请求
     * @return 是true，否则false
     */
    public static boolean isMultipart(HttpServletRequest request) {
        String contentType = request.getContentType();
        return contentType != null && contentType.startsWith(MULTIPART);
    }

    /**
     * 输出字符串内容到http响应中
     * 
     * @param response
     *            http响应
     * @param value
     *            字符串内容
     * @throws IOException
     */
    public static void print(HttpServletResponse response, String value)
            throws IOException {
        response.setContentType("text/html; charset=" + charSet);
        // response.setContentLength(value.getBytes().length);
        PrintWriter out = null;
        try {
            out = response.getWriter();
            out.print(value);
            out.flush();
        }
        finally {
            out.close();
        }
    }

    /**
     * 取得http请求中的参数，如果是文件上传可以包含FileItem
     * 
     * @param request
     *            http请求
     * @return 参数Map
     */
    @SuppressWarnings("unchecked")
    public static Map getParameters(HttpServletRequest request) {
        if (!isMultipart(request)) {
            return request.getParameterMap();
        }

        ServletFileUpload fileUpload = new ServletFileUpload(
                new DiskFileItemFactory());
        fileUpload.setHeaderEncoding(charSet);

        List fileItems = null;

        try {
            fileItems = fileUpload.parseRequest(request);
        }
        catch (FileUploadException e) {
            throw new RuntimeException("Could not upload", e);
        }

        HashMap valueListMap = new HashMap();
        for (int i = 0; i < fileItems.size(); i++) {
            FileItem fileItem = (FileItem) fileItems.get(i);

            List valueList = (List) valueListMap.get(fileItem.getFieldName());
            if (valueList == null) {
                valueList = new ArrayList();
                valueListMap.put(fileItem.getFieldName(), valueList);
            }

            if (fileItem.isFormField()) {
                try {
                    valueList.add(fileItem.getString(charSet));
                }
                catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
            else {
                valueList.add(fileItem);

            }
            logger.debug("Read parameter[" + fileItem.getFieldName() + "]");
        }

        HashMap parameters = new HashMap();
        Iterator iterator = valueListMap.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry entry = (Map.Entry) iterator.next();
            String paramName = (String) entry.getKey();
            List valueList = (List) entry.getValue();

            if (valueList.get(0) instanceof String) {
                parameters.put(paramName, ArrayUtils.toArray(valueList));
            }
            else {
                parameters.put(paramName, valueList.toArray(new FileItem[0]));
            }
        }

        // 追加request内的普通参数
        Enumeration enumeration = request.getParameterNames();
        while (enumeration.hasMoreElements()) {
            String paramName = (String) enumeration.nextElement();
            String[] paramValues = request.getParameterValues(paramName);
            parameters.put(paramName, paramValues);
        }

        return parameters;
    }

    /**
     * 下载文件
     * 
     * @param file
     *            文件
     * @param request
     *            http请求
     * @param response
     *            http响应
     * @throws ServletException
     * @throws IOException
     */
    public static void download(File file, HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        download(file, request, response, null, null);
    }

    /**
     * 下载文件
     * 
     * @param file
     *            文件
     * @param request
     *            http请求
     * @param response
     *            http响应
     * @param fileName
     *            指定用户浏览器下载的文件名
     * @throws ServletException
     * @throws IOException
     */
    public static void download(File file, HttpServletRequest request,
            HttpServletResponse response, String fileName)
            throws ServletException, IOException {
        download(file, request, response, null, fileName);
    }

    /**
     * 下载文件
     * 
     * @param file
     *            文件
     * @param request
     *            http请求
     * @param response
     *            http响应
     * @param mimeTypes
     *            mime类型的映射表
     * @throws ServletException
     * @throws IOException
     */    
    @SuppressWarnings("unchecked")
    public static void download(File file, HttpServletRequest request,
            HttpServletResponse response, Map mimeTypes)
            throws ServletException, IOException {
        download(file, request, response, mimeTypes, null);
    }

    /**
     * 下载文件
     * 
     * @param file
     *            文件
     * @param request
     *            http请求
     * @param response
     *            http响应
     * @param mimeTypes
     *            mime类型的映射表
     * @param fileName
     *            指定用户浏览器下载的文件名
     * @throws ServletException
     * @throws IOException
     */
    @SuppressWarnings("unchecked")
    public static void download(File file, HttpServletRequest request,
            HttpServletResponse response, Map mimeTypes, String fileName)
            throws ServletException, IOException {
        String mimeType = null;

        if (mimeTypes != null) {
            String extension = FileUtils.getExtension(file.getName());

            if (extension != null) {
                mimeType = (String) mimeTypes.get(extension);
            }
        }

        if (mimeType == null) {
            mimeType = "application/data";
        }

        response.setContentType(mimeType + "; charset=" + charSet);

        if (fileName != null) {
            fileName = URLUtils.encode(fileName, charSet);
            fileName = fileName.replaceAll("\\+", "%20");
            response.setHeader("Content-Disposition", "attachment; filename="
                    + fileName);
        }

        String range = request.getHeader("Range");

        if (range == null) {
            doDownload(file, request, response);

            if (logger.isDebugEnabled()) {
                logger.debug("doDownload: " + file.getPath());
            }
            return;
        }

        long startPos = getStartPosition(range);
        if (startPos == NONE_FLAG) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        long endPos = getEndPosition(range, file.length());

        doRangeDownload(file, startPos, endPos, request, response);

        if (logger.isDebugEnabled()) {
            logger.debug("doRangeDownload: " + file.getPath() + ", " + startPos
                    + "," + endPos);
        }
    }

    private static int NONE_FLAG = -1;
    private static int BUFFER_SIZE = 1024 * 4;

    private static long getStartPosition(String range) {
        return string2Long(range.substring(range.indexOf("=") + 1, range
                .lastIndexOf("-")));
    }

    private static long getEndPosition(String range, long fileSize) {
        long endPos = string2Long(range.substring(range.indexOf("-") + 1));
        if (endPos == NONE_FLAG || endPos >= fileSize) {
            endPos = fileSize - 1;
        }
        return endPos;
    }

    private static long string2Long(String stringValue) {
        long numeric = 0;

        if (stringValue.length() == 0) {
            numeric = NONE_FLAG;
        }
        else {
            try {
                numeric = Long.parseLong(stringValue);
            }
            catch (NumberFormatException ex) {
                numeric = 0;
            }
        }

        return numeric;
    }

    private static void doDownload(File file, HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        InputStream in = null;
        OutputStream out = null;

        try {
            in = new FileInputStream(file);
            out = response.getOutputStream();

            response.setContentLength(new Long(file.length()).intValue());

            byte[] buffer = new byte[BUFFER_SIZE];
            int length;
            while ((length = in.read(buffer)) != -1) {
                out.write(buffer, 0, length);
            }
        }
        finally {
            in.close();

            out.flush();
            out.close();
        }
    }

    private static void doRangeDownload(File file, long startPos, long endPos,
            HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        InputStream in = null;
        OutputStream out = null;

        try {
            in = new FileInputStream(file);
            out = response.getOutputStream();

            if (startPos > 0) {
                in.skip(startPos);
            }

            response.setStatus(HttpServletResponse.SC_PARTIAL_CONTENT);
            response.setContentLength(new Long(endPos - startPos + 1)
                    .intValue());
            response.setHeader("Content-Range", "bytes " + startPos + '-'
                    + endPos + '/' + file.length());

            byte[] buffer = new byte[BUFFER_SIZE];
            int length;
            while ((length = in.read(buffer)) != -1) {
                out.write(buffer, 0, length);

                startPos = startPos + length;

                if (startPos > endPos) {
                    break;
                }
            }
        }
        finally {
            in.close();

            out.flush();
            out.close();
        }
    }

    /**
     * 下载输入流的内容为文件
     * 
     * @param in
     *            输入流
     * @param request
     *            http请求
     * @param response
     *            http响应
     * @throws ServletException
     * @throws IOException
     */
    public static void download(InputStream in, HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        download(in, request, response, null);
    }

    /**
     * 下载输入流的内容为文件
     * 
     * @param in
     *            输入流
     * @param request
     *            http请求
     * @param response
     *            http响应
     * @param fileName
     *            指定用户浏览器下载的文件名
     * @throws ServletException
     * @throws IOException
     */
    public static void download(InputStream in, HttpServletRequest request,
            HttpServletResponse response, String fileName)
            throws ServletException, IOException {
        if (fileName != null) {
            response.setHeader("Content-Disposition", "attachment; filename="
                    + URLUtils.encode(fileName, charSet));
        }

        OutputStream out = null;

        try {
            out = response.getOutputStream();
            byte[] buffer = new byte[BUFFER_SIZE];
            int length;
            while ((length = in.read(buffer)) != -1) {
                out.write(buffer, 0, length);
            }
        }
        finally {
            in.close();

            out.flush();
            out.close();
        }
    }

    /**
     * 增加cookie，cookie的path为"/"
     * 
     * @param response
     *            http响应
     * @param cookieName
     *            cookie的名称
     * @param cookieValue
     *            cookie的值
     * @param maxAge
     *            cookie的存活期，毫秒为单位
     */
    public static void addCookie(HttpServletResponse response,
            String cookieName, String cookieValue, int maxAge) {
        Cookie cookie = new Cookie(cookieName, cookieValue);
        cookie.setMaxAge(maxAge);
        cookie.setPath("/");
        response.addCookie(cookie);
    }

    /**
     * 取得cookie的值
     * 
     * @param request
     *            http请求
     * @param cookieName
     *            cookie的名称
     * @return cookie的值
     */
    public static String getCookie(HttpServletRequest request, String cookieName) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            return null;
        }
        for (int i = 0; i < cookies.length; i++) {
            Cookie cookie = cookies[i];
            if (cookieName.equals(cookie.getName())) {
                return cookie.getValue();
            }
        }
        return null;
    }

    /**
     * 删除cookie
     * 
     * @param response
     *            http响应
     * @param cookieName
     *            cookie的名称
     */
    public static void removeCookie(HttpServletResponse response,
            String cookieName) {
        addCookie(response, cookieName, "", 0);
    }

    /**
     * 取得网站的跟目录，比如：http://www.zdsoft.net
     * 
     * @param request
     *            http请求
     * @return 网站的跟目录
     */
    public static String getWebsiteRoot(HttpServletRequest request) {
        int serverPort = request.getServerPort();
        return request.getScheme() + "://" + request.getServerName()
                + (serverPort == 80 ? "" : ":" + serverPort)
                + request.getContextPath();
    }

    /**
     * 为了跨域设置cookie需要增加P3P的HTTP头
     * 
     * @param response
     */
    public static void setP3PHeader(HttpServletResponse response) {
        response.setHeader("P3P", P3P_HEADER);
    }

}
