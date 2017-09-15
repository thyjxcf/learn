package net.zdsoft.office.repaire.dao;

import java.util.*;

import net.zdsoft.office.repaire.entity.OfficeTeachAreaAuth;
import net.zdsoft.keel.util.Pagination;
/**
 * office_teach_area_auth
 * @author 
 * 
 */
public interface OfficeTeachAreaAuthDao{

	/**
	 * 新增office_teach_area_auth
	 * @param officeTeachAreaAuth
	 * @return
	 */
	public OfficeTeachAreaAuth save(OfficeTeachAreaAuth officeTeachAreaAuth);

	/**
	 * 根据ids数组删除office_teach_area_auth
	 * @param ids
	 * @return
	 */
	public Integer delete(String[] ids);

	/**
	 * 更新office_teach_area_auth
	 * @param officeTeachAreaAuth
	 * @return
	 */
	public Integer update(OfficeTeachAreaAuth officeTeachAreaAuth);

	/**
	 * 根据id获取office_teach_area_auth
	 * @param id
	 * @return
	 */
	public OfficeTeachAreaAuth getOfficeTeachAreaAuthById(String id);

	/**
	 * 根据ids数组查询office_teach_area_authmap
	 * @param ids
	 * @return
	 */
	public Map<String, OfficeTeachAreaAuth> getOfficeTeachAreaAuthMapByIds(String[] ids);

	/**
	 * 获取office_teach_area_auth列表
	 * @return
	 */
	public List<OfficeTeachAreaAuth> getOfficeTeachAreaAuthList();

	/**
	 * 分页获取office_teach_area_auth列表
	 * @param page
	 * @return
	 */
	public List<OfficeTeachAreaAuth> getOfficeTeachAreaAuthPage(Pagination page);

	public List<OfficeTeachAreaAuth> getOfficeTeachAreaAuthList(String state,
			String userId);

	public void deleteByUserIds(String[] userIds);

	public void saveBach(List<OfficeTeachAreaAuth> arealist);
}