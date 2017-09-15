package net.zdsoft.office.dailyoffice.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.zdsoft.eis.base.common.entity.BasicClass;
import net.zdsoft.eis.base.common.entity.Teacher;
import net.zdsoft.eis.base.common.entity.User;
import net.zdsoft.eis.base.common.service.BasicClassService;
import net.zdsoft.eis.base.common.service.TeacherService;
import net.zdsoft.eis.base.common.service.UserService;
import net.zdsoft.eis.base.subsystemcall.entity.EduadmCourseDto;
import net.zdsoft.eis.base.subsystemcall.service.EduadmSubsystemService;
import net.zdsoft.keel.util.DateUtils;
import net.zdsoft.keel.util.Pagination;
import net.zdsoft.office.dailyoffice.dao.OfficeUtilityApplyDao;
import net.zdsoft.office.dailyoffice.dto.AttendanceDto;
import net.zdsoft.office.dailyoffice.entity.OfficeUtilityApply;
import net.zdsoft.office.dailyoffice.service.OfficeUtilityApplyService;

import org.apache.commons.lang3.StringUtils;
/**
 * office_utility_apply
 * @author 
 * 
 */
public class OfficeUtilityApplyServiceImpl implements OfficeUtilityApplyService{
	
	private UserService userService;
	private OfficeUtilityApplyDao officeUtilityApplyDao;

	@Override
	public void save(OfficeUtilityApply officeUtilityApply){
		officeUtilityApplyDao.save(officeUtilityApply);
	}
	
	@Override
	public void addOfficeUtilityApplies(
			List<OfficeUtilityApply> officeUtilityApplies) {
		officeUtilityApplyDao.insertOfficeUtilityApplies(officeUtilityApplies);
	}
	
	@Override
	public void updateStateByIds(String[] ids, Integer state) {
		officeUtilityApplyDao.updateStateByIds(ids, state);
	}
	
	@Override
	public boolean isApplyByOthers(String roomId, String period, String type,
			String userId, Date date) {
		return officeUtilityApplyDao.isApplyByOthers(roomId, period, type, userId, date);
	}
	
	@Override
	public void batchDelete(List<OfficeUtilityApply> officeUtilityApplies) {
		officeUtilityApplyDao.batchDelete(officeUtilityApplies);
	}
	
	@Override
	public Map<String, OfficeUtilityApply> getApplyMap(String roomType,
			Date applyDate, String unitId, String userId) {
		Map<String, OfficeUtilityApply> applyMap = new HashMap<String, OfficeUtilityApply>();
		
		List<OfficeUtilityApply> officeUtilityApplies = officeUtilityApplyDao.getOfficeUtilityApplyList(roomType, applyDate, unitId, userId);
		Map<String, User> userMap = userService.getUserWithDelMap(unitId);
		
		for(OfficeUtilityApply officeUtilityApply:officeUtilityApplies){
			//如果不是当前登录人员，那么要显示姓名
			if(!userId.equals(officeUtilityApply.getUserId())){
				if(userMap.get(officeUtilityApply.getUserId())!=null){
					officeUtilityApply.setUserName(userMap.get(officeUtilityApply.getUserId()).getRealname());
				}else{
					officeUtilityApply.setUserName("用户已删除");
				}
			}
			
			applyMap.put(officeUtilityApply.getRoomId()+"_"+officeUtilityApply.getApplyPeriod(), officeUtilityApply);
		}
		return applyMap;
	}

	@Override
	public Integer delete(String[] ids){
		return officeUtilityApplyDao.delete(ids);
	}

	@Override
	public Integer update(OfficeUtilityApply officeUtilityApply){
		return officeUtilityApplyDao.update(officeUtilityApply);
	}

	@Override
	public OfficeUtilityApply getOfficeUtilityApplyById(String id){
		return officeUtilityApplyDao.getOfficeUtilityApplyById(id);
	}

	@Override
	public Map<String, OfficeUtilityApply> getOfficeUtilityApplyMapByIds(String[] ids){
		return officeUtilityApplyDao.getOfficeUtilityApplyMapByIds(ids);
	}
	
	@Override
	public List<OfficeUtilityApply> getOfficeUtilityApplyListByIds(String[] ids) {
		return officeUtilityApplyDao.getOfficeUtilityApplyListByIds(ids);
	}

	@Override
	public List<OfficeUtilityApply> getOfficeUtilityApplyPage(Pagination page){
		return officeUtilityApplyDao.getOfficeUtilityApplyPage(page);
	}

	@Override
	public List<OfficeUtilityApply> getOfficeUtilityApplyByUnitIdList(String unitId){
		return officeUtilityApplyDao.getOfficeUtilityApplyByUnitIdList(unitId);
	}

	@Override
	public List<OfficeUtilityApply> getOfficeUtilityApplyByUnitIdPage(String unitId, Pagination page){
		return officeUtilityApplyDao.getOfficeUtilityApplyByUnitIdPage(unitId, page);
	}
	
	@Override
	public List<OfficeUtilityApply> getOfficeUtilityApply(String unitId,
			String type, String state, String courseId) {
		return officeUtilityApplyDao.getOfficeUtilityApply(unitId, type, state, courseId);
	}
	
	@Override
	public List<AttendanceDto> getCourseArrange(String unitId, String courseId) {
		List<AttendanceDto> attendanceDtoList = new ArrayList<AttendanceDto>();
		List<OfficeUtilityApply> applyList = getOfficeUtilityApply(unitId, "3", "3", courseId);
		Map<String, String> groupMap = new HashMap<String, String>();
		for(OfficeUtilityApply entity : applyList) {
			groupMap.put(entity.getRoomId()+entity.getApplyDate()+entity.getApplyPeriod(), entity.getRoomId()+entity.getApplyDate()+entity.getApplyPeriod());
		}
		for(OfficeUtilityApply entity : applyList) {
			AttendanceDto dto = new AttendanceDto();
			int period = Integer.parseInt(entity.getApplyPeriod()); 
			if(!groupMap.containsKey(entity.getRoomId()+entity.getApplyDate()+(period+1))) {
				dto.setRoomId(entity.getRoomId());
				dto.setCourseId(entity.getCourseId());
				dto.setTeacherId(entity.getUserId());
				dto.setDateStr(DateUtils.date2String(entity.getApplyDate(), "yyyy-MM-dd"));
				dto.setEndPeriod(period);
				String periodStr = "";
				if(period == 99) {
					periodStr = "中午";
					dto.setSortNum(5);
				}else {
					periodStr = period+"";
					if(period >= 5) {
						dto.setSortNum(period + 1);
					}
				}
				period --;
				while(groupMap.containsKey(entity.getRoomId()+entity.getApplyDate()+(period))) {
					periodStr = (period)+"," + periodStr;
					period --;
				}
				dto.setStartPeriod(period+1);
				dto.setPeriodStr(periodStr);
				attendanceDtoList.add(dto);
			}
		}
		return attendanceDtoList;
	}
	
	@Override
	public List<AttendanceDto> getCourseArrange(String unitId,
			Date startApplyDate, Date endApplyDate, String studyType) {
		List<AttendanceDto> attendanceDtoList = new ArrayList<AttendanceDto>();
		List<OfficeUtilityApply> applyList = officeUtilityApplyDao.getOfficeUtilityApply(unitId, "3", "3", startApplyDate, endApplyDate, studyType);
		Map<String, String> groupMap = new HashMap<String, String>();
		for(OfficeUtilityApply entity : applyList) {
			groupMap.put(entity.getCourseId()+entity.getRoomId()+entity.getApplyDate()+entity.getApplyPeriod(), entity.getRoomId()+entity.getApplyDate()+entity.getApplyPeriod());
		}
		for(OfficeUtilityApply entity : applyList) {
			AttendanceDto dto = new AttendanceDto();
			int period = Integer.parseInt(entity.getApplyPeriod()); 
			if(!groupMap.containsKey(entity.getCourseId()+ entity.getRoomId()+entity.getApplyDate()+(period+1))) {
				dto.setRoomId(entity.getRoomId());
				dto.setCourseId(entity.getCourseId());
				dto.setTeacherId(entity.getUserId());
				dto.setDateStr(DateUtils.date2String(entity.getApplyDate(), "yyyy-MM-dd"));
				dto.setEndPeriod(period);
				String periodStr = "";
				if(period == 99) {
					periodStr = "中午";
					dto.setSortNum(5);
				}else {
					periodStr = period+"";
					if(period >= 5) {
						dto.setSortNum(period + 1);
					}
				}
				period --;
				while(groupMap.containsKey(entity.getCourseId()+ entity.getRoomId()+entity.getApplyDate()+(period))) {
					periodStr = (period)+"," + periodStr;
					period --;
				}
				dto.setStartPeriod(period+1);
				dto.setPeriodStr(periodStr);
				attendanceDtoList.add(dto);
			}
		}
		return attendanceDtoList;
	}

	public void setOfficeUtilityApplyDao(OfficeUtilityApplyDao officeUtilityApplyDao){
		this.officeUtilityApplyDao = officeUtilityApplyDao;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}
}