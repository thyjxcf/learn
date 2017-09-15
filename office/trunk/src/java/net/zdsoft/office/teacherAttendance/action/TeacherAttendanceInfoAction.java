package net.zdsoft.office.teacherAttendance.action;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import net.zdsoft.eis.base.common.entity.Dept;
import net.zdsoft.eis.base.common.service.DeptService;
import net.zdsoft.keel.util.DateUtils;
import net.zdsoft.leadin.util.excel.ZdCell;
import net.zdsoft.leadin.util.excel.ZdExcel;
import net.zdsoft.leadin.util.excel.ZdStyle;
import net.zdsoft.office.teacherAttendance.constant.AttendanceConstants;
import net.zdsoft.office.teacherAttendance.entity.OfficeAttendanceInfo;
import net.zdsoft.office.teacherAttendance.service.OfficeAttendanceInfoService;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.PrintSetup;
import org.apache.poi.ss.usermodel.Sheet;

public class TeacherAttendanceInfoAction extends TeacherAttendanceCommonAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private  OfficeAttendanceInfoService officeAttendanceInfoService;
	private  DeptService deptService;
	
	private OfficeAttendanceInfo officeAttendanceInfo=new OfficeAttendanceInfo();
	
	private List<OfficeAttendanceInfo> officeAttendanceInfoList;
	
	private List<Dept> deptList;
	private Date startTime;
	private Date endTime;
	private String deptId;
	private String searchName;
	private String id;
	
	@Override
	public String execute() throws Exception {
		return SUCCESS;
	}
	
	//获取列表
	public String getAttendanceInfoList(){
		Calendar c=Calendar.getInstance();
		if(endTime==null){
			endTime=c.getTime();
		}
		if(startTime==null){
			c.set(Calendar.DAY_OF_MONTH,1);
			startTime=c.getTime();
		}
		officeAttendanceInfoList=officeAttendanceInfoService.getOfficeAttendanceInfoByCondition(getUnitId(),getLoginUser().getUserId(), 
				StringUtils.trim(searchName), deptId, startTime, endTime,isCustomRole(AttendanceConstants.TEACHER_ATTENCE_ADMIN),getPage());
		return SUCCESS;
	}
	public void doExport(){
		officeAttendanceInfoList=officeAttendanceInfoService.getOfficeAttendanceInfoByCondition(getUnitId(),getLoginUser().getUserId(), 
				StringUtils.trim(searchName), deptId, startTime, endTime,isCustomRole(AttendanceConstants.TEACHER_ATTENCE_ADMIN),null);
		
		ZdExcel zdExcel = new ZdExcel();
		ZdStyle style2 = new ZdStyle(ZdStyle.BOLD|ZdStyle.BORDER|ZdStyle.ALIGN_CENTER);
		ZdStyle style3 = new ZdStyle(ZdStyle.BORDER|ZdStyle.ALIGN_CENTER|ZdStyle.WRAP_TEXT);
		List<ZdCell> zdlist=new ArrayList<ZdCell>();
		
		zdlist.add(new ZdCell("考勤日期",2,style2));
		zdlist.add(new ZdCell("姓名",2,style2));
		zdlist.add(new ZdCell("所在部门",2,style2));
		zdlist.add(new ZdCell("上班打卡时间",2,style2));
		zdlist.add(new ZdCell("下班打卡时间",2,style2));
		zdlist.add(new ZdCell("状态",2,style2));
		zdlist.add(new ZdCell("时长",2,style2));
		zdlist.add(new ZdCell("考勤说明",2,style2));
		zdExcel.add(zdlist.toArray(new ZdCell[0]));
		
		String clockTimeAm="";
		String clockTimePm="";
		if(CollectionUtils.isNotEmpty(officeAttendanceInfoList)){
			for(OfficeAttendanceInfo info:officeAttendanceInfoList){
				if(info.getClockTimeAm()!=null){
					clockTimeAm=DateUtils.date2String(info.getClockTimeAm(),"yyyy-MM-dd HH:mm:ss");
				}else{
					if(info.isCanNoApplyAm() || info.getIsHoliday()) clockTimeAm="";
					else clockTimeAm="缺卡";
				}
				if(info.getClockTimePm()!=null){
					clockTimePm=DateUtils.date2String(info.getClockTimePm(),"yyyy-MM-dd HH:mm:ss");
				}else{
					if(info.isCanNoApplyPm() || info.getIsHoliday()) clockTimePm="";
					else clockTimePm="缺卡";
				}
				ZdCell[] zdCell=new ZdCell[8]; 
				zdCell[0]=new ZdCell(DateUtils.date2String(info.getAttenceDate(),"yyyy-MM-dd"),2,style3);
				zdCell[1]=new ZdCell(info.getUserName(),2,style3);
				zdCell[2]=new ZdCell(info.getDeptName(),2,style3);
				zdCell[3]=new ZdCell(clockTimeAm,2,style3);
				zdCell[4]=new ZdCell(clockTimePm,2,style3);
				zdCell[5]=new ZdCell(info.getIsHoliday()?"正常":info.getClockStateTotal(),2,style3);
				zdCell[6]=new ZdCell(info.getTimeLength(),2,style3);
				zdCell[7]=new ZdCell(info.getRemark(),2,style3);
				zdExcel.add(zdCell);
			}
		}
		Sheet sheet = zdExcel.createSheet("sheet1");
		sheet.getPrintSetup().setPaperSize(PrintSetup.A4_PAPERSIZE);
		for(int i=0;i<8;i++){
			zdExcel.setCellWidth(sheet, i, 1);
		}
		zdExcel.export("考勤信息");
	}
	
	//删除考勤信息
	public String deleteInfo(){
		if(StringUtils.isNotEmpty(id)){
			try {
				officeAttendanceInfoService.delete(new String[]{id});
				promptMessageDto.setOperateSuccess(true);
				promptMessageDto.setPromptMessage("删除成功");
			} catch (Exception e) 
			{
				promptMessageDto.setOperateSuccess(false);
				promptMessageDto.setErrorMessage("删除失败:"+e.getCause().getMessage());
			}
		}else{
			promptMessageDto.setOperateSuccess(false);
			promptMessageDto.setErrorMessage("删除失败:找不到删除的对象");
		}
		
		return SUCCESS;
	}
	
	public OfficeAttendanceInfo getOfficeAttendanceInfo() {
		return officeAttendanceInfo;
	}

	public void setOfficeAttendanceInfo(OfficeAttendanceInfo officeAttendanceInfo) {
		this.officeAttendanceInfo = officeAttendanceInfo;
	}

	public List<OfficeAttendanceInfo> getOfficeAttendanceInfoList() {
		return officeAttendanceInfoList;
	}

	public void setOfficeAttendanceInfoList(
			List<OfficeAttendanceInfo> officeAttendanceInfoList) {
		this.officeAttendanceInfoList = officeAttendanceInfoList;
	}

	public void setOfficeAttendanceInfoService(
			OfficeAttendanceInfoService officeAttendanceInfoService) {
		this.officeAttendanceInfoService = officeAttendanceInfoService;
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

	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	public String getSearchName() {
		return searchName;
	}

	public void setSearchName(String searchName) {
		this.searchName = searchName;
	}

	public List<Dept> getDeptList() {
		deptList=deptService.getDepts(getUnitId());
		return deptList;
	}

	public void setDeptList(List<Dept> deptList) {
		this.deptList = deptList;
	}

	public void setDeptService(DeptService deptService) {
		this.deptService = deptService;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
