package net.zdsoft.eis.base.common.service;

import java.util.List;
import java.util.Map;

import net.zdsoft.eis.base.common.entity.Region;

public interface RegionService {
	/**
	 * 通过行政区名称获取对象，默认业务用行政区划
	 * @param regionName 行政区name
	 * @return
	 */
	public List<Region> getRegionsByRegionName (String regionName);
	/**
	 * 通过行政区名称获取对象，指定行政区划类型
	 * @param regionName 行政区name
	 * @param type 行政区划类型
	 * @return
	 * @author zhangkc
	 * @date 2014年7月23日 下午6:33:08
	 */
	public List<Region> getRegionsByRegionName(String regionName, String type);
	
    /**
     * 取行政区划，默认业务用行政区划
     * 
     * @param code
     * @return
     */
    public Region getRegion(String code);
    /**
     * 取行政区划，指定行政区划类型
     * @param code
     * @param type
     * @return
     */
    public Region getRegion(String code, String type);

    /**
     * 取行政区划，默认业务用行政区划
     * 
     * @param fullCode
     * @return
     */
    public Region getRegionByFullCode(String fullCode);
    /**
     * 取行政区划，指定行政区划类型
     * @param fullCode
     * @param type 
     * @return
     */
    public Region getRegionByFullCode(String fullCode, String type);

    /**
     * 通过主关键字获取行政区的全称,如 浙江/杭州/西湖区，默认业务用行政区划
     * 
     * @param code
     * @return
     */
    public String getFullName(String code);
    
    /**
     * 通过主关键字获取行政区的全称,如 浙江/杭州/西湖区，指定行政区划类型
     * 
     * @param code
     * @return
     */
    public String getFullName(String code,String type);

    /**
     * 通过全代码获取行政区的全称，默认业务用行政区划
     * 
     * @param code
     * @return
     */
    public String getFullNameByFullCode(String fullCode);
    /**
     * 通过全代码获取行政区的全称，指定行政区划类型
     * 
     * @param code
     * @return
     */
    public String getFullNameByFullCode(String fullCode, String type);
    
    /**
     * 根据编码得到行政区名称（去掉*等符号），默认业务用行政区划
     * 
     * @param code
     * @return
     */
    public String getRegionName(String code);
    /**
     * 根据编码得到行政区名称（去掉*等符号），指定行政区划类型
     * 
     * @param code
     * @return
     */
    public String getRegionName(String code, String type);

    /**
     * 根据区域代码取得该区域下属的区域列表，默认业务用行政区划
     * 
     * @param regionCode
     * @return
     */
    public List<Region> getSubRegions(String regionCode);
    
    /**
     * 根据区域代码取得该区域下属的区域列表，指定行政区划类型
     * 
     * @param regionCode
     * @return
     */
    public List<Region> getSubRegions(String regionCode, String type);

    /**
     * 根据六位行政区划取出直接区域，默认业务用行政区划
     * 
     * @param fullCode
     * @return
     */
    public List<Region> getSubRegionsByFullCode(String fullCode);
    /**
     * 根据六位行政区划取出直接区域，指定行政区划类型
     * 
     * @param fullCode
     * @return
     */
    public List<Region> getSubRegionsByFullCode(String fullCode, String type);

    // ====================行政区划树或下拉选择行政区划=========================
    /**
     * 得到全部行政区域list，默认业务用行政区划
     * 
     * @return
     */
    public List<String[]> getRegionCodeNames();
    /**
     * 得到全部行政区域list，指定行政区划类型
     * 
     * @return
     */
    public List<String[]> getRegionCodeNames(String type);
    
    /**
     * 得到全部行政区域list，默认业务用行政区划
     * 
     * @return
     */
    public List<Region> getRegions();
    /**
     * 得到全部行政区域list，指定行政区划类型
     * 
     * @return
     */
    public List<Region> getRegions(String type);

    /**
     * 取code是两位数的行政区域,取出所有两位数的区域，默认业务用行政区划
     * 
     * @return
     */
    public List<Region> getSubRegionsBy2();
    /**
     * 取code是两位数的行政区域,取出所有两位数的区域，指定行政区划类型
     * 
     * @return
     */
    public List<Region> getSubRegionsBy2(String type);

    /**
     * 取code是四位数的行政区域，默认业务用行政区划
     * 
     * @param code 四位数code的头两位
     * @return
     */
    public List<Region> getSubRegionsBy4(String code);
    /**
     * 取code是四位数的行政区域，指定行政区划类型
     * 
     * @param code 四位数code的头两位
     * @return
     */
    public List<Region> getSubRegionsBy4(String code,String type);

    /**
     * 取code是六位数的行政区域，默认业务用行政区划
     * 
     * @param code 六位数code的头四位
     * @return
     */
    public List<Region> getSubRegionsBy6(String code);
    /**
     * 取code是六位数的行政区域，指定行政区划类型
     * 
     * @param code 六位数code的头四位
     * @return
     */
    public List<Region> getSubRegionsBy6(String code,String type);

    // ====================取出有对应单位的行政区划，PrvFlat报表中使用=========================
    /**
     * 取code是两位数的行政区域,取出现有单位所有两位数的区域，默认业务用行政区划
     * 
     * @return
     */
    public List<Region> getSubUnitRegionsBy2();
    /**
     * 取code是两位数的行政区域,取出现有单位所有两位数的区域，指定行政区划类型
     * 
     * @return
     */
    public List<Region> getSubUnitRegionsBy2(String type);

    /**
     * 取code是四位数的现有单位行政区域，默认业务用行政区划
     * 
     * @param code 四位数code的头两位
     * @return
     */
    public List<Region> getSubUnitRegionsBy4(String code);
    /**
     * 取code是四位数的现有单位行政区域，指定行政区划类型
     * 
     * @param code 四位数code的头两位
     * @return
     */
    public List<Region> getSubUnitRegionsBy4(String code,String type);

    /**
     * 取code是六位数的现有单位行政区域，默认业务用行政区划
     * 
     * @param code 六位数code的头四位
     * @return
     */
    public List<Region> getSubUnitRegionsBy6(String code);
    /**
     * 取code是六位数的现有单位行政区域，指定行政区划类型
     * 
     * @param code 六位数code的头四位
     * @return
     */
    public List<Region> getSubUnitRegionsBy6(String code,String type);

    /**
     * 取行政区划，默认业务用行政区划
     * 
     * @param regionCodes
     * @return
     */
    public List<Region> getRegionListByCodes(String[] regionCodes);
    /**
     * 取行政区划，指定行政区划类型
     * 
     * @param regionCodes
     * @return
     */
    public List<Region> getRegionListByCodes(String[] regionCodes,String type);

    // ====================行政区划map=========================
    /**
     * 得到全部行政区域MAP，默认业务用行政区划
     * 
     * @return key=regionCode,value=fullName
     */
    public Map<String, String> getRegionMap();
    /**
     * 得到全部行政区域MAP，指定行政区划类型
     * 
     * @return key=regionCode,value=fullName
     */
    public Map<String, String> getRegionMap(String type);
    
    /**
     * 得到全部行政区域MAP，默认业务用行政区划
     * 
     * @return key=fullCode,value=fullName
     */
    public Map<String, String> getRegionFullCodeMap();
    /**
     * 得到全部行政区域MAP，指定行政区划类型
     * 
     * @return key=fullCode,value=fullName
     */
    public Map<String, String> getRegionFullCodeMap(String type);
    
    /**
     * 得到全部行政区域(名字->full_code)MAP，默认业务用行政区划
     * 
     * @return key=fullName,value=fullCode
     */
    public Map<String, String> getRegionNameMap();
    /**
     * 得到全部行政区域(名字->full_code)MAP，指定行政区划类型
     * 
     * @return key=fullName,value=fullCode
     */
    public Map<String, String> getRegionNameMap(String type);
    
    /**
     * 取行政区划,名称、类型
     * @param fullName
     * @param type
     * @return
     * @author huy
     * @date 2014-9-4下午08:22:06
     */
    public Region getRegionByFullName(String fullName, String type);
}
