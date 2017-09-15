package net.zdsoft.eis.base.data.dao;

import java.util.List;

import net.zdsoft.eis.base.common.entity.ColsDisplay;

/**
 * @author yanb
 * 
 */
public interface BaseColsDisplayDao {
    /**
     * 增加
     * 
     * @param colsDisplay
     */
    public void insertColsDisplay(ColsDisplay colsDisplay);

    /**
     * 删除
     * 
     * @param colsDisplayIds
     */
    public void deleteColsDisplay(String[] colsDisplayIds);

    /**
     * 更新
     * 
     * @param colsDisplay
     */
    public void updateColsDisplay(ColsDisplay colsDisplay);
    
    /**
     * 批量更新
     *
     *@author "yangk"
     * Jul 5, 2010 8:28:53 PM
     * @param colsDisplayList
     */
    public void batchUpdateColsDisplay(List<ColsDisplay> colsDisplayList);
    
    /**
     * 批量插入
     *
     *@author "yangk"
     * Jul 5, 2010 8:33:16 PM
     * @param colsDisplayList
     */
    public void batchInsertColsDisplay(List<ColsDisplay> colsDisplayList);
}
