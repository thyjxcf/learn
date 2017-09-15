package net.zdsoft.keelcnet.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.JarURLConnection;
import java.net.URL;
//import java.text.MessageFormat;
import java.util.ArrayList;
//import java.util.Date;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.jar.JarFile;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

//import net.zdsoft.keelcnet.config.BootstrapManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import com.atlassian.core.util.ClassLoaderUtils;
import com.atlassian.core.util.zip.FolderArchiver;
import com.opensymphony.util.TextUtils;

/**
 * File operation utils
 * 
 * @author Brave Tao
 * @since 2004-5-15
 * @version $Id: FileUtils.java,v 1.2 2006/12/20 11:09:15 liangxiao Exp $
 */
public class FileUtils {
    private static Logger LOG = LoggerFactory.getLogger(FileUtils.class);
//    private static final int BUFFER_SIZE = 1024;
    public static final ResourceLoader RESOURCE_LOADER = new DefaultResourceLoader();
    public static final ResourcePatternResolver RESOURCE_RESOLVER = new PathMatchingResourcePatternResolver(
            RESOURCE_LOADER);
    public static final String CLASSPATH = ResourceLoader.CLASSPATH_URL_PREFIX;

    public FileUtils() {
    }

    public static String getFileName(String fullName) {
        int a = fullName.lastIndexOf("\\") + 1;
        int b = fullName.lastIndexOf("/") + 1;

        return fullName.substring((a <= b) ? b : a);
    }

    /**
     * Copy whole dir files
     * 
     * @param src_dir
     * @param dest_dir
     * @throws IOException
     */
    public static void copyDirectory(File src_dir, File dest_dir)
            throws IOException {
        File[] files = src_dir.listFiles();
        dest_dir.mkdirs();

        for (int i = 0; i < files.length; i++) {
            File file = files[i];
            File dest = new File(dest_dir, file.getName());

            if (file.isFile()) {
                copyFile(new FileInputStream(file), dest);
            }
            else {
                copyDirectory(file, dest);
            }
        }
    }

    /**
     * Copy from src to dest
     * 
     * @param src_file
     * @param dest_file
     * @throws IOException
     */
    public static void copyFile(File src_file, File dest_file)
            throws IOException {
        InputStream input = new FileInputStream(src_file);
        copyFile(input, dest_file);
    }

    /**
     * It's more common
     * 
     * @param src_stream
     * @param dest_file
     * @throws IOException
     */
    public static void copyFile(InputStream src_stream, File dest_file)
            throws IOException {
        File parentFile = dest_file.getParentFile();

        if (!parentFile.isDirectory()) {
            parentFile.mkdirs();
        }

        dest_file.createNewFile();

        InputStream input = new BufferedInputStream(src_stream);
        OutputStream output = new BufferedOutputStream(new FileOutputStream(
                dest_file));
        int ch;

        while ((ch = input.read()) != -1) {
            output.write(ch);
        }

        input.close();
        output.close();
    }

    /**
     * Save stringContent as file
     * 
     * @param stringContent
     * @param destFile
     * @throws IOException
     */
    public static void saveTextFile(String stringContent, File destFile)
            throws IOException {
        destFile.getParentFile().mkdirs();
        destFile.createNewFile();

        FileWriter writer = new FileWriter(destFile);
        writer.write(stringContent);
        writer.close();
    }

    /**
     * zip file
     * 
     * @param baseDir
     * @param zipFile
     *            file to zip
     * @throws Exception
     */
    public static void createZipFile(File baseDir, File zipFile)
            throws Exception {
        FolderArchiver compressor = new FolderArchiver(baseDir, zipFile);
        compressor.doArchive();
    }

//    /**
//     * temp file
//     * 
//     * @param prefix
//     * @return
//     */
//    public static File createTempFileInCNetTemp(String prefix) {
//        Date date = new Date();
//        String pattern = "_{0,date,MMddyyyy}_{1,time,HHmmss}";
//        String uniqueRandomFileName = MessageFormat.format(pattern,
//                new Object[] { date, date });
//
//        return new File(BootstrapManager.getUploadFolder(), prefix
//                + uniqueRandomFileName);
//    }

    /**
     * Load properties file as list, ignore line begin with '#'
     * 
     * @param resource
     * @return
     */
    public static List<String> readResourcesAsList(String resource) {
        List<String> result = new ArrayList<String>();

        try {
            InputStream is = ClassLoaderUtils.getResourceAsStream(resource,
                    FileUtils.class);
            BufferedReader in = new BufferedReader(new InputStreamReader(is));
            String s = null;

            do {
                if ((s = in.readLine()) == null) {
                    break;
                }

                String niceS = TextUtils.noNull(s).trim();

                if (TextUtils.stringSet(niceS) && (niceS.charAt(0) != '#')) {
                    result.add(s);
                }
            }
            while (true);

            is.close();
        }
        catch (IOException e) {
            LOG.error("IOException reading stream: " + e, e);
        }

        return result;
    }

    /**
     * 
     * @param resource
     * @return
     */
    public static String getResourceContent(String resource) {
        InputStream is = ClassLoaderUtils.getResourceAsStream(resource,
                FileUtils.class);

        return getInputStreamTextContent(is);
    }

    /**
     * 
     * @param req
     * @param resource
     * @return
     */
    public static String getResourceContent(HttpServletRequest req,
            String resource) {
        InputStream is = req.getSession().getServletContext()
                .getResourceAsStream(resource);
        String result = getInputStreamTextContent(is);

        if (result == null) {
            result = "";
        }

        return result;
    }

    /**
     * 
     * @param is
     * @return
     */
    public static String getInputStreamTextContent(InputStream is) {
        if (is == null) {
            return null;
        }

        String result = null;

        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream(is
                    .available());
            pump(is, baos);
            result = new String(baos.toByteArray());
            is.close();
        }
        catch (IOException e) {
            LOG.error("IOException reading stream: " + e, e);
        }

        return result;
    }

    /**
     * pump stream
     * 
     * @param is
     * @param os
     * @throws IOException
     */
    private static void pump(InputStream is, OutputStream os)
            throws IOException {
        byte[] buffer = new byte[1024];
        int lengthRead;

        while ((lengthRead = is.read(buffer)) >= 0) {
            os.write(buffer, 0, lengthRead);
        }
    }

    public static void main(String[] args) {
        String filename = "conf/spring/test/ok.xml";
        filename = filename.substring("conf/spring/".length(), filename
                .length()
                - ".xml".length());
        System.out.println(filename);
        filename = "conf\\spring\\test\\ok.xml";
        filename = filename.substring("conf/spring/".length(), filename
                .length()
                - ".xml".length());
        System.out.println(filename);
    }

    /**
     * load files just from config files.
     * @param prefix
     * @param suffix
     * @return
     */
    public static String[] readClasspathFiles(String prefix, String suffix){
        if ((prefix == null) || (suffix == null)) {
            return null;
        }

        if (true == prefix.startsWith(CLASSPATH)) {
            prefix = prefix.substring(CLASSPATH.length());
        }

        if (true == prefix.startsWith("/")) {
            prefix = prefix.substring(1);
        }

        if (false == prefix.endsWith("/")) {
            prefix += "/";
        }

        if (false == suffix.startsWith(".")) {
            suffix = "." + suffix;
        }

        List<String> files = new ArrayList<String>();

        String regx="";
        try {
            regx = CLASSPATH + prefix + "*" + suffix;
            Resource[] rs = RESOURCE_RESOLVER.getResources(regx);

            for (int i = 0; i < rs.length; i++) {
                Resource r = rs[i];
                String fullname = CLASSPATH + prefix + r.getFile().getName();
                files.add(fullname);

                // if (LOG.isDebugEnabled()) {
                // LOG.debug("加载配置：" + fullname);
                // }
            }
        }catch(IOException e){
            LOG.error("读取配置文件错误("+regx+"): "+e.toString());
        }
        
        String[] filearray = new String[files.size()];
        files.toArray(filearray);

        return filearray;        
    }
    /**
     * load file,include in jar
     * 
     * @param prefix
     * @param suffix
     * @param isLoadJar
     * @return
     */
    public static String[] readClasspathFiles(String prefix, String suffix,
            ServletContext servletContext) {
        if ((prefix == null) || (suffix == null)) {
            return null;
        }

        if (true == prefix.startsWith(CLASSPATH)) {
            prefix = prefix.substring(CLASSPATH.length());
        }

        if (true == prefix.startsWith("/")) {
            prefix = prefix.substring(1);
        }

        if (false == prefix.endsWith("/")) {
            prefix += "/";
        }

        if (false == suffix.startsWith(".")) {
            suffix = "." + suffix;
        }

        List<String> files = new ArrayList<String>();

        try {
            String regx = CLASSPATH + prefix + "*" + suffix;
            Resource[] rs = RESOURCE_RESOLVER.getResources(regx);

            for (int i = 0; i < rs.length; i++) {
                Resource r = rs[i];
                String fullname = CLASSPATH + prefix + r.getFile().getName();
                files.add(fullname);

                // if (LOG.isDebugEnabled()) {
                // LOG.debug("加载配置：" + fullname);
                // }
            }
        }
        catch (IOException ex) {
            try {
                // ignore jar exception, reload mappings from jar
                ClassLoader cl = Thread.currentThread().getContextClassLoader();
                URL url = cl.getResource(prefix);
                JarURLConnection con = (JarURLConnection) url.openConnection();
                JarFile jarfile = con.getJarFile();
                ZipFile zf = new ZipFile(jarfile.getName());
                Enumeration<? extends ZipEntry> en = zf.entries();

                while (en.hasMoreElements()) {
                    ZipEntry ze = (ZipEntry) en.nextElement();
                    String filename = ze.getName();

                    if (ze.isDirectory()) {
                        continue;
                    }

                    if (filename.endsWith(suffix)
                            && filename.startsWith(prefix)) {
                        String shortname = filename.substring(
                                prefix.length() + 1, filename.length()
                                        - suffix.length());

                        if ((shortname.indexOf("/") == -1)
                                && (shortname.indexOf("\\") == -1)) {
                            files.add(CLASSPATH + filename);

                            // if (LOG.isDebugEnabled()) {
                            // StringBuffer sb = new StringBuffer();
                            // sb.append("\n---加载jar配置---------------------")
                            // .append("\njar文件: " +
                            // zf.getName()).append("\n配置文件: " +
                            // filename);
                            // LOG.debug(sb.toString());
                            // }
                        }
                    }
                }

                zf.close();
            }
            catch (Exception e) {
                // ignore
            }
        }

        File[] jars = readJarFiles(servletContext);        

        List<String> jarConfigs = new ArrayList<String>();

        if ((jars != null)) {
            for (int i = 0; i < jars.length; i++) {
                try {
                    ZipFile zf = new ZipFile(jars[i].getCanonicalPath());
                    Enumeration<? extends ZipEntry> e = zf.entries();

                    while (e.hasMoreElements()) {
                        ZipEntry ze = (ZipEntry) e.nextElement();

                        if (ze.isDirectory()) {
                            continue;
                        }

                        String filename = ze.getName();

                        if ((false == filename.startsWith(prefix))
                                || (false == filename.endsWith(suffix))) {
                            continue;
                        }

                        filename = filename.substring(prefix.length(), filename
                                .length()
                                - suffix.length());

                        if ((filename.indexOf('/') == -1)
                                && (filename.indexOf("\\") == -1)) {
                            filename = prefix + filename + suffix;
                            jarConfigs.add(CLASSPATH + filename);

                            // if (LOG.isDebugEnabled()) {
                            // StringBuffer sb = new StringBuffer();
                            // sb.append("\n---加载jar配置---------------------")
                            // .append("\njar文件: " + jars[i].getName())
                            // .append("\n配置文件: " + filename);
                            // LOG.debug(sb.toString());
                            // }
                        }
                    }

                    zf.close();
                }
                catch (Exception e) {
                    // ignore
                }
            }
        }

        for (Iterator<String> iter = jarConfigs.iterator(); iter.hasNext();) {
            String element = (String) iter.next();

            if (!files.contains(element)) {
                // LOG.debug("忽略jar配置文件：" + element);
                // } else {
                files.add(element);
            }
        }

        String[] filearray = new String[files.size()];
        files.toArray(filearray);

        return filearray;
    }
    
    /**
     * 读取jar文件
     * @param servletContext
     * @return
     */
    public static File[] readJarFiles(ServletContext servletContext) {
        File[] jars = null;

        if (servletContext != null) {
            // 在web环境下，load context目录下的lib包
            String contextRoot = servletContext.getRealPath("/");
            File libDir = new File(new File(contextRoot), "WEB-INF/lib/");
            jars = libDir.listFiles();
        }
        else {
            // 在测试环境下，load classpath 包
            String classpath = System.getProperty("java.class.path");
            if (classpath == null || "".equals(classpath)) {
                jars=null;
            }
            else {
                String[] jarfiles = classpath.split(";");
                jars = new File[jarfiles.length];

                for (int i = 0; i < jarfiles.length; i++) {
                    jars[i] = new File(jarfiles[i]);
                }
            }
        }
        return jars;
    }
}
