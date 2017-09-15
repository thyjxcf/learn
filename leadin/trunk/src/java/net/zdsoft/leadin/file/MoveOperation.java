/*
 * Created on 2004-8-20
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package net.zdsoft.leadin.file;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author liangxiao
 * 
 * To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class MoveOperation extends FileOperation {

    public MoveOperation() {
    }

    public void execute(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
//        request.setCharacterEncoding("UTF-8");
        PrintWriter pageOut = response.getWriter();

        Map<String, String> sources = getParameters(request, "src");
        Map<String, String> destinations = getParameters(request, "dest");

        String[] keys = getKeys(sources);
        for (int i = 0; i < keys.length; i++) {
            String source = (String) sources.get(keys[i]);
            String destination = (String) destinations.get(keys[i]);
//            source = URLDecoder.decode(URLEncoder.encode(source,"ISO-8859-1"), "ISO-8859-1");            
//            destination = URLDecoder.decode(URLEncoder.encode(destination,"ISO-8859-1"), "ISO-8859-1");
//            source = new String(source.getBytes(),"UTF-8");
//            destination = new String(destination.getBytes(),"UTF-8");

            if (source == null || destination == null) {
                continue;
            }

            source = getRootDir() + File.separator + source;
            destination = getRootDir() + File.separator + destination;

            File srcFile = new File(source);
            File destFile = new File(destination);

            if (!srcFile.exists()) {
                println(pageOut, keys[i], false);
                continue;
            }
            createDirectory(destination);
            if (destFile.exists()) {
                destFile.delete();
            }

            println(pageOut, keys[i], transfer(srcFile, destFile));
        }

        pageOut.flush();
        pageOut.close();
    }
    private void createDirectory(String fileName){
        while(fileName.endsWith("\\")){
            fileName=fileName.substring(0,fileName.length()-1);
        }
        while(fileName.endsWith("/")){
            fileName=fileName.substring(0,fileName.length()-1);
        }
        int i=fileName.lastIndexOf("\\");
        int j=fileName.lastIndexOf("/");
        new File(fileName.substring(0,i>j?i:j)).mkdirs();
        
    }
    protected boolean transfer(File src, File dest) {
        return src.renameTo(dest);
    }
}