/* 
 * @(#)BaseUnitDao.java    Created on Nov 18, 2009
 * Copyright (c) 2009 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.eis.base.data.dao;

import java.util.List;
import java.util.Map;


import net.zdsoft.eis.base.data.entity.BaseUnit;
import net.zdsoft.eis.base.sync.EventSourceType;
import net.zdsoft.keel.util.Pagination;

/**
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: Nov 18, 2009 3:32:36 PM $
 */
public interface BaseUnitDao {
	/**
	 * 获取单位Map key为单位名称
	 * @param unitNames
	 * @return
	 */
	public Map<String, BaseUnit> getUnitMapByUnitName(String[] unitNames);
    /**
     * 保存unit
     * 
     * @param unit
     */
    public void insertUnit(BaseUnit unit);
    
    /**
     * 更新单位信息
     * 
     * @param unit
     */
    public void updateUnit(BaseUnit unit);
    
    /**
     * 删除单位(仅将isdeleted设置为1，而不是真正删除)
     * @param arrayIds
     * @param eventSource 用于拦截
     */
    public void deleteUnit(String[] arrayIds, EventSourceType eventSource);
    
    /**
     * 更新单位状态置为mark
     * 
     * @param unitIds
     * @param mark
     */
    public void updateUnitMark(String[] unitIds, Integer mark);

    /**
     * 更新单位短信余额
     * 
     * @param unitId
     * @param balance
     */
    public void updateUnitBalance(String unitId, int balance);
    
    /**
     * 执行sql
     * @param sqlStr
     * @param params
     */
    public void excuteSql(String sqlStr, Object[] params);
    
    
    /**
     * 取可用的下属单位的编号
     * 
     * @param parentid
     * @param unitclass
     * @param lenth 
     * @return
     */
    public String getAvaUnionId(String parentid, int unitclass, int length);
    
    /**
     * 取下属单位的统一编号最大流水号
     * 
     * @param parentid
     * @param parentUnionId TODO
     * @param unitclass
     * @param lenth 
     * @return 流水号字符串
     */
    public String getAvaUnionId(String parentid, String parentUnionId, int unitclass, int length);
    
    /**
     * 取下属单位的统一编号最大值,除unittype类型单位外
     * 
     * @param parentid
     * @param unitclass
     * @param unittype
     * @return
     */
    public String getAvaUnionIdExceptType(String parentid, int unitclass, int unittype);
    
    /**
     * 返回单位名称为Unitname的单位数量
     * 
     * @param unitName
     * @return
     */
    public Integer getCountUnitByName(String unitName);
    
    /**
     * 返回unionId单位数量
     * 
     * @param unionId
     * @return
     */
    public Integer getCountUnionId(String unionId);
    
    /**
     * 统计全平台报送类型位authorized单位数量
     * 
     * @param authorized
     * @return
     */
    public Integer getCountAllUnitByAuthorized(Integer authorized);
    
    /**
     * 获取统一编号统计数量
     * 
     * @param unionIds
     * @return
     */
    public Map<String, Integer> getCountsByUnionIds(String[] unionIds);
    
    /**
     * 取出在用单位的下属单位
     * 
     * @param unionid 单位编号
     * @param unitName 查询的单位名称
     * @param page
     * @return
     */
    public List<BaseUnit> getUnderlingUnits(String unionid, String unitName, Pagination page);
    
    /**
     * 取单位信息
     * @param unitId
     * @return
     */
    public BaseUnit getBaseUnit(String unitId);
    
    /**
     * 根据unionId取得unit
     * 
     * @param unionId
     * @return
     */
    public BaseUnit getBaseUnitByUnionId(String unionId);
    
    /**
     * 批量获取unit
     * 
     * @param unitIds
     * @return map<unitId,unit>
     */
    public Map<String, BaseUnit> getBaseUnitMap(String[] unitIds);
    
    /**
     * 所有下属单位
     * 
     * @param unionId 单位编号
     * @param unitClass 单位分类
     * @param unitType 单位类型(不包含)
     * @return
     */
    public List<BaseUnit> getBaseUnitsByUnionCodeUnitType(String unionId, int unitClass, int unitType);
    
    /**
     * 所有下属单位
     * 
     * @param unionId
     * @return
     */
    public List<BaseUnit> getUnitsByUnionCode(String unionId);
    
    /**
     * 根据单位id，获取单位列表
     * 
     * @param unitIds
     * @return
     */
    public List<BaseUnit> getBaseUnits(String[] unitIds);
    
    /**
     * 返回所有单位
     * 
     * @return
     */
    public List<BaseUnit> getBaseUnits();

    /**
     * 返回当前的单位数量
     */
    public int getAllUnitCount() ;
    
    /**
     * 更新办学性质信息
     * @param unitId
     * @param runSchType
     */
    public void updateRunSchType(String unitId, String runSchType);
}
