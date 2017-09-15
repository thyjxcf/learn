package net.zdsoft.office.msgcenter.service.impl;

import java.util.*;

import net.zdsoft.office.msgcenter.entity.OfficeSmsDetail;
import net.zdsoft.office.msgcenter.service.OfficeSmsDetailService;
import net.zdsoft.office.msgcenter.dao.OfficeSmsDetailDao;
import net.zdsoft.keel.util.Pagination;
/**
 * office_sms_detail
 * @author 
 * 
 */
public class OfficeSmsDetailServiceImpl implements OfficeSmsDetailService{
	private OfficeSmsDetailDao officeSmsDetailDao;

	@Override
	public OfficeSmsDetail save(OfficeSmsDetail officeSmsDetail){
		return officeSmsDetailDao.save(officeSmsDetail);
	}

	@Override
	public Integer delete(String[] ids){
		return officeSmsDetailDao.delete(ids);
	}

	@Override
	public Integer update(OfficeSmsDetail officeSmsDetail){
		return officeSmsDetailDao.update(officeSmsDetail);
	}

	@Override
	public OfficeSmsDetail getOfficeSmsDetailById(String id){
		return officeSmsDetailDao.getOfficeSmsDetailById(id);
	}

	@Override
	public Map<String, OfficeSmsDetail> getOfficeSmsDetailMapByIds(String[] ids){
		return officeSmsDetailDao.getOfficeSmsDetailMapByIds(ids);
	}

	@Override
	public List<OfficeSmsDetail> getOfficeSmsDetailList(){
		return officeSmsDetailDao.getOfficeSmsDetailList();
	}

	@Override
	public List<OfficeSmsDetail> getOfficeSmsDetailPage(Pagination page){
		return officeSmsDetailDao.getOfficeSmsDetailPage(page);
	}
	
	@Override
	public void batchSave(List<OfficeSmsDetail> officeSmsDetailList){
		officeSmsDetailDao.batchSave(officeSmsDetailList);
	}
	
	@Override
	public void deleteByInfoIds(String[] infoIds){
		officeSmsDetailDao.deleteByInfoIds(infoIds);
	}

	public void setOfficeSmsDetailDao(OfficeSmsDetailDao officeSmsDetailDao){
		this.officeSmsDetailDao = officeSmsDetailDao;
	}
}
