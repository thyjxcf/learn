package net.zdsoft.office.secretaryMail.service.impl;

import java.util.*;
import net.zdsoft.office.secretaryMail.entity.OfficeJzmailInfo;
import net.zdsoft.office.secretaryMail.service.OfficeJzmailInfoService;
import net.zdsoft.office.secretaryMail.dao.OfficeJzmailInfoDao;
import net.zdsoft.keel.util.Pagination;
/**
 * office_jzmail_info
 * @author 
 * 
 */
public class OfficeJzmailInfoServiceImpl implements OfficeJzmailInfoService{
	private OfficeJzmailInfoDao officeJzmailInfoDao;

	@Override
	public OfficeJzmailInfo save(OfficeJzmailInfo officeJzmailInfo){
		officeJzmailInfoDao.deleteAll();
		return officeJzmailInfoDao.save(officeJzmailInfo);
	}

	@Override
	public Integer delete(String[] ids){
		return officeJzmailInfoDao.delete(ids);
	}

	@Override
	public Integer update(OfficeJzmailInfo officeJzmailInfo){
		return officeJzmailInfoDao.update(officeJzmailInfo);
	}

	@Override
	public OfficeJzmailInfo getOfficeJzmailInfoById(String id){
		return officeJzmailInfoDao.getOfficeJzmailInfoById(id);
	}

	@Override
	public Map<String, OfficeJzmailInfo> getOfficeJzmailInfoMapByIds(String[] ids){
		return officeJzmailInfoDao.getOfficeJzmailInfoMapByIds(ids);
	}

	@Override
	public List<OfficeJzmailInfo> getOfficeJzmailInfoList(){
		return officeJzmailInfoDao.getOfficeJzmailInfoList();
	}

	@Override
	public List<OfficeJzmailInfo> getOfficeJzmailInfoPage(Pagination page){
		return officeJzmailInfoDao.getOfficeJzmailInfoPage(page);
	}

	@Override
	public List<OfficeJzmailInfo> getOfficeJzmailInfoByUnitIdList(String unitId){
		return officeJzmailInfoDao.getOfficeJzmailInfoByUnitIdList(unitId);
	}

	@Override
	public List<OfficeJzmailInfo> getOfficeJzmailInfoByUnitIdPage(String unitId, Pagination page){
		return officeJzmailInfoDao.getOfficeJzmailInfoByUnitIdPage(unitId, page);
	}

	public void setOfficeJzmailInfoDao(OfficeJzmailInfoDao officeJzmailInfoDao){
		this.officeJzmailInfoDao = officeJzmailInfoDao;
	}
}

