package net.zdsoft.eis.base.data.sync.schsecurity.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.zdsoft.eis.base.common.entity.Dept;
import net.zdsoft.eis.base.common.entity.Role;
import net.zdsoft.eis.base.common.entity.School;
import net.zdsoft.eis.base.common.entity.Unit;
import net.zdsoft.eis.base.common.entity.User;
import net.zdsoft.eis.base.common.service.DeptService;
import net.zdsoft.eis.base.common.service.SchoolService;
import net.zdsoft.eis.base.common.service.UnitService;
import net.zdsoft.eis.base.common.service.UserService;
import net.zdsoft.eis.base.data.dao.BaseTeacherDao;
import net.zdsoft.eis.base.data.dao.BaseUserDao;
import net.zdsoft.eis.base.data.entity.BaseTeacher;
import net.zdsoft.eis.base.data.sync.schsecurity.constant.JBSyncConstant;
import net.zdsoft.eis.base.data.sync.schsecurity.dao.JGJBXXDao;
import net.zdsoft.eis.base.data.sync.schsecurity.dao.SyncTYSFYHBLogDao;
import net.zdsoft.eis.base.data.sync.schsecurity.dao.TYSFYHBDao;
import net.zdsoft.eis.base.data.sync.schsecurity.dao.XXJBXXBDao;
import net.zdsoft.eis.base.data.sync.schsecurity.entity.JGJBXX;
import net.zdsoft.eis.base.data.sync.schsecurity.entity.TYSFYHB;
import net.zdsoft.eis.base.data.sync.schsecurity.entity.XXJBXXB;
import net.zdsoft.eis.base.data.sync.schsecurity.exception.SyncException;
import net.zdsoft.eis.system.data.dao.UserRoleRelationDao;
import net.zdsoft.eis.system.data.entity.UserRoleRelation;
import net.zdsoft.eis.system.data.service.RoleService;
import net.zdsoft.leadin.util.UUIDGenerator;

import org.apache.commons.collections.CollectionUtils;

public class TYSFYHBSyncServiceImpl extends AbstractSyncJBServiceImpl<TYSFYHB>{
	
	private TYSFYHBDao tYSFYHBDao;
	private SyncTYSFYHBLogDao syncTYSFYHBLogDao;
	private JGJBXXDao jGJBXXDao;
	private XXJBXXBDao xXJBXXBDao;
	private BaseTeacherDao baseTeacherDao;
	private UnitService unitService;
	private DeptService deptService;
	private UserService userService;
	private BaseUserDao baseUserDao;
	private UserRoleRelationDao userRoleRelationDao;
	private RoleService roleService;
	private SchoolService schoolService;

	
	@Override
	String getTimeCode() {
		return JBSyncConstant.JB_SYNC_TIME_USER;
	}
	
	@Override
	String getLastGxsj() {
		return tYSFYHBDao.getLastGxsj();
	}
	
	@Override
	void saveDatas(Date beginTime) {
		if(beginTime == null) {
			throw new RuntimeException("未获取上一次同步用户表的时间");
		}
		int version = syncTYSFYHBLogDao.getLastVersion();
		List<TYSFYHB> list = tYSFYHBDao.getListByGxsj(beginTime);
		int success = 0;
		int error = 0;
		for(TYSFYHB entity : list) {
			entity.setSyncTime(new Date());
			entity.setSyncResult(JBSyncConstant.SYNC_RESULT_INIT);
			entity.setSyncVersion(version+1);
			User user = userService.getUserByUserName(entity.getYHM());
			//新增数据状态不正常的不同步
			if(user == null && !JBSyncConstant.ZF_JGYXBS_ZC.equals(entity.getSTATUS())) {
				error ++;
				continue;
			}
			entity.setSJBHLX(user == null ? JBSyncConstant.SJBHLX_INSERT : JBSyncConstant.SJBHLX_UPDATE);
			//关闭以前版本中相同id的失败记录
			syncTYSFYHBLogDao.closeFailRecord(entity.getYHID());
			syncTYSFYHBLogDao.save(entity);
			success++;
		}
		LOG.error("[同步机构共："+CollectionUtils.size(list)+"条，成功："+success+"条，失败："+error+"条]");
	}
	
	@Override
	void insert(TYSFYHB entity) {
		if(JBSyncConstant.ZF_USER_STATUS_ZC.equals(entity.getSTATUS())) {
			//新增教师
			BaseTeacher teacher = new BaseTeacher();
			Unit unit = null;
			if(JBSyncConstant.YHSX_JGGLY.equals(entity.getYHSX()) || JBSyncConstant.YHSX_JGJZG.equals(entity.getYHSX())) {
				JGJBXX jgjbxx = jGJBXXDao.getById(entity.getDWH());
				if(jgjbxx == null){
					throw new SyncException("未找到该用户所属机构[zfim表]");
				}
//				unit =  unitService.getUnitByUnionId(jgjbxx.getJGBSM());
				if(JBSyncConstant.JGLBM_SJYT.equals(jgjbxx.getJGLBM())) {
					unit = unitService.getTopEdu();
				}else {
					unit = unitService.getUnit(get32Id(jgjbxx.getDQBH()));
				}
			}else if(JBSyncConstant.YHSX_XXGLY.equals(entity.getYHSX()) || JBSyncConstant.YHSX_XXJZG.equals(entity.getYHSX())){
				XXJBXXB xxjbxxb = xXJBXXBDao.getById(entity.getDWH());
				if(xxjbxxb == null){
					throw new SyncException("未找到该用户所属机构[zfim表]");
				}
//				unit =  unitService.getUnitByUnionId(xxjbxxb.getXXBSM());
				School school = schoolService.getSchoolByCode(xxjbxxb.getXXBSM());
				unit =  unitService.getUnit(school.getId());
			}else {
				throw new SyncException("没有这样的用户属性！");
			}
			if(unit == null) {
				throw new SyncException("未找到该用户所属的单位！");
			}
			teacher.setUnitid(unit.getId());
			List<Dept> deptList = deptService.getDepts(unit.getId());
			if(deptList == null || deptList.size() == 0) {
				throw new SyncException("未找到该用户所属的部门！");
			}
			teacher.setId(UUIDGenerator.getUUID());
			teacher.setDeptid(deptList.get(0).getId());
			teacher.setTchId("000001"); 
			teacher.setSex(entity.getXB());
			teacher.setName(entity.getXM());
			teacher.setNation("01");
			teacher.setBirthday(new Date());
			teacher.setEusing("11"); //在职标记
			
			//新增用户
			User user = new User();
			user.setId(UUIDGenerator.getUUID());
			user.setTeacherid(teacher.getId());
			user.setUnitid(teacher.getUnitid());
			user.setOwnerType(User.TEACHER_LOGIN);
			user.setName(entity.getYHM());
			user.setRealname(entity.getXM());
			user.setMark(JBSyncConstant.STATUS_RELATION_MAP.get(entity.getSTATUS()));
			user.setType(User.USER_TYPE_COMMON);
			user.setRole(2);
			//新增用户角色关系
			UserRoleRelation userRole = new UserRoleRelation();
			userRole.setFlag(0);
			userRole.setUserid(user.getId());
			Role role = roleService.getRole(user.getUnitid(), JBSyncConstant.ROLE_IDENTITY);
			if(role == null) {
				throw new SyncException("未找到该用户所在单位的校安管理员角色！");
			}
			userRole.setRoleid(role.getId());
			List<UserRoleRelation> userRoles = new ArrayList<UserRoleRelation>();
			userRoles.add(userRole);
			userRoleRelationDao.saveUserRoles(userRoles);
			baseTeacherDao.insertTeacher(teacher);
			baseUserDao.insertUser(user);
		}
	}

	@Override
	void update(TYSFYHB entity) {
		//新增用户
		User user = userService.getUserByUserName(entity.getYHM());
		if(user == null) {
			throw new SyncException("未找到该用户！");
		}
		
		Unit unit = null;
		if(JBSyncConstant.YHSX_JGGLY.equals(entity.getYHSX()) || JBSyncConstant.YHSX_JGJZG.equals(entity.getYHSX())) {
			JGJBXX jgjbxx = jGJBXXDao.getById(entity.getDWH());
//			unit =  unitService.getUnitByUnionId(jgjbxx.getJGBSM());
			if(jgjbxx.getJGLBM().equals(JBSyncConstant.JGLBM_SJYT)) {
				unit = unitService.getTopEdu();
			}else {
				unit = unitService.getUnit(get32Id(jgjbxx.getDQBH()));
			}
		}else if(JBSyncConstant.YHSX_XXGLY.equals(entity.getYHSX()) || JBSyncConstant.YHSX_XXJZG.equals(entity.getYHSX())){
			XXJBXXB xxjbxxb = xXJBXXBDao.getById(entity.getDWH());
//			unit =  unitService.getUnitByUnionId(xxjbxxb.getXXBSM());
			School school = schoolService.getSchoolByCode(xxjbxxb.getXXBSM());
			unit =  unitService.getUnit(school.getId());
		}else {
			throw new SyncException("没有这样的用户属性！");
		}
		if(unit == null) {
			throw new SyncException("未找到该用户所属的单位！");
		}
		user.setUnitid(unit.getId());
		user.setRealname(entity.getXM());
		user.setMark(JBSyncConstant.STATUS_RELATION_MAP.get(entity.getSTATUS()));
		//新增用户角色关系
		UserRoleRelation userRole = new UserRoleRelation();
		userRole.setFlag(0);
		userRole.setUserid(user.getId());
		Role role = roleService.getRole(user.getUnitid(), JBSyncConstant.ROLE_IDENTITY);
		if(role == null) {
			throw new SyncException("未找到该用户所在单位的校安管理员角色！");
		}
		userRole.setRoleid(role.getId());
		List<UserRoleRelation> userRoles = new ArrayList<UserRoleRelation>();
		userRoles.add(userRole);
		
		BaseTeacher teacher = baseTeacherDao.getBaseTeacher(user.getTeacherid());
		teacher.setUnitid(user.getUnitid());
		List<Dept> deptList = deptService.getDepts(user.getUnitid());
		if(deptList == null || deptList.size() == 0) {
			throw new SyncException("未找到该用户所属的部门！");
		}
		teacher.setDeptid(deptList.get(0).getId());
		teacher.setSex(entity.getXB());
		teacher.setName(entity.getXM());
		if(!entity.getSTATUS().equals(JBSyncConstant.ZF_USER_STATUS_ZC) && !entity.getSTATUS().equals(JBSyncConstant.ZF_USER_STATUS_SD)) {
			teacher.setIsdeleted(true);
		}
		userRoleRelationDao.deleteUserRole(new String[]{user.getId()});
		userRoleRelationDao.saveUserRoles(userRoles);
		baseTeacherDao.updateTeacher(teacher);
		baseUserDao.updateUser(user);
	}

	@Override
	List<TYSFYHB> getData() {
		List<TYSFYHB> list = syncTYSFYHBLogDao.getListByResults(new String[] {
				JBSyncConstant.SYNC_RESULT_INIT, JBSyncConstant.SYNC_RESULT_FAIL});
		return list;
	}
	
	@Override
	void updateData(TYSFYHB entity) {
		syncTYSFYHBLogDao.update(entity);
	}

	public void settYSFYHBDao(TYSFYHBDao tYSFYHBDao) {
		this.tYSFYHBDao = tYSFYHBDao;
	}

	public void setSyncTYSFYHBLogDao(SyncTYSFYHBLogDao syncTYSFYHBLogDao) {
		this.syncTYSFYHBLogDao = syncTYSFYHBLogDao;
	}

	public void setBaseTeacherDao(BaseTeacherDao baseTeacherDao) {
		this.baseTeacherDao = baseTeacherDao;
	}

	public void setUnitService(UnitService unitService) {
		this.unitService = unitService;
	}

	public void setDeptService(DeptService deptService) {
		this.deptService = deptService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public void setBaseUserDao(BaseUserDao baseUserDao) {
		this.baseUserDao = baseUserDao;
	}

	public void setUserRoleRelationDao(UserRoleRelationDao userRoleRelationDao) {
		this.userRoleRelationDao = userRoleRelationDao;
	}

	public void setjGJBXXDao(JGJBXXDao jGJBXXDao) {
		this.jGJBXXDao = jGJBXXDao;
	}

	public void setxXJBXXBDao(XXJBXXBDao xXJBXXBDao) {
		this.xXJBXXBDao = xXJBXXBDao;
	}

	public void setRoleService(RoleService roleService) {
		this.roleService = roleService;
	}

	public void setSchoolService(SchoolService schoolService) {
		this.schoolService = schoolService;
	}
}
