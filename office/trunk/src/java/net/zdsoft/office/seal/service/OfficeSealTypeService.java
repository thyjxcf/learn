package net.zdsoft.office.seal.service;

import java.util.*;
import net.zdsoft.office.seal.entity.OfficeSealType;
import net.zdsoft.keel.util.Pagination;
/**
 * office_seal_type
 * @author 
 * 
 */
public interface OfficeSealTypeService{

	/**
	 * 新增office_seal_type
	 * @param officeSealType
	 * @return
	 */
	public OfficeSealType save(OfficeSealType officeSealType);

	/**
	 * 根据ids数组删除office_seal_type数据
	 * @param ids
	 * @return
	 */
	public Integer delete(String[] ids);

	/**
	 * 更新office_seal_type
	 * @param officeSealType
	 * @return
	 */
	public Integer update(OfficeSealType officeSealType);

	/**
	 * 根据id获取office_seal_type
	 * @param id
	 * @return
	 */
	public OfficeSealType getOfficeSealTypeById(String id);

	/**
	 * 根据ids数组查询office_seal_typemap
	 * @param ids
	 * @return
	 */
	public Map<String, OfficeSealType> getOfficeSealTypeMapByIds(String[] ids);

	/**
	 * 获取office_seal_type列表
	 * @return
	 */
	public List<OfficeSealType> getOfficeSealTypeList();

	/**
	 * 分页获取office_seal_type列表
	 * @param page
	 * @return
	 */
	public List<OfficeSealType> getOfficeSealTypePage(Pagination page);

	/**
	 * 根据UnitId获取office_seal_type列表
	 * @param unitId
	 * @return
	 */
	public List<OfficeSealType> getOfficeSealTypeByUnitIdList(String unitId);

	/**
	 * 根据UnitId分页获取office_seal_type
	 * @param unitId
	 * @param page
	 * @return
	 */
	public List<OfficeSealType> getOfficeSealTypeByUnitIdPage(String unitId, Pagination page);
	
	/**
	 * 根据id组装成map
	 * @param checkid
	 * @return
	 */
	public Map<String,OfficeSealType> getOfficeSealTypeMap(String[] checkid);
	
	public Map<String,OfficeSealType> getOfficeSealTypeByUnitIdMap(String unitId);
}