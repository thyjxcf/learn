/* 
 * @(#)VideoConverterServiceImpl.java    Created on Mar 20, 2012
 * Copyright (c) 2012 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.eis.base.converter.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.zdsoft.eis.base.converter.service.VideoConverterService;
import net.zdsoft.eis.base.converter.util.CommandUtil;
import net.zdsoft.keel.util.FileUtils;
import net.zdsoft.keel.util.ImageUtils;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: Mar 20, 2012 1:53:34 PM $
 */
public class VideoConverterServiceImpl implements VideoConverterService {
    private Logger log = LoggerFactory.getLogger(VideoConverterServiceImpl.class);

    private static String ffmpegpath; // ffmpeg.exe的目录
    private static String mencoderpath; // mencoder的目录

    public static void main(String args[]) {
        VideoConverterServiceImpl c = new VideoConverterServiceImpl();

        ffmpegpath = "D:\\video\\ffmpeg\\bin\\ffmpeg.exe";
        mencoderpath = "D:\\video\\mencoder\\mencoder.exe";

        String filePath = "D:\\video\\test.avi";
        try {
            c.conver(filePath, 128, 86);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // String flvFilePath = "D:\\video\\a.flv";
        // String picFilePath = "D:\\video\\a_picture.jpg";
        // String previewPicFilePath = "D:\\video\\a_preview.jpg";
        // try {
        // c.processImg(flvFilePath, picFilePath);
        // ImageUtils.changeOppositeSize(picFilePath, previewPicFilePath, 128, 86);
        // } catch (Exception e) {
        // e.printStackTrace();
        // }
        System.out.println("=========finished===========");
    }

    /**
     * 初始化
     */
    public void init() {

        // 查找pdf2swf软件安装位置
        ffmpegpath = System.getenv("FFMPEG_PATH");
        if (StringUtils.isEmpty(ffmpegpath)) {
            System.out.println("提示：请配置环境变量FFMPEG_PATH(即ffmpeg程序的路径)");
        } else {
            System.out.println("提示：环境变量FFMPEG_PATH(即ffmpeg程序的路径)=" + ffmpegpath);
        }

        mencoderpath = System.getenv("MENCODER_PATH");
        if (StringUtils.isEmpty(mencoderpath)) {
            System.out.println("提示：请配置环境变量MENCODER_PATH(即mencoder程序的路径)");
        } else {
            System.out.println("提示：环境变量MENCODER_PATH(即mencoder程序的路径)=" + mencoderpath);
        }
    }

    public void destroy() {
        ffmpegpath = null;
        mencoderpath = null;
    }

    /**
     * 视频转换
     * 
     * @param filePath
     * @return
     * @throws Exception
     */
    public void conver(String filePath, int width, int height) throws Exception {
        // 文件路径名 不包括扩展名
        String filePathNoPostfix = filePath.substring(0, filePath.lastIndexOf("."));

        String flvFilePath = filePathNoPostfix + ".flv";
        String aviFilePath = filePathNoPostfix + ".avi";
        String picFilePath = filePathNoPostfix + "_picture.jpg";
        String previewPicFilePath = filePathNoPostfix + "_preview.jpg";

        if (!checkfile(filePath)) {
            throw new Exception(filePath + "文件不存在");
        }

        String fileType = FileUtils.getExtension(filePath);
        if (!("flv".equals(fileType))) {
            long begintime = (new Date()).getTime();
            process(filePath, flvFilePath, aviFilePath);
            long endtime = (new Date()).getTime();
            long timecha = (endtime - begintime);
            String totaltime = sumTime(timecha);
            log.debug("转换成功，共用了:" + totaltime + " ");
        }
        try {
        	processImg(flvFilePath, picFilePath);
        	log.debug("截图成功");
        	
        	ImageUtils.changeOppositeSize(picFilePath, previewPicFilePath, width, height);
        	log.debug("缩略图成功");
		} catch (Exception e) {
			log.debug("截图失败, message=" + e.getMessage());
		}
    }

    private void process(String filePath, String flvFilePath, String aviFilePath) throws Exception {
        int type = checkContentType(filePath);
        if (type == 0) {
            processFLV(filePath, flvFilePath, filePath);// 直接将文件转为flv文件
        } else if (type == 1) {
            processAVI(filePath, aviFilePath, type);
            processFLV(filePath, flvFilePath, aviFilePath);// 将avi转为flv
        } else {
            throw new Exception("格式不能转换");
        }
    }

    private int checkContentType(String filePath) {
        String type = FileUtils.getExtension(filePath);
        // ffmpeg能解析的格式：（asx，asf，mpg，wmv，3gp，mp4，mov，avi，flv等）
        if (type.equals("avi")) {
            return 0;
        } else if (type.equals("mpg")) {
            return 0;
        } else if (type.equals("wmv")) {
            return 0;
        } else if (type.equals("3gp")) {
            return 0;
        } else if (type.equals("mov")) {
            return 0;
        } else if (type.equals("mp4")) {
            return 0;
        } else if (type.equals("asf")) {
            return 0;
        } else if (type.equals("asx")) {
            return 0;
        } else if (type.equals("flv")) {
            return 0;
        }
        // 对ffmpeg无法解析的文件格式(wmv9，rm，rmvb等),
        // 可以先用别的工具（mencoder）转换为avi(ffmpeg能解析的)格式.
        else if (type.equals("wmv9")) {
            return 1;
        } else if (type.equals("rm")) {
            return 1;
        } else if (type.equals("rmvb")) {
            return 1;
        }
        return 9;
    }

    private boolean checkfile(String filePath) {
        File file = new File(filePath);
        if (!file.isFile()) {
            return false;
        } else {
            return true;
        }
    }

    // 对ffmpeg无法解析的文件格式(wmv9，rm，rmvb等), 可以先用别的工具（mencoder）转换为avi(ffmpeg能解析的)格式.
    private void processAVI(String filePath, String aviFilePath, int type) throws Exception {
        List<String> commend = createCommand();
        commend.add(mencoderpath);
        commend.add(filePath);
        commend.add("-oac");
        commend.add("mp3lame");
        commend.add("-lameopts");
        commend.add("preset=64");
        commend.add("-ovc");
        commend.add("x264");
        commend.add("-x264encopts");  
        commend.add("bitrate=300");
        commend.add("-of");
        commend.add("avi");
        commend.add("-o");
        commend.add(aviFilePath);
        // 命令类型：mencoder 1.rmvb -oac mp3lame -lameopts preset=64 -of avi -o rmvb.avi

        CommandUtil.execCommand(commend);
    }

    /**
     * ffmpeg能解析的格式：（asx，asf，mpg，wmv，3gp，mp4，mov，avi，flv等）
     * 
     * @param filePath 原文件
     * @param flvFilePath 转换后的文件
     * @param convertFilePath 要转换的文件（原文件或中间文件）
     * @return
     * @throws Exception
     */
    private void processFLV(String filePath, String flvFilePath, String convertFilePath)
            throws Exception {

        if (!checkfile(convertFilePath)) {
            log.debug(convertFilePath + " is not file");
            return;
        }

        List<String> commend = createCommand();
        commend.add(ffmpegpath);
        commend.add("-i");
        commend.add(convertFilePath);
        commend.add("-vcodec");
        commend.add("libx264");
        commend.add("-qscale");
        commend.add("6");
//        commend.add("-b");
//        commend.add("300k");
//        commend.add("-r");
//        commend.add("29.97"); 
        commend.add("-filter:v");
        commend.add("yadif");
        
        commend.add("-acodec");
        commend.add("libmp3lame");
        commend.add("-ab");
        commend.add("128");
        commend.add("-ac");
        commend.add("2");
        commend.add("-ar");
        commend.add("22050");
        commend.add("-y");
        commend.add(flvFilePath);

        CommandUtil.execCommand(commend);
        deleteFile(filePath, convertFilePath);

    }

    public void processImg(String flvFilePath, String picFilePath) throws Exception {

        List<String> commend = createCommand();
        commend.add(ffmpegpath);
        commend.add("-an");
        commend.add("-i");
        commend.add(flvFilePath);
        commend.add("-y");
        commend.add("-f");
        commend.add("image2");
        commend.add("-ss");
        commend.add("60");
        commend.add("-vframes");
        commend.add("1");
        //commend.add("-t");
        //commend.add("0:0:0.001");
        // commend.add("-s");
        // commend.add("128x86");
        commend.add(picFilePath);

        CommandUtil.execCommand(commend);
    }

    private List<String> createCommand() {
        List<String> commend = new ArrayList<String>();
        // String os = System.getProperty("os.name");
        // if (os != null && os.startsWith("Windows")) {
        // } else {
        // commend.add("/bin/sh");
        // commend.add("-c");
        // }
        return commend;
    }

    private void deleteFile(String filePath, String middleFilePath) {
        File file = new File(middleFilePath);
        if (!(filePath.equals(middleFilePath))) {
            if (file.delete()) {
                log.debug("文件" + middleFilePath + "已删除 ");
            }
        }
    }

    public String sumTime(long ms) {
        int ss = 1000;
        long mi = ss * 60;
        long hh = mi * 60;
        long dd = hh * 24;

        long day = ms / dd;
        long hour = (ms - day * dd) / hh;
        long minute = (ms - day * dd - hour * hh) / mi;
        long second = (ms - day * dd - hour * hh - minute * mi) / ss;
        long milliSecond = ms - day * dd - hour * hh - minute * mi - second * ss;

        String strDay = day < 10 ? "0" + day + "天" : "" + day + "天";
        String strHour = hour < 10 ? "0" + hour + "小时" : "" + hour + "小时";
        String strMinute = minute < 10 ? "0" + minute + "分" : "" + minute + "分";
        String strSecond = second < 10 ? "0" + second + "秒" : "" + second + "秒";
        String strMilliSecond = milliSecond < 10 ? "0" + milliSecond : "" + milliSecond;
        strMilliSecond = milliSecond < 100 ? "0" + strMilliSecond + "毫秒" : "" + strMilliSecond
                + " 毫秒";
        return strDay + " " + strHour + ":" + strMinute + ":" + strSecond + " " + strMilliSecond;
    }
}
