package net.zdsoft.eis.base.converter.util;

import java.io.File;
import java.util.Calendar;

import org.apache.commons.lang.StringUtils;

/**
 * store目录工具类
 * 
 * @author shenl
 * 
 */
public final class StorePathUtil {
	/**
	 * 获取store目录下的文件
	 * 
	 * @param path
	 * @return
	 */
	public static File getStoreFile(String path) {
		if (StringUtils.isEmpty(path)) {
			return null;
		}
		path = removeCache(path);
		File storeFile = new File(path);
		return storeFile;
	}

	/**
	 * 链接地址加入一个随机数
	 * 
	 * @param buffer
	 */
	public static String formatUri(StringBuffer path) {
		return formatUri(path, true);
	}

	/**
	 * 链接地址加入一个随机数
	 * 
	 * @param buffer
	 */
	public static String formatUri(StringBuffer path, Boolean setCache) {
		Calendar calendar = Calendar.getInstance();
		if (setCache) {
			path.append("?_").append(calendar.get(Calendar.MILLISECOND));
		}
		return path.toString();
	}

	/**
	 * 先检查有没有随机参数，如果有先去掉
	 * 
	 * @param path
	 * @return
	 */
	public static String removeCache(String path) {
		if (StringUtils.isEmpty(path)) {
			return path;
		}
		int pix = path.lastIndexOf("?");
		if (pix != -1) {
			path = path.substring(0, pix);
		}
		return path;
	}

	/**
	 * 获取扩展名
	 * 
	 * @param filename
	 * @return
	 */
	public static String getExtensionName(String filename) {
		if ((filename != null) && (filename.length() > 0)) {
			int dot = filename.lastIndexOf('.');
			if ((dot > -1) && (dot < (filename.length() - 1))) {
				return filename.substring(dot + 1).toLowerCase();
			}
		}
		return filename;
	}
	
	
	/**
	 * 根据图片路径得到缩略图或小图片或默认图片
	 * @param picturePath
	 * @param toPictureName 要得到的图片名称
	 * @return
	 */
	public static String getResourcePreviewPath(String resourcePath, String toPictureName){
		if(StringUtils.isEmpty(resourcePath)){
			return resourcePath;
		}
		
		String toPicturePath = resourcePath;
		resourcePath = removeCache(resourcePath);
		
		//取扩展名
		String ext = "jpg";
		
		//将文件名改为缩略文件名
		int pix = resourcePath.lastIndexOf(".");
		if (pix != -1) {
			toPicturePath = resourcePath.substring(0, pix) + "_"+ toPictureName + "." + ext;
		}
		
		//加随机参数
		toPicturePath = formatUri(new StringBuffer(toPicturePath), true);
		
		return toPicturePath;
	}
}
