package net.zdsoft.eis.base.converter.service;

import net.zdsoft.eis.base.converter.entity.ConverterFile;

public interface WindowsConverterFileService {
    /**
     * 转换并截图
     * 
     * @param filePath 文件路径
     * @param width 高度
     * @param height 宽度
     * @throws Exception
     */
    public boolean conver(ConverterFile task) ;
}
