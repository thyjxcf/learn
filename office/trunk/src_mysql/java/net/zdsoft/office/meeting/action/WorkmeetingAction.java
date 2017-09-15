package net.zdsoft.office.meeting.action;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.zdsoft.eis.base.attachment.entity.Attachment;
import net.zdsoft.eis.base.attachment.service.AttachmentService;
import net.zdsoft.eis.base.common.entity.Dept;
import net.zdsoft.eis.base.common.entity.Unit;
import net.zdsoft.eis.base.common.service.DeptService;
import net.zdsoft.eis.base.common.service.UserService;
import net.zdsoft.eis.base.storage.StorageFileUtils;
import net.zdsoft.eis.frame.action.PageAction;
import net.zdsoft.keelcnet.action.UploadFile;
import net.zdsoft.leadin.common.entity.BusinessTask;
import net.zdsoft.leadin.util.UUIDGenerator;
import net.zdsoft.office.meeting.entity.MeetingsInfoCondition;
import net.zdsoft.office.meeting.entity.OfficeDeptLeader;
import net.zdsoft.office.meeting.entity.OfficeWorkMeeting;
import net.zdsoft.office.meeting.entity.OfficeWorkMeetingConfirm;
import net.zdsoft.office.meeting.entity.OfficeWorkMeetingMinutes;
import net.zdsoft.office.meeting.service.OfficeDeptLeaderService;
import net.zdsoft.office.meeting.service.OfficeWorkMeetingAttendService;
import net.zdsoft.office.meeting.service.OfficeWorkMeetingConfirmService;
import net.zdsoft.office.meeting.service.OfficeWorkMeetingMinutesService;
import net.zdsoft.office.meeting.service.OfficeWorkMeetingService;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.lang3.StringUtils;



public class WorkmeetingAction extends PageAction{
	private static final long serialVersionUID = 2153096055738769520L;
	
	public String show;//1,待参加会议 2,已参加会议
	public String meetingName;//会议名称;
	public Date startTime;
	public Date endTime;
	public List<OfficeWorkMeeting> meetinglist;
	public OfficeWorkMeeting meeting=new OfficeWorkMeeting();
	public OfficeWorkMeetingService officeWorkMeetingService;
	public OfficeWorkMeetingConfirmService officeWorkMeetingConfirmService;
	public OfficeWorkMeetingAttendService officeWorkMeetingAttendService;
	
	public String meetingId;
	public String state;//审核状态
	public String backState;
	public String submitState;
	public String attendRemark;
	public String transferId;//转交对象Id
	public String minutesId;

	
	private OfficeDeptLeaderService officeDeptLeaderService;
	private DeptService deptService; 
	private UserService userService;
	private List<OfficeDeptLeader> leaderList=new ArrayList<OfficeDeptLeader>();
	private List<OfficeWorkMeeting> meetingList=new ArrayList<OfficeWorkMeeting>();
	private String userId;//负责人 权限设置
	private String deptId;
	private String leadId;
	private Integer fileSize = 5;//单个附件大小
	private Integer fileCountSize = 50;//总附件大小
	private String[] removeAttachment;//已删除附件的id
	private AttachmentService attachmentService;
	private String readonlyStyle;
	private String fileName;//文件名
	private OfficeWorkMeetingMinutesService officeWorkMeetingMinutesService; 
	private OfficeWorkMeetingMinutes officeWorkMeetingMinutes=new OfficeWorkMeetingMinutes(); 
	private boolean hasEdit;
	

	public String workmeetingAdmin(){
		if(getLoginInfo().getUnitClass() == Unit.UNIT_CLASS_SCHOOL){
			hasEdit = getLoginInfo().validateAllModelOpera(70019,"WORK_MEETING_AUDIT");
		}else if(getLoginInfo().getUnitClass() == Unit.UNIT_CLASS_EDU){
			hasEdit = getLoginInfo().validateAllModelOpera(70519,"WORK_MEETING_AUDIT");
		}
		return SUCCESS;
	}
	//我的会议
	public String myMeeting(){
		if(StringUtils.isBlank(show)){
			show = "1";
		}
		return SUCCESS;
	}
	
	public String myMeetingList(){
		MeetingsInfoCondition mic = new MeetingsInfoCondition();
		mic.setUserId(getLoginInfo().getUser().getId());
		mic.setUnitId(getLoginInfo().getUnitID());
		mic.setMeetingName(meetingName);
		mic.setStartDate(startTime);
		mic.setEndDate(endTime);
		if(StringUtils.isBlank(show))
			show = "1";
		mic.setIsEnd(show);
		meetinglist = officeWorkMeetingService.getOfficeWorkMeetingByConditionList(mic,getPage());
		return SUCCESS;
	}
	
	public String minutesView(){
		//TODO
		if(StringUtils.isNotBlank(minutesId)){
			officeWorkMeetingMinutes = officeWorkMeetingMinutesService.getOfficeWorkMeetingMinutesById(minutesId);
			meeting = officeWorkMeetingService.getOfficeWorkMeetingById(officeWorkMeetingMinutes.getMeetingId());
		}
		return SUCCESS;
	}
	
	public String yesMeeting(){
		try{
			if(StringUtils.isNotBlank(meetingId)){
				OfficeWorkMeetingConfirm meetingsConfirm = new OfficeWorkMeetingConfirm();
				meetingsConfirm.setMeetingId(meetingId);
				meetingsConfirm.setAttendUserId(getLoginInfo().getUser().getId());
				meetingsConfirm.setAttendType(OfficeWorkMeetingConfirm.TYPE_ATTEND);
				officeWorkMeetingConfirmService.save(meetingsConfirm);
			}else{
				jsonError = "数据有误！";
			}
		}catch (Exception e) {
			jsonError = "操作失败！";
		}
		return SUCCESS;
	}
	
	public String noMeeting(){
		try{
			if(StringUtils.isNotBlank(meetingId)){
				OfficeWorkMeetingConfirm meetingsConfirm = new OfficeWorkMeetingConfirm();
				meetingsConfirm.setMeetingId(meetingId);
				meetingsConfirm.setAttendUserId(getLoginInfo().getUser().getId());
				meetingsConfirm.setAttendType(OfficeWorkMeetingConfirm.TYPE_NOT_ATTEND);
				meetingsConfirm.setRemark(attendRemark);
				officeWorkMeetingConfirmService.save(meetingsConfirm);
			}else{
				jsonError = "数据有误！";
			}
		}catch (Exception e) {
			jsonError = "操作失败！";
		}
		return SUCCESS;
	}
	
	public String transferMeeting(){
		// 转交
		try{
			if(transferId.equals(getLoginInfo().getUser().getId())){
				jsonError = "不能转交给自己";
				return SUCCESS;
			}
			boolean flag = officeWorkMeetingConfirmService.isTransfered(meetingId, transferId);
			if(flag){
				jsonError = "该转交人已负责替代其它人参会，请选择其它人员";
				return SUCCESS;
			}
			flag = officeWorkMeetingConfirmService.isHeTransfered(meetingId, transferId, getLoginInfo().getUser().getId());
			if(flag){
				jsonError = "该会议是从对方转交过来的，不能反向转交";
				return SUCCESS;
			}
			OfficeWorkMeetingConfirm meetingsConfirm = new OfficeWorkMeetingConfirm();
			meetingsConfirm.setMeetingId(meetingId);
			meetingsConfirm.setAttendUserId(getLoginInfo().getUser().getId());
			meetingsConfirm.setAttendType(OfficeWorkMeetingConfirm.TYPE_TRANSFER);
			meetingsConfirm.setTransferUserId(transferId);
			officeWorkMeetingConfirmService.saveTransfer(meetingsConfirm);
		
		}catch (Exception e) {
			jsonError = "转交失败！";
		}
		return SUCCESS;
	}
	
	//会议申请
	public String meetingApply(){
		if(StringUtils.isBlank(state)){
			state = "0";
		}
		return SUCCESS;
	}
	// 到会人员
	public String showAttendInfo(){
		meeting = officeWorkMeetingService.getMeetingsAttendInfoById(meetingId);
		return SUCCESS;
	}
	public String meetingApplyList(){
		MeetingsInfoCondition mic = new MeetingsInfoCondition();
		mic.setUserId(getLoginInfo().getUser().getId());
		mic.setUnitId(getUnitId());
		mic.setAuditState(state);
		mic.setMeetingName(meetingName);
		mic.setStartDate(startTime);
		mic.setEndDate(endTime);
		meetinglist = officeWorkMeetingService.getMeetingInfoPage(mic,getPage());
		return SUCCESS;
	}
	

	
	//会议查询
	public String queryMeet(){
		return SUCCESS;
	}
	public String queryMeetList(){
		try {
			meetingList=officeWorkMeetingService.getOfficeWorkMeetingListBySearchParams(this.getUnitId(),meetingName,startTime,endTime,this.getPage());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	//会议纪要管理
	public String meetingMinutesManage(){
		return SUCCESS;
	}
	
	public String meetingMinutesManageList(){
		meetingName=getRequest().getParameter("meetingName");
		try {
			meetingName=java.net.URLDecoder.decode(meetingName,"utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		//纪要负责人为当前登录人员才能查看
		try {
			meetinglist=officeWorkMeetingService.getOfficeWorkMeetingManageListByParams(this.getUnitId(),getLoginUser().getUserId(),meetingName,this.getPage());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	public String meetingMinutesManageEdit(){
		// 纪要管理编辑页面
		if(StringUtils.isNotBlank(meetingId)){
			meeting=officeWorkMeetingService.getOfficeWorkMeetingById(meetingId);
			if((officeWorkMeetingMinutesService.getOfficeWorkMinutesByMeetId(meetingId))!=null){//非第一次设置
				officeWorkMeetingMinutes=officeWorkMeetingMinutesService.getOfficeWorkMinutesByMeetId(meetingId);
				//获取附件信息
				List<Attachment> attachments=attachmentService.getAttachments(officeWorkMeetingMinutes.getId(), OfficeWorkMeetingMinutes.OFFICEWORKMEETINGMINUTES_ATTACHMENT);
				if(attachments!=null&&attachments.size()>0){
					officeWorkMeetingMinutes.setFileName(attachments.get(0).getFileName());
					officeWorkMeetingMinutes.setFileUrl(attachments.get(0).getFileUrl());
				}
			}
		}else{
			meeting=new OfficeWorkMeeting();
		}
		return SUCCESS;
	}
	
	public String meetingManageInfoSave(){
		//纪要管理保存
		try {
			UploadFile file=StorageFileUtils.handleFile(new String[] {"txt","rtf","doc","docx","xls","xlsx","csv","ppt","pptx","pdf"}, 200*1024);
		
			officeWorkMeetingMinutes.setCreateTime(new Date());
			officeWorkMeetingMinutes.setCreateUserId(getLoginInfo().getUser().getId());
			if(StringUtils.isNotBlank(officeWorkMeetingMinutes.getId())){
				officeWorkMeetingMinutesService.update(officeWorkMeetingMinutes);
				promptMessageDto.setOperateSuccess(true);
				promptMessageDto.setPromptMessage("保存成功！");
			}else{
				officeWorkMeetingMinutes.setId(UUIDGenerator.getUUID());
				officeWorkMeetingMinutes.setUnitId(this.getUnitId());
				officeWorkMeetingMinutesService.save(officeWorkMeetingMinutes);
				promptMessageDto.setOperateSuccess(true);
				promptMessageDto.setPromptMessage("修改成功！");
			}
			if(file!=null){
				deleteFile(officeWorkMeetingMinutes.getId());
				Attachment  attachment=new Attachment();
				attachment.setFileName(file.getFileName());
				attachment.setContentType(file.getContentType());
				attachment.setFileSize(file.getFileSize());
				attachment.setUnitId(getUnitId());
				attachment.setObjectId(officeWorkMeetingMinutes.getId());
				attachment.setConStatus(BusinessTask.TASK_STATUS_NOT_NEED_HAND);
				attachment.setObjectType(OfficeWorkMeetingMinutes.OFFICEWORKMEETINGMINUTES_ATTACHMENT);
				attachmentService.saveAttachment(attachment, file);
			}
			//清空附件
			if(StringUtils.isBlank(fileName)){
				deleteFile(officeWorkMeetingMinutes.getId());
			}
		} catch (Exception e) {
			promptMessageDto.setOperateSuccess(false);
			if(e.getCause()!=null){
				promptMessageDto.setPromptMessage(e.getCause().getMessage());
			}else{
				promptMessageDto.setPromptMessage("操作失败:"+e.getMessage());
			}
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	public void deleteFile(String objectId){
		List<Attachment> attachments=attachmentService.getAttachments(objectId,OfficeWorkMeetingMinutes.OFFICEWORKMEETINGMINUTES_ATTACHMENT);
		String[] attachmentIds=new String[attachments.size()];
		for(int i=0;i<attachmentIds.length;i++){
			attachmentIds[i]=attachments.get(i).getId();
		}
		attachmentService.deleteAttachments(attachmentIds);
	}
	//权限管理
	public String meetAuthList(){
		try {
			leaderList=officeDeptLeaderService.getOfficeDeptLeaderByUnitIdList(this.getUnitId());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	public String meetAuthEdit(){
		// 权限设置保存   设置部门负责人的时候判断用户是否此部门的
		if(StringUtils.isNotBlank(userId)&&StringUtils.isNotBlank(deptId)){
			Dept dept=deptService.getDept(userService.getUser(userId).getDeptid());
			if(!dept.getId().equals(deptId)){
				promptMessageDto.setOperateSuccess(false);
				promptMessageDto.setPromptMessage("该用户不在此部门，请重新选择！");
				return SUCCESS;
			}
			try {
				if(StringUtils.isNotBlank(leadId)&&leadId!=null){
					OfficeDeptLeader leader=officeDeptLeaderService.getOfficeDeptLeaderById(leadId);
					leader.setUserId(userId);
					officeDeptLeaderService.update(leader);
				}else{
					OfficeDeptLeader leader=new OfficeDeptLeader();
					leader.setDeptId(deptId);
					leader.setUnitId(this.getUnitId());
					leader.setUserId(userId);
					officeDeptLeaderService.save(leader);
				}
				promptMessageDto.setOperateSuccess(true);
				promptMessageDto.setPromptMessage("操作成功！");
			} catch (Exception e) {
				promptMessageDto.setOperateSuccess(false);
				promptMessageDto.setPromptMessage("操作失败！");
				e.printStackTrace();
			}
		}else{
			promptMessageDto.setOperateSuccess(false);
			promptMessageDto.setPromptMessage("请设置部门负责人！");
		}
		return SUCCESS;
	}
	

	
	
	
	
	public String meetingApplyAdd(){
		meeting = new OfficeWorkMeeting();
		if(StringUtils.isNotBlank(meetingId)){
			meeting = officeWorkMeetingService.getOfficeWorkMeetingById(meetingId);
    	}
		if(meeting == null){
			meeting = new OfficeWorkMeeting();
    	}
		if(StringUtils.isBlank(meeting.getAuditUserId())){
			meeting.setAuditUserId(getLoginInfo().getUser().getId());
			meeting.setAuditUser(getLoginInfo().getUser().getRealname());
		}
		if(meeting.getAuditTime() == null){
			meeting.setAuditTime(new Date());
		}
		return SUCCESS;
	}
	
	public String meetingApplySave(){
//		if(StringUtils.isBlank(meeting.getOtherDept())){
//			jsonError = "列席科室不能为空！";
//			return SUCCESS;
//		}
		try{
			if(StringUtils.isNotBlank(submitState)){
				meeting.setIsDeleted(false);
				meeting.setIsPublish(false);
				meeting.setCreateUserId(getLoginInfo().getUser().getId());
				meeting.setUnitId(getLoginInfo().getUnitID());
				meeting.setCreateTime(new Date());
				meeting.setState(NumberUtils.toInt(submitState));
				officeWorkMeetingService.save(meeting);
			}else{
				jsonError = "数据有误！";
			}
		}catch (Exception e) {
			System.out.println(e.getMessage());
			jsonError = "操作失败！";
		}
		return SUCCESS;
	}
	//删除
	public String deletedMeeting(){
		try{
			if(StringUtils.isNotBlank(meetingId)){
				officeWorkMeetingService.delete(new String[]{meetingId});
			}else{
				jsonError="数据出错！";
			}
		}catch (Exception e) {
			jsonError="删除失败！";
		}
		return SUCCESS;
	}
	//提交
	public String submitMeeting(){
		try{
			if(StringUtils.isNotBlank(meetingId)){
				officeWorkMeetingService.submitMeeting(meetingId);
			}else{
				jsonError="数据出错！";
			}
		}catch (Exception e) {
			jsonError="提交失败！";
		}
		return SUCCESS;
	}
	
	public String meetingAuditSave(){
		try{
			if(StringUtils.isNotBlank(meeting.getId())){
				officeWorkMeetingService.updateAudit(meeting);
			}else{
				jsonError="数据出错！";
			}
		}catch (Exception e) {
			jsonError = "审核失败！";
		}
		return SUCCESS;
	}
	//会议发布
	public String publishMeeting(){
		try{
			if(StringUtils.isNotBlank(meetingId)){
				officeWorkMeetingService.publishMeeting(meetingId);
			}else{
				jsonError="数据出错！";
			}
		}catch (Exception e) {
			jsonError="发布失败！";
		}
		return SUCCESS;
	}
	

	public String meetingAudit(){
		if(StringUtils.isBlank(state)){
			state = "-1";
		}
		return SUCCESS;
	}
	
	public String meetingAuditList(){
		MeetingsInfoCondition mic = new MeetingsInfoCondition();
		mic.setUnitId(getUnitId());
		if(StringUtils.isBlank(state)){
			state = "-1";
		}
		mic.setAuditState(state);
		mic.setMeetingName(meetingName);
		mic.setStartDate(startTime);
		mic.setEndDate(endTime);
		meetinglist = officeWorkMeetingService.getMeetingInfoPage(mic, getPage());
		return SUCCESS;
	}
	
	
	

	public String getShow() {
		return show;
	}

	public void setShow(String show) {
		this.show = show;
	}


	public List<OfficeWorkMeeting> getMeetinglist() {
		return meetinglist;
	}
	public void setMeetinglist(List<OfficeWorkMeeting> meetinglist) {
		this.meetinglist = meetinglist;
	}
	public void setOfficeWorkMeetingService(
			OfficeWorkMeetingService officeWorkMeetingService) {
		this.officeWorkMeetingService = officeWorkMeetingService;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getMeetingId() {
		return meetingId;
	}
	public void setMeetingId(String meetingId) {
		this.meetingId = meetingId;
	}
	public OfficeWorkMeeting getMeeting() {
		return meeting;
	}
	public void setMeeting(OfficeWorkMeeting meeting) {
		this.meeting = meeting;
	}
	public String getBackState() {
		return backState;
	}
	public void setBackState(String backState) {
		this.backState = backState;
	}
	public String getSubmitState() {
		return submitState;
	}
	public void setSubmitState(String submitState) {
		this.submitState = submitState;
	}

	
	public void setOfficeDeptLeaderService(
			OfficeDeptLeaderService officeDeptLeaderService) {
		this.officeDeptLeaderService = officeDeptLeaderService;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getLeadId() {
		return leadId;
	}
	public void setLeadId(String leadId) {
		this.leadId = leadId;
	}
	public void setDeptService(DeptService deptService) {
		this.deptService = deptService;
	}
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	public List<OfficeWorkMeeting> getMeetingList() {
		return meetingList;
	}
	
	public List<OfficeDeptLeader> getLeaderList() {
		return leaderList;
	}
	public String getDeptId() {
		return deptId;
	}
	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}
	public String getMeetingName() {
		return meetingName;
	}
	public void setMeetingName(String meetingName) {
		this.meetingName = meetingName;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Integer getFileSize() {
		return fileSize;
	}
	public void setFileSize(Integer fileSize) {
		this.fileSize = fileSize;
	}
	public Integer getFileCountSize() {
		return fileCountSize;
	}
	public void setFileCountSize(Integer fileCountSize) {
		this.fileCountSize = fileCountSize;
	}
	public String[] getRemoveAttachment() {
		return removeAttachment;
	}
	public void setRemoveAttachment(String[] removeAttachment) {
		this.removeAttachment = removeAttachment;
	}
	public OfficeWorkMeetingMinutes getOfficeWorkMeetingMinutes() {
		return officeWorkMeetingMinutes;
	}
	public void setOfficeWorkMeetingMinutes(
			OfficeWorkMeetingMinutes officeWorkMeetingMinutes) {
		this.officeWorkMeetingMinutes = officeWorkMeetingMinutes;
	}
	public void setOfficeWorkMeetingMinutesService(
			OfficeWorkMeetingMinutesService officeWorkMeetingMinutesService) {
		this.officeWorkMeetingMinutesService = officeWorkMeetingMinutesService;
	}
	public String getReadonlyStyle() {
		return readonlyStyle;
	}
	public void setReadonlyStyle(String readonlyStyle) {
		this.readonlyStyle = readonlyStyle;
	}
	public void setAttachmentService(AttachmentService attachmentService) {
		this.attachmentService = attachmentService;
	}
	
	public boolean getHasEdit() {
		return hasEdit;
	}
	public void setHasEdit(boolean hasEdit) {
		this.hasEdit = hasEdit;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getAttendRemark() {
		return attendRemark;
	}
	public void setAttendRemark(String attendRemark) {
		this.attendRemark = attendRemark;
	}
	public String getTransferId() {
		return transferId;
	}
	public void setTransferId(String transferId) {
		this.transferId = transferId;
	}
	public void setOfficeWorkMeetingConfirmService(
			OfficeWorkMeetingConfirmService officeWorkMeetingConfirmService) {
		this.officeWorkMeetingConfirmService = officeWorkMeetingConfirmService;
	}
	public void setOfficeWorkMeetingAttendService(
			OfficeWorkMeetingAttendService officeWorkMeetingAttendService) {
		this.officeWorkMeetingAttendService = officeWorkMeetingAttendService;
	}
	
}
