package net.zdsoft.office.msgcenter.service.impl;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

import net.zdsoft.eis.base.attachment.entity.Attachment;
import net.zdsoft.eis.base.attachment.service.AttachmentService;
import net.zdsoft.eis.base.common.entity.Dept;
import net.zdsoft.eis.base.common.entity.Teacher;
import net.zdsoft.eis.base.common.entity.Unit;
import net.zdsoft.eis.base.common.entity.User;
import net.zdsoft.eis.base.common.service.DeptService;
import net.zdsoft.eis.base.common.service.SystemIniService;
import net.zdsoft.eis.base.common.service.TeacherService;
import net.zdsoft.eis.base.common.service.UnitService;
import net.zdsoft.eis.base.common.service.UserService;
import net.zdsoft.eis.base.constant.BaseConstant;
import net.zdsoft.eis.component.push.service.PushRemoteService;
import net.zdsoft.eis.sms.constant.SmsConstant;
import net.zdsoft.eis.sms.dto.MsgDto;
import net.zdsoft.eis.sms.dto.SendDetailDto;
import net.zdsoft.eis.sms.service.SmsClientService;
import net.zdsoft.keel.util.Pagination;
import net.zdsoft.keel.util.SecurityUtils;
import net.zdsoft.keel.util.UUIDUtils;
import net.zdsoft.keelcnet.action.UploadFile;
import net.zdsoft.leadin.common.entity.BusinessTask;
import net.zdsoft.office.msgcenter.dao.OfficeMsgSendingDao;
import net.zdsoft.office.msgcenter.dto.MessageSearch;
import net.zdsoft.office.msgcenter.dto.SmsInfoDto;
import net.zdsoft.office.msgcenter.entity.OfficeMsgFolderDetail;
import net.zdsoft.office.msgcenter.entity.OfficeMsgMainsend;
import net.zdsoft.office.msgcenter.entity.OfficeMsgReceiving;
import net.zdsoft.office.msgcenter.entity.OfficeMsgRecycle;
import net.zdsoft.office.msgcenter.entity.OfficeMsgSending;
import net.zdsoft.office.msgcenter.service.OfficeMsgFolderDetailService;
import net.zdsoft.office.msgcenter.service.OfficeMsgMainsendService;
import net.zdsoft.office.msgcenter.service.OfficeMsgReceivingService;
import net.zdsoft.office.msgcenter.service.OfficeMsgRecycleService;
import net.zdsoft.office.msgcenter.service.OfficeMsgSendingService;
import net.zdsoft.office.util.Constants;
import net.zdsoft.office.util.OfficeUtils;
/**
 * office_msg_sending
 * @author 
 * 
 */
public class OfficeMsgSendingServiceImpl implements OfficeMsgSendingService{
	
	private UserService userService;
	private DeptService deptService;
	private UnitService unitService;
	private TeacherService teacherService;
	private AttachmentService attachmentService;
	private OfficeMsgRecycleService officeMsgRecycleService;
	private OfficeMsgMainsendService officeMsgMainsendService;
	private OfficeMsgReceivingService officeMsgReceivingService;
	private OfficeMsgFolderDetailService officeMsgFolderDetailService;
	private SmsClientService smsClientService;
	private SystemIniService systemIniService;
	private PushRemoteService pushRemoteService;
	
	private OfficeMsgSendingDao officeMsgSendingDao;

	@Override
	public void save(OfficeMsgSending officeMsgSending, List<UploadFile> uploadFileList, MsgDto msgDto, boolean isNotMsg){
		if(isNotMsg){
			this.save(officeMsgSending, uploadFileList, msgDto);
		}
		else{
			if(Constants.MSG_STATE_SEND == officeMsgSending.getState()){
				String[] userIds = null;
				if(StringUtils.isNotBlank(officeMsgSending.getUserIds())){
					userIds = officeMsgSending.getUserIds().split(",");
				}
				//是否维护手机号码
				if(msgDto != null && StringUtils.isNotBlank(officeMsgSending.getUserIds())){
					SmsInfoDto smsInfoDto = getSmsInfoDto(userIds);
					List<SendDetailDto> sendDetailDtoList = smsInfoDto.getSendDetailDtos();
					if(CollectionUtils.isNotEmpty(sendDetailDtoList)){
						SmsThread smsThread = new SmsThread(msgDto, sendDetailDtoList);
						smsThread.start();
					}
				}
				if(StringUtils.isNotBlank(officeMsgSending.getUserIds())){
					//消息来源类型为空的时候，设置为消息
					if(officeMsgSending.getMsgType() == null || officeMsgSending.getMsgType() == 0){
						officeMsgSending.setMsgType(BaseConstant.MSG_TYPE_NOTE);
					}
					pushToMobile(officeMsgSending.getMsgType(),officeMsgSending.getSendUserName(),officeMsgSending.getSimpleContent(),userIds);
				}
			}
		}
	}
	
	@Override
	public void save(OfficeMsgSending officeMsgSending, List<UploadFile> uploadFileList, MsgDto msgDto){
		if(StringUtils.isEmpty(officeMsgSending.getId())){
			officeMsgSending.setId(UUIDUtils.newId());
		}
		//消息来源类型为空的时候，设置为消息
		if(officeMsgSending.getMsgType() == null || officeMsgSending.getMsgType() == 0){
			officeMsgSending.setMsgType(BaseConstant.MSG_TYPE_NOTE);
		}
		officeMsgSending.setIsRead(Constants.UNREAD);
		officeMsgSending.setIsWithdraw(false);
		if(uploadFileList!=null && uploadFileList.size() > 0){
			officeMsgSending.setHasAttached(Constants.HASATTACHMENT);
		}else{
			officeMsgSending.setHasAttached(Constants.NOATTACHMENT);
		}
		if(officeMsgSending.getCreateTime()==null){
			officeMsgSending.setCreateTime(new Date());
		}
		//如果回复id为空，那么设置成跟本身id一致
		if(StringUtils.isEmpty(officeMsgSending.getReplyMsgId())){
			officeMsgSending.setReplyMsgId(officeMsgSending.getId());
		}
		
		//主送信息表插入数据
		String[] userIds = null;
		if(StringUtils.isNotBlank(officeMsgSending.getUserIds())){
			userIds = officeMsgSending.getUserIds().split(",");
		}
		String[] deptIds = null;
		if(StringUtils.isNotBlank(officeMsgSending.getDeptIds())){
			deptIds = officeMsgSending.getDeptIds().split(",");
		}
		String[] unitIds  = null;
		if(StringUtils.isNotBlank(officeMsgSending.getUnitIds())){
			unitIds = officeMsgSending.getUnitIds().split(",");
		}
		if(StringUtils.isNotBlank(officeMsgSending.getUserIds())){
			Map<String, User> userMap = userService.getUserWithDelMap(userIds);
			saveMainSendUsers(officeMsgSending, userMap, userIds);
		}
		if(StringUtils.isNotBlank(officeMsgSending.getDeptIds())){
			Map<String, Dept> deptMap = deptService.getDeptMap(deptIds);
			saveMainSendDepts(officeMsgSending, deptMap, deptIds);
		}
		if(StringUtils.isNotBlank(officeMsgSending.getUnitIds())){
			Map<String, Unit> unitMap = unitService.getUnitMap(unitIds);
			saveMainSendUnits(officeMsgSending, unitMap, unitIds);
		}
		//TODO 如果是发送消息，那么往收件人表里插入数据
		if(Constants.MSG_STATE_SEND == officeMsgSending.getState()){
			//是否维护手机号码
			Map<String, Boolean> smsMap = null;
			if(msgDto != null && StringUtils.isNotBlank(officeMsgSending.getUserIds())){
				SmsInfoDto smsInfoDto = getSmsInfoDto(userIds);
				smsMap = smsInfoDto.getSmsMap();
				List<SendDetailDto> sendDetailDtoList = smsInfoDto.getSendDetailDtos();
				if(CollectionUtils.isNotEmpty(sendDetailDtoList)){
					SmsThread smsThread = new SmsThread(msgDto, sendDetailDtoList);
					smsThread.start();
				}
			}
			officeMsgSending.setSendTime(new Date());
			if(StringUtils.isNotBlank(officeMsgSending.getUserIds())){
				saveMsgReceivings(officeMsgSending, userIds, smsMap);
				pushToMobile(officeMsgSending.getMsgType(),officeMsgSending.getSendUserName(),officeMsgSending.getSimpleContent(),userIds);
			}
			if(StringUtils.isNotBlank(officeMsgSending.getDeptIds())){
				saveMsgReceivingNotUsers(officeMsgSending, deptIds, Constants.DEPT);
			}
			if(StringUtils.isNotBlank(officeMsgSending.getUnitIds())){
				saveMsgReceivingNotUsers(officeMsgSending, unitIds, Constants.UNIT);
			}
		}
		officeMsgSendingDao.save(officeMsgSending);
		saveAttachment(officeMsgSending, uploadFileList);
	}
	
	@Override
	public void saveByMobile(OfficeMsgSending officeMsgSending,
			List<Attachment> attachmentList, MsgDto msgDto) {
		if(StringUtils.isEmpty(officeMsgSending.getId())){
			officeMsgSending.setId(UUIDUtils.newId());
		}
		officeMsgSending.setMsgType(BaseConstant.MSG_TYPE_NOTE);
		officeMsgSending.setIsRead(Constants.UNREAD);
		officeMsgSending.setIsWithdraw(false);
		if(attachmentList!=null && attachmentList.size() > 0){
			officeMsgSending.setHasAttached(Constants.HASATTACHMENT);
		}else{
			officeMsgSending.setHasAttached(Constants.NOATTACHMENT);
		}
		if(officeMsgSending.getCreateTime()==null){
			officeMsgSending.setCreateTime(new Date());
		}
		//如果回复id为空，那么设置成跟本身id一致
		if(StringUtils.isEmpty(officeMsgSending.getReplyMsgId())){
			officeMsgSending.setReplyMsgId(officeMsgSending.getId());
		}
		
		//主送信息表插入数据
		String[] userIds = officeMsgSending.getUserIds().split(",");
		Map<String, User> userMap = userService.getUserWithDelMap(userIds);
		saveMainSendUsers(officeMsgSending, userMap, userIds);
		//如果是发送消息，那么往收件人表里插入数据
		if(Constants.MSG_STATE_SEND == officeMsgSending.getState()){
			//是否维护手机号码
			Map<String, Boolean> smsMap = null;
			if(msgDto != null){
				SmsInfoDto smsInfoDto = getSmsInfoDto(userIds);
				smsMap = smsInfoDto.getSmsMap();
				List<SendDetailDto> sendDetailDtoList = smsInfoDto.getSendDetailDtos();
				if(CollectionUtils.isNotEmpty(sendDetailDtoList)){
					SmsThread smsThread = new SmsThread(msgDto, sendDetailDtoList);
					smsThread.start();
				}
			}
			officeMsgSending.setSendTime(new Date());
			saveMsgReceivings(officeMsgSending, userIds, smsMap);
			pushToMobile(officeMsgSending.getMsgType(),officeMsgSending.getSendUserName(),officeMsgSending.getSimpleContent(),userIds);
		}
		officeMsgSendingDao.save(officeMsgSending);
		for(Attachment attachment:attachmentList){
			attachment.setObjectId(officeMsgSending.getId());
		}
		attachmentService.batchInsertAttachment(attachmentList);
	}
	
	@Override
	public void update(OfficeMsgSending officeMsgSending, List<UploadFile> uploadFileList, String[] removeAttachment, MsgDto msgDto){
		if(StringUtils.isNotBlank(officeMsgSending.getId())){
			officeMsgSending.setMsgType(BaseConstant.MSG_TYPE_NOTE);
			officeMsgSending.setIsRead(Constants.UNREAD);
			officeMsgSending.setIsWithdraw(false);
			List<Attachment> attachments = attachmentService.getAttachments(officeMsgSending.getId(), Constants.MESSAGE_ATTACHMENT);//附件
			int removeLength = 0;
			if(removeAttachment == null || removeAttachment.length == 0){
				removeLength = 0;
			}else{
				removeLength = removeAttachment.length;
			}
			if((attachments.size()-removeLength+uploadFileList.size()) > 0){
				officeMsgSending.setHasAttached(Constants.HASATTACHMENT);
			}else{
				officeMsgSending.setHasAttached(Constants.NOATTACHMENT);
			}
			if(officeMsgSending.getCreateTime()==null){
				officeMsgSending.setCreateTime(new Date());
			}
			//如果回复id为空，那么设置成跟本身id一致
			if(StringUtils.isEmpty(officeMsgSending.getReplyMsgId())){
				officeMsgSending.setReplyMsgId(officeMsgSending.getId());
			}
			
			//主送信息表插入数据
			officeMsgMainsendService.deleteByMessageId(officeMsgSending.getId());
			String[] userIds = null;
			if(StringUtils.isNotBlank(officeMsgSending.getUserIds())){
				userIds = officeMsgSending.getUserIds().split(",");
			}
			String[] deptIds = null;
			if(StringUtils.isNotBlank(officeMsgSending.getDeptIds())){
				deptIds = officeMsgSending.getDeptIds().split(",");
			}
			String[] unitIds  = null;
			if(StringUtils.isNotBlank(officeMsgSending.getUnitId())){
				unitIds = officeMsgSending.getUnitId().split(",");
			}
			if(StringUtils.isNotBlank(officeMsgSending.getUserIds())){
				Map<String, User> userMap = userService.getUserWithDelMap(userIds);
				saveMainSendUsers(officeMsgSending, userMap, userIds);
			}
			if(StringUtils.isNotBlank(officeMsgSending.getDeptIds())){
				Map<String, Dept> deptMap = deptService.getDeptMap(deptIds);
				saveMainSendDepts(officeMsgSending, deptMap, deptIds);
			}
			if(StringUtils.isNotBlank(officeMsgSending.getUnitIds())){
				Map<String, Unit> unitMap = unitService.getUnitMap(unitIds);
				saveMainSendUnits(officeMsgSending, unitMap, unitIds);
			}
			//TODO 如果是发送消息，那么往收件人表里插入数据
			if(Constants.MSG_STATE_SEND == officeMsgSending.getState()){
				//是否维护手机号码
				Map<String, Boolean> smsMap = null;
				if(msgDto != null && StringUtils.isNotBlank(officeMsgSending.getUserIds())){
					SmsInfoDto smsInfoDto = getSmsInfoDto(userIds);
					smsMap = smsInfoDto.getSmsMap();
					List<SendDetailDto> sendDetailDtoList = smsInfoDto.getSendDetailDtos();
					if(CollectionUtils.isNotEmpty(sendDetailDtoList)){
						SmsThread smsThread = new SmsThread(msgDto, sendDetailDtoList);
						smsThread.start();
					}
				}
				officeMsgSending.setSendTime(new Date());
				if(StringUtils.isNotBlank(officeMsgSending.getUserIds())){
					saveMsgReceivings(officeMsgSending, userIds, smsMap);
					pushToMobile(officeMsgSending.getMsgType(),officeMsgSending.getSendUserName(),officeMsgSending.getSimpleContent(),userIds);
				}
				if(StringUtils.isNotBlank(officeMsgSending.getDeptIds())){
					saveMsgReceivingNotUsers(officeMsgSending, deptIds, Constants.DEPT);
				}
				if(StringUtils.isNotBlank(officeMsgSending.getUnitIds())){
					saveMsgReceivingNotUsers(officeMsgSending, unitIds, Constants.UNIT);
				}
			}
			officeMsgSendingDao.update(officeMsgSending);
			if(ArrayUtils.isNotEmpty(removeAttachment)){
				attachmentService.deleteAttachments(removeAttachment);
			}
			saveAttachment(officeMsgSending, uploadFileList);
		}else{
			save(officeMsgSending,uploadFileList, msgDto);
		}
	}
	
	@Override
	public void updateByMobile(OfficeMsgSending officeMsgSending,
			List<Attachment> attachmentList, String[] removeAttachment,
			MsgDto msgDto) {
		if(StringUtils.isNotBlank(officeMsgSending.getId())){
			officeMsgSending.setMsgType(BaseConstant.MSG_TYPE_NOTE);
			officeMsgSending.setIsRead(Constants.UNREAD);
			officeMsgSending.setIsWithdraw(false);
			List<Attachment> attachments = attachmentService.getAttachments(officeMsgSending.getId(), Constants.MESSAGE_ATTACHMENT);//附件
			int removeLength = 0;
			if(removeAttachment == null || removeAttachment.length == 0){
				removeLength = 0;
			}else{
				removeLength = removeAttachment.length;
			}
			if((attachments.size()-removeLength+attachmentList.size()) > 0){
				officeMsgSending.setHasAttached(Constants.HASATTACHMENT);
			}else{
				officeMsgSending.setHasAttached(Constants.NOATTACHMENT);
			}
			if(officeMsgSending.getCreateTime()==null){
				officeMsgSending.setCreateTime(new Date());
			}
			//如果回复id为空，那么设置成跟本身id一致
			if(StringUtils.isEmpty(officeMsgSending.getReplyMsgId())){
				officeMsgSending.setReplyMsgId(officeMsgSending.getId());
			}
			
			//主送信息表插入数据
			String[] userIds = officeMsgSending.getUserIds().split(",");
			Map<String, User> userMap = userService.getUserWithDelMap(userIds);
			officeMsgMainsendService.deleteByMessageId(officeMsgSending.getId());
			saveMainSendUsers(officeMsgSending, userMap, userIds);
			//如果是发送消息，那么往收件人表里插入数据
			if(Constants.MSG_STATE_SEND == officeMsgSending.getState()){
				//是否维护手机号码
				Map<String, Boolean> smsMap = null;
				if(msgDto != null){
					SmsInfoDto smsInfoDto = getSmsInfoDto(userIds);
					smsMap = smsInfoDto.getSmsMap();
					List<SendDetailDto> sendDetailDtoList = smsInfoDto.getSendDetailDtos();
					if(CollectionUtils.isNotEmpty(sendDetailDtoList)){
						SmsThread smsThread = new SmsThread(msgDto, sendDetailDtoList);
						smsThread.start();
					}
				}
				officeMsgSending.setSendTime(new Date());
				saveMsgReceivings(officeMsgSending, userIds, smsMap);
				pushToMobile(officeMsgSending.getMsgType(),officeMsgSending.getSendUserName(),officeMsgSending.getSimpleContent(),userIds);
			}
			officeMsgSendingDao.update(officeMsgSending);
			if(ArrayUtils.isNotEmpty(removeAttachment)){
				attachmentService.deleteAttachments(removeAttachment);
			}
			for(Attachment attachment:attachmentList){
				attachment.setObjectId(officeMsgSending.getId());
			}
			attachmentService.batchInsertAttachment(attachmentList);
		}else{
			saveByMobile(officeMsgSending,attachmentList, msgDto);
		}
		
	}
	
	private class SmsThread extends Thread{
    	private MsgDto msgDto;
    	private List<SendDetailDto> sendDetailDtoList;
    	public SmsThread(MsgDto msgDto, List<SendDetailDto> sendDetailDtoList){
    		this.msgDto = msgDto;
    		this.sendDetailDtoList = sendDetailDtoList;
    	}

		@Override
		public void run() {
			smsClientService.saveSmsBatch(msgDto, sendDetailDtoList);
 		}
    }
	
	/**
	 * 推送到云服务
	 * @param msgType
	 * @param title
	 * @param content
	 * @param userIds
	 */
	private void pushToMobile(Integer msgType, String title, String content, String... userIds){
		String needQrCode = systemIniService.getValue(BaseConstant.NEEDQRCODE);
		String needPushWK = systemIniService.getValue(BaseConstant.NEED_PUSH_WK);
		if(content.length() > 100){
			content = content.substring(0, 100)+"……";
		}
		//推送到原生app
		if("1".equals(needQrCode)){
			PushThread pushThread = new PushThread(title, content, userIds);
			pushThread.start();
		}
		//推送到微课
		if("1".equals(needPushWK) && BaseConstant.WK_PUSH_TYPES_SET.contains(msgType)){
			PushToWKThread pushThread = new PushToWKThread(content, userIds);
			pushThread.start();
		}
	}
	
	private class PushThread extends Thread{
    	private String title;
    	private String content;
    	private String[] userIds;
    	public PushThread(String title, String content, String... userIds){
    		this.title = title;
    		this.content = content;
    		this.userIds = userIds;
    	}

		@Override
		public void run() {
			pushRemoteService.pushGtMsg(userIds, title, content);
 		}
    }
	
	/**
	 * 推送到微课
	 * @author chens
	 *
	 */
	private class PushToWKThread extends Thread{
    	private String content;
    	private String[] userIds;
    	/**
         * 和微课约定的字符加密串
         */
        public static final String OA_WK_VERIFY_CODE = "hKlRAvs7A4V/ryjXDMzD+rG9mOx33AijfDmysIGq97MtGcSzSu6TSQ==";
        public static final int msgType = 1;//消息类型，HTML类型：7，图文类型：3，纯文本：1
        public String auth = getVerifyKey();//约定字符串微课这边提供，前后两个时间戳要一样
        public static final int isNeedAppendToken = 1;//判断是否要在推送内容的链接中带上
        public PushToWKThread(String content, String... userIds){
    		this.content = content;
    		this.userIds = userIds;
    	}
        
        public String getVerifyKey() {
            // code = 约定字符串+时间戳
            // 密文 = MD5（微课约定字符串 + 时间戳） + 时间戳
            String updatestamp = String.valueOf(System.currentTimeMillis());
            String verifyKey = SecurityUtils.encodeByMD5(OA_WK_VERIFY_CODE
                    + updatestamp)
                    + updatestamp;
            return verifyKey;
        }

		@Override
		public void run() {
			String url = systemIniService.getValue(BaseConstant.WK_URL);
			String appId = systemIniService.getValue(BaseConstant.WK_APPID);//第三方应用的ID,由微课这边提供
			String publicId = systemIniService.getValue(BaseConstant.WK_PUBLICID);//用来推送消息的公众号ID，由微课这边提供
			if(StringUtils.isBlank(url) ||
				StringUtils.isBlank(appId) ||
				StringUtils.isBlank(publicId)){
				return;
			}
			String tokens = "['"+StringUtils.join(userIds, "','")+"']";//要发送的用户对象,格式[‘ownerId1’,’ownerId2’,’ownerId3’..]
			
			StringBuffer param = new StringBuffer();
			param.append("appId=").append(appId);
			param.append("&publicId=").append(publicId);
			param.append("&tokens=").append(tokens);
			param.append("&msgType=").append(msgType);
			param.append("&auth=").append(auth);
			try {
				param.append("&content=").append(URLEncoder.encode(content,"UTF-8"));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			param.append("&isNeedAppendToken=").append(isNeedAppendToken);
			OfficeUtils.sendPost(url, param.toString());
 		}
    }
	
	public void saveMainSendUsers(OfficeMsgSending officeMsgSending, Map<String, User> userMap, String[] userIds){
		List<OfficeMsgMainsend> officeMsgMainsends = new ArrayList<OfficeMsgMainsend>();
		for(String userId:userIds){
			OfficeMsgMainsend officeMsgMainsend = new OfficeMsgMainsend();
			officeMsgMainsend.setMessageId(officeMsgSending.getId());
			officeMsgMainsend.setReceiverId(userId);
			User user = userMap.get(userId);
			if(user!=null){
				officeMsgMainsend.setReceiverName(user.getRealname());
			}
			officeMsgMainsend.setReceiverType(Constants.TEACHER);
			officeMsgMainsends.add(officeMsgMainsend);
		}
		officeMsgMainsendService.batchSave(officeMsgMainsends);
	}
	
	public void saveMainSendDepts(OfficeMsgSending officeMsgSending, Map<String, Dept> deptMap, String[] deptIds){
		List<OfficeMsgMainsend> officeMsgMainsends = new ArrayList<OfficeMsgMainsend>();
		for(String deptId:deptIds){
			OfficeMsgMainsend officeMsgMainsend = new OfficeMsgMainsend();
			officeMsgMainsend.setMessageId(officeMsgSending.getId());
			officeMsgMainsend.setReceiverId(deptId);
			Dept dept = deptMap.get(deptId);
			if(dept!=null){
				officeMsgMainsend.setReceiverName(dept.getDeptname());
			}
			officeMsgMainsend.setReceiverType(Constants.DEPT);
			officeMsgMainsends.add(officeMsgMainsend);
		}
		officeMsgMainsendService.batchSave(officeMsgMainsends);
	}
	
	public void saveMainSendUnits(OfficeMsgSending officeMsgSending, Map<String, Unit> unitMap, String[] unitIds){
		List<OfficeMsgMainsend> officeMsgMainsends = new ArrayList<OfficeMsgMainsend>();
		for(String unitId:unitIds){
			OfficeMsgMainsend officeMsgMainsend = new OfficeMsgMainsend();
			officeMsgMainsend.setMessageId(officeMsgSending.getId());
			officeMsgMainsend.setReceiverId(unitId);
			Unit unit = unitMap.get(unitId);
			if(unit!=null){
				officeMsgMainsend.setReceiverName(unit.getName());
			}
			officeMsgMainsend.setReceiverType(Constants.UNIT);
			officeMsgMainsends.add(officeMsgMainsend);
		}
		officeMsgMainsendService.batchSave(officeMsgMainsends);
	}
	
	public void saveMsgReceivings(OfficeMsgSending officeMsgSending, String[] userIds, Map<String, Boolean> smsMap){
		List<OfficeMsgReceiving> officeMsgReceivings = new ArrayList<OfficeMsgReceiving>();
		for(String userId:userIds){
			OfficeMsgReceiving officeMsgReceiving = new OfficeMsgReceiving();
			officeMsgReceiving.setMessageId(officeMsgSending.getId());
			officeMsgReceiving.setSendUserId(officeMsgSending.getCreateUserId());
			officeMsgReceiving.setReceiveUserId(userId);
			officeMsgReceiving.setSendUsername(officeMsgSending.getSendUserName());
			officeMsgReceiving.setTitle(officeMsgSending.getTitle());
			officeMsgReceiving.setReceiverType(Constants.TEACHER);
			officeMsgReceiving.setMsgType(officeMsgSending.getMsgType());
			officeMsgReceiving.setIsRead(Constants.UNREAD);
			officeMsgReceiving.setIsDeleted(false);
			officeMsgReceiving.setState(Constants.MSG_STATE_RECEIVE);
			officeMsgReceiving.setIsEmergency(officeMsgSending.getIsEmergency());
			officeMsgReceiving.setSendTime(new Date());
			officeMsgReceiving.setIsDownloadAttachment(false);
			if(smsMap!=null && smsMap.get(userId)!=null){
				officeMsgReceiving.setIsSmsReceived(smsMap.get(userId));
			}else{
				officeMsgReceiving.setIsSmsReceived(false);
			}
			officeMsgReceiving.setReplyMsgId(officeMsgSending.getReplyMsgId());
			officeMsgReceiving.setHasAttached(officeMsgSending.getHasAttached());
			officeMsgReceiving.setHasStar(Constants.NOSTAR);
			officeMsgReceiving.setIsWithdraw(false);
			officeMsgReceivings.add(officeMsgReceiving);
		}
		officeMsgReceivingService.batchSave(officeMsgReceivings);
	}
	
	public void saveMsgReceivingNotUsers(OfficeMsgSending officeMsgSending, String[] ids, Integer receiverType){
		List<OfficeMsgReceiving> officeMsgReceivings = new ArrayList<OfficeMsgReceiving>();
		for(String id:ids){
			OfficeMsgReceiving officeMsgReceiving = new OfficeMsgReceiving();
			officeMsgReceiving.setMessageId(officeMsgSending.getId());
			officeMsgReceiving.setSendUserId(officeMsgSending.getCreateUserId());
			officeMsgReceiving.setReceiveUserId(id);
			officeMsgReceiving.setSendUsername(officeMsgSending.getSendUserName());
			officeMsgReceiving.setTitle(officeMsgSending.getTitle());
			officeMsgReceiving.setReceiverType(receiverType);
			officeMsgReceiving.setMsgType(officeMsgSending.getMsgType());
			officeMsgReceiving.setIsRead(Constants.UNREAD);
			officeMsgReceiving.setIsDeleted(false);
			officeMsgReceiving.setState(Constants.MSG_STATE_RECEIVE);
			officeMsgReceiving.setIsEmergency(officeMsgSending.getIsEmergency());
			officeMsgReceiving.setSendTime(new Date());
			officeMsgReceiving.setIsDownloadAttachment(false);
			officeMsgReceiving.setIsSmsReceived(false);
			officeMsgReceiving.setReplyMsgId(officeMsgSending.getReplyMsgId());
			officeMsgReceiving.setHasAttached(officeMsgSending.getHasAttached());
			officeMsgReceiving.setHasStar(Constants.NOSTAR);
			officeMsgReceiving.setIsWithdraw(false);
			officeMsgReceivings.add(officeMsgReceiving);
		}
		officeMsgReceivingService.batchSave(officeMsgReceivings);
	}
	
	public void saveAttachment(OfficeMsgSending officeMsgSending, List<UploadFile> uploadFileList){
		//保存附件
		if(!CollectionUtils.isEmpty(uploadFileList)){
			for (UploadFile uploadFile : uploadFileList) {
				Attachment attachment = new Attachment();
				attachment.setFileName(uploadFile.getFileName());
				attachment.setContentType(uploadFile.getContentType());
				attachment.setFileSize(uploadFile.getFileSize());
				attachment.setUnitId(officeMsgSending.getUnitId());
				attachment.setObjectId(officeMsgSending.getId());
				attachment.setObjectType(Constants.MESSAGE_ATTACHMENT);
				attachment.setConStatus(BusinessTask.TASK_STATUS_NOT_NEED_HAND);
				attachmentService.saveAttachment(attachment, uploadFile, false);
			}
		}
	}
	
	public SmsInfoDto getSmsInfoDto(String[] userIds){
		
		List<User> users = userService.getUsersWithDel(userIds);
		Set<String> teacherIds = new HashSet<String>();
		for(User user:users){
			teacherIds.add(user.getTeacherid());
		}
		Map<String, Teacher> teacherMap = teacherService.getTeacherWithDeletedMap(teacherIds.toArray(new String[0]));
		
		Map<String, Boolean> smsMap = new HashMap<String, Boolean>();//是否维护手机号码
		List<SendDetailDto> sendDetailDtos = new ArrayList<SendDetailDto>();//需要发送短信的对象list
		for(User user:users){
			Teacher teacher = teacherMap.get(user.getTeacherid());
			if(teacher!=null && StringUtils.isNotBlank(teacher.getPersonTel())){
				SendDetailDto sendDetailDto = new SendDetailDto();
				sendDetailDto.setReceiverId(user.getId());// 短信接收人userId,这里实现的是自由短信,无对应用户id
				sendDetailDto.setReceiverName(user.getRealname());// 短信接收人用户名,这里实现的是自由短信,用手机号码替代用户名
				sendDetailDto.setBusinessType(SmsConstant.SMS_BUSINESS_PAY);// Sdk收费短信
				sendDetailDto.setMobile(teacher.getPersonTel());// 短信接收人手机号码
				sendDetailDto.setReceiverType(User.TEACHER_LOGIN);// 短信接收用户类型:教师用户,学生用户,家长用户
				sendDetailDto.setUnitId(user.getUnitid());// 短信接收用户单位id
				sendDetailDtos.add(sendDetailDto);
				smsMap.put(user.getId(), true);
			}else{
				smsMap.put(user.getId(), false);
			}
		}
		SmsInfoDto smsInfoDto = new SmsInfoDto();
		smsInfoDto.setSmsMap(smsMap);
		smsInfoDto.setSendDetailDtos(sendDetailDtos);
		return smsInfoDto;
	}
	
	@Override
	public void callBackMsg(String msgId) {
		OfficeMsgSending officeMsgSending = officeMsgSendingDao.getOfficeMsgSendingById(msgId);
		String newId = UUIDUtils.newId();
		officeMsgSending.setId(newId);
		officeMsgSending.setState(Constants.MSG_STATE_DRAFT);
		officeMsgSending.setSendTime(null);
		officeMsgSending.setReplyMsgId(newId);
		
		List<Attachment> attachments = attachmentService.getAttachments(msgId, Constants.MESSAGE_ATTACHMENT);//附件
		
		List<OfficeMsgMainsend> officeMsgMainsends = officeMsgMainsendService.getOfficeMsgMainsendList(msgId);
		for(OfficeMsgMainsend officeMsgMainsend:officeMsgMainsends){
			officeMsgMainsend.setId(UUIDUtils.newId());
			officeMsgMainsend.setMessageId(newId);
		}
		//撤回
		officeMsgSendingDao.updateWithdraw(msgId);
		//将撤回的老信息删除，草稿箱新建一条，老数据为了让原先发出去的可以看到被撤回的数据 	TODO
		officeMsgSendingDao.updateStateByIds(new String[]{msgId}, Constants.MSG_STATE_RECYCLE);
		officeMsgReceivingService.updateIsWithdraw(msgId);
		
		officeMsgSendingDao.save(officeMsgSending);
		officeMsgMainsendService.batchSave(officeMsgMainsends);
		for(Attachment attachment:attachments){
			attachment.setId(UUIDUtils.newId());
			attachment.setObjectId(newId);
			attachmentService.saveAttachment(attachment);
		}
	}

	@Override
	public void delete(String[] ids, Integer state){
		List<OfficeMsgSending> officeMsgSendings = officeMsgSendingDao.getOfficeMsgSendingList(ids);
		List<OfficeMsgRecycle> officeMsgRecycles = new ArrayList<OfficeMsgRecycle>();
		for(OfficeMsgSending officeMsgSending:officeMsgSendings){
			OfficeMsgRecycle officeMsgRecycle = new OfficeMsgRecycle();
			officeMsgRecycle.setDeleteTime(new Date());
			officeMsgRecycle.setIsEmergency(officeMsgSending.getIsEmergency());
			officeMsgRecycle.setMsgtype(officeMsgSending.getMsgType());
			officeMsgRecycle.setReferenceId(officeMsgSending.getId());
			officeMsgRecycle.setSendTime(officeMsgSending.getSendTime());
			officeMsgRecycle.setState(state);
			officeMsgRecycle.setTitle(officeMsgSending.getTitle());
			officeMsgRecycle.setUserId(officeMsgSending.getCreateUserId());
			officeMsgRecycles.add(officeMsgRecycle);
		}
		officeMsgRecycleService.batchSave(officeMsgRecycles);
		officeMsgSendingDao.updateStateByIds(ids, Constants.MSG_STATE_RECYCLE);
	}
	
	@Override
	public void turnToFolder(String[] ids, String folderId, Integer msgState, boolean isCopy) {
		List<OfficeMsgSending> officeMsgSendings = officeMsgSendingDao.getOfficeMsgSendingList(ids);
		List<OfficeMsgFolderDetail> officeMsgFolderDetails = new ArrayList<OfficeMsgFolderDetail>();
		for(OfficeMsgSending officeMsgSending:officeMsgSendings){
			OfficeMsgFolderDetail officeMsgFolderDetail = new OfficeMsgFolderDetail();
			officeMsgFolderDetail.setCreationTime(new Date());
			officeMsgFolderDetail.setFolderId(folderId);
			officeMsgFolderDetail.setIsEmergency(officeMsgSending.getIsEmergency());
			officeMsgFolderDetail.setReferenceId(officeMsgSending.getId());
			officeMsgFolderDetail.setSendTime(officeMsgSending.getSendTime());
			officeMsgFolderDetail.setTitle(officeMsgSending.getTitle());
			officeMsgFolderDetail.setUserId(officeMsgSending.getCreateUserId());
			officeMsgFolderDetail.setMsgType(officeMsgSending.getMsgType());
			officeMsgFolderDetail.setReferenceState(msgState);
			officeMsgFolderDetail.setIsDeleted(false);
			if(isCopy){
				officeMsgFolderDetail.setIsCopy(Constants.COPY);
			}else{
				officeMsgFolderDetail.setIsCopy(Constants.MOVE);
			}
			officeMsgFolderDetails.add(officeMsgFolderDetail);
		}
		officeMsgFolderDetailService.batchSave(officeMsgFolderDetails);
		//不是拷贝的，要调整发件箱的状态
		if(!isCopy){
			officeMsgSendingDao.updateStateByIds(ids, Constants.MSG_STATE_CUSTOMER);
		}
	}
	
	@Override
	public void turnSingleToFolder(String id, String folderId, Integer state, boolean isCopy) {
		OfficeMsgSending officeMsgSending = officeMsgSendingDao.getOfficeMsgSendingById(id);
		OfficeMsgFolderDetail officeMsgFolderDetail = new OfficeMsgFolderDetail();
		officeMsgFolderDetail.setCreationTime(new Date());
		officeMsgFolderDetail.setFolderId(folderId);
		officeMsgFolderDetail.setIsEmergency(officeMsgSending.getIsEmergency());
		officeMsgFolderDetail.setReferenceId(officeMsgSending.getId());
		officeMsgFolderDetail.setSendTime(officeMsgSending.getSendTime());
		officeMsgFolderDetail.setTitle(officeMsgSending.getTitle());
		officeMsgFolderDetail.setUserId(officeMsgSending.getCreateUserId());
		officeMsgFolderDetail.setMsgType(officeMsgSending.getMsgType());
		officeMsgFolderDetail.setReferenceState(state);
		officeMsgFolderDetail.setIsDeleted(false);
		if(isCopy){
			officeMsgFolderDetail.setIsCopy(Constants.COPY);
		}else{
			officeMsgFolderDetail.setIsCopy(Constants.MOVE);
		}
		officeMsgFolderDetailService.save(officeMsgFolderDetail);
		//拷贝过去的不需要更新数据state
		if(!isCopy){
			officeMsgSendingDao.updateStateByIds(new String[]{id}, Constants.MSG_STATE_CUSTOMER);
		}
	}
	
	@Override
	public void revertById(String id, Integer state) {
		officeMsgSendingDao.updateRevertById(id, state);
	}

	@Override
	public OfficeMsgSending getOfficeMsgSendingSimpleById(String id,
			String operateType) {
		OfficeMsgSending officeMsgSending = officeMsgSendingDao.getOfficeMsgSendingById(id);
		List<Attachment> attachments = attachmentService.getAttachments(id, Constants.MESSAGE_ATTACHMENT);//附件
		if(Constants.OPERATE_TYPE_FORWARDING.equals(operateType)){//转发
			officeMsgSending.setReplyMsgId("");
			officeMsgSending.setTitle("转发："+officeMsgSending.getTitle());
			if(attachments.size()>0)
				officeMsgSending.setAttachments(attachments);
		}else if(Constants.OPERATE_TYPE_REPLY.equals(operateType)){//回复
			officeMsgSending.setId("");
			officeMsgSending.setTitle("回复："+officeMsgSending.getTitle());
			officeMsgSending.setContent("");
			officeMsgSending.setSimpleContent("");
		}else if(Constants.OPERATE_TYPE_REPLY_ALL.equals(operateType)){//回复全部
			officeMsgSending.setId("");
			officeMsgSending.setTitle("回复："+officeMsgSending.getTitle());
			officeMsgSending.setContent("");
			officeMsgSending.setSimpleContent("");
		}else if(Constants.OPERATE_TYPE_RESEND.equals(operateType)){//重新发送
			officeMsgSending.setReplyMsgId("");
			if(attachments.size()>0)
				officeMsgSending.setAttachments(attachments);
		}
		if(Constants.OPERATE_TYPE_REPLY.equals(operateType)){//回复
			User user = userService.getUser(officeMsgSending.getCreateUserId());
			officeMsgSending.setUserIds(officeMsgSending.getCreateUserId());
			officeMsgSending.setUserNames(user.getRealname());
			String detailNames = userService.getUserDetailNamesStr(new String[]{officeMsgSending.getCreateUserId()});
			officeMsgSending.setDetailNames(detailNames);
		}
		if(Constants.OPERATE_TYPE_REPLY_ALL.equals(operateType)){//回复全部
			//从mainSend表获取收件人信息
			List<OfficeMsgMainsend> officeMsgMainsends = officeMsgMainsendService.getOfficeMsgMainsendList(id);
			StringBuffer userIds = new StringBuffer();
			StringBuffer deptIds = new StringBuffer();
			StringBuffer unitIds = new StringBuffer();
			StringBuffer userNames = new StringBuffer();
			boolean flag = false;//收件人是否包含发件人
			for(OfficeMsgMainsend officeMsgMainsend:officeMsgMainsends){
				if(officeMsgMainsend.getReceiverType() == Constants.TEACHER){
					if(!flag && officeMsgMainsend.getReceiverId().equals(officeMsgSending.getCreateUserId())){
						flag = true;
					}
					if(userIds.length() == 0){
						userIds.append(officeMsgMainsend.getReceiverId());
						userNames.append(officeMsgMainsend.getReceiverName());
					}else{
						userIds.append(","+officeMsgMainsend.getReceiverId());
						userNames.append(","+officeMsgMainsend.getReceiverName());
					}
				}else if(officeMsgMainsend.getReceiverType() == Constants.DEPT){
					if(deptIds.length() == 0){
						deptIds.append(officeMsgMainsend.getReceiverId());
					}else{
						deptIds.append(","+officeMsgMainsend.getReceiverId());
					}
				}else if(officeMsgMainsend.getReceiverType() == Constants.UNIT){
					if(unitIds.length() == 0){
						unitIds.append(officeMsgMainsend.getReceiverId());
					}else{
						unitIds.append(","+officeMsgMainsend.getReceiverId());
					}
				}
			}
			//TODO 如果没包含发件人，那么引入发件人
			if(!flag){
				User user = userService.getUser(officeMsgSending.getCreateUserId());
				if(userIds.length()>0){
					userIds.append(","+officeMsgSending.getCreateUserId());
					userNames.append(","+user.getRealname());
				}else{
					userIds.append(officeMsgSending.getCreateUserId());
					userNames.append(user.getRealname());
				}
			}
			officeMsgSending.setUnitIds(unitIds.toString());
			officeMsgSending.setDeptIds(deptIds.toString());
			officeMsgSending.setUserIds(userIds.toString());
			officeMsgSending.setUserNames(userNames.toString());
			String detailNames = "";
			if(unitIds.length() > 0){
				String unitNameStr = unitService.getUnitDetailNamesStr(unitIds.toString().split(","));
				detailNames = unitNameStr;
			}
			if(deptIds.length() > 0){
				String deptNameStr = deptService.getDeptDetailNamesStr(deptIds.toString().split(","));
				if(detailNames.length() > 0){
					detailNames += "," + deptNameStr;
				}else{
					detailNames = deptNameStr;
				}
			}
			if(userIds.length() > 0){
				String userNameStr = userService.getUserDetailNamesStr(userIds.toString().split(","));
				if(detailNames.length() > 0){
					detailNames += "," + userNameStr;
				}else{
					detailNames = userNameStr;
				}
			}
			officeMsgSending.setDetailNames(detailNames);
		}
		if(Constants.OPERATE_TYPE_RESEND.equals(operateType) || Constants.OPERATE_TYPE_SEND.equals(operateType)){//重新发送
			//从mainSend表获取收件人信息
			List<OfficeMsgMainsend> officeMsgMainsends = officeMsgMainsendService.getOfficeMsgMainsendList(id);
			StringBuffer userIds = new StringBuffer();
			StringBuffer deptIds = new StringBuffer();
			StringBuffer unitIds = new StringBuffer();
			StringBuffer userNames = new StringBuffer();
			for(OfficeMsgMainsend officeMsgMainsend:officeMsgMainsends){
				if(officeMsgMainsend.getReceiverType() == Constants.TEACHER){
					if(userIds.length() == 0){
						userIds.append(officeMsgMainsend.getReceiverId());
						userNames.append(officeMsgMainsend.getReceiverName());
					}else{
						userIds.append(","+officeMsgMainsend.getReceiverId());
						userNames.append(","+officeMsgMainsend.getReceiverName());
					}
				}else if(officeMsgMainsend.getReceiverType() == Constants.DEPT){
					if(deptIds.length() == 0){
						deptIds.append(officeMsgMainsend.getReceiverId());
					}else{
						deptIds.append(","+officeMsgMainsend.getReceiverId());
					}
				}else if(officeMsgMainsend.getReceiverType() == Constants.UNIT){
					if(unitIds.length() == 0){
						unitIds.append(officeMsgMainsend.getReceiverId());
					}else{
						unitIds.append(","+officeMsgMainsend.getReceiverId());
					}
				}
			}
			officeMsgSending.setUnitIds(unitIds.toString());
			officeMsgSending.setDeptIds(deptIds.toString());
			officeMsgSending.setUserIds(userIds.toString());
			officeMsgSending.setUserNames(userNames.toString());
			String detailNames = "";
			if(unitIds.length() > 0){
				String unitNameStr = unitService.getUnitDetailNamesStr(unitIds.toString().split(","));
				detailNames = unitNameStr;
			}
			if(deptIds.length() > 0){
				String deptNameStr = deptService.getDeptDetailNamesStr(deptIds.toString().split(","));
				if(detailNames.length() > 0){
					detailNames += "," + deptNameStr;
				}else{
					detailNames = deptNameStr;
				}
			}
			if(userIds.length() > 0){
				String userNameStr = userService.getUserDetailNamesStr(userIds.toString().split(","));
				if(detailNames.length() > 0){
					detailNames += "," + userNameStr;
				}else{
					detailNames = userNameStr;
				}
			}
			officeMsgSending.setDetailNames(detailNames);
			
			if(attachments.size()>0)
				officeMsgSending.setAttachments(attachments);
		}
		return officeMsgSending;
	}

	@Override
	public OfficeMsgSending getOfficeMsgSendingById(String id){
		OfficeMsgSending officeMsgSending = officeMsgSendingDao.getOfficeMsgSendingById(id);
		User user = userService.getUserWithDel(officeMsgSending.getCreateUserId());
		if(user!=null){
			Teacher teacher = teacherService.getTeacher(user.getTeacherid());
			if(teacher!=null){
				Dept dept = deptService.getDept(teacher.getDeptid());
				Unit unit = unitService.getUnit(user.getUnitid());
				officeMsgSending.setSendUserName(user.getRealname()+"("+unit.getName()+"-"+dept.getDeptname()+")");
			}else{
				officeMsgSending.setSendUserName(user.getRealname());
			}
			officeMsgSending.setSendUserNameSimple(user.getRealname());
		}else{
			officeMsgSending.setSendUserName("用户已删除");
			officeMsgSending.setSendUserNameSimple("用户已删除");
		}
		
		//从mainSend表获取收件人信息
		List<OfficeMsgMainsend> officeMsgMainsends = officeMsgMainsendService.getOfficeMsgMainsendList(id);
		StringBuffer userIds = new StringBuffer();
		StringBuffer deptIds = new StringBuffer();
		StringBuffer unitIds = new StringBuffer();
		StringBuffer userNames = new StringBuffer();
		String[] mainSendNames = new String[officeMsgMainsends.size()];
		int i = 0;
		for(OfficeMsgMainsend officeMsgMainsend:officeMsgMainsends){
			mainSendNames[i] = officeMsgMainsend.getReceiverName();
			i++;
			if(officeMsgMainsend.getReceiverType() == Constants.TEACHER){
				if(userIds.length() == 0){
					userIds.append(officeMsgMainsend.getReceiverId());
					userNames.append(officeMsgMainsend.getReceiverName());
				}else{
					userIds.append(","+officeMsgMainsend.getReceiverId());
					userNames.append(","+officeMsgMainsend.getReceiverName());
				}
			}else if(officeMsgMainsend.getReceiverType() == Constants.DEPT){
				if(deptIds.length() == 0){
					deptIds.append(officeMsgMainsend.getReceiverId());
				}else{
					deptIds.append(","+officeMsgMainsend.getReceiverId());
				}
			}else if(officeMsgMainsend.getReceiverType() == Constants.UNIT){
				if(unitIds.length() == 0){
					unitIds.append(officeMsgMainsend.getReceiverId());
				}else{
					unitIds.append(","+officeMsgMainsend.getReceiverId());
				}
			}
		}
		officeMsgSending.setUnitIds(unitIds.toString());
		officeMsgSending.setDeptIds(deptIds.toString());
		officeMsgSending.setUserIds(userIds.toString());
		officeMsgSending.setUserNames(userNames.toString());
		String detailNames = "";
		if(unitIds.length() > 0){
			String unitNameStr = unitService.getUnitDetailNamesStr(unitIds.toString().split(","));
			detailNames = unitNameStr;
		}
		if(deptIds.length() > 0){
			String deptNameStr = deptService.getDeptDetailNamesStr(deptIds.toString().split(","));
			if(detailNames.length() > 0){
				detailNames += "," + deptNameStr;
			}else{
				detailNames = deptNameStr;
			}
		}
		if(userIds.length() > 0){
			String userNameStr = userService.getUserDetailNamesStr(userIds.toString().split(","));
			if(detailNames.length() > 0){
				detailNames += "," + userNameStr;
			}else{
				detailNames = userNameStr;
			}
		}
		officeMsgSending.setMainSendNames(mainSendNames);
		officeMsgSending.setReceiveNum(officeMsgMainsends.size());
		officeMsgSending.setDetailNames(detailNames);
		
		//如果是已发送，从receiving表获取已读未读数据信息
		if(officeMsgSending.getState() >= Constants.MSG_STATE_SEND){
			int hasReadNum = 0;//已读人员
			int unReadNum = 0;//未读人员
			List<OfficeMsgReceiving> officeMsgReceivings = officeMsgReceivingService.getOfficeMsgReceivingListByMessageId(id);                  
			for(OfficeMsgReceiving officeMsgReceiving:officeMsgReceivings){
				if(Constants.UNREAD == officeMsgReceiving.getIsRead()){
					unReadNum ++;
				}else{
					hasReadNum ++;
				}
			}
			officeMsgSending.setHasReadNum(hasReadNum);
			officeMsgSending.setUnReadNum(unReadNum);
		}
		List<Attachment> attachments = attachmentService.getAttachments(id, Constants.MESSAGE_ATTACHMENT);//附件
		if(attachments.size()>0)
			officeMsgSending.setAttachments(attachments);
		return officeMsgSending;
	}
	
	@Override
	public String getOfficeMsgSendingUserNames(String id) {
		//从mainSend表获取收件人信息,手机端只传人员信息
		List<OfficeMsgMainsend> officeMsgMainsends = officeMsgMainsendService.getOfficeMsgMainsendList(id);
		StringBuffer userNames = new StringBuffer();
		for(OfficeMsgMainsend officeMsgMainsend:officeMsgMainsends){
			if(officeMsgMainsend.getReceiverType() == Constants.TEACHER){
				if(userNames.length() == 0){
					userNames.append(officeMsgMainsend.getReceiverName());
				}else{
					userNames.append(","+officeMsgMainsend.getReceiverName());
				}
			}
		}
		return userNames.toString();
	}

	@Override
	public Map<String, OfficeMsgSending> getOfficeMsgSendingMapByIds(String[] ids){
		return officeMsgSendingDao.getOfficeMsgSendingMapByIds(ids);
	}

	@Override
	public List<OfficeMsgSending> getOfficeMsgSendingList(
			MessageSearch messageSearch, Pagination page) {
		List<OfficeMsgSending> officeMsgSendings = officeMsgSendingDao.getOfficeMsgSendingList(messageSearch, page);
		if(CollectionUtils.isEmpty(officeMsgSendings)){
			return officeMsgSendings;
		}
		String[] msgSendIds = new String[officeMsgSendings.size()]; 
		int i = 0;
		for(OfficeMsgSending officeMsgSending:officeMsgSendings){
			msgSendIds[i] = officeMsgSending.getId();
			i++;
		}
		Map<String, String> readMap =  officeMsgReceivingService.findMessageReadStatus(msgSendIds);
		for(OfficeMsgSending officeMsgSending:officeMsgSendings){
			officeMsgSending.setReadStr(readMap.get(officeMsgSending.getId()));
		}
		return officeMsgSendings;
	}

	@Override
	public List<OfficeMsgSending> getOfficeMsgSendingPage(Pagination page){
		return officeMsgSendingDao.getOfficeMsgSendingPage(page);
	}

	@Override
	public List<OfficeMsgSending> getOfficeMsgSendingByUnitIdList(String unitId){
		return officeMsgSendingDao.getOfficeMsgSendingByUnitIdList(unitId);
	}

	@Override
	public List<OfficeMsgSending> getOfficeMsgSendingByUnitIdPage(String unitId, Pagination page){
		return officeMsgSendingDao.getOfficeMsgSendingByUnitIdPage(unitId, page);
	}
	
	@Override
	public Integer getUnSendNum(String userId) {
		return officeMsgSendingDao.getUnSendNum(userId);
	}

	public void setOfficeMsgSendingDao(OfficeMsgSendingDao officeMsgSendingDao){
		this.officeMsgSendingDao = officeMsgSendingDao;
	}

	public void setAttachmentService(AttachmentService attachmentService) {
		this.attachmentService = attachmentService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public void setOfficeMsgReceivingService(
			OfficeMsgReceivingService officeMsgReceivingService) {
		this.officeMsgReceivingService = officeMsgReceivingService;
	}

	public void setOfficeMsgMainsendService(
			OfficeMsgMainsendService officeMsgMainsendService) {
		this.officeMsgMainsendService = officeMsgMainsendService;
	}

	public void setOfficeMsgRecycleService(
			OfficeMsgRecycleService officeMsgRecycleService) {
		this.officeMsgRecycleService = officeMsgRecycleService;
	}

	public void setOfficeMsgFolderDetailService(
			OfficeMsgFolderDetailService officeMsgFolderDetailService) {
		this.officeMsgFolderDetailService = officeMsgFolderDetailService;
	}

	public void setSmsClientService(SmsClientService smsClientService) {
		this.smsClientService = smsClientService;
	}

	public void setTeacherService(TeacherService teacherService) {
		this.teacherService = teacherService;
	}

	public void setDeptService(DeptService deptService) {
		this.deptService = deptService;
	}

	public void setUnitService(UnitService unitService) {
		this.unitService = unitService;
	}

	public void setSystemIniService(SystemIniService systemIniService) {
		this.systemIniService = systemIniService;
	}

	public void setPushRemoteService(PushRemoteService pushRemoteService) {
		this.pushRemoteService = pushRemoteService;
	}
	
}