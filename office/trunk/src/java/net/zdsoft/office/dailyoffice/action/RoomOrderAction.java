package net.zdsoft.office.dailyoffice.action;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import net.zdsoft.eis.base.common.entity.CurrentSemester;
import net.zdsoft.eis.base.common.entity.CustomRole;
import net.zdsoft.eis.base.common.entity.CustomRoleUser;
import net.zdsoft.eis.base.common.entity.Dept;
import net.zdsoft.eis.base.common.entity.Mcodedetail;
import net.zdsoft.eis.base.common.entity.School;
import net.zdsoft.eis.base.common.entity.SchoolSemester;
import net.zdsoft.eis.base.common.entity.Semester;
import net.zdsoft.eis.base.common.entity.Teacher;
import net.zdsoft.eis.base.common.entity.Unit;
import net.zdsoft.eis.base.common.entity.User;
import net.zdsoft.eis.base.common.service.CustomRoleService;
import net.zdsoft.eis.base.common.service.CustomRoleUserService;
import net.zdsoft.eis.base.common.service.DeptService;
import net.zdsoft.eis.base.common.service.McodedetailService;
import net.zdsoft.eis.base.common.service.SchoolSemesterService;
import net.zdsoft.eis.base.common.service.SchoolService;
import net.zdsoft.eis.base.common.service.SemesterService;
import net.zdsoft.eis.base.common.service.TeacherService;
import net.zdsoft.eis.base.common.service.UserService;
import net.zdsoft.eis.base.constant.BaseConstant;
import net.zdsoft.eis.base.constant.enumeration.VersionType;
import net.zdsoft.eis.base.deploy.SystemDeployService;
import net.zdsoft.eis.base.storage.StorageFileUtils;
import net.zdsoft.eis.base.subsystemcall.entity.EduadmCourseDto;
import net.zdsoft.eis.base.subsystemcall.entity.StusysSectionTimeSetDto;
import net.zdsoft.eis.base.subsystemcall.service.BaseDataSubsystemService;
import net.zdsoft.eis.base.subsystemcall.service.EduadmSubsystemService;
import net.zdsoft.eis.frame.action.PageAction;
import net.zdsoft.eis.frame.dto.PromptMessageDto;
import net.zdsoft.eisu.base.common.entity.TeachPlace;
import net.zdsoft.eisu.base.common.service.TeachPlaceService;
import net.zdsoft.keelcnet.action.UploadFile;
import net.zdsoft.keelcnet.config.ContainerManager;
import net.zdsoft.leadin.exception.FileUploadFailException;
import net.zdsoft.leadin.util.excel.ZdCell;
import net.zdsoft.leadin.util.excel.ZdExcel;
import net.zdsoft.leadin.util.excel.ZdStyle;
import net.zdsoft.office.dailyoffice.entity.OfficeApplyNumber;
import net.zdsoft.office.dailyoffice.entity.OfficeLabInfo;
import net.zdsoft.office.dailyoffice.entity.OfficeLabSet;
import net.zdsoft.office.dailyoffice.entity.OfficeRoomOrderSet;
import net.zdsoft.office.dailyoffice.entity.OfficeTimeSet;
import net.zdsoft.office.dailyoffice.entity.OfficeUtilityApply;
import net.zdsoft.office.dailyoffice.entity.RoomorderAuditSms;
import net.zdsoft.office.dailyoffice.service.OfficeApplyNumberService;
import net.zdsoft.office.dailyoffice.service.OfficeLabInfoService;
import net.zdsoft.office.dailyoffice.service.OfficeLabSetService;
import net.zdsoft.office.dailyoffice.service.OfficeRoomOrderSetService;
import net.zdsoft.office.dailyoffice.service.OfficeTimeSetService;
import net.zdsoft.office.dailyoffice.service.OfficeUtilityApplyService;
import net.zdsoft.office.dailyoffice.service.RoomorderAuditSmsService;
import net.zdsoft.office.util.Constants;
import net.zdsoft.office.util.OfficeUtils;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.Sheet;


/**
 * @author chens
 * @version 创建时间：2014-12-31 下午3:24:22
 * 
 */
@SuppressWarnings("serial")
public class RoomOrderAction extends PageAction {
	
	private DeptService deptService;
	private TeachPlaceService teachPlaceService;
	private OfficeTimeSetService officeTimeSetService;
	private OfficeApplyNumberService officeApplyNumberService;
	private OfficeUtilityApplyService officeUtilityApplyService;
	private CustomRoleService customRoleService;
	private CustomRoleUserService customRoleUserService;
	private TeacherService teacherService;
	private BaseDataSubsystemService baseDataSubsystemService;
	private EduadmSubsystemService eduadmSubsystemService;
	private OfficeRoomOrderSetService officeRoomOrderSetService;
	private McodedetailService	mcodedetailService;
	private OfficeLabSetService officeLabSetService;
	private OfficeLabInfoService officeLabInfoService;
	private UserService userService;
	private SchoolService schoolService;
	private SchoolSemesterService schoolSemesterService;
	private SemesterService semesterService;
	private RoomorderAuditSmsService roomorderAuditSmsService;

	private OfficeTimeSet officeTimeSet;
	private OfficeApplyNumber officeApplyNumber;
	private Map<String, OfficeUtilityApply> applyMap;//教室申请信息
	
	private List<OfficeApplyNumber> officeApplyNumberList;
	private List<TeachPlace> teachPlaceList;//场地（机房、会议室）
	private List<EduadmCourseDto> eduadmCourseDtoList = new ArrayList<EduadmCourseDto>();
	
	private OfficeLabSet officeLabSet;
	private OfficeLabInfo officeLabInfo;
	private RoomorderAuditSms roomorderAuditSms;
	private List<OfficeLabSet> officeLabSetList;
	private String searchLabMode;
	private String searchName;
	private String searchSubject;
	private String searchGrade;
	private String searchUserName;
	private String[] checkid;
	private List<String> gradeList;
	private boolean isEdu;
	
	private Date startTime;
	private Date endTime;
	private String roomType;
	private String auditState;
	private Date currentTime;
	private List<String> periodList;//节次或时间
	private List<String> amPeriodList;//早上节次
	private List<String> pmPeriodList;//下午和晚上节次，主要为了中间插入一个中午
	private Map<String, String> timeMap;
	
	private String applyState;//1申请，2撤销
	private String[] applyRooms;
	private String purpose;//用途
	private String courseId;//课程
	private boolean canEdit = true;//是否可以申请，如果是当前日期之前的，就不能点击，同时没有申请和撤销按钮
	
	private String applyNumberId;//申请信息id,审核使用
	private String[] applyNumberIds;//批量审核
	private String remark;//审核意见
	
	private boolean auditAdmin = false;//审核负责人，同时可以修改时段设置
	
	private List<OfficeRoomOrderSet> officeRoomOrderSetList = new ArrayList<OfficeRoomOrderSet>();
	private OfficeRoomOrderSet officeRoomOrderSet = new OfficeRoomOrderSet();
	private Map<String, Mcodedetail>	mcodedetailMap=new HashMap<String, Mcodedetail>();

    public static final String APPLY_STATE = "1"; //申请
    public static final String CANCEL_STATE = "2"; //撤销
    public static final String ROOM_ORDER_AUDIT = "room_order_audit";//教室预约审核权限
    private String selected_ids;
    private String unSelected_ids;
    private String useTypes;
    private String needAudits;
    private String isDefaults;
    
    private boolean isEisuSchool;//是否为中职部署
    private boolean hasGrade;//是否使用年级
    
	@Override
	public String execute() throws Exception {
		officeRoomOrderSetList = officeRoomOrderSetService.getOfficeRoomOrderSetByUnitIdPage(getUnitId(), null);
		return SUCCESS;
	}
	
	public String myOrderList(){
		officeApplyNumberList = officeApplyNumberService.getOfficeApplyNumberByUnitIdPage(getUnitId(),getLoginUser().getUserId(),startTime,endTime,roomType,auditState,getPage());
		for(OfficeApplyNumber officeApplyNumber:officeApplyNumberList){
			try {
				officeApplyNumber.setWeekDay(OfficeUtils.dayForWeek(officeApplyNumber.getApplyDate()));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return SUCCESS;
	}
	
	public String orderApply(){
		if(currentTime == null){
			currentTime = new Date();
		}
		officeRoomOrderSetList = officeRoomOrderSetService.getOfficeRoomOrderSetByUnitIdPage(getUnitId(), null);
		officeRoomOrderSet=officeRoomOrderSetService.getOfficeRoomOrderSetBySelect(getUnitId());
		return SUCCESS;
	}
	
	public String orderApplyList(){
		officeRoomOrderSet = officeRoomOrderSetService.getOfficeRoomOrderSetByType(getUnitId(), roomType);
		if(StringUtils.isBlank(officeRoomOrderSet.getUseType())){
			promptMessageDto = new PromptMessageDto();
			promptMessageDto.setErrorMessage("请先设置该类型的使用类型");
			return PROMPTMSG;
		}
		
		officeTimeSet = officeTimeSetService.getOfficeTimeSetByUnitId(getUnitId());
		if(officeTimeSet == null || StringUtils.isBlank(officeTimeSet.getNoonStartTime())){
			promptMessageDto = new PromptMessageDto();
			promptMessageDto.setErrorMessage("请先进行时段设置");
			return PROMPTMSG;
		}
		String areaId = "";
		Dept d = deptService.getDept(getLoginInfo().getUser().getDeptid());
		String unitId = getUnitId();
		if(d != null){
			areaId = d.getAreaId();
		}
		if(StringUtils.isNotBlank(areaId)){
			teachPlaceList = teachPlaceService.getTeachPlaceByTypeInArea(areaId, null);
			boolean flag = true;
			for(int i = teachPlaceList.size()-1; i >= 0; i--) {
				flag = true;
				String placeType = teachPlaceList.get(i).getPlaceType();
				if(StringUtils.isNotBlank(placeType)){
					String[] types = placeType.split(",");
					for(String type : types){
						if(StringUtils.equals(type, roomType)){
							flag = false;
							break;
						}
					}
				}
				if(flag){
					teachPlaceList.remove(i);
				}
			}
		}else{
			teachPlaceList = teachPlaceService.getTeachPlaceByUnitIdAndType(unitId, roomType);
		}
		applyMap = officeUtilityApplyService.getApplyMap(roomType,currentTime,unitId,getLoginUser().getUserId());
		if(Constants.OFFICE_ROOM_ORDER_USER_TYPE_SECTION.equals(officeRoomOrderSet.getUseType())){
			//节次
			String acadyear = "";
			String semesterStr = "";
			int mornperiods = 0;
			int amperiods = 0;
			int pmperiods = 0;
			int nightperiods = 0;
			if(!isEdu()){
				SchoolSemester schoolSemester = schoolSemesterService.getCurrentAcadyear(getUnitId());
				if (schoolSemester == null ) {
					promptMessageDto = new PromptMessageDto();
					promptMessageDto.setErrorMessage("还没有设置当前学期, 请先设置当前学期再做此操作");
					return PROMPTMSG;
				}
				acadyear = schoolSemester.getAcadyear();
				semesterStr = schoolSemester.getSemester();
				mornperiods = schoolSemester.getMornperiods();
				amperiods = schoolSemester.getAmperiods();
				pmperiods = schoolSemester.getPmperiods();
				nightperiods = schoolSemester.getNightperiods();
			}else{
				CurrentSemester cur = semesterService.getCurrentSemester();
				if (cur == null ) {
					promptMessageDto = new PromptMessageDto();
					promptMessageDto.setErrorMessage("还没有设置当前学期, 请先设置当前学期再做此操作");
					return PROMPTMSG;
				}
				acadyear = cur.getAcadyear();
				semesterStr = cur.getSemester();
				Semester semester = semesterService.getSemester(acadyear, semesterStr);
				mornperiods = semester.getMornperiods();
				amperiods = semester.getAmPeriods();
				pmperiods = semester.getPmPeriods();
				nightperiods = semester.getNightPeriods();
			}
	        int totalSection = mornperiods+amperiods+pmperiods+nightperiods;
	        amPeriodList = new ArrayList<String>();
	        pmPeriodList = new ArrayList<String>();
	        for(int i=1;i<=mornperiods+amperiods;i++){
	        	amPeriodList.add(i+"");
	        }
	        for(int i=mornperiods+amperiods+1;i<=totalSection;i++){
	        	pmPeriodList.add(i+"");
	        }
	        if(isZhenghSchool()) {
	        	eduadmCourseDtoList = eduadmSubsystemService.getEduadmCourse(unitId, acadyear, semesterStr, getLoginInfo().getUser().getTeacherid(), null);
	        }
		}else{
			//时间段
			officeTimeSet = officeTimeSetService.getOfficeTimeSetByUnitId(getUnitId());
			if(officeTimeSet == null){
				promptMessageDto = new PromptMessageDto();
	        	promptMessageDto.setErrorMessage("请先设置时段信息");
	        	return PROMPTMSG;
			}
			periodList = new ArrayList<String>();
			timeMap = new HashMap<String, String>();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			SimpleDateFormat sdf2 = new SimpleDateFormat("HH:mm");
			SimpleDateFormat sdf3 = new SimpleDateFormat("HHmm");
			Date date = new Date();
			//date.setHours(officeTimeSet.getStartTime().substring(0,2));
			Date date1 = new Date();
			Date date2 = new Date();
			try {
				date1 = sdf1.parse(sdf.format(date)+" "+officeTimeSet.getStartTime()+":00");
				date2 = sdf1.parse(sdf.format(date)+" "+officeTimeSet.getEndTime()+":00");
			} catch (ParseException e) {
				e.printStackTrace();
			}
			int periodTime = officeTimeSet.getTimeInterval();
			while(date1.before(date2)){
				periodList.add(sdf3.format(date1));
				timeMap.put(sdf3.format(date1), sdf2.format(date1));
				date1 = new Date(date1.getTime() + periodTime * 60 * 1000);
			}
		}
		officeLabInfo = new OfficeLabInfo();
		officeLabInfo.setTeacherId(getLoginInfo().getUser().getId());
		officeLabInfo.setTeacherName(getLoginInfo().getUser().getRealname());
		return SUCCESS;
	}
	
	public String orderApplyView(){
		officeApplyNumber = officeApplyNumberService.getOfficeApplyNumberById(applyNumberId);
		if("11".equals(officeApplyNumber.getType())){
			if(StringUtils.isNotBlank(officeApplyNumber.getLabInfoId())){
				officeLabInfo = officeLabInfoService.getOfficeLabInfoById(officeApplyNumber.getLabInfoId());
				if(officeLabInfo != null){
					if(StringUtils.isNotBlank(officeLabInfo.getLabSetId())){
						officeLabSet = officeLabSetService.getOfficeLabSetById(officeLabInfo.getLabSetId());
						if(officeLabSet == null){
							officeLabSet = new OfficeLabSet();
						}
					}
					if(StringUtils.isNotBlank(officeLabInfo.getTeacherId())){
						User user = userService.getUser(officeLabInfo.getTeacherId());
						if(user != null){
							officeLabInfo.setTeacherName(user.getRealname());
						}
					}
				}else{
					officeLabInfo = new OfficeLabInfo();
				}
			}else{
				officeLabInfo = new OfficeLabInfo();
				officeLabSet = new OfficeLabSet();
			}
		}
		try {
			officeApplyNumber.setWeekDay(OfficeUtils.dayForWeek(officeApplyNumber.getApplyDate()));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	/**
	 * 申请与撤销
	 * @return
	 */
	public String saveApplyInfo(){//TODO
		officeRoomOrderSet = officeRoomOrderSetService.getOfficeRoomOrderSetByType(getUnitId(), roomType);
		OfficeApplyNumber officeApplyNumber = new OfficeApplyNumber();
		officeApplyNumber.setPurpose(purpose);
		officeApplyNumber.setApplyDate(currentTime);
		officeApplyNumber.setType(roomType);
		officeApplyNumber.setApplyRooms(applyRooms);
		officeApplyNumber.setApplyUserId(getLoginUser().getUserId());
		officeApplyNumber.setUnitId(getUnitId());
		//设置一卡通号
		Teacher teacher = teacherService.getTeacher(getLoginInfo().getUser().getTeacherid());
		officeApplyNumber.setCardNumber(teacher.getGeneralCard());
		
		Map<String, StusysSectionTimeSetDto> sstsMap = new HashMap<String, StusysSectionTimeSetDto>();
		boolean flag = false;
		if(Constants.OFFICE_ROOM_ORDER_USER_TYPE_SECTION.equals(officeRoomOrderSet.getUseType())){
			SchoolSemester schoolSemester = schoolSemesterService.getCurrentAcadyear(getUnitId());
			List<StusysSectionTimeSetDto> sstsDtoList = baseDataSubsystemService.getSectionTimeSets(getUnitId(), schoolSemester.getAcadyear(),schoolSemester.getSemester());
			String systemDeploySchool = getSystemDeploySchool();
			if(systemDeploySchool.equals(BaseConstant.SYS_DEPLOY_SCHOOL_ZHZG) && TeachPlace.PLACE_TYPE_COMPUTER.equals(roomType)){
				flag = true;
				if(CollectionUtils.isEmpty(sstsDtoList)){
					jsonError = "请先联系管理人员维护当前学年学期节次对应的时间！";
					return SUCCESS;
				}else{
					for(StusysSectionTimeSetDto sstsDto:sstsDtoList){
						sstsMap.put(String.valueOf(sstsDto.getSectionNumber()), sstsDto);
					}
				}
			}
		}
		if(APPLY_STATE.equals(applyState)){
			if("0".equals(officeRoomOrderSet.getNeedAudit())){//不需要审核
				officeApplyNumber.setState(Constants.APPLY_STATE_PASS);
				officeApplyNumber.setAuditUserId(getLoginUser().getUserId());
				officeApplyNumber.setAuditTime(new Date());
				officeApplyNumber.setCourseId(courseId);
			}else{
				sstsMap = new HashMap<String, StusysSectionTimeSetDto>();//镇海不走审核，走审核不推送数据
				officeApplyNumber.setState(Constants.APPLY_STATE_NEED_AUDIT);
			}
			try {
				flag = officeApplyNumberService.saveRoom(officeApplyNumber,sstsMap);
				if(flag){
					jsonError = "您申请的" + officeRoomOrderSet.getName() + "已被占用，请刷新界面重新申请！";
				}
			} catch (Exception e) {
				e.printStackTrace();
				jsonError = "申请失败！";
			}
		}else if(CANCEL_STATE.equals(applyState)){
			officeApplyNumber.setState(Constants.APPLY_STATE_PASS);
			officeApplyNumber.setAuditUserId(getLoginUser().getUserId());
			try {
				officeApplyNumberService.cancel(officeApplyNumber, flag, sstsMap);
			} catch (Exception e) {
				e.printStackTrace();
				jsonError = "撤销失败！";
			}
		}else{
			jsonError = "传入数据有误！";
		}
		return SUCCESS;
	}
	
	/**
	 * 会议室申请专用
	 * @return
	 */
	public String saveMeetingRoomInfo(){
		try {
			//设置附件文件的相关属性
			UploadFile file = StorageFileUtils.handleFile(new String[] {}, Integer.parseInt(systemIniService.getValue(Constants.FILE_INIID))*1024);
			officeApplyNumber.setApplyUserId(getLoginUser().getUserId());
			officeApplyNumber.setUnitId(getUnitId());
			//设置一卡通号
			Teacher teacher = teacherService.getTeacher(getLoginInfo().getUser().getTeacherid());
			officeApplyNumber.setCardNumber(teacher.getGeneralCard());
			
			boolean flag = officeApplyNumberService.saveMeetingRoom(officeApplyNumber,file);
			if(flag){
				jsonError = "您申请的会议室已被占用，请刷新界面重新申请！";
			}
		} catch (FileUploadFailException e) {
			if(e.getCause()!=null){
				jsonError = e.getCause().getMessage();
			}else{
				jsonError = e.getMessage();
			}
		} catch (Exception e) {
			e.printStackTrace();
			jsonError = "申请失败！";
		}
		return SUCCESS;
	}
	
	public String orderAudit(){
		officeRoomOrderSetList = officeRoomOrderSetService.getOfficeRoomOrderSetByUnitIdPage(getUnitId(), null);
		roomorderAuditSms = roomorderAuditSmsService.getRoomorderAuditSmsByUserId(getUnitId(), getLoginUser().getUserId());
		if(roomorderAuditSms == null){
			roomorderAuditSms = new RoomorderAuditSms();
			roomorderAuditSms.setNeedSms("0");
		}
		return SUCCESS;
	}
	
	public String orderAuditList(){
		officeApplyNumberList = officeApplyNumberService.getOfficeApplyNumberAuditPage(getUnitId(),startTime,endTime,roomType,auditState,searchSubject,getPage());
		for(OfficeApplyNumber officeApplyNumber:officeApplyNumberList){
			try {
				officeApplyNumber.setWeekDay(OfficeUtils.dayForWeek(officeApplyNumber.getApplyDate()));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return SUCCESS;
	}
	
	public String orderAuditEdit(){
		officeApplyNumber = officeApplyNumberService.getOfficeApplyNumberById(applyNumberId);
		if("11".equals(officeApplyNumber.getType())){
			if(StringUtils.isNotBlank(officeApplyNumber.getLabInfoId())){
				officeLabInfo = officeLabInfoService.getOfficeLabInfoById(officeApplyNumber.getLabInfoId());
				if(officeLabInfo != null){
					if(StringUtils.isNotBlank(officeLabInfo.getLabSetId())){
						officeLabSet = officeLabSetService.getOfficeLabSetById(officeLabInfo.getLabSetId());
						if(officeLabSet == null){
							officeLabSet = new OfficeLabSet();
						}
					}
					if(StringUtils.isNotBlank(officeLabInfo.getTeacherId())){
						User user = userService.getUser(officeLabInfo.getTeacherId());
						if(user != null){
							officeLabInfo.setTeacherName(user.getRealname());
						}
					}
				}else{
					officeLabInfo = new OfficeLabInfo();
				}
			}else{
				officeLabInfo = new OfficeLabInfo();
				officeLabSet = new OfficeLabSet();
			}
		}
		try {
			officeApplyNumber.setWeekDay(OfficeUtils.dayForWeek(officeApplyNumber.getApplyDate()));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	public String auditSmsSave() {
		try {
			roomorderAuditSmsService.deleteByUserId(getUnitId(), getLoginUser().getUserId());
			if(StringUtils.equals("1", roomorderAuditSms.getNeedSms())){
				roomorderAuditSms.setUnitId(getUnitId());
				roomorderAuditSms.setAuditorId(getLoginUser().getUserId());
				roomorderAuditSmsService.save(roomorderAuditSms);
			}
		} catch (Exception e) {
			jsonError = "操作失败";
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	/**
	 * 反馈
	 * @return
	 */
	public String feedback(){
		officeApplyNumber = officeApplyNumberService.getOfficeApplyNumberById(applyNumberId);
		return SUCCESS;
	}
	
	/**
	 * 保存反馈信息
	 * @return
	 */
	public String saveFeedback(){
		try {
			officeApplyNumberService.updateFeedback(officeApplyNumber);
		} catch (Exception e) {
			jsonError = "操作失败";
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	//通过
	public String audit(){
		if(StringUtils.isBlank(applyNumberId)){
			jsonError = "传入的值为空";
		}else{
			try {
				String systemDeploySchool = getSystemDeploySchool();
				boolean flag = false;
				if(systemDeploySchool.equals(BaseConstant.SYS_DEPLOY_SCHOOL_ZHZG)){
					flag = true;
				}
				officeApplyNumberService.update(applyNumberId,auditState,remark,getLoginUser().getUserId(), flag);
			} catch (Exception e) {
				e.printStackTrace();
				jsonError = "审核失败";
			}
		}
		return SUCCESS;
	}
	
	//批量通过
	public String passSelected(){
		if(ArrayUtils.isEmpty(applyNumberIds)){
			jsonError = "传入的值为空";
		}else{
			try {
				String systemDeploySchool = getSystemDeploySchool();
				boolean flag = false;
				if(systemDeploySchool.equals(BaseConstant.SYS_DEPLOY_SCHOOL_ZHZG)){
					flag = true;
				}
				officeApplyNumberService.pass(applyNumberIds,this.getLoginUser().getUserId(), flag);
			} catch (Exception e) {
				e.printStackTrace();
				jsonError = "审核失败";
			}
		}
		return SUCCESS;
	}
	
	public String timeSet(){
		officeTimeSet = officeTimeSetService.getOfficeTimeSetByUnitId(getUnitId());
		if(officeTimeSet == null){
			officeTimeSet = new OfficeTimeSet();
		}
		return SUCCESS;
	}
	
	public String timeSetSave(){
		try {
			officeTimeSet.setUnitId(getUnitId());
			if(StringUtils.isBlank(officeTimeSet.getId())){
				officeTimeSetService.save(officeTimeSet);
			}else{
				officeTimeSetService.update(officeTimeSet);
			}
		} catch (Exception e) {
			e.printStackTrace();
			jsonError = "操作失败";
		}
		return SUCCESS;
	}
	
	/**
	 * 判断其是否为各种管理员
	 */
	private boolean isPracticeAdmin(String str){
		CustomRole role = customRoleService.getCustomRoleByRoleCode(getUnitId(), str);
		boolean flag;
		if(role == null){
			flag = false;
			return flag;
		}
		List<CustomRoleUser> roleUs = customRoleUserService.getCustomRoleUserListByUserId(getLoginUser().getUserId());
		if(CollectionUtils.isNotEmpty(roleUs)){
			for(CustomRoleUser ru : roleUs){
				if(StringUtils.equals(ru.getRoleId(), role.getId())){
					flag = true;
					return flag;
				}
			}
		}
		flag = false;
		return flag;
	}

	/**
	 * 类型信息设置
	 * @return
	 */
	public String orderSet(){//TODO
		officeRoomOrderSetList=officeRoomOrderSetService.getOfficeRoomOrderSetByUnitIdList(getUnitId());
		mcodedetailMap=mcodedetailService.getMcodeDetailMap("DM-CDLX");
		Map<String, OfficeRoomOrderSet> orderSetMap = new HashMap<String, OfficeRoomOrderSet>();
		Set<String> keys=mcodedetailMap.keySet();
		for(String key : keys){
			OfficeRoomOrderSet offSet=new OfficeRoomOrderSet();
			offSet.setThisId(key);
			orderSetMap.put(key, offSet);
		}
		for(OfficeRoomOrderSet item : officeRoomOrderSetList){
			orderSetMap.put(item.getThisId(), item);
		}
		officeRoomOrderSetList.clear();
		Iterator inter=orderSetMap.entrySet().iterator();
		while(inter.hasNext()){
			Entry<String,OfficeRoomOrderSet> entry=(Entry<String, OfficeRoomOrderSet>) inter.next();
			OfficeRoomOrderSet item = entry.getValue();
			if(!(this.isEdu() && item.getThisId().equals("11"))){
				officeRoomOrderSetList.add(entry.getValue());
			}
		}
		Collections.sort(officeRoomOrderSetList, new Comparator<OfficeRoomOrderSet>() {
			@Override
			public int compare(OfficeRoomOrderSet o1, OfficeRoomOrderSet o2) {
				Integer t1 = Integer.parseInt(StringUtils.isEmpty(o1.getThisId())?"0":o1.getThisId());
				Integer t2 = Integer.parseInt(StringUtils.isEmpty(o2.getThisId())?"0":o2.getThisId());
				return t1.compareTo(t2);
			}
		});
		return SUCCESS;
	}
	public String orderSetSave(){
		/*try {
			List<String> needAuditTemp=new ArrayList<String>();
			for(String str: needAudit){
				if(str!=null&&str.length()!=0){
					needAuditTemp.add(str);
				}
			}
			needAudit = needAuditTemp.toArray(new String[0]);
			List<String> needAuditTemp2=new ArrayList<String>();
			for(String str: useType){
				if(str!=null&&str.length()!=0){
					needAuditTemp2.add(str);
				}
			}
			useType=needAuditTemp2.toArray(new String[0]);*/
		try{
			String[] needAud=needAudits.split(",");
			String[] useTyp=useTypes.split(",");
			String[] selected=selected_ids.split(",");
			String[] isDefault=isDefaults.split(",");
			for (int i = 0; i < selected.length; i++) {
				OfficeRoomOrderSet officeSet=officeRoomOrderSetService.getOfficeRoomOrderSetByThisId(selected[i], getUnitId());
				if(officeSet!=null&&StringUtils.isNotBlank(officeSet.getId())){
					OfficeRoomOrderSet officeRoomOrderSet=new OfficeRoomOrderSet();
					officeRoomOrderSet.setNeedAudit(needAud[i]);
					officeRoomOrderSet.setUseType(useTyp[i]);
					officeRoomOrderSet.setId(officeSet.getId());
					officeRoomOrderSet.setUnitId(getUnitId());
					officeRoomOrderSet.setThisId(selected[i]);
					officeRoomOrderSet.setIsSelected(isDefault[i]);
					officeRoomOrderSetService.update(officeRoomOrderSet);
				}
				if(officeSet==null && StringUtils.isNotBlank(selected[i])){
					OfficeRoomOrderSet officeRoomOrderSet=new OfficeRoomOrderSet();
					officeRoomOrderSet.setNeedAudit(needAud[i]);
					officeRoomOrderSet.setUseType(useTyp[i]);
					officeRoomOrderSet.setUnitId(getUnitId());
					officeRoomOrderSet.setThisId(selected[i]);
					officeRoomOrderSet.setIsSelected(isDefault[i]);
					officeRoomOrderSetService.save(officeRoomOrderSet);
				}
			}
			String[] unSelected=unSelected_ids.split(",");
			List<String> isDelete=new ArrayList<String>();
			for (int i=0;i<unSelected.length;i++){
			OfficeRoomOrderSet officeSet=officeRoomOrderSetService.getOfficeRoomOrderSetByThisId(unSelected[i], getUnitId());
				if(officeSet!=null&&StringUtils.isNotBlank(officeSet.getId())){
					isDelete.add(officeSet.getId());
				}
			}
			unSelected=isDelete.toArray(new String[0]);
			officeRoomOrderSetService.delete(unSelected);
		} catch (Exception e) {
			e.printStackTrace();
			jsonError = "操作失败";
		}
		return SUCCESS;
	}
	
	/**
	 * 实验种类设置 TODO
	 * @return
	 */
	public String labTypeSet(){
		return SUCCESS;
	}
	
	public String labTypeSetList(){
		officeLabSetList = officeLabSetService.getOfficeLabSetBySubjectPage(getUnitId(), searchSubject, null, getPage());
		return SUCCESS;
	}
	
	public String labTypeSetAdd(){
		officeLabSet = new OfficeLabSet();
		return SUCCESS;
	}
	
	public String labTypeSetEdit(){
		officeLabSet = officeLabSetService.getOfficeLabSetById(officeLabSet.getId());
		return SUCCESS;
	}
	
	public String labTypeSetSave(){
		try{
			if(StringUtils.isBlank(officeLabSet.getId())){//新增
				officeLabSet.setUnitId(getUnitId());
				officeLabSet.setCreateTime(new Date());
				officeLabSetService.save(officeLabSet);
			}else{
				OfficeLabSet oldLabSet = officeLabSetService.getOfficeLabSetById(officeLabSet.getId());
				officeLabSet.setUnitId(oldLabSet.getUnitId());
				officeLabSet.setCreateTime(oldLabSet.getCreateTime());
				officeLabSetService.update(officeLabSet);
			}
			
		}catch (Exception e) {
			e.printStackTrace();
			jsonError = "保存失败";
		}
		return SUCCESS;
	}
	
	public String labTypeSetDelete(){
		try{
			officeLabSetService.delete(checkid);
		}catch (Exception e) {
			e.printStackTrace();
			jsonError = "删除失败";
		}
		return SUCCESS;
	}
	
	/**
	 * 实验室申请专用
	 * @return
	 */
	public String saveLabRoomInfo(){//TODO
		try {
			officeApplyNumber.setApplyUserId(getLoginUser().getUserId());
			officeApplyNumber.setUnitId(getUnitId());
			//设置一卡通号
			Teacher teacher = teacherService.getTeacher(getLoginInfo().getUser().getTeacherid());
			officeApplyNumber.setCardNumber(teacher.getGeneralCard());
			
			SimpleDateFormat sdf=new SimpleDateFormat("HHmm");
			SimpleDateFormat sdf2=new SimpleDateFormat("HH:mm");
			if(StringUtils.isNotBlank(officeApplyNumber.getUseType())&&StringUtils.equals(officeApplyNumber.getUseType(), "2")){
				String useStartTime=sdf.format(sdf2.parse(officeApplyNumber.getUseStartTime()));
				String useEndTime=sdf.format(sdf2.parse(officeApplyNumber.getUseEndTime()));
				String useTime=useStartTime+"-"+useEndTime;
				officeApplyNumber.setApplyRooms(new String[]{BaseConstant.ZERO_GUID+"_"+useTime});
			}
			
			boolean flag = officeApplyNumberService.saveLabRoom(officeApplyNumber, officeLabInfo);
			if(flag){
				jsonError = "您申请的实验室已被占用，请刷新界面重新申请！";
			}
		} catch (FileUploadFailException e) {
			if(e.getCause()!=null){
				jsonError = e.getCause().getMessage();
			}else{
				jsonError = e.getMessage();
			}
		} catch (Exception e) {
			e.printStackTrace();
			jsonError = "申请失败！";
		}
		return SUCCESS;
	}
	
	public String getSubjectSelect(){
		if(StringUtils.isBlank(officeLabInfo.getSubject()) && StringUtils.isBlank(searchGrade)){
			officeLabSetList = new ArrayList<OfficeLabSet>();
		}else{
			officeLabSetList = officeLabSetService.getOfficeLabSetBySubjectPage(getUnitId(), officeLabInfo.getSubject(), searchGrade, null);
		}
		return SUCCESS;
	}
	
	public String getLabSetInfo(){
		officeLabSet = officeLabSetService.getOfficeLabSetById(officeLabInfo.getLabSetId());
		if(officeLabSet != null){
			jsonMessageDto.put("courseBook", officeLabSet.getCourseBook());
			jsonMessageDto.put("apparatus", officeLabSet.getApparatus());
			jsonMessageDto.put("reagent", officeLabSet.getReagent());
			jsonMessageDto.put("subject", officeLabSet.getSubject());
			jsonMessageDto.put("grade", officeLabSet.getGrade());
		}else{
			jsonMessageDto.put("courseBook", "");
			jsonMessageDto.put("apparatus", "");
			jsonMessageDto.put("reagent", "");
			jsonMessageDto.put("subject", "");
			jsonMessageDto.put("grade", "");
		}
		return SUCCESS;
	}
	
	/**
	 * 实验申请统计
	 * @return
	 */
	public String labApplyCount(){//TODO
		return SUCCESS;
	}
	
	public String labApplyCountList(){
		officeApplyNumberList = officeApplyNumberService.getOfficeApplyNumbersByConditions(getUnitId(), 
				searchLabMode, searchName, searchSubject, searchGrade, searchUserName, startTime, endTime, getPage());
		for(OfficeApplyNumber officeApplyNumber:officeApplyNumberList){
			try {
				officeApplyNumber.setWeekDay(OfficeUtils.dayForWeek(officeApplyNumber.getApplyDate()));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return SUCCESS;
	}
	
	public String doExport(){
		officeApplyNumberList = officeApplyNumberService.getOfficeApplyNumbersByConditions(getUnitId(), 
				searchLabMode, searchName, searchSubject, searchGrade, searchUserName, startTime, endTime, getPage());
		for(OfficeApplyNumber officeApplyNumber:officeApplyNumberList){
			try {
				officeApplyNumber.setWeekDay(OfficeUtils.dayForWeek(officeApplyNumber.getApplyDate()));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		
		ZdExcel zdExcel = new ZdExcel();
		ZdStyle style2 = new ZdStyle(ZdStyle.BOLD|ZdStyle.BORDER|ZdStyle.ALIGN_CENTER);
		ZdStyle style3 = new ZdStyle(ZdStyle.BORDER|ZdStyle.ALIGN_CENTER|ZdStyle.WRAP_TEXT);
		List<ZdCell>zdlist=new ArrayList<ZdCell>();
		
		zdlist.add(new ZdCell("实验名称",1,style2));
		zdlist.add(new ZdCell("实验形式",1,style2));
		zdlist.add(new ZdCell("学科",1,style2));
		if(this.isHasGrade()){
			zdlist.add(new ZdCell("年级",1,style2));
		}
		zdlist.add(new ZdCell("申请人",1,style2));
		zdlist.add(new ZdCell("时间",1,style2));
		zdlist.add(new ZdCell("申请信息",1,style2));
		zdlist.add(new ZdCell("用途",1,style2));

		zdExcel.add(new ZdCell("实验申请统计", zdlist.size(), 2, new ZdStyle(ZdStyle.BOLD|ZdStyle.ALIGN_CENTER|ZdStyle.WRAP_TEXT, 18)));
		zdExcel.add(zdlist.toArray(new ZdCell[0]));
		
		for(OfficeApplyNumber apply : officeApplyNumberList){
			int index = 0;
			ZdCell[] cells = new ZdCell[zdlist.size()];
			cells[index++] = new ZdCell(apply.getLabName(), 1, style3);
			cells[index++] = new ZdCell(apply.getLabMode(), 1, style3);
			cells[index++] = new ZdCell(apply.getLabSubject(), 1, style3);
			if(this.isHasGrade()){
				cells[index++] = new ZdCell(apply.getLabGrade(), 1, style3);
			}
			cells[index++] = new ZdCell(apply.getUserName(), 1, style3);
			
			DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			cells[index++] = new ZdCell(sdf.format(apply.getApplyDate())+"("+apply.getWeekDay()+")", 1, style3);
			
			cells[index++] = new ZdCell(apply.getContent(), 1, style3);
			cells[index++] = new ZdCell(apply.getPurpose(), 1, style3);
			
			zdExcel.add(cells);
		}
		Sheet sheet = zdExcel.createSheet("sheet1");
		for(int i=0;i<3;i++){
			zdExcel.setCellWidth(sheet, i, 2);
		}
		zdExcel.export("lab_apply_list");
		return NONE;
	}
	
	/**
	 * 判断是否中职部署
	 * @return
	 */
	public boolean isEisuSchool(){
		if (systemDeployService == null) {
			systemDeployService = (SystemDeployService) ContainerManager
					.getComponent("systemDeployService");
		}
		if(VersionType.EISU == systemDeployService.getVersionType()){
			return true;
		}
		return false;
	}
	
	public void setOfficeTimeSetService(OfficeTimeSetService officeTimeSetService) {
		this.officeTimeSetService = officeTimeSetService;
	}

	public void setOfficeApplyNumberService(
			OfficeApplyNumberService officeApplyNumberService) {
		this.officeApplyNumberService = officeApplyNumberService;
	}

	public OfficeTimeSet getOfficeTimeSet() {
		return officeTimeSet;
	}

	public void setOfficeTimeSet(OfficeTimeSet officeTimeSet) {
		this.officeTimeSet = officeTimeSet;
	}

	public List<OfficeApplyNumber> getOfficeApplyNumberList() {
		return officeApplyNumberList;
	}

	public void setOfficeApplyNumberList(
			List<OfficeApplyNumber> officeApplyNumberList) {
		this.officeApplyNumberList = officeApplyNumberList;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getRoomType() {
		return roomType;
	}

	public void setRoomType(String roomType) {
		this.roomType = roomType;
	}

	public Date getCurrentTime() {
		return currentTime;
	}

	public void setCurrentTime(Date currentTime) {
		this.currentTime = currentTime;
	}

	public List<TeachPlace> getTeachPlaceList() {
		return teachPlaceList;
	}

	public void setTeachPlaceList(List<TeachPlace> teachPlaceList) {
		this.teachPlaceList = teachPlaceList;
	}

	public void setTeachPlaceService(TeachPlaceService teachPlaceService) {
		this.teachPlaceService = teachPlaceService;
	}

	public void setDeptService(DeptService deptService) {
		this.deptService = deptService;
	}

	public String getApplyState() {
		return applyState;
	}

	public void setApplyState(String applyState) {
		this.applyState = applyState;
	}

	public String[] getApplyRooms() {
		return applyRooms;
	}

	public void setApplyRooms(String[] applyRooms) {
		this.applyRooms = applyRooms;
	}

	public String getPurpose() {
		return purpose;
	}

	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}

	public Map<String, OfficeUtilityApply> getApplyMap() {
		return applyMap;
	}

	public void setApplyMap(Map<String, OfficeUtilityApply> applyMap) {
		this.applyMap = applyMap;
	}

	public void setOfficeUtilityApplyService(
			OfficeUtilityApplyService officeUtilityApplyService) {
		this.officeUtilityApplyService = officeUtilityApplyService;
	}

	public String getAuditState() {
		return auditState;
	}

	public void setAuditState(String auditState) {
		this.auditState = auditState;
	}

	public String getApplyNumberId() {
		return applyNumberId;
	}

	public void setApplyNumberId(String applyNumberId) {
		this.applyNumberId = applyNumberId;
	}

	public String[] getApplyNumberIds() {
		return applyNumberIds;
	}

	public void setApplyNumberIds(String[] applyNumberIds) {
		this.applyNumberIds = applyNumberIds;
	}

	public List<String> getPeriodList() {
		return periodList;
	}

	public void setPeriodList(List<String> periodList) {
		this.periodList = periodList;
	}
	
	public Map<String, String> getTimeMap() {
		return timeMap;
	}

	public void setTimeMap(Map<String, String> timeMap) {
		this.timeMap = timeMap;
	}

	public void setCustomRoleService(CustomRoleService customRoleService) {
		this.customRoleService = customRoleService;
	}

	public void setCustomRoleUserService(CustomRoleUserService customRoleUserService) {
		this.customRoleUserService = customRoleUserService;
	}

	public boolean isAuditAdmin() {
		auditAdmin = isPracticeAdmin(ROOM_ORDER_AUDIT);
		return auditAdmin;
	}

	public void setAuditAdmin(boolean auditAdmin) {
		this.auditAdmin = auditAdmin;
	}

	public void setTeacherService(TeacherService teacherService) {
		this.teacherService = teacherService;
	}

	public void setBaseDataSubsystemService(
			BaseDataSubsystemService baseDataSubsystemService) {
		this.baseDataSubsystemService = baseDataSubsystemService;
	}

	public boolean isCanEdit() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		Date towWeekAfterDate = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(towWeekAfterDate);
		calendar.add(Calendar.DAY_OF_YEAR, getNextSundayNum());
		towWeekAfterDate = calendar.getTime();
		try {
			Date date1 = sdf.parse(sdf.format(date));
			Date date2 = sdf.parse(sdf.format(currentTime));
			//当前日期之前及两周之后都不能申请
			if(date1.after(date2) || date2.after(towWeekAfterDate)){
				return false;
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return canEdit;
	}
	
	public int getNextSundayNum(){
		Calendar c = Calendar.getInstance();
        int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
        if(dayOfWeek == 1){
        	dayOfWeek = 7;
        }else{
        	dayOfWeek--;
        }
        int lastSunday = 14 - dayOfWeek;
        return lastSunday;
	}
	
	public void setCanEdit(boolean canEdit) {
		this.canEdit = canEdit;
	}
	
	/**
	 * 判断系统部署学校是否是镇海
	 * 
	 * @return
	 */
	public boolean isZhenghSchool() {
		String value = getSystemDeploySchool();
		return BaseConstant.SYS_DEPLOY_SCHOOL_ZHZG.equals(value);
	}

	public OfficeApplyNumber getOfficeApplyNumber() {
		return officeApplyNumber;
	}

	public void setOfficeApplyNumber(OfficeApplyNumber officeApplyNumber) {
		this.officeApplyNumber = officeApplyNumber;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public List<String> getAmPeriodList() {
		return amPeriodList;
	}

	public void setAmPeriodList(List<String> amPeriodList) {
		this.amPeriodList = amPeriodList;
	}

	public List<String> getPmPeriodList() {
		return pmPeriodList;
	}

	public void setPmPeriodList(List<String> pmPeriodList) {
		this.pmPeriodList = pmPeriodList;
	}

	public List<EduadmCourseDto> getEduadmCourseDtoList() {
		return eduadmCourseDtoList;
	}

	public void setEduadmCourseDtoList(List<EduadmCourseDto> eduadmCourseDtoList) {
		this.eduadmCourseDtoList = eduadmCourseDtoList;
	}

	public void setEduadmSubsystemService(
			EduadmSubsystemService eduadmSubsystemService) {
		this.eduadmSubsystemService = eduadmSubsystemService;
	}

	public String getCourseId() {
		return courseId;
	}

	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}

	public List<OfficeRoomOrderSet> getOfficeRoomOrderSetList() {
		return officeRoomOrderSetList;
	}

	public void setOfficeRoomOrderSetList(
			List<OfficeRoomOrderSet> officeRoomOrderSetList) {
		this.officeRoomOrderSetList = officeRoomOrderSetList;
	}

	public OfficeRoomOrderSet getOfficeRoomOrderSet() {
		return officeRoomOrderSet;
	}

	public void setOfficeRoomOrderSet(OfficeRoomOrderSet officeRoomOrderSet) {
		this.officeRoomOrderSet = officeRoomOrderSet;
	}

	public void setOfficeRoomOrderSetService(
			OfficeRoomOrderSetService officeRoomOrderSetService) {
		this.officeRoomOrderSetService = officeRoomOrderSetService;
	}

	public String getSelected_ids() {
		return selected_ids;
	}

	public void setSelected_ids(String selected_ids) {
		this.selected_ids = selected_ids;
	}

	public String getUnSelected_ids() {
		return unSelected_ids;
	}

	public void setUnSelected_ids(String unSelected_ids) {
		this.unSelected_ids = unSelected_ids;
	}

	public String getUseTypes() {
		return useTypes;
	}

	public void setUseTypes(String useTypes) {
		this.useTypes = useTypes;
	}

	public String getNeedAudits() {
		return needAudits;
	}

	public void setNeedAudits(String needAudits) {
		this.needAudits = needAudits;
	}

	public Map<String, Mcodedetail> getMcodedetailMap() {
		return mcodedetailMap;
	}

	public void setMcodedetailMap(Map<String, Mcodedetail> mcodedetailMap) {
		this.mcodedetailMap = mcodedetailMap;
	}

	public void setMcodedetailService(McodedetailService mcodedetailService) {
		this.mcodedetailService = mcodedetailService;
	}

	public String getIsDefaults() {
		return isDefaults;
	}

	public void setIsDefaults(String isDefaults) {
		this.isDefaults = isDefaults;
	}

	public void setOfficeLabSetService(OfficeLabSetService officeLabSetService) {
		this.officeLabSetService = officeLabSetService;
	}

	public void setOfficeLabInfoService(OfficeLabInfoService officeLabInfoService) {
		this.officeLabInfoService = officeLabInfoService;
	}

	public List<OfficeLabSet> getOfficeLabSetList() {
		return officeLabSetList;
	}

	public void setOfficeLabSetList(List<OfficeLabSet> officeLabSetList) {
		this.officeLabSetList = officeLabSetList;
	}

	public OfficeLabSet getOfficeLabSet() {
		return officeLabSet;
	}

	public void setOfficeLabSet(OfficeLabSet officeLabSet) {
		this.officeLabSet = officeLabSet;
	}

	public String[] getCheckid() {
		return checkid;
	}

	public void setCheckid(String[] checkid) {
		this.checkid = checkid;
	}

	public String getSearchSubject() {
		return searchSubject;
	}

	public void setSearchSubject(String searchSubject) {
		this.searchSubject = searchSubject;
	}

	public OfficeLabInfo getOfficeLabInfo() {
		return officeLabInfo;
	}

	public void setOfficeLabInfo(OfficeLabInfo officeLabInfo) {
		this.officeLabInfo = officeLabInfo;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public List<String> getGradeList() {
		gradeList = new ArrayList<String>();
		School school = schoolService.getSchool(getUnitId());
		String schSection = school.getSections();
		if(StringUtils.isNotBlank(schSection)){
			String[] sections = schSection.split(",");
			for (int i = 0; i < sections.length; i++) {
				List<Mcodedetail> mcodedetailList = mcodedetailService.getMcodeDetails("DM-RKXD-" + sections[i]);
				if(CollectionUtils.isNotEmpty(mcodedetailList)){
					for(Mcodedetail mcode : mcodedetailList){
						gradeList.add(mcode.getContent());
					}
				}
			}
		}
		return gradeList;
	}

	public void setGradeList(List<String> gradeList) {
		this.gradeList = gradeList;
	}

	public void setSchoolService(SchoolService schoolService) {
		this.schoolService = schoolService;
	}

	public String getSearchLabMode() {
		return searchLabMode;
	}

	public void setSearchLabMode(String searchLabMode) {
		this.searchLabMode = searchLabMode;
	}

	public String getSearchName() {
		return searchName;
	}

	public void setSearchName(String searchName) {
		this.searchName = searchName;
	}

	public String getSearchGrade() {
		return searchGrade;
	}

	public void setSearchGrade(String searchGrade) {
		this.searchGrade = searchGrade;
	}

	public String getSearchUserName() {
		return searchUserName;
	}

	public void setSearchUserName(String searchUserName) {
		this.searchUserName = searchUserName;
	}

	public boolean isEdu() {
		isEdu = getLoginInfo().getUnitClass() == Unit.UNIT_CLASS_EDU;
		return isEdu;
	}

	public void setEdu(boolean isEdu) {
		this.isEdu = isEdu;
	}

	public void setEisuSchool(boolean isEisuSchool) {
		this.isEisuSchool = isEisuSchool;
	}

	public void setSchoolSemesterService(SchoolSemesterService schoolSemesterService) {
		this.schoolSemesterService = schoolSemesterService;
	}

	public void setSemesterService(SemesterService semesterService) {
		this.semesterService = semesterService;
	}

	public boolean isHasGrade() {
		if(this.isEisuSchool() || this.isEdu()){
			return false;
		}else{
			return true;
		}
	}

	public void setHasGrade(boolean hasGrade) {
		this.hasGrade = hasGrade;
	}

	public RoomorderAuditSms getRoomorderAuditSms() {
		return roomorderAuditSms;
	}

	public void setRoomorderAuditSms(RoomorderAuditSms roomorderAuditSms) {
		this.roomorderAuditSms = roomorderAuditSms;
	}

	public void setRoomorderAuditSmsService(
			RoomorderAuditSmsService roomorderAuditSmsService) {
		this.roomorderAuditSmsService = roomorderAuditSmsService;
	}

}
