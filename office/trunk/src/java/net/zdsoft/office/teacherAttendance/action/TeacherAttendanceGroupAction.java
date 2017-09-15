package net.zdsoft.office.teacherAttendance.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.zdsoft.eis.base.common.entity.Dept;
import net.zdsoft.eis.base.common.entity.Teacher;
import net.zdsoft.eis.base.common.entity.User;
import net.zdsoft.eis.base.common.service.DeptService;
import net.zdsoft.eis.base.common.service.TeacherService;
import net.zdsoft.eis.base.common.service.UserService;
import net.zdsoft.eis.frame.action.PageAction;
import net.zdsoft.office.teacherAttendance.entity.OfficeAttendanceExcludeUser;
import net.zdsoft.office.teacherAttendance.entity.OfficeAttendanceGroup;
import net.zdsoft.office.teacherAttendance.entity.OfficeAttendanceGroupPeopleDto;
import net.zdsoft.office.teacherAttendance.entity.OfficeAttendanceGroupUser;
import net.zdsoft.office.teacherAttendance.entity.OfficeAttendanceNotStaticstisPeopleDto;
import net.zdsoft.office.teacherAttendance.entity.OfficeAttendancePlace;
import net.zdsoft.office.teacherAttendance.entity.OfficeAttendanceSet;
import net.zdsoft.office.teacherAttendance.service.OfficeAttendanceExcludeUserService;
import net.zdsoft.office.teacherAttendance.service.OfficeAttendanceGroupService;
import net.zdsoft.office.teacherAttendance.service.OfficeAttendanceGroupUserService;
import net.zdsoft.office.teacherAttendance.service.OfficeAttendancePlaceService;
import net.zdsoft.office.teacherAttendance.service.OfficeAttendanceSetService;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;


/** 
 * @author  lufeng 
 * @version 创建时间：2017-4-12 下午01:36:35 
 * 类说明 
 */
public class TeacherAttendanceGroupAction extends PageAction{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private OfficeAttendanceGroup officeAttendanceGroup  = new OfficeAttendanceGroup();
	private List<OfficeAttendanceGroup> groupList = new ArrayList<OfficeAttendanceGroup>();
	private List<OfficeAttendancePlace> attendancePlaceList = new ArrayList<OfficeAttendancePlace>();
	private List<OfficeAttendanceGroupPeopleDto> groupPeopelDtoList = new ArrayList<OfficeAttendanceGroupPeopleDto>();
	private List<OfficeAttendanceSet> attendanceSetList = new ArrayList<OfficeAttendanceSet>();
	private OfficeAttendanceSetService officeAttendanceSetService;
	private OfficeAttendancePlaceService officeAttendancePlaceService;
	private OfficeAttendanceGroupService officeAttendanceGroupService;
	private OfficeAttendanceGroupUserService officeAttendanceGroupUserService;
	private OfficeAttendanceExcludeUserService officeAttendanceExcludeUserService;
	private OfficeAttendanceExcludeUser officeAttendanceExcludeUser = new OfficeAttendanceExcludeUser();
	private OfficeAttendanceNotStaticstisPeopleDto officeAttendanceNotStaticstisPeopleDto = new OfficeAttendanceNotStaticstisPeopleDto();
	private String groupName;
	private String groupId;
	private String places;
	private String teacherIdStr;
	private TeacherService teacherService; 
	private UserService userService;
	private DeptService deptService;
	private Map<String,String> placeMap = new HashMap<String,String>();
	private boolean addAttendance=false;
	private String groupUserId;
	public String execute(){
		
		return "success";
	}
	
	public String notAddStatisticLink(){
		List<OfficeAttendanceExcludeUser> officeAttendanceExcludeUserList = officeAttendanceExcludeUserService.getOfficeAttendanceExcludeUserByUnitId(getUnitId());
		Map<String,User> userMap = userService.getUserMap(getUnitId());
		String names = new String();
		String teacherIds = new String();
		if(officeAttendanceExcludeUserList != null){
			int size = officeAttendanceExcludeUserList.size();
			for(int i=0;i<size;i++){
				User u = userMap.get(officeAttendanceExcludeUserList.get(i).getUserId());
				if(u != null){
					if(i == 0){
						names += u.getRealname();
						teacherIds += u.getTeacherid();
					}else{
						names +=  "," + u.getRealname() ;
						teacherIds += ","+u.getTeacherid();
					}
				}
			}
			officeAttendanceNotStaticstisPeopleDto.setTeacherIds(teacherIds);
			officeAttendanceNotStaticstisPeopleDto.setTeacherNames(names);
		}
		return "success";
	}
	public String notAddStatisticEdit(){
		List<User> users = userService.getUsersByOwner(User.TEACHER_LOGIN,teacherIdStr.split(","));
		try{
			officeAttendanceExcludeUserService.save(getUnitId(), users);
			promptMessageDto.setOperateSuccess(true);
			promptMessageDto.setPromptMessage("保存成功");
		}catch(Exception e){
			e.printStackTrace();
			promptMessageDto.setOperateSuccess(false);
			promptMessageDto.setErrorMessage("保存失败");
		}
		return "success";
	}
	public String editGroup(){
		List<User> users = userService.getUsersByOwner(User.TEACHER_LOGIN,teacherIdStr.split(","));
		officeAttendanceGroup.setName(groupName);
		officeAttendanceGroup.setPlaceIds(places);
		try{
			promptMessageDto = officeAttendanceGroupService.save(officeAttendanceGroup,users);

		}catch(Exception e){
			e.printStackTrace();
			promptMessageDto.setOperateSuccess(false);
			promptMessageDto.setErrorMessage("保存失败！");
		}
		return "success";
	}
	public String listGroupPeople(){
		Map<String,User> userMap = userService.getUserMap(getUnitId());
		List<OfficeAttendanceGroup> groupList = officeAttendanceGroupService.listOfficeAttendanceGroupByUnitId(getUnitId());
		String[] groupIds = new String[groupList.size()];
		for(int m=0;m<groupList.size();m++){
			groupIds[m] = groupList.get(m).getId();
		}
		Map<String, List<OfficeAttendanceGroupUser>> groupUserMap = officeAttendanceGroupUserService.getOfficeAttendanceGroupUserMap(groupIds);
		List<OfficeAttendanceGroupUser> userList = groupUserMap.get(groupId);
		Map<String,Dept> deptMap = deptService.getDeptMap(getUnitId());
		List<Dept> deptList = deptService.getDepts(getUnitId());
		Map<String,Teacher> teacherMap = teacherService.getTeacherMap(getUnitId());
		List<OfficeAttendanceExcludeUser> officeAttendanceExcludeUserList = officeAttendanceExcludeUserService.getOfficeAttendanceExcludeUserByUnitId(getUnitId());
		List<String> notAttendanceUserIds = new ArrayList<String>();
		if(CollectionUtils.isNotEmpty(officeAttendanceExcludeUserList)){
			for(OfficeAttendanceExcludeUser u:officeAttendanceExcludeUserList){
				notAttendanceUserIds.add(u.getUserId());
			}
		}
		Map<String,List<OfficeAttendanceGroupPeopleDto>> deptGroup = new HashMap<String,List<OfficeAttendanceGroupPeopleDto>>();
		if(userList != null){
			int size = userList.size();
			for(int i=0;i<size;i++){
				OfficeAttendanceGroupPeopleDto groupPeople = new OfficeAttendanceGroupPeopleDto();
				User u = userMap.get(userList.get(i).getUserId());
				Teacher t = teacherMap.get(u.getTeacherid());
				Dept d = deptMap.get(t.getDeptid());
				groupPeople.setName(u.getRealname());
				groupPeople.setDepartmentName(d.getDeptname());
				if(notAttendanceUserIds.contains(u.getId())){
					groupPeople.setAddAttendancestatistics(false);
				}else{
					groupPeople.setAddAttendancestatistics(true);
				}
				groupPeople.setGroupUserId(u.getId());
//				groupPeopelDtoList.add(groupPeople);
				List<OfficeAttendanceGroupPeopleDto> groupPeo = deptGroup.get(t.getDeptid());
				if(groupPeo == null){
					groupPeo = new ArrayList<OfficeAttendanceGroupPeopleDto>();
					groupPeo.add(groupPeople);
					deptGroup.put(t.getDeptid() , groupPeo);
				}else{
					groupPeo.add(groupPeople);
					deptGroup.put(t.getDeptid() , groupPeo);
				}
			}
		}
		for(Dept dept:deptList){
			List<OfficeAttendanceGroupPeopleDto>  dto = deptGroup.get(dept.getId());
			if(dto != null){
				groupPeopelDtoList.addAll(dto);
			}
		}
		return "success";
	}
	public String isAddAttendancePeople(){
		try{
			if(!addAttendance){
				OfficeAttendanceExcludeUser officeAttendanceExcludeUser = new OfficeAttendanceExcludeUser();
				officeAttendanceExcludeUser.setUnitId(getUnitId());
				officeAttendanceExcludeUser.setUserId(groupUserId);
				officeAttendanceExcludeUserService.save(officeAttendanceExcludeUser);
			}else{
				officeAttendanceExcludeUserService.deleteByUserId(new String[]{groupUserId});
			}
			promptMessageDto.setOperateSuccess(true);
			promptMessageDto.setPromptMessage("添加成功");
		}catch(Exception e){
			e.printStackTrace();
			promptMessageDto.setOperateSuccess(false);
			promptMessageDto.setErrorMessage("添加失败");
		}
		return "success";
	}
	public String newGroup(){
		attendanceSetList = officeAttendanceSetService.getOfficeAttendanceSetByUnitId(getUnitId());
		attendancePlaceList = officeAttendancePlaceService.listOfficeAttendancePlaceByUnitId(getUnitId());
		if(StringUtils.isEmpty(groupId)){
			officeAttendanceGroup.setUnitId(getUnitId());
		}else{
			officeAttendanceGroup = officeAttendanceGroupService.getOfficeAttendanceGroupById(groupId);
			Map<String,User> userMap = userService.getUserMap(getUnitId());
			Map<String, List<OfficeAttendanceGroupUser>> groupUserMap = officeAttendanceGroupUserService.getOfficeAttendanceGroupUserMap(new String[]{groupId});
			List<OfficeAttendanceGroupUser> userList = groupUserMap.get(groupId);
			String names = new String();
			String teacherIds = new String();
			if(userList != null){
				int size = userList.size();
				for(int i=0;i<size;i++){
					User u = userMap.get(userList.get(i).getUserId());
					if(u != null){
						if(i == 0){
							names += u.getRealname();
							teacherIds += u.getTeacherid();
						}else{
							names +=  "," + u.getRealname() ;
							teacherIds += ","+u.getTeacherid();
						}
					}
				}
			}
			officeAttendanceGroup.setUserNames(names);
			officeAttendanceGroup.setTeacherIds(teacherIds);
		}

			if(StringUtils.isNotEmpty(groupId)){
				List<OfficeAttendancePlace> plaList = officeAttendancePlaceService.listOfficeAttendancePlaceIds(officeAttendanceGroup.getPlaceIds().split(","));
				for(OfficeAttendancePlace pla:plaList){
					placeMap.put(pla.getId(), pla.getName());
				}
			}
			return "success";
	}
	public String checkGroupName(){
		officeAttendanceGroup = officeAttendanceGroupService.getOfficeAttendanceGroupByName(groupName,groupId);
		if(officeAttendanceGroup == null){
			promptMessageDto.setOperateSuccess(true);
			promptMessageDto.setPromptMessage("组名没有重复");
		}else{
			promptMessageDto.setOperateSuccess(false);
			promptMessageDto.setErrorMessage("组名重复,请修改！");
		}
		return "success";
	}
	public String listAttendanceGroup(){
		groupList = officeAttendanceGroupService.listOfficeAttendanceGroupByUnitId(getUnitId());
		
		return "success";
	}
	public String doDelete(){
		try{
			officeAttendanceGroupService.deleteGroupAndUser(groupId);
			promptMessageDto.setOperateSuccess(true);
			promptMessageDto.setPromptMessage("删除成功");
		}catch(Exception e){
			e.printStackTrace();
			promptMessageDto.setOperateSuccess(false);
			promptMessageDto.setErrorMessage("删除失败");
		}
		return "success";
	}
	public OfficeAttendanceGroup getOfficeAttendanceGroup() {
		return officeAttendanceGroup;
	}
	public void setOfficeAttendanceGroup(OfficeAttendanceGroup officeAttendanceGroup) {
		this.officeAttendanceGroup = officeAttendanceGroup;
	}
	public List<OfficeAttendancePlace> getAttendancePlaceList() {
		return attendancePlaceList;
	}
	public void setAttendancePlaceList(
			List<OfficeAttendancePlace> attendancePlaceList) {
		this.attendancePlaceList = attendancePlaceList;
	}
	public List<OfficeAttendanceSet> getAttendanceSetList() {
		return attendanceSetList;
	}
	public void setAttendanceSetList(List<OfficeAttendanceSet> attendanceSetList) {
		this.attendanceSetList = attendanceSetList;
	}
	public void setOfficeAttendanceSetService(
			OfficeAttendanceSetService officeAttendanceSetService) {
		this.officeAttendanceSetService = officeAttendanceSetService;
	}
	public void setOfficeAttendancePlaceService(
			OfficeAttendancePlaceService officeAttendancePlaceService) {
		this.officeAttendancePlaceService = officeAttendancePlaceService;
	}
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public String getGroupId() {
		return groupId;
	}
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getPlaces() {
		return places;
	}
	public void setPlaces(String places) {
		this.places = places;
	}
	public String getTeacherIdStr() {
		return teacherIdStr;
	}
	public void setTeacherIdStr(String teacherIdStr) {
		this.teacherIdStr = teacherIdStr;
	}
	public void setOfficeAttendanceGroupService(
			OfficeAttendanceGroupService officeAttendanceGroupService) {
		this.officeAttendanceGroupService = officeAttendanceGroupService;
	}
	public void setTeacherService(TeacherService teacherService) {
		this.teacherService = teacherService;
	}
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	public List<OfficeAttendanceGroup> getGroupList() {
		return groupList;
	}
	public void setGroupList(List<OfficeAttendanceGroup> groupList) {
		this.groupList = groupList;
	}
	public Map<String, String> getPlaceMap() {
		return placeMap;
	}
	public void setPlaceMap(Map<String, String> placeMap) {
		this.placeMap = placeMap;
	}
	public void setOfficeAttendanceGroupUserService(
			OfficeAttendanceGroupUserService officeAttendanceGroupUserService) {
		this.officeAttendanceGroupUserService = officeAttendanceGroupUserService;
	}
	public boolean isAddAttendance() {
		return addAttendance;
	}
	public void setAddAttendance(boolean addAttendance) {
		this.addAttendance = addAttendance;
	}
	public void setDeptService(DeptService deptService) {
		this.deptService = deptService;
	}
	public List<OfficeAttendanceGroupPeopleDto> getGroupPeopelDtoList() {
		return groupPeopelDtoList;
	}
	public void setGroupPeopelDtoList(
			List<OfficeAttendanceGroupPeopleDto> groupPeopelDtoList) {
		this.groupPeopelDtoList = groupPeopelDtoList;
	}
	public String getGroupUserId() {
		return groupUserId;
	}
	public void setGroupUserId(String groupUserId) {
		this.groupUserId = groupUserId;
	}

	public void setOfficeAttendanceExcludeUserService(
			OfficeAttendanceExcludeUserService officeAttendanceExcludeUserService) {
		this.officeAttendanceExcludeUserService = officeAttendanceExcludeUserService;
	}

	public OfficeAttendanceExcludeUser getOfficeAttendanceExcludeUser() {
		return officeAttendanceExcludeUser;
	}

	public OfficeAttendanceNotStaticstisPeopleDto getOfficeAttendanceNotStaticstisPeopleDto() {
		return officeAttendanceNotStaticstisPeopleDto;
	}

	public void setOfficeAttendanceNotStaticstisPeopleDto(
			OfficeAttendanceNotStaticstisPeopleDto officeAttendanceNotStaticstisPeopleDto) {
		this.officeAttendanceNotStaticstisPeopleDto = officeAttendanceNotStaticstisPeopleDto;
	}

	public void setOfficeAttendanceExcludeUser(
			OfficeAttendanceExcludeUser officeAttendanceExcludeUser) {
		this.officeAttendanceExcludeUser = officeAttendanceExcludeUser;
	}
	
}
