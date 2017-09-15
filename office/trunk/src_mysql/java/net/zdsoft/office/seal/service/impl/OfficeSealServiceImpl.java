package net.zdsoft.office.seal.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.zdsoft.eis.base.common.entity.CustomRole;
import net.zdsoft.eis.base.common.entity.CustomRoleUser;
import net.zdsoft.eis.base.common.entity.Dept;
import net.zdsoft.eis.base.common.entity.Unit;
import net.zdsoft.eis.base.common.entity.User;
import net.zdsoft.eis.base.common.service.CustomRoleService;
import net.zdsoft.eis.base.common.service.CustomRoleUserService;
import net.zdsoft.eis.base.common.service.DeptService;
import net.zdsoft.eis.base.common.service.UnitService;
import net.zdsoft.eis.base.common.service.UserService;
import net.zdsoft.eis.base.constant.BaseConstant;
import net.zdsoft.eis.base.subsystemcall.service.OfficeSubsystemService;
import net.zdsoft.keel.util.DateUtils;
import net.zdsoft.keel.util.Pagination;
import net.zdsoft.office.seal.dao.OfficeSealDao;
import net.zdsoft.office.seal.entity.OfficeSeal;
import net.zdsoft.office.seal.entity.OfficeSealType;
import net.zdsoft.office.seal.service.OfficeSealService;
import net.zdsoft.office.seal.service.OfficeSealTypeService;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
/**
 * office_seal
 * @author 
 * 
 */
public class OfficeSealServiceImpl implements OfficeSealService{
	private OfficeSealDao officeSealDao;
	private OfficeSealTypeService officeSealTypeService;
	private UserService userService;
	private DeptService deptService;
	private UnitService unitService;
	private OfficeSubsystemService officeSubsystemService;
	private CustomRoleService customRoleService;
	private CustomRoleUserService customRoleUserService;

	@Override
	public OfficeSeal save(OfficeSeal officeSeal){
		//发送到消息中心
		if(StringUtils.isNotBlank(officeSeal.getState())&&!StringUtils.equals("1", officeSeal.getState())){
			remindMessage(officeSeal);
		}
		return officeSealDao.save(officeSeal);
	}

	@Override
	public Integer delete(String[] ids){
		return officeSealDao.delete(ids);
	}

	@Override
	public Integer update(OfficeSeal officeSeal){
		//发送到消息中心
		if(StringUtils.isNotBlank(officeSeal.getState())&&!StringUtils.equals("1", officeSeal.getState())){
			remindMessage(officeSeal);
		}
		return officeSealDao.update(officeSeal);
	}

	public void remindMessage(OfficeSeal ent){
		User user = null;
		String title = "";
		String content = "";
		Integer msgType=0;
		Set<String> userIds = new HashSet<String>();
		Set<String> userManageIds = new HashSet<String>();

		if(StringUtils.equals("2", ent.getState())){
			Dept dept=deptService.getDept(ent.getDeptId());
			String userId=dept.getDeputyHeadId();
			if(StringUtils.isNotBlank(userId)){
				user = userService.getUser(ent.getCreateUserId());
				title = "用印申请信息提醒";
				msgType = BaseConstant.MSG_TYPE_SEAL;
				content = "您好！您有用印申请需要处理。申请人："+user.getRealname() 
						+ ",申请时间：" + DateUtils.date2String(ent.getCreateTime(), "yyyy-MM-dd")+ "。";
				//获取审核人信息
				userIds.add(userId);
				Unit unit = unitService.getUnit(user.getUnitid());
				officeSubsystemService.sendMsgDetail(user, unit, title, content, content, false, msgType, userIds.toArray(new String[0]), null);
			}
		}else if((StringUtils.equals("3", ent.getState())||StringUtils.equals("4", ent.getState()))&&StringUtils.isBlank(ent.getManageUserId())){
			//用印管理人
			CustomRole role = customRoleService.getCustomRoleByRoleCode(ent.getUnitId(),  "office_seal_manage");
			List<CustomRoleUser> cusList=customRoleUserService.getCustomRoleUserList(role.getId());
			for (CustomRoleUser customRoleUser : cusList) {
				userManageIds.add(customRoleUser.getUserId());
			}
			user = userService.getUser(ent.getAuditUserId());
			User user2 = userService.getUser(ent.getCreateUserId());
			title = "用印审核过需处理信息提醒";
			msgType = BaseConstant.MSG_TYPE_SEAL;
			content = "您好！您有已审核过的用印信息需要加章处理。申请人："+user2.getRealname() 
					+ ",申请时间：" + DateUtils.date2String(ent.getCreateTime(), "yyyy-MM-dd")+ "。";
			//获取审核人信息
			Unit unit = unitService.getUnit(user.getUnitid());
			officeSubsystemService.sendMsgDetail(user, unit, title, content, content, false, msgType, userManageIds.toArray(new String[0]), null);
		}else if(StringUtils.isNotBlank(ent.getManageUserId())){
			user = userService.getUser(ent.getManageUserId());
			title = "用印处理信息提醒";
			msgType = BaseConstant.MSG_TYPE_SEAL;
			content = "您好！您申请的用印信息已经处理完成。";
			//获取审核人信息
			userIds.add(ent.getCreateUserId());
			Unit unit = unitService.getUnit(ent.getUnitId());
			officeSubsystemService.sendMsgDetail(user, unit, title, content, content, false, msgType, userIds.toArray(new String[0]), null);
		}
	}
	@Override
	public OfficeSeal getOfficeSealById(String id){//TODO
		OfficeSeal officeSeal= officeSealDao.getOfficeSealById(id);
		Map<String, OfficeSealType> officeSealMap=officeSealTypeService.getOfficeSealTypeByUnitIdMap(officeSeal.getUnitId());
		Set<String> userSet=new HashSet<String>();
		Set<String> deptSet=new HashSet<String>();
		officeSeal.setSealName(officeSealMap.get(officeSeal.getSealType())==null?"不存在":officeSealMap.get(officeSeal.getSealType()).getTypeName());
		userSet.add(officeSeal.getAuditUserId());
		userSet.add(officeSeal.getCreateUserId());
		userSet.add(officeSeal.getManageUserId());
		deptSet.add(officeSeal.getDeptId());
		Map<String,User> userMap=userService.getUserWithDelMap(userSet.toArray(new String[0]));
		Map<String,Dept> deptMap=deptService.getDeptMap(deptSet.toArray(new String[0]));
		if(StringUtils.isNotBlank(officeSeal.getAuditUserId())){
			User user=userMap.get(officeSeal.getAuditUserId());
			if(user==null){
				officeSeal.setAuditUserName("用户已删除");
			}else officeSeal.setAuditUserName(user.getRealname());
		}
		if(StringUtils.isNotBlank(officeSeal.getCreateUserId())){
			User user2=userMap.get(officeSeal.getCreateUserId());
			if(user2==null){
				officeSeal.setCreateUserName("用户已删除");
			}else officeSeal.setCreateUserName(user2.getRealname());
		}
		if(StringUtils.isNotBlank(officeSeal.getManageUserId())){
			User user3=userMap.get(officeSeal.getManageUserId());
			if(user3==null){
				officeSeal.setManageUserName("用户已删除");
			}else officeSeal.setManageUserName(user3.getRealname());
		}
		Dept dept=deptMap.get(officeSeal.getDeptId());
		if(dept==null){
			officeSeal.setDeptName("部门已删除");
		}else officeSeal.setDeptName(dept.getDeptname());
		return officeSeal;
	}

	@Override
	public Map<String, OfficeSeal> getOfficeSealMapByIds(String[] ids){
		return officeSealDao.getOfficeSealMapByIds(ids);
	}

	@Override
	public List<OfficeSeal> getOfficeSealList(){
		return officeSealDao.getOfficeSealList();
	}

	@Override
	public List<OfficeSeal> getOfficeSealPage(Pagination page){
		return officeSealDao.getOfficeSealPage(page);
	}

	@Override
	public List<OfficeSeal> getOfficeSealByUnitIdList(String unitId){
		return officeSealDao.getOfficeSealByUnitIdList(unitId);
	}

	@Override
	public List<OfficeSeal> getOfficeSealByUnitIdPage(String unitId, Pagination page){
		return officeSealDao.getOfficeSealByUnitIdPage(unitId, page);
	}

	@Override
	public List<OfficeSeal> getOfficeSealByOthers(String years,
			String semesters, String userId, String unitId,Pagination page) {
		List<OfficeSeal> officeSealList= officeSealDao.getOfficeSealByOthers(years, semesters, userId, unitId,page);
		Map<String, OfficeSealType> officeSealMap=officeSealTypeService.getOfficeSealTypeByUnitIdMap(unitId);
		Set<String> userSet=new HashSet<String>();
		Set<String> deptSet=new HashSet<String>();
		for(OfficeSeal officeSeal: officeSealList){
			officeSeal.setSealName(officeSealMap.get(officeSeal.getSealType())==null?"不存在":officeSealMap.get(officeSeal.getSealType()).getTypeName());
			userSet.add(officeSeal.getAuditUserId());
			userSet.add(officeSeal.getCreateUserId());
			userSet.add(officeSeal.getManageUserId());
			deptSet.add(officeSeal.getDeptId());
		}
		Map<String,User> userMap=userService.getUserWithDelMap(userSet.toArray(new String[0]));
		Map<String,Dept> deptMap=deptService.getDeptMap(deptSet.toArray(new String[0]));
		for(OfficeSeal officeSeal:officeSealList){
			if(StringUtils.isNotBlank(officeSeal.getAuditUserId())){
				User user=userMap.get(officeSeal.getAuditUserId());
				if(user==null){
					officeSeal.setAuditUserName("用户已删除");
				}else officeSeal.setAuditUserName(user.getRealname());
			}
			if(StringUtils.isNotBlank(officeSeal.getCreateUserId())){
				User user2=userMap.get(officeSeal.getCreateUserId());
				if(user2==null){
					officeSeal.setCreateUserName("用户已删除");
				}else officeSeal.setCreateUserName(user2.getRealname());
			}
			if(StringUtils.isNotBlank(officeSeal.getManageUserId())){
				User user3=userMap.get(officeSeal.getManageUserId());
				if(user3==null){
					officeSeal.setManageUserName("用户已删除");
				}else officeSeal.setManageUserName(user3.getRealname());
			}
			Dept dept=deptMap.get(officeSeal.getDeptId());
			if(dept==null){
				officeSeal.setDeptName("部门已删除");
			}else officeSeal.setDeptName(dept.getDeptname());
		}
		return officeSealList;
	}

	@Override
	public List<OfficeSeal> getOfficeSealManageByOthers(List<Dept> depts,String unitId,
			String deptId, String sealType, Pagination page) {
		List<OfficeSeal> officeSealList= officeSealDao.getOfficeSealManageByOthers(unitId, deptId, sealType, page);
		Map<String, OfficeSealType> officeSealMap=officeSealTypeService.getOfficeSealTypeByUnitIdMap(unitId);
		Set<String> userSet=new HashSet<String>();
		Set<String> deptSet=new HashSet<String>();
		for(OfficeSeal officeSeal: officeSealList){
			officeSeal.setSealName(officeSealMap.get(officeSeal.getSealType())==null?"不存在":officeSealMap.get(officeSeal.getSealType()).getTypeName());
			userSet.add(officeSeal.getAuditUserId());
			userSet.add(officeSeal.getCreateUserId());
			userSet.add(officeSeal.getManageUserId());
			deptSet.add(officeSeal.getDeptId());
		}
		if(CollectionUtils.isNotEmpty(depts)){
			for(Dept dept:depts){
				for(OfficeSeal officeSeal: officeSealList){
					if(StringUtils.equals(dept.getId(), officeSeal.getDeptId())){
						officeSeal.setMark("1");
					}
				}
			}
		}
		Map<String,User> userMap=userService.getUserWithDelMap(userSet.toArray(new String[0]));
		Map<String,Dept> deptMap=deptService.getDeptMap(deptSet.toArray(new String[0]));
		for(OfficeSeal officeSeal:officeSealList){
			if(StringUtils.isNotBlank(officeSeal.getAuditUserId())){
				User user=userMap.get(officeSeal.getAuditUserId());
				if(user==null){
					officeSeal.setAuditUserName("用户已删除");
				}else officeSeal.setAuditUserName(user.getRealname());
			}
			if(StringUtils.isNotBlank(officeSeal.getCreateUserId())){
				User user2=userMap.get(officeSeal.getCreateUserId());
				if(user2==null){
					officeSeal.setCreateUserName("用户已删除");
				}else officeSeal.setCreateUserName(user2.getRealname());
			}
			if(StringUtils.isNotBlank(officeSeal.getManageUserId())){
				User user3=userMap.get(officeSeal.getManageUserId());
				if(user3==null){
					officeSeal.setManageUserName("用户已删除");
				}else officeSeal.setManageUserName(user3.getRealname());
			}
			Dept dept=deptMap.get(officeSeal.getDeptId());
			if(dept==null){
				officeSeal.setDeptName("部门已删除");
			}else officeSeal.setDeptName(dept.getDeptname());
		}
		return officeSealList;
	}

	@Override
	public List<OfficeSeal> getOfficeSealByUnitIdTypeId(String unitId,
			String[] typeIds) {
		return officeSealDao.getOfficeSealByUnitIdTypeId(unitId, typeIds);
	}

	public void setOfficeSealDao(OfficeSealDao officeSealDao){
		this.officeSealDao = officeSealDao;
	}

	public void setOfficeSealTypeService(OfficeSealTypeService officeSealTypeService) {
		this.officeSealTypeService = officeSealTypeService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public void setDeptService(DeptService deptService) {
		this.deptService = deptService;
	}

	public void setUnitService(UnitService unitService) {
		this.unitService = unitService;
	}

	public void setOfficeSubsystemService(
			OfficeSubsystemService officeSubsystemService) {
		this.officeSubsystemService = officeSubsystemService;
	}

	public void setCustomRoleService(CustomRoleService customRoleService) {
		this.customRoleService = customRoleService;
	}

	public void setCustomRoleUserService(CustomRoleUserService customRoleUserService) {
		this.customRoleUserService = customRoleUserService;
	}
	
}
