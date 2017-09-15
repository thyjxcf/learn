package net.zdsoft.office.studentLeave.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.zdsoft.jbpm.core.entity.TaskHandlerResult;
import net.zdsoft.jbpm.core.entity.TaskHandlerSave;
import net.zdsoft.jbpm.core.service.ProcessHandlerService;
import net.zdsoft.jbpm.core.service.TaskHandlerService;
import net.zdsoft.keel.util.Pagination;
import net.zdsoft.office.studentLeave.constant.OfficeStuLeaveConstants;
import net.zdsoft.office.studentLeave.dao.OfficeHwstudentLeaveDao;
import net.zdsoft.office.studentLeave.entity.OfficeHwstudentLeave;
import net.zdsoft.office.studentLeave.service.OfficeHwstudentLeaveService;
import net.zdsoft.office.studentLeave.service.OfficeLeavetypeGeneralService;
import net.zdsoft.office.studentLeave.service.OfficeLeavetypeLiveService;
import net.zdsoft.office.studentLeave.service.OfficeLeavetypeLongService;
import net.zdsoft.office.studentLeave.service.OfficeLeavetypeTemporaryService;
import net.zdsoft.office.util.Constants;

import org.apache.commons.lang3.StringUtils;
/**
 * office_hwstudent_leave
 * @author 
 * 
 */
public class OfficeHwstudentLeaveServiceImpl implements OfficeHwstudentLeaveService{
	private OfficeHwstudentLeaveDao officeHwstudentLeaveDao;
	private OfficeLeavetypeGeneralService officeLeavetypeGeneralService;
	private OfficeLeavetypeLiveService officeLeavetypeLiveService;
	private OfficeLeavetypeTemporaryService officeLeavetypeTemporaryService;
	private OfficeLeavetypeLongService officeLeavetypeLongService;
	private ProcessHandlerService processHandlerService;
	private TaskHandlerService taskHandlerService;
	
	public void setTaskHandlerService(TaskHandlerService taskHandlerService) {
		this.taskHandlerService = taskHandlerService;
	}

	public void setProcessHandlerService(ProcessHandlerService processHandlerService) {
		this.processHandlerService = processHandlerService;
	}

	public void setOfficeLeavetypeGeneralService(
			OfficeLeavetypeGeneralService officeLeavetypeGeneralService) {
		this.officeLeavetypeGeneralService = officeLeavetypeGeneralService;
	}

	public void setOfficeLeavetypeLiveService(
			OfficeLeavetypeLiveService officeLeavetypeLiveService) {
		this.officeLeavetypeLiveService = officeLeavetypeLiveService;
	}

	public void setOfficeLeavetypeTemporaryService(
			OfficeLeavetypeTemporaryService officeLeavetypeTemporaryService) {
		this.officeLeavetypeTemporaryService = officeLeavetypeTemporaryService;
	}

	public void setOfficeLeavetypeLongService(
			OfficeLeavetypeLongService officeLeavetypeLongService) {
		this.officeLeavetypeLongService = officeLeavetypeLongService;
	}

	@Override
	public OfficeHwstudentLeave save(OfficeHwstudentLeave officeHwstudentLeave){
		return officeHwstudentLeaveDao.save(officeHwstudentLeave);
	}

	@Override
	public Integer delete(String[] ids){
		return officeHwstudentLeaveDao.delete(ids);
	}

	@Override
	public Integer update(OfficeHwstudentLeave officeHwstudentLeave){
		return officeHwstudentLeaveDao.update(officeHwstudentLeave);
	}

	@Override
	public OfficeHwstudentLeave getOfficeHwstudentLeaveById(String id){
		return officeHwstudentLeaveDao.getOfficeHwstudentLeaveById(id);
	}

	@Override
	public Map<String, OfficeHwstudentLeave> getOfficeHwstudentLeaveMapByIds(String[] ids){
		return officeHwstudentLeaveDao.getOfficeHwstudentLeaveMapByIds(ids);
	}

	@Override
	public List<OfficeHwstudentLeave> getOfficeHwstudentLeaveList(){
		return officeHwstudentLeaveDao.getOfficeHwstudentLeaveList();
	}

	@Override
	public List<OfficeHwstudentLeave> getOfficeHwstudentLeavePage(Pagination page){
		return officeHwstudentLeaveDao.getOfficeHwstudentLeavePage(page);
	}

	@Override
	public List<OfficeHwstudentLeave> getOfficeHwstudentLeaveByUnitIdList(String unitId){
		return officeHwstudentLeaveDao.getOfficeHwstudentLeaveByUnitIdList(unitId);
	}
	
	@Override
	public List<OfficeHwstudentLeave> findByUnitIdAndType(String unitId,
			String classId, String studentId, String leaveType) {
		return officeHwstudentLeaveDao.findByUnitIdAndType(unitId,classId,studentId, leaveType);
	}
	@Override
	public List<OfficeHwstudentLeave> findByUnitIdAndSubmit(String unitId,
			String studentId) {
		return officeHwstudentLeaveDao.findByUnitIdAndSubmit(unitId,studentId);
	}
	@Override
	public List<OfficeHwstudentLeave> getLeavesByIdsAndState(String stuId,
			String[] leaveIds, String[] states) {
		return officeHwstudentLeaveDao.getLeavesByIdsAndState(stuId, leaveIds, states);
	}
	@Override
	public List<OfficeHwstudentLeave> getOfficeHwstudentLeaveByUnitIdPage(String unitId, Pagination page){
		return officeHwstudentLeaveDao.getOfficeHwstudentLeaveByUnitIdPage(unitId, page);
	}
	@Override
	public List<OfficeHwstudentLeave> getOfficeHwstudentLeaveByUnitIdPage(
			String unitId, String stuId, String type, Pagination page) {
		return officeHwstudentLeaveDao.getOfficeHwstudentLeaveByUnitIdPage(unitId,stuId, type, page);
	}
	public void setOfficeHwstudentLeaveDao(OfficeHwstudentLeaveDao officeHwstudentLeaveDao){
		this.officeHwstudentLeaveDao = officeHwstudentLeaveDao;
	}
	
	@Override
	public void deleteById(String leaveId) {
		OfficeHwstudentLeave leave = officeHwstudentLeaveDao.getOfficeHwstudentLeaveById(leaveId);
			if(OfficeStuLeaveConstants.OFFCIE_STULEAVE_WAIT.equals(leave.getState())
					|| OfficeStuLeaveConstants.OFFCIE_STULEAVE_PASS.equals(leave.getState())
					|| OfficeStuLeaveConstants.OFFCIE_STULEAVE_FAILED.equals(leave.getState())){
				processHandlerService.deleteProcessInstance(leave.getFlowId(), true);
			}
			if(StringUtils.equals(leave.getType(), "1")){
				officeLeavetypeGeneralService.deleteByLeaveId(leaveId);
			}else if(StringUtils.equals(leave.getType(), "2")){
				officeLeavetypeTemporaryService.deleteByLeaveId(leaveId);
			}else if(StringUtils.equals(leave.getType(), "3")){
				officeLeavetypeLiveService.deleteByLeaveId(leaveId);
			}else if(StringUtils.equals(leave.getType(), "4")){
				officeLeavetypeLongService.deleteByLeaveId(leaveId);
			}
		officeHwstudentLeaveDao.delete(new String[]{leaveId});
	}
	
	@Override
	public Map<String, OfficeHwstudentLeave> findByFlowIds(String type,
			String[] flowIds) {
		return officeHwstudentLeaveDao.findByFlowIds(type,flowIds);
	}
	
	@Override
	public List<OfficeHwstudentLeave> getAuditedList(String userId,
			String[] state, String type, Pagination page) {
		return officeHwstudentLeaveDao.getAuditedList(userId,state,type,page);
	}
	
	@Override
	public void doAudit(boolean pass, TaskHandlerSave taskHandlerSave,
			String id, String currentStepId) {
		OfficeHwstudentLeave updateLeave = officeHwstudentLeaveDao.getOfficeHwstudentLeaveById(id);
		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("pass", pass);
		taskHandlerSave.setVariables(variables);
		TaskHandlerResult result;
		if(pass){
			taskHandlerSave.getComment().setTextComment("[审核通过]"+taskHandlerSave.getComment().getTextComment());
			result = taskHandlerService.completeTask(taskHandlerSave);
		}else{
			taskHandlerSave.getComment().setTextComment("[审核不通过]"+taskHandlerSave.getComment().getTextComment());
			boolean isNotFinish = taskHandlerService.isExclusiveGatewayForNext(updateLeave.getUnitId(), taskHandlerSave.getSubsystemId(), taskHandlerSave.getCurrentTask().getTaskDefinitionKey(), updateLeave.getFlowId());
			if(isNotFinish){
				result = taskHandlerService.completeTask(taskHandlerSave);
			}else{
				result = taskHandlerService.suspendTask(taskHandlerSave);
			}
		}
		if(result.getStatus()==TaskHandlerResult.STATUS_FINISH){
			if(result.getResult()==TaskHandlerResult.RESULT_PASS){
				updateLeave.setState(Constants.LEAVE_APPLY_FLOW_FINSH_PASS+"");
			}else if(result.getResult()==TaskHandlerResult.RESULT_NOT_PASS){
				updateLeave.setState(Constants.LEAVE_APPLY_FLOW_FINSH_NOT_PASS+"");
			}
			update(updateLeave);
		}
	}
}

