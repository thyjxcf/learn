package net.zdsoft.office.remote;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.zdsoft.eis.base.attachment.entity.Attachment;
import net.zdsoft.eis.base.attachment.service.AttachmentService;
import net.zdsoft.eis.base.common.entity.Unit;
import net.zdsoft.eis.base.common.entity.User;
import net.zdsoft.eis.base.common.service.UnitService;
import net.zdsoft.eis.base.common.service.UserService;
import net.zdsoft.eis.base.storage.StorageFileService;
import net.zdsoft.eis.base.storage.StorageFileUtils;
import net.zdsoft.eis.frame.action.BaseActionSupport;
import net.zdsoft.eis.sms.dto.MsgDto;
import net.zdsoft.keel.util.FileUtils;
import net.zdsoft.keel.util.Pagination;
import net.zdsoft.keel.util.ServletUtils;
import net.zdsoft.keel.util.URLUtils;
import net.zdsoft.keel.util.Validators;
import net.zdsoft.keelcnet.action.UploadFile;
import net.zdsoft.keelcnet.config.BootstrapManager;
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

/**
 * @author chens
 * @version 创建时间：2015-3-10 上午11:03:53
 * 
 */
@SuppressWarnings("serial")
public class RemoteMsgCenterHfiveAction extends BaseActionSupport{
	
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
	private StorageFileService storageFileService;
	
	private Pagination page = null;

	private int nowPageIndex;
	private String pageIndex;//当前第几页
	private String pageSize;//一页几行
	
	private String attachmentId;
	private String msgId;
	private String userId;
	private String unitName;
	private String userName;
	private String smsContent;
	private String operateType;
	private String unitId;
	private String title;
	private String userIds;
	private String sendUserName;
	private String simpleContent;
	private String state;
	private String replyMsgId;
	private String removeAttachment;
	private Integer msgState;
	private String searchTitle;
	private String hasStar;
	private String deleteId;
	private String deleteIds;
	
	SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM-dd HH:mm");
	protected Map<String, Object> jsonMap = new HashMap<String, Object>();

	public void sendNote() throws IOException{
		//重发或者草稿箱发送使用
		if(StringUtils.isNotBlank(msgId)){
			officeMsgSending = officeMsgSendingService.getOfficeMsgSendingSimpleByIdByMobile(msgId, operateType);
			JSONObject returnObject = new JSONObject();
			returnObject.put("msgId", msgId);
			returnObject.put("userIds", officeMsgSending.getUserIds());
			returnObject.put("userNames", officeMsgSending.getUserNames());
			returnObject.put("replyMsgId", officeMsgSending.getReplyMsgId());
			returnObject.put("title", officeMsgSending.getTitle());
			returnObject.put("simpleContent", officeMsgSending.getSimpleContent());
			returnObject.put("operateType", operateType);
			returnObject.put("attachmentArray",createAttachmentArray(officeMsgSending.getAttachments()));
			jsonMap.put(getListObjectName(), returnObject);
			jsonMap.put("result", 1);
			responseJSON(jsonMap);
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
				File file;
				try {
					storageFileService.setDirPath(attachment);
					file = attachment.getFile();
					if(file !=null){
						attachmentObject.put("fileExist", true);
					}else{
						attachmentObject.put("fileExist", false);
					}
				} catch (IOException e) {
					e.printStackTrace();
					attachmentObject.put("fileExist", false);
				}

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
			List<UploadFile> uploadFileList  = StorageFileUtils.handleFiles(new String[] {}, 5*1024, 50*1024);
			MsgDto msgDto = null;
			if(Constants.MSG_STATE_SEND == officeMsgSending.getState() && officeMsgSending.getIsNeedsms()){
				msgDto = new MsgDto();
				msgDto.setUserId(officeMsgSending.getCreateUserId());
				User user = userService.getUser(userId);
				Unit unit = unitService.getUnit(unitId);
				msgDto.setUnitName(unit.getName());
				msgDto.setUnitId(unit.getId());
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
			if(StringUtils.isNotBlank(officeMsgSending.getId())){
				organizeNote(uploadFileList);
				String[] removeAttachmentArray = removeAttachment.split(",");
				officeMsgSendingService.update(officeMsgSending,uploadFileList,removeAttachmentArray,msgDto);
			}else{
				officeMsgSendingService.save(officeMsgSending, uploadFileList,msgDto);
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
			jsonMap.put(getListObjectName(), jsonError);
			jsonMap.put("result", 0);
			responseJSON(jsonMap);
		}else{
			jsonMap.put("result", 1);
			responseJSON(jsonMap);
		}
	}
	
	/**
	 * 组织消息附件
	 */
	public void organizeNote(List<UploadFile> uploadFileList){
		//转发、重新发送
		if (Constants.OPERATE_TYPE_FORWARDING.equals(operateType) || Constants.OPERATE_TYPE_RESEND.equals(operateType)) {
			// 消息附件信息
			List<Attachment> attachments = attachmentService.getAttachments(officeMsgSending.getId(), Constants.MESSAGE_ATTACHMENT);//附件
			String[] removeAttachmentArray = removeAttachment.split(",");
			if (CollectionUtils.isNotEmpty(attachments)) {
				flag:for (int i = 0; i < attachments.size(); i++) {
					Attachment attachment = attachments.get(i);
					// 判断原有的附件删除串不是空
					if (!Validators.isEmpty(removeAttachmentArray)) {
						for (int j = 0; j < removeAttachmentArray.length; j++) {
							if (attachment.getId().equals(removeAttachmentArray[j])) {
								continue flag;
							}
						}
					}
					if (attachment != null) {
						storageFileService.setDirPath(attachment);
					}
					String fileName = attachment.getFileName();
					String contentType = attachment.getContentType();
					try {
						UploadFile uploadFile = new UploadFile(fileName,attachment.getFile(),contentType,"");
						uploadFileList.add(uploadFile);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
			officeMsgSending.setId(null);
			removeAttachment = null;
		}
	}
	
	public void viewMsgSingle() throws IOException{
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
		jsonMap.put(getListObjectName(), returnObject);
		jsonMap.put("result", 1);
		responseJSON(jsonMap);
	}
	
	public void obtainUserNames() throws IOException{
		String userNames = officeMsgSendingService.getOfficeMsgSendingUserNames(msgId);
		jsonMap.put(getListObjectName(), userNames);
		jsonMap.put("result", 1);
		responseJSON(jsonMap);
	}
	
	/**
	 * 会话列表
	 * @return
	 * @throws Exception 
	 */
	public void msgDetail() throws Exception{
		officeMsgSending = officeMsgSendingService.getOfficeMsgSendingById(replyMsgId);
		if(officeMsgSending == null){
			jsonError = "信息缺失";
			jsonMap.put(getListObjectName(), jsonError);
			jsonMap.put("result", 0);
			responseJSON(jsonMap);
		}else{
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
				jsob.put("simpleContent", officeMsgSendingMap.get(receiving.getMessageId()).getSimpleContent());//无格式内容
				jsob.put("countNum", receiving.getCountNum());//会话条数
				jsob.put("dateStr", simpleDateFormat.format(receiving.getSendTime()));//时间
				jsob.put("isWithdraw", receiving.getIsWithdraw());
				items.add(jsob);
			}
			jsonMap.put(getListObjectName(), items);
			jsonMap.put("result", 1);
			responseJSON(jsonMap);
		}
	}
	
	/**
	 * 会话详细
	 * @return
	 * @throws IOException 
	 */
	public void msgDetailContent() throws IOException{
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
		jsonMap.put(getListObjectName(), returnObject);
		jsonMap.put("result", 1);
		responseJSON(jsonMap);
	}
	
	
	/**
	 * 收件箱
	 * 
	 * @return
	 */
	public String msgInbox() {
		int totalNum = officeMsgReceivingService.getNumber(userId, null);
		int unReadNum =  officeMsgReceivingService.getNumber(userId, Constants.UNREAD+"");
		return SUCCESS;
	}
	
	public void getUnReadNum() throws IOException{
		int unReadNum =  officeMsgReceivingService.getNumber(userId, Constants.UNREAD+"");
		JSONObject json = new JSONObject();
		json.put("unReadNum", unReadNum);
		jsonMap.put(getListObjectName(), json);
		jsonMap.put("result", 1);
		responseJSON(jsonMap);
	}

	/**
	 * 收件箱-信息列表
	 * TODO
	 * @return
	 * @throws Exception 
	 */
	public void remoteReceivedMessagesList() throws Exception {
		if(StringUtils.isNotBlank(searchTitle)){
			searchTitle = URLUtils.decode(searchTitle, "utf-8"); 
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
			if(StringUtils.isBlank(receiving.getMessageId()) 
					|| !officeMsgSendingMap.containsKey(receiving.getMessageId())
					|| officeMsgSendingMap.get(receiving.getMessageId()) == null){
				continue;
			}
			JSONObject jsob=new JSONObject();
			jsob.put("msgId", receiving.getId());//receiveId
			jsob.put("replyMsgId", receiving.getReplyMsgId());//会话id
			jsob.put("isRead", (receiving.getIsRead()==0?false:true));//是否已读
			jsob.put("hasAttached", (receiving.getHasAttached()==1?true:false));//是否有附件
			jsob.put("sendUsername", receiving.getSendUsername());//发件人
			jsob.put("hasStar", (receiving.getHasStar()==1?true:false));//是否加星
			jsob.put("isEmergency", (receiving.getIsEmergency()==2?true:false));//紧急
			jsob.put("title", receiving.getTitle());//标题
			jsob.put("simpleContent", officeMsgSendingMap.containsKey(receiving.getMessageId())?(officeMsgSendingMap.get(receiving.getMessageId()).getSimpleContent()):"");//无格式内容
			jsob.put("countNum", receiving.getCountNum());//会话条数
			jsob.put("dateStr", simpleDateFormat.format(receiving.getSendTime()));//时间
			jsob.put("isWithdraw", receiving.getIsWithdraw());
			items.add(jsob);
		}
		if(nowPageIndex!=page.getPageIndex()){
			items=new JSONArray();
		}
		jsonMap.put(getListObjectName(), items);
		jsonMap.put("result", 1);
		responseJSON(jsonMap);
	}
	/**
	 * 全部设置为已读
	 * @throws Exception 
	 */
	public void readAll() throws Exception{
		try {
			officeMsgReceivingService.readAll(userId);
		} catch (Exception e) {
			e.printStackTrace();
			jsonError = "设置已读失败";
		}
		if(StringUtils.isNotBlank(jsonError)){
			jsonMap.put(getListObjectName(), jsonError);
			jsonMap.put("result", 0);
			responseJSON(jsonMap);
		}else{
			jsonMap.put("result", 1);
			responseJSON(jsonMap);
		}
	}
	/**
	 * 统一加星
	 * @throws Exception 
	 */
	public void changeAllStar() throws Exception{
		int hasStarNum = booleanStrToInt(hasStar);
		try {
			officeMsgReceivingService.changeAllStar(userId,replyMsgId,hasStarNum);
		} catch (Exception e) {
			e.printStackTrace();
			jsonError = "设置失败";
		}
		if(StringUtils.isNotBlank(jsonError)){
			jsonMap.put(getListObjectName(), jsonError);
			jsonMap.put("result", 1);
			responseJSON(jsonMap);
		}else{
			jsonMap.put("result", 1);
			responseJSON(jsonMap);
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
		int hasStarNum = booleanStrToInt(hasStar);
		try {
			officeMsgReceivingService.changeStar(msgId, hasStarNum);
		} catch (Exception e) {
			e.printStackTrace();
			jsonError = "设置失败";
		}
		if(StringUtils.isNotBlank(jsonError)){
			jsonMap.put(getListObjectName(), jsonError);
			jsonMap.put("result", 0);
			responseJSON(jsonMap);
		}else{
			jsonMap.put("result", 1);
			responseJSON(jsonMap);
		}
	}
	
	public void removeMsg() throws Exception{
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
			jsonMap.put(getListObjectName(), jsonError);
			jsonMap.put("result", 0);
			responseJSON(jsonMap);
		}else{
			jsonMap.put("result", 1);
			responseJSON(jsonMap);
		}
	}
	
	public void removeMsgs() throws Exception{
		String[] deleteIdsArray = deleteIds.split(",");
		try {
			if(msgState == Constants.MSG_STATE_DRAFT || msgState == Constants.MSG_STATE_SEND){
				officeMsgSendingService.delete(deleteIdsArray,msgState);
			}else if(msgState == Constants.MSG_STATE_RECEIVE){
				officeMsgReceivingService.removeByReplyMsgIds(userId,deleteIdsArray);
			}else if(msgState == Constants.MSG_STATE_RECYCLE){
				officeMsgRecycleService.delete(deleteIdsArray);
			}else if(msgState == Constants.MSG_STATE_CUSTOMER){
				officeMsgFolderDetailService.delete(deleteIdsArray);
			}
		} catch (Exception e) {
			e.printStackTrace();
			jsonError = "删除失败";
		}
		if(StringUtils.isNotBlank(jsonError)){
			jsonMap.put(getListObjectName(), jsonError);
			jsonMap.put("result", 0);
			responseJSON(jsonMap);
		}else{
			jsonMap.put("result", 1);
			responseJSON(jsonMap);
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
		if(StringUtils.isNotBlank(searchTitle)){
			searchTitle = URLUtils.decode(searchTitle, "utf-8"); 
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
		
		jsonMap.put(getListObjectName(), items);
		jsonMap.put("result", 1);
		responseJSON(jsonMap);
	}
	
	/**
	 * 撤回消息
	 * @throws Exception 
	 */
	public void callBackMsg() throws Exception{
		try {
			officeMsgSendingService.callBackMsg(msgId);
		} catch (Exception e) {
			e.printStackTrace();
			jsonError = "撤回失败";
		}
		if(StringUtils.isNotBlank(jsonError)){
			jsonMap.put(getListObjectName(), jsonError);
			jsonMap.put("result", 0);
			responseJSON(jsonMap);
		}else{
			jsonMap.put("result", 1);
			responseJSON(jsonMap);
		}
	}
	
	public String remindSms(){
		
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
		if(StringUtils.isNotBlank(searchTitle)){
			searchTitle = URLUtils.decode(searchTitle, "utf-8"); 
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
		
		jsonMap.put(getListObjectName(), items);
		jsonMap.put("result", 1);
		responseJSON(jsonMap);
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
		if(StringUtils.isNotBlank(searchTitle)){
			searchTitle = URLUtils.decode(searchTitle, "utf-8"); 
		}
		if(!findPage()){
			return;
		}
		officeMsgFolders = officeMsgFolderService.getOfficeMsgFolderList(userId);
		officeMsgRecycles = officeMsgRecycleService.getOfficeMsgRecyclePage(userId,searchTitle,page);
	}
	
	public String revertMessage(){
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
		if (StringUtils.isBlank(pageIndex)) {
			jsonMap.put(getListObjectName(), "远程调用当前第几页不能为空");
			jsonMap.put("result", 0);
			responseJSON(jsonMap);
			return false;
		}
		if (StringUtils.isBlank(pageSize)) {
			jsonMap.put(getListObjectName(), "远程调用一页几行不能为空");
			jsonMap.put("result", 0);
			responseJSON(jsonMap);
			return false;
		}
		page=new Pagination(0,false);
		nowPageIndex = Integer.valueOf(pageIndex);
		page.setPageIndex(nowPageIndex);
		page.setPageSize(Integer.valueOf(pageSize));
		jsonMap.put("page", page);
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
				}
				// mimeType = "application/x-msdownload";
				// HttpUtility.UrlEncode 在 Encode 的时候, 将空格转换成加号('+'), 在
				// Decode 的时候将加号转为空格, 但是浏览器是不能理解加号为空格的, 所以如果文件名包含了空格,
				// 在浏览器下载得到的文件, 空格就变成了加号
				response.setHeader(
						"Content-Disposition",
						"attachment; filename=\""
								+ URLUtils.encode(attachment.getFileName(),
										"UTF-8").replace("+", "%20")
								+ "\";");

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
	
	protected String getListObjectName() {
		return "result_array";
	}
	
	protected String responseJSON(Map<String, Object> jsonMap) {
		JSONObject jsonObject = new JSONObject();
		for (String key : jsonMap.keySet()) {
			jsonObject.put(key, jsonMap.get(key));
		}
		try {
			ServletUtils.print(getResponse(), jsonObject.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return SUCCESS;
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

	public OfficeMsgSending getOfficeMsgSending() {
		return officeMsgSending;
	}

	public void setOfficeMsgSending(OfficeMsgSending officeMsgSending) {
		this.officeMsgSending = officeMsgSending;
	}

	public OfficeMsgRecycle getOfficeMsgRecycle() {
		return officeMsgRecycle;
	}

	public void setOfficeMsgRecycle(OfficeMsgRecycle officeMsgRecycle) {
		this.officeMsgRecycle = officeMsgRecycle;
	}

	public OfficeMsgReceiving getOfficeMsgReceiving() {
		return officeMsgReceiving;
	}

	public void setOfficeMsgReceiving(OfficeMsgReceiving officeMsgReceiving) {
		this.officeMsgReceiving = officeMsgReceiving;
	}

	public OfficeMsgFolderDetail getOfficeMsgFolderDetail() {
		return officeMsgFolderDetail;
	}

	public void setOfficeMsgFolderDetail(OfficeMsgFolderDetail officeMsgFolderDetail) {
		this.officeMsgFolderDetail = officeMsgFolderDetail;
	}

	public List<OfficeMsgSending> getOfficeMsgSendings() {
		return officeMsgSendings;
	}

	public void setOfficeMsgSendings(List<OfficeMsgSending> officeMsgSendings) {
		this.officeMsgSendings = officeMsgSendings;
	}

	public List<OfficeMsgRecycle> getOfficeMsgRecycles() {
		return officeMsgRecycles;
	}

	public void setOfficeMsgRecycles(List<OfficeMsgRecycle> officeMsgRecycles) {
		this.officeMsgRecycles = officeMsgRecycles;
	}

	public List<OfficeMsgReceiving> getOfficeMsgReceivings() {
		return officeMsgReceivings;
	}

	public void setOfficeMsgReceivings(List<OfficeMsgReceiving> officeMsgReceivings) {
		this.officeMsgReceivings = officeMsgReceivings;
	}

	public List<OfficeMsgFolder> getOfficeMsgFolders() {
		return officeMsgFolders;
	}

	public void setOfficeMsgFolders(List<OfficeMsgFolder> officeMsgFolders) {
		this.officeMsgFolders = officeMsgFolders;
	}

	public List<OfficeMsgFolderDetail> getOfficeMsgFolderDetails() {
		return officeMsgFolderDetails;
	}

	public void setOfficeMsgFolderDetails(
			List<OfficeMsgFolderDetail> officeMsgFolderDetails) {
		this.officeMsgFolderDetails = officeMsgFolderDetails;
	}

	public Map<String, OfficeMsgSending> getOfficeMsgSendingMap() {
		return officeMsgSendingMap;
	}

	public void setOfficeMsgSendingMap(
			Map<String, OfficeMsgSending> officeMsgSendingMap) {
		this.officeMsgSendingMap = officeMsgSendingMap;
	}

	public Pagination getPage() {
		return page;
	}

	public void setPage(Pagination page) {
		this.page = page;
	}

	public int getNowPageIndex() {
		return nowPageIndex;
	}

	public void setNowPageIndex(int nowPageIndex) {
		this.nowPageIndex = nowPageIndex;
	}

	public String getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(String pageIndex) {
		this.pageIndex = pageIndex;
	}

	public String getPageSize() {
		return pageSize;
	}

	public void setPageSize(String pageSize) {
		this.pageSize = pageSize;
	}

	public String getMsgId() {
		return msgId;
	}

	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getSmsContent() {
		return smsContent;
	}

	public void setSmsContent(String smsContent) {
		this.smsContent = smsContent;
	}

	public String getOperateType() {
		return operateType;
	}

	public void setOperateType(String operateType) {
		this.operateType = operateType;
	}

	public String getUnitId() {
		return unitId;
	}

	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUserIds() {
		return userIds;
	}

	public void setUserIds(String userIds) {
		this.userIds = userIds;
	}

	public String getSendUserName() {
		return sendUserName;
	}

	public void setSendUserName(String sendUserName) {
		this.sendUserName = sendUserName;
	}

	public String getSimpleContent() {
		return simpleContent;
	}

	public void setSimpleContent(String simpleContent) {
		this.simpleContent = simpleContent;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getReplyMsgId() {
		return replyMsgId;
	}

	public void setReplyMsgId(String replyMsgId) {
		this.replyMsgId = replyMsgId;
	}

	public String getRemoveAttachment() {
		return removeAttachment;
	}

	public void setRemoveAttachment(String removeAttachment) {
		this.removeAttachment = removeAttachment;
	}

	public Integer getMsgState() {
		return msgState;
	}

	public void setMsgState(Integer msgState) {
		this.msgState = msgState;
	}

	public String getSearchTitle() {
		return searchTitle;
	}

	public void setSearchTitle(String searchTitle) {
		this.searchTitle = searchTitle;
	}

	public String getHasStar() {
		return hasStar;
	}

	public void setHasStar(String hasStar) {
		this.hasStar = hasStar;
	}

	public String getDeleteId() {
		return deleteId;
	}

	public void setDeleteId(String deleteId) {
		this.deleteId = deleteId;
	}

	public String getDeleteIds() {
		return deleteIds;
	}

	public void setDeleteIds(String deleteIds) {
		this.deleteIds = deleteIds;
	}

	public SimpleDateFormat getSimpleDateFormat() {
		return simpleDateFormat;
	}

	public void setSimpleDateFormat(SimpleDateFormat simpleDateFormat) {
		this.simpleDateFormat = simpleDateFormat;
	}
	
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	
	public void setUnitService(UnitService unitService) {
		this.unitService = unitService;
	}

	public void setStorageFileService(StorageFileService storageFileService) {
		this.storageFileService = storageFileService;
	}
	
}
