package net.zdsoft.office.remote;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.zdsoft.eis.base.attachment.entity.Attachment;
import net.zdsoft.eis.base.attachment.service.AttachmentService;
import net.zdsoft.eis.base.common.entity.CustomRole;
import net.zdsoft.eis.base.common.entity.CustomRoleUser;
import net.zdsoft.eis.base.common.entity.Unit;
import net.zdsoft.eis.base.common.entity.User;
import net.zdsoft.eis.base.common.service.CustomRoleService;
import net.zdsoft.eis.base.common.service.CustomRoleUserService;
import net.zdsoft.eis.base.common.service.UnitService;
import net.zdsoft.eis.base.common.service.UserService;
import net.zdsoft.eis.base.constant.BaseConstant;
import net.zdsoft.eis.base.storage.StorageFileService;
import net.zdsoft.eis.base.storage.StorageFileUtils;
import net.zdsoft.eis.sms.dto.MsgDto;
import net.zdsoft.keel.util.DateUtils;
import net.zdsoft.keel.util.Pagination;
import net.zdsoft.keel.util.URLUtils;
import net.zdsoft.keel.util.Validators;
import net.zdsoft.keelcnet.action.UploadFile;
import net.zdsoft.leadin.exception.FileUploadFailException;
import net.zdsoft.leadin.util.UUIDGenerator;
import net.zdsoft.office.msgcenter.dto.MessageSearch;
import net.zdsoft.office.msgcenter.dto.ReadInfoDto;
import net.zdsoft.office.msgcenter.entity.OfficeMsgFolder;
import net.zdsoft.office.msgcenter.entity.OfficeMsgFolderDetail;
import net.zdsoft.office.msgcenter.entity.OfficeMsgReceiving;
import net.zdsoft.office.msgcenter.entity.OfficeMsgSending;
import net.zdsoft.office.msgcenter.service.OfficeMsgReceivingService;
import net.zdsoft.office.msgcenter.service.OfficeMsgSendingService;
import net.zdsoft.office.util.Constants;
import net.zdsoft.office.util.OfficeUtils;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

/**
 * 
 * @author chenlw
 *
 */
@SuppressWarnings("serial")
public class RemoteOfficeMsgCenterH5Action extends OfficeJsonBaseAction{
	
	private OfficeMsgSending officeMsgSending;//消息发送
	private OfficeMsgReceiving officeMsgReceiving;//收件箱
	
	private List<OfficeMsgSending> officeMsgSendings;
	private List<OfficeMsgReceiving> officeMsgReceivings;
	private List<OfficeMsgFolder> officeMsgFolders;
	private List<OfficeMsgFolderDetail> officeMsgFolderDetails;
	
	private Map<String, OfficeMsgSending> officeMsgSendingMap;
	
	private UserService userService;
	private UnitService unitService;
	private AttachmentService attachmentService;
	private OfficeMsgSendingService officeMsgSendingService;
	private OfficeMsgReceivingService officeMsgReceivingService;
	private StorageFileService storageFileService;
	private CustomRoleUserService customRoleUserService;
	private CustomRoleService customRoleService;
	
	private String attachmentId;
	private String receiveType;//个人 部门 单位
	private String msgId;
	private String userId;
	private String unitName;
	private String userName;
	private String smsContent;
	private String operateType;//0.保存1.发送
	private String unitId;
	private String title;
	private String userIds;
	private String sendUserName;
	private String simpleContent;
	private String replyMsgId;
	private String removeAttachment;
	private String searchStr;//查询字符
	private String hasStar;
	
	private String editType;//编辑类型：1、新增(发送)2.回复3.回复全部4.转发5.再次编辑（重发）
	private String dataType;//列表类型：1.草稿2.发件3.收件
	private boolean isNeedsms = false;//是否发短信
	SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM-dd HH:mm");
	protected Map<String, Object> jsonMap = new HashMap<String, Object>();

	/**
	 * 收件箱-信息列表
	 * @return
	 * @throws Exception 
	 */
	public void receivedMessagesList() throws Exception {
		Pagination page = getPage();
		MessageSearch search = new MessageSearch();
		search.setSearchTitleORSender(searchStr);//此处根据标题和发件人查找
		search.setMsgType(BaseConstant.MSG_TYPE_NOTE);//只要消息类型
		
		//如果有部门、单位消息接收权限，则同时也接收相关消息
		User user = userService.getUser(userId);
		boolean deptFlag= checkRole(user, "dept_receiver");
		boolean unitFlag= checkRole(user, "unit_receiver");
		if(StringUtils.isNotBlank(receiveType) && !receiveType.equals("0")){
			if(receiveType.equals(String.valueOf(Constants.DEPT))){//部门
				if(deptFlag){
					search.setReceiveDeptId(user.getDeptid());
				}
			}else if(receiveType.equals(String.valueOf(Constants.UNIT))){//单位
				if(unitFlag){
					search.setReceiveUnitId(user.getUnitid());
				}
			}else{//个人
				search.setReceiveUserId(userId);
			}
		}else{
			search.setReceiveUserId(userId);
			if(deptFlag){
				search.setReceiveDeptId(user.getDeptid());
			}
			if(unitFlag){
				search.setReceiveUnitId(user.getUnitid());
			}
		}
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
			String simpleContent="";
			if(officeMsgSendingMap.containsKey(receiving.getMessageId())){
				simpleContent = officeMsgSendingMap.get(receiving.getMessageId()).getSimpleContent();
			}
			if(StringUtils.isBlank(simpleContent)){
				simpleContent="";
			}
			jsob.put("msgId", receiving.getId());//receiveId
			jsob.put("replyMsgId", receiving.getReplyMsgId());//会话id
			jsob.put("isRead", (receiving.getIsRead()==0?false:true));//是否已读
			jsob.put("hasAttached", (receiving.getHasAttached()==1?true:false));//是否有附件
			jsob.put("photoUsername", receiving.getSendUsername());//发件人
			jsob.put("hasStar", (receiving.getHasStar()==1?true:false));//是否加星
			jsob.put("isEmergency", (receiving.getIsEmergency()==2?true:false));//是否紧急
			jsob.put("photoUrl", receiving.getPhotoUrl());//头像
			jsob.put("title", receiving.getTitle());//标题
//			jsob.put("simpleContent", simpleContent);//无格式内容
//			jsob.put("countNum", receiving.getCountNum());//会话条数
			jsob.put("dateStr", simpleDateFormat.format(receiving.getSendTime()));//时间
			jsob.put("isWithdraw", receiving.getIsWithdraw());
			items.add(jsob);
		}
		
		int msgURN = officeMsgReceivingService.getNumberForReceive(search, BaseConstant.MSG_TYPE_NOTE+"", Constants.UNREAD+"");
		jsonMap.put("msgCount", msgURN);
		jsonMap.put(getListObjectName(), items);
		jsonMap.put("result", 1);
		jsonMap.put("page", page);
		responseJSON(jsonMap);
	}
	
	/**
	 * 显示发件箱信息
	 * @return
	 * @throws Exception 
	 */
	public void sendedMessagesList() throws Exception {
		Pagination page = getPage();
		MessageSearch messageSearch = new MessageSearch();
		messageSearch.setUserId(userId);
		messageSearch.setSearchTitle(searchStr);
		messageSearch.setMsgType(BaseConstant.MSG_TYPE_NOTE);
		messageSearch.setState(Constants.MSG_STATE_SEND);
		officeMsgSendings = officeMsgSendingService.getOfficeMsgSendingList(messageSearch, page);
		JSONArray items = new JSONArray();
		for (OfficeMsgSending sending : officeMsgSendings) {
			JSONObject jsob=new JSONObject();
			jsob.put("msgId", sending.getId());//receiveId
			jsob.put("hasAttached", (sending.getHasAttached()==1?true:false));//是否有附件
			jsob.put("title", sending.getTitle());//标题
			jsob.put("photoUsername",sending.getReceivingName());
			jsob.put("photoUrl", sending.getPhotoUrl());//头像
			if(StringUtils.isBlank(sending.getSimpleContent())){
				jsob.put("simpleContent", "");//简单内容
			}else{
				jsob.put("simpleContent", sending.getSimpleContent());//简单内容
			}
			jsob.put("dateStr", simpleDateFormat.format(sending.getCreateTime()));//时间
//			jsob.put("readStr", sending.getReadStr());//已读未读人数2/3
			jsob.put("isWithdraw", sending.getIsWithdraw());//是否已撤回
			items.add(jsob);
		}
		User user = userService.getUser(userId);
		boolean deptFlag= checkRole(user, "dept_receiver");
		boolean unitFlag= checkRole(user, "unit_receiver");
		messageSearch.setReceiveUserId(userId);
		if(deptFlag){
			messageSearch.setReceiveDeptId(user.getDeptid());
		}
		if(unitFlag){
			messageSearch.setReceiveUnitId(user.getUnitid());
		}
		int msgURN = officeMsgReceivingService.getNumberForReceive(messageSearch, BaseConstant.MSG_TYPE_NOTE+"", Constants.UNREAD+"");
		jsonMap.put("msgCount", msgURN);
		jsonMap.put(getListObjectName(), items);
		jsonMap.put("result", 1);
		jsonMap.put("page", page);
		responseJSON(jsonMap);
	}
	
	
	/** 显示草稿箱信息
	 * 
	 * @return
	 * @throws Exception 
	 */
	public void draftMessagesList() throws Exception {
		Pagination page = getPage();
		MessageSearch messageSearch = new MessageSearch();
		messageSearch.setUserId(userId);
		messageSearch.setSearchTitle(searchStr);
		messageSearch.setMsgType(BaseConstant.MSG_TYPE_NOTE);
		messageSearch.setState(Constants.MSG_STATE_DRAFT);
		officeMsgSendings = officeMsgSendingService.getOfficeMsgSendingList(messageSearch, page);
		JSONArray items = new JSONArray();
		for (OfficeMsgSending sending : officeMsgSendings) {
			JSONObject jsob=new JSONObject();
			jsob.put("msgId", sending.getId());//receiveId
			jsob.put("hasAttached", (sending.getHasAttached()==1?true:false));//是否有附件
			jsob.put("title", sending.getTitle());//标题
			jsob.put("photoUsername",sending.getReceivingName());
			jsob.put("photoUrl", sending.getPhotoUrl());//头像
			if(StringUtils.isBlank(sending.getSimpleContent())){
				jsob.put("simpleContent", "");//简单内容
			}else{
				jsob.put("simpleContent", sending.getSimpleContent());//简单内容
			}
			jsob.put("dateStr", simpleDateFormat.format(sending.getCreateTime()));//时间
			items.add(jsob);
		}
		User user = userService.getUser(userId);
		boolean deptFlag= checkRole(user, "dept_receiver");
		boolean unitFlag= checkRole(user, "unit_receiver");
		messageSearch.setReceiveUserId(userId);
		if(deptFlag){
			messageSearch.setReceiveDeptId(user.getDeptid());
		}
		if(unitFlag){
			messageSearch.setReceiveUnitId(user.getUnitid());
		}
		int msgURN = officeMsgReceivingService.getNumberForReceive(messageSearch, BaseConstant.MSG_TYPE_NOTE+"", Constants.UNREAD+"");
		jsonMap.put("msgCount", msgURN);
		jsonMap.put(getListObjectName(), items);
		jsonMap.put("result", 1);
		jsonMap.put("page", page);
		responseJSON(jsonMap);
	}
	
	/**
	 * 收件箱--会话列表
	 * @return
	 * @throws Exception 
	 */
	public void messageReceiveDetail() throws Exception{
		officeMsgSending = officeMsgSendingService.getOfficeMsgSendingById(replyMsgId);
		if(officeMsgSending == null){
			jsonError = "信息缺失";
			jsonMap.put(getListObjectName(), jsonError);
			jsonMap.put("result", 0);
			responseJSON(jsonMap);
		}else{
			User user = userService.getUser(userId);
			
			officeMsgReceivings = officeMsgReceivingService.getOfficeMsgReceivingListByReplyMsgIdForMobile
					(replyMsgId, userId, checkRole(user, "dept_receiver"), checkRole(user, "unit_receiver"));
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
				String simContent = officeMsgSendingMap.get(receiving.getMessageId()).getSimpleContent();
				if(StringUtils.isBlank(simContent)){
					simContent="";
				}
				jsob.put("msgId", receiving.getId());//receiveId
				jsob.put("replyMsgId", receiving.getReplyMsgId());//会话id
				jsob.put("isRead", (receiving.getIsRead()==0?false:true));//是否已读
				jsob.put("hasAttached", (receiving.getHasAttached()==1?true:false));//是否有附件
				jsob.put("sendUsername", receiving.getSendUsername());//发件人
				jsob.put("hasStar", (receiving.getHasStar()==1?true:false));//是否加星
				jsob.put("isEmergency", (receiving.getIsEmergency()==2?true:false));//紧急
				jsob.put("title", receiving.getTitle());//标题
				jsob.put("isSendInfo", receiving.getIsSendInfo());
				jsob.put("simpleContent", simContent);//无格式内容
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
	public void messageDetailContent() throws IOException{
		officeMsgReceivingService.updateRead(msgId);
//		officeMsgFolders = officeMsgFolderService.getOfficeMsgFolderList(userId);
		officeMsgReceiving = officeMsgReceivingService.getOfficeMsgReceivingById(msgId);
		officeMsgSending = officeMsgSendingService.getOfficeMsgSendingById(officeMsgReceiving.getMessageId());
		String simContent = officeMsgSending.getSimpleContent();
		if(StringUtils.isBlank(simContent)){
			simContent="";
		}
		JSONObject returnObject = new JSONObject();
		returnObject.put("msgId", msgId);
		returnObject.put("messageId", officeMsgReceiving.getMessageId());
		returnObject.put("userIds", officeMsgSending.getUserIds());
		returnObject.put("sendUserName", officeMsgSending.getSendUserNameSimple());
		returnObject.put("userNames", officeMsgSending.getDetailNames());
		returnObject.put("replyMsgId", officeMsgSending.getReplyMsgId());
		returnObject.put("title", officeMsgSending.getTitle());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
		returnObject.put("sendTime",sdf.format(officeMsgSending.getSendTime()));
		returnObject.put("simpleContent",simContent);
		returnObject.put("attachmentArray",RemoteOfficeUtils.createAttachmentArray(officeMsgSending.getAttachments()));
		returnObject.put("isWithdraw", officeMsgSending.getIsWithdraw());
		jsonMap.put(getListObjectName(), returnObject);
		jsonMap.put("result", 1);
		responseJSON(jsonMap);
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
	private int booleanStrToInt(String hasStar){
		if("false".equals(hasStar)){
			return 0;
		}else if("true".equals(hasStar)){
			return 1;
		}
		return 0;
	}
	
	public void messageRemove() throws Exception{
		try {
//			if(msgState == Constants.MSG_STATE_DRAFT || msgState == Constants.MSG_STATE_SEND){
//				officeMsgSendingService.delete(new String[]{deleteId},msgState);
//			}else if(msgState == Constants.MSG_STATE_RECEIVE || msgState == Constants.MSG_STATE_IMPORT){
				officeMsgReceivingService.deleteById(msgId);
//			}else if(msgState == Constants.MSG_STATE_RECYCLE){
//				officeMsgRecycleService.delete(new String[]{deleteId});
//			}else if(msgState == Constants.MSG_STATE_CUSTOMER){
//				officeMsgFolderDetailService.delete(new String[]{deleteId});
//			}
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
	
	public List<OfficeMsgSending> getMsgSendings(String replyMsgid, String userid){
		List<OfficeMsgReceiving> msgReceivings = officeMsgReceivingService.getOfficeMsgReceivingListByReplyMsgId(replyMsgid, userid);
		Map<String, OfficeMsgSending> msgSendingMap = new HashMap<String, OfficeMsgSending>();
		List<OfficeMsgSending> msgSendingList = new ArrayList<OfficeMsgSending>();
		if(CollectionUtils.isNotEmpty(msgReceivings)){
			String[] msgSendingIds = new String[msgReceivings.size()];
			for(int i=0;i<msgReceivings.size();i++){
				msgSendingIds[i] = msgReceivings.get(i).getMessageId();
			}
			msgSendingMap = officeMsgSendingService.getOfficeMsgSendingMapByIds(msgSendingIds);
		}
		if(msgSendingMap.size() > 0){
			Set<String> senderids = new HashSet<String>();
			for(String key : msgSendingMap.keySet()){
				OfficeMsgSending sending = msgSendingMap.get(key);
				if(sending != null && StringUtils.isNotBlank(sending.getCreateUserId())){
					senderids.add(sending.getCreateUserId());
				}
			}
			Map<String, User> sendersMap = userService.getUserWithDelMap(senderids.toArray(new String[0]));
			for(String key : msgSendingMap.keySet()){
				OfficeMsgSending sending = msgSendingMap.get(key);
				if(sending != null && !replyMsgid.equals(sending.getId())){
					User sender = sendersMap.get(sending.getCreateUserId());
					if(sender != null){
						sending.setSendUserName(sender.getRealname());
					}
					msgSendingList.add(sending);
				}
			}
			Collections.sort(msgSendingList, new Comparator<OfficeMsgSending>() {
				@Override
				public int compare(OfficeMsgSending o1, OfficeMsgSending o2) {
					return o2.getCreateTime().compareTo(o1.getCreateTime());
				}
			});
		}
		return msgSendingList;
	}
	
	/**
	 * 发件箱详情
	 * 2016年12月14日
	 */
	public void messageDetail(){
		JSONObject returnObject = new JSONObject();
		if(StringUtils.isNotBlank(msgId)){
			officeMsgSending = officeMsgSendingService.getOfficeMsgSendingById(msgId);
			returnObject.put("userIds", officeMsgSending.getUserIds());
			returnObject.put("msgId", msgId);
			returnObject.put("replyMsgId", officeMsgSending.getReplyMsgId());
			returnObject.put("sendUserName", officeMsgSending.getSendUserNameSimple());
			returnObject.put("title", officeMsgSending.getTitle());
			returnObject.put("sendTime", simpleDateFormat.format(officeMsgSending.getCreateTime()));
			if(StringUtils.isBlank(officeMsgSending.getSimpleContent())){
				returnObject.put("content", "");
			}else{
				returnObject.put("content", officeMsgSending.getSimpleContent());//简单内容
			}
			returnObject.put("attachmentArray",RemoteOfficeUtils.createAttachmentArray(officeMsgSending.getAttachments()));
			returnObject.put("isWithdraw", officeMsgSending.getIsWithdraw());
			if(StringUtils.isNotBlank(msgId)){
				returnObject.put("readUserArray", this.getIsReadInfo(msgId, true));//已读    数组    格式： 姓名==头像路径
			}else{
				returnObject.put("readUserArray", new JSONArray());
			}
			if(StringUtils.isNotBlank(msgId)){
				returnObject.put("unReadUserArray", this.getIsReadInfo(msgId, false));//未读    数据    格式：姓名==头像路径
			}else{
				returnObject.put("unReadUserArray", new JSONArray());
			}
			JSONArray replyArray = new JSONArray();
			
			officeMsgSendings = this.getMsgSendings(msgId, userId);
			
			Set<String> senderIds = new HashSet<String>();
			for(OfficeMsgSending msgSending : officeMsgSendings){
				senderIds.add(msgSending.getCreateUserId());
			}
			Map<String, String> userPhotoMap = userSetService.getUserPhotoMap(senderIds.toArray(new String[0]));
			
			for(OfficeMsgSending msgSending : officeMsgSendings){
				JSONObject jsonEx = new JSONObject();
				jsonEx.put("id", msgSending.getId());
				jsonEx.put("createTime", DateUtils.date2String(msgSending.getCreateTime(), "MM-dd HH:mm"));
				jsonEx.put("content", msgSending.getSimpleContent());
				
				String userURL = userPhotoMap.get(msgSending.getCreateUserId());
				jsonEx.put("userName", StringUtils.isNotBlank(userURL)?(msgSending.getSendUserName()+"=="+userURL):msgSending.getSendUserName());// 格式：姓名==头像路径
				
				jsonEx.put("hasAttached", (msgSending.getHasAttached()==1?true:false));//是否有附件
				replyArray.add(jsonEx);
			}
			returnObject.put("reply", replyArray);
//			SystemIni smsSystemIni = systemIniService.getSystemIni("SMS.TICKET");//是否有短信
			if(OfficeUtils.isDeploySMS(unitId)){
				returnObject.put("hasSms", true);
			}else{
				returnObject.put("hasSms", false);
			}
		}
		jsonMap.put(getDetailObjectName(), returnObject);
		jsonMap.put("result", 1);
		responseJSON(jsonMap);
	}
	
	/**
	 * 获取是否读件人的信息 
	 * @param msgId
	 * @param isRead
	 * @return
	 */
	public JSONArray getIsReadInfo(String msgId, boolean isRead){
		JSONArray jsonArry = new JSONArray();
		Set<String> allUserIds = new HashSet<String>();
		List<ReadInfoDto> readInfoDtos = officeMsgReceivingService.getIsReadInfo(msgId, isRead?1:0);
		for(ReadInfoDto readInfo : readInfoDtos){
			List<String> userids = readInfo.getUserIdList();
			if(CollectionUtils.isNotEmpty(userids)){
				for(String userid : userids){
					allUserIds.add(userid);
				}
			}
		}
		Map<String, User> userMap = userService.getUserWithDelMap(allUserIds.toArray(new String[0]));
		Map<String, String> userPhotoMap = userSetService.getUserPhotoMap(allUserIds.toArray(new String[0]));
		for(ReadInfoDto readInfo : readInfoDtos){
			JSONObject jsonObject = new JSONObject();
			List<String> userids = readInfo.getUserIdList();
			if(CollectionUtils.isNotEmpty(userids)){
				for(String userid : userids){
					String userName = "";
					User user = userMap.get(userid);
					if(user != null){
						userName = user.getRealname();
					}
					String url = StringUtils.isNotBlank(userPhotoMap.get(userid))?userPhotoMap.get(userid):"";
					jsonObject.put("readInfo", userName + "==" + url);
					jsonArry.add(jsonObject);
				}
			}
			List<String> detailNames=readInfo.getDetailNames();
			if(CollectionUtils.isNotEmpty(detailNames)){
				for(String detailName:detailNames){
					jsonObject.put("readInfo", detailName + "==");
					jsonArry.add(jsonObject);
				}
			}
			
		}
		return jsonArry;
	}
	
	
	/**
	 * 邮件编辑
	 * @throws IOException
	 * 2016年12月13日
	 */
	public void messageEdit() throws IOException{
		JSONObject returnObject = new JSONObject();
//		SystemIni smsSystemIni = systemIniService.getSystemIni("SMS.TICKET");//是否有短信
		if(OfficeUtils.isDeploySMS(unitId)){
			returnObject.put("hasSms", true);
		}else{
			returnObject.put("hasSms", false);
		}
		if(StringUtils.isNotBlank(msgId)){
//			officeMsgSending = officeMsgSendingService.getOfficeMsgSendingById(msgId);
			officeMsgSending = officeMsgSendingService.getOfficeMsgSendingSimpleByIdByMobile(msgId,editType);
			if(StringUtils.isBlank(officeMsgSending.getId())){
				returnObject.put("msgId", UUIDGenerator.getUUID());
			}else{
				returnObject.put("msgId", msgId);
			}
			System.out.println(returnObject.get("msgId"));
			returnObject.put("replyMsgId", officeMsgSending.getReplyMsgId());
			returnObject.put("editType", editType);
			returnObject.put("isNeedsms", officeMsgSending.getIsNeedsms());
			returnObject.put("userIds", officeMsgSending.getUserIds());
			returnObject.put("userNames", officeMsgSending.getUserNames());
			returnObject.put("title", officeMsgSending.getTitle());
			if(StringUtils.isNotBlank(officeMsgSending.getSimpleContent())){
				returnObject.put("simpleContent", officeMsgSending.getSimpleContent());
			}else{
				returnObject.put("simpleContent", "");
			}
			returnObject.put("attachmentArray",RemoteOfficeUtils.createAttachmentArray(officeMsgSending.getAttachments()));
		}
		
		jsonMap.put(getDetailObjectName(), returnObject);
		jsonMap.put("result", 1);
		responseJSON(jsonMap);
	}
	
	/**
	 * 保存发送邮件
	 * @throws Exception
	 * 2016年12月14日
	 */
	public void messageSave() throws Exception{
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
		if("0".equals(operateType)){
			officeMsgSending.setState(Constants.MSG_STATE_DRAFT);
		}else{
			officeMsgSending.setState(Constants.MSG_STATE_SEND);
		}
		officeMsgSending.setReplyMsgId(replyMsgId);
		
		officeMsgSending.setIsEmergency(1);//一般
		officeMsgSending.setIsNeedsms(isNeedsms);
		System.out.println(msgId);
		try {
			List<UploadFile> uploadFileList  = StorageFileUtils.handleFiles(new String[] {}, 5*1024, 50*1024);
			MsgDto msgDto = null;
			if(Constants.MSG_STATE_SEND == officeMsgSending.getState() && officeMsgSending.getIsNeedsms()){
				msgDto = new MsgDto();
				msgDto.setUserId(officeMsgSending.getCreateUserId());
				User user = userService.getUser(userId);
				Unit unit = unitService.getUnit(unitId);
				msgDto.setUnitName(unit.getName());
				msgDto.setUserName(user.getRealname());
				//定义发短信的内容
				String content = "您有一个新消息《" + officeMsgSending.getTitle() + "》，请查阅！("+ user.getRealname() +"-" + unit.getName() + ")";
				officeMsgSending.setSmsContent(content);
				msgDto.setContent(officeMsgSending.getSmsContent());
				msgDto.setTiming(false);
			}

			if(StringUtils.isNotBlank(officeMsgSending.getId())){
				OfficeMsgSending everMsg=officeMsgSendingService.getOfficeMsgSendingById(officeMsgSending.getId());
				//organizeNote(uploadFileList);//转发和重新发送
				if(everMsg!=null &&StringUtils.isNotBlank(everMsg.getId())){
//				organizeNote(uploadFileList);
					String[] removeAttachmentArray = removeAttachment.split(",");
					officeMsgSendingService.update(officeMsgSending,uploadFileList,removeAttachmentArray,msgDto);
				}else{
					officeMsgSendingService.save(officeMsgSending, uploadFileList,msgDto);
				}
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
			jsonMap.put(getDetailObjectName(), jsonError);
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
		//草稿发送
		if(Constants.OPERATE_TYPE_RESEND.equals(editType)){
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
//			officeMsgSending.setId("");
//			removeAttachment = "";
		}
	}
	/**
	 * 校验是否有相关权限
	 * @param userId
	 * @param roleCode
	 * @return
	 */
	public boolean checkRole(User user, String roleCode){
		boolean hasRole = true;
		if(user == null)
			return false;
		
		CustomRole role = customRoleService.getCustomRoleByRoleCode(user.getUnitid(), roleCode);
		if(role != null){
			List<CustomRoleUser> roleUserList = customRoleUserService.getCustomRoleUserList(role.getId());
			if(CollectionUtils.isNotEmpty(roleUserList)){
				hasRole = false;
				for(CustomRoleUser item : roleUserList){
					if(StringUtils.equals(user.getId(), item.getUserId())){
						return true;
					}
				}
			}
		}
		return hasRole;
	}
	protected String getListObjectName() {
		return "result_array";
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

	public void setOfficeMsgReceivingService(
			OfficeMsgReceivingService officeMsgReceivingService) {
		this.officeMsgReceivingService = officeMsgReceivingService;
	}

	public OfficeMsgSending getOfficeMsgSending() {
		return officeMsgSending;
	}

	public void setOfficeMsgSending(OfficeMsgSending officeMsgSending) {
		this.officeMsgSending = officeMsgSending;
	}

	public OfficeMsgReceiving getOfficeMsgReceiving() {
		return officeMsgReceiving;
	}

	public void setOfficeMsgReceiving(OfficeMsgReceiving officeMsgReceiving) {
		this.officeMsgReceiving = officeMsgReceiving;
	}

	public List<OfficeMsgSending> getOfficeMsgSendings() {
		return officeMsgSendings;
	}

	public void setOfficeMsgSendings(List<OfficeMsgSending> officeMsgSendings) {
		this.officeMsgSendings = officeMsgSendings;
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

	public String getHasStar() {
		return hasStar;
	}

	public void setHasStar(String hasStar) {
		this.hasStar = hasStar;
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
	@Override
	protected String getDetailObjectName() {
		return "result_object";
	}

	public String getSearchStr() {
		return searchStr;
	}

	public void setSearchStr(String searchStr) {
		this.searchStr = searchStr;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public boolean isNeedsms() {
		return isNeedsms;
	}

	public void setNeedsms(boolean isNeedsms) {
		this.isNeedsms = isNeedsms;
	}

	public String getEditType() {
		return editType;
	}

	public void setEditType(String editType) {
		this.editType = editType;
	}

	public void setCustomRoleUserService(CustomRoleUserService customRoleUserService) {
		this.customRoleUserService = customRoleUserService;
	}

	public void setCustomRoleService(CustomRoleService customRoleService) {
		this.customRoleService = customRoleService;
	}

	public String getReceiveType() {
		return receiveType;
	}

	public void setReceiveType(String receiveType) {
		this.receiveType = receiveType;
	}
	
}
