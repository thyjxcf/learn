package net.zdsoft.eis.base.subsystemcall.service;

import java.util.List;
import java.util.Set;

import net.zdsoft.eis.base.common.entity.Unit;
import net.zdsoft.eis.base.common.entity.User;
import net.zdsoft.eis.base.subsystemcall.entity.OfficeMsgSendingDto;
import net.zdsoft.eis.sms.dto.MsgDto;
import net.zdsoft.keelcnet.action.UploadFile;

/**
 * @author chens
 * @version 创建时间：2015-7-21 下午6:43:56
 * 
 */
public interface OfficeSubsystemService {
	
	/**
	 * 
	 * @param user	创建消息用户
	 * @param unit	创建消息单位
	 * @param title	标题
	 * @param content	内容--可带html标签的
	 * @param simpleContent	内容--不带html标签的；同时作为短信内容
	 * @param isNoteMsg	是否需要发送短信
	 * @param msgType	微代码‘DM-MSGTYPE’，BaseConstant中
	 * @param users	发送用户
	 * @param uploadFileList	附件，消息那边查看
	 */
	public void sendMsgDetail(User user,Unit unit,String title, String content,String simpleContent, boolean isNoteMsg, Integer msgType,String[] userIds, List<UploadFile> uploadFileList);
	
	public void sendMsg(OfficeMsgSendingDto officeMsgSendingDto, List<UploadFile> uploadFileList, MsgDto msgDto);
	
	public void sendMsg(OfficeMsgSendingDto officeMsgSendingDto, List<UploadFile> uploadFileList, MsgDto msgDto, boolean isNotMsg);
	
	/**
	 * 获取请假学生
	 * @param time
	 * @param studentIds
	 * @return
	 */
	public Set<String> remoteStuIsLeaveByonetime(String[] studentIds, String time);
	
	/**
	 * 获取请假学生
	 * @param studentIds
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public Set<String> remoteStuIsLeaveBytwotime(String[] studentIds, String startTime, String endTime);
	/**
	 * 判断签到时间是否过期
	 * @param userId
	 * @param ownerType
	 * @param modSets
	 * @return
	 */
	public boolean getOfficeSigntimeSet(String unitId, String time);
	/**
	 * 判断是否签到
	 * @param userId
	 * @param ownerType
	 * @param modSets
	 * @return
	 */
	public boolean getOfficeSignedOnList(String userId, String years,String semester,String unitId,String time);
	/**
	 * 判断是否有权限
	 * @param userId
	 * @param ownerType
	 * @param modSets
	 * @return
	 */
	public boolean getOfficeSigned(String signInSystem);
	
	/**
	 * 初始化二维码
	 */
	public void initQrCode();
	
}
