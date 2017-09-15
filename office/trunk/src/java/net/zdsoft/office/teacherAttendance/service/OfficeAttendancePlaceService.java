package net.zdsoft.office.teacherAttendance.service;


import java.util.*;

import net.zdsoft.keel.util.Pagination;
import net.zdsoft.office.teacherAttendance.entity.OfficeAttendancePlace;
/**
 * 办公地点
 * @author 
 * 
 */
public interface OfficeAttendancePlaceService{

	/**
	 * 新增办公地点
	 * @param officeAttendancePlace
	 * @return
	 */
	public OfficeAttendancePlace save(OfficeAttendancePlace officeAttendancePlace);

	/**
	 * 根据ids数组删除办公地点数据
	 * @param ids
	 * @return
	 */
	public Integer delete(String[] ids);
	
	/**
	 * 根据ids数组获取list
	 * @param ids
	 * @return
	 */
	public List<OfficeAttendancePlace> getListByIds(String[] ids);

	/**
	 * 更新办公地点
	 * @param officeAttendancePlace
	 * @return
	 */
	public Integer update(OfficeAttendancePlace officeAttendancePlace);

	/**
	 * 根据id获取办公地点
	 * @param id
	 * @return
	 */
	public OfficeAttendancePlace getOfficeAttendancePlaceById(String id);
	
	/**
	 * 根据name查询list数据，排除ignoreId
	 * @param unitId
	 * @param name
	 * @param ignoreId
	 * @return
	 */
	List<OfficeAttendancePlace> getListByName(String unitId, String name,String ignoreId);

	/**
	 * 根据unitid获取办公地点列表
	 * @param unitid
	 * @return
	 */
	public List<OfficeAttendancePlace> listOfficeAttendancePlaceByUnitId(String unitId);
	
	/**
	 * 根据unitid获取办公地点列表
	 * @param unitid
	 * @return
	 */
	public List<OfficeAttendancePlace> listOfficeAttendancePlaceIds(String[] ids);
	/**
	 * 根据unitid获取办公地点Map
	 * @param unitId
	 * @return
	 */
	public Map<String,OfficeAttendancePlace> getOfficeAttendancePlaceMapByUnitId(String unitId);
	
}