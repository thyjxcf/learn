package net.zdsoft.office.repaire.service;

import java.util.List;
import java.util.Map;

import net.zdsoft.keel.util.Pagination;
import net.zdsoft.office.repaire.entity.OfficeTypeAuth;
/**
 * office_type_auth
 * @author 
 * 
 */
public interface OfficeTypeAuthService{

	/**
	 * 新增office_type_auth
	 * @param officeTypeAuth
	 * @return
	 */
	public OfficeTypeAuth save(OfficeTypeAuth officeTypeAuth);

	/**
	 * 根据ids数组删除office_type_auth数据
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
	 * 得到已经查到的用户信息
	 * @param unitId TODO
	 * @param repaireState
	 * @param page
	 * @return
	 */
	public List<OfficeTypeAuth> getOfficeTypeAuthUserPage(String unitId,
			String repaireState, Pagination page);

	public List<OfficeTypeAuth> getOfficeTypeAuthList(String unitId, String repaireState, String typeId);
	
	public List<OfficeTypeAuth> getOfficeTypeAuthList(String repaireState,
			String userId);
	/**
	 * 权限保存
	 * @param userId
	 * @param types
	 * @param areaIds
	 */
	public void saveAuth(String userId, String[] types, String[] areaIds);

	public void deleteAuthByUser(String[] userIds);
	
}