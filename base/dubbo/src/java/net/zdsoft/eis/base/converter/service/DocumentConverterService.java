package net.zdsoft.eis.base.converter.service;

/**
 * 文档转换
 * 
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: Feb 10, 2012 1:12:55 PM $
 */
public interface DocumentConverterService {
    /**
     * 转换并截图
     * 
     * @param filePath 文件路径
     * @param width 高度
     * @param height 宽度
     * @throws Exception
     */
    public void conver(String filePath, boolean isAddMark, int width, int height) throws Exception;

    /**
     * 将指定pdf文件的首页转换为指定路径的缩略图
     * 
     * @param filepath 原文件路径，例如d:/test.pdf
     * @param imagepath 图片生成路径，例如 d:/test-1.jpg
     * @param zoom 缩略图显示倍数，1表示不缩放，0.3则缩小到30%
     */
    public void convertPDF2JPG(String filepath, String imagepath, float zoom) throws Exception;

    /**
     * 转换为pdf
     * 
     * @param input
     * @param output
     * @throws Exception
     */
    public void convert2PDF(String input, String output) throws Exception;

    /**
     * PDF转SWF工具
     * 
     * @param sourcePath
     * @param destPath
     * @return
     * @throws Exception
     */
    public int convertPDF2SWF(String sourcePath, String destPath, String args) throws Exception;}
