package net.zdsoft.office.meeting.service;

import java.util.List;
import java.util.Map;

import net.zdsoft.keel.util.Pagination;
import net.zdsoft.office.meeting.entity.OfficeExecutiveMeetAttend;
/**
 * office_executive_meet_attend
 * @author 
 * 
 */
public interface OfficeExecutiveMeetAttendService{

	/**
	 * 新增office_executive_meet_attend
	 * @param officeExecutiveMeetAttend
	 * @return
	 */
	public OfficeExecutiveMeetAttend save(OfficeExecutiveMeetAttend officeExecutiveMeetAttend);

	/**
	 * 批量保存会议参加表
	 * @param list
	 */
	public void batchSave(List<OfficeExecutiveMeetAttend> list);
	
	/**
	 * 根据会议id删除相关参会信息
	 * @param meetId
	 */
	public void deleteByMeetId(String meetId);
	
	/**
	 * 根据ids数组删除office_executive_meet_attend数据
	 * @param ids
	 * @return
	 */
	public Integer delete(String[] ids);

	/**
	 * 更新office_executive_meet_attend
	 * @param officeExecutiveMeetAttend
	 * @return
	 */
	public Integer update(OfficeExecutiveMeetAttend officeExecutiveMeetAttend);

	/**
	 * 根据id获取office_executive_meet_attend
	 * @param id
	 * @return
	 */
	public OfficeExecutiveMeetAttend getOfficeExecutiveMeetAttendById(String id);

	/**
	 * 根据ids数组查询office_executive_meet_attendmap
	 * @param ids
	 * @return
	 */
	public Map<String, OfficeExecutiveMeetAttend> getOfficeExecutiveMeetAttendMapByIds(String[] ids);

	/**
	 * 分页获取office_executive_meet_attend列表
	 * @param page
	 * @return
	 */
	public List<OfficeExecutiveMeetAttend> getOfficeExecutiveMeetAttendPage(Pagination page);
	
	/**
	 * 获取需要参加的会议ids
	 * @param unitId
	 * @param objIds
	 * @return
	 */
	public List<String> getMeetIds(String unitId, String[] objIds);
	
	/**
	 * 获取每个会议对应的参会科室
	 * @param unitId
	 * @param meetIds
	 * @return
	 */
	public Map<String, String> getAttendDeptMap(String unitId, String[] meetIds);
}