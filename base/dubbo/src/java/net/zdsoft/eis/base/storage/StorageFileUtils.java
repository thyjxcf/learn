/* 
 * @(#)StorageFileUtils.java    Created on Jan 26, 2011
 * Copyright (c) 2011 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.eis.base.storage;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import net.zdsoft.eis.base.photo.PhotoEntity;
import net.zdsoft.keel.util.ArrayUtils;
import net.zdsoft.keel.util.ImageUtils;
import net.zdsoft.keelcnet.action.UploadFile;
import net.zdsoft.keelcnet.config.BootstrapManager;
import net.zdsoft.leadin.exception.FileUploadFailException;

import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.dispatcher.multipart.MultiPartRequestWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 存储文件（附件、照片等支持扩容的文件）工具类
 * 
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: Jan 26, 2011 11:08:08 AM $
 */
public class StorageFileUtils {
    private static final Logger log = LoggerFactory.getLogger(StorageFileUtils.class);

    /**
     * 
     * @param fileFormats 支持的文件格式
     * @param fileMaxSize 以K为单位 0表示不限制大小
     * @throws FileUploadFailException
     * @return
     */
    public static UploadFile handleFile(String[] fileFormats, int fileMaxSize)
            throws FileUploadFailException {

        HttpServletRequest request = ServletActionContext.getRequest();
        if (request instanceof MultiPartRequestWrapper) {
            MultiPartRequestWrapper requestWrapper = (MultiPartRequestWrapper) request;
            Enumeration<String> e = requestWrapper.getFileParameterNames();
            if (e.hasMoreElements()) {
                String fieldName = e.nextElement();
                File uploadedFile = (((File[]) requestWrapper.getFiles(fieldName))[0]);
                String fileName = requestWrapper.getFileNames(fieldName)[0];
              //vediocapture照片采集 特殊处理
                if(("unused").endsWith(fileName))
                	fileName="vediocapture.bmp";
                String contentTypes = requestWrapper.getContentTypes(fieldName)[0];

                String fileExt = net.zdsoft.keel.util.FileUtils.getExtension(fileName);
                if (org.apache.commons.lang.ArrayUtils.isNotEmpty(fileFormats) && !ArrayUtils.contains(fileFormats, fileExt)) {
                    throw new FileUploadFailException("文件格式不对，系统只支持"
                            + ArrayUtils.toString(fileFormats, "、") + "格式");
                }

                if (fileMaxSize > 0 && uploadedFile.length() / 1024 > fileMaxSize) {
                    throw new FileUploadFailException("文件大小不能大于" + fileMaxSize + "K");
                }
                //不能上传空文档
    			if(uploadedFile.length() == 0){
    				throw new FileUploadFailException("上传文件大小为空!");
    			}

                UploadFile uploadfile = new UploadFile(fileName, uploadedFile, contentTypes,
                        fieldName);
                return uploadfile;
            }
        }
        return null;
    }
    
    /**
     * 
     * @param fileFormats 支持的文件格式
     * @param fileMaxSize 以K为单位 0表示不限制大小
     * @throws FileUploadFailException
     * @return
     */
    public static List<UploadFile> handleFiles(String[] fileFormats, int fileMaxSize)
            throws FileUploadFailException {
    	List<UploadFile> uploadFileList = new ArrayList<UploadFile>();
        HttpServletRequest request = ServletActionContext.getRequest();
        if (request instanceof MultiPartRequestWrapper) {
            MultiPartRequestWrapper requestWrapper = (MultiPartRequestWrapper) request;
            Enumeration<String> e = requestWrapper.getFileParameterNames();
            while (e.hasMoreElements()) {
                String fieldName = e.nextElement();
                File[] uploadedFiles = ((File[]) requestWrapper.getFiles(fieldName));
                for (int i = 0; i < uploadedFiles.length; i++) {
                	File uploadedFile =  uploadedFiles[i];
                	//不能上传空文档
                	String fileName = requestWrapper.getFileNames(fieldName)[i];
                	if(uploadedFile.length() == 0){
                		throw new FileUploadFailException(fileName+"文件大小为空!");
                	}
                	//vediocapture照片采集 特殊处理
                	if(("unused").endsWith(fileName))
                		fileName="vediocapture.bmp";
                	String contentTypes = requestWrapper.getContentTypes(fieldName)[i];
                	
                	String fileExt = net.zdsoft.keel.util.FileUtils.getExtension(fileName);
                	if (org.apache.commons.lang.ArrayUtils.isNotEmpty(fileFormats) && !ArrayUtils.contains(fileFormats, fileExt)) {
                		throw new FileUploadFailException(fileName+"文件格式不对，系统只支持"
                				+ ArrayUtils.toString(fileFormats, "、") + "格式");
                	}
                	
                	if (fileMaxSize > 0 && uploadedFile.length() / 1024 > fileMaxSize) {
                		throw new FileUploadFailException(fileName+"文件大小不能大于" + fileMaxSize + "K");
                	}
                
                	
                	UploadFile uploadfile = new UploadFile(fileName, uploadedFile, contentTypes,
                			fieldName);
                	uploadFileList.add(uploadfile);
				}
            }
        }
        return uploadFileList;
    }
    
    /**
     * 
     * @param fileFormats 支持的文件格式
     * @param fileMaxSize 以K为单位 0表示不限制大小
     * @param fileCountSize 以K为单位 0表示不限制大小
     * @throws FileUploadFailException
     * @return
     */
    public static List<UploadFile> handleFiles(String[] fileFormats, int fileMaxSize, int fileCountSize)
    		throws FileUploadFailException {
    	List<UploadFile> uploadFileList = new ArrayList<UploadFile>();
    	HttpServletRequest request = ServletActionContext.getRequest();
    	if (request instanceof MultiPartRequestWrapper) {
    		MultiPartRequestWrapper requestWrapper = (MultiPartRequestWrapper) request;
    		Enumeration<String> e = requestWrapper.getFileParameterNames();
    		long countSize = 0;
    		while (e.hasMoreElements()) {
    			String fieldName = e.nextElement();
    			File[] uploadedFiles = ((File[]) requestWrapper.getFiles(fieldName));
    			for (int i = 0; i < uploadedFiles.length; i++) {
    				File uploadedFile =  uploadedFiles[i];
    				//不能上传空文档
    				String fileName = requestWrapper.getFileNames(fieldName)[i];
    				if(uploadedFile.length() == 0){
    					throw new FileUploadFailException(fileName+"文件大小为空!");
    				}
    				//vediocapture照片采集 特殊处理
    				if(("unused").endsWith(fileName))
    					fileName="vediocapture.bmp";
    				String contentTypes = requestWrapper.getContentTypes(fieldName)[i];
    				
    				String fileExt = net.zdsoft.keel.util.FileUtils.getExtension(fileName);
    				if (org.apache.commons.lang.ArrayUtils.isNotEmpty(fileFormats) && !ArrayUtils.contains(fileFormats, fileExt)) {
    					throw new FileUploadFailException(fileName+"文件格式不对，系统只支持"
    							+ ArrayUtils.toString(fileFormats, "、") + "格式");
    				}
    				countSize = countSize + uploadedFile.length();
    				if (fileMaxSize > 0 && uploadedFile.length() / 1024 > fileMaxSize) {
    					throw new FileUploadFailException(fileName+"文件大小不能大于" + fileMaxSize + "K");
    				}
    				
    				if (fileCountSize > 0 && countSize / 1024 > fileCountSize) {
    					throw new FileUploadFailException("文件总大小不能大于" + fileCountSize + "K");
    				}
    				
    				UploadFile uploadfile = new UploadFile(fileName, uploadedFile, contentTypes,
    						fieldName);
    				uploadFileList.add(uploadfile);
    			}
    		}
    	}
    	return uploadFileList;
    }

    /**
     * 
     * @param storageFileEntity
     * @return
     * @throws IOException
     */
    public static File getFileLocalPath(StorageFileEntity storageFileEntity) throws IOException {
        return getFileLocalPath(storageFileEntity.getDirPath(), storageFileEntity.getFilePath(),
                null);
    }

    /**
     * 获取附件文件，位于服务器绝对路径
     * 
     * @param dirPath
     * @param filePath
     * @param fileName
     * @return
     * @throws IOException
     */
    private static File getFileLocalPath(String dirPath, String filePath, String fileName)
            throws IOException {
    	if (null == fileName) {
            String fullDir = dirPath + File.separator + filePath;
            File file = new File(fullDir);
            return file;
        } else {
            String fullDir = dirPath + File.separator + filePath;
            File file = new File(fullDir);
            file.mkdirs();

            if (fileName != null) {
                file = new File(fullDir, fileName);
            }

            return file;
        }
    }

    /**
     * 为了处理当文件上传失败时，与数据库中记录不一致的情况
     * 
     * @param storageFileEntity
     * @param filePath
     * @param fileName
     * @param uploadedfile
     * @param isDeleteUploadedFile 是否删除上传文件
     * 
     * @return
     * @throws FileUploadFailException 如果文件上传失败则抛出该异常
     */
    public static boolean storeFile(StorageFileEntity storageFileEntity, String filePath,
            String fileName, UploadFile uploadedfile, boolean isDeleteUploadedFile)
            throws FileUploadFailException {

        String dirPath = storageFileEntity.getDirPath();

        if (uploadedfile == null) {
            throw new IllegalArgumentException("上传文件为空!");
        }
        if(uploadedfile.getFileSize() == 0){
        	throw new IllegalArgumentException("上传文件大小为空!");
        }

        try {
            File file = getFileLocalPath(dirPath, filePath, fileName);
            File srcFile = uploadedfile.getFile();
            if (srcFile != null) {
                if (storageFileEntity instanceof PhotoEntity) {
                    PhotoEntity instance = (PhotoEntity) storageFileEntity;
                    int photoWidth = instance.getPhotoWidth();
                    int photoHeight = instance.getPhotoHeight();

                    // 把原始的保存在临时文件夹下的临时文件，保存为指定目标地址、指定压缩比例的图片
                    ImageUtils.changeOppositeSize(srcFile.getAbsolutePath(),
                            file.getAbsolutePath(), photoWidth, photoHeight);
                } else {
                    FileUtils.copyFile(srcFile, file);
                }
            }

            if (log.isInfoEnabled()) {
                log.info("文件保存操作 - 临时文件 [" + uploadedfile.getFile().getAbsolutePath()
                        + "] , 目标文件 [" + file.getPath() + "]");
            }

            // 删除临时文件
            if (isDeleteUploadedFile) {
                uploadedfile.getFile().delete();
            }
        } catch (Exception e) {
            // 在该处捕获IOException将其封装为UnchekedException向service层抛出，表示文件上传失败
            throw new FileUploadFailException("由于网络或其他原因导致文件上传失败");
        }

        return true;
    }
    
    public static String getPictureUrl(String dirId, String filePath) {
        String showPicUrl = "";

        // Server server =
        // serverService.getServerByServerCode(System.getProperty("eis.run.subsystem",
        // "eis_basedata"));
        // if(server != null) {
        // String url = server.getUrl();
        String url = BootstrapManager.getBaseUrl();
        showPicUrl = url + "/common/open/base/showPicture.action?dirId=" + dirId + "&filePath="
                + filePath;
        // }
        return showPicUrl;
    }
}
