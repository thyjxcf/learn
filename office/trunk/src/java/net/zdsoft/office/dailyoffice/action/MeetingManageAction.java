package net.zdsoft.office.dailyoffice.action;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.zdsoft.eis.base.attachment.entity.Attachment;
import net.zdsoft.eis.base.attachment.service.AttachmentService;
import net.zdsoft.eis.base.common.entity.CustomRole;
import net.zdsoft.eis.base.common.entity.CustomRoleUser;
import net.zdsoft.eis.base.common.entity.Dept;
import net.zdsoft.eis.base.common.entity.User;
import net.zdsoft.eis.base.common.service.CustomRoleService;
import net.zdsoft.eis.base.common.service.CustomRoleUserService;
import net.zdsoft.eis.base.common.service.DeptService;
import net.zdsoft.eis.base.common.service.UserService;
import net.zdsoft.eis.base.constant.BaseConstant;
import net.zdsoft.eis.base.storage.StorageFileUtils;
import net.zdsoft.eis.frame.action.PageAction;
import net.zdsoft.keel.util.UUIDUtils;
import net.zdsoft.keelcnet.action.UploadFile;
import net.zdsoft.leadin.common.entity.BusinessTask;
import net.zdsoft.office.dailyoffice.entity.OfficeMeetingApply;
import net.zdsoft.office.dailyoffice.entity.OfficeMeetingAudit;
import net.zdsoft.office.dailyoffice.entity.OfficeMeetingUser;
import net.zdsoft.office.dailyoffice.service.OfficeMeetingApplyService;
import net.zdsoft.office.dailyoffice.service.OfficeMeetingAuditService;
import net.zdsoft.office.dailyoffice.service.OfficeMeetingUserService;
import net.zdsoft.office.msgcenter.entity.OfficeMsgSending;
import net.zdsoft.office.msgcenter.service.OfficeMsgSendingService;
import net.zdsoft.office.util.Constants;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import com.opensymphony.xwork2.ModelDriven;

/**
 * 
 * @author laiy
 *
 * @version 创建时间：2015-1-22 上午10:54:11
 */
public class MeetingManageAction extends PageAction implements ModelDriven<OfficeMeetingApply>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -128301448916106979L;

	private OfficeMeetingApply officeMeetingApply = new OfficeMeetingApply();
	
	private List<OfficeMeetingApply> officeMeetingApplyList = new ArrayList<OfficeMeetingApply>();
	
	private OfficeMeetingApplyService officeMeetingApplyService;
	
	private OfficeMeetingAuditService officeMeetingAuditService;
	
	private OfficeMeetingUserService officeMeetingUserService;
	
	private AttachmentService attachmentService;
	
	private DeptService deptService;
	
	private UserService userService;
	
	private String queryName;
	
	private String queryState;
	
	private Date queryBeginDate;
	
	private Date queryEndDate;
	
	private String classStyle;
	
	private String readonlyStyle;
	
	private String doAction;
	
	private CustomRoleService customRoleService;
	private CustomRoleUserService customRoleUserService;
	private OfficeMsgSendingService officeMsgSendingService;
	
	public String execute() {
		return SUCCESS;
	}
	
	public String meetingList() {
		String[] querystate = null;
		if("apply".equals(doAction)){
			if(StringUtils.isNotBlank(queryState)){
				querystate = new String[]{queryState};
			}else{
				querystate = null;
			}
			officeMeetingApplyList = officeMeetingApplyService
					.getOfficeMeetingApplyList(getUnitId(), getLoginUser().getUserId(), queryName, querystate, queryBeginDate, queryEndDate, getPage());
		}else{
			if(StringUtils.isNotBlank(queryState)){
				querystate = new String[]{queryState};
			}else{//审核时不显示未提交记录
				querystate = new String[]{"1","2","3"};
			}
			officeMeetingApplyList = officeMeetingApplyService
					.getOfficeMeetingApplyList(getUnitId(), null, queryName, querystate, queryBeginDate, queryEndDate, getPage());
		}
		if(StringUtils.isNotBlank(queryState)){
			querystate = queryState.split(",");
		}
		//显示使用时间设置
		officeMeetingApplyList = officeMeetingApplyService.setMeetingApplyTimeStr(officeMeetingApplyList);
		return SUCCESS;
	}
	
	public String meetingEdit() {
		//设置显示样式，点击“修改”可修改，点击“查看”不可以修改
		if("true".equals(readonlyStyle)){
			classStyle = "input-readonly";
		}else{
			classStyle = "";
		}
		String applyId = officeMeetingApply.getId();
		//编辑
		if(StringUtils.isNotBlank(applyId)){
			officeMeetingApply = officeMeetingApplyService
					.getOfficeMeetingApplyById(applyId);
			Set<String> alluserids = new HashSet<String>();
			if(StringUtils.isNotBlank(officeMeetingApply.getHostUserId())){
				alluserids.add(officeMeetingApply.getHostUserId());
			}
			List<OfficeMeetingUser> userList = officeMeetingUserService
					.getOfficeMeetingUserListByApplyId(applyId);
			for(OfficeMeetingUser user : userList){
				alluserids.add(user.getUserId());
			}
			//获取包括主持人、通知人员的userMap
			Map<String, User> userMap = userService
					.getUserWithDelMap(alluserids.toArray(new String[alluserids.size()]));
			if(StringUtils.isNotBlank(officeMeetingApply.getHostUserId())){
				if(userMap.containsKey(officeMeetingApply.getHostUserId())){
					officeMeetingApply.setHostUserName(userMap
							.get(officeMeetingApply.getHostUserId()).getRealname());
				}else{
					officeMeetingApply.setHostUserName("用户已删除");
				}
			}
			String meetingUserIds = officeMeetingApply.getMeetingUserIds();
			String meetingUserNames = officeMeetingApply.getMeetingUserNames();
			for(OfficeMeetingUser user : userList){
				if(userMap.containsKey(user.getUserId())){
					User meetinguser = userMap.get(user.getUserId());
					if(StringUtils.isBlank(meetingUserIds)){
						meetingUserIds = meetinguser.getId();
						meetingUserNames = meetinguser.getRealname();
					}else{
						meetingUserIds += "," + meetinguser.getId();
						meetingUserNames += "," + meetinguser.getRealname();
					}
				}
			}
			officeMeetingApply.setMeetingUserIds(meetingUserIds);
			officeMeetingApply.setMeetingUserNames(meetingUserNames);
			//部门名称
			String[] deptIds = officeMeetingApply.getDeptIds().split(",");
			Map<String, Dept> deptMap = deptService.getDeptMap(deptIds);
			String deptNames = "";
			for(int i = 0; i < deptIds.length; i++){
				if(deptMap.containsKey(deptIds[i])){
					if(StringUtils.isBlank(deptNames)){
						deptNames = deptMap.get(deptIds[i]).getDeptname();
					}else{
						deptNames += "," + deptMap.get(deptIds[i]).getDeptname();
					}
				}
			}
			officeMeetingApply.setDeptNames(deptNames);
			//获取附件信息
			List<Attachment> attachments = attachmentService
					.getAttachments(officeMeetingApply.getId(), OfficeMeetingApply.OFFICE_MEETING_ATTACHMENT);
			if(attachments!=null&&attachments.size()>0){
				officeMeetingApply.setFileName(attachments.get(0).getFileName());
				officeMeetingApply.setFileUrl(attachments.get(0).getDownloadPath());
			}
			//获取对应的审核信息
			OfficeMeetingAudit audit = officeMeetingAuditService.getOfficeMeetingAuditByApplyId(officeMeetingApply.getId());
			officeMeetingApply.setOpinion(audit.getOpinion());
			//使用时间设置
			officeMeetingApplyService.setMeetingApplyTimeStr(new ArrayList<OfficeMeetingApply>(){{add(officeMeetingApply);}});
		}
		return SUCCESS;
	}
	
	public String save() {
		try{
			if(officeMeetingApply.getAuditState().equals(OfficeMeetingAudit.STATUS_AUDIT_CHECKING)){
				//当此保存并提交时
				officeMeetingApply.setApplyDate(new Date());
			}
			officeMeetingApply.setState(OfficeMeetingApply.STATUS_APPLY_REJECT);
			
			if(StringUtils.isNotBlank(officeMeetingApply.getId())){
				officeMeetingApplyService.update(officeMeetingApply);
			}else{
				officeMeetingApply.setId(UUIDUtils.newId());
				officeMeetingApply.setUnitId(getUnitId());
				officeMeetingApply.setApplyUserId(getLoginUser().getUserId());
				officeMeetingApply = officeMeetingApplyService.save(officeMeetingApply);
			}
			//设置附件文件的相关属性
			UploadFile file = StorageFileUtils.handleFile(new String[] {"txt","rtf","doc","docx","xls","xlsx","csv","ppt","pptx","pdf"},Integer.parseInt(systemIniService.getValue(Constants.FILE_INIID))*1024);
			if(file != null){
				//删除原有的授课计划附件
				deleteFile(officeMeetingApply.getId());
				 
				Attachment attachment = new Attachment();
				attachment.setFileName(file.getFileName());
				attachment.setContentType(file.getContentType());
				attachment.setFileSize(file.getFileSize());
				attachment.setUnitId(getUnitId());
				attachment.setObjectId(officeMeetingApply.getId());
				attachment.setConStatus(BusinessTask.TASK_STATUS_NOT_NEED_HAND);
				attachment.setObjectType(OfficeMeetingApply.OFFICE_MEETING_ATTACHMENT);
				attachmentService.saveAttachment(attachment, file);
			}
			//选择清空附件
			if(StringUtils.isBlank(officeMeetingApply.getFileName())){
				deleteFile(officeMeetingApply.getId());
			}
			
			//将通知人员ID批量新增入office_meeting_user表中
			officeMeetingUserService.batchsave(officeMeetingApply.getId(), 
					officeMeetingApply.getMeetingUserIds().split(","));
			//同步将信息保存到office_meeting_audit表中
			OfficeMeetingAudit meetingAudit = officeMeetingAuditService
					.getOfficeMeetingAuditByApplyId(officeMeetingApply.getId());
			if(StringUtils.isBlank(meetingAudit.getId())){
				meetingAudit.setUnitId(getLoginUser().getUserId());
				meetingAudit.setApplyId(officeMeetingApply.getId());
				meetingAudit.setState(officeMeetingApply.getAuditState());
				officeMeetingAuditService.save(meetingAudit);
			}else{
				meetingAudit.setState(officeMeetingApply.getAuditState());
				officeMeetingAuditService.update(meetingAudit);
			}
			//-----------------上传或修改附件-------------------
			
			promptMessageDto.setOperateSuccess(true);
			promptMessageDto.setPromptMessage("操作成功!");
		}catch(Exception e){
			promptMessageDto.setOperateSuccess(false);
			promptMessageDto.setPromptMessage("操作失败："+e.getMessage());
		}
		return SUCCESS;
	}

	public void deleteFile(String applyId){
		List<Attachment> attachments = attachmentService
				.getAttachments(applyId, OfficeMeetingApply.OFFICE_MEETING_ATTACHMENT);
		String[] attachmentIds = new String[attachments.size()];
		for(int i = 0; i < attachments.size(); i++){
			attachmentIds[i] = attachments.get(i).getId();
		}
		attachmentService.deleteAttachments(attachmentIds);
	}
	
	public String submit() {
		try{
			officeMeetingApply = officeMeetingApplyService
					.getOfficeMeetingApplyById(officeMeetingApply.getId());
			officeMeetingApply.setApplyDate(new Date());
			officeMeetingApplyService.update(officeMeetingApply);
			OfficeMeetingAudit meetingAudit = officeMeetingAuditService
					.getOfficeMeetingAuditByApplyId(officeMeetingApply.getId());
			meetingAudit.setState(OfficeMeetingAudit.STATUS_AUDIT_CHECKING);
			officeMeetingAuditService.update(meetingAudit);
			promptMessageDto.setOperateSuccess(true);
			promptMessageDto.setPromptMessage("提交成功!");
		}catch(Exception e){
			promptMessageDto.setOperateSuccess(false);
			promptMessageDto.setPromptMessage("提交失败:"+e.getMessage());
		}
		return SUCCESS;
	}
	
	public String delete() {
		try{
			String applyId = officeMeetingApply.getId();
			//删除office_meeting_user表中信息
			List<OfficeMeetingUser> userList = officeMeetingUserService
					.getOfficeMeetingUserListByApplyId(applyId);
			Set<String> userIds = new HashSet<String>();
			for(OfficeMeetingUser user : userList){
				userIds.add(user.getId());
			}
			officeMeetingUserService.delete(userIds.toArray(new String[userIds.size()]));
			//删除sys_attachment表中信息
			List<Attachment> attachments = attachmentService
					.getAttachments(officeMeetingApply.getId(), OfficeMeetingApply.OFFICE_MEETING_ATTACHMENT);
			String[] attachmentIds = new String[attachments.size()];
			for(int i = 0; i < attachments.size(); i++){
				attachmentIds[i] = attachments.get(i).getId();
			}
			attachmentService.deleteAttachments(attachmentIds);
			//删除office_meeting_apply表中信息
			officeMeetingApplyService.delete(new String[]{applyId});
			//删除office_meeting_audit表中信息
			OfficeMeetingAudit meetingAudit = officeMeetingAuditService
				.getOfficeMeetingAuditByApplyId(applyId);
			officeMeetingAuditService.delete(new String[]{meetingAudit.getId()});
			promptMessageDto.setOperateSuccess(true);
			promptMessageDto.setPromptMessage("删除成功!");
		}catch(Exception e){
			promptMessageDto.setOperateSuccess(false);
			promptMessageDto.setPromptMessage("删除失败："+e.getMessage());
		}
		return SUCCESS;
	}
	
	//审核结果提交
	public String auditSave(){
		try{
			String applyId = officeMeetingApply.getId();
			OfficeMeetingAudit audit = officeMeetingAuditService.getOfficeMeetingAuditByApplyId(applyId);
			audit.setOpinion(officeMeetingApply.getOpinion());
			audit.setState(officeMeetingApply.getAuditState());
			audit.setAuditUserId(getLoginUser().getUserId());
			audit.setAuditDate(new Date());
			officeMeetingAuditService.update(audit);
			
			OfficeMeetingApply apply = officeMeetingApplyService.getOfficeMeetingApplyById(applyId);
			if(officeMeetingApply.getAuditState().equals(OfficeMeetingAudit.STATUS_AUDIT_PASS)){
				apply.setState(OfficeMeetingApply.STATUS_APPLY_PASS);
			}else{
				apply.setState(OfficeMeetingApply.STATUS_APPLY_REJECT);
			}
			apply.setAuditDate(audit.getAuditDate());
			officeMeetingApplyService.update(apply);
			
			if(officeMeetingApply.getAuditState().equals(OfficeMeetingAudit.STATUS_AUDIT_PASS)){
				this.sendMsg(apply);
			}
			
			promptMessageDto.setOperateSuccess(true);
			promptMessageDto.setPromptMessage("审核成功!");
		}catch(Exception e){
			promptMessageDto.setOperateSuccess(false);
			promptMessageDto.setPromptMessage("审核失败："+e.getMessage());
		}
		return SUCCESS;
	}
	
	public void sendMsg(OfficeMeetingApply apply){//TODO
		StringBuffer sbf = new StringBuffer();
		//申请人
		sbf.append(apply.getApplyUserId());
		//主持人
		if(StringUtils.isNotBlank(apply.getHostUserId()) && !apply.getApplyUserId().equals(apply.getHostUserId())){
			sbf.append(",").append(apply.getHostUserId());
		}
		//与会人员
		List<OfficeMeetingUser> officeMeetingUsers = officeMeetingUserService.getOfficeMeetingUserListByApplyId(apply.getId());
		for(OfficeMeetingUser officeMeetingUser:officeMeetingUsers){
			if(!officeMeetingUser.getUserId().equals(apply.getHostUserId()) && !officeMeetingUser.getUserId().equals(apply.getApplyUserId())){
				sbf.append(",").append(officeMeetingUser.getUserId());
			}
		}
		
		OfficeMsgSending officeMsgSending = new OfficeMsgSending();
		officeMsgSending.setCreateUserId(getLoginInfo().getUser().getId());
		officeMsgSending.setTitle("您有一条会议通知，请及时参加");
		
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm");
		StringBuffer content = new StringBuffer("");
		content.append("会议将从").append(sdf.format(apply.getStartTime()));
		content.append("到").append(sdf.format(apply.getEndTime())).append("，");
		content.append("在").append(apply.getMeetingPlace()).append("召开。");
		content.append("内容详情：").append(apply.getMeetingContent());
		
		officeMsgSending.setContent(content.toString());
		officeMsgSending.setSimpleContent(content.toString());
		officeMsgSending.setUnitId(getUnitId());
		officeMsgSending.setUserIds(sbf.toString());
		officeMsgSending.setSendUserName(getLoginInfo().getUser().getRealname());
		officeMsgSending.setState(Constants.MSG_STATE_SEND);
		officeMsgSending.setIsNeedsms(false);
		officeMsgSending.setMsgType(BaseConstant.MSG_TYPE_MEETING);
		officeMsgSendingService.save(officeMsgSending, null, null);
	}
	
	/**
	 * 判断当前登陆用户是否有 会议管理审核权限
	 * @return
	 */
	public boolean isRightOfMeetingAudit(){
		String str = "office_meetingmanage";
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

	public OfficeMeetingApply getOfficeMeetingApply() {
		return officeMeetingApply;
	}

	public void setOfficeMeetingApply(OfficeMeetingApply officeMeetingApply) {
		this.officeMeetingApply = officeMeetingApply;
	}

	public void setOfficeMeetingApplyService(
			OfficeMeetingApplyService officeMeetingApplyService) {
		this.officeMeetingApplyService = officeMeetingApplyService;
	}

	public void setOfficeMeetingUserService(
			OfficeMeetingUserService officeMeetingUserService) {
		this.officeMeetingUserService = officeMeetingUserService;
	}

	public void setDeptService(DeptService deptService) {
		this.deptService = deptService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public List<OfficeMeetingApply> getOfficeMeetingApplyList() {
		return officeMeetingApplyList;
	}

	public String getQueryName() {
		return queryName;
	}
	
	public String getQueryState() {
		return queryState;
	}

	public void setQueryState(String queryState) {
		this.queryState = queryState;
	}

	public void setQueryName(String queryName) {
		this.queryName = queryName;
	}

	public Date getQueryBeginDate() {
		return queryBeginDate;
	}

	public void setQueryBeginDate(Date queryBeginDate) {
		this.queryBeginDate = queryBeginDate;
	}

	public Date getQueryEndDate() {
		return queryEndDate;
	}

	public void setQueryEndDate(Date queryEndDate) {
		this.queryEndDate = queryEndDate;
	}

	public void setOfficeMeetingAuditService(
			OfficeMeetingAuditService officeMeetingAuditService) {
		this.officeMeetingAuditService = officeMeetingAuditService;
	}

	public void setAttachmentService(AttachmentService attachmentService) {
		this.attachmentService = attachmentService;
	}

	public String getClassStyle() {
		return classStyle;
	}

	public void setClassStyle(String classStyle) {
		this.classStyle = classStyle;
	}

	public String getReadonlyStyle() {
		return readonlyStyle;
	}

	public void setReadonlyStyle(String readonlyStyle) {
		this.readonlyStyle = readonlyStyle;
	}

	public String getDoAction() {
		return doAction;
	}

	public void setDoAction(String doAction) {
		this.doAction = doAction;
	}

	public void setCustomRoleService(CustomRoleService customRoleService) {
		this.customRoleService = customRoleService;
	}

	public void setCustomRoleUserService(CustomRoleUserService customRoleUserService) {
		this.customRoleUserService = customRoleUserService;
	}

	public void setOfficeMsgSendingService(
			OfficeMsgSendingService officeMsgSendingService) {
		this.officeMsgSendingService = officeMsgSendingService;
	}

	@Override
	public OfficeMeetingApply getModel() {
		return officeMeetingApply;
	}

}
