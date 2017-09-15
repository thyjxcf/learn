/*
 * Created on 2004-8-20
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package net.zdsoft.leadin.file;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;

/**
 * @author liangxiao
 * 
 * To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public abstract class FileOperation implements Command {
    private static String rootDir = null;

    public FileOperation() {
    }

    public static String getRootDir() {
        if (rootDir == null) {
            throw new NullPointerException("root dir is null");
        }
        return rootDir;
    }

    public static void setRootDir(String rootDir) {
        FileOperation.rootDir = rootDir;
    }
    
    protected Map<String, String> getParameters(HttpServletRequest request, String prefix) {
        HashMap<String, String> parameters = new HashMap<String, String>();
        @SuppressWarnings("unchecked")
        Enumeration<String> enumeration = request.getParameterNames();

        while (enumeration.hasMoreElements()) {
            String parameterName = (String) enumeration.nextElement();
            String parameterValue = request.getParameter(parameterName);

            if (parameterName.startsWith(prefix)) {
                parameters.put(parameterName.substring(prefix.length()),
                        parameterValue);
            }
        }
        return parameters;
    }

    @SuppressWarnings("unchecked")
    protected Map getParameters(List<FileItem> fileItems, String prefix) {
        HashMap parameters = new HashMap();

        for (int i = 0; i < fileItems.size(); i++) {
            FileItem fileItem = (FileItem) fileItems.get(i);
            String fieldName = fileItem.getFieldName();

            if (fieldName.startsWith(prefix)) {
                if (fileItem.isFormField()) {
                    try {
						parameters.put(fieldName.substring(prefix.length()),
						        fileItem.getString("UTF-8"));
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();

					}
                }
                else if (fileItem.getSize() != 0) {
                    parameters.put(fieldName.substring(prefix.length()),
                            fileItem);
                }
            }
        }
        return parameters;
    }

    protected String[] getKeys(Map<String, String> parameters) {
        String[] keys = new String[parameters.size()];
        Iterator<String> iterator = parameters.keySet().iterator();

        int keyIndex = 0;
        while (iterator.hasNext()) {
            keys[keyIndex++] = (String) iterator.next();
        }

        return keys;
    }

    protected boolean isValidName(String name) {
        return name != null && name.trim().length() > 0;
    }

    protected void println(PrintWriter out, String key, boolean isSuccess)
            throws IOException {
        println(out, key, null, isSuccess);
    }

    protected void println(PrintWriter out, String key, String message,
            boolean isSuccess) throws IOException {
        if (message == null) {
            out.println((isSuccess ? "1" : "0") + "=" + key);
        }
        else {
            out.println((isSuccess ? "1" : "0") + "=" + key + ":" + message);
        }
    }
}