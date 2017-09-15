package net.zdsoft.office.repaire.dao;

import java.util.List;
import java.util.Map;

import net.zdsoft.keel.util.Pagination;
import net.zdsoft.office.repaire.entity.OfficeRepaireType;
/**
 * office_repaire_type
 * @author 
 * 
 */
public interface OfficeRepaireTypeDao{

	/**
	 * 新增office_repaire_type
	 * @param officeRepaireType
	 * @return
	 */
	public OfficeRepaireType save(OfficeRepaireType officeRepaireType);

	/**
	 * 删除office_repaire_type
	 * @param id
	 * @return
	 */
	public Integer delete(String id);

	/**
	 * 更新office_repaire_type
	 * @param officeRepaireType
	 * @return
	 */
	public Integer update(OfficeRepaireType officeRepaireType);

	/**
	 * 根据id获取office_repaire_type
	 * @param id
	 * @return
	 */
	public OfficeRepaireType getOfficeRepaireTypeById(String id);

	/**
	 * 根据ids数组查询office_repaire_typemap
	 * @param ids
	 * @return
	 */
	public Map<String, OfficeRepaireType> getOfficeRepaireTypeMapByIds(String[] ids);
	
	/**
	 * 获取map
	 * @param unitId
	 * @return
	 */
	public Map<String, String> getOfficeRepaireTypeMapByUnitId(String unitId);

	/**
	 * 获取office_repaire_type列表
	 * @return
	 */
	public List<OfficeRepaireType> getOfficeRepaireTypeList();

	/**
	 * 分页获取office_repaire_type列表
	 * @param page
	 * @return
	 */

	public List<OfficeRepaireType> getOfficeRepaireTypePage(Pagination page);
	/**
	 * 根据unitId获取office_repaire_type列表
	 * @param unitId
	 * @return
	 */
	public List<OfficeRepaireType> getOfficeRepaireTypeByUnitIdList(String unitId, String thisId);

	/**
	 * 根据unitId分页office_repaire_type获取
	 * @param unitId
	 * @param page
	 * @return
	 */
	public List<OfficeRepaireType> getOfficeRepaireTypeByUnitIdPage(String unitId, Pagination page);

	public List<OfficeRepaireType> getOfficeRepaireTypeByUnitIdList(
			String unitId);
}