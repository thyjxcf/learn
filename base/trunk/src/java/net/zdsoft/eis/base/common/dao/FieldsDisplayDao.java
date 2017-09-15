package net.zdsoft.eis.base.common.dao;

import java.util.List;

import net.zdsoft.eis.base.common.entity.FieldsDisplay;

/**
 * 
 * 
 *@author weixh
 *@since 2013-3-7
 */
public interface FieldsDisplayDao {
	/**
     * 根据schGUID、类型,得到显示信息设置记录
     * 
     * @param unitId
     * @param type
     * @return List
     */
    public List<FieldsDisplay> getColsDisplays(String unitId, String type);

    /**
     * 得到要设置的数据项列表，根据某校unitid、type、colsUse是否显示属性
     * 
     * @param type
     * @param colsUse 是否显示 0表示不
     * @param unitid
     * @return List 在选择要使用字段时，不用管‘必选字段’
     *         ----必选(即必须使用)字段在设置保存时，不管原初值是否使用，都设为使用(js中checked)。
     */
    public List<FieldsDisplay> getColsDisplays(String unitId, String type, Integer colsUse);
    
    /**
     * 取得下级显示
     * @param parentId
     * @param colsUse
     * @return
     */
    public List<FieldsDisplay> getColsDisplays(String parentId, Integer colsUse);

}
