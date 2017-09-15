package net.zdsoft.office.meeting.service;

import java.util.List;
import java.util.Map;

import net.zdsoft.keel.util.Pagination;
import net.zdsoft.office.meeting.entity.OfficeExecutiveIssue;
/**
 * office_executive_issue
 * @author 
 * 
 */
public interface OfficeExecutiveIssueService{

	/**
	 * 新增office_executive_issue
	 * @param officeExecutiveIssue
	 * @return
	 */
	public OfficeExecutiveIssue save(OfficeExecutiveIssue officeExecutiveIssue);

	/**
	 * 根据ids数组删除office_executive_issue数据
	 * @param ids
	 * @return
	 */
	public Integer delete(String[] ids);

	/**
	 * 更新office_executive_issue
	 * @param officeExecutiveIssue
	 * @return
	 */
	public Integer update(OfficeExecutiveIssue officeExecutiveIssue);

	/**
	 * 根据id获取office_executive_issue
	 * @param id
	 * @return
	 */
	public OfficeExecutiveIssue getOfficeExecutiveIssueById(String id);

	/**
	 * 根据ids数组查询office_executive_issuemap
	 * @param ids
	 * @return
	 */
	public Map<String, OfficeExecutiveIssue> getOfficeExecutiveIssueMapByIds(String[] ids);

	/**
	 * 获取office_executive_issue列表
	 * @return
	 */
	public List<OfficeExecutiveIssue> getOfficeExecutiveIssueList(String meetId, Pagination page);

	/**
	 * 分页获取office_executive_issue列表
	 * @param page
	 * @return
	 */
	public List<OfficeExecutiveIssue> getOfficeExecutiveIssuePage(Pagination page);

	/**
	 * 根据UnitId获取office_executive_issue列表
	 * @param unitId
	 * @return
	 */
	public List<OfficeExecutiveIssue> getOfficeExecutiveIssueByUnitIdList(String unitId);

	/**
	 * 根据UnitId分页获取office_executive_issue
	 * @param unitId
	 * @param userId
	 * @param queryName
	 * @param queryStates
	 * @param page
	 * @return
	 */
	public List<OfficeExecutiveIssue> getOfficeExecutiveIssueByConditions(String unitId, String userId, String queryName, String[] queryStates, Pagination page);
	
	public void submitIssue(String issueId);
	
	public void saveIssue(OfficeExecutiveIssue issue);
	
	public void deleteIssue(String issueId);
	
	public void removeIssue(String unitId, String issueId, String meetId);
	
	public void saveIssueAudit(OfficeExecutiveIssue issue);
	
	/**
	 * 根据会议ids获取会议对应的议题map
	 * @param meetIds
	 * @return
	 */
	public Map<String, List<OfficeExecutiveIssue>> getOfficeExecutiveIssueMapByMeetIds(String[] meetIds);
	
	/**
	 * 根据会议id及用户id获取相关议题
	 * @param meetIds
	 * @param userId
	 * @param deptId
	 * @return
	 */
	public Map<String, List<OfficeExecutiveIssue>> getOfficeExecutiveIssueMapByMeetIds(String[] meetIds, String userId, String deptId);
	
	/**
	 * 搜索出已经审核通过的并且没有还没有绑定会议的议题
	 * @param unitId
	 * @param page
	 */
	public List<OfficeExecutiveIssue> getOfficeExecutiveIssues(String unitId, Pagination page);
	
	/**
	 * 将议题添加到会议
	 * @param unitId
	 * @param meetId
	 * @param ids
	 * @param needAddOne 是否需要加1
	 */
	public void addToMeet(String unitId, String meetId, String[] ids, boolean needAddOne);
	
	/**
	 * 保存议题序号
	 * @param issueList
	 */
	public void sortIssue(List<OfficeExecutiveIssue> issueList);
}