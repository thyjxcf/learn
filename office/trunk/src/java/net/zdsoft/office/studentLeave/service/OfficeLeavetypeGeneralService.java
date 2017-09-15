package net.zdsoft.office.studentLeave.service;

import java.util.*;

import net.zdsoft.office.studentLeave.entity.OfficeLeavetypeGeneral;
import net.zdsoft.keel.util.Pagination;
/**
 * office_leavetype_general
 * @author 
 * 
 */
public interface OfficeLeavetypeGeneralService{

	/**
	 * 新增office_leavetype_general
	 * @param officeLeavetypeGeneral
	 * @return
	 */
	public OfficeLeavetypeGeneral save(OfficeLeavetypeGeneral officeLeavetypeGeneral);

	/**
	 * 根据ids数组删除office_leavetype_general数据
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
	
	public OfficeLeavetypeGeneral findByLeaveId(String leaveId);

	public void insertGeneral(OfficeLeavetypeGeneral leaveGeneral);

	public void updateGeneral(OfficeLeavetypeGeneral leaveGeneral);

	public void startFlow(OfficeLeavetypeGeneral leaveGeneral, String userId);
	/**
	 * 获得学生的请假列表
	 * @param unitId
	 * @param stuId
	 * @param page
	 * @return
	 */
	public List<OfficeLeavetypeGeneral> getLeavelist(String unitId, String stuId, Pagination page);

	public void deleteByLeaveId(String leaveId);
	
	public List<OfficeLeavetypeGeneral> getAuditList(String searchType,
			String userId, Pagination page);

	public List<OfficeLeavetypeGeneral> findByLeaveTimeAndLeaveIds(Date date,
			String[] leaveIds);
	/**
	 * @param id
	 * @param startTime
	 * @param endTime
	 * @param hasTemporary 是否判断临时请假
	 * @param temId TODO
	 * @param stuId TODO
	 * @return
	 */
	public boolean isExistTime(String id, Date startTime, Date endTime, boolean hasTemporary, String temId, String stuId);
}