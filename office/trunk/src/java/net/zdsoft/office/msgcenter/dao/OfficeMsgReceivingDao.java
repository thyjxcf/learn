package net.zdsoft.office.msgcenter.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import net.zdsoft.keel.util.Pagination;
import net.zdsoft.office.msgcenter.dto.MessageSearch;
import net.zdsoft.office.msgcenter.entity.OfficeMsgReceiving;
/**
 * 信息接受信息表
 * @author 
 * 
 */
public interface OfficeMsgReceivingDao{

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
	 * 根据ids数组删除信息接受信息表
	 * @param ids
	 * @return
	 */
	public Integer delete(String[] ids);
	
	/**
	 * 还原
	 * @param id
	 */
	public void updateRevertById(String id);
	
	/**
	 * 根据会话id删除信息
	 * @param replyMsgIds
	 */
	public void deleteByReplyMsgIds(String receiveUserId, String[] replyMsgIds);

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
	public void updateReadAll(String receiveUserId);
	
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
	 * 获取已读未读人员ids
	 * @param messageId
	 * @param isRead
	 * @return
	 */
	public List<String> getIsReadInfo(String messageId, Integer isRead);

	/**
	 * 根据ids数组查询信息接受信息表map
	 * @param ids
	 * @return
	 */
	public Map<String, OfficeMsgReceiving> getOfficeMsgReceivingMapByIds(String[] ids);
	
	/**
	 * 根据会话ids获取数据列表
	 * @param receiveUserId
	 * @param replyMsgIds
	 * @return
	 */
	public List<OfficeMsgReceiving> getOfficeMsgReceivingList(String receiveUserId, String[] replyMsgIds);
	
	/**
	 * 根据ids获取数据列表
	 * @param receiveUserId
	 * @param replyMsgIds
	 * @return
	 */
	public List<OfficeMsgReceiving> getOfficeMsgReceivingList(String[] ids);

	/**
	 * 获取信息接受信息表列表
	 * @return
	 */
	public List<OfficeMsgReceiving> getOfficeMsgReceivingList(MessageSearch messageSearch, Pagination page);

	/**
	 * 获取加星的map
	 * @param receiveUserId
	 * @param replyMsgIds
	 * @return
	 */
	public Map<String, String> getStarMapByreplyMsgIds(String receiveUserId, String[] replyMsgIds);
	
	/**
	 * 获取待办的map
	 * @param receiveUserId
	 * @param replyMsgIds
	 * @return
	 */
	public Map<String, String> getNeedTodoMapByreplyMsgIds(String receiveUserId, String[] replyMsgIds);
	
	/**
	 * 获取加星的map
	 * @param receiveUserId
	 * @param replyMsgIds
	 * @return
	 */
	public Map<String, String> getReceivingUnReadMapByreplyMsgIds(String receiveUserId, String[] replyMsgIds);
	
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
	public List<OfficeMsgReceiving> getOfficeMsgReceivingListByReplyMsgIdForMobile(String replyMsgId, String[] allIds);
	/**
	 * 根据replyMsgId获取会话列表
	 * @param replyMsgId
	 * @param userId
	 * @return
	 */
	public List<OfficeMsgReceiving> getOfficeMsgReceivingListByReplyMsgId(String replyMsgId, String userId);
	
	/**
	 * 获取这个会话中，为发件人的信息
	 * @param replyMsgId
	 * @param userId
	 * @return
	 */
	public List<OfficeMsgReceiving> getOfficeMsgReceivingListSend(String replyMsgId, String userId);
	
	/**
	 * 获取会话条数map
	 * @param replyMsgId
	 * @param userId
	 * @return
	 */
	public Map<String, Integer> getOfficeMsgReceivingCountMap(
			String[] replyMsgId, String userId);
	/**
	 * 获取已读人员map信息
	 * @param msgSendIds
	 * @return
	 */
	public Map<String, String> findMessageReadStatus(String[] msgSendIds);
	
	/**
	 * 整体设置加星
	 * @param receiveUserId
	 * @param replyMsgId
	 * @param hasStar
	 */
	public void updateAllStar(String receiveUserId, String replyMsgId, Integer hasStar);
	
	/**
	 * 整体设置加星
	 * @param receiveUserId
	 * @param replyMsgId
	 * @param needTodo
	 */
	public void updateAllNeedTodo(String receiveUserId, String replyMsgId, Integer needTodo);
	
	/**
	 * 单条待办
	 * @param id
	 * @param needTodo
	 */
	public void updateNeedTodo(String id, Integer needTodo);
	
	/**
	 * 单条加星
	 * @param id
	 * @param hasStar
	 */
	public void updateStar(String id, Integer hasStar);
	
	/**
	 * 获取最近联系人
	 * @param sendUserId
	 * @param size
	 * @return
	 */
	public List<String> findReceiveUserIds(String sendUserId, int size);
	
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
     * 按照收件人信息获取
     * @param receiveUserId
     * @return
     */
    public List<OfficeMsgReceiving> getOfficeMsgReceivings(String receiveUserId);
    
    public Map<String, Date> getReadDate(String messageId);
}