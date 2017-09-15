package net.zdsoft.office.msgcenter.service;

import java.util.List;
import java.util.Map;

import net.zdsoft.eis.base.attachment.entity.Attachment;
import net.zdsoft.eis.sms.dto.MsgDto;
import net.zdsoft.keel.util.Pagination;
import net.zdsoft.keelcnet.action.UploadFile;
import net.zdsoft.office.msgcenter.dto.MessageSearch;
import net.zdsoft.office.msgcenter.entity.OfficeMsgSending;
/**
 * office_msg_sending
 * @author 
 * 
 */
public interface OfficeMsgSendingService{

	/**
	 * 新增
	 * @param officeMsgSending
	 * @param uploadFileList
	 * @param msgDto 短信信息
	 */
	public void save(OfficeMsgSending officeMsgSending, List<UploadFile> uploadFileList, MsgDto msgDto);
	
	public void save(OfficeMsgSending officeMsgSending, List<UploadFile> uploadFileList, MsgDto msgDto, boolean isNotMsg);
	/**
	 * 手机端新增
	 * @param officeMsgSending
	 * @param uploadFileList
	 * @param msgDto 短信信息
	 */
	public void saveByMobile(OfficeMsgSending officeMsgSending, List<Attachment> attachmentList, MsgDto msgDto);

	/**
	 * 根据ids数组删除office_msg_sending数据
	 * @param ids
	 * @return
	 */
	public void delete(String[] ids, Integer state);
	
	/**
	 * 移动到自定义文件夹
	 * @param ids
	 * @param folderId
	 * @param msgState
	 */
	public void turnToFolder(String[] ids, String folderId, Integer msgState, boolean isCopy);
	
	/**
	 * 移动单条信息到自定义信息夹
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
	public void revertById(String id, Integer state);

	/**
	 * 更新office_msg_sending
	 * @param officeMsgSending
	 * @param uploadFileList
	 * @param removeAttachment
	 * @param msgDto 短信信息
	 * @return
	 */
	public void update(OfficeMsgSending officeMsgSending, List<UploadFile> uploadFileList, String[] removeAttachment, MsgDto msgDto);
	
	/**
	 * 手机端更新office_msg_sending
	 * @param officeMsgSending
	 * @param attachmentList
	 * @param removeAttachment
	 * @param msgDto 短信信息
	 * @return
	 */
	public void updateByMobile(OfficeMsgSending officeMsgSending, List<Attachment> attachmentList, String[] removeAttachment, MsgDto msgDto);

	/**
	 * 撤回
	 * @param msgId
	 */
	public void callBackMsg(String msgId);
	
	/**
	 * 获取简洁的数据，供转发，回复之类使用
	 * @param id
	 * @param operateType 操作类型
	 * @return
	 */
	public OfficeMsgSending getOfficeMsgSendingSimpleById(String id, String operateType);
	
	/**
	 * 根据id获取office_msg_sending
	 * @param id
	 * @return
	 */
	public OfficeMsgSending getOfficeMsgSendingById(String id);
	
	/**
	 * 获取接收人
	 * @param id
	 * @return
	 */
	public String getOfficeMsgSendingUserNames(String id);

	/**
	 * 根据ids数组查询office_msg_sendingmap
	 * @param ids
	 * @return
	 */
	public Map<String, OfficeMsgSending> getOfficeMsgSendingMapByIds(String[] ids);

	/**
	 * 根据条件获取发件箱列表
	 * @param messageSearch
	 * @param page
	 * @return
	 */
	public List<OfficeMsgSending> getOfficeMsgSendingList(MessageSearch messageSearch,Pagination page);

	/**
	 * 分页获取office_msg_sending列表
	 * @param page
	 * @return
	 */
	public List<OfficeMsgSending> getOfficeMsgSendingPage(Pagination page);

	/**
	 * 根据UnitId获取office_msg_sending列表
	 * @param unitId
	 * @return
	 */
	public List<OfficeMsgSending> getOfficeMsgSendingByUnitIdList(String unitId);

	/**
	 * 根据UnitId分页获取office_msg_sending
	 * @param unitId
	 * @param page
	 * @return
	 */
	public List<OfficeMsgSending> getOfficeMsgSendingByUnitIdPage(String unitId, Pagination page);

	/**
	 * 获取草稿箱数量
	 * @param userId
	 * @return
	 */
	public Integer getUnSendNum(String userId);
}