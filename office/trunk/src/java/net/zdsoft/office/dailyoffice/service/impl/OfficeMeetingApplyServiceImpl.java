package net.zdsoft.office.dailyoffice.service.impl;

import java.text.SimpleDateFormat;
import java.util.*;

import org.apache.commons.lang.StringUtils;

import net.zdsoft.office.dailyoffice.entity.OfficeMeetingApply;
import net.zdsoft.office.dailyoffice.entity.OfficeMeetingAudit;
import net.zdsoft.office.dailyoffice.service.OfficeMeetingApplyService;
import net.zdsoft.office.dailyoffice.service.OfficeMeetingAuditService;
import net.zdsoft.office.dailyoffice.dao.OfficeMeetingApplyDao;
import net.zdsoft.eis.base.common.entity.User;
import net.zdsoft.eis.base.common.service.UserService;
import net.zdsoft.keel.util.DateUtils;
import net.zdsoft.keel.util.Pagination;
/**
 * office_meeting_apply
 * @author 
 * 
 */
public class OfficeMeetingApplyServiceImpl implements OfficeMeetingApplyService{
	private OfficeMeetingApplyDao officeMeetingApplyDao;
	private OfficeMeetingAuditService officeMeetingAuditService;
	private UserService userService;

	@Override
	public OfficeMeetingApply save(OfficeMeetingApply officeMeetingApply){
		return officeMeetingApplyDao.save(officeMeetingApply);
	}

	@Override
	public Integer delete(String[] ids){
		return officeMeetingApplyDao.delete(ids);
	}

	@Override
	public Integer update(OfficeMeetingApply officeMeetingApply){
		return officeMeetingApplyDao.update(officeMeetingApply);
	}

	@Override
	public OfficeMeetingApply getOfficeMeetingApplyById(String id){
		return officeMeetingApplyDao.getOfficeMeetingApplyById(id);
	}

	@Override
	public Map<String, OfficeMeetingApply> getOfficeMeetingApplyMapByIds(String[] ids){
		return officeMeetingApplyDao.getOfficeMeetingApplyMapByIds(ids);
	}

	@Override
	public List<OfficeMeetingApply> getOfficeMeetingApplyList(){
		return officeMeetingApplyDao.getOfficeMeetingApplyList();
	}

	@Override
	public List<OfficeMeetingApply> getOfficeMeetingApplyPage(Pagination page){
		return officeMeetingApplyDao.getOfficeMeetingApplyPage(page);
	}
	
	@Override
	public List<OfficeMeetingApply> getOfficeMeetingApplyList(String unitId, String applyUserId, 
			String queryName, String[] queryState, Date queryBeginDate, Date queryEndDate, Pagination page){
		List<OfficeMeetingApply> applyList = officeMeetingApplyDao
				.getOfficeMeetingApplyList(unitId, applyUserId, queryName, queryState, 
						DateUtils.date2String(queryBeginDate, "yyyy-MM-dd"), 
						DateUtils.date2String(queryEndDate, "yyyy-MM-dd"), page);
		
		Set<String> userIdsSet = new HashSet<String>();
		Set<String> applyIdsSet = new HashSet<String>();
		for(OfficeMeetingApply apply1 : applyList){
			applyIdsSet.add(apply1.getId());
			userIdsSet.add(apply1.getApplyUserId());
		}
		
		Map<String, User> userMap = userService
				.getUserWithDelMap(userIdsSet.toArray(new String[userIdsSet.size()]));
		Map<String, OfficeMeetingAudit> auditMap = officeMeetingAuditService
				.getOfficeMeetingAuditMapByApplyIds(applyIdsSet.toArray(new String[applyIdsSet.size()]));
		for(OfficeMeetingApply apply1 : applyList){
			if(userMap.containsKey(apply1.getApplyUserId())){
				apply1.setApplyUserName(userMap.get(apply1.getApplyUserId()).getRealname());
			}else{
				apply1.setApplyUserName("用户已删除");
			}
			if(auditMap.containsKey(apply1.getId())){
				apply1.setAuditState(auditMap.get(apply1.getId()).getState());
				apply1.setOpinion(auditMap.get(apply1.getId()).getOpinion());
			}
		}
		return applyList;
	}

	@Override
	public List<OfficeMeetingApply> getOfficeMeetingApplyByUnitIdList(String unitId){
		return officeMeetingApplyDao.getOfficeMeetingApplyByUnitIdList(unitId);
	}

	@Override
	public List<OfficeMeetingApply> getOfficeMeetingApplyByUnitIdPage(String unitId, Pagination page){
		return officeMeetingApplyDao.getOfficeMeetingApplyByUnitIdPage(unitId, page);
	}
	
	@Override
	public List<OfficeMeetingApply> setMeetingApplyTimeStr(List<OfficeMeetingApply> applyList){
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy/MM/dd HH:mm");
		for(OfficeMeetingApply apply : applyList){
			StringBuffer useTimeRange = new StringBuffer("");
			String startdate = sdf.format(apply.getStartTime());
			String enddate = sdf.format(apply.getEndTime());
			if(StringUtils.equals(startdate, enddate)){
				useTimeRange.append(startdate);
			}else{
				useTimeRange.append(startdate).append(" - ").append(enddate);
			}
			apply.setTimeStr(useTimeRange.toString());
		}
		return applyList;
	}

	public void setOfficeMeetingApplyDao(OfficeMeetingApplyDao officeMeetingApplyDao){
		this.officeMeetingApplyDao = officeMeetingApplyDao;
	}

	public void setOfficeMeetingAuditService(
			OfficeMeetingAuditService officeMeetingAuditService) {
		this.officeMeetingAuditService = officeMeetingAuditService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

}
