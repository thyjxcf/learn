package net.zdsoft.office.msgcenter.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import net.zdsoft.eis.base.common.entity.User;
import net.zdsoft.eis.sms.dto.MsgDto;
import net.zdsoft.keel.util.Pagination;
import net.zdsoft.office.msgcenter.dto.MessageSearch;
import net.zdsoft.office.msgcenter.dto.ReadInfoDto;
import net.zdsoft.office.msgcenter.entity.OfficeMsgReceiving;
/**
 * 信息接受信息表
 * @author 
 * 
 */
public interface OfficeMsgReceivingService{

	/**
	 * 新增信息接受信息表
	 * @param officeMsgReceiving
	 * @return
	 */
	public OfficeMsgReceiving save(OfficeMsgReceiving officeMsgReceiving);
	
	/**
	 * 批量新增收件人信息
	 * @param officeMsgReceivings
	 */
	public void batchSave(List<OfficeMsgReceiving> officeMsgReceivings);

	/**
	 * 根据ids数组删除信息接受信息表数据
	 * @param ids
	 * @return
	 */
	public Integer delete(String[] ids);
	
	/**
	 * 根据会话ids移动到自定义文件夹
	 * @param receiveUserId
	 * @param replyMsgIds
	 * @param folderId
	 */
	public void turnToFolder(String receiveUserId, String[] replyMsgIds, String folderId, boolean isCopy);
	
	/**
	 * 废件箱根据ids移动到自定义信息夹
	 * @param ids
	 * @param folderId
	 */
	public void turnToFolderFromDraft(String[] ids, String folderId);
	
	/**
	 * 移动或拷贝单条信息到自定义信息夹
	 * @param id
	 * @param folderId
	 * @param msgState
	 * @param isCopy
	 */
	public void turnSingleToFolder(String id, String folderId, Integer msgState, boolean isCopy);
	
	/**
	 * 还原
	 * @param id
	 */
	public void revertById(String id);
	
	/**
	 * 删除单条信息
	 * @param id
	 */
	public void deleteById(String id);
	
	/**
	 * 根据会话id删除收件箱数据
	 * @param receiveUserId
	 * @param replyMsgIds
	 */
	public void removeByReplyMsgIds(String receiveUserId, String[] replyMsgIds);

	/**
	 * 更新信息接受信息表
	 * @param officeMsgReceiving
	 * @return
	 */
	public Integer update(OfficeMsgReceiving officeMsgReceiving);
	
	/**
	 * 全部设置为已读
	 * @param receiveUserId
	 */
	public void readAll(String receiveUserId);
	
	/**
	 * 设置为已读
	 * @param id
	 */
	public void updateRead(String id);
	/**
	 * 已数组ids设置是否已读
	 * @param ids
	 */
	public void changeState(String receiveUserId,String[] ids,int state);
	
	/**
	 * 被撤回
	 * @param messageId
	 */
	public void updateIsWithdraw(String messageId);
	
	/**
	 * 获取数量
	 * @param userId
	 * @param readType
	 * @return
	 */
	public Integer getNumber(String userId, String readType);
	
	public Integer getNumber(String userId, String msgType, String readType);

	public Integer getNumberForReceive(MessageSearch search, String msgType, String readType);

	/**
	 * 根据id获取信息接受信息表
	 * @param id
	 * @return
	 */
	public OfficeMsgReceiving getOfficeMsgReceivingById(String id);
	
	/**
	 * 获取已读未读userid
	 * @param messageId
	 * @param isRead
	 * @return
	 */
	public List<String> getIsReadInfoUserIds(String messageId, Integer isRead);
	
	/**
	 * 获取已读未读人员信息
	 * @param messageId
	 * @param isRead
	 * @return
	 */
	public List<ReadInfoDto> getIsReadInfo(String messageId, Integer isRead);
	
	/**
	 * 提醒未接收短信人员
	 * @param messageId
	 * @param msgDto
	 */
	public void remindSms(String messageId, MsgDto msgDto);
	
	/**
	 * 根据ids数组查询信息接受信息表map
	 * @param ids
	 * @return
	 */
	public Map<String, OfficeMsgReceiving> getOfficeMsgReceivingMapByIds(String[] ids);

	/**
	 * 获取信息接受信息表列表
	 * @return
	 */
	public List<OfficeMsgReceiving> getOfficeMsgReceivingList(MessageSearch messageSearch, Pagination page);
	
	/**
	 * 获取重要消息列表
	 * @return
	 */
	public List<OfficeMsgReceiving> getOfficeMsgReceivingImportList(MessageSearch messageSearch, Pagination page);
	
	/**
	 * 获取待办消息列表
	 * @return
	 */
	public List<OfficeMsgReceiving> getOfficeMsgReceivingTodoList(MessageSearch messageSearch, Pagination page);
	
	/**
	 * 根据messageId获取接收人员列表
	 * @param messageId
	 * @return
	 */
	public List<OfficeMsgReceiving> getOfficeMsgReceivingListByMessageId(String messageId);
	
	public List<OfficeMsgReceiving> getOfficeMsgReceivingListByMessageIds(String[] messageIds);
	
	/**
	 * 根据replyMsgId获取会话列表
	 * @param replyMsgId
	 * @param userId
	 * @return
	 */
	public List<OfficeMsgReceiving> getOfficeMsgReceivingListByReplyMsgIdForMobile(String replyMsgId, String userId
			,boolean deptRole,boolean unitRole);
	/**
	 * 根据replyMsgId获取会话列表
	 * @param replyMsgId
	 * @param userId
	 * @return
	 */
	public List<OfficeMsgReceiving> getOfficeMsgReceivingListByReplyMsgId(String replyMsgId, String userId);
	
	/**
	 * 整体设置加星
	 * @param receiveUserId
	 * @param replyMsgId
	 * @param hasStar
	 */
	public void changeAllStar(String receiveUserId, String replyMsgId, Integer hasStar);
	
	/**
	 * 整体设置待办
	 * @param receiveUserId
	 * @param replyMsgId
	 * @param needTodo
	 */
	public void changeAllNeedTodo(String receiveUserId, String replyMsgId, Integer needTodo);
	
	/**
	 * 单条记录待办
	 * @param id
	 * @param needTodo
	 */
	public void changeNeedTodo(String id, Integer needTodo);
	
	/**
	 * 单条记录加星
	 * @param id
	 * @param hasStar
	 */
	public void changeStar(String id, Integer hasStar);
	
	/**
	 * 获取已读人员map信息
	 * @param msgSendIds
	 * @return
	 */
	public Map<String, String> findMessageReadStatus(String[] msgSendIds);
	
	/**
	 * 获取最近联系人
	 * @param sendUserId
	 * @param size
	 * @return
	 */
	public List<User> findReceiveUsers(String sendUserId, int size);
	
	/**
	 * 获取这个时间段以后发生过变化的数据userIds 推送IM使用
	 * @param time
	 * @return
	 */
	public String[] getChangedReceivingUserIds(String time);
	
	/**
     * 根据userIds获取未读条数
     * @param userIds
     * @return
     */
    public Map<String, Integer> getMsgReceivingMap(String[] userIds);
    
    /**
     * 获取未推送的数据
     * @return
     */
    public List<OfficeMsgReceiving> getMsgUnPushed(Pagination page);
    
    /**
     * 更新推送状态
     * @param ids
     */
    public void updatePushed(String[] ids);
    
    /**
	 * 消息内容接口
	 * @param userId
	 * @return
	 */
	public List<OfficeMsgReceiving> getOfficeMsgDetails(String userId);
	
	/**
	 * 获取读消息的时间信息
	 * @param messageId
	 * @return
	 */
	public Map<String, Date> getReadDate(String messageId);
}