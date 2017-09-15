package net.zdsoft.eis.base.subsystemcall.service;

import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;
import net.zdsoft.eis.base.common.entity.Unit;
import net.zdsoft.eis.base.common.entity.User;
import net.zdsoft.eis.base.subsystemcall.entity.OfficeMsgSendingDto;
import net.zdsoft.eis.base.subsystemcall.entity.OfficeStepInfoDto;
import net.zdsoft.eis.sms.dto.MsgDto;
import net.zdsoft.keelcnet.action.UploadFile;

/**
 * @author chens
 * @version 创建时间：2015-7-21 下午6:43:56
 * 
 */
public interface OfficeSubsystemService {
	
	/**
	 * 获取该单位请假的学生(jsonarray： [{studentId， classId}])
	 * @param unitId
	 * @param dayTime
	 * @return
	 */
	public String getStuLeaveJsonString(String unitId, Date dayTime);
	/**
	 * 获得某种类型下的请假通过的学生id
	 * @param unitId
	 * @param classId 可以为null
	 * @param leaveType 1.一般请假2.临时出校3住校、通校申请4长期通校
	 * @param applyType 这个参数在leaveType为3的时候启用，1表示申请住校2表示申请通校；可以为null
	 * @param date
	 * @return Set<String>
	 */
	public String getHwStuLeavesByUnitId(String unitId, String classId,
			String leaveType, String applyType, Date date);
	public String getStuLeaveTime(String unitId, String leaveType,
			String studentId, Date date);
	/**
	 * 获得杭外学生的所有请假信息
	 * @param unitId
	 * @param studentId
	 * @return
	 */
	public String getStuLeaveInfo(String unitId, String studentId);
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
	 * @return 
	 */
	public String sendMsgDetail(User user,Unit unit,String title, String content,String simpleContent, boolean isNoteMsg, Integer msgType,String[] userIds, List<UploadFile> uploadFileList);
	
	public String sendMsg(OfficeMsgSendingDto officeMsgSendingDto, List<UploadFile> uploadFileList, MsgDto msgDto);
	
	public void sendMsg(OfficeMsgSendingDto officeMsgSendingDto, List<UploadFile> uploadFileList, MsgDto msgDto, boolean isNotMsg);
	
	public OfficeMsgSendingDto getSendMsgById(String msgId);
	
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
	
	/**
	 * 待处理数接口
	 * @param remoteParam
	 * @return
	 */
	String weikeOfficeCount(String remoteParam);
	
	/**
	 * 消息内容接口
	 * @param remoteParam
	 * @return
	 */
	public String getOfficeMsgDetails(String remoteParam);
	
	/**
	 * 电话本分组详情
	 * @param remoteParam
	 * @return
	 */
	public String getAddressBookDetails(String remoteParam);
	
	/**
	 * 获取当前日期不在岗老师
	 * @param remoteParam
	 * @return
	 */
	public String getAbsenceUsers(String remoteParam);
	
	/**
	 * 获取工作大纲
	 * @param remoteParam
	 * @return
	 */
	public String getWorkArrangeOutlines(String remoteParam);
	
	/**
	 * 通知公告类信息详情
	 * @param remoteParam
	 * @return
	 */
	public String getBulletinDetails(String remoteParam);
	
	/**
	 * 获取考勤信息
	 * @param remoteParam
	 * @return
	 */
	String attendanceDetail(String remoteParam);
	
	/**
	 * 打卡提交
	 * @param remoteParam
	 * @return
	 */
	String attendanceSubmit(String remoteParam);
	
	/**
	 * 补卡申请
	 * @param remoteParam
	 * @return
	 */
	String attendanceApply(String remoteParam);
	
	/**
	 * 考勤统计
	 * @param remoteParam
	 * @return
	 */
	String attendanceCount(String remoteParam);
	
	/**
	 * 考勤月历
	 * @param remoteParam
	 * @return
	 */
	String attendanceMonth(String remoteParam);
	
	/**
	 * 批量更新流程步骤信息
	 * @param flowId
	 * @param flowStepInfo
	 */
	public void batchUpdateFlowStepInfo(String flowId, String flowStepInfo);
	
	/**
	 * 返回步骤信息：stepUserType_taskUserId
	 * @param flowId
	 * @param stepId
	 * @return
	 */
	public OfficeStepInfoDto getStepInfo(String flowId, String stepId);
	
	/**
	 * 通过步骤信息组装DTO类
	 * @param stepInfo
	 * @return
	 */
	public OfficeStepInfoDto formatStepInfo(String stepInfo);
	
	JSONObject validateH5(String unitId, String userId, HttpServletRequest request);

	
}
