package net.zdsoft.office.msgcenter.action;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

import net.zdsoft.eis.base.attachment.entity.Attachment;
import net.zdsoft.eis.base.attachment.service.AttachmentService;
import net.zdsoft.eis.base.common.entity.CustomRole;
import net.zdsoft.eis.base.common.entity.CustomRoleUser;
import net.zdsoft.eis.base.common.entity.Module;
import net.zdsoft.eis.base.common.entity.StorageDir;
import net.zdsoft.eis.base.common.entity.Unit;
import net.zdsoft.eis.base.common.entity.User;
import net.zdsoft.eis.base.common.service.CustomRoleService;
import net.zdsoft.eis.base.common.service.CustomRoleUserService;
import net.zdsoft.eis.base.common.service.ModuleService;
import net.zdsoft.eis.base.common.service.StorageDirService;
import net.zdsoft.eis.base.common.service.UnitService;
import net.zdsoft.eis.base.common.service.UserService;
import net.zdsoft.eis.base.constant.BaseConstant;
import net.zdsoft.eis.base.storage.StorageFileService;
import net.zdsoft.eis.base.storage.StorageFileUtils;
import net.zdsoft.eis.frame.action.PageAction;
import net.zdsoft.eis.sms.dto.MsgDto;
import net.zdsoft.keel.util.DateUtils;
import net.zdsoft.keel.util.Pagination;
import net.zdsoft.keel.util.Validators;
import net.zdsoft.keelcnet.action.UploadFile;
import net.zdsoft.leadin.exception.FileUploadFailException;
import net.zdsoft.office.bulletin.entity.PushMessageTargetItem;
import net.zdsoft.office.msgcenter.dto.MessageSearch;
import net.zdsoft.office.msgcenter.dto.ReadInfoDto;
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
import net.zdsoft.office.msgcenter.service.PushMessageeService;
import net.zdsoft.office.qrCode.service.QrCodeService;
import net.zdsoft.office.util.Constants;
import net.zdsoft.office.util.OfficeUtils;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

/**
 * @author chens
 * @version 创建时间：2015-3-10 上午11:03:53
 * 
 */
@SuppressWarnings("serial")
public class MsgCenterAction extends PageAction{
	
	private Integer receiveType;//接收对象，4部门，5单位
	private Integer msgState;//消息所属文件夹类型
	private Integer detailState;//来源id(1：草稿箱，2：废件箱，3：收件箱)
	private String operateType;//操作类型（回复，回复全部，转发，重新发送，草稿箱发送）
	private Integer number;//会话数字
	private Boolean isSendInfo = false;//会话中显示的是否发件箱的数据,默认为false
	private Integer isRead;//是否已读人员
	private String smsContent;//短信内容
	private Integer totalNum;//总数
	private Integer unReadNum;//未读数量
	private Integer unSendNum;//草稿箱未发送数量
	private Boolean needNum = true;//是否显示数字
	private Boolean sendToOtherUnit = false;//发消息是否可以发送到其它单位
	
	
	private Integer fileSize = 5;//单个附件大小
	private Integer fileCountSize = 50;//总附件大小
	
	private String searchTitle;//标题
	private String searchSender;//发件人姓名
	private String searchBeginTime;//开始时间
	private String searchEndTime;//结束时间
	private Integer searchMsgType;//消息来源
	private String readType;//是否已读,""：全部，0：未读，1：已读
	
	private String msgId;//消息id
	private String folderId;//自定义信息夹id
	private String folderDetailId;//自定义信息夹详细id
	private String replyMsgId;//会话id
	private Integer hasStar;//0:不加星，1：加星
	private Integer needTodo;//0:普通，1：待办
	
	private String todayStr;//当前日期
	private String yesterdayStr;//昨天
	
	private String deleteId;//删除的id
	private String[] deleteIds;//删除的ids
	
	private String[] removeAttachment;//已删除附件的id
	
	private QrCodeService qrCodeService;
	private OfficeMsgSending officeMsgSending;//消息发送
	private OfficeMsgFolder officeMsgFolder;//自定义信息夹
	private OfficeMsgRecycle officeMsgRecycle;//废件箱
	private OfficeMsgReceiving officeMsgReceiving;//收件箱
	private OfficeMsgFolderDetail officeMsgFolderDetail;//自定义信息夹详细信息
	
	private List<OfficeMsgSending> officeMsgSendings;
	private List<OfficeMsgRecycle> officeMsgRecycles;
	private List<OfficeMsgReceiving> officeMsgReceivings;
	private List<OfficeMsgFolder> officeMsgFolders;
	private List<OfficeMsgFolderDetail> officeMsgFolderDetails;
	private List<ReadInfoDto> readInfoDtos;
	private List<User> users;
	private List<User> recentContactList; // 最近的联系人列表
	
	private Map<String, OfficeMsgSending> officeMsgSendingMap;
	
	private Module module;
	
	private UserService userService;
	private AttachmentService attachmentService;
	private StorageFileService storageFileService;
	private OfficeMsgSendingService officeMsgSendingService;
	private OfficeMsgRecycleService officeMsgRecycleService;
	private OfficeMsgReceivingService officeMsgReceivingService;
	private OfficeMsgFolderService officeMsgFolderService;
	private OfficeMsgFolderDetailService officeMsgFolderDetailService;
	private ModuleService moduleService;
	private StorageDirService storageDirService;
	private UnitService unitService;
	private CustomRoleService customRoleService;
	private CustomRoleUserService customRoleUserService;
	
	private boolean canViewQrCode = false;
	private boolean standard = true;
	private static final Integer size = 30;
	private String desktopIn;
	
	//网站推送
	private PushMessageeService pushMessageeService;
	private String pushUnitTargetItemId;
	private List<PushMessageTargetItem> pushUnitTargetItems;
	private String homePage;
	private boolean titleLink;
	private String isCheck;//是否选中
	private boolean registOff=false;//是否開啟
	
	//切换
	private boolean switchName=false;//是否切换
	
	private Boolean xinJiangDeploy;//是否新疆部署
	
	private Boolean deptReceiver;//是否是部门消息接收人
	private Boolean unitReceiver;//是否是单位消息接收人

	@Override
	public String execute() throws Exception {
		unReadNum =  officeMsgReceivingService.getNumber(getLoginUser().getUserId(), Constants.UNREAD+"");
		unSendNum = officeMsgSendingService.getUnSendNum(getLoginUser().getUserId());
		
		if(StringUtils.isBlank(desktopIn)){
			desktopIn = "0";
		}
		return SUCCESS;
	}
	
	/**
	 * 搜索本单位用户
	 * @return
	 */
	public String searchUser() {
		String searchName = getRequest().getParameter("searchTxt");
		try {
			searchName = java.net.URLDecoder.decode(searchName, "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		Pagination page = new Pagination(10, false);
		users = userService.getUsersBySearchName(null, searchName, page);
		return SUCCESS;
	}
	
	public String sendNote(){
		Integer modelId = 69002;
		if(getLoginInfo().getUnitClass() == Unit.UNIT_CLASS_EDU){
			modelId = 69052;
		}
		
		sendToOtherUnit = getLoginInfo().validateAllModelOpera(modelId,Constants.SEND_TO_OTHER_UNIT);
		//重发或者草稿箱发送使用
		if(StringUtils.isNotBlank(msgId)){
			officeMsgSending = officeMsgSendingService.getOfficeMsgSendingSimpleById(msgId, operateType);
		}else{
			officeMsgSending = new OfficeMsgSending();
		}
		return SUCCESS;
	}
	
	/**
	 * TODO 东莞发消息，通讯录非弹出模式
	 * @return
	 */
	public String sendDgNote(){
		Integer modelId = 69002;
		if(getLoginInfo().getUnitClass() == Unit.UNIT_CLASS_EDU){
			modelId = 69052;
		}
		recentContactList = officeMsgReceivingService.findReceiveUsers(getLoginUser().getUserId(), size);
		sendToOtherUnit = getLoginInfo().validateAllModelOpera(modelId,Constants.SEND_TO_OTHER_UNIT);
		//重发或者草稿箱发送使用
		if(StringUtils.isNotBlank(msgId)){
			officeMsgSending = officeMsgSendingService.getOfficeMsgSendingSimpleById(msgId, operateType);
		}else{
			officeMsgSending = new OfficeMsgSending();
		}
		return SUCCESS;
	}
	
	public String saveNote(){
		try {
			//获取附件
			List<UploadFile> uploadFileList  = StorageFileUtils.handleFiles(new String[] {}, 30*1024, 100*1024);
			byte[] bs = new byte[] {-30, -128, -115};
			String s = new String(bs);
			String content = officeMsgSending.getContent();
			String simpleContent = StringUtils.replace(officeMsgSending.getSimpleContent(), s, "");
//			if(content.indexOf("img")<0 && StringUtils.isBlank(simpleContent) && !getXinJiangDeploy()){
//				jsonError = "内容不能只包含格式";
//				return SUCCESS;
//			}
			//去掉不可见字符
			officeMsgSending.setSimpleContent(simpleContent);
			officeMsgSending.setUnitId(getUnitId());
			officeMsgSending.setCreateUserId(getLoginUser().getUserId());
			officeMsgSending.setSendUserName(getLoginInfo().getUser().getRealname());
			if(officeMsgSending.getIsNeedsms() == null){
				officeMsgSending.setIsNeedsms(false);
			}
			MsgDto msgDto = null;
			if(Constants.MSG_STATE_SEND == officeMsgSending.getState() && officeMsgSending.getIsNeedsms()){
				msgDto = new MsgDto();
				msgDto.setUserId(getLoginUser().getUserId());
				msgDto.setUnitName(getLoginInfo().getUnitName());
				msgDto.setUserName(getLoginInfo().getUser().getName());
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
				officeMsgSendingService.update(officeMsgSending,uploadFileList,removeAttachment,msgDto);
			}else{
				officeMsgSendingService.save(officeMsgSending, uploadFileList,msgDto);
			}
			if ("1".equals(isCheck)&&StringUtils.isNotBlank(pushUnitTargetItemId)) {// 发送且推送到单位网站
				officeMsgSending.setTitleLink(titleLink);
                String url = unitService.getUnit(getLoginInfo().getUnitID()).getHomepage();
				//String url="http://wpdcc.szxy.edu88.com/";
                String currentUrl = "http://"+ getRequest().getServerName()+":"+request.getServerPort()+request.getContextPath();
                if (!Validators.isEmpty(url)) {
                    getPushMessageBean(url).pushMessage(pushUnitTargetItemId, officeMsgSending, getLoginInfo(),currentUrl);
                }
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
		return SUCCESS;
	}
	
	/**
	 * 快速回复
	 * @return
	 */
	public String sendQuick(){
		String content = officeMsgSending.getContent();
		if(StringUtils.isBlank(content)){
			jsonError = "回复内容不能为空";
			return SUCCESS;
		}
		officeMsgSending.setSimpleContent(content);
		try {
			//获取附件
			officeMsgSending.setUnitId(getUnitId());
			officeMsgSending.setCreateUserId(getLoginUser().getUserId());
			officeMsgSending.setSendUserName(getLoginInfo().getUser().getRealname());
			officeMsgSending.setIsEmergency(Constants.MSG_COMMON);
			officeMsgSending.setState(Constants.MSG_STATE_SEND);
			
			if(officeMsgSending.getIsNeedsms() == null){
				officeMsgSending.setIsNeedsms(false);
			}
			MsgDto msgDto = null;
			if(Constants.MSG_STATE_SEND == officeMsgSending.getState() && officeMsgSending.getIsNeedsms()){
				msgDto = new MsgDto();
				msgDto.setUserId(getLoginUser().getUserId());
				msgDto.setUnitName(getLoginInfo().getUnitName());
				msgDto.setUserName(getLoginInfo().getUser().getName());
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
			
			officeMsgSendingService.save(officeMsgSending, null,msgDto);
		}catch (Exception e) {
			e.printStackTrace();
			jsonError = "回复失败";
		}
		return SUCCESS;
	}
	
	/**
	 * 组织消息附件
	 */
	public void organizeNote(List<UploadFile> uploadFileList){
		//转发、重新发送
		if (Constants.OPERATE_TYPE_FORWARDING.equals(operateType) || Constants.OPERATE_TYPE_RESEND.equals(operateType)) {
			// 消息附件信息
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
	
	public String viewMsgSingle(){
		String userId = getLoginUser().getUserId();
		officeMsgFolders = officeMsgFolderService.getOfficeMsgFolderList(userId);
		
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
			folderId = officeMsgFolderDetail.getFolderId();
			if(officeMsgFolderDetail.getReferenceState() == Constants.MSG_STATE_DRAFT || officeMsgFolderDetail.getReferenceState() == Constants.MSG_STATE_SEND){
				officeMsgSending = officeMsgSendingService.getOfficeMsgSendingById(officeMsgFolderDetail.getReferenceId());
			}else if(officeMsgFolderDetail.getReferenceState() == Constants.MSG_STATE_RECEIVE){
				officeMsgReceiving = officeMsgReceivingService.getOfficeMsgReceivingById(officeMsgFolderDetail.getReferenceId());
				officeMsgSending = officeMsgSendingService.getOfficeMsgSendingById(officeMsgReceiving.getMessageId());
			}
		}
		return SUCCESS;
	}
	
	/**
	 * 查看是否已读人员信息
	 * @return
	 */
	public String viewIsReadInfo(){
		readInfoDtos = officeMsgReceivingService.getIsReadInfo(msgId,isRead);
		return SUCCESS;
	}
	
	/**
	 * 个人会话列表
	 * @return
	 */
	public String msgDetail(){
		try {
			officeMsgSending = officeMsgSendingService.getOfficeMsgSendingById(replyMsgId);
			officeMsgReceivings = officeMsgReceivingService.getOfficeMsgReceivingListByReplyMsgId(replyMsgId, getLoginUser().getUserId());
			if(CollectionUtils.isNotEmpty(officeMsgReceivings)){
				String[] msgSendingIds = new String[officeMsgReceivings.size()];
				for(int i=0;i<officeMsgReceivings.size();i++){
					msgSendingIds[i] = officeMsgReceivings.get(i).getMessageId();
				}
				officeMsgSendingMap = officeMsgSendingService.getOfficeMsgSendingMapByIds(msgSendingIds);
			}
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM-dd HH:mm");
			for(OfficeMsgReceiving officeMsgReceiving:officeMsgReceivings){
				try {
					officeMsgReceiving.setDateStr(OfficeUtils.dayForWeek(officeMsgReceiving.getSendTime())+" "+simpleDateFormat.format(officeMsgReceiving.getSendTime()));
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	/**
	 * 部门会话列表
	 * @return
	 */
	public String msgDetailOther(){
		try {
			String objectId = "";
			if(receiveType == Constants.DEPT){//部门
				objectId = getLoginInfo().getUser().getDeptid();
			}else if(receiveType == Constants.UNIT){//单位
				objectId = getLoginInfo().getUnitID();
			}
			officeMsgSending = officeMsgSendingService.getOfficeMsgSendingById(replyMsgId);
			officeMsgReceivings = officeMsgReceivingService.getOfficeMsgReceivingListByReplyMsgId(replyMsgId, objectId);
			if(CollectionUtils.isNotEmpty(officeMsgReceivings)){
				String[] msgSendingIds = new String[officeMsgReceivings.size()];
				for(int i=0;i<officeMsgReceivings.size();i++){
					msgSendingIds[i] = officeMsgReceivings.get(i).getMessageId();
				}
				officeMsgSendingMap = officeMsgSendingService.getOfficeMsgSendingMapByIds(msgSendingIds);
			}
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM-dd HH:mm");
			for(OfficeMsgReceiving officeMsgReceiving:officeMsgReceivings){
				try {
					officeMsgReceiving.setDateStr(OfficeUtils.dayForWeek(officeMsgReceiving.getSendTime())+" "+simpleDateFormat.format(officeMsgReceiving.getSendTime()));
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	
	/**
	 * 会话详细
	 * @return
	 */
	public String msgDetailContent(){
		try {
			officeMsgReceivingService.updateRead(msgId);
			String userId = getLoginUser().getUserId();
			officeMsgFolders = officeMsgFolderService.getOfficeMsgFolderList(userId);
			officeMsgReceiving = officeMsgReceivingService.getOfficeMsgReceivingById(msgId);
			officeMsgSending = officeMsgSendingService.getOfficeMsgSendingById(officeMsgReceiving.getMessageId());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	/**
	 * 会话详细
	 * @return
	 */
	public String msgDetailContentOther(){
		try {
			officeMsgReceivingService.updateRead(msgId);
			String userId = getLoginUser().getUserId();
			officeMsgFolders = officeMsgFolderService.getOfficeMsgFolderList(userId);
			officeMsgReceiving = officeMsgReceivingService.getOfficeMsgReceivingById(msgId);
			officeMsgSending = officeMsgSendingService.getOfficeMsgSendingById(officeMsgReceiving.getMessageId());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	/**
	 * 移动到我的信息夹
	 * @return
	 */
	public String turnToFolder(){
		try {
			if(msgState == Constants.MSG_STATE_DRAFT || msgState == Constants.MSG_STATE_SEND){
				officeMsgSendingService.turnToFolder(deleteIds,folderId,msgState,false);
			}else if(msgState == Constants.MSG_STATE_RECEIVE){
				officeMsgReceivingService.turnToFolder(getLoginUser().getUserId(),deleteIds,folderId,false);
			}else if(msgState == Constants.MSG_STATE_RECYCLE){
				officeMsgRecycleService.turnToFolder(getLoginUser().getUserId(),deleteIds,folderId);
			}else if(msgState == Constants.MSG_STATE_CUSTOMER){
				officeMsgFolderDetailService.turnToFolder(deleteIds,folderId);
			}
		} catch (Exception e) {
			e.printStackTrace();
			jsonError = "操作失败";
		}
		return SUCCESS;
	}
	
	/**
	 * 拷贝到我的信息夹，只有收件箱、发件箱、草稿箱有拷贝到功能
	 * @return
	 */
	public String copyToFolder(){
		try {
			if(msgState == Constants.MSG_STATE_DRAFT || msgState == Constants.MSG_STATE_SEND){
				officeMsgSendingService.turnToFolder(deleteIds,folderId,msgState,true);
			}else{
				String objectId = "";
				 if(msgState == Constants.MSG_STATE_RECEIVE){
					 objectId = getLoginUser().getUserId();
				 }else if(msgState == Constants.MSG_STATE_DEPT){
					 objectId = getLoginInfo().getUser().getDeptid();
				 }else if(msgState == Constants.MSG_STATE_UNIT){
					 objectId = getLoginUser().getUnitId();
				 }
				officeMsgReceivingService.turnToFolder(objectId,deleteIds,folderId,true);
			}
		} catch (Exception e) {
			e.printStackTrace();
			jsonError = "操作失败";
		}
		return SUCCESS;
	}
	
	public String turnSingleToFolder(){
		try {
			if(msgState == Constants.MSG_STATE_DRAFT || msgState == Constants.MSG_STATE_SEND){
				officeMsgSendingService.turnSingleToFolder(msgId,folderId,msgState, false);
			}else if(msgState == Constants.MSG_STATE_RECEIVE || msgState == Constants.MSG_STATE_IMPORT){
				officeMsgReceivingService.turnSingleToFolder(msgId,folderId,Constants.MSG_STATE_RECEIVE, false);
			}else if(msgState == Constants.MSG_STATE_RECYCLE){
				officeMsgRecycleService.turnSingleToFolder(msgId,folderId);
			}else if(msgState == Constants.MSG_STATE_CUSTOMER){
				officeMsgFolderDetailService.turnSingleToFolder(msgId,folderId);
			}
		} catch (Exception e) {
			e.printStackTrace();
			jsonError = "操作失败";
		}
		return SUCCESS;
	}
	
	/**
	 * 单个拷贝到其它文件夹
	 * @return
	 */
	public String copySingleToFolder(){
		try {
			if(msgState == Constants.MSG_STATE_DRAFT || msgState == Constants.MSG_STATE_SEND){
				officeMsgSendingService.turnSingleToFolder(msgId,folderId,msgState,true);
			}else if(msgState == Constants.MSG_STATE_RECEIVE || msgState == Constants.MSG_STATE_IMPORT){
				officeMsgReceivingService.turnSingleToFolder(msgId,folderId,Constants.MSG_STATE_RECEIVE,true);
			}
		} catch (Exception e) {
			e.printStackTrace();
			jsonError = "操作失败";
		}
		return SUCCESS;
	}
	
	/**
	 * 收件箱
	 * 
	 * @return
	 */
	public String msgInboxTab() {
		return SUCCESS;
	}
	public String msgInboxUser() {
//		System.out.println("----数据查询开始----->"+DateUtils.date2String(new Date(), "yyyy-MM-dd HH:mm:ss"));
		totalNum = officeMsgReceivingService.getNumber(getLoginUser().getUserId(), null);
//		System.out.println("----数据查询中----->"+DateUtils.date2String(new Date(), "yyyy-MM-dd HH:mm:ss"));
		unReadNum =  officeMsgReceivingService.getNumber(getLoginUser().getUserId(), Constants.UNREAD+"");
//		System.out.println("----数据查询结束----->"+DateUtils.date2String(new Date(), "yyyy-MM-dd HH:mm:ss"));
		return SUCCESS;
	}
	public String msgInboxOther() {
		if(receiveType == Constants.DEPT){//部门
			totalNum = officeMsgReceivingService.getNumber(getLoginInfo().getUser().getDeptid(), null);
			unReadNum =  officeMsgReceivingService.getNumber(getLoginInfo().getUser().getDeptid(), Constants.UNREAD+"");
		}else if(receiveType == Constants.UNIT){//单位
			totalNum = officeMsgReceivingService.getNumber(getLoginUser().getUnitId(), null);
			unReadNum =  officeMsgReceivingService.getNumber(getLoginUser().getUnitId(), Constants.UNREAD+"");
		}
		return SUCCESS;
	}

	/**
	 * TODO 个人收件箱-信息列表
	 * 
	 * @return
	 */
	public String listReceivedMessagesUser() {
		String userId = getLoginUser().getUserId();
		officeMsgFolders = officeMsgFolderService.getOfficeMsgFolderList(userId);
		Date date = new Date();
		todayStr = DateUtils.date2StringByDay(date);
		date = DateUtils.addDay(date, -1);
		yesterdayStr = DateUtils.date2StringByDay(date);
		MessageSearch search = new MessageSearch();
		search.setSearchTitle(searchTitle);
		search.setSearchSender(searchSender);
		search.setSearchBeginTime(searchBeginTime);
		search.setSearchEndTime(searchEndTime);
		search.setReadType(readType);
		search.setMsgType(searchMsgType);
		search.setReceiveUserId(userId);
		officeMsgReceivings = officeMsgReceivingService.getOfficeMsgReceivingList(search, getPage());
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM-dd HH:mm");
		for(OfficeMsgReceiving officeMsgReceiving:officeMsgReceivings){
			try {
				officeMsgReceiving.setDateStr(OfficeUtils.dayForWeek(officeMsgReceiving.getSendTime())+" "+simpleDateFormat.format(officeMsgReceiving.getSendTime()));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return SUCCESS;
	}
	/**
	 * 部门收件箱列表
	 * @return
	 */
	public String listReceivedMessagesOther(){
		String userId = getLoginUser().getUserId();
		officeMsgFolders = officeMsgFolderService.getOfficeMsgFolderList(userId);
		Date date = new Date();
		todayStr = DateUtils.date2StringByDay(date);
		date = DateUtils.addDay(date, -1);
		yesterdayStr = DateUtils.date2StringByDay(date);
		MessageSearch search = new MessageSearch();
		search.setSearchTitle(searchTitle);
		search.setSearchSender(searchSender);
		search.setSearchBeginTime(searchBeginTime);
		search.setSearchEndTime(searchEndTime);
		search.setReadType(readType);
		search.setMsgType(BaseConstant.MSG_TYPE_NOTE);
		if(receiveType == Constants.DEPT){//部门
			search.setReceiveUserId(getLoginInfo().getUser().getDeptid());
		}else if(receiveType == Constants.UNIT){//单位
			search.setReceiveUserId(getLoginInfo().getUnitID());
		}
		officeMsgReceivings = officeMsgReceivingService.getOfficeMsgReceivingList(search, getPage());
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM-dd HH:mm");
		for(OfficeMsgReceiving officeMsgReceiving:officeMsgReceivings){
			try {
				officeMsgReceiving.setDateStr(OfficeUtils.dayForWeek(officeMsgReceiving.getSendTime())+" "+simpleDateFormat.format(officeMsgReceiving.getSendTime()));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return SUCCESS;
	}
	/**
	 * 全部设置为已读
	 */
	public String readAll(){
		try {
			String objectId = getLoginUser().getUserId();
			if(receiveType == Constants.DEPT){//部门
				objectId = getLoginInfo().getUser().getDeptid();
			}else if(receiveType == Constants.UNIT){//单位
				objectId = getLoginInfo().getUnitID();
			}
			officeMsgReceivingService.readAll(objectId);
		} catch (Exception e) {
			e.printStackTrace();
			jsonError = "设置已读失败";
		}
		return SUCCESS;
	}
	/**
	 * 统一设置为待办
	 */
	public String changeAllNeedTodo(){
		try {
			officeMsgReceivingService.changeAllNeedTodo(getLoginUser().getUserId(),replyMsgId,needTodo);
		} catch (Exception e) {
			e.printStackTrace();
			jsonError = "设置失败";
		}
		return SUCCESS;
	}
	/**
	 * 统一加星
	 */
	public String changeAllStar(){
		try {
			officeMsgReceivingService.changeAllStar(getLoginUser().getUserId(),replyMsgId,hasStar);
		} catch (Exception e) {
			e.printStackTrace();
			jsonError = "设置失败";
		}
		return SUCCESS;
	}
	
	/**
	 * 单个待办
	 */
	public String changeNeedTodo(){
		try {
			officeMsgReceivingService.changeNeedTodo(msgId, needTodo);
		} catch (Exception e) {
			e.printStackTrace();
			jsonError = "设置失败";
		}
		return SUCCESS;
	}
	/**
	 * 单个加星
	 */
	public String changeStar(){
		try {
			officeMsgReceivingService.changeStar(msgId, hasStar);
		} catch (Exception e) {
			e.printStackTrace();
			jsonError = "设置失败";
		}
		return SUCCESS;
	}
	
	public String removeMsg(){
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
		return SUCCESS;
	}
	
	public String removeMsgs(){
		try {
			if(msgState == Constants.MSG_STATE_DRAFT || msgState == Constants.MSG_STATE_SEND){
				officeMsgSendingService.delete(deleteIds,msgState);
			}else if(msgState == Constants.MSG_STATE_RECEIVE){
				officeMsgReceivingService.removeByReplyMsgIds(getLoginUser().getUserId(),deleteIds);
			}else if(msgState == Constants.MSG_STATE_RECYCLE){
				officeMsgRecycleService.delete(deleteIds);
			}else if(msgState == Constants.MSG_STATE_CUSTOMER){
				officeMsgFolderDetailService.delete(deleteIds);
			}
		} catch (Exception e) {
			e.printStackTrace();
			jsonError = "删除失败";
		}
		return SUCCESS;
	}
	/**
	 * 重要消息
	 * 
	 * @return
	 */
	public String msgImport() {
		return SUCCESS;
	}
	
	/**
	 * 重要消息
	 * 
	 * @return
	 */
	public String listImportMessages() {
		String userId = getLoginUser().getUserId();
		officeMsgFolders = officeMsgFolderService.getOfficeMsgFolderList(userId);
		Date date = new Date();
		todayStr = DateUtils.date2StringByDay(date);
		date = DateUtils.addDay(date, -1);
		yesterdayStr = DateUtils.date2StringByDay(date);
		MessageSearch search = new MessageSearch();
		search.setSearchTitle(searchTitle);
		search.setSearchSender(searchSender);
		search.setSearchBeginTime(searchBeginTime);
		search.setSearchEndTime(searchEndTime);
		search.setReadType(readType);
		search.setMsgType(searchMsgType);
		search.setReceiveUserId(userId);
		officeMsgReceivings = officeMsgReceivingService.getOfficeMsgReceivingImportList(search, getPage());
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM-dd HH:mm");
		for(OfficeMsgReceiving officeMsgReceiving:officeMsgReceivings){
			try {
				officeMsgReceiving.setDateStr(OfficeUtils.dayForWeek(officeMsgReceiving.getSendTime())+" "+simpleDateFormat.format(officeMsgReceiving.getSendTime()));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return SUCCESS;
	}
	
	/**
	 * 重要消息
	 * 
	 * @return
	 */
	public String msgTodo() {
		return SUCCESS;
	}
	
	/**
	 * 待办消息
	 * 
	 * @return
	 */
	public String listTodoMessages() {
		String userId = getLoginUser().getUserId();
		officeMsgFolders = officeMsgFolderService.getOfficeMsgFolderList(userId);
		Date date = new Date();
		todayStr = DateUtils.date2StringByDay(date);
		date = DateUtils.addDay(date, -1);
		yesterdayStr = DateUtils.date2StringByDay(date);
		MessageSearch search = new MessageSearch();
		search.setSearchTitle(searchTitle);
		search.setSearchSender(searchSender);
		search.setSearchBeginTime(searchBeginTime);
		search.setSearchEndTime(searchEndTime);
		search.setReadType(readType);
		search.setMsgType(searchMsgType);
		search.setReceiveUserId(userId);
		officeMsgReceivings = officeMsgReceivingService.getOfficeMsgReceivingTodoList(search, getPage());
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM-dd HH:mm");
		for(OfficeMsgReceiving officeMsgReceiving:officeMsgReceivings){
			try {
				officeMsgReceiving.setDateStr(OfficeUtils.dayForWeek(officeMsgReceiving.getSendTime())+" "+simpleDateFormat.format(officeMsgReceiving.getSendTime()));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return SUCCESS;
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
	 * 
	 * @return
	 */
	public String listSendedMessages() {
		String userId = getLoginUser().getUserId();
		officeMsgFolders = officeMsgFolderService.getOfficeMsgFolderList(userId);
		MessageSearch messageSearch = new MessageSearch();
		messageSearch.setUserId(userId);
		messageSearch.setSearchTitle(searchTitle);
		messageSearch.setMsgType(searchMsgType);
		messageSearch.setState(Constants.MSG_STATE_SEND);
		messageSearch.setSearchBeginTime(searchBeginTime);
		messageSearch.setSearchEndTime(searchEndTime);
		officeMsgSendings = officeMsgSendingService.getOfficeMsgSendingList(messageSearch, getPage());
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM-dd HH:mm");
		for(OfficeMsgSending officeMsgSending:officeMsgSendings){
			try {
				officeMsgSending.setDateStr(OfficeUtils.dayForWeek(officeMsgSending.getSendTime())+" "+simpleDateFormat.format(officeMsgSending.getSendTime()));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return SUCCESS;
	}
	
	/**
	 * 撤回消息
	 */
	public String callBackMsg(){
		try {
			officeMsgSendingService.callBackMsg(msgId);
		} catch (Exception e) {
			e.printStackTrace();
			jsonError = "撤回失败";
		}
		return SUCCESS;
	}
	
	public String remindSms(){
		try {
			MsgDto msgDto = new MsgDto();
			msgDto.setUserId(getLoginUser().getUserId());
			msgDto.setUnitName(getLoginInfo().getUnitName());
			msgDto.setUserName(getLoginInfo().getUser().getName());
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
	 */
	public String listDraftMessages() {
		unSendNum = officeMsgSendingService.getUnSendNum(getLoginUser().getUserId());
		String userId = getLoginUser().getUserId();
		officeMsgFolders = officeMsgFolderService.getOfficeMsgFolderList(userId);
		MessageSearch messageSearch = new MessageSearch();
		messageSearch.setUserId(userId);
		messageSearch.setSearchTitle(searchTitle);
		messageSearch.setMsgType(searchMsgType);
		messageSearch.setState(Constants.MSG_STATE_DRAFT);
		officeMsgSendings = officeMsgSendingService.getOfficeMsgSendingList(messageSearch, getPage());
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM-dd HH:mm");
		for(OfficeMsgSending officeMsgSending:officeMsgSendings){
			try {
				officeMsgSending.setDateStr(OfficeUtils.dayForWeek(officeMsgSending.getCreateTime())+" "+simpleDateFormat.format(officeMsgSending.getCreateTime()));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return SUCCESS;
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
	 */
	public String listAbandonMessages() {
		String userId = getLoginUser().getUserId();
		officeMsgFolders = officeMsgFolderService.getOfficeMsgFolderList(userId);
		officeMsgRecycles = officeMsgRecycleService.getOfficeMsgRecyclePage(userId,searchTitle,getPage());
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM-dd HH:mm");
		for(OfficeMsgRecycle officeMsgRecycle:officeMsgRecycles){
			try {
				officeMsgRecycle.setDateStr(OfficeUtils.dayForWeek(officeMsgRecycle.getDeleteTime())+" "+simpleDateFormat.format(officeMsgRecycle.getDeleteTime()));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return SUCCESS;
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
	
	/**
	 * 刚开始进来的界面加载使用
	 * @return
	 */
	public String listFolders() {
		String userId = getLoginUser().getUserId();
		officeMsgFolders = officeMsgFolderService.getOfficeMsgFolderList(userId);
		return SUCCESS;
	}
	public String saveFolder(){
		try {
			if(officeMsgFolderService.isExist(officeMsgFolder.getId(), getLoginUser().getUserId(),officeMsgFolder.getFolderName())){
				jsonError = "信息夹名称不能重复";
			}else{
				if(StringUtils.isNotBlank(officeMsgFolder.getId())){
					officeMsgFolderService.update(officeMsgFolder);
				}else{
					officeMsgFolder.setUserId(getLoginUser().getUserId());
					officeMsgFolderService.save(officeMsgFolder);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			jsonError = "保存失败";
		}
		return SUCCESS;
	}
	
	public String deleteFolder(){
		try {
			if(StringUtils.isNotBlank(folderId)){
				boolean flag = officeMsgFolderDetailService.isExist(folderId);
				if(flag){
					jsonError = "该文件夹下面包含数据，不能删除";
				}else{
					officeMsgFolderService.delete(new String[]{folderId});
				}
			}else{
				jsonError = "传入的值为空";
			}
		} catch (Exception e) {
			e.printStackTrace();
			jsonError = "操作失败";
		}
		return SUCCESS;
	}
	/**
	 * 自定义文件夹
	 * 
	 * @return
	 */
	public String msgFolder() {
		officeMsgFolder = officeMsgFolderService.getOfficeMsgFolderById(folderId);
		if(officeMsgFolder == null){
			officeMsgFolder = new OfficeMsgFolder();
		}
		return SUCCESS;
	}
	
	/** 显示自定义文件夹信息 */
	public String listFolderMessages() {
		String userId = getLoginUser().getUserId();
		officeMsgFolders = officeMsgFolderService.getOfficeMsgFolderList(userId);
		officeMsgFolderDetails = officeMsgFolderDetailService.getOfficeMsgFolderDetailPage(searchTitle, folderId, getPage());
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM-dd HH:mm");
		for(OfficeMsgFolderDetail officeMsgFolderDetail:officeMsgFolderDetails){
			try {
				officeMsgFolderDetail.setDateStr(OfficeUtils.dayForWeek(officeMsgFolderDetail.getCreationTime())+" "+simpleDateFormat.format(officeMsgFolderDetail.getCreationTime()));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return SUCCESS;
	}
	
	public String revertMessageFromFolder(){
		try {
			officeMsgFolderDetailService.revertMessage(folderDetailId);
		} catch (Exception e) {
			e.printStackTrace();
			jsonError = "还原失败";
		}
		return SUCCESS;
	}
	
	public String queryMessage(){
		unReadNum = officeMsgReceivingService.getNumber(getLoginUser().getUserId(), Constants.UNREAD+"");
		int moduleId = 69002;
		if(getLoginInfo().getUnitClass() == Unit.UNIT_CLASS_EDU){
			moduleId = 69052;
		}
		module = moduleService.getModuleByIntId(moduleId);//消息中心模块id
		return SUCCESS;
	}
	
	/**
	 * TODO	新消息在桌面显示
	 * @return
	 */
	public String deskMessange(){
		int moduleId = 69002;
		if(getLoginInfo().getUnitClass() == Unit.UNIT_CLASS_EDU){
			moduleId = 69052;
		}
		module = moduleService.getModuleByIntId(moduleId);//通知模块id
		MessageSearch search = new MessageSearch();
//		search.setReadType(Constants.UNREAD+"");
//		search.setMsgType(BaseConstant.MSG_TYPE_NOTE);
		search.setReceiveUserId(getLoginUser().getUserId());
		officeMsgReceivings = officeMsgReceivingService.getOfficeMsgReceivingList(search, getPage());
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM-dd");
		for(OfficeMsgReceiving officeMsgReceiving:officeMsgReceivings){
			try {
				officeMsgReceiving.setDateStr(simpleDateFormat.format(officeMsgReceiving.getSendTime()));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return SUCCESS;
	}


	public Integer getReceiveType() {
		return receiveType;
	}

	public void setReceiveType(Integer receiveType) {
		this.receiveType = receiveType;
	}

	public Integer getMsgState() {
		return msgState;
	}

	public void setMsgState(Integer msgState) {
		this.msgState = msgState;
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

	public String getSearchTitle() {
		return searchTitle;
	}

	public void setSearchTitle(String searchTitle) {
		this.searchTitle = searchTitle;
	}

	public String getSearchSender() {
		return searchSender;
	}

	public void setSearchSender(String searchSender) {
		this.searchSender = searchSender;
	}

	public String getSearchBeginTime() {
		return searchBeginTime;
	}

	public void setSearchBeginTime(String searchBeginTime) {
		this.searchBeginTime = searchBeginTime;
	}

	public String getSearchEndTime() {
		return searchEndTime;
	}

	public void setSearchEndTime(String searchEndTime) {
		this.searchEndTime = searchEndTime;
	}

	public String getReadType() {
		return readType;
	}

	public void setReadType(String readType) {
		this.readType = readType;
	}

	public String getMsgId() {
		return msgId;
	}

	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}

	public String getReplyMsgId() {
		return replyMsgId;
	}

	public void setReplyMsgId(String replyMsgId) {
		this.replyMsgId = replyMsgId;
	}

	public Integer getHasStar() {
		return hasStar;
	}

	public void setHasStar(Integer hasStar) {
		this.hasStar = hasStar;
	}

	public String getTodayStr() {
		return todayStr;
	}

	public void setTodayStr(String todayStr) {
		this.todayStr = todayStr;
	}

	public String getYesterdayStr() {
		return yesterdayStr;
	}

	public void setYesterdayStr(String yesterdayStr) {
		this.yesterdayStr = yesterdayStr;
	}

	public String[] getDeleteIds() {
		return deleteIds;
	}

	public void setDeleteIds(String[] deleteIds) {
		this.deleteIds = deleteIds;
	}

	public OfficeMsgSending getOfficeMsgSending() {
		return officeMsgSending;
	}

	public void setOfficeMsgSending(OfficeMsgSending officeMsgSending) {
		this.officeMsgSending = officeMsgSending;
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

	public List<OfficeMsgRecycle> getOfficeMsgRecycles() {
		return officeMsgRecycles;
	}

	public void setOfficeMsgRecycles(List<OfficeMsgRecycle> officeMsgRecycles) {
		this.officeMsgRecycles = officeMsgRecycles;
	}

	public void setOfficeMsgRecycleService(
			OfficeMsgRecycleService officeMsgRecycleService) {
		this.officeMsgRecycleService = officeMsgRecycleService;
	}

	public void setOfficeMsgReceivingService(
			OfficeMsgReceivingService officeMsgReceivingService) {
		this.officeMsgReceivingService = officeMsgReceivingService;
	}

	public void setOfficeMsgSendingService(
			OfficeMsgSendingService officeMsgSendingService) {
		this.officeMsgSendingService = officeMsgSendingService;
	}

	public OfficeMsgFolder getOfficeMsgFolder() {
		return officeMsgFolder;
	}

	public void setOfficeMsgFolder(OfficeMsgFolder officeMsgFolder) {
		this.officeMsgFolder = officeMsgFolder;
	}

	public List<OfficeMsgFolder> getOfficeMsgFolders() {
		return officeMsgFolders;
	}

	public void setOfficeMsgFolders(List<OfficeMsgFolder> officeMsgFolders) {
		this.officeMsgFolders = officeMsgFolders;
	}

	public void setOfficeMsgFolderService(
			OfficeMsgFolderService officeMsgFolderService) {
		this.officeMsgFolderService = officeMsgFolderService;
	}
	
	public String getFolderDetailId() {
		return folderDetailId;
	}

	public void setFolderDetailId(String folderDetailId) {
		this.folderDetailId = folderDetailId;
	}

	public String getFolderId() {
		return folderId;
	}

	public void setFolderId(String folderId) {
		this.folderId = folderId;
	}

	public void setOfficeMsgFolderDetailService(
			OfficeMsgFolderDetailService officeMsgFolderDetailService) {
		this.officeMsgFolderDetailService = officeMsgFolderDetailService;
	}

	public List<OfficeMsgFolderDetail> getOfficeMsgFolderDetails() {
		return officeMsgFolderDetails;
	}

	public void setOfficeMsgFolderDetails(
			List<OfficeMsgFolderDetail> officeMsgFolderDetails) {
		this.officeMsgFolderDetails = officeMsgFolderDetails;
	}

	public Integer getDetailState() {
		return detailState;
	}

	public void setDetailState(Integer detailState) {
		this.detailState = detailState;
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

	public String getDeleteId() {
		return deleteId;
	}

	public void setDeleteId(String deleteId) {
		this.deleteId = deleteId;
	}

	public String getOperateType() {
		return operateType;
	}

	public void setOperateType(String operateType) {
		this.operateType = operateType;
	}

	public String[] getRemoveAttachment() {
		return removeAttachment;
	}

	public void setRemoveAttachment(String[] removeAttachment) {
		this.removeAttachment = removeAttachment;
	}

	public void setAttachmentService(AttachmentService attachmentService) {
		this.attachmentService = attachmentService;
	}

	public void setStorageFileService(StorageFileService storageFileService) {
		this.storageFileService = storageFileService;
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public Boolean getIsSendInfo() {
		return isSendInfo;
	}

	public void setIsSendInfo(Boolean isSendInfo) {
		this.isSendInfo = isSendInfo;
	}

	public Integer getIsRead() {
		return isRead;
	}

	public void setIsRead(Integer isRead) {
		this.isRead = isRead;
	}

	public List<ReadInfoDto> getReadInfoDtos() {
		return readInfoDtos;
	}

	public void setReadInfoDtos(List<ReadInfoDto> readInfoDtos) {
		this.readInfoDtos = readInfoDtos;
	}

	public String getSmsContent() {
		return smsContent;
	}

	public void setSmsContent(String smsContent) {
		this.smsContent = smsContent;
	}

	public Integer getTotalNum() {
		return totalNum;
	}

	public void setTotalNum(Integer totalNum) {
		this.totalNum = totalNum;
	}

	public Integer getUnReadNum() {
		return unReadNum;
	}

	public void setUnReadNum(Integer unReadNum) {
		this.unReadNum = unReadNum;
	}

	public Boolean getNeedNum() {
		return needNum;
	}

	public void setNeedNum(Boolean needNum) {
		this.needNum = needNum;
	}

	public Map<String, OfficeMsgSending> getOfficeMsgSendingMap() {
		return officeMsgSendingMap;
	}

	public void setOfficeMsgSendingMap(
			Map<String, OfficeMsgSending> officeMsgSendingMap) {
		this.officeMsgSendingMap = officeMsgSendingMap;
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public Module getModule() {
		return module;
	}

	public void setModule(Module module) {
		this.module = module;
	}

	public void setModuleService(ModuleService moduleService) {
		this.moduleService = moduleService;
	}

	public void setStorageDirService(StorageDirService storageDirService) {
		this.storageDirService = storageDirService;
	}

	public Boolean getSendToOtherUnit() {
		return sendToOtherUnit;
	}

	public void setSendToOtherUnit(Boolean sendToOtherUnit) {
		this.sendToOtherUnit = sendToOtherUnit;
	}
	
	public void setQrCodeService(QrCodeService qrCodeService) {
		this.qrCodeService = qrCodeService;
	}

	public boolean isCanViewQrCode() {
		String needQrCode = systemIniService
				.getValue(BaseConstant.NEEDQRCODE);
		StorageDir dir = storageDirService.getActiveStorageDir();
		//二维码地址
		String detailPath = dir.getDir() + File.separator + "qr_code" + File.separator;
		String newFileName = "oaQrCode.jpg";
		File outputFile = new File(detailPath+newFileName);   
		if(outputFile.exists() && "1".equals(needQrCode)){
			canViewQrCode = true;
		}
		return canViewQrCode;
	}
	
	public void setCanViewQrCode(boolean canViewQrCode) {
		this.canViewQrCode = canViewQrCode;
	}
	
	/**
	 * 发消息是否采用标准模式，目前东莞为非标准模式，通讯录跟发消息界面在同一个界面
	 * 
	 * @return
	 */
	public boolean isStandard() {
			String standardValue = systemIniService
					.getValue(BaseConstant.SENDNOTE_STANDARD);
		if(StringUtils.isNotBlank(standardValue) && "0".equals(standardValue)){
			return false;
		}
		return true;
	}
	public boolean isRegistOff() {
		String standardValue = systemIniService
				.getValue("REGIST_OFF");
		if(StringUtils.isNotBlank(standardValue) && "1".equals(standardValue)){
			return true;
		}
		return false;
	}
	
	public void setStandard(boolean standard) {
		this.standard = standard;
	}
	
	
	public void setRegistOff(boolean registOff) {
		this.registOff = registOff;
	}

	public Integer getNeedTodo() {
		return needTodo;
	}

	public void setNeedTodo(Integer needTodo) {
		this.needTodo = needTodo;
	}

	public List<User> getRecentContactList() {
		return recentContactList;
	}

	public void setRecentContactList(List<User> recentContactList) {
		this.recentContactList = recentContactList;
	}

	public Integer getUnSendNum() {
		return unSendNum;
	}

	public void setUnSendNum(Integer unSendNum) {
		this.unSendNum = unSendNum;
	}

	public Integer getSearchMsgType() {
		return searchMsgType;
	}

	public void setSearchMsgType(Integer searchMsgType) {
		this.searchMsgType = searchMsgType;
	}

	/**
	 * @return the pushUnitTargetItemId
	 */
	public String getPushUnitTargetItemId() {
		return pushUnitTargetItemId;
	}

	/**
	 * @param pushUnitTargetItemId the pushUnitTargetItemId to set
	 */
	public void setPushUnitTargetItemId(String pushUnitTargetItemId) {
		this.pushUnitTargetItemId = pushUnitTargetItemId;
	}

	public void setPushMessageeService(PushMessageeService pushMessageeService) {
		this.pushMessageeService = pushMessageeService;
	}

	/**
	 * @return the pushUnitTargetItems
	 */
	public List<PushMessageTargetItem> getPushUnitTargetItems() {
		if (null == this.pushUnitTargetItems) {
            try {
                String url = unitService.getUnit(getLoginUser().getUnitId()).getHomepage();
            	//String url="http://wpdcc.szxy.edu88.com/";
                if (!Validators.isEmpty(url)) {
                    this.pushUnitTargetItems = getPushMessageBean(url).getAvailableTargetItems();
                    System.out.println(pushUnitTargetItems.size());
                }
                else {
                    this.pushUnitTargetItems = Collections.emptyList();
                }
            }
            catch (Exception e) {
                this.pushUnitTargetItems = Collections.emptyList();
                e.printStackTrace();
            }
        }
        return this.pushUnitTargetItems;
	}
	
	protected PushMessageeService getPushMessageBean(String url) {
		url = StringUtils.trim(url);
        String serviceUrl = url + "/Service/CommonService.asmx?wsdl";
        pushMessageeService.initialize(serviceUrl);
        return pushMessageeService;
    }
	/**
	 * @param pushUnitTargetItems the pushUnitTargetItems to set
	 */
	public void setPushUnitTargetItems(List<PushMessageTargetItem> pushUnitTargetItems) {
		this.pushUnitTargetItems = pushUnitTargetItems;
	}

	/**
	 * @return the homePage
	 */
	public String getHomePage() {
		if(homePage==null){
			return homePage=unitService.getUnit(getLoginUser().getUnitId()).getHomepage();
		}
		return homePage;
	}

	/**
	 * @param homePage the homePage to set
	 */
	public void setHomePage(String homePage) {
		this.homePage = homePage;
	}

	/**
	 * @return the titleLink
	 */
	public boolean isTitleLink() {
		return titleLink;
	}

	/**
	 * @param titleLink the titleLink to set
	 */
	public void setTitleLink(boolean titleLink) {
		this.titleLink = titleLink;
	}

	/**
	 * @return the isCheck
	 */
	public String getIsCheck() {
		return isCheck;
	}

	/**
	 * @param isCheck the isCheck to set
	 */
	public void setIsCheck(String isCheck) {
		this.isCheck = isCheck;
	}

	public void setUnitService(UnitService unitService) {
		this.unitService = unitService;
	}
	
	public String getUserName(){
		return getLoginInfo().getUser().getRealname();
	}
	
	public String getUnitName(){
		return getLoginInfo().getUnitName();
	}

	public boolean isSwitchName() {
		String standardValue = systemIniService
				.getValue("SWITCH_NAME");
		if(StringUtils.isNotBlank(standardValue) && "1".equals(standardValue)){
			return true;
		}
		return false;
	}

	public void setSwitchName(boolean switchName) {
		this.switchName = switchName;
	}

	public Boolean getXinJiangDeploy() {
		xinJiangDeploy = false;
		if (BaseConstant.SYS_DEPLOY_SCHOOL_XINJIANG.equals(getSystemDeploySchool())){
			xinJiangDeploy = true;
		}
		return xinJiangDeploy;
	}

	public void setXinJiangDeploy(Boolean xinJiangDeploy) {
		this.xinJiangDeploy = xinJiangDeploy;
	}

	public Boolean getDeptReceiver() {
		deptReceiver = true;
		CustomRole role = customRoleService.getCustomRoleByRoleCode(getUnitId(), "dept_receiver");
		if(role != null){
			List<CustomRoleUser> roleUserList = customRoleUserService.getCustomRoleUserList(role.getId());
			if(CollectionUtils.isNotEmpty(roleUserList)){
				deptReceiver = false;
				for(CustomRoleUser item : roleUserList){
					if(StringUtils.equals(getLoginInfo().getUser().getId(), item.getUserId())){
						return true;
					}
				}
			}
		}
		return deptReceiver;
	}

	public void setDeptReceiver(Boolean deptReceiver) {
		this.deptReceiver = deptReceiver;
	}

	public Boolean getUnitReceiver() {
		unitReceiver = true;
		CustomRole role = customRoleService.getCustomRoleByRoleCode(getUnitId(), "unit_receiver");
		if(role != null){
			List<CustomRoleUser> roleUserList = customRoleUserService.getCustomRoleUserList(role.getId());
			if(CollectionUtils.isNotEmpty(roleUserList)){
				unitReceiver = false;
				for(CustomRoleUser item : roleUserList){
					if(StringUtils.equals(getLoginInfo().getUser().getId(), item.getUserId())){
						return true;
					}
				}
			}
		}
		return unitReceiver;
	}

	public void setUnitReceiver(Boolean unitReceiver) {
		this.unitReceiver = unitReceiver;
	}

	public void setCustomRoleService(CustomRoleService customRoleService) {
		this.customRoleService = customRoleService;
	}

	public void setCustomRoleUserService(CustomRoleUserService customRoleUserService) {
		this.customRoleUserService = customRoleUserService;
	}

	public String getDesktopIn() {
		return desktopIn;
	}

	public void setDesktopIn(String desktopIn) {
		this.desktopIn = desktopIn;
	}
	
}
