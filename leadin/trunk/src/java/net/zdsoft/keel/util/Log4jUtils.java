/* 
 * @(#)Log4jUtils.java    Created on 2005-5-17
 * Copyright (c) 2005 ZDSoft.net, Inc. All rights reserved.
 * $Header: /project/keel/src/net/zdsoft/keel/util/Log4jUtils.java,v 1.7 2008/07/31 11:25:34 huangwj Exp $
 */
package net.zdsoft.keel.util;

import java.io.File;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import org.apache.log4j.Appender;
import org.apache.log4j.FileAppender;
import org.apache.log4j.Logger;

/**
 * Log4j工具类
 * 
 * @author liangxiao
 * @author huangwj
 * @version $Revision: 1.7 $, $Date: 2008/07/31 11:25:34 $
 */
public final class Log4jUtils {

    private static Logger logger = Logger.getLogger(Log4jUtils.class);

    private static String logRoot = null;

    static {
        @SuppressWarnings("unchecked")
        Enumeration enumeration = Logger.getRootLogger().getAllAppenders();
        while (enumeration.hasMoreElements()) {
            Appender appender = (Appender) enumeration.nextElement();
            if (appender instanceof FileAppender) {
                logRoot = ((FileAppender) appender).getFile();
                break;
            }
        }

        if (logRoot != null) {
            logRoot = logRoot.substring(0, logRoot.lastIndexOf('/'));
        }
    }

    /**
     * 取得日志文件根目录
     * 
     * @return 日志文件根目录
     */
    public static String getLogRoot() {
        return logRoot;
    }

    /**
     * 取得所有日志文件
     * 
     * @return 日志文件List
     */
    public static List<File> getAllLogs() {
        ArrayList<File> logs = new ArrayList<File>();
        if (logRoot == null) {
            return logs;
        }

        File file = new File(logRoot);
        if (!file.exists()) {
            return logs;
        }

        File[] files = file.listFiles();
        for (int i = 0; i < files.length; i++) {
            if (files[i].isFile()) {
                logs.add(files[i]);
            }
        }

        return logs;
    }

    /**
     * 根据名称找到日志文件
     * 
     * @param logName
     *            日志文件名称
     * @return 日志文件
     */
    public static File getLog(String logName) {
        if (logName.indexOf("..") != -1) {
            throw new IllegalArgumentException("Illegal logName");
        }

        File log = new File(logRoot + File.separator + logName);
        return log.exists() ? log : null;
    }

    /**
     * 根据名称删除日志文件
     * 
     * @param logName
     *            日志文件名称
     */
    public static void removeLog(String logName) {
        if (logName.indexOf("..") != -1) {
            throw new IllegalArgumentException("Illegal logName");
        }

        File log = new File(logRoot + File.separator + logName);
        if (log.exists()) {
            log.delete();
            logger.debug("Deleted log file " + log);
        }
    }

    private Log4jUtils() {
    }

    public static void main(String[] args) {
    }

}
