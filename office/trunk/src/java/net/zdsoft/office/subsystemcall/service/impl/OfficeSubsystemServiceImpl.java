package net.zdsoft.office.subsystemcall.service.impl;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.zdsoft.eis.base.common.entity.CustomRole;
import net.zdsoft.eis.base.common.entity.CustomRoleUser;
import net.zdsoft.eis.base.common.entity.Dept;
import net.zdsoft.eis.base.common.entity.Mcodedetail;
import net.zdsoft.eis.base.common.entity.Module;
import net.zdsoft.eis.base.common.entity.Student;
import net.zdsoft.eis.base.common.entity.Teacher;
import net.zdsoft.eis.base.common.entity.TeacherDuty;
import net.zdsoft.eis.base.common.entity.Unit;
import net.zdsoft.eis.base.common.entity.User;
import net.zdsoft.eis.base.common.service.CustomRoleService;
import net.zdsoft.eis.base.common.service.CustomRoleUserService;
import net.zdsoft.eis.base.common.service.DeptService;
import net.zdsoft.eis.base.common.service.McodedetailService;
import net.zdsoft.eis.base.common.service.ModuleService;
import net.zdsoft.eis.base.common.service.StudentService;
import net.zdsoft.eis.base.common.service.SystemIniService;
import net.zdsoft.eis.base.common.service.TeacherDutyService;
import net.zdsoft.eis.base.common.service.TeacherService;
import net.zdsoft.eis.base.common.service.UnitService;
import net.zdsoft.eis.base.common.service.UserService;
import net.zdsoft.eis.base.constant.BaseConstant;
import net.zdsoft.eis.base.deploy.SystemDeployService;
import net.zdsoft.eis.base.subsystemcall.entity.OfficeMsgSendingDto;
import net.zdsoft.eis.base.subsystemcall.entity.OfficeStepInfoDto;
import net.zdsoft.eis.base.subsystemcall.service.OfficeSubsystemService;
import net.zdsoft.eis.component.addressbook.constant.GroupConstant;
import net.zdsoft.eis.component.addressbook.entity.CustomGroup;
import net.zdsoft.eis.component.addressbook.entity.CustomGroupItem;
import net.zdsoft.eis.component.addressbook.service.CustomGroupItemService;
import net.zdsoft.eis.component.addressbook.service.CustomGroupService;
import net.zdsoft.eis.sms.dto.MsgDto;
import net.zdsoft.keel.util.Pagination;
import net.zdsoft.keelcnet.action.UploadFile;
import net.zdsoft.office.bulletin.entity.OfficeBulletin;
import net.zdsoft.office.bulletin.service.OfficeBulletinService;
import net.zdsoft.office.dailyoffice.entity.OfficeSignedOn;
import net.zdsoft.office.dailyoffice.entity.OfficeSigntimeSet;
import net.zdsoft.office.dailyoffice.entity.OfficeWorkArrangeOutline;
import net.zdsoft.office.dailyoffice.service.OfficeSignedOnService;
import net.zdsoft.office.dailyoffice.service.OfficeSigntimeSetService;
import net.zdsoft.office.dailyoffice.service.OfficeWorkArrangeOutlineService;
import net.zdsoft.office.enums.WeikeAppUrlEnum;
import net.zdsoft.office.msgcenter.entity.OfficeBusinessJump;
import net.zdsoft.office.msgcenter.entity.OfficeMsgReceiving;
import net.zdsoft.office.msgcenter.entity.OfficeMsgSending;
import net.zdsoft.office.msgcenter.service.OfficeMsgReceivingService;
import net.zdsoft.office.msgcenter.service.OfficeMsgSendingService;
import net.zdsoft.office.officeFlow.dto.HisTask;
import net.zdsoft.office.officeFlow.entity.OfficeFlowStepInfo;
import net.zdsoft.office.officeFlow.service.OfficeFlowService;
import net.zdsoft.office.officeFlow.service.OfficeFlowStepInfoService;
import net.zdsoft.office.qrCode.service.QrCodeService;
import net.zdsoft.office.remote.service.RemoteOfficeAppService;
import net.zdsoft.office.remote.service.RemoteTacherAttendanceService;
import net.zdsoft.office.studentLeave.entity.OfficeHwstudentLeave;
import net.zdsoft.office.studentLeave.entity.OfficeLeavetypeGeneral;
import net.zdsoft.office.studentLeave.entity.OfficeLeavetypeLive;
import net.zdsoft.office.studentLeave.entity.OfficeLeavetypeLong;
import net.zdsoft.office.studentLeave.entity.OfficeLeavetypeTemporary;
import net.zdsoft.office.studentLeave.entity.OfficeStudentLeave;
import net.zdsoft.office.studentLeave.service.OfficeHwstudentLeaveService;
import net.zdsoft.office.studentLeave.service.OfficeLeavetypeGeneralService;
import net.zdsoft.office.studentLeave.service.OfficeLeavetypeLiveService;
import net.zdsoft.office.studentLeave.service.OfficeLeavetypeLongService;
import net.zdsoft.office.studentLeave.service.OfficeLeavetypeTemporaryService;
import net.zdsoft.office.studentLeave.service.OfficeStudentLeaveService;
import net.zdsoft.office.teacherLeave.entity.OfficeTeacherLeave;
import net.zdsoft.office.teacherLeave.service.OfficeTeacherLeaveService;
import net.zdsoft.office.util.Constants;

import org.apache.commons.lang.StringUtils;
import org.springframework.util.CollectionUtils;

import com.alibaba.fastjson.JSON;

/**
 * @author chens
 * @version 创建时间：2015-7-21 下午6:43:56
 * 
 */
public class OfficeSubsystemServiceImpl implements OfficeSubsystemService{

	private OfficeMsgSendingService officeMsgSendingService;
	private OfficeStudentLeaveService officeStudentLeaveService;
	private OfficeSigntimeSetService officeSigntimeSetService;
	private OfficeSignedOnService officeSignedOnService;
	private SystemIniService systemIniService;
	private QrCodeService qrCodeService;
	private RemoteOfficeAppService remoteOfficeAppService;
	private OfficeMsgReceivingService officeMsgReceivingService;
	private CustomGroupService customGroupService;
	private UserService userService;
	private UnitService unitService;
	private CustomRoleService customRoleService;
	private CustomRoleUserService customRoleUserService;
	private SystemDeployService systemDeployService;
	private DeptService deptService;
	private McodedetailService mcodedetailService;
	private CustomGroupItemService customGroupItemService;
	private TeacherService teacherService;
	private TeacherDutyService teacherDutyService;
	private RemoteTacherAttendanceService remoteTacherAttendanceService;
	private OfficeBulletinService officeBulletinService;
	private OfficeFlowStepInfoService officeFlowStepInfoService;
	private OfficeTeacherLeaveService officeTeacherLeaveService;
	private OfficeWorkArrangeOutlineService officeWorkArrangeOutlineService;
	private ModuleService moduleService;
	private StudentService studentService;
	private OfficeFlowService officeFlowService;
	
	private OfficeHwstudentLeaveService officeHwstudentLeaveService;
	private OfficeLeavetypeLiveService officeLeavetypeLiveService;
	private OfficeLeavetypeGeneralService officeLeavetypeGeneralService;
	private OfficeLeavetypeTemporaryService officeLeavetypeTemporaryService;
	private OfficeLeavetypeLongService officeLeavetypeLongService;
	
	@Override
	public String sendMsgDetail(User user, Unit unit, String title, String content, String simpleContent,
			boolean isNoteMsg, Integer msgType, String[] userIds, List<UploadFile> uploadFileList) {
		OfficeMsgSendingDto officeMsgSending = new OfficeMsgSendingDto();
		officeMsgSending.setCreateUserId((user==null?BaseConstant.ZERO_GUID:user.getId()));
		officeMsgSending.setSendUserName((user==null||StringUtils.isBlank(user.getRealname())?"系统发送":user.getRealname()));
		officeMsgSending.setUnitId((unit==null?BaseConstant.ZERO_GUID:unit.getId()));
		officeMsgSending.setTitle(title);
		officeMsgSending.setContent(content);
		officeMsgSending.setSimpleContent(simpleContent);
		StringBuffer mainUserString=new StringBuffer();
		for(int i=0;i<userIds.length;i++){
			if(i==userIds.length-1){
				mainUserString.append(userIds[i]);
			}else{
				mainUserString.append(userIds[i]+",");
			}
		}
		officeMsgSending.setUserIds(mainUserString.toString());
		officeMsgSending.setIsNeedsms(isNoteMsg);
		officeMsgSending.setMsgType(msgType);
		
		MsgDto msgDto = null;
		if(isNoteMsg){
			msgDto = new MsgDto();
			msgDto.setUnitName((unit!=null?unit.getName():"系统单位"));
			msgDto.setUserId((user==null?BaseConstant.ZERO_GUID:user.getId()));
			msgDto.setUserName((user==null||StringUtils.isBlank(user.getRealname())?"系统发送":user.getRealname()));
			msgDto.setContent(simpleContent);
		}
		return sendMsg(officeMsgSending, uploadFileList, msgDto);
	}
	
	public String sendMsg(OfficeMsgSendingDto officeMsgSendingDto, List<UploadFile> uploadFileList, MsgDto msgDto){
		OfficeMsgSending officeMsgSending = new OfficeMsgSending();
		toEntity(officeMsgSendingDto, officeMsgSending);
		officeMsgSendingService.save(officeMsgSending, uploadFileList, msgDto);
		return officeMsgSending.getId();
	}
	
	public void sendMsg(OfficeMsgSendingDto officeMsgSendingDto, List<UploadFile> uploadFileList, MsgDto msgDto, boolean isNotMsg){
		OfficeMsgSending officeMsgSending = new OfficeMsgSending();
		toEntity(officeMsgSendingDto, officeMsgSending);
		officeMsgSendingService.save(officeMsgSending, uploadFileList, msgDto, isNotMsg);
	}
	
	public OfficeMsgSendingDto getSendMsgById(String msgId){
		OfficeMsgSendingDto officeMsgSendingDto = new OfficeMsgSendingDto();
		OfficeMsgSending officeMsgSending = officeMsgSendingService.getOfficeMsgSendingById(msgId);
		toDto(officeMsgSendingDto, officeMsgSending);
		return officeMsgSendingDto;
	}
	
	public void toDto(OfficeMsgSendingDto officeMsgSendingDto, OfficeMsgSending officeMsgSending){
		if(officeMsgSending != null){
			officeMsgSendingDto.setTitle(officeMsgSending.getTitle());
			officeMsgSendingDto.setContent(officeMsgSending.getContent());
			officeMsgSendingDto.setIsNeedsms(officeMsgSending.getIsNeedsms());
			officeMsgSendingDto.setSmsContent(officeMsgSending.getSmsContent());
			officeMsgSendingDto.setCreateUserId(officeMsgSending.getCreateUserId());
			officeMsgSendingDto.setUnitId(officeMsgSending.getUnitId());
			officeMsgSendingDto.setSimpleContent(officeMsgSending.getSimpleContent());
			officeMsgSendingDto.setUserIds(officeMsgSending.getUserIds());
			officeMsgSendingDto.setSendUserName(officeMsgSending.getSendUserName());
			officeMsgSendingDto.setMsgType(officeMsgSending.getMsgType());
			officeMsgSendingDto.setMsgType(officeMsgSending.getMsgType());
		}
	}
	
	public void toEntity(OfficeMsgSendingDto officeMsgSendingDto, OfficeMsgSending officeMsgSending){
		officeMsgSending.setTitle(officeMsgSendingDto.getTitle());
		officeMsgSending.setContent(officeMsgSendingDto.getContent());
		officeMsgSending.setIsNeedsms(officeMsgSendingDto.getIsNeedsms());
		officeMsgSending.setSmsContent(officeMsgSendingDto.getSmsContent());
		officeMsgSending.setCreateUserId(officeMsgSendingDto.getCreateUserId());
		officeMsgSending.setUnitId(officeMsgSendingDto.getUnitId());
		officeMsgSending.setSimpleContent(officeMsgSendingDto.getSimpleContent());
		officeMsgSending.setUserIds(officeMsgSendingDto.getUserIds());
		officeMsgSending.setSendUserName(officeMsgSendingDto.getSendUserName());
		officeMsgSending.setMsgType(officeMsgSendingDto.getMsgType());
		officeMsgSending.setState(Constants.MSG_STATE_SEND);
		officeMsgSending.setMsgType(officeMsgSendingDto.getMsgType());
	}

	public void setOfficeMsgSendingService(
			OfficeMsgSendingService officeMsgSendingService) {
		this.officeMsgSendingService = officeMsgSendingService;
	};
	

	//时间点获取请假学生
	public Set<String> remoteStuIsLeaveByonetime(String[] studentIds, String time){
		Set<String> stuIds = new HashSet<String>();
		SimpleDateFormat sdf = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" );
		Date date;
		try {
			date= sdf.parse(time);
		} catch (ParseException e) {
			return stuIds;
		}
		if(studentIds==null || studentIds.length<=0){
			return stuIds;
		}
		
		
		List<OfficeStudentLeave> list = officeStudentLeaveService.findStuIsLeaveBytime(date,studentIds);
		
		return findstudentIdFromList(list);
	}
	
	//时间段获取请假学生
	public Set<String> remoteStuIsLeaveBytwotime(String[] studentIds, String startTime, String endTime){
		Set<String> stuIds = new HashSet<String>();
		Date start;
		Date end;
		SimpleDateFormat sdf = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" );
		
		if(StringUtils.isBlank(startTime)){
			return stuIds;
		}
		if(StringUtils.isBlank(endTime)){
			return stuIds;
		}
		try {
			start= sdf.parse(startTime);
		} catch (ParseException e) {
			return stuIds;
		}
		try {
			end= sdf.parse(endTime);
		} catch (ParseException e) {
			return stuIds;
		}
		if(start.after(end)){
			return stuIds;
		}
		if(studentIds==null || studentIds.length<=0){
			return stuIds;
		}
		List<OfficeStudentLeave> list = officeStudentLeaveService.findStuIsLeaveBytime(start,end,studentIds);
		return findstudentIdFromList(list);
	}

	private Set<String> findstudentIdFromList(List<OfficeStudentLeave> list){
		Set<String> stuIds = new HashSet<String>();
		if(CollectionUtils.isEmpty(list)){
			return stuIds;
		}else{
			for(OfficeStudentLeave off:list){
				String stuid = off.getStudentId();
				stuIds.add(stuid);
			}
			return stuIds;
		}
	}
	
	public void setOfficeStudentLeaveService(
			OfficeStudentLeaveService officeStudentLeaveService) {
		this.officeStudentLeaveService = officeStudentLeaveService;
	}

	@Override
	public boolean getOfficeSigntimeSet(String unitId, String time) {
		OfficeSigntimeSet officeSigntimeSet=officeSigntimeSetService.getOfficeSigntimeSetByUnitIdPage(unitId, time);
		if(officeSigntimeSet!=null){
			return true;
		}
		return false;
	}

	@Override
	public boolean getOfficeSignedOnList(String userId, String years,
			String semester, String unitId, String time) {
		List<OfficeSignedOn> officeSignedOnList=officeSignedOnService.
				getOfficeSignedOnByUnitIdTime(userId, years, semester, unitId,time);
		if(org.apache.commons.collections.CollectionUtils.isNotEmpty(officeSignedOnList)){
			return true;
		}
		return false;
	}

	@Override
	public boolean getOfficeSigned(String signInSystem) {
		String standardValue = systemIniService
				.getValue(signInSystem);
		if(StringUtils.isNotBlank(standardValue) && "1".equals(standardValue)){
			return true;
		}
		return false;
	}

	public void setOfficeSigntimeSetService(
			OfficeSigntimeSetService officeSigntimeSetService) {
		this.officeSigntimeSetService = officeSigntimeSetService;
	}

	public void setOfficeSignedOnService(OfficeSignedOnService officeSignedOnService) {
		this.officeSignedOnService = officeSignedOnService;
	}
	
	public void setRemoteTacherAttendanceService(
			RemoteTacherAttendanceService remoteTacherAttendanceService) {
		this.remoteTacherAttendanceService = remoteTacherAttendanceService;
	}

	public void setSystemIniService(SystemIniService systemIniService) {
		this.systemIniService = systemIniService;
	}

	public void setQrCodeService(QrCodeService qrCodeService) {
		this.qrCodeService = qrCodeService;
	}
	
	public void setRemoteOfficeAppService(
			RemoteOfficeAppService remoteOfficeAppService) {
		this.remoteOfficeAppService = remoteOfficeAppService;
	}
	
	@Override
	public void initQrCode() {
		qrCodeService.init();
	}

	@Override
	public String weikeOfficeCount(String remoteParam) {
		return remoteOfficeAppService.weikeOfficeCount(remoteParam);
	}
	
	public String getStuLeaveJsonString(String unitId, Date dayTime){
		if(dayTime==null){
			dayTime = new Date();
		}
		
		List<Student> stulist = studentService.getStudentsByFaintness(unitId, "");
		Set<String> stuIds = new HashSet<String>();
		Iterator<Student> stuiterator = stulist.iterator();
		Student stu = null;
		while(stuiterator.hasNext()){
			stu = stuiterator.next();
			stuIds.add(stu.getId());
		}
		
		List<OfficeStudentLeave> leavelist = officeStudentLeaveService.findStuIsLeaveBytime(dayTime, stuIds.toArray(new String[0]));
		
		Iterator<OfficeStudentLeave> leaveiterator = leavelist.iterator();
		OfficeStudentLeave ent = null;
		JSONArray array = new JSONArray();
		while(leaveiterator.hasNext()){
			ent = leaveiterator.next();
			JSONObject obj = new JSONObject();
			obj.put("studentId", ent.getStudentId());
			obj.put("classId", ent.getClassId());
			array.add(obj);
		}
		
		return array.toString();
	}
	@Override
	public String getStuLeaveInfo(String unitId, String studentId) {
		List<OfficeHwstudentLeave> stuLeave  = officeHwstudentLeaveService.findByUnitIdAndSubmit(unitId,studentId);
		Set<String> leaveIds = new HashSet<String>();
		for (OfficeHwstudentLeave e : stuLeave) {
			leaveIds.add(e.getId());
		}
		JSONArray array = new JSONArray();
		if(org.apache.commons.collections.CollectionUtils.isEmpty(stuLeave)){
			return array.toString();
		}
		Map<String,OfficeLeavetypeGeneral> generalMap = officeLeavetypeGeneralService.getMapByLeaveIds(leaveIds.toArray(new String[0]));
		Map<String,OfficeLeavetypeTemporary> temporaryMap = officeLeavetypeTemporaryService.getMapByLeaveIds(leaveIds.toArray(new String[0]));
		Map<String,OfficeLeavetypeLive> liveMap = officeLeavetypeLiveService.getMapByLeaveIds(leaveIds.toArray(new String[0]));
		Map<String,OfficeLeavetypeLong> longMap = officeLeavetypeLongService.getMapByLeaveIds(leaveIds.toArray(new String[0]));
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:00");
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
		List<String> taskList = null;
		for (OfficeHwstudentLeave leave : stuLeave) {
			JSONObject obj = new JSONObject();
			obj.put("studentId", studentId);
			obj.put("leaveType", leave.getType());
			obj.put("applyTime", sdf2.format(leave.getCreationTime()));
			obj.put("state", leave.getState());
			List<HisTask> hisTask = officeFlowService.getHisTask(leave.getFlowId());
			taskList = new ArrayList<String>();
			for(HisTask task : hisTask){
				taskList.add(task.getTaskName()+"("+task.getAssigneeName()+")"+"："+task.getComment().getTextComment());
			}
			obj.put("task", taskList);
			//TODO
			if(StringUtils.equals(leave.getType(), "1")){
				OfficeLeavetypeGeneral general = generalMap.get(leave.getId());
				obj.put("leaveTime", sdf1.format(general.getStartTime())+"至"+sdf1.format(general.getEndTime()));
				obj.put("days", general.getDays());
				obj.put("remark", general.getRemark());
			}else if(StringUtils.equals(leave.getType(), "2")){
				OfficeLeavetypeTemporary temporary = temporaryMap.get(leave.getId());
				obj.put("leaveTime", sdf1.format(temporary.getStartTime())+"至"+sdf1.format(temporary.getEndTime()));
				obj.put("remark", temporary.getRemark());
				obj.put("linkPhone", temporary.getLinkPhone());
			}else if(StringUtils.equals(leave.getType(), "3")){
				OfficeLeavetypeLive live = liveMap.get(leave.getId());
				obj.put("leaveTime", sdf2.format(live.getStartTime())+"至"+sdf2.format(live.getEndTime()));
				obj.put("applyType", live.getApplyType());
				obj.put("remark", live.getRemark());
				obj.put("days", live.getDays());
			}else if(StringUtils.equals(leave.getType(), "4")){
				OfficeLeavetypeLong longLeave = longMap.get(leave.getId());
				obj.put("leaveTime", sdf2.format(longLeave.getStartTime())+"至"+sdf2.format(longLeave.getEndTime()));
				obj.put("hasBed", longLeave.getHasBed());
				obj.put("address", longLeave.getAddress());
				obj.put("mateName", longLeave.getMateName());
				obj.put("mateGx", longLeave.getMateGx());
				obj.put("days", longLeave.getDays());
			}else{
				continue;
			}
			array.add(obj);
		}
		return array.toString();
	}
	@Override
	public String getStuLeaveTime(String unitId, String leaveType,
			String studentId, Date date) {
		if(date==null){
			date = new Date();
		}
		JSONArray array = new JSONArray();
		List<OfficeHwstudentLeave> stuLeave  = officeHwstudentLeaveService.findByUnitIdAndType(unitId,null,studentId, leaveType);
		if(org.apache.commons.collections.CollectionUtils.isEmpty(stuLeave)){
			JSONObject obj = new JSONObject();
			obj.put("studentId", studentId);
			obj.put("isLeave", "false");
			array.add(obj);
			return array.toString();
		}
		Set<String> leaveIds = new HashSet<String>();
		for (OfficeHwstudentLeave leave : stuLeave) {
			leaveIds.add(leave.getId());
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		
		if(StringUtils.equals(leaveType, "1")){
			List<OfficeLeavetypeGeneral> generals = officeLeavetypeGeneralService.findByLeaveTimeAndLeaveIds(date, leaveIds.toArray(new String[0]));
			if(org.apache.commons.collections.CollectionUtils.isNotEmpty(generals)){
				for (OfficeLeavetypeGeneral officeLeavetypeGeneral : generals) {
					JSONObject obj = new JSONObject();
					obj.put("studentId", studentId);
					obj.put("isLeave", "true");
					obj.put("startTime", sdf.format(officeLeavetypeGeneral.getStartTime()));
					obj.put("endTime",  sdf.format(officeLeavetypeGeneral.getEndTime()));
					array.add(obj);
					break;
				}
			}else{
				JSONObject obj = new JSONObject();
				obj.put("studentId", studentId);
				obj.put("isLeave", "false");
				array.add(obj);
			}
		}else if(StringUtils.equals(leaveType, "2")){
			List<OfficeLeavetypeTemporary> temporarys = officeLeavetypeTemporaryService.findByLeaveTimeAndLeaveIds(date, leaveIds.toArray(new String[0]));
			if(org.apache.commons.collections.CollectionUtils.isNotEmpty(temporarys)){
				for (OfficeLeavetypeTemporary officeLeavetypeTemporary : temporarys) {
					JSONObject obj = new JSONObject();
					obj.put("studentId", studentId);
					obj.put("isLeave", "true");
					obj.put("startTime", sdf.format(officeLeavetypeTemporary.getStartTime()));
					obj.put("endTime",  sdf.format(officeLeavetypeTemporary.getEndTime()));
					array.add(obj);
					break;
				}
			}else{
				JSONObject obj = new JSONObject();
				obj.put("studentId", studentId);
				obj.put("isLeave", "false");
				array.add(obj);
			}
		}
		return array.toString();
	}
	@Override
	public String getHwStuLeavesByUnitId(String unitId, String classId,
			String leaveType, String applyType, Date date) {
		Set<String> stuIds = new HashSet<String>();
		if(date==null){
			date = new Date();
		}
		List<OfficeHwstudentLeave> stuLeave  = officeHwstudentLeaveService.findByUnitIdAndType(unitId,classId,null, leaveType);
		Set<String> leaveIds = new HashSet<String>(); 
		Map<String,OfficeHwstudentLeave> leaveMap = new HashMap<String, OfficeHwstudentLeave>();
		for (OfficeHwstudentLeave officeHwstudentLeave : stuLeave) {
			leaveIds.add(officeHwstudentLeave.getId());
			leaveMap.put(officeHwstudentLeave.getId(), officeHwstudentLeave);
		}
		if(StringUtils.equals(leaveType, "1")){
			List<OfficeLeavetypeGeneral> generals = officeLeavetypeGeneralService.findByLeaveTimeAndLeaveIds(date,leaveIds.toArray(new String[0]));
			for (OfficeLeavetypeGeneral officeLeavetypeGeneral : generals) {
				OfficeHwstudentLeave leave = leaveMap.get(officeLeavetypeGeneral.getLeaveId());
				stuIds.add(leave.getStudentId());
			}
		}else if(StringUtils.equals(leaveType, "2")){
			List<OfficeLeavetypeTemporary> temporarys = officeLeavetypeTemporaryService.findByLeaveTimeAndLeaveIds(date,leaveIds.toArray(new String[0]));
			for (OfficeLeavetypeTemporary officeLeavetypeTemporary : temporarys) {
				OfficeHwstudentLeave leave = leaveMap.get(officeLeavetypeTemporary.getLeaveId());
				stuIds.add(leave.getStudentId());
			}
		}else if(StringUtils.equals(leaveType, "3")){
			List<OfficeLeavetypeLive> lives = officeLeavetypeLiveService.findByLeaveTimeAndAppleyTypeAdnLeaveIds(date,applyType,leaveIds.toArray(new String[0]));
			for (OfficeLeavetypeLive officeLeavetypeLive : lives) {
				OfficeHwstudentLeave leave = leaveMap.get(officeLeavetypeLive.getLeaveId());
				stuIds.add(leave.getStudentId());
			}	
		}else if(StringUtils.equals(leaveType, "4")){
			List<OfficeLeavetypeLong> longLeaves = officeLeavetypeLongService.findByLeaveTimeAndLeaveIds(date,leaveIds.toArray(new String[0])); 
			for (OfficeLeavetypeLong officeLeavetypeLong : longLeaves) {
				OfficeHwstudentLeave leave = leaveMap.get(officeLeavetypeLong.getLeaveId());
				stuIds.add(leave.getStudentId());
			}
		}
		JSONArray array = new JSONArray();
		JSONObject obj = new JSONObject();
		obj.put("studentIds", stuIds);
		array.add(obj);
		return array.toString();
	}
	@Override
	public String getOfficeMsgDetails(String remoteParam){
		try {
			String userId = remoteParam;
			
			List<OfficeMsgReceiving> officeMsgReceivings = officeMsgReceivingService.getOfficeMsgDetails(userId);
			if(!CollectionUtils.isEmpty(officeMsgReceivings)){
				JSONArray jsonArray = new JSONArray();
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				for(OfficeMsgReceiving item : officeMsgReceivings){
					JSONObject json = new JSONObject();
					json.put("id", item.getId());
					json.put("receiverType", item.getReceiverType());
					json.put("msgType", item.getMsgType());
					json.put("isRead", item.getIsRead());
					json.put("title", item.getTitle());
					json.put("sendTime", simpleDateFormat.format(item.getSendTime()));
					jsonArray.add(json);
				}
				return jsonArray.toString();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}
	
	@Override
	public String getAbsenceUsers(String remoteParam) {//TODO
		try {
			String userId=JSON.parseObject(remoteParam).getString("userId");
			User user=userService.getUser(userId);
			if(user==null){
				return "";
			}
			Unit unit=unitService.getUnit(user.getUnitid());
			if(unit==null){
				return "";
			}
			
			List<OfficeTeacherLeave> officeTeacherLeaves=officeTeacherLeaveService.getOfficeTeacherLeavesByUnitIdAndUserId(unit.getId(), null, new Date());
			JSONArray userDetails=new JSONArray();
			if(!CollectionUtils.isEmpty(officeTeacherLeaves)){
				for (OfficeTeacherLeave officeTeacherLeave : officeTeacherLeaves) {
					JSONObject json=new JSONObject();
					json.put("deptName", officeTeacherLeave.getDeptName());
					json.put("realName", officeTeacherLeave.getApplyUserName());
					json.put("reason", officeTeacherLeave.getLeaveType());
					userDetails.add(json);
				}
			}
			return userDetails.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	@Override
	public String getWorkArrangeOutlines(String remoteParam) {
		try {
			String userId=JSON.parseObject(remoteParam).getString("userId");
			String size=JSON.parseObject(remoteParam).getString("size");
			if(size == null){
				size = "3";
			}
			User user=userService.getUser(userId);
			if(user==null){
				return "";
			}
			Unit unit=unitService.getUnit(user.getUnitid());
			if(unit==null){
				return "";
			}
			OfficeBusinessJump businessJump = new OfficeBusinessJump();
			List<Module> ml = moduleService.getModules(new String[]{"70510","70010"}, unit.getUnitclass());
			if(org.apache.commons.collections.CollectionUtils.isNotEmpty(ml)){
				businessJump.setModule(ml.get(0));
			}
			List<OfficeWorkArrangeOutline> officeWorkArrangeOutlineList=officeWorkArrangeOutlineService.getOfficeWorkArrangeOutlinesByThings(unit, Integer.parseInt(size));
			JSONArray userDetails=new JSONArray();
			for (OfficeWorkArrangeOutline officeWorkArrangeOutline : officeWorkArrangeOutlineList) {
				JSONObject json=new JSONObject();
				json.put("title", officeWorkArrangeOutline.getName());
				json.put("officeWorkId", officeWorkArrangeOutline.getId());
				SimpleDateFormat sdf=new SimpleDateFormat("MM/dd");
				String start=sdf.format(officeWorkArrangeOutline.getStartTime());
				String end=sdf.format(officeWorkArrangeOutline.getEndTime());
				json.put("start", start);
				json.put("end", end);
				json.put("arrangeQuery", true);
				userDetails.add(json);
			}
			
			JSONObject data = new JSONObject();
			data.put("data", userDetails);
			data.put("arrangeQuery", false);
			data.put("modelId", businessJump.getModule().getId());
			return data.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	@Override
	public String getAddressBookDetails(String remoteParam){//TODO
		try {
			String userId = com.alibaba.fastjson.JSON.parseObject(remoteParam).getString("userId");
			String groupType = com.alibaba.fastjson.JSON.parseObject(remoteParam).getString("groupType");
			User user = userService.getUser(userId);
			if(user == null)
				return "";
			Unit unit = unitService.getUnit(user.getUnitid());
			if(unit == null)
				return "";
			if(StringUtils.isBlank(groupType))
				return "";
			
			//职务MAP
			Map<String, Mcodedetail> dutyMap = new HashMap<String, Mcodedetail>();;
			if(unit.getUnitclass() == Unit.UNIT_CLASS_EDU){
				dutyMap = mcodedetailService.getMcodeDetailMap("DM-JYJZW");
			}else if(unit.getUnitclass() == Unit.UNIT_CLASS_SCHOOL){
				dutyMap = mcodedetailService.getMcodeDetailMap("DM-XXZW");
			}
			
			//获取本单位下教师信息
			List<Teacher> teacherList = teacherService.getTeachers(unit.getId());
			Map<String, Teacher> teacherMap = new HashMap<String, Teacher>();
			Set<String> teacherIds = new HashSet<String>();
			if(CollectionUtils.isEmpty(teacherList)){
				return "";
			}
			else{
				for (int i = 0; i < teacherList.size(); i++) {
					Teacher teacher = teacherList.get(i);
					teacherIds.add(teacher.getId());
					teacherMap.put(teacher.getId(), teacher);
				}
			}
			//设置教师职务
			List<TeacherDuty> teacherDutyList = teacherDutyService.getTeacherDutyListByTeacherIds(teacherIds.toArray(new String[0]));
			Map<String, String> dutyNamesMap = this.getDutyNamesMap(dutyMap, teacherDutyList);
			
			JSONArray jsonArray = new JSONArray();
			//type:1-自定义用户组；2-部门组；3-职务组
			if(groupType.equals("1")){
				//---------------------------------------处理自定义组信息--------------------------------------
				//获取用户组（本单位+其他单位；公共组+自己创建的个人组）
				List<CustomGroup> groupList = customGroupService.getCustomGroups(user.getUnitid(),
						user.getId(), false, false);
				//获取系统组
				if(this.isSystemAdmin(user)){
					List<CustomGroup> systemList = customGroupService.getCustomGroups(user.getUnitid(), GroupConstant.GROUP_TYPE_OFFSYSTEM);
					if(!CollectionUtils.isEmpty(systemList))
						groupList.addAll(systemList);
				}
				
				if(!CollectionUtils.isEmpty(groupList)){
					Set<String> groupIds = new HashSet<String>();
					for(CustomGroup group : groupList){
						groupIds.add(group.getId());
					}
					List<CustomGroupItem> groupItems = customGroupItemService.getCustomGroupItems(groupIds.toArray(new String[0]));
					
					//获取所有组成员MAP，及组-成员对应关系MAP
					Set<String> groupUserIds = new HashSet<String>();
					Map<String, List<String>> groupUserIdMap = new HashMap<String, List<String>>();
					for(CustomGroupItem groupItem : groupItems){
						groupUserIds.add(groupItem.getItemId());
						List<String> userids = groupUserIdMap.get(groupItem.getGroupId());
						if(CollectionUtils.isEmpty(userids)){
							userids = new ArrayList<String>();
						}
						userids.add(groupItem.getItemId());
						groupUserIdMap.put(groupItem.getGroupId(), userids);
					}
					Map<String, User> groupUserMap = userService.getUsersMap(groupUserIds.toArray(new String[0]));
					
					for(CustomGroup group : groupList){
						JSONObject json = new JSONObject();
						json.put("groupName", group.getGroupName());
						json.put("groupType", group.getType());
						
						JSONArray userDetails = new JSONArray();
						List<String> userids = groupUserIdMap.get(group.getId());
						if(!CollectionUtils.isEmpty(userids)){
							for(String userid : userids){
								User groupUser = groupUserMap.get(userid);
								if(groupUser != null){
									Teacher groupTeacher = teacherMap.get(groupUser.getTeacherid());
									if(groupTeacher != null){
										JSONObject userDetail = new JSONObject();
										userDetail.put("teacherId", groupTeacher.getId());
										userDetail.put("userName", groupUser.getRealname());
										userDetail.put("duty", dutyNamesMap.get(groupTeacher.getId()));
										userDetail.put("personTel", groupTeacher.getPersonTel());
										userDetail.put("email", groupTeacher.getEmail());
										userDetail.put("linkPhone", groupTeacher.getOfficeTel());
										userDetail.put("deptName", deptService.getDept(groupTeacher.getDeptid()).getDeptname());
										userDetail.put("unitName", unitService.getUnit(groupTeacher.getUnitid()).getName());
										userDetails.add(userDetail);
									}
								}
							}
						}
						json.put("userDetails", userDetails);
						jsonArray.add(json);
					}
				}
			}
			else if(groupType.equals("2")){
				//------------------------------处理部门组信息---------------------------------
				List<Dept> deptList = deptService.getDepts(user.getUnitid());
				if(!CollectionUtils.isEmpty(deptList)){
					for(Dept dept : deptList){
						JSONObject json = new JSONObject();
						json.put("groupName", dept.getDeptname());
						json.put("groupType", 6);
						
						JSONArray userDetails = new JSONArray();
						for(Teacher deptTeacher : teacherList){
							if(dept.getId().equals(deptTeacher.getDeptid())){
								JSONObject userDetail = new JSONObject();
								userDetail.put("teacherId", deptTeacher.getId());
								userDetail.put("userName", deptTeacher.getName());
								userDetail.put("duty", dutyNamesMap.get(deptTeacher.getId()));
								userDetail.put("personTel", deptTeacher.getPersonTel());
								userDetail.put("email", deptTeacher.getEmail());
								userDetail.put("linkPhone", deptTeacher.getOfficeTel());
								userDetail.put("deptName", deptService.getDept(deptTeacher.getDeptid()).getDeptname());
								userDetail.put("unitName", unitService.getUnit(deptTeacher.getUnitid()).getName());
								userDetails.add(userDetail);
							}
						}
						json.put("userDetails", userDetails);
						jsonArray.add(json);
					}
				}
			}
			else if(groupType.equals("3")){
				//------------------------------处理职务组信息---------------------------------
				if(!CollectionUtils.isEmpty(teacherDutyList)){
					for(Map.Entry<String, Mcodedetail> entry : dutyMap.entrySet()){
						JSONObject json = new JSONObject();
						json.put("groupName", entry.getValue().getContent());
						json.put("groupType", 7);
						
						JSONArray userDetails = new JSONArray();
						for(TeacherDuty duty : teacherDutyList){
							if(entry.getValue().getThisId().equals(duty.getDutyCode())){
								Teacher dutyTeacher = teacherMap.get(duty.getTeacherId());
								if(dutyTeacher != null){
									JSONObject userDetail = new JSONObject();
									userDetail.put("teacherId", dutyTeacher.getId());
									userDetail.put("userName", dutyTeacher.getName());
									userDetail.put("duty", dutyNamesMap.get(dutyTeacher.getId()));
									userDetail.put("personTel", dutyTeacher.getPersonTel());
									userDetail.put("email", dutyTeacher.getEmail());
									userDetail.put("linkPhone", dutyTeacher.getOfficeTel());
									userDetail.put("deptName", deptService.getDept(dutyTeacher.getDeptid()).getDeptname());
									userDetail.put("unitName", unitService.getUnit(dutyTeacher.getUnitid()).getName());
									userDetails.add(userDetail);
								}
							}
						}
						//不显示没有成员的职务组
						if(userDetails.size() > 0){
							json.put("userDetails", userDetails);
							jsonArray.add(json);
						}
					}
				}
			}
			else{
				return "";
			}
			return jsonArray.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}
	
	//获取职务名称MAP
	public Map<String, String> getDutyNamesMap(Map<String, Mcodedetail> dutyMap, List<TeacherDuty> teacherDutyList){
		Map<String, String> dutyNamesMap = new HashMap<String, String>();
		for(TeacherDuty duty:teacherDutyList){
			if(dutyMap.get(duty.getDutyCode())!=null){
				String teacherId = duty.getTeacherId();
				String dutyNames = dutyNamesMap.get(teacherId);
				if(StringUtils.isNotBlank(dutyNames)){
					dutyNames = dutyNames+","+dutyMap.get(duty.getDutyCode()).getContent();
				}else{
					dutyNames = dutyMap.get(duty.getDutyCode()).getContent();
				}
				dutyNamesMap.put(teacherId,dutyNames);
			}
		}
		return dutyNamesMap;
	}
	
	public boolean isSystemAdmin(User user) {
		Unit unit = unitService.getTopEdu();
		//如果是顶级单位，并且有维护公共组权限，且不是独立部署，则有获取系统组权限
		if (unit.getId().equals(user.getUnitid()) && this.isAddressBookAdmin(user) && !systemDeployService.isDeployAsIndependence())
			return true;
		return false;
	}
	
	public boolean isAddressBookAdmin(User user){
		CustomRole role = customRoleService.getCustomRoleByRoleCode(user.getUnitid(), GroupConstant.ADDRESS_BOOK_ADMIN);
		boolean flag;
		if(role == null){
			flag = false;
			return flag;
		}
		List<CustomRoleUser> roleUs = customRoleUserService.getCustomRoleUserListByUserId(user.getId());
		if(CollectionUtils.isEmpty(roleUs)){
			for(CustomRoleUser ru : roleUs){
				if(org.apache.commons.lang.StringUtils.equals(ru.getRoleId(), role.getId())){
					flag = true;
					return flag;
				}
			}
		}
		flag = false;
		return flag;
	}

	public void setOfficeMsgReceivingService(
			OfficeMsgReceivingService officeMsgReceivingService) {
		this.officeMsgReceivingService = officeMsgReceivingService;
	}

	public void setCustomGroupService(CustomGroupService customGroupService) {
		this.customGroupService = customGroupService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public void setUnitService(UnitService unitService) {
		this.unitService = unitService;
	}

	public void setCustomRoleService(CustomRoleService customRoleService) {
		this.customRoleService = customRoleService;
	}

	public void setCustomRoleUserService(CustomRoleUserService customRoleUserService) {
		this.customRoleUserService = customRoleUserService;
	}

	public void setSystemDeployService(SystemDeployService systemDeployService) {
		this.systemDeployService = systemDeployService;
	}

	public void setDeptService(DeptService deptService) {
		this.deptService = deptService;
	}

	public void setCustomGroupItemService(
			CustomGroupItemService customGroupItemService) {
		this.customGroupItemService = customGroupItemService;
	}

	public void setTeacherService(TeacherService teacherService) {
		this.teacherService = teacherService;
	}

	public void setTeacherDutyService(TeacherDutyService teacherDutyService) {
		this.teacherDutyService = teacherDutyService;
	}

	public void setMcodedetailService(McodedetailService mcodedetailService) {
		this.mcodedetailService = mcodedetailService;
	}

	@Override
	public String attendanceDetail(String remoteParam) {
		// TODO Auto-generated method stub
		return remoteTacherAttendanceService.attendanceDetail(remoteParam);
	}

	@Override
	public String attendanceSubmit(String remoteParam) {
		// TODO Auto-generated method stub
		return remoteTacherAttendanceService.attendanceSubmit(remoteParam);
	}

	@Override
	public String attendanceApply(String remoteParam) {
		// TODO Auto-generated method stub
		return remoteTacherAttendanceService.attendanceApply(remoteParam);
	}

	@Override
	public String attendanceCount(String remoteParam) {
		// TODO Auto-generated method stub
		return remoteTacherAttendanceService.attendanceCount(remoteParam);
	}

	@Override
	public String attendanceMonth(String remoteParam) {
		// TODO Auto-generated method stub
		return remoteTacherAttendanceService.attendanceMonth(remoteParam);
	}

	@Override
	public String getBulletinDetails(String remoteParam) {
		try {
			String userId = com.alibaba.fastjson.JSON.parseObject(remoteParam).getString("userId");
			String bulletinType = com.alibaba.fastjson.JSON.parseObject(remoteParam).getString("bulletinType");
			Integer pageNum = com.alibaba.fastjson.JSON.parseObject(remoteParam).getInteger("pageNum");
			User user = userService.getUser(userId);
			if(user == null)
				return "";
			Unit unit = unitService.getUnit(user.getUnitid());
			if(unit == null)
				return "";
			
			if(pageNum == null)
				pageNum = 8;
			Pagination page = new Pagination(pageNum, false);
			List<OfficeBulletin> officeBulletinList = officeBulletinService.getOfficeBulletinListPage(new String[] {bulletinType}, user.getUnitid(), userId, null, OfficeBulletin.STATE_PASS, null, null, null, null, page);
			
			JSONObject json = new JSONObject();
			
			//bulletinType:1-通知；2-行事历；3-公告；4-政策文件
			int[] modelIds ={70002,70003,70004,70005};
			int[] modelIdsEdu ={70502,70503,70504,70505};
			Integer modelId;
			if(unit.getUnitclass() == Unit.UNIT_CLASS_EDU){
				modelId = modelIdsEdu[Integer.parseInt(bulletinType)-1];
				if(modelId == 70504)
					modelId = 70502;
			}else{
				modelId = modelIds[Integer.parseInt(bulletinType)-1];
				if(modelId == 70004)
					modelId = 70002;
			}
			json.put("modelId", modelId);//模块ID
			
			JSONArray bulletinDetails = new JSONArray();
			if(!CollectionUtils.isEmpty(officeBulletinList)){
				DateFormat dateFormat = new SimpleDateFormat("yy-MM-dd HH:mm:ss");
				for(OfficeBulletin bulletin : officeBulletinList){
					JSONObject bulletinDetail = new JSONObject();
					bulletinDetail.put("bulletinId", bulletin.getId());
					bulletinDetail.put("areaName", bulletin.getAreaName());//地区标签
					bulletinDetail.put("title", bulletin.getTitle());
					bulletinDetail.put("createTime", dateFormat.format(bulletin.getCreateTime()));
					bulletinDetail.put("isNew", bulletin.getIsNew());//是否未读
					bulletinDetails.add(bulletinDetail);
				}
				if(officeBulletinList.size() > pageNum){//是否显示更多
					json.put("viewMore", true);
				}
				json.put("bulletinDetails", bulletinDetails);
			}
			return json.toString();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}
	
	public void setOfficeBulletinService(OfficeBulletinService officeBulletinService) {
		this.officeBulletinService = officeBulletinService;
	}

	@Override
	public void batchUpdateFlowStepInfo(String flowId, String flowStepInfo){
		officeFlowStepInfoService.batchUpdateInfo(flowId, flowStepInfo);
	}
	
	@Override
	public OfficeStepInfoDto getStepInfo(String flowId, String stepId){
		OfficeFlowStepInfo stepInfo = officeFlowStepInfoService.getStepInfo(flowId, stepId);
		if(stepInfo != null){
			OfficeStepInfoDto dto  = new OfficeStepInfoDto();
			dto.setFlowId(stepInfo.getFlowId());
			dto.setStepId(stepInfo.getStepId());
			dto.setTaskUserId(stepInfo.getTaskUserId());
			dto.setStepType(stepInfo.getStepType());
			
			if(StringUtils.isNotBlank(stepInfo.getTaskUserId())){
				String[] taskUserIds = stepInfo.getTaskUserId().split(",");
				Map<String, User> userMap = userService.getUsersMap(taskUserIds);
				String taskUserName = "";
				for(String userId : taskUserIds){
					User user = userMap.get(userId);
					if(user != null){
						if(StringUtils.isBlank(taskUserName))
							taskUserName = user.getRealname();
						else
							taskUserName += "," + user.getRealname();
					}
				}
				dto.setTaskUserName(taskUserName);
				dto.setTaskDetailName(userService.getUserDetailNamesStr(taskUserIds));
			}
			
			return dto;
		}else{
			return new OfficeStepInfoDto();
		}
	}
	
	@Override
	public OfficeStepInfoDto formatStepInfo(String stepInfo){
		if(StringUtils.isNotBlank(stepInfo)){
			String[] infos = stepInfo.split("-");
			if("remove".equals(infos[0])){
				return new OfficeStepInfoDto();
			}
			else{
				OfficeStepInfoDto stepInfoDto = new OfficeStepInfoDto();
				String userIds = this.filterUserId(infos[2]);
				stepInfoDto.setTaskUserId(userIds);
				stepInfoDto.setStepType(infos[3]);
				
				if(StringUtils.isNotBlank(stepInfoDto.getTaskUserId())){
					String[] taskUserIds = stepInfoDto.getTaskUserId().split(",");
					Map<String, User> userMap = userService.getUsersMap(taskUserIds);
					String taskUserName = "";
					for(String userId : taskUserIds){
						User user = userMap.get(userId);
						if(user != null){
							if(StringUtils.isBlank(taskUserName))
								taskUserName = user.getRealname();
							else
								taskUserName += "," + user.getRealname();
						}
					}
					stepInfoDto.setTaskUserName(taskUserName);
					stepInfoDto.setTaskDetailName(userService.getUserDetailNamesStr(taskUserIds));
				}
				return stepInfoDto;
			}
		}
		else{
			return new OfficeStepInfoDto();
		}
	}
	
	public String filterUserId(String userIds){//过滤半选人ID
		Set<String> set = new HashSet<String>();
		String[] list = userIds.split(",");
		if(list.length > 0){
			for(String item : list){
				if(!item.equals("00000000000000000000000000000001") 
						&& !item.equals("00000000000000000000000000000002")
						&& !item.equals("00000000000000000000000000000003")
						&& !item.equals("00000000000000000000000000000004")
						&& !item.equals("office_schoolmaster")){
					set.add(item);
				}
			}
		}
		if(set.size()>0){
			String returnStr = "";
			for(String item : set){
				if(StringUtils.isBlank(returnStr))
					returnStr = item;
				else
					returnStr += "," + item;
			}
			return returnStr;
		}
		else{
			return "";
		}
	}

	public void setOfficeFlowStepInfoService(
			OfficeFlowStepInfoService officeFlowStepInfoService) {
		this.officeFlowStepInfoService = officeFlowStepInfoService;
	}

	@Override
	public JSONObject validateH5(String unitId, String userId, HttpServletRequest request) {
		return WeikeAppUrlEnum.validateH5(unitId, userId, request);
	}

	public void setOfficeTeacherLeaveService(
			OfficeTeacherLeaveService officeTeacherLeaveService) {
		this.officeTeacherLeaveService = officeTeacherLeaveService;
	}

	public void setOfficeWorkArrangeOutlineService(
			OfficeWorkArrangeOutlineService officeWorkArrangeOutlineService) {
		this.officeWorkArrangeOutlineService = officeWorkArrangeOutlineService;
	}

	public void setModuleService(ModuleService moduleService) {
		this.moduleService = moduleService;
	}
	
	public void setStudentService(StudentService studentService) {
		this.studentService = studentService;
	}

	public void setOfficeHwstudentLeaveService(
			OfficeHwstudentLeaveService officeHwstudentLeaveService) {
		this.officeHwstudentLeaveService = officeHwstudentLeaveService;
	}

	public void setOfficeLeavetypeLiveService(
			OfficeLeavetypeLiveService officeLeavetypeLiveService) {
		this.officeLeavetypeLiveService = officeLeavetypeLiveService;
	}

	public void setOfficeLeavetypeGeneralService(
			OfficeLeavetypeGeneralService officeLeavetypeGeneralService) {
		this.officeLeavetypeGeneralService = officeLeavetypeGeneralService;
	}

	public void setOfficeLeavetypeTemporaryService(
			OfficeLeavetypeTemporaryService officeLeavetypeTemporaryService) {
		this.officeLeavetypeTemporaryService = officeLeavetypeTemporaryService;
	}

	public void setOfficeLeavetypeLongService(
			OfficeLeavetypeLongService officeLeavetypeLongService) {
		this.officeLeavetypeLongService = officeLeavetypeLongService;
	}

	public void setOfficeFlowService(OfficeFlowService officeFlowService) {
		this.officeFlowService = officeFlowService;
	}
	
}
