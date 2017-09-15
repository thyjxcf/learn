package net.zdsoft.office.msgcenter.dao;

import java.util.List;
import java.util.Map;

import net.zdsoft.keel.util.Pagination;
import net.zdsoft.office.msgcenter.dto.MessageSearch;
import net.zdsoft.office.msgcenter.entity.OfficeMsgSending;
/**
 * office_msg_sending
 * @author 
 * 
 */
public interface OfficeMsgSendingDao{

	/**
	 * 新增office_msg_sending
	 * @param officeMsgSending
	 * @return
	 */
	public OfficeMsgSending save(OfficeMsgSending officeMsgSending);

	/**
	 * 根据ids数组删除office_msg_sending
	 * @param ids
	 * @return
	 */
	public void updateStateByIds(String[] ids, Integer state);
	
	/**
	 * 撤回
	 * @param id
	 */
	public void updateWithdraw(String id);
	
	/**
	 * 还原
	 * @param id
	 */
	public void updateRevertById(String id, Integer state);

	/**
	 * 更新office_msg_sending
	 * @param officeMsgSending
	 * @return
	 */
	public Integer update(OfficeMsgSending officeMsgSending);

	/**
	 * 根据id获取office_msg_sending
	 * @param id
	 * @return
	 */
	public OfficeMsgSending getOfficeMsgSendingById(String id);

	/**
	 * 根据ids数组查询office_msg_sendingmap
	 * @param ids
	 * @return
	 */
	public Map<String, OfficeMsgSending> getOfficeMsgSendingMapByIds(String[] ids);

	/**
	 * 获取office_msg_sending列表
	 * @return
	 */
	public List<OfficeMsgSending> getOfficeMsgSendingList(String[] ids);
	
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
	 * 根据unitId获取office_msg_sending列表
	 * @param unitId
	 * @return
	 */
	public List<OfficeMsgSending> getOfficeMsgSendingByUnitIdList(String unitId);

	/**
	 * 根据unitId分页office_msg_sending获取
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