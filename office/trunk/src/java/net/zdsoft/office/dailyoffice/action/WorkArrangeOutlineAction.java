package net.zdsoft.office.dailyoffice.action;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import net.zdsoft.eis.base.attachment.entity.Attachment;
import net.zdsoft.eis.base.attachment.service.AttachmentService;
import net.zdsoft.eis.base.common.entity.CurrentSemester;
import net.zdsoft.eis.base.common.entity.CustomRole;
import net.zdsoft.eis.base.common.entity.CustomRoleUser;
import net.zdsoft.eis.base.common.entity.Dept;
import net.zdsoft.eis.base.common.entity.SystemIni;
import net.zdsoft.eis.base.common.entity.Unit;
import net.zdsoft.eis.base.common.service.CustomRoleService;
import net.zdsoft.eis.base.common.service.CustomRoleUserService;
import net.zdsoft.eis.base.common.service.DeptService;
import net.zdsoft.eis.base.converter.service.ConverterFileTypeService;
import net.zdsoft.eis.base.storage.StorageFileUtils;
import net.zdsoft.eis.frame.action.PageSemesterAction;
import net.zdsoft.keel.util.DateUtils;
import net.zdsoft.keel.util.UUIDUtils;
import net.zdsoft.keelcnet.action.UploadFile;
import net.zdsoft.leadin.common.entity.BusinessTask;
import net.zdsoft.leadin.util.UUIDGenerator;
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
	private AttachmentService attachmentService;
	private ConverterFileTypeService converterFileTypeService;
	
	private String state;
	private String deptId;
	private String searchYear;
	private String workOutlineId;//工作大纲id
	private String detailId;//工作安排id
	private String contentId;//工作内容id
	private String removeAttachmentId;
	private boolean isDeptManage; //是否是部门负责人
	private boolean isWeekworkManage; //是否是周工作安排管理者
	private boolean canEdit = false;//学校端：当前学年学期的工作大纲，教育局端：当前年度  可以新增
	
	private OfficeWorkArrangeDetail officeWorkArrangeDetail=new OfficeWorkArrangeDetail();
	private OfficeWorkArrangeContent officeWorkArrangeContent=new OfficeWorkArrangeContent();
	private OfficeWorkArrangeOutline officeWorkArrangeOutline=new OfficeWorkArrangeOutline();
	
	private Date[] workDates;
	private String[] contents;
	private String[] arrangContents;
	private String[] deptIds;
	private String[] places;
	private String[] workStartTime;
	private String[] workEndTime;
	private String[] attendees;
	private String remark;//备注
	private boolean isTrue=false;
	private String fileSize;
	
	private List<String> acadyears;
	private List<Dept> deptList;
	private List<OfficeWorkArrangeOutline> officeWorkArrangeOutlineList;
	private List<OfficeWorkArrangeDetail> officeWorkArrangeDetailList;
	
	private Map<Date, List<OfficeWorkArrangeContent>> officeWorkArrangeContentsMap;
	private Map<Date, String> dateMap;
	
	private Map<Date,Integer> changeDate=new HashMap<Date, Integer>();
	
	private String currentDeptId;//当前部门id
	private String currentDeptName;//当前部门名称
	
	private boolean useNewFields;//是否使用新字段（如时间、参与人员）
	
	private boolean canView;
	
	private boolean arrangeQuery;
	
	public boolean isArrangeQuery() {
		return arrangeQuery;
	}

	public void setArrangeQuery(boolean arrangeQuery) {
		this.arrangeQuery = arrangeQuery;
	}

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
		if(officeWorkArrangeDetail==null) {
			officeWorkArrangeDetail=new OfficeWorkArrangeDetail();
			officeWorkArrangeDetail.setId(UUIDGenerator.getUUID());
		}else if(StringUtils.isBlank(officeWorkArrangeDetail.getId())){
			officeWorkArrangeDetail.setId(UUIDGenerator.getUUID());
		}
		else{
			List<Attachment> attlist=attachmentService.getAttachments(officeWorkArrangeDetail.getId(), Constants.WORK_ATTACHMENT);
			if(CollectionUtils.isNotEmpty(attlist)){
				for (Attachment attachment : attlist) {
					if(attachment.getConStatus()==BusinessTask.TASK_STATUS_SUCCESS){
						officeWorkArrangeOutline.setAttachmentContent(attachment);
						break;
					}
				}
				officeWorkArrangeDetail.setAttachments(attlist);
			}
			officeWorkArrangeDetailList=new ArrayList<OfficeWorkArrangeDetail>();
			officeWorkArrangeDetailList.add(officeWorkArrangeDetail);
		}
		return SUCCESS;
	}
	
	public String saveFileAttach(){
		try{
			List<UploadFile>  files=StorageFileUtils.handleFiles(new String[] {},0);
			Attachment attachment=new Attachment();
			if(CollectionUtils.isNotEmpty(files)){
				for(UploadFile uploadFile:files){
					attachment.setFileName(uploadFile.getFileName());
					attachment.setContentType(uploadFile.getContentType());
					attachment.setFileSize(uploadFile.getFileSize());
					attachment.setUnitId(getUnitId());
					attachment.setObjectId(detailId);
					attachment.setObjectType(Constants.WORK_ATTACHMENT);
					String fileExt = net.zdsoft.keel.util.FileUtils.getExtension(attachment.getFileName());
					if(converterFileTypeService.isVideo(fileExt)||converterFileTypeService.isDocument(fileExt)){
						attachment.setConStatus(BusinessTask.TASK_STATUS_NO_HAND);
					}
					if(converterFileTypeService.isPicture(fileExt)||converterFileTypeService.isAudio(fileExt)){
						attachment.setConStatus(BusinessTask.TASK_STATUS_SUCCESS);
					}
					attachmentService.saveAttachment(attachment, uploadFile, false);
				}
				promptMessageDto.setOperateSuccess(true);
				promptMessageDto.setPromptMessage("文件保存成功");
				promptMessageDto.setBusinessValue(attachment.getId()+"*"+attachment.getDownloadPath());
			} 
		}catch (Exception e) {
			e.printStackTrace();
			promptMessageDto.setOperateSuccess(false);
			promptMessageDto.setErrorMessage("文件保存出现问题");
		}
		return SUCCESS;
	}
	public String deleteFileAttach(){
		try {
			if(StringUtils.isNotBlank(removeAttachmentId)){
				attachmentService.deleteAttachments(new String[]{removeAttachmentId});
				promptMessageDto.setOperateSuccess(true);
				promptMessageDto.setPromptMessage("文件删除成功");
			}else{
				promptMessageDto.setOperateSuccess(false);
				promptMessageDto.setErrorMessage("文件删除出现问题,文件未找到或者已删除");
			}
		} catch (Exception e) {
			e.printStackTrace();
			promptMessageDto.setOperateSuccess(false);
			promptMessageDto.setErrorMessage("文件删除出现问题,文件未找到或者已删除");
		}
		return SUCCESS;
	}
	//TODO
	public String arrangeSave() {
		try {
			OfficeWorkArrangeDetail everDatail=officeWorkArrangeDetailService.getOfficeWorkArrangeDetailById(officeWorkArrangeDetail.getId());
			if(everDatail==null) {
				if(StringUtils.isBlank(officeWorkArrangeDetail.getId())) officeWorkArrangeDetail.setId(UUIDUtils.newId());
				List<OfficeWorkArrangeContent> owacList = new ArrayList<OfficeWorkArrangeContent>();
				for(int i=0;i<workDates.length;i++){
					if(workDates[i] != null){
						OfficeWorkArrangeContent owac = new OfficeWorkArrangeContent();
						owac.setOutlineId(officeWorkArrangeDetail.getOutlineId());
						owac.setDetailId(officeWorkArrangeDetail.getId());
						owac.setWorkDate(workDates[i]);
						owac.setArrangContent(arrangContents[i]);
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
						owac.setArrangContent(arrangContents[i]);
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
		
		Date startTime=officeWorkArrangeOutline.getStartTime();
		Date endTime=officeWorkArrangeOutline.getEndTime();
		
		officeWorkArrangeContentsMap = officeWorkArrangeContentService.getOfficeWorkArrangeContentsMapByOutLineId(workOutlineId);
		
		if(officeWorkArrangeContentsMap!=null&&officeWorkArrangeContentsMap.size()>0){
			for (Entry<Date, List<OfficeWorkArrangeContent>> officew : officeWorkArrangeContentsMap.entrySet()) {
				List<OfficeWorkArrangeContent> officeWorkArrangeContents=(List<OfficeWorkArrangeContent>) officew.getValue();
				for (OfficeWorkArrangeContent officeWorkArrangeContent : officeWorkArrangeContents) {
					if(officeWorkArrangeContent!=null&&StringUtils.isNotBlank(officeWorkArrangeContent.getArrangContent())){
						canView=true;
						break;
					}
				}
				if(canView){
					break;
				}
			}
		}
		
		for (Date date : officeWorkArrangeContentsMap.keySet()) {
			if(date.before(startTime)||date.after(endTime)){
				changeDate.put(date, 1);
			}else{
				changeDate.put(date, 0);
			}
		}
		dateForWeek(officeWorkArrangeContentsMap.keySet());
		officeWorkArrangeDetailList = officeWorkArrangeDetailService.getOfficeWorkArrangeDetailListByOutLineId(workOutlineId);
		if(CollectionUtils.isNotEmpty(officeWorkArrangeDetailList)){
			//List<Attachment> allAttachments=new A
			for(OfficeWorkArrangeDetail detail:officeWorkArrangeDetailList){
				List<Attachment> attachments=attachmentService.getAttachments(detail.getId(),Constants.WORK_ATTACHMENT);
				if(CollectionUtils.isNotEmpty(attachments)){
						for (Attachment attachment : attachments) {
							if(attachment.getConStatus()==BusinessTask.TASK_STATUS_SUCCESS){
								officeWorkArrangeOutline.setAttachmentContent(attachment);
								break;
							}
						}
					detail.setAttachments(attachments);
				}
			}
		}
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
			officeWorkArrangeOutline = officeWorkArrangeOutlineService.getOfficeWorkArrangeOutlineById(workOutlineId);
			Date startTime=officeWorkArrangeOutline.getStartTime();
			Date endTime=officeWorkArrangeOutline.getEndTime();
			officeWorkArrangeContentsMap = officeWorkArrangeContentService.getOfficeWorkArrangeContentsMapByOutLineId(workOutlineId);
			boolean notChange=false;
			for (Date date : officeWorkArrangeContentsMap.keySet()) {
				if(date.before(startTime)||date.after(endTime)){
					notChange=true;
				}
			}
			if(notChange&&StringUtils.equals("3", state)){
				promptMessageDto.setOperateSuccess(false);
				promptMessageDto.setErrorMessage("上报日期超出工作安排时间，请注意调整！");
				return SUCCESS;
			}
			
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
		officeWorkArrangeOutline.setStartTime(new Date());
		officeWorkArrangeContentsMap = officeWorkArrangeContentService.getOfficeWorkArrangeContentsMapByOutLineId(workOutlineId);
		
		if(officeWorkArrangeContentsMap!=null&&officeWorkArrangeContentsMap.size()>0){
			for (Entry<Date, List<OfficeWorkArrangeContent>> officew : officeWorkArrangeContentsMap.entrySet()) {
				List<OfficeWorkArrangeContent> officeWorkArrangeContents=(List<OfficeWorkArrangeContent>) officew.getValue();
				for (OfficeWorkArrangeContent officeWorkArrangeContent : officeWorkArrangeContents) {
					if(officeWorkArrangeContent!=null&&StringUtils.isNotBlank(officeWorkArrangeContent.getArrangContent())){
						canView=true;
						break;
					}
				}
			}
		}
		
		dateForWeek(officeWorkArrangeContentsMap.keySet());
		officeWorkArrangeDetailList = officeWorkArrangeDetailService.getOfficeWorkArrangeDetailListByOutLineId(workOutlineId);
		if(CollectionUtils.isNotEmpty(officeWorkArrangeDetailList)){
			for(OfficeWorkArrangeDetail detail:officeWorkArrangeDetailList){
				//officeWorkArrangeDetail=detail;
				List<Attachment> attachments=attachmentService.getAttachments(detail.getId(),Constants.WORK_ATTACHMENT);
				if(CollectionUtils.isNotEmpty(attachments)){
					for (Attachment attachment : attachments) {
						if(attachment.getConStatus()==BusinessTask.TASK_STATUS_SUCCESS){
							officeWorkArrangeOutline.setAttachmentContent(attachment);
							break;
						}
					}
					detail.setAttachments(attachments);
				}
			}
		}
		isTrue=true;
		return SUCCESS;
	}
	
	public String arrangeReportExport() {//TODO
		officeWorkArrangeContentsMap = officeWorkArrangeContentService.getOfficeWorkArrangeContentsMapByOutLineId(workOutlineId);
		
		if(officeWorkArrangeContentsMap!=null&&officeWorkArrangeContentsMap.size()>0){
			for (Entry<Date, List<OfficeWorkArrangeContent>> officew : officeWorkArrangeContentsMap.entrySet()) {
				List<OfficeWorkArrangeContent> officeWorkArrangeContents=(List<OfficeWorkArrangeContent>) officew.getValue();
				for (OfficeWorkArrangeContent officeWorkArrangeContent : officeWorkArrangeContents) {
					if(officeWorkArrangeContent!=null&&StringUtils.isNotBlank(officeWorkArrangeContent.getArrangContent())){
						canView=true;
						break;
					}
				}
			}
		}
		
		dateForWeek(officeWorkArrangeContentsMap.keySet());
		officeWorkArrangeDetailList = officeWorkArrangeDetailService.getOfficeWorkArrangeDetailListByOutLineId(workOutlineId);
		
		ZdExcel zdExcel = new ZdExcel();
		ZdStyle style2 = new ZdStyle(ZdStyle.BOLD|ZdStyle.BORDER|ZdStyle.ALIGN_CENTER);
		ZdStyle style3 = new ZdStyle(ZdStyle.BORDER|ZdStyle.ALIGN_CENTER|ZdStyle.WRAP_TEXT);
		List<ZdCell>zdlist=new ArrayList<ZdCell>();
		
		officeWorkArrangeOutline = officeWorkArrangeOutlineService.getOfficeWorkArrangeOutlineById(workOutlineId);
		
		zdlist.add(new ZdCell("日期",1,style2));
		if(this.getUseNewFields()){
			zdlist.add(new ZdCell("时间",1,style2));
		}
		zdlist.add(new ZdCell("工作内容",1,style2));
		if(canView){
			zdlist.add(new ZdCell("具体要求、安排",1,style2));
		}
		zdlist.add(new ZdCell("责任部门",1,style2));
		if(this.getUseNewFields()){
			zdlist.add(new ZdCell("参与人员",1,style2));
		}
		zdlist.add(new ZdCell("地点",1,style2));
		
		//zdExcel.add(new ZdCell("周工作汇总表", zdlist.size(), 2, new ZdStyle(ZdStyle.BOLD|ZdStyle.ALIGN_CENTER|ZdStyle.WRAP_TEXT, 18)));
		zdExcel.add(new ZdCell(officeWorkArrangeOutline.getName(), zdlist.size(), 2, new ZdStyle(ZdStyle.BOLD|ZdStyle.ALIGN_CENTER|ZdStyle.WRAP_TEXT, 18)));
		
		ZdCell[] cells3 = new ZdCell[2];
		cells3[0] = new ZdCell("工作重点：",1, 2, style3);
		String workContent = "";
		workContent=officeWorkArrangeOutline.getWorkContent();
		int zdList=0;
		if(this.getUseNewFields()){
			if(canView){
				zdList=7;
			}else{
				zdList=6;
			}
		}else{
			if(canView){
				zdList=5;
			}else{
				zdList=4;
			}
		}
		cells3[1] = new ZdCell(workContent, zdList-1, 2, new ZdStyle(ZdStyle.BORDER|ZdStyle.WRAP_TEXT));
		zdExcel.add(cells3);
		
		
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
					if(canView){
						cells[index++] = new ZdCell(item.getArrangContent(), 1, style3);
					}
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
			if(item!=null&&org.apache.commons.lang.StringUtils.isNotBlank(item.getRemark())){
				remark += item.getDeptName() + "：" + item.getRemark() + "；    ";
			}
		}
		cells2[1] = new ZdCell(remark, zdlist.size()-1, 2, new ZdStyle(ZdStyle.BORDER|ZdStyle.WRAP_TEXT));
		zdExcel.add(cells2);
		
		ZdCell[] cells4 = new ZdCell[1];
		Date date=new Date();
		SimpleDateFormat sdf2=new SimpleDateFormat("yyyy-MM-dd");
		String dateString=sdf2.format(date);
		cells4[0] = new ZdCell("导出时间："+dateString , zdlist.size(), 2, new ZdStyle(ZdStyle.BORDER|ZdStyle.ALIGN_RIGHT));
		zdExcel.add(cells4);
		
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
		if(StringUtils.isNotBlank(workOutlineId)){
			officeWorkArrangeOutline=officeWorkArrangeOutlineService.getOfficeWorkArrangeOutlineById(workOutlineId);
			if(officeWorkArrangeOutline!=null&&!StringUtils.equals("3", officeWorkArrangeOutline.getState())){
				workOutlineId="";
			}
		}
		deptList = deptService.getDepts(getUnitId());
		return SUCCESS;
	}
	
	//TODO
	public String arrangeQueryList() {
		officeWorkArrangeOutline = officeWorkArrangeOutlineService.getOfficeWorkArrangeOutlineById(workOutlineId);
		officeWorkArrangeContentsMap = officeWorkArrangeContentService.getOfficeWorkArrangeContentsMapByOutLineId(workOutlineId, deptId);
		if(officeWorkArrangeContentsMap!=null&&officeWorkArrangeContentsMap.size()>0){
			for (Entry<Date, List<OfficeWorkArrangeContent>> officew : officeWorkArrangeContentsMap.entrySet()) {
				List<OfficeWorkArrangeContent> officeWorkArrangeContents=(List<OfficeWorkArrangeContent>) officew.getValue();
				for (OfficeWorkArrangeContent officeWorkArrangeContent : officeWorkArrangeContents) {
					if(officeWorkArrangeContent!=null&&StringUtils.isNotBlank(officeWorkArrangeContent.getArrangContent())){
						canView=true;
						break;
					}
				}
			}
		}
		dateForWeek(officeWorkArrangeContentsMap.keySet());
		officeWorkArrangeDetailList = officeWorkArrangeDetailService.getOfficeWorkArrangeDetailListByOutLineId(workOutlineId,deptId);
		if(CollectionUtils.isNotEmpty(officeWorkArrangeDetailList)){
			for(OfficeWorkArrangeDetail detail:officeWorkArrangeDetailList){
				//officeWorkArrangeDetail=detail;
				List<Attachment> attachments=attachmentService.getAttachments(detail.getId(),Constants.WORK_ATTACHMENT);
				if(CollectionUtils.isNotEmpty(attachments)){
					for (Attachment attachment : attachments) {
						if(attachment.getConStatus()==BusinessTask.TASK_STATUS_SUCCESS){
							officeWorkArrangeOutline.setAttachmentContent(attachment);
							break;
						}
					}
			            // if(attachments.get(0).getConStatus()==BusinessTask.TASK_STATUS_SUCCESS){
			            // int index0 = conAttachments.get(0).getFileName().lastIndexOf(".");
			            // conAttachments.get(0).setFileName(conAttachments.get(0).getFileName().substring(0, index0) + ".pdf");
			            // conAttachments.get(0).setExtName("pdf");
			            // }
			            //ad.setContentAttachment(new AttachmentContent(conAttachments.get(0)));
					detail.setAttachments(attachments);
				}
			}
		}
		isTrue=true;
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

	public void setAttachmentService(AttachmentService attachmentService) {
		this.attachmentService = attachmentService;
	}

	public String getRemoveAttachmentId() {
		return removeAttachmentId;
	}

	public void setRemoveAttachmentId(String removeAttachmentId) {
		this.removeAttachmentId = removeAttachmentId;
	}

	public boolean isTrue() {
		return isTrue;
	}

	public void setTrue(boolean isTrue) {
		this.isTrue = isTrue;
	}

	public String getFileSize() {
		return systemIniService.getValue(Constants.FILE_INIID);
	}

	public void setFileSize(String fileSize) {
		this.fileSize = fileSize;
	}
	public String getExtNames(){
		String[] extNames = converterFileTypeService.getAllExtNames();
		StringBuffer sb = new StringBuffer();
		if(extNames!=null){//rtf,doc,docx,xls,xlsx,csv,ppt,pptx,pdf,bmp,jpg,jpeg,png,gif
			for (String extName : extNames) {
				boolean contain=extName.contains("bmp")||extName.contains("jpg")||extName.contains("jpeg")||extName.contains("png")||extName.contains("gif");
				boolean contain2=extName.contains("doc")||extName.contains("docx")||extName.contains("xls")||extName.contains("xlsx")||extName.contains("pdf");
				boolean contain3=extName.contains("ppt")||extName.contains("pptx");
				if(contain||contain2||contain3){
					sb.append(extName+",");
				}
			}
		}
		return sb.toString();
	}

	public void setConverterFileTypeService(
			ConverterFileTypeService converterFileTypeService) {
		this.converterFileTypeService = converterFileTypeService;
	}

	public Map<Date,Integer> getChangeDate() {
		return changeDate;
	}

	public void setChangeDate(Map<Date,Integer> changeDate) {
		this.changeDate = changeDate;
	}

	public String[] getArrangContents() {
		return arrangContents;
	}

	public void setArrangContents(String[] arrangContents) {
		this.arrangContents = arrangContents;
	}

	public boolean isCanView() {
		return canView;
	}

	public void setCanView(boolean canView) {
		this.canView = canView;
	}

}
