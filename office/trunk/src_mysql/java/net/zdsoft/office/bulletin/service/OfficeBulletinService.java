package net.zdsoft.office.bulletin.service;

import java.util.List;
import java.util.Map;

import net.zdsoft.keel.util.Pagination;
import net.zdsoft.office.bulletin.entity.OfficeBulletin;
/**
 * office_bulletin
 * @author 
 * 
 */
public interface OfficeBulletinService{

	/**
	 * 新增office_bulletin
	 * @param officeBulletin
	 * @return
	 */
	public void save(OfficeBulletin officeBulletin);

	/**
	 * 
	 * @param id
	 * @param userId
	 * @return
	 */
	public Integer delete(String id, String userId);
	
	/**
	 * 根据ids数组删除office_bulletin数据
	 * @param ids
	 * @param userId
	 * @return
	 */
	public Integer deleteIds(String[] ids,String userId);
	
	/**
	 * 发布
	 * @param ids
	 * @return
	 */
	public Integer publish(String[] ids,String userId);

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
	 * @param startTime TODO
	 * @param endTime TODO
	 * @param publishName TODO
	 * @param page
	 * @return
	 */
	public List<OfficeBulletin> getOfficeBulletinPage(String unitId, String isReadUserId, String userId,String bulletinType,String state,String startTime,String endTime, String publishName, String searName, String searchAreaId, Pagination page);
	
	/**
	 * 根据条件获取office_bulletin列表
	 * @param unitId
	 * @param areaId 可为空
	 * @param state 可为空
	 * @param startTime TODO
	 * @param endTime TODO
	 * @param searName TODO
	 * @param publishName TODO
	 * @param page 可为空
	 * @return
	 */
	public List<OfficeBulletin> getOfficeBulletinListPage(String[] bulletinTypes,String unitId, String userId, String areaId, String state, String startTime, String endTime, String searName, String publishName, Pagination page);

	/**
	 * 分教育局学校获取数据
	 * @param bulletinType
	 * @param unitId
	 * @param state
	 * @param tabClass
	 * @param page
	 * @return
	 */
	public List<OfficeBulletin> getOfficeBulletinListPage(String bulletinType,String unitId, String userId, String state, int tabClass,Pagination page);
	
	public Integer submit(String bulletinId);

	public Integer unPublish(String bulletinId,String userId,String idea);
	/**
	 * 置顶
	 * @param bulletinId
	 */
	public void top(String bulletinId);

	public void qxTop(String bulletinId);
	
	public void saveOrder(String[] bulletinIds, String[] orderIds, String userId);

	public void qxPublish(String bulletinId, String userId);
}