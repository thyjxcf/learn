package net.zdsoft.office.expenditure.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import net.sf.json.JSONObject;
import net.zdsoft.eis.base.common.entity.Dept;
import net.zdsoft.eis.base.common.entity.Unit;
import net.zdsoft.eis.base.common.entity.User;
import net.zdsoft.eis.base.common.service.DeptService;
import net.zdsoft.eis.base.common.service.UnitService;
import net.zdsoft.eis.base.common.service.UserService;
import net.zdsoft.eis.component.flowManage.constant.FlowConstant;
import net.zdsoft.eis.component.flowManage.entity.Flow;
import net.zdsoft.eis.component.flowManage.service.FlowManageService;
import net.zdsoft.eis.frame.action.PageSemesterAction;
import net.zdsoft.jbpm.activiti.engine.task.AgileIdentityLinkType;
import net.zdsoft.jbpm.core.entity.Comment;
import net.zdsoft.jbpm.core.entity.TaskHandlerSave;
import net.zdsoft.jbpm.core.entity.TaskHandlerShow;
import net.zdsoft.jbpm.core.service.TaskHandlerService;
import net.zdsoft.office.expenditure.constant.ExpenConstants;
import net.zdsoft.office.expenditure.entity.OfficeExpenditure;
import net.zdsoft.office.expenditure.entity.OfficeExpenditureBusTrip;
import net.zdsoft.office.expenditure.entity.OfficeExpenditureChgOpinion;
import net.zdsoft.office.expenditure.entity.OfficeExpenditureKjOpinion;
import net.zdsoft.office.expenditure.entity.OfficeExpenditureMetting;
import net.zdsoft.office.expenditure.entity.OfficeExpenditureOutlay;
import net.zdsoft.office.expenditure.entity.OfficeExpenditureReception;
import net.zdsoft.office.expenditure.service.OfficeExpenditureBusTripService;
import net.zdsoft.office.expenditure.service.OfficeExpenditureChgOpinionService;
import net.zdsoft.office.expenditure.service.OfficeExpenditureKjOpinionService;
import net.zdsoft.office.expenditure.service.OfficeExpenditureMettingService;
import net.zdsoft.office.expenditure.service.OfficeExpenditureOutlayService;
import net.zdsoft.office.expenditure.service.OfficeExpenditureReceptionService;
import net.zdsoft.office.expenditure.service.OfficeExpenditureService;
import net.zdsoft.office.officeFlow.service.OfficeFlowService;

/**
 * 
* @Package net.zdsoft.office.expenditure.action 
* @author songxq  
* @date 2017-8-21 下午3:01:38 
* @version V1.0
 */
@SuppressWarnings("serial")
public class OfficeExpenditureAction extends PageSemesterAction{
	
	private OfficeExpenditureService officeExpenditureService;
	private OfficeExpenditureMettingService officeExpenditureMettingService;
	private OfficeExpenditureReceptionService officeExpenditureReceptionService;
	private OfficeExpenditureOutlayService officeExpenditureOutlayService;
	private OfficeExpenditureBusTripService officeExpenditureBusTripService;
	private FlowManageService flowManageService;
	private TaskHandlerService taskHandlerService;
	private UserService userService;
	private OfficeExpenditureKjOpinionService officeExpenditureKjOpinionService;
	private OfficeExpenditureChgOpinionService officeExpenditureChgOpinionService;
	private OfficeFlowService officeFlowService;
	private UnitService unitService;
	private DeptService deptService;
	
	private OfficeExpenditure officeExpenditure=new OfficeExpenditure();
	private List<OfficeExpenditure> officeExpenditures=new ArrayList<OfficeExpenditure>();
	private OfficeExpenditureMetting officeExpenditureMetting=new OfficeExpenditureMetting();
	private List<OfficeExpenditureMetting> officeExpenditureMettings=new ArrayList<OfficeExpenditureMetting>();
	private OfficeExpenditureReception officeExpenditureReception=new OfficeExpenditureReception();
	private List<OfficeExpenditureReception> officeExpenditureReceptions=new ArrayList<OfficeExpenditureReception>();
	private OfficeExpenditureOutlay officeExpenditureOutlay=new OfficeExpenditureOutlay();
	private List<OfficeExpenditureOutlay> officeExpenditureOutlays=new ArrayList<OfficeExpenditureOutlay>();
	private OfficeExpenditureBusTrip officeExpenditureBusTrip=new OfficeExpenditureBusTrip();
	private List<OfficeExpenditureBusTrip> officeExpenditureBusTrips=new ArrayList<OfficeExpenditureBusTrip>();
	private OfficeExpenditureChgOpinion officeExpenditureChgOpinion;
	private OfficeExpenditureKjOpinion officeExpenditureKjOpinion;
	
	private List<Flow> flowList;
	private int auditStatus;
	private String type;
	
	private String taskId;
	private String userName;
	private String taskHandlerSaveJson;
	private String currentStepId;
	private String flowId;
	
	private String step;
	private boolean stepExist;
	private boolean kjExist;
	private boolean isPass;
	
	private Unit unit;
	private String fromTab;
	
	private boolean queryExpenditure;
	
	public String execute(){
		return SUCCESS;
	}
	public String expenditureList(){
		officeExpenditures=officeExpenditureService.getOfficeExpenditures(getUnitId(), getLoginUser().getUserId(), type, null, getPage());
		return SUCCESS;
	}
	public String expenditureEdit(){
		try {
			if(StringUtils.isNotBlank(officeExpenditure.getId())){
				officeExpenditure=officeExpenditureService.getOfficeExpenditureById(officeExpenditure.getId());
				if(officeExpenditure!=null&&StringUtils.equals(officeExpenditure.getType(), ExpenConstants.TYPE_1)){
					officeExpenditureMetting=officeExpenditureMettingService.getOfficeExpenditureMettingByPrimarId(officeExpenditure.getId());
				}else if(officeExpenditure!=null&&StringUtils.equals(officeExpenditure.getType(), ExpenConstants.TYPE_2)){
					officeExpenditureReception=officeExpenditureReceptionService.getOfficeExpenditureReceptionByExId(officeExpenditure.getId());
				}else if(officeExpenditure!=null&&StringUtils.equals(officeExpenditure.getType(), ExpenConstants.TYPE_3)){
					officeExpenditureOutlay=officeExpenditureOutlayService.getOfficeExpenditureOutlayByExId(officeExpenditure.getId());
				}else if(officeExpenditure!=null&&StringUtils.equals(officeExpenditure.getType(), ExpenConstants.TYPE_4)){
					officeExpenditureBusTrip=officeExpenditureBusTripService.getOfficeExpenditureBusTripByExId(officeExpenditure.getId());
				}
			}else{
				officeExpenditure.setType("1");
			}
			flowList=flowManageService.getFinishFlowList(getUnitId(), FlowConstant.FLOW_OWNER_UNIT, FlowConstant.OFFICE_EXPENDITURE,FlowConstant.OFFICE_SUBSYSTEM,FlowConstant.OFFICEDOC_FLOW_EASY_LEVEL_1);
			if(officeExpenditure==null){
				officeExpenditure=new OfficeExpenditure();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	public String expenditureDelete(){
		try {
			if(StringUtils.isNotBlank(officeExpenditure.getId())){
				officeExpenditureService.delete(new String[]{officeExpenditure.getId()});
				promptMessageDto.setOperateSuccess(true);
				promptMessageDto.setPromptMessage("删除成功");
			}
		} catch (Exception e) {
			e.printStackTrace();
			promptMessageDto.setOperateSuccess(false);
			promptMessageDto.setErrorMessage("删除失败");
		}
		return SUCCESS;
	}
	public String expenditureSave(){
		try {
			if(StringUtils.isNotBlank(officeExpenditure.getId())){
				officeExpenditureService.updateThing(officeExpenditure, officeExpenditureMetting, 
						officeExpenditureReception, officeExpenditureOutlay, officeExpenditureBusTrip);
			}else{
				officeExpenditure.setApplyDate(new Date());
				officeExpenditure.setApplyUserId(getLoginUser().getUserId());
				officeExpenditure.setUnitId(getUnitId());
				officeExpenditure.setIsdeleted(false);
				officeExpenditure.setCreationTime(new Date());
				officeExpenditure.setModifyTime(new Date());
				officeExpenditureService.saveThing(officeExpenditure, officeExpenditureMetting,
						officeExpenditureReception, officeExpenditureOutlay, officeExpenditureBusTrip);
			}
			promptMessageDto.setOperateSuccess(true);
			if(StringUtils.isNotBlank(officeExpenditure.getState())&&StringUtils.equals(officeExpenditure.getState(), "1")){
				promptMessageDto.setPromptMessage("保存成功");
			}else{
				promptMessageDto.setPromptMessage("提交成功,已进入流程中");
			}
		} catch (Exception e) {
			e.printStackTrace();
			promptMessageDto.setOperateSuccess(false);
			promptMessageDto.setErrorMessage("保存失败");
		}
		return SUCCESS;
	}
	
	public String auditList(){
		if(auditStatus==0){
			officeExpenditures=officeExpenditureService.toDoAudit(getUnitId(), getLoginUser().getUserId(), getPage());
		}else if(auditStatus==2){
			officeExpenditures=officeExpenditureService.HaveDoAudit(getLoginUser().getUserId(), getPage());
		}else{
			officeExpenditures=officeExpenditureService.getOfficeExpendituresByUnitId(getUnitId());
			if(CollectionUtils.isEmpty(officeExpenditures)){
				officeExpenditures=new ArrayList<OfficeExpenditure>();
			}else{
				officeExpenditures=officeFlowService.haveDoneAudit(officeExpenditures, getLoginUser().getUserId(), getPage());
			}
		}
		
		Iterator<OfficeExpenditure> iterator =  officeExpenditures.iterator();
		Set<String> userIds = new HashSet<String>();
		Set<String> ids1 = new HashSet<String>();
		Set<String> ids2 = new HashSet<String>();
		Set<String> ids3 = new HashSet<String>();
		Set<String> ids4 = new HashSet<String>();
		while(iterator.hasNext()){
			OfficeExpenditure ent = iterator.next();
			userIds.add(ent.getApplyUserId());
			
			if(ExpenConstants.TYPE_1.equals(ent.getType())){
				ids1.add(ent.getId());
			}else if(ExpenConstants.TYPE_2.equals(ent.getType())){
				ids2.add(ent.getId());
			}else if(ExpenConstants.TYPE_3.equals(ent.getType())){
				ids3.add(ent.getId());
			}else if(ExpenConstants.TYPE_4.equals(ent.getType())){
				ids4.add(ent.getId());
			}
		}

		Map<String, OfficeExpenditureMetting> map1 = officeExpenditureMettingService.getOfficeExpenditureMettingByExIds(ids1.toArray(new String[0]));
		Map<String, OfficeExpenditureReception> map2 = officeExpenditureReceptionService.getOfficeExpenditureReceptionByExIds(ids2.toArray(new String[0]));
		Map<String, OfficeExpenditureOutlay> map3 = officeExpenditureOutlayService.getOfficeExpenditureOutlayByExIds(ids3.toArray(new String[0]));
		Map<String, OfficeExpenditureBusTrip> map4 = officeExpenditureBusTripService.getOfficeExpenditureBusTripByExIds(ids4.toArray(new String[0]));
		
		
		Map<String, User> map = userService.getUsersMap(userIds.toArray(new String[0]));
		Map<String, Dept> deptMap = deptService.getDeptMap(getUnitId());
		for(OfficeExpenditure ent : officeExpenditures){
			if(map.containsKey(ent.getApplyUserId())){
				User user = map.get(ent.getApplyUserId());
				if(user!=null){
					ent.setApplyUserName(user.getRealname());
					if(deptMap.containsKey(user.getDeptid())){
						Dept dept = deptMap.get(user.getDeptid());
						if(dept!=null)
							ent.setApplyUserDeptName(dept.getDeptname());
					}
				}
			}
			//费用合计
			if(ExpenConstants.TYPE_1.equals(ent.getType())){
				if(map1.containsKey(ent.getId())){
					ent.setFee(map1.get(ent.getId()).getSum());
				}
			}else if(ExpenConstants.TYPE_2.equals(ent.getType())){
				if(map2.containsKey(ent.getId())){
					ent.setFee(map2.get(ent.getId()).getSum());
				}
			}else if(ExpenConstants.TYPE_3.equals(ent.getType())){
				if(map3.containsKey(ent.getId())){
					ent.setFee(map3.get(ent.getId()).getSum());
				}
			}else if(ExpenConstants.TYPE_4.equals(ent.getType())){
				if(map4.containsKey(ent.getId())){
					ent.setFee(map4.get(ent.getId()).getSum());
				}
			}
		}
		return SUCCESS;
	}
	public String expenditureAudit(){//TODO
		officeExpenditure=officeExpenditureService.getOfficeExpenditureById(officeExpenditure.getId());
		if(officeExpenditure!=null&&StringUtils.equals(officeExpenditure.getType(), ExpenConstants.TYPE_1)){
			officeExpenditureMetting=officeExpenditureMettingService.getOfficeExpenditureMettingByPrimarId(officeExpenditure.getId());
		}else if(officeExpenditure!=null&&StringUtils.equals(officeExpenditure.getType(), ExpenConstants.TYPE_2)){
			officeExpenditureReception=officeExpenditureReceptionService.getOfficeExpenditureReceptionByExId(officeExpenditure.getId());
		}else if(officeExpenditure!=null&&StringUtils.equals(officeExpenditure.getType(), ExpenConstants.TYPE_3)){
			officeExpenditureOutlay=officeExpenditureOutlayService.getOfficeExpenditureOutlayByExId(officeExpenditure.getId());
		}else if(officeExpenditure!=null&&StringUtils.equals(officeExpenditure.getType(), ExpenConstants.TYPE_4)){
			officeExpenditureBusTrip=officeExpenditureBusTripService.getOfficeExpenditureBusTripByExId(officeExpenditure.getId());
		}
		
		if(StringUtils.isNotBlank(officeExpenditure.getUnitId())){
			unit=unitService.getUnit(officeExpenditure.getUnitId());
		}
		if(unit==null){
			unit=new Unit();
		}
		
		TaskHandlerSave taskHandlerSave = new TaskHandlerSave();
		TaskHandlerShow taskHandlerShow = taskHandlerService.getTaskHandlerShow(taskId, getLoginUser().getUserId());
		
		officeExpenditureChgOpinion=officeExpenditureChgOpinionService.getOfficeExpenditureChgOpinionByTaskId(taskId);
		if(officeExpenditureChgOpinion==null){
			officeExpenditureChgOpinion=new OfficeExpenditureChgOpinion();
		}
		officeExpenditureKjOpinion=officeExpenditureKjOpinionService.getOfficeExpenditureKjOpinionByTaskId(taskId);
		if(officeExpenditureKjOpinion==null){
			officeExpenditureKjOpinion=new OfficeExpenditureKjOpinion();
		}
		
		step=taskHandlerShow.getCurrentTask().getStep();
		if(StringUtils.equals(step, ExpenConstants.FLOW_STEP_BGCWKZ_CGRY)){
			stepExist=true;
		}else if(StringUtils.equals(step, ExpenConstants.FLOW_STEP_BGCWKZ_KJSHH)){
			kjExist=true;
		}
		
		taskHandlerSave.setCurrentTask(taskHandlerShow.getCurrentTask());
		taskHandlerSave.setCurrentUserId(getLoginUser().getUserId());
		taskHandlerSave.setCurrentUnitId(getLoginUser().getUnitId());
		taskHandlerSave.setSubsystemId(FlowConstant.OFFICE_SUBSYSTEM);
		User user = userService.getUser(getLoginUser().getUserId());
		
		Comment comment = new Comment();
		comment.setAssigneeType(AgileIdentityLinkType.PRINCIPAL);
		comment.setAssigneeId(user.getId());
		comment.setAssigneeName(user.getRealname());
		userName = user.getRealname();
		taskHandlerSave.setComment(comment);
		JSONObject json = new JSONObject().fromObject(taskHandlerSave);
		taskHandlerSaveJson = json.toString();
		officeExpenditure.setTaskName(taskHandlerSave.getCurrentTask().getTaskName());
		currentStepId = taskHandlerSave.getCurrentTask().getTaskDefinitionKey();
		flowId = officeExpenditure.getFlowId();
		return SUCCESS;
	}
	public String expenditureAuditPass(){
		try {
			JSONObject object = new JSONObject().fromObject(taskHandlerSaveJson);
			TaskHandlerSave taskHandlerSave =(TaskHandlerSave) JSONObject.toBean(object,
				 TaskHandlerSave.class);
			taskHandlerSave.getComment().setOperateTime(new Date());
			officeExpenditureService.passFlow(isPass, taskHandlerSave,officeExpenditureChgOpinion,officeExpenditureKjOpinion, officeExpenditure.getId());
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
	
	public String exChangeMetting(){
		flowList=flowManageService.getFinishFlowList(getUnitId(), FlowConstant.FLOW_OWNER_UNIT, FlowConstant.OFFICE_EXPENDITURE,FlowConstant.OFFICE_SUBSYSTEM,FlowConstant.OFFICEDOC_FLOW_EASY_LEVEL_1);
		return SUCCESS;
	}
	public String exChangeReception(){
		flowList=flowManageService.getFinishFlowList(getUnitId(), FlowConstant.FLOW_OWNER_UNIT, FlowConstant.OFFICE_EXPENDITURE,FlowConstant.OFFICE_SUBSYSTEM,FlowConstant.OFFICEDOC_FLOW_EASY_LEVEL_1);
		return SUCCESS;
	}
	public String exChangeOutlay(){
		flowList=flowManageService.getFinishFlowList(getUnitId(), FlowConstant.FLOW_OWNER_UNIT, FlowConstant.OFFICE_EXPENDITURE,FlowConstant.OFFICE_SUBSYSTEM,FlowConstant.OFFICEDOC_FLOW_EASY_LEVEL_1);
		return SUCCESS;
	}
	public String exChangeBusTrip(){
		flowList=flowManageService.getFinishFlowList(getUnitId(), FlowConstant.FLOW_OWNER_UNIT, FlowConstant.OFFICE_EXPENDITURE,FlowConstant.OFFICE_SUBSYSTEM,FlowConstant.OFFICEDOC_FLOW_EASY_LEVEL_1);
		return SUCCESS;
	}
	public OfficeExpenditure getOfficeExpenditure() {
		return officeExpenditure;
	}
	public void setOfficeExpenditure(OfficeExpenditure officeExpenditure) {
		this.officeExpenditure = officeExpenditure;
	}
	public List<OfficeExpenditure> getOfficeExpenditures() {
		return officeExpenditures;
	}
	public void setOfficeExpenditures(List<OfficeExpenditure> officeExpenditures) {
		this.officeExpenditures = officeExpenditures;
	}
	public void setOfficeExpenditureService(
			OfficeExpenditureService officeExpenditureService) {
		this.officeExpenditureService = officeExpenditureService;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public OfficeExpenditureMetting getOfficeExpenditureMetting() {
		return officeExpenditureMetting;
	}
	public void setOfficeExpenditureMetting(OfficeExpenditureMetting officeExpenditureMetting) {
		this.officeExpenditureMetting = officeExpenditureMetting;
	}
	public List<OfficeExpenditureMetting> getOfficeExpenditureMettings() {
		return officeExpenditureMettings;
	}
	public void setOfficeExpenditureMettings(
			List<OfficeExpenditureMetting> officeExpenditureMettings) {
		this.officeExpenditureMettings = officeExpenditureMettings;
	}
	public OfficeExpenditureReception getOfficeExpenditureReception() {
		return officeExpenditureReception;
	}
	public void setOfficeExpenditureReception(OfficeExpenditureReception officeExpenditureReception) {
		this.officeExpenditureReception = officeExpenditureReception;
	}
	public List<OfficeExpenditureReception> getOfficeExpenditureReceptions() {
		return officeExpenditureReceptions;
	}
	public void setOfficeExpenditureReceptions(
			List<OfficeExpenditureReception> officeExpenditureReceptions) {
		this.officeExpenditureReceptions = officeExpenditureReceptions;
	}
	public OfficeExpenditureOutlay getOfficeExpenditureOutlay() {
		return officeExpenditureOutlay;
	}
	public void setOfficeExpenditureOutlay(OfficeExpenditureOutlay officeExpenditureOutlay) {
		this.officeExpenditureOutlay = officeExpenditureOutlay;
	}
	public List<OfficeExpenditureOutlay> getOfficeExpenditureOutlays() {
		return officeExpenditureOutlays;
	}
	public void setOfficeExpenditureOutlays(List<OfficeExpenditureOutlay> officeExpenditureOutlays) {
		this.officeExpenditureOutlays = officeExpenditureOutlays;
	}
	public OfficeExpenditureBusTrip getOfficeExpenditureBusTrip() {
		return officeExpenditureBusTrip;
	}
	public void setOfficeExpenditureBusTrip(OfficeExpenditureBusTrip officeExpenditureBusTrip) {
		this.officeExpenditureBusTrip = officeExpenditureBusTrip;
	}
	public List<OfficeExpenditureBusTrip> getOfficeExpenditureBusTrips() {
		return officeExpenditureBusTrips;
	}
	public void setOfficeExpenditureBusTrips(
			List<OfficeExpenditureBusTrip> officeExpenditureBusTrips) {
		this.officeExpenditureBusTrips = officeExpenditureBusTrips;
	}
	public void setOfficeExpenditureMettingService(
			OfficeExpenditureMettingService officeExpenditureMettingService) {
		this.officeExpenditureMettingService = officeExpenditureMettingService;
	}
	public void setOfficeExpenditureReceptionService(
			OfficeExpenditureReceptionService officeExpenditureReceptionService) {
		this.officeExpenditureReceptionService = officeExpenditureReceptionService;
	}
	public void setOfficeExpenditureOutlayService(
			OfficeExpenditureOutlayService officeExpenditureOutlayService) {
		this.officeExpenditureOutlayService = officeExpenditureOutlayService;
	}
	public void setOfficeExpenditureBusTripService(
			OfficeExpenditureBusTripService officeExpenditureBusTripService) {
		this.officeExpenditureBusTripService = officeExpenditureBusTripService;
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
	public void setTaskHandlerService(TaskHandlerService taskHandlerService) {
		this.taskHandlerService = taskHandlerService;
	}
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	public String getStep() {
		return step;
	}
	public void setStep(String step) {
		this.step = step;
	}
	public boolean isStepExist() {
		return stepExist;
	}
	public void setStepExist(boolean stepExist) {
		this.stepExist = stepExist;
	}
	public boolean isKjExist() {
		return kjExist;
	}
	public void setKjExist(boolean kjExist) {
		this.kjExist = kjExist;
	}
	public OfficeExpenditureChgOpinion getOfficeExpenditureChgOpinion() {
		return officeExpenditureChgOpinion;
	}
	public void setOfficeExpenditureChgOpinion(
			OfficeExpenditureChgOpinion officeExpenditureChgOpinion) {
		this.officeExpenditureChgOpinion = officeExpenditureChgOpinion;
	}
	public OfficeExpenditureKjOpinion getOfficeExpenditureKjOpinion() {
		return officeExpenditureKjOpinion;
	}
	public void setOfficeExpenditureKjOpinion(OfficeExpenditureKjOpinion officeExpenditureKjOpinion) {
		this.officeExpenditureKjOpinion = officeExpenditureKjOpinion;
	}
	public boolean isPass() {
		return isPass;
	}
	public void setPass(boolean isPass) {
		this.isPass = isPass;
	}
	public void setOfficeExpenditureKjOpinionService(
			OfficeExpenditureKjOpinionService officeExpenditureKjOpinionService) {
		this.officeExpenditureKjOpinionService = officeExpenditureKjOpinionService;
	}
	public void setOfficeExpenditureChgOpinionService(
			OfficeExpenditureChgOpinionService officeExpenditureChgOpinionService) {
		this.officeExpenditureChgOpinionService = officeExpenditureChgOpinionService;
	}
	public void setOfficeFlowService(OfficeFlowService officeFlowService) {
		this.officeFlowService = officeFlowService;
	}
	public Unit getUnit() {
		return unit;
	}
	public void setUnit(Unit unit) {
		this.unit = unit;
	}
	public void setUnitService(UnitService unitService) {
		this.unitService = unitService;
	}
	public String getFromTab() {
		return fromTab;
	}
	public void setFromTab(String fromTab) {
		this.fromTab = fromTab;
	}
	public void setDeptService(DeptService deptService) {
		this.deptService = deptService;
	}
	public boolean isQueryExpenditure() {
		return queryExpenditure;
	}
	public void setQueryExpenditure(boolean queryExpenditure) {
		this.queryExpenditure = queryExpenditure;
	}
	
}
