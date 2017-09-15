package net.zdsoft.office.studentLeave.service;
import java.util.*;
import net.zdsoft.keel.util.Pagination;
import net.zdsoft.office.studentLeave.entity.OfficeLeaveType;
/**
 * office_leave_type
 * @author 
 * 
 */
public interface OfficeLeaveTypeService{

	/**
	 * 新增office_leave_type
	 * @param officeLeaveType
	 * @return
	 */
	public OfficeLeaveType save(OfficeLeaveType officeLeaveType);

	/**
	 * 根据ids数组删除office_leave_type数据
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
	 * 根据UnitId获取office_leave_type列表
	 * @param unitId
	 * @return
	 */
	public List<OfficeLeaveType> getOfficeLeaveTypeByUnitIdList(String unitId, Integer state);

	/**
	 * 根据UnitId分页获取office_leave_type
	 * @param unitId
	 * @param page
	 * @return
	 */
	public List<OfficeLeaveType> getOfficeLeaveTypeByUnitIdPage(String unitId, Pagination page);
	/**
	 * 根据id获取类型名称
	 * @param leaveId
	 * @return
	 */
	public Map<String,OfficeLeaveType> getLeaveTypeNameByLeaveIds(String[] leaveIds);
}