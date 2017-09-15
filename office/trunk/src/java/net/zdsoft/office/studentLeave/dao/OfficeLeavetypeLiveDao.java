package net.zdsoft.office.studentLeave.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import net.zdsoft.keel.util.Pagination;
import net.zdsoft.office.studentLeave.entity.OfficeLeavetypeLive;
/**
 * office_leavetype_live
 * @author 
 * 
 */
public interface OfficeLeavetypeLiveDao{
	public void deleteByLeaveId(String leaveId);
	/**
	 * 新增office_leavetype_live
	 * @param officeLeavetypeLive
	 * @return
	 */
	public OfficeLeavetypeLive save(OfficeLeavetypeLive officeLeavetypeLive);

	/**
	 * 根据ids数组删除office_leavetype_live
	 * @param ids
	 * @return
	 */
	public Integer delete(String[] ids);

	/**
	 * 更新office_leavetype_live
	 * @param officeLeavetypeLive
	 * @return
	 */
	public Integer update(OfficeLeavetypeLive officeLeavetypeLive);

	/**
	 * 根据id获取office_leavetype_live
	 * @param id
	 * @return
	 */
	public OfficeLeavetypeLive getOfficeLeavetypeLiveById(String id);

	/**
	 * 根据ids数组查询office_leavetype_livemap
	 * @param ids
	 * @return
	 */
	public Map<String, OfficeLeavetypeLive> getOfficeLeavetypeLiveMapByIds(String[] ids);

	/**
	 * 获取office_leavetype_live列表
	 * @return
	 */
	public List<OfficeLeavetypeLive> getOfficeLeavetypeLiveList();

	/**
	 * 分页获取office_leavetype_live列表
	 * @param page
	 * @return
	 */
	public List<OfficeLeavetypeLive> getOfficeLeavetypeLivePage(Pagination page);
	
	public OfficeLeavetypeLive findByLeaveId(
			String leaveId);

	public List<OfficeLeavetypeLive> findByLeaveIds(String[] leaveIds);
	public List<OfficeLeavetypeLive> findByLeaveTimeAndAppleyTypeAdnLeaveIds(
			Date date, String applyType, String[] leaveIds);
	public Map<String, OfficeLeavetypeLive> getMapByLeaveIds(String[] leaveIds);
	
	public List<OfficeLeavetypeLive> isExistTime(String id, Date startTime,
			Date endTime);

}