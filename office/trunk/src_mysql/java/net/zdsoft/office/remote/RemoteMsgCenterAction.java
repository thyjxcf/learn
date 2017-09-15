package net.zdsoft.office.remote;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.zdsoft.eis.base.attachment.entity.Attachment;
import net.zdsoft.eis.base.attachment.service.AttachmentService;
import net.zdsoft.eis.base.common.entity.Unit;
import net.zdsoft.eis.base.common.entity.User;
import net.zdsoft.eis.base.common.service.UnitService;
import net.zdsoft.eis.base.common.service.UserService;
import net.zdsoft.eis.base.constant.BaseConstant;
import net.zdsoft.eis.frame.action.RemoteBaseAction;
import net.zdsoft.eis.frame.util.RemoteCallUtils;
import net.zdsoft.eis.sms.dto.MsgDto;
import net.zdsoft.keel.util.FileUtils;
import net.zdsoft.keel.util.Pagination;
import net.zdsoft.keel.util.URLUtils;
import net.zdsoft.keel.util.Validators;
import net.zdsoft.keelcnet.config.BootstrapManager;
import net.zdsoft.leadin.common.entity.BusinessTask;
import net.zdsoft.leadin.exception.FileUploadFailException;
import net.zdsoft.office.msgcenter.dto.MessageSearch;
import net.zdsoft.office.msgcenter.entity.OfficeMsgFolder;
import net.zdsoft.office.msgcenter.entity.OfficeMsgFolderDetail;
import net.zdsoft.office.msgcenter.entity.OfficeMsgReceiving;
import net.zdsoft.office.msgcenter.entity.OfficeMsgRecycle;
import net.zdsoft.office.msgcenter.entity.OfficeMsgSending;
import net.zdsoft.office.msgcenter.service.OfficeMsgFolderDetailService;
import net.zdsoft.office.msgcenter.service.OfficeMsgFolderService;
import net.zdsoft.office.msgcenter.service.OfficeMsgReceivingService;
import net.zdsoft.office.msgcenter.service.OfficeMsgRecycleService;
import net.zdsoft.office.msgcenter.service.OfficeMsgSendingService;
import net.zdsoft.office.util.Constants;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;


/**
 * @author chens
 * @version 创建时间：2015-3-10 上午11:03:53
 * 
 */
@SuppressWarnings("serial")
public class RemoteMsgCenterAction extends RemoteBaseAction{
	
	private OfficeMsgSending officeMsgSending;//消息发送
	private OfficeMsgRecycle officeMsgRecycle;//废件箱
	private OfficeMsgReceiving officeMsgReceiving;//收件箱
	private OfficeMsgFolderDetail officeMsgFolderDetail;//自定义信息夹详细信息
	
	private List<OfficeMsgSending> officeMsgSendings;
	private List<OfficeMsgRecycle> officeMsgRecycles;
	private List<OfficeMsgReceiving> officeMsgReceivings;
	private List<OfficeMsgFolder> officeMsgFolders;
	private List<OfficeMsgFolderDetail> officeMsgFolderDetails;
	
	private Map<String, OfficeMsgSending> officeMsgSendingMap;
	
	private UserService userService;
	private UnitService unitService;
	private AttachmentService attachmentService;
	private OfficeMsgSendingService officeMsgSendingService;
	private OfficeMsgRecycleService officeMsgRecycleService;
	private OfficeMsgReceivingService officeMsgReceivingService;
	private OfficeMsgFolderService officeMsgFolderService;
	private OfficeMsgFolderDetailService officeMsgFolderDetailService;
	
	private Pagination page = null;

	private int nowPageIndex;
	
	private String attachmentId;
	private String filename;
	private String filePath;
	
	SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM-dd HH:mm");

	public void sendNote() throws IOException{
		String msgId = getParamValue("msgId");
		String operateType = getParamValue("operateType");
		//重发或者草稿箱发送使用
		if(StringUtils.isNotBlank(msgId)){
			officeMsgSending = officeMsgSendingService.getOfficeMsgSendingSimpleById(msgId, operateType);
			JSONObject returnObject = new JSONObject();
			returnObject.put("msgId", msgId);
			returnObject.put("userIds", officeMsgSending.getUserIds());
			returnObject.put("userNames", officeMsgSending.getUserNames());
			returnObject.put("replyMsgId", officeMsgSending.getReplyMsgId());
			returnObject.put("title", officeMsgSending.getTitle());
			returnObject.put("simpleContent", officeMsgSending.getSimpleContent());
			returnObject.put("operateType", operateType);
			returnObject.put("attachmentArray",createAttachmentArray(officeMsgSending.getAttachments()));
			sendResult(RemoteCallUtils.convertJson(returnObject).toString());
		}
	}
	
	public JSONArray createAttachmentArray(List<Attachment> attachments){
		JSONArray attachmentArray = new JSONArray();
		if(CollectionUtils.isNotEmpty(attachments)){
			for (Attachment attachment : attachments) {
				JSONObject attachmentObject=new JSONObject();
				attachmentObject.put("id", attachment.getId());
				attachmentObject.put("fileName", attachment.getFileName());
				attachmentObject.put("fileSize", attachment.getFileSize());
				attachmentObject.put("extName", attachment.getExtName());
				attachmentObject.put("downloadPath", getNewDownloadPath(attachment.getId()));
				attachmentArray.add(attachmentObject);
			}
		}
		return attachmentArray;
	}
	
	/**
     * 安卓端调用
     * @return
     */
    public String getNewDownloadPath(String attachmentId) {
    	String basePath = BootstrapManager.getBaseUrl();
    	String url = basePath + "/common/open/office/remoteDownLoad.action?attachmentId="+attachmentId+"&isExternalLink=1";
        return url;
    }
	
	public void saveNote() throws Exception{
		String msgId = getParamValue("msgId");
		String userId = getParamValue("userId");
		String unitId = getParamValue("unitId");
		String title = getParamValue("title");
		String userIds = getParamValue("userIds");
		String sendUserName = getParamValue("sendUserName");
		String simpleContent = getParamValue("simpleContent");
		String state = getParamValue("state");
		String operateType = getParamValue("operateType");
		String replyMsgId = getParamValue("replyMsgId");
		String removeAttachmentStr = getParamValue("removeAttachment");
		String attachmentArrayStr = getParamValue("attachmentArray");
		JSONArray attachmentArray = JSONArray.fromObject(attachmentArrayStr);
		
		String[] removeAttachment = removeAttachmentStr.split(",");
		
		if(StringUtils.isNotBlank(title)){
			title = URLUtils.decode(title, "utf-8"); 
		}
		if(StringUtils.isNotBlank(simpleContent)){
			simpleContent = URLUtils.decode(simpleContent, "utf-8"); 
		}
		
		officeMsgSending = new OfficeMsgSending();
		officeMsgSending.setId(msgId);
		officeMsgSending.setCreateUserId(userId);
		officeMsgSending.setUnitId(unitId);
		officeMsgSending.setTitle(title);
		officeMsgSending.setUserIds(userIds);
		officeMsgSending.setSendUserName(sendUserName);
		officeMsgSending.setSimpleContent(simpleContent);
		officeMsgSending.setContent(simpleContent);
		officeMsgSending.setState(Integer.parseInt(state));
		officeMsgSending.setReplyMsgId(replyMsgId);
		
		officeMsgSending.setIsEmergency(1);//一般
		officeMsgSending.setIsNeedsms(false);
		try {
			MsgDto msgDto = null;
			if(Constants.MSG_STATE_SEND == officeMsgSending.getState() && officeMsgSending.getIsNeedsms()){
				msgDto = new MsgDto();
				msgDto.setUserId(officeMsgSending.getCreateUserId());
				User user = userService.getUser(userId);
				Unit unit = unitService.getUnit(unitId);
				msgDto.setUnitName(unit.getName());
				msgDto.setUserName(user.getRealname());
				msgDto.setContent(officeMsgSending.getSmsContent());
				if(officeMsgSending.getTiming()!=null && officeMsgSending.getTiming()){
					msgDto.setTiming(true);
					msgDto.setSendDate(officeMsgSending.getSmsTime().substring(0,10).replaceAll("-",""));
					msgDto.setSendHour(Integer.parseInt(officeMsgSending.getSmsTime().substring(11,13)));
					msgDto.setSendMinutes(Integer.parseInt(officeMsgSending.getSmsTime().substring(14,16)));
				}else{
					msgDto.setTiming(false);
				}
			}
			List<Attachment> attachments  = organizeAttachment(officeMsgSending, operateType, removeAttachment, attachmentArray);
			if(StringUtils.isNotBlank(officeMsgSending.getId())){
				officeMsgSendingService.updateByMobile(officeMsgSending,attachments,removeAttachment,msgDto);
			}else{
				officeMsgSendingService.saveByMobile(officeMsgSending, attachments,msgDto);
			}
		} catch (FileUploadFailException e) {
			if(e.getCause()!=null){
				jsonError = e.getCause().getMessage();
			}else{
				jsonError = e.getMessage();
			}
		}catch (Exception e) {
			e.printStackTrace();
			jsonError = "保存失败";
		}
		if(StringUtils.isNotBlank(jsonError)){
			sendResult(RemoteCallUtils.convertError(jsonError).toString());
		}else{
			sendResult(RemoteCallUtils.convertJson("1").toString());
		}
	}
	
	/**
	 * 组织消息附件
	 */
	public List<Attachment> organizeAttachment(OfficeMsgSending officeMsgSending, String operateType, String[] removeAttachment, JSONArray attachmentArray){
		List<Attachment> attachmentAll = new ArrayList<Attachment>();
		//转发、重新发送
		if (Constants.OPERATE_TYPE_FORWARDING.equals(operateType) || Constants.OPERATE_TYPE_RESEND.equals(operateType)) {
			// 组织原来的附件
			List<Attachment> attachments = attachmentService.getAttachments(officeMsgSending.getId(), Constants.MESSAGE_ATTACHMENT);//附件
			if (CollectionUtils.isNotEmpty(attachments)) {
				flag:for (int i = 0; i < attachments.size(); i++) {
					Attachment attachment = attachments.get(i);
					// 判断原有的附件删除串不是空
					if (!Validators.isEmpty(removeAttachment)) {
						for (int j = 0; j < removeAttachment.length; j++) {
							if (attachment.getId().equals(removeAttachment[j])) {
								continue flag;
							}
						}
					}
					attachment.setId(null);
					attachment.setObjectId(null);
					attachmentAll.add(attachment);
				}
			}
			officeMsgSending.setId(null);
			removeAttachment = null;
		}
		 for (int i = 0; i < attachmentArray.size(); i++) {
			JSONObject object = (JSONObject)attachmentArray.get(i);
			Attachment attachment = new Attachment();
			attachment.setObjectType(Constants.MESSAGE_ATTACHMENT);
			attachment.setFileName(object.getString("fileName"));
			attachment.setFileSize(object.getLong("fileSize"));
			attachment.setContentType(object.getString("contentType"));
			attachment.setUnitId(officeMsgSending.getUnitId());
			attachment.setDirId(BaseConstant.ZERO_GUID);
			attachment.setFilePath(object.getString("filePath"));
			attachment.setCreationTime(new Date());
			attachment.setModifyTime(new Date());
			attachment.setExtName(object.getString("extName"));
			attachment.setConStatus(BusinessTask.TASK_STATUS_NOT_NEED_HAND);
			attachmentAll.add(attachment);
		}
		return attachmentAll;
	}
	
	public void viewMsgSingle() throws IOException{
		String userId = getParamValue("userId");
		int msgState = Integer.parseInt(getParamValue("msgState"));
		String msgId = getParamValue("msgId");
		officeMsgFolders = officeMsgFolderService.getOfficeMsgFolderList(userId);
		int detailState;
		if(msgState == Constants.MSG_STATE_DRAFT || msgState == Constants.MSG_STATE_SEND){
			//发件箱或者草稿箱点击查看
			officeMsgSending = officeMsgSendingService.getOfficeMsgSendingById(msgId);
			detailState = msgState;
		}else if(msgState == Constants.MSG_STATE_RECEIVE){
			//TODO  收件箱会话模式，这里可以不加方法
		}else if(msgState == Constants.MSG_STATE_RECYCLE){
			//废件箱点击查看
			officeMsgRecycle = officeMsgRecycleService.getOfficeMsgRecycleById(msgId);
			if(officeMsgRecycle.getState() == Constants.MSG_STATE_DRAFT || officeMsgRecycle.getState() == Constants.MSG_STATE_SEND){
				detailState = officeMsgRecycle.getState();
				officeMsgSending = officeMsgSendingService.getOfficeMsgSendingById(officeMsgRecycle.getReferenceId());
			}else if(officeMsgRecycle.getState() == Constants.MSG_STATE_RECEIVE){
				detailState = officeMsgRecycle.getState();
				officeMsgReceiving = officeMsgReceivingService.getOfficeMsgReceivingById(officeMsgRecycle.getReferenceId());
				officeMsgSending = officeMsgSendingService.getOfficeMsgSendingById(officeMsgReceiving.getMessageId());
			}else if(officeMsgRecycle.getState() == Constants.MSG_STATE_CUSTOMER){
				officeMsgFolderDetail = officeMsgFolderDetailService.getOfficeMsgFolderDetailById(officeMsgRecycle.getReferenceId());
				detailState = officeMsgFolderDetail.getReferenceState();
				if(officeMsgFolderDetail.getReferenceState() == Constants.MSG_STATE_DRAFT || officeMsgFolderDetail.getReferenceState() == Constants.MSG_STATE_SEND){
					officeMsgSending = officeMsgSendingService.getOfficeMsgSendingById(officeMsgFolderDetail.getReferenceId());
				}else if(officeMsgFolderDetail.getReferenceState() == Constants.MSG_STATE_RECEIVE){
					officeMsgReceiving = officeMsgReceivingService.getOfficeMsgReceivingById(officeMsgFolderDetail.getReferenceId());
					officeMsgSending = officeMsgSendingService.getOfficeMsgSendingById(officeMsgReceiving.getMessageId());
				}
			}
		}else if(msgState == Constants.MSG_STATE_CUSTOMER){
			//自定义信息夹点击查看
			officeMsgFolderDetail = officeMsgFolderDetailService.getOfficeMsgFolderDetailById(msgId);
			detailState = officeMsgFolderDetail.getReferenceState();
			String folderId = officeMsgFolderDetail.getFolderId();
			if(officeMsgFolderDetail.getReferenceState() == Constants.MSG_STATE_DRAFT || officeMsgFolderDetail.getReferenceState() == Constants.MSG_STATE_SEND){
				officeMsgSending = officeMsgSendingService.getOfficeMsgSendingById(officeMsgFolderDetail.getReferenceId());
			}else if(officeMsgFolderDetail.getReferenceState() == Constants.MSG_STATE_RECEIVE){
				officeMsgReceiving = officeMsgReceivingService.getOfficeMsgReceivingById(officeMsgFolderDetail.getReferenceId());
				officeMsgSending = officeMsgSendingService.getOfficeMsgSendingById(officeMsgReceiving.getMessageId());
			}
		}
		JSONObject returnObject = new JSONObject();
		returnObject.put("msgId", msgId);
		returnObject.put("userIds", officeMsgSending.getUserIds());
		returnObject.put("sendUserName", officeMsgSending.getSendUserNameSimple());
		returnObject.put("userNames", officeMsgSending.getUserNames());
		returnObject.put("replyMsgId", officeMsgSending.getReplyMsgId());
		returnObject.put("title", officeMsgSending.getTitle());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
		returnObject.put("sendTime",officeMsgSending.getSendTime()!=null?sdf.format(officeMsgSending.getSendTime()):"");
		returnObject.put("simpleContent", officeMsgSending.getSimpleContent());
		returnObject.put("attachmentArray",createAttachmentArray(officeMsgSending.getAttachments()));
		returnObject.put("isWithdraw", officeMsgSending.getIsWithdraw());
		sendResult(RemoteCallUtils.convertJson(returnObject).toString());
	}
	
	public void obtainUserNames() throws IOException{
		String msgId = getParamValue("msgId");
		String userNames = officeMsgSendingService.getOfficeMsgSendingUserNames(msgId);
		sendResult(RemoteCallUtils.convertJson(userNames).toString());
	}
	
	/**
	 * 会话列表
	 * @return
	 * @throws Exception 
	 */
	public void msgDetail() throws Exception{
		String userId = getParamValue("userId");
		String replyMsgId = getParamValue("replyMsgId");
		officeMsgSending = officeMsgSendingService.getOfficeMsgSendingById(replyMsgId);
		officeMsgReceivings = officeMsgReceivingService.getOfficeMsgReceivingListByReplyMsgId(replyMsgId, userId);
		if(CollectionUtils.isNotEmpty(officeMsgReceivings)){
			String[] msgSendingIds = new String[officeMsgReceivings.size()];
			for(int i=0;i<officeMsgReceivings.size();i++){
				msgSendingIds[i] = officeMsgReceivings.get(i).getMessageId();
			}
			officeMsgSendingMap = officeMsgSendingService.getOfficeMsgSendingMapByIds(msgSendingIds);
		}
		JSONArray items = new JSONArray();
		
		for (OfficeMsgReceiving receiving : officeMsgReceivings) {
			JSONObject jsob=new JSONObject();
			jsob.put("msgId", receiving.getId());//receiveId
			jsob.put("replyMsgId", receiving.getReplyMsgId());//会话id
			jsob.put("isRead", (receiving.getIsRead()==0?false:true));//是否已读
			jsob.put("hasAttached", (receiving.getHasAttached()==1?true:false));//是否有附件
			jsob.put("sendUsername", receiving.getSendUsername());//发件人
			jsob.put("hasStar", (receiving.getHasStar()==1?true:false));//是否加星
			jsob.put("isEmergency", (receiving.getIsEmergency()==2?true:false));//紧急
			jsob.put("title", receiving.getTitle());//标题
			jsob.put("isSendInfo", receiving.getIsSendInfo());
			OfficeMsgSending sending = officeMsgSendingMap.get(receiving.getMessageId());
			if(sending != null){
				jsob.put("simpleContent", sending.getSimpleContent());//无格式内容
			}else{
				jsob.put("simpleContent", "");//无格式内容
			}
//			jsob.put("simpleContent", officeMsgSendingMap.get(receiving.getMessageId()).getSimpleContent());//无格式内容
			jsob.put("countNum", receiving.getCountNum());//会话条数
			jsob.put("dateStr", simpleDateFormat.format(receiving.getSendTime()));//时间
			jsob.put("isWithdraw", receiving.getIsWithdraw());
			items.add(jsob);
		}
		sendResult(RemoteCallUtils.convertJsons(items).toString());
	}
	
	/**
	 * 会话详细
	 * @return
	 * @throws IOException 
	 */
	public void msgDetailContent() throws IOException{
		String userId = getParamValue("userId");
		String msgId = getParamValue("msgId");
		
		officeMsgReceivingService.updateRead(msgId);
		officeMsgFolders = officeMsgFolderService.getOfficeMsgFolderList(userId);
		officeMsgReceiving = officeMsgReceivingService.getOfficeMsgReceivingById(msgId);
		officeMsgSending = officeMsgSendingService.getOfficeMsgSendingById(officeMsgReceiving.getMessageId());
		
		JSONObject returnObject = new JSONObject();
		returnObject.put("msgId", msgId);
		returnObject.put("messageId", officeMsgReceiving.getMessageId());
		returnObject.put("userIds", officeMsgSending.getUserIds());
		returnObject.put("sendUserName", officeMsgSending.getSendUserNameSimple());
		returnObject.put("userNames", officeMsgSending.getUserNames());
		returnObject.put("replyMsgId", officeMsgSending.getReplyMsgId());
		returnObject.put("title", officeMsgSending.getTitle());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
		returnObject.put("sendTime",sdf.format(officeMsgSending.getSendTime()));
		returnObject.put("simpleContent", officeMsgSending.getSimpleContent());
		returnObject.put("attachmentArray",createAttachmentArray(officeMsgSending.getAttachments()));
		returnObject.put("isWithdraw", officeMsgSending.getIsWithdraw());
		sendResult(RemoteCallUtils.convertJson(returnObject).toString());
	}
	
	
	/**
	 * 收件箱
	 * 
	 * @return
	 */
	public String msgInbox() {
		String userId = getParamValue("userId");
		int totalNum = officeMsgReceivingService.getNumber(userId, null);
		int unReadNum =  officeMsgReceivingService.getNumber(userId, Constants.UNREAD+"");
		return SUCCESS;
	}
	
	public void getUnReadNum() throws IOException{
		String userId = getParamValue("userId");
		int unReadNum =  officeMsgReceivingService.getNumber(userId, Constants.UNREAD+"");
		JSONObject json = new JSONObject();
		json.put("unReadNum", unReadNum);
		sendResult(RemoteCallUtils.convertJson(json).toString());
	}

	/**
	 * 收件箱-信息列表
	 * TODO
	 * @return
	 * @throws Exception 
	 */
	public void remoteReceivedMessagesList() throws Exception {
		String userId = getParamValue("userId");
		String searchTitle = getParamValue("searchTitle");
		if(StringUtils.isNotBlank(searchTitle)){
			searchTitle = URLUtils.decode(getParamValue("searchTitle"), "utf-8"); 
		}
		if(!findPage()){
			return;
		}
//		officeMsgFolders = officeMsgFolderService.getOfficeMsgFolderList(userId);
//		Date date = new Date();
//		todayStr = DateUtils.date2StringByDay(date);
//		date = DateUtils.addDay(date, -1);
//		yesterdayStr = DateUtils.date2StringByDay(date);
		MessageSearch search = new MessageSearch();
		search.setSearchTitle(searchTitle);
//		search.setSearchSender(searchSender);
//		search.setSearchBeginTime(searchBeginTime);
//		search.setSearchEndTime(searchEndTime);
//		search.setReadType(readType);
//		search.setMsgType(BaseConstant.MSG_TYPE_NOTE);
		search.setReceiveUserId(userId);
		officeMsgReceivings = officeMsgReceivingService.getOfficeMsgReceivingList(search, page);
		List<String> sendingIds = new ArrayList<String>();
		for (OfficeMsgReceiving receiving : officeMsgReceivings) {
			sendingIds.add(receiving.getMessageId());
		}
		officeMsgSendingMap =  officeMsgSendingService.getOfficeMsgSendingMapByIds(sendingIds.toArray(new String[0]));
		JSONArray items = new JSONArray();
		for (OfficeMsgReceiving receiving : officeMsgReceivings) {
			JSONObject jsob=new JSONObject();
			jsob.put("msgId", receiving.getId());//receiveId
			jsob.put("replyMsgId", receiving.getReplyMsgId());//会话id
			jsob.put("isRead", (receiving.getIsRead()==0?false:true));//是否已读
			jsob.put("hasAttached", (receiving.getHasAttached()==1?true:false));//是否有附件
			jsob.put("sendUsername", receiving.getSendUsername());//发件人
			jsob.put("hasStar", (receiving.getHasStar()==1?true:false));//是否加星
			jsob.put("isEmergency", (receiving.getIsEmergency()==2?true:false));//紧急
			jsob.put("title", receiving.getTitle());//标题
			if(StringUtils.isNotBlank(receiving.getMessageId()) && officeMsgSendingMap.containsKey(receiving.getMessageId())){
				OfficeMsgSending sending = officeMsgSendingMap.get(receiving.getMessageId());
				if(sending != null){
					jsob.put("simpleContent", sending.getSimpleContent());//无格式内容
				}else{
					jsob.put("simpleContent", "");//无格式内容
				}
			}else{
				jsob.put("simpleContent", "");//无格式内容
			}
			jsob.put("countNum", receiving.getCountNum());//会话条数
			jsob.put("dateStr", simpleDateFormat.format(receiving.getSendTime()));//时间
			jsob.put("isWithdraw", receiving.getIsWithdraw());
			items.add(jsob);
		}
		if(nowPageIndex!=page.getPageIndex()){
			items=new JSONArray();
		}
		System.out.println(items);
		sendResult(RemoteCallUtils.convertJsons(items).toString());
	}
	/**
	 * 全部设置为已读
	 * @throws Exception 
	 */
	public void readAll() throws Exception{
		String userId = getParamValue("userId");
		try {
			officeMsgReceivingService.readAll(userId);
		} catch (Exception e) {
			e.printStackTrace();
			jsonError = "设置已读失败";
		}
		if(StringUtils.isNotBlank(jsonError)){
			sendResult(RemoteCallUtils.convertError(jsonError).toString());
		}else{
			sendResult(RemoteCallUtils.convertJson("1").toString());
		}
	}
	/**
	 * 统一加星
	 * @throws Exception 
	 */
	public void changeAllStar() throws Exception{
		String userId = getParamValue("userId");
		String replyMsgId = getParamValue("replyMsgId");
		int hasStar = booleanStrToInt(getParamValue("hasStar"));
		try {
			officeMsgReceivingService.changeAllStar(userId,replyMsgId,hasStar);
		} catch (Exception e) {
			e.printStackTrace();
			jsonError = "设置失败";
		}
		if(StringUtils.isNotBlank(jsonError)){
			sendResult(RemoteCallUtils.convertError(jsonError).toString());
		}else{
			sendResult(RemoteCallUtils.convertJson("1").toString());
		}
	}
	
	private int booleanStrToInt(String hasStar){
		if("false".equals(hasStar)){
			return 0;
		}else if("true".equals(hasStar)){
			return 1;
		}
		return 0;
	}
	
	/**
	 * 单个加星
	 * @throws Exception 
	 */
	public void changeStar() throws Exception{
		String msgId = getParamValue("msgId");
		int hasStar = booleanStrToInt(getParamValue("hasStar"));
		try {
			officeMsgReceivingService.changeStar(msgId, hasStar);
		} catch (Exception e) {
			e.printStackTrace();
			jsonError = "设置失败";
		}
		if(StringUtils.isNotBlank(jsonError)){
			sendResult(RemoteCallUtils.convertError(jsonError).toString());
		}else{
			sendResult(RemoteCallUtils.convertJson("1").toString());
		}
	}
	
	public void removeMsg() throws Exception{
		int msgState = Integer.parseInt(getParamValue("msgState"));
		String deleteId = getParamValue("deleteId");
		try {
			if(msgState == Constants.MSG_STATE_DRAFT || msgState == Constants.MSG_STATE_SEND){
				officeMsgSendingService.delete(new String[]{deleteId},msgState);
			}else if(msgState == Constants.MSG_STATE_RECEIVE || msgState == Constants.MSG_STATE_IMPORT){
				officeMsgReceivingService.deleteById(deleteId);
			}else if(msgState == Constants.MSG_STATE_RECYCLE){
				officeMsgRecycleService.delete(new String[]{deleteId});
			}else if(msgState == Constants.MSG_STATE_CUSTOMER){
				officeMsgFolderDetailService.delete(new String[]{deleteId});
			}
		} catch (Exception e) {
			e.printStackTrace();
			jsonError = "删除失败";
		}
		if(StringUtils.isNotBlank(jsonError)){
			sendResult(RemoteCallUtils.convertError(jsonError).toString());
		}else{
			sendResult(RemoteCallUtils.convertJson("1").toString());
		}
	}
	
	public void removeMsgs() throws Exception{
		String userId = getParamValue("userId");
		int msgState = Integer.parseInt(getParamValue("msgState"));
		String[] deleteIds = getParamValue("deleteIds").split(",");
		try {
			if(msgState == Constants.MSG_STATE_DRAFT || msgState == Constants.MSG_STATE_SEND){
				officeMsgSendingService.delete(deleteIds,msgState);
			}else if(msgState == Constants.MSG_STATE_RECEIVE){
				officeMsgReceivingService.removeByReplyMsgIds(userId,deleteIds);
			}else if(msgState == Constants.MSG_STATE_RECYCLE){
				officeMsgRecycleService.delete(deleteIds);
			}else if(msgState == Constants.MSG_STATE_CUSTOMER){
				officeMsgFolderDetailService.delete(deleteIds);
			}
		} catch (Exception e) {
			e.printStackTrace();
			jsonError = "删除失败";
		}
		if(StringUtils.isNotBlank(jsonError)){
			sendResult(RemoteCallUtils.convertError(jsonError).toString());
		}else{
			sendResult(RemoteCallUtils.convertJson("1").toString());
		}
	}
	
	/**
	 * 发件箱
	 * 
	 * @return
	 */
	public String msgOutbox() {
		return SUCCESS;
	}

	/**
	 * 显示发件箱信息
	 * TODO
	 * @return
	 * @throws Exception 
	 */
	public void remoteSendedMessagesList() throws Exception {
		String userId = getParamValue("userId");
		String searchTitle = getParamValue("searchTitle");
		if(StringUtils.isNotBlank(searchTitle)){
			searchTitle = URLUtils.decode(getParamValue("searchTitle"), "utf-8"); 
		}
		if(!findPage()){
			return;
		}
		MessageSearch messageSearch = new MessageSearch();
		messageSearch.setUserId(userId);
		messageSearch.setSearchTitle(searchTitle);
//		messageSearch.setMsgType(BaseConstant.MSG_TYPE_NOTE);
		messageSearch.setState(Constants.MSG_STATE_SEND);
//		messageSearch.setSearchBeginTime(searchBeginTime);
//		messageSearch.setSearchEndTime(searchEndTime);
		officeMsgSendings = officeMsgSendingService.getOfficeMsgSendingList(messageSearch, page);
		JSONArray items = new JSONArray();
		for (OfficeMsgSending sending : officeMsgSendings) {
			JSONObject jsob=new JSONObject();
			jsob.put("msgId", sending.getId());//receiveId
			jsob.put("hasAttached", (sending.getHasAttached()==1?true:false));//是否有附件
			jsob.put("title", sending.getTitle());//标题
			jsob.put("simpleContent", sending.getSimpleContent());//简单内容
			jsob.put("dateStr", simpleDateFormat.format(sending.getCreateTime()));//时间
			jsob.put("readStr", sending.getReadStr());//已读未读人数2/3
			jsob.put("isWithdraw", sending.getIsWithdraw());//是否已撤回
			items.add(jsob);
		}
		if(nowPageIndex!=page.getPageIndex()){
			items=new JSONArray();
		}
		
		sendResult(RemoteCallUtils.convertJsons(items).toString());
	}
	
	/**
	 * 撤回消息
	 * @throws Exception 
	 */
	public void callBackMsg() throws Exception{
		String msgId = getParamValue("msgId");
		try {
			officeMsgSendingService.callBackMsg(msgId);
		} catch (Exception e) {
			e.printStackTrace();
			jsonError = "撤回失败";
		}
		if(StringUtils.isNotBlank(jsonError)){
			sendResult(RemoteCallUtils.convertError(jsonError).toString());
		}else{
			sendResult(RemoteCallUtils.convertJson("1").toString());
		}
	}
	
	public String remindSms(){
		String msgId = getParamValue("msgId");
		String userId = getParamValue("userId");
		String unitName = getParamValue("unitName");
		String userName = getParamValue("userName");
		String smsContent = getParamValue("smsContent");
		try {
			MsgDto msgDto = new MsgDto();
			msgDto.setUserId(userId);
			msgDto.setUnitName(unitName);
			msgDto.setUserName(userName);
			msgDto.setContent(smsContent);
			msgDto.setTiming(false);
			officeMsgReceivingService.remindSms(msgId, msgDto);
		} catch (Exception e) {
			e.printStackTrace();
			jsonError = "短信提醒失败";
		}
		return SUCCESS;
	}
	
	
	/**
	 * 草稿箱
	 * 
	 * @return
	 */
	public String msgDraftbox() {
		return SUCCESS;
	}

	/**
	 * 显示草稿箱信息
	 * 
	 * @return
	 * @throws Exception 
	 */
	public void listDraftMessages() throws Exception {
		String userId = getParamValue("userId");
		String searchTitle = getParamValue("searchTitle");
		if(StringUtils.isNotBlank(searchTitle)){
			searchTitle = URLUtils.decode(getParamValue("searchTitle"), "utf-8"); 
		}
		if(!findPage()){
			return;
		}
		officeMsgFolders = officeMsgFolderService.getOfficeMsgFolderList(userId);
		MessageSearch messageSearch = new MessageSearch();
		messageSearch.setUserId(userId);
		messageSearch.setSearchTitle(searchTitle);
//		messageSearch.setMsgType(BaseConstant.MSG_TYPE_NOTE);
		messageSearch.setState(Constants.MSG_STATE_DRAFT);
		officeMsgSendings = officeMsgSendingService.getOfficeMsgSendingList(messageSearch, page);
		JSONArray items = new JSONArray();
		for (OfficeMsgSending sending : officeMsgSendings) {
			JSONObject jsob=new JSONObject();
			jsob.put("msgId", sending.getId());//receiveId
			jsob.put("hasAttached", (sending.getHasAttached()==1?true:false));//是否有附件
			jsob.put("title", sending.getTitle());//标题
			jsob.put("simpleContent", sending.getSimpleContent());//简单内容
			jsob.put("dateStr", simpleDateFormat.format(sending.getCreateTime()));//时间
			items.add(jsob);
		}
		if(nowPageIndex!=page.getPageIndex()){
			items=new JSONArray();
		}
		
		sendResult(RemoteCallUtils.convertJsons(items).toString());
	}
	
	
	
	/**
	 * 废件箱
	 * 
	 * @return
	 */
	public String msgAbandon() {
		return SUCCESS;
	}

	/**
	 * 显示废件箱信息
	 * 
	 * @return
	 * @throws Exception 
	 */
	public void listAbandonMessages() throws Exception {
		String userId = getParamValue("userId");
		String searchTitle = getParamValue("searchTitle");
		if(StringUtils.isNotBlank(searchTitle)){
			searchTitle = URLUtils.decode(getParamValue("searchTitle"), "utf-8"); 
		}
		if(!findPage()){
			return;
		}
		officeMsgFolders = officeMsgFolderService.getOfficeMsgFolderList(userId);
		officeMsgRecycles = officeMsgRecycleService.getOfficeMsgRecyclePage(userId,searchTitle,page);
	}
	
	public String revertMessage(){
		String msgId = getParamValue("msgId");
		try {
			officeMsgRecycleService.revertMessage(msgId);
		} catch (Exception e) {
			e.printStackTrace();
			jsonError = "还原失败";
		}
		return SUCCESS;
	}
	
	
	//如果是分页需要传pageIndex、pageSize
	private boolean findPage() throws Exception{
		String pageIndex = getParamValue("pageIndex");//当前第几页
		String pageSize = getParamValue("pageSize");//一页几行
		if (StringUtils.isBlank(pageIndex)) {
			sendResult(RemoteCallUtils.convertError("远程调用当前第几页不能为空").toString());
			return false;
		}
		if (StringUtils.isBlank(pageSize)) {
			sendResult(RemoteCallUtils.convertError("远程调用一页几行不能为空").toString());
			return false;
		}
		page=new Pagination(0,false);
		nowPageIndex = Integer.valueOf(pageIndex);
		page.setPageIndex(nowPageIndex);
		page.setPageSize(Integer.valueOf(pageSize));
		return true;
	}
	
	public String downloadAttachment(){
		response = getResponse();
		Attachment attachment = attachmentService
				.getAttachment(attachmentId);
		if (attachment == null) {
			promptMessageDto.setErrorMessage("附件没有找到！\n附件 ID ["
					+ attachmentId + "]");
			return PROMPTMSG;
		}
		try {
			InputStream attachmentData = null;
			attachmentData = attachment.getContentsAsStream();
			if (attachmentData != null) {
				String mimeType = null;
				int fileSize = 0;

				try {
					File file = attachment.getFile();
					mimeType = this.getServletContext().getMimeType(
							file.getAbsolutePath());
					fileSize = (int) file.length();
				} catch (IOException e) {
					mimeType = attachment.getContentType();
					fileSize = (int) attachment.getFileSize();
				}

				if ((mimeType == null) || mimeType.trim().equals("")) {
					mimeType = "application/unknown";
				} else if (mimeType.startsWith("text/html")) {
					mimeType = "application/unknown";
				}else if(mimeType.startsWith("text/plain")){
					mimeType = "text/plain;charset=GBK";
				}
				// mimeType = "application/x-msdownload";
				// HttpUtility.UrlEncode 在 Encode 的时候, 将空格转换成加号('+'), 在
				// Decode 的时候将加号转为空格, 但是浏览器是不能理解加号为空格的, 所以如果文件名包含了空格,
				// 在浏览器下载得到的文件, 空格就变成了加号
				String name = getDownLoadFileName(attachment.getFileName());
				response.reset();
				response.addHeader("Content-Disposition","attachment; " + name);

				response.setContentType(mimeType);
				response.setContentLength(fileSize);

				if (log.isDebugEnabled()) {
					StringBuffer sb = new StringBuffer();
					sb.append("\nContent-disposition: attachment; filename=\""
							+ attachment.getFileName() + "\";");
					sb.append("\n        ContentType: "
							+ attachment.getContentType());
					sb.append("\n      ContentLength: "
							+ attachment.getFileSize());
					log.debug("\n附件下载：" + sb.toString());
				}

				FileUtils.serveFile(response, attachmentData);
			} else {
				promptMessageDto.setErrorMessage("附件没有找到！\n附件 ID ["
						+ attachmentId + "]");
				return PROMPTMSG;
			}
		} catch (IOException e) {
			promptMessageDto.setErrorMessage("附件没有找到！\n附件 ID ["
					+ attachmentId + "]");
			return PROMPTMSG;
		}

		return NONE;
	}
	
	private String getDownLoadFileName(String filename) {
		String new_filename = null;
		try {
			new_filename = URLEncoder.encode(filename, "UTF8");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		HttpServletRequest request = ServletActionContext.getRequest();
		String userAgent = request.getHeader("User-Agent");
		// System.out.println(userAgent);
		String rtn = "filename=\"" + new_filename + "\"";
		// 如果没有UA，则默认使用IE的方式进行编码，因为毕竟IE还是占多数的
		if (userAgent != null) {
			userAgent = userAgent.toLowerCase();
			// IE浏览器，只能采用URLEncoder编码
			if (userAgent.indexOf("msie") != -1) {
				rtn = "filename=\"" + new_filename + "\"";
			}
			// Opera浏览器只能采用filename*
			else if (userAgent.indexOf("opera") != -1) {
				rtn = "filename*=UTF-8''" + new_filename;
			}
			// Safari浏览器，只能采用ISO编码的中文输出
			else if (userAgent.indexOf("safari") != -1) {
				try {
					rtn = "filename=" + new String(filename.getBytes("UTF-8"),"ISO8859-1");
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
			}
			// Chrome浏览器，只能采用MimeUtility编码或ISO编码的中文输出
			else if (userAgent.indexOf("applewebkit") != -1) {
				try {
					rtn = "filename=" + new String(filename.getBytes("UTF-8"),"ISO8859-1");
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
			}
			// FireFox浏览器，可以使用MimeUtility或filename*或ISO编码的中文输出
			else if (userAgent.indexOf("mozilla") != -1) {
				rtn = "filename*=UTF-8''" + new_filename;
			}
		}
		return rtn;
	}
	public String netDownloadAttahment(){
		response = ServletActionContext.getResponse();

        try {
            String path = null;
            if (StringUtils.isBlank(filename)) {
                filename = "";
                path = filePath;
            } else {
                path = BootstrapManager.getStoreHome() + File.separator + filePath + File.separator
                        + filename;
            }

            File storefile = new File(path);

            if (!storefile.exists()) {
                throw new FileNotFoundException(storefile.getPath());
            }

            if (log.isDebugEnabled()) {
                log.debug("读取文件: " + storefile.getAbsolutePath());
            }

            InputStream data = new FileInputStream(storefile);
            int fileSize = (int) storefile.length();
            String mimeType = this.getServletContext().getMimeType(storefile.getAbsolutePath());

            if ((mimeType == null) || mimeType.trim().equals("")) {
                mimeType = "application/unknown";
            } else if (mimeType.startsWith("text/html")) {
                mimeType = "application/unknown";
            }
            mimeType = "application/x-msdownload";

            response.setHeader("Content-Disposition", "attachment; filename="
                    + URLUtils.encode(filename, "UTF-8"));

            response.setContentType(mimeType);
            response.setContentLength(fileSize);
            FileUtils.serveFile(response, data);
        } catch (Exception e) {
            promptMessageDto.setErrorMessage("文件下载出错！\n错误信息[" + e.getMessage() + "]");
            return PROMPTMSG;
        }
        return null;
	}

	public String getAttachmentId() {
		return attachmentId;
	}

	public void setAttachmentId(String attachmentId) {
		this.attachmentId = attachmentId;
	}

	public void setAttachmentService(AttachmentService attachmentService) {
		this.attachmentService = attachmentService;
	}

	public void setOfficeMsgSendingService(
			OfficeMsgSendingService officeMsgSendingService) {
		this.officeMsgSendingService = officeMsgSendingService;
	}

	public void setOfficeMsgRecycleService(
			OfficeMsgRecycleService officeMsgRecycleService) {
		this.officeMsgRecycleService = officeMsgRecycleService;
	}

	public void setOfficeMsgReceivingService(
			OfficeMsgReceivingService officeMsgReceivingService) {
		this.officeMsgReceivingService = officeMsgReceivingService;
	}

	public void setOfficeMsgFolderService(
			OfficeMsgFolderService officeMsgFolderService) {
		this.officeMsgFolderService = officeMsgFolderService;
	}

	public void setOfficeMsgFolderDetailService(
			OfficeMsgFolderDetailService officeMsgFolderDetailService) {
		this.officeMsgFolderDetailService = officeMsgFolderDetailService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public void setUnitService(UnitService unitService) {
		this.unitService = unitService;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	
}
