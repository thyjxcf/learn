package net.zdsoft.office.remote;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.zdsoft.eis.base.common.entity.Dept;
import net.zdsoft.eis.base.common.entity.Mcodedetail;
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
import net.zdsoft.office.dailyoffice.entity.OfficeBusinessTrip;
import net.zdsoft.office.dailyoffice.service.OfficeBusinessTripService;
import net.zdsoft.office.teacherLeave.entity.OfficeTeacherLeave;
import net.zdsoft.office.util.Constants;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

/**
* @Package net.zdsoft.office.remote 
* @author songxq  
* @date 2016-5-24 下午4:13:57 
* @version V1.0
 */
@SuppressWarnings("serial")
public class RemoteBusinessTripAction extends OfficeJsonBaseAction{
	
	private List<OfficeBusinessTrip> officeBusinessTripList;
	private OfficeBusinessTrip officeBusinessTrip;
	private FlowManageService flowManageService;
	private OfficeBusinessTripService officeBusinessTripService; 
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
	
	public String execute() throws Exception{
		User user = userService.getUser(userId);
		userName = user.getRealname();
		return SUCCESS;
	}
	public void applyList(){
		Pagination page = getPage();
//		page.setPageSize(15);
		officeBusinessTripList = officeBusinessTripService.getOfficeBusinessTripByUnitIdUserIdPage(unitId, userId,String.valueOf(applyStatus), page);
		JSONArray array = new JSONArray();
		for(OfficeBusinessTrip officeBusinessTrip : officeBusinessTripList){
			JSONObject json = new JSONObject();
			json.put("id", officeBusinessTrip.getId());
			json.put("beginTime", DateUtils.date2String(officeBusinessTrip.getBeginTime(), "MM-dd"));
			String content = officeBusinessTrip.getTripReason();
			if(content.length() > 25){
				content = content.substring(0, 25)+"...";
			}
			json.put("content", content);
			json.put("days", officeBusinessTrip.getDays());
			json.put("applyStatus", officeBusinessTrip.getState());
			array.add(json);
		}
		jsonMap.put(getListObjectName(), array);
		jsonMap.put("page", page);
		jsonMap.put("result", 1);
		responseJSON(jsonMap);
	}
	public void applyBusinessTrip(){

		if(StringUtils.isNotEmpty(id)){
			officeBusinessTrip = officeBusinessTripService.getOfficeBusinessTripById(id);
			if(officeBusinessTrip==null){
				officeBusinessTrip = new OfficeBusinessTrip();
				User user = userService.getUser(userId);
				officeBusinessTrip.setApplyUserId(user.getId());
				officeBusinessTrip.setUserName(user.getRealname());
				officeBusinessTrip.setUnitId(unitId);
			}
		}else{
			officeBusinessTrip=new OfficeBusinessTrip();
			User user = userService.getUser(userId);
			officeBusinessTrip.setApplyUserId(user.getId());
			officeBusinessTrip.setUserName(user.getRealname());
			officeBusinessTrip.setUnitId(unitId);
		}
		flowList = flowManageService.getFinishFlowList(unitId, FlowConstant.FLOW_OWNER_UNIT, FlowConstant.OFFICE_BUSINESS_TRIP,FlowConstant.OFFICE_SUBSYSTEM,FlowConstant.OFFICEDOC_FLOW_EASY_LEVEL_1);
		JSONObject json = new JSONObject();
		json.put("id", officeBusinessTrip.getId());
		json.put("applyUserId", officeBusinessTrip.getApplyUserId());
		json.put("unitId", officeBusinessTrip.getUnitId());
		json.put("applyUserName", officeBusinessTrip.getUserName());
		json.put("beginTime", DateUtils.date2String(officeBusinessTrip.getBeginTime(), "yyyy-MM-dd"));
		json.put("endTime", DateUtils.date2String(officeBusinessTrip.getEndTime(), "yyyy-MM-dd"));
		json.put("days", officeBusinessTrip.getDays());
		json.put("state", officeBusinessTrip.getState());
		json.put("createTime", DateUtils.date2String(officeBusinessTrip.getCreateTime(), "yyyy-MM-dd HH:mm"));
		json.put("place", officeBusinessTrip.getPlace());
		json.put("tripReason", officeBusinessTrip.getTripReason());
		json.put("attachmentArray",RemoteOfficeUtils.createAttachmentArray(officeBusinessTrip.getAttachments()));
		json.put("flowId", officeBusinessTrip.getFlowId());
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
		json.put("flowArray",flowArray);
		jsonMap.put(getDetailObjectName(), json);
		jsonMap.put("result",1);
		responseJSON(jsonMap);
	}
	public void saveBusinessTrip(){
		UploadFile file = null;
		JSONObject json = new JSONObject();
		try {
			boolean flag = officeBusinessTripService.isExistConflict(officeBusinessTrip.getId(),officeBusinessTrip.getApplyUserId(),officeBusinessTrip.getBeginTime(), officeBusinessTrip.getEndTime());
			if(flag) {
				jsonMap.put("result", 0);
				jsonMap.put("msg", "保存失败，该申请人在该时间段内已经存在申请！");
				responseJSON(jsonMap);
				return;
			}
			file = StorageFileUtils.handleFile(new String[] {}, 5*1024);
			officeBusinessTrip.setState(String.valueOf(Constants.LEAVE_APPLY_SAVE));
			officeBusinessTrip.setIsDeleted(false);
			if(StringUtils.isNotEmpty(officeBusinessTrip.getId())){
				officeBusinessTripService.update(officeBusinessTrip,file);
			}else{
				officeBusinessTripService.add(officeBusinessTrip,file);
			}
			jsonMap.put("result",1);
		}catch (Exception e) {
			e.printStackTrace();
			jsonMap.put("result",0);
		}
		responseJSON(jsonMap);
	}
	public void submitBusinessTrip(){
		UploadFile file = null;
		JSONObject json = new JSONObject();
		try {
			file = StorageFileUtils.handleFile(new String[] {}, 5*1024);
			boolean flag = officeBusinessTripService.isExistConflict(officeBusinessTrip.getId(),officeBusinessTrip.getApplyUserId(),officeBusinessTrip.getBeginTime(), officeBusinessTrip.getEndTime());
			if(flag) {
				jsonMap.put("result", 0);
				jsonMap.put("msg", "保存失败，该申请人在该时间段内已经存在申请！");
				responseJSON(jsonMap);
				return;
			}
			officeBusinessTrip.setCreateTime(new Date());
			officeBusinessTrip.setIsDeleted(false);
			officeBusinessTrip.setState(String.valueOf(Constants.LEAVE_APPLY_FLOWING));
			officeBusinessTripService.startFlow(officeBusinessTrip,officeBusinessTrip.getApplyUserId(),file);
			jsonMap.put("result",1);
		}catch (Exception e) {
			e.printStackTrace();
			e.printStackTrace();
			if(e.getCause()!=null){
				if(e.getCause().getMessage()!=null){
					jsonMap.put("msg", "提交失败:"+e.getCause().getMessage());
				}else{
					jsonMap.put("msg", "未设置流程审核人员");
				}
			}else{
				jsonMap.put("msg", "提交失败:"+e.getMessage());
			}
		}
		responseJSON(jsonMap);
	}
	public void deleteBusinessTrip(){
		JSONObject json = new JSONObject();
		if(StringUtils.isNotEmpty(id)){
			try {
				officeBusinessTripService.delete(new String[]{id});
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
			officeBusinessTripList=officeBusinessTripService.toDoAudit(userId,page);
		}else{
			officeBusinessTripList=officeBusinessTripService.HaveDoAudit(userId,page);
		}
		JSONArray array = new JSONArray();
		for(OfficeBusinessTrip officeBusinessTrip : officeBusinessTripList){
			JSONObject json = new JSONObject();
			json.put("id", officeBusinessTrip.getId());
			json.put("beginTime", DateUtils.date2String(officeBusinessTrip.getBeginTime(), "MM-dd"));
			json.put("days",officeBusinessTrip.getDays());
			json.put("applyUserName", officeBusinessTrip.getUserName());
			String content = officeBusinessTrip.getTripReason();
			if(content.length() > 25){
				content = content.substring(0, 25)+"...";
			}
			json.put("content", content);
			json.put("applyStatus", officeBusinessTrip.getState());
			json.put("taskId", officeBusinessTrip.getTaskId());
			array.add(json);
		}
		jsonMap.put(getListObjectName(), array);
		jsonMap.put("page", page);
		jsonMap.put("result", 1);
		responseJSON(jsonMap);
	}
	public void aduitBusinessTrip(){
		officeBusinessTrip = officeBusinessTripService.getOfficeBusinessTripById(id);
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
		officeBusinessTrip.setTaskName(taskHandlerSave.getCurrentTask().getTaskName());
		currentStepId = taskHandlerSave.getCurrentTask().getTaskDefinitionKey();
		flowId = officeBusinessTrip.getFlowId();
		
		//获取用户图像
		String photoUrl = userSetService.getUserPhotoUrl(officeBusinessTrip.getApplyUserId());
		String deptName = "";
		if(user != null){
			Dept dept = deptService.getDept(user.getDeptid());
			if(dept!=null){
				deptName = dept.getDeptname();
			}
		}
		
		JSONObject json = new JSONObject();
		json.put("id", officeBusinessTrip.getId());
		json.put("photoUrl", photoUrl);
		json.put("applyUserName", officeBusinessTrip.getUserName());
		json.put("deptName", deptName);
		json.put("place", officeBusinessTrip.getPlace());
		json.put("beginTime", DateUtils.date2String(officeBusinessTrip.getBeginTime(), "yyyy-MM-dd"));
		json.put("endTime", DateUtils.date2String(officeBusinessTrip.getEndTime(), "yyyy-MM-dd"));
		json.put("days", officeBusinessTrip.getDays());
		json.put("tripReason", officeBusinessTrip.getTripReason());
		json.put("attachmentArray",RemoteOfficeUtils.createAttachmentArray(officeBusinessTrip.getAttachments()));
		json.put("taskHandlerSaveJson", taskHandlerSaveJson);
		json.put("taskName", taskHandlerSave.getCurrentTask().getTaskName());
		json.put("userName", userName);
		json.put("currentStepId", currentStepId);
		json.put("flowId", flowId);
		json.put("hisTaskCommentArray",RemoteOfficeUtils.createHisTaskCommentArray(officeBusinessTrip.getHisTaskList()));
		jsonMap.put(getDetailObjectName(), json);
		jsonMap.put("result", 1);
		responseJSON(jsonMap);
	}
	public void auditPassBusinessTrip(){
		JSONObject json = new JSONObject();
		try {
			JSONObject object = new JSONObject().fromObject(taskHandlerSaveJson);
			TaskHandlerSave taskHandlerSave =(TaskHandlerSave) JSONObject.toBean(object,
				 TaskHandlerSave.class);
			taskHandlerSave.getComment().setOperateTime(new Date());
			officeBusinessTripService.passFlow(isPass(),taskHandlerSave,id);
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
		officeBusinessTrip=officeBusinessTripService.getOfficeBusinessTripById(id);
		if(officeBusinessTrip==null){
			officeBusinessTrip=new OfficeBusinessTrip();
		}
		//获取用户图像
		String photoUrl = userSetService.getUserPhotoUrl(officeBusinessTrip.getApplyUserId());
		User user = userService.getUser(officeBusinessTrip.getApplyUserId());
		String deptName = "";
		if(user != null){
			Dept dept = deptService.getDept(user.getDeptid());
			if(dept!=null){
				deptName = dept.getDeptname();
			}
		}
		
		JSONObject json = new JSONObject();
		json.put("applyUserName", officeBusinessTrip.getUserName());
		json.put("deptName", deptName);
		json.put("photoUrl", photoUrl);
		json.put("beginTime", DateUtils.date2String(officeBusinessTrip.getBeginTime(), "yyyy-MM-dd"));
		json.put("endTime", DateUtils.date2String(officeBusinessTrip.getEndTime(), "yyyy-MM-dd"));
		json.put("days", officeBusinessTrip.getDays());
		json.put("applyStatus", officeBusinessTrip.getState());
		json.put("place", officeBusinessTrip.getPlace());
		json.put("tripReason", officeBusinessTrip.getTripReason());
		json.put("attachmentArray",RemoteOfficeUtils.createAttachmentArray(officeBusinessTrip.getAttachments()));
		json.put("hisTaskCommentArray",RemoteOfficeUtils.createHisTaskCommentArray(officeBusinessTrip.getHisTaskList()));
		
		
		
		jsonMap.put(getDetailObjectName(), json);
		jsonMap.put("result", 1);
		responseJSON(jsonMap);
	}
	@Override
	protected String getListObjectName() {
		return "result_array";
	}

	@Override
	protected String getDetailObjectName() {
		return "result_object";
	}

	public List<OfficeBusinessTrip> getOfficeBusinessTripList() {
		return officeBusinessTripList;
	}

	public void setOfficeBusinessTripList(List<OfficeBusinessTrip> officeBusinessTripList) {
		this.officeBusinessTripList = officeBusinessTripList;
	}

	public OfficeBusinessTrip getOfficeBusinessTrip() {
		return officeBusinessTrip;
	}

	public void setOfficeBusinessTrip(OfficeBusinessTrip officeBusinessTrip) {
		this.officeBusinessTrip = officeBusinessTrip;
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

	public String getUnitId() {
		return unitId;
	}
	public void setUnitId(String unitId) {
		this.unitId = unitId;
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

	public TaskHandlerService getTaskHandlerService() {
		return taskHandlerService;
	}

	public void setTaskHandlerService(TaskHandlerService taskHandlerService) {
		this.taskHandlerService = taskHandlerService;
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

	public void setFlowManageService(FlowManageService flowManageService) {
		this.flowManageService = flowManageService;
	}

	public void setOfficeBusinessTripService(
			OfficeBusinessTripService officeBusinessTripService) {
		this.officeBusinessTripService = officeBusinessTripService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	
	public void setUserSetService(UserSetService userSetService) {
		this.userSetService = userSetService;
	}
	
	public void setDeptService(DeptService deptService) {
		this.deptService = deptService;
	}
}
