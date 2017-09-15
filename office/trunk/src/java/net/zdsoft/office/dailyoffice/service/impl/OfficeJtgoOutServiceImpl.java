package net.zdsoft.office.dailyoffice.service.impl;

import java.util.*;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import net.zdsoft.office.convertflow.constant.ConvertFlowConstants;
import net.zdsoft.office.convertflow.service.OfficeConvertFlowService;
import net.zdsoft.office.convertflow.service.OfficeFlowSendMsgService;
import net.zdsoft.office.dailyoffice.entity.OfficeJtgoOut;
import net.zdsoft.office.dailyoffice.service.OfficeJtgoOutService;
import net.zdsoft.office.dailyoffice.dao.OfficeJtgoOutDao;
import net.zdsoft.office.officeFlow.dto.HisTask;
import net.zdsoft.office.officeFlow.service.OfficeFlowService;
import net.zdsoft.office.officeFlow.service.OfficeFlowStepInfoService;
import net.zdsoft.office.util.Constants;
import net.zdsoft.eis.base.attachment.entity.Attachment;
import net.zdsoft.eis.base.attachment.service.AttachmentService;
import net.zdsoft.eis.base.common.entity.User;
import net.zdsoft.eis.base.common.service.UserService;
import net.zdsoft.eis.component.flowManage.constant.FlowConstant;
import net.zdsoft.jbpm.core.entity.TaskHandlerResult;
import net.zdsoft.jbpm.core.entity.TaskHandlerSave;
import net.zdsoft.jbpm.core.service.ProcessHandlerService;
import net.zdsoft.jbpm.core.service.TaskHandlerService;
import net.zdsoft.keel.util.Pagination;
import net.zdsoft.keel.util.UUIDUtils;
import net.zdsoft.keelcnet.action.UploadFile;
/**
 * office_jtgo_out
 * @author 
 * 
 */
public class OfficeJtgoOutServiceImpl implements OfficeJtgoOutService{
	private OfficeJtgoOutDao officeJtgoOutDao;
	private AttachmentService attachmentService;
	private ProcessHandlerService processHandlerService;
	private UserService userService;
	private TaskHandlerService taskHandlerService;
	private OfficeConvertFlowService officeConvertFlowService;
	private OfficeFlowSendMsgService officeFlowSendMsgService;
	private OfficeFlowService officeFlowService;
	private OfficeFlowStepInfoService officeFlowStepInfoService;

	@Override
	public OfficeJtgoOut save(OfficeJtgoOut officeJtgoOut){
		return officeJtgoOutDao.save(officeJtgoOut);
	}

	@Override
	public Integer delete(String[] ids){
		OfficeJtgoOut officeJtgoOut=officeJtgoOutDao.getOfficeJtgoOutById(ids[0]);
		if(Integer.valueOf(officeJtgoOut.getState())>Constants.APPLY_STATE_SAVE){
			processHandlerService.deleteProcessInstance(officeJtgoOut.getFlowId(), true);
		}
		List<Attachment> attachments=attachmentService.getAttachments(ids[0], Constants.OFFICE_BUSINESS_TRIP_ATT);
		Set<String> attacheIdSet=new HashSet<String>();
		for (Attachment attachment : attachments) {
			attacheIdSet.add(attachment.getId());
		}
		attachmentService.deleteAttachments(attacheIdSet.toArray(new String[]{}));
		return officeJtgoOutDao.delete(ids);
	}

	@Override
	public Integer update(OfficeJtgoOut officeJtgoOut){
		return officeJtgoOutDao.update(officeJtgoOut);
	}

	@Override
	public OfficeJtgoOut getOfficeJtgoOutById(String id){
		OfficeJtgoOut officeJtgoOut=officeJtgoOutDao.getOfficeJtgoOutById(id);
		if(officeJtgoOut!=null){
			List<Attachment> attachments=attachmentService.getAttachments(id, Constants.OFFICE_BUSINESS_TRIP_ATT);
			if(CollectionUtils.isNotEmpty(attachments)){
				officeJtgoOut.setAttachments(attachments);
			}
			if(StringUtils.isNotBlank(officeJtgoOut.getId())){
				User user=userService.getUser(officeJtgoOut.getApplyUserId());
				if(user!=null){
					officeJtgoOut.setApplyUserName(user.getRealname());
				}else{
					officeJtgoOut.setApplyUserName("用户已删除");
				}
				if(StringUtils.isNotBlank(officeJtgoOut.getFlowId())){
					List<HisTask> hisTask = officeFlowService.getHisTask(officeJtgoOut.getFlowId());
					officeJtgoOut.setHisTaskList(hisTask);
				}
			}
			
		}
		return officeJtgoOut;
	}
	@Override
	public Map<String, OfficeJtgoOut> getOfficeJtgoOutMapByIds(String[] ids){
		return officeJtgoOutDao.getOfficeJtgoOutMapByIds(ids);
	}

	@Override
	public List<OfficeJtgoOut> getOfficeJtgoOutList(){
		return officeJtgoOutDao.getOfficeJtgoOutList();
	}

	@Override
	public List<OfficeJtgoOut> getOfficeJtgoOutPage(Pagination page){
		return officeJtgoOutDao.getOfficeJtgoOutPage(page);
	}

	@Override
	public List<OfficeJtgoOut> getOfficeJtgoOutByUnitIdList(String unitId){
		return officeJtgoOutDao.getOfficeJtgoOutByUnitIdList(unitId);
	}

	@Override
	public List<OfficeJtgoOut> getOfficeJtgoOutByUnitIdPage(String unitId, Pagination page){
		return officeJtgoOutDao.getOfficeJtgoOutByUnitIdPage(unitId, page);
	}

	@Override
	public List<OfficeJtgoOut> getOfficeJtgoOutByUnitIdAndStates(String unitId,
			String states, Pagination page) {
		return officeJtgoOutDao.getOfficeJtgoOutByUnitIdAndStates(unitId, states, page);
	}

	@Override
	public void save(OfficeJtgoOut officeJtgoOut, UploadFile file) {
		officeJtgoOutDao.save(officeJtgoOut);
		if(file!=null){
			Attachment attachment=new Attachment();
			attachment.setFileName(file.getFileName());
			attachment.setContentType(file.getContentType());
			attachment.setObjectId(officeJtgoOut.getId());
			attachment.setFileSize(file.getFileSize());
			attachment.setUnitId(officeJtgoOut.getUnitId());
			attachment.setObjectType(Constants.OFFICE_BUSINESS_TRIP_ATT);
			attachmentService.saveAttachment(attachment, file);
		}
		
	}
	
	@Override
	public void update(OfficeJtgoOut officeJtgoOut, UploadFile file) {
		officeJtgoOutDao.update(officeJtgoOut);
		List<Attachment> attachments=attachmentService.getAttachments(officeJtgoOut.getId(), Constants.OFFICE_BUSINESS_TRIP_ATT);
		if(file!=null){
			if(CollectionUtils.isNotEmpty(attachments)){
				Attachment attachment=attachments.get(0);
				attachment.setFileName(file.getFileName());
				attachment.setFileSize(file.getFileSize());
				attachment.setContentType(file.getContentType());
				attachmentService.updateAttachment(attachment, file, true);
			}else{
				Attachment attachment=new Attachment();
				attachment.setFileName(file.getFileName());
				attachment.setContentType(file.getContentType());
				attachment.setObjectId(officeJtgoOut.getId());
				attachment.setFileSize(file.getFileSize());
				attachment.setUnitId(officeJtgoOut.getUnitId());
				attachment.setObjectType(Constants.OFFICE_BUSINESS_TRIP_ATT);
				attachmentService.saveAttachment(attachment, file);
			}
		}
		if(StringUtils.isBlank(officeJtgoOut.getUploadContentFileInput())&&file==null){
			String[] attachmentIds = new String[attachments.size()];
			for(int i = 0; i < attachments.size(); i++){
				attachmentIds[i] = attachments.get(i).getId();
			}
			attachmentService.deleteAttachments(attachmentIds);
		}
	}

	@Override
	public void startFlow(OfficeJtgoOut officeJtgoOut, String userId,
			UploadFile file) {
		if(StringUtils.isBlank(officeJtgoOut.getId())){
			officeJtgoOut.setId(UUIDUtils.newId());
		}
		Map<String,Object> variable=new HashMap<String, Object>();
		variable.put("pass", true);
		String flowId = processHandlerService.startProcessInstance(FlowConstant.OFFICE_SUBSYSTEM, officeJtgoOut.getFlowId(), Integer.parseInt(FlowConstant.OFFICE_SUBSYSTEM+FlowConstant.OFFICE_GO_OUT), officeJtgoOut.getId(), userId, variable);
		officeFlowStepInfoService.batchUpdateByFlowId(officeJtgoOut.getFlowId(), flowId);
		officeJtgoOut.setFlowId(flowId);
		
		OfficeJtgoOut everJtgoOut=officeJtgoOutDao.getOfficeJtgoOutById(officeJtgoOut.getId());
		if(everJtgoOut!=null){
			update(officeJtgoOut, file);
		}else{
			save(officeJtgoOut, file);
		}
		//update(officeJtgoOut);
		
		officeFlowSendMsgService.startFlowSendMsg(officeJtgoOut, ConvertFlowConstants.OFFICE_JTGO_OUT);
	}

	@Override
	public Map<String, OfficeJtgoOut> getOfficeBusinessTripMapByFlowIds(
			String[] array) {
		return officeJtgoOutDao.getOfficeBusinessTripMapByFlowIds(array);
	}

	@Override
	public void deleteInvalid(String officeJtgoOutId, String userId) {
		OfficeJtgoOut officeJtgoOut=officeJtgoOutDao.getOfficeJtgoOutById(officeJtgoOutId);
		if(officeJtgoOut!=null){
			officeJtgoOut.setState(String.valueOf(Constants.APPLY_STATE_INVALID));
			officeJtgoOut.setInvalidUser(userId);
			update(officeJtgoOut);
		}
		
	}

	@Override
	public void passOfficeJtgoOut(boolean pass,
			TaskHandlerSave taskHandlerSave, String id) {
		OfficeJtgoOut officeJtgoOut=officeJtgoOutDao.getOfficeJtgoOutById(id);
		Map<String,Object> variod=new HashMap<String, Object>();
		variod.put("pass", pass);
		taskHandlerSave.setVariables(variod);
		TaskHandlerResult result;
		if(pass){
			taskHandlerSave.getComment().setTextComment("[审核通过]"+taskHandlerSave.getComment().getTextComment());
			result=taskHandlerService.completeTask(taskHandlerSave);
		}else{
			taskHandlerSave.getComment().setTextComment("[审核不通过]"+taskHandlerSave.getComment().getTextComment());
			officeJtgoOut.setState(String.valueOf(Constants.APPLY_STATE_NOPASS));
			update(officeJtgoOut);
			result=taskHandlerService.completeTask(taskHandlerSave);
		}
		if(result.getStatus()==TaskHandlerResult.STATUS_FINISH){
			if(result.getResult()==TaskHandlerResult.RESULT_PASS){
				officeJtgoOut.setState(String.valueOf(Constants.APPLY_STATE_PASS));
			}else if (result.getResult()==TaskHandlerResult.RESULT_NOT_PASS) {
				officeJtgoOut.setState(String.valueOf(Constants.APPLY_STATE_NOPASS));
			}
			update(officeJtgoOut);
		}
		officeFlowSendMsgService.completeTaskSendMsg(taskHandlerSave.getCurrentUserId(), pass, officeJtgoOut, ConvertFlowConstants.OFFICE_JTGO_OUT, result);
	}

	@Override
	public List<OfficeJtgoOut> doneAudit(String userId, boolean invalid,
			Pagination page) {
		return officeJtgoOutDao.doneAudit(userId, invalid, page);
	}
	
	@Override
	public void changeFlow(String gooutId, String userId, String modelId, String jsonResult){
		//TODO
		OfficeJtgoOut goout = this.getOfficeJtgoOutById(gooutId);
		goout.setState(String.valueOf(Constants.APPLY_STATE_NEED_AUDIT));
		
		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("pass",true);
		String flowId = processHandlerService.startProcessInstance(FlowConstant.OFFICE_SUBSYSTEM,modelId, Integer.parseInt(FlowConstant.OFFICE_SUBSYSTEM+FlowConstant.OFFICE_GO_OUT), gooutId, userId, jsonResult, variables);
		officeFlowStepInfoService.batchUpdateByFlowId(modelId, flowId);
		goout.setFlowId(flowId);
		update(goout);
		
		officeFlowSendMsgService.startFlowSendMsg(goout, ConvertFlowConstants.OFFICE_JTGO_OUT);
	}

	public void setOfficeJtgoOutDao(OfficeJtgoOutDao officeJtgoOutDao){
		this.officeJtgoOutDao = officeJtgoOutDao;
	}

	public void setAttachmentService(AttachmentService attachmentService) {
		this.attachmentService = attachmentService;
	}

	public void setProcessHandlerService(ProcessHandlerService processHandlerService) {
		this.processHandlerService = processHandlerService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public void setTaskHandlerService(TaskHandlerService taskHandlerService) {
		this.taskHandlerService = taskHandlerService;
	}

	public void setOfficeConvertFlowService(
			OfficeConvertFlowService officeConvertFlowService) {
		this.officeConvertFlowService = officeConvertFlowService;
	}

	public void setOfficeFlowSendMsgService(
			OfficeFlowSendMsgService officeFlowSendMsgService) {
		this.officeFlowSendMsgService = officeFlowSendMsgService;
	}

	public void setOfficeFlowService(OfficeFlowService officeFlowService) {
		this.officeFlowService = officeFlowService;
	}

	public void setOfficeFlowStepInfoService(
			OfficeFlowStepInfoService officeFlowStepInfoService) {
		this.officeFlowStepInfoService = officeFlowStepInfoService;
	}

}
