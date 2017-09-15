/*
 * Created on 2004-8-19
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package net.zdsoft.leadin.file;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;

/**
 * @author liangxiao
 * 
 * To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class UploadOperation extends FileOperation {
    private int cacheSize = 1024;
    private long maxSize = 1024 * 1024 * 1024;
    private String tempDir = System.getProperty("java.io.tmpdir");

    public UploadOperation() {
    }

    protected int getCacheSize() {
        return cacheSize;
    }

    protected void setCacheSize(int cacheSize) {
        this.cacheSize = cacheSize;
    }

    protected long getMaxSize() {
        return maxSize;
    }

    protected void setMaxSize(long maxSize) {
        this.maxSize = maxSize;
    }

    protected String getTempDir() {
        return tempDir;
    }

    protected void setTempDir(String tempDir) {
        this.tempDir = tempDir;
    }

    @SuppressWarnings({ "deprecation", "unchecked" })
    public void execute(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");
        PrintWriter out = response.getWriter();

        org.apache.commons.fileupload.DiskFileUpload diskFileUpload = new org.apache.commons.fileupload.DiskFileUpload();
        List fileItems = null;

        try {
            fileItems = diskFileUpload.parseRequest(request, cacheSize,
                    maxSize, tempDir);
        }
        catch (FileUploadException e) {
            out.print(e.getMessage());
            out.flush();
            out.close();
            return;
        }

        Map filePaths = getParameters(fileItems, "filePath");
        Map realNames = getParameters(fileItems, "realName");
        Map fileNames = getParameters(fileItems, "fileName");

        String[] keys = getKeys(fileNames);
        for (int i = 0; i < keys.length; i++) {
            String tempPath = (String) filePaths.get(keys[i]);
            FileItem fileItem = (FileItem) fileNames.get(keys[i]);
            String filePath = getRootDir() + (tempPath == null ? "" : tempPath);
            String realName = (String) realNames.get(keys[i]);
            String fileName = null;

            if (isValidName(realName)) {
                fileName = filePath + File.separator + realName;
            }
            else {
                fileName = filePath + getFileName(fileItem.getName());
            }

            if (tempPath != null) {
                new File(filePath).mkdirs();
            }

            try {
                fileItem.write(new File(fileName));
                println(out, keys[i], fileName, true);
            }
            catch (Exception e) {
                e.printStackTrace();
                println(out, keys[i], e.getMessage(), false);
            }
        }

        out.flush();
        out.close();
    }

    private String getFileName(String fileName) {
        return fileName.substring(fileName.lastIndexOf(File.separator));
    }

}