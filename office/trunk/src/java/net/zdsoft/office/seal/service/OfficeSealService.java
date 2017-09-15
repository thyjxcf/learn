package net.zdsoft.office.seal.service;

import java.util.*;
import net.zdsoft.office.seal.entity.OfficeSeal;
import net.zdsoft.eis.base.common.entity.Dept;
import net.zdsoft.keel.util.Pagination;
import net.zdsoft.keelcnet.action.UploadFile;
/**
 * office_seal
 * @author 
 * 
 */
public interface OfficeSealService{

	/**
	 * 新增office_seal
	 * @param officeSeal
	 * @return
	 */
	public void save(OfficeSeal officeSeal,UploadFile file);
	public OfficeSeal save(OfficeSeal officeSeal);

	/**
	 * 根据ids数组删除office_seal数据
	 * @param ids
	 * @return
	 */
	public Integer delete(String[] ids);

	/**
	 * 更新office_seal
	 * @param officeSeal
	 * @return
	 */
	public void update(OfficeSeal officeSeal,UploadFile file);
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
	 * 根据UnitId获取office_seal列表
	 * @param unitId
	 * @return
	 */
	public List<OfficeSeal> getOfficeSealByUnitIdList(String unitId);

	/**
	 * 根据UnitId分页获取office_seal
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
	 * 根据部门列表
	 * @param years
	 * @param semesters
	 * @param userId
	 * @param unitId
	 * @return
	 */
	public List<OfficeSeal> getOfficeSealManageByOthers(List<Dept> depts,String unitId,String deptId,String sealType,Pagination page);
	
	/**
	 * 根据unitId和typeId取列表
	 * @param unitId
	 * @param typeIds
	 * @return
	 */
	public List<OfficeSeal> getOfficeSealByUnitIdTypeId(String unitId,String[] typeIds);
}