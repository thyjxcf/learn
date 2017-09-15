package net.zdsoft.office.remote;

import java.util.Date;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.zdsoft.eis.base.common.entity.Dept;
import net.zdsoft.eis.base.common.entity.User;
import net.zdsoft.eis.base.common.service.DeptService;
import net.zdsoft.eis.base.common.service.UserService;
import net.zdsoft.eis.base.common.service.UserSetService;
import net.zdsoft.eis.base.storage.StorageFileUtils;
import net.zdsoft.eis.component.flowManage.constant.FlowConstant;
import net.zdsoft.eis.component.flowManage.entity.Flow;
import net.zdsoft.eis.component.flowManage.service.FlowManageService;
import net.zdsoft.jbpm.activiti.engine.task.AgileIdentityLinkType;
import net.zdsoft.jbpm.core.entity.Comment;
import net.zdsoft.jbpm.core.entity.TaskHandlerSave;
import net.zdsoft.jbpm.core.entity.TaskHandlerShow;
import net.zdsoft.jbpm.core.service.TaskHandlerService;
import net.zdsoft.keel.util.DateUtils;
import net.zdsoft.keel.util.Pagination;
import net.zdsoft.keelcnet.action.UploadFile;
import net.zdsoft.office.dailyoffice.entity.OfficeGoOut;
import net.zdsoft.office.dailyoffice.service.OfficeGoOutService;
import net.zdsoft.office.util.Constants;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

/**
* @Package net.zdsoft.office.remote 
* @author songxq  
* @date 2016-5-30 下午2:56:49 
* @version V1.0
 */
@SuppressWarnings("serial")
public class RemoteGoOutAction extends OfficeJsonBaseAction{
	
	private OfficeGoOut	officeGoOut;
	private List<OfficeGoOut> officeGoOutList;
	private FlowManageService flowManageService;
	private OfficeGoOutService officeGoOutService; 
	private UserService userService;
	private UserSetService userSetService;
	private DeptService deptService;
	
	private int applyStatus;
	
	private String userId;
	private String unitId;
	private String userName;
	private Date startTime;
	private Date endTime;
	private String id;
	
	private List<Flow> flowList;
	private String taskId;
	private TaskHandlerService taskHandlerService;
	private String taskHandlerSaveJson;
	private boolean isPass;
	private String currentStepId;
	private String flowId;
	
	private int auditStatus;
	private String fromTab;
	
	public void applyList(){
		Pagination page = getPage();
		officeGoOutList = officeGoOutService.getOfficeGoOutByUnitIdUserIdPage(unitId, userId, String.valueOf(applyStatus), page);
		JSONArray array = new JSONArray();
		for(OfficeGoOut officeGoOut : officeGoOutList){
			JSONObject json = new JSONObject();
			json.put("id", officeGoOut.getId());
			json.put("beginTime", DateUtils.date2String(officeGoOut.getBeginTime(), "MM-dd HH:mm"));
			String content = officeGoOut.getTripReason();
			if(content.length() > 25){
				content = content.substring(0, 25)+"...";
			}
			json.put("hours", officeGoOut.getHours());
			json.put("content", content);
			json.put("applyStatus", officeGoOut.getState());
			array.add(json);
		}
		jsonMap.put(getListObjectName(), array);
		jsonMap.put("page", page);
		jsonMap.put("result", 1);
		responseJSON(jsonMap);
	}
	public void applyGoOut(){
		if(StringUtils.isNotEmpty(id)){
			officeGoOut = officeGoOutService.getOfficeGoOutById(id);
			if(officeGoOut==null){
				officeGoOut = new OfficeGoOut();
				User user = userService.getUser(userId);
				officeGoOut.setApplyUserId(user.getId());
				officeGoOut.setUserName(user.getRealname());
				officeGoOut.setUnitId(unitId);
			}
		}else{
			officeGoOut=new OfficeGoOut();
			User user = userService.getUser(userId);
			officeGoOut.setApplyUserId(user.getId());
			officeGoOut.setUserName(user.getRealname());
			officeGoOut.setUnitId(unitId);
		}
		flowList = flowManageService.getFinishFlowList(unitId, FlowConstant.FLOW_OWNER_UNIT, FlowConstant.OFFICE_GO_OUT,FlowConstant.OFFICE_SUBSYSTEM,FlowConstant.OFFICEDOC_FLOW_EASY_LEVEL_1);
		JSONObject json = new JSONObject();
		json.put("id", officeGoOut.getId());
		json.put("unitId", officeGoOut.getUnitId());
		json.put("createTime", new Date());
		json.put("applyUserId", officeGoOut.getApplyUserId());
		json.put("applyUserName", officeGoOut.getUserName());
		json.put("beginTime", DateUtils.date2String(officeGoOut.getBeginTime(), "yyyy-MM-dd HH:mm"));
		json.put("endTime", DateUtils.date2String(officeGoOut.getEndTime(), "yyyy-MM-dd HH:mm"));
		json.put("hours", officeGoOut.getHours());
		json.put("tripReason", officeGoOut.getTripReason());
		json.put("attachmentArray",RemoteOfficeUtils.createAttachmentArray(officeGoOut.getAttachments()));
		json.put("flowId", officeGoOut.getFlowId());
		
		JSONArray flowArray = new JSONArray();
		if(CollectionUtils.isNotEmpty(flowList)){
			for (Flow flow : flowList) {
				JSONObject flowObject=new JSONObject();
				flowObject.put("flowId", flow.getFlowId());
				flowObject.put("flowName", flow.getFlowName());
				flowObject.put("isDefaultFlow", flow.isDefaultFlow());
				flowArray.add(flowObject);
			}
		}
		JSONArray typeArray = new JSONArray();
		for(int i=1;i<3;i++){
			JSONObject typeObject=new JSONObject();
			typeObject.put("type", i);
			if(i==1){
				typeObject.put("typeName", "因公外出");
			}else{
				typeObject.put("typeName", "因私外出");
			}
			typeArray.add(typeObject);
		}
		json.put("typeArray",typeArray);
		json.put("flowArray",flowArray);
		jsonMap.put(getDetailObjectName(), json);
		jsonMap.put("result",1);
		responseJSON(jsonMap);
	}
	public void saveGoOut(){
		UploadFile file = null;
		JSONObject json = new JSONObject();
		try {
			file = StorageFileUtils.handleFile(new String[] {}, 5*1024);
			officeGoOut.setState(String.valueOf(Constants.LEAVE_APPLY_SAVE));
			officeGoOut.setApplyUserId(userId);
			boolean flag = officeGoOutService.isExistConflict(officeGoOut.getId(),userId,startTime, endTime);
			if(flag) {
				jsonMap.put("result", 0);
				jsonMap.put("msg", "保存失败，该申请人在该时间段内已经存在申请！");
				responseJSON(jsonMap);
				return;
			}
			if(StringUtils.isNotEmpty(officeGoOut.getId())){
				officeGoOutService.update(officeGoOut,file);
			}else{
				officeGoOutService.add(officeGoOut,file);
			}
			json.put("result",1);
		}catch (Exception e) {
			e.printStackTrace();
			json.put("result",0);
		}
		responseJSON(json);
	}
	public void submitGoOut(){
		UploadFile file = null;
		JSONObject json = new JSONObject();
		try {
			boolean flag = officeGoOutService.isExistConflict(officeGoOut.getId(),userId,officeGoOut.getBeginTime(), officeGoOut.getEndTime());
			if(flag) {
				jsonMap.put("result", 0);
				jsonMap.put("msg", "保存失败，该申请人在该时间段内已经存在申请！");
				responseJSON(jsonMap);
				return;
			}
			file = StorageFileUtils.handleFile(new String[] {}, 5*1024);
			officeGoOut.setApplyUserId(userId);
			officeGoOut.setCreateTime(new Date());
			officeGoOut.setIsDeleted(false);
			officeGoOut.setState(String.valueOf(Constants.LEAVE_APPLY_FLOWING));
			officeGoOutService.startFlow(officeGoOut,userId,file);
			json.put("result",1);
		}catch (Exception e) {
			json.put("result",0);
			e.printStackTrace();
			if(e.getCause()!=null){
				if(e.getCause().getMessage()!=null){
					json.put("msg", "提交失败:"+e.getCause().getMessage());
				}else{
					json.put("msg", "未设置流程审核人员");
				}
			}else{
				json.put("msg", "提交失败:"+e.getMessage());
			}
		}
		responseJSON(json);
	}
	public void deleteBusinessTrip(){
		JSONObject json = new JSONObject();
		if(StringUtils.isNotEmpty(id)){
			try {
				officeGoOutService.delete(new String[]{id});
				json.put("result",1);
			} catch (Exception e) {
				json.put("result",0);
			}
		}else{
			json.put("result",0);
		}
		responseJSON(json);
	}
	
	public void doDeleteApply(){
		JSONObject json = new JSONObject();
		if(StringUtils.isNotEmpty(id)){
			try {
				officeGoOutService.deleteRevoke(id);
				json.put("result",1);
			} catch (Exception e) {
				json.put("result",0);
			}
		}else{
			json.put("result",0);
		}
		responseJSON(json);
	}
	public void auditList(){
		Pagination page=getPage();
//		page.setPageSize(15);
		if(auditStatus==0){
			officeGoOutList=officeGoOutService.toDoAudit(userId,page);
		}else{
			officeGoOutList=officeGoOutService.HaveDoAudit(userId,true,page);
		}
		JSONArray array = new JSONArray();
		for(OfficeGoOut officeGoOut : officeGoOutList){
			JSONObject json = new JSONObject();
			json.put("id", officeGoOut.getId());
			json.put("beginTime", DateUtils.date2String(officeGoOut.getBeginTime(), "MM-dd HH:mm"));
			json.put("applyUserName", officeGoOut.getUserName());
			String content = officeGoOut.getTripReason();
			if(content.length() > 25){
				content = content.substring(0, 25)+"...";
			}
			json.put("content", content);
			json.put("hours", officeGoOut.getHours());
			json.put("applyStatus", officeGoOut.getState());
			json.put("taskId", officeGoOut.getTaskId());
			array.add(json);
		}
		jsonMap.put(getListObjectName(), array);
		jsonMap.put("page", page);
		jsonMap.put("result", 1);
		responseJSON(jsonMap);
	}
	public void auditGoOut(){
		officeGoOut = officeGoOutService.getOfficeGoOutById(id);
		TaskHandlerSave taskHandlerSave = new TaskHandlerSave();
		TaskHandlerShow taskHandlerShow = taskHandlerService.getTaskHandlerShow(taskId, userId);
		taskHandlerSave.setCurrentTask(taskHandlerShow.getCurrentTask());
		taskHandlerSave.setCurrentUserId(userId);
		taskHandlerSave.setCurrentUnitId(unitId);
		taskHandlerSave.setSubsystemId(FlowConstant.OFFICE_SUBSYSTEM);
		User user = userService.getUser(userId);
		Comment comment = new Comment();
		comment.setAssigneeType(AgileIdentityLinkType.PRINCIPAL);
		comment.setAssigneeId(user.getId());
		comment.setAssigneeName(user.getRealname());
		userName = user.getRealname();
		taskHandlerSave.setComment(comment);
		JSONObject jsonStr = new JSONObject().fromObject(taskHandlerSave);
		taskHandlerSaveJson = jsonStr.toString();
		officeGoOut.setTaskName(taskHandlerSave.getCurrentTask().getTaskName());
		currentStepId = taskHandlerSave.getCurrentTask().getTaskDefinitionKey();
		flowId = officeGoOut.getFlowId();
		
		String photoUrl = userSetService.getUserPhotoUrl(officeGoOut.getApplyUserId());
		String deptName = "";
		if(user != null){
			Dept dept = deptService.getDept(user.getDeptid());
			if(dept!=null){
				deptName = dept.getDeptname();
			}
		}
		
		JSONObject json = new JSONObject();
		json.put("id", officeGoOut.getId());
		json.put("applyUserName", officeGoOut.getUserName());
		json.put("deptName", deptName);
		json.put("photoUrl", photoUrl);
		json.put("beginTime", DateUtils.date2String(officeGoOut.getBeginTime(), "yyyy-MM-dd HH:mm"));
		json.put("endTime", DateUtils.date2String(officeGoOut.getEndTime(), "yyyy-MM-dd HH:mm"));
		json.put("hours", officeGoOut.getHours());
		json.put("typeStr", getTypeStr(officeGoOut.getOutType()));
		json.put("tripReason", officeGoOut.getTripReason());
		json.put("attachmentArray",RemoteOfficeUtils.createAttachmentArray(officeGoOut.getAttachments()));
		json.put("taskHandlerSaveJson", taskHandlerSaveJson);
		json.put("taskName", taskHandlerSave.getCurrentTask().getTaskName());
		json.put("userName", userName);
		json.put("currentStepId", currentStepId);
		json.put("flowId", flowId);
		json.put("hisTaskCommentArray",RemoteOfficeUtils.createHisTaskCommentArray(officeGoOut.getHisTaskList()));
		
		jsonMap.put(getDetailObjectName(), json);
		jsonMap.put("result", 1);
		responseJSON(jsonMap);
	}
	public void auditPassGoOut(){
		JSONObject json = new JSONObject();
		try {
			JSONObject object = new JSONObject().fromObject(taskHandlerSaveJson);
			TaskHandlerSave taskHandlerSave =(TaskHandlerSave) JSONObject.toBean(object,
				 TaskHandlerSave.class);
			taskHandlerSave.getComment().setOperateTime(new Date());
			officeGoOutService.passFlow(isPass(),taskHandlerSave,id);
			json.put("result",1);
		} catch (Exception e) {
			e.printStackTrace();
			json.put("result",0);
			if(e.getCause()!=null){
				json.put("msg", "审核失败:"+e.getCause().getMessage());
			}else{
				json.put("msg", "审核失败:"+e.getMessage());
			}
		}
		responseJSON(json);
	}
	public void applyDetail(){
		officeGoOut=officeGoOutService.getOfficeGoOutById(id);
		if(officeGoOut==null){
			officeGoOut=new OfficeGoOut();
		}
		
		String photoUrl = userSetService.getUserPhotoUrl(officeGoOut.getApplyUserId());
		User user = userService.getUser(officeGoOut.getApplyUserId());
		String deptName = "";
		if(user != null){
			Dept dept = deptService.getDept(user.getDeptid());
			if(dept!=null){
				deptName = dept.getDeptname();
			}
		}
		
		
		JSONObject json = new JSONObject();
		json.put("applyUserName", officeGoOut.getUserName());
		json.put("deptName", deptName);
		json.put("photoUrl", photoUrl);
		json.put("beginTime", DateUtils.date2String(officeGoOut.getBeginTime(), "yyyy-MM-dd HH:mm"));
		json.put("endTime", DateUtils.date2String(officeGoOut.getEndTime(), "yyyy-MM-dd HH:mm"));
		json.put("hours", officeGoOut.getHours());
		json.put("typeStr", getTypeStr(officeGoOut.getOutType()));
		json.put("tripReason", officeGoOut.getTripReason());
		json.put("attachmentArray",RemoteOfficeUtils.createAttachmentArray(officeGoOut.getAttachments()));
		json.put("hisTaskCommentArray",RemoteOfficeUtils.createHisTaskCommentArray(officeGoOut.getHisTaskList()));
		json.put("applyStatus", officeGoOut.getState());
		
		jsonMap.put(getDetailObjectName(), json);
		jsonMap.put("result", 1);
		responseJSON(jsonMap);
	}
	private String getTypeStr(String type) {
		String typeStr ="";
		if("1".equals(type)){
			typeStr = "因公外出";
		}else if("2".equals(type)){
			typeStr = "因私外出";
		}
		return typeStr;
	}
	
	@Override
	protected String getListObjectName() {
		return "result_array";
	}

	@Override
	protected String getDetailObjectName() {
		return "result_object";
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

	public void setFlowManageService(FlowManageService flowManageService) {
		this.flowManageService = flowManageService;
	}

	public void setOfficeGoOutService(OfficeGoOutService officeGoOutService) {
		this.officeGoOutService = officeGoOutService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public int getApplyStatus() {
		return applyStatus;
	}

	public void setApplyStatus(int applyStatus) {
		this.applyStatus = applyStatus;
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

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public List<Flow> getFlowList() {
		return flowList;
	}

	public void setFlowList(List<Flow> flowList) {
		this.flowList = flowList;
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

	public int getAuditStatus() {
		return auditStatus;
	}

	public void setAuditStatus(int auditStatus) {
		this.auditStatus = auditStatus;
	}

	public String getFromTab() {
		return fromTab;
	}

	public void setFromTab(String fromTab) {
		this.fromTab = fromTab;
	}
	public String getUnitId() {
		return unitId;
	}
	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}

	public void setTaskHandlerService(TaskHandlerService taskHandlerService) {
		this.taskHandlerService = taskHandlerService;
	}
	
	public void setUserSetService(UserSetService userSetService) {
		this.userSetService = userSetService;
	}
	public void setDeptService(DeptService deptService) {
		this.deptService = deptService;
	}
}
