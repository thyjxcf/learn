package net.zdsoft.office.dutyinformation.service.impl;

import java.util.*;

import net.zdsoft.office.dutyinformation.entity.OfficeDutyInformationSetEx;
import net.zdsoft.office.dutyinformation.service.OfficeDutyInformationSetExService;
import net.zdsoft.office.dutyinformation.dao.OfficeDutyInformationSetExDao;
import net.zdsoft.keel.util.Pagination;
/**
 * office_duty_information_set_ex
 * @author 
 * 
 */
public class OfficeDutyInformationSetExServiceImpl implements OfficeDutyInformationSetExService{
	private OfficeDutyInformationSetExDao officeDutyInformationSetExDao;

	@Override
	public OfficeDutyInformationSetEx save(OfficeDutyInformationSetEx officeDutyInformationSetEx){
		return officeDutyInformationSetExDao.save(officeDutyInformationSetEx);
	}

	@Override
	public void batchSave(
			List<OfficeDutyInformationSetEx> officeDutyInformationSetExs) {
		officeDutyInformationSetExDao.batchSave(officeDutyInformationSetExs);
	}

	@Override
	public Integer delete(String[] ids){
		return officeDutyInformationSetExDao.delete(ids);
	}

	@Override
	public void delete(String dutyId) {
		officeDutyInformationSetExDao.delete(dutyId);
	}

	@Override
	public Integer update(OfficeDutyInformationSetEx officeDutyInformationSetEx){
		return officeDutyInformationSetExDao.update(officeDutyInformationSetEx);
	}

	@Override
	public List<OfficeDutyInformationSetEx> getOfficeDutyInformationSetExsByDutyId(
			String dutyId) {
		return officeDutyInformationSetExDao.getOfficeDutyInformationSetExsByDutyId(dutyId);
	}

	@Override
	public OfficeDutyInformationSetEx getOfficeDutyInformationSetExById(String id){
		return officeDutyInformationSetExDao.getOfficeDutyInformationSetExById(id);
	}

	@Override
	public Map<String, OfficeDutyInformationSetEx> getOfficeDutyInformationSetExMapByIds(String[] ids){
		return officeDutyInformationSetExDao.getOfficeDutyInformationSetExMapByIds(ids);
	}

	@Override
	public List<OfficeDutyInformationSetEx> getOfficeDutyInformationSetExList(){
		return officeDutyInformationSetExDao.getOfficeDutyInformationSetExList();
	}

	@Override
	public List<OfficeDutyInformationSetEx> getOfficeDutyInformationSetExPage(Pagination page){
		return officeDutyInformationSetExDao.getOfficeDutyInformationSetExPage(page);
	}

	public void setOfficeDutyInformationSetExDao(OfficeDutyInformationSetExDao officeDutyInformationSetExDao){
		this.officeDutyInformationSetExDao = officeDutyInformationSetExDao;
	}
}
