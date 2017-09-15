package net.zdsoft.eis.base.common.dao;

import java.util.List;

import net.zdsoft.eis.base.common.entity.ColsDisplay;

public interface ColsDisplayDao {

    /**
     * 根据schGUID、学生教师类型,得到该学校的显示信息设置记录 （是否有某校学生设置记录）
     * 
     * @param unitId
     * @param type
     * @return List
     */
    public List<ColsDisplay> getColsDisplays(String unitId, String type);

    /**
     * 得到要设置的数据项列表，根据某校unitid、type(教师、学生)、colsUse是否显示属性
     * 
     * @param type 教师、学生
     * @param colsUse 是否显示 0 表示不
     * @param unitid 学校id
     * @return List 在选择要使用字段时，不用管‘必选字段’
     *         ----必选(即必须使用)字段在设置保存时，不管原初值是否使用，都设为使用(js中checked)。
     */
    public List<ColsDisplay> getColsDisplays(String unitId, String type, Integer colsUse);
    
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
    public List<ColsDisplay> getColsDisplays(String unitId, String[] type, Integer colsUse);

}
