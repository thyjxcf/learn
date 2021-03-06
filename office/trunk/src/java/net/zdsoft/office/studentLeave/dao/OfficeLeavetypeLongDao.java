package net.zdsoft.office.studentLeave.dao;

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
public interface OfficeLeavetypeLongDao{
	public void deleteByLeaveId(String leaveId);
	/**
	 * 新增office_leavetype_long
	 * @param officeLeavetypeLong
	 * @return
	 */
	public OfficeLeavetypeLong save(OfficeLeavetypeLong officeLeavetypeLong);

	/**
	 * 根据ids数组删除office_leavetype_long
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
	public OfficeLeavetypeLong findByLeaveId(
			String leaveId);

	public List<OfficeLeavetypeLong> findByLeaveIds(String[] leaveIds);
	public List<OfficeLeavetypeLong> findByLeaveTimeAndLeaveIds(Date date,
			String[] leaveIds);
	public Map<String, OfficeLeavetypeLong> getMapByLeaveIds(String[] leaveIds);
	
	public List<OfficeLeavetypeLong> isExistTime(String longId, Date startTime,
			Date endTime);

}