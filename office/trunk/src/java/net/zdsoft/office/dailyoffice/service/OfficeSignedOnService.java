package net.zdsoft.office.dailyoffice.service;

import java.util.List;
import java.util.Map;

import net.zdsoft.keel.util.Pagination;
import net.zdsoft.office.dailyoffice.entity.OfficeSignedOn;
/**
 * office_signed_on
 * @author 
 * 
 */
public interface OfficeSignedOnService{

	/**
	 * 新增office_signed_on
	 * @param officeSignedOn
	 * @return
	 */
	public OfficeSignedOn save(OfficeSignedOn officeSignedOn);

	/**
	 * 根据ids数组删除office_signed_on数据
	 * @param ids
	 * @return
	 */
	public Integer delete(String[] ids);

	/**
	 * 更新office_signed_on
	 * @param officeSignedOn
	 * @return
	 */
	public Integer update(OfficeSignedOn officeSignedOn);

	/**
	 * 根据id获取office_signed_on
	 * @param id
	 * @return
	 */
	public OfficeSignedOn getOfficeSignedOnById(String id);

	/**
	 * 根据ids数组查询office_signed_onmap
	 * @param ids
	 * @return
	 */
	public Map<String, OfficeSignedOn> getOfficeSignedOnMapByIds(String[] ids);

	/**
	 * 获取office_signed_on列表
	 * @return
	 */
	public List<OfficeSignedOn> getOfficeSignedOnList();

	/**
	 * 分页获取office_signed_on列表
	 * @param page
	 * @return
	 */
	public List<OfficeSignedOn> getOfficeSignedOnPage(Pagination page);

	/**
	 * 根据UnitId获取office_signed_on列表
	 * @param unitId
	 * @return
	 */
	public List<OfficeSignedOn> getOfficeSignedOnByUnitIdList(String unitId);

	/**
	 * 根据UnitId分页获取office_signed_on
	 * @param unitId
	 * @param page
	 * @return
	 */
	public List<OfficeSignedOn> getOfficeSignedOnByUnitIdPage(String unitId, Pagination page);
	
	/**
	 * 根据学年学期部门进行查询
	 * @param unitId
	 * @param page
	 * @return
	 */
	public List<OfficeSignedOn> getOfficeSignedOnByUnitIdDeptPage(String year,String semester,String time,String DeptId,String signed,String unitId, Pagination page);
	/**
	 * 根据学年学期部门进行查询个人信息
	 * @param unitId
	 * @param page
	 * @return
	 */
	public List<OfficeSignedOn> getOfficeSignedOnByUnitIdTimePage(String userId,String year,String semester,String time,String unitId,Pagination page);
	
	/**
	 * 根据学年学期部门进行查询次数
	 * @param unitId
	 * @param page
	 * @return
	 */
	public List<OfficeSignedOn> getOfficeSignedOnCountByUnitIdDeptPage(String year,String semester,String startTime,String endTime,String DeptId,String unitId, Pagination page);
	
	/**
	 * 根据学年学期部门进行查询个人次数
	 * @param unitId
	 * @param page
	 * @return
	 */
	public List<OfficeSignedOn> getOfficeSignedOnCountByUnitIdTimePage(String userId,String year,String semester,String startTime,String endTime,String unitId,Pagination page);
	/**
	 * 查询个人信息是否签到
	 * @param unitId
	 * @param page
	 * @return
	 */
	public List<OfficeSignedOn> getOfficeSignedOnByUnitIdTime(String userId,String year,String semester,String unitId,String time);
}