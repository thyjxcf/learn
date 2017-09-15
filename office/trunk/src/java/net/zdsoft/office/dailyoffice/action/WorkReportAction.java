package net.zdsoft.office.dailyoffice.action;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.zdsoft.eis.base.common.entity.CustomRole;
import net.zdsoft.eis.base.common.entity.CustomRoleUser;
import net.zdsoft.eis.base.common.entity.Dept;
import net.zdsoft.eis.base.common.entity.Unit;
import net.zdsoft.eis.base.common.entity.User;
import net.zdsoft.eis.base.common.service.CustomRoleService;
import net.zdsoft.eis.base.common.service.CustomRoleUserService;
import net.zdsoft.eis.base.common.service.DeptService;
import net.zdsoft.eis.base.common.service.UnitService;
import net.zdsoft.eis.base.common.service.UserService;
import net.zdsoft.eis.frame.action.PageAction;
import net.zdsoft.office.dailyoffice.entity.OfficeLog;
import net.zdsoft.office.dailyoffice.entity.OfficeWorkReport;
import net.zdsoft.office.dailyoffice.service.OfficeLogService;
import net.zdsoft.office.dailyoffice.service.OfficeWorkReportService;
import net.zdsoft.office.util.Constants;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
/**
 * @author chens
 * @version 创建时间：2014-12-31 下午3:23:24
 * 
 */
public class WorkReportAction extends PageAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private OfficeWorkReport officeWorkReport=new OfficeWorkReport();
	private List<OfficeWorkReport> officeWorkReportList=new ArrayList<OfficeWorkReport>();
	
	private OfficeLogService officeLogService;
	private OfficeWorkReportService officeWorkReportService;
	private UserService userService;
	private DeptService deptService;
	private UnitService unitService;
	private CustomRoleService customRoleService;
	private CustomRoleUserService customRoleUserService;
	private String readonlyStyle;
	private String classStyle;
	private String contents;
	private String beginTimes;
	private String endTimes;
	private String states;
	private String reportTypes;
	private String createUserNames;
	private Date nowWeekFirstDay;
	private Date nowWeekLastDay;
	private Date nowMonthFirstDay;
	private Date nowMonthLastDay;
	private String userNames;
	private String userIds;
	
	private boolean canDelete;
	@Override
	public String execute() throws Exception {
		return SUCCESS;
	}
	public String myWorkReport(){
		return SUCCESS;
	}
	public String myWorkReportEdit(){
		if("true".equals(readonlyStyle)){
			setClassStyle("input-readonly");
		}else{
			setClassStyle("");
		}
		//获取当前周月
		Calendar c=Calendar.getInstance();
		Date d=new Date();
		c.set(Calendar.DAY_OF_WEEK,2);
		nowWeekFirstDay=c.getTime();
		
		c.setTime(d);
		c.set(Calendar.DAY_OF_WEEK,6);
		nowWeekLastDay=c.getTime();
		
		c.setTime(d);
		c.set(Calendar.DAY_OF_MONTH, c.getActualMinimum(Calendar.DATE));
		nowMonthFirstDay=c.getTime();
		
		c.setTime(d);
		c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DATE));
		nowMonthLastDay=c.getTime();
		
		if(StringUtils.isNotBlank(officeWorkReport.getId())){
			officeWorkReport=officeWorkReportService.getOfficeWorkReportById(officeWorkReport.getId());
			String userName=userService.getUserDetailNamesStr(officeWorkReport.getReceiveUserId().split(","));
			officeWorkReport.setUserName(userName);
		}else{
			officeWorkReport.setBeginTime(nowWeekFirstDay);
			officeWorkReport.setEndTime(nowWeekLastDay);
			List<OfficeLog> logList=officeLogService.getOfficeList(getUnitId(), 
					getLoginUser().getUserId(),String.valueOf(Constants.REPORT_MOD_ID),null);
			if(CollectionUtils.isNotEmpty(logList)){
				for(OfficeLog log:logList){
					if(log.getCode().equals(Constants.LOG_WEEK)){
						OfficeLog officeLogWeek=log;
						String receiveUserId=officeLogWeek.getDescription();
						if(StringUtils.isNotBlank(receiveUserId)){
							String userName=userService.getUserDetailNamesStr(receiveUserId.split(","));
							officeWorkReport.setUserName(userName);
							officeWorkReport.setReceiveUserId(receiveUserId);
							//officeWorkReport.setContent(report.getContent());
						}
					}else if(log.getCode().equals(Constants.LOG_MONTH)){
						OfficeLog officeLogMonth=log;
						userIds=officeLogMonth.getDescription();
						if(StringUtils.isNotBlank(userIds)){
							userNames=userService.getUserDetailNamesStr(userIds.split(","));
							//officeWorkReport.setContent(report.getContent());
						}
					}
				}
			}
		}
		return SUCCESS;
	}
	public String myWorkReportSave(){
		try {
			if(StringUtils.isBlank(officeWorkReport.getId())){
				officeWorkReport.setUnitId(getUnitId());
				officeWorkReport.setCreateUserId(getLoginUser().getUserId());
				officeWorkReport.setCreateTime(new Date());
				officeWorkReport.setDeptId(getLoginInfo().getUser().getDeptid());
				officeWorkReport.setCreateUserName(getLoginInfo().getUser().getRealname());
				officeWorkReportService.save(officeWorkReport);
			}else{
				officeWorkReport.setCreateTime(new Date());
				officeWorkReportService.update(officeWorkReport);
			}
			promptMessageDto.setOperateSuccess(true);
			promptMessageDto.setPromptMessage("操作成功!");
		} catch (Exception e) {
			promptMessageDto.setOperateSuccess(false);
			promptMessageDto.setPromptMessage("操作失败："+e.getMessage());
		}
		
		return SUCCESS;
	}
	public String myWorkReportSubmit(){
		try {
			officeWorkReport=officeWorkReportService.getOfficeWorkReportById(officeWorkReport.getId());
			officeWorkReport.setState("2");
			officeWorkReportService.update(officeWorkReport);
			promptMessageDto.setOperateSuccess(true);
			promptMessageDto.setPromptMessage("提交成功!");
		} catch (Exception e) {
			promptMessageDto.setOperateSuccess(false);
			promptMessageDto.setPromptMessage("提交失败:"+e.getMessage());
		}
		return SUCCESS;
	}
	public String cancel(){
		try {
			officeWorkReport=officeWorkReportService.getOfficeWorkReportById(officeWorkReport.getId());
			officeWorkReport.setState("8");
			officeWorkReport.setInvalidUserId(getLoginUser().getUserId());
			officeWorkReport.setInvalidTime(new Date());
			officeWorkReportService.update(officeWorkReport);
			promptMessageDto.setOperateSuccess(true);
			promptMessageDto.setPromptMessage("撤回成功!");
		} catch (Exception e) {
			e.printStackTrace();
			promptMessageDto.setOperateSuccess(false);
			promptMessageDto.setPromptMessage("撤回失败！");
		}
		return SUCCESS;
	}
	public String delete(){
		try {
			String officeWorkReportId=officeWorkReport.getId();
			officeWorkReportService.delete(new String[]{officeWorkReportId});
			promptMessageDto.setOperateSuccess(true);
			promptMessageDto.setPromptMessage("删除成功!");
		} catch (Exception e) {
			promptMessageDto.setOperateSuccess(false);
			promptMessageDto.setPromptMessage("删除失败："+e.getMessage());
		}
		return SUCCESS;
	}
	public String myWorkReportView(){
		officeWorkReport=officeWorkReportService.getOfficeWorkReportById(officeWorkReport.getId());
		String userName=userService.getUserDetailNamesStr(officeWorkReport.getReceiveUserId().split(","));
		if(org.apache.commons.lang3.StringUtils.equals("8", officeWorkReport.getState())){
			User user=userService.getUser(officeWorkReport.getInvalidUserId());
			if(user!=null){
				officeWorkReport.setInvalidUserName(user.getRealname());
			}
		}
		officeWorkReport.setUserName(userName);
		return SUCCESS;
	}
	public String myWorkReportList(){
		officeWorkReportList=officeWorkReportService.getOfficeWorkReportByUnitIdPageContent(getUnitId(), getPage(), getLoginUser().getUserId(),contents, beginTimes, endTimes,reportTypes,states);
		return SUCCESS;
	}
	
	public String workReportSearch(){
		return SUCCESS;
	}
	public String workReportSearchView(){
		officeWorkReport=officeWorkReportService.getOfficeWorkReportById(officeWorkReport.getId());
		String deptId=officeWorkReport.getDeptId();
		String deptName=deptService.getDept(deptId).getDeptname();
		officeWorkReport.setDeptId(deptName);
		String unitId=officeWorkReport.getUnitId();
		String unitName=unitService.getUnit(unitId).getName();
		officeWorkReport.setUnitId(unitName);
		return SUCCESS;
	}
	public String workReportSearchList(){
		Set<String> deptIds=new HashSet<String>();
		Set<String> unitIds=new HashSet<String>();
		officeWorkReportList=officeWorkReportService.getOfficeWorkReportByUnitIdPageContentCreateUserName(getLoginUser().getUserId(), getPage(), contents, beginTimes, endTimes, reportTypes, createUserNames);
		if(officeWorkReportList!=null&&officeWorkReportList.size()>0){
		for (OfficeWorkReport officeWorkReport : officeWorkReportList) {
			String deptId=officeWorkReport.getDeptId();
			deptIds.add(deptId);
			//String deptName=deptService.getDept(deptId).getDeptname();
			//officeWorkReport.setDeptId(deptName);
			String unitId=officeWorkReport.getUnitId();
			unitIds.add(unitId);
			//String unitName=unitService.getUnit(unitId).getName();
			//officeWorkReport.setUnitId(unitName);
			}	
		}
		Map<String,Dept> mapdept=deptService.getDeptMap(deptIds.toArray(new String[0]));
		Map<String,Unit> mapUnit=unitService.getUnitMap(unitIds.toArray(new String[0]));
		if(officeWorkReportList!=null&&officeWorkReportList.size()>0){
		for (OfficeWorkReport officeWorkReport : officeWorkReportList) {
			Dept dept=mapdept.get(officeWorkReport.getDeptId());
			if(dept!=null){
				officeWorkReport.setDeptId(dept.getDeptname());
			}else{
				officeWorkReport.setDeptId("部门已删除");
			}
			Unit unit=mapUnit.get(officeWorkReport.getUnitId());
			if(unit!=null){
				officeWorkReport.setUnitId(unit.getName());
			}else{
				officeWorkReport.setUnitId("单位已删除");
			}
			}
		}
		return SUCCESS;
	}

	public OfficeWorkReport getOfficeWorkReport() {
		return officeWorkReport;
	}

	public void setOfficeWorkReport(OfficeWorkReport officeWorkReport) {
		this.officeWorkReport = officeWorkReport;
	}

	public String getReadonlyStyle() {
		return readonlyStyle;
	}

	public void setReadonlyStyle(String readonlyStyle) {
		this.readonlyStyle = readonlyStyle;
	}

	public String getClassStyle() {
		return classStyle;
	}

	public void setClassStyle(String classStyle) {
		this.classStyle = classStyle;
	}

	public void setOfficeWorkReportService(
			OfficeWorkReportService officeWorkReportService) {
		this.officeWorkReportService = officeWorkReportService;
	}

	public List<OfficeWorkReport> getOfficeWorkReportList() {
//		return new ArrayList<OfficeWorkReport>();
		return officeWorkReportList;
	}

	public void setOfficeWorkReportList(List<OfficeWorkReport> officeWorkReportList) {
		this.officeWorkReportList = officeWorkReportList;
	}

	public void setStates(String states) {
		this.states = states;
	}
	public String getReportTypes() {
		return reportTypes;
	}
	public void setReportTypes(String reportTypes) {
		this.reportTypes = reportTypes;
	}

	public void setDeptService(DeptService deptService) {
		this.deptService = deptService;
	}

	public void setUnitService(UnitService unitService) {
		this.unitService = unitService;
	}
	public String getContents() {
		return contents;
	}
	public void setContents(String contents) {
		this.contents = contents;
	}
	public String getBeginTimes() {
		return beginTimes;
	}
	public void setBeginTimes(String beginTimes) {
		this.beginTimes = beginTimes;
	}
	public String getEndTimes() {
		return endTimes;
	}
	public void setEndTimes(String endTimes) {
		this.endTimes = endTimes;
	}
	public String getCreateUserNames() {
		return createUserNames;
	}
	public void setCreateUserNames(String createUserNames) {
		this.createUserNames = createUserNames;
	}
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	public String getStates() {
		return states;
	}
	public void setOfficeLogService(OfficeLogService officeLogService) {
		this.officeLogService = officeLogService;
	}
	public Date getNowWeekFirstDay() {
		return nowWeekFirstDay;
	}
	public void setNowWeekFirstDay(Date nowWeekFirstDay) {
		this.nowWeekFirstDay = nowWeekFirstDay;
	}
	public Date getNowWeekLastDay() {
		return nowWeekLastDay;
	}
	public void setNowWeekLastDay(Date nowWeekLastDay) {
		this.nowWeekLastDay = nowWeekLastDay;
	}
	public Date getNowMonthFirstDay() {
		return nowMonthFirstDay;
	}
	public void setNowMonthFirstDay(Date nowMonthFirstDay) {
		this.nowMonthFirstDay = nowMonthFirstDay;
	}
	public Date getNowMonthLastDay() {
		return nowMonthLastDay;
	}
	public void setNowMonthLastDay(Date nowMonthLastDay) {
		this.nowMonthLastDay = nowMonthLastDay;
	}
	public String getUserNames() {
		return userNames;
	}
	public void setUserNames(String userNames) {
		this.userNames = userNames;
	}
	public String getUserIds() {
		return userIds;
	}
	public void setUserIds(String userIds) {
		this.userIds = userIds;
	}
	public void setCustomRoleService(CustomRoleService customRoleService) {
		this.customRoleService = customRoleService;
	}
	public void setCustomRoleUserService(CustomRoleUserService customRoleUserService) {
		this.customRoleUserService = customRoleUserService;
	}
	public boolean isCanDelete() {
		CustomRole customRole=customRoleService.getCustomRoleByRoleCode(getUnitId(), "work_report_manage");
		if(customRole==null){
			return false;
		}
		List<CustomRoleUser> customRoleUsers=customRoleUserService.getCustomRoleUserListByUserId(getLoginUser().getUserId());
		if(CollectionUtils.isNotEmpty(customRoleUsers)){
			for (CustomRoleUser customRoleUser : customRoleUsers) {
				if(org.apache.commons.lang3.StringUtils.equals(customRoleUser.getRoleId(), customRole.getId())){
					return true;
				}
			}
		}
		return false;
	}
	public void setCanDelete(boolean canDelete) {
		this.canDelete = canDelete;
	}
	
}
