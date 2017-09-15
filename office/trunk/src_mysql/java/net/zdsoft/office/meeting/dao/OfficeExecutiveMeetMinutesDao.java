package net.zdsoft.office.meeting.dao;

import java.util.List;
import java.util.Map;

import net.zdsoft.keel.util.Pagination;
import net.zdsoft.office.meeting.entity.OfficeExecutiveMeetMinutes;
/**
 * office_executive_meet_minutes
 * @author 
 * 
 */
public interface OfficeExecutiveMeetMinutesDao{

	/**
	 * 新增office_executive_meet_minutes
	 * @param officeExecutiveMeetMinutes
	 * @return
	 */
	public OfficeExecutiveMeetMinutes save(OfficeExecutiveMeetMinutes officeExecutiveMeetMinutes);

	/**
	 * 根据ids数组删除office_executive_meet_minutes
	 * @param ids
	 * @return
	 */
	public Integer delete(String[] ids);

	/**
	 * 更新office_executive_meet_minutes
	 * @param officeExecutiveMeetMinutes
	 * @return
	 */
	public Integer update(OfficeExecutiveMeetMinutes officeExecutiveMeetMinutes);

	/**
	 * 根据id获取office_executive_meet_minutes
	 * @param id
	 * @return
	 */
	public OfficeExecutiveMeetMinutes getOfficeExecutiveMeetMinutesById(String id);

	/**
	 * 根据ids数组查询office_executive_meet_minutesmap
	 * @param ids
	 * @return
	 */
	public Map<String, OfficeExecutiveMeetMinutes> getOfficeExecutiveMeetMinutesMapByIds(String[] ids);

	/**
	 * 获取office_executive_meet_minutes列表
	 * @return
	 */
	public List<OfficeExecutiveMeetMinutes> getOfficeExecutiveMeetMinutesList(String deptId, String meetId);

	/**
	 * 分页获取office_executive_meet_minutes列表
	 * @param page
	 * @return
	 */
	public List<OfficeExecutiveMeetMinutes> getOfficeExecutiveMeetMinutesPage(Pagination page);
	
	/**
	 * 批量保存
	 * @param list
	 */
	public void batchSave(List<OfficeExecutiveMeetMinutes> list);
	
	public void deleteByMeetId(String meetId);
	
	/**
	 * 判断是否包含纪要
	 * @param unitId
	 * @param meetIds
	 * @param deptId
	 * @return
	 */
	public Map<String, String> getMinutesMap(String unitId, String[] meetIds,String deptId);
}