package net.zdsoft.office.meeting.service;

import java.util.*;

import net.zdsoft.office.meeting.entity.OfficeDeptLeader;
import net.zdsoft.keel.util.Pagination;
/**
 * office_dept_leader
 * @author 
 * 
 */
public interface OfficeDeptLeaderService{

	/**
	 * 新增office_dept_leader
	 * @param officeDeptLeader
	 * @return
	 */
	public OfficeDeptLeader save(OfficeDeptLeader officeDeptLeader);

	/**
	 * 根据ids数组删除office_dept_leader数据
	 * @param ids
	 * @return
	 */
	public Integer delete(String[] ids);

	/**
	 * 更新office_dept_leader
	 * @param officeDeptLeader
	 * @return
	 */
	public Integer update(OfficeDeptLeader officeDeptLeader);

	/**
	 * 根据id获取office_dept_leader
	 * @param id
	 * @return
	 */
	public OfficeDeptLeader getOfficeDeptLeaderById(String id);

	/**
	 * 根据ids数组查询office_dept_leadermap
	 * @param ids
	 * @return
	 */
	public Map<String, OfficeDeptLeader> getOfficeDeptLeaderMapByIds(String[] ids);

	/**
	 * 获取office_dept_leader列表
	 * @return
	 */
	public List<OfficeDeptLeader> getOfficeDeptLeaderList();

	/**
	 * 分页获取office_dept_leader列表
	 * @param page
	 * @return
	 */
	public List<OfficeDeptLeader> getOfficeDeptLeaderPage(Pagination page);

	/**
	 * 根据UnitId获取office_dept_leader列表
	 * @param unitId
	 * @return
	 */
	public List<OfficeDeptLeader> getOfficeDeptLeaderByUnitIdList(String unitId);

	/**
	 * 根据UnitId分页获取office_dept_leader
	 * @param unitId
	 * @param page
	 * @return
	 */
	public List<OfficeDeptLeader> getOfficeDeptLeaderByUnitIdPage(String unitId, Pagination page);
	
	/**
	 * 获得map
	 */
	public Map<String, OfficeDeptLeader> getOfficeLeaderMap();

	public OfficeDeptLeader getOfficeDeptLeader(String unitId,
			String userId, String deptId);

	public Map<String, String> getLeaderMap(String[] deptIds);
}