package net.zdsoft.office.bulletin.dao;


import java.util.List;
import java.util.Map;

import net.zdsoft.keel.util.Pagination;
import net.zdsoft.office.bulletin.entity.OfficeBulletin;
/**
 * office_bulletin
 * @author 
 * 
 */
public interface OfficeBulletinDao{

	/**
	 * 新增office_bulletin
	 * @param officeBulletin
	 * @return
	 */
	public OfficeBulletin save(OfficeBulletin officeBulletin);

	/**
	 * 
	 * @param id
	 * @param userId
	 * @return
	 */
	public Integer delete(String id,String userId);
	
	/**
	 * 根据ids数组删除office_bulletin
	 * @param ids
	 * @param userId
	 * @return
	 */
	public Integer deleteIds(String[] ids,String userId);
	
	/**
	 * 根据ids数组删除office_bulletin
	 * @param ids
	 * @return
	 */
	public Integer publish(String[] ids,String userId);
	
	public Integer submit(String id);

	/**
	 * 更新office_bulletin
	 * @param officeBulletin
	 * @return
	 */
	public Integer update(OfficeBulletin officeBulletin);

	/**
	 * 根据id获取office_bulletin
	 * @param id
	 * @return
	 */
	public OfficeBulletin getOfficeBulletinById(String id);

	/**
	 * 根据ids数组查询office_bulletinmap
	 * @param ids
	 * @return
	 */
	public Map<String, OfficeBulletin> getOfficeBulletinMapByIds(String[] ids);

	/**
	 * 获取office_bulletin列表
	 * @return
	 */
	public List<OfficeBulletin> getOfficeBulletinList();

	/**
	 * 分页获取office_bulletin列表
	 * @param startTime
	 * @param endTime
	 * @param userIds 
	 * @param page
	 * @return
	 */
	public List<OfficeBulletin> getOfficeBulletinPage(String unitId, String userId,String[] bulletinType,String state,String startTime,String endTime, String searName, String[] userIds, String searchAreaId, Pagination page);
	
	/**
	 * 学校通知查看
	 * @param type
	 * @param unitId
	 * @param areaId
	 * @param state
	 * @param startTime
	 * @param endTime
	 * @param searName
	 * @param userIds
	 * @param page 可为空
	 * @return
	 */
	public List<OfficeBulletin> getOfficeBulletinListPage(String[] types,String unitId, String areaId, String state, String startTime, String endTime, String searName, String[] userIds, Pagination page);

	/**
	 * 分别获取教育局及学校通知公告信息
	 * @param type
	 * @param unitId
	 * @param parentId
	 * @param state
	 * @param tabClass
	 * @param page
	 * @return
	 */
	public List<OfficeBulletin> getOfficeBulletinListPage(String type, String unitId, String parentId, String state, int tabClass, Pagination page);
	
	public Integer unPublish(String bulletinId, String userId, String idea);

	public void top(String bulletinId);

	public void qxTop(String bulletinId);
	
	public void saveOrder(String[] bulletinIds, String[] orderIds, String userId);

	public Integer qxPublish(String id, String userId);
	/**
	 * 
	 * @param type
	 * @param unitId
	 * @param areaId
	 * @param state
	 * @param startTime
	 * @param endTime
	 * @param searName
	 * @param userIds
	 * @param topId TODO
	 * @param parentId TODO
	 * @param page
	 * @return
	 */
	public List<OfficeBulletin> getOfficeBulletinListPage1(String[] types,
			String unitId, String areaId, String state, String startTime,
			String endTime, String searName, String[] userIds, String topId, String parentId, Pagination page);
}