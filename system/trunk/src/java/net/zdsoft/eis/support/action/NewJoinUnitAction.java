package net.zdsoft.eis.support.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

import net.zdsoft.eis.base.common.entity.EduInfo;
import net.zdsoft.eis.base.common.entity.Region;
import net.zdsoft.eis.base.common.entity.School;
import net.zdsoft.eis.base.common.entity.Unit;
import net.zdsoft.eis.base.common.service.EduInfoService;
import net.zdsoft.eis.base.common.service.RegionService;
import net.zdsoft.eis.base.common.service.SchoolService;
import net.zdsoft.eis.base.common.service.UnitService;
import net.zdsoft.eis.support.page.PageAction;

public class NewJoinUnitAction extends PageAction{
    private static final long serialVersionUID = 1L;
    
    @SuppressWarnings("unused")
    private static final int TYPE_DIRECT = 1;
	@SuppressWarnings("unused")
    private static final int TYPE_ALL = 2;
	
	private String regionCode;
	private String cityRegionCode;
	private String countyRegionCode;
	private String queryUnitName;
	private int queryRange; // 1 省级、2 市级、3 区县级
	private List<Region> listOfRegion;
	private List<Region> listOfSubRegion;
	private List<Unit> listOfUnit;
	private Region region;
	private int queryType; // 1 直属， 2 下属
	private String regionCodeByList;
	
	private RegionService regionService;	
	private UnitService unitService;
	private Map<String, String> mapOfUnitCount;
	private SchoolService schoolService;
	private EduInfoService eduInfoService;
	
	public String fetchUnitsByProvince(){
		if (StringUtils.isBlank(regionCode)){
			listOfRegion = new ArrayList<Region>();
			listOfUnit = new ArrayList<Unit>();
			listOfSubRegion = new ArrayList<Region>();
			return SUCCESS;
		}
		listOfRegion = regionService.getSubRegionsByFullCode(regionCode);
		mapOfUnitCount = new HashMap<String, String>();
		for(Region region : listOfRegion){
			mapOfUnitCount.put(region.getFullCode(),String.valueOf(unitService.getCountByRegion(region.getFullCode().replaceAll("00","__"))));
		}
		region = regionService.getRegionByFullCode(regionCode.replaceAll("_", "0"));
		
		listOfSubRegion = new ArrayList<Region>(); 
		// 增加直属和全部
		Region cnetSysRegion = new Region();
				
		cnetSysRegion.setRegionName("全部单位");
		cnetSysRegion.setFullCode(genRegionCodeByType(regionCode));
		listOfRegion.add(0, cnetSysRegion);
		
		cnetSysRegion = new Region();
		cnetSysRegion.setRegionName("直属单位");
		cnetSysRegion.setFullCode(regionCode);
		listOfRegion.add(0, cnetSysRegion);
//		listOfUnit = unitService.getUnitsByReginoCode(regionCode, GlobalConstant.UNIT_CLASS_EDU);
		listOfUnit = unitService.getUnitsByRegion(regionCode);
//		for(UnitDto dto : listOfUnit){
//			String unitName = dto.getName();
//			if (net.zdsoft.keel.util.StringUtils.getRealLength(unitName) > 20){
//				dto.setMarkName(net.zdsoft.keel.util.StringUtils.cutOut(unitName, 20, "…"));
//			}
//			else{
//				dto.setMarkName(unitName);
//			}
//		}
		genUnitInfo(listOfUnit);
		return SUCCESS;
	}
	
	public String fetchUnitByCity(){
		if (StringUtils.isBlank(cityRegionCode)){
			listOfRegion = new ArrayList<Region>();
			listOfUnit = new ArrayList<Unit>();
			listOfSubRegion = new ArrayList<Region>();
			return SUCCESS;
		}
		listOfRegion = regionService.getSubRegionsByFullCode(regionCode);
		
		mapOfUnitCount = new HashMap<String, String>();
		for(Region region : listOfRegion){
			mapOfUnitCount.put(region.getFullCode(),String.valueOf(unitService.getCountByRegion(region.getFullCode().replaceAll("00","__"))));
		}
		region = regionService.getRegionByFullCode(cityRegionCode.replaceAll("_", "0"));
		
		
		// 增加直属和全部
		Region region = new Region();
				
		region.setRegionName("全部单位");
		region.setFullCode(genRegionCodeByType(regionCode));
		listOfRegion.add(0, region);
		
		region = new Region();
		region.setRegionName("直属单位");
		region.setFullCode(regionCode);
		listOfRegion.add(0, region);
		
		if (!cityRegionCode.equals(regionCode) && cityRegionCode.indexOf("_") < 0){
			listOfSubRegion= regionService.getSubRegionsByFullCode(cityRegionCode);
			region = new Region();
			region.setRegionName("全部单位");
			region.setFullCode(genRegionCodeByType(cityRegionCode));
			listOfSubRegion.add(0, region);
			
			region = new Region();
			region.setRegionName("直属单位");
			region.setFullCode(cityRegionCode);
			listOfSubRegion.add(0, region);
		}
		else{
			listOfSubRegion = new ArrayList<Region>();
		}
		
		if (StringUtils.isBlank(queryUnitName))
			listOfUnit = unitService.getUnitsByRegion(cityRegionCode);
		else{
			listOfUnit = unitService.getUnitsByFaintness(queryUnitName);
		}

		genUnitInfo(listOfUnit);
		return SUCCESS;
	}
	
	public String fetchUnitByCounty(){
		if (StringUtils.isBlank(countyRegionCode)){
			listOfRegion = new ArrayList<Region>();
			listOfUnit = new ArrayList<Unit>();
			listOfSubRegion = new ArrayList<Region>();
			return SUCCESS; 
		}
		listOfRegion = regionService.getSubRegionsByFullCode(regionCode);
		mapOfUnitCount = new HashMap<String, String>();
		for(Region region : listOfRegion){
			mapOfUnitCount.put(region.getFullCode(),String.valueOf(unitService.getCountByRegion(region.getFullCode().replaceAll("00","__"))));
		}
		region = regionService.getRegionByFullCode(countyRegionCode.replaceAll("_", "0"));
		
		
		listOfSubRegion= regionService.getSubRegionsByFullCode(cityRegionCode);
		// 增加直属和全部
		Region region = new Region();
				
		region.setRegionName("全部单位");
		region.setFullCode(genRegionCodeByType(regionCode));
		listOfRegion.add(0, region);
		
		region = new Region();
		region.setRegionName("直属单位");
		region.setFullCode(regionCode);
		listOfRegion.add(0, region);
		
		region = new Region();
		region.setRegionName("全部单位");
		region.setFullCode(genRegionCodeByType(cityRegionCode));
		listOfSubRegion.add(0, region);
		
		region = new Region();
		region.setRegionName("直属单位");
		region.setFullCode(cityRegionCode);
		listOfSubRegion.add(0, region);
		
		if (StringUtils.isBlank(queryUnitName))
			listOfUnit = unitService.getUnitsByUnionCode(countyRegionCode, Unit.UNIT_CLASS_EDU);
		else{
			listOfUnit = unitService.getUnitsByFaintness(queryUnitName, Unit.UNIT_CLASS_EDU);
		}

		genUnitInfo(listOfUnit);
		return SUCCESS;
	}
	
	//新增方法 2009-1-8
	/**
	 * 构建单位信息
	 * @param listOfUnit
	 */
	private void genUnitInfo(List<Unit> listOfUnit) {
		Set<String> setOfUnitId = new HashSet<String>();
		for(Unit dto : listOfUnit){
			setOfUnitId.add(dto.getId());
		}
		
		List<School> listOfSchool = schoolService.getSchools(setOfUnitId.toArray(new String[0])); 
		Map<String, School> mapOfSchDto = new HashMap<String, School>();
		for(School dto : listOfSchool){
			mapOfSchDto.put(dto.getId(), dto);
		}
		Map<String, EduInfo> mapOfEdu = eduInfoService.getEduInfos(setOfUnitId.toArray(new String[0]));
		
		for(Unit dto : listOfUnit){			
			if (Unit.UNIT_CLASS_SCHOOL == dto.getUnitclass()){
				School basicSchoolinfoDto = mapOfSchDto.get(dto.getId());
				if (null != basicSchoolinfoDto){
//					String homepage = basicSchoolinfoDto.getHomepage();
//					if (StringUtils.isNotBlank(homepage)){
//						dto.setWebContext(homepage);
//					}
//					else{
//						dto.setWebContext(dto.getSld());
//					}
				}
			}
			else{
				//如果是教育局，并且首页没有维护，只维护了二级域名，则显示二级域名地址
				EduInfo edu = mapOfEdu.get(dto.getId());
				if (edu != null){
//						String homepage = edu.getHomepage();
//						if (StringUtils.isNotBlank(homepage)){
//							dto.setWebContext(homepage);
//						}
//						else{
//							String sld = dto.getSld();
//							if (StringUtils.isNotBlank(sld)){
//								dto.setWebContext(SystemInitUtils.getCenterURL() + "/" + dto.getSld());
//							}							
//						}
				}
			}
			
		}
		
		
	}
	
	
	
	
	/**
	 * 根据类型转化region Code
	 * @param regionCode
	 * @param type
	 * @return
	 */
	private String genRegionCodeByType(String regionCode){
		if (StringUtils.isBlank(regionCode) || regionCode.length() != 6)
			return "";
		String firstSection = regionCode.substring(0, 2);
		String secondSection = regionCode.substring(2, 4);
		String thirdSection = regionCode.substring(4, 6);
		
		if (secondSection.equals("00")){
			return firstSection + "____";
		}
		else if (thirdSection.equals("00")){
			return firstSection + secondSection + "__";
		}
		else
			return regionCode;
	}
	
	public String getRegionNameDisplay(){
		if (net.zdsoft.keel.util.StringUtils.getRealLength(region.getFullName()) > 24){
			return net.zdsoft.keel.util.StringUtils.cutOut(region.getFullName(), 24, "…");
		}
		return region.getFullName();
	}
	
	public String getRegionCode() {
		return regionCode;
	}
	public void setRegionCode(String regionCode) {
		this.regionCode = regionCode;
	}
	public String getQueryUnitName() {
		return queryUnitName;
	}
	public void setQueryUnitName(String queryUnitName) {
		this.queryUnitName = queryUnitName;
	}
	public int getQueryRange() {
		return queryRange;
	}
	public void setQueryRange(int queryRange) {
		this.queryRange = queryRange;
	}

    public void setRegionService(RegionService regionService) {
        this.regionService = regionService;
    }

	public List<Region> getListOfRegion() {
		return listOfRegion;
	}

	public void setListOfRegion(List<Region> listOfRegion) {
		this.listOfRegion = listOfRegion;
	}

	public Region getRegion() {
		return region;
	}

	public void setRegion(Region region) {
		this.region = region;
	}

	public List<Region> getListOfSubRegion() {
		return listOfSubRegion;
	}

	public void setListOfSubRegion(List<Region> listOfSubRegion) {
		this.listOfSubRegion = listOfSubRegion;
	}

	public List<Unit> getListOfUnit() {
		return listOfUnit;
	}

	public void setListOfUnit(List<Unit> listOfUnit) {
		this.listOfUnit = listOfUnit;
	}

	public UnitService getUnitService() {
		return unitService;
	}

	public void setUnitService(UnitService unitService) {
		this.unitService = unitService;
	}

	public int getQueryType() {
		return queryType;
	}

	public void setQueryType(int queryType) {
		this.queryType = queryType;
	}


	public String getCityRegionCode() {
		return cityRegionCode;
	}

	public void setCityRegionCode(String cityRegionCode) {
		this.cityRegionCode = cityRegionCode;
	}

	public String getCountyRegionCode() {
		return countyRegionCode;
	}

	public void setCountyRegionCode(String countyRegionCode) {
		this.countyRegionCode = countyRegionCode;
	}

	public String getRegionCodeByList() {
		return regionCodeByList;
	}

	public void setRegionCodeByList(String regionCodeByList) {
		this.regionCodeByList = regionCodeByList;
	}

	public Map<String, String> getMapOfUnitCount() {
		return mapOfUnitCount;
	}

	public void setMapOfUnitCount(Map<String, String> mapOfUnitCount) {
		this.mapOfUnitCount = mapOfUnitCount;
	}

	public void setSchoolService(SchoolService schoolService) {
		this.schoolService = schoolService;
	}

    public void setEduInfoService(EduInfoService eduInfoService) {
        this.eduInfoService = eduInfoService;
    }

}
