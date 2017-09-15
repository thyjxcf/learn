package net.zdsoft.office.remote.service.impl;

import java.math.BigDecimal;
import java.text.DateFormat;
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

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.zdsoft.eis.base.common.entity.DateInfo;
import net.zdsoft.eis.base.common.entity.User;
import net.zdsoft.eis.base.common.service.DateInfoService;
import net.zdsoft.eis.base.common.service.UnitService;
import net.zdsoft.eis.base.common.service.UserService;
import net.zdsoft.eis.component.flowManage.constant.FlowConstant;
import net.zdsoft.eis.component.flowManage.entity.Flow;
import net.zdsoft.eis.component.flowManage.service.FlowManageService;
import net.zdsoft.eis.frame.util.RedisUtils;
import net.zdsoft.eis.frame.util.RemoteCallUtils;
import net.zdsoft.keel.util.DateUtils;
import net.zdsoft.office.convertflow.constant.ConvertFlowConstants;
import net.zdsoft.office.remote.service.RemoteTacherAttendanceService;
import net.zdsoft.office.teacherAttendance.constant.AttendanceConstants;
import net.zdsoft.office.teacherAttendance.constant.ClockTypeEnum;
import net.zdsoft.office.teacherAttendance.constant.WeekdayEnum;
import net.zdsoft.office.teacherAttendance.dto.AttendanceCountDto;
import net.zdsoft.office.teacherAttendance.dto.AttendanceDetailCountDto;
import net.zdsoft.office.teacherAttendance.dto.AttendanceDto;
import net.zdsoft.office.teacherAttendance.entity.OfficeAttendanceColckApply;
import net.zdsoft.office.teacherAttendance.entity.OfficeAttendanceGroup;
import net.zdsoft.office.teacherAttendance.entity.OfficeAttendanceGroupUser;
import net.zdsoft.office.teacherAttendance.entity.OfficeAttendanceInfo;
import net.zdsoft.office.teacherAttendance.entity.OfficeAttendancePlace;
import net.zdsoft.office.teacherAttendance.entity.OfficeAttendanceSet;
import net.zdsoft.office.teacherAttendance.service.OfficeAttendanceColckApplyService;
import net.zdsoft.office.teacherAttendance.service.OfficeAttendanceColckLogService;
import net.zdsoft.office.teacherAttendance.service.OfficeAttendanceGroupService;
import net.zdsoft.office.teacherAttendance.service.OfficeAttendanceGroupUserService;
import net.zdsoft.office.teacherAttendance.service.OfficeAttendanceInfoService;
import net.zdsoft.office.teacherAttendance.service.OfficeAttendancePlaceService;
import net.zdsoft.office.teacherAttendance.service.OfficeAttendanceSetService;
import net.zdsoft.office.util.Constants;

public class RemoteTacherAttendanceServiceImpl implements
		RemoteTacherAttendanceService {
	//今天
	private static final int INFO_TYPE_0 = 0;
	//过去
	private static final int INFO_TYPE_Y = -1;
	//未来
	private static final int INFO_TYPE_T = 1;

	private UserService userService;
	private UnitService unitService;
	private OfficeAttendancePlaceService officeAttendancePlaceService;
	private OfficeAttendanceGroupService officeAttendanceGroupService;
	private OfficeAttendanceGroupUserService officeAttendanceGroupUserService;
	private OfficeAttendanceInfoService officeAttendanceInfoService;
	private OfficeAttendanceColckApplyService officeAttendanceColckApplyService;
	private OfficeAttendanceColckLogService officeAttendanceColckLogService;
	private OfficeAttendanceSetService officeAttendanceSetService;
	private DateInfoService dateInfoService;
	private FlowManageService flowManageService;
	
	private DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
	
	/**
	 * 获取考勤信息
	 */
	@Override
	public String attendanceDetail(String remoteParam) {
		try {
			JSONObject pjson = RemoteCallUtils.getParamJson(remoteParam);
			String userId = pjson.containsKey("syncUserId")?pjson.getString("syncUserId"):"";//数字校园userId
			Long attendanceDate = pjson.containsKey("attendanceDate")?pjson.getLong("attendanceDate"):0;//考勤日期(选填)
			
			if(StringUtils.isBlank(userId)){
				return RemoteCallUtils.returnResultError("userId不能为空");
			}
			
			User user = userService.getUser(userId);
			if(user == null){
				return RemoteCallUtils.returnResultError("用户信息不存在或非教师登录！");
			}
			
			
			//获取考勤组
			OfficeAttendanceGroupUser groupuser = officeAttendanceGroupUserService.getItemByUserId(userId);
			OfficeAttendanceGroup group = null;
			if(groupuser!=null){
				group = officeAttendanceGroupService.getOfficeAttendanceGroupById(groupuser.getGroupId());
			}
			if(group ==null){
				return RemoteCallUtils.returnResultError("没有所属的考勤组,请联系管理员！");
			}
			
			String[] placeIds = new String[]{};
			if(StringUtils.isNotBlank(group.getPlaceIds())){
				placeIds = group.getPlaceIds().split(",");
			}
			//获取考勤时间设置
			OfficeAttendanceSet aset = officeAttendanceSetService.getOfficeAttendanceSetById(group.getAttSetId());
			//办公地点
			
			if(aset == null){
				return RemoteCallUtils.returnResultError("所属考勤组没有维护时段信息,请联系管理员！");
			}
			
			
			//考勤日期
			Date attendanceTime = null;//yyyy-MM-dd
			//今天
			Date today = new Date();//对应的考勤日期
			//计算当天的上班可打卡时间点
			//上班时间
			Date startTime =DateUtils.string2Date(DateUtils.date2String(today, "yyyy-MM-dd") + " " + aset.getStartTimeStr(), "yyyy-MM-dd HH:mm:ss");//上班时间
			//当天上班可打卡时间
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(startTime);
			calendar.add(Calendar.HOUR, StringUtils.isNotBlank(aset.getStartRange())?-Integer.valueOf(aset.getStartRange()):0);//
			Date startClockTime = calendar.getTime();
			int infoType = INFO_TYPE_0;//0:今天，-1:过去的，1：将来的
			
			//优先计算当前时间 对应的考勤日期
			if(today.before(startClockTime)){
				//判断当前时间是否在可打卡时间之前
				//考勤日期为昨天的日期
				calendar.add(Calendar.DATE, -1);
				attendanceTime = DateUtils.string2Date(DateUtils.date2String(calendar.getTime(), "yyyy-MM-dd"), "yyyy-MM-dd");
				//上班时间
				startTime = addDate_1(calendar, startTime);
				//上班可打卡时间
				startClockTime = addDate_1(calendar, startClockTime);
			}else{//考勤日期为今天的日期
				attendanceTime = DateUtils.string2Date(DateUtils.date2String(today, "yyyy-MM-dd"), "yyyy-MM-dd");
			}
			if(attendanceDate>0){
				Date _attendanceDate = new Date(attendanceDate);
				//如果传了考勤日期 判断考勤日期与attendanceTime是否相同，相同则为今天的考勤，否则为过去或者未来 获取的记录自动生成缺卡记录，未来的记录不用生成缺卡记录
				Date attendanceTimeParm = DateUtils.string2Date(DateUtils.date2String(_attendanceDate, "yyyy-MM-dd"), "yyyy-MM-dd");
				if(attendanceTimeParm.before(attendanceTime)){
					//如果在当前考勤日期之前
					infoType = INFO_TYPE_Y;
					attendanceTime = attendanceTimeParm;
				}else if(attendanceTimeParm.after(attendanceTime)){
					//如果在当前考勤日期之后
					infoType = INFO_TYPE_T;
					attendanceTime = attendanceTimeParm;
				}else{
					//相等
					infoType = INFO_TYPE_0;
				}
			}
			
			if(infoType != INFO_TYPE_0){//跳转查看详情接口
				return attendanceDetail(groupuser, group, aset, user, attendanceTime, userId, infoType);
			}
			
			List<OfficeAttendancePlace> placelist = officeAttendancePlaceService.getListByIds(placeIds);
			if(CollectionUtils.isEmpty(placelist)){
				return RemoteCallUtils.returnResultError("所属考勤组没有维护办公地点,请联系管理员！");
			}
			
			
			//迟到开始时间
			calendar.setTime(startTime);
			calendar.add(Calendar.MINUTE, StringUtils.isNotBlank(aset.getElasticRange())?Integer.valueOf(aset.getElasticRange()):0);
			Date lateTime = calendar.getTime();
			
			//下午上班时间
			Date pmTime =DateUtils.string2Date(DateUtils.date2String(attendanceTime, "yyyy-MM-dd") + " " + aset.getPmTimeStr(), "yyyy-MM-dd HH:mm:ss");
			
			//下班时间
			Date endTime =DateUtils.string2Date(DateUtils.date2String(attendanceTime, "yyyy-MM-dd") + " " + aset.getEndTimeStr(), "yyyy-MM-dd HH:mm:ss");
			//早退截止时间
			calendar.setTime(endTime);
			calendar.add(Calendar.MINUTE, StringUtils.isNotBlank(aset.getEndElasticRange())?-Integer.valueOf(aset.getEndElasticRange()):0);
			Date earlyTime = calendar.getTime();
			
			calendar.setTime(attendanceTime);
			int weekday = calendar.get(Calendar.DAY_OF_WEEK);
			
			//查询打卡记录
			boolean isfeast = false;
			DateInfo dateinfo = dateInfoService.getDateInfo(user.getUnitid(), attendanceTime);
			if(dateinfo==null || (dateinfo != null && StringUtils.isNotBlank(dateinfo.getIsfeast()) && "Y".equals(dateinfo.getIsfeast()))){
				isfeast = true;
			}
			
			//打卡记录
			List<OfficeAttendanceInfo> infolist = officeAttendanceInfoService.getListByUserIdDate(userId, attendanceTime);
			
			JSONObject json = new JSONObject();
			json.put("dataType", infoType);
			json.put("currTime", new Date().getTime());//当前服务器时间
			json.put("attendanceDate", attendanceTime.getTime());//考勤日期
			json.put("weekdayStr", WeekdayEnum.getName(weekday));//周几
			
			json.put("isHoliday", isfeast);//是否是休息日
			
			//请假等记录
			Map<Date, JSONArray> dayOtherMap = officeAttendanceInfoService.getOtherInfo(user.getUnitid(), userId, attendanceTime, attendanceTime);
			JSONArray remarkJsons = new JSONArray();
			boolean isLeave = false;
			if(dayOtherMap!=null && dayOtherMap.size()>0){
				remarkJsons = dayOtherMap.get(attendanceTime);
				if(remarkJsons!=null && remarkJsons.size()>0)
					isLeave = true;
			}
			json.put("remarkJsons", remarkJsons);
			
			calendar.setTime(startClockTime);
			calendar.add(Calendar.DATE, 1);
			json.put("startClockTime", calendar.getTime().getTime());//上班可打卡时间(下一天)
			json.put("startTime", startTime.getTime());//上班时间
			json.put("lateTime", lateTime.getTime());//上班打卡弹性时间
			json.put("pmTime", pmTime.getTime());//下午上班时间
			json.put("earlyTime", earlyTime.getTime());//下班弹性时间  不算早退
			json.put("endTime", endTime.getTime());//下班时间
			
			if(isfeast){
				json.put("startTimeStr", "");//上班时间(9:00)
				json.put("endTimeStr", "");//下班时间(18:00)
			}else{
				json.put("startTimeStr", aset.getStartTime());//上班时间(9:00)
				json.put("endTimeStr", aset.getEndTime());//下班时间(18:00)
			}
			
			
			//办公地点
			JSONArray places = new JSONArray();
			for(OfficeAttendancePlace ent : placelist){
				JSONObject j = new JSONObject();
				j.put("name", ent.getName());//办公地点名称
				j.put("longitude", ent.getLongitude());//经度
				j.put("latitude", ent.getLatitude());//纬度
				j.put("range", ent.getRange());//有效范围
				places.add(j);
			}
			json.put("places", places);
			
			//打卡记录
			boolean isAmLog = false;//是否有上班记录 没有需要补卡提示
			boolean isApplyData = false;//缺卡或者是审批通过的数据 需要查询补卡数据
			JSONArray infos = new JSONArray();
			for(OfficeAttendanceInfo ent : infolist){
				if(AttendanceConstants.ATTENDANCE_TYPE_AM.equals(ent.getType())){//有上午记录则为true
					isAmLog = true;
				}
				if(AttendanceConstants.ATTENDANCE_LOG_TYPE_1.equals(ent.getLogType())){
					isApplyData = true;
				}
			}
			Map<String, OfficeAttendanceColckApply> map98 = null;
			if(!isAmLog || isApplyData){
				//查询补卡记录
				map98 = officeAttendanceColckApplyService.getMapByDate(userId, attendanceTime, attendanceTime);
			}
			
			//没有上班数据  先补缺卡数据
			if(!isAmLog && !new Date().before(pmTime)){
				//上班缺卡  自动生成缺卡数据
				String key = df.format(attendanceTime)+"_"+AttendanceConstants.ATTENDANCE_TYPE_AM;
				JSONObject j = new JSONObject();
				j.put("type", AttendanceConstants.ATTENDANCE_TYPE_AM);//上班打卡or下班打卡
				j.put("clockTime", 0);//打卡时间
				j.put("clockState", AttendanceConstants.ATTENDANCE_CLOCK_STATE_98);//状态
//				j.put("clockStateName", isLeave?"":ClockTypeEnum.CLOCK_TYPE_98.getName());//状态
				j.put("clockStateName", ClockTypeEnum.CLOCK_TYPE_98.getName());//状态
				j.put("clockPlace", "");//打卡地点
				j.put("attendanceTime", aset.getStartTime());//当时的考勤时间
				
				if(map98!=null && map98.containsKey(key)){
					OfficeAttendanceColckApply apply = map98.get(key);
					if(apply!=null){
						JSONObject applyInfo = createApplyJson(apply.getId(),String.valueOf(apply.getApplyStatus()),apply.getApplyStatusName());
						j.put("applyInfo", applyInfo);
					}
				}
				if(isfeast && !map98.containsKey(key)){//如果是休息日  且没有补卡申请， 则不需要有补卡记录
					
				}else{
					infos.add(j);
				}
			}
			
			for(OfficeAttendanceInfo ent : infolist){
				JSONObject j = new JSONObject();
				j.put("type", ent.getType());//上班打卡or下班打卡
				j.put("clockTime", ent.getClockTime().getTime());//打卡时间
				j.put("clockState", ent.getClockState());//状态 0:正常，1：迟到，2：早退，3：外勤，98：缺卡
				j.put("clockStateName", ClockTypeEnum.getName(ent.getClockState()));//状态
				j.put("clockPlace", ent.getClockPlace());//打卡地点
				//冗余字段  上下班时间有可能会有变化
				j.put("attendanceTime", ent.getAttendanceTime());//当时的考勤时间
				j.put("remark", ent.getRemark());//备注
				
				//如果是审批通过
				if(AttendanceConstants.ATTENDANCE_LOG_TYPE_1.equals(ent.getLogType())){
					String key = df.format(ent.getAttenceDate())+"_"+ent.getType();
					if(map98!=null && map98.containsKey(key)){
						OfficeAttendanceColckApply apply = map98.get(key);
						if(apply!=null){
							JSONObject applyInfo = createApplyJson(apply.getId(),String.valueOf(apply.getApplyStatus()),apply.getApplyStatusName());
							j.put("applyInfo", applyInfo);
						}
					}
				}
				
				infos.add(j);
			}
			
			
			json.put("clockInfos", infos);
			
			//上次忘打卡提示 
			if(!isAmLog){//没有上班打卡记录 则需要提示上次忘打卡情况
				if(new Date().before(pmTime)){ 
					//早上未打卡并且时间在下午上班之前
					//查询上一个考勤日期下班打卡情况 如果没有则需要补卡提示
					Calendar c = Calendar.getInstance();
					c.setTime(attendanceTime);
					c.add(Calendar.DATE, -1);
					//判断是否是休息日
					OfficeAttendanceInfo item = officeAttendanceInfoService.getItemByAdateType(userId, c.getTime(), AttendanceConstants.ATTENDANCE_TYPE_PM);
					if(item == null){//没有昨天下班打卡记录
						DateInfo dateinfo1 = dateInfoService.getDateInfo(user.getUnitid(), c.getTime());
						if(dateinfo1!=null && "Y".equals(dateinfo1.getIsfeast())){//休息日则不需要缺卡提示
							
						}else{
							//查询是否有补卡申请记录
							OfficeAttendanceColckApply apply = officeAttendanceColckApplyService.getObjByDateType(userId, df.format(c.getTime()), AttendanceConstants.ATTENDANCE_TYPE_PM);
							if(apply==null){
									//如果不是休息日且没有申请记录
									//查询是否有请假等记录
									Map<Date, JSONArray> dayOtherMap1 = officeAttendanceInfoService.getOtherInfo(user.getUnitid(), userId, c.getTime(), c.getTime());
									JSONArray remarkJsons1 = new JSONArray();
									boolean isLeave1 = false;
									if(dayOtherMap1!=null && dayOtherMap1.size()>0){
										remarkJsons1 = dayOtherMap1.get(attendanceTime);
										if(remarkJsons1!=null && remarkJsons1.size()>0)
											isLeave1 = true;
									}
									if(!isLeave1){
										JSONObject noClockjson = new JSONObject();
										noClockjson.put("attendanceDate", c.getTime().getTime());//考勤日期
										noClockjson.put("type", AttendanceConstants.ATTENDANCE_TYPE_PM);//上班打卡or下班打卡
										noClockjson.put("weekdayStr", WeekdayEnum.getName(c.get(Calendar.DAY_OF_WEEK)));//周几
										noClockjson.put("timeStr", aset.getEndTime());//上班or下班时间
										json.put("noClockjson", noClockjson);
									}
							}
						}
					}
				}else{
					//上班缺卡提示
					//判断今天是否是休息日
					//查询是否有补卡申请记录
					String key = df.format(attendanceTime.getTime())+"_"+AttendanceConstants.ATTENDANCE_TYPE_AM;
					if(map98!=null && !map98.containsKey(key) && !isfeast && !isLeave){
						//如果没有申请记录且不是休息日 也没有请假
						JSONObject noClockjson = new JSONObject();
						noClockjson.put("attendanceDate", attendanceTime.getTime());//考勤日期
						noClockjson.put("type", AttendanceConstants.ATTENDANCE_TYPE_AM);//上班打卡or下班打卡
						noClockjson.put("weekdayStr", WeekdayEnum.getName(weekday));//周几
						noClockjson.put("timeStr", aset.getStartTime());//上班or下班时间
						json.put("noClockjson", noClockjson);
					}
				}
			}
			
			return RemoteCallUtils.returnResultJson(json);
		} catch (Exception e) {
			e.printStackTrace();
			return RemoteCallUtils.returnResultError("程序异常");
		}
		
	}
	
	public String attendanceDetail(OfficeAttendanceGroupUser groupuser, OfficeAttendanceGroup group,OfficeAttendanceSet aset, User user, Date attendanceTime, String userId, int dataType) {
		try {
			
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(attendanceTime);
			int weekday = calendar.get(Calendar.DAY_OF_WEEK);
			
			boolean isfeast = false;
			DateInfo dateinfo = dateInfoService.getDateInfo(user.getUnitid(), attendanceTime);
			if(dateinfo==null || (dateinfo != null && StringUtils.isNotBlank(dateinfo.getIsfeast()) && "Y".equals(dateinfo.getIsfeast()))){
				isfeast = true;
			}
			//打卡记录
			List<OfficeAttendanceInfo> infolist = officeAttendanceInfoService.getListByUserIdDate(userId, attendanceTime);
			
			
			JSONObject json = new JSONObject();
			json.put("dataType", dataType);
//			json.put("currTime", new Date().getTime());//当前服务器时间
			json.put("attendanceDate", attendanceTime.getTime());//考勤日期
			json.put("weekdayStr", WeekdayEnum.getName(weekday));//周几
			
			json.put("isHoliday", isfeast);//是否是休息日
			//请假等记录
			Map<Date, JSONArray> dayOtherMap = officeAttendanceInfoService.getOtherInfo(user.getUnitid(), userId, attendanceTime, attendanceTime);
			JSONArray remarkJsons = new JSONArray();
			boolean isLeave = false;
			if(dayOtherMap!=null && dayOtherMap.size()>0){
				remarkJsons = dayOtherMap.get(attendanceTime);
				if(remarkJsons!=null && remarkJsons.size()>0)
					isLeave = true;
			}
			json.put("remarkJsons", remarkJsons);
			
			json.put("startTimeStr", aset.getStartTime());//上班时间(9:00)
			json.put("endTimeStr", aset.getEndTime());//下班时间(18:00)
			
			Map<String, OfficeAttendanceColckApply> map98 = officeAttendanceColckApplyService.getMapByDate(userId, attendanceTime, attendanceTime);
			if(dataType != INFO_TYPE_T){
				//打卡记录
				JSONArray infos = getDayClockAllLog(aset, isfeast, infolist, true, false, attendanceTime, null, null, map98, isLeave);
				json.put("clockInfos", infos);
			}else{
				json.put("clockInfos", new JSONArray());
			}
			
			return RemoteCallUtils.returnResultJson(json);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return RemoteCallUtils.returnResultError("程序异常");
		}
	}

	/**
	 * 
	 * @param aset
	 * @param isfeast
	 * @param infolist
	 * @param isFlag是否自动生成缺卡或者旷工记录
	 * @param isMonth 是否考勤月历
	 * @param map98 申请记录
	 * @return
	 */
	private JSONArray getDayClockAllLog(OfficeAttendanceSet aset,
			boolean isfeast, List<OfficeAttendanceInfo> infolist, boolean isFlag, boolean isMonth, Date attenceDate
			, Date startClockTime, Date pmTime, Map<String, OfficeAttendanceColckApply> map98, boolean isLeave) {
		JSONArray infos = new JSONArray();
		String attDateStr = DateUtils.date2String(attenceDate, "yyyy-MM-dd");
		String curDate = "";
		if(startClockTime!=null){
			curDate = DateUtils.date2String(startClockTime, "yyyy-MM-dd");
		}
		
		//迟到开始时间计算
		Calendar calendar = Calendar.getInstance();
		Date startTime =DateUtils.string2Date(attDateStr + " " + aset.getStartTimeStr(), "yyyy-MM-dd HH:mm:ss");//上班时间
		calendar.setTime(startTime);
		calendar.add(Calendar.MINUTE, StringUtils.isNotBlank(aset.getElasticRange())?Integer.valueOf(aset.getElasticRange()):0);
		Date lateTime = calendar.getTime();
		
		
		if(infolist!=null && infolist.size()>=2){//如果数据正常
			for(OfficeAttendanceInfo ent : infolist){
				JSONObject j = new JSONObject();
				j.put("type", ent.getType());//上班打卡or下班打卡
				j.put("clockTime", ent.getClockTime().getTime());//打卡时间
				j.put("clockState", ent.getClockState());//状态
				j.put("clockPlace", ent.getClockPlace());//打卡地点
				j.put("clockStateName", ClockTypeEnum.getName(ent.getClockState()));//状态
				
				if(isMonth){
					//当天的考勤时间点long型
					j.put("attendanceTime", lateTime.getTime());//当时的考勤时间
				}else{
					//冗余字段  上下班时间有可能会有变化
					j.put("attendanceTime", ent.getAttendanceTime());//当时的考勤时间
				}
				
				//如果是审批通过
				if(AttendanceConstants.ATTENDANCE_LOG_TYPE_1.equals(ent.getLogType())){
					String key = attDateStr+"_"+ent.getType();
					if(map98.containsKey(key)){
						OfficeAttendanceColckApply apply = map98.get(key);
						if(apply!=null){
							JSONObject applyInfo = createApplyJson(apply.getId(),String.valueOf(apply.getApplyStatus()),apply.getApplyStatusName());
							j.put("applyInfo", applyInfo);
						}
					}
				}
				
				infos.add(j);
			}
		}else{//(缺卡)休息日如果有补卡申请记录 则也会生成缺卡数据
			boolean islockAM = true;//是否缺上班卡
			boolean islockPM = true;//是否缺下班卡
			
			if(StringUtils.isNotBlank(curDate) && attDateStr.equals(curDate)){
				islockPM = false;
				//如果是当天 而且时间在下午上班之前
				if(new Date().before(pmTime)){
					islockAM = false;
				}
			}
			
			JSONObject oneJson = new JSONObject();
			if(infolist == null || infolist.size()==0){
				oneJson = null;
			}else{
				OfficeAttendanceInfo ent = infolist.get(0);
				if(AttendanceConstants.ATTENDANCE_TYPE_AM.equals(ent.getType())){
					islockAM = false;
				}else{
					islockPM = false;
				}
				oneJson.put("type", ent.getType());//上班打卡or下班打卡
				oneJson.put("clockTime", ent.getClockTime().getTime());//打卡时间
				oneJson.put("clockState", ent.getClockState());//状态
				oneJson.put("clockStateName", ClockTypeEnum.getName(ent.getClockState()));//状态
				oneJson.put("clockPlace", ent.getClockPlace());//打卡地点
				
				if(isMonth){
					//当天的考勤时间点long型
					oneJson.put("attendanceTime", lateTime.getTime());//当时的考勤时间
				}else{
					//冗余字段  上下班时间有可能会有变化
					oneJson.put("attendanceTime", ent.getAttendanceTime());//当时的考勤时间
				}
				
				//如果是审批通过
				if(AttendanceConstants.ATTENDANCE_LOG_TYPE_1.equals(ent.getLogType())){
					String key = attDateStr+"_"+ent.getType();
					if(map98.containsKey(key)){
						OfficeAttendanceColckApply apply = map98.get(key);
						if(apply!=null){
							JSONObject applyInfo = createApplyJson(apply.getId(),String.valueOf(apply.getApplyStatus()),apply.getApplyStatusName());
							oneJson.put("applyInfo", applyInfo);
						}
					}
				}
			}
			
			if(!isFlag){
				if(oneJson!=null)
					infos.add(oneJson);
				return infos;
			}
			
			if(!islockAM && !islockPM && oneJson!=null){
				infos.add(oneJson);
				return infos;
			}
			
			if(islockAM){
				String key = attDateStr+"_"+AttendanceConstants.ATTENDANCE_TYPE_AM;
				
				JSONObject j = new JSONObject();
				j.put("type", AttendanceConstants.ATTENDANCE_TYPE_AM);//上班打卡or下班打卡
				j.put("clockTime", 0);//打卡时间
				j.put("clockState", AttendanceConstants.ATTENDANCE_CLOCK_STATE_98);//状态
//				j.put("clockStateName", isLeave?"":ClockTypeEnum.CLOCK_TYPE_98.getName());//状态
				j.put("clockStateName", ClockTypeEnum.CLOCK_TYPE_98.getName());//状态
				j.put("clockPlace", "");//打卡地点
				
				if(map98.containsKey(key)){
					OfficeAttendanceColckApply apply = map98.get(key);
					if(apply!=null){
						JSONObject applyInfo = createApplyJson(apply.getId(),String.valueOf(apply.getApplyStatus()),apply.getApplyStatusName());
						j.put("applyInfo", applyInfo);
					}
				}
				
				if(isMonth){
					//当天的考勤时间点long型
					j.put("attendanceTime", lateTime.getTime());//当时的考勤时间
				}else{
					//冗余字段  上下班时间有可能会有变化
					j.put("attendanceTime", aset.getStartTime());//当时的考勤时间
				}
				if(isfeast && !map98.containsKey(key)){
					
				}else{
					infos.add(j);
				}
				
				if(oneJson!=null && !islockPM){
					infos.add(oneJson);
				}
			}
			if(islockPM){
				String key = attDateStr+"_"+AttendanceConstants.ATTENDANCE_TYPE_PM;
				if(oneJson!=null && !islockAM){
					infos.add(oneJson);
				}
				
				JSONObject j = new JSONObject();
				j.put("type", AttendanceConstants.ATTENDANCE_TYPE_PM);//上班打卡or下班打卡
				j.put("clockTime", 0);//打卡时间
				j.put("clockState", AttendanceConstants.ATTENDANCE_CLOCK_STATE_98);//状态
//				j.put("clockStateName", isLeave?"":ClockTypeEnum.getName(AttendanceConstants.ATTENDANCE_CLOCK_STATE_98));//状态
				j.put("clockStateName", ClockTypeEnum.getName(AttendanceConstants.ATTENDANCE_CLOCK_STATE_98));//状态
				j.put("clockPlace", "");//打卡地点
				
				if(isMonth){
					//当天的考勤时间点long型
					j.put("attendanceTime", lateTime.getTime());//当时的考勤时间
				}else{
					//冗余字段  上下班时间有可能会有变化
					j.put("attendanceTime", aset.getEndTime());//当时的考勤时间
				}
				if(map98.containsKey(key)){
					OfficeAttendanceColckApply apply = map98.get(key);
					if(apply!=null){
						JSONObject applyInfo = createApplyJson(apply.getId(),String.valueOf(apply.getApplyStatus()),apply.getApplyStatusName());
						j.put("applyInfo", applyInfo);
					}
				}
				
				if(isfeast && !map98.containsKey(key)){
					
				}else{
					infos.add(j);
				}
			}
		}
		return infos;
	}

	/**
	 * 打卡提交
	 */
	@Override
	public String attendanceSubmit(String remoteParam) {
		try {
			JSONObject json = RemoteCallUtils.getParamJson(remoteParam);
			String userId = json.containsKey("syncUserId")?json.getString("syncUserId"):"";//数字校园userId
			Long attendanceDate = json.containsKey("attendanceDate")?json.getLong("attendanceDate"):0;//考勤日期
			String type = json.containsKey("type")?json.getString("type"):"";//上班or下班打卡
			String clockState = json.containsKey("clockState")?json.getString("clockState"):"";//打卡状态
			Long clockTime = json.containsKey("clockTime")?json.getLong("clockTime"):0;//打卡时间
			String attendanceTime = json.containsKey("attendanceTime")?json.getString("attendanceTime"):"";//当时的考勤时间（如9：00或者18：00）
			String clockPlace = json.containsKey("clockPlace")?json.getString("clockPlace"):"";//打卡地点
			String longitude = json.containsKey("longitude")?json.getString("longitude"):"";//经度 
			String latitude = json.containsKey("latitude")?json.getString("latitude"):"";//纬度
			boolean isHoliday = json.containsKey("isHoliday")?json.getBoolean("isHoliday"):false;//是否节假日
			String remark = json.containsKey("remark")?json.getString("remark"):"";//备注
			
			//判断非空
			if(StringUtils.isBlank(userId)){
				return RemoteCallUtils.returnResultError("程序异常,userId参数丢失");
			}
			if(attendanceDate==0){
				return RemoteCallUtils.returnResultError("程序异常,attendanceDate参数丢失");
			}
			if(StringUtils.isBlank(type)){
				return RemoteCallUtils.returnResultError("程序异常,type参数丢失");
			}
			if(StringUtils.isBlank(clockState)){
				return RemoteCallUtils.returnResultError("程序异常,clockState参数丢失");
			}
			if(clockTime==0){
				return RemoteCallUtils.returnResultError("程序异常,clockTime参数丢失");
			}
			if(!isHoliday && StringUtils.isBlank(attendanceTime)){
				return RemoteCallUtils.returnResultError("程序异常,attendanceTime参数丢失");
			}
			if(StringUtils.isBlank(clockPlace)){
				return RemoteCallUtils.returnResultError("程序异常,clockPlace参数丢失");
			}
			if(StringUtils.isBlank(longitude)){
				return RemoteCallUtils.returnResultError("程序异常,longitude参数丢失");
			}
			if(StringUtils.isBlank(latitude)){
				return RemoteCallUtils.returnResultError("程序异常,latitude参数丢失");
			}
			
			//组织数据
			Date _attendanceDate = DateUtils.string2Date(df.format(new Date(attendanceDate)), "yyyy-MM-dd");
			Date _clockTime = new Date(clockTime);
			
			OfficeAttendanceInfo item = null;
			if(!isHoliday){//工作日
				item = officeAttendanceInfoService.getItemByAdateType(userId, _attendanceDate, type);
			}
			if(item == null){
				item = new OfficeAttendanceInfo();
				item.setUserId(userId);
				
				User user = userService.getUser(userId);
				if(user!=null)
					item.setUnitId(user.getUnitid());
			}else{
				item.setModifyTime(new Date());
			}
			item.setAttenceDate(_attendanceDate);
			item.setIsHoliday(isHoliday);
			item.setType(type);
			item.setLogType(AttendanceConstants.ATTENDANCE_LOG_TYPE_DEFAULT);
			item.setClockState(clockState);
			item.setClockTime(_clockTime);
			item.setAttendanceTime(attendanceTime);
			item.setClockPlace(clockPlace);
			item.setLongitude(longitude);
			item.setLatitude(latitude);
			item.setRemark(remark);
			
			//需要同步考虑记录表
			if(StringUtils.isNotBlank(item.getId())){
				officeAttendanceInfoService.update(item);
			}else{
				officeAttendanceInfoService.save(item);
			}
			
			JSONObject rj = new JSONObject();
			rj.put("type", item.getType());//上班打卡or下班打卡
			rj.put("clockTime", item.getClockTime().getTime());//打卡时间
			rj.put("clockState", item.getClockState());//状态
			rj.put("clockPlace", item.getClockPlace());//打卡地点
			rj.put("clockStateName", ClockTypeEnum.getName(item.getClockState()));//状态
			rj.put("attendanceTime", item.getAttendanceTime());//当时的考勤时间
			rj.put("remark", item.getRemark());
			
			return RemoteCallUtils.returnResultJsonMsg(rj, "操作成功");
		} catch (Exception e) {
			e.printStackTrace();
			return RemoteCallUtils.returnResultError("程序异常");
		}
		
	}

	/**
	 * 补卡申请
	 */
	@Override
	public String attendanceApply(String remoteParam) {
		try {
			JSONObject json = RemoteCallUtils.getParamJson(remoteParam);
			String userId = json.containsKey("syncUserId")?json.getString("syncUserId"):"";//数字校园userId
			Long attendanceDate = json.containsKey("attendanceDate")?json.getLong("attendanceDate"):0;//考勤日期
			String type = json.containsKey("type")?json.getString("type"):"";//上班or下班打卡
			String remark = json.containsKey("remark")?json.getString("remark"):"";//补卡原因
//			Long clockTime = json.containsKey("clockTime")?json.getLong("clockTime"):0;//休息日 补卡时间
			
			//判断非空
			if(StringUtils.isBlank(userId)){
				return RemoteCallUtils.returnResultError("程序异常,userId参数丢失");
			}
			if(attendanceDate==0){
				return RemoteCallUtils.returnResultError("程序异常,attendanceDate参数丢失");
			}
			if(StringUtils.isBlank(type)){
				return RemoteCallUtils.returnResultError("程序异常,type参数丢失");
			}
			User user = userService.getUser(userId);
			if(user == null){
				return RemoteCallUtils.returnResultError("程序异常,找不到对应的用户信息");
			}
			
			OfficeAttendanceGroupUser groupuser=officeAttendanceGroupUserService.getItemByUserId(userId);
			
			OfficeAttendanceGroup group = null;
			if(groupuser!=null){
				group = officeAttendanceGroupService.getOfficeAttendanceGroupById(groupuser.getGroupId());
			}
			if(group ==null){
				return RemoteCallUtils.returnResultError("没有所属的考勤组,请联系管理员！");
			}
			String attendanceDateStr = df.format(new Date(attendanceDate));
			
			OfficeAttendanceColckApply apply = officeAttendanceColckApplyService.getObjByDateType(userId, attendanceDateStr, type);
			if(apply!=null){
				if(1 == apply.getApplyStatus()
						|| 2 == apply.getApplyStatus()){//待审批或者通过
					return RemoteCallUtils.returnResultError("该班次已有补卡申请，不需要再次申请。");
				}
			}
//			boolean isfeast = false;
			Date attenceDate = DateUtils.string2Date(attendanceDateStr, "yyyy-MM-dd");
//			DateInfo dateinfo = dateInfoService.getDateInfo(user.getUnitid(), attenceDate);
//			if(dateinfo != null && StringUtils.isNotBlank(dateinfo.getIsfeast()) && "Y".equals(dateinfo.getIsfeast())){
//				isfeast = true;
//			}
			
			OfficeAttendanceColckApply ent = new OfficeAttendanceColckApply();
			
			ent.setUnitId(user.getUnitid());
			ent.setApplyUserId(userId);
			ent.setAttenceDate(attenceDate);
			ent.setType(type);
			ent.setReason(remark);
			List<Flow> flowList = flowManageService.getFinishFlowList(user.getUnitid(), FlowConstant.FLOW_OWNER_UNIT, FlowConstant.OFFICE_ATTENDANCE,FlowConstant.OFFICE_SUBSYSTEM,FlowConstant.OFFICEDOC_FLOW_EASY_LEVEL_1);
			List<Flow> flowList2  = flowManageService.getFinishFlowList(user.getDeptid(), FlowConstant.FLOW_OWNER_STRING, 
					FlowConstant.OFFICE_ATTENDANCE,FlowConstant.OFFICE_SUBSYSTEM,FlowConstant.OFFICEDOC_FLOW_EASY_LEVEL_1);
			if(CollectionUtils.isNotEmpty(flowList2)){
				flowList.addAll(flowList2);
			}
			
			if(CollectionUtils.isNotEmpty(flowList)){ 
				String flowId = flowList.get(0).getFlowId();
				for(Flow flow : flowList){
					if(flow.isDefaultFlow()){
						flowId = flow.getFlowId();
						break;
					}
				}
				ent.setFlowId(flowId);
				
			}else{
				return RemoteCallUtils.returnResultError("程序异常:没有设置审批流程，请联系管理员。");
			}
			
			officeAttendanceColckApplyService.startFlow(ent,userId);
			
			//获取考勤时间设置
			OfficeAttendanceSet aset = officeAttendanceSetService.getOfficeAttendanceSetById(group.getAttSetId());
			
			OfficeAttendanceColckApply applyResult = officeAttendanceColckApplyService.getObjByDateType(userId, attendanceDateStr, type);
			JSONObject rj = new JSONObject();
			rj.put("attendanceDate", attendanceDate);
			rj.put("type", type);
			rj.put("clockTime", 0);//打卡时间
			rj.put("clockState", AttendanceConstants.ATTENDANCE_CLOCK_STATE_98);//状态
			rj.put("clockStateName", ClockTypeEnum.CLOCK_TYPE_98.getName());//状态
			rj.put("clockPlace", "");//打卡地点
			rj.put("attendanceTime", AttendanceConstants.ATTENDANCE_TYPE_AM.equals(type)?aset.getStartTime():aset.getEndTime());//当时的考勤时间
			JSONObject applyInfo = createApplyJson(applyResult.getId(),String.valueOf(applyResult.getApplyStatus()),applyResult.getApplyStatusName());
			rj.put("applyInfo", applyInfo);
			
			return RemoteCallUtils.returnResultJsonMsg(rj, "提交成功,已进入流程中");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return RemoteCallUtils.returnResultError("程序异常"+e.getMessage());
		}
		
	}

	private JSONObject createApplyJson(String id, String state, String stateName) {
		JSONObject applyInfo = new JSONObject();
		applyInfo.put("state", state);
		applyInfo.put("stateName", stateName);
		applyInfo.put("url", RedisUtils.get("EIS.BASE.PATH.V6")+"/common/open/officemobile/appApplyDetail.action?id="+id+"&dataType=99&businessType="+ConvertFlowConstants.OFFICE_TEACHER_ATTENDANCE);
		return applyInfo;
	}

	/**
	 * 考勤统计
	 */
	@Override
	public String attendanceCount(String remoteParam) {
		try {
			JSONObject json = RemoteCallUtils.getParamJson(remoteParam);
			String userId = json.containsKey("syncUserId")?json.getString("syncUserId"):"";//数字校园userId
			String year = json.containsKey("year")?json.getString("year"):"";//年份
			String month = json.containsKey("month")?json.getString("month"):"";//月份
			
			//判断非空
			if(StringUtils.isBlank(userId)){
				return RemoteCallUtils.returnResultError("程序异常,userId参数丢失");
			}
			User user = userService.getUser(userId);
			if(user==null){
				return RemoteCallUtils.returnResultError("程序异常,userId找不到对应的数字校园用户信息");
			}
			OfficeAttendanceGroupUser groupuser = officeAttendanceGroupUserService.getItemByUserId(userId);
			OfficeAttendanceGroup group = null;
			if(groupuser!=null){
				group = officeAttendanceGroupService.getOfficeAttendanceGroupById(groupuser.getGroupId());
			}
			if(group ==null){
				return RemoteCallUtils.returnResultError("没有所属的考勤组,请联系管理员！");
			}
			
			OfficeAttendanceSet aset = officeAttendanceSetService.getOfficeAttendanceSetById(group.getAttSetId());
			
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(DateUtils.string2Date(df.format(new Date()), "yyyy-MM-dd"));
			if(StringUtils.isNotBlank(year) && StringUtils.isNotBlank(month)){
				calendar.set(Integer.valueOf(year), Integer.valueOf(month)-1, 1);
			}else{
				//为空则默认当前年月
				calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), 1);
			}
			Date currDate = calendar.getTime(); 
			
			//判断是否是当前年月
			Date startClockTime = null;
			Date pmTime = null;
			
			Calendar cc = Calendar.getInstance();
			if(cc.get(Calendar.YEAR)==calendar.get(Calendar.YEAR) && cc.get(Calendar.MONTH)==calendar.get(Calendar.MONTH)){
				//如果是当月 则查询到当天的时候要根据时间点判断是否需要自动生成上班的缺卡数据
				//计算当天的上班可打卡时间点
				//上班时间
				//当前时间
				Date startTime =DateUtils.string2Date(DateUtils.date2String(cc.getTime(), "yyyy-MM-dd") + " " + aset.getStartTimeStr(), "yyyy-MM-dd HH:mm:ss");//上班时间
				//当天上班可打卡时间
				cc.setTime(startTime);
				cc.add(Calendar.HOUR, StringUtils.isNotBlank(aset.getStartRange())?-Integer.valueOf(aset.getStartRange()):0);//
				startClockTime = cc.getTime();
				if(new Date().before(startClockTime)){//如果当前时间在可打卡时间之前, 则考勤应该是前一天的时间
					cc.setTime(startClockTime);
					cc.add(Calendar.DATE, -1);
					startClockTime = cc.getTime();
				}
				//下午上班时间
				pmTime =DateUtils.string2Date(DateUtils.date2String(startClockTime, "yyyy-MM-dd") + " " + aset.getPmTimeStr(), "yyyy-MM-dd HH:mm:ss");
			}
			
			//第一天
			int firstNum = calendar.getActualMinimum(Calendar.DAY_OF_MONTH);
			calendar.set(Calendar.DATE, firstNum);
			Date startDate = calendar.getTime();
			//最后一天
			int lastNum = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
			calendar.set(Calendar.DATE, lastNum);
			Date endDate = calendar.getTime();
			
			//获取数据
			List<OfficeAttendanceInfo> list = officeAttendanceInfoService.getListByStartAndEndTime(userId, startDate, endDate);
	
			Map<Date, JSONArray> dayOtherMap = officeAttendanceInfoService.getOtherInfo(user.getUnitid(), userId, startDate, endDate);
			//异常记录(外勤，迟到，早退，缺卡)
			AttendanceCountDto dto = getCountDtoList(user.getUnitid(), aset, list, startDate, endDate, startClockTime, pmTime, dayOtherMap);
			
			JSONObject rj = new JSONObject();
			
			rj.put("groupName", group.getName());
			rj.put("currDate",currDate.getTime());
			//组织数据
			//考勤统计
			JSONArray a1 = new JSONArray();
			Map<String, AttendanceDetailCountDto> mCountMap = dto.getDetailMap();
			for(String key : mCountMap.keySet()){
				AttendanceDetailCountDto item = mCountMap.get(key);
				JSONObject j = new JSONObject();
				j.put("type", item.getType());
				j.put("name", item.getName());
				j.put("numStr", item.getNumStr());
				j.put("percentage", item.getPercentage());//两位小数
				a1.add(j);
			}
			
			rj.put("attendanceCount", a1);//考勤数据统计
			//请假等统计
			JSONArray a2 = a2 = officeAttendanceInfoService.getOtherCountInfo(group.getUnitId(), userId, startDate, endDate);
			rj.put("remarkCount", a2);//考勤数据统计
			
			JSONArray attendanceAbnormalInfos = new JSONArray();
			
			//迟到
			List<AttendanceDto> list1 = dto.getList1();
			JSONArray arr1 = new JSONArray();
			for(AttendanceDto d : list1){
				JSONObject j = new JSONObject();
				j.put("attendanceDate", d.getAttenceDate().getTime());//考勤日期
				j.put("weekdayStr", d.getWeekdayStr());//周几
				j.put("timeStr", d.getTimeStr());//打卡时间字符串
				arr1.add(j);
			}
			JSONObject j1 = new JSONObject();
			j1.put("name", ClockTypeEnum.getName(AttendanceConstants.ATTENDANCE_CLOCK_STATE_1));
			j1.put("type", AttendanceConstants.ATTENDANCE_CLOCK_STATE_1);
			j1.put("infos", arr1);
			attendanceAbnormalInfos.add(j1);
			
			//早退
			List<AttendanceDto> list2 = dto.getList2();
			JSONArray arr2 = new JSONArray();
			for(AttendanceDto d : list2){
				JSONObject j = new JSONObject();
				j.put("attendanceDate", d.getAttenceDate().getTime());//考勤日期
				j.put("weekdayStr", d.getWeekdayStr());//周几
				j.put("timeStr", d.getTimeStr());//打卡时间字符串
				arr2.add(j);
			}
			JSONObject j2 = new JSONObject();
			j2.put("name", ClockTypeEnum.getName(AttendanceConstants.ATTENDANCE_CLOCK_STATE_2));
			j2.put("type", AttendanceConstants.ATTENDANCE_CLOCK_STATE_2);
			j2.put("infos", arr2);
			attendanceAbnormalInfos.add(j2);
			
			//外勤
			List<AttendanceDto> list3 = dto.getList3();
			JSONArray arr3 = new JSONArray();
			for(AttendanceDto d : list3){
				JSONObject j = new JSONObject();
				j.put("attendanceDate", d.getAttenceDate().getTime());//考勤日期
				j.put("weekdayStr", d.getWeekdayStr());//周几
				j.put("timeStr", d.getTimeStr());//打卡时间字符串
				arr3.add(j);
			}
			JSONObject j3 = new JSONObject();
			j3.put("name", ClockTypeEnum.getName(AttendanceConstants.ATTENDANCE_CLOCK_STATE_3));
			j3.put("type", AttendanceConstants.ATTENDANCE_CLOCK_STATE_3);
			j3.put("infos", arr3);
			attendanceAbnormalInfos.add(j3);
			
			//缺卡
			List<AttendanceDto> list4 = dto.getList4();
			JSONArray arr4 = new JSONArray();
			for(AttendanceDto d : list4){
				JSONObject j = new JSONObject();
				j.put("attendanceDate", d.getAttenceDate().getTime());//考勤日期
				j.put("weekdayStr", d.getWeekdayStr());//周几
				j.put("timeStr", d.getTimeStr());//打卡时间字符串
				arr4.add(j);
			}
			JSONObject j4 = new JSONObject();
			j4.put("name", ClockTypeEnum.getName(AttendanceConstants.ATTENDANCE_CLOCK_STATE_98));
			j4.put("type", AttendanceConstants.ATTENDANCE_CLOCK_STATE_98);
			j4.put("infos", arr4);
			attendanceAbnormalInfos.add(j4);
			
			//旷工
			List<AttendanceDto> list5 = dto.getList5();
			JSONArray arr5 = new JSONArray();
			for(AttendanceDto d : list5){
				JSONObject j = new JSONObject();
				j.put("attendanceDate", d.getAttenceDate().getTime());//考勤日期
				j.put("weekdayStr", d.getWeekdayStr());//周几
				j.put("timeStr", d.getTimeStr());//打卡时间字符串
				arr5.add(j);
			}
			JSONObject j5 = new JSONObject();
			j5.put("name", ClockTypeEnum.getName(AttendanceConstants.ATTENDANCE_CLOCK_STATE_99));
			j5.put("type", AttendanceConstants.ATTENDANCE_CLOCK_STATE_99);
			j5.put("infos", arr5);
			attendanceAbnormalInfos.add(j5);
			
			rj.put("attendanceAbnormalInfos", attendanceAbnormalInfos);
			return RemoteCallUtils.returnResultJson(rj);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return RemoteCallUtils.returnResultError("程序异常");
		}
		
	}

	/**
	 * 考勤月历
	 */
	@Override
	public String attendanceMonth(String remoteParam) {
		try {
			JSONObject pjson = RemoteCallUtils.getParamJson(remoteParam);
			Calendar calendar = Calendar.getInstance();
			String curYear = String.valueOf(calendar.get(Calendar.YEAR));
			String curMonth = String.valueOf(calendar.get(Calendar.MONTH)+1);//+1对应真正的月份
			
			
			String userId = pjson.containsKey("syncUserId")?pjson.getString("syncUserId"):"";//数字校园userId
			String year = pjson.containsKey("year")?pjson.getString("year"):"";//年份
			String month = pjson.containsKey("month")?pjson.getString("month"):"";//月份 比日历中的month大1
			if(StringUtils.isBlank(year))
				year = curYear;
			if(StringUtils.isBlank(month))
				month = curMonth;
			
			//获取考勤时间设置
			OfficeAttendanceGroupUser groupuser = officeAttendanceGroupUserService.getItemByUserId(userId);
			OfficeAttendanceGroup group = null;
			if(groupuser!=null){
				group = officeAttendanceGroupService.getOfficeAttendanceGroupById(groupuser.getGroupId());
			}
			if(group ==null){
				return RemoteCallUtils.returnResultError("没有所属的考勤组,请联系管理员！");
			}
			OfficeAttendanceSet aset = officeAttendanceSetService.getOfficeAttendanceSetById(group.getAttSetId());
			
			JSONObject rj = new JSONObject();
			int queryYear = 0;
			int queryMonth = 0;//真正的月份，不是日历中的月份  相差1
			//判断是否是当月
			Date currDate = null;
			boolean isCurMonth = false;
			if(curYear.equals(year) && curMonth.equals(month)){
				isCurMonth = true;
				queryYear = Integer.valueOf(curYear);
				queryMonth = Integer.valueOf(curMonth);
				currDate = DateUtils.string2Date(DateUtils.date2String(calendar.getTime(),"yyyy-MM-dd"), "yyyy-MM-dd");
				calendar.setTime(currDate);
				rj.put("currDate", currDate.getTime());//当前日期（或每个月1号）
			}else{
				calendar.set(Integer.valueOf(year), Integer.valueOf(month)-1, 1);//当月1号
				queryYear = calendar.get(Calendar.YEAR);
				queryMonth = calendar.get(Calendar.MONTH)+1;
				currDate = DateUtils.string2Date(DateUtils.date2String(calendar.getTime(),"yyyy-MM-dd"), "yyyy-MM-dd");
				rj.put("currDate",currDate.getTime());
			}
			
			//如果是当月 则查询到当天的时候要根据时间点判断是否需要自动生成上班的缺卡数据
			Date startClockTime = null;
			Date pmTime = null;
			if(isCurMonth){
				//计算当天的上班可打卡时间点
				//上班时间
				//当前时间
				Date currentTime = new Date();
				Date startTime =DateUtils.string2Date(DateUtils.date2String(currentTime, "yyyy-MM-dd") + " " + aset.getStartTimeStr(), "yyyy-MM-dd HH:mm:ss");//上班时间
				//当天上班可打卡时间
				Calendar cc = Calendar.getInstance();
				cc.setTime(startTime);
				cc.add(Calendar.HOUR, StringUtils.isNotBlank(aset.getStartRange())?-Integer.valueOf(aset.getStartRange()):0);//
				startClockTime = calendar.getTime();
				if(currentTime.before(startClockTime)){//如果当前时间在可打卡时间之前, 则考勤应该是前一天的时间
					cc.setTime(startClockTime);
					cc.add(Calendar.DATE, -1);
					startClockTime = cc.getTime();
				}
				//下午上班时间
				pmTime =DateUtils.string2Date(DateUtils.date2String(startClockTime, "yyyy-MM-dd") + " " + aset.getPmTimeStr(), "yyyy-MM-dd HH:mm:ss");
			}
			
			rj.put("weekdayStr", WeekdayEnum.getName(calendar.get(Calendar.DAY_OF_WEEK)));//周几
			
			
			JSONArray arr = new JSONArray();
			//遍历日期
			Calendar startCalendar = Calendar.getInstance();
			startCalendar.set(queryYear, queryMonth-1, 1);
			Calendar endCalendar = Calendar.getInstance();
//			if(isCurMonth){
//				endCalendar.setTime(currDate);
//				
//			}else{
				endCalendar.set(Calendar.YEAR, queryYear);
				endCalendar.set(Calendar.MONTH, queryMonth-1);
				//一个月的最后一天
				endCalendar.set(Calendar.DATE, endCalendar.getActualMaximum(Calendar.DAY_OF_MONTH));
//			}
			
			//起止日期格式化
			startCalendar.setTime(DateUtils.string2Date(DateUtils.date2String(startCalendar.getTime()), "yyyy-MM-dd"));
			endCalendar.setTime(DateUtils.string2Date(DateUtils.date2String(endCalendar.getTime()), "yyyy-MM-dd"));
			
			Map<String, DateInfo> dateinfoMap = this.getDateInfoMap(group.getUnitId(), DateUtils.date2String(startCalendar.getTime(), "yyyy-MM-dd"), DateUtils.date2String(endCalendar.getTime(), "yyyy-MM-dd"));
			Map<String, List<OfficeAttendanceInfo>> infoMap = officeAttendanceInfoService.getMapByStartAndEndTime(userId, startCalendar.getTime(), endCalendar.getTime());
			//请假等信息
			Map<Date, JSONArray> dayOtherMap = officeAttendanceInfoService.getOtherInfo(group.getUnitId(), userId, startCalendar.getTime(), endCalendar.getTime());
			//遍历
			Date today = DateUtils.string2Date(DateUtils.date2String(new Date(), "yyyy-MM-dd"), "yyyy-MM-dd");
			
			Map<String, OfficeAttendanceColckApply> map98 = officeAttendanceColckApplyService.getMapByDate(userId, startCalendar.getTime(), endCalendar.getTime());
			while(!endCalendar.before(startCalendar)){
				String dateStr = DateUtils.date2String(startCalendar.getTime(), "yyyy-MM-dd");
				boolean isfeast = false;
				if(dateinfoMap.containsKey(dateStr)){
					DateInfo info = dateinfoMap.get(dateStr);
					if(info==null || (info != null && "Y".equals(info.getIsfeast()))){
						isfeast = true;
					}
				}else{
					isfeast = true;
				}
				
				
				JSONObject j = new JSONObject();
				j.put("dayOfMonth", startCalendar.get(Calendar.DAY_OF_MONTH));//几号
				j.put("isHoliday", isfeast);//是否是休息日
				//请假等记录
				JSONArray remarkJsons = new JSONArray();
				boolean isLeave = false;
				if(dayOtherMap!=null && dayOtherMap.size()>0){
					remarkJsons = dayOtherMap.get(startCalendar.getTime());
					if(remarkJsons!=null && remarkJsons.size()>0)
						isLeave = true;
				}
				j.put("remarkJsons", remarkJsons);
				
				//打卡记录
				String key = DateUtils.date2String(startCalendar.getTime(), "yyyy-MM-dd");
				List<OfficeAttendanceInfo> infolist = null;
				if(infoMap.containsKey(key)){
					infolist = infoMap.get(key);
				}else{
					infolist = new ArrayList<OfficeAttendanceInfo>();
				}
				
				boolean isFlag = false;
				if(!startCalendar.getTime().after(today)){
					isFlag = true;
				}
				JSONArray infos = getDayClockAllLog(aset, isfeast, infolist, isFlag, true, startCalendar.getTime(), startClockTime, pmTime, map98,isLeave);
				j.put("clockInfos", infos);
				
				arr.add(j);
				
				startCalendar.add(Calendar.DATE, 1);
			}
			
			rj.put("monthInfos", arr);
			rj.put("currServerDate", new Date().getTime());
			return RemoteCallUtils.returnResultJson(rj);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return RemoteCallUtils.returnResultError("程序异常");
		}
	}
	
	

	private Map<String, DateInfo> getDateInfoMap(String unitId, String beginDate, String endDate){
		List<DateInfo> list = dateInfoService.getDateInfos(unitId, beginDate, endDate);
		Map<String, DateInfo> map = new HashMap<String, DateInfo>();
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		for(DateInfo ent : list){
			map.put(df.format(ent.getDate()), ent);
		}
		return map;
	}
	
	/**
	 * 数据统计（迟到、早退等）
	 * @param list
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	private AttendanceCountDto getCountDtoList(String unitId, OfficeAttendanceSet aset, List<OfficeAttendanceInfo> list, Date startTime, Date endTime,
			Date startClockTime, Date pmTime,Map<Date, JSONArray> dayOtherMap){
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Map<String, List<OfficeAttendanceInfo>> mapAM = new HashMap<String, List<OfficeAttendanceInfo>>();
		Map<String, List<OfficeAttendanceInfo>> mapPM = new HashMap<String, List<OfficeAttendanceInfo>>();
		for(OfficeAttendanceInfo ent : list){
			String dateStr = df.format(ent.getAttenceDate());
			String key = dateStr+"_"+ent.getType();
			if(AttendanceConstants.ATTENDANCE_TYPE_AM.equals(ent.getType())){
				if(mapAM.containsKey(key)){
					mapAM.get(key).add(ent);
				}else{
					List<OfficeAttendanceInfo> l = new ArrayList<OfficeAttendanceInfo>();
					l.add(ent);
					mapAM.put(key, l);
				}
			}else if(AttendanceConstants.ATTENDANCE_TYPE_PM.equals(ent.getType())){
				if(mapPM.containsKey(key)){
					mapPM.get(key).add(ent);
				}else{
					List<OfficeAttendanceInfo> l = new ArrayList<OfficeAttendanceInfo>();
					l.add(ent);
					mapPM.put(key, l);
				}
			}
		}
		
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(startTime);
		
		Map<String, DateInfo> dateinfoMap = this.getDateInfoMap(unitId, df.format(startTime), df.format(endTime));
		AttendanceCountDto dto = new AttendanceCountDto();
		
		//遍历
		calendar.setTime(DateUtils.string2Date(DateUtils.date2String(calendar.getTime(), "yyyy-MM-dd"), "yyyy-MM-dd"));
		endTime = DateUtils.string2Date(DateUtils.date2String(endTime, "yyyy-MM-dd"), "yyyy-MM-dd");
		Date today = DateUtils.string2Date(DateUtils.date2String(new Date(), "yyyy-MM-dd"), "yyyy-MM-dd");
		
		while(!endTime.before(calendar.getTime())){
			String dateStr = df.format(calendar.getTime());
			boolean isHoliday = false;
			if(dateinfoMap.containsKey(dateStr)){
				DateInfo info = dateinfoMap.get(dateStr);
				if(info==null || (info != null && "Y".equals(info.getIsfeast()))){
					isHoliday = true;
				}else{
					isHoliday = false;
				}
			}else{
				//没有数据 也是节假日
				isHoliday = true;
			}
			
			boolean isFlag = false;//是否自动生成缺卡记录
			if(!calendar.getTime().after(today)){
				isFlag = true;
			}
			boolean isLeave = false;
			if(dayOtherMap!=null && dayOtherMap.size()>0){
				JSONArray remarkJsons = dayOtherMap.get(calendar.getTime());
				if(remarkJsons!=null && remarkJsons.size()>0)
					isLeave = true;
			}
			getDayData(aset, dto, isHoliday, dateStr, mapAM, mapPM, isFlag, calendar,startClockTime, pmTime, isLeave);
			
			calendar.add(Calendar.DATE, 1);
		}
		
		Map<String, AttendanceDetailCountDto> dCountMap = dto.getDetailMap();//linkedhashmap
		
		calendar.setTime(startTime);
		int mCount = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);//月总天数
		int mDCount = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)*2;//月总次数
		for(Map.Entry<String, AttendanceDetailCountDto> entry : dCountMap.entrySet()){
			String key = entry.getKey();
			AttendanceDetailCountDto item = entry.getValue();
			if(AttendanceConstants.ATTENDANCE_COUNT_TYPE_1.equals(key)){
				item.setNumStr(item.getNum()+"天");
				item.setPercentage(formatDouble2((double)item.getNum()/mCount));
			}else if(AttendanceConstants.ATTENDANCE_COUNT_TYPE_2.equals(key)){
				item.setNumStr(item.getNum()+"天");
				item.setPercentage(formatDouble2((double)item.getNum()/mCount));
			}else if(AttendanceConstants.ATTENDANCE_COUNT_TYPE_3.equals(key)){
				item.setNumStr(item.getNum()+"次");
				item.setPercentage(formatDouble2((double)item.getNum()/mDCount));
			}else if(AttendanceConstants.ATTENDANCE_COUNT_TYPE_4.equals(key)){
				item.setNumStr(item.getNum()+"次");
				item.setPercentage(formatDouble2((double)item.getNum()/mDCount));
			}else if(AttendanceConstants.ATTENDANCE_COUNT_TYPE_5.equals(key)){
				item.setNumStr(item.getNum()+"次");
				item.setPercentage(formatDouble2((double)item.getNum()/mDCount));
			}else if(AttendanceConstants.ATTENDANCE_COUNT_TYPE_6.equals(key)){
				item.setNumStr(item.getNum()+"次");
				item.setPercentage(formatDouble2((double)item.getNum()/mDCount));
			}else if(AttendanceConstants.ATTENDANCE_COUNT_TYPE_7.equals(key)){
				item.setNumStr(item.getNum()+"天");
				item.setPercentage(formatDouble2((double)item.getNum()/mCount));
			}else if(AttendanceConstants.ATTENDANCE_COUNT_TYPE_8.equals(key)){
				item.setNumStr(item.getNum()+"天");
				item.setPercentage(formatDouble2((double)item.getNum()/mCount));
			}
		}
		
		return dto;
	}
	
	/**
	 * 获取某一天的考勤异常数据（缺卡自动生成缺卡数据）
	 * @param dto
	 * @param dateStr
	 * @param mapAM
	 * @param mapPM
	 */
	private void getDayData(OfficeAttendanceSet aset,AttendanceCountDto dto, boolean isHoliday, String dateStr, Map<String, List<OfficeAttendanceInfo>> mapAM, 
			Map<String, List<OfficeAttendanceInfo>> mapPM, boolean isFlag, Calendar calendar,
			Date startClockTime, Date pmTime, boolean isLeave){
		
		String curDate = "";
		Date currTime = new Date();
		if(startClockTime!=null){
			curDate = DateUtils.date2String(startClockTime, "yyyy-MM-dd");
		}
		
		boolean isCurDay = false;
		if(StringUtils.isNotBlank(curDate) && dateStr.equals(curDate)){
			isCurDay = true;
		}
		
		//+1
		String amKey = dateStr+"_"+AttendanceConstants.ATTENDANCE_TYPE_AM;
		String pmKey = dateStr+"_"+AttendanceConstants.ATTENDANCE_TYPE_PM;
		Map<String, AttendanceDetailCountDto> map = dto.getDetailMap();
		
		AttendanceDetailCountDto dto1 = map.get(AttendanceConstants.ATTENDANCE_COUNT_TYPE_1);//"出勤天数"
		AttendanceDetailCountDto dto2 = map.get(AttendanceConstants.ATTENDANCE_COUNT_TYPE_2);//正常打卡
		AttendanceDetailCountDto dto5 = map.get(AttendanceConstants.ATTENDANCE_COUNT_TYPE_5);//缺卡
		AttendanceDetailCountDto dto7 = map.get(AttendanceConstants.ATTENDANCE_COUNT_TYPE_7);//旷工
		AttendanceDetailCountDto dto8 = map.get(AttendanceConstants.ATTENDANCE_COUNT_TYPE_8);//休息
		
		if(isHoliday){
			dto8.setNum(dto8.getNum()+1);
		}
		
		if(mapAM.containsKey(amKey) && mapPM.containsKey(pmKey)){
			dto1.setNum(dto1.getNum()+1);
			dto2.setNum(dto2.getNum()+1);
			
			List<OfficeAttendanceInfo> l = mapAM.get(amKey);
			//工作日上or下班只有一条数据
			if(l!=null && l.size()>0){
				OfficeAttendanceInfo ent = l.get(0);
				getAttDto(dto, ent);
			}
			List<OfficeAttendanceInfo> l2 = mapPM.get(pmKey);
			if(l2!=null && l2.size()>0){
				OfficeAttendanceInfo ent = l2.get(0);
				getAttDto(dto, ent);
			}
		}else if(mapAM.containsKey(amKey)){//缺卡
			dto1.setNum(dto1.getNum()+1);//出勤
			
			List<OfficeAttendanceInfo> l = mapAM.get(amKey);
			if(l!=null && l.size()>0){
				OfficeAttendanceInfo ent = l.get(0);
				getAttDto(dto, ent);
			}
			if(isFlag && !isHoliday && !isLeave){//缺下班卡
				if(!isCurDay){//当天下班不算缺卡
					dto5.setNum(dto5.getNum()+1);//缺卡
					getClockDataType4(aset, dto, dateStr, AttendanceConstants.ATTENDANCE_TYPE_PM);
				}
			}
		}else if(mapPM.containsKey(pmKey)){//缺卡
			dto1.setNum(dto1.getNum()+1);//出勤
			
			List<OfficeAttendanceInfo> l2 = mapPM.get(pmKey);
			if(isFlag && !isHoliday && !isLeave){
//			if(isFlag && !isHoliday){
				if(!(isCurDay && currTime.before(pmTime))){//当天且时间再下午上班时间之前 也不算缺卡
					dto5.setNum(dto5.getNum()+1);//缺卡
					getClockDataType4(aset, dto, dateStr, AttendanceConstants.ATTENDANCE_TYPE_AM);
				}
			}
			if(l2!=null && l2.size()>0){
				OfficeAttendanceInfo ent = l2.get(0);
				getAttDto(dto, ent);	
			}
		}else{//旷工
//			if(isFlag && !isHoliday && isCurDay && currTime.after(pmTime) && !isLeave){//上班缺卡
			if(isFlag && !isHoliday && !isLeave){
				if(isCurDay && currTime.after(pmTime)){
					dto5.setNum(dto5.getNum()+1);//缺卡
					getClockDataType4(aset, dto, dateStr, AttendanceConstants.ATTENDANCE_TYPE_AM);
				}else if(isCurDay && currTime.before(pmTime)){//不算旷工
					
				}else{
					dto7.setNum(dto7.getNum()+1);//旷工
					getClockDataType5(dto, dateStr, "");
				}
			}
		}
	}

	/**
	 * 生成缺卡数据
	 * @param dto
	 * @param dateStr
	 * @param type
	 */
	private void getClockDataType4(OfficeAttendanceSet aset, AttendanceCountDto dto, String dateStr,
			String type) {
		String str = "";
		if(AttendanceConstants.ATTENDANCE_TYPE_AM.equals(type)){
			str = aset.getStartTime();
		}else{
			str = aset.getEndTime();
		}
		AttendanceDto ad = new AttendanceDto();
		ad.setAttenceDate(DateUtils.string2Date(dateStr, "yyyy-MM-dd"));
		ad.setTimeStr(str);
		ad.setType(type);
		Calendar mc = Calendar.getInstance();
		mc.setTime(ad.getAttenceDate());
		ad.setWeekdayStr(WeekdayEnum.getName(mc.get(Calendar.DAY_OF_WEEK)));
		
		dto.getList4().add(ad);
	}
	
	/**
	 * 生成旷工数据
	 * @param dto
	 * @param dateStr
	 * @param type
	 */
	private void getClockDataType5(AttendanceCountDto dto, String dateStr,
			String type) {
		AttendanceDto ad = new AttendanceDto();
		ad.setAttenceDate(DateUtils.string2Date(dateStr, "yyyy-MM-dd"));
		ad.setTimeStr("");
		ad.setType("");
		Calendar mc = Calendar.getInstance();
		mc.setTime(ad.getAttenceDate());
		ad.setWeekdayStr(WeekdayEnum.getName(mc.get(Calendar.DAY_OF_WEEK)));
		
		dto.getList5().add(ad);
	}

	private void getAttDto(AttendanceCountDto dto, OfficeAttendanceInfo ent) {
		AttendanceDto ad = new AttendanceDto();
		ad.setAttenceDate(ent.getAttenceDate());
		ad.setTimeStr(DateUtils.date2String(ent.getClockTime(), "HH:mm"));
		ad.setType(ent.getType());
		Calendar mc = Calendar.getInstance();
		mc.setTime(ad.getAttenceDate());
		ad.setWeekdayStr(WeekdayEnum.getName(mc.get(Calendar.DAY_OF_WEEK)));
		
		Map<String, AttendanceDetailCountDto> map = dto.getDetailMap();
		if(ent.getClockState().equals(AttendanceConstants.ATTENDANCE_CLOCK_STATE_1)){
			AttendanceDetailCountDto dto6 = map.get(AttendanceConstants.ATTENDANCE_COUNT_TYPE_6);//迟到
			//迟到
			dto6.setNum(dto6.getNum()+1);
			dto.getList1().add(ad);
		}else if(ent.getClockState().equals(AttendanceConstants.ATTENDANCE_CLOCK_STATE_2)){
			AttendanceDetailCountDto dto4 = map.get(AttendanceConstants.ATTENDANCE_COUNT_TYPE_4);//早退
			//早退
			dto4.setNum(dto4.getNum()+1);
			dto.getList2().add(ad);
		}else if(ent.getClockState().equals(AttendanceConstants.ATTENDANCE_CLOCK_STATE_3)){
			AttendanceDetailCountDto dto3 = map.get(AttendanceConstants.ATTENDANCE_COUNT_TYPE_3);//外勤打卡
			//外勤
			dto3.setNum(dto3.getNum()+1);
			dto.getList3().add(ad);
		}
	}


	/**
	 * 日期提前到前一天
	 * @param calendar
	 * @param pmTime
	 * @return
	 */
	private Date addDate_1(Calendar calendar, Date date) {
		calendar.setTime(date);
		calendar.add(Calendar.DATE, -1);
		date = calendar.getTime();
		return date;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public void setUnitService(UnitService unitService) {
		this.unitService = unitService;
	}

	public void setOfficeAttendancePlaceService(
			OfficeAttendancePlaceService officeAttendancePlaceService) {
		this.officeAttendancePlaceService = officeAttendancePlaceService;
	}

	public void setOfficeAttendanceGroupService(
			OfficeAttendanceGroupService officeAttendanceGroupService) {
		this.officeAttendanceGroupService = officeAttendanceGroupService;
	}

	public void setOfficeAttendanceGroupUserService(
			OfficeAttendanceGroupUserService officeAttendanceGroupUserService) {
		this.officeAttendanceGroupUserService = officeAttendanceGroupUserService;
	}

	public void setOfficeAttendanceInfoService(
			OfficeAttendanceInfoService officeAttendanceInfoService) {
		this.officeAttendanceInfoService = officeAttendanceInfoService;
	}

	public void setOfficeAttendanceColckApplyService(
			OfficeAttendanceColckApplyService officeAttendanceColckApplyService) {
		this.officeAttendanceColckApplyService = officeAttendanceColckApplyService;
	}

	public void setOfficeAttendanceColckLogService(
			OfficeAttendanceColckLogService officeAttendanceColckLogService) {
		this.officeAttendanceColckLogService = officeAttendanceColckLogService;
	}
	
	public void setOfficeAttendanceSetService(
			OfficeAttendanceSetService officeAttendanceSetService) {
		this.officeAttendanceSetService = officeAttendanceSetService;
	}
	
	public void setDateInfoService(DateInfoService dateInfoService) {
		this.dateInfoService = dateInfoService;
	}
	
	public void setFlowManageService(FlowManageService flowManageService) {
		this.flowManageService = flowManageService;
	}
	
	/**
	 * 四舍五入保留两位小数
	 * @param d
	 * @return
	 */
	public static double formatDouble2(double d) {
        // 旧方法，已经不再推荐使用
//        BigDecimal bg = new BigDecimal(d).setScale(2, BigDecimal.ROUND_HALF_UP);
        
        // 新方法，如果不需要四舍五入，可以使用RoundingMode.DOWN
        BigDecimal bg = new BigDecimal(d).setScale(2, BigDecimal.ROUND_HALF_UP);
        return bg.doubleValue();
    }
	
	public static void main(String[] args) throws ParseException {
		Calendar c = Calendar.getInstance();
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date ss = df.parse("2017-5-2");
		System.out.println(df.format(ss));
		Date d = new Date();
		System.out.println(ss.getTime());
		System.out.println(df.format(d));
	}
}
