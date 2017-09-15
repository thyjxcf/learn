package net.zdsoft.office.bulletin.service;

import java.util.List;
import java.util.Map;

import net.zdsoft.keel.util.Pagination;
import net.zdsoft.office.bulletin.entity.OfficeBulletinType;
/**
 * office_bulletin_type
 * @author 
 * 
 */
public interface OfficeBulletinTypeService{

	/**
	 * 新增office_bulletin_type
	 * @param officeBulletinType
	 * @return
	 */
	public OfficeBulletinType save(OfficeBulletinType officeBulletinType);

	/**
	 * 根据ids数组删除office_bulletin_type数据
	 * @param ids
	 * @return
	 */
	public Integer delete(String[] ids);

	/**
	 * 更新office_bulletin_type
	 * @param officeBulletinType
	 * @return
	 */
	public Integer update(OfficeBulletinType officeBulletinType);

	/**
	 * 根据id获取office_bulletin_type
	 * @param id
	 * @return
	 */
	public OfficeBulletinType getOfficeBulletinTypeById(String id);
	
	/**
	 * 根据id获取office_bulletin_type
	 * @param id
	 * @return
	 */
	public OfficeBulletinType getOfficeBulletinTypeByType(String type);

	/**
	 * 根据ids数组查询office_bulletin_typemap
	 * @param ids
	 * @return
	 */
	public Map<String, OfficeBulletinType> getOfficeBulletinTypeMapByIds(String[] ids);

	/**
	 * 获取office_bulletin_type列表
	 * @return
	 */
	public List<OfficeBulletinType> getOfficeBulletinTypeList(Integer showNumber);

	/**
	 * 分页获取office_bulletin_type列表
	 * @param page
	 * @return
	 */
	public List<OfficeBulletinType> getOfficeBulletinTypePage(Pagination page);
}