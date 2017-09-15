package net.zdsoft.office.dailyoffice.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import net.sf.json.JSONObject;
import net.zdsoft.eis.base.attachment.entity.Attachment;
import net.zdsoft.eis.base.attachment.service.AttachmentService;
import net.zdsoft.eis.base.common.entity.CustomRole;
import net.zdsoft.eis.base.common.entity.CustomRoleUser;
import net.zdsoft.eis.base.common.entity.Dept;
import net.zdsoft.eis.base.common.entity.Module;
import net.zdsoft.eis.base.common.entity.User;
import net.zdsoft.eis.base.common.service.CustomRoleService;
import net.zdsoft.eis.base.common.service.CustomRoleUserService;
import net.zdsoft.eis.base.common.service.DeptService;
import net.zdsoft.eis.base.common.service.ModuleService;
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
import net.zdsoft.keel.util.Pagination;
import net.zdsoft.keel.util.ServletUtils;
import net.zdsoft.keelcnet.action.UploadFile;
import net.zdsoft.leadin.common.entity.BusinessTask;
import net.zdsoft.leadin.util.UUIDGenerator;
import net.zdsoft.leadin.util.excel.ZdCell;
import net.zdsoft.leadin.util.excel.ZdExcel;
import net.zdsoft.leadin.util.excel.ZdStyle;
import net.zdsoft.office.dailyoffice.entity.OfficeGoOut;
import net.zdsoft.office.dailyoffice.entity.OfficeJtgoOut;
import net.zdsoft.office.dailyoffice.service.OfficeGoOutService;
import net.zdsoft.office.dailyoffice.service.OfficeJtgoOutService;
import net.zdsoft.office.officeFlow.service.OfficeFlowService;
import net.zdsoft.office.util.Constants;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.Sheet;

/**
* @Package net.zdsoft.office.dailyoffice.action 
* @author songxq  
* @date 2016-5-20 下午2:59:47 
* @version V1.0
 */
@SuppressWarnings("serial")
public class GoOutAction extends PageAction{
	
	private OfficeGoOutService officeGoOutService;
	private FlowManageService flowManageService;
	private TaskHandlerService taskHandlerService;
	private UserService userService;
	private OfficeJtgoOutService officeJtgoOutService;
	private AttachmentService attachmentService;
	private ConverterFileTypeService converterFileTypeService;
	
	private List<Flow> flowList;
	private String taskHandlerSaveJson;
	private String currentStepId;
	private String flowId;
	private String taskId;
	private boolean isPass;
	private Date startTime;
	private Date endTime;
	private String deptId;
	private DeptService deptService;
	
	private String gooutId;
	private String jsonResult;
	private String id;
	private String taskDefinitionKey;
	private String processInstanceId;
	private String easyLevel;
	private Module module;
	private ModuleService moduleService;
	private String textComment;
	private boolean canChangeNextTask;
	
	private String states;
	private OfficeGoOut officeGoOut=new OfficeGoOut();
	private List<OfficeGoOut> officeGoOutList=new ArrayList<OfficeGoOut>();
	
	private OfficeJtgoOut officeJtgoOut=new OfficeJtgoOut();
	private List<OfficeJtgoOut> officeJtgoOuts=new ArrayList<OfficeJtgoOut>();
	
	private CustomRoleService customRoleService;
	private CustomRoleUserService customRoleUserService;
	
	private boolean schoolMaster;
	private final static String OFFICE_SCHOOLMASTER="office_schoolmaster";
	private String removeAttachmentId;
	private String fileSize;
	
	private String reTaskId;
	private String taskKey;
	private String showReBackId;
	private boolean canBeRetract;
	private OfficeFlowService officeFlowService;
	
	public String execute() throws Exception{
		return SUCCESS;
	}
	public String myGoOut(){
		return SUCCESS;
	}
	public String myGoOutList(){
		officeGoOutList=officeGoOutService.getOfficeGoOutByUnitIdUserIdPage(getUnitId(), getLoginUser().getUserId(), states, getPage());
		return SUCCESS;
	}
	public String myGoOutEdit(){
		//flowList=flowManageService.getFinishFlowList(getUnitId(), FlowConstant.FLOW_OWNER_UNIT, FlowConstant.OFFICE_GO_OUT,FlowConstant.OFFICE_SUBSYSTEM,FlowConstant.OFFICEDOC_FLOW_EASY_LEVEL_1);
		module=moduleService.getModuleByIntId(Constants.intId);
		if(StringUtils.isNotBlank(officeGoOut.getId())){
			officeGoOut=officeGoOutService.getOfficeGoOutById(officeGoOut.getId());
		}else{
			officeGoOut.setBeginTime(new Date());//默认现在
			officeGoOut.setUnitId(getUnitId());
			officeGoOut.setId(UUIDGenerator.getUUID());
		}
		officeGoOut=officeGoOutService.getOfficeGoOutByOfficeLog(officeGoOut, getUnitId(), getLoginUser().getUserId());
		flowList=officeGoOut.getFlowList();
		if(CollectionUtils.isEmpty(flowList)){
			flowList=flowManageService.getFinishFlowList(getUnitId(), FlowConstant.FLOW_OWNER_UNIT, FlowConstant.OFFICE_GO_OUT,FlowConstant.OFFICE_SUBSYSTEM,FlowConstant.OFFICEDOC_FLOW_EASY_LEVEL_1);
			List<Flow> flowList2  = flowManageService.getFinishFlowList(getLoginInfo().getUser().getDeptid(), FlowConstant.FLOW_OWNER_STRING, 
					FlowConstant.OFFICE_GO_OUT,FlowConstant.OFFICE_SUBSYSTEM,FlowConstant.OFFICEDOC_FLOW_EASY_LEVEL_1);
			if(CollectionUtils.isNotEmpty(flowList2)){
				flowList.addAll(flowList2);
			}
		}
		if(StringUtils.isBlank(officeGoOut.getFlowId())){
			for(Flow item : flowList){
				if(item.isDefaultFlow()){
					officeGoOut.setFlowId(item.getFlowId());
				}
				if(item.isOfferToSchool()){
					officeGoOut.setFlowId(item.getFlowId());
				}
			}
		}
		return SUCCESS;
	}
	public String myGoOutSave(){
		UploadFile file = null;
		try {
			//file = StorageFileUtils.handleFile(new String[] {},Integer.parseInt(systemIniService.getValue(Constants.FILE_INIID))*1024);
			if(officeGoOut.getBeginTime().compareTo(officeGoOut.getEndTime()) > 0){
				promptMessageDto.setOperateSuccess(false);
				promptMessageDto.setErrorMessage("开始时间与结束时间之间 前后时间不合逻辑，请更正！");
				return SUCCESS;
			}
			boolean flag = officeGoOutService.isExistConflict(officeGoOut.getId(),getLoginUser().getUserId(), officeGoOut.getBeginTime(), officeGoOut.getEndTime());
			if(flag) {
				promptMessageDto.setOperateSuccess(false);
				promptMessageDto.setErrorMessage("保存失败，该申请人在该时间段内已经存在申请！");
				return SUCCESS;
			}
			officeGoOut.setState(String.valueOf(Constants.APPLY_STATE_SAVE));
			if(StringUtils.isNotEmpty(officeGoOut.getId())){
				OfficeGoOut office=officeGoOutService.getOfficeGoOutById(officeGoOut.getId());
				if(office!=null){
					if(officeGoOut.getCreateTime() == null){
						OfficeGoOut oldGoout = officeGoOutService.getOfficeGoOutById(officeGoOut.getId());
						if(oldGoout != null){
							officeGoOut.setCreateTime(oldGoout.getCreateTime());
						}
						else{
							officeGoOut.setCreateTime(new Date());
						}
					}
					officeGoOut.setIsDeleted(false);
					officeGoOutService.update(officeGoOut,null,false,null);
				}else{
					officeGoOut.setCreateTime(new Date());
					officeGoOut.setApplyUserId(getLoginUser().getUserId());
					officeGoOut.setIsDeleted(false);
					officeGoOut.setUnitId(getUnitId());
					officeGoOutService.add(officeGoOut,null,false);
				}
			}
			promptMessageDto.setOperateSuccess(true);
			promptMessageDto.setPromptMessage("保存成功");
			promptMessageDto.setErrorMessage(officeGoOut.getId());
		} catch (Exception e) {
			e.printStackTrace();
			promptMessageDto.setOperateSuccess(false);
			promptMessageDto.setErrorMessage("保存失败:"+e.getMessage());
		}
		return SUCCESS;
	}
	public String myGoOutSubmit(){
		UploadFile file = null;
		try {
			//file = StorageFileUtils.handleFile(new String[] {}, Integer.parseInt(systemIniService.getValue(Constants.FILE_INIID))*1024);
			if(officeGoOut.getBeginTime().compareTo(officeGoOut.getEndTime()) > 0){
				promptMessageDto.setOperateSuccess(false);
				promptMessageDto.setErrorMessage("开始时间与结束时间之间 前后时间不合逻辑，请更正！");
				return SUCCESS;
			}
			boolean flag = officeGoOutService.isExistConflict(officeGoOut.getId(),getLoginUser().getUserId(), officeGoOut.getBeginTime(), officeGoOut.getEndTime());
			if(flag) {
				promptMessageDto.setOperateSuccess(false);
				promptMessageDto.setErrorMessage("保存失败，该申请人在该时间段内已经存在申请！");
				return SUCCESS;
			}
			officeGoOut.setState(String.valueOf(Constants.APPLY_STATE_NEED_AUDIT));
			OfficeGoOut office=officeGoOutService.getOfficeGoOutById(officeGoOut.getId());
			if(office==null){
				officeGoOut.setCreateTime(new Date());
				officeGoOut.setApplyUserId(getLoginUser().getUserId());
				officeGoOut.setIsDeleted(false);
				officeGoOut.setUnitId(getUnitId());
			}
			officeGoOutService.startFlow(officeGoOut,getLoginUser().getUserId(),null,false,null);
			promptMessageDto.setOperateSuccess(true);
			promptMessageDto.setPromptMessage("提交成功,已进入流程中");
		} catch (Exception e) {
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
	public String myGoOutDelete(){
		if(StringUtils.isNotEmpty(officeGoOut.getId())){
			try {
				officeGoOutService.delete(new String[]{officeGoOut.getId()});
				promptMessageDto.setOperateSuccess(true);
				promptMessageDto.setPromptMessage("删除成功");
			} catch (Exception e) {
				promptMessageDto.setOperateSuccess(false);
				promptMessageDto.setErrorMessage("删除失败:"+e.getCause().getMessage());
			}
		}else{
			promptMessageDto.setOperateSuccess(false);
			promptMessageDto.setErrorMessage("删除失败:找不到删除的对象");
		}
		return SUCCESS;
	}
	public String jtGoOut(){//TODO
		return SUCCESS;
	}
	public String jtGoOutList(){
		officeJtgoOuts=officeJtgoOutService.getOfficeJtgoOutByUnitIdAndStates(getUnitId(), states, getPage());
		return SUCCESS;
	}
	public String jtGoOutEdit(){
		flowList=flowManageService.getFinishFlowList(getUnitId(), FlowConstant.FLOW_OWNER_UNIT, FlowConstant.OFFICE_GO_OUT, FlowConstant.OFFICE_SUBSYSTEM, FlowConstant.OFFICEDOC_FLOW_EASY_LEVEL_1);
		List<Flow> flowList2  = flowManageService.getFinishFlowList(getLoginInfo().getUser().getDeptid(), FlowConstant.FLOW_OWNER_STRING, 
				FlowConstant.OFFICE_GO_OUT,FlowConstant.OFFICE_SUBSYSTEM,FlowConstant.OFFICEDOC_FLOW_EASY_LEVEL_1);
		if(CollectionUtils.isNotEmpty(flowList2)){
			flowList.addAll(flowList2);
		}
		
		if(StringUtils.isNotBlank(officeJtgoOut.getId())){
			officeJtgoOut=officeJtgoOutService.getOfficeJtgoOutById(officeJtgoOut.getId());
		}else{
			officeJtgoOut.setUnitId(getUnitId());
			for(Flow item : flowList){
				if(item.isDefaultFlow()){
					officeJtgoOut.setFlowId(item.getFlowId());
				}
			}
		}
		return SUCCESS;
	}
	public String jtGoOutSave(){//TODO
		UploadFile file;
		try {
			file=StorageFileUtils.handleFile(new String[]{}, Integer.parseInt(systemIniService.getValue(Constants.FILE_INIID))*1024);
			officeJtgoOut.setState(String.valueOf(Constants.APPLY_STATE_SAVE));
			if(StringUtils.isNotBlank(officeJtgoOut.getId())){
				officeJtgoOutService.update(officeJtgoOut, file);
			}else{
				officeJtgoOut.setApplyUserId(getLoginUser().getUserId());
				officeJtgoOut.setCreateTime(new Date());
				officeJtgoOut.setUnitId(getUnitId());
				officeJtgoOut.setIsDeleted(false);
				officeJtgoOutService.save(officeJtgoOut, file);
			}
			promptMessageDto.setOperateSuccess(true);
			promptMessageDto.setPromptMessage("操作成功");
			promptMessageDto.setErrorMessage(officeJtgoOut.getId());
		} catch (Exception e) {
			e.printStackTrace();
			promptMessageDto.setOperateSuccess(false);
			promptMessageDto.setErrorMessage("操作失败");
		}
		return SUCCESS;
	}
	public String jtGoOutSubmit(){
		UploadFile file=null;
		try {
			file=StorageFileUtils.handleFile(new String[]{}, Integer.parseInt(systemIniService.getValue(Constants.FILE_INIID))*1024);
			officeJtgoOut.setState(String.valueOf(Constants.APPLY_STATE_NEED_AUDIT));
			if(StringUtils.isBlank(officeJtgoOut.getId())){
				officeJtgoOut.setApplyUserId(getLoginUser().getUserId());
				officeJtgoOut.setCreateTime(new Date());
				officeJtgoOut.setIsDeleted(false);
				officeJtgoOut.setUnitId(getUnitId());
			}
			officeJtgoOutService.startFlow(officeJtgoOut, getLoginUser().getUserId(), file);
			promptMessageDto.setOperateSuccess(true);
			promptMessageDto.setPromptMessage("提交成功,已进入流程中");
		} catch (Exception e) {
			e.printStackTrace();
			promptMessageDto.setOperateSuccess(false);
			promptMessageDto.setErrorMessage("提交失败:"+e.getCause().getMessage());
		}
		return SUCCESS;
	}
	public String jtGoOutDelete(){
		if(StringUtils.isNotBlank(officeJtgoOut.getId())){
			try {
				officeJtgoOutService.delete(new String[]{officeJtgoOut.getId()});
				promptMessageDto.setOperateSuccess(true);
				promptMessageDto.setPromptMessage("删除成功");
			} catch (Exception e) {
				e.printStackTrace();
				promptMessageDto.setOperateSuccess(false);
				promptMessageDto.setPromptMessage("删除失败"+e.getMessage());
			}
		}else{
			promptMessageDto.setOperateSuccess(false);
			promptMessageDto.setErrorMessage("删除失败：找不到Id");
		}
		return SUCCESS;
	}
	public String jtGoOutView(){
		officeJtgoOut=officeJtgoOutService.getOfficeJtgoOutById(officeJtgoOut.getId());
		return SUCCESS;
	}
	public String jtGoOutAudit(){
		officeJtgoOut=officeJtgoOutService.getOfficeJtgoOutById(officeJtgoOut.getId());
		TaskHandlerSave taskHandlerSave=new TaskHandlerSave();
		TaskHandlerShow taskHandlerShow=taskHandlerService.getTaskHandlerShow(taskId, getLoginUser().getUserId());
		taskHandlerSave.setCurrentTask(taskHandlerShow.getCurrentTask());
		taskHandlerSave.setCurrentUserId(getLoginUser().getUserId());
		taskHandlerSave.setCurrentUnitId(getUnitId());
		taskHandlerSave.setSubsystemId(FlowConstant.OFFICE_SUBSYSTEM);
		User user=userService.getUser(getLoginUser().getUserId());
		Comment comment=new Comment();
		comment.setAssigneeType(AgileIdentityLinkType.PRINCIPAL);
		comment.setAssigneeName(user.getRealname());
		comment.setAssigneeId(user.getId());
		officeJtgoOut.setUserName(user.getRealname());
		taskHandlerSave.setComment(comment);
		JSONObject json=new JSONObject().fromObject(taskHandlerSave);
		taskHandlerSaveJson=json.toString();
		officeJtgoOut.setTaskName(taskHandlerSave.getCurrentTask().getTaskName());
		currentStepId=taskHandlerSave.getCurrentTask().getTaskDefinitionKey();
		flowId=officeJtgoOut.getFlowId();
		return SUCCESS;
	}
	
	public String invalidJtGoOut(){
		if(StringUtils.isNotEmpty(officeJtgoOut.getId())){
			try {
				officeJtgoOutService.deleteInvalid(officeJtgoOut.getId(),getLoginInfo().getUser().getId());
				promptMessageDto.setOperateSuccess(true);
				promptMessageDto.setPromptMessage("作废成功");
			} catch (Exception e) {
				e.printStackTrace();
				promptMessageDto.setOperateSuccess(false);
				promptMessageDto.setErrorMessage("作废失败");
			}
		}else{
			promptMessageDto.setOperateSuccess(false);
			promptMessageDto.setErrorMessage("作废失败:找不到作废的id");
		}
		return SUCCESS;
	}
	public String jtGoOutPass(){
		try {
			JSONObject object = new JSONObject().fromObject(taskHandlerSaveJson);
			TaskHandlerSave taskHandlerSave =(TaskHandlerSave) JSONObject.toBean(object,
				 TaskHandlerSave.class);
			taskHandlerSave.getComment().setOperateTime(new Date());
			officeJtgoOutService.passOfficeJtgoOut(isPass(), taskHandlerSave, officeJtgoOut.getId());
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
	//取消
	public String myGoOutRevoke(){
		if(StringUtils.isNotEmpty(officeGoOut.getId())){
			try {
				officeGoOutService.deleteRevoke(officeGoOut.getId());
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
	public String myGoOutView(){
		officeGoOut=officeGoOutService.getOfficeGoOutById(officeGoOut.getId());
		if(officeGoOut==null){
			officeGoOut=new OfficeGoOut();
		}
		return SUCCESS;
	}
	public String goOut(){
		return SUCCESS;
	}
	public String goOutList(){
		if("0".equals(states)){
			officeGoOutList=officeGoOutService.toDoAudit(getLoginUser().getUserId(),getPage());
		}else if("3".equals(states)){
			officeGoOutList = officeGoOutService.getOfficeGoOutByUnitIdList(getLoginInfo().getUnitID());
			officeGoOutList = officeFlowService.haveDoneAudit(officeGoOutList, getLoginUser().getUserId(), getPage());
			for(OfficeGoOut goout : officeGoOutList){
				goout.setDesHours(String.valueOf(goout.getHours()));
			}
		}else if("2".equals(states)){
			officeGoOutList=officeGoOutService.HaveDoAudit(getLoginUser().getUserId(),false,null);
		}else{
			officeGoOutList=officeGoOutService.HaveDoAudit(getLoginUser().getUserId(),true,null);
		}
		//重新分页
		Pagination page = getPage();
		Integer maxRow = officeGoOutList.size();
		page.setMaxRowCount(maxRow);
		page.initialize();
		if(CollectionUtils.isNotEmpty(officeGoOutList)){
			Integer oldCur = page.getCurRowNum();
			Integer newCur = page.getPageIndex()*page.getPageSize();
			newCur = newCur>maxRow?maxRow:newCur;
			List<OfficeGoOut> newList = new ArrayList<OfficeGoOut>();
			
			newList.addAll(officeGoOutList.subList(oldCur-1, newCur));
			officeGoOutList = newList;
		}
		return SUCCESS;
	}
	public String goOutAudit(){
		officeGoOut = officeGoOutService.getOfficeGoOutById(officeGoOut.getId());
		TaskHandlerSave taskHandlerSave = new TaskHandlerSave();
		TaskHandlerShow taskHandlerShow = taskHandlerService.getTaskHandlerShow(taskId, getLoginUser().getUserId());
		taskHandlerSave.setCurrentTask(taskHandlerShow.getCurrentTask());
		taskHandlerSave.setCurrentUserId(getLoginUser().getUserId());
		taskHandlerSave.setCurrentUnitId(getLoginUser().getUnitId());
		taskHandlerSave.setSubsystemId(FlowConstant.OFFICE_SUBSYSTEM);
		User user = userService.getUser(getLoginUser().getUserId());
		textComment=officeGoOutService.getTextComment(getUnitId(),user.getId());
		
		Comment comment = new Comment();
		comment.setAssigneeType(AgileIdentityLinkType.PRINCIPAL);
		comment.setAssigneeId(user.getId());
		comment.setAssigneeName(user.getRealname());
		officeGoOut.setApplyUserName(user.getRealname());
		taskHandlerSave.setComment(comment);
		JSONObject json = new JSONObject().fromObject(taskHandlerSave);
		taskHandlerSaveJson = json.toString();
		officeGoOut.setTaskName(taskHandlerSave.getCurrentTask().getTaskName());
		currentStepId = taskHandlerSave.getCurrentTask().getTaskDefinitionKey();
		flowId = officeGoOut.getFlowId();
		return SUCCESS;
	}
	public String goOutPass(){
		try {
			JSONObject object = new JSONObject().fromObject(taskHandlerSaveJson);
			TaskHandlerSave taskHandlerSave =(TaskHandlerSave) JSONObject.toBean(object,
				 TaskHandlerSave.class);
			taskHandlerSave.getComment().setOperateTime(new Date());
			officeGoOutService.passFlow(isPass(),taskHandlerSave,officeGoOut.getId(), currentStepId);
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
	public String goOutView(){
		officeGoOut=officeGoOutService.getOfficeGoOutById(officeGoOut.getId());
		if(officeGoOut==null){
			officeGoOut=new OfficeGoOut();
		}
		return SUCCESS;
	}
	/**
	 * 审核通过的作废
	 * @return
	 * 2016年9月12日
	 */
	public String invalidLeave(){
		if(StringUtils.isNotEmpty(officeGoOut.getId())){
			try {
				officeGoOutService.deleteInvalid(officeGoOut.getId(),getLoginInfo().getUser().getId());
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
	
	//外出统计
	public String goStatistics(){
		Calendar c = Calendar.getInstance();
		endTime = c.getTime();
		c.set(Calendar.DAY_OF_MONTH, c.getActualMinimum(Calendar.DAY_OF_MONTH));
		startTime = c.getTime();
		return SUCCESS;
	}
	public Date getTMDate(Date time){
		Calendar c = Calendar.getInstance();
		c.setTime(time);
		c.add(c.DATE, 1);
		return c.getTime();
	}
	public String goStatisticsList(){
		if(endTime!=null){
			endTime=getTMDate(endTime);
		}
		officeGoOutList=officeGoOutService.getStatistics(getUnitId(),startTime, endTime, this.getUserIds(deptId));
		return SUCCESS;
	}
	
	public String[] getUserIds(String deptid){
		List<User> users = new ArrayList<User>();
		if(!this.isUnitViewRole()){
			deptid = deptService.isPrincipanGroupHead(getLoginInfo().getUser().getId());
			if(StringUtils.isBlank(deptid)){//不是部门负责人
				users.add(getLoginInfo().getUser());
			}
			else{
				users = userService.getUsersByDeptId(deptid);
				if(!StringUtils.equals(deptid, getLoginInfo().getUser().getDeptid())){//负责的部门非自己所在部门，查询人员再加自己
					users.add(getLoginInfo().getUser());
				}
			}
		}
		else{
			if(StringUtils.isBlank(deptid)){
				users = userService.getUsers(getLoginInfo().getUser().getUnitid());
			}
			else{
				users = userService.getUsersByDeptId(deptid);
			}
		}
		Set<String> uids = new HashSet<String>(); 
		for(User item : users){
			uids.add(item.getId());
		}
		
		if(CollectionUtils.isEmpty(users)){
			return new String[]{" "};
		}else{
			return uids.toArray(new String[0]);
		}
	}
	
	public String goStatisticsExport(){
		endTime=getTMDate(endTime);
		
		officeGoOutList=officeGoOutService.getStatistics(getUnitId(),startTime, endTime, this.getUserIds(deptId));
		ZdExcel zdExcel = new ZdExcel();
		ZdStyle style2 = new ZdStyle(ZdStyle.BOLD|ZdStyle.BORDER|ZdStyle.ALIGN_CENTER);
		ZdStyle style3 = new ZdStyle(ZdStyle.BORDER);
		zdExcel.add(new ZdCell("外出统计", 7, 2, new ZdStyle(ZdStyle.BOLD|ZdStyle.ALIGN_CENTER|ZdStyle.WRAP_TEXT, 18)));
		
		List<ZdCell> zdlist=new ArrayList<ZdCell>();
		zdlist.add(new ZdCell("姓名",1,2,style2));
		zdlist.add(new ZdCell("因公外出",2,1,style2));
		zdlist.add(new ZdCell("因私外出",2,1,style2));
		zdlist.add(new ZdCell("总计",2,1,style2));
		zdExcel.add(zdlist.toArray(new ZdCell[0]));
		
		ZdCell[] cells1 = new ZdCell[7];
		cells1[0] = new ZdCell();
		cells1[1] = new ZdCell("外出次数", 1, style3);
		cells1[2] = new ZdCell("外出时间(小时)", 1, style3);
		cells1[3] = new ZdCell("外出次数", 1, style3);
		cells1[4] = new ZdCell("外出时间(小时)", 1, style3);
		cells1[5] = new ZdCell("外出次数", 1, style3);
		cells1[6] = new ZdCell("外出时间(小时)", 1, style3);
		zdExcel.add(cells1);
		for(OfficeGoOut office:officeGoOutList){
				ZdCell[] cells = new ZdCell[7];
				
				cells[0] = new ZdCell(office.getApplyUserName(), 1, style3);
				cells[1] = new ZdCell(String.valueOf(office.getNumJob()!=null?office.getNumJob():0), 1, style3);
				cells[2] = new ZdCell(String.valueOf(office.getHoursJob()!=null?office.getHoursJob():0), 1, style3);
				cells[3] = new ZdCell(String.valueOf(office.getNumSelf()!=null?office.getNumSelf():0), 1, style3);
				cells[4] = new ZdCell(String.valueOf(office.getHoursSelf()!=null?office.getHoursSelf():0), 1, style3);
				cells[5] = new ZdCell(String.valueOf(office.getOutNum()!=null?office.getOutNum():0), 1, style3);
				cells[6] = new ZdCell(String.valueOf(office.getSumHours()!=null?office.getSumHours():0), 1, style3);
				zdExcel.add(cells);
		}
		
		Sheet sheet = zdExcel.createSheet("sheet1");
		for(int i=0;i<7;i++){
			zdExcel.setCellWidth(sheet, i, 2);
		}
		zdExcel.export("go_out");
		return NONE;
	}
	
	public String changeFlow() throws IOException{//TODO
		String value = "success";
		if(StringUtils.isNotBlank(jsonResult)){
			try {
				String gooutid = gooutId;
				if(gooutid.contains("_")){
					gooutId = gooutid.split("_")[0];
					String type = gooutid.split("_")[1];
					if(StringUtils.equals(type, "1")){
						officeGoOutService.changeFlow(gooutId,getLoginUser().getUserId(),id,jsonResult);
					}else{
						officeJtgoOutService.changeFlow(gooutId,getLoginUser().getUserId(),id,jsonResult);
					}
				}
				else{
					officeGoOutService.changeFlow(gooutId,getLoginUser().getUserId(),id,jsonResult);
				}
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
		String flowid = "";
		String gooutid = gooutId;
		if(gooutid.contains("_")){
			gooutId = gooutid.split("_")[0];
			String type = gooutid.split("_")[1];
			if(StringUtils.equals(type, "1")){
				OfficeGoOut goout=officeGoOutService.getOfficeGoOutById(gooutId);
				flowid = goout.getFlowId();
			}else{
				OfficeJtgoOut goout = officeJtgoOutService.getOfficeJtgoOutById(gooutId);
				flowid = goout.getFlowId();
			}
		}else{
			OfficeGoOut goout=officeGoOutService.getOfficeGoOutById(gooutId);
			flowid = goout.getFlowId();
		}
		List<TaskDescription> todoTasks = taskHandlerService.getTodoTasks(flowid);
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
					attachment.setObjectId(gooutId);
					attachment.setObjectType(Constants.OFFICE_BUSINESS_TRIP_ATT);
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
	
	/**
	 * 单位下教研组
	 * @return
	 */
	public List<Dept> getDirectDepts() {
		return deptService.getDepts(getUnitId());//getDirectDepts(getUnitId(),2,null);
	}
	
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
	
	public String getStates() {
		return states;
	}
	public void setStates(String states) {
		this.states = states;
	}
	public OfficeGoOut getOfficeGoOut() {
		return officeGoOut;
	}
	public void setOfficeGoOut(OfficeGoOut officeGoOut) {
		this.officeGoOut = officeGoOut;
	}
	public List<OfficeGoOut> getOfficeGoOutList() {
		return officeGoOutList;
	}
	public void setOfficeGoOutList(List<OfficeGoOut> officeGoOutList) {
		this.officeGoOutList = officeGoOutList;
	}
	public void setOfficeGoOutService(OfficeGoOutService officeGoOutService) {
		this.officeGoOutService = officeGoOutService;
	}
	public List<Flow> getFlowList() {
		return flowList;
	}
	public void setFlowList(List<Flow> flowList) {
		this.flowList = flowList;
	}
	public void setFlowManageService(FlowManageService flowManageService) {
		this.flowManageService = flowManageService;
	}
	public String getTaskHandlerSaveJson() {
		return taskHandlerSaveJson;
	}
	public void setTaskHandlerSaveJson(String taskHandlerSaveJson) {
		this.taskHandlerSaveJson = taskHandlerSaveJson;
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
	public String getTaskId() {
		return taskId;
	}
	public void setTaskId(String taskId) {
		this.taskId = taskId;
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
	public void setUserService(UserService userService) {
		this.userService = userService;
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
	public void setDeptService(DeptService deptService) {
		this.deptService = deptService;
	}
	public boolean isUnitViewRole() {
		CustomRole role = customRoleService.getCustomRoleByRoleCode(getUnitId(), "go_out_view");
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
	public void setCustomRoleUserService(CustomRoleUserService customRoleUserService) {
		this.customRoleUserService = customRoleUserService;
	}
	public OfficeJtgoOut getOfficeJtgoOut() {
		return officeJtgoOut;
	}
	public void setOfficeJtgoOut(OfficeJtgoOut officeJtgoOut) {
		this.officeJtgoOut = officeJtgoOut;
	}
	public List<OfficeJtgoOut> getOfficeJtgoOuts() {
		return officeJtgoOuts;
	}
	public void setOfficeJtgoOuts(List<OfficeJtgoOut> officeJtgoOuts) {
		this.officeJtgoOuts = officeJtgoOuts;
	}
	public void setOfficeJtgoOutService(OfficeJtgoOutService officeJtgoOutService) {
		this.officeJtgoOutService = officeJtgoOutService;
	}
	public void setCustomRoleService(CustomRoleService customRoleService) {
		this.customRoleService = customRoleService;
	}
	public boolean isSchoolMaster() {
		CustomRole customRole=customRoleService.getCustomRoleByRoleCode(getUnitId(), OFFICE_SCHOOLMASTER);
		if(customRole==null){
			return false;
		}
		List<CustomRoleUser> cus=customRoleUserService.getCustomRoleUserListByUserId(getLoginUser().getUserId());
		if(CollectionUtils.isNotEmpty(cus)){
			for (CustomRoleUser customRoleUser : cus) {
				if(StringUtils.equals(customRole.getId(), customRoleUser.getRoleId())){
					return true;
				}
			}
		}
		return false;
	}
	public void setSchoolMaster(boolean schoolMaster) {
		this.schoolMaster = schoolMaster;
	}
	public String getGooutId() {
		return gooutId;
	}
	public void setGooutId(String gooutId) {
		this.gooutId = gooutId;
	}
	public String getJsonResult() {
		return jsonResult;
	}
	public void setJsonResult(String jsonResult) {
		this.jsonResult = jsonResult;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
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
	public String getEasyLevel() {
		return easyLevel;
	}
	public void setEasyLevel(String easyLevel) {
		this.easyLevel = easyLevel;
	}
	public Module getModule() {
		return module;
	}
	public void setModule(Module module) {
		this.module = module;
	}
	public void setModuleService(ModuleService moduleService) {
		this.moduleService = moduleService;
	}
	public String getTextComment() {
		return textComment;
	}
	public void setTextComment(String textComment) {
		this.textComment = textComment;
	}
	public String getRemoveAttachmentId() {
		return removeAttachmentId;
	}
	public void setRemoveAttachmentId(String removeAttachmentId) {
		this.removeAttachmentId = removeAttachmentId;
	}
	public void setAttachmentService(AttachmentService attachmentService) {
		this.attachmentService = attachmentService;
	}
	public String getFileSize() {
		return  systemIniService.getValue(Constants.FILE_INIID);
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
					.checkRetract(officeGoOut.getHisTaskList(), getLoginUser().getUserId(), officeGoOut.getFlowId()));
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
	public void setOfficeFlowService(OfficeFlowService officeFlowService) {
		this.officeFlowService = officeFlowService;
	}
	public boolean isCanChangeNextTask() {
		int num = officeFlowService.getTaskDescNum(getUnitId(), getLoginUser().getUserId(), taskId);
		return num>0;
	}
	public void setCanChangeNextTask(boolean canChangeNextTask) {
		this.canChangeNextTask = canChangeNextTask;
	}
}
