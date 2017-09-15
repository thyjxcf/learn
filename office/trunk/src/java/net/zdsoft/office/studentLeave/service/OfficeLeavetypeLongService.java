package net.zdsoft.office.studentLeave.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import net.zdsoft.keel.util.Pagination;
import net.zdsoft.office.studentLeave.entity.OfficeLeavetypeLong;
/**
 * office_leavetype_long
 * @author 
 * 
 */
public interface OfficeLeavetypeLongService{
	public void deleteByLeaveId(String leaveId);
	/**
	 * 新增office_leavetype_long
	 * @param officeLeavetypeLong
	 * @return
	 */
	public OfficeLeavetypeLong save(OfficeLeavetypeLong officeLeavetypeLong);

	/**
	 * 根据ids数组删除office_leavetype_long数据
	 * @param ids
	 * @return
	 */
	public Integer delete(String[] ids);

	/**
	 * 更新office_leavetype_long
	 * @param officeLeavetypeLong
	 * @return
	 */
	public Integer update(OfficeLeavetypeLong officeLeavetypeLong);

	/**
	 * 根据id获取office_leavetype_long
	 * @param id
	 * @return
	 */
	public OfficeLeavetypeLong getOfficeLeavetypeLongById(String id);

	/**
	 * 根据ids数组查询office_leavetype_longmap
	 * @param ids
	 * @return
	 */
	public Map<String, OfficeLeavetypeLong> getOfficeLeavetypeLongMapByIds(String[] ids);

	/**
	 * 获取office_leavetype_long列表
	 * @return
	 */
	public List<OfficeLeavetypeLong> getOfficeLeavetypeLongList();

	/**
	 * 分页获取office_leavetype_long列表
	 * @param page
	 * @return
	 */
	public List<OfficeLeavetypeLong> getOfficeLeavetypeLongPage(Pagination page);
	
	public OfficeLeavetypeLong findByLeaveId(String leaveId);

	public void insertLong(OfficeLeavetypeLong leaveLong);

	public void updateLong(OfficeLeavetypeLong leaveLong);

	public void startFlow(OfficeLeavetypeLong leaveLong, String userId);
	/**
	 * 获得学生的请假列表
	 * @param unitId
	 * @param stuId
	 * @param page
	 * @return
	 */
	public List<OfficeLeavetypeLong> getLeavelist(String unitId, String stuId, Pagination page);
	
	public List<OfficeLeavetypeLong> getAuditList(String searchType,
			String userId, Pagination page);
	public List<OfficeLeavetypeLong> findByLeaveTimeAndLeaveIds(Date date,
			String[] leaveIds);
	public Map<String, OfficeLeavetypeLong> getMapByLeaveIds(String[] leaveIds);

}