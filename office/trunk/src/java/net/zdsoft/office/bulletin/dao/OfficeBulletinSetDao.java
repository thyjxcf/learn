package net.zdsoft.office.bulletin.dao;

import java.util.*;
import net.zdsoft.office.bulletin.entity.OfficeBulletinSet;
import net.zdsoft.keel.util.Pagination;
/**
 * office_bulletin_set
 * @author 
 * 
 */
public interface OfficeBulletinSetDao{

	/**
	 * 新增office_bulletin_set
	 * @param officeBulletinSet
	 * @return
	 */
	public OfficeBulletinSet save(OfficeBulletinSet officeBulletinSet);

	/**
	 * 根据ids数组删除office_bulletin_set
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
	 * 根据unitId获取office_bulletin_set列表
	 * @param unitId
	 * @return
	 */
	public List<OfficeBulletinSet> getOfficeBulletinSetByUnitIdList(String unitId,String roleCode);

	/**
	 * 根据unitId分页office_bulletin_set获取
	 * @param unitId
	 * @param page
	 * @return
	 */
	public List<OfficeBulletinSet> getOfficeBulletinSetByUnitIdPage(String unitId, Pagination page);
	
	public List<OfficeBulletinSet> getOfficeBulletinSetByUnitIdUserIdList(String unitId,String userId,String roleCode);
}