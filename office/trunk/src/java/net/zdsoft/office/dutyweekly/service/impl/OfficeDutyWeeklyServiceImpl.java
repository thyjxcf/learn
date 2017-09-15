package net.zdsoft.office.dutyweekly.service.impl;

import java.util.*;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import net.zdsoft.office.attendLecture.entity.OfficeAttendLecture;
import net.zdsoft.office.dutyweekly.entity.OfficeDutyWeekly;
import net.zdsoft.office.dutyweekly.service.OfficeDutyWeeklyService;
import net.zdsoft.office.dutyweekly.dao.OfficeDutyWeeklyDao;
import net.zdsoft.eis.base.common.entity.BasicClass;
import net.zdsoft.eis.base.common.entity.User;
import net.zdsoft.eis.base.common.service.BasicClassService;
import net.zdsoft.eis.base.common.service.UserService;
import net.zdsoft.keel.util.Pagination;
/**
 * office_duty_weekly
 * @author 
 * 
 */
public class OfficeDutyWeeklyServiceImpl implements OfficeDutyWeeklyService{
	private OfficeDutyWeeklyDao officeDutyWeeklyDao;
	private UserService userService;
	private BasicClassService basicClassService;

	@Override
	public OfficeDutyWeekly save(OfficeDutyWeekly officeDutyWeekly){
		return officeDutyWeeklyDao.save(officeDutyWeekly);
	}

	@Override
	public Integer delete(String[] ids){
		return officeDutyWeeklyDao.delete(ids);
	}

	@Override
	public Integer update(OfficeDutyWeekly officeDutyWeekly){
		return officeDutyWeeklyDao.update(officeDutyWeekly);
	}

	@Override
	public OfficeDutyWeekly getOfficeDutyWeeklyById(String id){
		OfficeDutyWeekly officeDutyWeekly= officeDutyWeeklyDao.getOfficeDutyWeeklyById(id);
		if(officeDutyWeekly!=null){
			Map<String,User> userMap=userService.getUserWithDelMap(officeDutyWeekly.getUnitId());
			String[] teacherIds=StringUtils.split(officeDutyWeekly.getDutyTeacher(),",");
			int i=0;
			String teacherNames="";
			String teaIds="";
			for (String string : teacherIds) {
				User user=userMap.get(string);
				if(user==null){
					continue;
				}
				if(i==0){
					teacherNames=user.getRealname();
					teaIds=user.getId();
				}else{
					teacherNames+=","+user.getRealname();
					teaIds+=","+user.getId();
				}
				i++;
			}
			officeDutyWeekly.setDutyTeacherNames(teacherNames);
			officeDutyWeekly.setDutyTeacher(teaIds);
		}
		return officeDutyWeekly;
	}

	@Override
	public Map<String, OfficeDutyWeekly> getOfficeDutyWeeklyMapByIds(String[] ids){
		return officeDutyWeeklyDao.getOfficeDutyWeeklyMapByIds(ids);
	}

	@Override
	public List<OfficeDutyWeekly> getOfficeDutyWeeklyList(){
		return officeDutyWeeklyDao.getOfficeDutyWeeklyList();
	}

	@Override
	public List<OfficeDutyWeekly> getOfficeDutyWeeklyPage(Pagination page){
		return officeDutyWeeklyDao.getOfficeDutyWeeklyPage(page);
	}

	@Override
	public List<OfficeDutyWeekly> getOfficeDutyWeeklyByUnitIdList(String unitId){
		return officeDutyWeeklyDao.getOfficeDutyWeeklyByUnitIdList(unitId);
	}

	@Override
	public List<OfficeDutyWeekly> getOfficeDutyWeeklyByUnitIdPage(String unitId, Pagination page){
		List<OfficeDutyWeekly> officeDutyWeeklies=officeDutyWeeklyDao.getOfficeDutyWeeklyByUnitIdPage(unitId, page);
		Set<String> classIds = new HashSet<String>();
		for (OfficeDutyWeekly item : officeDutyWeeklies) {
			classIds.add(item.getDutyClass());
		}
		Map<String, BasicClass> classMapWithDeleted = basicClassService.getClassMapWithDeleted(classIds.toArray(new String[0]));
		for (OfficeDutyWeekly item : officeDutyWeeklies) {
				BasicClass basicClass = classMapWithDeleted.get(item.getDutyClass());
				if(basicClass!=null){
					item.setDutyClassName(basicClass.getClassnamedynamic());
				}
		}
		
		Map<String,User> userMap=userService.getUserWithDelMap(unitId);
		if(CollectionUtils.isNotEmpty(officeDutyWeeklies)){
			for (OfficeDutyWeekly officeDutyWeekly : officeDutyWeeklies) {
				String[] teacherIds=StringUtils.split(officeDutyWeekly.getDutyTeacher(),",");
				int i=0;
				String teacherNames="";
				for (String string : teacherIds) {
					User user=userMap.get(string);
					if(user==null){
						continue;
					}
					if(i==0){
						teacherNames=user.getRealname();
					}else{
						teacherNames+=","+user.getRealname();
					}
					i++;
				}
				officeDutyWeekly.setDutyTeacherNames(teacherNames);
			}
		}
		return officeDutyWeeklies;
	}

	@Override
	public boolean isExistConflict(String unitId,String year,String semester, String id, Integer week,
			Date stateTime, Date endTime) {
		boolean lala=officeDutyWeeklyDao.isExistConflicts(unitId,year,semester, id, stateTime, endTime);
		boolean haha=officeDutyWeeklyDao.isExistConflictss(unitId,year,semester, id, week);
		boolean hehe=lala||haha;
		return hehe;
	}

	@Override
	public OfficeDutyWeekly getOfficeDutyWeeklyByUnitIdAndDate(String unitId,
			Date dutyDate) {
		return officeDutyWeeklyDao.getOfficeDutyWeeklyByUnitIdAndDate(unitId, dutyDate);
	}

	@Override
	public List<OfficeDutyWeekly> getOfficeDutyWeeklyByUnitIdPagess(
			String unitId, String year, String semester, Pagination page) {
		//return officeDutyWeeklyDao.getOfficeDutyWeeklyByUnitIdPagess(unitId, year, semester, page);
		List<OfficeDutyWeekly> officeDutyWeeklies=officeDutyWeeklyDao.getOfficeDutyWeeklyByUnitIdPagess(unitId, year, semester, page);
		Set<String> classIds = new HashSet<String>();
		for (OfficeDutyWeekly item : officeDutyWeeklies) {
			classIds.add(item.getDutyClass());
		}
		Map<String, BasicClass> classMapWithDeleted = basicClassService.getClassMapWithDeleted(classIds.toArray(new String[0]));
		for (OfficeDutyWeekly item : officeDutyWeeklies) {
				BasicClass basicClass = classMapWithDeleted.get(item.getDutyClass());
				if(basicClass!=null){
					item.setDutyClassName(basicClass.getClassnamedynamic());
				}
		}
		
		Map<String,User> userMap=userService.getUserWithDelMap(unitId);
		if(CollectionUtils.isNotEmpty(officeDutyWeeklies)){
			for (OfficeDutyWeekly officeDutyWeekly : officeDutyWeeklies) {
				String[] teacherIds=StringUtils.split(officeDutyWeekly.getDutyTeacher(),",");
				int i=0;
				String teacherNames="";
				for (String string : teacherIds) {
					User user=userMap.get(string);
					if(user==null){
						continue;
					}
					if(i==0){
						teacherNames=user.getRealname();
					}else{
						teacherNames+=","+user.getRealname();
					}
					i++;
				}
				officeDutyWeekly.setDutyTeacherNames(teacherNames);
			}
		}
		return officeDutyWeeklies;
	}

	@Override
	public String findMaxWeek(String unitId, String year, String semester) {
		return officeDutyWeeklyDao.findMaxWeek(unitId, year, semester);
	}

	@Override
	public OfficeDutyWeekly getFindMaxWeek(String unitId, String year,
			String semester) {
		return officeDutyWeeklyDao.getFindMaxWeek(unitId, year, semester);
	}

	@Override
	public Map<String, OfficeDutyWeekly> getOfficeDMap(String[] weeklyIds) {
		return officeDutyWeeklyDao.getOfficeDMap(weeklyIds);
	}

	@Override
	public OfficeDutyWeekly getOfficeDutyWeeklyByUnitIdAnds(String unitId,
			String year, String semester, String week) {
		return officeDutyWeeklyDao.getOfficeDutyWeeklyByUnitIdAnds(unitId, year, semester, week);
	}

	public void setOfficeDutyWeeklyDao(OfficeDutyWeeklyDao officeDutyWeeklyDao){
		this.officeDutyWeeklyDao = officeDutyWeeklyDao;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public void setBasicClassService(BasicClassService basicClassService) {
		this.basicClassService = basicClassService;
	}
	
}
