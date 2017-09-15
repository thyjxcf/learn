package net.zdsoft.office.bulletin.service.impl;

import java.util.List;

import net.zdsoft.office.bulletin.dao.OfficeBulletinTlUnitDao;
import net.zdsoft.office.bulletin.entity.OfficeBulletinTlUnit;
import net.zdsoft.office.bulletin.service.OfficeBulletinTlUnitService;
/**
 * office_bulletin_tl_unit
 * @author 
 * 
 */
public class OfficeBulletinTlUnitServiceImpl implements OfficeBulletinTlUnitService{
	private OfficeBulletinTlUnitDao officeBulletinTlUnitDao;

	@Override
	public void batchSave(List<OfficeBulletinTlUnit> list) {
		officeBulletinTlUnitDao.batchSave(list);
	}
	
	@Override
	public void deleteByBulletinId(String bulletinId) {
		officeBulletinTlUnitDao.deleteByBulletinId(bulletinId);
	}
	
	@Override
	public List<OfficeBulletinTlUnit> getOfficeBulletinTlUnitList(
			String bulletinId) {
		return officeBulletinTlUnitDao.getOfficeBulletinTlUnitList(bulletinId);
	}

	public void setOfficeBulletinTlUnitDao(OfficeBulletinTlUnitDao officeBulletinTlUnitDao){
		this.officeBulletinTlUnitDao = officeBulletinTlUnitDao;
	}
}