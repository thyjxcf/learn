package net.zdsoft.office.meeting.service;

import java.util.List;
import java.util.Map;

import net.zdsoft.keel.util.Pagination;
import net.zdsoft.office.meeting.entity.OfficeExecutiveFixedDept;
/**
 * office_executive_fixed_dept
 * @author 
 * 
 */
public interface OfficeExecutiveFixedDeptService{

	/**
	 * 新增office_executive_fixed_dept
	 * @param officeExecutiveFixedDept
	 * @return
	 */
	public OfficeExecutiveFixedDept save(OfficeExecutiveFixedDept officeExecutiveFixedDept);

	/**
	 * 批量保存固定列席科室
	 * @param unitId
	 * @param deptIds
	 */
	public void batchUpdate(String unitId, String deptIds);
	
	/**
	 * 根据ids数组删除office_executive_fixed_dept数据
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
	 * 根据UnitId获取office_executive_fixed_dept列表
	 * @param unitId
	 * @return
	 */
	public List<OfficeExecutiveFixedDept> getOfficeExecutiveFixedDeptByUnitIdList(String unitId);

	/**
	 * 根据UnitId分页获取office_executive_fixed_dept
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