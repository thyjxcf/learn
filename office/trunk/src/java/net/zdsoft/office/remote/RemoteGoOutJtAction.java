package net.zdsoft.office.remote;

import java.util.Date;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.zdsoft.eis.base.common.entity.Dept;
import net.zdsoft.eis.base.common.entity.User;
import net.zdsoft.eis.base.common.service.DeptService;
import net.zdsoft.eis.base.common.service.UserService;
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
import net.zdsoft.keelcnet.action.UploadFile;
import net.zdsoft.leadin.util.UUIDGenerator;
import net.zdsoft.office.jtgoout.entity.GooutStudentEx;
import net.zdsoft.office.jtgoout.entity.GooutTeacherEx;
import net.zdsoft.office.jtgoout.entity.OfficeJtGoout;
import net.zdsoft.office.jtgoout.service.GooutStudentExService;
import net.zdsoft.office.jtgoout.service.GooutTeacherExService;
import net.zdsoft.office.jtgoout.service.OfficeJtGooutService;
import net.zdsoft.office.util.Constants;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;


public class RemoteGoOutJtAction extends OfficeJsonBaseAction{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private OfficeJtGooutService officeJtGooutService;
	private UserService userService;
	private FlowManageService flowManageService;
	private GooutStudentExService gooutStudentExService;
	private GooutTeacherExService gooutTeacherExService;
	private DeptService deptService;
	private TaskHandlerService taskHandlerService;
	
	private OfficeJtGoout officeJtGoout = new OfficeJtGoout();
	private GooutStudentEx gooutStudentEx = new GooutStudentEx();
	private GooutTeacherEx gooutTeacherEx = new GooutTeacherEx();
	private String userId;
	private String unitId;
	private String id;
	private List<Flow> flowList;
	private String taskId;
	private String removeAttachment;
	
	private String userName;
	private String taskHandlerSaveJson;
	private String currentStepId;
	private String flowId;
	private boolean isPass;
	private String jsonResult;
	private String textComment;
	
	public void applyGoOutJt(){
		System.out.println(id);
		if(StringUtils.isNotEmpty(id)){
			officeJtGoout = officeJtGooutService.getOfficeJtGooutById(id);
			if(officeJtGoout==null){
				officeJtGoout = new OfficeJtGoout();
				User user = userService.getUser(userId);
				officeJtGoout.setApplyUserId(user.getId());
				//officeJtGoout.setUserName(user.getRealname());
				officeJtGoout.setUnitId(unitId);
				
				if(StringUtils.equals("1", officeJtGoout.getType())){
					gooutStudentEx=gooutStudentExService.getGooutStudentExByJtGoOutId(officeJtGoout.getId());
				}
				if(StringUtils.equals("2", officeJtGoout.getType())){
					gooutTeacherEx=gooutTeacherExService.getGooutTeacherExByJtGooutId(officeJtGoout.getId());
				}
				if(gooutStudentEx == null){
					gooutStudentEx = new GooutStudentEx();
				}
				if(gooutTeacherEx == null){
					gooutTeacherEx = new GooutTeacherEx();
				}
			}
		}else{
			officeJtGoout=new OfficeJtGoout();
			User user = userService.getUser(userId);
			officeJtGoout.setApplyUserId(user.getId());
			officeJtGoout.setId(UUIDGenerator.getUUID());
			//officeJtGoout.setUserName(user.getRealname());
			officeJtGoout.setUnitId(unitId);
			
			officeJtGoout=officeJtGooutService.getOfficeJtGooutByUnitIdAndUserId(officeJtGoout, unitId, userId);
			flowList=officeJtGoout.getFlows();
			
		}
		if(CollectionUtils.isNotEmpty(officeJtGoout.getFlows())){
			flowList=officeJtGoout.getFlows();
		}else{
			flowList=flowManageService.getFinishFlowList(getUnitId(), FlowConstant.FLOW_OWNER_UNIT, FlowConstant.OFFICE_JT_GOOUT, FlowConstant.OFFICE_SUBSYSTEM, FlowConstant.OFFICEDOC_FLOW_EASY_LEVEL_1);
			User user = userService.getUser(userId);
			if(user != null && StringUtils.isNotBlank(user.getDeptid())){
				List<Flow> flowList2  = flowManageService.getFinishFlowList(user.getDeptid(), FlowConstant.FLOW_OWNER_STRING, 
						FlowConstant.OFFICE_JT_GOOUT,FlowConstant.OFFICE_SUBSYSTEM,FlowConstant.OFFICEDOC_FLOW_EASY_LEVEL_1);
				if(CollectionUtils.isNotEmpty(flowList2)){
					flowList.addAll(flowList2);
				}
			}
		}
		JSONObject json = new JSONObject();
		json.put("id", officeJtGoout.getId());
		json.put("unitId", officeJtGoout.getUnitId());
		json.put("createTime", new Date());
		json.put("applyUserId", officeJtGoout.getApplyUserId());
		//json.put("applyUserName", officeJtGoout.getApplyUserName());
		json.put("beginTime", DateUtils.date2String(officeJtGoout.getStartTime(), "yyyy-MM-dd HH:mm"));
		json.put("endTime", DateUtils.date2String(officeJtGoout.getEndTime(), "yyyy-MM-dd HH:mm"));
		json.put("goOutJtType", officeJtGoout.getType());
		json.put("organization", gooutStudentEx.getOrganization());
		json.put("activityNumber", gooutStudentEx.getActivityNumber());
		json.put("place", gooutStudentEx.getPlace());
		json.put("scontent", gooutStudentEx.getContent());
		json.put("vehicle", gooutStudentEx.getVehicle());
		json.put("isDrivinglicence", gooutStudentEx.getIsDrivinglicence());
		json.put("isOrganization", gooutStudentEx.getIsOrganization());
		json.put("traveUnit", gooutStudentEx.getTraveUnit());
		json.put("traveLinkPerson", gooutStudentEx.getTraveLinkPerson());
		json.put("traveLinkPhone", gooutStudentEx.getTraveLinkPhone());
		json.put("isInsurance", gooutStudentEx.getIsInsurance());
		json.put("activityLeaderId", gooutStudentEx.getActivityLeaderId());
		json.put("activityLeaderName", gooutStudentEx.getActivityLeaderName());
		json.put("activityLeaderPhone", gooutStudentEx.getActivityLeaderPhone());
		json.put("leadGroupId", gooutStudentEx.getLeadGroupId());
		json.put("leadGroupName", gooutStudentEx.getLeadGroupName());
		json.put("leadGroupPhone", gooutStudentEx.getLeadGroupPhone());
		json.put("otherTeacherId", gooutStudentEx.getOtherTeacherId());
		json.put("otherTeacherNames", gooutStudentEx.getOtherTeacherNames());
		json.put("activityIdeal", gooutStudentEx.getActivityIdeal());
		json.put("saftIdeal", gooutStudentEx.getSaftIdeal());
		
		json.put("tcontent", gooutTeacherEx.getContent());
		json.put("partakePersonId", gooutTeacherEx.getPartakePersonId());
		json.put("partakePersonNames", gooutTeacherEx.getPartakePersonNames());
		
		json.put("attachmentArray",RemoteOfficeUtils.createAttachmentArray(officeJtGoout.getAttachments()));
		json.put("flowId", officeJtGoout.getFlowId());
		
		JSONArray flowArray = new JSONArray();
		for (Flow flow : flowList) {
			JSONObject flowObject=new JSONObject();
			flowObject.put("flowId", flow.getFlowId());
			flowObject.put("flowName", flow.getFlowName());
			flowObject.put("isDefaultFlow", flow.isDefaultFlow());
			flowArray.add(flowObject);
		}
		JSONArray typeArray = new JSONArray();
		for(int i=1;i<3;i++){
			JSONObject typeObject=new JSONObject();
			typeObject.put("type", i);
			if(i==1){
				typeObject.put("typeName", "学生集体活动");
			}else{
				typeObject.put("typeName", "教师集体培训");
			}
			typeArray.add(typeObject);
		}
		json.put("typeArray",typeArray);
		json.put("flowArray",flowArray);
		jsonMap.put(getDetailObjectName(), json);
		jsonMap.put("result",1);
		responseJSON(jsonMap);
	}
	public void submitGoOutJt(){
		List<UploadFile> files = null;
		JSONObject json = new JSONObject();
		try {
			files = StorageFileUtils.handleFiles(new String[] {}, 5*1024,50*1024);
			officeJtGoout.setApplyUserId(userId);
			officeJtGoout.setCreateTime(new Date());
			officeJtGoout.setIsDeleted(false);
			officeJtGoout.setState(String.valueOf(Constants.APPLY_STATE_NEED_AUDIT));
			
			String[] removeAttachmentArray = null;
			if(StringUtils.isNotBlank(removeAttachment)){
				removeAttachmentArray = removeAttachment.split(",");
			}
			officeJtGooutService.startFlow(officeJtGoout, gooutStudentEx, gooutTeacherEx, userId,files,removeAttachmentArray, getPage());
			json.put("id",officeJtGoout.getId());
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

	
	public void auditGoOutJt(){
		officeJtGoout=officeJtGooutService.getOfficeJtGooutById(id);
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
		officeJtGoout.setTaskName(taskHandlerSave.getCurrentTask().getTaskName());
		currentStepId = taskHandlerSave.getCurrentTask().getTaskDefinitionKey();
		flowId = officeJtGoout.getFlowId();
		
		String photoUrl = userSetService.getUserPhotoUrl(officeJtGoout.getApplyUserId());
		String deptName = "";
		if(user != null){
			Dept dept = deptService.getDept(user.getDeptid());
			if(dept!=null){
				deptName = dept.getDeptname();
			}
		}
		if(officeJtGoout!=null&&StringUtils.equals("1", officeJtGoout.getType())){
			gooutStudentEx=gooutStudentExService.getGooutStudentExByJtGoOutId(officeJtGoout.getId());
		}
		if(officeJtGoout!=null&&StringUtils.equals("2", officeJtGoout.getType())){
			gooutTeacherEx=gooutTeacherExService.getGooutTeacherExByJtGooutId(officeJtGoout.getId());
		}
		if(gooutStudentEx == null){
			gooutStudentEx = new GooutStudentEx();
		}
		if(gooutTeacherEx == null){
			gooutTeacherEx = new GooutTeacherEx();
		}
		
		JSONObject json = new JSONObject();
		json.put("applyUserName", user.getRealname());
		json.put("deptName", deptName);
		json.put("photoUrl", photoUrl);
		json.put("beginTime", DateUtils.date2String(officeJtGoout.getStartTime(), "yyyy-MM-dd HH:mm"));
		json.put("endTime", DateUtils.date2String(officeJtGoout.getEndTime(), "yyyy-MM-dd HH:mm"));
		json.put("goOutJtType", officeJtGoout.getType());
		json.put("organize", gooutStudentEx.getOrganize());
		json.put("organization", gooutStudentEx.getOrganization());
		json.put("activityNumber", gooutStudentEx.getActivityNumber());
		json.put("place", gooutStudentEx.getPlace());
		json.put("scontent", gooutStudentEx.getContent());
		json.put("vehicle", gooutStudentEx.getVehicle());
		json.put("isDrivinglicenceStr", returnYesOrNo((gooutStudentEx.getIsDrivinglicence()==null||!gooutStudentEx.getIsDrivinglicence())?false:true));
		json.put("isOrganizationStr", returnYesOrNo((gooutStudentEx.getIsOrganization()==null||!gooutStudentEx.getIsOrganization())?false:true));
		json.put("traveUnit", gooutStudentEx.getTraveUnit());
		json.put("traveLinkPerson", gooutStudentEx.getTraveLinkPerson());
		json.put("traveLinkPhone", gooutStudentEx.getTraveLinkPhone());
		json.put("isInsuranceStr", returnYesOrNo((gooutStudentEx.getIsInsurance()==null||!gooutStudentEx.getIsInsurance())?false:true));
		json.put("activityLeaderName", gooutStudentEx.getActivityLeaderName());
		json.put("activityLeaderPhone", gooutStudentEx.getActivityLeaderPhone());
		json.put("leadGroupName", gooutStudentEx.getLeadGroupName());
		json.put("leadGroupPhone", gooutStudentEx.getLeadGroupPhone());
		json.put("otherTeacherNames", gooutStudentEx.getOtherTeacherNames());
		json.put("activityIdealStr", returnYesOrNo((gooutStudentEx.getActivityIdeal()==null||!gooutStudentEx.getActivityIdeal())?false:true));
		json.put("saftIdealStr", returnYesOrNo((gooutStudentEx.getSaftIdeal()==null||!gooutStudentEx.getSaftIdeal())?false:true));
		
		json.put("tcontent", gooutTeacherEx.getContent());
		json.put("partakePersonNames", gooutTeacherEx.getPartakePersonNames());
		
		json.put("attachmentArray",RemoteOfficeUtils.createAttachmentArray(officeJtGoout.getAttachments()));
		json.put("hisTaskCommentArray",RemoteOfficeUtils.createHisTaskCommentArray(officeJtGoout.getHisTaskList(), officeJtGoout.getUnitId(), officeJtGoout.getFlowId()));
		json.put("applyStatus", officeJtGoout.getState());
		
		json.put("taskHandlerSaveJson", taskHandlerSaveJson);
		json.put("taskName", taskHandlerSave.getCurrentTask().getTaskName());
		json.put("userName", userName);
		json.put("currentStepId", currentStepId);
		json.put("flowId", flowId);
		json.put("id", officeJtGoout.getId());
		
		jsonMap.put(getDetailObjectName(), json);
		jsonMap.put("result", 1);
		responseJSON(jsonMap);
	}
	public void deDeleteApply(){
		JSONObject json=new JSONObject();
		if(StringUtils.isNotBlank(id)){
			try {
				officeJtGooutService.deleteRevoke(id);
				json.put("result",1);
			} catch (Exception e) {
				json.put("result",0);
			}
		}else{
			json.put("result",0);
		}
		responseJSON(json);
	}
	
	public void auditPassGoOutJt(){
		JSONObject json = new JSONObject();
		try {
			JSONObject object = new JSONObject().fromObject(taskHandlerSaveJson);
			TaskHandlerSave taskHandlerSave =(TaskHandlerSave) JSONObject.toBean(object,
				 TaskHandlerSave.class);
			taskHandlerSave.getComment().setOperateTime(new Date());
			officeJtGooutService.passFlow(isPass(),taskHandlerSave,id,currentStepId);
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
	
	public String returnYesOrNo(boolean flag){
		if(flag){
			return "是";
		}else return "否";
	}
	public void applyDetailJt(){
		officeJtGoout=officeJtGooutService.getOfficeJtGooutById(id);
		if(officeJtGoout==null){
			officeJtGoout=new OfficeJtGoout();
		}
		
		String photoUrl = userSetService.getUserPhotoUrl(officeJtGoout.getApplyUserId());
		User user = userService.getUser(officeJtGoout.getApplyUserId());
		String deptName = "";
		if(user != null){
			Dept dept = deptService.getDept(user.getDeptid());
			if(dept!=null){
				deptName = dept.getDeptname();
			}
		}
		
		if(officeJtGoout!=null&&StringUtils.equals("1", officeJtGoout.getType())){
			gooutStudentEx=gooutStudentExService.getGooutStudentExByJtGoOutId(officeJtGoout.getId());
		}
		if(officeJtGoout!=null&&StringUtils.equals("2", officeJtGoout.getType())){
			gooutTeacherEx=gooutTeacherExService.getGooutTeacherExByJtGooutId(officeJtGoout.getId());
		}
		if(gooutStudentEx == null){
			gooutStudentEx = new GooutStudentEx();
		}
		if(gooutTeacherEx == null){
			gooutTeacherEx = new GooutTeacherEx();
		}
		JSONObject json = new JSONObject();
		json.put("applyUserName", user.getRealname());
		json.put("deptName", deptName);
		json.put("photoUrl", photoUrl);
		json.put("beginTime", DateUtils.date2String(officeJtGoout.getStartTime(), "yyyy-MM-dd HH:mm"));
		json.put("endTime", DateUtils.date2String(officeJtGoout.getEndTime(), "yyyy-MM-dd HH:mm"));
		json.put("goOutJtType", officeJtGoout.getType());
		json.put("organize", gooutStudentEx.getOrganize());
		json.put("organization", gooutStudentEx.getOrganization());
		json.put("activityNumber", gooutStudentEx.getActivityNumber());
		json.put("place", gooutStudentEx.getPlace());
		json.put("scontent", gooutStudentEx.getContent());
		json.put("vehicle", gooutStudentEx.getVehicle());
		json.put("isDrivinglicenceStr", returnYesOrNo((gooutStudentEx.getIsDrivinglicence()==null || !gooutStudentEx.getIsDrivinglicence())?false:true));
		json.put("isOrganizationStr", returnYesOrNo((gooutStudentEx.getIsOrganization()==null||!gooutStudentEx.getIsOrganization())?false:true));
		json.put("traveUnit", gooutStudentEx.getTraveUnit());
		json.put("traveLinkPerson", gooutStudentEx.getTraveLinkPerson());
		json.put("traveLinkPhone", gooutStudentEx.getTraveLinkPhone());
		json.put("isInsuranceStr", returnYesOrNo((gooutStudentEx.getIsInsurance()==null||!gooutStudentEx.getIsInsurance())?false:true));
		json.put("activityLeaderName", gooutStudentEx.getActivityLeaderName());
		json.put("activityLeaderPhone", gooutStudentEx.getActivityLeaderPhone());
		json.put("leadGroupName", gooutStudentEx.getLeadGroupName());
		json.put("leadGroupPhone", gooutStudentEx.getLeadGroupPhone());
		json.put("otherTeacherNames", gooutStudentEx.getOtherTeacherNames());
		json.put("activityIdealStr", returnYesOrNo((gooutStudentEx.getActivityIdeal()==null || !gooutStudentEx.getActivityIdeal())?false:true));
		json.put("saftIdealStr", returnYesOrNo((gooutStudentEx.getSaftIdeal()==null||!gooutStudentEx.getSaftIdeal())?false:true));
		
		json.put("tcontent", gooutTeacherEx.getContent());
		json.put("partakePersonNames", gooutTeacherEx.getPartakePersonNames());
		
		json.put("attachmentArray",RemoteOfficeUtils.createAttachmentArray(officeJtGoout.getAttachments()));
		json.put("hisTaskCommentArray",RemoteOfficeUtils.createHisTaskCommentArray(officeJtGoout.getHisTaskList(), officeJtGoout.getUnitId(), officeJtGoout.getFlowId()));
		json.put("applyStatus", officeJtGoout.getState());
		
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


	public OfficeJtGoout getOfficeJtGoout() {
		return officeJtGoout;
	}

	public void setOfficeJtGoout(OfficeJtGoout officeJtGoout) {
		this.officeJtGoout = officeJtGoout;
	}

	public GooutStudentEx getGooutStudentEx() {
		return gooutStudentEx;
	}

	public void setGooutStudentEx(GooutStudentEx gooutStudentEx) {
		this.gooutStudentEx = gooutStudentEx;
	}

	public GooutTeacherEx getGooutTeacherEx() {
		return gooutTeacherEx;
	}

	public void setGooutTeacherEx(GooutTeacherEx gooutTeacherEx) {
		this.gooutTeacherEx = gooutTeacherEx;
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

	public void setOfficeJtGooutService(OfficeJtGooutService officeJtGooutService) {
		this.officeJtGooutService = officeJtGooutService;
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
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	public void setFlowManageService(FlowManageService flowManageService) {
		this.flowManageService = flowManageService;
	}
	public void setGooutStudentExService(GooutStudentExService gooutStudentExService) {
		this.gooutStudentExService = gooutStudentExService;
	}
	public void setGooutTeacherExService(GooutTeacherExService gooutTeacherExService) {
		this.gooutTeacherExService = gooutTeacherExService;
	}
	public void setDeptService(DeptService deptService) {
		this.deptService = deptService;
	}
	public void setTaskHandlerService(TaskHandlerService taskHandlerService) {
		this.taskHandlerService = taskHandlerService;
	}
	public String getTaskId() {
		return taskId;
	}
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
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
	public boolean isPass() {
		return isPass;
	}
	public void setPass(boolean isPass) {
		this.isPass = isPass;
	}
	public String getJsonResult() {
		return jsonResult;
	}
	public void setJsonResult(String jsonResult) {
		this.jsonResult = jsonResult;
	}
	public String getTextComment() {
		return textComment;
	}
	public void setTextComment(String textComment) {
		this.textComment = textComment;
	}
	public String getRemoveAttachment() {
		return removeAttachment;
	}
	public void setRemoveAttachment(String removeAttachment) {
		this.removeAttachment = removeAttachment;
	}
	
}
