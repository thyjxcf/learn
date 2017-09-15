package net.zdsoft.office.survey.service.impl;

import java.util.*;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.collections.CollectionUtils;

import net.zdsoft.office.survey.entity.OfficeSurveyApply;
import net.zdsoft.office.survey.service.OfficeSurveyApplyService;
import net.zdsoft.office.survey.dao.OfficeSurveyApplyDao;
import net.zdsoft.eis.base.common.entity.Dept;
import net.zdsoft.eis.base.common.entity.Teacher;
import net.zdsoft.eis.base.common.entity.User;
import net.zdsoft.eis.base.common.service.DeptService;
import net.zdsoft.eis.base.common.service.TeacherService;
import net.zdsoft.eis.base.common.service.UserService;
import net.zdsoft.keel.util.Pagination;
/**
 * office_survey_apply
 * @author 
 * 
 */
public class OfficeSurveyApplyServiceImpl implements OfficeSurveyApplyService{
	private OfficeSurveyApplyDao officeSurveyApplyDao;
	private UserService userService;
	private TeacherService teacherService;
	private DeptService deptService;

	@Override
	public OfficeSurveyApply save(OfficeSurveyApply officeSurveyApply){
		return officeSurveyApplyDao.save(officeSurveyApply);
	}

	@Override
	public Integer delete(String[] ids){
		return officeSurveyApplyDao.delete(ids);
	}

	@Override
	public Integer update(OfficeSurveyApply officeSurveyApply){
		return officeSurveyApplyDao.update(officeSurveyApply);
	}

	@Override
	public OfficeSurveyApply getOfficeSurveyApplyById(String id){
		return officeSurveyApplyDao.getOfficeSurveyApplyById(id);
	}

	@Override
	public Map<String, OfficeSurveyApply> getOfficeSurveyApplyMapByIds(String[] ids){
		return officeSurveyApplyDao.getOfficeSurveyApplyMapByIds(ids);
	}

	@Override
	public List<OfficeSurveyApply> getOfficeSurveyApplyList(){
		return officeSurveyApplyDao.getOfficeSurveyApplyList();
	}

	@Override
	public List<OfficeSurveyApply> getOfficeSurveyApplyPage(Pagination page){
		return officeSurveyApplyDao.getOfficeSurveyApplyPage(page);
	}

	@Override
	public List<OfficeSurveyApply> getOfficeSurveyApplyByUnitIdList(String unitId){
		return officeSurveyApplyDao.getOfficeSurveyApplyByUnitIdList(unitId);
	}

	@Override
	public List<OfficeSurveyApply> getOfficeSurveyApplyByUnitIdPage(String unitId, Pagination page){
		return officeSurveyApplyDao.getOfficeSurveyApplyByUnitIdPage(unitId, page);
	}

	@Override
	public List<OfficeSurveyApply> getSurveyApplyListByConditions(String unitId, String searchType, String searchPlace, String appayUserId,
			String searchName, Date searchStartTime, Date searchEndTime, Pagination page){
		List<OfficeSurveyApply> list = officeSurveyApplyDao.getSurveyApplyListByConditions(unitId, searchType, searchPlace, appayUserId, searchName, searchStartTime, searchEndTime, page);
		Map<String, User> userMap = userService.getUserWithDelMap(unitId);
		Map<String, Teacher> teacherMap = teacherService.getTeacherMap(unitId);
		Map<String, Dept> deptMap = deptService.getDeptMap(unitId);
		for(OfficeSurveyApply item : list){
			item.setApplyDeptName("");
			User user = userMap.get(item.getApplyUserId());
			if(user != null && teacherMap.get(user.getTeacherid()) != null){
				Teacher teacher = teacherMap.get(user.getTeacherid());
				String deptId = teacher.getDeptid();
				if(StringUtils.isNotBlank(deptId) && deptMap.get(deptId) != null){
					item.setApplyDeptName(deptMap.get(deptId).getDeptname());
				}
			}
		}
		
		return list;
	}
	
	public void setOfficeSurveyApplyDao(OfficeSurveyApplyDao officeSurveyApplyDao){
		this.officeSurveyApplyDao = officeSurveyApplyDao;
	}

@Override
	public List<OfficeSurveyApply> getSurveyApplyList(String userId, String unitId,
			String applyStatus, String searchPlace, Pagination page) {
		List<OfficeSurveyApply> list = officeSurveyApplyDao.getSurveyApplyList(userId, unitId, applyStatus, searchPlace, page);
		Set<String> userIds = new HashSet<String>();
		if(CollectionUtils.isNotEmpty(list)){
		for(OfficeSurveyApply ent : list){
			userIds.add(ent.getApplyUserId());
			}
		}
		Map<String, User> userMap = userService.getUserWithDelMap(userIds.toArray(new String[0]));
		if(CollectionUtils.isNotEmpty(list)){
		for(OfficeSurveyApply ent : list){
			if(userMap.containsKey(ent.getApplyUserId())){
				User user = userMap.get(ent.getApplyUserId());
				if(user != null){
					ent.setApplyUserName(user.getRealname());
					ent.setApplyDeptName(deptService.getDept(user.getDeptid()).getDeptname());
				}else{
					ent.setApplyUserName("该用户已删除");
					ent.setApplyDeptName("该部门不存在");
				}
			}
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

	public void setDeptService(DeptService deptService) {
		this.deptService = deptService;
	}

}
