package net.zdsoft.office.dutyweekly.action;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.zdsoft.eis.base.common.entity.BasicClass;
import net.zdsoft.eis.base.common.entity.CustomRole;
import net.zdsoft.eis.base.common.entity.CustomRoleUser;
import net.zdsoft.eis.base.common.entity.Grade;
import net.zdsoft.eis.base.common.entity.Unit;
import net.zdsoft.eis.base.common.service.BasicClassService;
import net.zdsoft.eis.base.common.service.CustomRoleService;
import net.zdsoft.eis.base.common.service.CustomRoleUserService;
import net.zdsoft.eis.base.common.service.GradeService;
import net.zdsoft.eis.base.common.service.UnitService;
import net.zdsoft.eis.frame.action.PageSemesterAction;
import net.zdsoft.keel.util.DateUtils;
import net.zdsoft.keel.util.ServletUtils;
import net.zdsoft.leadin.doc.DocumentHandler;
import net.zdsoft.leadin.util.excel.ZdCell;
import net.zdsoft.leadin.util.excel.ZdExcel;
import net.zdsoft.leadin.util.excel.ZdStyle;
import net.zdsoft.office.dutyweekly.entity.OfficeDutyProject;
import net.zdsoft.office.dutyweekly.entity.OfficeDutyRemark;
import net.zdsoft.office.dutyweekly.entity.OfficeDutyWeekly;
import net.zdsoft.office.dutyweekly.entity.OfficeWeeklyApply;
import net.zdsoft.office.dutyweekly.service.OfficeDutyProjectService;
import net.zdsoft.office.dutyweekly.service.OfficeDutyRemarkService;
import net.zdsoft.office.dutyweekly.service.OfficeDutyWeeklyService;
import net.zdsoft.office.dutyweekly.service.OfficeWeeklyApplyService;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.tools.ant.filters.StringInputStream;

@SuppressWarnings("serial")
public class OfficeDutyWeeklyAction extends PageSemesterAction{
	
	private OfficeDutyWeeklyService officeDutyWeeklyService;
	private OfficeDutyProjectService officeDutyProjectService;
	private OfficeWeeklyApplyService officeWeeklyApplyService;
	private OfficeDutyRemarkService officeDutyRemarkService;
	private BasicClassService basicClassService;
	private UnitService unitService;
	private GradeService gradeService;
	private CustomRoleService customRoleService;
	private CustomRoleUserService customRoleUserService;
	private OfficeDutyWeekly officeDutyWeekly=new OfficeDutyWeekly();
	private List<OfficeDutyWeekly> officeDutyWeeklies=new ArrayList<OfficeDutyWeekly>();
	private OfficeWeeklyApply officeWeeklyApply=new OfficeWeeklyApply();
	private OfficeDutyProject officeDutyProject=new OfficeDutyProject();
	private List<OfficeDutyProject> officeDutyProjects=new ArrayList<OfficeDutyProject>();
	private OfficeDutyRemark officeDutyRemark=new OfficeDutyRemark();
	private List<OfficeDutyRemark> officeDutyRemarks=new ArrayList<OfficeDutyRemark>();
	private List<BasicClass> eisuClasss=new ArrayList<BasicClass>();//班级列表
	private List<BasicClass> classList;
	private String id;//classId
	
	private String dutyClassId;//值周班级
	
	private String officeDutyWeekId;
	private String[] checkid;//项目选中
	private Date dutyDate;
	private Map<String,OfficeWeeklyApply> applyMap=new HashMap<String, OfficeWeeklyApply>();
	private Map<String,OfficeWeeklyApply> totalMap=new HashMap<String, OfficeWeeklyApply>();
	private String[] applyRooms;
	private String[] fullSore;
	private String dutyWeeklyId;
	private String dutyRemark;
	
	private Date dutyStartTime;
	private Date dutyEndTime;
	private String week;//周次
	
	private String semesters;
	private String years;
	private Map<String,String> countMap=new HashMap<String, String>();
	private List<String> coreList=new ArrayList<String>();
	private Map<String,String> coreMap=new HashMap<String, String>();
	private boolean admin;//超管
	
	public String execute(){
		return SUCCESS;
	}
	
	public String dutyWeeklyList(){
		officeDutyWeeklies=officeDutyWeeklyService.getOfficeDutyWeeklyByUnitIdPagess(getUnitId(),getCurrentSemester().getAcadyear(),getCurrentSemester().getSemester(),getPage());
		return SUCCESS;
	}
	public String dutyWeeklyEdit(){
		try {
			if(StringUtils.isNotBlank(officeDutyWeekId)){
				officeDutyWeekly=officeDutyWeeklyService.getOfficeDutyWeeklyById(officeDutyWeekId);
			}else{
				officeDutyWeekly=officeDutyWeeklyService.getFindMaxWeek(getUnitId(), getCurrentSemester().getAcadyear(), getCurrentSemester().getSemester());
				if(officeDutyWeekly!=null){
					Date start=DateUtils.addDay(officeDutyWeekly.getWeekEndTime(),1);
					Date end=DateUtils.addDay(start, 6);
					officeDutyWeekly=new OfficeDutyWeekly();
					officeDutyWeekly.setWeekStartTime(start);
					officeDutyWeekly.setWeekEndTime(end);
				}else{
					officeDutyWeekly=new OfficeDutyWeekly();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	public String dutyWeeklySave(){
		try {
			boolean flag=officeDutyWeeklyService.isExistConflict(getUnitId(),getCurrentSemester().getAcadyear(),getCurrentSemester().getSemester(), officeDutyWeekly.getId(), officeDutyWeekly.getWeek(), officeDutyWeekly.getWeekStartTime(), officeDutyWeekly.getWeekEndTime());
			if(flag){
				promptMessageDto.setOperateSuccess(false);
				promptMessageDto.setErrorMessage("保存失败，该时间段有交叉或者周次重复，请重新选择！");
				return SUCCESS;
			}
			
			if(StringUtils.isNotBlank(officeDutyWeekly.getId())){
				List<OfficeWeeklyApply> officeWeeklyApplys=officeWeeklyApplyService.getOfficeWeeklyApplies(getUnitId(), officeDutyWeekly.getId());
				if(CollectionUtils.isNotEmpty(officeWeeklyApplys)){
					promptMessageDto.setOperateSuccess(false);
					promptMessageDto.setErrorMessage("修改失败,已存在值周登记记录");
					return SUCCESS;
				}
				officeDutyWeeklyService.update(officeDutyWeekly);
			}else{
				officeDutyWeekly.setCreateTime(new Date());
				officeDutyWeekly.setCreateUserId(getLoginUser().getUserId());
				officeDutyWeekly.setState(1);
				officeDutyWeekly.setUnitId(getUnitId());
				String years = this.getCurrentSemester().getAcadyear();
				officeDutyWeekly.setYear(years);
				officeDutyWeekly.setSemester(Integer.valueOf(this.getCurrentSemester().getSemester()));
				officeDutyWeeklyService.save(officeDutyWeekly);
			}
			promptMessageDto.setOperateSuccess(true);
			promptMessageDto.setPromptMessage("保存成功");
		} catch (Exception e) {
			e.printStackTrace();
			promptMessageDto.setOperateSuccess(false);
			promptMessageDto.setErrorMessage("保存失败");
		}
		return SUCCESS;
	}
	
	public String dutyWeeklyDelete(){
		try {
			if(StringUtils.isNotBlank(officeDutyWeekId)){
				List<OfficeWeeklyApply> officeWeeklyApplys=officeWeeklyApplyService.getOfficeWeeklyApplies(getUnitId(), officeDutyWeekId);
				if(CollectionUtils.isNotEmpty(officeWeeklyApplys)){
					promptMessageDto.setOperateSuccess(false);
					promptMessageDto.setErrorMessage("删除失败,已存在值周登记记录");
					return SUCCESS;
				}
				officeDutyWeeklyService.delete(new String[]{officeDutyWeekId});
			}
			promptMessageDto.setOperateSuccess(true);
			promptMessageDto.setPromptMessage("删除成功");
		} catch (Exception e) {
			e.printStackTrace();
			promptMessageDto.setOperateSuccess(false);
			promptMessageDto.setErrorMessage("删除失败");
		}
		return SUCCESS;
	}
	
	public String weeklyApplyList(){
		if(dutyDate==null){
			dutyDate=DateUtils.string2Date(DateUtils.date2String(new Date(), "yyyy-MM-dd"),"yyyy-MM-dd");
		}
		officeDutyWeekly=officeDutyWeeklyService.getOfficeDutyWeeklyByUnitIdAndDate(getUnitId(), dutyDate);
		if(officeDutyWeekly==null){
			officeDutyWeekly=new OfficeDutyWeekly();
		}
		if(officeDutyWeekly!=null&&StringUtils.contains(officeDutyWeekly.getDutyTeacher(), getLoginUser().getUserId())){
			officeDutyWeekly.setCanEdit(true);
		}
		officeDutyProjects=officeDutyProjectService.getOfficeDutyProjectByUnitIdListss(getUnitId(),getCurrentSemester().getAcadyear(),getCurrentSemester().getSemester());
		eisuClasss=basicClassService.getClasses(getUnitId());
		BasicClass removeClass=null;
		for (BasicClass item : eisuClasss) {
			if(StringUtils.equals(officeDutyWeekly.getDutyClass(), item.getId())){
				removeClass=item;
				break;
			}
		}
		if(removeClass!=null){
			dutyClassId=removeClass.getId();
		}
		eisuClasss.remove(removeClass);
		if(officeDutyWeekly!=null&&StringUtils.isNotBlank(officeDutyWeekly.getId())){
			applyMap=officeWeeklyApplyService.getOfficeWeeklyApplyMapByUnitIdAndWeeklyId(getUnitId(), officeDutyWeekly.getId(), dutyDate);
			officeDutyRemark=officeDutyRemarkService.getOfficeDutyRemarkByUnitIdAndOthers(getUnitId(), officeDutyWeekly.getId(), dutyDate);
			if(officeDutyRemark==null){
				officeDutyRemark=new OfficeDutyRemark();
			}
		}
		return SUCCESS;
	}
	
	public String weeklyQueryList(){
		if(dutyStartTime==null&&dutyEndTime==null&&StringUtils.isBlank(week)){
			dutyStartTime=DateUtils.string2Date(DateUtils.date2String(new Date(), "yyyy-MM-dd"),"yyyy-MM-dd");
			dutyEndTime=DateUtils.string2Date(DateUtils.date2String(new Date(), "yyyy-MM-dd"),"yyyy-MM-dd");
			officeDutyWeekly=officeDutyWeeklyService.getOfficeDutyWeeklyByUnitIdAndDate(getUnitId(), dutyEndTime);
			if(officeDutyWeekly!=null){
				totalMap=officeWeeklyApplyService.getOfficeCountMapByUnitIdAndWeeklyId(getUnitId(), officeDutyWeekly.getId(), dutyEndTime);
				officeDutyRemark=officeDutyRemarkService.getOfficeDutyRemarkByUnitIdAndOthers(getUnitId(), officeDutyWeekly.getId(), dutyEndTime);
			}
			if(officeDutyRemark!=null&&StringUtils.isNotBlank(officeDutyRemark.getRemark())){
				String remark=officeDutyRemark.getRemark();
				String dateStr=DateUtils.date2String(dutyStartTime,"yyyy-MM-dd");
				officeDutyRemark.setRemarks(remark+"("+dateStr+")");
				officeDutyRemarks.add(officeDutyRemark);
			}
		}else if(dutyStartTime==null&&dutyEndTime==null&&StringUtils.isNotBlank(week)){
			officeDutyWeekly=officeDutyWeeklyService.getOfficeDutyWeeklyByUnitIdAnds(getUnitId(), this.getCurrentSemester().getAcadyear(), this.getCurrentSemester().getSemester(), week);
			dutyStartTime=officeDutyWeekly.getWeekStartTime();
			dutyEndTime=officeDutyWeekly.getWeekEndTime();
			if(officeDutyWeekly!=null){
				totalMap=officeWeeklyApplyService.getOfficeWMapCount(getUnitId(), officeDutyWeekly.getId(),null);
				officeDutyRemarks=officeDutyRemarkService.getOfficeDutyRemarksAndUnitIdAndMore(getUnitId(), officeDutyWeekly.getId(), null);
			}
		}else if(((dutyStartTime!=null&&dutyEndTime==null)||(dutyEndTime!=null&&dutyStartTime==null))&&StringUtils.isBlank(week)){
			officeDutyWeekly=officeDutyWeeklyService.getOfficeDutyWeeklyByUnitIdAndDate(getUnitId(), dutyStartTime==null?dutyEndTime:dutyStartTime);
			if(officeDutyWeekly!=null){
				totalMap=officeWeeklyApplyService.getOfficeCountMapByUnitIdAndWeeklyId(getUnitId(), officeDutyWeekly.getId(), dutyStartTime==null?dutyEndTime:dutyStartTime);
				officeDutyRemarks=officeDutyRemarkService.getOfficeDutyRemarksAndUnitIdAndMore(getUnitId(), officeDutyWeekly.getId(), new Date[]{dutyStartTime==null?dutyEndTime:dutyStartTime});
			}
		}else if(dutyStartTime!=null&&dutyEndTime!=null&&StringUtils.isNotBlank(week)){
			officeDutyWeekly=officeDutyWeeklyService.getOfficeDutyWeeklyByUnitIdAnds(getUnitId(), this.getCurrentSemester().getAcadyear(), this.getCurrentSemester().getSemester(), week);
			if(officeDutyWeekly!=null){
				dutyStartTime=officeDutyWeekly.getWeekStartTime();
				dutyEndTime=officeDutyWeekly.getWeekEndTime();
				if(officeDutyWeekly!=null&&dutyStartTime.compareTo(officeDutyWeekly.getWeekStartTime())>=0&&dutyEndTime.compareTo(officeDutyWeekly.getWeekEndTime())<=0){
					List<Date> dateList=new ArrayList<Date>();
					Date startTime=dutyStartTime;
					Date endTime=dutyEndTime;
					while(startTime.before(endTime)||startTime.compareTo(endTime)==0){
						dateList.add(startTime);
						startTime =DateUtils.addDay(startTime, 1);
					}
					totalMap=officeWeeklyApplyService.getOfficeWMapCount(getUnitId(), officeDutyWeekly.getId(),dateList.toArray(new Date[0]));
					officeDutyRemarks=officeDutyRemarkService.getOfficeDutyRemarksAndUnitIdAndMore(getUnitId(), officeDutyWeekly.getId(), dateList.toArray(new Date[0]));
				}
			}else{//按照日期来查询
				totalMap=new HashMap<String, OfficeWeeklyApply>();
			}
		}else if(dutyStartTime!=null&&dutyEndTime!=null&&StringUtils.isBlank(week)){
			List<Date> dateList=new ArrayList<Date>();
			Date startTime=dutyStartTime;
			Date endTime=dutyEndTime;
			while(startTime.before(endTime)||startTime.compareTo(endTime)==0){
				dateList.add(startTime);
				startTime =DateUtils.addDay(startTime, 1);
			}
			totalMap=officeWeeklyApplyService.getOfficeWMapCount(getUnitId(), null,dateList.toArray(new Date[0]));
			officeDutyRemarks=officeDutyRemarkService.getOfficeDutyRemarksAndUnitIdAndMore(getUnitId(), null, dateList.toArray(new Date[0]));
		}else{
			totalMap=new HashMap<String, OfficeWeeklyApply>();
		}
		if(officeDutyWeekly==null){
			officeDutyWeekly=new OfficeDutyWeekly();
		}
		if(MapUtils.isEmpty(totalMap)){
			totalMap=new HashMap<String, OfficeWeeklyApply>();
		}
		if(CollectionUtils.isEmpty(officeDutyRemarks)){
			officeDutyRemarks=new ArrayList<OfficeDutyRemark>();
		}
		officeDutyProjects=officeDutyProjectService.getOfficeDutyProjectByUnitIdListss(getUnitId(),getCurrentSemester().getAcadyear(),getCurrentSemester().getSemester());
		eisuClasss=basicClassService.getClasses(getUnitId());
		return SUCCESS;
	}
	
	public String weeklyQueryCount(){
//		if((dutyStartTime!=null&&dutyEndTime==null)||(dutyEndTime!=null&&dutyStartTime==null)){
//			countMap=officeWeeklyApplyService.getOfficeWMap(getUnitId(), years, semesters, week, dutyStartTime, dutyEndTime, new Date[]{dutyStartTime==null?dutyEndTime:dutyStartTime});
//		}else if(dutyStartTime!=null&&dutyEndTime!=null){
//			List<Date> dateList=new ArrayList<Date>();
//			Date startTime=dutyStartTime;
//			Date endTime=dutyEndTime;
//			while(startTime.before(endTime)||startTime.compareTo(endTime)==0){
//				dateList.add(startTime);
//				startTime =DateUtils.addDay(startTime, 1);
//			}
//			countMap=officeWeeklyApplyService.getOfficeWMap(getUnitId(), years, semesters, week, dutyStartTime, dutyEndTime,dateList.toArray(new Date[0]));
//		}else{
		countMap=officeWeeklyApplyService.getOfficeWMap(getUnitId(), years, semesters, week, null, null,null);
//		}
		for (int i = 5; i >= 2; i--) {
			coreList.add(i+"");
//			coreMap.put(i+"", (i-5)+"");
		}
		eisuClasss=basicClassService.getClasses(getUnitId());
		return SUCCESS;
	}
	
	public String saveApplyInfo(){
		try {
			officeWeeklyApplyService.saveOfficeWeeklyApply(getUnitId(), getLoginUser().getUserId(), applyRooms, fullSore, dutyWeeklyId, dutyRemark, dutyDate);
			promptMessageDto.setOperateSuccess(true);
			promptMessageDto.setPromptMessage("操作成功");
		} catch (Exception e) {
			e.printStackTrace();
			promptMessageDto.setOperateSuccess(false);
			promptMessageDto.setErrorMessage("操作失败");
		}
		return SUCCESS;
	}
	public String dutyProject(){//TODO
		officeDutyProjects=officeDutyProjectService.getOfficeDutyProjectByUnitIdListss(getUnitId(),getCurrentSemester().getAcadyear(),getCurrentSemester().getSemester());
		return SUCCESS;
	}
	public String dutyProjectAdd(){
		try {
			officeDutyProjects=officeDutyProjectService.getOfficeDutyProjectByUnitIdListss(getUnitId(),getCurrentSemester().getAcadyear(),getCurrentSemester().getSemester());
			for (OfficeDutyProject officeDutyP : officeDutyProjects) {
				if(StringUtils.equals(officeDutyProject.getProjectName(), officeDutyP.getProjectName())){
					promptMessageDto.setOperateSuccess(false);
					promptMessageDto.setErrorMessage("该检查项目已存在！");
					return SUCCESS;
				}
			}
			officeDutyProject.setUnitId(getUnitId());
			officeDutyProject.setYear(this.getCurrentSemester().getAcadyear());
			officeDutyProject.setSemester(Integer.valueOf(getCurrentSemester().getSemester()));
			officeDutyProjectService.save(officeDutyProject);
			promptMessageDto.setOperateSuccess(true);
			promptMessageDto.setPromptMessage("检查项目添加成功");
		} catch (Exception e) {
			e.printStackTrace();
			promptMessageDto.setOperateSuccess(false);
			promptMessageDto.setErrorMessage("检查项目添加失败");
		}
		return SUCCESS;
	}
	public String dutyProjectEdit(){
		try {
			String dutyName=officeDutyProject.getProjectName();
			officeDutyProject=officeDutyProjectService.getOfficeDutyProjectById(officeDutyProject.getId());
			officeDutyProjects=officeDutyProjectService.getOfficeDutyProjectByUnitIdListss(getUnitId(),getCurrentSemester().getAcadyear(),getCurrentSemester().getSemester());
			for (OfficeDutyProject officeDutyP : officeDutyProjects) {
				if(!StringUtils.equals(officeDutyProject.getId(), officeDutyP.getId())&&StringUtils.equals(dutyName, officeDutyP.getProjectName())){
					promptMessageDto.setOperateSuccess(false);
					promptMessageDto.setErrorMessage("该检查项目已存在！");
					return SUCCESS;
				}
			}
			officeDutyProject.setProjectName(dutyName);
			officeDutyProjectService.update(officeDutyProject);
			promptMessageDto.setOperateSuccess(true);
			promptMessageDto.setPromptMessage("检查项目名称修改成功");
		} catch (Exception e) {
			e.printStackTrace();
			promptMessageDto.setOperateSuccess(false);
			promptMessageDto.setErrorMessage("检查项目名称修改失败");
		}
		return SUCCESS;
	}
	public String dutyDown(){
		if(dutyStartTime==null&&dutyEndTime==null&&StringUtils.isBlank(week)){
			dutyStartTime=DateUtils.string2Date(DateUtils.date2String(new Date(), "yyyy-MM-dd"),"yyyy-MM-dd");
			dutyEndTime=DateUtils.string2Date(DateUtils.date2String(new Date(), "yyyy-MM-dd"),"yyyy-MM-dd");
			officeDutyWeekly=officeDutyWeeklyService.getOfficeDutyWeeklyByUnitIdAndDate(getUnitId(), dutyEndTime);
			totalMap=officeWeeklyApplyService.getOfficeCountMapByUnitIdAndWeeklyId(getUnitId(), officeDutyWeekly.getId(), dutyEndTime);
			officeDutyRemark=officeDutyRemarkService.getOfficeDutyRemarkByUnitIdAndOthers(getUnitId(), officeDutyWeekly.getId(), dutyEndTime);
			if(officeDutyRemark!=null&&StringUtils.isNotBlank(officeDutyRemark.getRemark())){
				String remark=officeDutyRemark.getRemark();
				String dateStr=DateUtils.date2String(dutyStartTime,"yyyy-MM-dd");
				officeDutyRemark.setRemarks(remark+"("+dateStr+")");
				officeDutyRemarks.add(officeDutyRemark);
			}
		}else if(dutyStartTime==null&&dutyEndTime==null&&StringUtils.isNotBlank(week)){
			officeDutyWeekly=officeDutyWeeklyService.getOfficeDutyWeeklyByUnitIdAnds(getUnitId(), this.getCurrentSemester().getAcadyear(), this.getCurrentSemester().getSemester(), week);
			if(officeDutyWeekly!=null){
				totalMap=officeWeeklyApplyService.getOfficeWMapCount(getUnitId(), officeDutyWeekly.getId(),null);
				officeDutyRemarks=officeDutyRemarkService.getOfficeDutyRemarksAndUnitIdAndMore(getUnitId(), officeDutyWeekly.getId(), null);
			}
		}else if(((dutyStartTime!=null&&dutyEndTime==null)||(dutyEndTime!=null&&dutyStartTime==null))&&StringUtils.isBlank(week)){
			officeDutyWeekly=officeDutyWeeklyService.getOfficeDutyWeeklyByUnitIdAndDate(getUnitId(), dutyStartTime==null?dutyEndTime:dutyStartTime);
			totalMap=officeWeeklyApplyService.getOfficeCountMapByUnitIdAndWeeklyId(getUnitId(), officeDutyWeekly.getId(), dutyStartTime==null?dutyEndTime:dutyStartTime);
			officeDutyRemarks=officeDutyRemarkService.getOfficeDutyRemarksAndUnitIdAndMore(getUnitId(), officeDutyWeekly.getId(), new Date[]{dutyStartTime==null?dutyEndTime:dutyStartTime});
		}else if(dutyStartTime!=null&&dutyEndTime!=null&&StringUtils.isNotBlank(week)){
			officeDutyWeekly=officeDutyWeeklyService.getOfficeDutyWeeklyByUnitIdAnds(getUnitId(), this.getCurrentSemester().getAcadyear(), this.getCurrentSemester().getSemester(), week);
			if(dutyStartTime.compareTo(officeDutyWeekly.getWeekStartTime())>=0&&dutyEndTime.compareTo(officeDutyWeekly.getWeekEndTime())<=0){
				List<Date> dateList=new ArrayList<Date>();
				SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
				Date startTime=dutyStartTime;
				Date endTime=dutyEndTime;
				while(startTime.before(endTime)||startTime.compareTo(endTime)==0){
					dateList.add(startTime);
					startTime =DateUtils.addDay(startTime, 1);
				}
				totalMap=officeWeeklyApplyService.getOfficeWMapCount(getUnitId(), officeDutyWeekly.getId(),dateList.toArray(new Date[0]));
				officeDutyRemarks=officeDutyRemarkService.getOfficeDutyRemarksAndUnitIdAndMore(getUnitId(), officeDutyWeekly.getId(), dateList.toArray(new Date[0]));
			}else{//按照日期来查询
				totalMap=new HashMap<String, OfficeWeeklyApply>();
			}
		}else if(dutyStartTime!=null&&dutyEndTime!=null&&StringUtils.isBlank(week)){
			List<Date> dateList=new ArrayList<Date>();
			Date startTime=dutyStartTime;
			Date endTime=dutyEndTime;
			while(startTime.before(endTime)||startTime.compareTo(endTime)==0){
				dateList.add(startTime);
				startTime =DateUtils.addDay(startTime, 1);
			}
			totalMap=officeWeeklyApplyService.getOfficeWMapCount(getUnitId(), null,dateList.toArray(new Date[0]));
			officeDutyRemarks=officeDutyRemarkService.getOfficeDutyRemarksAndUnitIdAndMore(getUnitId(), null, dateList.toArray(new Date[0]));
		}else{
			totalMap=new HashMap<String, OfficeWeeklyApply>();
		}
		if(officeDutyWeekly==null){
			officeDutyWeekly=new OfficeDutyWeekly();
		}
		officeDutyProjects=officeDutyProjectService.getOfficeDutyProjectByUnitIdListss(getUnitId(),getCurrentSemester().getAcadyear(),getCurrentSemester().getSemester());
		eisuClasss=basicClassService.getClasses(getUnitId());
		Unit unit=unitService.getUnit(getUnitId());
		
		try {
			Map<String,Object> dataMap=new HashMap<String, Object>();
			dataMap.put("officeDutyRemarks", officeDutyRemarks);
			dataMap.put("officeDutyWeekly", officeDutyWeekly);
			dataMap.put("totalMap", totalMap);
//			officeDutyProjects.addAll(officeDutyProjects);
			dataMap.put("officeDutyProjects", officeDutyProjects);
//			eisuClasss = eisuClasss.subList(0, 2);
			dataMap.put("eisuClasss", eisuClasss);
			dataMap.put("unit", unit);
			
			String s=DocumentHandler.createDocString(dataMap, "officeDutyWeeks.xml");
			InputStream in=new StringInputStream(s, "utf-8");
			ServletUtils.download(in, getRequest(), getResponse(), "值周记录.doc");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return NONE;
	}
	
	public String dutyDownCount(){//TODO
		countMap=officeWeeklyApplyService.getOfficeWMap(getUnitId(), years, semesters, week, null, null,null);
		for (int i = 5; i >= 2; i--) {
			coreList.add(i+"");
		}
		eisuClasss=basicClassService.getClasses(getUnitId());
		Unit unit=unitService.getUnit(getUnitId());
		
		ZdExcel zdExcel = new ZdExcel();
		ZdStyle style2 = new ZdStyle(ZdStyle.BOLD|ZdStyle.BORDER|ZdStyle.ALIGN_CENTER);
		ZdStyle style3 = new ZdStyle(ZdStyle.BORDER|ZdStyle.ALIGN_CENTER|ZdStyle.WRAP_TEXT);
		List<ZdCell>zdlist=new ArrayList<ZdCell>();
		
		zdlist.add(new ZdCell("班级名",1,style2));
		for (String tpl : coreList) {
			if(StringUtils.equals("2", tpl)){
				zdlist.add(new ZdCell("3分以下",1,style2));
			}else{
				zdlist.add(new ZdCell(tpl+"分",1,style2));
			}
		}
		
		zdExcel.add(new ZdCell(unit.getName()+"值周检查统计表", coreList.size()+1, 2, new ZdStyle(ZdStyle.BOLD|ZdStyle.ALIGN_CENTER|ZdStyle.WRAP_TEXT, 18)));
		zdExcel.add(zdlist.toArray(new ZdCell[0]));
		
		for(BasicClass x : eisuClasss){
			int index = 0;
			ZdCell[] cells = new ZdCell[coreList.size()+1];
			cells[index++] = new ZdCell(x.getClassnamedynamic(), 1, style3);
			for (String tpl : coreList) {
				String core=countMap.get(x.getId()+"_"+tpl);
				if(StringUtils.isNotBlank(core)){
					cells[index++] = new ZdCell(core, 1, style3);
				}else{
					cells[index++] = new ZdCell("0", 1, style3);
				}
			}
			zdExcel.add(cells);
		}
		Sheet sheet = zdExcel.createSheet("sheet1");
		for(int i=0;i<3;i++){
			zdExcel.setCellWidth(sheet, i, 2);
		}
		zdExcel.export("duty_weeklyCount");
		return NONE;
	}
	public String dutyProjectDelete(){
		try {
			List<OfficeWeeklyApply> officeWp=officeWeeklyApplyService.getOfficeWeeklyAppliesByUnitId(getUnitId(), checkid);
			if(CollectionUtils.isNotEmpty(officeWp)){
				promptMessageDto.setOperateSuccess(false);
				promptMessageDto.setErrorMessage("删除失败，要删除的检查项目存在值周登记记录!");
				return SUCCESS;
			}
			officeDutyProjectService.delete(checkid);
			promptMessageDto.setOperateSuccess(true);
			promptMessageDto.setPromptMessage("检查项目删除成功");
		} catch (Exception e) {
			e.printStackTrace();
			promptMessageDto.setOperateSuccess(false);
			promptMessageDto.setErrorMessage("检查项目删除失败");
		}
		return SUCCESS;
	}
	public List<String> getWeekTimeList(){
		List<String> list=new ArrayList<String>();
		  for(int i = 1; i < 27; i++){
		    list.add(String.valueOf(i));
		 }
		return  list;
	}
	
	public OfficeDutyWeekly getOfficeDutyWeekly() {
		return officeDutyWeekly;
	}
	public void setOfficeDutyWeekly(OfficeDutyWeekly officeDutyWeekly) {
		this.officeDutyWeekly = officeDutyWeekly;
	}
	public List<OfficeDutyWeekly> getOfficeDutyWeeklies() {
		return officeDutyWeeklies;
	}
	public void setOfficeDutyWeeklies(List<OfficeDutyWeekly> officeDutyWeeklies) {
		this.officeDutyWeeklies = officeDutyWeeklies;
	}
	public OfficeWeeklyApply getOfficeWeeklyApply() {
		return officeWeeklyApply;
	}
	public void setOfficeWeeklyApply(OfficeWeeklyApply officeWeeklyApply) {
		this.officeWeeklyApply = officeWeeklyApply;
	}
	public OfficeDutyProject getOfficeDutyProject() {
		return officeDutyProject;
	}
	public void setOfficeDutyProject(OfficeDutyProject officeDutyProject) {
		this.officeDutyProject = officeDutyProject;
	}

	public String getOfficeDutyWeekId() {
		return officeDutyWeekId;
	}

	public void setOfficeDutyWeekId(String officeDutyWeekId) {
		this.officeDutyWeekId = officeDutyWeekId;
	}

	public void setOfficeDutyWeeklyService(
			OfficeDutyWeeklyService officeDutyWeeklyService) {
		this.officeDutyWeeklyService = officeDutyWeeklyService;
	}

	public List<OfficeDutyProject> getOfficeDutyProjects() {
		return officeDutyProjects;
	}

	public void setOfficeDutyProjects(List<OfficeDutyProject> officeDutyProjects) {
		this.officeDutyProjects = officeDutyProjects;
	}

	public void setOfficeDutyProjectService(
			OfficeDutyProjectService officeDutyProjectService) {
		this.officeDutyProjectService = officeDutyProjectService;
	}

	public String[] getCheckid() {
		return checkid;
	}

	public void setCheckid(String[] checkid) {
		this.checkid = checkid;
	}

	public void setOfficeWeeklyApplyService(
			OfficeWeeklyApplyService officeWeeklyApplyService) {
		this.officeWeeklyApplyService = officeWeeklyApplyService;
	}

	public Date getDutyDate() {
		return dutyDate;
	}

	public void setDutyDate(Date dutyDate) {
		this.dutyDate = dutyDate;
	}

	public List<BasicClass> getEisuClasss() {
		return eisuClasss;
	}

	public void setEisuClasss(List<BasicClass> eisuClasss) {
		this.eisuClasss = eisuClasss;
	}

	public void setBasicClassService(BasicClassService basicClassService) {
		this.basicClassService = basicClassService;
	}

	public Map<String,OfficeWeeklyApply> getApplyMap() {
		return applyMap;
	}

	public void setApplyMap(Map<String,OfficeWeeklyApply> applyMap) {
		this.applyMap = applyMap;
	}

	public String[] getApplyRooms() {
		return applyRooms;
	}

	public void setApplyRooms(String[] applyRooms) {
		this.applyRooms = applyRooms;
	}

	public String[] getFullSore() {
		return fullSore;
	}

	public void setFullSore(String[] fullSore) {
		this.fullSore = fullSore;
	}

	public String getDutyWeeklyId() {
		return dutyWeeklyId;
	}

	public void setDutyWeeklyId(String dutyWeeklyId) {
		this.dutyWeeklyId = dutyWeeklyId;
	}

	public String getDutyRemark() {
		return dutyRemark;
	}

	public void setDutyRemark(String dutyRemark) {
		this.dutyRemark = dutyRemark;
	}

	public void setOfficeDutyRemarkService(
			OfficeDutyRemarkService officeDutyRemarkService) {
		this.officeDutyRemarkService = officeDutyRemarkService;
	}

	public OfficeDutyRemark getOfficeDutyRemark() {
		return officeDutyRemark;
	}

	public void setOfficeDutyRemark(OfficeDutyRemark officeDutyRemark) {
		this.officeDutyRemark = officeDutyRemark;
	}

	public List<OfficeDutyRemark> getOfficeDutyRemarks() {
		return officeDutyRemarks;
	}

	public void setOfficeDutyRemarks(List<OfficeDutyRemark> officeDutyRemarks) {
		this.officeDutyRemarks = officeDutyRemarks;
	}

	public Date getDutyStartTime() {
		return dutyStartTime;
	}

	public void setDutyStartTime(Date dutyStartTime) {
		this.dutyStartTime = dutyStartTime;
	}

	public Date getDutyEndTime() {
		return dutyEndTime;
	}

	public void setDutyEndTime(Date dutyEndTime) {
		this.dutyEndTime = dutyEndTime;
	}

	public String getWeek() {
		return week;
	}

	public void setWeek(String week) {
		this.week = week;
	}

	public Map<String, OfficeWeeklyApply> getTotalMap() {
		return totalMap;
	}

	public void setTotalMap(Map<String, OfficeWeeklyApply> totalMap) {
		this.totalMap = totalMap;
	}

	public String getSemesters() {
		return semesters;
	}

	public void setSemesters(String semesters) {
		this.semesters = semesters;
	}

	public String getYears() {
		return years;
	}

	public void setYears(String years) {
		this.years = years;
	}
	public String getYear() {
		if (StringUtils.isBlank(years)) {
			return this.getCurrentSemester().getAcadyear();
		}
		return years;
	}
	public String getSemester() {
		if (StringUtils.isBlank(semesters)) {
			return this.getCurrentSemester().getSemester();
		}
		return semesters;
	}
	public List<String> getYearList() {
		List<String> acadyears = new ArrayList<String>();
		String currentYear = DateUtils.date2String(new Date(), "yyyy");
		acadyears.add(Integer.parseInt(currentYear)+3+"-"+(Integer.parseInt(currentYear)+4));
		acadyears.add(Integer.parseInt(currentYear)+2+"-"+(Integer.parseInt(currentYear)+3));
		acadyears.add(Integer.parseInt(currentYear)+1+"-"+(Integer.parseInt(currentYear)+2));
		acadyears.add(Integer.parseInt(currentYear)+"-"+(Integer.parseInt(currentYear)+1));
		acadyears.add(Integer.parseInt(currentYear)-1+"-"+Integer.parseInt(currentYear));
		acadyears.add(Integer.parseInt(currentYear)-2+"-"+(Integer.parseInt(currentYear)-1));
		acadyears.add(Integer.parseInt(currentYear)-3+"-"+(Integer.parseInt(currentYear)-2));
		return acadyears;
	}
	public String getUnitName(){
		Unit unit=unitService.getUnit(getUnitId());
		if(unit!=null){
			return unit.getName();
		}else{
			return "";
		}
	}
	public String findClassList(){
		classList = basicClassService.getClassesByGrade(id);
		return SUCCESS;
	}
	public Map<String,String> getCountMap() {
		return countMap;
	}

	public void setCountMap(Map<String,String> countMap) {
		this.countMap = countMap;
	}

	public List<String> getCoreList() {
		return coreList;
	}

	public void setCoreList(List<String> coreList) {
		this.coreList = coreList;
	}

	public Map<String,String> getCoreMap() {
		return coreMap;
	}

	public void setCoreMap(Map<String,String> coreMap) {
		this.coreMap = coreMap;
	}

	public void setUnitService(UnitService unitService) {
		this.unitService = unitService;
	}
	public boolean isAdmin() {
		CustomRole role = customRoleService.getCustomRoleByRoleCode(getUnitId(), "office_duty_weekly");
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
	public void setAdmin(boolean admin) {
		this.admin = admin;
	}
	public String getWeeks() {
        int i;
		if (StringUtils.isBlank(week)) {
			week = officeDutyWeeklyService.findMaxWeek(getUnitId(),this.getCurrentSemester().getAcadyear(),this.getCurrentSemester().getSemester());
            if (week == null || week.equals("")) {
                week = "1";
            }
            else {
                i = Integer.parseInt(week);
                week = Integer.toString(i + 1);
            }
		}
		return week;
	}
	public List<Grade> getGradesList() {
		return gradeService.getGrades(getUnitId());
	}
	public void setCustomRoleService(CustomRoleService customRoleService) {
		this.customRoleService = customRoleService;
	}

	public void setCustomRoleUserService(CustomRoleUserService customRoleUserService) {
		this.customRoleUserService = customRoleUserService;
	}

	public void setGradeService(GradeService gradeService) {
		this.gradeService = gradeService;
	}

	public List<BasicClass> getClassList() {
		return classList;
	}

	public void setClassList(List<BasicClass> classList) {
		this.classList = classList;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDutyClassId() {
		return dutyClassId;
	}

	public void setDutyClassId(String dutyClassId) {
		this.dutyClassId = dutyClassId;
	}
	
}
