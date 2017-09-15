/* 
 * @(#)DocumentConverterServiceImpl.java    Created on Feb 14, 2012
 * Copyright (c) 2012 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.eis.base.converter.service.impl;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.ConnectException;
import java.util.Iterator;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;

import net.zdsoft.eis.base.common.entity.StorageDir;
import net.zdsoft.eis.base.common.service.StorageDirService;
import net.zdsoft.eis.base.common.service.SystemIniService;
import net.zdsoft.eis.base.constant.BaseConstant;
import net.zdsoft.eis.base.converter.listening.SofficeListening;
import net.zdsoft.eis.base.converter.service.DocumentConverterService;
import net.zdsoft.eis.base.converter.util.CommandUtil;
import net.zdsoft.eis.base.converter.util.OSUtil;
import net.zdsoft.eis.base.converter.util.PdfAddMarkUtil;
import net.zdsoft.eis.base.converter.util.StorePathUtil;
import net.zdsoft.keel.util.FileUtils;
import net.zdsoft.keel.util.ImageUtils;
import net.zdsoft.keel.util.UUIDUtils;

import org.apache.commons.lang.StringUtils;
import org.icepdf.core.pobjects.Document;
import org.icepdf.core.pobjects.Page;
import org.icepdf.core.util.GraphicsRenderingHints;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.artofsolving.jodconverter.DefaultDocumentFormatRegistry;
import com.artofsolving.jodconverter.DocumentConverter;
import com.artofsolving.jodconverter.DocumentFormat;
import com.artofsolving.jodconverter.openoffice.connection.OpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.connection.SocketOpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.converter.OpenOfficeDocumentConverter;

/**
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: Feb 14, 2012 2:22:14 PM $
 */
public class DocumentConverterServiceImpl implements DocumentConverterService {
	private Logger log = LoggerFactory.getLogger(DocumentConverterServiceImpl.class);

    private OpenOfficeConnection connection;
    private DocumentConverter converter;
    private SystemIniService systemIniService;
    private StorageDirService storageDirService;
    private SofficeListening sofficeListening;

    /**
     * pdf2swf软件安装位置
     */
    private String swfHome;
    /**
     * xpdf语言所在目录
     */
    private String xpdfLanguageHome;

    public void setConnection(OpenOfficeConnection connection) {
        this.connection = connection;
    }

    public void setConverter(DocumentConverter converter) {
        this.converter = converter;
    }
    
    public void setSystemIniService(SystemIniService systemIniService) {
		this.systemIniService = systemIniService;
	}

	public void setStorageDirService(StorageDirService storageDirService) {
		this.storageDirService = storageDirService;
	}
	
	public void setSofficeListening(SofficeListening sofficeListening) {
		this.sofficeListening = sofficeListening;
	}

	/**
     * 初始化
     */
    public void init() {
        // 查找openoffice软件安装位置
        String openofficeHome = System.getenv("OPENOFFICE_HOME");
        if (StringUtils.isEmpty(openofficeHome)) {
            System.out.println("提示：请配置环境变量OPENOFFICE_HOME(即OpenOffice程序所在的目录)");
        } else {
            System.out.println("提示：环境变量OPENOFFICE_HOME(即OpenOffice程序所在的目录)=" + openofficeHome);
        }
        
        // 连接openoffice
        try {
            connection.connect();
        } catch (ConnectException e) {
            System.out.println("提示：连接不上OpenOffice");
        }finally{
        	//无论有没有连接上都会监听
        	sofficeListening.init();
        }
        
        // 查找pdf2swf软件安装位置
        swfHome = System.getenv("SWF_HOME");
        if (StringUtils.isEmpty(swfHome)) {
            System.out.println("提示：请配置环境变量SWF_HOME(即pdf2swf程序所在的目录)");
        } else {
            System.out.println("提示：环境变量SWF_HOME(即pdf2swf程序所在的目录)=" + swfHome);
            if(swfHome.contains(" ")){
    			swfHome = "\"" + swfHome + "\"";
            }
        }

        xpdfLanguageHome = System.getenv("XPDF_LANGUAGE_HOME");
        if (StringUtils.isEmpty(xpdfLanguageHome)) {
            System.out.println("提示：请配置环境变量XPDF_LANGUAGE_HOME(即xpdf语言所在的目录)");
        } else {
            System.out.println("提示：环境变量XPDF_LANGUAGE_HOME(即xpdf语言所在的目录)=" + xpdfLanguageHome);
        }
    }

    public void destroy() {
        swfHome = null;
        xpdfLanguageHome = null;

        if (connection != null) {
            connection.disconnect();
            connection = null;
        }
        converter = null;
    }

    /**
     * 转换为pdf
     * 
     * @param input
     * @param output
     * @throws Exception
     */
    public void convert2PDF(String input, String output) throws Exception {
        // DocumentFormat stw = new DocumentFormat("OpenOffice.org 1.0 Template",
        // DocumentFamily.TEXT,
        // "application/vnd.sun.xml.writer", "stw");
        DefaultDocumentFormatRegistry formatReg = new DefaultDocumentFormatRegistry();
        DocumentFormat pdf = formatReg.getFormatByFileExtension("pdf");
        File inputFile = new File(input);
        File outputFile = new File(output);

        while (!(connection.isConnected())) {
        	try {
        		connection.connect();
			} catch (ConnectException e) {
			    //如果连接不上等待10秒重新连接
			    System.out.println("openoffice 服务连接不上，等待10秒重新连接");
				Thread.sleep(10 * 1000);
			}
        }

        //文档转换成pdf时是否启用临时目录
//        Boolean isUseTemp = systemIniService.getBooleanValue("SYSTEM.CONVERTOR.PDF.2.TEMP");
//        if (isUseTemp) {
        	//先转换到临时目录
        //针对linux读写权限问题，在有权限的临时目录进行转换2016/7/19
        	String tempDir = OSUtil.getTempDir()+"/convert2PDF";
			String tempPath = tempDir + "/" + UUIDUtils.newId();
			File tempInFile = new File(tempDir);
			tempInFile.setReadable(true,false);
			tempInFile.setWritable(true,false);
			File tempFile = new File(tempPath);
			tempFile.setWritable(true,false);
			try {
				org.apache.commons.io.FileUtils.copyFileToDirectory(inputFile, tempInFile);
				File tempInFileCom =  new File(tempDir+input.substring(input.lastIndexOf("/")));
			    monitorConvert(tempInFileCom, tempFile, pdf);
				org.apache.commons.io.FileUtils.copyFile(tempFile, outputFile);
			} finally{
				org.apache.commons.io.FileUtils.deleteQuietly(tempInFile);
			}
//		}else {
//		    monitorConvert(inputFile, outputFile, pdf);
//		}
    }
    /**
     * 加入转换监控（如果长时间没有转换成功 ，则停掉当前转换文件）
     * @param inputFile
     * @param outputFile
     * @param pdf
     * @throws Exception 
     */
    private void monitorConvert(final File inputFile, final File outputFile, final DocumentFormat pdf) throws Exception{
        //开启转换线程
    	final ConverterException converterException = new ConverterException();
        Runnable convertThread = new Runnable(){
            @Override
            public void run() {
                try {
                    //转换
                    converter.convert(inputFile, outputFile, pdf);
                } catch (Exception e1) {
                	converterException.exception = e1;
                }
            }
        };
        //超时时间(计算，0.1M大约需要5秒钟)
        int timeoutSec = (int)(inputFile.length() / 1024 / 1024 * 10 * 5);
        //每次转换至少5秒钟
        if (timeoutSec < 5) {
            timeoutSec = 5;
        }
        monitorConvert(outputFile, timeoutSec, convertThread, "文件转换成pdf时失败");
        if (converterException.exception != null) {
            throw converterException.exception;
        }
    }
    /**
     * 监控线程
     * @param outputFile
     * @param convertThread
     */
    private void monitorConvert(File outputFile, int timeoutSec, Runnable run, String msg){
        Thread convertThread = new Thread(run);
        convertThread.setDaemon(true);
        convertThread.start();
        int timeOut = 0;
        while (convertThread.isAlive()) {
            if (outputFile.exists()) {
                //文件已存在，说明已开始转换
                try {
                    //等待转换线程结束
                    convertThread.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                break;
            }else if (timeOut >= timeoutSec) {
                //超过${timeoutSec }秒，说明openoffice已经卡死在那里。
                //重启soffice
                try {
                    SofficeListening.stopSoffice();
                }catch (Exception e) {
                    //直接停掉线程
                    convertThread.interrupt();
                }
                throw new RuntimeException(msg);
            }else {
                try {
                    //主线程等待1秒钟，判断文件是否开始转换
                    Thread.sleep(1 * 1000);
                    timeOut ++;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * PDF转SWF工具
     * 
     * @param sourcePath
     * @param destPath
     * @param fileName
     * @return
     * @throws IOException
     */
    public int convertPDF2SWF(String sourcePath, String swfPath, String args) throws Exception {
        // 源文件不存在则返回
        File source = new File(sourcePath);
        if (!source.exists()) {
            log.error(sourcePath + " 文件不存在");
            return 0;
        }
        // 调用pdf2swf命令进行转换  
        String command = swfHome + " -o \"" + swfPath + "\" -s languagedir=" + xpdfLanguageHome
                + " -s flashversion=9 \"" + sourcePath + "\"" + args;
        String os = System.getProperty("os.name");
        final String[] cmd;
        if (os != null && os.startsWith("Windows")) {
            cmd = new String[]{"cmd","/c",command };
        } else {
            cmd = new String[]{"/bin/sh", "-c", command };
        }
        CommandUtil.execCommand(cmd);

        return 0;
    }

    public static final String FILETYPE_JPG = "jpg";
    public static final String SUFF_IMAGE = "." + FILETYPE_JPG;

    /**
     * 将指定pdf文件的首页转换为指定路径的缩略图
     * 
     * @param filepath 原文件路径，例如d:/test.pdf
     * @param imagepath 图片生成路径，例如 d:/test-1.jpg
     * @param zoom 缩略图显示倍数，1表示不缩放，0.3则缩小到30%
     */
    public void convertPDF2JPG(String filepath, String imagepath, float zoom) throws Exception {
        // ICEpdf document class
        Document document = null;
        ImageWriter writer = null;
        FileOutputStream out = null;
        ImageOutputStream outImage = null;
        try {

            float rotation = 0f;
            document = new Document();
            document.setFile(filepath);
            // maxPages = document.getPageTree().getNumberOfPages();

            BufferedImage img = (BufferedImage) document.getPageImage(0,
                    GraphicsRenderingHints.SCREEN, Page.BOUNDARY_CROPBOX, rotation, zoom);

            Iterator<ImageWriter> iter = ImageIO.getImageWritersBySuffix(FILETYPE_JPG);
            writer = iter.next();
            File outFile = new File(imagepath);
            out = new FileOutputStream(outFile);
            outImage = ImageIO.createImageOutputStream(out);
            writer.setOutput(outImage);
            writer.write(new IIOImage(img, null, null));
        } finally {
            try {
                if (null != outImage)
                    outImage.close();

                if (null != out)
                    out.close();

                if (null != writer)
                    writer.dispose();

                if (null != document)
                    document.dispose();
            } catch (IOException e) {
                log.error(e.toString());
            }
        }
    }

    public void conver(String filePath, boolean isAddMark, int width, int height) throws Exception {
        // 文件路径名 不包括扩展名
        String filePathNoPostfix = filePath.substring(0, filePath.lastIndexOf("."));

        String swfFilePath = filePathNoPostfix + ".swf";
        String picFilePath = filePathNoPostfix + "_picture.jpg";
        String previewPicFilePath = filePathNoPostfix + "_preview.jpg";
        String defaultPdfPath = filePathNoPostfix + "_default.pdf";
        String handPdfPath = filePathNoPostfix + "_hand.pdf";
        String pdfPath = filePathNoPostfix + ".pdf";
        File handPdfFile = new File(handPdfPath);
        if (handPdfFile.exists()) {
        	filePath = pdfPath;
        	handPdfFile.delete();
        }

        boolean isPdf = false;

        String fileType = FileUtils.getExtension(filePath);
        // 转换为pdf
        if ("pdf".equalsIgnoreCase(fileType)) {
            pdfPath = filePath;
            isPdf = true;
            
            //查看*_default.pdf存在不存在，如果存在，则使用此文件进行转换。不存在则使用sourcePath转换（在重复转换的时候需要用到）
        	File defaultPdfFile = new File(defaultPdfPath);
        	if (defaultPdfFile.exists()) {
        		//删除加过水印的pdf
        		File source = new File(pdfPath);
        		if (source.exists()) {
        			source.delete();
				}
        		//将*_default.pdf转成*.pdf
        		defaultPdfFile.renameTo(source);
    		}
        } else {
            convert2PDF(filePath, pdfPath);
        }

        // 转换为图片
        convertPDF2JPG(pdfPath, picFilePath, 1);

        //转换为缩略图
        ImageUtils.changeOppositeSize(picFilePath, previewPicFilePath, width, height);

        //添加水印
        if(isAddMark){
        	String markImagePathTop = systemIniService.getValue("SYSTEM.CONVERTOR.RESOURCE.MARK.IMG.PATH");
        	String markImagePathEnd = systemIniService.getValue("SYSTEM.CONVERTOR.RESOURCE.MARK.IMG.PATH.END");
			
        	if (StringUtils.isNotBlank(markImagePathTop)) {
        		//转换后的pdf地址
                String markPdfPath = filePathNoPostfix + "_mark.pdf";
                //去除缓存地址
                markImagePathTop = StorePathUtil.removeCache(markImagePathTop);
                markImagePathEnd = StorePathUtil.removeCache(markImagePathEnd);
                
        		PdfAddMarkUtil.addPdfMark(pdfPath, markPdfPath, getStorePath() + markImagePathTop, getStorePath() + markImagePathEnd);
        		// 删除中间文件
                if (isPdf) {
                    //将两个pdf名称互换，为了下载的时候使用加水印的pdf
                    new File(pdfPath).renameTo(new File(defaultPdfPath));
                }else {
                	org.apache.commons.io.FileUtils.deleteQuietly(new File(pdfPath));
				}
        		new File(markPdfPath).renameTo(new File(pdfPath));
			}
		}
        // pdf转换为swf
        try {
            convertPDF2SWF(pdfPath, swfFilePath, "");
        } catch (Exception e) {
            //转换出错，尝试使用对文件中的图形转成点阵
            convertPDF2SWF(pdfPath, swfFilePath, " -s poly2bitmap");
        }
		//是否设置成只读
		Boolean isReadOnly = systemIniService.getBooleanValue("SYSTEM.CONVERTOR.RESOURCE.PDF.READONLY");
		if (isReadOnly) {
			File pdfReadonlyFile = new File(filePathNoPostfix + "_readonly.pdf");
			PdfAddMarkUtil.setReadOnly(pdfPath, pdfReadonlyFile.getAbsolutePath());
			org.apache.commons.io.FileUtils.deleteQuietly(new File(pdfPath));
			//修改成原pdf名称
			pdfReadonlyFile.renameTo(new File(pdfPath));
		}
    }

	public static void main(String[] args) throws Exception {
		// String sourcePath = "g:\\test\\测试.pdf";
		// String destPath = "g:\\test";
		// String fileName = "测试.swf";
		// convertPDF2SWF(sourcePath, destPath, fileName);
		// System.out.println("finished.........");

		String input = "c:\\tem\\1.txt";
		String output = "c:\\tem\\1.pdf";
		String fileName = "c:\\tem\\1.swf";
		SocketOpenOfficeConnection connection = new SocketOpenOfficeConnection("127.0.0.1", 8100);
		OpenOfficeDocumentConverter converter = new OpenOfficeDocumentConverter(connection);

		DocumentConverterServiceImpl documentConverterServiceImpl = new DocumentConverterServiceImpl();
		documentConverterServiceImpl.setConnection(connection);
		documentConverterServiceImpl.setConverter(converter);
		documentConverterServiceImpl.init();

		documentConverterServiceImpl.convert2PDF(input, output);
//		documentConverterServiceImpl.convertPDF2SWF(output, fileName, "");
		
//		documentConverterServiceImpl.convertPDF2SWF("E:\\workspace4\\eisu6\\store\\import_data\\test.pdf", "E:\\workspace4\\eisu6\\store\\import_data\\test.swf", "");
		System.out.println("finished......");
	}
	
	
	/**
	 * 获取默认store目录路径
	 * @return
	 */
	private String getStorePath(){
		StorageDir dir = storageDirService.getStorageDir(BaseConstant.ZERO_GUID);
		return dir.getDir();
	}
		
	private class ConverterException {
	    public Exception exception;
	}

	
}
