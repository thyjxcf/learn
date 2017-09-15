package net.zdsoft.eis.base.common.dao;

import java.util.List;
import java.util.Map;

import net.zdsoft.eis.base.common.entity.Region;

public interface RegionDao {
	/**
	 * 通过行政区名称获取对象
	 * @param regionName 行政区name
	 * @param type 行政区划码类型
	 * @return
	 */
	public List<Region> getRegionsByRegionName (String regionName, String type);
    /**
     * 通过主关键字获取行政区划对象
     * 
     * @param code 行政区CODE
     * @param type 行政区划码类型
     * @return
     */
    public Region getRegion(String code, String type);

    /**
     * 行政区划
     * 
     * @param fullCode
     * @param type 行政区划码类型
     * @return
     */
    public Region getRegionByFullCode(String fullCode, String type);

    /**
     * 取code是两位数的行政区域,取出所有两位数的区域
     * @param type 行政区划码类型
     * 
     * @return
     */
    public List<Region> getSubRegionsBy2(String type);

    /**
     * 取code是四位数的行政区域
     * 
     * @param code 四位数code的头两位
     * @param type 行政区划码类型
     * @return
     */
    public List<Region> getSubRegionsBy4(String code, String type);

    /**
     * 取code是六位数的行政区域
     * 
     * @param code 六位数code的头四位
     * @param type 行政区划码类型
     * @return
     */
    public List<Region> getSubRegionsBy6(String code, String type);

    /**
     * 得到全部行政区域
     * @param type 行政区划码类型
     * 
     * @return
     */
    public List<Region> getRegions(String type);

    /**
     * 取c_code是两位数的行政区域,取出现有单位所有两位数的区域
     * @param type 行政区划码类型
     * 
     * @return
     */
    public List<Region> getSubUnitRegionsBy2(String type);

    /**
     * 取c_code是四位数的现有单位行政区域
     * 
     * @param code 四位数code的头两位
     * @param type 行政区划码类型
     * @return
     */
    public List<Region> getSubUnitRegionsBy4(String code, String type);

    /**
     * 取code是六位数的现有单位行政区域
     * 
     * @param code 六位数code的头四位
     * @param type 行政区划码类型
     * @return
     */
    public List<Region> getSubUnitRegionsBy6(String code, String type);

    /**
     * 根据行政区域编号数组取行政区划信息列表
     * 
     * @param regionCodes
     * @param type 行政区划码类型
     * @return
     */
    public List<Region> getRegionListByCodes(String[] regionCodes, String type);

    /**
     * 根据code 模糊查询出符合条件的区域
     * 
     * @param fullCode
     * @param excludeFullCode 除掉特殊的code
     * @param type 行政区划码类型
     * @return
     */
    public List<Region> getSubRegions(String fullCode, String excludeFullCode, String type);

    /**
     * 得到全部行政区域MAP
     * @param type 行政区划码类型
     * 
     * @return key=regionCode,value=fullName
     */
    public Map<String, String> getRegionMap(String type);

    /**
     * 得到全部行政区域MAP
     * 
     * @return key=fullCode,value=fullName
     */
    public Map<String, String> getRegionFullCodeMap(String type);
    
    /**
     * 得到全部行政区域MAP
     * @param type 行政区划码类型
     * 
     * @return key=fullName,value=fullCode
     */
    public Map<String, String> getRegionNameMap(String type);
    
    /**
     * 获取行政区划，根据名称完全匹配
     * @param fullName
     * @param type
     * @return
     * @author huy
     * @date 2014-9-4下午08:24:12
     */
    public Region getRegionByFullName(String fullName, String type);
}
