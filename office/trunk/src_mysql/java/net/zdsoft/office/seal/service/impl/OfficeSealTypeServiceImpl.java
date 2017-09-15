package net.zdsoft.office.seal.service.impl;

import java.util.*;

import net.zdsoft.office.seal.entity.OfficeSealType;
import net.zdsoft.office.seal.service.OfficeSealTypeService;
import net.zdsoft.office.seal.dao.OfficeSealTypeDao;
import net.zdsoft.keel.util.Pagination;
/**
 * office_seal_type
 * @author 
 * 
 */
public class OfficeSealTypeServiceImpl implements OfficeSealTypeService{
	private OfficeSealTypeDao officeSealTypeDao;

	@Override
	public OfficeSealType save(OfficeSealType officeSealType){
		return officeSealTypeDao.save(officeSealType);
	}

	@Override
	public Integer delete(String[] ids){
		return officeSealTypeDao.delete(ids);
	}

	@Override
	public Integer update(OfficeSealType officeSealType){
		return officeSealTypeDao.update(officeSealType);
	}

	@Override
	public OfficeSealType getOfficeSealTypeById(String id){
		return officeSealTypeDao.getOfficeSealTypeById(id);
	}

	@Override
	public Map<String, OfficeSealType> getOfficeSealTypeMapByIds(String[] ids){
		return officeSealTypeDao.getOfficeSealTypeMapByIds(ids);
	}

	@Override
	public List<OfficeSealType> getOfficeSealTypeList(){
		return officeSealTypeDao.getOfficeSealTypeList();
	}

	@Override
	public List<OfficeSealType> getOfficeSealTypePage(Pagination page){
		return officeSealTypeDao.getOfficeSealTypePage(page);
	}

	@Override
	public List<OfficeSealType> getOfficeSealTypeByUnitIdList(String unitId){
		return officeSealTypeDao.getOfficeSealTypeByUnitIdList(unitId);
	}

	@Override
	public List<OfficeSealType> getOfficeSealTypeByUnitIdPage(String unitId, Pagination page){
		return officeSealTypeDao.getOfficeSealTypeByUnitIdPage(unitId, page);
	}

	@Override
	public Map<String, OfficeSealType> getOfficeSealTypeMap(String[] checkid) {
		return officeSealTypeDao.getOfficeSealTypeMap(checkid);
	}

	@Override
	public Map<String, OfficeSealType> getOfficeSealTypeByUnitIdMap(
			String unitId) {
		return officeSealTypeDao.getOfficeSealTypeByUnitIdMap(unitId);
	}

	public void setOfficeSealTypeDao(OfficeSealTypeDao officeSealTypeDao){
		this.officeSealTypeDao = officeSealTypeDao;
	}
}
