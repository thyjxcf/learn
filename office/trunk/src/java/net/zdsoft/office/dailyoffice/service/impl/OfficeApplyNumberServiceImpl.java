package net.zdsoft.office.dailyoffice.service.impl;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.zdsoft.eis.base.attachment.entity.Attachment;
import net.zdsoft.eis.base.attachment.service.AttachmentService;
import net.zdsoft.eis.base.common.entity.BasicClass;
import net.zdsoft.eis.base.common.entity.CustomRoleUser;
import net.zdsoft.eis.base.common.entity.Dept;
import net.zdsoft.eis.base.common.entity.Mcodedetail;
import net.zdsoft.eis.base.common.entity.Teacher;
import net.zdsoft.eis.base.common.entity.Unit;
import net.zdsoft.eis.base.common.entity.User;
import net.zdsoft.eis.base.common.service.BasicClassService;
import net.zdsoft.eis.base.common.service.CustomRoleService;
import net.zdsoft.eis.base.common.service.DeptService;
import net.zdsoft.eis.base.common.service.McodedetailService;
import net.zdsoft.eis.base.common.service.SystemIniService;
import net.zdsoft.eis.base.common.service.TeacherService;
import net.zdsoft.eis.base.common.service.UnitService;
import net.zdsoft.eis.base.common.service.UserService;
import net.zdsoft.eis.base.constant.BaseConstant;
import net.zdsoft.eis.base.subsystemcall.entity.EduadmCourseDto;
import net.zdsoft.eis.base.subsystemcall.entity.StusysSectionTimeSetDto;
import net.zdsoft.eis.base.subsystemcall.service.EduadmSubsystemService;
import net.zdsoft.eis.sms.constant.SmsConstant;
import net.zdsoft.eis.sms.dto.MsgDto;
import net.zdsoft.eis.sms.dto.SendDetailDto;
import net.zdsoft.eis.sms.service.SmsClientService;
import net.zdsoft.eisu.base.common.entity.TeachPlace;
import net.zdsoft.eisu.base.common.service.TeachPlaceService;
import net.zdsoft.keel.util.DateUtils;
import net.zdsoft.keel.util.Pagination;
import net.zdsoft.keelcnet.action.UploadFile;
import net.zdsoft.leadin.common.entity.BusinessTask;
import net.zdsoft.leadin.util.UUIDGenerator;
import net.zdsoft.office.dailyoffice.dao.OfficeApplyNumberDao;
import net.zdsoft.office.dailyoffice.entity.OfficeApplyNumber;
import net.zdsoft.office.dailyoffice.entity.OfficeLabInfo;
import net.zdsoft.office.dailyoffice.entity.OfficeLabSet;
import net.zdsoft.office.dailyoffice.entity.OfficeMeetingUser;
import net.zdsoft.office.dailyoffice.entity.OfficeRoomOrderSet;
import net.zdsoft.office.dailyoffice.entity.OfficeTimeSet;
import net.zdsoft.office.dailyoffice.entity.OfficeUtilityApply;
import net.zdsoft.office.dailyoffice.entity.OfficeUtilityNumber;
import net.zdsoft.office.dailyoffice.entity.RoomorderAuditSms;
import net.zdsoft.office.dailyoffice.entity.WebBookroom;
import net.zdsoft.office.dailyoffice.service.OfficeApplyNumberService;
import net.zdsoft.office.dailyoffice.service.OfficeLabInfoService;
import net.zdsoft.office.dailyoffice.service.OfficeLabSetService;
import net.zdsoft.office.dailyoffice.service.OfficeMeetingUserService;
import net.zdsoft.office.dailyoffice.service.OfficeRoomOrderSetService;
import net.zdsoft.office.dailyoffice.service.OfficeTimeSetService;
import net.zdsoft.office.dailyoffice.service.OfficeUtilityApplyService;
import net.zdsoft.office.dailyoffice.service.OfficeUtilityNumberService;
import net.zdsoft.office.dailyoffice.service.RoomorderAuditSmsService;
import net.zdsoft.office.dailyoffice.service.WebBookroomService;
import net.zdsoft.office.msgcenter.entity.OfficeMsgSending;
import net.zdsoft.office.msgcenter.service.OfficeMsgSendingService;
import net.zdsoft.office.util.Constants;
import net.zdsoft.office.util.OfficeUtils;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
/**
 * office_apply_number
 * @author 
 * 
 */
public class OfficeApplyNumberServiceImpl implements OfficeApplyNumberService{
	private UserService userService;
	private DeptService deptService;
	private TeacherService teacherService;
	private AttachmentService attachmentService;
	private TeachPlaceService teachPlaceService;
	private OfficeApplyNumberDao officeApplyNumberDao;
	private OfficeUtilityApplyService officeUtilityApplyService;
	private OfficeUtilityNumberService officeUtilityNumberService;
	private OfficeMeetingUserService officeMeetingUserService;
	private OfficeTimeSetService officeTimeSetService;
	private WebBookroomService webBookroomService;
	private OfficeMsgSendingService officeMsgSendingService;
	private EduadmSubsystemService eduadmSubsystemService;
	private BasicClassService basicClassService;
	private OfficeRoomOrderSetService officeRoomOrderSetService;
	protected SystemIniService systemIniService;
	private OfficeLabInfoService officeLabInfoService;
	private OfficeLabSetService officeLabSetService;
	private McodedetailService mcodedetailService;
	private UnitService unitService;
	private RoomorderAuditSmsService roomorderAuditSmsService;
	private SmsClientService smsClientService;
	private CustomRoleService customRoleService;

	@Override
	public boolean saveComputerRoom(OfficeApplyNumber officeApplyNumber, Map<String, StusysSectionTimeSetDto> sstsMap){
		String[] applyRooms = officeApplyNumber.getApplyRooms();
		Arrays.sort(applyRooms);  //进行排序
		boolean flag = false;//判断同一时间是否有其他人申请了，那边要刷新页面重新申请
		String id = "";//教室id
		String periods = "";//同一个教室的节次拼接
		String content = "";//需要组织的内容，推送到一卡通
		List<OfficeUtilityApply> officeUtilityApplies = new ArrayList<OfficeUtilityApply>();
		List<OfficeUtilityNumber> officeUtilityNumbers = new ArrayList<OfficeUtilityNumber>();
		officeApplyNumber.setId(UUIDGenerator.getUUID());
		//时段设置信息，主要为了获取中午时间段
		OfficeTimeSet officeTimeSet = officeTimeSetService.getOfficeTimeSetByUnitId(officeApplyNumber.getUnitId());
		//推送到一卡通 TODO
		List<WebBookroom> webBookrooms = new ArrayList<WebBookroom>();
		User user = userService.getUser(officeApplyNumber.getApplyUserId());
		Teacher teacher = teacherService.getTeacher(user.getTeacherid());
		Set<String> teachPlaceIdSet = new HashSet<String>();
		for(String applyRoom:applyRooms){
			teachPlaceIdSet.add(applyRoom.split("_")[0]);
		}
		Map<String, TeachPlace> teachPlaceMap = teachPlaceService.getTeachPlaceMap(teachPlaceIdSet.toArray(new String[0]));
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		//设置用途信息
//		Map<String, Teacher> teacherMap = teacherService.getTeacherMap(officeApplyNumber.getUnitId());
		Map<String, BasicClass> classMap = basicClassService.getClassMap(officeApplyNumber.getUnitId());
		if(StringUtils.isNotBlank(officeApplyNumber.getCourseId())) {
			EduadmCourseDto eduadmCourse = eduadmSubsystemService.getEduadmCourseById(officeApplyNumber.getCourseId());
			String className = "";
			if(eduadmCourse.getStudyType().equals("1")) {
				className = classMap.get(eduadmCourse.getClassId()).getClassname();
			}else {
				className = eduadmSubsystemService.getEduadmTeachClassName(eduadmCourse.getClassId());
			}
			officeApplyNumber.setPurpose(className + " " + eduadmCourse.getSubjectName());
		}
		for(String applyRoom:applyRooms){
			String[] idPeriod = applyRoom.split("_");
			flag = officeUtilityApplyService.isApplyByOthers(idPeriod[0],idPeriod[1],officeApplyNumber.getType(),officeApplyNumber.getApplyUserId(),officeApplyNumber.getApplyDate());
			//说明已经被占用,那么要重新刷新再申请
			if(flag){
				break;
			}
			if(sstsMap.size() > 0){
				WebBookroom webBookroom = new WebBookroom();
				webBookroom.setStaffid(teacher.getGeneralCard());
				webBookroom.setControllerid(teachPlaceMap.get(idPeriod[0]).getControllerID());
				webBookroom.setDoorno(teachPlaceMap.get(idPeriod[0]).getDoorNO());
				//中午时间段
				if(Constants.NOON_NUMBER.equals(idPeriod[1])){
					webBookroom.setBegindatetime(sdf.format(officeApplyNumber.getApplyDate())+officeTimeSet.getNoonStartTime().replace(":","")+"00");
					webBookroom.setEnddatetime(sdf.format(officeApplyNumber.getApplyDate())+officeTimeSet.getNoonEndTime().replace(":","")+"00");
				}else{
					webBookroom.setBegindatetime(sdf.format(officeApplyNumber.getApplyDate())+sstsMap.get(idPeriod[1]).getBeginTime().replace(":","")+"00");
					webBookroom.setEnddatetime(sdf.format(officeApplyNumber.getApplyDate())+sstsMap.get(idPeriod[1]).getEndTime().replace(":","")+"00");
				}
				webBookroom.setSendflag(0);
				webBookrooms.add(webBookroom);
			}
			OfficeUtilityApply officeUtilityApply = new OfficeUtilityApply();
			OfficeUtilityNumber officeUtilityNumber = new OfficeUtilityNumber();
			if(StringUtils.isBlank(id)){
				content += "申请使用"+teachPlaceMap.get(idPeriod[0]).getPlaceName();
				id = idPeriod[0];
			}
			String s = idPeriod[1];
			if(Constants.NOON_NUMBER.equals(idPeriod[1])){
				s = Constants.NOON_NAME;
			}
			if(id.equals(idPeriod[0])){
				if(StringUtils.isBlank(periods)){
					periods += s;
				}else{
					periods += ","+s;
				}
			}else{
				content += "节次为"+periods+";"+teachPlaceMap.get(idPeriod[0]).getPlaceName();
				id = idPeriod[0];
				periods = s;
			}
			//设置申请信息
			officeUtilityApply.setId(UUIDGenerator.getUUID());
			officeUtilityApply.setType(officeApplyNumber.getType());
			officeUtilityApply.setUnitId(officeApplyNumber.getUnitId());
			officeUtilityApply.setState(officeApplyNumber.getState());
			officeUtilityApply.setRoomId(idPeriod[0]);
			officeUtilityApply.setApplyPeriod(idPeriod[1]);
			officeUtilityApply.setUserId(officeApplyNumber.getApplyUserId());
			officeUtilityApply.setApplyDate(officeApplyNumber.getApplyDate());
			officeUtilityApply.setPurpose(officeApplyNumber.getPurpose());
			officeUtilityApply.setCourseId(officeApplyNumber.getCourseId());
			officeUtilityApplies.add(officeUtilityApply);
			//设置关联信息
			officeUtilityNumber.setNumberId(officeApplyNumber.getId());
			officeUtilityNumber.setUtilityId(officeUtilityApply.getId());
			officeUtilityNumber.setUnitId(officeApplyNumber.getUnitId());
			officeUtilityNumbers.add(officeUtilityNumber);
		}
		if(flag){
			return flag;
		}
		content += "节次为"+periods+"";
		
		officeApplyNumber.setContent(content);
		officeApplyNumberDao.save(officeApplyNumber);
		officeUtilityNumberService.addOfficeUtilityNumbers(officeUtilityNumbers);
		officeUtilityApplyService.batchDelete(officeUtilityApplies);
		officeUtilityApplyService.addOfficeUtilityApplies(officeUtilityApplies);
		if(webBookrooms.size() > 0){
			webBookroomService.batchSave(webBookrooms);
		}
		return flag;
	}
	
	@Override
	public boolean saveRoom(OfficeApplyNumber officeApplyNumber, Map<String, StusysSectionTimeSetDto> sstsMap){
		String[] applyRooms = officeApplyNumber.getApplyRooms();
		Arrays.sort(applyRooms);  //进行排序
		boolean flag = false;//判断同一时间是否有其他人申请了，那边要刷新页面重新申请
		String id = "";//教室id
		String periods = "";//同一个教室的节次拼接
		String content = "";//需要组织的内容，推送到一卡通
		List<OfficeUtilityApply> officeUtilityApplies = new ArrayList<OfficeUtilityApply>();
		List<OfficeUtilityNumber> officeUtilityNumbers = new ArrayList<OfficeUtilityNumber>();
		OfficeRoomOrderSet officeRoomOrderSet = officeRoomOrderSetService.getOfficeRoomOrderSetByType(officeApplyNumber.getUnitId(), officeApplyNumber.getType());
		
		officeApplyNumber.setId(UUIDGenerator.getUUID());
		//时段设置信息，主要为了获取中午时间段
		OfficeTimeSet officeTimeSet = officeTimeSetService.getOfficeTimeSetByUnitId(officeApplyNumber.getUnitId());
		//推送到一卡通 TODO
		List<WebBookroom> webBookrooms = new ArrayList<WebBookroom>();
		User user = userService.getUser(officeApplyNumber.getApplyUserId());
		Teacher teacher = teacherService.getTeacher(user.getTeacherid());
		Set<String> teachPlaceIdSet = new HashSet<String>();
		for(String applyRoom:applyRooms){
			teachPlaceIdSet.add(applyRoom.split("_")[0]);
		}
		Map<String, TeachPlace> teachPlaceMap = teachPlaceService.getTeachPlaceMap(teachPlaceIdSet.toArray(new String[0]));
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyyMMddHHmmss");
		//设置用途信息
//		Map<String, Teacher> teacherMap = teacherService.getTeacherMap(officeApplyNumber.getUnitId());
		Map<String, BasicClass> classMap = basicClassService.getClassMap(officeApplyNumber.getUnitId());
		if(StringUtils.isNotBlank(officeApplyNumber.getCourseId())) {
			EduadmCourseDto eduadmCourse = eduadmSubsystemService.getEduadmCourseById(officeApplyNumber.getCourseId());
			String className = "";
			if(eduadmCourse.getStudyType().equals("1")) {
				className = classMap.get(eduadmCourse.getClassId()).getClassname();
			}else {
				className = eduadmSubsystemService.getEduadmTeachClassName(eduadmCourse.getClassId());
			}
			officeApplyNumber.setPurpose(className + " " + eduadmCourse.getSubjectName());
		}
		for(String applyRoom:applyRooms){
			String[] idPeriod = applyRoom.split("_");
			flag = officeUtilityApplyService.isApplyByOthers(idPeriod[0],idPeriod[1],officeApplyNumber.getType(),officeApplyNumber.getApplyUserId(),officeApplyNumber.getApplyDate());
			//说明已经被占用,那么要重新刷新再申请
			if(flag){
				break;
			}
			OfficeUtilityApply officeUtilityApply = new OfficeUtilityApply();
			OfficeUtilityNumber officeUtilityNumber = new OfficeUtilityNumber();
			
			if("0".equals(officeRoomOrderSet.getNeedAudit())){//不需要审核，保存时就推送到一卡通
				if(sstsMap.size() > 0){
					WebBookroom webBookroom = new WebBookroom();
					webBookroom.setStaffid(teacher.getGeneralCard());
					webBookroom.setControllerid(teachPlaceMap.get(idPeriod[0]).getControllerID());
					webBookroom.setDoorno(teachPlaceMap.get(idPeriod[0]).getDoorNO());
					//中午时间段
					if(Constants.NOON_NUMBER.equals(idPeriod[1])){
						webBookroom.setBegindatetime(sdf.format(officeApplyNumber.getApplyDate())+officeTimeSet.getNoonStartTime().replace(":","")+"00");
						webBookroom.setEnddatetime(sdf.format(officeApplyNumber.getApplyDate())+officeTimeSet.getNoonEndTime().replace(":","")+"00");
					}else{
						webBookroom.setBegindatetime(sdf.format(officeApplyNumber.getApplyDate())+sstsMap.get(idPeriod[1]).getBeginTime().replace(":","")+"00");
						webBookroom.setEnddatetime(sdf.format(officeApplyNumber.getApplyDate())+sstsMap.get(idPeriod[1]).getEndTime().replace(":","")+"00");
					}
					webBookroom.setSendflag(0);
					webBookrooms.add(webBookroom);
				}
			}
			
			if(Constants.OFFICE_ROOM_ORDER_USER_TYPE_SECTION.equals(officeRoomOrderSet.getUseType())){//节次
				if(StringUtils.isBlank(id)){
					content += "申请使用"+teachPlaceMap.get(idPeriod[0]).getPlaceName();
					id = idPeriod[0];
				}
				String s = idPeriod[1];
				if(Constants.NOON_NUMBER.equals(idPeriod[1])){
					s = Constants.NOON_NAME;
				}
				if(id.equals(idPeriod[0])){
					if(StringUtils.isBlank(periods)){
						periods += s;
					}else{
						periods += ","+s;
					}
				}else{
					content += "节次为"+periods+";"+teachPlaceMap.get(idPeriod[0]).getPlaceName();
					id = idPeriod[0];
					periods = s;
				}
			}else{//时间段
				if(StringUtils.isBlank(id)){
					content += "申请使用"+teachPlaceMap.get(idPeriod[0]).getPlaceName();
					id = idPeriod[0];
				}
				String s = idPeriod[1].substring(0,2)+":"+idPeriod[1].substring(2,4);
				if(id.equals(idPeriod[0])){
					if(StringUtils.isBlank(periods)){
						periods += s;
					}else{
						periods += ","+s;
					}
				}else{
					content += "时间段为"+periods+";"+teachPlaceMap.get(idPeriod[0]).getPlaceName();
					id = idPeriod[0];
					periods = s;
				}
			}
			
			//设置申请信息
			officeUtilityApply.setId(UUIDGenerator.getUUID());
			officeUtilityApply.setType(officeApplyNumber.getType());
			officeUtilityApply.setUnitId(officeApplyNumber.getUnitId());
			officeUtilityApply.setState(officeApplyNumber.getState());
			officeUtilityApply.setRoomId(idPeriod[0]);
			officeUtilityApply.setApplyPeriod(idPeriod[1]);
			officeUtilityApply.setUserId(officeApplyNumber.getApplyUserId());
			officeUtilityApply.setApplyDate(officeApplyNumber.getApplyDate());
			officeUtilityApply.setPurpose(officeApplyNumber.getPurpose());
			officeUtilityApply.setCourseId(officeApplyNumber.getCourseId());
			officeUtilityApplies.add(officeUtilityApply);
			//设置关联信息
			officeUtilityNumber.setNumberId(officeApplyNumber.getId());
			officeUtilityNumber.setUtilityId(officeUtilityApply.getId());
			officeUtilityNumber.setUnitId(officeApplyNumber.getUnitId());
			officeUtilityNumbers.add(officeUtilityNumber);
		}
		if(flag){
			return flag;
		}
		if(Constants.OFFICE_ROOM_ORDER_USER_TYPE_SECTION.equals(officeRoomOrderSet.getUseType())){//节次
			content += "节次为"+periods+"";
		}else{
			content += "时间段为"+periods+"";
		}
		
		officeApplyNumber.setContent(content);
		officeApplyNumberDao.save(officeApplyNumber);
		officeUtilityNumberService.addOfficeUtilityNumbers(officeUtilityNumbers);
		officeUtilityApplyService.batchDelete(officeUtilityApplies);
		officeUtilityApplyService.addOfficeUtilityApplies(officeUtilityApplies);
		if(webBookrooms.size() > 0){
			webBookroomService.batchSave(webBookrooms);
		}
		
		//发短息提醒
		if("1".equals(officeRoomOrderSet.getNeedAudit())){
			try {
				User applyUser = userService.getUser(officeApplyNumber.getApplyUserId());
				String applyDate = DateUtils.date2String(officeApplyNumber.getApplyDate(),"yyyy-MM-dd") + "（" + OfficeUtils.dayForWeek(officeApplyNumber.getApplyDate()) + "）";
				if(applyUser != null){
					content = applyUser.getRealname() + "申请预约在" + "“" + applyDate + "”“" + content + "”";
					this.sendSMS(applyUser, content);
				}
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return flag;
	}
	
	@Override
	public boolean saveMeetingRoom(OfficeApplyNumber officeApplyNumber,UploadFile file){//TODO
		String[] applyRooms = officeApplyNumber.getApplyRooms();
		Arrays.sort(applyRooms);  //进行排序
		boolean flag = false;//判断同一时间是否有其他人申请了，那边要刷新页面重新申请
		String id = "";//教室id
		String periods = "";//同一个教室的节次拼接
		String content = "";//需要组织的内容，推送到一卡通
		List<OfficeUtilityApply> officeUtilityApplies = new ArrayList<OfficeUtilityApply>();
		List<OfficeUtilityNumber> officeUtilityNumbers = new ArrayList<OfficeUtilityNumber>();
		List<String> utilityApplyIds = new ArrayList<String>();
		List<String> utilityNumberIds = new ArrayList<String>();
		OfficeRoomOrderSet officeRoomOrderSet = officeRoomOrderSetService.getOfficeRoomOrderSetByType(officeApplyNumber.getUnitId(), officeApplyNumber.getType());
		officeApplyNumber.setId(UUIDGenerator.getUUID());
		if("0".equals(officeRoomOrderSet.getNeedAudit())){//不需要审核
			officeApplyNumber.setState(Constants.APPLY_STATE_PASS);//直接通过
		}else{
			officeApplyNumber.setState(Constants.APPLY_STATE_NEED_AUDIT);
		}
		Set<String> teachPlaceIdSet = new HashSet<String>(); 
		for(String applyRoom:applyRooms){
			teachPlaceIdSet.add(applyRoom.split("_")[0]);
		}
		Map<String, TeachPlace> teachPlaceMap = teachPlaceService.getTeachPlaceMap(teachPlaceIdSet.toArray(new String[0]));
		for(String applyRoom:applyRooms){
			String[] idPeriod = applyRoom.split("_");
			flag = officeUtilityApplyService.isApplyByOthers(idPeriod[0],idPeriod[1],officeApplyNumber.getType(),officeApplyNumber.getApplyUserId(),officeApplyNumber.getApplyDate());
			//说明已经被占用,那么要重新刷新再申请
			if(flag){
				break;
			}
			OfficeUtilityApply officeUtilityApply = new OfficeUtilityApply();
			OfficeUtilityNumber officeUtilityNumber = new OfficeUtilityNumber();
			
			if(Constants.OFFICE_ROOM_ORDER_USER_TYPE_SECTION.equals(officeRoomOrderSet.getUseType())){//节次
				if(StringUtils.isBlank(id)){
					content += "申请使用"+teachPlaceMap.get(idPeriod[0]).getPlaceName();
					id = idPeriod[0];
				}
				String s = idPeriod[1];
				if(Constants.NOON_NUMBER.equals(idPeriod[1])){
					s = Constants.NOON_NAME;
				}
				if(id.equals(idPeriod[0])){
					if(StringUtils.isBlank(periods)){
						periods += s;
					}else{
						periods += ","+s;
					}
				}else{
					content += "节次为"+periods+";"+teachPlaceMap.get(idPeriod[0]).getPlaceName();
					id = idPeriod[0];
					periods = s;
				}
			}else{//时间段
				if(StringUtils.isBlank(id)){
					content += "申请使用"+teachPlaceMap.get(idPeriod[0]).getPlaceName();
					id = idPeriod[0];
				}
				String s = idPeriod[1].substring(0,2)+":"+idPeriod[1].substring(2,4);
				if(id.equals(idPeriod[0])){
					if(StringUtils.isBlank(periods)){
						periods += s;
					}else{
						periods += ","+s;
					}
				}else{
					content += "时间段为"+periods+";"+teachPlaceMap.get(idPeriod[0]).getPlaceName();
					id = idPeriod[0];
					periods = s;
				}
			}
			
			//设置申请信息
			officeUtilityApply.setId(UUIDGenerator.getUUID());
			officeUtilityApply.setType(officeApplyNumber.getType());
			officeUtilityApply.setUnitId(officeApplyNumber.getUnitId());
			officeUtilityApply.setState(officeApplyNumber.getState());
			officeUtilityApply.setRoomId(idPeriod[0]);
			officeUtilityApply.setApplyPeriod(idPeriod[1]);
			officeUtilityApply.setUserId(officeApplyNumber.getApplyUserId());
			officeUtilityApply.setApplyDate(officeApplyNumber.getApplyDate());
			officeUtilityApply.setPurpose(officeApplyNumber.getPurpose());
			officeUtilityApplies.add(officeUtilityApply);
			utilityApplyIds.add(officeUtilityApply.getId());
			//设置关联信息
			officeUtilityNumber.setId(UUIDGenerator.getUUID());
			officeUtilityNumber.setNumberId(officeApplyNumber.getId());
			officeUtilityNumber.setUtilityId(officeUtilityApply.getId());
			officeUtilityNumber.setUnitId(officeApplyNumber.getUnitId());
			officeUtilityNumbers.add(officeUtilityNumber);
			utilityNumberIds.add(officeUtilityNumber.getId());
		}
		if(flag){
			return flag;
		}
		if(Constants.OFFICE_ROOM_ORDER_USER_TYPE_SECTION.equals(officeRoomOrderSet.getUseType())){//节次
			content += "节次为"+periods+"";
		}else{
			content += "时间段为"+periods+"";
		}
		
		officeApplyNumber.setContent(content);
		
		if("0".equals(officeRoomOrderSet.getNeedAudit())){//不需要审核
			officeApplyNumber.setAuditUserId(officeApplyNumber.getApplyUserId());
			officeApplyNumber.setAuditTime(new Date());
			sendMsg(officeApplyNumber,officeApplyNumber.getApplyUserId());//推送信息到消息中心，申请人为发件人
		}
		officeApplyNumberDao.save(officeApplyNumber);
		
		officeUtilityApplyService.batchDelete(officeUtilityApplies);
		officeUtilityApplyService.addOfficeUtilityApplies(officeUtilityApplies);
		officeUtilityNumberService.addOfficeUtilityNumbers(officeUtilityNumbers);
		
		//会议室申请有附件及参会人员id
		String meetingUserIds = officeApplyNumber.getMeetingUserIds();
		officeMeetingUserService.batchsave(officeApplyNumber.getId(), meetingUserIds.split(","));
		if(file != null){
			Attachment attachment = new Attachment();
			attachment.setFileName(file.getFileName());
			attachment.setContentType(file.getContentType());
			attachment.setFileSize(file.getFileSize());
			attachment.setUnitId(officeApplyNumber.getUnitId());
			attachment.setObjectId(officeApplyNumber.getId());
			attachment.setConStatus(BusinessTask.TASK_STATUS_NOT_NEED_HAND);
			attachment.setObjectType(OfficeApplyNumber.OFFICE_MEETING_ROOM_ATTACHMENT);
			attachmentService.saveAttachment(attachment, file);
		}
		
		if("1".equals(officeRoomOrderSet.getNeedAudit())){
			try {
				User applyUser = userService.getUser(officeApplyNumber.getApplyUserId());
				String applyDate = DateUtils.date2String(officeApplyNumber.getApplyDate(),"yyyy-MM-dd") + "（" + OfficeUtils.dayForWeek(officeApplyNumber.getApplyDate()) + "）";
				if(applyUser != null){
					content = applyUser.getRealname() + "申请预约在" + "“" + applyDate + "”“" + content + "”";
					this.sendSMS(applyUser, content);
				}
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return flag;
	}
	
	public boolean saveLabRoom(OfficeApplyNumber officeApplyNumber, OfficeLabInfo officeLabInfo){//TODO
		String[] applyRooms = officeApplyNumber.getApplyRooms();
		Arrays.sort(applyRooms);  //进行排序
		boolean flag = false;//判断同一时间是否有其他人申请了，那边要刷新页面重新申请
		String id = "";//教室id
		String periods = "";//同一个教室的节次拼接
		String content = "";//需要组织的内容，推送到一卡通
		List<OfficeUtilityApply> officeUtilityApplies = new ArrayList<OfficeUtilityApply>();
		List<OfficeUtilityNumber> officeUtilityNumbers = new ArrayList<OfficeUtilityNumber>();
		
		OfficeRoomOrderSet officeRoomOrderSet = officeRoomOrderSetService.getOfficeRoomOrderSetByType(officeApplyNumber.getUnitId(), officeApplyNumber.getType());
		officeApplyNumber.setId(UUIDGenerator.getUUID());
		if("0".equals(officeRoomOrderSet.getNeedAudit())){//不需要审核
			officeApplyNumber.setState(Constants.APPLY_STATE_PASS);//直接通过
			officeApplyNumber.setAuditUserId(officeApplyNumber.getApplyUserId());
			officeApplyNumber.setAuditTime(new Date());
		}else{
			officeApplyNumber.setState(Constants.APPLY_STATE_NEED_AUDIT);
		}
		
		Set<String> teachPlaceIdSet = new HashSet<String>(); 
		for(String applyRoom:applyRooms){
			teachPlaceIdSet.add(applyRoom.split("_")[0]);
		}
		Map<String, TeachPlace> teachPlaceMap = teachPlaceService.getTeachPlaceMap(teachPlaceIdSet.toArray(new String[0]));
		
		for(String applyRoom:applyRooms){
			String[] idPeriod = applyRoom.split("_");
			if(StringUtils.isNotBlank(officeApplyNumber.getUseType())&&StringUtils.equals(officeApplyNumber.getUseType(), "1")){
				flag = officeUtilityApplyService.isApplyByOthers(idPeriod[0],idPeriod[1],officeApplyNumber.getType(),officeApplyNumber.getApplyUserId(),officeApplyNumber.getApplyDate());
				//说明已经被占用,那么要重新刷新再申请
				if(flag){
					break;
				}
			}
			OfficeUtilityApply officeUtilityApply = new OfficeUtilityApply();
			OfficeUtilityNumber officeUtilityNumber = new OfficeUtilityNumber();
			
			if(StringUtils.isNotBlank(officeApplyNumber.getUseType())&&StringUtils.equals(officeApplyNumber.getUseType(), "1")){
				if(Constants.OFFICE_ROOM_ORDER_USER_TYPE_SECTION.equals(officeRoomOrderSet.getUseType())){//节次
					if(StringUtils.isBlank(id)){
						content += "申请使用"+teachPlaceMap.get(idPeriod[0]).getPlaceName();
						id = idPeriod[0];
					}
					String s = idPeriod[1];
					if(Constants.NOON_NUMBER.equals(idPeriod[1])){
						s = Constants.NOON_NAME;
					}
					if(id.equals(idPeriod[0])){
						if(StringUtils.isBlank(periods)){
							periods += s;
						}else{
							periods += ","+s;
						}
					}else{
						content += "节次为"+periods+";"+teachPlaceMap.get(idPeriod[0]).getPlaceName();
						id = idPeriod[0];
						periods = s;
					}
				}else{//时间段
					if(StringUtils.isBlank(id)){
						content += "申请使用"+teachPlaceMap.get(idPeriod[0]).getPlaceName();
						id = idPeriod[0];
					}
					String s = idPeriod[1].substring(0,2)+":"+idPeriod[1].substring(2,4);
					if(id.equals(idPeriod[0])){
						if(StringUtils.isBlank(periods)){
							periods += s;
						}else{
							periods += ","+s;
						}
					}else{
						content += "时间段为"+periods+";"+teachPlaceMap.get(idPeriod[0]).getPlaceName();
						id = idPeriod[0];
						periods = s;
					}
				}
			}else{
				content="申请使用实验室时间段为"+officeApplyNumber.getUseStartTime()+"到"+officeApplyNumber.getUseEndTime();
			}
			
			//设置申请信息
			officeUtilityApply.setId(UUIDGenerator.getUUID());
			officeUtilityApply.setType(officeApplyNumber.getType());
			officeUtilityApply.setUnitId(officeApplyNumber.getUnitId());
			officeUtilityApply.setState(officeApplyNumber.getState());
			officeUtilityApply.setRoomId(idPeriod[0]);
			officeUtilityApply.setApplyPeriod(idPeriod[1]);
			officeUtilityApply.setUserId(officeApplyNumber.getApplyUserId());
			officeUtilityApply.setApplyDate(officeApplyNumber.getApplyDate());
			officeUtilityApply.setPurpose(officeApplyNumber.getPurpose());
			officeUtilityApplies.add(officeUtilityApply);
			//设置关联信息
			officeUtilityNumber.setId(UUIDGenerator.getUUID());
			officeUtilityNumber.setNumberId(officeApplyNumber.getId());
			officeUtilityNumber.setUtilityId(officeUtilityApply.getId());
			officeUtilityNumber.setUnitId(officeApplyNumber.getUnitId());
			officeUtilityNumbers.add(officeUtilityNumber);
		}
		
		if(flag){
			return flag;
		}
		
		if(StringUtils.isNotBlank(officeApplyNumber.getUseType())&&StringUtils.equals(officeApplyNumber.getUseType(), "1")){
			if(Constants.OFFICE_ROOM_ORDER_USER_TYPE_SECTION.equals(officeRoomOrderSet.getUseType())){//节次
				content += "节次为"+periods+"";
			}else{
				content += "时间段为"+periods+"";
			}
		}
		
		officeApplyNumber.setContent(content);
		
		//设置实验详情
		officeLabInfo.setId(UUIDGenerator.getUUID());
		officeLabInfo.setUnitId(officeApplyNumber.getUnitId());
		officeLabInfoService.save(officeLabInfo);
		
		officeApplyNumber.setLabInfoId(officeLabInfo.getId());
		officeApplyNumberDao.save(officeApplyNumber);
		
		for(OfficeUtilityApply apply : officeUtilityApplies){
			apply.setLabInfoId(officeLabInfo.getId());
		}
		officeUtilityApplyService.batchDelete(officeUtilityApplies);
		officeUtilityApplyService.addOfficeUtilityApplies(officeUtilityApplies);
		officeUtilityNumberService.addOfficeUtilityNumbers(officeUtilityNumbers);
		
		if("1".equals(officeRoomOrderSet.getNeedAudit())){
			try {
				User applyUser = userService.getUser(officeApplyNumber.getApplyUserId());
				String applyDate = DateUtils.date2String(officeApplyNumber.getApplyDate(),"yyyy-MM-dd") + "（" + OfficeUtils.dayForWeek(officeApplyNumber.getApplyDate()) + "）";
				if(applyUser != null){
					content = applyUser.getRealname() + "申请预约在" + "“" + applyDate + "”“" + content + "”";
					this.sendSMS(applyUser, content);
				}
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return flag;
	}
	
	@Override
	public void cancel(OfficeApplyNumber officeApplyNumber, boolean flag, Map<String, StusysSectionTimeSetDto> sstsMap) {
		//TODO
		String[] applyRooms = officeApplyNumber.getApplyRooms();
		Arrays.sort(applyRooms);  //进行排序
		String id = "";//教室id
		String periods = "";//同一个教室的节次拼接
		String content = "";//需要组织的内容，推送到一卡通
		List<OfficeUtilityApply> officeUtilityApplies = new ArrayList<OfficeUtilityApply>();
		Set<String> teachPlaceIdSet = new HashSet<String>();
		for(String applyRoom:applyRooms){
			teachPlaceIdSet.add(applyRoom.split("_")[0]);
		}
		Map<String, TeachPlace> teachPlaceMap = teachPlaceService.getTeachPlaceMap(teachPlaceIdSet.toArray(new String[0]));
		OfficeRoomOrderSet officeRoomOrderSet = officeRoomOrderSetService.getOfficeRoomOrderSetByType(officeApplyNumber.getUnitId(), officeApplyNumber.getType());
		officeApplyNumber.setId(UUIDGenerator.getUUID());
		for(String applyRoom:applyRooms){
			String[] idPeriod = applyRoom.split("_");
			OfficeUtilityApply officeUtilityApply = new OfficeUtilityApply();
			if(StringUtils.isBlank(id)){
				content += "撤销使用"+teachPlaceMap.get(idPeriod[0]).getPlaceName();
				id = idPeriod[0];
			}
			String s = "";
			if(Constants.OFFICE_ROOM_ORDER_USER_TYPE_SECTION.equals(officeRoomOrderSet.getUseType())){//节次
				s = idPeriod[1];
				if(Constants.NOON_NUMBER.equals(idPeriod[1])){
					s = Constants.NOON_NAME;
				}
			}else{
				s = idPeriod[1].substring(0,2)+":"+idPeriod[1].substring(2,4);
			}
			if(id.equals(idPeriod[0])){
				if(StringUtils.isBlank(periods)){
					periods += s;
				}else{
					periods += ","+s;
				}
			}else{
				if(Constants.OFFICE_ROOM_ORDER_USER_TYPE_SECTION.equals(officeRoomOrderSet.getUseType())){//节次
					content += "节次为"+periods+";"+teachPlaceMap.get(idPeriod[0]).getPlaceName();
				}else{
					content += "时间段为"+periods+";"+teachPlaceMap.get(idPeriod[0]).getPlaceName();
				}
				id = idPeriod[0];
				periods = s;
			}
			//设置申请信息
			officeUtilityApply.setType(officeApplyNumber.getType());
			officeUtilityApply.setRoomId(idPeriod[0]);
			officeUtilityApply.setApplyPeriod(idPeriod[1]);
			officeUtilityApply.setUserId(officeApplyNumber.getApplyUserId());
			officeUtilityApply.setUnitId(officeApplyNumber.getUnitId());
			officeUtilityApply.setApplyDate(officeApplyNumber.getApplyDate());
			officeUtilityApplies.add(officeUtilityApply);
		}
		if(Constants.OFFICE_ROOM_ORDER_USER_TYPE_SECTION.equals(officeRoomOrderSet.getUseType())){//节次
			content += "节次为"+periods+"";
		}else{
			content += "时间段为"+periods+"";
		}
		officeApplyNumber.setContent(content);
		officeApplyNumberDao.save(officeApplyNumber);
		officeUtilityApplyService.batchDelete(officeUtilityApplies);
		
		if(flag){
			if(Constants.OFFICE_ROOM_ORDER_USER_TYPE_SECTION.equals(officeRoomOrderSet.getUseType())){//节次
				//时段设置信息，主要为了获取中午时间段
				OfficeTimeSet officeTimeSet = officeTimeSetService.getOfficeTimeSetByUnitId(officeApplyNumber.getUnitId());
				cancelSectionRoom(officeTimeSet,officeUtilityApplies,sstsMap);
			}else{
				cancelTimeRoom(officeUtilityApplies);
			}
		}
		
		//TODO 撤销发短息提醒
		if("1".equals(officeRoomOrderSet.getNeedAudit())){
			try {
				User applyUser = userService.getUser(officeApplyNumber.getApplyUserId());
				String applyDate = DateUtils.date2String(officeApplyNumber.getApplyDate(),"yyyy-MM-dd") + "（" + OfficeUtils.dayForWeek(officeApplyNumber.getApplyDate()) + "）";
				if(applyUser != null){
					content = applyUser.getRealname() + "撤销申请预约在" + "“" + applyDate + "”“" + content + "”";
					this.sendSMS(applyUser, content);
				}
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void cancelSectionRoom(OfficeTimeSet officeTimeSet,List<OfficeUtilityApply> ouaList,Map<String, StusysSectionTimeSetDto> sstsMap){
		if(ouaList.size() > 0){
			Set<String> userIds = new HashSet<String>();
			Set<String> roomIds = new HashSet<String>();
			for(OfficeUtilityApply officeUtilityApply:ouaList){
				userIds.add(officeUtilityApply.getUserId());
				roomIds.add(officeUtilityApply.getRoomId());
			}
			Map<String, User> userMap = userService.getUserWithDelMap(userIds.toArray(new String[0]));
			Map<String, TeachPlace> tpMap = teachPlaceService.getTeachPlaceMap(roomIds.toArray(new String[0]));
			List<String> teacherIds = new ArrayList<String>();
			for(User user:userMap.values()){
				teacherIds.add(user.getTeacherid());
			}
			Map<String, Teacher> teacherMap = teacherService.getTeacherMap(teacherIds.toArray(new String[0]));
			List<WebBookroom> webBookrooms = new ArrayList<WebBookroom>();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			for(OfficeUtilityApply officeUtilityApply:ouaList){
				WebBookroom webBookroom = new WebBookroom();
				User user = userMap.get(officeUtilityApply.getUserId());
				Teacher teacher = teacherMap.get(user.getTeacherid());
				webBookroom.setStaffid(teacher.getGeneralCard());
				webBookroom.setControllerid(tpMap.get(officeUtilityApply.getRoomId()).getControllerID());
				webBookroom.setDoorno(tpMap.get(officeUtilityApply.getRoomId()).getDoorNO());
				//中午时间段
				if(Constants.NOON_NUMBER.equals(officeUtilityApply.getApplyPeriod())){
					webBookroom.setBegindatetime(sdf.format(officeUtilityApply.getApplyDate())+officeTimeSet.getNoonStartTime().replace(":","")+"00");
					webBookroom.setEnddatetime(sdf.format(officeUtilityApply.getApplyDate())+officeTimeSet.getNoonEndTime().replace(":","")+"00");
				}else{
					webBookroom.setBegindatetime(sdf.format(officeUtilityApply.getApplyDate())+sstsMap.get(officeUtilityApply.getApplyPeriod()).getBeginTime().replace(":","")+"00");
					webBookroom.setEnddatetime(sdf.format(officeUtilityApply.getApplyDate())+sstsMap.get(officeUtilityApply.getApplyPeriod()).getEndTime().replace(":","")+"00");
				}
				webBookroom.setSendflag(2);
				webBookrooms.add(webBookroom);
			}
			if(webBookrooms.size() > 0){
				webBookroomService.batchUpdate(webBookrooms);
			}
		}
	}
	
	public void cancelTimeRoom(List<OfficeUtilityApply> ouaList){
		if(ouaList.size() > 0){
			Set<String> userIds = new HashSet<String>();
			Set<String> roomIds = new HashSet<String>();
			for(OfficeUtilityApply officeUtilityApply:ouaList){
				userIds.add(officeUtilityApply.getUserId());
				roomIds.add(officeUtilityApply.getRoomId());
			}
			String unitId = ouaList.get(0).getUnitId();
			OfficeTimeSet officeTimeSet = officeTimeSetService.getOfficeTimeSetByUnitId(unitId);
			Map<String, User> userMap = userService.getUserWithDelMap(userIds.toArray(new String[0]));
			Map<String, TeachPlace> tpMap = teachPlaceService.getTeachPlaceMap(roomIds.toArray(new String[0]));
			List<String> teacherIds = new ArrayList<String>();
			for(User user:userMap.values()){
				teacherIds.add(user.getTeacherid());
			}
			Map<String, Teacher> teacherMap = teacherService.getTeacherMap(teacherIds.toArray(new String[0]));
			List<WebBookroom> webBookrooms = new ArrayList<WebBookroom>();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			SimpleDateFormat sdf1 = new SimpleDateFormat("yyyyMMddHHmmss");
			for(OfficeUtilityApply officeUtilityApply:ouaList){
				WebBookroom webBookroom = new WebBookroom();
				User user = userMap.get(officeUtilityApply.getUserId());
				Teacher teacher = teacherMap.get(user.getTeacherid());
				webBookroom.setStaffid(teacher.getGeneralCard());
				webBookroom.setControllerid(tpMap.get(officeUtilityApply.getRoomId()).getControllerID());
				webBookroom.setDoorno(tpMap.get(officeUtilityApply.getRoomId()).getDoorNO());
				webBookroom.setBegindatetime(sdf.format(officeUtilityApply.getApplyDate())+officeUtilityApply.getApplyPeriod()+"00");
				String endTime = sdf.format(officeUtilityApply.getApplyDate())+officeUtilityApply.getApplyPeriod()+"00";
				try {
					Date date = sdf1.parse(endTime);
					date = DateUtils.addMinute(date, officeTimeSet.getTimeInterval());
					webBookroom.setEnddatetime(sdf1.format(date));
				} catch (ParseException e) {
					e.printStackTrace();
				}
				webBookroom.setSendflag(2);
				webBookrooms.add(webBookroom);
			}
			if(webBookrooms.size() > 0){
				webBookroomService.batchUpdate(webBookrooms);
			}
		}
	}

	@Override
	public Integer delete(String[] ids){
		return officeApplyNumberDao.delete(ids);
	}

	@Override
	public OfficeApplyNumber getOfficeApplyNumberById(String id){
		OfficeApplyNumber officeApplyNumber = officeApplyNumberDao.getOfficeApplyNumberById(id);
		Set<String> userIdSet = new HashSet<String>();
		userIdSet.add(officeApplyNumber.getApplyUserId());
		if(officeApplyNumber.getState() > Constants.APPLY_STATE_NEED_AUDIT){
			userIdSet.add(officeApplyNumber.getAuditUserId());
		}
		Map<String,User> userMap = userService.getUserWithDelMap(userIdSet.toArray(new String[0]));
		
		if(userMap.containsKey(officeApplyNumber.getApplyUserId())){
			officeApplyNumber.setUserName(userMap.get(officeApplyNumber.getApplyUserId()).getRealname());
		}else{
			officeApplyNumber.setUserName("用户已删除");
		}
		
		if(officeApplyNumber.getState() > Constants.APPLY_STATE_NEED_AUDIT){
			if(userMap.containsKey(officeApplyNumber.getAuditUserId())){
				officeApplyNumber.setAuditUserName(userMap.get(officeApplyNumber.getAuditUserId()).getRealname());
			}else{
				officeApplyNumber.setAuditUserName("用户已删除");
			}
		}
		
		
		if(TeachPlace.PLACE_TYPE_MEETING.equals(officeApplyNumber.getType()) && StringUtils.isNotBlank(officeApplyNumber.getMeetingTheme())){
			Map<String, Dept> deptMap = deptService.getDeptMap(officeApplyNumber.getUnitId());
			userIdSet = new HashSet<String>();
			if(StringUtils.isNotBlank(officeApplyNumber.getHostUserId())){
				userIdSet.add(officeApplyNumber.getHostUserId());
			}
			List<OfficeMeetingUser> officeMeetingUsers = officeMeetingUserService.getOfficeMeetingUserListByApplyId(id);
			for(OfficeMeetingUser officeMeetingUser:officeMeetingUsers){
				userIdSet.add(officeMeetingUser.getUserId());
			}
			userMap = userService.getUserWithDelMap(userIdSet.toArray(new String[0]));
			if(StringUtils.isNotBlank(officeApplyNumber.getHostUserId())){
				if(userMap.containsKey(officeApplyNumber.getHostUserId())){
					officeApplyNumber.setHostUserName(userMap.get(officeApplyNumber.getHostUserId()).getRealname());
				}else{
					officeApplyNumber.setHostUserName("用户已删除");
				}
			}
			String[] deptIds = officeApplyNumber.getDeptIds().split(",");
			StringBuffer sbf = new StringBuffer();
			int i = 0;
			for(String deptId:deptIds){
				if(deptMap.containsKey(deptId)){
					if(i==0){
						sbf.append(deptMap.get(deptId).getDeptname());
					}else{
						sbf.append(","+deptMap.get(deptId).getDeptname());
					}
					i++;
				}
			}
			officeApplyNumber.setDeptNames(sbf.toString());
			sbf = new StringBuffer();
			i = 0;
			for(OfficeMeetingUser officeMeetingUser:officeMeetingUsers){
				if(userMap.containsKey(officeMeetingUser.getUserId())){
					if(i==0){
						sbf.append(userMap.get(officeMeetingUser.getUserId()).getRealname());
					}else{
						sbf.append(","+userMap.get(officeMeetingUser.getUserId()).getRealname());
					}
					i++;
				}
			}
			officeApplyNumber.setMeetingUserNames(sbf.toString());
			//获取附件信息
			List<Attachment> attachments = attachmentService
					.getAttachments(officeApplyNumber.getId(), OfficeApplyNumber.OFFICE_MEETING_ROOM_ATTACHMENT);
			if(attachments!=null && attachments.size() > 0){
				officeApplyNumber.setAttachment(attachments.get(0));
			}
		}
		
		return officeApplyNumber;
	}

	@Override
	public Map<String, OfficeApplyNumber> getOfficeApplyNumberMapByIds(String[] ids){
		return officeApplyNumberDao.getOfficeApplyNumberMapByIds(ids);
	}

	@Override
	public List<OfficeApplyNumber> getOfficeApplyNumberPage(Pagination page){
		return officeApplyNumberDao.getOfficeApplyNumberPage(page);
	}

	@Override
	public List<OfficeApplyNumber> getOfficeApplyNumberByUnitIdList(String unitId){
		return officeApplyNumberDao.getOfficeApplyNumberByUnitIdList(unitId);
	}

	@Override
	public List<OfficeApplyNumber> getOfficeApplyNumberByUnitIdPage(String unitId, String userId, Date startTime,Date endTime,String roomType, String auditState, Pagination page){
		return officeApplyNumberDao.getOfficeApplyNumberByUnitIdPage(unitId, userId, startTime, endTime, roomType, auditState, page);
	}
	
	@Override
	public List<OfficeApplyNumber> getOfficeApplyNumberAuditPage(String unitId,
			Date startTime, Date endTime, String roomType, String auditState, String searchSubject, Pagination page) {
		List<OfficeApplyNumber> officeApplyNumbers = officeApplyNumberDao.getOfficeApplyNumberAuditPage(unitId, startTime, endTime, roomType, auditState, searchSubject, page);
		Set<String> userIdSet = new HashSet<String>();
		for(OfficeApplyNumber officeApplyNumber:officeApplyNumbers){
			userIdSet.add(officeApplyNumber.getApplyUserId());
		}
		Map<String, User> userMap = userService.getUserWithDelMap(userIdSet.toArray(new String[0]));
		for(OfficeApplyNumber officeApplyNumber:officeApplyNumbers){
			if(userMap.get(officeApplyNumber.getApplyUserId())!=null){
				officeApplyNumber.setUserName(userMap.get(officeApplyNumber.getApplyUserId()).getRealname());
			}else{
				officeApplyNumber.setUserName("用户已删除");
			}
		}
		return officeApplyNumbers;
	}
	
	@Override
	public void pass(String[] ids, String userId, boolean flag) {
		String[] utilityApplyIds = officeUtilityNumberService.getUtilityApplyIds(ids);
		
		//推送信息到消息中心，审核人为发件人
		List<OfficeApplyNumber> officeApplyNumbers = officeApplyNumberDao.getOfficeApplyNumberList(ids);
		for(OfficeApplyNumber officeApplyNumber:officeApplyNumbers){
			sendMsg(officeApplyNumber,userId);
		}
		
		// 推送到一卡通
		if(flag){
			sendToWebBookRoom(utilityApplyIds);
		}
		officeUtilityApplyService.updateStateByIds(utilityApplyIds,Constants.APPLY_STATE_PASS);
		officeApplyNumberDao.updatePass(ids, userId,Constants.APPLY_STATE_PASS);
	}
	
	@Override
	public void update(String id, String state, String remark, String userId, boolean flag) {
		String[] utilityApplyIds = officeUtilityNumberService.getUtilityApplyIds(new String[]{id});
		
		if(state.equals(String.valueOf(Constants.APPLY_STATE_PASS))){
			//推送信息到消息中心，审核人为发件人
			OfficeApplyNumber officeApplyNumber = officeApplyNumberDao.getOfficeApplyNumberById(id);
			sendMsg(officeApplyNumber,userId);
		}
		
		// 推送到一卡通
		if(flag && state.equals(String.valueOf(Constants.APPLY_STATE_PASS))){
			sendToWebBookRoom(utilityApplyIds);
		}
		officeUtilityApplyService.updateStateByIds(utilityApplyIds,Integer.valueOf(state));
		officeApplyNumberDao.update(id, state, remark, userId);
	}
	
	private void sendMsg(OfficeApplyNumber officeApplyNumber, String userId){
		User user = userService.getUser(userId);
		StringBuffer sbf = new StringBuffer();
		//申请人
		sbf.append(officeApplyNumber.getApplyUserId());
		//主持人
		if(StringUtils.isNotBlank(officeApplyNumber.getHostUserId()) && !officeApplyNumber.getApplyUserId().equals(officeApplyNumber.getHostUserId())){
			sbf.append(",").append(officeApplyNumber.getHostUserId());
		}
		//与会人员
		List<OfficeMeetingUser> officeMeetingUsers = officeMeetingUserService.getOfficeMeetingUserListByApplyId(officeApplyNumber.getId());
		for(OfficeMeetingUser officeMeetingUser:officeMeetingUsers){
			if(!officeMeetingUser.getUserId().equals(officeApplyNumber.getHostUserId()) && !officeMeetingUser.getUserId().equals(officeApplyNumber.getApplyUserId())){
				sbf.append(",").append(officeMeetingUser.getUserId());
			}
		}
		//TODO---------------------组织消息内容------------------------
		OfficeMsgSending officeMsgSending = new OfficeMsgSending();
		officeMsgSending.setCreateUserId(userId);
		officeMsgSending.setTitle("会议室申请通过信息提醒");
		officeMsgSending.setContent(getContent(officeApplyNumber));
		officeMsgSending.setSimpleContent(getContent(officeApplyNumber));
		officeMsgSending.setUnitId(officeApplyNumber.getUnitId());
		officeMsgSending.setUserIds(sbf.toString());
		officeMsgSending.setSendUserName(user.getRealname());
		officeMsgSending.setState(Constants.MSG_STATE_SEND);
		officeMsgSending.setIsNeedsms(false);
		officeMsgSending.setMsgType(BaseConstant.MSG_TYPE_MEETING);
		officeMsgSendingService.save(officeMsgSending, null, null);
	}
	
	private String getContent(OfficeApplyNumber officeApplyNumber) {
        StringBuffer sb = new StringBuffer();
        sb.append("    您好！您有新的会议需要参加。会议主题：")
                .append(officeApplyNumber.getMeetingTheme()).append("");
        sb.append("，日期：").append(DateUtils.date2String(officeApplyNumber.getApplyDate(),"yyyy-MM-dd"));
        sb.append("，会议室信息：").append(officeApplyNumber.getContent().substring(4));
        return sb.toString();
    }
	
	@Override
	public void updateFeedback(OfficeApplyNumber officeApplyNumber) {
		officeApplyNumberDao.updateFeedback(officeApplyNumber);
		
	}
	
	public void sendToWebBookRoom(String[] utilityApplyIds){
		List<OfficeUtilityApply> ouaList = officeUtilityApplyService.getOfficeUtilityApplyListByIds(utilityApplyIds);
		if(ouaList.size() > 0){
			Set<String> userIds = new HashSet<String>();
			Set<String> roomIds = new HashSet<String>();
			for(OfficeUtilityApply officeUtilityApply:ouaList){
				userIds.add(officeUtilityApply.getUserId());
				roomIds.add(officeUtilityApply.getRoomId());
			}
			String unitId = ouaList.get(0).getUnitId();
			OfficeTimeSet officeTimeSet = officeTimeSetService.getOfficeTimeSetByUnitId(unitId);
			Map<String, User> userMap = userService.getUserWithDelMap(userIds.toArray(new String[0]));
			Map<String, TeachPlace> tpMap = teachPlaceService.getTeachPlaceMap(roomIds.toArray(new String[0]));
			List<String> teacherIds = new ArrayList<String>();
			for(User user:userMap.values()){
				teacherIds.add(user.getTeacherid());
			}
			Map<String, Teacher> teacherMap = teacherService.getTeacherMap(teacherIds.toArray(new String[0]));
			List<WebBookroom> webBookrooms = new ArrayList<WebBookroom>();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			SimpleDateFormat sdf1 = new SimpleDateFormat("yyyyMMddHHmmss");
			for(OfficeUtilityApply officeUtilityApply:ouaList){
				WebBookroom webBookroom = new WebBookroom();
				User user = userMap.get(officeUtilityApply.getUserId());
				Teacher teacher = teacherMap.get(user.getTeacherid());
				if(StringUtils.isBlank(teacher.getGeneralCard())){//一卡通账号为空就不推送了
					continue;
				}
				webBookroom.setStaffid(teacher.getGeneralCard());
				webBookroom.setControllerid(tpMap.get(officeUtilityApply.getRoomId()).getControllerID());
				webBookroom.setDoorno(tpMap.get(officeUtilityApply.getRoomId()).getDoorNO());
				webBookroom.setBegindatetime(sdf.format(officeUtilityApply.getApplyDate())+officeUtilityApply.getApplyPeriod()+"00");
				String endTime = sdf.format(officeUtilityApply.getApplyDate())+officeUtilityApply.getApplyPeriod()+"00";
				try {
					Date date = sdf1.parse(endTime);
					date = DateUtils.addMinute(date, officeTimeSet.getTimeInterval());
					webBookroom.setEnddatetime(sdf1.format(date));
				} catch (ParseException e) {
					e.printStackTrace();
				}
				webBookroom.setSendflag(0);
				webBookrooms.add(webBookroom);
			}
			if(webBookrooms.size() > 0){
				webBookroomService.batchSave(webBookrooms);
			}
		}
	}
	
	public boolean isSchoolZHZG(){
		String deploySchool = systemIniService
				.getValue(BaseConstant.SYSTEM_DEPLOY_SCHOOL);
		String systemDeploySchool = org.apache.commons.lang.StringUtils.isNotBlank(deploySchool) ? deploySchool
				: org.apache.commons.lang.StringUtils.EMPTY;
		boolean flag = false;
		if(systemDeploySchool.equals(BaseConstant.SYS_DEPLOY_SCHOOL_ZHZG)){
			flag = true;
		}
		return flag;
	}
	
	public void sendSMS(User applyUser, String content){//TODO 发短信
		Unit unit = unitService.getUnit(applyUser.getUnitid());
		
		MsgDto msgDto = new MsgDto();
		msgDto.setUserId(applyUser.getId());
		msgDto.setUnitName(unit.getName());
		msgDto.setUnitId(unit.getId());
		msgDto.setUserName(applyUser.getRealname());
		msgDto.setContent(content);
		msgDto.setTiming(false);
		
		Map<String, List<CustomRoleUser>> roleUserMap = customRoleService.getCustomRoleWithUserList(unit.getId(), new String[]{"room_order_audit"});
		List<CustomRoleUser> roleUserList = roleUserMap.get("room_order_audit");
		
		List<RoomorderAuditSms> auditors = roomorderAuditSmsService.getRoomorderAuditSmsList(unit.getId());
		Set<String> userIds = new HashSet<String>();
		Set<String> teacherIds = new HashSet<String>();
		boolean flag = false;
		for(RoomorderAuditSms item : auditors){
			flag = false;
			String auditorId = item.getAuditorId();
			if(CollectionUtils.isNotEmpty(roleUserList)){
				for(CustomRoleUser roleUser : roleUserList){
					if(StringUtils.equals(auditorId, roleUser.getUserId())){
						flag = true;
						break;
					}
				}
			}
			if(flag){
				userIds.add(auditorId);
			}
		}
		Map<String, User> userMap = userService.getUsersMap(userIds.toArray(new String[0]));
		for(User user : userMap.values()){
			teacherIds.add(user.getTeacherid());
		}
		Map<String, Teacher> teacherMap = teacherService.getTeacherMap(teacherIds.toArray(new String[0]));
		List<SendDetailDto> sendDetailDtos = new ArrayList<SendDetailDto>();
		for(User user : userMap.values()){
			Teacher tea = teacherMap.get(user.getTeacherid());
			if(tea != null && StringUtils.isNotBlank(tea.getPersonTel())){
				SendDetailDto sendDetailDto = new SendDetailDto();
				sendDetailDto.setReceiverId(user.getId());
				sendDetailDto.setReceiverName(user.getRealname());
				sendDetailDto.setBusinessType(SmsConstant.SMS_BUSINESS_PAY);// Sdk收费短信
				sendDetailDto.setMobile(tea.getPersonTel());
				sendDetailDto.setReceiverType(User.TEACHER_LOGIN);// 短信接收用户类型:教师用户
				sendDetailDto.setUnitId(user.getUnitid());// 短信接收用户单位id
				sendDetailDtos.add(sendDetailDto);
			}
		}
		SmsThread smsThread = new SmsThread(msgDto, sendDetailDtos);
		smsThread.start();
	}
	
	/**
	 * 发送短信
	 * @author Administrator
	 *
	 */
	private class SmsThread extends Thread{
   	private MsgDto msgDto;
   	private List<SendDetailDto> sendDetailDtoList;
   	public SmsThread(MsgDto msgDto, List<SendDetailDto> sendDetailDtoList){
   		this.msgDto = msgDto;
   		this.sendDetailDtoList = sendDetailDtoList;
   	}

		@Override
		public void run() {
			smsClientService.saveSmsBatch(msgDto, sendDetailDtoList);
		}
   }
	
	@Override
	public List<OfficeApplyNumber> getOfficeApplyNumbersByConditions(String unitId, String searchLabMode, 
			String searchName, String searchSubject, String searchGrade, String searchUserName, Date startTime, Date endTime, Pagination page){
		List<User> userlist = userService.getUsersByFaintness(unitId, searchUserName);
		if(CollectionUtils.isNotEmpty(userlist)){
			Set<String> userIds = new HashSet<String>();
			Set<String> labSetIds = new HashSet<String>();
			Set<String> labInfoIds = new HashSet<String>();
			
			for(User user : userlist){
				userIds.add(user.getId());
			}
			Map<String, User> userMap = userService.getUsersMap(userIds.toArray(new String[0]));
			
			Map<String, OfficeLabSet> labSetMap = officeLabSetService.getOfficeLabSetMapByConditions(unitId, searchName, searchSubject, searchGrade);
			for(String key : labSetMap.keySet()){
				labSetIds.add(key);
			}
			Map<String, OfficeLabInfo> labInfoMap = officeLabInfoService.getOfficeLabInfoMapByConditions(unitId, searchLabMode, labSetIds.toArray(new String[0]));
			for(String key : labInfoMap.keySet()){
				labInfoIds.add(key);
			}
			
			List<OfficeApplyNumber> list = officeApplyNumberDao.getOfficeApplyNumbersByConditions(unitId, startTime, endTime, userIds.toArray(new String[0]), labInfoIds.toArray(new String[0]), page);
			for(OfficeApplyNumber apply : list){
				OfficeLabInfo labInfo = labInfoMap.get(apply.getLabInfoId());
				if(labInfo != null){
					OfficeLabSet labSet = labSetMap.get(labInfo.getLabSetId());
					if(labSet != null){
						apply.setLabName(labSet.getName());
						Mcodedetail mcodedetail = mcodedetailService.getMcodeDetail("DM-SYSLX", labSet.getSubject());
						if(mcodedetail != null){
							apply.setLabSubject(mcodedetail.getContent());
						}
						apply.setLabGrade(labSet.getGrade());
					}
					if(StringUtils.isNotBlank(labInfo.getLabMode())){
						apply.setLabMode(labInfo.getLabMode().equals("1")?"教师演示实验":"学生操作实验");
					}
				}
				User user = userMap.get(apply.getApplyUserId());
				if(user != null){
					apply.setUserName(user.getRealname());
				}
			}
			return list;
		}
		else{
			return new ArrayList<OfficeApplyNumber>();
		}
		
	}
	
	public void setOfficeApplyNumberDao(OfficeApplyNumberDao officeApplyNumberDao){
		this.officeApplyNumberDao = officeApplyNumberDao;
	}

	public void setTeachPlaceService(TeachPlaceService teachPlaceService) {
		this.teachPlaceService = teachPlaceService;
	}

	public void setOfficeUtilityApplyService(
			OfficeUtilityApplyService officeUtilityApplyService) {
		this.officeUtilityApplyService = officeUtilityApplyService;
	}

	public void setOfficeUtilityNumberService(
			OfficeUtilityNumberService officeUtilityNumberService) {
		this.officeUtilityNumberService = officeUtilityNumberService;
	}
	
	public void setAttachmentService(AttachmentService attachmentService) {
		this.attachmentService = attachmentService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public void setOfficeMeetingUserService(
			OfficeMeetingUserService officeMeetingUserService) {
		this.officeMeetingUserService = officeMeetingUserService;
	}

	public void setDeptService(DeptService deptService) {
		this.deptService = deptService;
	}

	public void setTeacherService(TeacherService teacherService) {
		this.teacherService = teacherService;
	}

	public void setOfficeTimeSetService(OfficeTimeSetService officeTimeSetService) {
		this.officeTimeSetService = officeTimeSetService;
	}
	
	public void setWebBookroomService(WebBookroomService webBookroomService) {
		this.webBookroomService = webBookroomService;
	}

	public void setOfficeMsgSendingService(
			OfficeMsgSendingService officeMsgSendingService) {
		this.officeMsgSendingService = officeMsgSendingService;
	}

	public void setBasicClassService(BasicClassService basicClassService) {
		this.basicClassService = basicClassService;
	}

	public void setEduadmSubsystemService(
			EduadmSubsystemService eduadmSubsystemService) {
		this.eduadmSubsystemService = eduadmSubsystemService;
	}

	public void setOfficeRoomOrderSetService(
			OfficeRoomOrderSetService officeRoomOrderSetService) {
		this.officeRoomOrderSetService = officeRoomOrderSetService;
	}

	public void setSystemIniService(SystemIniService systemIniService) {
		this.systemIniService = systemIniService;
	}

	public void setOfficeLabInfoService(OfficeLabInfoService officeLabInfoService) {
		this.officeLabInfoService = officeLabInfoService;
	}

	public void setOfficeLabSetService(OfficeLabSetService officeLabSetService) {
		this.officeLabSetService = officeLabSetService;
	}

	public void setMcodedetailService(McodedetailService mcodedetailService) {
		this.mcodedetailService = mcodedetailService;
	}

	public void setUnitService(UnitService unitService) {
		this.unitService = unitService;
	}

	public void setRoomorderAuditSmsService(
			RoomorderAuditSmsService roomorderAuditSmsService) {
		this.roomorderAuditSmsService = roomorderAuditSmsService;
	}

	public void setSmsClientService(SmsClientService smsClientService) {
		this.smsClientService = smsClientService;
	}

	public void setCustomRoleService(CustomRoleService customRoleService) {
		this.customRoleService = customRoleService;
	}

}