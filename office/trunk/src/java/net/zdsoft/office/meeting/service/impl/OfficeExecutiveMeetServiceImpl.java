package net.zdsoft.office.meeting.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.zdsoft.eis.base.common.entity.Teacher;
import net.zdsoft.eis.base.common.entity.User;
import net.zdsoft.eis.base.common.service.TeacherService;
import net.zdsoft.eis.base.common.service.UserService;
import net.zdsoft.keel.util.Pagination;
import net.zdsoft.office.meeting.dao.OfficeExecutiveMeetDao;
import net.zdsoft.office.meeting.entity.OfficeDeptLeader;
import net.zdsoft.office.meeting.entity.OfficeExecutiveIssue;
import net.zdsoft.office.meeting.entity.OfficeExecutiveMeet;
import net.zdsoft.office.meeting.service.OfficeDeptLeaderService;
import net.zdsoft.office.meeting.service.OfficeExecutiveFixedDeptService;
import net.zdsoft.office.meeting.service.OfficeExecutiveIssueService;
import net.zdsoft.office.meeting.service.OfficeExecutiveMeetAttendService;
import net.zdsoft.office.meeting.service.OfficeExecutiveMeetMinutesService;
import net.zdsoft.office.meeting.service.OfficeExecutiveMeetService;
import net.zdsoft.office.util.Constants;

import org.apache.commons.collections.CollectionUtils;
/**
 * office_executive_meet
 * @author 
 * 
 */
public class OfficeExecutiveMeetServiceImpl implements OfficeExecutiveMeetService{
	
	private UserService userService;
	private TeacherService teacherService;
	private OfficeDeptLeaderService officeDeptLeaderService;
	private OfficeExecutiveIssueService officeExecutiveIssueService;
	private OfficeExecutiveFixedDeptService officeExecutiveFixedDeptService;
	private OfficeExecutiveMeetAttendService officeExecutiveMeetAttendService;
	private OfficeExecutiveMeetMinutesService officeExecutiveMeetMinutesService;
	private OfficeExecutiveMeetDao officeExecutiveMeetDao;

	@Override
	public OfficeExecutiveMeet save(OfficeExecutiveMeet officeExecutiveMeet){
		return officeExecutiveMeetDao.save(officeExecutiveMeet);
	}

	@Override
	public Integer delete(String[] ids){
		return officeExecutiveMeetDao.delete(ids);
	}

	@Override
	public Integer update(OfficeExecutiveMeet officeExecutiveMeet){
		return officeExecutiveMeetDao.update(officeExecutiveMeet);
	}
	
	@Override
	public void publishMeet(String id) {
		officeExecutiveMeetDao.publishMeet(id);
	}

	@Override
	public OfficeExecutiveMeet getOfficeExecutiveMeetById(String id){
		return officeExecutiveMeetDao.getOfficeExecutiveMeetById(id);
	}

	@Override
	public Map<String, OfficeExecutiveMeet> getOfficeExecutiveMeetMapByIds(String[] ids){
		return officeExecutiveMeetDao.getOfficeExecutiveMeetMapByIds(ids);
	}

	@Override
	public List<OfficeExecutiveMeet> getOfficeExecutiveMeetList(){
		return officeExecutiveMeetDao.getOfficeExecutiveMeetList();
	}

	@Override
	public List<OfficeExecutiveMeet> getOfficeExecutiveMeetPage(Pagination page){
		return officeExecutiveMeetDao.getOfficeExecutiveMeetPage(page);
	}

	@Override
	public List<OfficeExecutiveMeet> getOfficeExecutiveMeetByUnitIdList(String unitId){
		return officeExecutiveMeetDao.getOfficeExecutiveMeetByUnitIdList(unitId);
	}

	@Override
	public List<OfficeExecutiveMeet> getOfficeExecutiveMeetByUnitIdPage(String unitId, String queryName, String startTime, String endTime, Pagination page){
		List<OfficeExecutiveMeet> list = officeExecutiveMeetDao.getOfficeExecutiveMeetByUnitIdPage(unitId, queryName, startTime, endTime, null, page);
		Set<String> meetIdSet = new HashSet<String>();
		for(OfficeExecutiveMeet oem:list){
			meetIdSet.add(oem.getId());
		}
		Map<String, String> minutesMap = officeExecutiveMeetMinutesService.getMinutesMap(unitId, meetIdSet.toArray(new String[0]), null);
		//获取参会科室
		Map<String, String> meetAttendMap = officeExecutiveMeetAttendService.getAttendDeptMap(unitId, meetIdSet.toArray(new String[0]));
		for(OfficeExecutiveMeet oem:list){
			if(oem.getState() == Constants.HASPUBLISH && oem.getMeetDate().compareTo(new Date())<0){
				oem.setStart(true);
				if(minutesMap.containsKey(oem.getId())){
					oem.setHasMinutes(true);
				}else{
					oem.setHasMinutes(false);
				}
			}else{
				oem.setStart(false);
				oem.setHasMinutes(false);
			}
			oem.setAttendDeptNames(meetAttendMap.get(oem.getId()));
		}
		
		return list;
	}
	
	@Override
	public List<OfficeExecutiveMeet> getOfficeExecutiveMeetOverduePage(
			String unitId, String queryName, String startTime, String endTime,
			Pagination page) {
		List<OfficeExecutiveMeet> list = officeExecutiveMeetDao.getOfficeExecutiveMeetOverduePage(unitId, queryName, startTime, endTime, page);
		if(CollectionUtils.isNotEmpty(list)){
			Set<String> meetIdSet = new HashSet<String>();
			for(OfficeExecutiveMeet meet:list){
				meetIdSet.add(meet.getId());
			}
			//获取参会科室
			Map<String, String> meetAttendMap = officeExecutiveMeetAttendService.getAttendDeptMap(unitId, meetIdSet.toArray(new String[0]));
			for(OfficeExecutiveMeet oem:list){
				oem.setAttendDeptNames(meetAttendMap.get(oem.getId()));
			}
		}
		return list;
	}
	
	@Override
	public List<OfficeExecutiveMeet> getMyMeetListPage(String unitId,
			 String userId, String queryName, String startTime, String endTime, Pagination page) {
		User user = userService.getUser(userId);
		Teacher teacher = teacherService.getTeacher(user.getTeacherid());
		OfficeDeptLeader odl = officeDeptLeaderService.getOfficeDeptLeader(unitId, userId, teacher.getDeptid());
		boolean flag = false;//判断是否固定参会科室负责人
		String deptId = "";
		if(odl!=null){
			deptId = teacher.getDeptid();
			flag = officeExecutiveFixedDeptService.isFixedDept(teacher.getDeptid());
		}
		List<OfficeExecutiveMeet> list = officeExecutiveMeetDao.getOfficeExecutiveMeetByUnitIdPage(unitId, queryName, startTime, endTime, Constants.HASPUBLISH, page);
		if(CollectionUtils.isNotEmpty(list)){
			List<String> meetIds = new ArrayList<String>();
			for(OfficeExecutiveMeet meet:list){
				meetIds.add(meet.getId());
			}
			Map<String, String> minutesMap = officeExecutiveMeetMinutesService.getMinutesMap(unitId, meetIds.toArray(new String[0]), teacher.getDeptid());
			Map<String, List<OfficeExecutiveIssue>> map = new HashMap<String, List<OfficeExecutiveIssue>>();
			//如果是固定列席科室，可以查看所有议题，不是固定列席科室，就只能查看跟自己相关的议题
			if(flag){
				map = officeExecutiveIssueService.getOfficeExecutiveIssueMapByMeetIds(meetIds.toArray(new String[0]));
			}else{
				map = officeExecutiveIssueService.getOfficeExecutiveIssueMapByMeetIds(meetIds.toArray(new String[0]),userId,deptId);
			}
			for(OfficeExecutiveMeet oem:list){
				if(oem.getState() == Constants.HASPUBLISH && oem.getMeetDate().compareTo(new Date())<0){
					oem.setStart(true);
					if(minutesMap.containsKey(oem.getId())){
						oem.setHasMinutes(true);
					}else{
						oem.setHasMinutes(false);
					}
				}else{
					oem.setStart(false);
					oem.setHasMinutes(false);
				}
				List<OfficeExecutiveIssue> issues = map.get(oem.getId());
				Collections.sort(issues,  new Comparator<OfficeExecutiveIssue>() {
					public int compare(OfficeExecutiveIssue o1, OfficeExecutiveIssue o2) {
						Integer number1 = o1.getSerialNumber();
						Integer number2 = o2.getSerialNumber();
						return number1.compareTo(number2);
					}
				});
				oem.setIssues(issues);
			}
		}
		return list;
	}
	
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public void setTeacherService(TeacherService teacherService) {
		this.teacherService = teacherService;
	}
	
	public void setOfficeDeptLeaderService(
			OfficeDeptLeaderService officeDeptLeaderService) {
		this.officeDeptLeaderService = officeDeptLeaderService;
	}

	public void setOfficeExecutiveIssueService(
			OfficeExecutiveIssueService officeExecutiveIssueService) {
		this.officeExecutiveIssueService = officeExecutiveIssueService;
	}

	public void setOfficeExecutiveFixedDeptService(
			OfficeExecutiveFixedDeptService officeExecutiveFixedDeptService) {
		this.officeExecutiveFixedDeptService = officeExecutiveFixedDeptService;
	}

	public void setOfficeExecutiveMeetAttendService(
			OfficeExecutiveMeetAttendService officeExecutiveMeetAttendService) {
		this.officeExecutiveMeetAttendService = officeExecutiveMeetAttendService;
	}

	public void setOfficeExecutiveMeetMinutesService(
			OfficeExecutiveMeetMinutesService officeExecutiveMeetMinutesService) {
		this.officeExecutiveMeetMinutesService = officeExecutiveMeetMinutesService;
	}

	public void setOfficeExecutiveMeetDao(OfficeExecutiveMeetDao officeExecutiveMeetDao){
		this.officeExecutiveMeetDao = officeExecutiveMeetDao;
	}
}