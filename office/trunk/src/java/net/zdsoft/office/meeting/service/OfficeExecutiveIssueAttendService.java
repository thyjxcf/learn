package net.zdsoft.office.meeting.service;

import java.util.List;
import java.util.Map;

import net.zdsoft.keel.util.Pagination;
import net.zdsoft.office.meeting.entity.OfficeExecutiveIssueAttend;
/**
 * office_executive_issue_attend
 * @author 
 * 
 */
public interface OfficeExecutiveIssueAttendService{

	/**
	 * 新增office_executive_issue_attend
	 * @param officeExecutiveIssueAttend
	 * @return
	 */
	public OfficeExecutiveIssueAttend save(OfficeExecutiveIssueAttend officeExecutiveIssueAttend);

	/**
	 * 根据ids数组删除office_executive_issue_attend数据
	 * @param ids
	 * @return
	 */
	public Integer delete(String[] ids);

	/**
	 * 更新office_executive_issue_attend
	 * @param officeExecutiveIssueAttend
	 * @return
	 */
	public Integer update(OfficeExecutiveIssueAttend officeExecutiveIssueAttend);

	/**
	 * 根据id获取office_executive_issue_attend
	 * @param id
	 * @return
	 */
	public OfficeExecutiveIssueAttend getOfficeExecutiveIssueAttendById(String id);

	/**
	 * 根据ids数组查询office_executive_issue_attendmap
	 * @param ids
	 * @return
	 */
	public Map<String, OfficeExecutiveIssueAttend> getOfficeExecutiveIssueAttendMapByIds(String[] ids);

	/**
	 * 获取office_executive_issue_attend列表
	 * @return
	 */
	public List<OfficeExecutiveIssueAttend> getOfficeExecutiveIssueAttendList();

	/**
	 * 分页获取office_executive_issue_attend列表
	 * @param page
	 * @return
	 */
	public List<OfficeExecutiveIssueAttend> getOfficeExecutiveIssueAttendPage(Pagination page);
	
	public List<OfficeExecutiveIssueAttend> getOfficeExecutiveIssueAttendList(String[] issueIds);
	
	/**
	 * 获取未组织数据的议题参加信息
	 * @param issueIds
	 * @return
	 */
	public List<OfficeExecutiveIssueAttend> getOfficeExecutiveIssueAttendListSimple(String[] issueIds);
	
	public void deleteByIssueIds(String[] issueIds);
	
	public void batchSave(List<OfficeExecutiveIssueAttend> attendlist);
	
	public void batchUpdate(List<OfficeExecutiveIssueAttend> attendlist);
	
	public Map<String, String> getOfficeExecutiveIssueAttendMap(String[] issueIds, String userId, String deptId);
}