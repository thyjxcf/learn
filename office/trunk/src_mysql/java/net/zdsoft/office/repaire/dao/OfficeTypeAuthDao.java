package net.zdsoft.office.repaire.dao;

import java.util.*;

import net.zdsoft.office.repaire.entity.OfficeTypeAuth;
import net.zdsoft.keel.util.Pagination;
/**
 * office_type_auth
 * @author 
 * 
 */
public interface OfficeTypeAuthDao{

	/**
	 * 新增office_type_auth
	 * @param officeTypeAuth
	 * @return
	 */
	public OfficeTypeAuth save(OfficeTypeAuth officeTypeAuth);

	/**
	 * 根据ids数组删除office_type_auth
	 * @param ids
	 * @return
	 */
	public Integer delete(String[] ids);

	/**
	 * 更新office_type_auth
	 * @param officeTypeAuth
	 * @return
	 */
	public Integer update(OfficeTypeAuth officeTypeAuth);

	/**
	 * 根据id获取office_type_auth
	 * @param id
	 * @return
	 */
	public OfficeTypeAuth getOfficeTypeAuthById(String id);

	/**
	 * 根据ids数组查询office_type_authmap
	 * @param ids
	 * @return
	 */
	public Map<String, OfficeTypeAuth> getOfficeTypeAuthMapByIds(String[] ids);

	/**
	 * 获取office_type_auth列表
	 * @param unitId TODO
	 * @return
	 */
	public List<OfficeTypeAuth> getOfficeTypeAuthList(String unitId);

	/**
	 * 分页获取office_type_auth列表
	 * @param unitId TODO
	 * @param page
	 * @return
	 */
	public List<OfficeTypeAuth> getOfficeTypeAuthPage(String unitId, Pagination page);
	/**
	 * @param unitId TODO
	 * @param state 1
	 * @param page
	 * @return
	 */
	public List<OfficeTypeAuth> getUserPage(String unitId, String state, Pagination page);

	public List<OfficeTypeAuth> getOfficeTypeAuthList(String state,
			String userId);

	public void deleteByUserIds(String[] strings);

	public void saveBach(List<OfficeTypeAuth> typelist);
	
	public List<OfficeTypeAuth> getOfficeTypeAuthList(String unitId, String state, String typeId);
}