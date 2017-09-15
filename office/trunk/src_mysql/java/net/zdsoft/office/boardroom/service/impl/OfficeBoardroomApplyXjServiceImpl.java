package net.zdsoft.office.boardroom.service.impl;

import java.util.*;

import org.apache.commons.lang.StringUtils;

import net.zdsoft.office.boardroom.entity.OfficeApplyExXj;
import net.zdsoft.office.boardroom.entity.OfficeBoardroomApplyXj;
import net.zdsoft.office.boardroom.entity.OfficeBoardroomXj;
import net.zdsoft.office.boardroom.service.OfficeApplyDetailsXjService;
import net.zdsoft.office.boardroom.service.OfficeApplyExXjService;
import net.zdsoft.office.boardroom.service.OfficeBoardroomApplyXjService;
import net.zdsoft.office.boardroom.service.OfficeBoardroomXjService;
import net.zdsoft.office.boardroom.dao.OfficeBoardroomApplyXjDao;
import net.zdsoft.office.dailyoffice.entity.OfficeApplyNumber;
import net.zdsoft.office.dailyoffice.entity.OfficeRoomOrderSet;
import net.zdsoft.office.dailyoffice.entity.OfficeUtilityApply;
import net.zdsoft.office.dailyoffice.entity.OfficeUtilityNumber;
import net.zdsoft.office.util.Constants;
import net.zdsoft.eis.base.common.entity.Dept;
import net.zdsoft.eis.base.common.entity.User;
import net.zdsoft.eis.base.common.service.DeptService;
import net.zdsoft.eis.base.common.service.UserService;
import net.zdsoft.eisu.base.common.entity.TeachPlace;
import net.zdsoft.keel.util.Pagination;
import net.zdsoft.leadin.util.UUIDGenerator;
/**
 * office_boardroom_apply_xj
 * @author 
 * 
 */
public class OfficeBoardroomApplyXjServiceImpl implements OfficeBoardroomApplyXjService{
	private OfficeBoardroomApplyXjDao officeBoardroomApplyXjDao;
	private DeptService deptService;
	private UserService userService;
	private OfficeBoardroomXjService officeBoardroomXjService;
	private OfficeApplyExXjService officeApplyExXjService;
	private OfficeApplyDetailsXjService officeApplyDetailsXjService;

	@Override
	public OfficeBoardroomApplyXj save(OfficeBoardroomApplyXj officeBoardroomApplyXj){
		return officeBoardroomApplyXjDao.save(officeBoardroomApplyXj);
	}
	
	@Override
	public void batchSave(Map<String, OfficeBoardroomApplyXj> map){
		officeBoardroomApplyXjDao.batchSave(map);
	}

	@Override
	public Integer delete(String[] ids){
		return officeBoardroomApplyXjDao.delete(ids);
	}

	@Override
	public Integer update(OfficeBoardroomApplyXj officeBoardroomApplyXj){
		return officeBoardroomApplyXjDao.update(officeBoardroomApplyXj);
	}

	@Override
	public OfficeBoardroomApplyXj getOfficeBoardroomApplyXjById(String id){
		return officeBoardroomApplyXjDao.getOfficeBoardroomApplyXjById(id);
	}

	@Override
	public Map<String, OfficeBoardroomApplyXj> getOfficeBoardroomApplyXjMapByIds(String[] ids){
		return officeBoardroomApplyXjDao.getOfficeBoardroomApplyXjMapByIds(ids);
	}

	@Override
	public List<OfficeBoardroomApplyXj> getOfficeBoardroomApplyXjList(){
		return officeBoardroomApplyXjDao.getOfficeBoardroomApplyXjList();
	}

	@Override
	public List<OfficeBoardroomApplyXj> getOfficeBoardroomApplyXjPage(Pagination page){
		return officeBoardroomApplyXjDao.getOfficeBoardroomApplyXjPage(page);
	}

	@Override
	public List<OfficeBoardroomApplyXj> getOfficeBoardroomApplyXjByUnitIdList(String unitId){
		return officeBoardroomApplyXjDao.getOfficeBoardroomApplyXjByUnitIdList(unitId);
	}

	@Override
	public List<OfficeBoardroomApplyXj> getOfficeBoardroomApplyXjByUnitIdPage(String unitId, Pagination page){
		return officeBoardroomApplyXjDao.getOfficeBoardroomApplyXjByUnitIdPage(unitId, page);
	}

	@Override
	public List<OfficeBoardroomApplyXj> getOfficeBoardroomApplyXjByDeptIdUnitIdPage(
			String applyStartDate, String applyEndDate,String auditState, String roomId,
			String deptId, String unitId, Pagination page) {
		List<OfficeBoardroomApplyXj> officeBoardroomApplyXjs=officeBoardroomApplyXjDao.getOfficeBoardroomApplyXjByDeptIdUnitIdPage(applyStartDate,
				applyEndDate,auditState, roomId, deptId, unitId, page);
		Set<String> deptIdSet=new HashSet<String>();
		Set<String> userIdSet=new HashSet<String>();
		for (OfficeBoardroomApplyXj officeBoardroomApplyXj : officeBoardroomApplyXjs) {
			deptIdSet.add(officeBoardroomApplyXj.getApplyDeptId());
			userIdSet.add(officeBoardroomApplyXj.getApplyUserId());
			userIdSet.add(officeBoardroomApplyXj.getAuditUserId());
		}
		Map<String,Dept> deptMap=deptService.getDeptMap(deptIdSet.toArray(new String[0]));
		Map<String,User> userMap=userService.getUserWithDelMap(userIdSet.toArray(new String[0]));
		Map<String,OfficeBoardroomXj> officeBoardroomMap=officeBoardroomXjService.getOfficeBoardroomMapByUnitId(unitId);
		for(OfficeBoardroomApplyXj officeBoardroomApplyXj:officeBoardroomApplyXjs){
			if(deptMap.get(officeBoardroomApplyXj.getApplyDeptId())!=null){
				officeBoardroomApplyXj.setDeptName(deptMap.get(officeBoardroomApplyXj.getApplyDeptId()).getDeptname());
			}else{
				officeBoardroomApplyXj.setDeptName("部门已删除");
			}
			if(userMap.get(officeBoardroomApplyXj.getApplyUserId())!=null){
				officeBoardroomApplyXj.setApplyUserName(userMap.get(officeBoardroomApplyXj.getApplyUserId()).getRealname());
			}else{
				officeBoardroomApplyXj.setApplyUserName("用户已删除");
			}
			if(userMap.get(officeBoardroomApplyXj.getAuditUserId())!=null){
				officeBoardroomApplyXj.setAuditUserName(userMap.get(officeBoardroomApplyXj.getAuditUserId()).getRealname());
			}else{
				officeBoardroomApplyXj.setAuditUserName("");
			}
			if(officeBoardroomMap.get(officeBoardroomApplyXj.getRoomId())!=null){
				officeBoardroomApplyXj.setRoomName(officeBoardroomMap.get(officeBoardroomApplyXj.getRoomId()).getName());
			}else{
				officeBoardroomApplyXj.setRoomName("会议室已删除");
			}
		}
		return officeBoardroomApplyXjs;
	}
	
	@Override
	public void deleteRecord(String unitId,String[] ids) {
		List<OfficeApplyExXj> officeApplyExXjs=officeApplyExXjService.getOfficeApplyExXjByApplyId(unitId, ids[0]);
		Set<String> detailsId=new HashSet<String>();
		Set<String> applyExId=new HashSet<String>();
		for (OfficeApplyExXj officeApplyExXj : officeApplyExXjs) {
			detailsId.add(officeApplyExXj.getDetailsId());
			applyExId.add(officeApplyExXj.getId());
		}
		officeApplyExXjService.delete(applyExId.toArray(new String[0]));
		officeApplyDetailsXjService.delete(detailsId.toArray(new String[0]));
		officeBoardroomApplyXjDao.delete(ids);
		
	}

	@Override
	public void pass(String[] ids, String userId, String auditOption,String state) {
		String[] officeApplyDetailIds=officeApplyExXjService.getOfficeApplyDetailsXjByIds(ids);
		if(StringUtils.equals(state, "4")){
			officeApplyDetailsXjService.delete(officeApplyDetailIds);
		}else{
			officeApplyDetailsXjService.updateStateByIds(officeApplyDetailIds, state);
		}
		officeBoardroomApplyXjDao.updateStateByIds(ids, userId, auditOption, new Date(), state);
	}

	public void setOfficeBoardroomApplyXjDao(OfficeBoardroomApplyXjDao officeBoardroomApplyXjDao){
		this.officeBoardroomApplyXjDao = officeBoardroomApplyXjDao;
	}

	public void setDeptService(DeptService deptService) {
		this.deptService = deptService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public void setOfficeBoardroomXjService(
			OfficeBoardroomXjService officeBoardroomXjService) {
		this.officeBoardroomXjService = officeBoardroomXjService;
	}

	public void setOfficeApplyExXjService(
			OfficeApplyExXjService officeApplyExXjService) {
		this.officeApplyExXjService = officeApplyExXjService;
	}

	public void setOfficeApplyDetailsXjService(
			OfficeApplyDetailsXjService officeApplyDetailsXjService) {
		this.officeApplyDetailsXjService = officeApplyDetailsXjService;
	}
	
}
	