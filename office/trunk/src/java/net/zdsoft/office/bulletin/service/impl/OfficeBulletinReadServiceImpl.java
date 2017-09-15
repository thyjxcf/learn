package net.zdsoft.office.bulletin.service.impl;


import java.util.List;
import java.util.Map;

import net.zdsoft.keel.util.Pagination;
import net.zdsoft.office.bulletin.dao.OfficeBulletinReadDao;
import net.zdsoft.office.bulletin.entity.OfficeBulletinRead;
import net.zdsoft.office.bulletin.service.OfficeBulletinReadService;
/**
 * office_bulletin_read
 * @author 
 * 
 */
public class OfficeBulletinReadServiceImpl implements OfficeBulletinReadService{
	private OfficeBulletinReadDao officeBulletinReadDao;

	@Override
	public void save(OfficeBulletinRead officeBulletinRead){
		boolean flag = officeBulletinReadDao.isExist(officeBulletinRead);
		if(!flag){
			officeBulletinReadDao.save(officeBulletinRead);
		}
	}

	@Override
	public Integer delete(String[] ids){
		return officeBulletinReadDao.delete(ids);
	}

	@Override
	public OfficeBulletinRead getOfficeBulletinReadByUnitIdAndBulletinId(
			String unitId, String bulletinId, String userId) {
		return officeBulletinReadDao.getOfficeBulletinReadByUnitIdAndBulletinId(unitId, bulletinId, userId);
	}

	@Override
	public Integer update(OfficeBulletinRead officeBulletinRead){
		return officeBulletinReadDao.update(officeBulletinRead);
	}

	@Override
	public OfficeBulletinRead getOfficeBulletinReadById(String id){
		return officeBulletinReadDao.getOfficeBulletinReadById(id);
	}

	@Override
	public Map<String, OfficeBulletinRead> getOfficeBulletinReadMapByIds(String[] ids){
		return officeBulletinReadDao.getOfficeBulletinReadMapByIds(ids);
	}

	@Override
	public List<OfficeBulletinRead> getOfficeBulletinReadList(){
		return officeBulletinReadDao.getOfficeBulletinReadList();
	}

	@Override
	public List<OfficeBulletinRead> getOfficeBulletinReadPage(Pagination page){
		return officeBulletinReadDao.getOfficeBulletinReadPage(page);
	}

	@Override
	public List<OfficeBulletinRead> getOfficeBulletinReadByUnitIdList(String unitId){
		return officeBulletinReadDao.getOfficeBulletinReadByUnitIdList(unitId);
	}

	@Override
	public List<OfficeBulletinRead> getOfficeBulletinReadByUnitIdPage(String unitId, Pagination page){
		return officeBulletinReadDao.getOfficeBulletinReadByUnitIdPage(unitId, page);
	}

	public void setOfficeBulletinReadDao(OfficeBulletinReadDao officeBulletinReadDao){
		this.officeBulletinReadDao = officeBulletinReadDao;
	}
}	