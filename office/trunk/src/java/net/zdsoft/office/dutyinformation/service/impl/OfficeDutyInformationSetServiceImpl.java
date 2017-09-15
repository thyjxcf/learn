package net.zdsoft.office.dutyinformation.service.impl;

import java.util.*;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import net.zdsoft.office.dutyinformation.entity.OfficeDutyApply;
import net.zdsoft.office.dutyinformation.entity.OfficeDutyInformationSet;
import net.zdsoft.office.dutyinformation.entity.OfficeDutyInformationSetEx;
import net.zdsoft.office.dutyinformation.service.OfficeDutyApplyService;
import net.zdsoft.office.dutyinformation.service.OfficeDutyInformationSetExService;
import net.zdsoft.office.dutyinformation.service.OfficeDutyInformationSetService;
import net.zdsoft.office.dutyinformation.dao.OfficeDutyInformationSetDao;
import net.zdsoft.eis.base.common.entity.Dept;
import net.zdsoft.eis.base.common.entity.Teacher;
import net.zdsoft.eis.base.common.entity.User;
import net.zdsoft.eis.base.common.service.DeptService;
import net.zdsoft.eis.base.common.service.TeacherService;
import net.zdsoft.eis.base.common.service.UserService;
import net.zdsoft.keel.util.Pagination;
import net.zdsoft.leadin.util.UUIDGenerator;
/**
 * office_duty_information_set
 * @author 
 * 
 */
public class OfficeDutyInformationSetServiceImpl implements OfficeDutyInformationSetService{
	private OfficeDutyInformationSetDao officeDutyInformationSetDao;
	private OfficeDutyInformationSetExService officeDutyInformationSetExService;
	private OfficeDutyApplyService officeDutyApplyService;
	private UserService userService;
	private DeptService deptService;
	private TeacherService teacherService;

	@Override
	public OfficeDutyInformationSet save(OfficeDutyInformationSet officeDutyInformationSet){
		if(StringUtils.isBlank(officeDutyInformationSet.getId())){
			officeDutyInformationSet.setId(UUIDGenerator.getUUID());
		}
		if(StringUtils.isNotBlank(officeDutyInformationSet.getUserIds())){
			String[] userIds=officeDutyInformationSet.getUserIds().split(",");
			saveMainUsers(officeDutyInformationSet,userIds);
		}
		return officeDutyInformationSetDao.save(officeDutyInformationSet);
	}

	public void saveMainUsers(OfficeDutyInformationSet officeDutyInformationSet,String[] userIds){
		List<OfficeDutyInformationSetEx> officeDutyInformationSetExs=new ArrayList<OfficeDutyInformationSetEx>();
		if(ArrayUtils.isNotEmpty(userIds)){
			for (String userId : userIds) {
				OfficeDutyInformationSetEx officeDutyInformationSetEx=new OfficeDutyInformationSetEx();
				officeDutyInformationSetEx.setDutyInformationId(officeDutyInformationSet.getId());
				officeDutyInformationSetEx.setUserId(userId);
				officeDutyInformationSetExs.add(officeDutyInformationSetEx);
			}
		}
		officeDutyInformationSetExService.batchSave(officeDutyInformationSetExs);
	}
	
	@Override
	public List<OfficeDutyInformationSet> getOfficeDutyInformationSetsByUnitIdName(
			String unitId,String year, String dutyName, Pagination page) {
		List<OfficeDutyInformationSet> officeDutyInformationSets=officeDutyInformationSetDao.getOfficeDutyInformationSetsByUnitIdName(unitId,year, dutyName, page);
		List<OfficeDutyApply> officeDutyApplies=officeDutyApplyService.getOfficeDutyApplyByUnitIdList(unitId);
		for (OfficeDutyInformationSet officeDutyInformationSet : officeDutyInformationSets) {
			for (OfficeDutyApply officeDutyApply : officeDutyApplies) {
				if(StringUtils.equals(officeDutyInformationSet.getId(), officeDutyApply.getDutyInformationId())){
					officeDutyInformationSet.setOnlyView(true);
					break;
				}
			}
		}
		return officeDutyInformationSets;
	}

	@Override
	public Integer delete(String[] ids){
		officeDutyInformationSetExService.delete(ids[0]);
		return officeDutyInformationSetDao.delete(ids);
	}

	@Override
	public List<OfficeDutyInformationSet> getOfficeDutyInformationSetsByIds(
			String[] ids) {
		return officeDutyInformationSetDao.getOfficeDutyInformationSetsByIds(ids);
	}

	@Override
	public Integer update(OfficeDutyInformationSet officeDutyInformationSet){
		officeDutyInformationSetExService.delete(officeDutyInformationSet.getId());
		if(StringUtils.isNotBlank(officeDutyInformationSet.getUserIds())){
			String[] userIds=officeDutyInformationSet.getUserIds().split(",");
			saveMainUsers(officeDutyInformationSet, userIds);
		}
		return officeDutyInformationSetDao.update(officeDutyInformationSet);
	}

	@Override
	public OfficeDutyInformationSet getOfficeDutyInformationSetById(String id){
		OfficeDutyInformationSet officeDutyInformationSet=officeDutyInformationSetDao.getOfficeDutyInformationSetById(id);
		if(officeDutyInformationSet!=null){
			List<OfficeDutyInformationSetEx> officeDutyInformationSetExs=officeDutyInformationSetExService.getOfficeDutyInformationSetExsByDutyId(officeDutyInformationSet.getId());
			if(CollectionUtils.isNotEmpty(officeDutyInformationSetExs)){
				Map<String,Dept> deptMap=deptService.getDeptMap(officeDutyInformationSet.getUnitId());
				
				Set<String> userIdSet=new HashSet<String>();
				for (OfficeDutyInformationSetEx officeDutyInformationSetEx : officeDutyInformationSetExs) {
					userIdSet.add(officeDutyInformationSetEx.getUserId());
				}
				
				officeDutyInformationSet.setUserSet(userIdSet);
				
				Map<String,User> userMap=userService.getUsersMap(userIdSet.toArray(new String[0]));
				
				Set<String> teacherSet=new HashSet<String>();
				int i=0;
				if(CollectionUtils.isNotEmpty(userIdSet)){
					for(String string: userIdSet){
						User user=userMap.get(string);
						if(user!=null){
							teacherSet.add(user.getTeacherid());
						}
					}
					
					Map<String,Teacher> teacherMap=teacherService.getTeacherMap(teacherSet.toArray(new String[0]));
					StringBuffer sbNames=new StringBuffer();
					StringBuffer sbIds=new StringBuffer();
					for (String string : userIdSet) {
						User user=userMap.get(string);
						Dept dept=null;
						if(user!=null&&teacherMap.containsKey(user.getTeacherid())){
							Teacher teacher=teacherMap.get(user.getTeacherid());
							if(teacher!=null&&deptMap.containsKey(teacher.getDeptid())){
								dept=deptMap.get(teacher.getDeptid());
							}
						}
						if(i==0){
							if(user!=null){
								sbNames.append(user.getRealname());
								sbIds.append(user.getId());
							}
							if(user!=null&dept!=null){
								sbNames.append("("+dept.getDeptname()+")");
							}
						}else{
							if(user!=null){
								sbNames.append(","+user.getRealname());
								sbIds.append(","+user.getId());
							}
							if(user!=null&&dept!=null){
								sbNames.append("("+dept.getDeptname()+")");
							}
						}
						i++;
					}
					officeDutyInformationSet.setUserNames(sbNames.toString());
					officeDutyInformationSet.setUserIds(sbIds.toString());
				}
			}
		}
		return officeDutyInformationSet;
	}

	@Override
	public boolean isExistConflict(String unitId, String dutyName, String dutyId) {
		return officeDutyInformationSetDao.isExistConflict(unitId, dutyName, dutyId);
	}

	@Override
	public Map<String, OfficeDutyInformationSet> getOfficeDutyInformationSetMapByIds(String[] ids){
		return officeDutyInformationSetDao.getOfficeDutyInformationSetMapByIds(ids);
	}

	@Override
	public List<OfficeDutyInformationSet> getOfficeDutyInformationSetList(){
		return officeDutyInformationSetDao.getOfficeDutyInformationSetList();
	}

	@Override
	public List<OfficeDutyInformationSet> getOfficeDutyInformationSetPage(Pagination page){
		return officeDutyInformationSetDao.getOfficeDutyInformationSetPage(page);
	}

	@Override
	public List<OfficeDutyInformationSet> getOfficeDutyInformationSetByUnitIdList(String unitId){
		return officeDutyInformationSetDao.getOfficeDutyInformationSetByUnitIdList(unitId);
	}

	@Override
	public List<OfficeDutyInformationSet> getOfficeDutyInformationSetByUnitIdPage(String unitId, Pagination page){
		return officeDutyInformationSetDao.getOfficeDutyInformationSetByUnitIdPage(unitId, page);
	}

	public void setOfficeDutyInformationSetDao(OfficeDutyInformationSetDao officeDutyInformationSetDao){
		this.officeDutyInformationSetDao = officeDutyInformationSetDao;
	}

	public void setOfficeDutyInformationSetExService(
			OfficeDutyInformationSetExService officeDutyInformationSetExService) {
		this.officeDutyInformationSetExService = officeDutyInformationSetExService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public void setDeptService(DeptService deptService) {
		this.deptService = deptService;
	}

	public void setTeacherService(TeacherService teacherService) {
		this.teacherService = teacherService;
	}

	public void setOfficeDutyApplyService(
			OfficeDutyApplyService officeDutyApplyService) {
		this.officeDutyApplyService = officeDutyApplyService;
	}
	
}
