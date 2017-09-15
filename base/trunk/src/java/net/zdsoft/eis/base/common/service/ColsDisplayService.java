package net.zdsoft.eis.base.common.service;

import java.util.List;

import net.zdsoft.eis.base.common.entity.ColsDisplay;

public interface ColsDisplayService {

    /**
     * 根据schGUID、学生教师类型,得到该学校的显示信息设置记录 （是否有某校学生设置记录）
     * 
     * @param unitId
     * @param type
     * @return List
     */
    public List<ColsDisplay> getColsDisplays(String unitId, String type);
    
    /**
     * 根据schGUID、学生教师类型,得到该学校的显示信息设置记录 （是否有某校学生设置记录）
     * 
     * @param unitId
     * @param type
     * @return List
     */
    public List<ColsDisplay> getColsDisplays(String unitId, String[] type);

    /**
     * 得到要设置的数据项列表，根据某校unitid、type(教师、学生)、use是否显示属性。如果为空时就取默认的字段
     * 
     * @param type 教师、学生
     * @param colsUse 是否显示 0 表示不
     * @param unitid 学校id
     * 
     * @return List
     */
    public List<ColsDisplay> getColsDisplays(String unitId, String type, Integer colsUse);
    
    /**
     * 得到要设置的数据项列表，根据某校unitid、type(教师、学生)、use是否显示属性。如果为空时就取默认的字段
     * 
     * @param type 教师、学生
     * @param colsUse 是否显示 0 表示不
     * @param unitid 学校id
     * 
     * @return List
     */
    public List<ColsDisplay> getColsDisplays(String unitId, String[] type, Integer colsUse);

    /**
     * 得到要设置的数据项列表，根据某校unitid、type(教师、学生)、use是否显示属性。如果为空时不取默认的字段
     * 
     * @param type 教师、学生
     * @param colsUse 是否显示 0 表示不
     * @param unitid 学校id
     * 
     * @return List
     */
    public List<ColsDisplay> getColsWithoutDefault(String unitId, String type, Integer colsUse);

}
