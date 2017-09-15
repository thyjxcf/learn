package net.zdsoft.eis.base.common.dao;

import java.util.List;
import java.util.Map;

import net.zdsoft.eis.base.common.entity.Unit;
import net.zdsoft.keel.util.Pagination;

public interface UnitDao {

    // =====================单个单位=============================
    /**
     * 得到顶级教育局
     * 
     * @return
     */
    public Unit getTopEdu();

    /**
     * 根据单位ID，取得单位信息
     * 
     * @param unitId
     * @return
     */
    public Unit getUnit(String unitId);

    /**
     * 根据unionId取得unit
     * 
     * @param unionId
     * @return
     */
    public Unit getUnitByUnionId(String unionId);

    /**
     * 根据单位名称返回单位
     * 
     * @param unitName
     * @return
     */
    public Unit getUnitByName(String unitName);
    
    /**
     * 根据etohSchoolId得到Unit
     * 
     * @param etohSchoolId
     * @return 如果不存在则返回null
     */
    public Unit getUnitByEtohSchoolId(String etohSchoolId);
    
    // =====================一般查询=============================
    /**
     * 返回所有单位
     * 
     * @return
     */
    public List<Unit> getUnits();
    
    /**
     * 返回所有单位
     * 
     * @return
     */
    public Map<String, Unit> getUnitMap();

    /**
     * 单位列表
     * 
     * @param state 状态
     * @return
     */
    public List<Unit> getUnits(int state);

    /**
     * 单位列表
     * 
     * @param state
     * @param unitClass
     * @return
     */
    public List<Unit> getUnits(int state, int unitClass);

    /**
     * 单位列表
     * 
     * @param state
     * @param useType 使用类型
     * @return
     */
    public List<Unit> getUnitsByUseType(int state, int useType);

    /**
     * 批量获取unit
     * 
     * @param ids
     * @return
     */
    public List<Unit> getUnits(String[] ids);
    
    
    /**
     * 批量获取unit 包括删除了的
     * @param ids
     * @return
     */
	public List<Unit> getUnitsWithDel(String[] ids);
	
	/**
	 * 批量获取unit的map 包括删除了的
	 * @param ids
	 * @return
	 */
	public Map<String, Unit> getUnitsMapWithDel(String[] ids);

    // =====================直属单位=============================
    /**
     * 直属单位
     * 
     * @param parentId
     * @return
     */
    public List<Unit> getUnderlingUnits(String parentId);

    /**
     * 直属单位
     * 
     * @param parentId
     * @param state 
     * @param unitClass 类型 1 教育局 2 学校
     * @return
     */
    public List<Unit> getUnderlingUnits(String parentId, int state, int unitClass);
    
    /**
     * 直属单位
     * 
     * @param parentId
     * @param state 
     * @param unitClass 类型 1 教育局 2 学校
     * @param unitType
     * @return
     */
    public List<Unit> getUnderlingUnits(String parentId, int state,int unitClass,int unitType);

    /**
     * 得到相同parentid的单位列表，并且可以指定除外的单位，和是否含远程注册单位
     * 
     * @param parentId
     * @param exceptId 除外的单位Id
     * @param withRemote 是否包含远程注册单位，true:包含；false:不包含
     * @return
     */
    public List<Unit> getUnderlingUnits(String parentId, String exceptId, boolean withRemote);
    
    /**
     * 东莞定制获取学校单位列表
     * @param parentId
     * @param unitClass
     * @param state
     * @param runSchoolType
     * @param unitUseTypes
     * @param page TODO
     * @return
     */
    public List<Unit> getUnderlingUnits(String parentId, int unitClass,
			int state, String runSchoolType, String[] unitUseTypes, Pagination page);

    // =====================模糊查询单位=============================
    /**
     * 模糊搜索根据unitName得到unit
     * 
     * @param unitName
     * @return
     */
    public List<Unit> getUnitsByFaintness(String unitName);

    /**
     * 模糊搜索根据unitName得到unit
     * 
     * @param unitName
     * @return
     */
    public List<Unit> getUnitsByFaintness(String unitName, int unitClass);
    
    
    /**
     * 按单位名称模糊查询
     * @param searchName
     * @param page
     * @return
     */
	public List<Unit> getUnitsBySearchName(String searchName, Pagination page);

    
    // =====================region所有下属单位=============================
    /**
     * 根据行政区划码得到单位
     * 
     * @param region 行政区划码（模糊查询）
     * @param state
     * @return
     */
    public List<Unit> getUnitsByRegion(String region, int state);

    /**
     * 根据传入的区域码检索出该区域下的所有单位数
     * 
     * @param region 行政区划码（模糊查询）
     * @param state
     * @return
     */
    public int getCountByRegion(String region, int state);

    
    // =====================union_code所有下属单位==========================
    /**
     * 所有下属单位
     * 
     * @param unionId
     * @return
     */
    public List<Unit> getUnitsByUnionCode(String unionId);
    
    /**
     * 所有下属单位
     * 
     * @param unionId
     * @param state 
     * @param exceptId 不包括id为exceptId单位
     * @return
     */
    public List<Unit> getUnitsByUnionCode(String unionId, int state, String exceptId);
    
    /**
     * 获取下属单位Map根据单位使用类别分类
     * @param unionId
     * @param state
     * @param exceptId
     * @return
     */
    public Map<String, Integer> getUnderUnitMapByUnitUseType(String unionId, int state, String exceptId);
    
    /**
     * 所有下属单位
     * 
     * @param unionId
     * @param state 
     * @param unitClass
     * @return
     */
    public List<Unit> getUnitsByUnionCode(String unionId, int state, int unitClass);
    
    /**
     * 所有下属单位id
     * @param unionId
     * @param state
     * @param unitClass
     * @return
     */
    public List<String> getUnitIdsByUnionCode(String unionId, int state, int unitClass);

    /**
     * 所有下属单位
     * 
     * @param unionId 单位编号
     * @param unitClass 单位分类
     * @param unitType 单位类型(不包含)
     * @return
     */
    public List<Unit> getUnitsByUnionCodeUnitType(String unionId, int unitClass, int unitType);
    
    public List<Unit> getUnitsByUnionCodeUnitType(String unionId, int state,int unitClass, int unitType);
    
    /**
     * 所有下属单位
     * 
     * @param unionId 单位编号
     * @param state 状态
     *  @param unitClass 单位分类
     * @param unitType 单位类型(不包含)
     * @return
     */
    public List<Unit> getUnitsList(String unionId, int state,
			int unitClass, int unitType);

    
    // =====================Map=============================
    /**
     * 批量获取unit
     * 
     * @param unitIds
     * @return key=unitId
     */
    public Map<String, Unit> getUnitMap(String[] unitIds);

    /**
     * 所有下属单位
     * 
     * @param unionId
     * @param state
     * @param unitClass
     * @return
     */
    public Map<String, Unit> getUnitMap(String unionId, int state, int unitClass);
    /**
	 * 获取单位Map key为单位名称
	 * @param unitNames
	 * @return
	 */
	public Map<String, Unit> getUnitMapByUnitName(String[] unitNames);
 /**
     * 查下属单位范围内更加条件检索
     * @param unitName
     * @param unionId
     * @param state
     * @param unitClass
     * @return
     */
    public List<Unit> getUnitsByNameAndUnionCode(String unitName, String unionId, int state,
			int unitClass);
    
    /**
     * 获取下属单位list 
     * @param unionid 单位编号
     * @param unitName 查询的单位名称
     * @param page
     * @return
     */
    public List<Unit> getUnderlingUnits(String unionid, String unitName, Pagination page);
    
    /**
     * 分页获取下属单位列表
     * @param parentId
     * @param unitClass
     * @param page
     * @return
     * @author huy
     * @date 2015-12-21下午02:39:57
     */
    public List<Unit> getUnderlingUnitsByPage(String parentId, int unitClass,int state,
			Pagination page);
    
    public List<Unit> getUnitListBySerialNumber(String sNumber, String eNumber);
}
