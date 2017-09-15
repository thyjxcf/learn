package net.zdsoft.office.schedule.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.zdsoft.eis.base.common.dao.DeptDao;
import net.zdsoft.eis.base.common.dao.UnitDao;
import net.zdsoft.eis.base.common.dao.UserDao;
import net.zdsoft.eis.base.common.entity.Dept;
import net.zdsoft.eis.base.common.entity.Teacher;
import net.zdsoft.eis.base.common.entity.Unit;
import net.zdsoft.eis.base.common.entity.User;
import net.zdsoft.eis.base.common.service.TeacherService;
import net.zdsoft.eis.base.common.service.UserService;
import net.zdsoft.keel.util.Pagination;
import net.zdsoft.office.schedule.dao.OfficeCalendarAuthDao;
import net.zdsoft.office.schedule.entity.OfficeCalendarAuth;
import net.zdsoft.office.schedule.service.OfficeCalendarAuthService;
import net.zdsoft.office.schedule.util.ScheduleConstant;

import org.apache.commons.lang.StringUtils;
/**
 * office_calendar_auth
 * @author 
 * 
 */
public class OfficeCalendarAuthServiceImpl implements OfficeCalendarAuthService{
	private OfficeCalendarAuthDao officeCalendarAuthDao;
	private DeptDao deptDao;
	private UserDao userDao;
	private UserService userService;
	private UnitDao unitDao;
	private TeacherService teacherService;
	
	public void setTeacherService(TeacherService teacherService) {
		this.teacherService = teacherService;
	}

	public void setUnitDao(UnitDao unitDao) {
		this.unitDao = unitDao;
	}

	public void setDeptDao(DeptDao deptDao) {
		this.deptDao = deptDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	@Override
	public OfficeCalendarAuth save(OfficeCalendarAuth officeCalendarAuth){
		return officeCalendarAuthDao.save(officeCalendarAuth);
	}

	@Override
	public Integer delete(String[] ids){
		return officeCalendarAuthDao.delete(ids);
	}

	@Override
	public Integer update(OfficeCalendarAuth officeCalendarAuth){
		return officeCalendarAuthDao.update(officeCalendarAuth);
	}
	
	public boolean checkHasAuth(String unitId, String type, String userId){
		String objectId = unitId;
		// 部门负责人时，objectId为部门id
		if(ScheduleConstant.CALENDAR_AUTH_TYPE_DEPT.equals(type)){
			User uu = userDao.getUser(userId);
			if(uu != null){
				Teacher tt = teacherService.getTeacher(uu.getTeacherid());
				if(tt != null){
					objectId = tt.getDeptid();
				}
			}
		}
		return officeCalendarAuthDao.checkHasAuth(unitId, type, objectId, userId);
	}

	@Override
	public OfficeCalendarAuth getOfficeCalendarAuthById(String id){
		return officeCalendarAuthDao.getOfficeCalendarAuthById(id);
	}

	@Override
	public Map<String, OfficeCalendarAuth> getOfficeCalendarAuthMapByIds(String[] ids){
		return officeCalendarAuthDao.getOfficeCalendarAuthMapByIds(ids);
	}

	@Override
	public List<OfficeCalendarAuth> getOfficeCalendarAuthList(){
		return officeCalendarAuthDao.getOfficeCalendarAuthList();
	}

	@Override
	public List<OfficeCalendarAuth> getOfficeCalendarAuthPage(Pagination page){
		return officeCalendarAuthDao.getOfficeCalendarAuthPage(page);
	}

	@Override
	public List<OfficeCalendarAuth> getOfficeCalendarAuthByUnitIdList(String unitId){

		List<OfficeCalendarAuth> leaderList=new ArrayList<OfficeCalendarAuth>();
		List<Dept> depts=deptDao.getDepts(unitId);
		Map<String,OfficeCalendarAuth> leadMap=officeCalendarAuthDao.getOfficeAuthMap(unitId);
		Set<String> userIds = new HashSet<String>();
		for(Dept dept:depts){
			OfficeCalendarAuth offDeptLead=new OfficeCalendarAuth();
			offDeptLead.setUnitId(unitId);
			offDeptLead.setObjectId(dept.getId());
			if(leadMap.containsKey(dept.getId())){
				offDeptLead.setLeaderId(leadMap.get(dept.getId()).getLeaderId());
				offDeptLead.setId(leadMap.get(dept.getId()).getId());
			}
			offDeptLead.setObjectName(dept.getDeptname());
			offDeptLead.setAuthType(ScheduleConstant.CALENDAR_AUTH_TYPE_DEPT);
			if(StringUtils.isNotBlank(offDeptLead.getLeaderId())){
				String[] ids = offDeptLead.getLeaderId().split(",");
				for(int i=0;i<ids.length;i++){
					userIds.add(ids[i]);
				}
			}
			leaderList.add(offDeptLead);
		}
		Unit unit = unitDao.getUnit(unitId);
		OfficeCalendarAuth offUnitLead=new OfficeCalendarAuth();
		offUnitLead.setAuthType(ScheduleConstant.CALENDAR_AUTH_TYPE_LEADER);
		offUnitLead.setObjectId(unitId);
		offUnitLead.setObjectName(unit.getName());
		if(leadMap.containsKey(unitId)){
			offUnitLead.setLeaderId(leadMap.get(unitId).getLeaderId());
			offUnitLead.setId(leadMap.get(unitId).getId());
		}
		if(StringUtils.isNotBlank(offUnitLead.getLeaderId())){
			String[] ids = offUnitLead.getLeaderId().split(",");
			for(int i=0;i<ids.length;i++){
				userIds.add(ids[i]);
			}
		}
		leaderList.add(offUnitLead);
		Map<String,User> userMap = new HashMap<String, User>();
		List<User> userlist = userService.getUsersWithDel(userIds.toArray(new String[]{}));
		
		for(User user : userlist){
			userMap.put(user.getId(), user);
		}
//		List<OfficeCalendarAuth> sumList=new ArrayList<OfficeCalendarAuth>();
//		sumList.addAll(leaderList);
		for(OfficeCalendarAuth lead:leaderList){
			if(StringUtils.isNotBlank(lead.getLeaderId())){
				String[] ids = lead.getLeaderId().split(",");
				for(int i =0;i<ids.length;i++){
					if(userMap.containsKey(ids[i])){
						if(StringUtils.isNotBlank(lead.getLeaderName()))
							lead.setLeaderName(lead.getLeaderName() + "," + userMap.get(ids[i]).getRealname());
						else
							lead.setLeaderName(userMap.get(ids[i]).getRealname());
					}
				}
			}
		}
	
		return leaderList;
	}

	@Override
	public List<OfficeCalendarAuth> getOfficeCalendarAuthByUnitIdPage(String unitId, Pagination page){
		return officeCalendarAuthDao.getOfficeCalendarAuthByUnitIdPage(unitId, page);
	}
	@Override
	public OfficeCalendarAuth getOfficeCalendarAuthByUnitIdList(String unitId,
			String objectId, String authType) {
		return officeCalendarAuthDao.getOfficeCalendarAuthByUnitIdPage(unitId, objectId,authType);
	}
	public void setOfficeCalendarAuthDao(OfficeCalendarAuthDao officeCalendarAuthDao){
		this.officeCalendarAuthDao = officeCalendarAuthDao;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	
}
