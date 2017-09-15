package net.zdsoft.office.bulletin.service;

import java.util.List;
import java.util.Map;

import net.zdsoft.keel.util.Pagination;
import net.zdsoft.office.bulletin.entity.OfficeBulletinRead;
/**
 * office_bulletin_read
 * @author 
 * 
 */
public interface OfficeBulletinReadService{

	/**
	 * 新增office_bulletin_read
	 * @param officeBulletinRead
	 * @return
	 */
	public void save(OfficeBulletinRead officeBulletinRead);

	/**
	 * 根据ids数组删除office_bulletin_read数据
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
	 * 根据UnitId获取office_bulletin_read列表
	 * @param unitId
	 * @return
	 */
	public List<OfficeBulletinRead> getOfficeBulletinReadByUnitIdList(String unitId);

	/**
	 * 根据UnitId分页获取office_bulletin_read
	 * @param unitId
	 * @param page
	 * @return
	 */
	public List<OfficeBulletinRead> getOfficeBulletinReadByUnitIdPage(String unitId, Pagination page);
	
	public OfficeBulletinRead getOfficeBulletinReadByUnitIdAndBulletinId(String unitId,String bulletinId,String userId);
}