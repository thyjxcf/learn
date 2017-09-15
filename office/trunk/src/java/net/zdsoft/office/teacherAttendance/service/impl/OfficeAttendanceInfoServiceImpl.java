package net.zdsoft.office.teacherAttendance.service.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.json.JSONArray;
import net.zdsoft.eis.base.common.entity.DateInfo;
import net.zdsoft.eis.base.common.entity.Dept;
import net.zdsoft.eis.base.common.entity.User;
import net.zdsoft.eis.base.common.service.DateInfoService;
import net.zdsoft.eis.base.common.service.DeptService;
import net.zdsoft.eis.base.common.service.UserService;
import net.zdsoft.eis.base.constant.WeikeAppConstant;
import net.zdsoft.keel.util.DateUtils;
import net.zdsoft.keel.util.Pagination;
import net.zdsoft.office.dailyoffice.entity.OfficeBusinessTrip;
import net.zdsoft.office.dailyoffice.entity.OfficeGoOut;
import net.zdsoft.office.dailyoffice.service.OfficeBusinessTripService;
import net.zdsoft.office.dailyoffice.service.OfficeGoOutService;
import net.zdsoft.office.jtgoout.service.OfficeJtGooutService;
import net.zdsoft.office.jtgoout.entity.GooutStudentEx;
import net.zdsoft.office.jtgoout.entity.GooutTeacherEx;
import net.zdsoft.office.jtgoout.entity.OfficeJtGoout;
import net.zdsoft.office.jtgoout.service.GooutStudentExService;
import net.zdsoft.office.jtgoout.service.GooutTeacherExService;
import net.zdsoft.office.jtgoout.service.OfficeJtGooutService;
import net.zdsoft.office.teacherAttendance.constant.AttendanceConstants;
import net.zdsoft.office.teacherAttendance.dao.OfficeAttendanceInfoDao;
import net.zdsoft.office.teacherAttendance.entity.OfficeAttendanceColckApply;
import net.zdsoft.office.teacherAttendance.entity.OfficeAttendanceColckLog;
import net.zdsoft.office.teacherAttendance.entity.OfficeAttendanceExcludeUser;
import net.zdsoft.office.teacherAttendance.entity.OfficeAttendanceGroup;
import net.zdsoft.office.teacherAttendance.entity.OfficeAttendanceGroupUser;
import net.zdsoft.office.teacherAttendance.entity.OfficeAttendanceInfo;
import net.zdsoft.office.teacherAttendance.entity.OfficeAttendanceSet;
import net.zdsoft.office.teacherAttendance.entity.OfficeAttendanceStatisticsViewDto;
import net.zdsoft.office.teacherAttendance.service.OfficeAttendanceColckApplyService;
import net.zdsoft.office.teacherAttendance.service.OfficeAttendanceColckLogService;
import net.zdsoft.office.teacherAttendance.service.OfficeAttendanceExcludeUserService;
import net.zdsoft.office.teacherAttendance.service.OfficeAttendanceGroupService;
import net.zdsoft.office.teacherAttendance.service.OfficeAttendanceGroupUserService;
import net.zdsoft.office.teacherAttendance.service.OfficeAttendanceInfoService;
import net.zdsoft.office.teacherLeave.entity.OfficeTeacherLeave;
import net.zdsoft.office.teacherLeave.service.OfficeTeacherLeaveService;
import net.zdsoft.office.util.Constants;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import com.alibaba.fastjson.JSONObject;
/**
 * 考勤打卡信息
 * @author 
 * 
 */
public class OfficeAttendanceInfoServiceImpl implements OfficeAttendanceInfoService{
	private OfficeAttendanceInfoDao officeAttendanceInfoDao;
	private DateInfoService dateInfoService;
	private UserService userService;
	private DeptService deptService;
	private OfficeAttendanceColckApplyService officeAttendanceColckApplyService;
	private OfficeTeacherLeaveService officeTeacherLeaveService;
	private OfficeGoOutService officeGoOutService;
	private OfficeBusinessTripService officeBusinessTripService;
	private OfficeAttendanceColckLogService  officeAttendanceColckLogService;
	private OfficeAttendanceExcludeUserService officeAttendanceExcludeUserService;
	private OfficeAttendanceGroupUserService officeAttendanceGroupUserService;
	private OfficeJtGooutService officeJtGooutService;
	private GooutStudentExService gooutStudentExService;
	private GooutTeacherExService gooutTeacherExService;
	private OfficeAttendanceGroupService officeAttendanceGroupService;
	public void setGooutTeacherExService(
			GooutTeacherExService gooutTeacherExService) {
		this.gooutTeacherExService = gooutTeacherExService;
	}
	public void setGooutStudentExService(
			GooutStudentExService gooutStudentExService) {
		this.gooutStudentExService = gooutStudentExService;
	}
	public void setOfficeAttendanceColckLogService(
			OfficeAttendanceColckLogService officeAttendanceColckLogService) {
		this.officeAttendanceColckLogService = officeAttendanceColckLogService;
	}
	
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public void setDeptService(DeptService deptService) {
		this.deptService = deptService;
	}
	public void setOfficeBusinessTripService(
			OfficeBusinessTripService officeBusinessTripService) {
		this.officeBusinessTripService = officeBusinessTripService;
	}
	
	public void setOfficeGoOutService(OfficeGoOutService officeGoOutService) {
		this.officeGoOutService = officeGoOutService;
	}
	
	public void setOfficeTeacherLeaveService(
			OfficeTeacherLeaveService officeTeacherLeaveService) {
		this.officeTeacherLeaveService = officeTeacherLeaveService;
	}
	@Override
	public OfficeAttendanceInfo save(OfficeAttendanceInfo officeAttendanceInfo){
		OfficeAttendanceInfo ent = officeAttendanceInfoDao.save(officeAttendanceInfo);
		OfficeAttendanceColckLog log = ent.entityToLog(ent);
		//插入log数据
		officeAttendanceColckLogService.save(log);
		return ent;
	}

	@Override
	public Integer delete(String[] ids){
		return officeAttendanceInfoDao.delete(ids);
	}

	@Override
	public Integer update(OfficeAttendanceInfo officeAttendanceInfo){
		Integer m = officeAttendanceInfoDao.update(officeAttendanceInfo);
		OfficeAttendanceColckLog log = officeAttendanceInfo.entityToLog(officeAttendanceInfo);
		//插入log数据
		officeAttendanceColckLogService.save(log);
		return m;
	}

	@Override
	public OfficeAttendanceInfo getOfficeAttendanceInfoById(String id){
		return officeAttendanceInfoDao.getOfficeAttendanceInfoById(id);
	}

	@Override
	public List<OfficeAttendanceInfo> getOfficeAttendanceInfoByCondition(
			String unitId, String userid,String searchName, String deptId, Date startTime,
			Date endTime,boolean isTeacherAttenceAdmin,Pagination page) {
		List<OfficeAttendanceInfo> attendanceInfoList=new ArrayList<OfficeAttendanceInfo>();
		String startTimeStr=DateUtils.date2String(startTime,"yyyy-MM-dd");
		String endTimeStr=DateUtils.date2String(endTime,"yyyy-MM-dd");
		//获取对应时间的dateinfo
		List<DateInfo> dateInfoList=dateInfoService.getDateInfos(unitId, startTimeStr, endTimeStr);
		
		if(CollectionUtils.isNotEmpty(dateInfoList)){
			//获取当天以及昨天的时间
			Date nowDate=new Date();
			String nowDay=DateUtils.date2String(nowDate, "yyyy-MM-dd");
			Calendar c=Calendar.getInstance();
			c.setTime(nowDate);
			c.add(Calendar.DAY_OF_MONTH,-1);
			String yesterday=DateUtils.date2String(c.getTime(), "yyyy-MM-dd");
			
			Set<String> userIds=new HashSet<String>();
			Map<String,User> userMap=userService.getUserMap(unitId);
			String deptName="";
			Map<String,Dept> deptMap=new HashMap<String, Dept>();
			boolean nameFlag=StringUtils.isNotBlank(searchName);
			if(isTeacherAttenceAdmin){
				//获取userIds
				if(StringUtils.isNotBlank(deptId)){
					Dept dept=deptService.getDept(deptId);
					deptName=dept!=null?dept.getDeptname():"";
					
					List<User> userList=userService.getUsersByDeptId(deptId);
					if(CollectionUtils.isNotEmpty(userList)){
						for(User user:userList){
							if(nameFlag && user.getRealname().indexOf(searchName)!=-1){
								userIds.add(user.getId());
							}else if(!nameFlag){
								userIds.add(user.getId());
							}
						}
					}
				}else{
					deptMap=deptService.getDeptMap(unitId);
					if(!nameFlag) searchName="";
					List<User> userList=userService.getUsersByFaintness(unitId, searchName);
					if(CollectionUtils.isNotEmpty(userList)){
						for(User user:userList){
							userIds.add(user.getId());
						}
					}
				}
			}else{
				deptId=userService.getDeptIdByUserId(userid);
				Dept dept=deptService.getDept(deptId);
				deptName=dept!=null?dept.getDeptname():"";

				userIds.add(userid);//非管理员 只显示自己的考勤信息
			}
			
			//获取userId对应的考勤时段
			Map<String,OfficeAttendanceSet> setMap=officeAttendanceColckApplyService.getSetMapByUserIds(userIds.toArray(new String[0]), unitId);
			
			String[] types=new String[]{AttendanceConstants.ATTENDANCE_TYPE_AM,AttendanceConstants.ATTENDANCE_TYPE_PM};
			//获取考勤信息
			Map<String,OfficeAttendanceInfo> attendaceInfoMap=officeAttendanceInfoDao.getOfficeAttendanceInfoByUnitIdMap(unitId, userIds.toArray(new String[0]), startTimeStr, endTimeStr);
			if(attendaceInfoMap==null)  attendaceInfoMap=new HashMap<String, OfficeAttendanceInfo>();
			//获取补卡信息
			Map<String,OfficeAttendanceColckApply> attendaceClockApplyMap=officeAttendanceColckApplyService.getOfficeAttendanceClockByUnitIdMap(unitId, userIds.toArray(new String[0]), startTimeStr, endTimeStr);
			if(attendaceClockApplyMap==null)  attendaceClockApplyMap=new HashMap<String,OfficeAttendanceColckApply>();
			
			Map<String,OfficeAttendanceInfo> attendaceInfoMapForList=new HashMap<String, OfficeAttendanceInfo>();
			for(DateInfo dateInfo:dateInfoList){
				//String isFeast=dateInfo.getIsfeast();
				String infodate="";
				if(dateInfo.getDate()!=null){
					infodate=DateUtils.date2String(dateInfo.getDate(),"yyyy-MM-dd");
				}
				//获取上班打卡或者下班打卡信息
				for(String userId:userIds){
					User user=userMap.get(userId);
					String userName="";
					if(user!=null){
						userName=user.getRealname();
						if(StringUtils.isBlank(deptId)){
							Dept dept=deptMap.get(user.getDeptid());
							deptName=dept!=null?dept.getDeptname():"";
						}
						
					}
					for(String type:types){
						OfficeAttendanceInfo attendaceInfo=attendaceInfoMap.get(userId+"_"+infodate+"_"+type);//数据库数据
						
						OfficeAttendanceInfo info=attendaceInfoMapForList.get(userId+"_"+infodate);//已存到map中的info
						
						String pmTime="";
						Date startRangeDate=null;//提前上班可打卡日期
						Date nowPmTimeDate=null;
						if(attendaceInfo==null){
							if(infodate.equals(nowDay) || (infodate.equals(yesterday) &&
									type.equals(AttendanceConstants.ATTENDANCE_TYPE_PM))){//今天整个时段 或者 昨天的下班时段
								
								OfficeAttendanceSet set=setMap.get(userId);
								if(set!=null && StringUtils.isNotBlank(set.getId())){
									pmTime=set.getPmTime();//下午上班时间
									String setStartTime=set.getStartTime();//上班时间
									Date setStartTimeDate=DateUtils.string2Date(nowDay+" "+setStartTime, "yyyy-MM-dd HH:mm");
									if(StringUtils.isNotBlank(set.getStartRange())){
										int hours=Integer.parseInt(set.getStartRange());//提前打卡时间
										c.setTime(setStartTimeDate);
										c.set(Calendar.HOUR_OF_DAY, c.get(Calendar.HOUR_OF_DAY)-hours);
										startRangeDate=c.getTime();
									}else
										startRangeDate=setStartTimeDate;
								}
								
								String nowPmTime=nowDay+" "+pmTime;
								nowPmTimeDate=DateUtils.string2Date(nowPmTime,"yyyy-MM-dd HH:mm");
							}
						}
						//是节假日的情况
						/*if(StringUtils.isNotBlank(isFeast) && isFeast.equals("Y")){
							OfficeAttendanceInfo isFeastInfo=new OfficeAttendanceInfo();
							isFeastInfo.setAttenceDate(dateInfo.getDate());
							isFeastInfo.setUserName(userName);
							isFeastInfo.setUserId(userId);
							isFeastInfo.setDeptName(deptName);
							isFeastInfo.setClockStateTotal("正常");
							if(attendaceInfo!=null){
								String infoId=attendaceInfo.getId();
								if(info==null){
									if(attendaceInfo.getType().equals(AttendanceConstants.ATTENDANCE_TYPE_AM)){
										isFeastInfo.setClockTimeAm(attendaceInfo.getClockTime());
										isFeastInfo.setInfoIdAm(infoId);
									}else if(attendaceInfo.getType().equals(AttendanceConstants.ATTENDANCE_TYPE_PM)){
										isFeastInfo.setClockTimePm(attendaceInfo.getClockTime());
										isFeastInfo.setInfoIdPm(infoId);
									}
									attendaceInfoMapForList.put(userId+"_"+infodate, isFeastInfo);
								}else{
									if(attendaceInfo.getType().equals(AttendanceConstants.ATTENDANCE_TYPE_AM)){
										info.setClockTimeAm(attendaceInfo.getClockTime());
										info.setInfoIdAm(infoId);
									}else if(attendaceInfo.getType().equals(AttendanceConstants.ATTENDANCE_TYPE_PM)){
										info.setClockTimePm(attendaceInfo.getClockTime());
										info.setInfoIdPm(infoId);
									}
									attendaceInfoMapForList.put(userId+"_"+infodate, info);
								}
							}else{
								//都为null时  给一个无打卡时间的对象。
								if(info==null){
									attendaceInfoMapForList.put(userId+"_"+infodate, isFeastInfo);
								}
							}
						}else{//非节假日*/
							if(attendaceInfo!=null){
								String attendanceClockState=attendaceInfo.getClockState();//数据库取出的状态
								String infoId=attendaceInfo.getId();
								//null时 添加新的key value
								if(info==null){
									attendaceInfo.setUserName(userName);
									attendaceInfo.setUserId(userId);
									attendaceInfo.setDeptName(deptName);
									//赋值 上班打卡时间或下班打卡时间
									if(attendaceInfo.getType().equals(AttendanceConstants.ATTENDANCE_TYPE_AM)){
										attendaceInfo.setClockTimeAm(attendaceInfo.getClockTime());
										attendaceInfo.setInfoIdAm(infoId);
									}else if(attendaceInfo.getType().equals(AttendanceConstants.ATTENDANCE_TYPE_PM)){
										attendaceInfo.setClockTimePm(attendaceInfo.getClockTime());
										attendaceInfo.setInfoIdPm(infoId);
									}
									attendaceInfo.setClockStateTotal(getClockStateStr(attendanceClockState));
									attendaceInfoMapForList.put(userId+"_"+infodate, attendaceInfo);
								}else{
									if(attendaceInfo.getType().equals(AttendanceConstants.ATTENDANCE_TYPE_AM)){
										info.setClockTimeAm(attendaceInfo.getClockTime());
										info.setInfoIdAm(infoId);
									}else if(attendaceInfo.getType().equals(AttendanceConstants.ATTENDANCE_TYPE_PM)){
										info.setClockTimePm(attendaceInfo.getClockTime());
										info.setInfoIdPm(infoId);
									}
									String infoClockState=info.getClockState();//已存到map中的状态
									
									//至少有一个正常的打卡状态
									if(infoClockState.equals(AttendanceConstants.ATTENDANCE_CLOCK_STATE_DEFAULT)
											||attendanceClockState.equals(AttendanceConstants.ATTENDANCE_CLOCK_STATE_DEFAULT)){
										if(infoClockState.equals(AttendanceConstants.ATTENDANCE_CLOCK_STATE_DEFAULT)){
											//map中已存的为正常打卡，总状态只需此次循环的不正常打卡状态
											info.setClockStateTotal(getClockStateStr(attendanceClockState));
										}/*else if(attendanceClockState.equals(AttendanceConstants.ATTENDANCE_CLOCK_STATE_DEFAULT)){
											info.setClockStateTotal(getClockStateStr(infoClockState));
										}*/
									//都是不正常的打卡状态   进入此方法不会两次缺卡
									}else{
										info.setClockStateTotal(info.getClockStateTotal()+"、"+getClockStateStr(attendanceClockState));
									}
									//TODO  时长或考勤说明
									if(info.getClockTimeAm()!=null && info.getClockTimePm()!=null){
										float hours=(info.getClockTimePm().getTime()-info.getClockTimeAm().getTime())/(1000*60*60);
										BigDecimal b = new BigDecimal(hours);
										float hours1 = b.setScale(1, BigDecimal.ROUND_HALF_UP).floatValue();
										info.setTimeLength(hours1+"小时");
									}
									attendaceInfoMapForList.put(userId+"_"+infodate, info);
								}
							}else{//缺卡
								OfficeAttendanceColckApply clockApply=attendaceClockApplyMap.get(userId+"_"+infodate+"_"+type);//获取补卡信息
								String clockType="";
								if(clockApply!=null)
									clockType=clockApply.getType();//上班类型--补卡申请
								
								attendaceInfo=new OfficeAttendanceInfo();
								
								if(info==null){
									attendaceInfo.setAttenceDate(dateInfo.getDate());
									attendaceInfo.setUserName(userName);
									attendaceInfo.setUserId(userId);
									attendaceInfo.setDeptName(deptName);
									
									if(infodate.equals(nowDay) || (infodate.equals(yesterday) &&
											type.equals(AttendanceConstants.ATTENDANCE_TYPE_PM))){//今天整个时段 或者 昨天的下班时段
										
										OfficeAttendanceSet set=setMap.get(userId);
										if(set!=null && StringUtils.isBlank(set.getId())){//没有考勤组（时段） 正常
											attendaceInfo.setClockState(AttendanceConstants.ATTENDANCE_CLOCK_STATE_DEFAULT);
											attendaceInfo.setClockStateTotal("正常");
											attendaceInfo.setCanNoApplyAm(true);
										}else{
											if(infodate.equals(nowDay)){//今天整个时段
												if(type.equals(AttendanceConstants.ATTENDANCE_TYPE_AM)){
													if(nowPmTimeDate.after(nowDate)){//当前日期小于下午上班日期
														attendaceInfo.setClockState(AttendanceConstants.ATTENDANCE_CLOCK_STATE_DEFAULT);
														attendaceInfo.setClockStateTotal("正常");
														attendaceInfo.setCanNoApplyAm(true);//此时不能补卡
													}else{
														attendaceInfo.setClockState(AttendanceConstants.ATTENDANCE_CLOCK_STATE_98);
														attendaceInfo.setClockStateTotal("缺卡");
													}
												}else{
													attendaceInfo.setClockState(AttendanceConstants.ATTENDANCE_CLOCK_STATE_DEFAULT);
													attendaceInfo.setClockStateTotal("正常");
													attendaceInfo.setCanNoApplyPm(true);
												}
											}else{
												if(startRangeDate.after(nowDate)){//当前日期小于 可提前打卡日期
													attendaceInfo.setClockState(AttendanceConstants.ATTENDANCE_CLOCK_STATE_DEFAULT);
													attendaceInfo.setClockStateTotal("正常");
													attendaceInfo.setCanNoApplyPm(true);//此时不能补卡
												}else{
													attendaceInfo.setClockState(AttendanceConstants.ATTENDANCE_CLOCK_STATE_98);
													attendaceInfo.setClockStateTotal("缺卡");
												}
											}
										}
									}else{
										attendaceInfo.setClockState(AttendanceConstants.ATTENDANCE_CLOCK_STATE_98);
										attendaceInfo.setClockStateTotal("缺卡");
									}
									
									if(clockApply!=null){
										if(clockApply.getApplyStatus()==Constants.APPLY_STATE_NOPASS){//审核未通过
											if(clockType.equals(AttendanceConstants.ATTENDANCE_TYPE_AM)){
												attendaceInfo.setAuditNoPassAm(true);//true 代表审核未通过
											}else{
												attendaceInfo.setAuditNoPassPm(true);
											}
										}else if(clockApply.getApplyStatus()==Constants.APPLY_STATE_NEED_AUDIT){//待审核 （ 审核通过则会有考勤打卡信息） 
											if(clockType.equals(AttendanceConstants.ATTENDANCE_TYPE_AM)){
												attendaceInfo.setHaveColckApplyAm(true);
											}else{
												attendaceInfo.setHaveColckApplyPm(true);
											}
										}
									}
									attendaceInfoMapForList.put(userId+"_"+infodate, attendaceInfo);
								}else{
									String infoClockState=info.getClockState();//已存到map中的状态
									if(infodate.equals(nowDay) || (infodate.equals(yesterday) &&
											type.equals(AttendanceConstants.ATTENDANCE_TYPE_PM))){//今天整个时段 或者 昨天的下班时段
										OfficeAttendanceSet set=setMap.get(userId);
										if(set!=null && StringUtils.isBlank(set.getId())){//没有考勤组（时段） 正常
											//info.setClockState(AttendanceConstants.ATTENDANCE_CLOCK_STATE_DEFAULT);
											//info.setClockStateTotal("正常");
											info.setCanNoApplyPm(true);
										}else{
											if(infodate.equals(nowDay)){//今天整个时段
												if(type.equals(AttendanceConstants.ATTENDANCE_TYPE_AM)){
													if(nowPmTimeDate.after(nowDate)){//当前日期小于下午上班日期
														info.setCanNoApplyAm(true);//此时不能补卡
													}else{
														info.setClockState(AttendanceConstants.ATTENDANCE_CLOCK_STATE_98);
														info.setClockStateTotal("缺卡");
													}
												}else{
													info.setCanNoApplyPm(true);
												}
											}else{
												if(startRangeDate.after(nowDate)){//当前日期小于 可提前打卡日期
													info.setCanNoApplyPm(true);//此时不能补卡
												}else{
													if(infoClockState.equals(AttendanceConstants.ATTENDANCE_CLOCK_STATE_DEFAULT)){
														info.setClockStateTotal("缺卡");
													}else if(infoClockState.equals(AttendanceConstants.ATTENDANCE_CLOCK_STATE_98)){
														info.setClockState(AttendanceConstants.ATTENDANCE_CLOCK_STATE_99);
														info.setClockStateTotal("旷工");
													}else{
														info.setClockStateTotal(info.getClockStateTotal()+"、"+"缺卡");
													}
												}
											}
										}
									}else{
										if(infoClockState.equals(AttendanceConstants.ATTENDANCE_CLOCK_STATE_DEFAULT)){
											info.setClockStateTotal("缺卡");
										}else if(infoClockState.equals(AttendanceConstants.ATTENDANCE_CLOCK_STATE_98)){
											info.setClockState(AttendanceConstants.ATTENDANCE_CLOCK_STATE_99);
											info.setClockStateTotal("旷工");
										}else{
											info.setClockStateTotal(info.getClockStateTotal()+"、"+"缺卡");
										}
									}
									if(clockApply!=null){
										if(clockApply.getApplyStatus()==Constants.APPLY_STATE_NOPASS){//审核未通过
											if(clockType.equals(AttendanceConstants.ATTENDANCE_TYPE_AM)){
												info.setAuditNoPassAm(true);//true 代表审核未通过
											}else{
												info.setAuditNoPassPm(true);
											}
										}else if(clockApply.getApplyStatus()==Constants.APPLY_STATE_NEED_AUDIT){
											if(clockType.equals(AttendanceConstants.ATTENDANCE_TYPE_AM)){
												info.setHaveColckApplyAm(true);
											}else{
												info.setHaveColckApplyPm(true);
											}
										}
									}
									attendaceInfoMapForList.put(userId+"_"+infodate, info);
								}
							}
						}
					//}
				}
			}
			for(int i=dateInfoList.size()-1;i>=0;i--){
				DateInfo dateInfo=dateInfoList.get(i);
				String infodate="";
				if(dateInfo.getDate()!=null){
					infodate=DateUtils.date2String(dateInfo.getDate(),"yyyy-MM-dd");
				}
				for(String userId:userIds){
					OfficeAttendanceInfo attendanceInfo=attendaceInfoMapForList.get(userId+"_"+infodate);
					if(attendanceInfo!=null){
						attendanceInfo.setIsHoliday(dateInfo.getIsfeast().equals("Y")?true:false);
						if(userId.equals(userid)){
							attendanceInfo.setMySelf(true);
						}
						attendanceInfoList.add(attendanceInfo);
					}
				}
			}
		}
		if(page==null){
			return attendanceInfoList;
		}
		// 用于显示输出页面
		 List<OfficeAttendanceInfo> summaryOutput=new ArrayList<OfficeAttendanceInfo>();
		 //对page进行 重新 初始化
		 if(CollectionUtils.isNotEmpty(attendanceInfoList)){
			 page.setMaxRowCount(attendanceInfoList.size());
			 page.initialize();
			 
			 int max=0;
			 if(page.getPageIndex() == page.getMaxPageIndex()){
				 max=page.getMaxRowCount();
				 
			 }else {
				 max=page.getPageIndex()*page.getPageSize();
				 
			 }
			 //显示list进行输出设置
			 for(int i=(page.getPageIndex()-1)*page.getPageSize();i<max;i++){
				 summaryOutput.add(attendanceInfoList.get(i));
			 }
		 }
		return summaryOutput;
	}
	public String getClockStateStr(String clockState){
		String clockStateStr="";
		if(clockState.equals(AttendanceConstants.ATTENDANCE_CLOCK_STATE_DEFAULT)){
			clockStateStr="正常";
		}else if(clockState.equals(AttendanceConstants.ATTENDANCE_CLOCK_STATE_1)){
			clockStateStr="迟到";
		}else if(clockState.equals(AttendanceConstants.ATTENDANCE_CLOCK_STATE_2)){
			clockStateStr="早退";
		}else if(clockState.equals(AttendanceConstants.ATTENDANCE_CLOCK_STATE_3)){
			clockStateStr="外勤";
		}else if(clockState.equals(AttendanceConstants.ATTENDANCE_CLOCK_STATE_98)){
			clockStateStr="缺卡";
		}
		return clockStateStr;
	}
	public void setOfficeAttendanceInfoDao(
			OfficeAttendanceInfoDao officeAttendanceInfoDao) {
		this.officeAttendanceInfoDao = officeAttendanceInfoDao;
	}

	public void setDateInfoService(DateInfoService dateInfoService) {
		this.dateInfoService = dateInfoService;
	}
	
	
	
	public List<OfficeAttendanceInfo> getListByUserIdDate(String userId, Date date){
		return officeAttendanceInfoDao.getListByUserIdDate(userId, date);
	}
	
	public OfficeAttendanceInfo getItemByAdateType(String userId, Date attenceTime, String type){
		return officeAttendanceInfoDao.getItemByAdateType(userId, attenceTime, type);
	}
	
	public List<OfficeAttendanceInfo> getListByStartAndEndTime(String userId, Date startTime, Date endTime){
		return officeAttendanceInfoDao.getListByStartAndEndTime(userId, startTime, endTime);
	}
	
	public Map<String, List<OfficeAttendanceInfo>> getMapByStartAndEndTime(String userId, Date startTime, Date endTime){
		Map<String, List<OfficeAttendanceInfo>> map = new HashMap<String, List<OfficeAttendanceInfo>>();
		List<OfficeAttendanceInfo> list = officeAttendanceInfoDao.getListByStartAndEndTime(userId, startTime, endTime);
		for(OfficeAttendanceInfo ent : list){
			String key = DateUtils.date2String(ent.getAttenceDate(), "yyyy-MM-dd");
			if(map.containsKey(key)){
				map.get(key).add(ent);
			}else{
				List<OfficeAttendanceInfo> l = new ArrayList<OfficeAttendanceInfo>();
				l.add(ent);
				map.put(key, l);
			}
		}
		return map;
	}
	
	public List<OfficeJtGoout> getJtGooutList(String unitId, String userId, Date startTime, Date endTime){
		List<OfficeJtGoout> list = officeJtGooutService.getListByStarttimeAndEndtime(unitId, startTime, endTime);
		Set<String> stuIds = new HashSet<String>();
		Set<String> teaIds = new HashSet<String>();
		for(OfficeJtGoout ent : list){
			if(StringUtils.equals("1", ent.getType())){
				stuIds.add(ent.getId());
			}
			if(StringUtils.equals("2", ent.getType())){
				teaIds.add(ent.getId());
			}
		}
		
		List<GooutStudentEx> stuList = gooutStudentExService.getGooutStudentExListByjtId(stuIds.toArray(new String[0]));
		List<GooutTeacherEx> teaList = gooutTeacherExService.getGooutTeacherExListByjtId(teaIds.toArray(new String[0]));
		Set<String> ids = new HashSet<String>();
		//判断是否跟userId相关
		Set<String> pSets = null;
		for(GooutStudentEx ent : stuList){
			pSets = new HashSet<String>();
			//活动负责人,带队老师,其他老师
//			ent.getActivityLeaderId();ent.getLeadGroupId();ent.getOtherTeacherId()
			pSets.add(ent.getActivityLeaderId());
			pSets.add(ent.getLeadGroupId());
			if(StringUtils.isNotBlank(ent.getOtherTeacherId())){
				String[] ss = ent.getOtherTeacherId().split(",");
				for(String s : ss){
					pSets.add(s);
				}
			}
			if(pSets.contains(userId)){
				ids.add(ent.getJtgooutId());
			}
		}
		
		for(GooutTeacherEx ent : teaList){
			pSets = new HashSet<String>();
			if(StringUtils.isNotBlank(ent.getPartakePersonId())){
				String[] ss = ent.getPartakePersonId().split(",");
				for(String s : ss){
					pSets.add(s);
				}
			}
			if(pSets.contains(userId)){
				ids.add(ent.getJtgooutId());
			}
		}
		
		List<OfficeJtGoout> rlist = new ArrayList<OfficeJtGoout>(); 
		for(OfficeJtGoout ent : list){
			if(ids.contains(ent.getId()))
				rlist.add(ent);
		}
		
		return rlist;
	}
	
	//获取请假等其他信息
	public Map<Date, JSONArray> getOtherInfo(String unitId, String userId, Date startTime, Date endTime){
		//请假
		String[] userIds = new String[]{userId};
		List<OfficeTeacherLeave> leaveList = officeTeacherLeaveService.getQueryList(unitId, userIds, startTime, endTime, null, true);
		List<OfficeGoOut> gooutList = officeGoOutService.getListByStarttimeAndEndtime(startTime, endTime, userIds);
		List<OfficeBusinessTrip> tripList = officeBusinessTripService.getListByStarttimeAndEndtime(startTime, endTime, userIds);
		List<OfficeJtGoout> jtgooutlist = this.getJtGooutList(unitId, userId, startTime, endTime);
		
		Map<Date, List<String>> leaveMap = new HashMap<Date, List<String>>();// 请假
		Map<Date, List<String>> gooutMap = new HashMap<Date, List<String>>();//外出
		Map<Date, List<String>> tripMap = new HashMap<Date, List<String>>();// 出差
		Map<Date, List<String>> jtgooutMap = new HashMap<Date, List<String>>();// 出差
		
		Calendar calendar = Calendar.getInstance();
		//请假
		for(OfficeTeacherLeave ent : leaveList){
			String startStr = DateUtils.date2String(ent.getLeaveBeignTime(), "yyyy-MM-dd");
			String endStr = DateUtils.date2String(ent.getLeaveEndTime(), "yyyy-MM-dd");
			Date startDate = DateUtils.string2Date(startStr, "yyyy-MM-dd");
			Date entDate = DateUtils.string2Date(endStr, "yyyy-MM-dd");
			
			String msgStr = startStr + " - " + endStr + " " +ent.getDays() + "天";
			
			calendar.setTime(startDate);
			while(!calendar.getTime().after(entDate)){
				if(leaveMap.containsKey(calendar.getTime())){
					leaveMap.get(calendar.getTime()).add(msgStr);
				}else{
					List<String> l = new ArrayList<String>();
					l.add(msgStr);
					leaveMap.put(calendar.getTime(), l);
				}
				calendar.add(Calendar.DATE, 1);
			}
		}
		//外出
		for(OfficeGoOut ent : gooutList){
			String startStr = DateUtils.date2String(ent.getBeginTime(), "yyyy-MM-dd");
			String endStr = DateUtils.date2String(ent.getEndTime(), "yyyy-MM-dd");
			Date startDate = DateUtils.string2Date(startStr, "yyyy-MM-dd");
			Date entDate = DateUtils.string2Date(endStr, "yyyy-MM-dd");
			
			String msgStr = startStr + " - " + endStr + " " +ent.getHours() + "小时";
			
			calendar.setTime(startDate);
			while(!calendar.getTime().after(entDate)){
				if(gooutMap.containsKey(calendar.getTime())){
					gooutMap.get(calendar.getTime()).add(msgStr);
				}else{
					List<String> l = new ArrayList<String>();
					l.add(msgStr);
					gooutMap.put(calendar.getTime(), l);
				}
				calendar.add(Calendar.DATE, 1);
			}
		}
		//出差
		for(OfficeBusinessTrip ent : tripList){
			String startStr = DateUtils.date2String(ent.getBeginTime(), "yyyy-MM-dd");
			String endStr = DateUtils.date2String(ent.getEndTime(), "yyyy-MM-dd");
			Date startDate = DateUtils.string2Date(startStr, "yyyy-MM-dd");
			Date entDate = DateUtils.string2Date(endStr, "yyyy-MM-dd");
			
			String msgStr = startStr + " - " + endStr + " " +ent.getDays() + "天";
			
			calendar.setTime(startDate);
			while(!calendar.getTime().after(entDate)){
				if(tripMap.containsKey(calendar.getTime())){
					tripMap.get(calendar.getTime()).add(msgStr);
				}else{
					List<String> l = new ArrayList<String>();
					l.add(msgStr);
					tripMap.put(calendar.getTime(), l);
				}
				calendar.add(Calendar.DATE, 1);
			}
		}
		
		//集体外出
		for(OfficeJtGoout ent : jtgooutlist){
			String startStr = DateUtils.date2String(ent.getStartTime(), "yyyy-MM-dd HH:mm:ss");
			String endStr = DateUtils.date2String(ent.getEndTime(), "yyyy-MM-dd HH:mm:ss");
			Date startDate = DateUtils.string2Date(startStr, "yyyy-MM-dd HH:mm:ss");
			Date endDate = DateUtils.string2Date(endStr, "yyyy-MM-dd HH:mm:ss");
			
			long diff = endDate.getTime() - startDate.getTime();
			String msgStr = startStr + " - " + endStr + " " +getDatePoor(diff);
			
			calendar.setTime(DateUtils.string2Date(startStr, "yyyy-MM-dd"));
			while(!calendar.getTime().after(DateUtils.string2Date(endStr, "yyyy-MM-dd"))){
				if(jtgooutMap.containsKey(calendar.getTime())){
					jtgooutMap.get(calendar.getTime()).add(msgStr);
				}else{
					List<String> l = new ArrayList<String>();
					l.add(msgStr);
					jtgooutMap.put(calendar.getTime(), l);
				}
				calendar.add(Calendar.DATE, 1);
			}
		}
		
//		j.put("type", "W06");//类型 W06:请假，W08：出差，W07：外出，W15：集体外出 
		Map<Date, JSONArray> dayMap = new HashMap<Date, JSONArray>();
		calendar.setTime(startTime);
		while(!calendar.getTime().after(endTime)){
			JSONArray remarkJsons = new JSONArray();
			//请假
			if(leaveMap.containsKey(calendar.getTime())){
				List<String> l = leaveMap.get(calendar.getTime());
				if(l != null && l.size()>0){
					JSONObject json1 = new JSONObject();
					json1.put("type", WeikeAppConstant.TEACHER_LEAVE);
					json1.put("name", "请假");
					json1.put("remark", l.toArray(new String[0]));
					remarkJsons.add(json1);
				}
			}
			//外出
			if(gooutMap.containsKey(calendar.getTime())){
				List<String> l = gooutMap.get(calendar.getTime());
				if(l != null && l.size()>0){
					JSONObject json2 = new JSONObject();
					json2.put("type", WeikeAppConstant.GO_OUT);
					json2.put("name", "外出");
					json2.put("remark", l.toArray(new String[0]));
					remarkJsons.add(json2);
				}
			}
			//出差
			if(tripMap.containsKey(calendar.getTime())){
				List<String> l = tripMap.get(calendar.getTime());
				if(l != null && l.size()>0){
					JSONObject json3 = new JSONObject();
					json3.put("type", WeikeAppConstant.EVECTION);
					json3.put("name", "出差");
					json3.put("remark", l.toArray(new String[0]));
					remarkJsons.add(json3);
				}
			}
			//集体外出
			if(jtgooutMap.containsKey(calendar.getTime())){
				List<String> l = jtgooutMap.get(calendar.getTime());
				if(l != null && l.size()>0){
					JSONObject json4 = new JSONObject();
					json4.put("type", WeikeAppConstant.JT_GO_OUT);
					json4.put("name", "集体外出");
					json4.put("remark", l.toArray(new String[0]));
					remarkJsons.add(json4);
				}
			}
			
			dayMap.put(calendar.getTime(), remarkJsons);
			calendar.add(Calendar.DATE, 1);
		}
		
		return dayMap;
	}
	
	//获取请假等其他信息
	public JSONArray getOtherCountInfo(String unitId, String userId, Date startTime, Date endTime){
		//请假
		String[] userIds = new String[]{userId};
		List<OfficeTeacherLeave> leaveList = officeTeacherLeaveService.getQueryList(unitId, userIds, startTime, endTime, null, true);
		List<OfficeGoOut> gooutList = officeGoOutService.getListByStarttimeAndEndtime(startTime, endTime, userIds);
		List<OfficeBusinessTrip> tripList = officeBusinessTripService.getListByStarttimeAndEndtime(startTime, endTime, userIds);
		List<OfficeJtGoout> jtgooutlist = this.getJtGooutList(unitId, userId, startTime, endTime);
		
		int leaveDays = 0;
		int gooutHours = 0;
		int tripDays = 0;
		long jtgooutDiffs = 0;
		
		for(OfficeTeacherLeave ent : leaveList){
			leaveDays += ent.getDays();
		}
		
		for(OfficeGoOut ent : gooutList){
			gooutHours += ent.getHours();
		}
		
		for(OfficeBusinessTrip ent : tripList){
			tripDays += ent.getDays();
		}
		
		for(OfficeJtGoout ent : jtgooutlist){
			if(ent.getEndTime()!=null && ent.getStartTime()!=null)
				jtgooutDiffs += ent.getEndTime().getTime() - ent.getStartTime().getTime();
		}
		
		
		JSONArray a = new JSONArray();
		//请假
//		W06:请假，W08：出差，W07：外出，W15：集体外出
		JSONObject json1 = new JSONObject();
		json1.put("type", WeikeAppConstant.TEACHER_LEAVE);
		json1.put("name", "请假");
		json1.put("numStr", leaveDays+"天");
		if(leaveDays>0){
			json1.put("flag", 1);//0:没有请假记录，1：有请假记录
		}else{
			json1.put("flag", 0);
		}
		a.add(json1);
		//外出
		JSONObject json2 = new JSONObject();
		json2.put("type", WeikeAppConstant.GO_OUT);
		json2.put("name", "外出");
		json2.put("numStr", gooutHours+"小时");
		if(gooutHours>0){
			json2.put("flag", 1);//0:没有请假记录，1：有请假记录
		}else{
			json2.put("flag", 0);
		}
		a.add(json2);
		//出差
		JSONObject json3 = new JSONObject();
		json3.put("type", WeikeAppConstant.EVECTION);
		json3.put("name", "出差");
		json3.put("numStr", tripDays+"天");
		if(tripDays>0){
			json3.put("flag", 1);//0:没有请假记录，1：有请假记录
		}else{
			json3.put("flag", 0);
		}
		a.add(json3);
		//集体外出
		JSONObject json4 = new JSONObject();
		json4.put("type", WeikeAppConstant.JT_GO_OUT);
		json4.put("name", "集体外出");
		json4.put("numStr", getDatePoor(jtgooutDiffs));
		if(jtgooutDiffs>0){
			json4.put("flag", 1);//0:没有请假记录，1：有请假记录
		}else{
			json4.put("flag", 0);
		}
		a.add(json4);
		//调休
		
		return a;
	}

	public String getOneDayStatistic(String unitId, String groupId,
			String deptId, Date queryDate) {
		// TODO Auto-generated method stub
		Map<String,String> allAttendance = new HashMap<String,String>();
		List<OfficeAttendanceGroup> groupList = officeAttendanceGroupService.listOfficeAttendanceGroupByUnitId(unitId);
		String[] groupIds = new String[groupList.size()];
		for(int m=0;m<groupList.size();m++){
			groupIds[m] = groupList.get(m).getId();
		}
		Map<String,List<OfficeAttendanceGroupUser>> groupUserMap = officeAttendanceGroupUserService.getOfficeAttendanceGroupUserMap(groupIds);
		List<OfficeAttendanceExcludeUser> excludeUsers = officeAttendanceExcludeUserService.getOfficeAttendanceExcludeUserByUnitId(unitId);
		Set<String> excludeSet = new HashSet<String>();
		for(OfficeAttendanceExcludeUser exclude:excludeUsers){
			excludeSet.add(exclude.getUserId());
		}
		if(StringUtils.isNotEmpty(groupId)){
			List<OfficeAttendanceGroupUser> groupUserList = groupUserMap.get(groupId);
			allAttendance = covertOfficeAttendanceInfoMap(groupUserList , excludeUsers);
			
		}else if(StringUtils.isNotEmpty(deptId)){
			List<User> deptUser = userService.getUsersByDeptId(deptId);
			allAttendance = covertUserMap(deptUser,excludeUsers);
		}else{
			List<User> unitUser = userService.getUserListByUnitId(unitId, null, User.TEACHER_LOGIN, null);
			allAttendance = covertUserMap(unitUser ,excludeUsers);
		}
		Set<String> userSet = allAttendance.keySet();
		int i =0;
		String[] userIds = new String[userSet.size()];
		for(String s:userSet){
			userIds[i++] = s;
		}
		
		JSONObject json = new JSONObject();
//		JSONArray jsonArr = new JSONArray();
//		JSONObject json2 = null;
		String[] attendancename = new String[11];
		Integer[] attendanceNum = new Integer[11];
//		取该单位下 迟到早退List
		List<OfficeAttendanceInfo> later = listOfficeAttendanceInfoByDateAndState(unitId, null,null,AttendanceConstants.ATTENDANCE_CLOCK_STATE_1, queryDate);
		List<OfficeAttendanceInfo> leaveEarly = listOfficeAttendanceInfoByDateAndState(unitId,null,null, AttendanceConstants.ATTENDANCE_CLOCK_STATE_2, queryDate);

		int laterSum = 0;
		int leaveEarlySum =0;
		laterSum = sumLeaveOrLater(allAttendance,later);
		leaveEarlySum = sumLeaveOrLater(allAttendance,leaveEarly);

		attendancename[0] = "迟到人数";
		attendanceNum[0] = laterSum;
		

		attendancename[1] = "早退人数";
		attendanceNum[1] = leaveEarlySum;
		Map<String,String> costomAttendance = getOfficeAttendanceInfoByDateAndState(AttendanceConstants.ATTENDANCE_CLOCK_STATE_DEFAULT, queryDate, unitId,null,null);
		Map<String,String> outWork = getOfficeAttendanceInfoByDateAndState(AttendanceConstants.ATTENDANCE_CLOCK_STATE_3, queryDate, unitId,null,null);
		
	
		int customSum=0;
		int outWorkSum=0;
		customSum =sumPeople(allAttendance,costomAttendance);
		outWorkSum =sumPeople(allAttendance,outWork);

		attendancename[2] = "正常考勤人数";
		attendanceNum[2] = customSum;

		attendancename[3] = "外勤考勤人数";
		attendanceNum[3] = outWorkSum;
	
//		List<Teacher> teacherList = teacherService.getTeachers(unitId);
//		List<OfficeAttendanceExcludeUser> notAddAttendance = officeAttendanceExcludeUserService.getOfficeAttendanceExcludeUserByUnitId(unitId);
//		int sumNotAddAttencedance = sumNotAttendance(allAttendance,notAddAttendance,flag);
//		
//		int attendanceNum = allAttendance.size() - sumNotAddAttencedance;

		attendancename[5] = "考勤人数";
		attendanceNum[5] = allAttendance.size();
	
		Set<String> applySet = new HashSet<String>();
		List<OfficeTeacherLeave> leaveList = officeTeacherLeaveService.getQueryList(unitId, userIds, queryDate, queryDate, null, true);
		int leaveSum =0;
		for(OfficeTeacherLeave l:leaveList){
			if(userSet.contains(l.getApplyUserId())){
				applySet.add(l.getApplyUserId());
				leaveSum++;
			}
		}

		attendancename[7] = "请假人数";
		
		attendanceNum[7] = leaveSum;
		List<OfficeGoOut> gooutList = officeGoOutService.getListByStarttimeAndEndtime(queryDate, DateUtils.addDay(queryDate, 1), userIds);
		int goOutSum=0;
		for(OfficeGoOut g:gooutList){
			if(userSet.contains(g.getApplyUserId())){
				applySet.add(g.getApplyUserId());
				goOutSum++;
			}
		}
		attendancename[8] = "外出人数";
		attendanceNum[8] = goOutSum;
		List<OfficeBusinessTrip> tripList = officeBusinessTripService.getListByStarttimeAndEndtime(queryDate, queryDate, userIds);
		
		Map<String,Set<String>> jtGoOutMap = officeJtGooutService.getMapStatistcGoOutSum(unitId, userIds, queryDate, DateUtils.addDay(queryDate, 1));
		int tripSum = 0;
		for(OfficeBusinessTrip t:tripList){
			if(userSet.contains(t.getApplyUserId())){
				applySet.add(t.getApplyUserId());
				tripSum++;
			}
		}

//		Map<String,Integer> jtGoOutMap = officeJtGooutService.getMapStatistcGoOutSum(unitId, userIds, queryDate, queryDate);
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
//		int jtGoOutNum = jtGoOutMap.get(format.format(queryDate));
		attendancename[9] = "出差人数";
		attendanceNum[9] = tripSum;
		Set<String> jtSet = jtGoOutMap.get(format.format(queryDate));
		if(jtSet == null) jtSet = new HashSet<String>();
		attendancename[10] = "集体外出人数";
		attendanceNum[10] = jtSet.size();
		applySet.addAll(jtSet);
		
		Map<String,String> missCard = getMissingCard(queryDate, unitId, AttendanceConstants.ATTENDANCE_CLOCK_STATE_98 ,null,null);
		Map<String,String> moreOneCard = getMissingCard(queryDate, unitId, null,null,null);
		
		
		Set<String> missCardSet = missCard.keySet();
		
		Set<String> oneMoreCardSet = moreOneCard.keySet();
		DateInfo info = dateInfoService.getDateInfo(unitId, queryDate);
		attendancename[4] = "缺卡人数";
		attendancename[6] = "旷工人数";
		if(info != null && "N".equals(info.getIsfeast())){
			
			//得到符合条件的 缺卡人数
			missCardSet.retainAll(userSet);
			Set<String> missRetain = new HashSet<String>(missCardSet);
			missRetain.retainAll(applySet);
			missCardSet.removeAll(missRetain);
			
			attendanceNum[4] = missCardSet.size();
			
			applySet.addAll(oneMoreCardSet);
			int allSum = userSet.size();
			userSet.retainAll(applySet);
			int notWorkNum = allSum - userSet.size();
			attendanceNum[6] = notWorkNum;
		}else{
			attendanceNum[4] = 0;
			attendanceNum[6] = 0;
		}
//		json.put("yInterval",max_ds);
		json.put("legendData", new String[]{"人数"});
		json.put("xAxisData", attendancename);
		json.put("loadingData", new Integer[][]{attendanceNum});
		return json.toString();
	}
	
	private Map<String,String> covertOfficeAttendanceInfoMap(List<OfficeAttendanceGroupUser> list , List<OfficeAttendanceExcludeUser> excludeUsers){
		Map<String,String> map = new HashMap<String,String>();
		for(OfficeAttendanceGroupUser l:list){
			map.put(l.getUserId(),l.getId());
		}
		for(OfficeAttendanceExcludeUser user:excludeUsers){
			if(map.get(user.getUserId()) != null){
				map.remove(user.getUserId());
			}
		}
		return map;
	}
	private Map<String,String> covertUserMap(List<User> list ,List<OfficeAttendanceExcludeUser> excludeUsers){
		Map<String,String> map = new HashMap<String,String>();
		for(User l:list){
			map.put(l.getId(),l.getId());
		}
		for(OfficeAttendanceExcludeUser user:excludeUsers){
			if(map.get(user.getUserId()) != null){
				map.remove(user.getUserId());
			}
		}
		return map;
	}

	private  int sumLeaveOrLater(Map<String,String> map,List<OfficeAttendanceInfo> log  ){
			int i=0;
			for(OfficeAttendanceInfo l:log){
				if(map.get(l.getUserId()) != null){
					i++;
				}
			}
			return i;
		
		
	}
	private int sumPeople(Map<String,String> map,Map<String,String> logMap ){
			int i=0;
			for(Map.Entry<String, String>  e:logMap.entrySet()){
				if(map.get(e.getKey()) != null){
					i++;
				}
			}
			return i;
	}
	public List<OfficeAttendanceStatisticsViewDto>  listStatistics(String unitId, String groupId,
			String deptId, Date startDate , Date endDate ,Pagination page){
		Map<String,String> allAttendance = new HashMap<String,String>();
		List<OfficeAttendanceGroup> groupList = officeAttendanceGroupService.listOfficeAttendanceGroupByUnitId(unitId);
		String[] groupIds = new String[groupList.size()];
		for(int m=0;m<groupList.size();m++){
			groupIds[m] = groupList.get(m).getId();
		}
		Map<String,List<OfficeAttendanceGroupUser>> groupUserMap = officeAttendanceGroupUserService.getOfficeAttendanceGroupUserMap(groupIds);
		List<OfficeAttendanceExcludeUser> excludeUsers = officeAttendanceExcludeUserService.getOfficeAttendanceExcludeUserByUnitId(unitId);
		if(StringUtils.isNotEmpty(groupId)){
			List<OfficeAttendanceGroupUser> groupUserList = groupUserMap.get(groupId);
			allAttendance = covertOfficeAttendanceInfoMap(groupUserList , excludeUsers);
		}else if(StringUtils.isNotEmpty(deptId)){
			List<User> deptUser = userService.getUsersByDeptId(deptId);
			allAttendance = covertUserMap(deptUser ,excludeUsers);
		}else{
			List<User> unitUser = userService.getUserListByUnitId(unitId, null, User.TEACHER_LOGIN, null);
			allAttendance = covertUserMap(unitUser , excludeUsers);
		}
		Set<String> userSet = allAttendance.keySet();
		int i =0;
		String[] userIds = new String[userSet.size()];
		for(String s:userSet){
			userIds[i++] = s;
		}
		List<OfficeAttendanceInfo> later = listOfficeAttendanceInfoByDateAndState(unitId, startDate,endDate,AttendanceConstants.ATTENDANCE_CLOCK_STATE_1, null);
		List<OfficeAttendanceInfo> leaveEarly = listOfficeAttendanceInfoByDateAndState(unitId,startDate,endDate, AttendanceConstants.ATTENDANCE_CLOCK_STATE_2, null);

		
		Map<String,Integer> sumMap = new HashMap<String,Integer>();
		statisticLaterOrLeaveEarly(allAttendance,later,sumMap,AttendanceConstants.ATTENDANCE_CLOCK_STATE_1);
		statisticLaterOrLeaveEarly(allAttendance,leaveEarly,sumMap,AttendanceConstants.ATTENDANCE_CLOCK_STATE_2);
		
		
		Map<String,String> costomAttendance = getOfficeAttendanceInfoByDateAndState(AttendanceConstants.ATTENDANCE_CLOCK_STATE_DEFAULT, null, unitId,startDate,endDate);
		Map<String,String> outWork = getOfficeAttendanceInfoByDateAndState(AttendanceConstants.ATTENDANCE_CLOCK_STATE_3, null, unitId,startDate,endDate);
		Map<String,Set<String>> mapDateUser = new HashMap<String,Set<String>>();
		
		CountSumMap(allAttendance,costomAttendance,sumMap,AttendanceConstants.ATTENDANCE_CLOCK_STATE_DEFAULT ,mapDateUser);
		CountSumMap(allAttendance,outWork,sumMap,AttendanceConstants.ATTENDANCE_CLOCK_STATE_3 ,mapDateUser);


		Map<String,String> missCard = getMissingCard(null, unitId, AttendanceConstants.ATTENDANCE_CLOCK_STATE_98 ,startDate,endDate);
		Map<String,String> moreOneCard = getMissingCard(null, unitId, null,startDate,endDate);
		//缺卡人 一天 缺卡 人 
		
		CountSumMap(allAttendance,missCard,sumMap,AttendanceConstants.ATTENDANCE_CLOCK_STATE_98 ,mapDateUser);
		CountSumMap(allAttendance,moreOneCard,sumMap,"moreOneCard" ,mapDateUser);
		SimpleDateFormat formate = new SimpleDateFormat("yyyy-MM-dd");
		List<OfficeTeacherLeave> leaveList = officeTeacherLeaveService.getQueryList(unitId, userIds, startDate,endDate, null, true);
		for(OfficeTeacherLeave l:leaveList){
			Date start = l.getLeaveBeignTime();
			Date end = l.getLeaveEndTime();
			statistics(mapDateUser,"leave",start,end , l.getApplyUserId());
			
		}
		
		List<OfficeGoOut> gooutList = officeGoOutService.getListByStarttimeAndEndtime(startDate,DateUtils.addDay(endDate, 1), userIds);
		for(OfficeGoOut o:gooutList){
			Date start = o.getBeginTime();
			Date end = o.getEndTime();
			statistics(mapDateUser,"goout",start,end ,o.getApplyUserId());
		}

		List<OfficeBusinessTrip> tripList = officeBusinessTripService.getListByStarttimeAndEndtime(startDate,endDate, userIds);
		for(OfficeBusinessTrip t:tripList){
			Date start = t.getBeginTime();
			Date end = t.getEndTime();
			statistics(mapDateUser,"business",start,end , t.getApplyUserId());
		}
		Map<String,Set<String>> jtGoOutMap = officeJtGooutService.getMapStatistcGoOutSum(unitId, userIds, startDate, DateUtils.addDay(endDate, 1));
		
		List<OfficeAttendanceStatisticsViewDto> viewList = new ArrayList<OfficeAttendanceStatisticsViewDto>();
		Date nextDate= null;
		int j=0;
		int allSum = userSet.size();
		
		List<DateInfo>  dateInfo = dateInfoService.getDateInfos(unitId, formate.format(startDate),formate.format(endDate));
		Map<String , DateInfo> dateInfoMap = new HashMap<String,DateInfo>();
		for(DateInfo info:dateInfo){
			dateInfoMap.put(formate.format(info.getDate()),info);
		}
		while(true){
			nextDate = DateUtils.addDay(startDate, j);
			j++;
			
			if(nextDate.compareTo(endDate) == 1){
				break;
			}
			String dateStr = formate.format(nextDate);
			String keyPrefix = dateStr + "_";
			OfficeAttendanceStatisticsViewDto statisticDto = new OfficeAttendanceStatisticsViewDto();
			
			statisticDto.setAttendanceNum(String.valueOf(allSum));
			statisticDto.setCustomAttendanceNum(covertToStr(sumMap.get(keyPrefix + AttendanceConstants.ATTENDANCE_CLOCK_STATE_DEFAULT)));
			statisticDto.setDay(nextDate);
			statisticDto.setDateStr(dateStr);
			statisticDto.setLaterNum(covertToStr(sumMap.get(keyPrefix + AttendanceConstants.ATTENDANCE_CLOCK_STATE_1)));
			statisticDto.setLeaveEarlyNum(covertToStr(sumMap.get(keyPrefix + AttendanceConstants.ATTENDANCE_CLOCK_STATE_2)));
			statisticDto.setOutWorkNum(covertToStr(sumMap.get(keyPrefix + AttendanceConstants.ATTENDANCE_CLOCK_STATE_3)));
			
			
			
			Set<String> businessSet = getResult(mapDateUser , keyPrefix + "business");
			Set<String> goOutSet = getResult(mapDateUser , keyPrefix +  "goout"); 
			Set<String> leaveSet =getResult(mapDateUser , keyPrefix + "leave");
			Set<String> jtGoOutSet =getResult(jtGoOutMap,dateStr); 
			
			statisticDto.setBusinessNum(covertToStr(businessSet.size()));
			statisticDto.setGoOutNum(covertToStr(goOutSet.size()));
			statisticDto.setLeaveNum(covertToStr(leaveSet.size()));
			statisticDto.setJtGoOutNum(covertToStr(jtGoOutSet.size()));
			//不算 缺卡 旷工 的 已经请过假了
			Set<String> missCardSet = getResult(mapDateUser , keyPrefix + AttendanceConstants.ATTENDANCE_CLOCK_STATE_98); 
			Set<String> oneMoreCardSet = getResult(mapDateUser , keyPrefix + "moreOneCard"); 
			

			businessSet.addAll(goOutSet);
			businessSet.addAll(leaveSet);
			businessSet.addAll(jtGoOutSet);
			Set<String> applyUser = new HashSet<String>();
			
			applyUser.addAll(businessSet);
			
			int missCardNum = getDifferenceSet(missCardSet , businessSet);
			
			Set<String> allUserSet = new HashSet<String>(userSet);
			
			applyUser.addAll(oneMoreCardSet);
			
			int notWorNum = getDifferenceSet(allUserSet ,applyUser );
			
			DateInfo info = dateInfoMap.get(dateStr);
			if(info != null && "N".equals(info.getIsfeast())){
				statisticDto.setMissCardNum(String.valueOf(missCardNum));
				statisticDto.setNotWorkNum(String.valueOf(notWorNum));
			}else{
				statisticDto.setMissCardNum("0");
				statisticDto.setNotWorkNum("0");
			}
			
			viewList.add(statisticDto);
		}
		if(page != null){
			
			List<OfficeAttendanceStatisticsViewDto> outPut = new ArrayList<OfficeAttendanceStatisticsViewDto>();
			//对page进行 重新 初始化
			page.setMaxRowCount(viewList.size());
			page.initialize();
			
			int max=0;
			if(page.getPageIndex() == page.getMaxPageIndex()){
				max=page.getMaxRowCount();
				
			}else {
				max=page.getPageIndex()*page.getPageSize();
				
			}
			//显示list进行输出设置
			for(int m=(page.getPageIndex()-1)*page.getPageSize();m<max;m++){
				outPut.add(viewList.get(m));
			}
			return outPut;
		}else{
			return viewList;
		}
	}
	private Set<String> getResult(Map<String,Set<String>> mapDateUser , String key){
		Set<String> setResult = mapDateUser.get(key) ;
		if(setResult == null){
			setResult = new HashSet<String>();
		}
		return setResult;
	}
	/**
	 * 取 set1 与 set 2 差集
	 * @param set1
	 * @param set2
	 * @return
	 */
	private int getDifferenceSet(Set<String> set1 ,Set<String> set2){
		Set<String> setSrc = new HashSet<String>(set1);
		set1.retainAll(set2);
		setSrc.removeAll(set1);
		
		return setSrc.size();
		
	}
	private String covertToStr(Integer i){
		if(i == null){
			return "0";
		}else{
			return String.valueOf(i);
		}
	}
	private void statistics(Map<String,Set<String>> sumMap ,String tag , Date start,Date end ,String userId){
		SimpleDateFormat formate = new SimpleDateFormat("yyyy-MM-dd");
		int j=0;
		while(true){
			Date nextDate = DateUtils.addDay(start, j);
			String dateStr = formate.format(nextDate);
			j++;
			if(nextDate.compareTo(end) > 0){
				break;
			}
			Set<String> set = sumMap.get(dateStr + "_" + tag);
			if(set == null){
				set = new HashSet<String>();
				set.add(userId);
				sumMap.put(dateStr + "_" + tag, set);
			}else{
				set.add(userId);
			}
		}
	}
	private void statisticLaterOrLeaveEarly(Map<String,String> allAttendance,List<OfficeAttendanceInfo> log,Map<String,Integer> sum ,String tag){
		SimpleDateFormat formate = new SimpleDateFormat("yyyy-MM-dd");
		for(OfficeAttendanceInfo l:log){
			if(allAttendance.get(l.getUserId()) != null){
				Date d = l.getAttenceDate();
				String dateFormat = formate.format(d);
				Integer s = sum.get(dateFormat+"_"+tag);
				if(s == null){
					s = 1;
					sum.put(dateFormat+"_"+tag, s);
				}else{
					s++;
					sum.put(dateFormat+"_"+tag, s);
				}
			}
		}
	}
	private void CountSumMap(Map<String,String> allAttendance,Map<String,String> statistc,Map<String,Integer> sumMap ,String tag ,Map<String,Set<String>> mapDateUser){
		for(Map.Entry<String, String> k:statistc.entrySet()){
			String[] keyArr = k.getKey().split("_");
			if(allAttendance.get(keyArr[0]) != null){
				Integer s = sumMap.get(keyArr[1] + "_" + tag);
				if(s == null){
					sumMap.put(keyArr[1] + "_" + tag, 1);
				}else{
					s++;
					sumMap.put(keyArr[1] + "_" + tag,s);
				}
				Set<String> set = mapDateUser.get(keyArr[1] + "_" + tag);
				if(set == null){
					set = new HashSet<String>();
					set.add(keyArr[0]);
					mapDateUser.put(keyArr[1] + "_" + tag, set);
				}else{
					set.add(keyArr[0]);
				}
			}
			
		}
	}
	
	public void setOfficeAttendanceColckApplyService(
			OfficeAttendanceColckApplyService officeAttendanceColckApplyService) {
		this.officeAttendanceColckApplyService = officeAttendanceColckApplyService;
	}

	@Override
	public Map<String, String> getMissingCard(Date date, String unitId,
			String attendanceState, Date startDate, Date endDate) {
		// TODO Auto-generated method stub
		return officeAttendanceInfoDao.getMissingCard(date, unitId, attendanceState, startDate, endDate);
	}

	@Override
	public Map<String, String> getOfficeAttendanceInfoByDateAndState(
			String state, Date date, String unitId, Date startDate, Date endDate) {
		// TODO Auto-generated method stub
		return officeAttendanceInfoDao.getOfficeAttendanceInfoByDateAndState(state, date, unitId, startDate, endDate);
	}

	@Override
	public List<OfficeAttendanceInfo> listOfficeAttendanceInfoByDateAndState(
			String unitId, Date startDate, Date endDate, String state, Date date) {
		// TODO Auto-generated method stub
		return officeAttendanceInfoDao.listOfficeAttendanceInfoByDateAndState(unitId, startDate, endDate, state, date);
	}

	public void setOfficeAttendanceExcludeUserService(
			OfficeAttendanceExcludeUserService officeAttendanceExcludeUserService) {
		this.officeAttendanceExcludeUserService = officeAttendanceExcludeUserService;
	}

	public void setOfficeAttendanceGroupUserService(
			OfficeAttendanceGroupUserService officeAttendanceGroupUserService) {
		this.officeAttendanceGroupUserService = officeAttendanceGroupUserService;
	}
	
	public static String getDatePoor(long diff) {
		 
	    long nd = 1000 * 24 * 60 * 60;
	    long nh = 1000 * 60 * 60;
	    long nm = 1000 * 60;
	    // long ns = 1000;
	    // 获得两个时间的毫秒时间差异
	    // 计算差多少天
	    long day = diff / nd;
	    // 计算差多少小时
	    long hour = diff % nd / nh;
	    // 计算差多少分钟
	    long min = diff % nd % nh / nm;
	    // 计算差多少秒//输出结果
	    // long sec = diff % nd % nh % nm / ns;
	    StringBuffer str = new StringBuffer();
	    if(day>0){
	    	str.append(day+"天");
	    }
	    if(hour>0){
	    	str.append(hour+"小时");
	    }
	    if(min>0){
	    	str.append(min+"分钟");
	    }
	    if(StringUtils.isBlank(str.toString())){
	    	return "0天";
	    }
	    return str.toString();
	}

	public void setOfficeJtGooutService(OfficeJtGooutService officeJtGooutService) {
		this.officeJtGooutService = officeJtGooutService;
	}
	public void setOfficeAttendanceGroupService(
			OfficeAttendanceGroupService officeAttendanceGroupService) {
		this.officeAttendanceGroupService = officeAttendanceGroupService;
	}


	
}