/* 
 * @(#)VideoConverterService.java    Created on Mar 20, 2012
 * Copyright (c) 2012 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.eis.base.converter.service;

/**
 * 视频转换
 * 
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: Mar 20, 2012 1:23:47 PM $
 */
public interface VideoConverterService {
    /**
     * 转换并截图
     * 
     * @param filePath 文件路径
     * @param width 高度
     * @param height 宽度
     * @throws Exception
     */
    public void conver(String filePath, int width, int height) throws Exception;
}
