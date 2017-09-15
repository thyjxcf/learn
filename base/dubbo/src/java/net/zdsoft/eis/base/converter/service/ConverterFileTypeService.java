/* 
 * @(#)ConverterFileTypeService.java    Created on 2014-4-11
 * Copyright (c) 2014 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.eis.base.converter.service;

public interface ConverterFileTypeService {

	/**
	 * 是否为文档文件
	 * 
	 * @param contentType
	 * @return
	 */
	public abstract boolean isDocument(String contentType);

	/**
	 * 是否为视频文件
	 * 
	 * @param contentType
	 * @return
	 */
	public abstract boolean isVideo(String contentType);

	/**
	 * 获取部分扩展名(文档和视频)
	 * 
	 * @return
	 */
	public abstract String[] getExtNames();
	
	/**
	 * 获取所有扩展名
	 * 
	 * @return
	 */
	public String[] getAllExtNames();

	/**
	 * 组装成文件路径
	 * 
	 * @param resourcePath
	 * @param contentType
	 * @return
	 */
	public abstract String buildFilePath(String resourcePath, String contentType);
	
	/**
	 * 是否为图片文件
	 * @param contentType
	 * @return
	 */
	public boolean isPicture(String contentType);
	/**
	 * 判断是否音频文件
	 * @param contentType
	 * @return
	 */
	public boolean isAudio(String contentType);
	/**
	 * 根据类型获取扩展名数组[type=1:文档文件，2：视频，3，图片，4，音频]
	 * @param type
	 * @return
	 */
	public String[] getConTypeExtNames(int type);
}