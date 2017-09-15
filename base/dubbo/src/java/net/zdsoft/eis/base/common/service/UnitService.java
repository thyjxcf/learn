package net.zdsoft.eis.base.common.service;

import java.util.List;
import java.util.Map;

import net.zdsoft.eis.base.common.entity.Unit;
import net.zdsoft.keel.util.Pagination;
import net.zdsoft.leadin.cache.CacheManager;

/**
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: Nov 19, 2009 1:54:48 PM $
 */
public interface UnitService extends CacheManager {
   
    // =====================单个单位=============================
    /**
     * 获取顶级教育局
     * 
     * @return
     */
    public Unit getTopEdu();
    
    /**
     * 通过单位ID取得单位信息
     * 
     * @param unitId
     * @return
     */
    public Unit getUnit(String unitId);
    
    /**
     * 通过单位ID取得单位信息，不从缓存获取
     * 
     * @param unitId
     * @return
     */
    public Unit getUnitNoFromCache(String unitId);

    /**
     * 根据unionid得到unit
     * 
     * @param unionId
     * @return
     */
    public Unit getUnitByUnionId(String unionId);
    
    /**
     * 根据单位名称获得单位
     * 
     * @param unitName
     * @return
     */
    public Unit getUnitByName(String unitName);

    /**
     * 根据一个给定行政级别差,取得高于指定单位该级差的单位对象.<br>
     * 如果不存在或者级差为负数,则返回<tt>null</tt>.
     * 
     * @param unitId 基础单位id
     * @param levelOffset 行政级别差
     * @return 高于指定单位一定行政级别差的单位对象
     */
    public Unit getUpperUnit(String unitId, int levelOffset);

    /**
     * 根据etohSchoolId得到UnitDto
     * 
     * @param etohSchoolId
     * @return 如果不存在则返回null
     */
    public Unit getUnitByEtohSchoolId(String etohSchoolId);
    
    // =====================一般查询=============================    
    /**
     * 所有单位
     * 
     * @return
     */
    public List<Unit> getUnits();
    
    /**
     * 状态正常的单位
     * 
     * @return
     */
    public List<Unit> getNormalUnits();

    /**
     * 所有状态正常的教育局单位
     * 
     * @return List
     */
    public List<Unit> getNormalEdus();

    /**
     * 所有状态正常的学校单位
     * 
     * @return
     */
    public List<Unit> getNormalSchs();
    
    /**
     * 状态正常的本地单位
     * 
     * @return
     */
    public List<Unit> getNormalLocalUnits();
    
    /**
     * 根据单位id，获取单位列表
     * 
     * @param unitIds
     * @return
     */
    public List<Unit> getUnits(String[] unitIds);
    
    
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
     * @return
     */
    public List<Unit> getUnderlingUnits(String parentId);
    

    /**
     * 直属单位
     * 
     * @param unitId
     * @param self 是否包括本单位
     * @return
     */
    public List<Unit> getUnderlingUnits(String parentId, boolean self);  

    /**
     * 返回该单位直属的学校
     * 
     * @param parentId
     * @return
     */
    public List<Unit> getUnderlingSchools(String parentId);
    
    /**
     * 直属且状态正常的单位
     * 
     * @param parentId 父结点id
     * @param unitClass 单位分类：学校或教育局
     * @return
     */
    public List<Unit> getUnderlingUnits(String parentId, int unitClass);
    
    /**
     * 直属且状态正常的单位
     * 
     * @param parentId 父结点id
     * @param unitClass 单位分类：学校或教育局
     * @param unitType 单位分类：学校或教育局
     * @return
     */
    public List<Unit> getUnderlingUnits(String parentId, int unitClass,int unitType);
    
    /**
     * 单位列表
     * 
     * @param parentId
     * @param exceptId 除外的单位Id
     * @param page 
     * @return
     */
    public List<Unit> getUnderlingUnits(String parentId, String exceptId);
    
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
    
    
    // =====================unitName模糊查询单位=============================
    /**
     * 模糊搜索根据unitName得到unitList
     * 
     * @param unitName
     * @return
     */
    public List<Unit> getUnitsByFaintness(String unitName);

    /**
     * 模糊搜索根据unitName得到unitList
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

    
    // =====================region模糊查询单位=============================
    /**
     * 根据行政区划，检索单位信息（未审核除外）
     * 
     * @param region
     * @return
     */
    public List<Unit> getUnitsByRegion(String region);


    /**
     * 根据传入的区域码检索出该区域下的所有单位数（未审核除外）
     * 
     * @param region
     * @return
     */
    public int getCountByRegion(String region);
    
    
    // =====================union_code所有下属单位==========================
    /**
     * 检索单位信息
     * 
     * @param unionId
     * @param unitClass
     * @return
     */
    public List<Unit> getUnitsByUnionCode(String unionId, int unitClass);
    
    /**
     * 查下属单位范围内更加条件检索
     * @param unitName
     * @param unionId
     * @param state
     * @param unitClass
     * @return
     */
    public List<Unit> getUnitsByNameAndUnionCode(String unitName, String unionId);
    /**
     * 获取指定单位所有直属和下属的学校
     * 
     * @param unionId
     * @return
     */
    public List<Unit> getSchsByUnionCode(String unionId);
    
    /**
     * 获取指定单位所有直属和下属的学校id
     * @param unionId
     * @return
     */
    public List<String> getSchIdsByUnionCode(String unionId);
    
    /**
     * 所有下属单位
     * 
     * @param unionId 单位编号
     * @param unitClass 单位分类
     * @param unitType 单位类型(不包含)
     * @return
     */
    public List<Unit> getUnitsByUnionCodeUnitType(String unionId, int unitClass, int unitType);
    

    // =====================Map==========================
    /**
     * 批量获取unit
     * 
     * @param unitIds
     * @return map<unitId,unit>
     */
    public Map<String, Unit> getUnitMap(String[] unitIds);
    
    
    /**
     * 状态正常的所有下属学校
     * 
     * @param unionId
     * @return map<unitId,unit>
     */
    public Map<String, Unit> getSchMap(String unionId);   
    
    /**
     * 以parentId为key的Map
     * @return
     */
    public Map<String, List<Unit>> getUnitMapKeyByParentId() ;
    
    // ===============================================      
    /**
     * 所有下属单位列表
     * 
     * @param unitId
     * @param self 是否包括本单位
     * @return
     */
    public List<Unit> getAllUnits(String unitId, boolean self);

    /**
     * 正常状态的所有下属单位列表
     * 
     * @param unitId
     * @param self 是否包括本单位
     * @return
     */
    public List<Unit> getAllNormalUnits(String unitId, boolean self);
    
    /**
     * 获取下属单位Map根据单位使用类别分类
     * @param unitId
     * @param self
     * @return
     */
    public Map<String, Integer> getUnderUnitMapByUnitUseType(String unitId, boolean self);

    /**
     * 正常状态的所有下属学校列表
     * 
     * @param unitId
     * @return
     */
    public List<Unit> getAllSchools(String unitId);

    /**
     * 正常状态的所有下属教育局列表，包括自身
     * 
     * @param unitId
     * @return
     */
    public List<Unit> getAllEdus(String unitId);
    
    /**
     * 正常状态的所有下属教育局列表
     * 
     * @param unitId
     * @param self 是否包括本单位
     * @return
     */
    public List<Unit> getAllEdus(String unitId, boolean self);
    
    /**
     * 根据下属单位id取本单位和所有上级单位id
     * 
     * @param unitId 下属单位id
     * @param self 是否包含自身
     * @return
     */
    public String[] getAllParentUnitIds(String unitId, boolean self);
    
    /**
     * 根据下属单位id取本单位和所有上级单位
     * 
     * @param unitId 下属单位id
     * @param self 是否包含自身
     * @return
     */
    public List<Unit> getAllParentUnits(String unitId, boolean self);

    
    // ====================公文使用===========================    
    /**
     * 根据单位选择范围，得到相对于当前单位的单位列表 （1所有单位、2本单位、3同级单位、4下级单位、5直接上级单位）
     * 
     * @param unit 当前单位
     * @param range 单位选择范围，格式如：1,2,3
     * @return
     */
    public List<Unit> getUnitsByRange(Unit unit, String range);

    /**
     * 根据单位选择范围，得到相对于当前单位的单位列表，并且不包含远程注册单位 （1所有单位、2本单位、3同级单位、4下级单位、5直接上级单位）
     * 说明：用于取上级平台单位列表，只是所有单位、或同级单位、或直接上级单位
     * 
     * @param unitId
     * @param range
     * @return
     */
    public List<Unit> getUnitsByRangeWithoutRemote(String unitId, String range);

    /**
     * 根据单位选择范围，得到相对于当前单位的单位intid字符串，如果是全部单位时返回"ALL_UNIT_INTID"
     * （1所有单位、2本单位、3同级单位、4下级单位、5直接上级单位、6直属单位）
     * 
     * @param unit 当前单位
     * @param range 单位选择范围，格式如：1,2,3
     * @return String 格式如：,12,4,5,6,9,10,；如果是全部单位时返回"ALL_UNIT_INTID"
     */
    public String getUnitIdStringByRange(Unit unit, String range);
    /**
     * 下属单位范围内根据名称查询
     * @param unitName
     * @param unionId
     * @return
     */
    public List<Unit> getSchsByNameAndUnionCode(String unitName,String unionId);
    
    /**
     * 获取下属单位list 
     * @param unionid 单位编号
     * @param searchName 查询名称
     * @return
     */
    public List<Unit> getUnderlingUnits(String unionid, String searchName, Pagination page);

    /**
     * 直属且状态正常的单位分页查询
     * 
     * @param parentId 父结点id
     * @param unitClass 单位分类：学校或教育局
     * @param page
     * @return
     */
    public List<Unit> getUnderlingUnitsByPage(String parentId, int unitClass, Pagination page);

    /**
     * 获取单位字符串，通讯录使用
     * @param unitIds
     * @return
     */
    public String getUnitDetailNamesStr(String[] unitIds);
    
    public List<Unit> getUnitListBySerialNumber(String sNumber, String eNumber);
}
