package net.zdsoft.office.meeting.dao;

import java.util.List;
import java.util.Map;

import net.zdsoft.keel.util.Pagination;
import net.zdsoft.office.meeting.entity.OfficeExecutiveFixedDept;
/**
 * office_executive_fixed_dept
 * @author 
 * 
 */
public interface OfficeExecutiveFixedDeptDao{

	/**
	 * 新增office_executive_fixed_dept
	 * @param officeExecutiveFixedDept
	 * @return
	 */
	public OfficeExecutiveFixedDept save(OfficeExecutiveFixedDept officeExecutiveFixedDept);

	public void deleteByUnitId(String unitId);
	public void batchSave(List<OfficeExecutiveFixedDept> list);
	
	/**
	 * 根据ids数组删除office_executive_fixed_dept
	 * @param ids
	 * @return
	 */
	public Integer delete(String[] ids);

	/**
	 * 更新office_executive_fixed_dept
	 * @param officeExecutiveFixedDept
	 * @return
	 */
	public Integer update(OfficeExecutiveFixedDept officeExecutiveFixedDept);

	/**
	 * 根据id获取office_executive_fixed_dept
	 * @param id
	 * @return
	 */
	public OfficeExecutiveFixedDept getOfficeExecutiveFixedDeptById(String id);

	/**
	 * 根据ids数组查询office_executive_fixed_deptmap
	 * @param ids
	 * @return
	 */
	public Map<String, OfficeExecutiveFixedDept> getOfficeExecutiveFixedDeptMapByIds(String[] ids);

	/**
	 * 获取office_executive_fixed_dept列表
	 * @return
	 */
	public List<OfficeExecutiveFixedDept> getOfficeExecutiveFixedDeptList();

	/**
	 * 分页获取office_executive_fixed_dept列表
	 * @param page
	 * @return
	 */
	public List<OfficeExecutiveFixedDept> getOfficeExecutiveFixedDeptPage(Pagination page);

	/**
	 * 根据unitId获取office_executive_fixed_dept列表
	 * @param unitId
	 * @return
	 */
	public List<OfficeExecutiveFixedDept> getOfficeExecutiveFixedDeptByUnitIdList(String unitId);

	/**
	 * 根据unitId分页office_executive_fixed_dept获取
	 * @param unitId
	 * @param page
	 * @return
	 */
	public List<OfficeExecutiveFixedDept> getOfficeExecutiveFixedDeptByUnitIdPage(String unitId, Pagination page);
	
	/**
	 * 判断是否固定参会科室
	 * @param deptId
	 * @return
	 */
	public boolean isFixedDept(String deptId);
}