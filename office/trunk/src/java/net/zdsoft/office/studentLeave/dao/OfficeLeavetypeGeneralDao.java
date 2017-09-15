package net.zdsoft.office.studentLeave.dao;

import java.util.*;

import net.zdsoft.office.studentLeave.entity.OfficeLeavetypeGeneral;
import net.zdsoft.keel.util.Pagination;
/**
 * office_leavetype_general
 * @author 
 * 
 */
public interface OfficeLeavetypeGeneralDao{

	/**
	 * 新增office_leavetype_general
	 * @param officeLeavetypeGeneral
	 * @return
	 */
	public OfficeLeavetypeGeneral save(OfficeLeavetypeGeneral officeLeavetypeGeneral);

	/**
	 * 根据ids数组删除office_leavetype_general
	 * @param ids
	 * @return
	 */
	public Integer delete(String[] ids);

	/**
	 * 更新office_leavetype_general
	 * @param officeLeavetypeGeneral
	 * @return
	 */
	public Integer update(OfficeLeavetypeGeneral officeLeavetypeGeneral);

	/**
	 * 根据id获取office_leavetype_general
	 * @param id
	 * @return
	 */
	public OfficeLeavetypeGeneral getOfficeLeavetypeGeneralById(String id);

	/**
	 * 根据ids数组查询office_leavetype_generalmap
	 * @param ids
	 * @return
	 */
	public Map<String, OfficeLeavetypeGeneral> getOfficeLeavetypeGeneralMapByIds(String[] ids);
	public Map<String, OfficeLeavetypeGeneral> getMapByLeaveIds(String[] leaveIds);
	/**
	 * 获取office_leavetype_general列表
	 * @return
	 */
	public List<OfficeLeavetypeGeneral> getOfficeLeavetypeGeneralList();

	/**
	 * 分页获取office_leavetype_general列表
	 * @param page
	 * @return
	 */
	public List<OfficeLeavetypeGeneral> getOfficeLeavetypeGeneralPage(Pagination page);

	public OfficeLeavetypeGeneral getOfficeLeavetypeGeneralByLeaveId(
			String leaveId);

	public List<OfficeLeavetypeGeneral> findByLeaveIds(String[] leaveIds);

	public void deleteByLeaveId(String leaveId);

	public List<OfficeLeavetypeGeneral> findByLeaveTimeAndLeaveIds(Date date,
			String[] leaveIds);

	public List<OfficeLeavetypeGeneral> isExistTime(String id, Date startTime,
			Date endTime);

}