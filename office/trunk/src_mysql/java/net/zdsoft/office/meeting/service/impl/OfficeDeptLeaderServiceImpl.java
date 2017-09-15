package net.zdsoft.office.meeting.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.zdsoft.eis.base.common.dao.DeptDao;
import net.zdsoft.eis.base.common.entity.Dept;
import net.zdsoft.eis.base.common.service.DeptService;
import net.zdsoft.eis.base.common.service.UserService;
import net.zdsoft.keel.util.Pagination;
import net.zdsoft.office.meeting.dao.OfficeDeptLeaderDao;
import net.zdsoft.office.meeting.entity.OfficeDeptLeader;
import net.zdsoft.office.meeting.service.OfficeDeptLeaderService;

import org.apache.commons.lang.StringUtils;
/**
 * office_dept_leader
 * @author 
 * 
 */
public class OfficeDeptLeaderServiceImpl implements OfficeDeptLeaderService{
	private OfficeDeptLeaderDao officeDeptLeaderDao;
	private DeptService deptService;
	private UserService userService;
	private DeptDao deptDao;
	
	@Override
	public OfficeDeptLeader save(OfficeDeptLeader officeDeptLeader){
		return officeDeptLeaderDao.save(officeDeptLeader);
	}

	@Override
	public Integer delete(String[] ids){
		return officeDeptLeaderDao.delete(ids);
	}

	@Override
	public Integer update(OfficeDeptLeader officeDeptLeader){
		return officeDeptLeaderDao.update(officeDeptLeader);
	}

	@Override
	public OfficeDeptLeader getOfficeDeptLeaderById(String id){
		return officeDeptLeaderDao.getOfficeDeptLeaderById(id);
	}

	@Override
	public Map<String, OfficeDeptLeader> getOfficeDeptLeaderMapByIds(String[] ids){
		return officeDeptLeaderDao.getOfficeDeptLeaderMapByIds(ids);
	}

	@Override
	public List<OfficeDeptLeader> getOfficeDeptLeaderList(){
		return officeDeptLeaderDao.getOfficeDeptLeaderList();
	}

	@Override
	public List<OfficeDeptLeader> getOfficeDeptLeaderPage(Pagination page){
		return officeDeptLeaderDao.getOfficeDeptLeaderPage(page);
	}

	@Override
	public List<OfficeDeptLeader> getOfficeDeptLeaderByUnitIdList(String unitId){
		List<OfficeDeptLeader> leaderList=new ArrayList<OfficeDeptLeader>();
		List<Dept> depts=deptDao.getDepts(unitId);
		Map<String,OfficeDeptLeader> leadMap=officeDeptLeaderDao.getOfficeDeptMap();
		for(Dept dept:depts){
			OfficeDeptLeader offDeptLead=new OfficeDeptLeader();
			offDeptLead.setUnitId(unitId);
			offDeptLead.setDeptId(dept.getId());
			if(leadMap.get(dept.getId())!=null&&StringUtils.isNotBlank(leadMap.get(dept.getId()).getUserId())){
				offDeptLead.setUserId(leadMap.get(dept.getId()).getUserId());
			}
			if(leadMap.get(dept.getId())!=null&&StringUtils.isNotBlank(leadMap.get(dept.getId()).getId())){
				offDeptLead.setId(leadMap.get(dept.getId()).getId());
			}
			leaderList.add(offDeptLead);
		}
		List<OfficeDeptLeader> sumList=new ArrayList<OfficeDeptLeader>();

		sumList.addAll(leaderList);
		for(OfficeDeptLeader lead:sumList){
			if(StringUtils.isNotBlank(lead.getDeptId())){
				lead.setDeptName(deptService.getDept(lead.getDeptId()).getDeptname());
			}
			if(StringUtils.isNotBlank(lead.getUserId())){
				lead.setUserName(userService.getUser(lead.getUserId()).getRealname());
			}
		}
		return sumList;
	}

	@Override
	public List<OfficeDeptLeader> getOfficeDeptLeaderByUnitIdPage(String unitId, Pagination page){
		return officeDeptLeaderDao.getOfficeDeptLeaderByUnitIdPage(unitId, page);
	}
	@Override
	public OfficeDeptLeader getOfficeDeptLeader(String unitId,
			String userId, String deptId) {
		return officeDeptLeaderDao.getOfficeDeptLeaderByUnitIdList(unitId,userId,deptId);
	}
	
	@Override
	public Map<String, String> getLeaderMap(String[] deptIds) {
		return officeDeptLeaderDao.getLeaderMap(deptIds);
	}
	
	public void setOfficeDeptLeaderDao(OfficeDeptLeaderDao officeDeptLeaderDao){
		this.officeDeptLeaderDao = officeDeptLeaderDao;
	}

	public void setDeptService(DeptService deptService) {
		this.deptService = deptService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public void setDeptDao(DeptDao deptDao) {
		this.deptDao = deptDao;
	}

	@Override
	public Map<String, OfficeDeptLeader> getOfficeLeaderMap() {
		return officeDeptLeaderDao.getOfficeDeptMap();
	}

}
