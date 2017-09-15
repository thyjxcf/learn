package net.zdsoft.office.subsystemcall.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import net.zdsoft.eis.base.common.entity.Unit;
import net.zdsoft.eis.base.common.entity.User;
import net.zdsoft.eis.base.common.service.SystemIniService;
import net.zdsoft.eis.base.constant.BaseConstant;
import net.zdsoft.eis.base.subsystemcall.entity.OfficeMsgSendingDto;
import net.zdsoft.eis.base.subsystemcall.service.OfficeSubsystemService;
import net.zdsoft.eis.sms.dto.MsgDto;
import net.zdsoft.keelcnet.action.UploadFile;
import net.zdsoft.office.dailyoffice.entity.OfficeSignedOn;
import net.zdsoft.office.dailyoffice.entity.OfficeSigntimeSet;
import net.zdsoft.office.dailyoffice.service.OfficeSignedOnService;
import net.zdsoft.office.dailyoffice.service.OfficeSigntimeSetService;
import net.zdsoft.office.msgcenter.entity.OfficeMsgSending;
import net.zdsoft.office.msgcenter.service.OfficeMsgSendingService;
import net.zdsoft.office.qrCode.service.QrCodeService;
import net.zdsoft.office.studentLeave.entity.OfficeStudentLeave;
import net.zdsoft.office.studentLeave.service.OfficeStudentLeaveService;
import net.zdsoft.office.util.Constants;

import org.apache.commons.lang.StringUtils;
import org.springframework.util.CollectionUtils;

/**
 * @author chens
 * @version 创建时间：2015-7-21 下午6:43:56
 * 
 */
public class OfficeSubsystemServiceImpl implements OfficeSubsystemService{

	private OfficeMsgSendingService officeMsgSendingService;
	private OfficeStudentLeaveService officeStudentLeaveService;
	private OfficeSigntimeSetService officeSigntimeSetService;
	private OfficeSignedOnService officeSignedOnService;
	private SystemIniService systemIniService;
	private QrCodeService qrCodeService;
	

	@Override
	public void sendMsgDetail(User user, Unit unit, String title, String content, String simpleContent,
			boolean isNoteMsg, Integer msgType, String[] userIds, List<UploadFile> uploadFileList) {
		OfficeMsgSendingDto officeMsgSending = new OfficeMsgSendingDto();
		officeMsgSending.setCreateUserId((user==null?BaseConstant.ZERO_GUID:user.getId()));
		officeMsgSending.setSendUserName((user==null||StringUtils.isBlank(user.getRealname())?"系统发送":user.getRealname()));
		officeMsgSending.setUnitId((unit==null?BaseConstant.ZERO_GUID:unit.getId()));
		officeMsgSending.setTitle(title);
		officeMsgSending.setContent(content);
		officeMsgSending.setSimpleContent(simpleContent);
		StringBuffer mainUserString=new StringBuffer();
		for(int i=0;i<userIds.length;i++){
			if(i==userIds.length-1){
				mainUserString.append(userIds[i]);
			}else{
				mainUserString.append(userIds[i]+",");
			}
		}
		officeMsgSending.setUserIds(mainUserString.toString());
		officeMsgSending.setIsNeedsms(isNoteMsg);
		officeMsgSending.setMsgType(msgType);
		
		MsgDto msgDto = null;
		if(isNoteMsg){
			msgDto = new MsgDto();
			msgDto.setUnitName((unit!=null?unit.getName():"系统单位"));
			msgDto.setUserId((user==null?BaseConstant.ZERO_GUID:user.getId()));
			msgDto.setUserName((user==null||StringUtils.isBlank(user.getRealname())?"系统发送":user.getRealname()));
			msgDto.setContent(simpleContent);
		}
		sendMsg(officeMsgSending, uploadFileList, msgDto);
	}
	
	public void sendMsg(OfficeMsgSendingDto officeMsgSendingDto, List<UploadFile> uploadFileList, MsgDto msgDto){
		OfficeMsgSending officeMsgSending = new OfficeMsgSending();
		toEntity(officeMsgSendingDto, officeMsgSending);
		officeMsgSendingService.save(officeMsgSending, uploadFileList, msgDto);
	}
	
	public void sendMsg(OfficeMsgSendingDto officeMsgSendingDto, List<UploadFile> uploadFileList, MsgDto msgDto, boolean isNotMsg){
		OfficeMsgSending officeMsgSending = new OfficeMsgSending();
		toEntity(officeMsgSendingDto, officeMsgSending);
		officeMsgSendingService.save(officeMsgSending, uploadFileList, msgDto, isNotMsg);
	}
	
	public void toEntity(OfficeMsgSendingDto officeMsgSendingDto, OfficeMsgSending officeMsgSending){
		officeMsgSending.setTitle(officeMsgSendingDto.getTitle());
		officeMsgSending.setContent(officeMsgSendingDto.getContent());
		officeMsgSending.setIsNeedsms(officeMsgSendingDto.getIsNeedsms());
		officeMsgSending.setSmsContent(officeMsgSendingDto.getSmsContent());
		officeMsgSending.setCreateUserId(officeMsgSendingDto.getCreateUserId());
		officeMsgSending.setUnitId(officeMsgSendingDto.getUnitId());
		officeMsgSending.setSimpleContent(officeMsgSendingDto.getSimpleContent());
		officeMsgSending.setUserIds(officeMsgSendingDto.getUserIds());
		officeMsgSending.setSendUserName(officeMsgSendingDto.getSendUserName());
		officeMsgSending.setMsgType(officeMsgSendingDto.getMsgType());
		officeMsgSending.setState(Constants.MSG_STATE_SEND);
		officeMsgSending.setMsgType(officeMsgSendingDto.getMsgType());
	}

	public void setOfficeMsgSendingService(
			OfficeMsgSendingService officeMsgSendingService) {
		this.officeMsgSendingService = officeMsgSendingService;
	};
	

	//时间点获取请假学生
	public Set<String> remoteStuIsLeaveByonetime(String[] studentIds, String time){
		Set<String> stuIds = new HashSet<String>();
		SimpleDateFormat sdf = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" );
		Date date;
		try {
			date= sdf.parse(time);
		} catch (ParseException e) {
			return stuIds;
		}
		if(studentIds==null || studentIds.length<=0){
			return stuIds;
		}
		
		
		List<OfficeStudentLeave> list = officeStudentLeaveService.findStuIsLeaveBytime(date,studentIds);
		
		return findstudentIdFromList(list);
	}
	
	//时间段获取请假学生
	public Set<String> remoteStuIsLeaveBytwotime(String[] studentIds, String startTime, String endTime){
		Set<String> stuIds = new HashSet<String>();
		Date start;
		Date end;
		SimpleDateFormat sdf = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" );
		
		if(StringUtils.isBlank(startTime)){
			return stuIds;
		}
		if(StringUtils.isBlank(endTime)){
			return stuIds;
		}
		try {
			start= sdf.parse(startTime);
		} catch (ParseException e) {
			return stuIds;
		}
		try {
			end= sdf.parse(endTime);
		} catch (ParseException e) {
			return stuIds;
		}
		if(start.after(end)){
			return stuIds;
		}
		if(studentIds==null || studentIds.length<=0){
			return stuIds;
		}
		List<OfficeStudentLeave> list = officeStudentLeaveService.findStuIsLeaveBytime(start,end,studentIds);
		return findstudentIdFromList(list);
	}

	private Set<String> findstudentIdFromList(List<OfficeStudentLeave> list){
		Set<String> stuIds = new HashSet<String>();
		if(CollectionUtils.isEmpty(list)){
			return stuIds;
		}else{
			for(OfficeStudentLeave off:list){
				String stuid = off.getStudentId();
				stuIds.add(stuid);
			}
			return stuIds;
		}
	}
	
	public void setOfficeStudentLeaveService(
			OfficeStudentLeaveService officeStudentLeaveService) {
		this.officeStudentLeaveService = officeStudentLeaveService;
	}

	@Override
	public boolean getOfficeSigntimeSet(String unitId, String time) {
		OfficeSigntimeSet officeSigntimeSet=officeSigntimeSetService.getOfficeSigntimeSetByUnitIdPage(unitId, time);
		if(officeSigntimeSet!=null){
			return true;
		}
		return false;
	}

	@Override
	public boolean getOfficeSignedOnList(String userId, String years,
			String semester, String unitId, String time) {
		List<OfficeSignedOn> officeSignedOnList=officeSignedOnService.
				getOfficeSignedOnByUnitIdTime(userId, years, semester, unitId,time);
		if(org.apache.commons.collections.CollectionUtils.isNotEmpty(officeSignedOnList)){
			return true;
		}
		return false;
	}

	@Override
	public boolean getOfficeSigned(String signInSystem) {
		String standardValue = systemIniService
				.getValue(signInSystem);
		if(StringUtils.isNotBlank(standardValue) && "1".equals(standardValue)){
			return true;
		}
		return false;
	}

	public void setOfficeSigntimeSetService(
			OfficeSigntimeSetService officeSigntimeSetService) {
		this.officeSigntimeSetService = officeSigntimeSetService;
	}

	public void setOfficeSignedOnService(OfficeSignedOnService officeSignedOnService) {
		this.officeSignedOnService = officeSignedOnService;
	}

	public void setSystemIniService(SystemIniService systemIniService) {
		this.systemIniService = systemIniService;
	}

	public void setQrCodeService(QrCodeService qrCodeService) {
		this.qrCodeService = qrCodeService;
	}
	
	@Override
	public void initQrCode() {
		qrCodeService.init();
	}

}
