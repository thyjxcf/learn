package net.zdsoft.office.bulletin.service;

import java.util.List;

import net.zdsoft.eis.sms.dto.MsgDto;
import net.zdsoft.keel.util.Pagination;
import net.zdsoft.office.bulletin.entity.OfficeBulletinTl;

/**
 * office_bulletin_tl
 * 
 * @author
 * 
 */
public interface OfficeBulletinTlService {

	/**
	 * 新增office_bulletin_tl
	 * 
	 * @param officeBulletinTl
	 * @return
	 */
	public OfficeBulletinTl save(OfficeBulletinTl officeBulletinTl);

	/**
	 * 根据ids数组删除
	 * 
	 * @param ids
	 * @return
	 */
	public void delete(String[] ids, String userId);

	/**
	 * 发布
	 * 
	 * @param ids
	 * @param userId
	 */
	public void publish(String[] ids, String userId);

	/**
	 * 取消发布
	 * 
	 * @param id
	 * @param userId
	 */
	public void qxPublish(String id, String userId);

	/**
	 * 置顶操作
	 * 
	 * @param id
	 * @param topState
	 */
	public void top(String id, Integer topState);

	/**
	 * 设置排序
	 * 
	 * @param bulletinIds
	 * @param orderIds
	 * @param userId
	 */
	public void saveOrder(String[] bulletinIds, String[] orderIds, String userId);

	/**
	 * 更新office_bulletin_tl
	 * 
	 * @param officeBulletinTl
	 * @return
	 */
	public void update(OfficeBulletinTl officeBulletinTl);

	/**
	 * 根据id获取office_bulletin_tl
	 * 
	 * @param id
	 * @return
	 */
	public OfficeBulletinTl getOfficeBulletinTlById(String id);

	/**
	 * 获取查看通知列表
	 * 
	 * @param unitId
	 * @param userId
	 * @param startTime
	 * @param endTime
	 * @param searchName
	 * @param publishName
	 * @param page
	 * @return
	 */
	public List<OfficeBulletinTl> getOfficeBulletinTlListPage(String unitId,
			String userId, String startTime, String endTime, String searchName,
			String publishName, Pagination page);

	/**
	 * 获取单位通知列表
	 * 
	 * @param unitId
	 * @param state
	 * @param startTime
	 * @param endTime
	 * @param searchName
	 * @param publishName
	 * @param page
	 * @return
	 */
	public List<OfficeBulletinTl> getOfficeBulletinManageListPage(
			String unitId, String state, String startTime,
			String endTime, String searchName, String publishName,
			Pagination page);
	
	/**
	 * 发送消息
	 * @param officeBulletinTl
	 * @param msgDto
	 */
	public void sendMsg(OfficeBulletinTl officeBulletinTl, MsgDto msgDto);
	
	/**
	 * 为未读通知人员发送短信提醒
	 * @param ids 通知ID
	 * @param userId 发送人
	 */
	public void remind(String[] ids, String userId); 

}