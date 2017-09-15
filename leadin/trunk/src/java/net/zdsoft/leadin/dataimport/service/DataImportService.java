/* 
 * @(#)DataImportService.java    Created on 2008-10-22
 * Copyright (c) 2008 ZDSoft Networks, Inc. All rights reserved.
 * $Id: DataImportService.java 25035 2008-10-27 11:23:11Z zhaosf $
 */
package net.zdsoft.leadin.dataimport.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import net.zdsoft.keel.action.Reply;
import net.zdsoft.leadin.dataimport.core.ImportObject;
import net.zdsoft.leadin.dataimport.core.ImportObjectNode;
import net.zdsoft.leadin.dataimport.exception.ImportErrorException;
import net.zdsoft.leadin.dataimport.param.DataImportParam;

/**
 * @author zhaosf
 * @version $Revision: 25035 $, $Date: 2008-10-27 19:23:11 +0800 $
 */
public interface DataImportService {
    /**
     * 导入数据
     * 
     * @param param
     * @param reply
     * @throws ImportErrorException
     */
    public void importDatas(DataImportParam param, Reply reply) throws ImportErrorException;

    /**
     * 导出数据
     * 
     * @param importObject
     * @param cols
     * @return
     */
    public List<List<String[]>> exportDatas(ImportObject importObject, String[] cols);

    /**
     * 取动态列
     * 
     * @param param
     * @return
     */
    public List<ImportObjectNode> getDynamicFields(DataImportParam param);
    
    /**
     * 取非微代码下拉列，需在导入xml配置中定义该列类型为"Select";如果希望下拉值是有序的，请设置待选项Map为有序Map
     * @param param
     * @return 返回Map，其中  
     * 	key: xml定义列的define(显示名称)<br/>
     * 	value: 该列待选项Map(key=下拉显示值，value=下拉实际值，通常为id之类)
     * @author zhangkc
     * @date 2013年10月22日 下午2:53:56
     */
    public Map<String, Map<String, String>> getConstraintFields(DataImportParam param);
    
    /**
     * 过滤的中文字段列表
     * 
     * @return
     */
    public Set<String> getFilterDefineFields();
    
    /**
     * 取重新定义的字段结点属性，优先级高于xml文件
     * @param nodeMap xml解析后的结点
     * @param param 结合参数重定义
     * @return 自定义的结点，不需要自定义的结点可以不包含
     */
    public List<ImportObjectNode> getRedefineFields(Map<String, ImportObjectNode> nodeMap, DataImportParam param);
}
