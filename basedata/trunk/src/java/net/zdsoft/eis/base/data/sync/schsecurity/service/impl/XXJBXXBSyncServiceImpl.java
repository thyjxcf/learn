package net.zdsoft.eis.base.data.sync.schsecurity.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.zdsoft.eis.base.common.entity.Dept;
import net.zdsoft.eis.base.common.entity.Role;
import net.zdsoft.eis.base.common.entity.School;
import net.zdsoft.eis.base.common.entity.Unit;
import net.zdsoft.eis.base.common.service.SchoolService;
import net.zdsoft.eis.base.constant.BaseConstant;
import net.zdsoft.eis.base.data.dao.BaseDeptDao;
import net.zdsoft.eis.base.data.dao.BaseSchoolDao;
import net.zdsoft.eis.base.data.dao.BaseUnitDao;
import net.zdsoft.eis.base.data.entity.BaseSchool;
import net.zdsoft.eis.base.data.entity.BaseUnit;
import net.zdsoft.eis.base.data.sync.schsecurity.constant.JBSyncConstant;
import net.zdsoft.eis.base.data.sync.schsecurity.dao.JGJBXXDao;
import net.zdsoft.eis.base.data.sync.schsecurity.dao.SyncXXJBXXBLogDao;
import net.zdsoft.eis.base.data.sync.schsecurity.dao.XXJBXXBDao;
import net.zdsoft.eis.base.data.sync.schsecurity.entity.JGJBXX;
import net.zdsoft.eis.base.data.sync.schsecurity.entity.XXJBXXB;
import net.zdsoft.eis.base.data.sync.schsecurity.exception.SyncException;
import net.zdsoft.eis.system.data.dao.RoleDao;
import net.zdsoft.eis.system.data.dao.RolePermDao;
import net.zdsoft.eis.system.data.entity.RolePerm;
import net.zdsoft.leadin.util.UUIDGenerator;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

public class XXJBXXBSyncServiceImpl extends AbstractSyncJBServiceImpl<XXJBXXB> {
	
	private XXJBXXBDao xXJBXXBDao;
	private JGJBXXDao jGJBXXDao;
	private SyncXXJBXXBLogDao syncXXJBXXBLogDao;
	private BaseDeptDao baseDeptDao;
	private RoleDao roleDao;
	private RolePermDao rolePermDao;
	private BaseUnitDao baseUnitDao;
	private BaseSchoolDao baseSchoolDao;
	private SchoolService schoolService;
	
	@Override
	String getLastGxsj() {
		return xXJBXXBDao.getLastGxsj();
	}
	
	@Override
	String getTimeCode() {
		return JBSyncConstant.JB_SYNC_TIME_SCHOOL;
	}
	
	@Override
	void saveDatas(Date beginTime) {
		if(beginTime == null) {
			throw new RuntimeException("未获取上一次同步学校表的时间");
		}
		int version = syncXXJBXXBLogDao.getLastVersion();
		List<XXJBXXB> list = xXJBXXBDao.getListByGxsj(beginTime);
		
		int success = 0;
		int error = 0;
		for(XXJBXXB entity : list) {
			entity.setSyncTime(new Date());
			entity.setSyncResult(JBSyncConstant.SYNC_RESULT_INIT);
			entity.setSyncVersion(version+1);
			School school = schoolService.getSchoolByCode(entity.getXXBSM());
//			School school = schoolService.getSchool(get32Id(entity.getXXBSM()));
			//新增数据状态不正常的不同步
			if(school == null && !JBSyncConstant.ZF_JGYXBS_ZC.equals(entity.getXXYXBS())) {
				error ++;
				continue;
			}
//			if("J3300000000000".equals(entity.getLSJGM())) {
//				entity.setDQBH("330000");
//			}else {
//				entity.setDQBH(entity.getLSJGM().substring(0,6));
//			}
			if(StringUtils.isBlank(entity.getDQBH())){
				error ++;
				LOG.error("[同步保存学校"+entity.getXXMC()+" DQBH为空，跳过！]");
				continue;
			}
			entity.setSJBHLX(school == null ? JBSyncConstant.SJBHLX_INSERT : JBSyncConstant.SJBHLX_UPDATE);
			//关闭以前版本中相同id的失败记录
			syncXXJBXXBLogDao.closeFailRecord(entity.getXXID());
			syncXXJBXXBLogDao.save(entity);
			success ++;
		}
		LOG.error("[同步机构共："+CollectionUtils.size(list)+"条，成功："+success+"条，失败："+error+"条]");
	}

	@Override
	void insert(XXJBXXB entity) {
		if(entity.getXXYXBS().equals(JBSyncConstant.ZF_JGYXBS_ZC)) {
			//创建学校
			BaseSchool baseSchool = new BaseSchool();
			baseSchool.setId(UUIDGenerator.getUUID());
			baseSchool.setName(entity.getXXMC());
			baseSchool.setCode(entity.getXXBSM());
			baseSchool.setRegion(entity.getDQBH());
			baseSchool.setSchoolType(entity.getXXLBM());
			//创建单位
			BaseUnit baseUnit = new BaseUnit();
			baseUnit.setId(baseSchool.getId());
			baseUnit.setName(entity.getXXMC());
			baseUnit.setUnitclass(Unit.UNIT_CLASS_SCHOOL);
			baseUnit.setRegion(entity.getDQBH());
			baseUnit.setOrderid((long)entity.getXSSH());
			baseUnit.setUnittype(Unit.UNIT_SCHOOL_ASP);
			
			JGJBXX jgjbxx = jGJBXXDao.getById(entity.getLSJGM());
			if(jgjbxx == null){
				throw new SyncException("未找到该学校隶属或归口管理机构[zfim表]！");
			}
			
//			Unit unit = baseUnitDao.getBaseUnitByUnionId(jgjbxx.getJGBSM());
			Unit unit = null;
			if(JBSyncConstant.JGLBM_SJYT.equals(jgjbxx.getJGLBM())) {
				unit = baseUnitDao.getBaseUnitByUnionId("33");
			}else {
				unit = baseUnitDao.getBaseUnit(get32Id(jgjbxx.getDQBH()));
			}
			if(unit == null) {
				throw new SyncException("未找到该学校隶属或归口管理机构！");
			}
			String maxUnionId = baseUnitDao.getAvaUnionId(unit.getId(), Unit.UNIT_CLASS_SCHOOL, 12);
			String unionid = jgjbxx.getDQBH()+"000001";
			if(StringUtils.isNotBlank(maxUnionId)) {
				unionid = maxUnionId.substring(0,6) + get6Id((Integer.parseInt(maxUnionId.substring(6))+1)+"");
			}
			baseUnit.setUnionid(unionid);
			baseUnit.setParentid(unit.getId());
			baseUnit.setRegionlevel(Unit.UNIT_REGION_SCHOOL);
			//创建单位管理部门
			Dept dept = new Dept();
			dept.setUnitId(baseUnit.getId());
			dept.setDeptCode(baseUnit.getRegion());
			dept.setDeptname("学校管理组");
			dept.setParentid(BaseConstant.ZERO_GUID);
			dept.setJymark(1);
			dept.setInstituteId(BaseConstant.ZERO_GUID);
			baseDeptDao.insertDept(dept);
			//创建校安管理员角色
			Role role = new Role();
			role.setIdentifier(JBSyncConstant.ROLE_IDENTITY);
			role.setUnitid(baseUnit.getId());
			role.setName("校安管理员");
			role.setDescription("负责校园安全管理");
			role.setSubsystem("13,97");
			role.setIsactive(true);
			roleDao.insertRole(role);
			Role unitRole = roleDao.getRoleByName(baseUnit.getId(), "校安管理员");
			List<RolePerm> permList = new ArrayList<RolePerm>();
			List<String> moduleIdList = getModuleIdsBySubsystemid(new String[] {"13","97"}, Unit.UNIT_CLASS_SCHOOL);
			for(String moduleId : moduleIdList) {
				RolePerm rolePerm = new RolePerm();
				rolePerm.setModuleid(Long.parseLong(moduleId));
				rolePerm.setRoleid(unitRole.getId());
				permList.add(rolePerm);
			}
			rolePermDao.saveRolePerms(permList);
			baseUnitDao.insertUnit(baseUnit);
			baseSchoolDao.insertSchool(baseSchool);
		}
	}

	@Override
	void update(XXJBXXB entity) {
		School school = schoolService.getSchoolByCode(entity.getXXBSM());
		BaseSchool baseSchool = baseSchoolDao.getBaseSchool(school.getId());
//		if(baseSchool == null) {
//			throw new SyncException("未找到该学校！");
//		}
//		
//		baseSchool.setName(entity.getXXMC());
////		baseSchool.setCode(entity.getXXBSM()); //唯一标识不能更新
//		baseSchool.setRegion(entity.getDQBH());
//		baseSchool.setSchoolType(entity.getXXLBM());
//		
		BaseUnit baseUnit = baseUnitDao.getBaseUnit(baseSchool.getId());
//		if(baseUnit == null) {
//			throw new SyncException("未找到该机构！");
//		}
////		baseUnit.setUnionid(entity.getXXBSM());//唯一标识不能更新
//		baseUnit.setName(entity.getXXMC());
//		baseUnit.setUnitclass(Unit.UNIT_CLASS_SCHOOL);
//		baseUnit.setRegion(entity.getDQBH());
//		baseUnit.setOrderid(entity.getXSSH()+"");
//		baseUnit.setUnittype(Unit.UNIT_SCHOOL_ASP);
//		JGJBXX jgjbxx = jGJBXXDao.getById(entity.getLSJGM());
////		Unit unit = baseUnitDao.getBaseUnitByUnionId(jgjbxx.getJGBSM());
////		Unit unit = baseUnitDao.getBaseUnit(get32Id(jgjbxx.getJGBSM()));
//		Unit unit = null;
//		if(jgjbxx.getJGLBM().equals(JBSyncConstant.JGLBM_SJYT)) {
//			unit = baseUnitDao.getBaseUnitByUnionId("33");
//		}else {
//			unit = baseUnitDao.getBaseUnit(get32Id(jgjbxx.getDQBH()));
//		}
//		if(unit == null) {
//			throw new SyncException("未找到该学校隶属或归口管理机构！");
//		}
//		baseUnit.setParentid(unit.getId());
//		baseUnit.setRegionlevel(Unit.UNIT_REGION_SCHOOL);
//		if(!entity.getXXYXBS().equals(JBSyncConstant.ZF_JGYXBS_ZC)) {
//			baseSchool.setIsdeleted(true);
//			baseUnit.setIsdeleted(true);
//		}
		//更新只更新名称
		baseSchool.setName(entity.getXXMC());
		baseUnit.setName(entity.getXXMC());
		baseSchoolDao.updateSchool(baseSchool);
		baseUnitDao.updateUnit(baseUnit);
	}

	@Override
	List<XXJBXXB> getData() {
		List<XXJBXXB> list = syncXXJBXXBLogDao.getListByResults(new String[] {
				JBSyncConstant.SYNC_RESULT_INIT, JBSyncConstant.SYNC_RESULT_FAIL});
		return list;
	}
	
	@Override
	void updateData(XXJBXXB entity) {
		syncXXJBXXBLogDao.update(entity);
	}

	public void setxXJBXXBDao(XXJBXXBDao xXJBXXBDao) {
		this.xXJBXXBDao = xXJBXXBDao;
	}

	public void setSyncXXJBXXBLogDao(SyncXXJBXXBLogDao syncXXJBXXBLogDao) {
		this.syncXXJBXXBLogDao = syncXXJBXXBLogDao;
	}

	public void setBaseSchoolDao(BaseSchoolDao baseSchoolDao) {
		this.baseSchoolDao = baseSchoolDao;
	}

	public void setBaseDeptDao(BaseDeptDao baseDeptDao) {
		this.baseDeptDao = baseDeptDao;
	}

	public void setRoleDao(RoleDao roleDao) {
		this.roleDao = roleDao;
	}

	public void setRolePermDao(RolePermDao rolePermDao) {
		this.rolePermDao = rolePermDao;
	}

	public void setBaseUnitDao(BaseUnitDao baseUnitDao) {
		this.baseUnitDao = baseUnitDao;
	}

	public void setSchoolService(SchoolService schoolService) {
		this.schoolService = schoolService;
	}

	public void setjGJBXXDao(JGJBXXDao jGJBXXDao) {
		this.jGJBXXDao = jGJBXXDao;
	}
}
