package net.zdsoft.office.teacherLeave.action;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.json.JSONObject;
import net.zdsoft.eis.base.attachment.entity.Attachment;
import net.zdsoft.eis.base.attachment.service.AttachmentService;
import net.zdsoft.eis.base.common.entity.CustomRole;
import net.zdsoft.eis.base.common.entity.CustomRoleUser;
import net.zdsoft.eis.base.common.entity.Dept;
import net.zdsoft.eis.base.common.entity.Mcodedetail;
import net.zdsoft.eis.base.common.entity.Module;
import net.zdsoft.eis.base.common.entity.Unit;
import net.zdsoft.eis.base.common.entity.User;
import net.zdsoft.eis.base.common.service.CustomRoleService;
import net.zdsoft.eis.base.common.service.CustomRoleUserService;
import net.zdsoft.eis.base.common.service.DeptService;
import net.zdsoft.eis.base.common.service.McodedetailService;
import net.zdsoft.eis.base.common.service.ModuleService;
import net.zdsoft.eis.base.common.service.UnitService;
import net.zdsoft.eis.base.common.service.UserService;
import net.zdsoft.eis.base.converter.service.ConverterFileTypeService;
import net.zdsoft.eis.base.storage.StorageFileUtils;
import net.zdsoft.eis.component.flowManage.constant.FlowConstant;
import net.zdsoft.eis.component.flowManage.entity.Flow;
import net.zdsoft.eis.component.flowManage.service.FlowManageService;
import net.zdsoft.eis.frame.action.PageAction;
import net.zdsoft.jbpm.activiti.engine.task.AgileIdentityLinkType;
import net.zdsoft.jbpm.core.entity.Comment;
import net.zdsoft.jbpm.core.entity.TaskDescription;
import net.zdsoft.jbpm.core.entity.TaskHandlerSave;
import net.zdsoft.jbpm.core.entity.TaskHandlerShow;
import net.zdsoft.jbpm.core.service.TaskHandlerService;
import net.zdsoft.keel.util.DateUtils;
import net.zdsoft.keel.util.ServletUtils;
import net.zdsoft.keel.util.UUIDUtils;
import net.zdsoft.keelcnet.action.UploadFile;
import net.zdsoft.leadin.common.entity.BusinessTask;
import net.zdsoft.leadin.util.excel.ZdCell;
import net.zdsoft.leadin.util.excel.ZdExcel;
import net.zdsoft.leadin.util.excel.ZdStyle;
import net.zdsoft.office.officeFlow.service.OfficeFlowService;
import net.zdsoft.office.teacherLeave.entity.OfficeTeacherLeave;
import net.zdsoft.office.teacherLeave.service.OfficeTeacherLeaveService;
import net.zdsoft.office.util.Constants;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.Sheet;


public class TeacherLeaveAction extends PageAction{

	private static final long serialVersionUID = 409443973348389654L;
	
	private List<OfficeTeacherLeave> officeTeacherLeaveList;
	private OfficeTeacherLeaveService officeTeacherLeaveService;
	private DeptService deptService;
	private UserService userService;
	private UnitService unitService;
	private OfficeTeacherLeave officeTeacherLeave;
	private FlowManageService flowManageService;
	private McodedetailService mcodedetailService;
	private AttachmentService attachmentService;
	private ModuleService moduleService;
	private ConverterFileTypeService converterFileTypeService;
	private Module module;
	private String FileSize;
	
	private int applyStatus;
	
	private String userId;
	private String userName;
	private String deptId;
	private Date startTime;
	private Date endTime;
	private String id;
	private String leaveId;
	private String removeAttachmentId;
	private String textComment;
	
	private List<Flow> flowList;
	private String taskId;
	private TaskHandlerService taskHandlerService;
	private String taskHandlerSaveJson;
	private boolean isPass;
	private String currentStepId;
	private String flowId;
	private String jsonResult;
	private String taskDefinitionKey;
	private String processInstanceId;
	private String easyLevel;
	
	private int auditStatus;
	private String fromTab;
	
	private List<Mcodedetail> mcodedetails;
	private Map<String, String> sumMap;
	private List<User> users;
	
	private boolean unitViewRole;
	private boolean groupHead;
	private CustomRoleService customRoleService;
	private CustomRoleUserService customRoleUserService;
	
	private OfficeFlowService officeFlowService;
	
	private String[] detailTime;//处理时间
	private String reTaskId;
	private String taskKey;
	private String showReBackId;
	private boolean canBeRetract;
	private boolean canChangeNextTask;
	
	public String execute() throws Exception {
		
		return SUCCESS;
	}
	
	public String applyList(){
		officeTeacherLeaveList = officeTeacherLeaveService.getApplyList(getLoginUser().getUserId(),getUnitId(),applyStatus,getPage());
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
	public String saveFileAttach(){
		try{
			List<UploadFile> files=StorageFileUtils.handleFiles(new String[] {}, 0);//js已限制
			Attachment attachment=null;
			if(!CollectionUtils.isEmpty(files)){
				for (UploadFile uploadFile : files) {
					attachment=new Attachment();
					attachment.setFileName(uploadFile.getFileName());
					attachment.setContentType(uploadFile.getContentType());
					attachment.setFileSize(uploadFile.getFileSize());
					attachment.setUnitId(getUnitId());
					attachment.setObjectId(leaveId);
					attachment.setObjectType(Constants.OFFICE_TEACHER_LEAVE_ATT);
					String fileExt = net.zdsoft.keel.util.FileUtils.getExtension(attachment.getFileName());
					if(converterFileTypeService.isVideo(fileExt)||converterFileTypeService.isDocument(fileExt)){
						attachment.setConStatus(BusinessTask.TASK_STATUS_NO_HAND);
					}
					if(converterFileTypeService.isPicture(fileExt)||converterFileTypeService.isAudio(fileExt)){
						attachment.setConStatus(BusinessTask.TASK_STATUS_SUCCESS);
					}
					attachmentService.saveAttachment(attachment, uploadFile);
				}
				promptMessageDto.setOperateSuccess(true);
				promptMessageDto.setPromptMessage("文件保存成功");
				promptMessageDto.setBusinessValue(attachment.getId()+"*"+attachment.getDownloadPath());
			}
		} catch (Exception e) {
			e.printStackTrace();
			promptMessageDto.setOperateSuccess(false);
			promptMessageDto.setErrorMessage("文件保存出现问题");
		}
	
		return SUCCESS;
	}
	public String applyTeacherLeave(){
		mcodedetails=mcodedetailService.getMcodeDetails("DM-QJLX");
		module=moduleService.getModuleByIntId(Constants.intId);
		
		if(StringUtils.isNotEmpty(id)){
			officeTeacherLeave = officeTeacherLeaveService.getOfficeTeacherLeaveById(id);
			if(officeTeacherLeave==null){
				officeTeacherLeave = new OfficeTeacherLeave();
				User user = userService.getUser(getLoginUser().getUserId());
				officeTeacherLeave.setApplyUserId(user.getId());
				officeTeacherLeave.setApplyUserName(user.getRealname());
				officeTeacherLeave.setUnitId(getUnitId());
				officeTeacherLeave.setDeptId(user.getDeptid());
			}
		}else{
			officeTeacherLeave = new OfficeTeacherLeave();
			User user = userService.getUser(getLoginUser().getUserId());
			officeTeacherLeave.setId(UUIDUtils.newId());
			officeTeacherLeave.setApplyUserId(user.getId());
			officeTeacherLeave.setApplyUserName(user.getRealname());
			officeTeacherLeave.setUnitId(getUnitId());
			officeTeacherLeave.setDeptId(user.getDeptid());
			officeTeacherLeave.setLeaveBeignTime(new Date());//默认当天
			List<String> times=new ArrayList<String>();
			officeTeacherLeave.setTimes(times);
		}
		officeTeacherLeave=officeTeacherLeaveService.getOfficeTeacherByOfficeLog(officeTeacherLeave,getUnitId(),getLoginUser().getUserId());
		flowList=officeTeacherLeave.getFlowList();
		if(CollectionUtils.isEmpty(flowList)){
			flowList = flowManageService.getFinishFlowList(getUnitId(), FlowConstant.FLOW_OWNER_UNIT, FlowConstant.OFFICE_LEAVE,FlowConstant.OFFICE_SUBSYSTEM,FlowConstant.OFFICEDOC_FLOW_EASY_LEVEL_1);
			List<Flow> flowList2  = flowManageService.getFinishFlowList(getLoginInfo().getUser().getDeptid(), FlowConstant.FLOW_OWNER_STRING, 
					FlowConstant.OFFICE_LEAVE,FlowConstant.OFFICE_SUBSYSTEM,FlowConstant.OFFICEDOC_FLOW_EASY_LEVEL_1);
			if(CollectionUtils.isNotEmpty(flowList2)){
				flowList.addAll(flowList2);
			}
			flowList = officeTeacherLeaveService.filterFlow(flowList, getUnitId(), getLoginUser().getUserId());
		}
		if(StringUtils.isEmpty(officeTeacherLeave.getFlowId())){
			for(Flow item : flowList){
				if(item.isDefaultFlow()){
					officeTeacherLeave.setFlowId(item.getFlowId());
				}
			}
		}
		return SUCCESS;
	}
	public String saveTeacherLeave(){
		try {
			officeTeacherLeave.setApplyStatus(Constants.LEAVE_APPLY_SAVE);
			officeTeacherLeave.setCreateUserId(getLoginUser().getUserId());
			
			JSONObject json=new JSONObject();
			if(ArrayUtils.isNotEmpty(detailTime)){
				for(String des: detailTime){
					String[] desYear=StringUtils.split(des, "_");
					json.put(desYear[0], desYear[1]);
				}
				officeTeacherLeave.setDescription(json.toString());
			}
			
			if(StringUtils.isNotEmpty(officeTeacherLeave.getId())){
				OfficeTeacherLeave everLeaver=officeTeacherLeaveService.getOfficeTeacherLeaveById(officeTeacherLeave.getId());
				if(everLeaver!=null){
					if(StringUtils.isBlank(officeTeacherLeave.getDescription())){
						officeTeacherLeave.setDescription(everLeaver.getDescription());
					}
					officeTeacherLeave.setCreateTime(new Date());
					officeTeacherLeaveService.update(officeTeacherLeave,null,false, null);
				}else{
					officeTeacherLeave.setCreateTime(new Date());
					officeTeacherLeaveService.add(officeTeacherLeave,null,false);
				}
			}
			promptMessageDto.setOperateSuccess(true);
			promptMessageDto.setPromptMessage("保存成功");
			promptMessageDto.setErrorMessage(officeTeacherLeave.getId());//将保存成功的请假信息ID传到页面
		}catch (Exception e) {
			e.printStackTrace();
			promptMessageDto.setOperateSuccess(false);
			promptMessageDto.setErrorMessage("保存失败:"+e.getCause().getMessage());
		}
		return SUCCESS;
	}
	public String printLeave(){
		officeTeacherLeave=officeTeacherLeaveService.getOfficeTeacherLeaveById(id);
		if(CollectionUtils.isNotEmpty(officeTeacherLeave.getAttachments())){
			List<String> fileNames=new ArrayList<String>();
			for(Attachment att:officeTeacherLeave.getAttachments()){
				fileNames.add(att.getFileName());
			}
			officeTeacherLeave.setFileNames(fileNames);
			officeTeacherLeave.setAttachments(null);
		}
		if(officeTeacherLeave!=null){
			if(officeTeacherLeave.getLeaveBeignTime()!=null){
				officeTeacherLeave.setLeaveBeginTimeStr(DateUtils.date2String(officeTeacherLeave.getLeaveBeignTime(),"yyyy-MM-dd"));
			}
			if(officeTeacherLeave.getLeaveEndTime()!=null){
				officeTeacherLeave.setLeaveEndTimeStr(DateUtils.date2String(officeTeacherLeave.getLeaveEndTime(),"yyyy-MM-dd"));
			}
			if(officeTeacherLeave.getCreateTime()!=null){
				officeTeacherLeave.setCreateTimeStr(DateUtils.date2String(officeTeacherLeave.getCreateTime(),"yyyy-MM-dd"));
			}
		}
		String leaveType=mcodedetailService.getMcodeDetail("DM-QJLX",officeTeacherLeave.getLeaveType()).getContent();
		officeTeacherLeave.setLeaveType(leaveType);
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		jsonMap.put("teacherLeave", officeTeacherLeave);
		JSONObject jsonObject = new JSONObject();
		for (String key : jsonMap.keySet()) {
			jsonObject.put(key, jsonMap.get(key));
		}
		try {
			ServletUtils.print(getResponse(), jsonObject.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	public String submitTeacherLeave(){
		UploadFile file = null;
		try {
//			file = StorageFileUtils.handleFile(new String[] {}, 5*1024);
			
			JSONObject json=new JSONObject();
			if(ArrayUtils.isNotEmpty(detailTime)){
				for(String des: detailTime){
					String[] desYear=StringUtils.split(des, "_");
					json.put(desYear[0], desYear[1]);
				}
				officeTeacherLeave.setDescription(json.toString());
			}
			
			if(StringUtils.isNotEmpty(officeTeacherLeave.getId())){
				OfficeTeacherLeave everLeaver=officeTeacherLeaveService.getOfficeTeacherLeaveById(officeTeacherLeave.getId());
				if(everLeaver!=null){
					if(StringUtils.isBlank(officeTeacherLeave.getDescription())){
						officeTeacherLeave.setDescription(everLeaver.getDescription());
					}
				}
			}
			
			officeTeacherLeave.setCreateUserId(getLoginUser().getUserId());
			officeTeacherLeaveService.startFlow(officeTeacherLeave,getLoginUser().getUserId(),null,false, null);
			promptMessageDto.setOperateSuccess(true);
			promptMessageDto.setPromptMessage("提交成功,已进入流程中");
		}catch (Exception e) {
			e.printStackTrace();
			promptMessageDto.setOperateSuccess(false);
			if(e.getCause()!=null){
				if(e.getCause().getMessage()!=null){
					promptMessageDto.setErrorMessage("提交失败:"+e.getCause().getMessage());
				}else{
					promptMessageDto.setErrorMessage("未设置流程审核人员");
				}
			}else{
				promptMessageDto.setErrorMessage("提交失败:"+e.getMessage());
			}
		}
		return SUCCESS;
	}
	/**
	 * 在最后一步审核结束前删除
	 * @return
	 * 2016年9月12日
	 */
	public String revokeLeave(){
		if(StringUtils.isNotEmpty(id)){
			try {
				officeTeacherLeaveService.deleteRevoke(id);
				promptMessageDto.setOperateSuccess(true);
				promptMessageDto.setPromptMessage("撤销成功");
			} catch (Exception e) {
				promptMessageDto.setOperateSuccess(false);
				promptMessageDto.setErrorMessage("撤销失败:"+e.getCause().getMessage());
			}
		}else{
			promptMessageDto.setOperateSuccess(false);
			promptMessageDto.setErrorMessage("撤销失败:找不到取消的对象");
		}
		
		return SUCCESS;
	}
	/**
	 * 审核通过的作废
	 * @return
	 * 2016年9月12日
	 */
	public String invalidLeave(){
		if(StringUtils.isNotEmpty(id)){
			try {
				officeTeacherLeaveService.deleteInvalid(id,getLoginInfo().getUser().getId());
				promptMessageDto.setOperateSuccess(true);
				promptMessageDto.setPromptMessage("作废成功");
			} catch (Exception e) {
				promptMessageDto.setOperateSuccess(false);
				promptMessageDto.setErrorMessage("作废失败:"+e.getCause().getMessage());
			}
		}else{
			promptMessageDto.setOperateSuccess(false);
			promptMessageDto.setErrorMessage("作废失败:找不到撤销的对象");
		}
		
		return SUCCESS;
	}
	public String deleteLeave(){
		if(StringUtils.isNotEmpty(id)){
			try {
				officeTeacherLeaveService.delete(new String[]{id});
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
	
	public String auditList(){
		if(auditStatus==0){
			officeTeacherLeaveList=officeTeacherLeaveService.toDoAudit(getLoginUser().getUserId(),getPage());
		}else 
			if(auditStatus==2){//已作废
			officeTeacherLeaveList=officeTeacherLeaveService.HaveDoAudit(getLoginUser().getUserId(),true,getPage());
		}else if(auditStatus==1){
			officeTeacherLeaveList=officeTeacherLeaveService.HaveDoAudit(getLoginUser().getUserId(),false,getPage());
		}else{
//			officeTeacherLeaveList=officeTeacherLeaveService.haveDoneAudit(getLoginUser().getUserId(), getUnitId(), getPage());
			
			Unit unit=unitService.getUnit(getLoginInfo().getUnitID());
			Set<String> unitIsSet=new HashSet<String>();
			if(getLoginInfo().getUnitClass()==Unit.UNIT_CLASS_EDU){
				List<Unit> unites=unitService.getUnderlingSchools(getLoginInfo().getUnitID());
				unites.add(unit);
				for (Unit unit2 : unites) {
					unitIsSet.add(unit2.getId());
				}
				officeTeacherLeaveList=officeTeacherLeaveService.getOfficeTeacherLeaveByUnitIdList(unitIsSet.toArray(new String[0]));
			}else{
				officeTeacherLeaveList=officeTeacherLeaveService.getOfficeTeacherLeaveByUnitIdList(getLoginInfo().getUnitID());
			}
			officeTeacherLeaveList = officeFlowService.haveDoneAudit(officeTeacherLeaveList, getLoginUser().getUserId(), getPage());
		}
		return SUCCESS;
	}
	
	public String auditTeacherLeave(){
		officeTeacherLeave = officeTeacherLeaveService.getOfficeTeacherLeaveById(id);
		if(CollectionUtils.isEmpty(officeTeacherLeave.getTimes())){
			List<String> times=new ArrayList<String>();
			officeTeacherLeave.setTimes(times);
		}
		TaskHandlerSave taskHandlerSave = new TaskHandlerSave();
		TaskHandlerShow taskHandlerShow = taskHandlerService.getTaskHandlerShow(taskId, getLoginUser().getUserId());
		taskHandlerSave.setCurrentTask(taskHandlerShow.getCurrentTask());
		taskHandlerSave.setCurrentUserId(getLoginUser().getUserId());
		taskHandlerSave.setCurrentUnitId(getLoginUser().getUnitId());
		taskHandlerSave.setSubsystemId(FlowConstant.OFFICE_SUBSYSTEM);
		User user = userService.getUser(getLoginUser().getUserId());
		//textComment=officeTeacherLeaveService.getTextComment(getUnitId(),user.getId());
		
		Comment comment = new Comment();
		comment.setAssigneeType(AgileIdentityLinkType.PRINCIPAL);
		comment.setAssigneeId(user.getId());
		comment.setAssigneeName(user.getRealname());
		userName = user.getRealname();
		taskHandlerSave.setComment(comment);
		JSONObject json = new JSONObject().fromObject(taskHandlerSave);
		taskHandlerSaveJson = json.toString();
		officeTeacherLeave.setTaskName(taskHandlerSave.getCurrentTask().getTaskName());
		currentStepId = taskHandlerSave.getCurrentTask().getTaskDefinitionKey();
		flowId = officeTeacherLeave.getFlowId();
		return SUCCESS;
	}
	
	/**
	 * 撤回 TODO
	 * @return
	 */
	public String retractFlow(){
		try {
			officeFlowService.retractFlow(showReBackId, reTaskId, taskKey);
			promptMessageDto.setOperateSuccess(true);
			promptMessageDto.setPromptMessage("撤回成功");
		} catch (Exception e) {
			e.printStackTrace();
			promptMessageDto.setOperateSuccess(false);
			promptMessageDto.setErrorMessage("撤回失败:" + e.getMessage());
		}
		return SUCCESS;
	}
	
	public String auditPassLeave(){
		try {
			JSONObject object = new JSONObject().fromObject(taskHandlerSaveJson);
			TaskHandlerSave taskHandlerSave =(TaskHandlerSave) JSONObject.toBean(object,
				 TaskHandlerSave.class);
			taskHandlerSave.getComment().setOperateTime(new Date());
			officeTeacherLeaveService.passFlow(isPass(),taskHandlerSave,id,currentStepId);
			promptMessageDto.setOperateSuccess(true);
			promptMessageDto.setPromptMessage("审核成功");
		} catch (Exception e) {
			e.printStackTrace();
			promptMessageDto.setOperateSuccess(false);
			if(e.getCause()!=null){
				promptMessageDto.setErrorMessage("审核失败:"+e.getCause().getMessage());
			}else{
				promptMessageDto.setErrorMessage("审核失败:"+e.getMessage());
			}
		}
		return SUCCESS;
	}
	
	public String applyQueryList(){
		users = new ArrayList<User>();
		if(!this.isUnitViewRole()){
			deptId = deptService.isPrincipanGroupHead(getLoginInfo().getUser().getId());
			if(StringUtils.isBlank(deptId)){//不是部门负责人 只查询自己
				users = userService.getUsers(new String[]{getLoginInfo().getUser().getId()});
			}
			else{
				users = userService.getUsersByDeptId(deptId);
				if(!StringUtils.equals(deptId, getLoginInfo().getUser().getDeptid())){//负责的部门非自己所在部门，查询人员再加自己
					users.add(getLoginInfo().getUser());
				}
			}
		}
		else{
			if(StringUtils.isBlank(deptId)){
				users = userService.getUsers(getLoginInfo().getUser().getUnitid());
			}
			else{
				users = userService.getUsersByDeptId(deptId);
			}
		}
		users = this.getUsersByName(users, userName);
		Set<String> uids = new HashSet<String>(); 
		for(User item : users){
			uids.add(item.getId());
		}
		
		officeTeacherLeaveList=officeTeacherLeaveService.getQueryList(getUnitId(),uids.toArray(new String[0]),startTime,endTime, getPage(),false);
		return SUCCESS;
	}
	
	public String teacherLeaveExport(){
		users = new ArrayList<User>();
		if(!this.isUnitViewRole()){
			deptId = deptService.isPrincipanGroupHead(getLoginInfo().getUser().getId());
			if(StringUtils.isBlank(deptId)){//不是部门负责人 只查询自己
				users = userService.getUsers(new String[]{getLoginInfo().getUser().getId()});
			}
			else{
				users = userService.getUsersByDeptId(deptId);
				if(!StringUtils.equals(deptId, getLoginInfo().getUser().getDeptid())){//负责的部门非自己所在部门，查询人员再加自己
					users.add(getLoginInfo().getUser());
				}
			}
		}
		else{
			if(StringUtils.isBlank(deptId)){
				users = userService.getUsers(getLoginInfo().getUser().getUnitid());
			}
			else{
				users = userService.getUsersByDeptId(deptId);
			}
		}
		users = this.getUsersByName(users, userName);
		Set<String> uids = new HashSet<String>(); 
		for(User item : users){
			uids.add(item.getId());
		}
		officeTeacherLeaveList=officeTeacherLeaveService.getQueryList(getUnitId(),uids.toArray(new String[0]),startTime,endTime, null,false);
		Map<String,Mcodedetail> map= mcodedetailService.getMcodeDetailMap("DM-QJLX");
		
		ZdExcel zdExcel = new ZdExcel();
		ZdStyle style2 = new ZdStyle(ZdStyle.BOLD|ZdStyle.BORDER);
		ZdStyle style3 = new ZdStyle(ZdStyle.BORDER|ZdStyle.ALIGN_CENTER|ZdStyle.WRAP_TEXT);
		
		List<ZdCell> zdlist=new ArrayList<ZdCell>();
		zdlist.add(new ZdCell("序号",1,style2));
		zdlist.add(new ZdCell("申请人",1,style2));
		zdlist.add(new ZdCell("请假开始时间",1,style2));
		zdlist.add(new ZdCell("请假结束时间",1,style2));
		zdlist.add(new ZdCell("部门",1,style2));
		zdlist.add(new ZdCell("请假类型",1,style2));
		zdlist.add(new ZdCell("请假天数",1,style2));
		zdExcel.add(new ZdCell("请假查询", zdlist.size(), 2, new ZdStyle(ZdStyle.BOLD|ZdStyle.ALIGN_CENTER|ZdStyle.WRAP_TEXT, 18)));
		zdExcel.add(zdlist.toArray(new ZdCell[0]));
		
		int j=1;
		for (OfficeTeacherLeave officeTeacherLeave : officeTeacherLeaveList) {
			List<ZdCell> zdcells=new ArrayList<ZdCell>();
			zdcells.add(new ZdCell(j+"",1,style3));
			zdcells.add(new ZdCell(officeTeacherLeave.getApplyUserName(),1,style3));
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
			String beginStr=sdf.format(officeTeacherLeave.getLeaveBeignTime());
			String endStr=sdf.format(officeTeacherLeave.getLeaveEndTime());
			zdcells.add(new ZdCell(beginStr,1,style3));
			zdcells.add(new ZdCell(endStr,1,style3));
			zdcells.add(new ZdCell(officeTeacherLeave.getDeptName(),1,style3));
			if(map.containsKey(officeTeacherLeave.getLeaveType())){
				zdcells.add(new ZdCell(map.get(officeTeacherLeave.getLeaveType()).getContent(),1,style3));
			}
			zdcells.add(new ZdCell(String.valueOf(officeTeacherLeave.getDays()),1,style3));
			j++;
			zdExcel.add(zdcells.toArray(new ZdCell[0]));
		}
		
		Sheet sheet = zdExcel.createSheet("sheet1");
		for(int i=0;i<3;i++){
			zdExcel.setCellWidth(sheet, i, 2);
		}
		zdExcel.export("teacher_leave");
		return NONE;
	}
	/**
	 * 按姓名过滤
	 * @param list
	 * @param name
	 * @return
	 */
	public List<User> getUsersByName(List<User> list, String name){
		if(CollectionUtils.isNotEmpty(list) && StringUtils.isNotBlank(name)){
			Iterator<User> it = list.iterator();  
		    while(it.hasNext()){
		    	User item = it.next(); 
		        if (!item.getRealname().contains(name)) {
		            it.remove();
		        }
		    }
		}
		return list;
	}
	
	public String applyDetail(){//TODO
		officeTeacherLeave=officeTeacherLeaveService.getOfficeTeacherLeaveById(id);
		if(CollectionUtils.isEmpty(officeTeacherLeave.getTimes())){
			List<String> times=new ArrayList<String>();
			officeTeacherLeave.setTimes(times);
		}
		if(officeTeacherLeave==null){
			officeTeacherLeave=new OfficeTeacherLeave();
		}
		
		return SUCCESS;
	}
	
	public String applySummary(){
		Calendar c = Calendar.getInstance();
		endTime = c.getTime();
		c.set(Calendar.DAY_OF_MONTH, c.getActualMinimum(Calendar.DAY_OF_MONTH));
		startTime = c.getTime();
		return SUCCESS;
	}
	public String applySummaryList(){
		mcodedetails = mcodedetailService.getMcodeDetails("DM-QJLX");
		if(!this.isUnitViewRole()){
			deptId = deptService.isPrincipanGroupHead(getLoginInfo().getUser().getId());
			if(StringUtils.isBlank(deptId)){//不是部门负责人 只查询自己
				users = userService.getUsers(new String[]{getLoginInfo().getUser().getId()});
			}
			else{
				users = userService.getUsersByDeptId(deptId);
				if(!StringUtils.equals(deptId, getLoginInfo().getUser().getDeptid())){//负责的部门非自己所在部门，查询人员再加自己
					users.add(getLoginInfo().getUser());
				}
			}
		}
		else{
			if(StringUtils.isBlank(deptId)){
				users = userService.getUsers(getLoginInfo().getUser().getUnitid());
			}
			else{
				users = userService.getUsersByDeptId(deptId);
			}
		}
		users = this.getUsersByName(users, userName);
		Set<String> uids = new HashSet<String>(); 
		for(User item : users){
			uids.add(item.getId());
		}
		List<OfficeTeacherLeave> list = officeTeacherLeaveService.getQueryList(getUnitId(),uids.toArray(new String[0]),startTime,endTime, null,true);
		uids = new HashSet<String>();
		for(OfficeTeacherLeave leave : list){
			if(StringUtils.isNotBlank(leave.getDescription())){
				uids.add(leave.getApplyUserId());
			}
		}
		users = userService.getUsers(uids.toArray(new String[0]));
		sumMap=officeTeacherLeaveService.getSumMap(list,startTime,endTime);
		//sumMap = officeTeacherLeaveService.getSumMap(getLoginInfo().getUnitID(),startTime,endTime,null);
		return SUCCESS;
	}
	public String applySummaryExport(){
		mcodedetails = mcodedetailService.getMcodeDetails("DM-QJLX");
		
		if(!this.isUnitViewRole()){
			deptId = deptService.isPrincipanGroupHead(getLoginInfo().getUser().getId());
			if(StringUtils.isBlank(deptId)){//不是部门负责人 只查询自己
				users = userService.getUsers(new String[]{getLoginInfo().getUser().getId()});
			}
			else{
				String[] userIds = officeTeacherLeaveService.getApplyUserIds(getLoginInfo().getUnitID(),startTime,endTime,deptId);
				if(!StringUtils.equals(deptId, getLoginInfo().getUser().getDeptid())){//负责的部门非自己所在部门，查询人员再加自己
					String[] userids = new String[userIds.length+1];
					int index;
					for(index=0;index<userIds.length;index++){
						userids[index] = userIds[index];
					}
					userids[index] = getLoginInfo().getUser().getId();
					users = userService.getUsers(userids);
				}else{
					users = userService.getUsers(userIds);
				}
			}
		}
		else{
			String[] userIds = officeTeacherLeaveService.getApplyUserIds(getLoginInfo().getUnitID(),startTime,endTime,deptId);
			users = userService.getUsers(userIds);
		}
		Set<String> uids = new HashSet<String>(); 
		for(User user:users){
			uids.add(user.getId());
		}
		List<OfficeTeacherLeave> list = officeTeacherLeaveService.getQueryList(getUnitId(),uids.toArray(new String[0]),startTime,endTime, null,true);
		Set<String> uidss = new HashSet<String>();
		for(OfficeTeacherLeave leave : list){
			if(StringUtils.isNotBlank(leave.getDescription())){
				uidss.add(leave.getApplyUserId());
			}
		}
		users = userService.getUsers(uidss.toArray(new String[0]));
		sumMap = officeTeacherLeaveService.getSumMap(list,startTime,endTime);
		ZdExcel zdExcel = new ZdExcel();
		ZdStyle style2 = new ZdStyle(ZdStyle.BOLD|ZdStyle.BORDER);
		ZdStyle style3 = new ZdStyle(ZdStyle.BORDER);
		zdExcel.add(new ZdCell("请假统计", mcodedetails.size()+1, 2, new ZdStyle(ZdStyle.BOLD|ZdStyle.ALIGN_CENTER|ZdStyle.WRAP_TEXT, 18)));
		
		List<ZdCell>zdlist=new ArrayList<ZdCell>();
		zdlist.add(new ZdCell("姓名",1,style2));
		for(Mcodedetail mcodedetail:mcodedetails){
			zdlist.add(new ZdCell(mcodedetail.getContent(),1,style2));
		}
		zdExcel.add(zdlist.toArray(new ZdCell[0]));
		
		for(User user:users){
			ZdCell[] cells = new ZdCell[mcodedetails.size()+1];
			cells[0] = new ZdCell(user.getRealname(), 1, style3);
			for(int i=0;i<mcodedetails.size();i++){
				if(sumMap.get(user.getId()+"_"+mcodedetails.get(i).getThisId())!=null){
					cells[i+1] = new ZdCell(sumMap.get(user.getId()+"_"+mcodedetails.get(i).getThisId())+"天", 1, style3);
				}else{
					cells[i+1] = new ZdCell("0天", 1, style3);
				}
			}
			zdExcel.add(cells);
		}
		
		Sheet sheet = zdExcel.createSheet("sheet1");
		for(int i=0;i<3;i++){
			zdExcel.setCellWidth(sheet, i, 2);
		}
		zdExcel.export("teacher_leave");
		return NONE;
	}
	/**
	 * 单位下教研组
	 * @return
	 */
	public List<Dept> getDirectDepts() {
		return deptService.getDepts(getUnitId());//getDirectDepts(getUnitId(),2,null);
	}
	
	public String changeFlow() throws IOException{//TODO
		String value = "success";
		if(StringUtils.isNotBlank(jsonResult)){
			try {
				officeTeacherLeaveService.changeFlow(leaveId,getLoginUser().getUserId(),id,jsonResult);
			}catch (Exception e) {
				e.printStackTrace();
				if(e.getCause()!=null){
					value ="流程启动出错:"+e.getCause().getMessage();
				}else{
					value ="流程启动出错:"+e.getMessage();
				}
			}
		}else{
			value = "无法获取流程";
		}
		ServletUtils.print(getResponse(), value);
		return NONE;
	}
	
	public String findCurrentstep(){
		OfficeTeacherLeave leave = officeTeacherLeaveService.getOfficeTeacherLeaveById(leaveId);
		List<TaskDescription> todoTasks = taskHandlerService.getTodoTasks(leave.getFlowId());
		if(todoTasks.size() > 0){
			for (TaskDescription item : todoTasks) {
				if(item.getTaskId().equals(taskId)){
					taskDefinitionKey = item.getTaskDefinitionKey();
					processInstanceId = item.getProcessInstanceId();
					easyLevel = "1";
				}
			}
		}
		return SUCCESS;
	}
	
	public String changeCurrentstep(){
		String value = "success";
		if(StringUtils.isNotBlank(jsonResult)){
			try {
				JSONObject object = new JSONObject().fromObject(taskHandlerSaveJson);
				TaskHandlerSave taskHandlerSave =(TaskHandlerSave) JSONObject.toBean(object,
					 TaskHandlerSave.class);
				taskHandlerSave.setProcessDefinitionJson(jsonResult);
				List<TaskDescription> todoTasks = taskHandlerService.getTodoTasks(id);
				for (TaskDescription item : todoTasks) {
					if(item.getTaskDefinitionKey().equals(currentStepId)){
						taskHandlerSave.setCurrentTask(item);
						break;
					}
				}
				taskHandlerService.completeTask(taskHandlerSave,false);
			} catch (Exception e) {
				e.printStackTrace();
				if(e.getCause()!=null){
					value ="流程修改出错:"+e.getCause().getMessage();
				}else{
					value ="流程修改出错:"+e.getMessage();
				}
			}
		}else{
			value = "无法获取流程";
		}
		try {
			ServletUtils.print(getResponse(), value);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return NONE;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setDeptService(DeptService deptService) {
		this.deptService = deptService;
	}

	public List<OfficeTeacherLeave> getOfficeTeacherLeaveList() {
		return officeTeacherLeaveList;
	}

	public void setOfficeTeacherLeaveList(
			List<OfficeTeacherLeave> officeTeacherLeaveList) {
		this.officeTeacherLeaveList = officeTeacherLeaveList;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
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

	public void setOfficeTeacherLeaveService(
			OfficeTeacherLeaveService officeTeacherLeaveService) {
		this.officeTeacherLeaveService = officeTeacherLeaveService;
	}

	public OfficeTeacherLeave getOfficeTeacherLeave() {
		return officeTeacherLeave;
	}

	public void setOfficeTeacherLeave(OfficeTeacherLeave officeTeacherLeave) {
		this.officeTeacherLeave = officeTeacherLeave;
	}
	

	public int getApplyStatus() {
		return applyStatus;
	}

	public void setApplyStatus(int applyStatus) {
		this.applyStatus = applyStatus;
	}

	public List<Flow> getFlowList() {
		return flowList;
	}

	public void setFlowList(List<Flow> flowList) {
		this.flowList = flowList;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public void setFlowManageService(FlowManageService flowManageService) {
		this.flowManageService = flowManageService;
	}

	public int getAuditStatus() {
		return auditStatus;
	}

	public void setAuditStatus(int auditStatus) {
		this.auditStatus = auditStatus;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public String getTaskHandlerSaveJson() {
		return taskHandlerSaveJson;
	}

	public void setTaskHandlerSaveJson(String taskHandlerSaveJson) {
		this.taskHandlerSaveJson = taskHandlerSaveJson;
	}

	public boolean isPass() {
		return isPass;
	}

	public void setPass(boolean isPass) {
		this.isPass = isPass;
	}

	public void setTaskHandlerService(TaskHandlerService taskHandlerService) {
		this.taskHandlerService = taskHandlerService;
	}

	public String getCurrentStepId() {
		return currentStepId;
	}

	public void setCurrentStepId(String currentStepId) {
		this.currentStepId = currentStepId;
	}

	public String getFlowId() {
		return flowId;
	}

	public void setFlowId(String flowId) {
		this.flowId = flowId;
	}

	public String getFromTab() {
		return fromTab;
	}

	public void setFromTab(String fromTab) {
		this.fromTab = fromTab;
	}

	public List<Mcodedetail> getMcodedetails() {
		return mcodedetails;
	}

	public void setMcodedetails(List<Mcodedetail> mcodedetails) {
		this.mcodedetails = mcodedetails;
	}

	public Map<String, String> getSumMap() {
		return sumMap;
	}

	public void setSumMap(Map<String, String> sumMap) {
		this.sumMap = sumMap;
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	public void setMcodedetailService(McodedetailService mcodedetailService) {
		this.mcodedetailService = mcodedetailService;
	}

	public boolean isUnitViewRole() {
		CustomRole role = customRoleService.getCustomRoleByRoleCode(getUnitId(), "teacher_leave_view");
		if(role == null){
			return false;
		}
		List<CustomRoleUser> roleUs = customRoleUserService.getCustomRoleUserListByUserId(getLoginUser().getUserId());
		if(CollectionUtils.isNotEmpty(roleUs)){
			for(CustomRoleUser ru : roleUs){
				if(StringUtils.equals(ru.getRoleId(), role.getId())){
					return true;
				}
			}
		}
		return false;
	}

	public void setUnitViewRole(boolean unitViewRole) {
		this.unitViewRole = unitViewRole;
	}

	public boolean isGroupHead() {
		String deptid = deptService.isPrincipanGroupHead(getLoginInfo().getUser().getId());
		return StringUtils.isNotBlank(deptid);
	}

	public void setGroupHead(boolean groupHead) {
		this.groupHead = groupHead;
	}

	public void setCustomRoleService(CustomRoleService customRoleService) {
		this.customRoleService = customRoleService;
	}

	public void setCustomRoleUserService(CustomRoleUserService customRoleUserService) {
		this.customRoleUserService = customRoleUserService;
	}

	public String getJsonResult() {
		return jsonResult;
	}

	public void setJsonResult(String jsonResult) {
		this.jsonResult = jsonResult;
	}

	public String getLeaveId() {
		return leaveId;
	}

	public void setLeaveId(String leaveId) {
		this.leaveId = leaveId;
	}

	public String getEasyLevel() {
		return easyLevel;
	}

	public void setEasyLevel(String easyLevel) {
		this.easyLevel = easyLevel;
	}

	public String getTaskDefinitionKey() {
		return taskDefinitionKey;
	}

	public void setTaskDefinitionKey(String taskDefinitionKey) {
		this.taskDefinitionKey = taskDefinitionKey;
	}

	public String getProcessInstanceId() {
		return processInstanceId;
	}

	public void setProcessInstanceId(String processInstanceId) {
		this.processInstanceId = processInstanceId;
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

	public void setModuleService(ModuleService moduleService) {
		this.moduleService = moduleService;
	}

	public Module getModule() {
		return module;
	}

	public void setModule(Module module) {
		this.module = module;
	}


	public String getTextComment() {
		return textComment;
	}

	public void setTextComment(String textComment) {
		this.textComment = textComment;
	}

	public String getFileSize() {
		return  systemIniService.getValue(Constants.FILE_INIID);
	}

	public void setFileSize(String fileSize) {
		FileSize = fileSize;
	}

	public String[] getDetailTime() {
		return detailTime;
	}

	public void setDetailTime(String[] detailTime) {
		this.detailTime = detailTime;
	}

	public String getReTaskId() {
		return reTaskId;
	}

	public void setReTaskId(String reTaskId) {
		this.reTaskId = reTaskId;
	}

	public String getTaskKey() {
		return taskKey;
	}

	public void setTaskKey(String taskKey) {
		this.taskKey = taskKey;
	}

	public String getShowReBackId() {
		return showReBackId;
	}

	public void setShowReBackId(String showReBackId) {
		this.showReBackId = showReBackId;
	}

	public boolean isCanBeRetract() {
		//撤回
		try{
			JSONObject json = JSONObject.fromObject(officeFlowService
					.checkRetract(officeTeacherLeave.getHisTaskList(), getLoginUser().getUserId(), officeTeacherLeave.getFlowId()));
			canBeRetract = json.getBoolean("result");
			if(canBeRetract){
				reTaskId = json.getString("reTaskId");
				taskKey = json.getString("taskKey");
				showReBackId = json.getString("showReBackId");
			}
		}catch (Exception e) {
			e.printStackTrace();
			canBeRetract = false;
		}
		return canBeRetract;
	}

	public void setCanBeRetract(boolean canBeRetract) {
		this.canBeRetract = canBeRetract;
	}

	public void setConverterFileTypeService(
			ConverterFileTypeService converterFileTypeService) {
		this.converterFileTypeService = converterFileTypeService;
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

	public void setOfficeFlowService(OfficeFlowService officeFlowService) {
		this.officeFlowService = officeFlowService;
	}

	public void setUnitService(UnitService unitService) {
		this.unitService = unitService;
	}

	public boolean isCanChangeNextTask() {
		int num = officeFlowService.getTaskDescNum(getUnitId(), getLoginUser().getUserId(), taskId);
		return num>0;
	}

	public void setCanChangeNextTask(boolean canChangeNextTask) {
		this.canChangeNextTask = canChangeNextTask;
	}

}