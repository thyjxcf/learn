package net.zdsoft.office.bulletin.service.impl;


import java.util.List;
import java.util.Map;

import net.zdsoft.keel.util.Pagination;
import net.zdsoft.office.bulletin.dao.OfficeBulletinTypeDao;
import net.zdsoft.office.bulletin.entity.OfficeBulletinType;
import net.zdsoft.office.bulletin.service.OfficeBulletinTypeService;
/**
 * office_bulletin_type
 * @author 
 * 
 */
public class OfficeBulletinTypeServiceImpl implements OfficeBulletinTypeService{
	private OfficeBulletinTypeDao officeBulletinTypeDao;

	@Override
	public OfficeBulletinType save(OfficeBulletinType officeBulletinType){
		return officeBulletinTypeDao.save(officeBulletinType);
	}

	@Override
	public Integer delete(String[] ids){
		return officeBulletinTypeDao.delete(ids);
	}

	@Override
	public Integer update(OfficeBulletinType officeBulletinType){
		return officeBulletinTypeDao.update(officeBulletinType);
	}

	@Override
	public OfficeBulletinType getOfficeBulletinTypeById(String id){
		return officeBulletinTypeDao.getOfficeBulletinTypeById(id);
	}
	
	@Override
	public OfficeBulletinType getOfficeBulletinTypeByType(String type) {
		return officeBulletinTypeDao.getOfficeBulletinTypeByType(type);
	}

	@Override
	public Map<String, OfficeBulletinType> getOfficeBulletinTypeMapByIds(String[] ids){
		return officeBulletinTypeDao.getOfficeBulletinTypeMapByIds(ids);
	}

	@Override
	public List<OfficeBulletinType> getOfficeBulletinTypeList(Integer showNumber){
		return officeBulletinTypeDao.getOfficeBulletinTypeList(showNumber);
	}

	@Override
	public List<OfficeBulletinType> getOfficeBulletinTypePage(Pagination page){
		return officeBulletinTypeDao.getOfficeBulletinTypePage(page);
	}

	public void setOfficeBulletinTypeDao(OfficeBulletinTypeDao officeBulletinTypeDao){
		this.officeBulletinTypeDao = officeBulletinTypeDao;
	}
}