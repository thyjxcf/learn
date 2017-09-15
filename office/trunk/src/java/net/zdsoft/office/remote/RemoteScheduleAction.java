package net.zdsoft.office.remote;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.zdsoft.eis.base.common.entity.Role;
import net.zdsoft.eis.base.common.service.DeptService;
import net.zdsoft.eis.base.common.service.UserService;
import net.zdsoft.eis.base.storage.StorageFileUtils;
import net.zdsoft.eis.system.data.entity.UserRoleRelation;
import net.zdsoft.eis.system.data.service.RolePermService;
import net.zdsoft.eis.system.data.service.UserRoleRelationService;
import net.zdsoft.keel.util.DateUtils;
import net.zdsoft.keel.util.Pagination;
import net.zdsoft.keelcnet.action.UploadFile;
import net.zdsoft.office.schedule.dto.OfficeCalendarDto;
import net.zdsoft.office.schedule.entity.OfficeCalendar;
import net.zdsoft.office.schedule.service.OfficeCalendarAuthService;
import net.zdsoft.office.schedule.service.OfficeCalendarService;
import net.zdsoft.office.schedule.service.OfficeWorkOutlineService;
import net.zdsoft.office.schedule.util.ScheduleConstant;
import net.zdsoft.office.util.OfficeUtils;

import org.apache.commons.lang.StringUtils;

@SuppressWarnings("serial")
public class RemoteScheduleAction extends OfficeJsonBaseAction {

	private String unitId;
	private String id;
	private String userId;// 登陆用户
	private String backAndForth;// 日历向前0,向后1
	private int choiseDay;// 页面选择的日期
	private String lastChoiseDay;// 页面选择的日期
	private String dataType;// 1.个人

	private boolean hasUnitAuth = false;//单位日志权限
	private boolean hasDeptAdd = false;//部门日志修改权限
	private boolean hasUnitAdd = false;//单位部门修改权限
	
	private boolean isGroupHead;
	private boolean isLeader;
	
	private Map<String, Integer> redMap = new HashMap<String, Integer>();

	private OfficeCalendarDto calDto = new OfficeCalendarDto();
	private OfficeCalendar calendar = new OfficeCalendar();
	private List<OfficeCalendar> calendarList = new ArrayList<OfficeCalendar>();
	private Map<String, OfficeCalendarDto> calMap;

	private OfficeCalendarAuthService officeCalendarAuthService;
	private OfficeCalendarService officeCalendarService;
	private UserService userService;
	private OfficeWorkOutlineService officeWorkOutlineService;
	private DeptService deptService;
	private UserRoleRelationService userRoleRelationService;
	private RolePermService rolePermService;

	@Override
	public String execute() throws Exception {
//		isHasUnitAuth();
		return SUCCESS;
	}

	/**
	 * 日历 
	 * 2016年11月17日
	 */
	public void scheduleCalendar() {
		Pagination page = getPage();
		JSONObject json = new JSONObject();
		Calendar cal = Calendar.getInstance();
		if (calDto.getCalendarTime() == null) {
			cal.setTime(new Date());
			choiseDay = new Date().getDate();
		} else {
			cal.setTime(calDto.getCalendarTime());
			if ("1".equals(backAndForth)) {
				cal.add(Calendar.MONTH, 1);
			} else if ("0".equals(backAndForth)) {
				cal.add(Calendar.MONTH, -1);
			}
		}
		calDto.setCalendarTime(cal.getTime());
		int lenth = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
		calDto.setWeekNum(cal.getActualMaximum(Calendar.WEEK_OF_MONTH));
		calDto.setDayNum(lenth);
		Calendar cal2 = Calendar.getInstance();
		cal2.setTime(calDto.getCalendarTime());
		cal2.set(Calendar.DATE, 1);// 该月第一天
		calDto.setFirstWeek(cal2.get(Calendar.DAY_OF_WEEK));
		cal2.add(Calendar.MONTH, -1);
		int prevDayNum = cal2.getActualMaximum(Calendar.DAY_OF_MONTH);

		String thisMonTime = DateUtils.date2String(cal.getTime(), "yyyy-MM");// 日历显示月份
		String currMonTime = DateUtils.date2String(new Date(), "yyyy-MM");// 现在月份
		if (StringUtils.isNotBlank(lastChoiseDay)
				&& thisMonTime.equals(lastChoiseDay.substring(0, 7))) {
			choiseDay = Integer.parseInt(lastChoiseDay.substring(8, 10));
		}
		if (thisMonTime.equals(currMonTime) && choiseDay < 1) {
			if (StringUtils.isNotBlank(lastChoiseDay)
					&& currMonTime.equals(lastChoiseDay.substring(0, 7))) {
				choiseDay = new Date().getDate();
			}
			if (StringUtils.isBlank(lastChoiseDay)){
				choiseDay = new Date().getDate();
			}
		}
		
		Calendar cal_s = Calendar.getInstance();
		cal_s.setTime(calDto.getCalendarTime());
		cal_s.set(Calendar.DAY_OF_MONTH, 1);
		String startTime = DateUtils.date2String(cal_s.getTime(), "yyyy-MM-dd");
		
		Calendar cal_e = Calendar.getInstance();
		cal_e.setTime(calDto.getCalendarTime());
		cal_e.add(Calendar.MONTH, 1);
		cal_e.set(Calendar.DATE, 1);
		cal_e.add(Calendar.DATE, -1);
		String endTime = DateUtils.date2String(cal_e.getTime(), "yyyy-MM-dd");
		JSONArray array = new JSONArray();
		redMap = officeCalendarService.getNumMap(unitId, userId, dataType, startTime, endTime);
		for (Map.Entry<String, Integer> entry : redMap.entrySet()) {
			   if(entry.getValue()>0){
				   String key = entry.getKey().toString();
				   array.add(Integer.parseInt(entry.getKey().substring(8, 10)));
			   }
			  }
		json.put("dateVal", DateUtils.date2String(cal.getTime(), "yyyy年MM月"));
		json.put("date", DateUtils.date2String(cal.getTime(),"yyyy-MM-dd"));
		json.put("weekNum", calDto.getWeekNum());
		json.put("dayNum", calDto.getDayNum());
		json.put("firstWeek", calDto.getFirstWeek());
		json.put("prevDayNum", prevDayNum);
		json.put("choiseDay", choiseDay);
		json.put("redArray", array);
		jsonMap.put(getDetailObjectName(), json);
		jsonMap.put("result", 1);
		jsonMap.put("page", page);
		responseJSON(jsonMap);
	}

	public void scheduleList() {
		Pagination page = getPage();
		JSONArray array = new JSONArray();
		if(calendar.getCalendarTime()==null){
			calendar.setCalendarTime(new Date());
		}
		//TODO 根据日期、权限（个人、科室、单位）查询calendarList
		calendarList = officeCalendarService.getCalendarsByConditions(unitId, userId, dataType, DateUtils.date2String(calendar.getCalendarTime(),"yyyy-MM-dd"), null);
		for(OfficeCalendar calendar : calendarList){
			JSONObject json = new JSONObject();
			json.put("id", calendar.getId());
			json.put("startDate", DateUtils.date2String(calendar.getCalendarTime(),"yyyy.MM.dd HH:mm"));
			json.put("endDate", DateUtils.date2String(calendar.getEndTime(),"yyyy.MM.dd HH:mm"));
			String content = OfficeUtils.delHTMLTag(calendar.getContent());
			if(content.length() > 25){
				content = content.substring(0, 25)+"...";
			}
			json.put("content", content);
			if(StringUtils.isBlank(calendar.getPlace())){
				json.put("place", "");
			}else{
				json.put("place", calendar.getPlace());
			}
			json.put("userName", calendar.getOperatorName());
			json.put("periodName", getPeriodStr(calendar.getPeriod()));
			array.add(json);
		}
//		isHasUnitAuth();
//		jsonMap.put("hasUnitAuth", hasUnitAuth);
		if(isGroupHead()){
			jsonMap.put("isGroupHead", 1);
		}else{
			jsonMap.put("isGroupHead", 0);
		}
		if(isLeader()){
			jsonMap.put("isLeader", 1);
		}else{
			jsonMap.put("isLeader", 0);
		}
		jsonMap.put(getListObjectName(), array);
		jsonMap.put("page", page);
		jsonMap.put("result", 1);
		responseJSON(jsonMap);
	}

	public void scheduleApply() {//TODO
		JSONObject json = new JSONObject();
//		if("2" != dataType){
			if(StringUtils.isNotEmpty(id)){
				calendar = officeCalendarService.getOfficeCalendarById(id);
				if(calendar==null){
					calendar = new OfficeCalendar();
					calendar.setUnitId(unitId);
					calendar.setOperator(userId);
					calendar.setCreator(userId);
				}
			}else{
				calendar = new OfficeCalendar();
				calendar.setUnitId(unitId);
				calendar.setOperator(userId);
				calendar.setCreator(userId);
			}
			json.put("id", calendar.getId());
			json.put("startDate", DateUtils.date2String(calendar.getCalendarTime(),"yyyy-MM-dd HH:mm"));
			json.put("endDate", DateUtils.date2String(calendar.getEndTime(),"yyyy-MM-dd HH:mm"));
			json.put("place", calendar.getPlace());
			json.put("period", calendar.getPeriod());
			json.put("content", calendar.getContent());
			json.put("attachmentArray",RemoteOfficeUtils.createAttachmentArray(calendar.getAttachments()));
//		}
//		else{
//			OfficeWorkOutline officeWorkOutline;
//			String deptid = userService.getDeptIdByUserId(getLoginUser().getUserId());
//			if(StringUtils.isNotEmpty(id)){
//				officeWorkOutline = officeWorkOutlineService.getOfficeWorkOutlineById(id);
//				if(officeWorkOutline==null){
//					officeWorkOutline = new OfficeWorkOutline();
//					officeWorkOutline.setUnitId(unitId);
//					officeWorkOutline.setOperator(userId);
//					officeWorkOutline.setCreateDept(deptid);
//				}
//			}else{
//				officeWorkOutline = new OfficeWorkOutline();
//				officeWorkOutline.setUnitId(unitId);
//				officeWorkOutline.setOperator(userId);
//				officeWorkOutline.setCreateDept(deptid);
//			}
//			json.put("id", officeWorkOutline.getId());
//			json.put("startDate", DateUtils.date2String(officeWorkOutline.getCalendarTime(),"yyyy-MM-dd HH:mm"));
//			json.put("endDate", DateUtils.date2String(officeWorkOutline.getEndTime(),"yyyy-MM-dd HH:mm"));
//			json.put("place", officeWorkOutline.getPlace());
//			json.put("period", officeWorkOutline.getPeriod());
//			json.put("content", officeWorkOutline.getContent());
//			json.put("attachmentArray",RemoteOfficeUtils.createAttachmentArray(officeWorkOutline.getAttachments()));
//		}
		
		JSONArray periodArray = new JSONArray();
		for(int i=0;i<5;i++){
			JSONObject periodObject=new JSONObject();
			periodObject.put("period", i);
			periodObject.put("periodName", getPeriodStr(i));
			periodArray.add(periodObject);
		}
		json.put("periodArray",periodArray);
		jsonMap.put(getDetailObjectName(), json);
		jsonMap.put("result",1);
		responseJSON(jsonMap);
	}

	public void scheduleSave() {
		UploadFile file = null;
		try {
//			if("2" != dataType){
				calendar.setOperator(userId);
				calendar.setUnitId(unitId);
//				calendar.setCreatorType(Integer.parseInt(dataType));
				calendar.setCreatorType(1);//个人日志
				calendar.setCreator(userId);
				//TODO 保存时添加附件
				//promptMessageDto = officeCalendarService.saveOrUpdate(calendar);
				file = StorageFileUtils.handleFile(new String[] {}, 5 * 1024);
				
				int day = (int) ((calendar.getEndTime().getTime() - calendar
						.getCalendarTime().getTime()) / (1000 * 60 * 60 * 24)) + 1;
				calendar.setHalfDays(day);
				calendar.setSmsAlarmTime(null);
				calendar.setIsSmsAlarm(false);
				promptMessageDto = officeCalendarService.saveOrUpdate(calendar, file);
//			}
//			else{
//				OfficeWorkOutline officeWorkOutline = new OfficeWorkOutline();
//				String deptid = userService.getDeptIdByUserId(getLoginUser().getUserId());
//				//-------------------------
//				officeWorkOutline.setId(calendar.getId());
//				officeWorkOutline.setEndTime(calendar.getEndTime());
//				officeWorkOutline.setCalendarTime(calendar.getCalendarTime());
//				
//				boolean flag=officeWorkOutlineService.isExistConflict(this.getUnitId(), deptid, officeWorkOutline.getId(), officeWorkOutline.getCalendarTime(), officeWorkOutline.getEndTime());
//				if(flag){
//					promptMessageDto.setOperateSuccess(false);
//					promptMessageDto.setPromptMessage("保存失败,当前时间段与已维护过的时间段有冲突!");
//				}
//				int day = (int) ((officeWorkOutline.getEndTime().getTime() - officeWorkOutline
//						.getCalendarTime().getTime()) / (1000 * 60 * 60 * 24)) + 1;
//				officeWorkOutline.setHalfDays(day);
//				try {
//					officeWorkOutline.setIsSmsAlarm(ScheduleConstant.WORKOUTLINE_NOTSMSALARM);
//					officeWorkOutline.setSmsAlarmTime(null);
//					
//					if(StringUtils.isNotBlank(officeWorkOutline.getId())){
//						officeWorkOutline.setOperator(userId);
//						officeWorkOutline.setModifyTime(new Date());
//						officeWorkOutlineService.update(officeWorkOutline, file);
//					}else{
//						officeWorkOutline.setCreateDept(deptid);
//						officeWorkOutline.setOperator(userId);
//						officeWorkOutline.setUnitId(unitId);
//						officeWorkOutline.setModifyTime(new Date());
//						officeWorkOutline.setIsDeleted(0);
////						officeWorkOutline.setSmsAlarmTime(null);
//						officeWorkOutline.setVersion(ScheduleConstant.WORKOUTLINE_VERSION);
//						officeWorkOutlineService.save(officeWorkOutline, file);
//					}
//					promptMessageDto.setOperateSuccess(true);
//					promptMessageDto.setPromptMessage("保存成功！");
//				} catch (Exception e) {
//					e.printStackTrace();
//					promptMessageDto.setOperateSuccess(false);
//					promptMessageDto.setPromptMessage("保存失败！");
//				}
//			}
			
			if (promptMessageDto.getOperateSuccess()) {
				jsonMap.put("result", 1);
			} else {
				jsonMap.put("result", 0);
				jsonMap.put("msg", "提交失败:" + promptMessageDto.getErrorMessage());
			}
		} catch (Exception e) {
			jsonMap.put("result", 0);
			e.printStackTrace();
			jsonMap.put("msg", "提交失败:" + e.getMessage());
		}
		responseJSON(jsonMap);
	}
	
	public void scheduleDetail() {
//		if(!"2".equals(dataType)){
			calendar = officeCalendarService.getOfficeCalendarById(id);
			if(calendar==null){
				calendar=new OfficeCalendar();
			}
			String photoUrl = userSetService.getUserPhotoUrl(calendar.getOperator());
			
			JSONObject json = new JSONObject();
			json.put("startDate", DateUtils.date2String(calendar.getCalendarTime(),"yyyy-MM-dd HH:mm"));
			json.put("endDate", DateUtils.date2String(calendar.getEndTime(),"yyyy-MM-dd HH:mm"));
			json.put("place", calendar.getPlace());
			json.put("periodName", getPeriodStr(calendar.getPeriod()));
			json.put("photoUrl", photoUrl);
			json.put("userName", calendar.getOperatorName());
			json.put("content", calendar.getContent());
			json.put("attachmentArray",RemoteOfficeUtils.createAttachmentArray(calendar.getAttachments()));
			jsonMap.put(getDetailObjectName(), json);
			jsonMap.put("result", 1);
			responseJSON(jsonMap);
//		}
//		else{
//			OfficeWorkOutline officeWorkOutline = officeWorkOutlineService.getOfficeWorkOutlineById(id);
//			if(officeWorkOutline==null){
//				officeWorkOutline=new OfficeWorkOutline();
//			}
////			String deptNam=deptService.getDept(userService.getDeptIdByUserId(getLoginUser().getUserId())).getDeptname();
//			User user = userService.getUser(officeWorkOutline.getOperator());
//			String userName = "";
//			if(user != null){
//				userName = user.getRealname();
//			}
//			String photoUrl = userSetService.getUserPhotoUrl(officeWorkOutline.getOperator());
//			JSONObject json = new JSONObject();
//			json.put("startDate", DateUtils.date2String(officeWorkOutline.getCalendarTime(),"yyyy-MM-dd HH:mm"));
//			json.put("endDate", DateUtils.date2String(officeWorkOutline.getEndTime(),"yyyy-MM-dd HH:mm"));
//			json.put("place", officeWorkOutline.getPlace());
//			json.put("periodName", getPeriodStr(officeWorkOutline.getPeriod()));
//			json.put("photoUrl", photoUrl);
//			json.put("userName", userName);
//			json.put("content", officeWorkOutline.getContent());
//			json.put("attachmentArray",RemoteOfficeUtils.createAttachmentArray(officeWorkOutline.getAttachments()));
//			jsonMap.put(getDetailObjectName(), json);
//			jsonMap.put("result", 1);
//			responseJSON(jsonMap);
//		}
	}

	private String getPeriodStr(int period){
		String periodName = "";
		switch (period) {
		case 1:
			periodName= "上午";
			break;
		case 2:
			periodName= "中午";
			break;
		case 3:
			periodName= "下午";
			break;
		case 4:
			periodName= "晚上";
			break;
		default:
			periodName= "全天";
			break;
		}
		return periodName;
	}
	
	private String getAuthStr(String dataType){
		String AuthName = "";
		if("3".equals(dataType)){
			AuthName = "领导日志";
		}else if("2".equals(dataType)){
			AuthName = "科室日志";
		}else{
			AuthName = "个人日志";
		}
		return AuthName;
	}
	public boolean isHasUnitAuth() {
		List<UserRoleRelation> relaList = userRoleRelationService.getUserRoles(new String[] { userId});
				 
		Set<String> allOperSet = new  HashSet<String>();
		if (!relaList.isEmpty()) {
			String[] roleIds = new String[relaList.size()];
			for (int i = 0; i < roleIds.length; i++) {
				roleIds[i] = relaList.get(i).getRoleid();
			}
			List<Role> roleList = rolePermService.getCacheRoleList(roleIds);
			for(Role role:roleList){
				allOperSet.addAll(role.getOperSet());
			}
		} 
		if (!hasUnitAuth) {
			hasUnitAuth = allOperSet.contains("SCHEDULE_LEADER");
		}
		return hasUnitAuth;
	}

	@Override
	protected String getListObjectName() {
		return "result_array";
	}

	@Override
	protected String getDetailObjectName() {
		return "result_object";
	}

	public String getUnitId() {
		return unitId;
	}

	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public void setHasUnitAuth(boolean hasUnitAuth) {
		this.hasUnitAuth = hasUnitAuth;
	}

	public OfficeCalendarDto getCalDto() {
		return calDto;
	}

	public void setCalDto(OfficeCalendarDto calDto) {
		this.calDto = calDto;
	}

	public void setOfficeCalendarAuthService(
			OfficeCalendarAuthService officeCalendarAuthService) {
		this.officeCalendarAuthService = officeCalendarAuthService;
	}

	public String getBackAndForth() {
		return backAndForth;
	}

	public void setBackAndForth(String backAndForth) {
		this.backAndForth = backAndForth;
	}

	public OfficeCalendar getCalendar() {
		return calendar;
	}

	public void setCalendar(OfficeCalendar calendar) {
		this.calendar = calendar;
	}

	public Map<String, OfficeCalendarDto> getCalMap() {
		return calMap;
	}

	public void setCalMap(Map<String, OfficeCalendarDto> calMap) {
		this.calMap = calMap;
	}

	public void setOfficeCalendarService(
			OfficeCalendarService officeCalendarService) {
		this.officeCalendarService = officeCalendarService;
	}

	public int getChoiseDay() {
		return choiseDay;
	}

	public void setChoiseDay(int choiseDay) {
		this.choiseDay = choiseDay;
	}

	public String getLastChoiseDay() {
		return lastChoiseDay;
	}

	public void setLastChoiseDay(String lastChoiseDay) {
		this.lastChoiseDay = lastChoiseDay;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public void setOfficeWorkOutlineService(
			OfficeWorkOutlineService officeWorkOutlineService) {
		this.officeWorkOutlineService = officeWorkOutlineService;
	}

	public void setDeptService(DeptService deptService) {
		this.deptService = deptService;
	}

	public void setUserRoleRelationService(
			UserRoleRelationService userRoleRelationService) {
		this.userRoleRelationService = userRoleRelationService;
	}

	public void setRolePermService(RolePermService rolePermService) {
		this.rolePermService = rolePermService;
	}

	public boolean isHasDeptAdd() {
		if (!hasDeptAdd) {
			hasDeptAdd = officeCalendarAuthService.checkHasAuth(getUnitId(), ScheduleConstant.CALENDAR_AUTH_TYPE_DEPT, userId);
		}
		return hasDeptAdd;
	}

	public void setHasDeptAdd(boolean hasDeptAdd) {
		this.hasDeptAdd = hasDeptAdd;
	}

	public boolean isHasUnitAdd() {
		if(!hasUnitAdd){
			hasUnitAdd = officeCalendarAuthService.checkHasAuth(getUnitId(), ScheduleConstant.CALENDAR_AUTH_TYPE_LEADER, getLoginInfo().getUser().getId());
		}
		return hasUnitAdd;
	}

	public void setHasUnitAdd(boolean hasUnitAdd) {
		this.hasUnitAdd = hasUnitAdd;
	}

	public boolean isGroupHead() {
		String deptId = deptService.isPrincipanGroupHead(userId);
		if(StringUtils.isNotBlank(deptId))
			return true;
		return false;
	}

	public void setGroupHead(boolean isGroupHead) {
		this.isGroupHead = isGroupHead;
	}

	public boolean isLeader() {
		if(!isLeader){
			isLeader = deptService.isLeader(unitId, userId);
		}
		return isLeader;
	}

	public void setLeader(boolean isLeader) {
		this.isLeader = isLeader;
	}

	public Map<String, Integer> getRedMap() {
		return redMap;
	}

	public void setRedMap(Map<String, Integer> redMap) {
		this.redMap = redMap;
	}

}
