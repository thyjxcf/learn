package net.zdsoft.office.taskManage.action;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import com.opensymphony.xwork2.ModelDriven;

import net.zdsoft.eis.base.attachment.entity.Attachment;
import net.zdsoft.eis.base.attachment.service.AttachmentService;
import net.zdsoft.eis.base.common.entity.CustomRole;
import net.zdsoft.eis.base.common.entity.CustomRoleUser;
import net.zdsoft.eis.base.common.entity.User;
import net.zdsoft.eis.base.common.service.CustomRoleService;
import net.zdsoft.eis.base.common.service.CustomRoleUserService;
import net.zdsoft.eis.base.common.service.UserService;
import net.zdsoft.eis.base.storage.StorageFileUtils;
import net.zdsoft.eis.frame.action.PageSemesterAction;
import net.zdsoft.keelcnet.action.UploadFile;
import net.zdsoft.leadin.common.entity.BusinessTask;
import net.zdsoft.office.taskManage.entity.OfficeTaskManage;
import net.zdsoft.office.taskManage.service.OfficeTaskManageService;

public class TaskManageAction extends PageSemesterAction implements ModelDriven<OfficeTaskManage>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 9089124383908284724L;
	
	private static final String OFFICE_TASK_MANAGE_LEADER = "OFFICE_TASK_MANAGE_LEADER";
	private static final String OFFICE_TASK_MANAGE_DEALUSER = "OFFICE_TASK_MANAGE_DEALUSER";
	
	private OfficeTaskManage officeTaskManage = new OfficeTaskManage();
	private List<OfficeTaskManage> officeTaskManageList = new ArrayList<OfficeTaskManage>();
	private String queryBeginDate;
	private String queryEndDate;
	private Boolean isView;
	private Boolean canAssign;
	
	private OfficeTaskManageService officeTaskManageService;
	private AttachmentService attachmentService;
	private CustomRoleService customRoleService;
	private CustomRoleUserService customRoleUserService;
	private UserService userService;
	
	public String execute() {
		canAssign = isPracticeAdmin("task_leaders");
		return SUCCESS;
	}
	
	/**
	 * 判断其是否具有发布任务的权限
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
	
	public String assignTaskList() {//TODO
		String unitId = getLoginInfo().getUnitID();
		Map<String, User> userMap = userService.getUserWithDelMap(unitId);
		officeTaskManageList = officeTaskManageService.getOfficeTaskManageListByCondition(unitId, null, new String[]{"1", "2", "3"}, queryBeginDate, queryEndDate, getPage());
		Set<String> taskIds = new HashSet<String>();
		for(OfficeTaskManage task : officeTaskManageList){
			User user = userMap.get(task.getDealUserId());
			if(user != null){
				task.setDealUserName(user.getRealname());
			}else{
				task.setDealUserName("用户已删除");
			}
			//当任务没有发布、完成任务，或者任务发布后，有实际完成时间，或者 规定完成时间比系统现在的时间晚，没有延迟
			if(task.getState() == 1 || task.getState() == 3 
					|| (task.getState() == 2 && (task.getActualFinishTime() != null
					|| task.getCompleteTime().getTime() > new Date().getTime()))){
				task.setIsDelay(false);
				if(task.getState() == 1){
					task.setRemindNumber(0);
				}
				else{
					Date time;
					if(task.getActualFinishTime() != null){
						time = task.getActualFinishTime();
					} else {
						time = new Date();
					}
					if(task.getFirstRemindTime() !=null && task.getFirstRemindTime().getTime() > time.getTime()){
						task.setRemindNumber(0);
					} 
					else if(task.getSecondRemindTime() !=null && task.getSecondRemindTime().getTime() > time.getTime()){
						task.setRemindNumber(1);
					} 
					else{
						task.setRemindNumber(2);
					}
				}
			}
			else{
				task.setRemindNumber(2);
				task.setIsDelay(true);
			}
			
			taskIds.add(task.getId());
		}
		
		Map<String, List<Attachment>> leaderAttachmentsMap = attachmentService.getAttachmentsMap(OFFICE_TASK_MANAGE_LEADER, taskIds.toArray(new String[0]));
		Map<String, List<Attachment>> dealUserAttachmentsMap = attachmentService.getAttachmentsMap(OFFICE_TASK_MANAGE_DEALUSER, taskIds.toArray(new String[0]));
		
		for(OfficeTaskManage task : officeTaskManageList){
			List<Attachment> leaderAttachments = leaderAttachmentsMap.get(task.getId());
			List<Attachment> dealUserAttachments = dealUserAttachmentsMap.get(task.getId());
			
			if(leaderAttachments != null && leaderAttachments.size() > 0){
				task.setLeaderAttachment(leaderAttachments.get(0));
				task.setLeaderFileURL(leaderAttachments.get(0).getDownloadPath());
			}
			if(dealUserAttachments != null && dealUserAttachments.size() > 0){
				task.setDealUserAttachment(dealUserAttachments.get(0));
				task.setDealUserFileURL(dealUserAttachments.get(0).getDownloadPath());
			}
		}
		return SUCCESS;
	}
	
	public String assignTaskAdd() {
		isView = false;
		return SUCCESS;
	}	
	
	public String assignTaskEdit() {
		officeTaskManage = officeTaskManageService.getOfficeTaskManageById(officeTaskManage.getId());
		User user = userService.getUserWithDel(officeTaskManage.getDealUserId());
		if(user != null){
			officeTaskManage.setDealUserName(user.getRealname());
		}else{
			officeTaskManage.setDealUserName("用户已删除");
		}
		List<Attachment> attachments = attachmentService.getAttachments(officeTaskManage.getId(), OFFICE_TASK_MANAGE_LEADER);
		if(attachments != null && attachments.size() > 0){
			officeTaskManage.setLeaderAttachment(attachments.get(0));
			officeTaskManage.setLeaderFileName(attachments.get(0).getFileName());
			officeTaskManage.setLeaderFileURL(attachments.get(0).getDownloadPath());
		}
		return SUCCESS;
	}	
	
	public String assignTaskSave() {
		try {
			String unitId = getLoginInfo().getUnitID();
			String userId = getLoginInfo().getUser().getId(); 
			
			//任务时间、提醒时间的校验
			if(officeTaskManage.getFirstRemindTime().getTime() >= officeTaskManage.getSecondRemindTime().getTime()){
				promptMessageDto.setOperateSuccess(false);
				promptMessageDto.setErrorMessage("第一次提醒时间必须小于第二次提醒时间！");
				return SUCCESS;
			}
			if(officeTaskManage.getSecondRemindTime().getTime() >= officeTaskManage.getCompleteTime().getTime()){
				promptMessageDto.setOperateSuccess(false);
				promptMessageDto.setErrorMessage("提醒时间必须小于规定完成时间！");
				return SUCCESS;
			}
			
			//设置附件文件的相关属性
			String[] zipFile = new String[]{"7z", "zip", "rar"};
			String[] doc = new String[] {"rtf","doc","docx","xls","xlsx","csv","ppt","pptx","pdf"};
			String[] video = new String[] {"avi","mpg","wmv","3gp","mov","mp4","asf","asx","flv","wmv9","rm","rmvb"};
			String[] picture = new String[] {"gif","bmp","jpg","jpeg","png"};
			String[] fileAccept = concatAll(doc, zipFile, video, picture);
			
			UploadFile file = StorageFileUtils.handleFile(fileAccept, 200*1024);
			
			if(StringUtils.isBlank(officeTaskManage.getId())){
				officeTaskManage.setUnitId(unitId);
				officeTaskManage.setCreateUserId(userId);
				officeTaskManage.setCreateTime(new Date());
				officeTaskManageService.save(officeTaskManage);
			} else {
				officeTaskManageService.update(officeTaskManage);
			}
			
			if(file != null){
				//删除原有的授课计划附件
				deleteFile(officeTaskManage.getId(), OFFICE_TASK_MANAGE_LEADER);
				 
				Attachment attachment = new Attachment();
				attachment.setFileName(file.getFileName());
				attachment.setContentType(file.getContentType());
				attachment.setFileSize(file.getFileSize());
				attachment.setUnitId(getUnitId());
				attachment.setObjectId(officeTaskManage.getId());
				attachment.setConStatus(BusinessTask.TASK_STATUS_NOT_NEED_HAND);
				attachment.setObjectType(OFFICE_TASK_MANAGE_LEADER);
				attachmentService.saveAttachment(attachment, file);
			}
			//选择清空附件
			if(StringUtils.isBlank(officeTaskManage.getLeaderFileName())){
				deleteFile(officeTaskManage.getId(), OFFICE_TASK_MANAGE_LEADER);
				officeTaskManage.setHasAttach(0);
			} else {
				officeTaskManage.setHasAttach(1);
			}
			
			if(officeTaskManage.getState() == 1){
				promptMessageDto.setPromptMessage("保存成功！");
			} else {
				promptMessageDto.setPromptMessage("提交成功！");
			}
			promptMessageDto.setOperateSuccess(true);
		} catch (Exception e) {
			promptMessageDto.setOperateSuccess(false);
			promptMessageDto.setPromptMessage(e.getMessage());
		}
		return SUCCESS;
	}	
	
	public String assignTaskDelete() {
		try {
			deleteFile(officeTaskManage.getId(), OFFICE_TASK_MANAGE_LEADER);
			officeTaskManageService.delete(new String[]{officeTaskManage.getId()});
			
			promptMessageDto.setOperateSuccess(true);
			promptMessageDto.setPromptMessage("撤销任务成功！");
		} catch (Exception e) {
			promptMessageDto.setOperateSuccess(false);
			promptMessageDto.setPromptMessage("操作异常，请重新操作！");
		}
		return SUCCESS;
	}
	
	public String dealTaskList(){//TODO
		String unitId = getLoginInfo().getUnitID();
		String userId = getLoginInfo().getUser().getId();
		officeTaskManageList = officeTaskManageService.getOfficeTaskManageListByCondition(unitId, userId, new String[]{"2", "3"}, queryBeginDate, queryEndDate, getPage());
		Set<String> taskIds = new HashSet<String>();
		for(OfficeTaskManage task : officeTaskManageList){
			//当任务没有发布、完成任务，或者任务发布后，有实际完成时间，或者 规定完成时间比系统现在的时间晚，没有延迟
			if(task.getState() == 1 || task.getState() == 3 
					|| (task.getState() == 2 && (task.getActualFinishTime() != null
					|| task.getCompleteTime().getTime() > new Date().getTime()))){
				task.setIsDelay(false);
				if(task.getState() == 1){
					task.setRemindNumber(0);
				}
				else{
					Date time;
					if(task.getActualFinishTime() != null){
						time = task.getActualFinishTime();
					} else {
						time = new Date();
					}
					if(task.getFirstRemindTime() !=null && task.getFirstRemindTime().getTime() > time.getTime()){
						task.setRemindNumber(0);
					} 
					else if(task.getSecondRemindTime() !=null && task.getSecondRemindTime().getTime() > time.getTime()){
						task.setRemindNumber(1);
					} 
					else{
						task.setRemindNumber(2);
					}
				}
			}
			else{
				task.setRemindNumber(2);
				task.setIsDelay(true);
			}
			taskIds.add(task.getId());
		}
		
		Map<String, List<Attachment>> leaderAttachmentsMap = attachmentService.getAttachmentsMap(OFFICE_TASK_MANAGE_LEADER, taskIds.toArray(new String[0]));
		Map<String, List<Attachment>> dealUserAttachmentsMap = attachmentService.getAttachmentsMap(OFFICE_TASK_MANAGE_DEALUSER, taskIds.toArray(new String[0]));
		
		for(OfficeTaskManage task : officeTaskManageList){
			List<Attachment> leaderAttachments = leaderAttachmentsMap.get(task.getId());
			List<Attachment> dealUserAttachments = dealUserAttachmentsMap.get(task.getId());
			
			if(leaderAttachments != null && leaderAttachments.size() > 0){
				task.setLeaderAttachment(leaderAttachments.get(0));
				task.setLeaderFileURL(leaderAttachments.get(0).getDownloadPath());
			}
			if(dealUserAttachments != null && dealUserAttachments.size() > 0){
				task.setDealUserAttachment(dealUserAttachments.get(0));
				task.setDealUserFileURL(dealUserAttachments.get(0).getDownloadPath());
			}
		}
		return SUCCESS;
	}

	public String dealTaskEdit(){
		officeTaskManage = officeTaskManageService.getOfficeTaskManageById(officeTaskManage.getId());
		User user = userService.getUserWithDel(officeTaskManage.getDealUserId());
		if(user != null){
			officeTaskManage.setDealUserName(user.getRealname());
		}else{
			officeTaskManage.setDealUserName("用户已删除");
		}

		List<Attachment> leaderAttachments = attachmentService.getAttachments(officeTaskManage.getId(), OFFICE_TASK_MANAGE_LEADER);
		List<Attachment> dealUserAttachments = attachmentService.getAttachments(officeTaskManage.getId(), OFFICE_TASK_MANAGE_DEALUSER);
		
		if(leaderAttachments != null && leaderAttachments.size() > 0){
			officeTaskManage.setLeaderAttachment(leaderAttachments.get(0));
			officeTaskManage.setLeaderFileName(leaderAttachments.get(0).getFileName());
			officeTaskManage.setLeaderFileURL(leaderAttachments.get(0).getDownloadPath());
		}
		if(dealUserAttachments != null && dealUserAttachments.size() > 0){
			officeTaskManage.setDealUserAttachment(dealUserAttachments.get(0));
			officeTaskManage.setDealUserFileName(dealUserAttachments.get(0).getFileName());
			officeTaskManage.setDealUserFileURL(dealUserAttachments.get(0).getDownloadPath());
		}
		return SUCCESS;
	}

	public String dealTaskSave(){
		try {
			//设置附件文件的相关属性
			String[] zipFile = new String[]{"7z", "zip", "rar"};
			String[] doc = new String[] {"rtf","doc","docx","xls","xlsx","csv","ppt","pptx","pdf"};
			String[] video = new String[] {"avi","mpg","wmv","3gp","mov","mp4","asf","asx","flv","wmv9","rm","rmvb"};
			String[] picture = new String[] {"gif","bmp","jpg","jpeg","png"};
			String[] fileAccept = concatAll(doc, zipFile, video, picture);
			
			UploadFile file = StorageFileUtils.handleFile(fileAccept, 200*1024);
			if(file != null){
				//删除原有的授课计划附件
				deleteFile(officeTaskManage.getId(), OFFICE_TASK_MANAGE_DEALUSER);
				 
				Attachment attachment = new Attachment();
				attachment.setFileName(file.getFileName());
				attachment.setContentType(file.getContentType());
				attachment.setFileSize(file.getFileSize());
				attachment.setUnitId(getUnitId());
				attachment.setObjectId(officeTaskManage.getId());
				attachment.setConStatus(BusinessTask.TASK_STATUS_NOT_NEED_HAND);
				attachment.setObjectType(OFFICE_TASK_MANAGE_DEALUSER);
				attachmentService.saveAttachment(attachment, file);
			}
			//选择清空附件
			if(StringUtils.isBlank(officeTaskManage.getDealUserFileName())){
				deleteFile(officeTaskManage.getId(), OFFICE_TASK_MANAGE_DEALUSER);
				officeTaskManage.setHasSubmitAttach(0);
			} else {
				officeTaskManage.setHasSubmitAttach(1);
			}
			
			officeTaskManage.setActualFinishTime(new Date());
			officeTaskManage.setState(3);
			officeTaskManageService.update(officeTaskManage);
			
			promptMessageDto.setOperateSuccess(true);
			promptMessageDto.setPromptMessage("提交任务成功！");
		} catch (Exception e) {
			promptMessageDto.setOperateSuccess(false);
			promptMessageDto.setPromptMessage(e.getMessage());
		}
		return SUCCESS;
	}
	
	public void deleteFile(String objectId, String objectType){
		List<Attachment> attachments = attachmentService
				.getAttachments(objectId, objectType);
		String[] attachmentIds = new String[attachments.size()];
		for(int i = 0; i < attachments.size(); i++){
			attachmentIds[i] = attachments.get(i).getId();
		}
		attachmentService.deleteAttachments(attachmentIds);
	}
	
	/**
	 * 数组合并
	 * @param first
	 * @param rest
	 * @return
	 */
	public static <T> T[] concatAll(T[] first, T[]... rest) {
		int totalLength = first.length;
		for (T[] array : rest) {
			totalLength += array.length;
		}
		T[] result = Arrays.copyOf(first, totalLength);
		int offset = first.length;
		for (T[] array : rest) {
			System.arraycopy(array, 0, result, offset, array.length);
			offset += array.length;
		}
		return result;
	}
	
	@Override
	public OfficeTaskManage getModel() {
		return officeTaskManage;
	}

	public OfficeTaskManage getOfficeTaskManage() {
		return officeTaskManage;
	}

	public void setOfficeTaskManage(OfficeTaskManage officeTaskManage) {
		this.officeTaskManage = officeTaskManage;
	}

	public String getQueryBeginDate() {
		return queryBeginDate;
	}

	public void setQueryBeginDate(String queryBeginDate) {
		this.queryBeginDate = queryBeginDate;
	}

	public String getQueryEndDate() {
		return queryEndDate;
	}

	public void setQueryEndDate(String queryEndDate) {
		this.queryEndDate = queryEndDate;
	}

	public List<OfficeTaskManage> getOfficeTaskManageList() {
		return officeTaskManageList;
	}

	public void setOfficeTaskManageService(
			OfficeTaskManageService officeTaskManageService) {
		this.officeTaskManageService = officeTaskManageService;
	}

	public Boolean getIsView() {
		return isView;
	}

	public void setIsView(Boolean isView) {
		this.isView = isView;
	}

	public void setAttachmentService(AttachmentService attachmentService) {
		this.attachmentService = attachmentService;
	}

	public Boolean getCanAssign() {
		return canAssign;
	}

	public void setCanAssign(Boolean canAssign) {
		this.canAssign = canAssign;
	}

	public void setCustomRoleService(CustomRoleService customRoleService) {
		this.customRoleService = customRoleService;
	}

	public void setCustomRoleUserService(CustomRoleUserService customRoleUserService) {
		this.customRoleUserService = customRoleUserService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

}
