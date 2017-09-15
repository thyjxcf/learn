package net.zdsoft.office.meeting.dao;

import java.util.List;
import java.util.Map;

import net.zdsoft.keel.util.Pagination;
import net.zdsoft.office.meeting.entity.OfficeExecutiveIssue;
/**
 * office_executive_issue
 * @author 
 * 
 */
public interface OfficeExecutiveIssueDao{

	/**
	 * 新增office_executive_issue
	 * @param officeExecutiveIssue
	 * @return
	 */
	public OfficeExecutiveIssue save(OfficeExecutiveIssue officeExecutiveIssue);

	/**
	 * 根据ids数组删除office_executive_issue
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
	 * 根据unitId获取office_executive_issue列表
	 * @param unitId
	 * @return
	 */
	public List<OfficeExecutiveIssue> getOfficeExecutiveIssueByUnitIdList(String unitId);

	/**
	 * 根据unitId分页office_executive_issue获取
	 * @param unitId
	 * @param userId
	 * @param deptId
	 * @param queryName
	 * @param queryStates
	 * @param page
	 * @return
	 */
	public List<OfficeExecutiveIssue> getOfficeExecutiveIssueByConditions(String unitId, String userId, String deptId, String queryName, String[] queryStates, Pagination page);
	
	public void submitIssue(String issueId);
	
	public void removeIssue(String issueId);
	
	public void saveIssueAudit(OfficeExecutiveIssue issue);
	
	/**
	 * 
	 * @param meetIds
	 * @return
	 */
	public List<OfficeExecutiveIssue> getOfficeExecutiveIssueByMeetIds(String[] meetIds);
	
	public List<OfficeExecutiveIssue> getOfficeExecutiveIssues(String unitId, Pagination page);

	public void addToMeet(String meetId,String[] ids,Integer firstNumber);
	
	/**
	 * 根据会议id获取议题ids
	 * @param meetId
	 */
	public List<String> getIdsByMeetId(String meetId);
	
	public void sortIssue(List<OfficeExecutiveIssue> issueList);
}