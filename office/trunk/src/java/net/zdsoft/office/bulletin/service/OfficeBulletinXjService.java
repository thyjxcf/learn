package net.zdsoft.office.bulletin.service;

import java.util.List;
import java.util.Map;

import net.zdsoft.keel.util.Pagination;
import net.zdsoft.office.bulletin.entity.OfficeBulletinXj;
/**
 * office_bulletin_xj
 * @author 
 * 
 */
public interface OfficeBulletinXjService{

	/**
	 * 新增office_bulletin_xj
	 * @param officeBulletinXj
	 * @return
	 */
	public void save(OfficeBulletinXj officeBulletinXj);
	
	/**
	 * 获取当前单位最新的期号
	 * @param unitId
	 * @return
	 */
	public Integer getLatestIssue(String unitId, String type);
	
	/**
	 * 判断当前期号是否可用
	 * @param unitId
	 * @return
	 */
	public boolean isIssueExist(String unitId, String type, int issue, String bulletinId);

	/**
	 * 
	 * @param id
	 * @param userId
	 * @return
	 */
	public Integer delete(String id, String userId);
	
	/**
	 * 根据ids数组删除office_bulletin_xj数据
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
	 * 更新office_bulletin_xj
	 * @param officeBulletinXj
	 * @return
	 */
	public Integer update(OfficeBulletinXj officeBulletinXj);

	/**
	 * 根据id获取office_bulletin_xj
	 * @param id
	 * @return
	 */
	public OfficeBulletinXj getOfficeBulletinXjById(String id);

	/**
	 * 根据ids数组查询office_bulletin_xjmap
	 * @param ids
	 * @return
	 */
	public Map<String, OfficeBulletinXj> getOfficeBulletinXjMapByIds(String[] ids);
	/**
	 * 根据ids数组查询office_bulletin_xjmap
	 * @param ids
	 * @return
	 */
	public List<OfficeBulletinXj> getOfficeBulletinXjListByIds(String[] ids);

	/**
	 * 获取office_bulletin_xj列表
	 * @return
	 */
	public List<OfficeBulletinXj> getOfficeBulletinXjList();

	/**
	 * 分页获取office_bulletin_xj列表
	 * @param startTime
	 * @param endTime
	 * @param publishName
	 * @param page
	 * @return
	 */
	public List<OfficeBulletinXj> getOfficeBulletinXjPage(String unitId, String isReadUserId, String userId,String bulletinType,String state,String startTime,String endTime, String publishName, String searName, String searchAreaId, Pagination page);
	
	/**
	 * 根据条件获取office_bulletin_xj列表
	 * @param unitId
	 * @param areaId 可为空
	 * @param state 可为空
	 * @param startTime
	 * @param endTime
	 * @param searName
	 * @param publishName
	 * @param page 可为空
	 * @return
	 */
	public List<OfficeBulletinXj> getOfficeBulletinXjListPage(String bulletinType,String unitId, String userId, String areaId, String state, String startTime, String endTime, String searName, String publishName, Pagination page);

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