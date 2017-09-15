/* 
 * @(#)BaseUnitService.java    Created on Nov 17, 2009
 * Copyright (c) 2009 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.eis.base.data.service;

import java.util.List;
import java.util.Map;

import net.zdsoft.eis.base.common.entity.User;
import net.zdsoft.eis.base.common.service.UnitService;
import net.zdsoft.eis.base.data.entity.BaseUnit;
import net.zdsoft.eis.base.sync.EventSourceType;
import net.zdsoft.keel.util.Pagination;
import net.zdsoft.leadin.tree.WebTree;

/**
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: Nov 17, 2009 4:15:43 PM $
 */
public interface BaseUnitService extends UnitService {
    /**
     * 保存unit
     * 
     * @param unit
     */
    public void insertUnit(BaseUnit unit);
    
    /**
     * 保存来自Mq的unit
     * 
     * @param unit
     */
    public void addUnitFromMq(BaseUnit unit);

    public void registerTopUnitFromMq(BaseUnit unit,User user) throws Exception ;
    /**
     * 更新单位信息
     * 
     * @param unit
     */
    public void updateUnit(BaseUnit unit);
    
    /**
     * 更新单位信息 FROMMq
     * @param unit
     */
    public void updateUnitFromMq(BaseUnit unit);

    /**
     * 保存单位信息（为运营平台新增单位的时候调用，基本和action中的新增单位功能相同，加了一些判断）
     * 
     * @param unitDto
     * @param userDto
     * @throws Exception
     */
    public void saveUnit(BaseUnit unitDto, User userDto) throws Exception;
    
    
    /**
     * 保存单位信息（为部署工具新增单位的时候调用）
     * 因为部署工具初始化单位时，不能初始化用户(用户初始化时需要调用passport,而因为
     * 顶级单位没有初始化,passport这时是不可用的)
     */
    public void saveUnitWithoutUser(BaseUnit unitDto) throws Exception ;
    
    /**
     * 保存单位管理员
     */
    public void saveAdminUser(String unitid , User userDto) throws Exception;

    /**
     * 检查unitId单位是否存在以及状态是否正常
     * 
     * @param unitId
     * @return
     */
    public boolean checkUnitRight(String unitId);

    /**
     * 根据上级单位parentid,生成Unionid
     * 
     * @param parentid
     * @param unitClass
     * @return
     */
    public String createUnionid(String parentid, Integer unitClass);

    /**
     * 根据上级单位id,生成非教育局单位的unionid
     * 
     * @param parentid
     * @return
     */
    public String createSpecialUnionid(String parentid);

    /**
     * 保存UnitDto和该unit管理员userDto信息，若是学校则同时新增关联职工和所在部门
     * 
     * @param unitDto
     * @param userDto
     * @return unitid
     */
    public String saveUnitWithUser(BaseUnit unitDto, User userDto) throws Exception;

    /**
     * 根据传入的unit数据，保存unit信息，并同时更新该unit管理员用户
     * 
     * @param unitDto
     * @param userDto
     * @return
     */
    public void updateUnitWithUser(BaseUnit unitDto, User userDto) throws Exception;

    /**
     * 新增或修改顶级单位
     * 
     * @param unitDto
     * @param userDto
     * @param hasUsedSerial 原来顶级单位的注册码是否有效
     * @return
     */
    public boolean saveOrUpdateTopUnit(BaseUnit unitDto, User userDto, boolean hasUsedSerial)
            throws Exception;

    /**
     * 更新单位短信余额
     * 
     * @param unitId
     * @param balance
     */
    public void updateUnitBalance(String unitId, int balance);

    /**
     * 更新单位统一编号
     * 
     * @param uu
     */
    public void updateUnitUnionId(String[][] uu);

    /**
     * 修改unitids单位状态为mark
     * 
     * @param unitIds
     * @param mark
     */
    public void updateUnitMark(String[] unitIds, Integer mark);

    /**
     * 删除单位
     * 
     * @param unitDto
     */
    public void deleteUnit(String[] arrayIds);

    /**
     * 删除单位相关报送数据
     * 
     * @param unitId
     * @return
     */
    public boolean deleteUnitTransmitInfo(String unitId);

    /**
     * 删除单位，主要用于接收MQ消息来删除本地数据
     * 
     * @param unit
     */
    public void deleteUnit(String unitId, EventSourceType eventSource);

    /**
     * 根据单位id得到该单位及下级单位树 ids的单位将置选中状态
     * 
     * @param unitId
     * @param ids
     * @param rootname
     * @return
     */
    public WebTree getUnitTree(String unitId, String[] ids, String rootName);

    /**
     * 根据单位id得到该单位及下级单位树并附加单位状态 ids的单位将置选中状态
     * 
     * @param unitId
     * @param ids
     * @param type 1 表示是推荐的页面，0 表示是审核的页面
     * @param rootname
     * @return
     */
    public WebTree getUnitTreeWithMark(String unitId, String[] ids, String rootName, int type);

    /**
     * 统计全平台授权类型authorized单位数量
     * 
     * @param authorized
     * @return
     */
    public Integer getCountAllUnitByAuthorized(Integer authorized);

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
     * 获取统一编号统计数量
     * 
     * @param unionIds
     * @return
     */
    public Map<String, Integer> getCountsByUnionIds(String[] unionIds);

    /**
     * 根据下属单位id取本单位和所有上级单位id
     * 
     * @param unionid 单位编号
     * @param searchName 查询名称
     * 
     * @return
     */
    public List<BaseUnit> getUnderlingBaseUnits(String unionid, String searchName, Pagination page);

    /**
     * 取单位信息
     * 
     * @param unitId
     * @return
     */
    public BaseUnit getBaseUnit(String unitId);

    /**
     * 根据unionid得到unit
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
    public List<BaseUnit> getBaseUnitsByUnionCodeUnitType(String unionId, int unitClass,
            int unitType);

    /**
     * 所有下属单位列表
     * 
     * @param unitId
     * @param self 是否包括本单位
     * @return
     */
    public List<BaseUnit> getAllBaseUnits(String unitId, boolean self);

    /**
     * 根据单位id，获取单位列表
     * 
     * @param unitIds
     * @return
     */
    public List<BaseUnit> getBaseUnits(String[] unitIds);
    
    public void createUnionidFromMq(BaseUnit unit);

    /**
     * 返回当前的单位数量
     */
    public int getAllUnitCount() ;
    
    /**
     * 从缓存中获取单位unionid
     * @param parentid
     * @param unitClass
     * @return
     * @author huy
     * @date 2014-10-31下午03:39:33
     */
    public String getUnionidFromCache(BaseUnit unitParent, Integer unitClass);
    
    /**
     * 根据学校类型获取学段
     * @param schoolType
     * @return
     * @author huy
     * @date 2014-11-4下午01:59:38
     */
    public String getSections(String schoolType);
    
    public void updateUnitCache(String destParentId, BaseUnit unit);
    
    /**
     * 为单位更新办学性质信息
     * @param unitId
     * @param runSchType
     */
    public void updateRunSchType(String unitId, String runSchType);
}
