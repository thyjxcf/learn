package net.zdsoft.office.schedule.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.json.JSONObject;
import net.zdsoft.eis.base.attachment.entity.Attachment;
import net.zdsoft.eis.base.attachment.service.AttachmentService;
import net.zdsoft.eis.base.common.dao.DeptDao;
import net.zdsoft.eis.base.common.dao.UserDao;
import net.zdsoft.eis.base.common.entity.Teacher;
import net.zdsoft.eis.base.common.entity.User;
import net.zdsoft.eis.base.common.service.DeptService;
import net.zdsoft.eis.base.common.service.TeacherService;
import net.zdsoft.eis.base.common.service.UserService;
import net.zdsoft.eis.base.constant.BaseConstant;
import net.zdsoft.eis.frame.dto.PromptMessageDto;
import net.zdsoft.keel.util.DateUtils;
import net.zdsoft.keel.util.Pagination;
import net.zdsoft.keelcnet.action.UploadFile;
import net.zdsoft.leadin.util.UUIDGenerator;
import net.zdsoft.office.schedule.dao.OfficeCalendarDao;
import net.zdsoft.office.schedule.dao.OfficeWorkOutlineDao;
import net.zdsoft.office.schedule.dto.DateDto;
import net.zdsoft.office.schedule.dto.OfficeCalendarDto;
import net.zdsoft.office.schedule.entity.OfficeCalendar;
import net.zdsoft.office.schedule.entity.OfficeWorkOutline;
import net.zdsoft.office.schedule.service.OfficeCalendarAuthService;
import net.zdsoft.office.schedule.service.OfficeCalendarService;
import net.zdsoft.office.schedule.util.ScheduleConstant;
import net.zdsoft.office.util.Constants;
import net.zdsoft.smsplatform.client.SendResult;
import net.zdsoft.smsplatform.client.ZDConstant;
import net.zdsoft.smsplatform.client.ZDResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;

import com.alibaba.fastjson.JSONArray;
/**
 * 日程安排表
 * @author 
 * 
 */
public class OfficeCalendarServiceImpl implements OfficeCalendarService{
	private OfficeCalendarDao officeCalendarDao;
	private UserDao userDao;
	private TeacherService teacherService;
	private DeptDao deptDao;
	private OfficeCalendarAuthService officeCalendarAuthService;
	private OfficeWorkOutlineDao officeWorkOutlineDao;
	private AttachmentService attachmentService;
	private UserService userService;
	private DeptService deptService;

	@Override
	public PromptMessageDto saveOrUpdate(OfficeCalendar officeCalendar){
		PromptMessageDto pm = new PromptMessageDto();
		pm.setOperateSuccess(true);
		pm.setPromptMessage("保存成功！");
		if(officeCalendarDao.checkExistsTimeCross(officeCalendar)){
			pm.setErrorMessage("开始结束时间和其他记录有交叉！");
			pm.setOperateSuccess(false);
			return pm;
		}
		Date now = new Date();
		officeCalendar.setModifyTime(now);
		if (StringUtils.isBlank(officeCalendar.getId())) {
			officeCalendar.setVersion(BaseConstant.ZERO_GUID);
		}
		if(officeCalendar.getIsSmsAlarm()){
			officeCalendar.setVersion(UUIDGenerator.getUUID());
			JSONObject json = new JSONObject();
			JSONArray receivers = new JSONArray();
			JSONObject receiverJ = new JSONObject();
			User u = userDao.getUser(officeCalendar.getCreator());
			Teacher teacher = teacherService.getTeacher(u.getTeacherid());
			json.put("msg", getScheduleMessageContent(officeCalendar));
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
			json.put("sendTime", sdf.format(officeCalendar.getSmsAlarmTime()));
			receiverJ.put("phone", teacher.getPersonTel());
			receiverJ.put("unitName", "");
			receiverJ.put("unitId", teacher.getUnitid());
			receiverJ.put("username", teacher.getName());
			receiverJ.put("userId", ZDConstant.DEFAULT_USER_ID);
			receivers.add(receiverJ);
			if(receivers.size()>0){
				json.put("receivers", receivers);           
				SmsThread smsThread = new SmsThread(json);
				smsThread.start();
			}
		}
		
		if (StringUtils.isBlank(officeCalendar.getId())) {
			officeCalendarDao.save(officeCalendar);
		} else {
			officeCalendarDao.update(officeCalendar);
		}
		return pm;
	}
	
	@Override
	public PromptMessageDto saveOrUpdate(OfficeCalendar officeCalendar, UploadFile file){
		PromptMessageDto pm = this.saveOrUpdate(officeCalendar);
		
		List<Attachment> attachments = attachmentService.getAttachments(officeCalendar.getId(), Constants.OFFICE_SCHEDULE_AIT);
		if(file!=null){
			if(CollectionUtils.isNotEmpty(attachments)){
				Attachment attachment = attachments.get(0);
				attachment.setFileName(file.getFileName());
				attachment.setContentType(file.getContentType());
				attachment.setFileSize(file.getFileSize());
				attachmentService.updateAttachment(attachment, file, true);
			}else{
				Attachment attachment = new Attachment();
				attachment.setFileName(file.getFileName());
				attachment.setContentType(file.getContentType());
				attachment.setFileSize(file.getFileSize());
				attachment.setUnitId(officeCalendar.getUnitId());
				attachment.setObjectId(officeCalendar.getId());
				attachment.setObjectType(Constants.OFFICE_SCHEDULE_AIT);
				attachmentService.saveAttachment(attachment, file);
			}
		}
		if(StringUtils.isBlank(officeCalendar.getUploadContentFileInput())&&file==null){
			String[] attachmentIds = new String[attachments.size()];
			for(int i = 0; i < attachments.size(); i++){
				attachmentIds[i] = attachments.get(i).getId();
			}
			attachmentService.deleteAttachments(attachmentIds);
		}
		return pm;
	}
	
	private String getScheduleMessageContent(OfficeCalendar schedule) {
        StringBuffer sb = new StringBuffer();
        sb.append("您好！请在").append(DateUtils.date2StringByMinute(schedule.getCalendarTime()));
        sb.append("参加").append(net.zdsoft.keel.util.StringUtils.htmlFilterToEmpty(schedule.getContent()));
        if(StringUtils.isNotBlank(schedule.getPlace())){
        	sb.append("，地点：").append(net.zdsoft.keel.util.StringUtils.htmlFilterToEmpty(schedule.getPlace()));
        }
        sb.append("。请注意安排好时间。");
        return sb.toString();
    }
	
	private class SmsThread extends Thread{
    	private JSONObject json;
    	public SmsThread(JSONObject json){
    		this.json = json;
    	}

		@Override
		public void run() {
			try {
				SendResult sr = ZDResponse.post(json);
				System.out.println("--日程安排发送短信--"+sr.getDescription());
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("--日程安排发送短信失败--");
			}
 		}
    }

	@Override
	public Integer delete(String[] ids){
		return officeCalendarDao.delete(ids);
	}

	@Override
	public OfficeCalendar getOfficeCalendarById(String id){//TODO
		OfficeCalendar officeCalendar = officeCalendarDao.getOfficeCalendarById(id);
		if(officeCalendar == null){
			officeCalendar = new OfficeCalendar();
			officeCalendar.setAllDayEvent(false);
		}else{
			String cts = DateUtils.date2String(officeCalendar.getCalendarTime(), "HH:mm");
			String ets = DateUtils.date2String(officeCalendar.getEndTime(), "HH:mm");
			if(("00:00".equals(cts) 
					&& "23:59".equals(ets)) || officeCalendar.getPeriod() == ScheduleConstant.PERIOD_ALLDAY) {
				officeCalendar.setAllDayEvent(true);
			}else{
				officeCalendar.setAllDayEvent(false);
			}
			User user = userDao.getUser(officeCalendar.getOperator());
			if(user != null){
				officeCalendar.setOperatorName(user.getRealname());
			}
		}
		
		officeCalendar.setAttachments(attachmentService.getAttachments(officeCalendar.getId(), Constants.OFFICE_SCHEDULE_AIT));
		return officeCalendar;
	}

	@Override
	public Map<String, OfficeCalendar> getOfficeCalendarMapByIds(String[] ids){
		return officeCalendarDao.getOfficeCalendarMapByIds(ids);
	}
	
	public Map<String, OfficeCalendarDto> getCalendarMapBy(OfficeCalendarDto oc, List<DateDto> dates){//TODO
		Map<String, OfficeCalendarDto> ocm = new HashMap<String, OfficeCalendarDto>();
		List<String> creats = new ArrayList<String>();
		Map<String, String> unameMap = new HashMap<String, String>();
		if((ScheduleConstant.CALENDAR_RANGE_TYPE_SELF).equals(oc.getRangeType())){
			creats.add(oc.getCreator());
			User uu = userDao.getUser(oc.getCreator());
			if(uu != null){
				unameMap.put(uu.getId(), uu.getRealname());
			}
		} else if((ScheduleConstant.CALENDAR_RANGE_TYPE_DEPT).equals(oc.getRangeType())){
			User uu = userDao.getUser(oc.getCreator());
			if(uu != null){
				Teacher tt = teacherService.getTeacher(uu.getTeacherid());
				if(tt != null){
					oc.setDeptId(tt.getDeptid());
					List<Teacher> tts = teacherService.getTeachers(tt.getUnitid(), tt.getDeptid());
					if(CollectionUtils.isNotEmpty(tts)){
						List<String> tids = new ArrayList<String>();
						for(Teacher ta : tts){
							tids.add(ta.getId());
						}
						List<User> us = userDao.getUsersByOwner(User.TEACHER_LOGIN, tids.toArray(new String[0]));
						for(User u : us){
							creats.add(u.getId());
							unameMap.put(u.getId(), u.getRealname());
						}
					}
				}
			}
		} else if((ScheduleConstant.CALENDAR_RANGE_TYPE_UNIT).equals(oc.getRangeType())){
			List<User> us = userDao.getUsers(oc.getUnitId(), User.TEACHER_LOGIN);
			for(User u : us){
				creats.add(u.getId());
				unameMap.put(u.getId(), u.getRealname());
			}
		}
		if(creats.size() < 1){
			return ocm;
		}
		List<OfficeCalendar> ocs = officeCalendarDao.getOfficeCalendarsBy(oc.getUnitId(), 
				DateUtils.date2StringByDay(oc.getCalendarTime()), DateUtils.date2StringByDay(oc.getEndTime()), NumberUtils.toInt(oc.getCreatorType()), creats.toArray(new String[0]));
		boolean needContent = oc.isNeedContent();
		boolean fullContent = oc.isFullContent();
		Calendar st = Calendar.getInstance();
		Calendar et = Calendar.getInstance();
		for (DateDto da : dates) {
			for (OfficeCalendar ocd : ocs) {
				st.setTime(DateUtils.getStartDate(ocd.getCalendarTime()));
				et.setTime(DateUtils.getEndDate(ocd.getEndTime()));
				if(st.getTime().compareTo(da.getCalendarDate()) > 0
						|| et.getTime().compareTo(da.getCalendarDate()) < 0){
					continue;
				}
				OfficeCalendarDto dto = null;
				ocd.setCreatorName(unameMap.get(ocd.getCreator()));
				int period = 0;
				if(ocd.getPeriod() > 0 || !needContent){// 非全天事件或者是月视图
					if(needContent){// 月视图时，都放在全天的时间段内
						period=ocd.getPeriod();
					}
					dealPeriodCal(dto, ocd, ocm, da, period, needContent, fullContent);
				} else {
					// 全天项目
					for(int i=ScheduleConstant.PERIOD_AM;i<=ScheduleConstant.PERIOD_NIGHT;i++){
						dealPeriodCal(dto, ocd, ocm, da, i, needContent, fullContent);
					}
				}
			}
		}
		return ocm;
	}
	
	/**
	 * 
	 * @param dto 数据存放dto
	 * @param ocd 原始日程数据
	 * @param ocm 数据存放map，key=dateStr+period
	 * @param da 日期
	 * @param period 时间段
	 * @param needContent 是否需要显示内容，周视图、日程用
	 * @param fullContent 是否需要显示全部内容，周视图导出用
	 */
	private void dealPeriodCal(OfficeCalendarDto dto, OfficeCalendar ocd, Map<String, OfficeCalendarDto> ocm, 
			DateDto da, int period, boolean needContent, boolean fullContent){
		String dastr = da.getStrByDay();
		dto = ocm.get(dastr + "-" + period);
		if(dto == null){
			dto = new OfficeCalendarDto();
			dto.setCalendarTime(da.getCalendarDate());
			dto.setCreator(ocd.getCreator());
			dto.setCreatorType(ocd.getCreatorType() + "");
			dto.setPeriod(period + "");
		}
		dto.setNum(dto.getNum() + 1);
		if(needContent){
			List<OfficeCalendar> cals = dto.getCals();
			if(cals == null){
				cals = new ArrayList<OfficeCalendar>();
			}
			if(!fullContent && cals.size() >= 3){
				return ;
			}
			cals.add(ocd);
			dto.setCals(cals);
		}
		ocm.put(dastr + "-" + period, dto);
	}

	public List<OfficeCalendar> getOfficeCalendarDetails(OfficeCalendarDto oc){
		List<String> creats = new ArrayList<String>();
		Map<String, String> unameMap = new HashMap<String, String>();
		if((ScheduleConstant.CALENDAR_RANGE_TYPE_SELF).equals(oc.getRangeType())){
			creats.add(oc.getCreator());
			User uu = userDao.getUser(oc.getCreator());
			if(uu != null){
				unameMap.put(uu.getId(), uu.getRealname());
			}
		} else if((ScheduleConstant.CALENDAR_RANGE_TYPE_DEPT).equals(oc.getRangeType())){
			User uu = userDao.getUser(oc.getCreator());
			if(uu != null){
				Teacher tt = teacherService.getTeacher(uu.getTeacherid());
				if(tt != null){
					oc.setDeptId(tt.getDeptid());
					List<Teacher> tts = teacherService.getTeachers(tt.getUnitid(), tt.getDeptid());
					if(CollectionUtils.isNotEmpty(tts)){
						List<String> tids = new ArrayList<String>();
						for(Teacher ta : tts){
							tids.add(ta.getId());
						}
						List<User> us = userDao.getUsersByOwner(User.TEACHER_LOGIN, tids.toArray(new String[0]));
						for(User u : us){
							creats.add(u.getId());
							unameMap.put(u.getId(), u.getRealname());
						}
					}
				}
			}
		} else if((ScheduleConstant.CALENDAR_RANGE_TYPE_UNIT).equals(oc.getRangeType())){
			List<User> us = userDao.getUsers(oc.getUnitId(), User.TEACHER_LOGIN);
			for(User u : us){
				creats.add(u.getId());
				unameMap.put(u.getId(), u.getRealname());
			}
		}
		if(creats.size() < 1){
			return new ArrayList<OfficeCalendar>();
		}
		List<OfficeCalendar> cals = officeCalendarDao.getOfficeCalendarsByDate(oc.getUnitId(), DateUtils.date2StringByDay(oc.getCalendarTime()), 
				NumberUtils.toInt(oc.getPeriod()), NumberUtils.toInt(oc.getCreatorType()), creats.toArray(new String[0]));
		if(CollectionUtils.isNotEmpty(cals)){
			for(OfficeCalendar cal : cals){
				cal.setCreatorName(unameMap.get(cal.getCreator()));
			}
		}
		return cals;
	}
	
	@Override
	public List<OfficeCalendar> getOfficeCalendarList(){
		return officeCalendarDao.getOfficeCalendarList();
	}

	@Override
	public List<OfficeCalendar> getOfficeCalendarPage(Pagination page){
		return officeCalendarDao.getOfficeCalendarPage(page);
	}

	@Override
	public List<OfficeCalendar> getOfficeCalendarByUnitIdList(String unitId){
		return officeCalendarDao.getOfficeCalendarByUnitIdList(unitId);
	}

	@Override
	public List<OfficeCalendar> getOfficeCalendarByUnitIdPage(String unitId, Pagination page){
		return officeCalendarDao.getOfficeCalendarByUnitIdPage(unitId, page);
	}
	
	@Override
	public List<OfficeCalendar> getOfficeCalendarListPage(String userId,
			String unitId, String startTime, String endTime, Pagination page) {
		String[] userIds = deptDao.getUserIdsByLeaderId(unitId, userId);
		List<OfficeCalendar> calendars = new ArrayList<OfficeCalendar>();
		if(ArrayUtils.isNotEmpty(userIds)){
			Map<String, User> userMap = userDao.getUserMap(userIds);
			calendars = officeCalendarDao.getOfficeCalendarListPage(unitId, startTime, endTime, userIds, page);
			for(OfficeCalendar officeCalendar:calendars){
				User user = userMap.get(officeCalendar.getCreator());
				if(user!=null){
					officeCalendar.setCreatorName(user.getRealname());
				}else {
					officeCalendar.setCreatorName("用户已删除");
				}
				String cts = DateUtils.date2String(officeCalendar.getCalendarTime(), "HH:mm");
				String ets = DateUtils.date2String(officeCalendar.getEndTime(), "HH:mm");
				if(("00:00".equals(cts) 
						&& "23:59".equals(ets)) || officeCalendar.getPeriod() == ScheduleConstant.PERIOD_ALLDAY) {
					officeCalendar.setAllDayEvent(true);
				}else{
					officeCalendar.setAllDayEvent(false);
				}
				
				String conString=officeCalendar.getContent();
				if(org.apache.commons.lang3.StringUtils.isNotBlank(conString)){
					String conS=conString.replaceAll("</?[^>]+>", "").replaceAll("<a>\\s*|\t|\r|\n</a>", "");    
					officeCalendar.setContent(conS);
				}
			}
		}
		return calendars;
	}

	@Override
	public List<OfficeCalendar> getCalendarListByCondition(String unitId, String deptId, 
			String searchName, String startTime, String endTime, Pagination page){
		List<User> users = userDao.getUsersByUnitId(unitId);
		Set<String> userIds = new HashSet<String>();
		for(User user : users){
			if(StringUtils.isNotBlank(deptId) && !deptId.equals(user.getDeptid()))
				continue;
			if(StringUtils.isNotBlank(searchName) && user.getRealname().indexOf(searchName) == -1)
				continue;
			userIds.add(user.getId());
		}
		Map<String, User> userMap = userDao.getUserMap(userIds.toArray(new String[0]));
		List<OfficeCalendar> list = officeCalendarDao.getCalendarListByCondition(unitId, userIds.toArray(new String[0]), startTime, endTime, page); 
		for(OfficeCalendar officeCalendar : list){//TODO
			User user = userMap.get(officeCalendar.getCreator());
			if(user != null){
				officeCalendar.setCreatorName(user.getRealname());
			}
			String cts = DateUtils.date2String(officeCalendar.getCalendarTime(), "HH:mm");
			String ets = DateUtils.date2String(officeCalendar.getEndTime(), "HH:mm");
			if(("00:00".equals(cts) 
					&& "23:59".equals(ets)) || officeCalendar.getPeriod() == ScheduleConstant.PERIOD_ALLDAY) {
				officeCalendar.setAllDayEvent(true);
			}else{
				officeCalendar.setAllDayEvent(false);
			}
			String conString=officeCalendar.getContent();
			if(org.apache.commons.lang3.StringUtils.isNotBlank(conString)){
				String conS=conString.replaceAll("</?[^>]+>", "").replaceAll("<a>\\s*|\t|\r|\n</a>", "");    
				officeCalendar.setContent(conS);
			}
		}
		return list;
	}
	
	@Override
	public List<OfficeCalendar> getCalendarListByConditions(String unitId, String userId,
			String creatorType, String calendarTime, Pagination page){//TODO
		Set<String> userIds = new HashSet<String>();
		List<OfficeCalendar> list = new ArrayList<OfficeCalendar>();
		boolean hasDeptAuth = officeCalendarAuthService.checkHasAuth(unitId, ScheduleConstant.CALENDAR_AUTH_TYPE_DEPT, userId);
		boolean hasUnitAuth = officeCalendarAuthService.checkHasAuth(unitId, ScheduleConstant.CALENDAR_AUTH_TYPE_LEADER, userId);
		
		//个人日志、单位日志取office_calendar；部门日志取office_work_outline；
		//个人日志范围有个人、科室、单位；部门日志范围有科室、单位；单位日志范围有个人、单位
		User uu = userDao.getUser(userId);
		if("2".equals(creatorType)){//部门日志
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			try {
				List<OfficeWorkOutline> outlineList = officeWorkOutlineDao.getOfficeWorkOutlineByPeriodOfDay(unitId, hasUnitAuth?null:uu.getDeptid(), sdf.parse(calendarTime), -1);
				if(CollectionUtils.isNotEmpty(outlineList)){
					for(OfficeWorkOutline ol : outlineList){
						OfficeCalendar oc = new OfficeCalendar();
						oc.setId(ol.getId());
						oc.setCalendarTime(ol.getCalendarTime());
						oc.setEndTime(ol.getEndTime());
						oc.setContent(ol.getContent());
						oc.setPlace(ol.getPlace());
						oc.setPeriod(ol.getPeriod());
						oc.setOperator(ol.getOperator());
						userIds.add(ol.getOperator());
						list.add(oc);
					}
					
				}
			} catch (ParseException e) {
				e.printStackTrace();
				return new ArrayList<OfficeCalendar>();
			}
		}
		else{
			if(hasUnitAuth){
				List<User> us = userDao.getUsers(unitId, User.TEACHER_LOGIN);
				for(User u : us){
					userIds.add(u.getId());
				}
			}
			else if(hasDeptAuth && "1".equals(creatorType)){
				if(uu != null){
					Teacher tt = teacherService.getTeacher(uu.getTeacherid());
					if(tt != null){
						List<Teacher> tts = teacherService.getTeachers(tt.getUnitid(), tt.getDeptid());
						if(CollectionUtils.isNotEmpty(tts)){
							List<String> tids = new ArrayList<String>();
							for(Teacher ta : tts){
								tids.add(ta.getId());
							}
							List<User> us = userDao.getUsersByOwner(User.TEACHER_LOGIN, tids.toArray(new String[0]));
							for(User u : us){
								userIds.add(u.getId());
							}
						}
					}
				}
			}
			else{
				userIds.add(userId);
			}
			list = officeCalendarDao.getCalendarListByConditions(unitId, userIds.toArray(new String[0]), creatorType, calendarTime, calendarTime, page);
		}
		Map<String, User> userMap = userService.getUserWithDelMap(userIds.toArray(new String[0]));
		for(OfficeCalendar calendar : list){
			if(userMap.containsKey(calendar.getOperator())){
				User user = userMap.get(calendar.getOperator());
				if(user != null){
					calendar.setOperatorName(user.getRealname());
				}
				if(userId.equals(calendar.getOperator())){
					calendar.setOperatorName(user.getRealname());
				}
			}
		}
		return list;
	}
	
	@Override
	public List<OfficeCalendar> getCalendarsByConditions(String unitId, String userId,
			String dataType, String choseDate, Pagination page){
		Set<String> userIds = new HashSet<String>();
		List<OfficeCalendar> list = new ArrayList<OfficeCalendar>();
		
		//显示本人的个人日志
		if("1".equals(dataType)){
			userIds.add(userId);
			list = officeCalendarDao.getCalendarListByConditions(unitId, userIds.toArray(new String[0]), "1", choseDate, choseDate, page);
		}
		//显示科室的个人日志
		else if("2".equals(dataType)){
//			String deptId= userService.getDeptIdByUserId(userId);
			String deptId = deptService.isPrincipanGroupHead(userId);
			List<User> users = userService.getUsersByDeptId(deptId);
			for(User u : users){
				userIds.add(u.getId());
			}
			list = officeCalendarDao.getCalendarListByConditions(unitId, userIds.toArray(new String[0]), "1", choseDate, choseDate, page);
		}else{//显示科室负责人的日志
			String[] userids = deptDao.getUserIdsByLeaderId(unitId, userId);
			if(ArrayUtils.isNotEmpty(userids)){
				list = officeCalendarDao.getCalendarListByConditions(unitId, userids, "1", choseDate, choseDate, page);
			}
		}
		Map<String, User> userMap = userService.getUserWithDelMap(unitId);
		for(OfficeCalendar calendar : list){
			if(userMap.containsKey(calendar.getOperator())){
				User user = userMap.get(calendar.getOperator());
				if(user != null){
					calendar.setOperatorName(user.getRealname());
				}
				if(userId.equals(calendar.getOperator())){
					calendar.setOperatorName(user.getRealname());
				}
			}
			String cts = DateUtils.date2String(calendar.getCalendarTime(), "HH:mm");
			String ets = DateUtils.date2String(calendar.getEndTime(), "HH:mm");
			if(("00:00".equals(cts) 
					&& "23:59".equals(ets)) || calendar.getPeriod() == ScheduleConstant.PERIOD_ALLDAY) {
				calendar.setAllDayEvent(true);
			}else{
				calendar.setAllDayEvent(false);
			}
		}
		return list;
	}
	
	@Override
	public Map<String, Integer> getNumMap(String unitId, String userId, String dataType, 
			String startTime, String endTime){
		Set<String> userIds = new HashSet<String>();
		Map<String, Integer> map = new HashMap<String, Integer>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		if("1".equals(dataType)){
			userIds.add(userId);
		}
		else if("2".equals(dataType)){
//			String deptId= userService.getDeptIdByUserId(userId);
			String deptId = deptService.isPrincipanGroupHead(userId);
			List<User> users = userService.getUsersByDeptId(deptId);
			for(User u : users){
				userIds.add(u.getId());
			}
		}
		else{//显示科室负责人的日志
			String[] userids = deptDao.getUserIdsByLeaderId(unitId, userId);
			if(ArrayUtils.isNotEmpty(userids)){
				for(String uid : userids){
					userIds.add(uid);
				}
			}
		}
		List<OfficeCalendar> list = officeCalendarDao.getCalendarListByConditions(unitId, userIds.toArray(new String[0]), "1", null, null, null);
		try {
			Calendar cal = Calendar.getInstance();
			cal.setTime(sdf.parse(startTime));
			int i;
			while(!cal.getTime().after(sdf.parse(endTime))){
				i = 0;
				Date choseDate = DateUtils.string2Date(sdf.format(cal.getTime()), "yyyy-MM-dd");
				for(OfficeCalendar item : list){
					Date data1 = DateUtils.string2Date(sdf.format(item.getCalendarTime()), "yyyy-MM-dd");
					Date data2 = DateUtils.string2Date(sdf.format(item.getEndTime()), "yyyy-MM-dd");
					if((!choseDate.before(data1)) 
							&& (!choseDate.after(data2))){
						i++;
					}
				}
				map.put(sdf.format(choseDate), i);
				cal.add(Calendar.DATE, 1);
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		return map;
	}
	
	public void setOfficeCalendarDao(OfficeCalendarDao officeCalendarDao){
		this.officeCalendarDao = officeCalendarDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	public void setTeacherService(TeacherService teacherService) {
		this.teacherService = teacherService;
	}

	public void setDeptDao(DeptDao deptDao) {
		this.deptDao = deptDao;
	}

	public void setOfficeCalendarAuthService(
			OfficeCalendarAuthService officeCalendarAuthService) {
		this.officeCalendarAuthService = officeCalendarAuthService;
	}

	public void setOfficeWorkOutlineDao(OfficeWorkOutlineDao officeWorkOutlineDao) {
		this.officeWorkOutlineDao = officeWorkOutlineDao;
	}

	public void setAttachmentService(AttachmentService attachmentService) {
		this.attachmentService = attachmentService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public void setDeptService(DeptService deptService) {
		this.deptService = deptService;
	}
	
}