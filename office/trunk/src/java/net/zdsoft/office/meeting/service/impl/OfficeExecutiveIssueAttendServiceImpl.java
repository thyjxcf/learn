package net.zdsoft.office.meeting.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.zdsoft.eis.base.common.entity.Dept;
import net.zdsoft.eis.base.common.entity.User;
import net.zdsoft.eis.base.common.service.DeptService;
import net.zdsoft.eis.base.common.service.UserService;
import net.zdsoft.keel.util.Pagination;
import net.zdsoft.office.meeting.dao.OfficeExecutiveIssueAttendDao;
import net.zdsoft.office.meeting.entity.OfficeExecutiveIssueAttend;
import net.zdsoft.office.meeting.service.OfficeExecutiveIssueAttendService;
/**
 * office_executive_issue_attend
 * @author 
 * 
 */
public class OfficeExecutiveIssueAttendServiceImpl implements OfficeExecutiveIssueAttendService{
	private OfficeExecutiveIssueAttendDao officeExecutiveIssueAttendDao;
	private DeptService deptService;
	private UserService userService;

	@Override
	public OfficeExecutiveIssueAttend save(OfficeExecutiveIssueAttend officeExecutiveIssueAttend){
		return officeExecutiveIssueAttendDao.save(officeExecutiveIssueAttend);
	}

	@Override
	public Integer delete(String[] ids){
		return officeExecutiveIssueAttendDao.delete(ids);
	}

	@Override
	public Integer update(OfficeExecutiveIssueAttend officeExecutiveIssueAttend){
		return officeExecutiveIssueAttendDao.update(officeExecutiveIssueAttend);
	}

	@Override
	public OfficeExecutiveIssueAttend getOfficeExecutiveIssueAttendById(String id){
		return officeExecutiveIssueAttendDao.getOfficeExecutiveIssueAttendById(id);
	}

	@Override
	public Map<String, OfficeExecutiveIssueAttend> getOfficeExecutiveIssueAttendMapByIds(String[] ids){
		return officeExecutiveIssueAttendDao.getOfficeExecutiveIssueAttendMapByIds(ids);
	}

	@Override
	public List<OfficeExecutiveIssueAttend> getOfficeExecutiveIssueAttendList(){
		return officeExecutiveIssueAttendDao.getOfficeExecutiveIssueAttendList();
	}

	@Override
	public List<OfficeExecutiveIssueAttend> getOfficeExecutiveIssueAttendPage(Pagination page){
		return officeExecutiveIssueAttendDao.getOfficeExecutiveIssueAttendPage(page);
	}
	
	@Override
	public List<OfficeExecutiveIssueAttend> getOfficeExecutiveIssueAttendList(String[] issueIds){
		List<OfficeExecutiveIssueAttend> attendlist = officeExecutiveIssueAttendDao.getOfficeExecutiveIssueAttendList(issueIds);
		Set<String> userIds = new HashSet<String>();
		Set<String> deptIds = new HashSet<String>();
		for(OfficeExecutiveIssueAttend attend : attendlist){
			if(attend.getType() == 3)
				userIds.add(attend.getObjectId());
			else
				deptIds.add(attend.getObjectId());
		}
		Map<String, User> userMap = userService.getUserWithDelMap(userIds.toArray(new String[0]));
		Map<String, Dept> deptMap = deptService.getDeptMap(deptIds.toArray(new String[0]));
		for(OfficeExecutiveIssueAttend attend : attendlist){
			if(attend.getType() == 3){
				User user = userMap.get(attend.getObjectId());
				if(user != null)
					attend.setObjectName(user.getRealname());
			}
			else{
				Dept dept = deptMap.get(attend.getObjectId());
				if(dept != null)
					attend.setObjectName(dept.getDeptname());
			}
		}
		return attendlist;
	}
	
	@Override
	public List<OfficeExecutiveIssueAttend> getOfficeExecutiveIssueAttendListSimple(
			String[] issueIds) {
		return officeExecutiveIssueAttendDao.getOfficeExecutiveIssueAttendList(issueIds);
	}
	
	@Override
	public void deleteByIssueIds(String[] issueIds){
		officeExecutiveIssueAttendDao.deleteByIssueIds(issueIds);
	}
	
	@Override
	public void batchSave(List<OfficeExecutiveIssueAttend> attendlist){
		officeExecutiveIssueAttendDao.batchSave(attendlist);
	}
	
	@Override
	public void batchUpdate(List<OfficeExecutiveIssueAttend> attendlist){
		officeExecutiveIssueAttendDao.batchUpdate(attendlist);
	}
	
	@Override
	public Map<String, String> getOfficeExecutiveIssueAttendMap(String[] issueIds,
			String userId, String deptId) {
		return officeExecutiveIssueAttendDao.getOfficeExecutiveIssueAttendMap(issueIds, userId, deptId);
	}
	
	public void setOfficeExecutiveIssueAttendDao(OfficeExecutiveIssueAttendDao officeExecutiveIssueAttendDao){
		this.officeExecutiveIssueAttendDao = officeExecutiveIssueAttendDao;
	}

	public void setDeptService(DeptService deptService) {
		this.deptService = deptService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

}