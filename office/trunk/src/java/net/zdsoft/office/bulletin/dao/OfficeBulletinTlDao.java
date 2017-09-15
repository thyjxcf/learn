package net.zdsoft.office.bulletin.dao;

import java.util.List;

import net.zdsoft.keel.util.Pagination;
import net.zdsoft.office.bulletin.entity.OfficeBulletinTl;
/**
 * office_bulletin_tl
 * @author 
 * 
 */
public interface OfficeBulletinTlDao{

	/**
	 * 新增office_bulletin_tl
	 * @param officeBulletinTl
	 * @return
	 */
	public OfficeBulletinTl save(OfficeBulletinTl officeBulletinTl);

	/**
	 * 根据ids数组删除office_bulletin_tl
	 * @param ids
	 * @return
	 */
	public Integer delete(String[] ids, String userId);
	
	/**
	 * 发布
	 * 
	 * @param ids
	 * @param userId
	 */
	public void publish(String[] ids, String userId);
	
	/**
	 * 取消发布
	 * @param id
	 * @param userId
	 * @return
	 */
	public void qxPublish(String id, String userId);
	
	/**
	 * 设置是否置顶
	 * @param id
	 * @param topState
	 */
	public void top(String id, Integer topState);

	/**
	 * 更新office_bulletin_tl
	 * @param officeBulletinTl
	 * @return
	 */
	public Integer update(OfficeBulletinTl officeBulletinTl);
	
	/**
	 * 设置排序
	 * 
	 * @param bulletinIds
	 * @param orderIds
	 * @param userId
	 */
	public void saveOrder(String[] bulletinIds, String[] orderIds, String userId);

	/**
	 * 根据id获取office_bulletin_tl
	 * @param id
	 * @return
	 */
	public OfficeBulletinTl getOfficeBulletinTlById(String id);
	
	/**
	 * 获取单位通知列表
	 * 
	 * @param unitId
	 * @param state
	 * @param startTime
	 * @param endTime
	 * @param searchName
	 * @param userIds
	 * @param page
	 * @return
	 */
	public List<OfficeBulletinTl> getOfficeBulletinManageListPage(
			String unitId, String state, String startTime,
			String endTime, String searchName,String[] userIds,
			Pagination page);
	
	/**
	 * 获取可查看通知列表
	 * 
	 * @param unitId
	 * @param state
	 * @param startTime
	 * @param endTime
	 * @param searchName
	 * @param userIds
	 * @param page
	 * @return
	 */
	public List<OfficeBulletinTl> getOfficeBulletinTlListViewPage(
			String unitId, String startTime,
			String endTime, String searchName,String[] userIds,
			Pagination page);

	public List<OfficeBulletinTl> getBulletinTlByIds(String[] ids);
	
}