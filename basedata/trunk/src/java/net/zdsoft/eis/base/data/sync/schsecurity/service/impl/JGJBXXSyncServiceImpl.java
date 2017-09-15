package net.zdsoft.eis.base.data.sync.schsecurity.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import net.zdsoft.eis.base.common.entity.Dept;
import net.zdsoft.eis.base.common.entity.Region;
import net.zdsoft.eis.base.common.entity.Role;
import net.zdsoft.eis.base.common.entity.Unit;
import net.zdsoft.eis.base.common.service.RegionService;
import net.zdsoft.eis.base.constant.BaseConstant;
import net.zdsoft.eis.base.data.dao.BaseDeptDao;
import net.zdsoft.eis.base.data.dao.BaseUnitDao;
import net.zdsoft.eis.base.data.entity.BaseUnit;
import net.zdsoft.eis.base.data.sync.schsecurity.constant.JBSyncConstant;
import net.zdsoft.eis.base.data.sync.schsecurity.dao.JGJBXXDao;
import net.zdsoft.eis.base.data.sync.schsecurity.dao.SyncJGJBXXLogDao;
import net.zdsoft.eis.base.data.sync.schsecurity.entity.JGJBXX;
import net.zdsoft.eis.base.data.sync.schsecurity.exception.SyncException;
import net.zdsoft.eis.system.data.dao.RoleDao;
import net.zdsoft.eis.system.data.dao.RolePermDao;
import net.zdsoft.eis.system.data.entity.RolePerm;



public class JGJBXXSyncServiceImpl extends AbstractSyncJBServiceImpl<JGJBXX> {

	private JGJBXXDao jGJBXXDao;
	private SyncJGJBXXLogDao syncJGJBXXLogDao;
	private BaseUnitDao baseUnitDao;
	private BaseDeptDao baseDeptDao;
	private RoleDao roleDao;
	private RolePermDao rolePermDao;
	private RegionService regionService;
	
	@Override
	String getLastGxsj() {
		return jGJBXXDao.getLastGxsj();
	}
	
	@Override
	String getTimeCode() {
		return JBSyncConstant.JB_SYNC_TIME_UNIT;
	}
	
	@Override
	void saveDatas(Date beginTime) {
		if(beginTime == null) {
			throw new RuntimeException("未获取上一次同步机构表的时间");
		}
		int version = syncJGJBXXLogDao.getLastVersion();
		List<JGJBXX> list = jGJBXXDao.getListByGxsj(beginTime);
		
		int success = 0;
		int error = 0;
		for(JGJBXX entity : list) {
			entity.setSyncTime(new Date());
			entity.setSyncResult(JBSyncConstant.SYNC_RESULT_INIT);
			entity.setSyncVersion(version+1);
//			Unit unit = baseUnitDao.getBaseUnitByUnionId(entity.getJGBSM());
			//Unit unit = baseUnitDao.getBaseUnit(get32Id(entity.getJGID().substring(0,6)));
			
			Unit unit = baseUnitDao.getBaseUnit(get32Id(entity.getDQBH()));
			//教育厅特殊处理
			if(JBSyncConstant.JGLBM_SJYT.equals(entity.getJGLBM())){
				unit = baseUnitDao.getBaseUnitByUnionId("33");
			}
			//新增数据状态不正常的不同步
			if((unit == null && !JBSyncConstant.ZF_JGYXBS_ZC.equals(entity.getJGYXBS())) || JBSyncConstant.JGLBM_SJYT.equals(entity.getJGLBM())) {
				LOG.error("[同步保存机构表，"+entity.getJGMC()+"同步保存失败，log数据不正常!]");
				error ++;
				continue;
			}
			if(StringUtils.isBlank(entity.getDQBH())){
				LOG.error("[同步保存机构表，"+entity.getJGMC()+"同步保存失败，DQBH为空!]");
				error ++;
				continue;
			}
			//entity.setDQBH(entity.getJGID().substring(0,6));
			entity.setSJBHLX(unit == null ? JBSyncConstant.SJBHLX_INSERT : JBSyncConstant.SJBHLX_UPDATE);
			//关闭以前版本中相同id的失败记录
			syncJGJBXXLogDao.closeFailRecord(entity.getJGID());
			syncJGJBXXLogDao.save(entity);
			success ++;
		}
		LOG.error("[同步机构共："+CollectionUtils.size(list)+"条，成功："+success+"条，失败："+error+"条]");
	}
	
	@Override
	void insert(JGJBXX entity) {
		if(JBSyncConstant.ZF_JGYXBS_ZC.equals(entity.getJGYXBS())) {
			//创建单位
			BaseUnit baseUnit = new BaseUnit();
			baseUnit.setId(get32Id(entity.getDQBH()));
			Region region = regionService.getRegionByFullCode(entity.getDQBH());
			if(region == null) {
				throw new SyncException("未找到该机构对应的行政区划！");
			}
			baseUnit.setUnionid(region.getRegionCode());
			baseUnit.setName(entity.getJGMC());
			baseUnit.setUnitclass(Unit.UNIT_CLASS_EDU);
			baseUnit.setRegion(entity.getDQBH());
			baseUnit.setOrderid((long)entity.getXSSH());
			if(JBSyncConstant.JGLBM_SJYT.equals(entity.getJGLBM())) {
				baseUnit.setUnittype(Unit.UNIT_EDU_TOP);
				baseUnit.setParentid(BaseConstant.ZERO_GUID);
				baseUnit.setRegionlevel(Unit.UNIT_REGION_PROVINCE);
				baseUnit.setTeacherEnableSms(Unit.TEACHER_CANNOT_SEND);
				baseUnit.setGuestbookSms(0);
				baseUnit.setFeeType(0);
			}else {
				baseUnit.setUnittype(Unit.UNIT_EDU_SUB);
				JGJBXX jgjbxx = jGJBXXDao.getById(entity.getLSJGM());
				if(jgjbxx == null){
					LOG.error("["+entity.getJGMC()+"没有找到所属机构，所属机构码："+entity.getLSJGM()+"]");
					throw new SyncException("未找到该机构隶属或归口管理机构[zfim表]！");
				}
//				Unit unit = baseUnitDao.getBaseUnitByUnionId(jgjbxx.getJGBSM());
//				Unit unit = baseUnitDao.getBaseUnit(get32Id(jgjbxx.getJGBSM()));
				Unit unit = null;
				if(JBSyncConstant.JGLBM_SJYT.equals(jgjbxx.getJGLBM())) {
					unit = baseUnitDao.getBaseUnitByUnionId("33");
				}else {
					unit = baseUnitDao.getBaseUnit(get32Id(jgjbxx.getDQBH()));
				}
				if(unit == null) {
					throw new SyncException("未找到该机构隶属或归口管理机构！");
				}
				baseUnit.setParentid(unit.getId());
				if(JBSyncConstant.JGLBM_SJYJ.equals(entity.getJGLBM())) {
					baseUnit.setRegionlevel(Unit.UNIT_REGION_CITY);
				}else if(JBSyncConstant.JGLBM_XJYJ.equals(entity.getJGLBM())) {
					baseUnit.setRegionlevel(Unit.UNIT_REGION_COUNTY);
				}else {
					throw new SyncException("该机构类别码未找到对应的单位行政等级！");
				}
				baseUnit.setTeacherEnableSms(Unit.TEACHER_CAN_SEND);
				baseUnit.setGuestbookSms(1);
				baseUnit.setFeeType(1);
			}
			//创建单位管理部门
			Dept dept = new Dept();
			dept.setUnitId(baseUnit.getId());
			dept.setDeptCode(baseUnit.getRegion());
			dept.setDeptname("教育局管理组");
			dept.setParentid(BaseConstant.ZERO_GUID);
			dept.setJymark(1);
			dept.setInstituteId(BaseConstant.ZERO_GUID);
			//创建校安管理员角色
			Role role = new Role();
			role.setIdentifier(JBSyncConstant.ROLE_IDENTITY);
			role.setUnitid(baseUnit.getId());
			role.setName("校安管理员");
			role.setDescription("负责校园安全管理");
			String[] subsystemids = null;
			if(entity.getJGLBM().equals(JBSyncConstant.JGLBM_SJYT)) {
				role.setSubsystem("13,97,98");
				subsystemids = new String[] {"13","97","98"};
			}else {
				role.setSubsystem("13,97");
				subsystemids = new String[] {"13","97"};
			}
			role.setIsactive(true);
			roleDao.insertRole(role);
			Role unitRole = roleDao.getRoleByName(baseUnit.getId(), "校安管理员");
			List<RolePerm> permList = new ArrayList<RolePerm>();
			List<String> moduleIdList = getModuleIdsBySubsystemid(subsystemids, Unit.UNIT_CLASS_EDU);
			for(String moduleId : moduleIdList) {
				RolePerm rolePerm = new RolePerm();
				rolePerm.setModuleid(Long.parseLong(moduleId));
				rolePerm.setRoleid(unitRole.getId());
				permList.add(rolePerm);
			}
			rolePermDao.saveRolePerms(permList);
			baseDeptDao.insertDept(dept);
			baseUnitDao.insertUnit(baseUnit);
		}
	}

	@Override
	void update(JGJBXX entity) {
		BaseUnit baseUnit = baseUnitDao.getBaseUnit(get32Id(entity.getDQBH()));
//		if(baseUnit == null) {
//			throw new SyncException("未找到该机构！");
//		}
//		//if region_code change
//		baseUnit.setUnionid();
//		baseUnit.setName(entity.getJGMC());
//		baseUnit.setUnitclass(Unit.UNIT_CLASS_EDU);
//		baseUnit.setRegion(entity.getDQBH());
//		baseUnit.setOrderid(entity.getXSSH()+"");
//		if(entity.getJGLBM().equals(JBSyncConstant.JGLBM_SJYT)) {
//			baseUnit.setUnittype(Unit.UNIT_EDU_TOP);
//			baseUnit.setParentid(BaseConstant.ZERO_GUID);
//			baseUnit.setRegionlevel(Unit.UNIT_REGION_PROVINCE);
//			baseUnit.setTeacherEnableSms(Unit.TEACHER_CANNOT_SEND);
//			baseUnit.setGuestbookSms(0);
//			baseUnit.setFeeType(0);
//		}else {
//			baseUnit.setUnittype(Unit.UNIT_EDU_SUB);
//		    JGJBXX jgjbxx = jGJBXXDao.getById(entity.getLSJGM());
////			Unit unit = baseUnitDao.getBaseUnitByUnionId(jgjbxx.getJGBSM());
////			Unit unit = baseUnitDao.getBaseUnit(get32Id(jgjbxx.getJGBSM()));
//			Unit unit = null;
//			if(jgjbxx.getJGLBM().equals(JBSyncConstant.JGLBM_SJYT)) {
//				unit = baseUnitDao.getBaseUnitByUnionId("33");
//			}else {
//				unit = baseUnitDao.getBaseUnit(get32Id(jgjbxx.getDQBH()));
//			}
//			if(unit == null) {
//				throw new SyncException("未找到该机构隶属或归口管理机构！");
//			}
//			baseUnit.setParentid(unit.getId());
//			if(entity.getJGLBM().equals(JBSyncConstant.JGLBM_SJYJ)) {
//				baseUnit.setRegionlevel(Unit.UNIT_REGION_CITY);
//			}else if(entity.getJGLBM().equals(JBSyncConstant.JGLBM_XJYJ)) {
//				baseUnit.setRegionlevel(Unit.UNIT_REGION_COUNTY);
//			}else {
//				throw new SyncException("该机构类别码未找到对应的单位行政等级！");
//			}
//			baseUnit.setTeacherEnableSms(Unit.TEACHER_CAN_SEND);
//			baseUnit.setGuestbookSms(1);
//			baseUnit.setFeeType(1);
//		}
//		if(!entity.getJGYXBS().equals(JBSyncConstant.ZF_JGYXBS_ZC)) {
//			baseUnit.setIsdeleted(true);
//		}
		//更新只更新名字
		baseUnit.setName(entity.getJGMC());
		baseUnitDao.updateUnit(baseUnit);
	}

	@Override
	List<JGJBXX> getData() {
		List<JGJBXX> list = syncJGJBXXLogDao.getListByResults(new String[] {
				JBSyncConstant.SYNC_RESULT_INIT, JBSyncConstant.SYNC_RESULT_FAIL});
		return list;
	}
	
	@Override
	void updateData(JGJBXX entity) {
		syncJGJBXXLogDao.update(entity);
	}

	public void setjGJBXXDao(JGJBXXDao jGJBXXDao) {
		this.jGJBXXDao = jGJBXXDao;
	}

	public void setSyncJGJBXXLogDao(SyncJGJBXXLogDao syncJGJBXXLogDao) {
		this.syncJGJBXXLogDao = syncJGJBXXLogDao;
	}

	public void setBaseUnitDao(BaseUnitDao baseUnitDao) {
		this.baseUnitDao = baseUnitDao;
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

	public void setRegionService(RegionService regionService) {
		this.regionService = regionService;
	}
}
