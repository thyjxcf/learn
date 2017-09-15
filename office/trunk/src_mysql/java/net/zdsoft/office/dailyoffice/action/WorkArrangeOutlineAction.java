package net.zdsoft.office.dailyoffice.action;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.zdsoft.eis.base.common.entity.CurrentSemester;
import net.zdsoft.eis.base.common.entity.CustomRole;
import net.zdsoft.eis.base.common.entity.CustomRoleUser;
import net.zdsoft.eis.base.common.entity.Dept;
import net.zdsoft.eis.base.common.entity.SystemIni;
import net.zdsoft.eis.base.common.entity.Unit;
import net.zdsoft.eis.base.common.service.CustomRoleService;
import net.zdsoft.eis.base.common.service.CustomRoleUserService;
import net.zdsoft.eis.base.common.service.DeptService;
import net.zdsoft.eis.frame.action.PageSemesterAction;
import net.zdsoft.keel.util.DateUtils;
import net.zdsoft.keel.util.UUIDUtils;
import net.zdsoft.leadin.util.excel.ZdCell;
import net.zdsoft.leadin.util.excel.ZdExcel;
import net.zdsoft.leadin.util.excel.ZdStyle;
import net.zdsoft.office.dailyoffice.entity.OfficeWorkArrangeContent;
import net.zdsoft.office.dailyoffice.entity.OfficeWorkArrangeDetail;
import net.zdsoft.office.dailyoffice.entity.OfficeWorkArrangeOutline;
import net.zdsoft.office.dailyoffice.service.OfficeWorkArrangeContentService;
import net.zdsoft.office.dailyoffice.service.OfficeWorkArrangeDetailService;
import net.zdsoft.office.dailyoffice.service.OfficeWorkArrangeOutlineService;
import net.zdsoft.office.util.Constants;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Sheet;

public class WorkArrangeOutlineAction extends PageSemesterAction{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2660327487040599858L;
	
	private DeptService deptService;
	private CustomRoleService customRoleService;
	private CustomRoleUserService customRoleUserService;
	private OfficeWorkArrangeDetailService officeWorkArrangeDetailService;
	private OfficeWorkArrangeOutlineService officeWorkArrangeOutlineService;
	private OfficeWorkArrangeContentService officeWorkArrangeContentService;
	
	private String state;
	private String deptId;
	private String searchYear;
	private String workOutlineId;//工作大纲id
	private String detailId;//工作安排id
	private String contentId;//工作内容id
	private boolean isDeptManage; //是否是部门负责人
	private boolean isWeekworkManage; //是否是周工作安排管理者
	private boolean canEdit = false;//学校端：当前学年学期的工作大纲，教育局端：当前年度  可以新增
	
	private OfficeWorkArrangeDetail officeWorkArrangeDetail;
	private OfficeWorkArrangeContent officeWorkArrangeContent;
	private OfficeWorkArrangeOutline officeWorkArrangeOutline;
	
	private Date[] workDates;
	private String[] contents;
	private String[] deptIds;
	private String[] places;
	private String[] workStartTime;
	private String[] workEndTime;
	private String[] attendees;
	private String remark;//备注
	
	private List<String> acadyears;
	private List<Dept> deptList;
	private List<OfficeWorkArrangeOutline> officeWorkArrangeOutlineList;
	private List<OfficeWorkArrangeDetail> officeWorkArrangeDetailList;
	
	private Map<Date, List<OfficeWorkArrangeContent>> officeWorkArrangeContentsMap;
	private Map<Date, String> dateMap;
	
	private String currentDeptId;//当前部门id
	private String currentDeptName;//当前部门名称
	
	private boolean useNewFields;//是否使用新字段（如时间、参与人员）
	
	@Override
	public String execute() throws Exception {
		//判断是否是周工作安排管理者
		isWeekworkManage = isPracticeAdmin(OfficeWorkArrangeOutline.WEEKWORK_MANAGE);
		//判断是否是有上报权限
		isDeptManage = isPracticeAdmin(OfficeWorkArrangeOutline.WEEKWORK_REPORT);
		return SUCCESS;
	}

	public String outlineList() {
		//学校端 获取学年学期
		if(getLoginInfo().getUnitClass() == Unit.UNIT_CLASS_SCHOOL){
			CurrentSemester semester = getCurrentSemester();
			if(semester!=null && getAcadyear().equals(semester.getAcadyear()) && getSemester().equals(semester.getSemester())){
				canEdit = true;
			}
			acadyears = getAcadyearList();
			officeWorkArrangeOutlineList = officeWorkArrangeOutlineService.getOfficeWorkArrangeOutlines(getUnitId(), getAcadyear(), getSemester(), getPage());
		}else{
			String currentYear = DateUtils.date2String(new Date(), "yyyy");
			if(currentYear.equals(getSearchYear())){
				canEdit = true;
			}
			officeWorkArrangeOutlineList = officeWorkArrangeOutlineService.getOfficeWorkArrangeOutlines(getUnitId(), getSearchYear(), getPage());
		}
		return SUCCESS;
	}
	
	public String outlineEdit() {
		if(StringUtils.isNotBlank(workOutlineId)) {
			officeWorkArrangeOutline = officeWorkArrangeOutlineService.getOfficeWorkArrangeOutlineById(workOutlineId);
			boolean flag = officeWorkArrangeDetailService.isExistDetailByOutlineId(officeWorkArrangeOutline.getId());
			if(flag) {
				officeWorkArrangeOutline.setUse(true);
			}
		}else{
			officeWorkArrangeOutline = new OfficeWorkArrangeOutline();
			StringBuffer sbf = new StringBuffer();
			sbf.append(getLoginInfo().getUnitName());
			if(getLoginInfo().getUnitClass() == Unit.UNIT_CLASS_EDU){
				sbf.append(getSearchYear()).append("年度第周行事历");
			}else{
				CurrentSemester currentSemester = getCurrentSemester();
				if(currentSemester.getSemester().equals("1")){
					sbf.append(currentSemester.getAcadyear().substring(0,4)).append("学年第一学期").append("第周行事历");
				}else{
					sbf.append(currentSemester.getAcadyear().substring(0,4)).append("学年第二学期").append("第周行事历");
				}
			}
			officeWorkArrangeOutline.setName(sbf.toString());
		}
		return SUCCESS;
	}
	
	public String outlineSave() {
		try {
			boolean flag = officeWorkArrangeOutlineService.isExistConflict(getUnitId(), officeWorkArrangeOutline.getId(), officeWorkArrangeOutline.getStartTime(), officeWorkArrangeOutline.getEndTime());
			if(flag) {
				promptMessageDto.setOperateSuccess(false);
				promptMessageDto.setErrorMessage("保存失败，该时间段跟已经维护过的时间段有交叉！");
				return SUCCESS;
			}
			
			if(StringUtils.isBlank(officeWorkArrangeOutline.getId())) {
				if(getLoginInfo().getUnitClass() == Unit.UNIT_CLASS_SCHOOL){
					officeWorkArrangeOutline.setAcadyear(getAcadyear());
					officeWorkArrangeOutline.setSemester(getSemester());
				}
				officeWorkArrangeOutline.setCreateTime(new Date());
				officeWorkArrangeOutline.setUnitId(getUnitId());
				officeWorkArrangeOutline.setCreateUserId(getLoginUser().getUserId());
				officeWorkArrangeOutline.setState(String.valueOf(Constants.APPLY_STATE_NEED_AUDIT));//默认未发布
				officeWorkArrangeOutlineService.save(officeWorkArrangeOutline);
			}else {
				officeWorkArrangeOutlineService.update(officeWorkArrangeOutline);
			}
			promptMessageDto.setOperateSuccess(true);
			promptMessageDto.setPromptMessage("保存成功！");
		} catch (Exception e) {
			promptMessageDto.setOperateSuccess(false);
			promptMessageDto.setErrorMessage("保存失败！");
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	public String outlineDelete() {
		try {
			if(StringUtils.isNotBlank(workOutlineId)) {
				boolean flag = officeWorkArrangeDetailService.isExistDetailByOutlineId(workOutlineId);
				if(flag) {
					promptMessageDto.setOperateSuccess(false);
					promptMessageDto.setErrorMessage("删除失败，该大纲下已有工作安排！");
					return SUCCESS;
				}
				officeWorkArrangeOutlineService.delete(new String[] {workOutlineId});
			}else {
				promptMessageDto.setOperateSuccess(false);
				promptMessageDto.setErrorMessage("传入数据有误！");
				return SUCCESS;
			}
			promptMessageDto.setOperateSuccess(true);
			promptMessageDto.setPromptMessage("删除成功！");
		} catch (Exception e) {
			promptMessageDto.setOperateSuccess(false);
			promptMessageDto.setErrorMessage("删除失败！");
		}
		return SUCCESS;
	}
	
	public String arrangeList() {
		if(getLoginInfo().getUnitClass() == Unit.UNIT_CLASS_SCHOOL){
			acadyears = getAcadyearList();
			officeWorkArrangeOutlineList = officeWorkArrangeOutlineService.getOfficeWorkArrangeOutlines(getUnitId(), getAcadyear(), getSemester(), getPage());
		}else{
			officeWorkArrangeOutlineList = officeWorkArrangeOutlineService.getOfficeWorkArrangeOutlines(getUnitId(), getSearchYear(), getPage());
		}
		List<String> outLineIds = new ArrayList<String>();
		for(OfficeWorkArrangeOutline officeWorkArrangeOutline : officeWorkArrangeOutlineList){
			outLineIds.add(officeWorkArrangeOutline.getId());
		}
		Map<String, OfficeWorkArrangeDetail> owadMap = officeWorkArrangeDetailService.getOfficeWorkArrangeDetailMap(outLineIds.toArray(new String[0]),getLoginInfo().getUser().getDeptid());
		for(OfficeWorkArrangeOutline officeWorkArrangeOutline : officeWorkArrangeOutlineList) {
			if(owadMap.containsKey(officeWorkArrangeOutline.getId())) {
				officeWorkArrangeOutline.setRemark(owadMap.get(officeWorkArrangeOutline.getId()).getRemark());
				officeWorkArrangeOutline.setDetailState(owadMap.get(officeWorkArrangeOutline.getId()).getState());
			}
		}
		return SUCCESS;
	}
	
	public String arrangeEdit() {
		officeWorkArrangeOutline = officeWorkArrangeOutlineService.getOfficeWorkArrangeOutlineById(workOutlineId);
		officeWorkArrangeDetail = officeWorkArrangeDetailService.getOfficeWorkArrangeDetail(workOutlineId, getLoginInfo().getUser().getDeptid());
		currentDeptId = getLoginInfo().getUser().getDeptid();
		currentDeptName = getLoginInfo().getUser().getDeptName();
		
		if(officeWorkArrangeDetail == null) {
			officeWorkArrangeDetail = new OfficeWorkArrangeDetail();
		}
		return SUCCESS;
	}
	
	//TODO
	public String arrangeSave() {
		try {
			if(StringUtils.isBlank(officeWorkArrangeDetail.getId())) {
				officeWorkArrangeDetail.setId(UUIDUtils.newId());
				List<OfficeWorkArrangeContent> owacList = new ArrayList<OfficeWorkArrangeContent>();
				for(int i=0;i<workDates.length;i++){
					if(workDates[i] != null){
						OfficeWorkArrangeContent owac = new OfficeWorkArrangeContent();
						owac.setOutlineId(officeWorkArrangeDetail.getOutlineId());
						owac.setDetailId(officeWorkArrangeDetail.getId());
						owac.setWorkDate(workDates[i]);
						owac.setContent(contents[i]);
						owac.setDeptIds(deptIds[i]);
						owac.setPlace(places[i]);
						if(this.getUseNewFields()){
							owac.setWorkStartTime(workStartTime[i]);
							owac.setWorkEndTime(workEndTime[i]);
							owac.setAttendees(attendees[i]);
						}
						owac.setState(officeWorkArrangeDetail.getState());
						owacList.add(owac);
					}
				}
				officeWorkArrangeDetail.setUnitId(getUnitId());
				officeWorkArrangeDetail.setDeptId(getLoginInfo().getUser().getDeptid());
				officeWorkArrangeDetail.setCreateUserId(getLoginUser().getUserId());
				officeWorkArrangeDetail.setOfficeWorkArrangeContents(owacList);
				officeWorkArrangeDetailService.save(officeWorkArrangeDetail);
			}else {
				List<OfficeWorkArrangeContent> owacList = new ArrayList<OfficeWorkArrangeContent>();
				for(int i=0;i<workDates.length;i++){
					if(workDates[i] != null){
						OfficeWorkArrangeContent owac = new OfficeWorkArrangeContent();
						owac.setOutlineId(officeWorkArrangeDetail.getOutlineId());
						owac.setDetailId(officeWorkArrangeDetail.getId());
						owac.setWorkDate(workDates[i]);
						owac.setContent(contents[i]);
						owac.setDeptIds(deptIds[i]);
						owac.setPlace(places[i]);
						if(this.getUseNewFields()){
							owac.setWorkStartTime(workStartTime[i]);
							owac.setWorkEndTime(workEndTime[i]);
							owac.setAttendees(attendees[i]);
						}
						owac.setState(officeWorkArrangeDetail.getState());
						owacList.add(owac);
					}
				}
				officeWorkArrangeDetail.setOfficeWorkArrangeContents(owacList);
				officeWorkArrangeDetailService.update(officeWorkArrangeDetail);
			}
			promptMessageDto.setOperateSuccess(true);
			promptMessageDto.setPromptMessage("操作成功！");
		} catch (Exception e) {
			e.printStackTrace();
			promptMessageDto.setOperateSuccess(false);
			promptMessageDto.setErrorMessage("操作失败！");
		}
		return SUCCESS;
	}
	
	
	public String arrangeAudit() {
		if(getLoginInfo().getUnitClass() == Unit.UNIT_CLASS_SCHOOL){
			acadyears = getAcadyearList();
			officeWorkArrangeOutlineList = officeWorkArrangeOutlineService.getOfficeWorkArrangeOutlines(getUnitId(), getAcadyear(), getSemester(), null);
		}else{
			officeWorkArrangeOutlineList = officeWorkArrangeOutlineService.getOfficeWorkArrangeOutlines(getUnitId(), getSearchYear(), null);
		}
		return SUCCESS;
	}
	
	
	public String auditList() {
		officeWorkArrangeOutline = officeWorkArrangeOutlineService.getOfficeWorkArrangeOutlineById(workOutlineId);
		officeWorkArrangeContentsMap = officeWorkArrangeContentService.getOfficeWorkArrangeContentsMapByOutLineId(workOutlineId);
		dateForWeek(officeWorkArrangeContentsMap.keySet());
		officeWorkArrangeDetailList = officeWorkArrangeDetailService.getOfficeWorkArrangeDetailListByOutLineId(workOutlineId);
		return SUCCESS;
	}
	
	public void dateForWeek(Set<Date> dateSet){
		dateMap = new HashMap<Date, String>();
		for(Date date:dateSet){
			Calendar c = Calendar.getInstance();
			c.setTime(date);
			int dayForWeek = 0;
			if(c.get(Calendar.DAY_OF_WEEK) == 1){
				dayForWeek = 7;
			}else{
				dayForWeek = c.get(Calendar.DAY_OF_WEEK) - 1;
			}
			String s="";
			if(dayForWeek==7){
				s="周日";
			}else if(dayForWeek==6){
				s="周六";
			}else if(dayForWeek==5){
				s="周五";
			}else if(dayForWeek==4){
				s="周四";
			}else if(dayForWeek==3){
				s="周三";
			}else if(dayForWeek==2){
				s="周二";
			}else if(dayForWeek==1){
				s="周一";
			}
			dateMap.put(date, s);
		} 
	}
	
	public String detailInfo(){
		officeWorkArrangeDetail = officeWorkArrangeDetailService.getOfficeWorkArrangeDetailById(detailId);
		return SUCCESS;
	}
	
	public String contentInfo(){
		officeWorkArrangeContent = officeWorkArrangeContentService.getOfficeWorkArrangeContentById(contentId);
		officeWorkArrangeOutline = officeWorkArrangeOutlineService.getOfficeWorkArrangeOutlineById(officeWorkArrangeContent.getOutlineId());
		return SUCCESS;
	}
	public String deleteInfo(){
		try{
			officeWorkArrangeContentService.delete(new String[]{contentId});
			promptMessageDto.setOperateSuccess(true);
			promptMessageDto.setPromptMessage("删除成功！");
		}catch (Exception e) {
			promptMessageDto.setOperateSuccess(false);
			promptMessageDto.setErrorMessage("删除失败！");
		}
		return SUCCESS;
	}
	
	public String publishInfo() {
		try{
			officeWorkArrangeOutlineService.updateState(workOutlineId, state);
			promptMessageDto.setOperateSuccess(true);
			promptMessageDto.setPromptMessage("操作成功！");
		}catch(Exception e) {
			e.printStackTrace();
			promptMessageDto.setOperateSuccess(false);
			promptMessageDto.setErrorMessage("操作失败！");
		}
		return SUCCESS;
	}
	
	public String modifyRemark(){
		try {
			officeWorkArrangeDetailService.updateRemark(detailId, remark);
			promptMessageDto.setOperateSuccess(true);
			promptMessageDto.setPromptMessage("操作成功！");
		} catch (Exception e) {
			e.printStackTrace();
			promptMessageDto.setOperateSuccess(false);
			promptMessageDto.setErrorMessage("操作失败！");
		}
		return SUCCESS;
	}
	
	public String modifyContent(){
		try {
			officeWorkArrangeContentService.update(officeWorkArrangeContent);
			promptMessageDto.setOperateSuccess(true);
			promptMessageDto.setPromptMessage("操作成功！");
		} catch (Exception e) {
			e.printStackTrace();
			promptMessageDto.setOperateSuccess(false);
			promptMessageDto.setErrorMessage("操作失败！");
		}
		return SUCCESS;
	}
	
	public String arrangeReportAdmin() {
		if(getLoginInfo().getUnitClass() == Unit.UNIT_CLASS_SCHOOL){
			acadyears = getAcadyearList();
			officeWorkArrangeOutlineList = officeWorkArrangeOutlineService.getOfficeWorkArrangeOutlineList(getUnitId(), getAcadyear(), getSemester(), String.valueOf(Constants.APPLY_STATE_PASS));
		}else{
			officeWorkArrangeOutlineList = officeWorkArrangeOutlineService.getOfficeWorkArrangeOutlineList(getUnitId(), getSearchYear(), String.valueOf(Constants.APPLY_STATE_PASS));
		}
		return SUCCESS;
	}
	
	public String arrangeReportList() {
		officeWorkArrangeOutline = officeWorkArrangeOutlineService.getOfficeWorkArrangeOutlineById(workOutlineId);
		officeWorkArrangeContentsMap = officeWorkArrangeContentService.getOfficeWorkArrangeContentsMapByOutLineId(workOutlineId);
		dateForWeek(officeWorkArrangeContentsMap.keySet());
		officeWorkArrangeDetailList = officeWorkArrangeDetailService.getOfficeWorkArrangeDetailListByOutLineId(workOutlineId);
		return SUCCESS;
	}
	
	public String arrangeReportExport() {//TODO
		officeWorkArrangeContentsMap = officeWorkArrangeContentService.getOfficeWorkArrangeContentsMapByOutLineId(workOutlineId);
		dateForWeek(officeWorkArrangeContentsMap.keySet());
		officeWorkArrangeDetailList = officeWorkArrangeDetailService.getOfficeWorkArrangeDetailListByOutLineId(workOutlineId);
		
		ZdExcel zdExcel = new ZdExcel();
		ZdStyle style2 = new ZdStyle(ZdStyle.BOLD|ZdStyle.BORDER|ZdStyle.ALIGN_CENTER);
		ZdStyle style3 = new ZdStyle(ZdStyle.BORDER|ZdStyle.ALIGN_CENTER|ZdStyle.WRAP_TEXT);
		List<ZdCell>zdlist=new ArrayList<ZdCell>();
		
		zdlist.add(new ZdCell("日期",1,style2));
		if(this.getUseNewFields()){
			zdlist.add(new ZdCell("时间",1,style2));
		}
		zdlist.add(new ZdCell("工作内容",1,style2));
		zdlist.add(new ZdCell("责任部门",1,style2));
		if(this.getUseNewFields()){
			zdlist.add(new ZdCell("参与人员",1,style2));
		}
		zdlist.add(new ZdCell("地点",1,style2));
		
		zdExcel.add(new ZdCell("周工作汇总表", zdlist.size(), 2, new ZdStyle(ZdStyle.BOLD|ZdStyle.ALIGN_CENTER|ZdStyle.WRAP_TEXT, 18)));
		
		if(!officeWorkArrangeContentsMap.isEmpty()){
			zdExcel.add(zdlist.toArray(new ZdCell[0]));
		}
		
		DateFormat sdf = new SimpleDateFormat("MM-dd");
		for (Date key : officeWorkArrangeContentsMap.keySet()) {
			List<OfficeWorkArrangeContent> list = officeWorkArrangeContentsMap.get(key);
			if(list.size() > 0){
				boolean firstRow = true;
				for(OfficeWorkArrangeContent item : list){
					int index = 0;
					ZdCell[] cells = new ZdCell[zdlist.size()];
					
					if(firstRow){//第一行设置日期
						cells[index++] = new ZdCell(sdf.format(key)+"("+this.getDateMap().get(key)+")", 1, list.size(), style3);
						firstRow = false;
					}else{
						cells[index++] = new ZdCell();
					}
					
					if(this.getUseNewFields()){
						if(StringUtils.isBlank(item.getWorkStartTime())){
							cells[index++] = new ZdCell("", 1, style3);
						}else{
							cells[index++] = new ZdCell(item.getWorkStartTime()+"-"+item.getWorkEndTime(), 1, style3);
						}
					}
					
					cells[index++] = new ZdCell(item.getContent(), 1, style3);
					cells[index++] = new ZdCell(item.getDeptNames(), 1, style3);
					if(this.getUseNewFields()){
						cells[index++] = new ZdCell(item.getAttendees(), 1, style3);
					}
					cells[index++] = new ZdCell(item.getPlace(), 1, style3);
					
					zdExcel.add(cells);
				}
			}
		}
		
		ZdCell[] cells2 = new ZdCell[2];
		cells2[0] = new ZdCell("备注：",1, 2, style3);
		String remark = "";
		for(OfficeWorkArrangeDetail item : officeWorkArrangeDetailList){
			remark += item.getDeptName() + "：" + item.getRemark() + "；    ";
		}
		cells2[1] = new ZdCell(remark, zdlist.size()-1, 2, new ZdStyle(ZdStyle.BORDER|ZdStyle.WRAP_TEXT));
		zdExcel.add(cells2);
		
		Sheet sheet = zdExcel.createSheet("sheet1");
		for(int i=0;i<3;i++){
			zdExcel.setCellWidth(sheet, i, 2);
		}
		zdExcel.export("weekly_report");
		return NONE;
	}
	
	public String arrangeQuery() {
		if(getLoginInfo().getUnitClass() == Unit.UNIT_CLASS_SCHOOL){
			acadyears = getAcadyearList();
			officeWorkArrangeOutlineList = officeWorkArrangeOutlineService.getOfficeWorkArrangeOutlineList(getUnitId(), getAcadyear(), getSemester(), String.valueOf(Constants.APPLY_STATE_PASS));
//			workOutlineId = officeWorkArrangeOutlineService.getOfficeWorkArrangeOutline(getUnitId(), getAcadyear(), getSemester(), String.valueOf(Constants.APPLY_STATE_PASS));
		}else{
			officeWorkArrangeOutlineList = officeWorkArrangeOutlineService.getOfficeWorkArrangeOutlineList(getUnitId(), getSearchYear(), String.valueOf(Constants.APPLY_STATE_PASS));
//			workOutlineId = officeWorkArrangeOutlineService.getOfficeWorkArrangeOutline(getUnitId(), String.valueOf(Constants.APPLY_STATE_PASS));
		}
		deptList = deptService.getDepts(getUnitId());
		return SUCCESS;
	}
	
	//TODO
	public String arrangeQueryList() {
		officeWorkArrangeContentsMap = officeWorkArrangeContentService.getOfficeWorkArrangeContentsMapByOutLineId(workOutlineId, deptId);
		dateForWeek(officeWorkArrangeContentsMap.keySet());
		officeWorkArrangeDetailList = officeWorkArrangeDetailService.getOfficeWorkArrangeDetailListByOutLineId(workOutlineId, deptId);
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
	
	public List<String> getYearList() {
		List<String> acadyears = new ArrayList<String>();
		String currentYear = DateUtils.date2String(new Date(), "yyyy");
		acadyears.add(Integer.parseInt(currentYear)+3+"");
		acadyears.add(Integer.parseInt(currentYear)+2+"");
		acadyears.add(Integer.parseInt(currentYear)+1+"");
		acadyears.add(Integer.parseInt(currentYear)+"");
		acadyears.add(Integer.parseInt(currentYear)-1+"");
		acadyears.add(Integer.parseInt(currentYear)-2+"");
		acadyears.add(Integer.parseInt(currentYear)-3+"");
		return acadyears;
	}
	
	public OfficeWorkArrangeOutline getOfficeWorkArrangeOutline() {
		return officeWorkArrangeOutline;
	}

	public void setOfficeWorkArrangeOutline(
			OfficeWorkArrangeOutline officeWorkArrangeOutline) {
		this.officeWorkArrangeOutline = officeWorkArrangeOutline;
	}

	public List<OfficeWorkArrangeOutline> getOfficeWorkArrangeOutlineList() {
		return officeWorkArrangeOutlineList;
	}

	public void setOfficeWorkArrangeOutlineList(
			List<OfficeWorkArrangeOutline> officeWorkArrangeOutlineList) {
		this.officeWorkArrangeOutlineList = officeWorkArrangeOutlineList;
	}

	public List<OfficeWorkArrangeDetail> getOfficeWorkArrangeDetailList() {
		return officeWorkArrangeDetailList;
	}

	public void setOfficeWorkArrangeDetailList(
			List<OfficeWorkArrangeDetail> officeWorkArrangeDetailList) {
		this.officeWorkArrangeDetailList = officeWorkArrangeDetailList;
	}

	public String getWorkOutlineId() {
		return workOutlineId;
	}

	public void setWorkOutlineId(String workOutlineId) {
		this.workOutlineId = workOutlineId;
	}

	public String getDetailId() {
		return detailId;
	}

	public void setDetailId(String detailId) {
		this.detailId = detailId;
	}

	public String getContentId() {
		return contentId;
	}

	public void setContentId(String contentId) {
		this.contentId = contentId;
	}

	public String getSearchYear() {
		if (StringUtils.isBlank(searchYear)) {
			searchYear = DateUtils.date2String(new Date(), "yyyy");
		}
		return searchYear;
	}

	public void setSearchYear(String searchYear) {
		this.searchYear = searchYear;
	}
	
	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public void setOfficeWorkArrangeOutlineService(
			OfficeWorkArrangeOutlineService officeWorkArrangeOutlineService) {
		this.officeWorkArrangeOutlineService = officeWorkArrangeOutlineService;
	}

	public void setOfficeWorkArrangeDetailService(
			OfficeWorkArrangeDetailService officeWorkArrangeDetailService) {
		this.officeWorkArrangeDetailService = officeWorkArrangeDetailService;
	}
	
	public void setOfficeWorkArrangeContentService(
			OfficeWorkArrangeContentService officeWorkArrangeContentService) {
		this.officeWorkArrangeContentService = officeWorkArrangeContentService;
	}

	public OfficeWorkArrangeDetail getOfficeWorkArrangeDetail() {
		return officeWorkArrangeDetail;
	}

	public void setOfficeWorkArrangeDetail(
			OfficeWorkArrangeDetail officeWorkArrangeDetail) {
		this.officeWorkArrangeDetail = officeWorkArrangeDetail;
	}

	public List<Dept> getDeptList() {
		return deptList;
	}

	public void setDeptList(List<Dept> deptList) {
		this.deptList = deptList;
	}

	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	public void setDeptService(DeptService deptService) {
		this.deptService = deptService;
	}

	public void setCustomRoleService(CustomRoleService customRoleService) {
		this.customRoleService = customRoleService;
	}
	
	public void setCustomRoleUserService(CustomRoleUserService customRoleUserService) {
		this.customRoleUserService = customRoleUserService;
	}

	public boolean isDeptManage() {
		return isDeptManage;
	}

	public void setDeptManage(boolean isDeptManage) {
		this.isDeptManage = isDeptManage;
	}

	public boolean isWeekworkManage() {
		return isWeekworkManage;
	}

	public void setWeekworkManage(boolean isWeekworkManage) {
		this.isWeekworkManage = isWeekworkManage;
	}

	public List<String> getAcadyears() {
		return acadyears;
	}

	public void setAcadyears(List<String> acadyears) {
		this.acadyears = acadyears;
	}

	public boolean isCanEdit() {
		return canEdit;
	}

	public void setCanEdit(boolean canEdit) {
		this.canEdit = canEdit;
	}

	public Date[] getWorkDates() {
		return workDates;
	}

	public void setWorkDates(Date[] workDates) {
		this.workDates = workDates;
	}

	public String[] getContents() {
		return contents;
	}

	public void setContents(String[] contents) {
		this.contents = contents;
	}

	public String[] getDeptIds() {
		return deptIds;
	}

	public void setDeptIds(String[] deptIds) {
		this.deptIds = deptIds;
	}

	public String[] getPlaces() {
		return places;
	}

	public void setPlaces(String[] places) {
		this.places = places;
	}

	public String getCurrentDeptId() {
		return currentDeptId;
	}

	public void setCurrentDeptId(String currentDeptId) {
		this.currentDeptId = currentDeptId;
	}

	public String getCurrentDeptName() {
		return currentDeptName;
	}

	public void setCurrentDeptName(String currentDeptName) {
		this.currentDeptName = currentDeptName;
	}

	public Map<Date, List<OfficeWorkArrangeContent>> getOfficeWorkArrangeContentsMap() {
		return officeWorkArrangeContentsMap;
	}

	public void setOfficeWorkArrangeContentsMap(
			Map<Date, List<OfficeWorkArrangeContent>> officeWorkArrangeContentsMap) {
		this.officeWorkArrangeContentsMap = officeWorkArrangeContentsMap;
	}

	public OfficeWorkArrangeContent getOfficeWorkArrangeContent() {
		return officeWorkArrangeContent;
	}

	public void setOfficeWorkArrangeContent(
			OfficeWorkArrangeContent officeWorkArrangeContent) {
		this.officeWorkArrangeContent = officeWorkArrangeContent;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Map<Date, String> getDateMap() {
		return dateMap;
	}

	public void setDateMap(Map<Date, String> dateMap) {
		this.dateMap = dateMap;
	}

	public boolean getUseNewFields() {
		SystemIni systemIni = systemIniService.getSystemIni("WEEKWORK.USE.NEW.FIELDS");
		if(systemIni != null && "1".equals(systemIni.getNowValue()))//使用新字段
			useNewFields = true;
		else
			useNewFields = false;
		return useNewFields;
	}

	public void setUseNewFields(boolean useNewFields) {
		this.useNewFields = useNewFields;
	}

	public String[] getAttendees() {
		return attendees;
	}

	public void setAttendees(String[] attendees) {
		this.attendees = attendees;
	}

	public String[] getWorkStartTime() {
		return workStartTime;
	}

	public void setWorkStartTime(String[] workStartTime) {
		this.workStartTime = workStartTime;
	}

	public String[] getWorkEndTime() {
		return workEndTime;
	}

	public void setWorkEndTime(String[] workEndTime) {
		this.workEndTime = workEndTime;
	}
	
	
}
