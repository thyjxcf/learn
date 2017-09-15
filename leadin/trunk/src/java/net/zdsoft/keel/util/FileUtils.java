/*
 * Created on 2004-10-13
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package net.zdsoft.keel.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 文件工具类
 * 
 * @author liangxiao
 * @version $Revision: 1.14 $, $Date: 2007/10/16 03:25:24 $
 */
public final class FileUtils {

    private static Logger logger = LoggerFactory.getLogger(FileUtils.class);

    private static int BUFFER_SIZE = 1024 * 4;

    private FileUtils() {
    }

    /**
     * 递归取得某个目录下所有的文件
     * 
     * @param path
     *            目录
     * @return 文件List
     */
    public static List<File> getNestedFiles(String path) {
        File directory = new File(path);
        if (!directory.exists() || !directory.isDirectory()) {
            throw new IllegalArgumentException("Nonexistent directory[" + path
                    + "]");
        }

        return new Recursiver().getFileList(directory);
    }

    /**
     * 把字符串写到文件中
     * 
     * @param path
     *            文件路径
     * @param str
     *            字符串
     * @param append
     *            是否追加，否的话会覆盖原来的内容
     */
    public static void writeString(String path, String str, boolean append) {
        FileWriter out = null;
        try {
            out = new FileWriter(path, append);
            out.write(str);
        }
        catch (IOException e) {
            throw new RuntimeException("Could not write String[" + path + "]",
                    e);
        }
        finally {
            close(out);
        }
    }

    /**
     * 从文件中读取字符串，使用默认字符集
     * 
     * @param path
     *            文件路径
     * @return 文件内容的字符串
     */
    public static String readString(String path) {
        FileInputStream in = null;
        ByteArrayOutputStream out = null;

        try {
            in = new FileInputStream(path);
            out = new ByteArrayOutputStream();

            byte[] buffer = new byte[BUFFER_SIZE];
            int length;
            while ((length = in.read(buffer)) != -1) {
                out.write(buffer, 0, length);
            }
            return new String(out.toByteArray());
        }
        catch (IOException e) {
            throw new RuntimeException("Could not read String[" + path + "]", e);
        }
        finally {
            close(in);
            close(out);
        }
    }

    /**
     * 从输入流中读取字符串，使用默认字符集
     * 
     * @param in
     *            输入流
     * @return 流内容的字符串
     */
    public static String readString(InputStream in) {
        ByteArrayOutputStream out = null;

        try {
            out = new ByteArrayOutputStream();

            byte[] buffer = new byte[BUFFER_SIZE];
            int length;
            while ((length = in.read(buffer)) != -1) {
                out.write(buffer, 0, length);
            }
            return new String(out.toByteArray());
        }
        catch (IOException e) {
            throw new RuntimeException("Could not read stream", e);
        }
        finally {
            close(in);
            close(out);
        }
    }

    /**
     * 读取指定路径的Properties文件
     * 
     * @param path
     *            路径
     * @return Properties对象
     */
    public static Properties readProperties(String path) {
        File file = new File(path);
        if (!file.exists()) {
            return null;
        }

        Properties properties = new Properties();
        InputStream in = null;

        try {
            in = new FileInputStream(file);
            properties.load(in);
        }
        catch (IOException e) {
            throw new RuntimeException("Could not read Properties[" + path
                    + "]", e);
        }
        finally {
            close(in);
        }

        return properties;
    }

    /**
     * 从输入流读取Properties对象
     * 
     * @param in
     *            输入流
     * @return Properties对象
     */
    public static Properties readProperties(InputStream in) {
        Properties properties = new Properties();

        try {
            properties.load(in);
        }
        catch (IOException e) {
            throw new RuntimeException("Could not read Properties", e);
        }
        finally {
            close(in);
        }

        return properties;
    }

    /**
     * 把Properties对象写到指定路径的文件里
     * 
     * @param path
     *            路进
     * @param properties
     *            Properties对象
     */
    public static void writeProperties(String path, Properties properties) {
        OutputStream out = null;

        try {
            out = new FileOutputStream(path);
            properties.store(out, null);
        }
        catch (IOException e) {
            throw new RuntimeException("Could not write Properties[" + path
                    + "]", e);
        }
        finally {
            close(out);
        }
    }

    /**
     * 关闭输入流
     * 
     * @param in
     *            输入流
     */
    public static void close(InputStream in) {
        if (in == null) {
            return;
        }

        try {
            in.close();
        }
        catch (IOException e) {
            logger.error(e.toString());
        }
    }

    /**
     * 关闭输出流
     * 
     * @param out
     *            输出流
     */
    public static void close(OutputStream out) {
        if (out == null) {
            return;
        }

        try {
            out.close();
        }
        catch (IOException e) {
            logger.error(e.toString());
        }
    }

    /**
     * 关闭Reader
     * 
     * @param in
     *            Reader
     */
    public static void close(Reader in) {
        if (in == null) {
            return;
        }

        try {
            in.close();
        }
        catch (IOException e) {
            logger.error(e.toString());
        }
    }

    /**
     * 关闭Writer
     * 
     * @param out
     *            Writer
     */
    public static void close(Writer out) {
        if (out == null) {
            return;
        }

        try {
            out.close();
        }
        catch (IOException e) {
            logger.error(e.toString());
        }
    }

    /**
     * 取得文件的后缀名
     * 
     * @param fileName
     *            文件名
     * @return 后缀名
     */
    public static String getExtension(String fileName) {
        if (Validators.isEmpty(fileName)) {
            return null;
        }

        int pointIndex = fileName.lastIndexOf(".");
        return pointIndex > 0 && pointIndex < fileName.length() ? fileName
                .substring(pointIndex + 1).toLowerCase() : null;
    }

    private static class Recursiver {

        private static ArrayList<File> files = new ArrayList<File>();

        public List<File> getFileList(File file) {
            File children[] = file.listFiles();

            for (int i = 0; i < children.length; i++) {
                if (children[i].isDirectory()) {
                    new Recursiver().getFileList(children[i]);
                }
                else {
                    files.add(children[i]);
                }
            }

            return files;
        }
    }

    public static void main(String[] args) throws Exception {
        List<File> list = getNestedFiles("D:\\workspace\\patch\\web");
        for (int i = 0; i < list.size(); i++) {
            System.out.println(i + " " + list.get(i));
        }

        System.out
                .println(URLEncoder
                        .encode(
                                "jdbc:sybase:Tds:192.168.0.16:2638?ServiceName=eisv&JCONNECT_VERSION=6&DYNAMIC_PREPARE=true",
                                "8859_1"));
    }

    //----------------2010-12-07----------------------
    /**
     * 写文件流
     * 
     * @param response
     * @param in
     * @throws IOException
     */
    public static void serveFile(HttpServletResponse response, InputStream in) throws IOException {
        // Write the content of the attachment out
        OutputStream out = response.getOutputStream();

        try {
            byte[] buffer = new byte[128 * 1024];
            int read_count;

            while ((read_count = in.read(buffer)) != -1) {
                out.write(buffer, 0, read_count);
            }
            out.flush();
        } catch (Exception e) {
            throw new IOException("Error while serving the requested file!");
        } finally {
            if (in != null) {
                in.close();
            }

            if (out != null) {
                out.close();
            }
        }
    }

}
