package net.zdsoft.office.bulletin.dao;

import java.util.List;
import java.util.Map;

import net.zdsoft.keel.util.Pagination;
import net.zdsoft.office.bulletin.entity.OfficeBulletinRead;
/**
 * office_bulletin_read
 * @author 
 * 
 */
public interface OfficeBulletinReadDao{

	/**
	 * 新增office_bulletin_read
	 * @param officeBulletinRead
	 * @return
	 */
	public OfficeBulletinRead save(OfficeBulletinRead officeBulletinRead);
	
	/**
	 * 判断是否存在
	 * @param officeBulletinRead
	 * @return
	 */
	public boolean isExist(OfficeBulletinRead officeBulletinRead);
	
	/**
	 * 根据通知公告id获取已读信息
	 * @param userId
	 * @param bulletinIds
	 * @return
	 */
	public Map<String, String> getReadMapByBulletinIds(String userId, String[] bulletinIds);
	
	/**
	 * 获取点击量信息
	 * @param bulletinIds
	 * @return
	 */
	public Map<String, String> getClickMapByBulletinIds(String[] bulletinIds);

	/**
	 * 根据ids数组删除office_bulletin_read
	 * @param ids
	 * @return
	 */
	public Integer delete(String[] ids);

	/**
	 * 更新office_bulletin_read
	 * @param officeBulletinRead
	 * @return
	 */
	public Integer update(OfficeBulletinRead officeBulletinRead);

	/**
	 * 根据id获取office_bulletin_read
	 * @param id
	 * @return
	 */
	public OfficeBulletinRead getOfficeBulletinReadById(String id);

	/**
	 * 根据ids数组查询office_bulletin_readmap
	 * @param ids
	 * @return
	 */
	public Map<String, OfficeBulletinRead> getOfficeBulletinReadMapByIds(String[] ids);

	/**
	 * 获取office_bulletin_read列表
	 * @return
	 */
	public List<OfficeBulletinRead> getOfficeBulletinReadList();

	/**
	 * 分页获取office_bulletin_read列表
	 * @param page
	 * @return
	 */
	public List<OfficeBulletinRead> getOfficeBulletinReadPage(Pagination page);

	/**
	 * 根据unitId获取office_bulletin_read列表
	 * @param unitId
	 * @return
	 */
	public List<OfficeBulletinRead> getOfficeBulletinReadByUnitIdList(String unitId);

	/**
	 * 根据unitId分页office_bulletin_read获取
	 * @param unitId
	 * @param page
	 * @return
	 */
	public List<OfficeBulletinRead> getOfficeBulletinReadByUnitIdPage(String unitId, Pagination page);
	
	/**
	 * 根据通知ID获取已读人员信息，key为通知ID
	 * @param bulletinIds
	 * @return
	 */
	public Map<String, List<OfficeBulletinRead>> getOfficeBulletinReadMap(String[] bulletinIds);
	
	public OfficeBulletinRead getOfficeBulletinReadByUnitIdAndBulletinId(String unitId,String bulletinId,String userId);
}