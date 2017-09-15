/*
 * Class:   FileUploadUtils.java
 * Author:  Sophie Dong
 * Copyright (c) 2006 winupon Networks, Inc. All rights reserved.
 * 
 * Last modified & Comments:
 * <BR>$FileUploadUtils.java$
 * <BR>Revision 1.00  2008-1-15  下午01:43:32  dongxx
 * <BR>first version
 * <BR>
 */
package net.zdsoft.eis.base.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.dispatcher.multipart.MultiPartRequestWrapper;

import net.zdsoft.keelcnet.action.UploadUtils.FileUploadException;
import net.zdsoft.keelcnet.config.BootstrapManager;
import net.zdsoft.leadin.util.UUIDGenerator;

/**
 * 导入文件上传
 * 
 * @author dongxx
 * @version 1.0
 */
public class FileUploadUtils {

	private static final String DOT = ".";
	
	public static final String STORE_PATH = File.separator  + "import" + File.separator;
		

	/**
	 * 处理上传的导入文件
	 * 
	 * @param request 文件上传的<tt>request</tt>
	 * @param postfix 上传文件后缀控制，不为空，则必须和这个后缀一致
	 * @param path 要保存的路径，只要到文件夹的路径即可，后面不带/
	 * @param fileName 要保存的文件名，不带后缀和路径
	 * @param fieldName 页面上的input的id，主要用于多个上传文件的时候，进行区分
	 * @param removeOld 服务器上老的文件是否要删除
	 * @return 包含上传文件保存在服务器上的文件名
	 * @throws FileUploadException
	 */
	public static List<String> processImportFile(MultiPartRequestWrapper request, String postfix, String path, String fileName, String fieldName, boolean removeOld) throws FileUploadException {
		List<String> fileList = new ArrayList<String>();
		Enumeration<String> e = request.getFileParameterNames(); 
//		if (!e.hasMoreElements()) {
//			throw new FileUploadException("所选择的文件无效，请确认文件是否存在！");
//		}
		if (removeOld){
			String tmpPath = "";
			if (StringUtils.isNotBlank(path)){
				tmpPath = BootstrapManager.getStoreHome() + File.separator + path + File.separator;
			}
			else{
				tmpPath = BootstrapManager.getStoreHome() + File.separator + FileUploadUtils.STORE_PATH;
			}
			File file = new File(tmpPath);
			File[] fs = file.listFiles();
			if(!ArrayUtils.isEmpty(fs))
				for(File f : fs){
					try {
						FileUtils.forceDelete(f);
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
		}
		// 有上传文件
		while (e.hasMoreElements()) {
			String fieldName_ = String.valueOf(e.nextElement());// 得到文件名
			if (StringUtils.isNotBlank(fieldName))
				if (!fieldName.equalsIgnoreCase(fieldName_))
					continue;
			File uploadedFile = (((File[]) request.getFiles(fieldName))[0]);// 取得上传文件
			if(uploadedFile.length()<=0){
				throw new FileUploadException("上传文件失败！");
			}
			if(uploadedFile.length()>15*1024*1024){
				throw new FileUploadException("上传文件的大小不能超过15MB！");
			}
			String originalFileName = request.getFileNames(fieldName)[0];// 上传文件的真实名
			String suffix = "";
			if (originalFileName.indexOf(DOT) >= 0){
				suffix = originalFileName.substring(originalFileName.lastIndexOf(DOT) + 1);
			}
			if (StringUtils.isNotBlank(postfix) && !postfix.equalsIgnoreCase(suffix)) {
				throw new FileUploadException("上传文件格式不正确，后缀必须是" + postfix);
			}
			String savedfileName;
			if (StringUtils.isBlank(suffix)){
				savedfileName = saveFile(uploadedFile, "", path, fileName);
			}
			else{
				savedfileName = saveFile(uploadedFile, DOT + suffix, path, fileName);
			}
			if (StringUtils.isNotEmpty(savedfileName)) {
				String filename = savedfileName;
				fileList.add(filename);
			} else {
				throw new FileUploadException("处理导入文件出错！");
			}
		}
		return fileList;
	}
		
	
	/**
	 * 保存文件
	 * 
	 * @param file
	 *            要保存的文件
	 * @param suffix
	 *            文件的后缀,需要点(.)号
	 * @param savePath 要保存的文件路径，后面不带/，如errorData
	 * @return 保存后的文件名,如果没有保存成功,则返回<code>StringUtils.EMPTY</code>
	 */
	private static String saveFile(File file, String suffix, String savePath, String fileName) {
		
		String savedFileName;
		if (StringUtils.isBlank(fileName)){
			savedFileName = UUIDGenerator.getUUID() + suffix;
		}
		else{
			savedFileName = fileName + suffix;
		}
		byte[] buffer = new byte[2048];
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
		String path;
		if (StringUtils.isNotBlank(savePath)){
			path = BootstrapManager.getStoreHome() + File.separator + savePath + File.separator;
		}
		else{
			path = BootstrapManager.getStoreHome() + FileUploadUtils.STORE_PATH;
		}
		try {
			bis = new BufferedInputStream(new FileInputStream(file));
			File dir = new File(path);
			if(!dir.exists()) {
				FileUtils.forceMkdir(dir);
			}				
			bos = new BufferedOutputStream(
					new FileOutputStream(path + savedFileName));
			while (true) {
				int length = bis.read(buffer);
				if (length == -1)
					break;
				bos.write(buffer, 0, length);
			}
			bos.close();
			bis.close();
		} catch (IOException e) { 
			return StringUtils.EMPTY;
		}
		return savedFileName;
	}
	
	

}
