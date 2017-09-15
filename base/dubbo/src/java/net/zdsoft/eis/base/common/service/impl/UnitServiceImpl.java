package net.zdsoft.eis.base.common.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import net.zdsoft.eis.base.cache.BaseCacheConstants;
import net.zdsoft.eis.base.common.dao.UnitDao;
import net.zdsoft.eis.base.common.entity.Unit;
import net.zdsoft.eis.base.common.service.UnitService;
import net.zdsoft.eis.base.constant.BaseConstant;
import net.zdsoft.eis.frame.cache.CascadeCacheManager;
import net.zdsoft.keel.util.Pagination;

public class UnitServiceImpl extends CascadeCacheManager implements UnitService {
	private UnitDao unitDao;

	public void setUnitDao(UnitDao unitDao) {
		this.unitDao = unitDao;
	}

	// -------------------缓存信息 begin ------------------------
	/**
	 * 将单位从上下级关系缓存中去掉（同时清理单位缓存信息）
	 * 
	 * @param parentId 上级单位guid
	 * @param unitId 要去除的单位32位guid
	 */
	protected void removeUnitFromCache(String parentId, String unitId) {
		removeEntityFromCache(BaseCacheConstants.EIS_UNITID_LIST_PARENTID
				+ parentId, unitId);
	}

	protected void updateUnitCache(String srcParentId, String destParentId,
			Unit unit) {
	    if (null != srcParentId) {
            srcParentId = BaseCacheConstants.EIS_UNITID_LIST_PARENTID + srcParentId;
        }
		updateEntityCache(srcParentId, BaseCacheConstants.EIS_UNITID_LIST_PARENTID + destParentId,
                unit);
		
		//如果是顶级教育局，则更新顶级教育局缓存
		if(Unit.TOP_UNIT_GUID.equals(unit.getParentid())){
		    putInCache(BaseCacheConstants.EIS_UNITIDTOP, unit);
		}
	}

	// =================== 从缓存中取数据 =====================
	public Unit getTopEdu() {
		return getEntityFromCache(new CacheEntityIdParam<Unit>() {

			public String fetchKey() {
				return BaseCacheConstants.EIS_UNITIDTOP;
			}

			public Unit fetchObject() {
				return unitDao.getTopEdu();
			}

			public Unit fetchObject(String id) {
				return unitDao.getUnit(id);
			}
		});
	}

	/**
	 * 取出单位Entity信息
	 * 
	 * @param unitId 单位ID
	 * @return 单位信息
	 */
	public Unit getUnit(final String unitId) {
		return getEntityFromCache(new CacheEntityParam<Unit>() {

			public String fetchKey() {
				return fetchCacheEntityKey() + unitId;
			}

			public Unit fetchObject() {
				return unitDao.getUnit(unitId);
			}
		});
	}
	
	/**
	 * 取出单位Entity信息
	 * 
	 * @param unitId 单位ID
	 * @return 单位信息
	 */
	public Unit getUnitNoFromCache(final String unitId) {
		return unitDao.getUnit(unitId);
	}

	public List<Unit> getUnderlingUnits(final String parentUnitId) {
		return getEntitiesFromCache(new CacheEntitiesIdParam<Unit>() {

			public Unit fetchObject(String id) {
				return unitDao.getUnit(id);
			}

			public String fetchKey() {
				return BaseCacheConstants.EIS_UNITID_LIST_PARENTID
						+ parentUnitId;
			}

			public List<Unit> fetchObjects() {
				return unitDao.getUnderlingUnits(parentUnitId);
			}
		});
	}

	// -------------------单位缓存信息 end------------------------

	public Unit getUnitByUnionId(String unionId) {
		return unitDao.getUnitByUnionId(unionId);
	}

	public List<Unit> getNormalUnits() {
		return unitDao.getUnits(Unit.UNIT_MARK_NORAML);
	}

	public List<Unit> getNormalLocalUnits() {
		return unitDao.getUnitsByUseType(Unit.UNIT_MARK_NORAML,
				Unit.UNIT_USETYPE_LOCAL);
	}

	public List<Unit> getAllUnits(String unitId, boolean self) {
		Unit unit = unitDao.getUnit(unitId);
		if (unit == null)
			return new ArrayList<Unit>();

		List<Unit> unitList = unitDao.getUnitsByUnionCode(unit.getUnionid());
		if (!self) {
			unitList.remove(unit);
		}
		return unitList;
	}

	public List<Unit> getAllNormalUnits(String unitId, boolean self) {
		Unit unit = unitDao.getUnit(unitId);
		if (unit == null)
			return new ArrayList<Unit>();
		List<Unit> unitList;
		if (!self) {
			unitList = unitDao.getUnitsByUnionCode(unit.getUnionid(),
					Unit.UNIT_MARK_NORAML, unitId);
		} else {
			unitList = unitDao.getUnitsByUnionCode(unit.getUnionid(),
					Unit.UNIT_MARK_NORAML, null);
		}
		return unitList;
	}
	
	public Map<String, Integer> getUnderUnitMapByUnitUseType(String unitId, boolean self) {
		Unit unit = unitDao.getUnit(unitId);
		if (unit == null)
			return new HashMap<String, Integer>();
		Map<String, Integer> map;
		if (!self) {
			map = unitDao.getUnderUnitMapByUnitUseType(unit.getUnionid(),
					Unit.UNIT_MARK_NORAML, unitId);
		} else {
			map = unitDao.getUnderUnitMapByUnitUseType(unit.getUnionid(),
					Unit.UNIT_MARK_NORAML, null);
		}
		return map;
	}

	public Map<String, Unit> getUnitMap(String[] unitIds) {
		return unitDao.getUnitMap(unitIds);
	}

	public List<Unit> getUnderlingSchools(String parentId) {
		return getUnderlingUnits(parentId, Unit.UNIT_CLASS_SCHOOL);
	}

	public List<Unit> getSchsByUnionCode(String unionId) {
		return unitDao.getUnitsByUnionCode(unionId, Unit.UNIT_MARK_NORAML,
				Unit.UNIT_CLASS_SCHOOL);
	}
	
	public List<String> getSchIdsByUnionCode(String unionId){
		return unitDao.getUnitIdsByUnionCode(unionId, Unit.UNIT_MARK_NORAML,
				Unit.UNIT_CLASS_SCHOOL);
	}

	public List<Unit> getUnderlingUnits(String parentId, int unitClass) {
		return unitDao.getUnderlingUnits(parentId, Unit.UNIT_MARK_NORAML,
				unitClass);
	}

	public List<Unit> getUnderlingUnits(String parentId, int unitClass,
			int unitType) {
		return unitDao.getUnderlingUnits(parentId, Unit.UNIT_MARK_NORAML,
				unitClass, unitType);
	}

	public List<Unit> getUnderlingUnits(String parentId, int unitClass,
			int state, String runSchoolType, String[] unitUseTypes, Pagination page) {
		return unitDao.getUnderlingUnits(parentId, unitClass, state,
				runSchoolType, unitUseTypes, page);
	}

	public List<Unit> getUnitsByFaintness(String unitName) {
		return unitDao.getUnitsByFaintness(unitName);
	}

	public List<Unit> getAllSchools(String unitId) {
		Unit unit = getUnit(unitId);
		return unitDao.getUnitsByUnionCode(unit.getUnionid(),
				Unit.UNIT_MARK_NORAML, Unit.UNIT_CLASS_SCHOOL);
	}
	
	public List<Unit> getAllEdus(String unitId) {
	    Unit unit = getUnit(unitId);
        return unitDao.getUnitsByUnionCode(unit.getUnionid(),
                Unit.UNIT_MARK_NORAML, Unit.UNIT_CLASS_EDU);
    }

	public List<Unit> getAllEdus(String unitId, boolean self) {
        Unit unit = getUnit(unitId);
        List<Unit> list = unitDao.getUnitsByUnionCode(unit.getUnionid(), Unit.UNIT_MARK_NORAML,
                Unit.UNIT_CLASS_EDU);
        for (int i = 0; i < list.size(); i++) {
			if(list.get(i).getId().equals(unitId))
				list.remove(i);
		}
		return list;
	}

	public List<Unit> getAllParentUnits(String unitId, boolean self) {
		// 取所有教育局单位,并组成map
		Map<String, Unit> unitMap = new HashMap<String, Unit>();
		List<Unit> list = getNormalEdus();
		for (Object object : list) {
			Unit unit = (Unit) object;
			unitMap.put(unit.getId(), unit);
		}
		Unit unit = getUnit(unitId);
		unitMap.put(unitId, unit);
		// 取本单位及所有上级单位
		List<Unit> rtnUnitList = new ArrayList<Unit>();
		while (true) {
			if (unitId.equals(unit.getId())) {
				if (self)
					rtnUnitList.add(unit);
			} else {
				rtnUnitList.add(unit);
			}
			unit = unitMap.get(unit.getParentid());
			if (unit == null)
				break;
		}
		return rtnUnitList;
	}

	public String[] getAllParentUnitIds(String unitId, boolean self) {
		// 取所有教育局单位,并组成map
		Map<String, Unit> unitMap = new HashMap<String, Unit>();
		List<Unit> list = getNormalEdus();
		for (Object object : list) {
			Unit unit = (Unit) object;
			unitMap.put(unit.getId(), unit);
		}

		unitMap.put(unitId, getUnit(unitId));

		// 取本单位及所有上级单位的id
		List<String> rtnUnitidsList = new ArrayList<String>();

		String id = unitId;
		if (!self) {
			if (null != unitMap.get(id)) {
				id = unitMap.get(id).getParentid();
			}
		}
		while (!Unit.TOP_UNIT_GUID.equals(id)) {
			rtnUnitidsList.add(id);
			if (null == unitMap.get(id)) {
				break;
			} else {
				id = unitMap.get(id).getParentid();
			}
		}

		String[] arr = null;
		if (rtnUnitidsList.size() > 0) {
			arr = rtnUnitidsList.toArray(new String[0]);
		}
		return arr;
	}

	// /**
	// * 递归获取下属单位的列表
	// *
	// * @param unitId
	// * @param unitMap
	// * @return
	// */
	// private List<Unit> getSubjectUnit(String unitId, Map<String, List<Unit>>
	// unitMap) {
	// List<Unit> list = new LinkedList<Unit>();
	// Unit recursionUnit;
	// List<Unit> subList = new LinkedList<Unit>();
	// if (unitMap.get(unitId) != null) {
	// subList = (List<Unit>) unitMap.get(unitId);
	// list.addAll(subList);
	// for (int i = 0; i < subList.size(); i++) {
	// recursionUnit = (Unit) subList.get(i);
	// list.addAll(getSubjectUnit(recursionUnit.getId(), unitMap));
	// }
	// }
	// return list;
	// }

	public Unit getUnitByName(String unitName) {
		return unitDao.getUnitByName(unitName);
	}

	public List<Unit> getUnits(String[] unitIds) {
		return unitDao.getUnits(unitIds);
	}
	

	public List<Unit> getUnitsWithDel(String[] ids) {
		return unitDao.getUnitsWithDel(ids);
	}
	
	public Map<String, Unit> getUnitsMapWithDel(String[] ids) {
		return unitDao.getUnitsMapWithDel(ids);
	}

	public List<Unit> getUnits() {
		return unitDao.getUnits();
	}

	public List<Unit> getUnitsByUnionCodeUnitType(String unionId,
			int unitClass, int unitType) {
		return unitDao
				.getUnitsByUnionCodeUnitType(unionId, unitClass, unitType);
	}

	public List<Unit> getNormalEdus() {
		return unitDao.getUnits(Unit.UNIT_MARK_NORAML, Unit.UNIT_CLASS_EDU);
	}

	public List<Unit> getNormalSchs() {
		return unitDao.getUnits(Unit.UNIT_MARK_NORAML, Unit.UNIT_CLASS_SCHOOL);
	}

	public Unit getUpperUnit(String unitId, int levelOffset) {
		if (0 > levelOffset) {
			return null;
		}

		Unit unit = unitDao.getUnit(unitId);
		if (null == unit) {
			return null;
		}

		while (0 < levelOffset) {
			if (Unit.TOP_UNIT_GUID.equals(unit.getParentid())) {
				return null;
			}
			unit = unitDao.getUnit(unit.getParentid());
			levelOffset--;
		}
		return unit;
	}

	public Unit getUnitByEtohSchoolId(String etohSchoolId) {
		return unitDao.getUnitByEtohSchoolId(etohSchoolId);
	}

	public Map<String, Unit> getSchMap(String unionId) {
		return unitDao.getUnitMap(unionId, Unit.UNIT_MARK_NORAML,
				Unit.UNIT_CLASS_SCHOOL);
	}

    public Map<String, List<Unit>> getUnitMapKeyByParentId() {
        List<Unit> unitList = unitDao.getUnits();
        Map<String, List<Unit>> unitMap = new HashMap<String, List<Unit>>();
        List<Unit> subList;
        // 将所有单位以父单位ID作为Key,放入unitmap中
        for (int i = 0; i < unitList.size(); i++) {
            Unit unit = unitList.get(i);
            if (!unitMap.containsKey(unit.getParentid())) {
                subList = new LinkedList<Unit>();
                for (int j = i; j < unitList.size(); j++) {
                    Unit unit_ = unitList.get(j);
                    if (unit_.getParentid().equals(unit.getParentid())) {
                        subList.add(unit_);
                    }
                }
                unitMap.put(unit.getParentid(), subList);
            }
        }
        return unitMap;
    }
    
	public List<Unit> getUnderlingUnits(String parentId, String exceptId) {
		return unitDao.getUnderlingUnits(parentId, exceptId, true);
	}

	public List<Unit> getUnitsByRange(Unit unit, String range) {
		List<Unit> unitList = new ArrayList<Unit>();
		if (unit == null)
			return unitList;

		if (range == null || "".equals(range))
			range = BaseConstant.RECEIVER_RANGE_ALL;

		// 所有单位时
		if (range.indexOf(BaseConstant.RECEIVER_RANGE_ALL) >= 0) {
			unitList = getNormalUnits();
			return unitList;
		}

		// 得到顶级单位
		Unit topUnit = this.getTopEdu();

		// 判断当前单位是否顶级单位：
		// 如果已经是顶级单位则没有直接上级和同级单位；如果不是顶级单位则有直接上级和同级单位
		boolean isTopUnit = topUnit.getId().equals(unit.getId()) ? true : false;

		// 显示直接上级单位时
		if (range.indexOf(BaseConstant.RECEIVER_RANGE_DIR_HIGHER) >= 0
				&& !isTopUnit) {
			// 得到直接上级单位
			Unit dirHigherUnit = this.getUnit(unit.getParentid());
			// 显示直接上级单位时
			unitList.add(dirHigherUnit);
		}

		// 显示本单位时
		if (range.indexOf(BaseConstant.RECEIVER_RANGE_SELF) >= 0) {
			unitList.add(unit);
		}

		// 显示同级单位时
		if (range.indexOf(BaseConstant.RECEIVER_RANGE_SAME) >= 0 && !isTopUnit) {
			List<Unit> sameUnitList = this.getUnderlingUnits(
					unit.getParentid(), unit.getId());
			unitList.addAll(sameUnitList);
		}

		// 显示下级单位时
		if (range.indexOf(BaseConstant.RECEIVER_RANGE_LOWER) >= 0) {
			List<Unit> subUnitList = this
					.getAllNormalUnits(unit.getId(), false);
			unitList.addAll(subUnitList);
		}

		// 显示直属单位时(如果已经显示下级了，就包含了直属，没有必要显示)
		if (range.indexOf(BaseConstant.RECEIVER_RANGE_DIR_UNDER) >= 0
				&& range.indexOf(BaseConstant.RECEIVER_RANGE_LOWER) < 0) {
			List<Unit> subUnitList = this
					.getUnderlingUnits(unit.getId(), false);
			unitList.addAll(subUnitList);
		}
		return unitList;
	}

	public List<Unit> getUnitsByRangeWithoutRemote(String unitId, String range) {
		if (unitId == null || "".equals(unitId))
			return null;
		if (range == null || "".equals(range))
			range = BaseConstant.RECEIVER_RANGE_ALL;

		// 得到当前单位
		Unit curUnit = this.getUnit(unitId);
		if (curUnit == null)
			return null;

		List<Unit> unitList = new ArrayList<Unit>();
		// 所有单位时
		if (range.indexOf(BaseConstant.RECEIVER_RANGE_ALL) >= 0) {
			unitList = this.getNormalLocalUnits();
			return unitList;
		}

		// 得到顶级单位
		Unit topUnit = this.getTopEdu();
		// 判断当前单位是否顶级单位
		boolean isTopUnit = topUnit.getId().equals(curUnit.getId()) ? true
				: false;

		// 显示直接上级单位时
		if (range.indexOf(BaseConstant.RECEIVER_RANGE_DIR_HIGHER) >= 0
				&& !isTopUnit) {
			// 得到直接上级单位
			Unit dirHigherUnit = this.getUnit(curUnit.getParentid());
			if (dirHigherUnit.getUsetype().intValue() == Unit.UNIT_USETYPE_LOCAL)
				unitList.add(dirHigherUnit);
		}

		// 显示本单位时
		// if(range.indexOf(BaseConstant.RECEIVER_RANGE_SELF)>=0 &&
		// curUnit.getUsetype().intValue()==GlobalConstant.UNIT_USETYPE_LOCAL){
		// unitList.add(curUnit);
		// }

		// 显示同级单位时
		if (range.indexOf(BaseConstant.RECEIVER_RANGE_SAME) >= 0 && !isTopUnit) {
			List<Unit> sameUnitList = unitDao.getUnderlingUnits(curUnit
					.getParentid(), curUnit.getId(), false);
			if (sameUnitList != null) {
				unitList.addAll(sameUnitList);
			}
		}

		// //显示下级单位时
		// if(range.indexOf(BaseConstant.RECEIVER_RANGE_LOWER)>=0){
		// List subUnitList = this.getSubUnitWithoutRemote(curUnit.getId());
		// unitList.addAll(subUnitList);
		// }

		return unitList;
	}

	public String getUnitIdStringByRange(Unit unit, String range) {
		// 所有单位时
		if (range.indexOf(BaseConstant.RECEIVER_RANGE_ALL) >= 0) {
			return BaseConstant.ALL_UNIT_INTID;
		}

		List<Unit> list = this.getUnitsByRange(unit, range);
		if (list == null || list.size() == 0)
			return "";

		StringBuffer rtn = new StringBuffer(",");
		for (int i = 0; i < list.size(); i++) {
			rtn.append(String.valueOf(((Unit) list.get(i)).getId()));
			rtn.append(",");
		}

		return rtn.toString();
	}

	public List<Unit> getUnitsByUnionCode(String unionId, int unitClass) {
		return unitDao.getUnitsByUnionCode(unionId, Unit.UNIT_MARK_NORAML,
				unitClass);
	}

	public List<Unit> getUnitsByRegion(String region) {
		return unitDao.getUnitsByRegion(region, Unit.UNIT_MARK_NORAML);
	}

	public List<Unit> getUnitsByFaintness(String unitName, int unitClass) {
		return unitDao.getUnitsByFaintness(unitName, unitClass);
	}

	public int getCountByRegion(String region) {
		return unitDao.getCountByRegion(region, Unit.UNIT_MARK_NORAML);
	}

	public List<Unit> getUnderlingUnits(String parentId, boolean self) {
		List<Unit> unitList = getUnderlingUnits(parentId);
		if (self) {
			Unit unit = unitDao.getUnit(parentId);
			unitList.add(unit);
		}
		return unitList;
	}
	public List<Unit> getSchsByNameAndUnionCode(String unitName,String unionId) {
		return unitDao.getUnitsByNameAndUnionCode(unitName, unionId, Unit.UNIT_MARK_NORAML,
				Unit.UNIT_CLASS_SCHOOL);
	}
	public List<Unit> getUnitsByNameAndUnionCode(String unitName,
			String unionId) {
		return unitDao.getUnitsByNameAndUnionCode(unitName, unionId, Unit.UNIT_MARK_NORAML,
				Unit.UNIT_CLASS_EDU);
	}
	public List<Unit> getUnderlingUnits(String unionid, String searchName,
			Pagination page) {
		return unitDao.getUnderlingUnits(unionid, searchName, page);
	}

	
	public List<Unit> getUnitsBySearchName(String searchName, Pagination page) {
		return unitDao.getUnitsBySearchName(searchName, page);
	}

	@Override
	public List<Unit> getUnderlingUnitsByPage(String parentId, int unitClass,
			Pagination page) {
		// TODO Auto-generated method stub
		return unitDao.getUnderlingUnitsByPage(parentId, unitClass, Unit.UNIT_MARK_NORAML, page);
	}
	
	@Override
	public String getUnitDetailNamesStr(String[] unitIds) {
		Map<String, Unit> unitMap = unitDao.getUnitMap(unitIds);
		StringBuffer unitNames = new StringBuffer();
		for(String unitId:unitIds){
			String unitName = "";
			Unit unit = unitMap.get(unitId);
			if(unit!=null){
				unitName = unit.getName();
			}else {
				unitName = "单位已删除";
			}
			if(unitNames.length() == 0){
				unitNames.append(unitName);
			}else{
				unitNames.append(","+unitName);
			}
		}
		return unitNames.toString();
	}

	@Override
	public List<Unit> getUnitListBySerialNumber(String sNumber, String eNumber) {
		return unitDao.getUnitListBySerialNumber(sNumber,eNumber);
	}
	

}
