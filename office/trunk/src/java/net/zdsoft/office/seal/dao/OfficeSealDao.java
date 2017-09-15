package net.zdsoft.office.seal.dao;

import java.util.*;

import net.zdsoft.office.seal.entity.OfficeSeal;
import net.zdsoft.keel.util.Pagination;
/**
 * office_seal
 * @author 
 * 
 */
public interface OfficeSealDao{

	/**
	 * 新增office_seal
	 * @param officeSeal
	 * @return
	 */
	public OfficeSeal save(OfficeSeal officeSeal);

	/**
	 * 根据ids数组删除office_seal
	 * @param ids
	 * @return
	 */
	public Integer delete(String[] ids);

	/**
	 * 更新office_seal
	 * @param officeSeal
	 * @return
	 */
	public Integer update(OfficeSeal officeSeal);

	/**
	 * 根据id获取office_seal
	 * @param id
	 * @return
	 */
	public OfficeSeal getOfficeSealById(String id);

	/**
	 * 根据ids数组查询office_sealmap
	 * @param ids
	 * @return
	 */
	public Map<String, OfficeSeal> getOfficeSealMapByIds(String[] ids);

	/**
	 * 获取office_seal列表
	 * @return
	 */
	public List<OfficeSeal> getOfficeSealList();

	/**
	 * 分页获取office_seal列表
	 * @param page
	 * @return
	 */
	public List<OfficeSeal> getOfficeSealPage(Pagination page);

	/**
	 * 根据unitId获取office_seal列表
	 * @param unitId
	 * @return
	 */
	public List<OfficeSeal> getOfficeSealByUnitIdList(String unitId);

	/**
	 * 根据unitId分页office_seal获取
	 * @param unitId
	 * @param page
	 * @return
	 */
	public List<OfficeSeal> getOfficeSealByUnitIdPage(String unitId, Pagination page);
	
	/**
	 * 根据学年学期查询列表
	 * @param years
	 * @param semesters
	 * @param userId
	 * @param unitId
	 * @return
	 */
	public List<OfficeSeal> getOfficeSealByOthers(String years,String semesters,String userId,String unitId,Pagination page);
	/**
	 * 根据部门查询列表
	 * @param years
	 * @param semesters
	 * @param userId
	 * @param unitId
	 * @return
	 */
	public List<OfficeSeal> getOfficeSealManageByOthers(String unitId,String deptId,String sealType,Pagination page);
	
	/**
	 * 根据unitId和typeId取列表
	 * @param unitId
	 * @param typeIds
	 * @return
	 */
	public List<OfficeSeal> getOfficeSealByUnitIdTypeId(String unitId,String[] typeIds);
}