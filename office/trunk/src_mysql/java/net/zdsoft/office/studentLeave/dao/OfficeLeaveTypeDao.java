package net.zdsoft.office.studentLeave.dao;
import java.util.List;
import java.util.Map;

import net.zdsoft.keel.util.Pagination;
import net.zdsoft.office.studentLeave.entity.OfficeLeaveType;
/**
 * office_leave_type
 * @author 
 * 
 */
public interface OfficeLeaveTypeDao{

	/**
	 * 新增office_leave_type
	 * @param officeLeaveType
	 * @return
	 */
	public OfficeLeaveType save(OfficeLeaveType officeLeaveType);

	/**
	 * 根据ids数组删除office_leave_type
	 * @param ids
	 * @return
	 */
	public Integer delete(String[] ids);

	/**
	 * 更新office_leave_type
	 * @param officeLeaveType
	 * @return
	 */
	public Integer update(OfficeLeaveType officeLeaveType);

	/**
	 * 根据id获取office_leave_type
	 * @param id
	 * @return
	 */
	public OfficeLeaveType getOfficeLeaveTypeById(String id);

	/**
	 * 根据ids数组查询office_leave_typemap
	 * @param ids
	 * @return
	 */
	public Map<String, OfficeLeaveType> getOfficeLeaveTypeMapByIds(String[] ids);

	/**
	 * 获取office_leave_type列表
	 * @return
	 */
	public List<OfficeLeaveType> getOfficeLeaveTypeList();

	/**
	 * 分页获取office_leave_type列表
	 * @param page
	 * @return
	 */
	public List<OfficeLeaveType> getOfficeLeaveTypePage(Pagination page);

	/**
	 * 根据unitId获取office_leave_type列表
	 * @param unitId
	 * @return
	 */
	public List<OfficeLeaveType> getOfficeLeaveTypeByUnitIdList(String unitId, Integer state);

	/**
	 * 根据unitId分页office_leave_type获取
	 * @param unitId
	 * @param page
	 * @return
	 */
	public List<OfficeLeaveType> getOfficeLeaveTypeByUnitIdPage(String unitId, Pagination page);
	
	/**
	 * 根据id获取名称
	 * @param id
	 * @return
	 */
	public Map<String,OfficeLeaveType> getleaveTypeNameByLeaveIds(String[] ids);
}