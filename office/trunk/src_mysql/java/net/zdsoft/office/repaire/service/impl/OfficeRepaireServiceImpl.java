package net.zdsoft.office.repaire.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.zdsoft.eis.base.attachment.entity.Attachment;
import net.zdsoft.eis.base.attachment.service.AttachmentService;
import net.zdsoft.eis.base.common.entity.BasicClass;
import net.zdsoft.eis.base.common.entity.Mcodedetail;
import net.zdsoft.eis.base.common.entity.Teacher;
import net.zdsoft.eis.base.common.entity.Unit;
import net.zdsoft.eis.base.common.entity.User;
import net.zdsoft.eis.base.common.service.BasicClassService;
import net.zdsoft.eis.base.common.service.McodedetailService;
import net.zdsoft.eis.base.common.service.TeacherService;
import net.zdsoft.eis.base.common.service.UnitService;
import net.zdsoft.eis.base.common.service.UserService;
import net.zdsoft.eis.base.constant.BaseConstant;
import net.zdsoft.eis.sms.constant.SmsConstant;
import net.zdsoft.eis.sms.dto.MsgDto;
import net.zdsoft.eis.sms.dto.SendDetailDto;
import net.zdsoft.eis.sms.service.SmsClientService;
import net.zdsoft.eisu.base.common.entity.TeachArea;
import net.zdsoft.eisu.base.common.service.TeachAreaService;
import net.zdsoft.keel.util.DateUtils;
import net.zdsoft.keel.util.Pagination;
import net.zdsoft.keelcnet.action.UploadFile;
import net.zdsoft.office.msgcenter.entity.OfficeMsgSending;
import net.zdsoft.office.msgcenter.service.OfficeMsgSendingService;
import net.zdsoft.office.repaire.dao.OfficeRepaireDao;
import net.zdsoft.office.repaire.entity.OfficeRepaire;
import net.zdsoft.office.repaire.entity.OfficeRepaireSms;
import net.zdsoft.office.repaire.entity.OfficeRepaireType;
import net.zdsoft.office.repaire.entity.OfficeTeachAreaAuth;
import net.zdsoft.office.repaire.entity.OfficeTypeAuth;
import net.zdsoft.office.repaire.service.OfficeRepaireService;
import net.zdsoft.office.repaire.service.OfficeRepaireSmsService;
import net.zdsoft.office.repaire.service.OfficeRepaireTypeService;
import net.zdsoft.office.repaire.service.OfficeTeachAreaAuthService;
import net.zdsoft.office.repaire.service.OfficeTypeAuthService;
import net.zdsoft.office.util.Constants;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
/**
 * office_repaire
 * @author 
 * 
 */
public class OfficeRepaireServiceImpl implements OfficeRepaireService{
	private OfficeRepaireDao officeRepaireDao;
	private OfficeTeachAreaAuthService officeTeachAreaAuthService;
	private OfficeTypeAuthService officeTypeAuthService;
	private UserService userService;
	private TeachAreaService teachAreaService;
	private OfficeRepaireTypeService officeRepaireTypeService;
	private OfficeMsgSendingService officeMsgSendingService;
	private McodedetailService mcodedetailService;
	private UnitService unitService;
	private SmsClientService smsClientService;
	private TeacherService teacherService;
	private BasicClassService basicClassService;
	private OfficeRepaireSmsService officeRepaireSmsService;
	private AttachmentService attachmentService;
	
	@Override
	public void add(OfficeRepaire officeRepaire,UploadFile file){//TODO
		OfficeRepaire re = officeRepaireDao.save(officeRepaire);
		if(file!=null){
			Attachment attachment = new Attachment();
			attachment.setFileName(file.getFileName());
			attachment.setContentType(file.getContentType());
			attachment.setFileSize(file.getFileSize());
			attachment.setUnitId(officeRepaire.getUnitId());
			attachment.setObjectId(officeRepaire.getId());
			attachment.setObjectType(Constants.OFFICE_REPAIRE_AIT);
			attachmentService.saveAttachment(attachment, file);
		}
		//----------------------判断一级类别是否设置接收短信-----------------------
		OfficeRepaireSms reSMS = officeRepaireSmsService.getOfficeRepaireSmsByTypeId(re.getUnitId(), re.getType());
		if(reSMS != null && reSMS.getIsNeedSms() != null && reSMS.getIsNeedSms()){//若为“接收”，则为一级负责人发送消息及短信
			OfficeRepaireType repaireType = officeRepaireTypeService.getOfficeRepaireTypeById(officeRepaire.getRepaireTypeId());
			User user = userService.getUser(officeRepaire.getUserId());
			officeRepaire.setUserName(user.getRealname());
			Mcodedetail mcodedetail = mcodedetailService.getMcodeDetail("DM-BXLB", officeRepaire.getType());
			officeRepaire.setTypeName(mcodedetail.getContent());
			if(repaireType != null){
				officeRepaire.setRepaireTypeName(repaireType.getTypeName());
			}else{
				officeRepaire.setRepaireTypeName("");
			}
			
			Set<String> userIdsSet = new HashSet<String>();
			String userIdsStr = "";
			List<OfficeTypeAuth> authlist = officeTypeAuthService.getOfficeTypeAuthList(re.getUnitId(), Constants.REPAIRE_STATE, re.getType());
			for(OfficeTypeAuth auth : authlist){
				userIdsSet.add(auth.getUserId());
			}
			if(StringUtils.isNotBlank(re.getRepaireTypeId())){//如果存在二级类别，则一并发送
				String[] userIds = repaireType.getUserIds().split(",");
				for(String userId : userIds){
					userIdsSet.add(userId);
				}
			}
			for(String userId : userIdsSet){
				if(StringUtils.isBlank(userIdsStr))
					userIdsStr = userId;
				else
					userIdsStr += "," + userId;
			}
		
			//-----------------组织消息内容并发送------------------
			OfficeMsgSending officeMsgSending = new OfficeMsgSending();
			officeMsgSending.setCreateUserId(officeRepaire.getUserId());
			officeMsgSending.setTitle("报修申请信息提醒");
			officeMsgSending.setContent(getContent(officeRepaire));
			officeMsgSending.setSimpleContent(getContent(officeRepaire));
			officeMsgSending.setUserIds(userIdsStr);
			officeMsgSending.setSendUserName(officeRepaire.getUserName());
			officeMsgSending.setState(Constants.MSG_STATE_SEND);
			officeMsgSending.setIsNeedsms(false);
			officeMsgSending.setUnitId(officeRepaire.getUnitId());
			officeMsgSending.setMsgType(BaseConstant.MSG_TYPE_REPAIRE);
			officeMsgSendingService.save(officeMsgSending, null, null);
			
			
			// 发短信
			Unit unit = unitService.getUnit(officeRepaire.getUnitId());
			MsgDto msgDto = new MsgDto();
			msgDto.setUserId(officeRepaire.getUserId());
			msgDto.setUnitName(unit.getName());
			msgDto.setUserName(officeRepaire.getUserName());
			msgDto.setContent(getContent(officeRepaire));
			msgDto.setTiming(false);
			List<SendDetailDto> sendDetailDtos = new ArrayList<SendDetailDto>();
			String[] userIds = userIdsStr.split(",");
			Map<String,User> userMap = userService.getUsersMap(userIds);
			Set<String> teacherIds = new HashSet<String>();
			for(String userId : userIds){
				User receiver = userMap.get(userId);
				if(receiver != null){
					teacherIds.add(receiver.getTeacherid());
				}
			}
			Map<String, Teacher> teacherMap = teacherService.getTeacherMap(teacherIds.toArray(new String[0]));
			for(String userId:userIds){
				User receiver = userMap.get(userId);
				if(receiver != null && receiver.getOrderid() != null){
					Teacher teacher = teacherMap.get(receiver.getTeacherid());
						if(teacher != null && StringUtils.isNotBlank(teacher.getPersonTel())){
						SendDetailDto sendDetailDto = new SendDetailDto();
						sendDetailDto.setReceiverId(userId);
						sendDetailDto.setReceiverName(receiver.getRealname());
						sendDetailDto.setBusinessType(SmsConstant.SMS_BUSINESS_PAY);// Sdk收费短信
						sendDetailDto.setMobile(teacher.getPersonTel());// 短信接收人手机号码
						sendDetailDto.setReceiverType(User.TEACHER_LOGIN);// 短信接收用户类型:教师用户,学生用户,家长用户
						sendDetailDto.setUnitId(officeRepaire.getUnitId());// 短信接收用户单位id
						sendDetailDtos.add(sendDetailDto);
					}
				}
			}
			SmsThread smsThread = new SmsThread(msgDto, sendDetailDtos);
			smsThread.start();
		}
		//return re;
	}
	private String getContent(OfficeRepaire officeRepaire) {
        StringBuffer sb = new StringBuffer();
        sb.append("    您好！您有报修申请需要处理。报修人：")
                .append(officeRepaire.getUserName()).append("");
        sb.append("，设备地点：").append(officeRepaire.getGoodsPlace());
        sb.append("，报修时间：").append(DateUtils.date2String(officeRepaire.getDetailTime(),"yyyy-MM-dd HH:mm"));
        sb.append("，电话：").append(officeRepaire.getPhone());
        sb.append("，设备名称：").append(officeRepaire.getGoodsName());
        if(StringUtils.isBlank(officeRepaire.getRepaireTypeName())){
        	sb.append("，类别：");
        	sb.append(officeRepaire.getTypeName()); 
        }else{
        	sb.append("，类别（二级类别）：");
        	sb.append(officeRepaire.getTypeName()); 
        	sb.append("（"+officeRepaire.getRepaireTypeName()+"）");
        }
        sb.append("，故障详情：").append(officeRepaire.getRemark());
        return sb.toString();
    }
	private String getContent2(OfficeRepaire officeRepaire) {
        StringBuffer sb = new StringBuffer();
        sb.append("    您好！您申请的报修已完成。报修负责人：")
                .append(officeRepaire.getUserName()).append("");
        sb.append("，设备地点：").append(officeRepaire.getGoodsPlace());
        sb.append("，报修时间：").append(DateUtils.date2String(officeRepaire.getDetailTime(),"yyyy-MM-dd HH:mm"));
        sb.append("，维修时间：").append(DateUtils.date2String(officeRepaire.getRepaireTime(),"yyyy-MM-dd HH:mm"));
        sb.append("，电话：").append(officeRepaire.getPhone());
        sb.append("，设备名称：").append(officeRepaire.getGoodsName());
        if(StringUtils.isBlank(officeRepaire.getRepaireTypeName())){
        	sb.append("，类别：");
        	sb.append(officeRepaire.getTypeName()); 
        }else{
        	sb.append("，类别（二级类别）：");
        	sb.append(officeRepaire.getTypeName()); 
        	sb.append("（"+officeRepaire.getRepaireTypeName()+"）");
        }
        sb.append("，故障详情：").append(officeRepaire.getRemark());
        sb.append("，维修备注：").append(officeRepaire.getRepaireRemark());
        return sb.toString();
    }
	@Override
	public Integer delete(String[] ids){
		List<Attachment> attachments = attachmentService
				.getAttachments(ids[0],Constants.OFFICE_REPAIRE_AIT);
		String[] attachmentIds = new String[attachments.size()];
		for(int i = 0; i < attachments.size(); i++){
			attachmentIds[i] = attachments.get(i).getId();
		}
		attachmentService.deleteAttachments(attachmentIds);
		return officeRepaireDao.delete(ids);
	}

	@Override
	public void update(OfficeRepaire officeRepaire,UploadFile file){//TODO
		OfficeRepaire oldRe = officeRepaireDao.getOfficeRepaireById(officeRepaire.getId());
		OfficeRepaireSms reSMS = officeRepaireSmsService.getOfficeRepaireSmsByTypeId(officeRepaire.getUnitId(), officeRepaire.getType());
		if(reSMS != null && reSMS.getIsNeedSms() != null && reSMS.getIsNeedSms()){//现在的一级类别是接收短信的
			OfficeRepaireType repaireType = officeRepaireTypeService.getOfficeRepaireTypeById(officeRepaire.getRepaireTypeId());
			User user = userService.getUser(oldRe.getUserId());
			officeRepaire.setUserName(user.getRealname());
			Mcodedetail mcodedetail = mcodedetailService.getMcodeDetail("DM-BXLB", officeRepaire.getType());
			officeRepaire.setTypeName(mcodedetail.getContent());
			if(repaireType != null){
				officeRepaire.setRepaireTypeName(repaireType.getTypeName());
			}else{
				officeRepaire.setRepaireTypeName("");
			}
			
			String userIdsStr = "";
			Set<String> userIdsSet = new HashSet<String>();
			List<OfficeTypeAuth> authlist = officeTypeAuthService.getOfficeTypeAuthList(officeRepaire.getUnitId(), Constants.REPAIRE_STATE, officeRepaire.getType());
			for(OfficeTypeAuth auth : authlist){
				userIdsSet.add(auth.getUserId());
			}
			if(StringUtils.isNotBlank(officeRepaire.getRepaireTypeId())){//如果存在二级类别，则一并发送
				String[] userIds = repaireType.getUserIds().split(",");
				for(String userId : userIds){
					userIdsSet.add(userId);
				}
			}
			for(String userId : userIdsSet){
				if(StringUtils.isBlank(userIdsStr))
					userIdsStr = userId;
				else
					userIdsStr += "," + userId;
			}
			// 发信息
			OfficeMsgSending officeMsgSending = new OfficeMsgSending();
			officeMsgSending.setCreateUserId(oldRe.getUserId());
			officeMsgSending.setTitle("报修申请信息提醒");
			officeMsgSending.setContent(getContent(officeRepaire));
			officeMsgSending.setSimpleContent(getContent(officeRepaire));
			officeMsgSending.setUserIds(userIdsStr);
			officeMsgSending.setSendUserName(officeRepaire.getUserName());
			officeMsgSending.setState(Constants.MSG_STATE_SEND);
			officeMsgSending.setIsNeedsms(false);
			officeMsgSending.setUnitId(officeRepaire.getUnitId());
			officeMsgSending.setMsgType(BaseConstant.MSG_TYPE_REPAIRE);
			officeMsgSendingService.save(officeMsgSending, null, null);
			
			// 发短信
			Unit unit = unitService.getUnit(officeRepaire.getUnitId());
			MsgDto msgDto = new MsgDto();
			msgDto.setUserId(officeRepaire.getUserId());
			msgDto.setUnitName(unit.getName());
			msgDto.setUserName(officeRepaire.getUserName());
			msgDto.setContent(getContent(officeRepaire));
			msgDto.setTiming(false);
			List<SendDetailDto> sendDetailDtos = new ArrayList<SendDetailDto>();
			String[] userIds = userIdsStr.split(",");
			Map<String,User> userMap = userService.getUsersMap(userIds);
			Set<String> teacherIds = new HashSet<String>();
			for(String userId : userIds){
				User receiver = userMap.get(userId);
				if(receiver != null){
					teacherIds.add(receiver.getTeacherid());
				}
			}
			Map<String, Teacher> teacherMap = teacherService.getTeacherMap(teacherIds.toArray(new String[0]));
			for(String userId:userIds){
				User receiver = userMap.get(userId);
				if(receiver != null && receiver.getOrderid() != null){
					Teacher teacher = teacherMap.get(receiver.getTeacherid());
						if(teacher != null && StringUtils.isNotBlank(teacher.getPersonTel())){
						SendDetailDto sendDetailDto = new SendDetailDto();
						sendDetailDto.setReceiverId(userId);
						sendDetailDto.setReceiverName(receiver.getRealname());
						sendDetailDto.setBusinessType(SmsConstant.SMS_BUSINESS_PAY);// Sdk收费短信
						sendDetailDto.setMobile(teacher.getPersonTel());// 短信接收人手机号码
						sendDetailDto.setReceiverType(User.TEACHER_LOGIN);// 短信接收用户类型:教师用户,学生用户,家长用户
						sendDetailDto.setUnitId(officeRepaire.getUnitId());// 短信接收用户单位id
						sendDetailDtos.add(sendDetailDto);
					}
				}
			}
			SmsThread smsThread = new SmsThread(msgDto, sendDetailDtos);
			smsThread.start();
		}
		officeRepaireDao.update(officeRepaire);
		List<Attachment> attachments = attachmentService.getAttachments(officeRepaire.getId(), Constants.OFFICE_REPAIRE_AIT);
		if(file!=null){
			if(CollectionUtils.isNotEmpty(attachments)){
				Attachment attachment = attachments.get(0);
				attachment.setFileName(file.getFileName());
				attachment.setContentType(file.getContentType());
				attachment.setFileSize(file.getFileSize());
				attachmentService.updateAttachment(attachment, file, true);
			}else{
				Attachment attachment = new Attachment();
				attachment.setFileName(file.getFileName());
				attachment.setContentType(file.getContentType());
				attachment.setFileSize(file.getFileSize());
				attachment.setUnitId(officeRepaire.getUnitId());
				attachment.setObjectId(officeRepaire.getId());
				attachment.setObjectType(Constants.OFFICE_REPAIRE_AIT);
				attachmentService.saveAttachment(attachment, file);
			}
		}
		if(StringUtils.isBlank(officeRepaire.getUploadContentFileInput())&&file==null){
			String[] attachmentIds = new String[attachments.size()];
			for(int i = 0; i < attachments.size(); i++){
				attachmentIds[i] = attachments.get(i).getId();
			}
			attachmentService.deleteAttachments(attachmentIds);
		}
		//return officeRepaireDao.update(officeRepaire);
	}
	@Override
	public Integer update(OfficeRepaire officeRepaire){
		return officeRepaireDao.update(officeRepaire);
	}
	@Override
	public OfficeRepaire save(OfficeRepaire officeRepaire){
		return officeRepaireDao.save(officeRepaire);
	}
	/**
	 * 发送短信
	 * @author Administrator
	 *
	 */
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
	
	@Override
	public void updateState(OfficeRepaire officeRepaire) {//TODO
		if(StringUtils.equals(officeRepaire.getState(), "3")){//当维修完毕，则发送消息给报修人
			OfficeRepaireType repaireType = officeRepaireTypeService.getOfficeRepaireTypeById(officeRepaire.getRepaireTypeId());
			User user = userService.getUser(officeRepaire.getRepaireUserId());
			officeRepaire.setUserName(user.getRealname());
			Mcodedetail mcodedetail = mcodedetailService.getMcodeDetail("DM-BXLB", officeRepaire.getType());
			officeRepaire.setTypeName(mcodedetail.getContent());
			if(repaireType != null){
				officeRepaire.setRepaireTypeName(repaireType.getTypeName());
			}else{
				officeRepaire.setRepaireTypeName("");
			}
			
			//-----------------组织消息内容并发送------------------
			OfficeMsgSending officeMsgSending = new OfficeMsgSending();
			officeMsgSending.setCreateUserId(officeRepaire.getRepaireUserId());
			officeMsgSending.setTitle("报修完毕信息提醒");
			officeMsgSending.setContent(getContent2(officeRepaire));
			officeMsgSending.setSimpleContent(getContent2(officeRepaire));
			officeMsgSending.setUserIds(officeRepaire.getUserId());
			officeMsgSending.setSendUserName(officeRepaire.getUserName());
			officeMsgSending.setState(Constants.MSG_STATE_SEND);
			officeMsgSending.setIsNeedsms(false);
			officeMsgSending.setUnitId(officeRepaire.getUnitId());
			officeMsgSending.setMsgType(BaseConstant.MSG_TYPE_REPAIRE);
			officeMsgSendingService.save(officeMsgSending, null, null);
			
			
			// 发短信
			Unit unit = unitService.getUnit(officeRepaire.getUnitId());
			MsgDto msgDto = new MsgDto();
			msgDto.setUserId(officeRepaire.getRepaireUserId());
			msgDto.setUnitName(unit.getName());
			msgDto.setUserName(officeRepaire.getUserName());
			msgDto.setContent(getContent2(officeRepaire));
			msgDto.setTiming(false);
			List<SendDetailDto> sendDetailDtos = new ArrayList<SendDetailDto>();
			User receiver = userService.getUser(officeRepaire.getUserId());
			if(receiver != null && receiver.getOrderid() != null){
				if(StringUtils.isNotBlank(officeRepaire.getPhone())){
					SendDetailDto sendDetailDto = new SendDetailDto();
					sendDetailDto.setReceiverId(receiver.getId());
					sendDetailDto.setReceiverName(receiver.getRealname());
					sendDetailDto.setBusinessType(SmsConstant.SMS_BUSINESS_PAY);// Sdk收费短信
					sendDetailDto.setMobile(officeRepaire.getPhone());// 短信接收人手机号码
					sendDetailDto.setReceiverType(User.TEACHER_LOGIN);// 短信接收用户类型:教师用户,学生用户,家长用户
					sendDetailDto.setUnitId(officeRepaire.getUnitId());// 短信接收用户单位id
					sendDetailDtos.add(sendDetailDto);
				}
				SmsThread smsThread = new SmsThread(msgDto, sendDetailDtos);
				smsThread.start();
			}
		}
		officeRepaireDao.updateState(officeRepaire);
	}
	
	@Override
	public void updateFeedBack(OfficeRepaire officeRepaire) {
		officeRepaireDao.updateFeedBack(officeRepaire);
	}

	@Override
	public OfficeRepaire getOfficeRepaireById(String id){
		OfficeRepaire officeRepaire = officeRepaireDao.getOfficeRepaireById(id);
		User user = userService.getUser(officeRepaire.getUserId());
		officeRepaire.setUserName(user.getRealname());
		TeachArea teachArea = teachAreaService.getTeachArea(officeRepaire.getTeachAreaId());
		officeRepaire.setTeachAreaName(teachArea == null ? "" : teachArea.getAreaName());
		if(StringUtils.isNotBlank(officeRepaire.getRepaireTypeId())){
			OfficeRepaireType officeRepaireType = officeRepaireTypeService.getOfficeRepaireTypeById(officeRepaire.getRepaireTypeId());
			officeRepaire.setRepaireTypeName(officeRepaireType.getTypeName());
		}
		String classId = officeRepaire.getClassId();
		if(StringUtils.isNotBlank(classId)) {
		    BasicClass bc = basicClassService.getClass(classId);
		    if(bc != null)
		        officeRepaire.setClassName(bc.getClassnamedynamic());
		}
		officeRepaire.setAttachments(attachmentService.getAttachments(officeRepaire.getId(), Constants.OFFICE_REPAIRE_AIT));
		return officeRepaire;
	}

	@Override
	public Map<String, OfficeRepaire> getOfficeRepaireMapByIds(String[] ids){
		return officeRepaireDao.getOfficeRepaireMapByIds(ids);
	}

	@Override
	public List<OfficeRepaire> getOfficeRepaireList(){
		return officeRepaireDao.getOfficeRepaireList();
	}

	@Override
	public List<OfficeRepaire> getOfficeRepairePage(Pagination page){
		return officeRepaireDao.getOfficeRepairePage(page);
	}

	@Override
	public List<OfficeRepaire> getOfficeRepaireByUnitIdList(String unitId){
		return officeRepaireDao.getOfficeRepaireByUnitIdList(unitId);
	}

	@Override
	public List<OfficeRepaire> getOfficeRepaireByUnitIdPage(String unitId, Pagination page){
		return officeRepaireDao.getOfficeRepaireByUnitIdPage(unitId, page);
	}
	@Override
	public List<OfficeRepaire> getOfficeRepaireList(String userId,
			String unitId, String areaId, String type, String state,
			Date startTime, Date endTime, String searchContent, Pagination page) {
		List<OfficeRepaire> list =  officeRepaireDao.getOfficeRepaireList(userId,unitId,areaId,type,state,startTime,endTime,searchContent, page);
		Set<String> userIds = new HashSet<String>();
		Set<String> classIds = new HashSet<String>();
		for(OfficeRepaire e : list){
			userIds.add(e.getUserId());
			if(StringUtils.isNotBlank(e.getClassId()))
			    classIds.add(e.getClassId());
		}
		Map<String,User> userMap = userService.getUserWithDelMap(userIds.toArray(new String[0]));
		Map<String, TeachArea> areaMap = teachAreaService.getTeachAreaMap(unitId);
		Map<String,String> typeMap = officeRepaireTypeService.getOfficeRepaireTypeMapByUnitId(unitId);
		
		Map<String, String> classNameMap = new HashMap<String, String>();
		if(CollectionUtils.isNotEmpty(classIds)) {
		    List<BasicClass> classes = basicClassService.getClasses(classIds.toArray(new String[0]));
		    for(BasicClass bc : classes) {
		        classNameMap.put(bc.getId(), bc.getClassnamedynamic());
		    }
		}
		
		for(OfficeRepaire e : list){
			if(StringUtils.isNotBlank(e.getRepaireTypeId()) && typeMap.containsKey(e.getRepaireTypeId()))
				e.setRepaireTypeName(typeMap.get(e.getRepaireTypeId()));
			if(userMap.containsKey(e.getUserId())){
				e.setUserName(userMap.get(e.getUserId()).getRealname());
			}else{
				e.setUserName("用户已删除");
			}
			if(areaMap.containsKey(e.getTeachAreaId())){
				e.setTeachAreaName(areaMap.get(e.getTeachAreaId()).getAreaName());
			}
			String className =classNameMap.get(e.getClassId()); 
			if(className != null) {
			    e.setClassName(className);
			}
		}
		return list;
	}
	public void setOfficeRepaireDao(OfficeRepaireDao officeRepaireDao){
		this.officeRepaireDao = officeRepaireDao;
	}
	@Override
	public List<OfficeRepaire> getOfficeRepaireByUnitIdList(String unitId,
			String typeId) {
		return officeRepaireDao.getOfficeRepaireByUnitIdList(unitId,typeId);
	}
	@Override
	public List<OfficeRepaire> getOfficeRepaireMangeList(String userId,
			String unitId, String areaId, String type, String state,
			Date startTime, Date endTime, String searchContent, Pagination page) {
		List<OfficeRepaire> list = new ArrayList<OfficeRepaire>();
		String[] areaIds;
		String[] types;
		if(StringUtils.isBlank(areaId) || StringUtils.equals(BaseConstant.ZERO_GUID, areaId)){
			List<OfficeTeachAreaAuth> arealist = officeTeachAreaAuthService.getOfficeTeachAreaAuthList(Constants.REPAIRE_STATE, userId);
			Set<String> ids = new HashSet<String>();
			for(OfficeTeachAreaAuth e : arealist){
				ids.add(e.getTeachAreaId());
			}
			areaIds = ids.toArray(new String[]{});
		}else{
			areaIds = new String[]{areaId};
		}
		if(StringUtils.isBlank(type)){
			List<OfficeTypeAuth> arealist = officeTypeAuthService.getOfficeTypeAuthList(Constants.REPAIRE_STATE, userId);
			Set<String> ids = new HashSet<String>();
			for(OfficeTypeAuth e : arealist){
				ids.add(e.getType());
			}
			types = ids.toArray(new String[]{});
		}else{
			types = new String[]{type};
		}
		if(types == null || types.length == 0||areaIds == null || areaIds.length == 0){
			return list;
		}
		
		list = officeRepaireDao.getOfficeRepaireList(unitId, areaIds, types, state, startTime, endTime, searchContent, page);
		Set<String> userIds = new HashSet<String>();
		Set<String> classIds = new HashSet<String>();
		
		for(OfficeRepaire e : list){
			userIds.add(e.getUserId());
			if(StringUtils.isNotBlank(e.getClassId())){
			    classIds.add(e.getClassId());
			}
		}
		Map<String,User> userMap = userService.getUserWithDelMap(userIds.toArray(new String[0]));
		Map<String, TeachArea> areaMap = teachAreaService.getTeachAreaMap(unitId);
		Map<String,String> typeMap = officeRepaireTypeService.getOfficeRepaireTypeMapByUnitId(unitId);
		
		Map<String, String> classNameMap = new HashMap<String, String>();
        if(CollectionUtils.isNotEmpty(classIds)) {
            List<BasicClass> classes = basicClassService.getClasses(classIds.toArray(new String[0]));
            for(BasicClass bc : classes) {
                classNameMap.put(bc.getId(), bc.getClassnamedynamic());
            }
        }
        
		for(OfficeRepaire e : list){
			if(StringUtils.isNotBlank(e.getRepaireTypeId()) && typeMap.containsKey(e.getRepaireTypeId()))
				e.setRepaireTypeName(typeMap.get(e.getRepaireTypeId()));
			if(userMap.containsKey(e.getUserId())){
				e.setUserName(userMap.get(e.getUserId()).getRealname());
			}else{
				e.setUserName("用户已删除");
			}
			if(areaMap.containsKey(e.getTeachAreaId())){
				e.setTeachAreaName(areaMap.get(e.getTeachAreaId()).getAreaName());
			}
			String className =classNameMap.get(e.getClassId()); 
            if(className != null) {
                e.setClassName(className);
            }
		}
		return list;
	}
	@Override
	public List<OfficeRepaire> getOfficeRepaireMangeListH5(String userId,
			String unitId, String areaId, String type, String[] state,
			Date startTime, Date endTime, String searchContent, Pagination page) {
		List<OfficeRepaire> list = new ArrayList<OfficeRepaire>();
		String[] areaIds;
		String[] types;
		if(StringUtils.isBlank(areaId) || StringUtils.equals(BaseConstant.ZERO_GUID, areaId)){
			List<OfficeTeachAreaAuth> arealist = officeTeachAreaAuthService.getOfficeTeachAreaAuthList(Constants.REPAIRE_STATE, userId);
			Set<String> ids = new HashSet<String>();
			for(OfficeTeachAreaAuth e : arealist){
				ids.add(e.getTeachAreaId());
			}
			areaIds = ids.toArray(new String[]{});
		}else{
			areaIds = new String[]{areaId};
		}
		if(StringUtils.isBlank(type)){
			List<OfficeTypeAuth> arealist = officeTypeAuthService.getOfficeTypeAuthList(Constants.REPAIRE_STATE, userId);
			Set<String> ids = new HashSet<String>();
			for(OfficeTypeAuth e : arealist){
				ids.add(e.getType());
			}
			types = ids.toArray(new String[]{});
		}else{
			types = new String[]{type};
		}
		if(types == null || types.length == 0||areaIds == null || areaIds.length == 0){
			return list;
		}
		
		list = officeRepaireDao.getOfficeRepaireListH5(unitId, areaIds, types, state, startTime, endTime, searchContent, page);
		Set<String> userIds = new HashSet<String>();
		Set<String> classIds = new HashSet<String>();
		
		for(OfficeRepaire e : list){
			userIds.add(e.getUserId());
			if(StringUtils.isNotBlank(e.getClassId())){
				classIds.add(e.getClassId());
			}
		}
		Map<String,User> userMap = userService.getUserWithDelMap(userIds.toArray(new String[0]));
		Map<String, TeachArea> areaMap = teachAreaService.getTeachAreaMap(unitId);
		Map<String,String> typeMap = officeRepaireTypeService.getOfficeRepaireTypeMapByUnitId(unitId);
		
		Map<String, String> classNameMap = new HashMap<String, String>();
		if(CollectionUtils.isNotEmpty(classIds)) {
			List<BasicClass> classes = basicClassService.getClasses(classIds.toArray(new String[0]));
			for(BasicClass bc : classes) {
				classNameMap.put(bc.getId(), bc.getClassnamedynamic());
			}
		}
		
		for(OfficeRepaire e : list){
			if(StringUtils.isNotBlank(e.getRepaireTypeId()) && typeMap.containsKey(e.getRepaireTypeId()))
				e.setRepaireTypeName(typeMap.get(e.getRepaireTypeId()));
			if(userMap.containsKey(e.getUserId())){
				e.setUserName(userMap.get(e.getUserId()).getRealname());
			}else{
				e.setUserName("用户已删除");
			}
			if(areaMap.containsKey(e.getTeachAreaId())){
				e.setTeachAreaName(areaMap.get(e.getTeachAreaId()).getAreaName());
			}
			String className =classNameMap.get(e.getClassId()); 
			if(className != null) {
				e.setClassName(className);
			}
		}
		return list;
	}
	@Override
	public int getOfficeRepaireMangeListH5Count(String userId,
			String unitId, String areaId, String type, String[] state,
			Date startTime, Date endTime, String searchContent) {
		String[] areaIds;
		String[] types;
		if(StringUtils.isBlank(areaId) || StringUtils.equals(BaseConstant.ZERO_GUID, areaId)){
			List<OfficeTeachAreaAuth> arealist = officeTeachAreaAuthService.getOfficeTeachAreaAuthList(Constants.REPAIRE_STATE, userId);
			Set<String> ids = new HashSet<String>();
			for(OfficeTeachAreaAuth e : arealist){
				ids.add(e.getTeachAreaId());
			}
			areaIds = ids.toArray(new String[]{});
		}else{
			areaIds = new String[]{areaId};
		}
		if(StringUtils.isBlank(type)){
			List<OfficeTypeAuth> arealist = officeTypeAuthService.getOfficeTypeAuthList(Constants.REPAIRE_STATE, userId);
			Set<String> ids = new HashSet<String>();
			for(OfficeTypeAuth e : arealist){
				ids.add(e.getType());
			}
			types = ids.toArray(new String[]{});
		}else{
			types = new String[]{type};
		}
		if(types == null || types.length == 0||areaIds == null || areaIds.length == 0){
			return 0;
		}
		
		return officeRepaireDao.getOfficeRepaireListH5Count(unitId, areaIds, types, state, startTime, endTime, searchContent);
	}
	
	public void setOfficeRepaireTypeService(
			OfficeRepaireTypeService officeRepaireTypeService) {
		this.officeRepaireTypeService = officeRepaireTypeService;
	}

	public void setOfficeMsgSendingService(
			OfficeMsgSendingService officeMsgSendingService) {
		this.officeMsgSendingService = officeMsgSendingService;
	}

	public void setMcodedetailService(McodedetailService mcodedetailService) {
		this.mcodedetailService = mcodedetailService;
	}
	
	public void setTeachAreaService(TeachAreaService teachAreaService) {
		this.teachAreaService = teachAreaService;
	}

	public void setOfficeTeachAreaAuthService(
			OfficeTeachAreaAuthService officeTeachAreaAuthService) {
		this.officeTeachAreaAuthService = officeTeachAreaAuthService;
	}
	
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public void setOfficeTypeAuthService(OfficeTypeAuthService officeTypeAuthService) {
		this.officeTypeAuthService = officeTypeAuthService;
	}
	public void setUnitService(UnitService unitService) {
		this.unitService = unitService;
	}
	public void setSmsClientService(SmsClientService smsClientService) {
		this.smsClientService = smsClientService;
	}
	public void setTeacherService(TeacherService teacherService) {
		this.teacherService = teacherService;
	}
    public void setBasicClassService(BasicClassService basicClassService) {
        this.basicClassService = basicClassService;
    }
	public void setOfficeRepaireSmsService(
			OfficeRepaireSmsService officeRepaireSmsService) {
		this.officeRepaireSmsService = officeRepaireSmsService;
	}
	public void setAttachmentService(AttachmentService attachmentService) {
		this.attachmentService = attachmentService;
	}
	@Override
	public OfficeRepaire getOfficeRepaireByUserIdLastTime(String userId) {
		return officeRepaireDao.getOfficeRepaireByUserIdLastTime(userId);
	}
	
}
