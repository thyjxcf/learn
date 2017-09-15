package net.zdsoft.office.teacherAttendance.service.impl;


import java.util.*;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import net.zdsoft.eis.base.common.entity.User;
import net.zdsoft.eis.base.common.service.UserService;
import net.zdsoft.eis.frame.dto.PromptMessageDto;
import net.zdsoft.keel.util.Pagination;
import net.zdsoft.office.teacherAttendance.dao.OfficeAttendanceGroupDao;
import net.zdsoft.office.teacherAttendance.entity.OfficeAttendanceGroup;
import net.zdsoft.office.teacherAttendance.entity.OfficeAttendanceGroupUser;
import net.zdsoft.office.teacherAttendance.entity.OfficeAttendancePlace;
import net.zdsoft.office.teacherAttendance.entity.OfficeAttendanceSet;
import net.zdsoft.office.teacherAttendance.service.OfficeAttendanceGroupService;
import net.zdsoft.office.teacherAttendance.service.OfficeAttendanceGroupUserService;
import net.zdsoft.office.teacherAttendance.service.OfficeAttendancePlaceService;
import net.zdsoft.office.teacherAttendance.service.OfficeAttendanceSetService;
/**
 * 考勤组
 * @author 
 * 
 */
public class OfficeAttendanceGroupServiceImpl implements OfficeAttendanceGroupService{
	private OfficeAttendanceGroupDao officeAttendanceGroupDao;
	private OfficeAttendanceGroupUserService officeAttendanceGroupUserService;
	private OfficeAttendanceSetService officeAttendanceSetService;
	private OfficeAttendancePlaceService officeAttendancePlaceService;
	private UserService userService;
	@Override
	public PromptMessageDto save(
			OfficeAttendanceGroup officeAttendanceGroup, List<User> users) {
		// TODO Auto-generated method stub
		List<OfficeAttendanceGroupUser> groupUsers = new ArrayList<OfficeAttendanceGroupUser>();
		List<OfficeAttendanceGroup> groupList = officeAttendanceGroupDao.listOfficeAttendanceGroupByUnitId(officeAttendanceGroup.getUnitId());
		String[] groupIds = new String[groupList.size()];
		for(int m=0;m<groupList.size();m++){
			groupIds[m] = groupList.get(m).getId();
		}
		Map<String, List<OfficeAttendanceGroupUser>> groupUserMap = officeAttendanceGroupUserService.getOfficeAttendanceGroupUserMap(groupIds);
	
		List<OfficeAttendanceGroupUser> allGroupUser = officeAttendanceGroupUserService.getOfficeAttendanceGroupUser();

		if(StringUtils.isNotEmpty(officeAttendanceGroup.getId())){
			List<OfficeAttendanceGroupUser> AddAttendance = groupUserMap.get(officeAttendanceGroup.getId());
			allGroupUser.removeAll(AddAttendance);
		}
		List<String> allGroupUserIds = new ArrayList<String>(); 
		for(OfficeAttendanceGroupUser u:allGroupUser){
			allGroupUserIds.add(u.getUserId());
		}
		Map<String,User> userMap = userService.getUserMap(officeAttendanceGroup.getUnitId());
		StringBuffer names = new StringBuffer();
		for(User u:users){
			if(allGroupUserIds.contains(u.getId())){
				names.append(userMap.get(u.getId()).getRealname() + " ");
			}
		}
		PromptMessageDto promptMessageDto  = new PromptMessageDto();
		if(StringUtils.isNotEmpty(names.toString())){
			promptMessageDto.setPromptMessage("这些人员  "+ names.toString() + "不能添加 ，他们已经添加到其他组了");
			promptMessageDto.setOperateSuccess(false);
			return promptMessageDto;
		}
		if(StringUtils.isEmpty(officeAttendanceGroup.getId())){
			officeAttendanceGroup.setCreationTime(new Date());
			officeAttendanceGroup = save(officeAttendanceGroup);
		}else{
			officeAttendanceGroup.setModifyTime(new Date());
			update(officeAttendanceGroup);
			officeAttendanceGroupUserService.deleteByGroupId(officeAttendanceGroup.getId());
		}
		for(User u:users){
			OfficeAttendanceGroupUser groupUser = new OfficeAttendanceGroupUser();
			groupUser.setGroupId(officeAttendanceGroup.getId());
			groupUser.setUserId(u.getId());
			groupUsers.add(groupUser);
		}
	
		officeAttendanceGroupUserService.batchSave(groupUsers);
		promptMessageDto.setOperateSuccess(true);
		promptMessageDto.setPromptMessage("保存成功！");
		return promptMessageDto;
	}
	@Override
	public OfficeAttendanceGroup save(OfficeAttendanceGroup officeAttendanceGroup){
		return officeAttendanceGroupDao.save(officeAttendanceGroup);
	}

	@Override
	public Integer delete(String[] ids){
		return officeAttendanceGroupDao.delete(ids);
	}

	@Override
	public Integer update(OfficeAttendanceGroup officeAttendanceGroup){
		return officeAttendanceGroupDao.update(officeAttendanceGroup);
	}

	@Override
	public OfficeAttendanceGroup getOfficeAttendanceGroupById(String id){
		return officeAttendanceGroupDao.getOfficeAttendanceGroupById(id);
	}

	public void setOfficeAttendanceGroupDao(
			OfficeAttendanceGroupDao officeAttendanceGroupDao) {
		this.officeAttendanceGroupDao = officeAttendanceGroupDao;
	}

	@Override
	public OfficeAttendanceGroup getOfficeAttendanceGroupByName(String name ,String id) {
		// TODO Auto-generated method stub
		return officeAttendanceGroupDao.getOfficeAttendanceGroupByName(name , id);
	}

	

	public void setOfficeAttendanceGroupUserService(
			OfficeAttendanceGroupUserService officeAttendanceGroupUserService) {
		this.officeAttendanceGroupUserService = officeAttendanceGroupUserService;
	}

	public List<OfficeAttendanceGroup> getListByPlaceId(String placeId){
		return officeAttendanceGroupDao.getListByPlaceId(placeId);
	}
	
	@Override
	public List<OfficeAttendanceGroup> listOfficeAttendanceGroupByUnitId(
			String unitId) {
		// TODO Auto-generated method stub
		List<OfficeAttendanceGroup> groupList = officeAttendanceGroupDao.listOfficeAttendanceGroupByUnitId(unitId);
		String[] groupIds = new String[groupList.size()];
		for(int m=0;m<groupList.size();m++){
			groupIds[m] = groupList.get(m).getId();
		}
		Map<String, OfficeAttendancePlace> placeMap  = officeAttendancePlaceService.getOfficeAttendancePlaceMapByUnitId(unitId);
		Map<String,OfficeAttendanceSet> setMap = officeAttendanceSetService.getOfficeAttendanceSetMapByUnitId(unitId);
		Map<String,User> userMap = userService.getUserMap(unitId);
		Map<String, List<OfficeAttendanceGroupUser>> groupUserMap = officeAttendanceGroupUserService.getOfficeAttendanceGroupUserMap(groupIds);
		List<OfficeAttendanceGroupUser> groupUserList;
		for(OfficeAttendanceGroup group:groupList){
			if(setMap.get(group.getAttSetId())!= null){
				group.setAttSetName(setMap.get(group.getAttSetId()).getName());
			}
			String PlaceIdsStr = group.getPlaceIds();
			if(StringUtils.isNotEmpty(PlaceIdsStr)){
				
				String[] placeIds = new String[]{};
				placeIds = group.getPlaceIds().split(",");
				String placeNames ="";
				 for(String str:placeIds){
					if(placeMap.get(str) != null){
						placeNames += placeMap.get(str).getName() + "  ";
					}
					group.setPlaceName(placeNames);
				}
			}
			groupUserList =groupUserMap.get(group.getId());
			String userNames = "";
			if(groupUserList != null){
				int size = groupUserList.size();
				for(int i=0;i<size;i++){
					User u = userMap.get(groupUserList.get(i).getUserId());
					if(u != null){
						if(i == 0){
							userNames += u.getRealname();
						}else{
							userNames += ","+u.getRealname();
						}
					}
				}
			}
			group.setUserNames(userNames);
		}
		
		return groupList;
	}
	
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	public void setOfficeAttendanceSetService(
			OfficeAttendanceSetService officeAttendanceSetService) {
		this.officeAttendanceSetService = officeAttendanceSetService;
	}
	public void setOfficeAttendancePlaceService(
			OfficeAttendancePlaceService officeAttendancePlaceService) {
		this.officeAttendancePlaceService = officeAttendancePlaceService;
	}
	@Override
	public void deleteGroupAndUser(String groupId) {
		// TODO Auto-generated method stub
		delete(new String[]{groupId});
		officeAttendanceGroupUserService.deleteByGroupId(groupId);
	}
	@Override
	public OfficeAttendanceGroup getOfficeNotAddAttendanceGroup() {
		// TODO Auto-generated method stub
		return officeAttendanceGroupDao.getOfficeNotAddAttendanceGroup();
	}
	@Override
	public Integer update(OfficeAttendanceGroup officeAttendanceGroup,
			List<User> users) {
		// TODO Auto-generated method stub
		return null;
	}
	
}