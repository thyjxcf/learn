package net.zdsoft.office.studentLeave.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import net.zdsoft.keel.util.Pagination;
import net.zdsoft.office.studentLeave.entity.OfficeLeavetypeTemporary;
/**
 * office_leavetype_temporary
 * @author 
 * 
 */
public interface OfficeLeavetypeTemporaryService{
	public void deleteByLeaveId(String leaveId);
	/**
	 * 新增office_leavetype_temporary
	 * @param officeLeavetypeTemporary
	 * @return
	 */
	public OfficeLeavetypeTemporary save(OfficeLeavetypeTemporary officeLeavetypeTemporary);

	/**
	 * 根据ids数组删除office_leavetype_temporary数据
	 * @param ids
	 * @return
	 */
	public Integer delete(String[] ids);

	/**
	 * 更新office_leavetype_temporary
	 * @param officeLeavetypeTemporary
	 * @return
	 */
	public Integer update(OfficeLeavetypeTemporary officeLeavetypeTemporary);

	/**
	 * 根据id获取office_leavetype_temporary
	 * @param id
	 * @return
	 */
	public OfficeLeavetypeTemporary getOfficeLeavetypeTemporaryById(String id);

	/**
	 * 根据ids数组查询office_leavetype_temporarymap
	 * @param ids
	 * @return
	 */
	public Map<String, OfficeLeavetypeTemporary> getOfficeLeavetypeTemporaryMapByIds(String[] ids);

	/**
	 * 获取office_leavetype_temporary列表
	 * @return
	 */
	public List<OfficeLeavetypeTemporary> getOfficeLeavetypeTemporaryList();

	/**
	 * 分页获取office_leavetype_temporary列表
	 * @param page
	 * @return
	 */
	public List<OfficeLeavetypeTemporary> getOfficeLeavetypeTemporaryPage(Pagination page);
	

	public OfficeLeavetypeTemporary findByLeaveId(String leaveId);

	public void insertTemporary(OfficeLeavetypeTemporary leaveTemporary);

	public void updateTemporary(OfficeLeavetypeTemporary leaveTemporary);

	public void startFlow(OfficeLeavetypeTemporary leaveTemporary, String userId);
	/**
	 * 获得学生的请假列表
	 * @param unitId
	 * @param stuId
	 * @param page
	 * @return
	 */
	public List<OfficeLeavetypeTemporary> getLeavelist(String unitId, String stuId, Pagination page);
	public List<OfficeLeavetypeTemporary> getAuditList(String searchType,
			String userId, Pagination page);
	public List<OfficeLeavetypeTemporary> findByLeaveTimeAndLeaveIds(Date date,
			String[] leaveIds);
	public Map<String, OfficeLeavetypeTemporary> getMapByLeaveIds(String[] leaveIds);

	
}