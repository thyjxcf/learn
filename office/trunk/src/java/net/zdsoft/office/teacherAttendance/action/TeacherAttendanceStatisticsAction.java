package net.zdsoft.office.teacherAttendance.action;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.zdsoft.eis.base.common.entity.Dept;
import net.zdsoft.eis.base.common.service.DeptService;
import net.zdsoft.eis.base.common.service.TeacherService;
import net.zdsoft.eis.frame.client.LoginInfo;
import net.zdsoft.keel.util.DateUtils;
import net.zdsoft.leadin.util.ExportUtil;
import net.zdsoft.office.teacherAttendance.entity.OfficeAttendanceGroup;
import net.zdsoft.office.teacherAttendance.entity.OfficeAttendanceSet;
import net.zdsoft.office.teacherAttendance.entity.OfficeAttendanceStatisticsViewDto;
import net.zdsoft.office.teacherAttendance.service.OfficeAttendanceGroupService;
import net.zdsoft.office.teacherAttendance.service.OfficeAttendanceInfoService;
import net.zdsoft.office.teacherAttendance.service.OfficeAttendanceSetService;

/** 
 * @author  lufeng 
 * @version 创建时间：2017-4-25 下午03:02:38 
 * 类说明 
 */
public class TeacherAttendanceStatisticsAction  extends TeacherAttendanceCommonAction{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private TeacherService teacherService;
	private Date queryDate;
	private Date startDate;
	private Date endDate;
	private String groupId;
	private String deptId;
	private String resultData;
	private OfficeAttendanceInfoService officeAttendanceInfoService;
	private List<OfficeAttendanceStatisticsViewDto> viewDtoList = new ArrayList<OfficeAttendanceStatisticsViewDto>();
	private List<OfficeAttendanceGroup> groupList = new  ArrayList<OfficeAttendanceGroup>();
	private OfficeAttendanceGroupService officeAttendanceGroupService;
	private DeptService deptService;
	private List<Dept> deptList = new ArrayList<Dept>();
	private OfficeAttendanceSetService officeAttendanceSetService;
	private Date maxDate;
	public String main(){
		queryDate = getEarlyTime( ) ;
		endDate =  getEarlyTime( );
		SimpleDateFormat format = new SimpleDateFormat("yyyy_MM");
		String dateStr = format.format(queryDate);
		String[] arr = dateStr.split("_");
		startDate = DateUtils.getDate(Integer.valueOf(arr[0]), Integer.valueOf(arr[1]), 1);	
		groupList = officeAttendanceGroupService.listOfficeAttendanceGroupByUnitId(getUnitId());
		
		if(isTeacherAttenceAdmin()){
			deptList = deptService.getDepts(getUnitId());
		}else{
			LoginInfo login = getLoginInfo();
			String deptId = login.getUser().getDeptid();
			Dept dept = deptService.getDept(deptId);
			deptList.add(dept);
		}
		return "success";
	}
	private Date getEarlyTime( ){
		Date day = new Date();
		List<OfficeAttendanceGroup> attGroupList = officeAttendanceGroupService.listOfficeAttendanceGroupByUnitId(getUnitId());
		Set<String> attSet = new HashSet<String>();
		for(OfficeAttendanceGroup g:attGroupList){
			attSet.add(g.getAttSetId());
		}
		Map<String,OfficeAttendanceSet> setMap = officeAttendanceSetService.getOfficeAttendanceSetMapByUnitId(getUnitId());
		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		Date ear = null;
//		Date day = new Date();
		for(String a:attSet){
			OfficeAttendanceSet  officeSet= setMap.get(a);
			String start = officeSet.getStartTime();
			String startRange = officeSet.getStartRange();
			int range = 0 - Integer.valueOf(startRange);
			String dayStr = format1.format(day);
			
			try {
				Date early = format2.parse(dayStr + "  " + start);
				Date t = DateUtils.addHour(early, range);
				if(day.after(t)){
					day = t;
				}
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		Date d = new Date();
		if(d.compareTo(day) >= 0){
			d = DateUtils.addDay(d, -1) ;
		}else{
			d = DateUtils.addDay(d, -2) ;
		}
		return d;
		
	}
	public String getStatisticBar(){
		resultData = officeAttendanceInfoService.getOneDayStatistic(getUnitId(), groupId, deptId, queryDate);
		
		return "success";
	}
	public String exportStatisticsList(){
		viewDtoList = officeAttendanceInfoService.listStatistics(getUnitId(), groupId, deptId, startDate, endDate , null);
		String[] fieldTitles = null;
		String[] propertyNames = null;
        fieldTitles = new String[] { "日期","考勤人数","正常考勤人数","外勤考勤人数","迟到人数","早退人数","请假人数","缺卡人数",
        		"旷工人数","出差人数","外出人数","集体外出人数"};
        propertyNames = new String[] {"dateStr", "attendanceNum", "customAttendanceNum", "outWorkNum","laterNum","leaveEarlyNum",
                   "leaveNum","missCardNum","notWorkNum","businessNum","goOutNum","jtGoOutNum"};
		 
		Map<String, List<OfficeAttendanceStatisticsViewDto>> map = new HashMap<String,List<OfficeAttendanceStatisticsViewDto>>();
		map.put("考勤信息", viewDtoList);
		String fileName = "考勤信息表";
		ExportUtil exportUtil = new ExportUtil();
		exportUtil.exportXLSFile(fieldTitles, propertyNames, map, fileName);
		return "success";
	}
	public String statisticsList(){
		if(endDate == null){
			endDate = new Date();
		}
		if(startDate == null){
			SimpleDateFormat format = new SimpleDateFormat("yyyy_MM");
			String dateStr = format.format(new Date());
			String[] arr = dateStr.split("_");
			startDate = DateUtils.getDate(Integer.valueOf(arr[0]), Integer.valueOf(arr[1]), 1);
		}
		viewDtoList = officeAttendanceInfoService.listStatistics(getUnitId(), groupId, deptId, startDate, endDate , getPage());
		return "success";
	}
	

	public Date getQueryDate() {
		return queryDate;
	}
	public void setQueryDate(Date queryDate) {
		this.queryDate = queryDate;
	}
	public TeacherService getTeacherService() {
		return teacherService;
	}
	public void setTeacherService(TeacherService teacherService) {
		this.teacherService = teacherService;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public List<OfficeAttendanceStatisticsViewDto> getViewDtoList() {
		return viewDtoList;
	}
	public void setViewDtoList(List<OfficeAttendanceStatisticsViewDto> viewDtoList) {
		this.viewDtoList = viewDtoList;
	}
	public void setOfficeAttendanceInfoService(
			OfficeAttendanceInfoService officeAttendanceInfoService) {
		this.officeAttendanceInfoService = officeAttendanceInfoService;
	}
	public String getResultData() {
		return resultData;
	}
	public void setResultData(String resultData) {
		this.resultData = resultData;
	}
	public String getGroupId() {
		return groupId;
	}
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	public String getDeptId() {
		return deptId;
	}
	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}
	public List<OfficeAttendanceGroup> getGroupList() {
		return groupList;
	}
	public void setGroupList(List<OfficeAttendanceGroup> groupList) {
		this.groupList = groupList;
	}
	public List<Dept> getDeptList() {
		return deptList;
	}
	public void setDeptList(List<Dept> deptList) {
		this.deptList = deptList;
	}
	public void setOfficeAttendanceGroupService(
			OfficeAttendanceGroupService officeAttendanceGroupService) {
		this.officeAttendanceGroupService = officeAttendanceGroupService;
	}
	public void setDeptService(DeptService deptService) {
		this.deptService = deptService;
	}
	public void setOfficeAttendanceSetService(
			OfficeAttendanceSetService officeAttendanceSetService) {
		this.officeAttendanceSetService = officeAttendanceSetService;
	}
	public Date getMaxDate() {
		return getEarlyTime();
	}
	public void setMaxDate(Date maxDate) {
		this.maxDate = maxDate;
	}
	
	
	
	
}
