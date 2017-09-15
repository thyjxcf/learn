package net.zdsoft.office.bulletin.service;

import java.util.List;
import java.util.Map;

import net.zdsoft.keel.util.Pagination;
import net.zdsoft.office.bulletin.entity.OfficeBulletinSet;
/**
 * office_bulletin_set
 * @author 
 * 
 */
public interface OfficeBulletinSetService{

	/**
	 * 新增office_bulletin_set
	 * @param officeBulletinSet
	 * @return
	 */
	public OfficeBulletinSet save(OfficeBulletinSet officeBulletinSet);

	/**
	 * 根据ids数组删除office_bulletin_set数据
	 * @param ids
	 * @return
	 */
	public Integer delete(String[] ids);

	/**
	 * 更新office_bulletin_set
	 * @param officeBulletinSet
	 * @return
	 */
	public Integer update(OfficeBulletinSet officeBulletinSet);

	/**
	 * 根据id获取office_bulletin_set
	 * @param id
	 * @return
	 */
	public OfficeBulletinSet getOfficeBulletinSetById(String id);

	/**
	 * 根据ids数组查询office_bulletin_setmap
	 * @param ids
	 * @return
	 */
	public Map<String, OfficeBulletinSet> getOfficeBulletinSetMapByIds(String[] ids);

	/**
	 * 获取office_bulletin_set列表
	 * @return
	 */
	public List<OfficeBulletinSet> getOfficeBulletinSetList();

	/**
	 * 分页获取office_bulletin_set列表
	 * @param page
	 * @return
	 */
	public List<OfficeBulletinSet> getOfficeBulletinSetPage(Pagination page);

	/**
	 * 根据UnitId获取office_bulletin_set列表
	 * @param unitId
	 * @return
	 */
	public List<OfficeBulletinSet> getOfficeBulletinSetByUnitIdList(String unitId,String roleCode);

	/**
	 * 根据UnitId分页获取office_bulletin_set
	 * @param unitId
	 * @param page
	 * @return
	 */
	public List<OfficeBulletinSet> getOfficeBulletinSetByUnitIdPage(String unitId, Pagination page);
	
	public List<OfficeBulletinSet> getOfficeBulletinSetByUnitIdUserIdList(String unitId,String userId,String roleCode);
}