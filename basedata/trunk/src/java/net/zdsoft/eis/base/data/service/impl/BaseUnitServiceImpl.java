/* 
 * @(#)BaseUnitServiceImpl.java    Created on Nov 17, 2009
 * Copyright (c) 2009 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.eis.base.data.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.zdsoft.eis.base.common.dao.SchoolDao;
import net.zdsoft.eis.base.common.entity.Dept;
import net.zdsoft.eis.base.common.entity.EduInfo;
import net.zdsoft.eis.base.common.entity.Mcodedetail;
import net.zdsoft.eis.base.common.entity.Region;
import net.zdsoft.eis.base.common.entity.SystemVersion;
import net.zdsoft.eis.base.common.entity.Unit;
import net.zdsoft.eis.base.common.entity.User;
import net.zdsoft.eis.base.common.service.McodedetailService;
import net.zdsoft.eis.base.common.service.RegionService;
import net.zdsoft.eis.base.common.service.SystemIniService;
import net.zdsoft.eis.base.common.service.UnitIniService;
import net.zdsoft.eis.base.common.service.impl.UnitServiceImpl;
import net.zdsoft.eis.base.constant.BaseConstant;
import net.zdsoft.eis.base.data.BasedataConstants;
import net.zdsoft.eis.base.data.dao.BaseUnitDao;
import net.zdsoft.eis.base.data.dao.BaseUserDao;
import net.zdsoft.eis.base.data.entity.BaseSchool;
import net.zdsoft.eis.base.data.entity.BaseTeacher;
import net.zdsoft.eis.base.data.entity.BaseUnit;
import net.zdsoft.eis.base.data.service.BaseDeptService;
import net.zdsoft.eis.base.data.service.BaseEduInfoService;
import net.zdsoft.eis.base.data.service.BaseSchoolService;
import net.zdsoft.eis.base.data.service.BaseTeacherService;
import net.zdsoft.eis.base.data.service.BaseUnitService;
import net.zdsoft.eis.base.data.service.BaseUserService;
import net.zdsoft.eis.base.data.service.PassportAccountService;
import net.zdsoft.eis.base.deploy.SystemDeployService;
import net.zdsoft.eis.base.subsystemcall.service.SystemSubsystemService;
import net.zdsoft.eis.base.sync.EventSourceType;
import net.zdsoft.eis.base.util.SystemLog;
import net.zdsoft.eis.frame.service.DeleteXmlDataService;
import net.zdsoft.eis.system.data.service.RoleService;
import net.zdsoft.keel.util.Pagination;
import net.zdsoft.keel.util.SpellUtils;
import net.zdsoft.keel.util.Validators;
import net.zdsoft.leadin.exception.ItemExistsException;
import net.zdsoft.leadin.tree.WebCheckBoxTreeNode;
import net.zdsoft.leadin.tree.WebTree;
import net.zdsoft.leadin.tree.WebTreeNode;
import net.zdsoft.leadin.util.UUIDGenerator;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: Nov 17, 2009 4:17:34 PM $
 */
public class BaseUnitServiceImpl extends UnitServiceImpl implements
		BaseUnitService {
	private static Logger log = LoggerFactory
			.getLogger(BaseUnitServiceImpl.class);

	private static final String UNIT_MODID = "SYS002";// 单位管理

	private static final int FINAL_LENGTH = 12;// 学校Unionid长度
	private static final int FINAL_EDU_LENGHT = 6;// 三级教育局unionid最大长度
	private static final int UNIONID_LENGTH_EDU4 = 3;// 第四级教育局Unionid新增部分长度
	private static final int SCHOOLE_UNION = 3;// 学校只使用学籍号最末3位
	private static final String NONEDU_UNIT_UNION_START = "A";
	private static final int UNIONID_ADDITION = 2;// unionid相对于上级单位的增量长度
	private static final int UNIONID_ADDITION_EDU4 = 3;// unionid相对于上级单位的增量长度(对于乡镇级别教育局的特殊情况)
	private static final String UNIT_TYPE_MCODE_ID = "DM-DWLX";// 单位类型微代码

	private BaseUnitDao baseUnitDao;
	private UnitIniService unitIniService;
	private SystemIniService systemIniService;
	private BaseDeptService baseDeptService;
	private BaseTeacherService baseTeacherService;
	private BaseSchoolService baseSchoolService;
	private McodedetailService mcodedetailService;
	private BaseEduInfoService baseEduInfoService;
	private RoleService roleService;
	private BaseUserService baseUserService;
	private DeleteXmlDataService deleteXmlDataService;
	private PassportAccountService passportAccountService;
	private BaseUserDao baseUserDao;
	private RegionService regionService;
	private SystemDeployService systemDeployService;
    private SystemSubsystemService systemSubsystemService;
    private SchoolDao schoolDao;
	
	public void setSystemDeployService(SystemDeployService systemDeployService) {
        this.systemDeployService = systemDeployService;
    }

    public void setBaseUnitDao(BaseUnitDao baseUnitDao) {
		this.baseUnitDao = baseUnitDao;
	}

	public void setBaseUserService(BaseUserService baseUserService) {
		this.baseUserService = baseUserService;
	}

	public void setBaseSchoolService(BaseSchoolService baseSchoolService) {
		this.baseSchoolService = baseSchoolService;
	}

	public void setUnitIniService(UnitIniService unitIniService) {
		this.unitIniService = unitIniService;
	}
	public void setBaseUserDao(BaseUserDao baseUserDao) {
		this.baseUserDao = baseUserDao;
	}

	/**
	 * 设置systemIniService
	 * @param systemIniService systemIniService
	 */
	public void setSystemIniService(SystemIniService systemIniService) {
	    this.systemIniService = systemIniService;
	}

	public void setBaseDeptService(BaseDeptService baseDeptService) {
		this.baseDeptService = baseDeptService;
	}

	public void setBaseTeacherService(BaseTeacherService baseTeacherService) {
		this.baseTeacherService = baseTeacherService;
	}

	public void setMcodedetailService(McodedetailService mcodedetailService) {
		this.mcodedetailService = mcodedetailService;
	}

	public void setBaseEduInfoService(BaseEduInfoService baseEduInfoService) {
		this.baseEduInfoService = baseEduInfoService;
	}

	public void setRoleService(RoleService roleService) {
		this.roleService = roleService;
	}

	public void setDeleteXmlDataService(
			DeleteXmlDataService deleteXmlDataService) {
		this.deleteXmlDataService = deleteXmlDataService;
	}

	public void setPassportAccountService(
			PassportAccountService passportAccountService) {
		this.passportAccountService = passportAccountService;
	}
	public void setSchoolDao(SchoolDao schoolDao) {
		this.schoolDao = schoolDao;
	}

	// =======================以上为set方法==========================

	public void setRegionService(RegionService regionService) {
		this.regionService = regionService;
	}

	public void insertUnit(BaseUnit unit) {
        unit.setLimitTeacher(systemSubsystemService.getUserCountLimit());
		baseUnitDao.insertUnit(unit);
		updateUnitCache(null, unit.getParentid(), unit);
	}

	public void addUnitFromMq(BaseUnit unit) {
		// 设置默认字段
		createUnionidFromMq(unit);
		unit.setMark(BaseUnit.UNIT_MARK_NORAML);
		unit.setUsetype(BaseUnit.UNIT_USETYPE_LOCAL);
		unit.setAuthorized(BaseUnit.UNIT_AUTHORIZED);
		unit.setRegcode(BaseUnit.UNIT_REGCODE_DEF);
		if (unit.getUnitclass() == 1)
			unit.setUnitusetype("01");
		else
			unit.setUnitusetype("11");

		baseUnitDao.insertUnit(unit);
		if (unit.getUnitclass() == 1)
			saveEduInfo(unit);
		unitIniService.initUnitOption(unit.getId());// 新增单位设置信息
		roleService.initUnitRoles(unit.getId(), unit.getUnitclass());// 初始化单位角色
		updateUnitCache(null, unit.getParentid(), unit);
	}
	
	
	public void registerTopUnitFromMq(BaseUnit unit,User user) throws Exception {        
	    // 设置默认字段
        createUnionidFromMq(unit);
        unit.setMark(BaseUnit.UNIT_MARK_NORAML);
        unit.setUsetype(BaseUnit.UNIT_USETYPE_LOCAL);
        unit.setAuthorized(BaseUnit.UNIT_AUTHORIZED);
        unit.setRegcode(BaseUnit.UNIT_REGCODE_DEF);
        if (unit.getUnitclass() == 1) {
            unit.setUnitusetype("01");// 教育局
        } else {
            if (SystemVersion.PRODUCT_EIS.equals(systemDeployService.getProductId())) {
                unit.setUnitusetype("11");// 中学
            } else if (SystemVersion.PRODUCT_EISU.equals(systemDeployService.getProductId())) {
                unit.setUnitusetype("41");// 中等职业学校
            } else {
                unit.setUnitusetype("11");// 中学
            }
        }

        user.setOwnerType(User.TEACHER_LOGIN);
        saveUnit(unit, user);
	}
	
	private void saveEduInfo(BaseUnit unit){
		EduInfo eduInfo = new EduInfo();
		eduInfo.setId(unit.getId());
		eduInfo.setIsAutonomy(false);
		eduInfo.setIsFrontier(false);
		if (StringUtils.isEmpty(unit.getUnionid())) {
            log.error("单位的unionCode为空：unitId={}", unit.getId());
            eduInfo.setEduCode("");
        } else {
            eduInfo.setEduCode(unit.getUnionid());
        }
		eduInfo.setCreationTime(new Date());
		eduInfo.setModifyTime(new Date());
		eduInfo.setIsdeleted(false);
		eduInfo.setHomepage(unit.getHomepage());
		baseEduInfoService.addEduInfo(eduInfo);
	}

	public void updateUnit(BaseUnit unit) {
		baseUnitDao.updateUnit(unit);
		updateUnitCache(unit.getSrcParentId(), unit.getParentid(), unit);
	}
	
	public void updateUnitFromMq(BaseUnit unit) {
	  //如果上级单位发生改变，则修改unionId
        Unit preUnit = getUnit(unit.getId());
        if (null != preUnit) {
            if (StringUtils.isEmpty(preUnit.getParentid())
                    || !(preUnit.getParentid().equals(unit.getParentid()))) {
                createUnionidFromMq(unit);
                unit.setSrcParentId(preUnit.getParentid());
            }
        }        
        updateUnit(unit);
        
	    if(Unit.UNIT_CLASS_EDU == unit.getUnitclass()){
    		EduInfo eduInfo =baseEduInfoService.getEduInfo(unit.getId());
    		if(null == eduInfo){
    		    saveEduInfo(unit);
    		}else{
    		    eduInfo.setHomepage(unit.getHomepage());
    		    baseEduInfoService.updateEduInfo(eduInfo);
    		}
	    }
		
	}

	private String unitValidate(BaseUnit unitDto) {
		if (org.apache.commons.lang.StringUtils.isBlank(unitDto.getRegion())) {
			return "请选择单位所在的行政区划";
		}
		// 单位名称必输与长度验证
		if (org.apache.commons.lang.StringUtils.isBlank(unitDto.getName())) {
			return "请输入单位名称!";
		}
		if (net.zdsoft.keel.util.StringUtils.getRealLength(unitDto.getName()) > BaseUnit.NAME_LENGTH) {
			return "请确认单位名称不超过" + BaseUnit.NAME_LENGTH + "个字符";
		}
		// 单位类型验证
		List<Mcodedetail> list = mcodedetailService
				.getMcodeDetails(UNIT_TYPE_MCODE_ID);
		Mcodedetail mcodedetailDto;
		boolean typeExists = false;
		for (int i = 0; i < list.size(); i++) {
			mcodedetailDto = list.get(i);
			if (mcodedetailDto.getThisId().equals(
					String.valueOf(unitDto.getUnittype()))) {
				typeExists = true;
				break;
			}
		}
		if (!typeExists) {
			return "该单位类型暂时不可用！";
		}
		// 上级单位的可用性验证
		if (Validators.isEmpty(unitDto.getParentid())) {
			return "请选择上级单位";
		}
		if (!unitDto.getParentid().equals(Unit.TOP_UNIT_GUID)) {
			if (!checkUnitRight(unitDto.getParentid())) {
				return "该上级单位暂时不可用！";
			}
			// 上级单位类型验证
			Unit parentUnit = getUnit(unitDto.getParentid());
			if (parentUnit.getUnitclass() != Unit.UNIT_CLASS_EDU) {
				return "该学校不能新增下级单位";
			}
		}
		return null;
	}

	private String userValidate(User userDto, boolean isNewUser) {
		// 用户登录名验证
		if (Validators.isEmpty(userDto.getName())) {
			return "账号不能为空！";
		} else if (!userDto.getName().matches(systemIniService.getValue(User.SYSTEM_NAME_EXPRESSION))) {
			return systemIniService.getValue(User.SYSTEM_NAME_ALERT);
		}
		if (net.zdsoft.keel.util.StringUtils.getRealLength(userDto.getName()) > BaseUnit.NAME_LENGTH) {
			return systemIniService.getValue(User.SYSTEM_NAME_ALERT);
		}

		// 有密码,且新增用户或修改用户且密码更改的，需要密码验证
		if (org.apache.commons.lang.StringUtils.isBlank(userDto.getPassword())) {
			return "用户密码不能为空！";
		}
		// 用户电子邮件的正确验证
		if (!Validators.isEmpty(userDto.getEmail())) {
			if (!Validators.isEmail(userDto.getEmail())) {
				return "请确认电子邮件地址填写正确！";
			}
		}
		return null;
	}

	private String takeRegion(String unionId) {
		String region = unionId;
		if (region != null && !"".equals(region)) {
			if (region.length() >= 6) {
				region = region.substring(0, 6);
			} else {
				for (int i = region.length(); i < 6; i++) {
					region = region + "0";
				}
			}
		}
		return region;
	}
	
	public void saveUnitWithoutUser(BaseUnit unitDto) throws Exception {
		_saveUnit(unitDto, null, false);
	}
	
	public void saveUnit(BaseUnit unitDto, User userDto) throws Exception {
		_saveUnit(unitDto, userDto, true);
	}
	
	public void saveAdminUser(String unitid , User userDto) throws Exception {
		String error = userValidate(userDto, true);
		if (null != error) {
			throw new Exception(error);
		}
		
		if (baseUserService.getUserNameCount(userDto.getName()) > 0) {
			throw new ItemExistsException("该账号已存在！", "userDto.name");
		}

		if (passportAccountService.queryAccountByUsername(userDto.getName()) != null) {
			throw new ItemExistsException("Passport中该账号已存在！", "userDto.name");
		}

		int minLimit = 4;
		int maxLimit = 20;
		if (net.zdsoft.keel.util.StringUtils.getRealLength(userDto.getName()) > maxLimit
				|| net.zdsoft.keel.util.StringUtils.getRealLength(userDto.getName()) < minLimit
				|| !userDto.getName().matches("[\u4e00-\u9fa5a-zA-Z0-9_]+")) {
			throw new ItemExistsException("username", "必须是" + minLimit + "-" + maxLimit
					+ "个字符(包括大小写字母、数字、下划线)。");
		}
		BaseUnit unitDto = this.getBaseUnit(unitid);
		userDto.setRealname("管理员");// 单位管理员的真是名称即单位名称
		userDto.setCreationTime(new Date());
		userDto.setUnitid(unitDto.getId());

		BaseTeacher teacher = new BaseTeacher();
		// String tchId = teacherDao.getAvaTchid(unit.getId());
		String tchId = "000000";
		String deptName ="";
		
		if (unitDto.getUnitclass().intValue() == Unit.UNIT_CLASS_SCHOOL) {
			deptName = Dept.SCH_ADMIN_GROUP_NAME;
		} else {
			deptName = Dept.EDU_ADMIN_GROUP_NAME;
		}

		Dept dept = baseDeptService.getDept(unitid, deptName);
		teacher.setTchId(tchId);
		teacher.setName(userDto.getRealname());
		teacher.setSex(BaseConstant.SEX_MALE);
		teacher.setEusing(BasedataConstants.EMPLOYEE_INCUMBENCY);
		teacher.setDeptid(dept.getId()); 
		teacher.setUnitid(unitDto.getId());
		teacher.setCardNumber("");
		teacher.setCreationTime(new Date());
		teacher.setDisplayOrder(1);
		
		baseTeacherService.insertTeacher(teacher);
		
		String teacherId = teacher.getId();
		userDto.setTeacherid(teacherId);
		baseUserService.saveUserByOtherObject(userDto, false);
	}

	private void _saveUnit(BaseUnit unitDto, User userDto,boolean needUser) throws Exception {
		String error = unitValidate(unitDto);
		if (null != error) {
			throw new Exception(error);
		}
		
		if (needUser ) {
			error = userValidate(userDto, true);
			if (null != error) {
				throw new Exception(error);
			}
		}

		// 如果是非教育局单位，则不需要验证unionid的唯一性
		if (unitDto.getUnittype() != Unit.UNIT_NOTEDU_NOTSCH) {
			// 单位unionid唯一性的验证，若没有则根据所选上级单位生成
			if (org.apache.commons.lang.StringUtils.isBlank(unitDto
					.getUnionid())) {
				String unionid = createUnionid(unitDto.getParentid(), unitDto
						.getUnitclass());
				unitDto.setUnionid(unionid);
			} else {
				if (getUnitByUnionId(unitDto.getUnionid()) != null) {
					int length = unitDto.getUnionid().length();
					if (length == 2 || length == 4 || length == 6) {
						log.error("[运营平台]" + "该所在行政区域已存在相应单位!");
						throw new Exception("该所在行政区域已存在相应单位!");
					}
				}
			}
		} else {
			// 对于非教育局单位的新增
			String unionid = createSpecialUnionid(unitDto.getParentid());
			unitDto.setUnionid(unionid);
			unitDto.setUnitclass(Unit.UNIT_CLASS_EDU);
			unitDto.setUnittype(Unit.UNIT_NOTEDU_NOTSCH);
		}
//		unitDto.setOrderid(unitDto.getUnionid());
		if(org.apache.commons.lang3.StringUtils.isNotBlank(unitDto.getUnionid()))
			unitDto.setOrderid(NumberUtils.toLong(unitDto.getUnionid().replaceAll("[^0-9]", "")));
		if (needUser ) {
			userDto.setRealname("管理员");// 单位管理员的真是名称即单位名称
		}
		String unitId = _saveUnitWithUser(unitDto, userDto,needUser);
		// userService.updateUserUnitintidByUserName(userDto.getName());

		if (org.apache.commons.lang.StringUtils.isBlank(unitId)) {
			return;
		}

		// 保存单位的同时,保存学校,如果新增的是学校的话
		if (unitDto.getUnitclass().equals(Unit.UNIT_CLASS_SCHOOL)) {
			BaseSchool basicSchoolinfoDto = baseSchoolService
					.getBaseSchool(unitId);
			if (null == basicSchoolinfoDto || org.apache.commons.lang.StringUtils.isBlank(basicSchoolinfoDto
					.getId())) {
				basicSchoolinfoDto = new BaseSchool();
				basicSchoolinfoDto.setId(unitId);
				String code = unitDto.getUnionid().substring(0, 6)
						+ unitDto.getUnionid().substring(6, 12);
				String schId = baseSchoolService.getSchoolIdByCode(code);
				if (org.apache.commons.lang.StringUtils.isBlank(schId))
					basicSchoolinfoDto.setCode(code);
				basicSchoolinfoDto.setEtohSchoolId(unitDto.getEtohSchoolId());
				basicSchoolinfoDto.setName(unitDto.getName());
				basicSchoolinfoDto.setRegion(unitDto.getRegion());
				basicSchoolinfoDto.setCreationTime(new Date());
				basicSchoolinfoDto.setAddress(unitDto.getAddress());
				basicSchoolinfoDto.setPostalcode(unitDto.getPostalcode());
				basicSchoolinfoDto.setLinkphone(unitDto.getLinkPhone());
				basicSchoolinfoDto.setFax(unitDto.getFax());
				basicSchoolinfoDto.setEmail(unitDto.getEmail());
				basicSchoolinfoDto.setHomepage(unitDto.getHomepage());
				baseSchoolService.addSchool(basicSchoolinfoDto);
			}
		} else if (unitDto.getUnitclass().equals(Unit.UNIT_CLASS_EDU)) {

			EduInfo edu = baseEduInfoService.getBaseEduInfo(unitId);
			if (edu == null) {
				EduInfo eduInfo = new EduInfo();
				eduInfo.setId(unitId);
				eduInfo.setIsAutonomy(false);
				eduInfo.setIsFrontier(false);
				eduInfo.setEduCode(unitDto.getUnionid());
				eduInfo.setCreationTime(new Date());
				eduInfo.setModifyTime(new Date());
				eduInfo.setIsdeleted(false);
				baseEduInfoService.addEduInfo(eduInfo);
			}
		}
	}

	public boolean checkUnitRight(String unitId) {
		Unit unit = getUnit(unitId);
		if (unit == null) {
			return false;
		}
		if (unit.getMark()!=null && unit.getMark() == Unit.UNIT_MARK_NORAML) {
			return true;
		} else {
			return false;
		}
	}

	public String createUnionid(String parentid, Integer unitClass) {
		StringBuffer sb = new StringBuffer();
		if (parentid == null) {
			return "";
		} else if (parentid.equals(Unit.TOP_UNIT_GUID)) {
			return "";
		}
		Unit unit = getUnit(parentid);
		String unionid = unit.getUnionid();

		if (unitClass == Unit.UNIT_CLASS_EDU) {
			// 对于第四级教育局(即乡镇级教育局)的unionid生成
			if (unionid.trim().length() == FINAL_EDU_LENGHT) {
				String eduunionid = baseUnitDao.getAvaUnionIdExceptType(
						parentid, Unit.UNIT_CLASS_EDU, Unit.UNIT_NOTEDU_NOTSCH);
				if (eduunionid == null) {
					sb.append(unionid);
					for (int i = 0; i < UNIONID_LENGTH_EDU4; i++) {
						if (i == UNIONID_LENGTH_EDU4 - 1) {
							sb.append("1");
						} else {
							sb.append("0");
						}
					}
				} else {
					String headAvaunionid = eduunionid.substring(0, unionid.length() );
					sb.append(headAvaunionid);
					long aval = Long.valueOf(eduunionid.substring(unionid.length()));
					aval++;
					String tail = String.valueOf(aval);
					tail = StringUtils.leftPad(tail, UNIONID_LENGTH_EDU4, "0");
					sb.append(tail);
				}
				return sb.toString();
			}
		}

		if (unitClass == Unit.UNIT_CLASS_SCHOOL) {
			// 学校单位的统一编号生成方式
			String avaunionid = baseUnitDao.getAvaUnionId(parentid,
					Unit.UNIT_CLASS_SCHOOL, 12);
			if (avaunionid == null) {
				sb.append(unionid);
				sb.append(net.zdsoft.keel.util.StringUtils.createCountsSymbol(
						"0", FINAL_LENGTH - unionid.length() - SCHOOLE_UNION));
				for (int i = 0; i < SCHOOLE_UNION; i++) {
					if (i == SCHOOLE_UNION - 1) {
						sb.append("1");
						break;
					}
					sb.append("0");
				}
			} else {
				String headAvaunionid = avaunionid.substring(0, unionid.length() );
				sb.append(headAvaunionid);
				long aval = Long.valueOf(avaunionid.substring(unionid.length()));
				aval++;
				String tail = String.valueOf(aval);
				tail = StringUtils.leftPad(tail, FINAL_LENGTH - unionid.length(), "0");
				sb.append(tail);
			}
		}
		return sb.toString();
	}

	public String createSpecialUnionid(String parentid) {
		Unit parentUnit = getUnit(parentid);
		if (parentUnit == null) {
			return "";
		}
//		String unionId = baseUnitDao.getAvaUnionId(parentid,Unit.UNIT_CLASS_EDU, 6);
		// 如果unionid为空,或者unionid为纯数字,表示该单位下属中还暂时没有非教育局单位
//		if (unionId == null || org.apache.commons.lang.StringUtils.isNumeric(unionId)) {
//			int symbolTime;
//			// 对于乡镇级的非教育局单位,unionid增量为3位
//			if (parentUnit.getUnionid().length() == FINAL_EDU_LENGHT) {
//				symbolTime = UNIONID_ADDITION_EDU4;
//			} else {
//				symbolTime = UNIONID_ADDITION;
//			}
//			return parentUnit.getUnionid()
//					+ net.zdsoft.keel.util.StringUtils.createCountsSymbol(
//							NONEDU_UNIT_UNION_START, symbolTime);
//		} else {
//			String str = unionId.substring(parentUnit.getUnionid().length(),
//					unionId.length());
//			str = net.zdsoft.keel.util.StringUtils.getNextUpCaseChar(str);
//			return parentUnit.getUnionid() + str;
//		}
		String pUnionId = parentUnit.getUnionid();
		String unionNoMax = baseUnitDao.getAvaUnionId(parentid, pUnionId,
				Unit.UNIT_CLASS_EDU, Unit.getUnionIdLength(parentUnit.getRegionlevel() + 1));
		// 如果unionNoMax为空,或者unionNoMax为纯数字,表示该单位下属中还暂时没有非教育局单位
		if (unionNoMax == null
				|| org.apache.commons.lang.StringUtils.isNumeric(unionNoMax)) {
			int symbolTime;
			// 对于乡镇级的非教育局单位,unionid增量为3位
			if (pUnionId.length() == FINAL_EDU_LENGHT
					|| pUnionId.length() == FINAL_EDU_LENGHT) {
				symbolTime = UNIONID_ADDITION_EDU4;
			} else {
				symbolTime = UNIONID_ADDITION;
			}
			return pUnionId
					+ net.zdsoft.keel.util.StringUtils.createCountsSymbol(
							NONEDU_UNIT_UNION_START, symbolTime);
		} else {
			return pUnionId + net.zdsoft.keel.util.StringUtils.getNextUpCaseChar(unionNoMax);
		}
	}
	
	public void createUnionidFromMq(BaseUnit unit) {
		String parentid = unit.getParentid();
		String regionCode = unit.getRegion();
		if (StringUtils.isEmpty(regionCode)) {
            log.error("单位的行政区划不能为空：unitId={}", unit.getId());
            return;
        }
		Integer unitClass = unit.getUnitclass();
			
		StringBuffer sb = new StringBuffer();
		Unit parentUnit = getUnit(parentid);
		String unionid = "";
		int regionlevel = 0;
				
		if (parentUnit != null) {
			unionid = parentUnit.getUnionid();
			regionlevel = parentUnit.getRegionlevel() + 1;
		} else {
			// 如果上级单位还未同步过来，则取行政区划
		    Region region = regionService.getRegionByFullCode(regionCode);
		    if (null == region) {
                log.error("根据行政区划码找不到行政区划信息：regionCode={}", regionCode);
                return;
            }
			unionid = region.getRegionCode();
			
			// 置reionLevel
			switch (unionid.length()) {
			case 0:
				regionlevel = BaseUnit.UNIT_REGION_COUNTRY;
				break;
			case 2:
				regionlevel = BaseUnit.UNIT_REGION_PROVINCE;
				break;
			case 4:
				regionlevel = BaseUnit.UNIT_REGION_CITY;
				break;
			case 6:
				regionlevel = BaseUnit.UNIT_REGION_COUNTY;
				break;
			case 9:
				regionlevel = BaseUnit.UNIT_REGION_LEVEL;
				break;
			default:
				break;
			}
		
			if (unitClass == Unit.UNIT_CLASS_SCHOOL) {
				regionlevel = regionlevel + 1;
			}
		}

		if (unitClass == Unit.UNIT_CLASS_EDU) {
			// 对于第四级教育局(即乡镇级教育局)的unionid生成
			if (unionid.trim().length() == FINAL_EDU_LENGHT) {
				String eduunionid = baseUnitDao.getAvaUnionIdExceptType(
						parentid, Unit.UNIT_CLASS_EDU, Unit.UNIT_NOTEDU_NOTSCH);
				if (eduunionid == null) {
					sb.append(unionid);
					for (int i = 0; i < UNIONID_LENGTH_EDU4; i++) {
						if (i == UNIONID_LENGTH_EDU4 - 1) {
							sb.append("1");
						} else {
							sb.append("0");
						}
					}
				} else {
					long aval = Long.valueOf(eduunionid);
					aval++;
					sb.append(aval);
				}
				unionid = sb.toString();
			} else {//对于非乡镇教育局，直接取行政区划码中的regionCode
			    Region region = regionService.getRegionByFullCode(regionCode);
	            if (null == region) {
	                log.error("根据行政区划码找不到行政区划信息：regionCode={}", regionCode);
	                return;
	            }
				unionid = region.getRegionCode();				
			}			
		}else if (unitClass == Unit.UNIT_CLASS_SCHOOL) {
			// 学校单位的统一编号生成方式
			String avaunionid = baseUnitDao.getAvaUnionId(parentid,
					Unit.UNIT_CLASS_SCHOOL, 12);
			if (avaunionid == null) {
				sb.append(unionid);
				sb.append(net.zdsoft.keel.util.StringUtils.createCountsSymbol(
						"0", FINAL_LENGTH - unionid.length() - SCHOOLE_UNION));
				for (int i = 0; i < SCHOOLE_UNION; i++) {
					if (i == SCHOOLE_UNION - 1) {
						sb.append("1");
						break;
					}
					sb.append("0");
				}
			} else {
				long aval = Long.valueOf(avaunionid);
				aval++;
				sb.append(aval);
			}
			unionid = sb.toString();
		}
		
		unit.setUnionid(unionid);
		unit.setRegionlevel(regionlevel);
	}

	public String saveUnitWithUser(BaseUnit unitDto, User userDto) throws Exception {
		return _saveUnitWithUser(unitDto, userDto, true);
	}
	/*
	 * 若是新增学校单位，则同时新增管理员关联的职工，及职工所在部门，并且两者建立关联（added by dongzk 2007-8-28）
	 */
	private String _saveUnitWithUser(BaseUnit unitDto, User userDto,boolean needUser)
			throws Exception {
		// 判断单位名称是否重复
		if (baseUnitDao.getCountUnitByName(unitDto.getName()) > 0) {
			throw new ItemExistsException("该单位名称已存在！", "name");
		}
		
		// 判断用户名称是否重复
		if (needUser) {
			if (baseUserService.getUserNameCount(userDto.getName()) > 0) {
				throw new ItemExistsException("该账号已存在！", "userDto.name");
			}

			if (passportAccountService.queryAccountByUsername(userDto.getName()) != null) {
				throw new ItemExistsException("Passport中该账号已存在！", "userDto.name");
			}

			int minLimit = 4;
			int maxLimit = 20;

			if (net.zdsoft.keel.util.StringUtils.getRealLength(userDto.getName()) > maxLimit
					|| net.zdsoft.keel.util.StringUtils.getRealLength(userDto.getName()) < minLimit
					|| !userDto.getName().matches("[\u4e00-\u9fa5a-zA-Z0-9_]+")) {
				throw new ItemExistsException("用户名必须是"+ minLimit + "-" + maxLimit
						+ "个字符(包括大小写字母、数字、下划线)。", "必须是" + minLimit + "-" + maxLimit
						+ "个字符(包括大小写字母、数字、下划线)。");
			}
		} 

		// 如果是数字校园运营平台调用的话，会传入region
		if (org.apache.commons.lang.StringUtils.isBlank(unitDto.getRegion())) {
			String region = takeRegion(unitDto.getUnionid());
			unitDto.setRegion(region);
		}

		insertUnit(unitDto);// 新增单位

		if (needUser) {
			// 管理员用户信息
			userDto.setCreationTime(new Date());
			userDto.setUnitid(unitDto.getId());
			userDto.setRealname("管理员");
		}

		// 新增学校单位时
		// 1、添加“学校管理员组”部门
		String deptName = "";
		if (unitDto.getUnitclass().intValue() == Unit.UNIT_CLASS_SCHOOL) {
			deptName = Dept.SCH_ADMIN_GROUP_NAME;
		} else {
			deptName = Dept.EDU_ADMIN_GROUP_NAME;
		}
		Dept dept = baseDeptService.getDept(unitDto.getId(), deptName);
		if(dept == null){
			dept = baseDeptService.getDefaultDept(unitDto.getId());
		}
		if (dept == null) {
			dept = new Dept();
			String guid = UUIDGenerator.getUUID();
			String deptCode = baseDeptService.getAvaDeptCode(unitDto.getId());
			Long orderid = baseDeptService.getAvaOrder(unitDto.getId());

			dept.setId(guid);
			dept.setUnitId(unitDto.getId());
			if (unitDto.getUnitclass().intValue() == Unit.UNIT_CLASS_SCHOOL) {
				dept.setDeptname(Dept.SCH_ADMIN_GROUP_NAME);
			} else {
				dept.setDeptname(Dept.EDU_ADMIN_GROUP_NAME);
			}
			dept.setDeptCode(deptCode);
			dept.setJymark(new Integer(1));// 科室
			dept.setParentid(Dept.TOP_GROUP_GUID);
			dept.setOrderid(orderid);
			dept.setCreationTime(new Date());
			dept.setDefault(true);
			baseDeptService.insertDept(dept);

		}
		
		// 2、添加“管理员”职工
		if (needUser) {
			
			BaseTeacher teacher = new BaseTeacher();
			// String tchId = teacherDao.getAvaTchid(unit.getId());
			String tchId = "000000";

			teacher.setTchId(tchId);
			teacher.setName(userDto.getRealname());
			teacher.setSex(BaseConstant.SEX_MALE);
			teacher.setEusing(BasedataConstants.EMPLOYEE_INCUMBENCY);
			teacher.setDeptid(dept.getId()); 
			teacher.setUnitid(unitDto.getId());
			teacher.setCardNumber("");
			teacher.setCreationTime(new Date());
			teacher.setDisplayOrder(1);

			baseTeacherService.insertTeacher(teacher);
			String teacherId = teacher.getId();

			// 3、“管理员”用户相关信息，并与职工关联
			userDto.setDeptid(dept.getId());
			userDto.setTeacherid(teacherId);
			
			baseUserService.saveUserByOtherObject(userDto, false);
		}
		unitIniService.initUnitOption(unitDto.getId());// 新增单位设置信息
		roleService.initUnitRoles(unitDto.getId(), unitDto.getUnitclass());// 初始化单位角色

		return unitDto.getId();
	}

	public void updateUnitWithUser(BaseUnit unitDto, User userDto)
			throws Exception {
		Unit preunit = getUnit(unitDto.getId());

		User preuser = (User) baseUserService.getUser(userDto.getId());
		if (BaseConstant.PASSWORD_VIEWABLE.equals(userDto.findClearPassword())) {
			userDto.setPassword(preuser.findClearPassword());
		}
		userDto.setAccountId(preuser.getAccountId());
		userDto.setSequence(preuser.getSequence());
		userDto.setDeptid(preuser.getDeptid());

		Integer unitNameCount = baseUnitDao.getCountUnitByName(unitDto
				.getName());
		Integer userNameCount = baseUserService.getUserNameCount(userDto
				.getName());

		// 判断单位名称是否重复
		if (!preunit.getName().equals(unitDto.getName())) {
			if (unitNameCount > 0) {
				throw new ItemExistsException("该单位名称已存在", "name");
			}

			if (Unit.TOP_UNIT_GUID.equals(preunit.getParentid())) {
				throw new ItemExistsException("顶级单位不允许修改单位名称", "name");
			}
		} else {
			if (unitNameCount > 1) {
				throw new ItemExistsException("该单位名称已存在", "name");
			}
		}

		// 判断用户名称是否重复
		if (!preuser.getName().equals(userDto.getName())) {
			if (userNameCount > 0) {
				throw new ItemExistsException("该账号已存在", "userDto.name");
			}
		} else {
			if (userNameCount > 1) {
				throw new ItemExistsException("该账号已存在", "userDto.name");
			}

		}
		
		int minLimit = 4;
		int maxLimit = 20;
		if (net.zdsoft.keel.util.StringUtils.getRealLength(userDto.getName()) > maxLimit
				|| net.zdsoft.keel.util.StringUtils.getRealLength(userDto.getName()) < minLimit
				|| !userDto.getName().matches("[\u4e00-\u9fa5a-zA-Z0-9_]+")) {
			throw new ItemExistsException("用户名必须是"+ minLimit + "-" + maxLimit
					+ "个字符(包括大小写字母、数字、下划线)。", "必须是" + minLimit + "-" + maxLimit
					+ "个字符(包括大小写字母、数字、下划线)。");
		}

		if (preunit.getUsetype() == Unit.UNIT_USETYPE_REPORT
				&& !preunit.getName().equalsIgnoreCase(unitDto.getName())) {
			throw new ItemExistsException("报送单位不允许本地修改单位名称", "name");
		}

		// 如果是数字校园运营平台调用的话，会传入region
		if (org.apache.commons.lang.StringUtils.isBlank(unitDto.getRegion())) {
			String region = takeRegion(unitDto.getUnionid());
			unitDto.setRegion(region);
		}

		// 取出更新前的单位信息
		Unit u = getUnit(unitDto.getId());

		unitDto.setSrcParentId(u.getParentid());
		updateUnit(unitDto);

		// 更新缓存
		if (u != null) {
			updateUnitCache(u.getParentid(), unitDto.getParentid(), unitDto);
		} else {
			putEntityInCache(unitDto);
		}

		// 如果单位锁定时,该单位所有用户的锁定
		if (unitDto.getMark()!=null && unitDto.getMark() == Unit.UNIT_MARK_LOCK) {
			baseUserService.updateUserLockInUnit(unitDto.getId());
		}

		User user = baseUserService.getUnitAdmin(unitDto.getId());
		user.setName(userDto.getName());
		user.setRealname("管理员");// 修改单位管理员中的用户名
		user.setEmail(userDto.getEmail());
		userDto.setRealname("管理员");
		userDto.setTeacherid(user.getTeacherid());
		// 密码设置
		if (BaseConstant.PASSWORD_VIEWABLE.equals(userDto.findClearPassword())) {
			user.setPassword(userDto.findClearPassword());
		} else if (userDto.findClearPassword() == null
				|| userDto.findClearPassword().trim().length() == 0) {
			user.setPassword("");
			user = unitIniService.getUserPass(user);
		}
		if (unitDto.getMark()!=null && unitDto.getMark() == Unit.UNIT_MARK_NORAML) {
			user.setMark(User.USER_MARK_NORMAL);
			// userDto.setMark(User.MARK_NORMAL);
		} else {
			user.setMark(User.USER_MARK_LOCK);
		}

		if (unitDto.getMark()!=null && unitDto.getMark() == Unit.UNIT_MARK_NORAML) {
			userDto.setMark(User.USER_MARK_NORMAL);
		} else {
			userDto.setMark(User.USER_MARK_LOCK);
		}
		// 修改学校单位时，判断是否有“学校管理员组”部门和关联职工，若没有则添加（added by dongzk 2007-8-28）
		// if(unitDto.getUnitclass().intValue() ==
		// GlobalConstant.UNIT_CLASS_SCHOOL
		// &&
		// !(groupDao.isGroupnameExists(GlobalConstant.SCH_ADMIN_GROUP_NAME,
		// unit.getId()))){
		// //1、添加“学校管理员组”部门
		// Group group = new Group();
		// String guid = UUIDGenerator.getUUID();
		// String deptid = groupDao.getAvaDeptId(unit.getId());
		// Integer orderid = groupDao.getAvaOrderInUnit(unit.getId());
		//          
		// group.setId(guid);
		// group.setUnitid(unit.getId());
		// group.setDeptname(GlobalConstant.SCH_ADMIN_GROUP_NAME);
		// group.setDeptid(deptid);
		// group.setJymark(new Integer(1));//科室
		// group.setParentid(GlobalConstant.TOP_GROUP_GUID);
		// group.setOrderid(orderid);
		//          
		// groupDao.save(group);
		//          
		// //2、添加“管理员”职工
		// Employee employee = new Employee();
		// String empId = employeeDao.getAvaEmpid(unit.getId());
		//          
		// employee.setEmpId(empId);
		// employee.setName("管理员");
		// employee.setSex(GlobalConstant.SEX_MALE);
		// employee.setEusing(McodeConstant.EMPLOYEE_INCUMBENCY);
		// employee.setGroupid(group.getId());
		// employee.setUnitid(unit.getId());
		// employee.setCardNumber("");
		//          
		// String employeeId = employeeDao.saveReturnId(employee);
		//          
		// //3、“管理员”用户相关信息，并与职工关联
		// userDto.setGroupid(group.getId());
		// userDto.setGroupintid(group.getGroupintid());
		// userDto.setEmpleeid(employeeId);
		// }
//20161009根据上面938行逻辑管理员必然存在，以下逻辑判断根据部门名称和部门信息中is_default=1 不准确导致重新添加教职工修改用户表owner_id信息
//		String deptName = "";
//		if (unitDto.getUnitclass().intValue() == Unit.UNIT_CLASS_SCHOOL) {
//			deptName = Dept.SCH_ADMIN_GROUP_NAME;
//		} else {
//			deptName = Dept.EDU_ADMIN_GROUP_NAME;
//		}
//		
//		if (!baseDeptService.isExistsDeptName(deptName, unitDto.getId())
//				&& !baseDeptService.isExistsDefaultDeptName(unitDto.getId())) {
//			Dept dept = baseDeptService.getDept(unitDto.getId(), deptName);
////			if(dept == null){
////				dept = baseDeptService.getDefaultDept(unitDto.getId());
////			}
//			dept = new Dept();
//			String guid = UUIDGenerator.getUUID();
//			String deptCode = baseDeptService.getAvaDeptCode(unitDto.getId());
//			Long orderid = baseDeptService.getAvaOrder(unitDto.getId());
//
//			dept.setId(guid);
//			dept.setUnitId(unitDto.getId());
//
//			if (unitDto.getUnitclass().intValue() == Unit.UNIT_CLASS_SCHOOL) {
//				dept.setDeptname(Dept.SCH_ADMIN_GROUP_NAME);
//			} else {
//				dept.setDeptname(Dept.EDU_ADMIN_GROUP_NAME);
//			}
//
//			dept.setDeptCode(deptCode);
//			dept.setJymark(new Integer(1));// 科室
//			dept.setParentid(Dept.TOP_GROUP_GUID);
//			dept.setOrderid(orderid);
//			dept.setDefault(true);
//			baseDeptService.insertDept(dept);
//			// 2、添加“管理员”职工
//			BaseTeacher teacher = new BaseTeacher();
//			String tchId = "000000";
//
//			teacher.setTchId(tchId);
//			teacher.setName(userDto.getRealname());
//			teacher.setSex(BaseConstant.SEX_MALE);
//			teacher.setEusing(BasedataConstants.EMPLOYEE_INCUMBENCY);
//			teacher.setDeptid(dept.getId());
//			teacher.setUnitid(unitDto.getId());
//			teacher.setCardNumber("");
//
//			baseTeacherService.insertTeacher(teacher);
//			String teacherId = teacher.getId();
//
//			// 3、“管理员”用户相关信息，并与职工关联
//			 userDto.setDeptid(dept.getId());
//			userDto.setTeacherid(teacherId);
//			
//		}
		baseUserService.updateUser(userDto);

		if (!preunit.getName().equals(unitDto.getName())) {
			// 更新学校表中，学校名称数据
			if (unitDto.getUnitclass() == Unit.UNIT_CLASS_SCHOOL) {
				BaseSchool basicSchoolinfo = baseSchoolService
						.getBaseSchool(unitDto.getId());
				if (basicSchoolinfo != null) {
					basicSchoolinfo.setName(unitDto.getName());
					baseSchoolService.updateSchool(basicSchoolinfo);
				}
			}

		}

		// 如果单位排序号改变，则下级单位的排序号前缀也随之改变
		// if (preunit.getOrderid() != unitDto.getOrderid()) {
		// unitDao.updateOrderidByUnionid(unitDto.getUnionid(), String
		// .valueOf(unitDto.getOrderid()), String.valueOf(preunit
		// .getOrderid()));
		// }
	}

	public boolean saveOrUpdateTopUnit(BaseUnit t_unitDto, User t_userDto,
			boolean hasUsedSerial) throws Exception {
		t_unitDto.setUsetype(Unit.UNIT_EDU_TOP);
		t_unitDto.setMark(Unit.UNIT_MARK_NORAML);
		boolean addAdmin = false;
		String admin_name = SpellUtils.getFirstSpell(t_unitDto.getName())
				+ "_admin";

		String unitGuid = null;
		// 数据库里原来就有顶级单位
		if (t_unitDto.getId() != null && !t_unitDto.getId().equals("")) {
			// 并且注册序列号是有效的，也就是超期后重新注册的情况,修改单位注册信息
			if (hasUsedSerial) {
				Unit unit = getUnit(t_unitDto.getId());
				t_unitDto.setUnionid(unit.getUnionid());

				// if (org.apache.commons.lang.StringUtils.isBlank(t_unitDto
				// .getEtohSchoolId())) {
				// t_unitDto.setEtohSchoolId(t_unitDto.getRegion() + "000");
				// }
				Unit u = getUnit(t_unitDto.getId());
				t_unitDto.setSrcParentId(u.getParentid());
				updateUnit(t_unitDto);
				if (u != null) {
					updateUnitCache(u.getParentid(), t_unitDto.getParentid(),
							t_unitDto);
				} else {
					putEntityInCache(t_unitDto);
				}

				// 如果单位是学校类型，则还要修改学校名称
				if (t_unitDto.getUnitclass() != null
						&& t_unitDto.getUnitclass().compareTo(
								Unit.UNIT_CLASS_SCHOOL) == 0) {
					BaseSchool bs = baseSchoolService.getBaseSchool(t_unitDto
							.getId());
					bs.setName(t_unitDto.getName());
					baseSchoolService.updateSchool(bs);
				}

			}// 否则就是系统初次注册，数据库初始化的顶级单位,用sql直接修改顶级单位的guid；
			else {

				unitGuid = UUIDGenerator.getUUID();
				String region = takeRegion(t_unitDto.getUnionid());

				if (t_unitDto.getUnitclass() == Unit.UNIT_CLASS_EDU) {
					String sqlStr = "update base_unit set serial_number = ?, region = ?, id=?,unionid=?,unit_name=?,poll_code=?,unit_class=?,"
							+ "use_type=?,unit_state=?,region_level=?,updatestamp=?,unit_use_type=? where id=?";
					Object[] params = { region + "000", region, unitGuid,
							t_unitDto.getUnionid(), t_unitDto.getName(),
							t_unitDto.getRegcode(), t_unitDto.getUnitclass(),
							t_unitDto.getUsetype(), t_unitDto.getMark(),
							t_unitDto.getRegionlevel(),
							System.currentTimeMillis(),
							t_unitDto.getUnitusetype(), t_unitDto.getId() };

					baseUnitDao.excuteSql(sqlStr, params);

					Unit unit = getUnit(t_unitDto.getId());

					putEntityInCache(unit);

				} else {

				}
			}
		}// 数据库里没有顶级单位，新增顶级单位
		else {

			// 重置单位表的unitintid的自增值为0
			String sqlStr;

			// chenxh 转移 到Oracle，或去除 sql server 专用写法
			sqlStr = "DBCC CHECKIDENT ('base_unit', RESEED, 0) ";

			String region = takeRegion(t_unitDto.getUnionid());

			baseUnitDao.excuteSql(sqlStr, new Object[0]);

			t_unitDto.setRegion(region);
			// 因为已经在hibernate的xml配置文件中将此identity字段配置为插入或更新都不执行，因此这里设unitintid值是无意义的
			t_unitDto.setAuthorized(BaseUnit.UNIT_AUTHORIZED);
//			t_unitDto.setOrderid(t_unitDto.getUnionid());
			t_unitDto.setUnittype(Unit.UNIT_MARK_NORAML);
			t_unitDto.setUsetype(Unit.UNIT_EDU_TOP);
			t_unitDto.setMark(Unit.UNIT_MARK_NORAML);
			// if (t_unitDto.getUnitclass() == Unit.UNIT_CLASS_EDU)
			// t_unitDto.setEtohSchoolId(region + "000");

			insertUnit(t_unitDto);

			// 初始化单位角色
			roleService.initUnitRoles(t_unitDto.getId(), t_unitDto
					.getUnitclass());

			unitGuid = t_unitDto.getId();
		}

		String deptName = "";
		if (t_unitDto.getUnitclass().intValue() == Unit.UNIT_CLASS_SCHOOL) {
			deptName = Dept.SCH_ADMIN_GROUP_NAME;
		} else {
			deptName = Dept.EDU_ADMIN_GROUP_NAME;
		}
		Dept dept = baseDeptService.getDept(t_unitDto.getId(), deptName);
		if(dept == null){
			dept = baseDeptService.getDefaultDept(t_unitDto.getId());
		}
		if (dept == null) {
			dept = new Dept();
			String guid = UUIDGenerator.getUUID();
			String deptCode = baseDeptService.getAvaDeptCode(t_unitDto.getId());
			Long orderid = baseDeptService.getAvaOrder(t_unitDto.getId());

			dept.setId(guid);
			dept.setUnitId(t_unitDto.getId());
			// group.setDeptname(groupName);
			if (t_unitDto.getUnitclass().intValue() == Unit.UNIT_CLASS_SCHOOL) {
				dept.setDeptname(Dept.SCH_ADMIN_GROUP_NAME);
			} else {
				dept.setDeptname(Dept.EDU_ADMIN_GROUP_NAME);
			}

			dept.setDeptCode(deptCode);
			dept.setJymark(new Integer(1));// 科室
			dept.setParentid(Dept.TOP_GROUP_GUID);
			dept.setOrderid(orderid);
            dept.setDefault(true);
            
			baseDeptService.insertDept(dept);
		}
		// //修改学校单位时，判断是否有“学校管理员组”部门和关联职工，若没有则添加（added by dongzk 2007-8-28）
		// Group group = new Group();
		// String guid = UUIDGenerator.getUUID();
		// String deptid = groupDao.getAvaDeptId(unit.getId());
		// Integer orderid = groupDao.getAvaOrderInUnit(unit.getId());
		//      
		//      
		// group.setId(guid);
		// group.setUnitid(unit.getId());
		// if(unit.getUnitclass().intValue() ==
		// GlobalConstant.UNIT_CLASS_SCHOOL){
		// group.setDeptname(GlobalConstant.SCH_ADMIN_GROUP_NAME);
		// }
		// else{
		// group.setDeptname(GlobalConstant.EDU_ADMIN_GROUP_NAME);
		// }
		//
		// group.setDeptid(deptid);
		// group.setJymark(new Integer(1));//科室
		// group.setParentid(GlobalConstant.TOP_GROUP_GUID);
		// group.setOrderid(orderid);
		//      
		// groupDao.save(group);
		//      
		// //2、添加“管理员”职工
		// Employee employee = new Employee();
		// String empId = employeeDao.getAvaEmpid(unit.getId());
		//      
		// employee.setEmpId(empId);
		// employee.setName(t_userDto.getRealname());
		// employee.setSex(GlobalConstant.SEX_MALE);
		// employee.setEusing(McodeConstant.EMPLOYEE_INCUMBENCY);
		// employee.setGroupid(group.getId());
		// employee.setUnitid(unit.getId());
		// employee.setCardNumber("");
		//      
		// String employeeId = employeeDao.saveReturnId(employee);
		//      
		// //3、“管理员”用户相关信息，并与职工关联
		// t_userDto.setGroupid(group.getId());
		// t_userDto.setGroupintid(group.getGroupintid());
		// t_userDto.setEmpleeid(employeeId);

		// if(unit.getUnitclass().intValue() == GlobalConstant.UNIT_CLASS_SCHOOL
		// &&
		// !(groupDao.isGroupnameExists(GlobalConstant.SCH_ADMIN_GROUP_NAME,
		// unit.getId()))){
		// //1、添加“学校管理员组”部门
		// Group group = new Group();
		// String guid = UUIDGenerator.getUUID();
		// String deptid = groupDao.getAvaDeptId(unit.getId());
		// Integer orderid = groupDao.getAvaOrderInUnit(unit.getId());
		//          
		// group.setId(guid);
		// group.setUnitid(unit.getId());
		// group.setDeptname(GlobalConstant.SCH_ADMIN_GROUP_NAME);
		// group.setDeptid(deptid);
		// group.setJymark(new Integer(1));//科室
		// group.setParentid(GlobalConstant.TOP_GROUP_GUID);
		// group.setOrderid(orderid);
		//          
		// groupDao.save(group);
		//          
		// //2、添加“管理员”职工
		// Employee employee = new Employee();
		// String empId = employeeDao.getAvaEmpid(unit.getId());
		//          
		// employee.setEmpId(empId);
		// employee.setName("管理员");
		// employee.setSex(GlobalConstant.SEX_MALE);
		// employee.setEusing(McodeConstant.EMPLOYEE_INCUMBENCY);
		// employee.setGroupid(group.getId());
		// employee.setUnitid(unit.getId());
		// employee.setCardNumber("");
		//          
		// String employeeId = employeeDao.saveReturnId(employee);
		//          
		// //3、“管理员”用户相关信息，并与职工关联
		// t_userDto.setGroupid(group.getId());
		// t_userDto.setGroupintid(group.getGroupintid());
		// t_userDto.setEmpleeid(employeeId);
		// }
		
		// 2、添加“管理员”职工
		BaseTeacher teacher = new BaseTeacher();
		// String tchId = teacherDao.getAvaTchid(unit.getId());
		String tchId = "000000";

		teacher.setTchId(tchId);
		teacher.setName(t_userDto.getRealname());
		teacher.setSex(BaseConstant.SEX_MALE);
		teacher.setEusing(BasedataConstants.EMPLOYEE_INCUMBENCY);
		teacher.setDeptid(dept.getId());
		teacher.setUnitid(t_unitDto.getId());
		teacher.setCardNumber("");

		baseTeacherService.insertTeacher(teacher);
		String teacherId = teacher.getId();

		// 3、“管理员”用户相关信息，并与职工关联
		// t_userDto.setDeptid(dept.getId());
		// t_userDto.setDeptintid(dept.getDeptintid());
		t_userDto.setTeacherid(teacherId);
		
		
		if (t_userDto.getId() == null) {// 新增管理员

			// 检查是否有重名的用户
			User t_user = baseUserService.getUserByUserName(admin_name);
			if (t_user != null) {
				admin_name += "2";
				t_userDto.setName(admin_name);
			}

			t_userDto.setUnitid(unitGuid);
			// t_userDto.setUnitintid(unit.getUnitintid());
			t_userDto.setTeacherid(null);
			t_userDto.setId(UUIDGenerator.getUUID());
			t_userDto.setPassword(admin_name);
			baseUserService.saveUserByOtherObject(t_userDto, false);

			addAdmin = true;
		} else {// 修改管理员

			if (unitGuid != null) {
				t_userDto.setUnitid(unitGuid);
				// 当单位guid改变时，同时也改变用户的guid
				t_userDto.setId(UUIDGenerator.getUUID());
			}
			// t_userDto.setUnitintid(unit.getUnitintid());
			baseUserService.updateUser(t_userDto, true);
		}

		return addAdmin;

	}

	public void deleteUnit(String[] arrayIds) {
		deleteUnit(arrayIds, EventSourceType.LOCAL);
	}

	private void deleteUnit(String[] arrayIds, EventSourceType eventSource) {
		if (arrayIds == null) {
			return;
		}
		List<String> deleteUnitIds = new ArrayList<String>();
		List<Unit> unitDtoList;
		for (int i = 0; i < arrayIds.length; i++) {
			deleteUnitIds.add(arrayIds[i]);
			unitDtoList = getAllSchools(arrayIds[i]);
			if (CollectionUtils.isEmpty(unitDtoList)) {
				continue;
			}

			for (Unit unitDto : unitDtoList) {
				if (!deleteUnitIds.contains(unitDto.getId())) {
					deleteUnitIds.add(unitDto.getId());
				}
			}
		}
		// 同步删除passport的内容
		List<User> ulist = baseUserDao.getUsersByUnitIds(deleteUnitIds.toArray(new String[] {}));
		if (CollectionUtils.isNotEmpty(ulist)) {
			Set<String> accountIdSet = new HashSet<String>();
			for (User u : ulist) {
				String accountId = u.getAccountId();
				if (StringUtils.isNotBlank(accountId))
					accountIdSet.add(u.getAccountId());
			}
			if (eventSource.getValue() == EventSourceType.LOCAL.getValue()) {
				passportAccountService.removeAccounts(accountIdSet
						.toArray(new String[0]));
			}
		}
		baseUnitDao.deleteUnit(deleteUnitIds.toArray(new String[] {}),
				eventSource);
		deleteUnitSysch(deleteUnitIds.toArray(new String[] {}));

		// 清除缓存
		for (String unitId : deleteUnitIds) {
			Unit u = getUnit(unitId);
			if (u == null)
				continue;
			removeUnitFromCache(u.getParentid(), unitId);
		}
	}

	private void deleteUnitSysch(String[] unitIds) {
		for (String uId : unitIds) {
			deleteXmlDataService.delete("*_unit_delete.xml", uId);
		}
	}

	public void updateUnitMark(String[] unitIds, Integer mark) {
		for (String uId : unitIds) {
			removeEntityFromCache(uId);
		}
		baseUnitDao.updateUnitMark(unitIds, mark);
	}

	public void updateUnitBalance(String unitId, int balance) {
		removeEntityFromCache(unitId);
		baseUnitDao.updateUnitBalance(unitId, balance);
	}

	public boolean deleteUnitTransmitInfo(String unitId) {
		boolean result = true;
		Unit unit = getUnit(unitId);
		try {
			deleteUnit(new String[] { unit.getId() });
		} catch (Exception e) {
			e.printStackTrace();
			result = false;
			SystemLog.log(UNIT_MODID, "删除" + unit.getName() + "单位相关报送信息失败");
		}
		SystemLog.log(UNIT_MODID, "删除" + unit.getName() + "单位相关报送信息成功");
		return result;
	}

	public void deleteUnit(String unitId, EventSourceType eventSource) {
		deleteUnit(new String[] { unitId }, eventSource);
	}

	public void updateUnitUnionId(String[][] uu) {
		if (uu == null) {
			return;
		}

		Set<String> unitIdSet = new HashSet<String>();
		Map<String, String> uMap = new HashMap<String, String>();
		for (int i = 0; i < uu.length; i++) {
			unitIdSet.add(uu[i][0]);
			uMap.put(uu[i][0], uu[i][1]);
		}

		Map<String, BaseUnit> unitMap = getBaseUnitMap(unitIdSet
				.toArray(new String[] {}));

		for (String unitId : unitIdSet) {
			if (unitMap.containsKey(unitId)) {
				BaseUnit unit = unitMap.get(unitId);
				String unionId = uMap.get(unitId);
				unit.setUnionid(unionId);
				updateUnit(unit);
			}
		}
	}

	public WebTree getUnitTree(String unitId, String[] ids, String rootName) {
		WebTree tree = new WebTree(rootName);
		Map<String, WebTreeNode> treeMap = new HashMap<String, WebTreeNode>();

		BaseUnit unit = getBaseUnit(unitId);
		if (unit == null) {
			return null;
		}
		String[] images = getImagesByUnit(unit);
		WebTreeNode treeNode = new WebCheckBoxTreeNode(unit.getName(),
				"arrayIds", unit.getId(), null, isUnitSelected(unit, ids),
				false, null, images[0], images[1]);

		tree.addChildNode(treeNode);
		treeMap.put(unitId, treeNode);

		Map<String, List<BaseUnit>> unitMap = getBaseUnitMapKeyByParentId();

		this.getTreeNode(unitId, unitMap, treeMap, ids);

		return tree;
	}

	public WebTree getUnitTreeWithMark(String unitId, String[] ids,
			String rootName, int type) {
		WebTree tree = new WebTree(rootName);
		Map<String, WebTreeNode> treeMap = new HashMap<String, WebTreeNode>();

		BaseUnit unit = getBaseUnit(unitId);
		if (unit == null) {
			return null;
		}
		String[] images = getImagesByUnit(unit);
		String unitMarkStr;
		unitMarkStr = getMarkStringByUnit(unit);
		String spanId = "";
		WebTreeNode treeNode = new WebCheckBoxTreeNode(unit.getName(),
				"arrayIds", unit.getId(), null, isUnitSelected(unit, ids),
				false, null, images[0], images[1], unitMarkStr, spanId);

		tree.addChildNode(treeNode);
		treeMap.put(unitId, treeNode);

		Map<String, List<BaseUnit>> unitMap = getBaseUnitMapKeyByParentId();

		this.getTreeNodeWithMark(unitId, unitMap, treeMap, ids, type);

		return tree;
	}

	private void getTreeNodeWithMark(String unitId,
			Map<String, List<BaseUnit>> unitMap,
			Map<String, WebTreeNode> treeMap, String[] ids, int type) {
		WebTreeNode treeNode, treeNode_;
		String[] images;
		List<BaseUnit> subList;

		if (unitMap.containsKey(unitId)) {
			subList = unitMap.get(unitId);
			if (CollectionUtils.isEmpty(subList)) {
				return;
			}

			Collections.sort(subList, new Comparator<BaseUnit>() {
				public int compare(BaseUnit o1, BaseUnit o2) {
					return o1.getUnitclass().compareTo(o2.getUnitclass());
				}
			});

			String unitMarkStr;
			String spanId = "";
			for (BaseUnit unitTemp : subList) {
				treeNode = treeMap.get(unitId);
				images = getImagesByUnit(unitTemp);
				unitMarkStr = getMarkStringByUnit(unitTemp);
				// spanId = " id='" + unitTemp.getId() + "' ";
				treeNode_ = new WebCheckBoxTreeNode(unitTemp.getName(),
						"arrayIds", unitTemp.getId(), null, isUnitSelected(
								unitTemp, ids), false, null, images[0],
						images[1], unitMarkStr, spanId);
				treeNode.addChildNode(treeNode_);
				treeMap.put(unitTemp.getId(), treeNode_);

				this.getTreeNodeWithMark(unitTemp.getId(), unitMap, treeMap,
						ids, type);
			}
		}
	}

	private void getTreeNode(String unitId,
			Map<String, List<BaseUnit>> unitMap,
			Map<String, WebTreeNode> treeMap, String[] ids) {
		WebTreeNode treeNode, treeNode_;
		String[] images;
		List<BaseUnit> subList;

		if (unitMap.containsKey(unitId)) {
			subList = unitMap.get(unitId);
			if (CollectionUtils.isEmpty(subList)) {
				return;
			}

			Collections.sort(subList, new Comparator<BaseUnit>() {
				public int compare(BaseUnit o1, BaseUnit o2) {
					return o1.getUnitclass().compareTo(o2.getUnitclass());
				}
			});

			for (BaseUnit unitTemp : subList) {
				treeNode = treeMap.get(unitId);
				images = getImagesByUnit(unitTemp);
				treeNode_ = new WebCheckBoxTreeNode(unitTemp.getName(),
						"arrayIds", unitTemp.getId(), null, isUnitSelected(
								unitTemp, ids), false, null, images[0],
						images[1]);
				treeNode.addChildNode(treeNode_);
				treeMap.put(unitTemp.getId(), treeNode_);

				this.getTreeNode(unitTemp.getId(), unitMap, treeMap, ids);
			}
		}
	}

	private boolean isUnitSelected(BaseUnit unit, String[] ids) {
		if (unit == null) {
			return false;
		}
		if (ids == null) {
			return true;
		}
		for (String id : ids) {
			if (id.equals(unit.getId())) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 根据单位类型得到相应图标
	 * 
	 * @param unit
	 * @return
	 */
	private String[] getImagesByUnit(BaseUnit unit) {
		String[] images = new String[2];
		if (unit == null) {
			return images;
		}
		images[0] = "prefix+'images/";
		images[1] = "prefix+'images/";
		if (unit.getUnitclass() == Unit.UNIT_CLASS_EDU) {
			images[0] += "edu.gif'";
			images[1] += "edu_selected.gif'";
		} else {
			images[0] += "school.gif'";
			images[1] += "school_selected.gif'";
		}
		return images;
	}

	/**
	 * 根据单位状态得到相应提示字符
	 * 
	 * @param unit
	 * @return
	 */
	private String getMarkStringByUnit(BaseUnit unit) {
		if (unit == null) {
			return null;
		}
		String result = null;
		switch (unit.getMark().intValue()) {
		case Unit.UNIT_MARK_NOTAUDIT:
			result = "<span style=\"color:red;padding-left:5px;\">未审核</span>";
			break;
		case Unit.UNIT_MARK_NORAML:
			result = "<span style=\"color:#2A258A;padding-left:5px;\">正常</span>";
			break;
		case Unit.UNIT_MARK_LOCK:
			result = "<span style=\"color:blue;padding-left:5px;\">锁定</span>";
			break;
		default:
			result = "<span style=\"padding-left:5px;\">未知状态</span>";
			break;
		}
		return result;
	}

	public Integer getCountAllUnitByAuthorized(Integer authorized) {
		return baseUnitDao.getCountAllUnitByAuthorized(authorized);
	}

	public Integer getCountUnitByName(String unitName) {
		return baseUnitDao.getCountUnitByName(unitName);
	}

	public Integer getCountUnionId(String unionId) {
		return baseUnitDao.getCountUnionId(unionId);
	}

	public Map<String, Integer> getCountsByUnionIds(String[] unionIds) {
		return baseUnitDao.getCountsByUnionIds(unionIds);
	}

	public List<BaseUnit> getUnderlingBaseUnits(String unionid, String searchName,
			Pagination page) {
		return baseUnitDao.getUnderlingUnits(unionid, searchName, page);
	}

	public BaseUnit getBaseUnit(String unitId) {
		return baseUnitDao.getBaseUnit(unitId);
	}

	public BaseUnit getBaseUnitByUnionId(String unionId) {
		return baseUnitDao.getBaseUnitByUnionId(unionId);
	}

	public Map<String, BaseUnit> getBaseUnitMap(String[] unitIds) {
		return baseUnitDao.getBaseUnitMap(unitIds);
	}

	public List<BaseUnit> getBaseUnitsByUnionCodeUnitType(String unionId,
			int unitClass, int unitType) {
		return baseUnitDao.getBaseUnitsByUnionCodeUnitType(unionId, unitClass,
				unitType);
	}

	public List<BaseUnit> getAllBaseUnits(String unitId, boolean self) {
		BaseUnit unit = getBaseUnit(unitId);
		List<BaseUnit> unitList = baseUnitDao.getUnitsByUnionCode(unit
				.getUnionid());
		if (!self) {
			unitList.remove(unit);
		}
		return unitList;
	}

	public List<BaseUnit> getBaseUnits(String[] unitIds) {
		return baseUnitDao.getBaseUnits(unitIds);
	}

	private Map<String, List<BaseUnit>> getBaseUnitMapKeyByParentId() {
		List<BaseUnit> unitList = baseUnitDao.getBaseUnits();
		Map<String, List<BaseUnit>> unitMap = new HashMap<String, List<BaseUnit>>();
		List<BaseUnit> subList;
		// 将所有单位以父单位ID作为Key,放入unitmap中
		for (int i = 0; i < unitList.size(); i++) {
			BaseUnit unit = unitList.get(i);
			if (!unitMap.containsKey(unit.getParentid())) {
				subList = new LinkedList<BaseUnit>();
				for (int j = i; j < unitList.size(); j++) {
					BaseUnit unit_ = unitList.get(j);
					if (unit_.getParentid().equals(unit.getParentid())) {
						subList.add(unit_);
					}
				}
				unitMap.put(unit.getParentid(), subList);
			}
		}
		return unitMap;
	}
      
    public int getAllUnitCount() {
        return baseUnitDao.getAllUnitCount();
    }


    public void setSystemSubsystemService(SystemSubsystemService systemSubsystemService) {
        this.systemSubsystemService=systemSubsystemService;
    }
    public SystemSubsystemService getSystemSubsystemService() {
        return this.systemSubsystemService;
    }

	@Override
	public String getUnionidFromCache(BaseUnit parentUnit, Integer unitClass) {
		String key = "CREATE_UNIONNO_MAX_" + parentUnit.getId();
		Object o = getFromCache(key);
        String unionNoMax = "";
        if(o == null) {
        	unionNoMax = baseUnitDao.getAvaUnionId(parentUnit.getId(),
    				parentUnit.getUnionid(), Unit.UNIT_CLASS_SCHOOL, 12);
    		if (unionNoMax == null) {
    			unionNoMax = "1";
    		} else if(StringUtils.isNumeric(unionNoMax)){
    			unionNoMax = Long.valueOf(unionNoMax) + 1 + "";
    		}
        	putInCache(key, unionNoMax);
        }else{
        	unionNoMax = String.valueOf(Long.valueOf(o.toString())+1);
            removeFromCache(key);
            putInCache(key, unionNoMax);
        }
        StringBuffer sb = new StringBuffer();
        sb.append(StringUtils.rightPad(parentUnit.getUnionid(), FINAL_LENGTH - unionNoMax.length(), "0"))
        .append(unionNoMax);
        return sb.toString();
	}

	@Override
	public String getSections(String schoolType) {
		return schoolDao.getSections(schoolType);
	}

	@Override
	public void updateUnitCache(String destParentId, BaseUnit unit) {
		updateUnitCache(null, destParentId, unit);
	}

	@Override
	public void updateRunSchType(String unitId, String runSchType) {
		baseUnitDao.updateRunSchType(unitId, runSchType);
	}
}
