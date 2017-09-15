package net.zdsoft.office.expenditure.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

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
import net.zdsoft.office.expenditure.constant.ExpenConstants;
import net.zdsoft.office.expenditure.dao.OfficeExpenditureDao;
import net.zdsoft.office.officeFlow.dto.HisTask;
import net.zdsoft.office.officeFlow.service.OfficeFlowService;
import net.zdsoft.office.teacherLeave.entity.OfficeTeacherLeave;
import net.zdsoft.office.util.Constants;
import net.zdsoft.eis.base.common.entity.Dept;
import net.zdsoft.eis.base.common.entity.User;
import net.zdsoft.eis.base.common.service.DeptService;
import net.zdsoft.eis.base.common.service.UserService;
import net.zdsoft.eis.component.flowManage.constant.FlowConstant;
import net.zdsoft.jbpm.core.entity.Comment;
import net.zdsoft.jbpm.core.entity.TaskDescription;
import net.zdsoft.jbpm.core.entity.TaskHandlerResult;
import net.zdsoft.jbpm.core.entity.TaskHandlerSave;
import net.zdsoft.jbpm.core.service.ProcessHandlerService;
import net.zdsoft.jbpm.core.service.TaskHandlerService;
import net.zdsoft.keel.util.Pagination;
/**
 * office_expenditure（财务开支）
 * @author 
 * 
 */
public class OfficeExpenditureServiceImpl implements OfficeExpenditureService{
	private OfficeExpenditureDao officeExpenditureDao;
	private OfficeExpenditureMettingService officeExpenditureMettingService;
	private OfficeExpenditureReceptionService officeExpenditureReceptionService;
	private OfficeExpenditureOutlayService officeExpenditureOutlayService;
	private OfficeExpenditureBusTripService officeExpenditureBusTripService;
	private ProcessHandlerService processHandlerService;
	private TaskHandlerService taskHandlerService;
	private UserService userService;
	private OfficeFlowService officeFlowService;
	private DeptService deptService;
	private OfficeExpenditureChgOpinionService officeExpenditureChgOpinionService;
	private OfficeExpenditureKjOpinionService officeExpenditureKjOpinionService;

	public void setDeptService(DeptService deptService) {
		this.deptService = deptService;
	}
	@Override
	public OfficeExpenditure save(OfficeExpenditure officeExpenditure){
		return officeExpenditureDao.save(officeExpenditure);
	}

	@Override
	public void delete(String[] ids){
		OfficeExpenditure officeExpenditure=officeExpenditureDao.getOfficeExpenditureById(ids[0]);
		
		if(officeExpenditure!=null&&StringUtils.equals(officeExpenditure.getType(), ExpenConstants.TYPE_1)){
			officeExpenditureMettingService.deleteByExId(officeExpenditure.getId());
		}else if(officeExpenditure!=null&&StringUtils.equals(officeExpenditure.getType(), ExpenConstants.TYPE_2)){
			officeExpenditureReceptionService.deleteByExId(officeExpenditure.getId());
		}else if(officeExpenditure!=null&&StringUtils.equals(officeExpenditure.getType(), ExpenConstants.TYPE_3)){
			officeExpenditureOutlayService.deleteByExId(officeExpenditure.getId());
		}else if(officeExpenditure!=null&&StringUtils.equals(officeExpenditure.getType(), ExpenConstants.TYPE_4)){
			officeExpenditureBusTripService.deleteByExId(officeExpenditure.getId());
		}
		
		officeExpenditureDao.delete(ids);
	}

	@Override
	public Integer update(OfficeExpenditure officeExpenditure){
		return officeExpenditureDao.update(officeExpenditure);
	}

	@Override
	public OfficeExpenditure getOfficeExpenditureById(String id){
		OfficeExpenditure officeExpenditure= officeExpenditureDao.getOfficeExpenditureById(id);
		
		if(officeExpenditure!=null){
			User user=userService.getUser(officeExpenditure.getApplyUserId());
			if(user!=null){
				officeExpenditure.setApplyUserName(user.getRealname());
				if(StringUtils.isNotBlank(user.getDeptid())){
					Dept dept = deptService.getDept(user.getDeptid());
					if(dept!=null)
						officeExpenditure.setApplyUserDeptName(dept.getDeptname());
				}
			}else{
				officeExpenditure.setApplyUserName("用户已删除");
			}
			if(StringUtils.isNotEmpty(officeExpenditure.getFlowId())){
				List<HisTask> hisTask = officeFlowService.getHisTask(officeExpenditure.getFlowId());
				List<HisTask> otherHisTask = new ArrayList<HisTask>();
				Set<String> kjSet = new HashSet<String>();
				Set<String> chgSet = new HashSet<String>();
				Map<String, Comment> comMap = new HashMap<String, Comment>();
				for (HisTask t : hisTask) {
					if(StringUtils.isNotBlank(t.getStep())){
						comMap.put(t.getTaskId(), t.getComment());
						if(ExpenConstants.FLOW_STEP_BGCWKZ_KJSHH.equals(t.getStep())){
							kjSet.add(t.getTaskId());
						}else if(ExpenConstants.FLOW_STEP_BGCWKZ_CGRY.equals(t.getStep())){
							chgSet.add(t.getTaskId());
						}else{
							otherHisTask.add(t);
						}
					}
				}
				if(kjSet.size()>0){
					List<OfficeExpenditureKjOpinion> list = officeExpenditureKjOpinionService.getOfficeExpenditureKjOpinionByTaskIds(kjSet.toArray(new String[0]));
					Map<String, OfficeExpenditureKjOpinion> map = new HashMap<String, OfficeExpenditureKjOpinion>();
					Iterator<OfficeExpenditureKjOpinion> iterator = list.iterator();
					while(iterator.hasNext()){
						OfficeExpenditureKjOpinion ent = iterator.next();
						if(comMap.containsKey(ent.getTaskId())){
							Comment c = comMap.get(ent.getTaskId());
							if(c!=null){
								ent.setAuditUserName(c.getAssigneeName());
								ent.setAuditDate(c.getOperateTime());
							}
						}
						map.put(ent.getTaskId(), ent);
					}
					officeExpenditure.setKjOpinionMap(map);
				}
				if(chgSet.size()>0){
					List<OfficeExpenditureChgOpinion> list = officeExpenditureChgOpinionService.getOfficeExpenditureChgOpinionByTaskIds(chgSet.toArray(new String[0]));
					Map<String, OfficeExpenditureChgOpinion> map = new HashMap<String, OfficeExpenditureChgOpinion>();
					Iterator<OfficeExpenditureChgOpinion> iterator = list.iterator();
					while(iterator.hasNext()){
						OfficeExpenditureChgOpinion ent = iterator.next();
						if(comMap.containsKey(ent.getTaskId())){
							Comment c = comMap.get(ent.getTaskId());
							if(c!=null){
								ent.setAuditUserName(c.getAssigneeName());
								ent.setAuditDate(c.getOperateTime());
							}
						}
						map.put(ent.getTaskId(), ent);
					}
					officeExpenditure.setChgOpinionMap(map);
				}
				officeExpenditure.setHisTaskList(hisTask);
				officeExpenditure.setOtherHisTask(otherHisTask);
			}
		}else{
			officeExpenditure=new OfficeExpenditure();
		}
		//获取会计和车管意见信息
		
		return officeExpenditure;
	}

	@Override
	public List<OfficeExpenditure> getOfficeExpenditures(String unitId,
			String applyUserId, String type, String state, Pagination page) {
		return officeExpenditureDao.getOfficeExpenditures(unitId, applyUserId, type, state, page);
	}
	
	public List<OfficeExpenditure> getOfficeExpendituresByUserIds(String unitId,
			String[] applyUserIds, String type, String state, Pagination page){
		return officeExpenditureDao.getOfficeExpendituresByUserIds(unitId, applyUserIds, type, state, page);
	}

	@Override
	public void saveThing(OfficeExpenditure officeExpenditure,OfficeExpenditureMetting officeExpenditureMetting,
			OfficeExpenditureReception officeExpenditureReception,OfficeExpenditureOutlay officeExpenditureOutlay,
			OfficeExpenditureBusTrip officeExpenditureBusTrip) {
		OfficeExpenditure officeEpd=officeExpenditureDao.save(officeExpenditure);
		
		if(officeEpd!=null&&StringUtils.equals(officeEpd.getState(), Constants.LEAVE_APPLY_FLOWING+"")){//处理流程
			Map<String, Object> variables = new HashMap<String, Object>();
			variables.put("pass",true);
			
			String flowId = processHandlerService.startProcessInstance(FlowConstant.OFFICE_SUBSYSTEM,officeEpd.getFlowId(), 
					Integer.parseInt(FlowConstant.OFFICE_SUBSYSTEM+FlowConstant.OFFICE_EXPENDITURE), officeEpd.getId(), officeEpd.getApplyUserId(), variables);
			
			officeEpd.setFlowId(flowId);
			officeExpenditureDao.update(officeEpd);
		}
		
		
		if(officeExpenditure!=null&&StringUtils.equals(officeExpenditure.getType(), ExpenConstants.TYPE_1)){
			officeExpenditureMetting.setExpenditureId(officeEpd.getId());
			officeExpenditureMetting.setCreationTime(new Date());
			officeExpenditureMettingService.save(officeExpenditureMetting);
			
		}else if(officeExpenditure!=null&&StringUtils.equals(officeExpenditure.getType(), ExpenConstants.TYPE_2)){
			officeExpenditureReception.setExpenditureId(officeEpd.getId());
			officeExpenditureReception.setCreationTime(new Date());
			officeExpenditureReceptionService.save(officeExpenditureReception);
		}else if(officeExpenditure!=null&&StringUtils.equals(officeExpenditure.getType(), ExpenConstants.TYPE_3)){
			officeExpenditureOutlay.setExpenditureId(officeEpd.getId());
			officeExpenditureOutlay.setCreationTime(new Date());
			officeExpenditureOutlayService.save(officeExpenditureOutlay);
		}else{
			officeExpenditureBusTrip.setExpenditureId(officeEpd.getId());
			officeExpenditureBusTrip.setCreationTime(new Date());
			officeExpenditureBusTripService.save(officeExpenditureBusTrip);
		}
		
	}

	@Override
	public void updateThing(OfficeExpenditure officeExpenditure,OfficeExpenditureMetting officeExpenditureMetting,
			OfficeExpenditureReception officeExpenditureReception,OfficeExpenditureOutlay officeExpenditureOutlay,
			OfficeExpenditureBusTrip officeExpenditureBusTrip) {
		OfficeExpenditure officeEpd=officeExpenditureDao.getOfficeExpenditureById(officeExpenditure.getId());//先删除
		officeEpd.setState(officeExpenditure.getState());
		officeEpd.setModifyTime(new Date());
		officeEpd.setType(officeExpenditure.getType());
		officeEpd.setFlowId(officeExpenditure.getFlowId());
		officeExpenditureDao.update(officeEpd);
		
		if(officeEpd!=null&&StringUtils.equals(officeEpd.getState(), Constants.LEAVE_APPLY_FLOWING+"")){//处理流程
			Map<String, Object> variables = new HashMap<String, Object>();
			variables.put("pass",true);
			
			String flowId = processHandlerService.startProcessInstance(FlowConstant.OFFICE_SUBSYSTEM,officeEpd.getFlowId(), 
					Integer.parseInt(FlowConstant.OFFICE_SUBSYSTEM+FlowConstant.OFFICE_EXPENDITURE), officeEpd.getId(), officeEpd.getApplyUserId(), variables);
			
			//officeFlowStepInfoService.batchUpdateByFlowId(officeTeacherLeave.getFlowId(), flowId);
			officeEpd.setFlowId(flowId);
			officeExpenditureDao.update(officeEpd);
		}
		
		if(officeEpd!=null&&StringUtils.equals(officeEpd.getType(), ExpenConstants.TYPE_1)){
			officeExpenditureMettingService.deleteByExId(officeExpenditure.getId());
		}else if(officeEpd!=null&&StringUtils.equals(officeEpd.getType(), ExpenConstants.TYPE_2)){
			officeExpenditureReceptionService.deleteByExId(officeExpenditure.getId());
		}else if(officeEpd!=null&&StringUtils.equals(officeEpd.getType(), ExpenConstants.TYPE_3)){
			officeExpenditureOutlayService.deleteByExId(officeExpenditure.getId());
		}else{
			officeExpenditureBusTripService.deleteByExId(officeExpenditure.getId());
		}
		
		if(officeExpenditure!=null&&StringUtils.equals(officeExpenditure.getType(), ExpenConstants.TYPE_1)){//新增
			officeExpenditureMetting.setExpenditureId(officeEpd.getId());
			officeExpenditureMetting.setCreationTime(new Date());
			officeExpenditureMettingService.save(officeExpenditureMetting);
		}else if(officeExpenditure!=null&&StringUtils.equals(officeExpenditure.getType(), ExpenConstants.TYPE_2)){
			officeExpenditureReception.setExpenditureId(officeEpd.getId());
			officeExpenditureReception.setCreationTime(new Date());
			officeExpenditureReceptionService.save(officeExpenditureReception);
		}else if(officeExpenditure!=null&&StringUtils.equals(officeExpenditure.getType(), ExpenConstants.TYPE_3)){
			officeExpenditureOutlay.setExpenditureId(officeEpd.getId());
			officeExpenditureOutlay.setCreationTime(new Date());
			officeExpenditureOutlayService.save(officeExpenditureOutlay);
		}else{
			officeExpenditureBusTrip.setExpenditureId(officeEpd.getId());
			officeExpenditureBusTrip.setCreationTime(new Date());
			officeExpenditureBusTripService.save(officeExpenditureBusTrip);
		}
		
	}

	@Override
	public List<OfficeExpenditure> toDoAudit(String unitId,String userId, Pagination page) {
		List<OfficeExpenditure> leaveList = new ArrayList<OfficeExpenditure>();
		List<TaskDescription> todoTaskList = new ArrayList<TaskDescription>();
		todoTaskList = taskHandlerService.getTodoTasks(userId, Integer.parseInt(FlowConstant.OFFICE_SUBSYSTEM+FlowConstant.OFFICE_EXPENDITURE), page);
		
		if(CollectionUtils.isNotEmpty(todoTaskList)){
			Set<String> flowIdSet= new HashSet<String>();
			for (TaskDescription task : todoTaskList) {
				flowIdSet.add(task.getProcessInstanceId());	
			}
			Map<String, OfficeExpenditure> officeEMap=officeExpenditureDao.getOfficeEMap(flowIdSet.toArray(new String[0]));
			
			Set<String> userIdSet = new HashSet<String>();
			for (String leaveId : officeEMap.keySet()) {
				OfficeExpenditure officeExpenditure = officeEMap.get(leaveId);
				if(officeExpenditure!=null){
					userIdSet.add(officeExpenditure.getApplyUserId());
				}
			}
			Map<String,User> userMap = new HashMap<String, User>();
			userMap = userService.getUserWithDelMap(userIdSet.toArray(new String[0]));
			
			for (TaskDescription task : todoTaskList) {
				OfficeExpenditure officeExpenditure=officeEMap.get(task.getProcessInstanceId());
				if(officeExpenditure!=null){
					OfficeExpenditure office=null;
					try {
						office=(OfficeExpenditure) BeanUtils.cloneBean(officeExpenditure);
					} catch (Exception e) {
						e.printStackTrace();
					}
					if(office==null){
						continue;
					}
					
					User user=userMap.get(office.getApplyUserId());
					if(user!=null){
						office.setApplyUserName(user.getRealname());
					}else{
						office.setApplyUserName("用户已删除");
					}
					office.setTaskId(task.getTaskId());
					office.setTaskName(task.getTaskName());
					leaveList.add(office);
				}
			}
		}
		return leaveList;
	}

	private String getProperty(Object bean , String name) {
		try {
			return BeanUtils.getProperty(bean, name);
		}catch(Exception e) {
			return "";
		}
	}
	@Override
	public void passFlow(boolean pass, TaskHandlerSave taskHandlerSave,OfficeExpenditureChgOpinion officeExpenditureChgOpinion,//TODO
			OfficeExpenditureKjOpinion officeExpenditureKjOpinion,String officeExpenditureId) {
		OfficeExpenditure officeExpenditure=officeExpenditureDao.getOfficeExpenditureById(officeExpenditureId);
		
		if(officeExpenditureChgOpinion!=null){
			officeExpenditureChgOpinion.setTaskId(taskHandlerSave.getCurrentTask().getTaskId());
			if(StringUtils.isNotBlank(officeExpenditureChgOpinion.getPayType())&&!StringUtils.equals(officeExpenditureChgOpinion.getPayType(), "3")){
				officeExpenditureChgOpinion.setFee(Double.valueOf(getProperty(officeExpenditureChgOpinion,"fee"+officeExpenditureChgOpinion.getPayType())));
			}
			officeExpenditureChgOpinion.setCreationTime(new Date());
			officeExpenditureChgOpinionService.save(officeExpenditureChgOpinion);
		}else if(officeExpenditureKjOpinion!=null){
			officeExpenditureKjOpinion.setTaskId(taskHandlerSave.getCurrentTask().getTaskId());
			officeExpenditureKjOpinion.setCreationTime(new Date());
			officeExpenditureKjOpinionService.save(officeExpenditureKjOpinion);
		}
		
		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("pass", pass);
		taskHandlerSave.setVariables(variables);
		TaskHandlerResult result;
		if(pass){
			taskHandlerSave.getComment().setTextComment("[审核通过]"+taskHandlerSave.getComment().getTextComment());
			//TODO 发送消息提醒
			//sendNote(officeExpenditure);
			result = taskHandlerService.completeTask(taskHandlerSave);
		}else{
			taskHandlerSave.getComment().setTextComment("[审核不通过]"+taskHandlerSave.getComment().getTextComment());
			boolean isNotFinish = taskHandlerService.isExclusiveGatewayForNext(officeExpenditure.getUnitId(), taskHandlerSave.getSubsystemId(), taskHandlerSave.getCurrentTask().getTaskDefinitionKey(), officeExpenditure.getFlowId());
			if(isNotFinish){
				result = taskHandlerService.completeTask(taskHandlerSave);
			}else{
				result = taskHandlerService.suspendTask(taskHandlerSave);
			}
		}
		
		if(result.getStatus()==TaskHandlerResult.STATUS_FINISH){
			if(result.getResult()==TaskHandlerResult.RESULT_PASS){
				officeExpenditure.setState(Constants.LEAVE_APPLY_FLOW_FINSH_PASS+"");
			}else if(result.getResult()==TaskHandlerResult.RESULT_NOT_PASS){
				officeExpenditure.setState(Constants.LEAVE_APPLY_FLOW_FINSH_NOT_PASS+"");
			}
			officeExpenditure.setModifyTime(new Date());
			update(officeExpenditure);
		}
	}

	@Override
	public List<OfficeExpenditure> HaveDoAudit(String userId,Pagination page) {
		List<OfficeExpenditure> officeExpenditures= officeExpenditureDao.HaveDoAudit(userId, page);
		if(CollectionUtils.isNotEmpty(officeExpenditures)){
			Set<String> userIdSet = new HashSet<String>();
			for (OfficeExpenditure officeExpenditure : officeExpenditures) {
				userIdSet.add(officeExpenditure.getApplyUserId());
			}
			Map<String,User> userMap = new HashMap<String, User>();
			userMap = userService.getUserWithDelMap(userIdSet.toArray(new String[0]));
			
			for (OfficeExpenditure officeExpenditure : officeExpenditures) {
				User user=userMap.get(officeExpenditure.getApplyUserId());
				if(user!=null){
					officeExpenditure.setApplyUserName(user.getRealname());
				}else{
					officeExpenditure.setApplyUserName("用户已删除");
				}
			}
		}
		return officeExpenditures;
	}
	
	@Override
	public List<OfficeExpenditure> getOfficeExpendituresByUnitId(String unitId) {
		return officeExpenditureDao.getOfficeExpendituresByUnitId(unitId);
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

	public void setOfficeExpenditureDao(OfficeExpenditureDao officeExpenditureDao){
		this.officeExpenditureDao = officeExpenditureDao;
	}

	public void setProcessHandlerService(ProcessHandlerService processHandlerService) {
		this.processHandlerService = processHandlerService;
	}

	public void setTaskHandlerService(TaskHandlerService taskHandlerService) {
		this.taskHandlerService = taskHandlerService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public void setOfficeFlowService(OfficeFlowService officeFlowService) {
		this.officeFlowService = officeFlowService;
	}

	public void setOfficeExpenditureChgOpinionService(
			OfficeExpenditureChgOpinionService officeExpenditureChgOpinionService) {
		this.officeExpenditureChgOpinionService = officeExpenditureChgOpinionService;
	}

	public void setOfficeExpenditureKjOpinionService(
			OfficeExpenditureKjOpinionService officeExpenditureKjOpinionService) {
		this.officeExpenditureKjOpinionService = officeExpenditureKjOpinionService;
	}
	
}
