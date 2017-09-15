package net.zdsoft.office.meeting.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.zdsoft.eis.base.common.dao.DeptDao;
import net.zdsoft.eis.base.common.entity.Dept;
import net.zdsoft.eis.base.common.entity.Teacher;
import net.zdsoft.eis.base.common.entity.User;
import net.zdsoft.eis.base.common.service.DeptService;
import net.zdsoft.eis.base.common.service.TeacherService;
import net.zdsoft.eis.base.common.service.UserService;
import net.zdsoft.keel.util.Pagination;
import net.zdsoft.office.meeting.dao.OfficeWorkMeetingDao;
import net.zdsoft.office.meeting.entity.MeetingsInfoCondition;
import net.zdsoft.office.meeting.entity.OfficeDeptLeader;
import net.zdsoft.office.meeting.entity.OfficeWorkMeeting;
import net.zdsoft.office.meeting.entity.OfficeWorkMeetingAttend;
import net.zdsoft.office.meeting.entity.OfficeWorkMeetingConfirm;
import net.zdsoft.office.meeting.entity.OfficeWorkMeetingMinutes;
import net.zdsoft.office.meeting.service.OfficeDeptLeaderService;
import net.zdsoft.office.meeting.service.OfficeWorkMeetingAttendService;
import net.zdsoft.office.meeting.service.OfficeWorkMeetingConfirmService;
import net.zdsoft.office.meeting.service.OfficeWorkMeetingMinutesService;
import net.zdsoft.office.meeting.service.OfficeWorkMeetingService;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

/**
 * office_work_meeting
 * @author 
 * 
 */
public class OfficeWorkMeetingServiceImpl implements OfficeWorkMeetingService{
	private OfficeWorkMeetingDao officeWorkMeetingDao;
	private OfficeWorkMeetingAttendService officeWorkMeetingAttendService; 
	private DeptDao deptDao;
	private UserService userService;
	private DeptService deptService;
	private TeacherService teacherService;
	private OfficeDeptLeaderService officeDeptLeaderService;
	private OfficeWorkMeetingConfirmService officeWorkMeetingConfirmService;
	private OfficeWorkMeetingMinutesService officeWorkMeetingMinutesService;

	@Override
	public OfficeWorkMeeting save(OfficeWorkMeeting meeting){

		if(StringUtils.isBlank(meeting.getId())){
			meeting = officeWorkMeetingDao.save(meeting);
			List<OfficeWorkMeetingAttend> meetingsAttendsList = new ArrayList<OfficeWorkMeetingAttend>(); 
			//部门，单位信息保存
			if(StringUtils.isNotBlank(meeting.getHostDept())){
				String[] deptIds = meeting.getHostDept().split(",");
				for(String deptId:deptIds){
					OfficeWorkMeetingAttend meetingsAttends = new OfficeWorkMeetingAttend();
					meetingsAttends.setMeetingId(meeting.getId());
					meetingsAttends.setType(OfficeWorkMeetingAttend.TYPE_HOST_DEPT);
					meetingsAttends.setObjectId(deptId);
					meetingsAttendsList.add(meetingsAttends);
				}
			}
			if(StringUtils.isNotBlank(meeting.getOtherDept())){
				String[] deptIds = meeting.getOtherDept().split(",");
				for(String deptId:deptIds){
					OfficeWorkMeetingAttend meetingsAttends = new OfficeWorkMeetingAttend();
					meetingsAttends.setMeetingId(meeting.getId());
					meetingsAttends.setType(OfficeWorkMeetingAttend.TYPE_OTHER_DEPT);
					meetingsAttends.setObjectId(deptId);
					meetingsAttendsList.add(meetingsAttends);
				}
			}
			if(StringUtils.isNotBlank(meeting.getLeader())){
				String[] leaders = meeting.getLeader().split(",");
				for(String leader:leaders){
					OfficeWorkMeetingAttend meetingsAttends = new OfficeWorkMeetingAttend();
					meetingsAttends.setMeetingId(meeting.getId());
					meetingsAttends.setType(OfficeWorkMeetingAttend.TYPE_LEADER);
					meetingsAttends.setObjectId(leader);
					meetingsAttendsList.add(meetingsAttends);
				}
			}
			officeWorkMeetingAttendService.batchSave(meetingsAttendsList);
        }else{ 
        	// 数据库中已经有数据
        	officeWorkMeetingDao.updateEdit(meeting);
        	List<OfficeWorkMeetingAttend> meetingsAttendsList = new ArrayList<OfficeWorkMeetingAttend>(); 
			//部门，单位信息保存
        	if(StringUtils.isNotBlank(meeting.getHostDept())){
				String[] deptIds = meeting.getHostDept().split(",");
				for(String deptId:deptIds){
					OfficeWorkMeetingAttend meetingsAttends = new OfficeWorkMeetingAttend();
					meetingsAttends.setMeetingId(meeting.getId());
					meetingsAttends.setType(OfficeWorkMeetingAttend.TYPE_HOST_DEPT);
					meetingsAttends.setObjectId(deptId);
					meetingsAttendsList.add(meetingsAttends);
				}
			}
			if(StringUtils.isNotBlank(meeting.getOtherDept())){
				String[] deptIds = meeting.getOtherDept().split(",");
				for(String deptId:deptIds){
					OfficeWorkMeetingAttend meetingsAttends = new OfficeWorkMeetingAttend();
					meetingsAttends.setMeetingId(meeting.getId());
					meetingsAttends.setType(OfficeWorkMeetingAttend.TYPE_OTHER_DEPT);
					meetingsAttends.setObjectId(deptId);
					meetingsAttendsList.add(meetingsAttends);
				}
			}
			if(StringUtils.isNotBlank(meeting.getLeader())){
				String[] leaders = meeting.getLeader().split(",");
				for(String leader:leaders){
					OfficeWorkMeetingAttend meetingsAttends = new OfficeWorkMeetingAttend();
					meetingsAttends.setMeetingId(meeting.getId());
					meetingsAttends.setType(OfficeWorkMeetingAttend.TYPE_LEADER);
					meetingsAttends.setObjectId(leader);
					meetingsAttendsList.add(meetingsAttends);
				}
			}
			officeWorkMeetingAttendService.deleteByMeetingId(meeting.getId(), 0);
			officeWorkMeetingAttendService.batchSave(meetingsAttendsList);
        }
		return meeting;
	}

	@Override
	public Integer delete(String[] ids){
		return officeWorkMeetingDao.delete(ids);
	}
	@Override
	public void submitMeeting(String id) {
		officeWorkMeetingDao.submitMeeting(id);
	}
	@Override
	public void publishMeeting(String id) {
		officeWorkMeetingDao.publishMeeting(id);
	}
	@Override
	public void updateAudit(OfficeWorkMeeting meeting) {
		officeWorkMeetingDao.updateAudit(meeting);
	}
	@Override
	public Integer update(OfficeWorkMeeting officeWorkMeeting){
		return officeWorkMeetingDao.update(officeWorkMeeting);
	}
	@Override
	public OfficeWorkMeeting getMeetingsAttendInfoById(String id) {
		// TODO Auto-generated method stub

		OfficeWorkMeeting meetingsInfo = officeWorkMeetingDao.getOfficeWorkMeetingById(id);
		if(meetingsInfo!=null){
			String attendNames = "";//确认参会人员
			String notAttendNames = "";//确认不参会人员
			String notSureNames = "";//不确定是否参会
			String noLeaders = "";//未设置负责人的部门
			
			int attendNum = 0;//确认参会人员个数
			int notAttendNum = 0;//确认不参会人员个数
			int notSureNum = 0;//不确定是否参会个数
			int noLeadersNum = 0;//未设置负责人的部门个数
			
			Map<String, User> userMap = userService.getUserMap(meetingsInfo.getUnitId());
			Map<String, Dept> deptMap = deptService.getDeptMap(meetingsInfo.getUnitId());
			List<OfficeWorkMeetingAttend> meetingsAttendsList = officeWorkMeetingAttendService.getMeetingsAttendsListByMeetingIds(new String[]{meetingsInfo.getId()});
			Set<String> deptIds = new HashSet<String>();//部门ids
			Set<String> userIds = new HashSet<String>();//领导人ids
			//部门及参会领导获取
			for(OfficeWorkMeetingAttend meetingsAttends:meetingsAttendsList){
				if(meetingsAttends.getType() == OfficeWorkMeetingAttend.TYPE_HOST_DEPT || meetingsAttends.getType() == OfficeWorkMeetingAttend.TYPE_OTHER_DEPT){
					deptIds.add(meetingsAttends.getObjectId());
				}
				if(meetingsAttends.getType() == OfficeWorkMeetingAttend.TYPE_LEADER){
					userIds.add(meetingsAttends.getObjectId());
				}
			}
			//获取部门负责人Map,key:deptId,value:userId
			Map<String, String> leaderMap = officeDeptLeaderService.getLeaderMap(deptIds.toArray(new String[0]));
			for(String deptId:deptIds){
				if(leaderMap.get(deptId)!=null){
					userIds.add(leaderMap.get(deptId));
				}else{
					//部门被删除的话，就不计算在内了
					if(deptMap.get(deptId)!=null){
						if(StringUtils.isBlank(noLeaders)){
							noLeaders += deptMap.get(deptId);
							noLeadersNum++;
						}else{
							noLeaders += "," + deptMap.get(deptId);
							noLeadersNum++;
						}
					}
				}
			}
			
			Map<String, OfficeWorkMeetingConfirm> attendMap = officeWorkMeetingConfirmService.getMeetingsConfirmMapByMeetingsId(meetingsInfo.getId());
			//可能存在一个领导交接给其中一个负责人，这样的话  userId会有重复的情况，过滤下
			Set<String> needAttendUserIdSet = new HashSet<String>();
			for(String userId:userIds){
				if(attendMap.get(userId)!=null){
					needAttendUserIdSet.add(getUserId(userId,attendMap));
				}else{
					//人员不在本单位的话，就不计算在内了
					if(userMap.get(userId)!=null){
						if(StringUtils.isBlank(notSureNames)){
							notSureNames += userMap.get(userId).getRealname();
							notSureNum++;
						}else{
							notSureNames += "," + userMap.get(userId).getRealname();
							notSureNum++;
						}
					}
				}
			}
			for(String userId:needAttendUserIdSet){
				OfficeWorkMeetingConfirm mc = attendMap.get(userId);
				if(userMap.get(userId)!=null){
					if(OfficeWorkMeetingConfirm.TYPE_ATTEND == mc.getAttendType()){
						if(StringUtils.isBlank(attendNames)){
							attendNames += userMap.get(userId).getRealname();
							attendNum++;
						}else{
							attendNames += "," + userMap.get(userId).getRealname();
							attendNum++;
						}
					}else if(OfficeWorkMeetingConfirm.TYPE_NOT_ATTEND == mc.getAttendType()){
						if(StringUtils.isBlank(notAttendNames)){
							notAttendNames += userMap.get(userId).getRealname()+"("+mc.getRemark()+")";
							notAttendNum++;
						}else{
							notAttendNames += "," + userMap.get(userId).getRealname()+"("+mc.getRemark()+")";
							notAttendNum++;
						}
					}else if(OfficeWorkMeetingConfirm.TYPE_ZERO == mc.getAttendType()){
						if(StringUtils.isBlank(notSureNames)){
							notSureNames += userMap.get(userId).getRealname();
							notSureNum++;
						}else{
							notSureNames += "," + userMap.get(userId).getRealname();
							notSureNum++;
						}
					}
				}
			}
			meetingsInfo.setAttendNames(attendNames);
			meetingsInfo.setAttendNum(attendNum);
			meetingsInfo.setNotAttendNames(notAttendNames);
			meetingsInfo.setNotAttendNum(notAttendNum);
			meetingsInfo.setNotSureNames(notSureNames);
			meetingsInfo.setNotSureNum(notSureNum);
//			meetingsInfo.setNoLeaders(noLeaders);
//			meetingsInfo.setNoLeadersNum(noLeadersNum);
		}else{
			meetingsInfo = new OfficeWorkMeeting();
		}
		return meetingsInfo;
	}
	private String getUserId(String userId,Map<String, OfficeWorkMeetingConfirm> attendMap){
		if(OfficeWorkMeetingConfirm.TYPE_TRANSFER==attendMap.get(userId).getAttendType()){
			for(int i=0;i<attendMap.size();i++){
				OfficeWorkMeetingConfirm meetingsConfirm = attendMap.get(userId);
				if(OfficeWorkMeetingConfirm.TYPE_TRANSFER == meetingsConfirm.getAttendType()){
					userId = meetingsConfirm.getTransferUserId();
				}else{
					break;
				}
			}
		}
		return userId;
	}
	@Override
	public OfficeWorkMeeting getOfficeWorkMeetingById(String id){

		OfficeWorkMeeting meeting =  officeWorkMeetingDao.getOfficeWorkMeetingById(id);
		if(meeting != null){
			Map<String,List<OfficeWorkMeetingAttend>> attmap  = officeWorkMeetingAttendService.getOfficeWorkMeetingAttendMap(new String[]{id});
			Map<String,Dept> deptMap = deptService.getDeptMap(meeting.getUnitId());
			Map<String,User> userMap = userService.getUserWithDelMap(meeting.getUnitId());
			if(attmap.containsKey(id + "," + OfficeWorkMeetingAttend.TYPE_HOST_DEPT)){
				List<OfficeWorkMeetingAttend> attlist = attmap.get(id + "," + OfficeWorkMeetingAttend.TYPE_HOST_DEPT);
				for(OfficeWorkMeetingAttend att : attlist){
					if(deptMap.containsKey(att.getObjectId())){
						if(StringUtils.isBlank(meeting.getHostDeptStr())){
							meeting.setHostDept(att.getObjectId());
							meeting.setHostDeptStr(deptMap.get(att.getObjectId()).getDeptname());
						}else{
							meeting.setHostDept(meeting.getHostDept() + "," + att.getObjectId());
							meeting.setHostDeptStr(meeting.getHostDeptStr()+ ","+deptMap.get(att.getObjectId()).getDeptname());
						}
					}
				}
				
			}
			if(attmap.containsKey(id + "," + OfficeWorkMeetingAttend.TYPE_OTHER_DEPT)){
				List<OfficeWorkMeetingAttend> attlist = attmap.get(id + "," + OfficeWorkMeetingAttend.TYPE_OTHER_DEPT);
				for(OfficeWorkMeetingAttend att : attlist){
					if(deptMap.containsKey(att.getObjectId())){
						if(StringUtils.isBlank(meeting.getOtherDeptStr())){
							meeting.setOtherDept(att.getObjectId());
							meeting.setOtherDeptStr(deptMap.get(att.getObjectId()).getDeptname());
						}else{
							meeting.setOtherDeptStr(meeting.getOtherDeptStr()+ ","+deptMap.get(att.getObjectId()).getDeptname());
							meeting.setOtherDept(meeting.getOtherDept() + "," + att.getObjectId());
						}
					}
				}
			}
			if(attmap.containsKey(id + "," + OfficeWorkMeetingAttend.TYPE_LEADER)){
				List<OfficeWorkMeetingAttend> attlist = attmap.get(id + "," + OfficeWorkMeetingAttend.TYPE_LEADER);
				for(OfficeWorkMeetingAttend att : attlist){
					if(userMap.containsKey(att.getObjectId())){
						if(StringUtils.isBlank(meeting.getLeaderStr())){
							meeting.setLeader(att.getObjectId());
							meeting.setLeaderStr(userMap.get(att.getObjectId()).getRealname());
						}else{
							meeting.setLeader(meeting.getLeader() + "," + att.getObjectId());
							meeting.setLeaderStr(meeting.getLeaderStr()+ ","+userMap.get(att.getObjectId()).getRealname());
						}
					}
				}
			}
			if(userMap.containsKey(meeting.getMinutesPeople()))
				meeting.setMinutesPeopleStr(userMap.get(meeting.getMinutesPeople()).getRealname());
			if(StringUtils.isNotBlank(meeting.getAuditUserId()) && userMap.containsKey(meeting.getAuditUserId())){
				meeting.setAuditUser(userMap.get(meeting.getAuditUserId()).getRealname());
			}
		}
		return meeting;

	}

	@Override
	public Map<String, OfficeWorkMeeting> getOfficeWorkMeetingMapByIds(String[] ids){
		return officeWorkMeetingDao.getOfficeWorkMeetingMapByIds(ids);
	}

	@Override
	public List<OfficeWorkMeeting> getOfficeWorkMeetingList(){
		return officeWorkMeetingDao.getOfficeWorkMeetingList();
	}

	@Override
	public List<OfficeWorkMeeting> getOfficeWorkMeetingPage(Pagination page){
		return officeWorkMeetingDao.getOfficeWorkMeetingPage(page);
	}

	@Override
	public List<OfficeWorkMeeting> getOfficeWorkMeetingByUnitIdList(String unitId){
		return officeWorkMeetingDao.getOfficeWorkMeetingByUnitIdList(unitId);
	}

	@Override
	public List<OfficeWorkMeeting> getOfficeWorkMeetingByUnitIdPage(String unitId, Pagination page){
		return officeWorkMeetingDao.getOfficeWorkMeetingByUnitIdPage(unitId, page);
	}
	
	@Override
	public List<OfficeWorkMeeting> getMeetingInfoPage(
			MeetingsInfoCondition mic, Pagination page) {
		List<OfficeWorkMeeting> meetinglist = new ArrayList<OfficeWorkMeeting>();
		meetinglist = officeWorkMeetingDao.getMeetingInfoPage(mic,page);
		if(CollectionUtils.isNotEmpty(meetinglist)){
			Set<String> meetingsIdSet = new HashSet<String>();
			for(OfficeWorkMeeting meet : meetinglist){
				meetingsIdSet.add(meet.getId());
			}
			Map<String,List<OfficeWorkMeetingAttend>> attmap  = officeWorkMeetingAttendService.getOfficeWorkMeetingAttendMap(meetingsIdSet.toArray(new String[0]));
			Map<String,Dept> deptMap = deptService.getDeptMap(mic.getUnitId());
			Map<String,User> userMap = userService.getUserWithDelMap(mic.getUnitId());
			for(OfficeWorkMeeting meet : meetinglist){
				if(attmap.containsKey(meet.getId() + "," + OfficeWorkMeetingAttend.TYPE_HOST_DEPT)){
					List<OfficeWorkMeetingAttend> attlist = attmap.get(meet.getId() + "," + OfficeWorkMeetingAttend.TYPE_HOST_DEPT);
					for(OfficeWorkMeetingAttend att : attlist){
						if(deptMap.containsKey(att.getObjectId())){
							if(StringUtils.isBlank(meet.getHostDeptStr())){
								meet.setHostDept(att.getObjectId());
								meet.setHostDeptStr(deptMap.get(att.getObjectId()).getDeptname());
							}else{
								meet.setHostDept(meet.getHostDept() + "," + att.getObjectId());
								meet.setHostDeptStr(meet.getHostDeptStr()+ ","+deptMap.get(att.getObjectId()).getDeptname());
							}
						}
					}
					if(StringUtils.isBlank(meet.getHostDeptStr())){
						meet.setHostDeptStr("部门已删除");
					}
				}
				if(attmap.containsKey(meet.getId() + "," + OfficeWorkMeetingAttend.TYPE_LEADER)){
					List<OfficeWorkMeetingAttend> attlist = attmap.get(meet.getId() + "," + OfficeWorkMeetingAttend.TYPE_LEADER);
					for(OfficeWorkMeetingAttend att : attlist){
						if(userMap.containsKey(att.getObjectId())){
							if(StringUtils.isBlank(meet.getLeaderStr())){
								meet.setLeader(att.getObjectId());
								meet.setLeaderStr(userMap.get(att.getObjectId()).getRealname());
							}else{
								meet.setLeader(meet.getLeader() + "," + att.getObjectId());
								meet.setLeaderStr(meet.getLeaderStr()+ ","+userMap.get(att.getObjectId()).getRealname());
							}
						}
					}
				}
				if(userMap.containsKey(meet.getMinutesPeople()))
					meet.setMinutesPeopleStr(userMap.get(meet.getMinutesPeople()).getRealname());
			}
		}
		return meetinglist;
	}
	
	@Override
	public List<OfficeWorkMeeting> getOfficeWorkMeetingByConditionList(
			MeetingsInfoCondition mic, Pagination page) {
		List<OfficeWorkMeeting> meetinglist = new ArrayList<OfficeWorkMeeting>();
		String userId = mic.getUserId();
		String unitId = mic.getUnitId();
		String meetingName = mic.getMeetingName();
		Date startTime = mic.getStartDate();
		Date endTime = mic.getEndDate();
		String isEnd = mic.getIsEnd();
		List<String> meetingsIds = new ArrayList<String>();
		Set<String> meetingsIdSet = new HashSet<String>();
		User user = userService.getUser(userId);
		Teacher teacher = teacherService.getTeacher(user.getTeacherid());
		String deptId = teacher.getDeptid();
		//判断自己是不是部门负责人
		OfficeDeptLeader leaderlist = officeDeptLeaderService.getOfficeDeptLeader(unitId,userId,deptId);
		String[] types;
		if(leaderlist != null){
			types = new String[]{OfficeWorkMeetingAttend.TYPE_HOST_DEPT+"",OfficeWorkMeetingAttend.TYPE_OTHER_DEPT+"",OfficeWorkMeetingAttend.TYPE_LEADER+""};
		}else{
			types = new String[]{OfficeWorkMeetingAttend.TYPE_LEADER+""};
			deptId = null;
		}
		//获取初始设置需要参加的会议ids
		meetingsIds = officeWorkMeetingAttendService.getMeetingsIds(userId, deptId,types);
		meetingsIdSet.addAll(meetingsIds);
		//获取转交参会的会议ids
		meetingsIds = officeWorkMeetingConfirmService.getMeetingsIdByUserId(userId);
		meetingsIdSet.addAll(meetingsIds);
		//取到的会议
		meetinglist = officeWorkMeetingDao.getMeetingsInfoList(unitId, isEnd, meetingName,startTime,endTime, meetingsIdSet.toArray(new String[0]),page);
		Map<String,List<OfficeWorkMeetingAttend>> attmap  = officeWorkMeetingAttendService.getOfficeWorkMeetingAttendMap(meetingsIdSet.toArray(new String[0]));
		Map<String,Dept> deptMap = deptService.getDeptMap(unitId);
		//纪要
		Map<String,OfficeWorkMeetingMinutes> minutesMap = officeWorkMeetingMinutesService.getOfficeWorkMeetingMinutesMap();
		for(OfficeWorkMeeting meet : meetinglist){
			if(minutesMap.containsKey(meet.getId())){
				meet.setMinutesId(minutesMap.get(meet.getId()).getId());
			}
			if(attmap.containsKey(meet.getId() + "," + OfficeWorkMeetingAttend.TYPE_HOST_DEPT)){
				List<OfficeWorkMeetingAttend> attlist = attmap.get(meet.getId() + "," + OfficeWorkMeetingAttend.TYPE_HOST_DEPT);
				for(OfficeWorkMeetingAttend att : attlist){
					if(deptMap.containsKey(att.getObjectId())){
						if(StringUtils.isBlank(meet.getHostDeptStr())){
							meet.setHostDeptStr(deptMap.get(att.getObjectId()).getDeptname());
							meet.setHostDept(att.getObjectId());
						}else{
							meet.setHostDept(meet.getHostDept() + "," + att.getObjectId());
							meet.setHostDeptStr(meet.getHostDeptStr()+ ","+deptMap.get(att.getObjectId()).getDeptname());
						}
					}
				}
			}
		}
		Map<String, User> userMap = userService.getUserWithDelMap(unitId);
		getDetailInfoList(userId, userMap, meetinglist);
		return meetinglist;
	}
	
	private void getDetailInfoList(String userId, Map<String, User> userMap,
			List<OfficeWorkMeeting> meetinglist) {
		for(OfficeWorkMeeting mi : meetinglist){
			Map<String, String> transferMap = officeWorkMeetingConfirmService.getTransferMapByMeetingId(mi.getId());
			Map<String, OfficeWorkMeetingConfirm> attendMap = officeWorkMeetingConfirmService.getMeetingConfirmMapByMeetingId(mi.getId());
			OfficeWorkMeetingConfirm meetingsConfirm = attendMap.get(userId);
			String transferDetail = "";
			if(meetingsConfirm!=null){
				if(transferMap.get(userId)!=null){
					transferDetail = setPreTransfer(transferMap, userMap, userId);
				}
				mi.setAttendState(meetingsConfirm.getAttendType());
				if(meetingsConfirm.getAttendType() == OfficeWorkMeetingConfirm.TYPE_TRANSFER){
					if(StringUtils.isBlank(transferDetail)){
						transferDetail = userMap.get(userId).getRealname();
					}
					transferDetail = setNexTransfer(attendMap, userMap, userId, transferDetail);
				}else{
					if(StringUtils.isNotBlank(transferDetail)){
						if(OfficeWorkMeetingConfirm.TYPE_ZERO == meetingsConfirm.getAttendType()){
							transferDetail = transferDetail + "(未确认是否参会)";
						}else if(OfficeWorkMeetingConfirm.TYPE_ATTEND == meetingsConfirm.getAttendType()){
							transferDetail = transferDetail + "(确认参会)";
						}else{
							transferDetail = transferDetail + "(确认不参会-"+meetingsConfirm.getRemark()+")";
						}
					}else{
						if(OfficeWorkMeetingConfirm.TYPE_ZERO == meetingsConfirm.getAttendType()){
							transferDetail = "未确认是否参会";
						}else if(OfficeWorkMeetingConfirm.TYPE_ATTEND == meetingsConfirm.getAttendType()){
							transferDetail = "确认参会";
						}else{
							transferDetail = "确认不参会-"+meetingsConfirm.getRemark();
						}
					}
				}
				mi.setTransferDetail(transferDetail);
			}else{
				mi.setAttendState(OfficeWorkMeetingConfirm.TYPE_ZERO);//未确定是否参会
			}
		}
	}

	private String setNexTransfer(
			Map<String, OfficeWorkMeetingConfirm> attendMap,
			Map<String, User> userMap, String userId, String transferDetail) {
		userId = attendMap.get(userId).getTransferUserId();
		String realName = "";
		for(int i=0;i<attendMap.size();i++){
			OfficeWorkMeetingConfirm meetingsConfirm = attendMap.get(userId);
			if(OfficeWorkMeetingConfirm.TYPE_TRANSFER ==meetingsConfirm.getAttendType()){
				realName = userMap.get(userId).getRealname();
				if(StringUtils.isNotBlank(realName)){
					transferDetail = transferDetail + ">" + realName;
				}else{
					transferDetail = transferDetail+">该人员已不在该单位";
				}
				userId = meetingsConfirm.getTransferUserId();
			}else{
				realName = userMap.get(userId).getRealname();
				if(StringUtils.isNotBlank(realName)){
					transferDetail = transferDetail + ">" + realName;
				}else{
					transferDetail = transferDetail+">该人员已不在该单位";
				}
				if(OfficeWorkMeetingConfirm.TYPE_ZERO == meetingsConfirm.getAttendType()){
					transferDetail = transferDetail + "(未确认是否参会)";
				}else if(OfficeWorkMeetingConfirm.TYPE_ATTEND == meetingsConfirm.getAttendType()){
					transferDetail = transferDetail + "(确认参会)";
				}else{
					transferDetail = transferDetail + "(确认不参会-"+meetingsConfirm.getRemark()+")";
				}
				break;
			}
		}
		return transferDetail;
	}

	private String setPreTransfer(Map<String, String> transferMap,
			Map<String, User> userMap, String userId) {
		String str = userMap.get(userId).getRealname();
		userId = transferMap.get(userId);
		String realName = "";
		for(int i=0;i<=transferMap.size();i++){
			if(i==0){
				realName = userMap.get(userId).getRealname();
				if(StringUtils.isNotBlank(realName)){
					str = realName + ">" + str;
				}else{
					str = "该人员已不在该单位>" + str;
				}
				userId = transferMap.get(userId);
			}else{
				if(StringUtils.isNotBlank(transferMap.get(userId))){
					realName = userMap.get(userId).getRealname();
					if(StringUtils.isNotBlank(realName)){
						str = realName + ">" + str;
					}else{
						str = "该人员已不在该单位>" + str;
					}
					userId = transferMap.get(userId);
				}else{
					if(StringUtils.isNotBlank(userId)){
						realName = userMap.get(userId).getRealname();
						if(StringUtils.isNotBlank(realName)){
							str = realName + ">" + str;
						}else{
							str = "该人员已不在该单位>" + str;
						}
					}
					break;
				}
			}
		}
		return str;
	}

	public void setOfficeWorkMeetingDao(OfficeWorkMeetingDao officeWorkMeetingDao){
		this.officeWorkMeetingDao = officeWorkMeetingDao;
	}

	@Override
	public List<OfficeWorkMeeting> getOfficeWorkMeetingListBySearchParams(
			String unitId, String meetName, Date startTime, Date endTime,
			Pagination page) {
		List<OfficeWorkMeeting> workMeetingList=officeWorkMeetingDao.getOfficeWorkMeetingListBySearchParams(unitId, meetName, startTime, endTime, page);
		if(CollectionUtils.isEmpty(workMeetingList)){
			return workMeetingList;
		}
		String[] meetingIds=new String[workMeetingList.size()];
		int i=0;
		for(OfficeWorkMeeting work:workMeetingList){
			meetingIds[i]=work.getId();
			i++;
		}
		Map<String,List<OfficeWorkMeetingAttend>> attmap  = officeWorkMeetingAttendService.getOfficeWorkMeetingAttendMap(meetingIds);
		List<OfficeWorkMeetingAttend> attendList=officeWorkMeetingAttendService.getOfficeWorkMeetingAttendListByMeetingIds(meetingIds);
//		Map<String, OfficeWorkMeetingAttend> attendMap=officeWorkMeetingAttendService.getattendMapByMeetingId(meetingIds);
		Map<String, Dept> deptMap=deptDao.getDeptMap(unitId);
		StringBuffer deptNames=new StringBuffer();
		for(OfficeWorkMeeting work:workMeetingList){
			if(attmap.containsKey(work.getId() + "," + OfficeWorkMeetingAttend.TYPE_HOST_DEPT)){
				List<OfficeWorkMeetingAttend> attendlist=attmap.get(work.getId() + "," + OfficeWorkMeetingAttend.TYPE_HOST_DEPT);
				for(OfficeWorkMeetingAttend att:attendlist){
					if(deptMap.containsKey(att.getObjectId())){
						if(StringUtils.isBlank(work.getHostDeptStr())){
							work.setHostDept(att.getObjectId());
							work.setHostDeptStr(deptMap.get(att.getObjectId()).getDeptname());
						}else{
							work.setHostDept(work.getHostDept() + "," + att.getObjectId());
							work.setHostDeptStr(work.getHostDeptStr()+ ","+deptMap.get(att.getObjectId()).getDeptname());
						}
					}
				}
			}
		}
		return workMeetingList;
	}

	public void setOfficeWorkMeetingAttendService(
			OfficeWorkMeetingAttendService officeWorkMeetingAttendService) {
		this.officeWorkMeetingAttendService = officeWorkMeetingAttendService;
	}

	public void setDeptDao(DeptDao deptDao) {
		this.deptDao = deptDao;
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

	public void setOfficeDeptLeaderService(
			OfficeDeptLeaderService officeDeptLeaderService) {
		this.officeDeptLeaderService = officeDeptLeaderService;
	}


	public void setOfficeWorkMeetingConfirmService(
			OfficeWorkMeetingConfirmService officeWorkMeetingConfirmService) {
		this.officeWorkMeetingConfirmService = officeWorkMeetingConfirmService;
	}

	
	public void setOfficeWorkMeetingMinutesService(
			OfficeWorkMeetingMinutesService officeWorkMeetingMinutesService) {
		this.officeWorkMeetingMinutesService = officeWorkMeetingMinutesService;
	}

	@Override
	public List<OfficeWorkMeeting> getOfficeWorkMeetingManageListByParams(
			String unitId, String userId, String meetName, Pagination page) {
		List<OfficeWorkMeeting> meetManagelist=officeWorkMeetingDao.getOfficeWorkMeetingManageListByParams(unitId, userId, meetName, page);
		Date today=new Date();
		for(OfficeWorkMeeting off:meetManagelist){
			if(today.after(off.getMeetingDate())){
				off.setSueState(OfficeWorkMeeting.HAS_END);//已结束
			}else{
				off.setSueState(OfficeWorkMeeting.NOT_END);//未结束
			}
		}
		return meetManagelist;
	}

}