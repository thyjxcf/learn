/* 
 * @(#)UnitAdminAction.java    Created on 2006-9-21
 * Copyright (c) 2006 ZDSoft Networks, Inc. All rights reserved.
 * $Header: f:/44cvsroot/stusys/stusys/src/net/zdsoft/stusys/system/unit/action/UnitAdminAction.java,v 1.73 2007/02/01 02:09:19 linqz Exp $
 */
package net.zdsoft.eis.base.data.action;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.zdsoft.eis.base.common.entity.EduInfo;
import net.zdsoft.eis.base.common.entity.Grade;
import net.zdsoft.eis.base.common.entity.Mcodedetail;
import net.zdsoft.eis.base.common.entity.Region;
import net.zdsoft.eis.base.common.entity.SystemVersion;
import net.zdsoft.eis.base.common.entity.UnitIni;
import net.zdsoft.eis.base.common.entity.User;
import net.zdsoft.eis.base.common.service.Mcode;
import net.zdsoft.eis.base.common.service.McodeService;
import net.zdsoft.eis.base.common.service.McodedetailService;
import net.zdsoft.eis.base.common.service.RegionService;
import net.zdsoft.eis.base.common.service.SchoolService;
import net.zdsoft.eis.base.common.service.SystemIniService;
import net.zdsoft.eis.base.common.service.SystemVersionService;
import net.zdsoft.eis.base.common.service.UnitIniService;
import net.zdsoft.eis.base.constant.BaseConstant;
import net.zdsoft.eis.base.data.BasedataConstants;
import net.zdsoft.eis.base.data.entity.BaseSchool;
import net.zdsoft.eis.base.data.entity.BaseUnit;
import net.zdsoft.eis.base.data.service.BaseEduInfoService;
import net.zdsoft.eis.base.data.service.BaseGradeService;
import net.zdsoft.eis.base.data.service.BaseSchoolService;
import net.zdsoft.eis.base.data.service.BaseUnitService;
import net.zdsoft.eis.base.data.service.BaseUserService;
import net.zdsoft.eis.base.deploy.SystemDeployService;
import net.zdsoft.eis.base.remote.dto.UnitRegisterResultDto;
import net.zdsoft.eis.base.remote.exception.UnitRegisterException;
import net.zdsoft.eis.base.remote.param.service.impl.CommonParamServiceImpl;
import net.zdsoft.eis.base.remote.service.UnitRemoteService;
import net.zdsoft.eis.base.subsystemcall.service.SchsecuritySubsystemService;
import net.zdsoft.eis.base.subsystemcall.service.SystemSubsystemService;
import net.zdsoft.eis.base.util.SoapProxy;
import net.zdsoft.eis.base.util.SystemLog;
import net.zdsoft.eis.frame.action.PageAction;
import net.zdsoft.eis.frame.dto.PromptMessageDto;
import net.zdsoft.eschool.smsclient.SmsException;
import net.zdsoft.keel.util.StringUtils;
import net.zdsoft.keel.util.Validators;
import net.zdsoft.leadin.exception.ItemExistsException;
import net.zdsoft.leadin.tree.WebTree;
import net.zdsoft.leadin.util.ExportUtil;
import net.zdsoft.leadin.util.PWD;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.struts2.dispatcher.multipart.MultiPartRequestWrapper;

import com.opensymphony.xwork2.ModelDriven;

public class UnitAdminAction extends PageAction implements
		ModelDriven<BaseUnit> {

	/**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = 1L;

	private int unitclass_school = BaseUnit.UNIT_CLASS_SCHOOL;

	private int unitclass_edu = BaseUnit.UNIT_CLASS_EDU;

	private int unitclass_nonedu = BaseUnit.UNIT_NOTEDU_NOTSCH;

	private int unittype_subedu = BaseUnit.UNIT_EDU_SUB;// 下属教育局

	private int unittype_asp = BaseUnit.UNIT_SCHOOL_ASP;// 托管中小学

	private int unittype_noedu = BaseUnit.UNIT_NOTEDU_NOTSCH;// 非教育局单位

	private int unittype_kg = BaseUnit.UNIT_SCHOOL_KINDERGARTEN;// 幼儿园

	private int unitmark_noaudit = BaseUnit.UNIT_MARK_NOTAUDIT;

	private int unitmark_normal = BaseUnit.UNIT_MARK_NORAML;

	private int unitmark_lock = BaseUnit.UNIT_MARK_LOCK;

	private String password_default = BaseConstant.PASSWORD_VIEWABLE;

	private String password_init = BaseConstant.PASSWORD_INIT;

	private int unitCountry = 1;// 乡镇级教育局标志

	private String modID = "SYS002";

	private String unitTypeMcodeId = "DM-DWLX";// 单位类型微代码

	private String unitClassMcodeId = "DM-DWFL";// 单位分类

	private String unitMarkMcodeId = "DM-DWZT";// 单位状态

	private int unitNameLength = BaseUnit.NAME_LENGTH;

//	private String userNameFieldTip = User.NAME_ALERT;

//	private String userPasswordFieldTip = User.PASSWORD_ALERT;

	private int unitMarkWithOutAutidt = BaseUnit.UNIT_MARK_NOTAUDIT;

	private String existsUnitOnEdu = "0"; // 教育局是否存在此单位 0 不存在，1 存在

	private int passwordName = BaseConstant.PASSWORD_GENERIC_RULE_NAME;// 1-----密码为登录名

	private int passwordNull = BaseConstant.PASSWORD_GENERIC_RULE_NULL;// 0-----密码为空

	private int passwordUnionize = BaseConstant.PASSWORD_GENERIC_RULE_UNIONIZE;// 2-----密码为本单位默认密码（即本单位统一密码）

	// 单位导出的文件的标题
	private static String[] EXPORT_FIELD_TITLES = new String[] { "单位统一编号",
			"单位名称", "注册日期", "单位分类", "管理员账号", "管理员登录密码" };

	// 记录对应的值对象的属性名称数组
	private static String[] PROPERTY_NAMES = new String[] { "unionid", "name",
			"creationTime", "unitclassName", "adminName",
			"password" };

	private BaseUnitService baseUnitService;

	private BaseUserService baseUserService;

	private RegionService regionService;

	private UnitRemoteService unitRemoteService;

	private UnitIniService unitIniService;

	private McodedetailService mcodedetailService;

	private SystemIniService systemIniService;
	private SystemVersionService systemVersionService;
	private SystemDeployService systemDeployService;
	private McodeService mcodeService;
	private SchsecuritySubsystemService schsecuritySubsystemService;
	private BaseGradeService baseGradeService;

	// private SmsConfigService smsConfigService;
	//
	// private SmsUseConfigService smsUseConfigService;

	private BaseSchoolService baseSchoolService;

	private BaseEduInfoService baseEduInfoService; // 教育局

	public String unitId;

	public List<BaseUnit> listOfUnitDto = new ArrayList<BaseUnit>();

	public List<Mcodedetail> unitClassList;

	private BaseUnit unitDto = new BaseUnit();

	private User userDto = new User();

	private UnitIni unitIni;

	private String genericPass;

	private String consistentPass;

	private String confirmPass;

	public boolean isTopAdmin = false;

	public boolean isEISSUnit = false;

	private boolean isTopUnit = false;

	private boolean isSelfDeal = false;

	private String errorMessage;

	private String syncSmsCenter;// 是否同步在短信平台创建帐号

	private String currentClientid;// 当前使用的短信平台的帐号

	private String delsmscenter;// 是否删除在短信平台的帐号

	private String xtreeScript;

	private String treeName;
	
	private String userName; //单位管理验证用户名是否可用

	// private SerialManager serialManager;
	private SystemSubsystemService systemSubsystemService;
	
	private SchoolService schoolService; 
	///schoolDao.getSections(schoolType);

	private String[] unitIds;

	private UnitRegisterResultDto unitRegisterResultDto;

	private List<BaseUnit> succUnitList;

	private List<BaseUnit> modifyUnitList;

	@SuppressWarnings("unchecked")
	private List errorList;
	
	private Object[] objectJson;
	
	private String unitUnionId;
	
	private String unitClass;
	
	private String parentUnitId;
	
	private boolean isNotSch = false; //是否非教育局单位

	public BaseUnit getUnitDto() {
		return unitDto;
	}

	public List<BaseUnit> getListOfUnitDto() {
		return listOfUnitDto;
	}

	private String unitName;// 查询的单位名称
	private Map<String, User> userMap;// 单位管理员map

	public String viewUnitMain() {
		unitId = getLoginInfo().getUnitID();
		return SUCCESS;
	}

	public String viewUnitList() {
		if (!Validators.isEmpty(errorMessage)) {
			addActionError(errorMessage);
		}

		if (getLoginInfo().getUser().getType() == User.USER_TYPE_TOPADMIN) {
			isTopAdmin = true;
		}
		if(org.apache.commons.lang.StringUtils.isBlank(unitId)){
			unitId = getLoginInfo().getUnitID();
		}else{
			BaseUnit unit = baseUnitService.getBaseUnit(getLoginInfo().getUnitID());// 获取本单位
            if (unit.getParentid().equals(unitId)) {
                unitId = unit.getId();
            }
		}

		// 有权限维护下属单位信息（直属和间接下属）
		unitDto = baseUnitService.getBaseUnit(unitId);

		// added by hexq 2009-6-23
		// 6个0代表全国顶级教育局,取得下面所有的单位
		 if (org.apache.commons.lang.StringUtils.isNotBlank(unitName)) {
            try {
                unitName = URLDecoder.decode(unitName, "utf-8");
            }
            catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
	    }
		if (unitDto.getRegion().equals(BaseUnit.TOP_UNIT_REGION_CODE)) {
			listOfUnitDto = baseUnitService.getUnderlingBaseUnits("", unitName,
					getPage());
		} else {
			listOfUnitDto = baseUnitService.getUnderlingBaseUnits(unitDto
					.getUnionid(), unitName, getPage());
		}

		// 取单位管理员信息
//		userMap = baseUserService.takeUnitAdmins(takeUnitIds(listOfUnitDto));
		userMap = baseUserService.getAdminUserMap(takeUnitIds(listOfUnitDto));
		return SUCCESS;
	}

	/**
	 * 根据unit list返回id数组，如果没值返回空数组
	 * 
	 * @author "yangk" Sep 8, 2010 3:44:26 PM
	 * @param listOfUnit
	 * @return
	 */
	private String[] takeUnitIds(List<BaseUnit> listOfUnit) {
		if (CollectionUtils.isNotEmpty(listOfUnit)) {
			String[] ids = new String[listOfUnit.size()];
			for (int i = 0; i < listOfUnit.size(); i++) {
				ids[i] = listOfUnit.get(i).getId();
			}
			return ids;
		} else {
			return new String[0];
		}
	}

	@SuppressWarnings("unchecked")
	public String exportUnit() {
		unitId = getLoginInfo().getUnitID();
		List<BaseUnit> unitList = baseUnitService.getAllBaseUnits(unitId, true);
		assembleUnit(unitList);
		Map<String, List> records = new HashMap<String, List>();
		records.put("单位列表", unitList);
		String fileName = "UnitInformation";
		ExportUtil exportUtil = new ExportUtil();
		exportUtil.exportXLSFile(EXPORT_FIELD_TITLES, PROPERTY_NAMES, records,
				fileName);
		return NONE;
	}

	/**
	 * 拼装单位信息，转化微代码，并增加管理员信息
	 * 
	 * @author "yangk" Sep 8, 2010 4:13:17 PM
	 * @param unitList
	 */
	private void assembleUnit(List<BaseUnit> unitList) {
//		Mcode unitMarkMcode = mcodeService.getMcode(unitMarkMcodeId);
		Mcode unitClassMcode = mcodeService.getMcode(unitClassMcodeId);
		Map<String, User> userIdMap = baseUserService
				.getUnitAdmins(takeUnitIds(unitList));
		for (BaseUnit unit : unitList) {
			if (userIdMap.containsKey(unit.getId())) {
				userDto = userIdMap.get(unit.getId());
				unit.setAdminName(userDto.getName());
				unit.setPassword(userDto.findClearPassword());
			}
			unit.setUnitclassName(unitClassMcode.get(String.valueOf(unit
					.getUnitclass())));
//			unit.setMarkName(unitMarkMcode.get(String.valueOf(unit.getMark())));
		}
	}

	public String editUnit() {
		if (unitId == null) {
            return ERROR;
        }
        unitDto = baseUnitService.getBaseUnit(unitId);
        if (unitDto.getUnitclass() == BaseUnit.UNIT_CLASS_SCHOOL) {
            BaseSchool school = baseSchoolService.getBaseSchool(unitId);
            if (school != null) {
                unitDto.setSchCode(school.getCode());
                unitDto.setSchtype(school.getType());
                unitDto.setRunschtype(school.getRunschtype());
                unitDto.setRegionPropertyCode(school.getRegionPropertyCode());
            }
        }
        userDto = baseUserService.getUnitAdmin(unitId);
        setUnitList();
    	return SUCCESS;
	}

	/**
	 * 修改单位
	 * @return
	 */
	public String updateUnit() {
		setPromptMessageDto(new PromptMessageDto());
		String oldRegion = unitDto.getRegion();
		String oldSchType = unitDto.getSchtype();
		unitDto.setRegion("");
		PromptMessageDto pDto = unitValidateNew(unitDto);
		promptMessageDto.setOperateSuccess(false);
		if(!pDto.getOperateSuccess()){
			promptMessageDto = pDto;
			return SUCCESS;
		}
		pDto = userValidateNew(userDto, false);
		if(!pDto.getOperateSuccess()){
			promptMessageDto = pDto;
			return SUCCESS;
		}
		if (unitclass_school == unitDto.getUnitclass()) {// 如果是学校类型的
	          String temp = baseSchoolService.getSchoolIdByCode(unitDto.getSchCode());
	          if (temp != null && !temp.equals(unitDto.getId())) {
	        	  promptMessageDto.setErrorMessage("学校代码已存在！请修改");
	              return SUCCESS;
	          }
	    }
		try {
			// 如果不是顶级教育局，需要设置相应的行政区划等级
			if (!this.isTopUnit()) {
				BaseUnit parentU = baseUnitService.getBaseUnit(unitDto.getParentid());
				unitDto.setRegionlevel(parentU.getRegionlevel() + 1);
			}
			//更新办学性质信息到单位表
			if(org.apache.commons.lang.StringUtils.isNotBlank(unitDto.getRunschtype())){
				baseUnitService.updateRunSchType(unitDto.getId(), unitDto.getRunschtype());
			}
			baseUnitService.updateUnitWithUser(unitDto, userDto);

			try {
				String unitId = unitDto.getId();
				if (null != unitId) {
					BaseSchool basicSchoolinfoDto = baseSchoolService.getBaseSchool(unitId);
					if (basicSchoolinfoDto == null) {
						basicSchoolinfoDto = new BaseSchool();
						basicSchoolinfoDto.setId(unitId);
						// basicSchoolinfoDto.setCode(unitDto.getUnionid().substring(0,
						// 6) + unitDto.getUnionid().substring(9, 12));
						basicSchoolinfoDto.setCode(unitDto.getUnionid());
						basicSchoolinfoDto.setEtohSchoolId(unitDto
								.getEtohSchoolId());
						basicSchoolinfoDto.setName(unitDto.getName());
						basicSchoolinfoDto.setRegion(unitDto.getRegion());
						basicSchoolinfoDto.setType(unitDto.getSchtype());
						basicSchoolinfoDto.setRunschtype(unitDto.getRunschtype());
						basicSchoolinfoDto.setRegionPropertyCode(unitDto.getRegionPropertyCode());
						basicSchoolinfoDto.setInfantyear(3);
						basicSchoolinfoDto.setGradeyear(6);
						basicSchoolinfoDto.setJunioryear(3);
						basicSchoolinfoDto.setSenioryear(3);
						String schooltype = unitDto.getSchtype();  // 学段集合
						if (org.apache.commons.lang.StringUtils.isNotBlank(schooltype)) {
							basicSchoolinfoDto.setSections(schoolService.getSections(schooltype));
						}
						baseSchoolService.addSchool(basicSchoolinfoDto);
					} else {
						basicSchoolinfoDto.setName(unitDto.getName());
//						if (unitDto.getUnionid() != null && unitDto.getUnionid().length() == 12){
							basicSchoolinfoDto.setCode(unitDto.getSchCode());
//						}
						basicSchoolinfoDto.setEtohSchoolId(unitDto.getEtohSchoolId());
						basicSchoolinfoDto.setRegion(unitDto.getRegion());
						basicSchoolinfoDto.setRunschtype(unitDto.getRunschtype());
						basicSchoolinfoDto.setRegionPropertyCode(unitDto.getRegionPropertyCode());
						String schooltype = basicSchoolinfoDto.getType();
						//学校类型发生变化
						if(!org.apache.commons.lang3.StringUtils.equals(schooltype, oldSchType)){
							//判断是否已经有年级信息，否则不能修改
							List<Grade> gradelist = baseGradeService.getGrades(unitId);
							if(CollectionUtils.isNotEmpty(gradelist)){
								promptMessageDto.setErrorMessage("该单位下已有年级信息，不能修改学校类别!");
					            return SUCCESS;
							}
							if (org.apache.commons.lang.StringUtils.isNotBlank(oldSchType)) {
								basicSchoolinfoDto.setSections(schoolService.getSections(oldSchType));
							}
							//初始化年级班级信息
							String sections = schoolService.getSections(oldSchType);
							if(org.apache.commons.lang.StringUtils.isNotBlank(sections)){
								//存在学段类型
								String[] sectionArray = sections.split(",");
								for(int i=0;i<sectionArray.length;i++){
									//初始化所有年级信息
									baseGradeService.initGrades(unitId, Integer.parseInt(sectionArray[i]));
								}
							}
						}
						basicSchoolinfoDto.setType(unitDto.getSchtype());
						baseSchoolService.updateSchool(basicSchoolinfoDto);
						if(!org.apache.commons.lang.StringUtils.equals(oldRegion, unitDto.getRegion())){
							schsecuritySubsystemService.updateRegionCode(basicSchoolinfoDto.getId(), unitDto.getRegion(), unitDto.getParentid(), unitDto.getRegionlevel());
						}
						
					}
				}
			} catch (Exception e) {
				log.error("保存单位时更新学校信息失败:" + e.getMessage());
				promptMessageDto.setErrorMessage("保存单位时更新学校信息失败");
				return SUCCESS;
			}

			if (syncSmsCenter != null && syncSmsCenter.equals("1")) {
				if (delsmscenter != null && delsmscenter.equals("1")) {
					// 删除短信平台的帐号
					// MsgClient.delClient(currentClientid);
					// 停用单位的短信配置
					// smsUseConfigService.saveSmsNotUsed(unitDto.getId());
				} else {
					// 调用接口,同步在短信平台注册帐号
					// String clientId = MsgClient.clientUpdate(unitDto,
					// currentClientid);
					// if (currentClientid == null && clientId != null) {
					// // 启用单位短信配置
					// saveSmsUseConfig(unitDto.getId(), clientId);
					// }
				}
			}
			promptMessageDto.setPromptMessage("保存" + unitDto.getName()
					+ "单位信息成功！");
			promptMessageDto.setOperateSuccess(true);
			SystemLog.log(modID, "修改" + unitDto.getName() + "单位信息成功！");
		} catch (SmsException se) {
			log.error(se.toString());
			String strNote;
			if (currentClientid == null) {
				strNote = "注册";
			} else {
				strNote = "更新";
			}
			//addActionError("同步在短信平台" + strNote + "帐号失败：" + se.getMessage());
			promptMessageDto.setErrorMessage("同步在短信平台" + strNote + "帐号失败：");
			SystemLog.log(modID, "修改" + unitDto.getName() + "单位,同步在短信平台"
					+ strNote + "帐号失败！");
			return SUCCESS;
		} catch (ItemExistsException e) {
//			addFieldError(e.getField(), e.getMessage());
			promptMessageDto.setErrorMessage(e.getMessage());
//			setUnitList();
			return SUCCESS;
		} catch (Exception e) {
			log.error(e.toString());
			promptMessageDto.setErrorMessage("保存单位信息失败");
			//addActionError("保存单位信息失败：" + e.getMessage());
			SystemLog.log(modID, "修改" + unitDto.getName() + "单位信息失败！");
			return SUCCESS;
		}
		return SUCCESS;
	}

	public String getUnitNew() {		
		BaseUnit unitParent = baseUnitService.getBaseUnit(getLoginInfo()
				.getUnitID());
		if (unitParent.getUnittype() == BaseUnit.UNIT_NOTEDU_NOTSCH) {
			setErrorMessage("非教育局单位不允许新增下级单位!");
			return INPUT;

		}
		// String unitId = getLoginInfo().getUser().getUnitid();
		// unitDto = unitService.getUnitNew(unitId);
		unitDto.setCreationTime(new Date());
		unitDto.setMark(BaseUnit.UNIT_MARK_NORAML);// 单位状态
		unitDto.setUsetype(BaseUnit.UNIT_USETYPE_LOCAL);
		unitDto.setAuthorized(BaseUnit.UNIT_AUTHORIZED);
		unitDto.setRegcode(BaseUnit.UNIT_REGCODE_DEF);

		setUnitList();

		userDto.setOrderid(1L);
		userDto.setMark(User.USER_MARK_NORMAL);
		userDto.setType(User.TYPE_ADMIN);
		// userDto.setDeptid(GlobalConstant.VIRTURAL_GROUP_GUID);

		setUnitClassList(getLoginInfo().getUnitID());

		return SUCCESS;
	}

	/**
	 * 获取教育局单位列表,用于学校变更上级单位
	 * 
	 */
	@SuppressWarnings("unchecked")
	private void setUnitList() {
		BaseUnit _unitDto = baseUnitService.getBaseUnit(getLoginInfo()
				.getUnitID());
		if (isSelfDeal()) {
			BaseUnit parentUnit = baseUnitService.getBaseUnit(_unitDto
					.getParentid());
			if (null != parentUnit) {
				listOfUnitDto.add(parentUnit);
			}
		} else {
			listOfUnitDto = baseUnitService.getBaseUnitsByUnionCodeUnitType(
					_unitDto.getUnionid(), BaseUnit.UNIT_CLASS_EDU,
					BaseUnit.UNIT_NOTEDU_NOTSCH);
		}

		// listOfUnitDto = new ArrayList();
		// listOfUnitDto.add(unitService.getUnitDto(getLoginInfo().getUnitID()));
		// List eduList =
		// unitService.getUnderlingUnits(getLoginInfo().getUnitID(),
		// GlobalConstant.UNIT_CLASS_EDU);
		// // 只添加顶级教育局和下属教育局和，其他类型单位均过滤
		// if (!CollectionUtils.isEmpty(eduList)) {
		// UnitDto ud;
		// for (int i = 0; i < eduList.size(); i++) {
		// ud = (UnitDto) eduList.get(i);
		// if (ud.getUnittype() == GlobalConstant.UNIT_EDU_SUB
		// || ud.getUnittype() == GlobalConstant.UNIT_EDU_TOP) {
		// listOfUnitDto.add(ud);
		// }
		// }
		// }
	}

	/**
0	 * 对于新增下属单位的类型过滤,即乡镇级教育局不能新增下属教育局
	 * 
	 * @param unitId
	 */
	@SuppressWarnings("unchecked")
	private void setUnitClassList(String unitId) {
		// 对于新增下属单位的类型过滤,即乡镇级教育局不能新增下属教育局
		BaseUnit ud = baseUnitService.getBaseUnit(unitId);

		List list = mcodedetailService.getMcodeDetails(unitTypeMcodeId);
		unitClassList = new ArrayList();

		if (list == null) {
			return;
		}
		if (ud.getUnittype() == BaseUnit.UNIT_NOTEDU_NOTSCH) {
			return;
		}

		Mcodedetail mcodedetailDto;
		for (int i = 0; i < list.size(); i++) {
			mcodedetailDto = (Mcodedetail) list.get(i);
			// 乡镇级教育局不能新增下属教育局的过滤
			if (mcodedetailDto.getThisId().trim().equals(
					String.valueOf(unittype_subedu))) {
				if (org.apache.commons.lang3.StringUtils.length(ud.getUnionid()) > BaseUnit.FINAL_EDU_LENGHT
						&& mcodedetailDto.getThisId().trim().equals(
								String.valueOf(BaseUnit.UNIT_EDU_SUB))) {
					continue;
				} else {
					unitClassList.add(mcodedetailDto);
				}
			} else if (mcodedetailDto.getThisId().trim().equals(
					String.valueOf(unittype_asp))) {
				unitClassList.add(mcodedetailDto);
			} else if (mcodedetailDto.getThisId().trim().equals(
					String.valueOf(unittype_kg))) {
				unitClassList.add(mcodedetailDto);
			} else if (mcodedetailDto.getThisId().trim().equals(
					String.valueOf(unittype_noedu))) {
				unitClassList.add(mcodedetailDto);

			} else {
				continue;
			}

		}
	}

	private String unitSaveReturn() {
		setUnitList();
		setUnitClassList(unitDto.getParentid());
		return INPUT;
	}

	public String unitSave() {
		setPromptMessageDto(new PromptMessageDto());
		promptMessageDto.setOperateSuccess(false);
		unitId = getLoginInfo().getUser().getUnitid();
		BaseUnit loginUnitDto = baseUnitService.getBaseUnit(unitId);
		if (loginUnitDto.getUnittype() == BaseUnit.UNIT_NOTEDU_NOTSCH) {
			//addActionError("非教育局单位不允许新增下级单位");
			//return unitSaveReturn();
			promptMessageDto.setErrorMessage("非教育局单位不允许新增下级单位");
			return SUCCESS;
		}
		PromptMessageDto pDto = unitValidateNew(unitDto);
		if(!pDto.getOperateSuccess()){
			promptMessageDto = pDto;
			return SUCCESS;
		}
		pDto = userValidateNew(userDto, true);
		if(!pDto.getOperateSuccess()){
			promptMessageDto = pDto;
			return SUCCESS;
		}
		if (unitCountReachedLimit()) {
			promptMessageDto.setErrorMessage("本平台单位数量已达最大限制 "
					+ getSystemSubsystemService().getUnitCountLimit()
					+ "，不能再添加单位!");
			return SUCCESS;
		}

		if (userDto.getPassword() == null || userDto.getPassword().equals("")) {
			//addActionError("单位管理员密码不能为空。");
			promptMessageDto.setErrorMessage("单位管理员密码不能为空。");
			return SUCCESS;
		}
		
	      if (unitclass_school == unitDto.getUnitclass()) {// 如果是学校类型的
	          String temp = baseSchoolService.getSchoolIdByCode(unitDto.getSchCode());
	          if (temp != null && !temp.equals(unitDto.getId())) {
	        	  promptMessageDto.setErrorMessage("学校代码已存在！请修改");
	              return SUCCESS;
	          }
	      }
 
		// 如果是非教育局单位，则不需要验证unionid的唯一性
		if (unitDto.getUnittype() != unitclass_nonedu) {
			// 单位unionid唯一性的验证，若没有则根据所选上级单位生成
			if (unitDto.getUnionid() == null || unitDto.getUnionid().trim().length() == 0) {
				String unionid = baseUnitService.createUnionid(unitDto.getParentid(), unitDto.getUnitclass());
				unitDto.setUnionid(unionid);
			} else {
				if (baseUnitService.getUnitByUnionId(unitDto.getUnionid()) != null) {
					// addFieldError的话，如果后面有select会被挡住，所以用addActionError
					if (unitDto.getUnionid().length() == 2) {
						// addFieldError("province", "该所在行政区域已存在相应单位!");
						//addActionError("该所在行政区域已存在相应的省级单位!");
						promptMessageDto.setErrorMessage("该所在行政区域已存在相应的省级单位!");
					} else if (unitDto.getUnionid().length() == 4) {
						// addFieldError("city", "该所在行政区域已存在相应单位!");
						//addActionError("该所在行政区域已存在相应的市级单位!");
						promptMessageDto.setErrorMessage("该所在行政区域已存在相应的市级单位!");
					} else if (unitDto.getUnionid().length() == 6) {
						// addFieldError("section", "该所在行政区域已存在相应单位!");
						//addActionError("该所在行政区域已存在相应的区县级单位!");
						promptMessageDto.setErrorMessage("该所在行政区域已存在相应的区县级单位!");
					}
					return SUCCESS;
				}
			}
		} else {
			// 对于非教育局单位的新增
			String unionid = baseUnitService.createSpecialUnionid(unitDto
					.getParentid());
			unitDto.setUnionid(unionid);
			unitDto.setUnitclass(BaseUnit.UNIT_CLASS_EDU);
			unitDto.setUnittype(BaseUnit.UNIT_NOTEDU_NOTSCH);
		}
//		unitDto.setOrderid(unitDto.getUnionid());
		if(org.apache.commons.lang3.StringUtils.isNotBlank(unitDto.getUnionid()))
			unitDto.setOrderid(NumberUtils.toLong(unitDto.getUnionid().replaceAll("[^0-9]", "")));
		userDto.setRealname("管理员");// 单位管理员的真实名称即单位名称
		unitDto.setCreationTime(new Date()); // 2009-2-6新增

		try {
			String tunitguid = baseUnitService.saveUnitWithUser(unitDto,
					userDto);

			// 保存单位的同时,保存学校或教育局信息
			try {
				String unitId = tunitguid;
				if (null != unitId
						&& unitDto.getUnitclass().equals(
								BaseUnit.UNIT_CLASS_SCHOOL)) {
					BaseSchool basicSchoolinfoDto = new BaseSchool();
					basicSchoolinfoDto.setId(unitId);
					basicSchoolinfoDto.setEtohSchoolId(unitDto
							.getEtohSchoolId());
					basicSchoolinfoDto.setName(unitDto.getName());
					basicSchoolinfoDto.setRegion(unitDto.getRegion());
					basicSchoolinfoDto.setCreationTime(new Date());
					basicSchoolinfoDto.setType(unitDto.getSchtype());
					basicSchoolinfoDto.setRunschtype(unitDto.getRunschtype());
					basicSchoolinfoDto.setCode(unitDto.getSchCode());
					basicSchoolinfoDto.setInfantyear(3);
					basicSchoolinfoDto.setGradeyear(6);
					basicSchoolinfoDto.setJunioryear(3);
					basicSchoolinfoDto.setSenioryear(3);
					String schooltype = unitDto.getSchtype();  // 学段集合
					if (org.apache.commons.lang.StringUtils.isNotBlank(schooltype)) {
						basicSchoolinfoDto.setSections(schoolService.getSections(schooltype));
					}
					//新增学校单位的区域属性码
					basicSchoolinfoDto.setRegionPropertyCode(unitDto.getRegionPropertyCode());
					baseSchoolService.addSchool(basicSchoolinfoDto);
				} else if (null != unitId
						&& unitDto.getUnitclass().equals(
								BaseUnit.UNIT_CLASS_EDU)) {
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
			} catch (Exception e) {
				log.error("保存单位时更新学校信息失败:" + e.getMessage());
			}

			if (syncSmsCenter != null && syncSmsCenter.equals("1")) {
				// 调用接口,同步在短信平台注册帐号
				// String clientId = MsgClient.clientUpdate(unitDto, null);
				// // 启用单位短信配置
				// saveSmsUseConfig(tunitguid, clientId);
			}

			promptMessageDto.setPromptMessage("注册单位信息成功！该单位统一编号为："
					+ unitDto.getUnionid());
			promptMessageDto.setOperateSuccess(true);
//			promptMessageDto.addOperation(new String[] {
//					"确定",
//					"unitAdmin.action?unitId=" + unitDto.getParentid()
//							+ "&&ec_p=" + this.getEc_p() + "&&ec_crd="
//							+ this.getEc_crd() });
			SystemLog.log(modID, "新增" + unitDto.getName() + "单位信息成功！");
		} catch (SmsException se) {
			log.error(se.toString());
			//addActionError("同步在短信平台注册帐号失败：" + se.getMessage());
			promptMessageDto.setErrorMessage("同步在短信平台注册帐号失败");
			SystemLog.log(modID, "新增" + unitDto.getName() + "单位,同步在短信平台注册帐号失败！");
			return SUCCESS;
		} catch (ItemExistsException itemE) {
			//addFieldError(itemE.getField(), itemE.getMessage());
			promptMessageDto.setErrorMessage(itemE.getMessage());
			return SUCCESS;
		} catch (Exception e) {
			log.error(e.toString());
			//addActionError("保存单位信息失败：" + e.getMessage());
			promptMessageDto.setErrorMessage("保存单位信息失败：");
			SystemLog.log(modID, "新增" + unitDto.getName() + "单位信息失败！");
			return SUCCESS;
		}
		return SUCCESS;
	}

	/**
	 * 平台单位数量限制
	 * 
	 * @param parentUnitId
	 * @return
	 */
	private boolean unitCountReachedLimit() {

		int topLimit = getSystemSubsystemService().getUnitCountLimit();

		if (topLimit == 0) {
			return false;
		}
		int allUnit = baseUnitService.getAllUnitCount();
		return allUnit >= topLimit;
	}
	
	/**
	 * 验证用户名称的可用性 true存在,false不存在
	 * 
	 * @param userName
	 * @return
	 */
	public String validateUserNameAvaliable() {
		setPromptMessageDto(new PromptMessageDto());
		promptMessageDto.setOperateSuccess(false);
		if (org.apache.commons.lang.StringUtils.isBlank(userName)) {
			promptMessageDto.setErrorMessage("请输入账号");
			return SUCCESS;
		}
		if (!userName.matches(systemIniService.getValue(User.SYSTEM_NAME_EXPRESSION))) {
			promptMessageDto.setErrorMessage(systemIniService.getValue(User.SYSTEM_NAME_ALERT));
			return SUCCESS;
		}
		if (baseUserService.getUserNameCount(userName) <= 0) {
			promptMessageDto.setOperateSuccess(true);
			promptMessageDto.setPromptMessage("");
		} else {
			promptMessageDto.setErrorMessage("该账号已存在");
		}
		return SUCCESS;
	}

	/**
	 * 
	 * @return
	 */
	public String try2DeleteUnit() {
		errorMessage = "";
		// UnitRemoteService unitRemoteService = null;
		// try {
		// //系统远程调用
		// unitRemoteService = (UnitRemoteService) SoapProxy
		// .newInstance(UnitRemoteService.class);
		// unitRemoteService.test();
		// } catch (Exception e) {
		// e.printStackTrace();
		// errorMessage =
		// "系统远程调用出现异常，无法判断选择单位是否已经在上局教育局注册过，从而导致删除后，再次新增此单位进行远程注册，可能会导致存在相同的单位而无法注册成功。错误代码为：\n"
		// + e.getMessage();
		//
		// }

		// likui 判断该单位是否已拥有下级单位，如果“是”，则不允许删除，返回提示页。
		List listOfSubUnit = baseUnitService.getAllNormalUnits(unitDto
				.getArrayIds()[0], false);
		if (listOfSubUnit != null && !listOfSubUnit.isEmpty()) {
			promptMessageDto
					.setErrorMessage("该单位下已经存在下属单位，请先删除该单位的下属单位后，再删除该单位！");
			promptMessageDto.setOperateSuccess(false);
			promptMessageDto.addOperation(new String[] { "返回",
					"unitAdmin.action?pageIndex=" + this.getPageIndex() });
			return SUCCESS;
		}

//		if (errorMessage.equals("")) {
//			if (unitDto.getName() == null) {
//				BaseUnit unitDtoTemp = baseUnitService.getBaseUnit(unitDto
//						.getArrayIds()[0]);
//				unitDtoTemp.setArrayIds(unitDto.getArrayIds());
//				unitDto = unitDtoTemp;
//			}
//
//			// 判断远程单位是否已经注册过了
//			// int ret = unitRemoteService.checkReport(unitDto.getPassword(),
//			// unitDto.getId(), unitDto.getName());
//			int ret = CommonParamServiceImpl.ERROR_GUID_NOTEXIST;
//			if (ret == CommonParamServiceImpl.PARAM_GUID_NOTNULL
//					|| ret == CommonParamServiceImpl.ERROR_GUID_NOTEXIST
//					|| ret == CommonParamServiceImpl.ERROR_UNIT_DELETE) {
//				existsUnitOnEdu = "0";// 教育局是否存在此单位 0 不存在，1 存在
//			} else
//				existsUnitOnEdu = "1";
//		}
		// likui 根据具体设置错误信息
		if (getLoginInfo().getUser().getType() == User.USER_TYPE_SUBADMIN
				|| getLoginInfo().getUser().getType() == User.USER_TYPE_TOPADMIN) {
			if (unitDto.getArrayIds() == null
					|| unitDto.getArrayIds().length > 1) {
				promptMessageDto.setErrorMessage("请确认选择了要删除的单位并且只选择了一个！");
			} else {
				unitDto = baseUnitService.getBaseUnit(unitDto.getArrayIds()[0]);
				if (unitDto != null) {
					return SUCCESS;
				} else {
					promptMessageDto.setErrorMessage("该单位不存在或已删除！");
				}
			}
		} else {
			promptMessageDto.setErrorMessage("只有系统创建的admin管理员才能删除下属单位！");
		}
		promptMessageDto.setOperateSuccess(false);
		promptMessageDto.addOperation(new String[] {
				"返回",
				"unitAdmin.action?ec_p" + this.getEc_p() + "&&ec_crd="
						+ this.getEc_crd() });
		return SUCCESS;
	}

	/**
	 * 删除报送单位相关报送数据
	 * 
	 * @return
	 */
	public boolean deleteUnitTransmitInfo(String dUnitId) {
		return baseUnitService.deleteUnitTransmitInfo(dUnitId);
	}

	/**
	 * 删除单位的实际操作(likui)
	 */
	@SuppressWarnings("rawtypes")
	public String deleteUnit() {
		setPromptMessageDto(new PromptMessageDto());
		errorMessage = "";
		// UnitRemoteService unitRemoteService = null;
		// try {
		// //系统远程调用
		// unitRemoteService = (UnitRemoteService) SoapProxy
		// .newInstance(UnitRemoteService.class);
		// unitRemoteService.test();
		// } catch (Exception e) {
		// e.printStackTrace();
		// errorMessage =
		// "系统远程调用出现异常，无法判断选择单位是否已经在上局教育局注册过，从而导致删除后，再次新增此单位进行远程注册，可能会导致存在相同的单位而无法注册成功。错误代码为：\n"
		// + e.getMessage();
		//
		// }

		// likui 判断该单位是否已拥有下级单位，如果“是”，则不允许删除，返回提示页。
		unitDto = baseUnitService.getBaseUnit(unitIds[0]);
		List listOfSubUnit = baseUnitService.getAllNormalUnits(unitIds[0], false);
		if (listOfSubUnit != null && !listOfSubUnit.isEmpty() && unitDto!=null && unitDto.getUnittype()!=unittype_noedu) {
			//非教育局单位可以直接删除，因为没有下级单位
			promptMessageDto.setErrorMessage("该单位下已经存在下属单位，请先删除该单位的下属单位后，再删除该单位！");
			promptMessageDto.setOperateSuccess(false);
			return SUCCESS;
		}

		if (errorMessage.equals("")) {
//			if (unitDto.getName() == null) {
//				BaseUnit unitDtoTemp = baseUnitService.getBaseUnit(unitIds[0]);
//				unitDtoTemp.setArrayIds(unitIds);
//				unitDto = unitDtoTemp;
//			}
			// 判断远程单位是否已经注册过了
			// int ret = unitRemoteService.checkReport(unitDto.getPassword(),
			// unitDto.getId(), unitDto.getName());
			int ret = CommonParamServiceImpl.ERROR_GUID_NOTEXIST;
			if (ret == CommonParamServiceImpl.PARAM_GUID_NOTNULL
					|| ret == CommonParamServiceImpl.ERROR_GUID_NOTEXIST
					|| ret == CommonParamServiceImpl.ERROR_UNIT_DELETE) {
				existsUnitOnEdu = "0";// 教育局是否存在此单位 0 不存在，1 存在
			} else
				existsUnitOnEdu = "1";
		}
		// likui 根据具体设置错误信息
		if (getLoginInfo().getUser().getType() == User.USER_TYPE_SUBADMIN
				|| getLoginInfo().getUser().getType() == User.USER_TYPE_TOPADMIN) {
			if (unitIds == null
					|| unitIds.length > 1) {
				promptMessageDto.setErrorMessage("请确认选择了要删除的单位并且只选择了一个！");
			} else {
				
				if (unitDto == null) {
					promptMessageDto.setErrorMessage("该单位不存在或已删除！");
				} else {
					try {
						baseUnitService.deleteUnit(unitIds);
					} catch (Exception e) {
						e.printStackTrace();
						promptMessageDto
								.setErrorMessage("删除" + unitDto.getName() + "单位失败！");
						promptMessageDto.setOperateSuccess(false);
						SystemLog.log(modID, "删除" + unitDto.getName() + "单位信息失败！");
						return SUCCESS;
					}
					promptMessageDto.setPromptMessage("删除" + unitDto.getName() + "单位成功！");
					promptMessageDto.setOperateSuccess(true);
					SystemLog.log(modID, "删除" + unitDto.getName() + "单位信息成功！");
					return SUCCESS;
				}
			}
		} else {
			promptMessageDto.setErrorMessage("只有系统创建的admin管理员才能删除下属单位！");
		}
		promptMessageDto.setOperateSuccess(false);
		return SUCCESS;
	}

	public String getRegionTree() {
		return SUCCESS;
	}

	public void setBaseUnitService(BaseUnitService baseUnitService) {
		this.baseUnitService = baseUnitService;
	}

	public String getUnitId() {
		return unitId;
	}

	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}

	// public void setUnitDto(UnitDto unitDto) {
	// this.unitDto = unitDto;
	// }

	public BaseUnit getModel() {
		return this.unitDto;
	}

	public User getUserDto() {
		return userDto;
	}
	
	private PromptMessageDto unitValidateNew(BaseUnit unitDto) {
		PromptMessageDto pMessageDto = new PromptMessageDto();
		pMessageDto.setOperateSuccess(false);
		// 单位名称必输与长度验证
		if (Validators.isEmpty(unitDto.getName())) {
			//addFieldError("name", "请输入单位名称!");
			pMessageDto.setErrorMessage("请输入单位名称!");
			return pMessageDto;
		}
		if (StringUtils.getRealLength(unitDto.getName()) > BaseUnit.NAME_LENGTH) {
			pMessageDto.setErrorMessage("请确认单位名称不超过" + BaseUnit.NAME_LENGTH + "个字符");
			return pMessageDto;
		}
		// 单位类型验证
		List<Mcodedetail> list = mcodedetailService
				.getMcodeDetails(unitTypeMcodeId);
		Mcodedetail mcodedetailDto;
		boolean typeExists = false;
		for (int i = 0; i < list.size(); i++) {
			mcodedetailDto = (Mcodedetail) list.get(i);
			if (mcodedetailDto.getThisId().equals(
					String.valueOf(unitDto.getUnittype()))) {
				typeExists = true;
				break;
			}
		}
		if (!typeExists) {
			addFieldError("unittype", "该单位类型暂时不可用！");
			pMessageDto.setErrorMessage("该单位类型暂时不可用！");
			return pMessageDto;
		}
		// 单位排序号的合法性验证
		// if (unitDto.getOrderid() != null) {
		// if (unitDto.getOrderid() > Math.pow(10,
		// GlobalConstant.UNITORDERID_LENGTH)) {
		// addFieldError("orderid", "请确认单位排序号长度不超过"
		// + GlobalConstant.UNITORDERID_LENGTH + "位");
		// return false;
		// }
		// }
		// 上级单位的可用性验证
		if (Validators.isEmpty(unitDto.getParentid())) {
			addFieldError("parentid", "请选择上级单位");
			pMessageDto.setErrorMessage("请选择上级单位");
			return pMessageDto;
		}
		if (!unitDto.getParentid().equals(BaseUnit.TOP_UNIT_GUID)) {
			if (!baseUnitService.checkUnitRight(unitDto.getParentid())) {
				//addFieldError("parentid", "该上级单位暂时不可用！");
				pMessageDto.setErrorMessage("该上级单位暂时不可用！");
				return pMessageDto;
			}
			// 上级单位类型验证
			BaseUnit parentUnit = baseUnitService.getBaseUnit(unitDto
					.getParentid());
			if (parentUnit.getUnitclass() != BaseUnit.UNIT_CLASS_EDU) {
				addFieldError("parentid", "该学校不能新增下级单位");
				pMessageDto.setErrorMessage("该学校不能新增下级单位");
				return pMessageDto;
			}
		}
		pMessageDto.setOperateSuccess(true);
		return pMessageDto;
	}

	private boolean unitValidate(BaseUnit unitDto) {
		// 单位名称必输与长度验证
		if (Validators.isEmpty(unitDto.getName())) {
			addFieldError("name", "请输入单位名称!");
			return false;
		}
		if (StringUtils.getRealLength(unitDto.getName()) > BaseUnit.NAME_LENGTH) {
			addFieldError("name", "请确认单位名称不超过" + BaseUnit.NAME_LENGTH + "个字符");
			return false;
		}
		// 单位类型验证
		List<Mcodedetail> list = mcodedetailService
				.getMcodeDetails(unitTypeMcodeId);
		Mcodedetail mcodedetailDto;
		boolean typeExists = false;
		for (int i = 0; i < list.size(); i++) {
			mcodedetailDto = (Mcodedetail) list.get(i);
			if (mcodedetailDto.getThisId().equals(
					String.valueOf(unitDto.getUnittype()))) {
				typeExists = true;
				break;
			}
		}
		if (!typeExists) {
			addFieldError("unittype", "该单位类型暂时不可用！");
			return false;
		}
		// 单位排序号的合法性验证
		// if (unitDto.getOrderid() != null) {
		// if (unitDto.getOrderid() > Math.pow(10,
		// GlobalConstant.UNITORDERID_LENGTH)) {
		// addFieldError("orderid", "请确认单位排序号长度不超过"
		// + GlobalConstant.UNITORDERID_LENGTH + "位");
		// return false;
		// }
		// }
		// 上级单位的可用性验证
		if (Validators.isEmpty(unitDto.getParentid())) {
			addFieldError("parentid", "请选择上级单位");
			return false;
		}
		if (!unitDto.getParentid().equals(BaseUnit.TOP_UNIT_GUID)) {
			if (!baseUnitService.checkUnitRight(unitDto.getParentid())) {
				addFieldError("parentid", "该上级单位暂时不可用！");
				return false;
			}
			// 上级单位类型验证
			BaseUnit parentUnit = baseUnitService.getBaseUnit(unitDto
					.getParentid());
			if (parentUnit.getUnitclass() != BaseUnit.UNIT_CLASS_EDU) {
				addFieldError("parentid", "该学校不能新增下级单位");
				return false;
			}
		}

		return true;
	}

	/**
	 * 验证用户个属性的合法性
	 * 
	 * @param userDto
	 * @param isNewUser
	 *            是新增用户或者是修改老用户
	 * @return
	 */
	private PromptMessageDto userValidateNew(User userDto, boolean isNewUser) {
		PromptMessageDto pMessageDto = new PromptMessageDto();
		pMessageDto.setOperateSuccess(false);
		// 用户登录名验证
		if (!userDto.getName().matches(systemIniService.getValue(User.SYSTEM_NAME_EXPRESSION))) {
			pMessageDto.setErrorMessage(systemIniService.getValue(User.SYSTEM_NAME_ALERT));
			return pMessageDto;
		}
		if (StringUtils.getRealLength(userDto.getName()) > BaseUnit.NAME_LENGTH) {
			pMessageDto.setErrorMessage(systemIniService.getValue(User.SYSTEM_NAME_ALERT));
			return pMessageDto;
		}
		// 有密码,且新增用户或修改用户且密码更改的，需要密码验证
		if (!Validators.isEmpty(userDto.findClearPassword())) {
			if (isNewUser
					|| (!password_default.equals(userDto.findClearPassword()) && !isNewUser)) {
				// 登陆密码的一致性及必要性验证
				if (!userDto.findClearPassword().matches(systemIniService.getValue(User.SYSTEM_PASSWORD_EXPRESSION))) {
					pMessageDto.setErrorMessage(systemIniService.getValue(User.SYSTEM_PASSWORD_ALERT));
					return pMessageDto;
				}
				if(userDto.findClearPassword().matches(systemIniService.getValue(User.SYSTEM_PASSWORD_STRONG))){
					pMessageDto.setErrorMessage(systemIniService.getValue(User.SYSTEM_PASSWORD_ALERT));
					return pMessageDto;
				}

				if (!userDto.findClearPassword().equalsIgnoreCase(
						userDto.getConfirmPassword())) {
					pMessageDto.setErrorMessage("请确认密码填写一致！");
					return pMessageDto;
				}
			}
		}else{
			pMessageDto.setErrorMessage("请确认登录密码不能为空！");
			return pMessageDto;
		}
		// 用户电子邮件的正确验证
		if (!Validators.isEmpty(userDto.getEmail())) {
			if (!Validators.isEmail(userDto.getEmail())) {
				pMessageDto.setErrorMessage("请确认电子邮件地址填写正确！");
				return pMessageDto;
			}
		}
		pMessageDto.setOperateSuccess(true);
		return pMessageDto;
	}
	
	/**
	 * 验证用户个属性的合法性
	 * 
	 * @param userDto
	 * @param isNewUser
	 *            是新增用户或者是修改老用户
	 * @return
	 */
	private boolean userValidate(User userDto, boolean isNewUser) {
		// 用户登录名验证
		if (Validators.isEmpty(userDto.getName())) {
			addFieldError("userDto.name", "请填写账号！");
			return false;
		} else if (!userDto.getName().matches(systemIniService.getValue(User.SYSTEM_NAME_EXPRESSION))) {
			addFieldError("userDto.name", systemIniService.getValue(User.SYSTEM_NAME_ALERT));
			return false;
		}
		if (StringUtils.getRealLength(userDto.getName()) > BaseUnit.NAME_LENGTH) {
			addFieldError("userDto.name", systemIniService.getValue(User.SYSTEM_NAME_ALERT));
			return false;
		}
		// 有密码,且新增用户或修改用户且密码更改的，需要密码验证
		if (!Validators.isEmpty(userDto.findClearPassword())) {
			if (isNewUser
					|| (!password_default.equals(userDto.findClearPassword()) && !isNewUser)) {
				// 登陆密码的一致性及必要性验证
				if (!userDto.findClearPassword().matches(systemIniService.getValue(User.SYSTEM_PASSWORD_EXPRESSION))) {
					addFieldError("userDto.password", systemIniService.getValue(User.SYSTEM_PASSWORD_ALERT));
					return false;
				}else {
					if(userDto.findClearPassword().matches(systemIniService.getValue(User.SYSTEM_PASSWORD_STRONG))){
						addFieldError("userDto.password", systemIniService.getValue(User.SYSTEM_PASSWORD_ALERT));
						return false;
					}
				}
				
				if (!userDto.findClearPassword().equalsIgnoreCase(
						userDto.getConfirmPassword())) {
					addFieldError("userDto.password", "请确认密码填写一致！");
					return false;
				}
			}
		}else{
			addFieldError("userDto.password", "请确认登录密码不能为空！");
			return false;
		}
		// 用户电子邮件的正确验证
		if (!Validators.isEmpty(userDto.getEmail())) {
			if (!Validators.isEmail(userDto.getEmail())) {
				addFieldError("userDto.email", "请确认电子邮件地址填写正确！");
				return false;
			}
		}
		return true;
	}

	public String unitGetRegion(){
		int regioncodelength = 6;
		Object[] obj = new Object[6];
		if (unitId == null || unitId.trim().length() == 0) {
			return null;
		}
		unitDto = baseUnitService.getBaseUnit(unitId);
		// int orderid = unitService.getAvaOrderid(unitId);
		String orderid = unitDto.getUnionid();
		if (unitDto == null || unitDto.getUnionid() == null) {
			return null;
		}
		String unionId = unitDto.getUnionid();
		//String regionCode = unitDto.getRegion();TODO
		if (unionId.length() <= regioncodelength) {
			String perunionId;
			Region regionDto;
			int i = 0;
			for (i = 0; i < unionId.length(); i += 2) {
				perunionId = unionId.substring(0, i + 2);
				regionDto = regionService.getRegion(perunionId);
				obj[i / 2] = new Object[] { regionDto };
			}
			if (i < regioncodelength) {
				List list = regionService.getSubRegions(unionId);
				obj[i / 2] = list.toArray(new Object[] {});
			}
		}
		obj[3] = new Object[] { orderid };// 排序编号
		obj[4] = new Object[] { unitDto.getRegionlevel() + 1 };// 行政级别
		obj[5] = new Object[] { unionId.length() == BaseUnit.FINAL_EDU_LENGHT + 3 };// 乡镇教育局标识
		objectJson = obj;
		return SUCCESS;
	}
	
	public String unitGetOrder() {
		Object obj;
		unitDto = baseUnitService.getBaseUnit(unitId);
		// Integer orderid = unitService.getAvaOrderid(unitId);
		String orderid = unitDto.getUnionid();
		obj = new Object[] {
				orderid,
				unitDto.getRegionlevel() + 1,
				unitDto.getUnionid().length() == (BaseUnit.FINAL_EDU_LENGHT + 3) };
		objectJson = new Object[]{obj};
		setUnitList();
		return SUCCESS;
	}
	
	@SuppressWarnings("unchecked")
	public Object[] getRegion(String unitId) {
		int regioncodelength = 6;
		Object[] obj = new Object[6];
		if (unitId == null || unitId.trim().length() == 0) {
			return null;
		}
		unitDto = baseUnitService.getBaseUnit(unitId);
		// int orderid = unitService.getAvaOrderid(unitId);
		String orderid = unitDto.getUnionid();
		if (unitDto == null || unitDto.getUnionid() == null) {
			return null;
		}
		String unionId = unitDto.getUnionid();
		if (unionId.length() <= regioncodelength) {
			String perunionId;
			Region regionDto;
			int i = 0;
			for (i = 0; i < unionId.length(); i += 2) {
				perunionId = unionId.substring(0, i + 2);
				regionDto = regionService.getRegion(perunionId);
				obj[i / 2] = new Object[] { regionDto };
			}
			if (i < regioncodelength) {
				List list = regionService.getSubRegions(unionId);
				obj[i / 2] = list.toArray(new Object[] {});
			}
		}
		obj[3] = new Object[] { orderid };// 排序编号
		obj[4] = new Object[] { unitDto.getRegionlevel() + 1 };// 行政级别
		obj[5] = new Object[] { unionId.length() == BaseUnit.FINAL_EDU_LENGHT + 3 };// 乡镇教育局标识
		return obj;
	}

	public Object getOrder(String unitId) {
		Object obj;
		unitDto = baseUnitService.getBaseUnit(unitId);
		// Integer orderid = unitService.getAvaOrderid(unitId);
		String orderid = unitDto.getUnionid();
		obj = new Object[] {
				orderid,
				unitDto.getRegionlevel() + 1,
				unitDto.getUnionid().length() == (BaseUnit.FINAL_EDU_LENGHT + 3) };
		return obj;
	}
	
	public String unitGetUnionId(){
		unitUnionId = baseUnitService.createUnionid(unitId, Integer.valueOf(unitClass));
		return unitUnionId;
	}

	public String getUnionid(String unitId, Integer unitClass) {
		unitUnionId = baseUnitService.createUnionid(unitId, unitClass);
		return unitUnionId;
	}

	public String getPasswordGeneric() {
		unitIni = unitIniService.getUnitOption(getLoginInfo().getUnitID(),
				UnitIni.UNIT_PASSWORD_CONFIG);
		if (unitIni == null) {
			unitIniService.saveUnitPasswordOption(getLoginInfo().getUnitID());
			unitIni = unitIniService.getUnitOption(getLoginInfo().getUnitID(),
					UnitIni.UNIT_PASSWORD_CONFIG);
		}
		return SUCCESS;
	}

	public String savePasswordGeneric() {
		setPromptMessageDto(new PromptMessageDto());
		if (genericPass.equals(String
				.valueOf(BaseConstant.PASSWORD_GENERIC_RULE_UNIONIZE))
				&& (consistentPass == null || consistentPass.length() == 0)) {
			// 如果使用统一密码且默认密码留空，则设置密码规则为空规则
			genericPass = String
					.valueOf(BaseConstant.PASSWORD_GENERIC_RULE_NULL);
			consistentPass = null;
		}
		// 如果是统一规则，且显示密码则不需要验证
		if (genericPass.equals(String
				.valueOf(BaseConstant.PASSWORD_GENERIC_RULE_UNIONIZE))) {
			if (!(consistentPass.equals(password_default) && confirmPass
					.equals(password_default))) {
				
			} else {
				consistentPass = null;
			}
		}
		try {
			unitIniService.updateUnitOption(getLoginInfo().getUnitID(),
					UnitIni.UNIT_PASSWORD_CONFIG, genericPass, consistentPass);
			SystemLog.log(modID, "密码生成规则修改");
			promptMessageDto.setOperateSuccess(true);
			promptMessageDto.setPromptMessage("保存成功！");
		} catch (Exception e) {
			log.error(e.toString());
			promptMessageDto.setOperateSuccess(false);
			promptMessageDto.setPromptMessage("保存失败！");
		}
		return SUCCESS;
	}

	@SuppressWarnings("unchecked")
	public Object[] getUnitFaintness(String faintnessName) {
		User userDto = getLoginInfo().getUser();
		if (userDto.getType() != User.USER_TYPE_TOPADMIN) {
			return null;
		}
		List list = baseUnitService.getUnitsByFaintness(faintnessName);
		return list.toArray(new Object[] {});
	}

	public List<Region> getSubRegion(String regionCode) {
		List<Region> list = regionService.getSubRegions(regionCode);
		return list;
	}

	public String getRemoteRegister() {

		String rootName = "请选择欲注册的单位";
		WebTree webTree = baseUnitService.getUnitTree(getLoginInfo()
				.getUnitID(), unitIds, rootName);
		xtreeScript = webTree.toString();
		treeName = webTree.getNodeName();
		return SUCCESS;
	}

	public boolean remoteTest() {
		boolean result = false;
		try {
			UnitRemoteService unitRemoteService;
			/**
			 * 如果和省平台对接，则上报采用其他接口
			 */
			if (isDeployWithProvince()) {
				String serviceUrl = systemDeployService.getProvinceWebService()
						+ "UnitRemoteService";
				unitRemoteService = (UnitRemoteService) SoapProxy
						.getWebServiceObject(serviceUrl,
								UnitRemoteService.class);
			} else {
				unitRemoteService = (UnitRemoteService) SoapProxy
						.newInstance(UnitRemoteService.class);
			}
			String test = unitRemoteService.test();
			if (BaseConstant.TEST_STRING.equals(test)) {
				result = true;
			}
		} catch (Exception e) {
			// 远程服务启动异常
			result = false;
		}
		return result;
	}

	private boolean isDeployWithProvince() {
		return systemDeployService.isDeployWithProvince();
	}

	/**
	 * 单位远程注册
	 * 
	 * @return
	 */
	public String remoteRegister() {
		UnitRemoteService unitRemoteService = null;
		setPromptMessageDto(new PromptMessageDto());
		unitIds = unitDto.getArrayIds();
		getRemoteRegister();

		if (!baseUnitService.getUnit(getLoginInfo().getUser().getUnitid())
				.getParentid().equals(BaseUnit.TOP_UNIT_GUID)) {
			addActionError("只有顶级教育局才能使用单位远程注册");
			return INPUT;
		}

		try {
			/**
			 * 如果和省平台对接，则上报采用其他接口
			 */
			if (isDeployWithProvince()) {
				String serviceUrl = systemIniService
						.getValue("SYSTEM.PRVFLAT.SERVICE.URL")
						+ "/services/" + "UnitRemoteService";
				unitRemoteService = (UnitRemoteService) SoapProxy
						.getWebServiceObject(serviceUrl,
								UnitRemoteService.class);
			} else {
				unitRemoteService = (UnitRemoteService) SoapProxy
						.newInstance(UnitRemoteService.class);
			}

			if (!BaseConstant.TEST_STRING.equals(unitRemoteService.test())) {
				throw new RuntimeException("远程服务暂时不可用");
			}
		} catch (Exception e) {
			log.error(e.toString());

			promptMessageDto
					.setErrorMessage("远程注册服务暂时不可用，请检查本地网络设置及远程服务设置是否正确！");
			promptMessageDto.setOperateSuccess(false);
			promptMessageDto.addOperation(new String[] { "返回",
					"unitAdmin-remoteRegister.action" });
			return PROMPTMSG;
		}

		Map<BaseUnit, User> objMap = new HashMap<BaseUnit, User>();

		String[] ids = unitDto.getArrayIds();
		if (ids == null) {
			promptMessageDto.setErrorMessage("请选择要远程注册的单位");
			promptMessageDto.setOperateSuccess(false);
			promptMessageDto.addOperation(new String[] { "返回",
					"unitAdmin.action" });
			return PROMPTMSG;
		}
		SystemVersion sv = systemVersionService.getSystemVersion();
		try {
			Map<String, BaseUnit> unitMap = baseUnitService.getBaseUnitMap(ids);
			List<BaseSchool> listOfSch = baseSchoolService.getBaseSchools(ids);
			Map<String, String> mapOfSch = new HashMap<String, String>();
			for (BaseSchool dto : listOfSch) {
				mapOfSch.put(dto.getId(), dto.getType());
			}
			Map<String, User> userMap = baseUserService.getAdminUserMap(ids);

			BaseUnit unitDto;
			User userDto;
			BaseUnit[] unitObjs = new BaseUnit[ids.length];
			User[] userObjs = new User[ids.length];
			String id;
			for (int i = 0; i < ids.length; i++) {
				id = ids[i];
				unitDto = unitMap.get(id);
				userDto = userMap.get(id);
				if (unitDto != null && userDto != null
						&& !objMap.containsKey(unitDto)) {
					if (org.apache.commons.lang.StringUtils.isBlank(userDto
							.getPassword())) {
						PWD pwd = new PWD("");
						userDto.setPassword(pwd.encode());// 用户密码加密
					}
					if (sv != null) {
						unitDto.setSysVersion(sv.getCurversion());// 单位当前使用产品版本
					}
					// 这个属性主要是为了适应省平台而设置的
					String schType = mapOfSch.get(unitDto.getId());
					unitDto.setSchtype(schType);
					unitObjs[i] = unitDto;
					userObjs[i] = userDto;
				}
			}

			this.setWebContext(unitObjs);

			if (!isDeployWithProvince()) {
				unitRegisterResultDto = unitRemoteService
						.saveRemoteUnitRegister(unitObjs, userObjs);
			} else {
				unitRegisterResultDto = unitRemoteService
						.saveRemoteUnitRegister2Prv(unitObjs);
			}

			// 如有需要的话，更新本地单位统一编号
			if (unitRegisterResultDto.findUpdateUnionIdUnit() != null) {
				baseUnitService.updateUnitUnionId(unitRegisterResultDto
						.findUpdateUnionIdUnit());
			}
		} catch (UnitRegisterException e) {
			// 远程注册单位出现错误信息，返回错误提示页面
			return remoteResult(e.getUnitRegisterResultDto());
		} catch (Exception e) {
			log.error(e.toString());
			if (e.getMessage() != null) {
				addActionError("远程注册单位时出现错误："
						+ e.getMessage().replaceAll("\"", "'"));
			} else {
				addActionError("远程注册单位时出现错误");
			}
			return INPUT;
		}

		return remoteResult(unitRegisterResultDto);
	}

	@SuppressWarnings("unchecked")
	public String remoteResult(UnitRegisterResultDto unitRegisterResultDto) {
		if (unitRegisterResultDto == null) {
			addActionError("远程注册服务暂时不可用！");
			return INPUT;
		}
		if (unitRegisterResultDto.hasError()) {
			String[] unitIds = unitRegisterResultDto.getErrorUnitIds();
			Map<String, BaseUnit> unitMap = baseUnitService
					.getBaseUnitMap(unitIds);
			errorList = new ArrayList();
			for (int i = 0; i < unitIds.length; i++) {
				errorList.add(new Object[] { unitMap.get(unitIds[i]).getName(),
						unitRegisterResultDto.getErrorContent()[i] });
			}
			// 设置原选中欲注册单位的ids，用于返回原远程注册页面，同时恢复原选中单位选中状态
			setUnitIds(unitRegisterResultDto.getSelectUnitIds());
			return SUCCESS;
		}

		String[] succUnitIds = unitRegisterResultDto.getRegSuccUnitIds();
		String[] modifyUnitIds = unitRegisterResultDto.getModifyUnitIds();
		String[] errorUnitIds = unitRegisterResultDto.getErrorUnitIds();

		if (succUnitIds != null) {
			succUnitList = baseUnitService.getBaseUnits(succUnitIds);
		}
		if (modifyUnitIds != null) {
			modifyUnitList = baseUnitService.getBaseUnits(modifyUnitIds);
		}
		if (errorUnitIds != null) {
			errorList = baseUnitService.getUnits(errorUnitIds);
		}
		return SUCCESS;
	}

	/**
	 * 远程测试欲导出注册信息的单位完整性
	 * 
	 * @param arrayIds
	 * @return
	 */
	public boolean exportTest(String[] arrayIds) {
		if (arrayIds == null || arrayIds.length == 0) {
			return false;
		}

		Map<String, BaseUnit> unitMap = baseUnitService
				.getBaseUnitMap(arrayIds);
		Map<String, User> userMap = baseUserService.getAdminUserMap(arrayIds);

		for (int i = 0; i < arrayIds.length; i++) {
			if (!(unitMap.containsKey(arrayIds[i]) && userMap
					.containsKey(arrayIds[i]))) {
				return false;
			}
		}
		return true;
	}

	public void export2XML() {
		unitIds = unitDto.getArrayIds();
		if (unitIds == null || unitIds.length == 0) {
			addActionError("请选择欲导出的单位");
			return;
		}

		Map<String, BaseUnit> unitMap = baseUnitService.getBaseUnitMap(unitIds);
		Map<String, User> userMap = baseUserService.getAdminUserMap(unitIds);

		List<XmlUnitInfo> xmlList = new ArrayList<XmlUnitInfo>();
		XmlUnitInfo xmlInfo;
		User userDto;
		for (int i = 0; i < unitIds.length; i++) {
			if (unitMap.containsKey(unitIds[i])
					&& userMap.containsKey(unitIds[i])) {
				xmlInfo = new XmlUnitInfo();
				xmlInfo.setUnitDto(unitMap.get(unitIds[i]));

				// 用户密码加密
				userDto = userMap.get(unitIds[i]);
				PWD pwd = new PWD(userDto.findClearPassword());
				userDto.setPassword(pwd.encode());

				xmlInfo.setUserDto(userDto);
				xmlList.add(xmlInfo);
			}
		}

		if (CollectionUtils.isEmpty(xmlList)) {
			addActionError("您选择的单位暂时不可用，所以不能导出xml文件");
			return;
		}
		XmlUnitFile xmlFile = new XmlUnitFile();
		try {
			xmlFile.buildXmlContent(xmlList);
			xmlFile.saveAsFile("registerOffline.xml");
		} catch (Exception e) {
			e.printStackTrace();
			addActionError("导出xml文件时出现错误:" + e.getMessage());
			return;
		}
	}

	public String importXML() {
		request = getRequest();
		setPromptMessageDto(new PromptMessageDto());

		Map<String, BaseUnit> unitMap = new HashMap<String, BaseUnit>();
		Map<String, User> userMap = new HashMap<String, User>();

		if (request instanceof MultiPartRequestWrapper) {
			MultiPartRequestWrapper requestWrapper = (MultiPartRequestWrapper) request;
			Enumeration<String> e = requestWrapper.getFileParameterNames();
			if (!e.hasMoreElements()) {
				return importReturn("所选择的注册信息文件无效或不存在！", false);
			}

			String fieldName, fileName;
			File uploadFile;
			int pos;
			while (e.hasMoreElements()) {
				fieldName = String.valueOf(e.nextElement());
				uploadFile = ((File[]) requestWrapper.getFiles(fieldName))[0];
				fileName = requestWrapper.getFileNames(fieldName)[0];

				pos = fileName.lastIndexOf(".");
				if (pos < 0
						|| !fileName.substring(pos + 1).equalsIgnoreCase("xml")) {
					return importReturn("请确认导入的注册信息文件必须为xml文件", false);
				}

				try {
					XmlUnitFile xmlFile = new XmlUnitFile();
					List<XmlUnitInfo> list = xmlFile
							.getUnitInfoList(uploadFile);

					if (CollectionUtils.isEmpty(list)) {
						return INPUT;
					}

					for (int i = 0; i < list.size(); i++) {
						if (list.get(i).getUnitDto() != null
								&& list.get(i).getUserDto() != null) {
							unitMap.put(list.get(i).getUnitDto().getId(), list
									.get(i).getUnitDto());
							userMap.put(list.get(i).getUserDto().getUnitid(),
									list.get(i).getUserDto());
						}
					}

					UnitRegisterResultDto unitRegisterResultDto = unitRemoteService
							.saveRemoteUnitRegister(unitMap.values().toArray(
									new BaseUnit[] {}), userMap.values()
									.toArray(new User[] {}));

					return importTip(unitRegisterResultDto, unitMap, userMap);
				} catch (UnitRegisterException er) {
					return importTip(er.getUnitRegisterResultDto(), unitMap,
							userMap);
				} catch (Exception ec) {
					ec.printStackTrace();
					return importReturn("导入注册信息时出错：" + ec.getMessage(), false);
				}
			}
		}

		return importReturn("导入注册信息成功！", true);

	}

	/**
	 * 导入错误信息提示
	 * 
	 * @param message
	 * @param isSuccess
	 * @return
	 */
	private String importReturn(String message, boolean isSuccess) {
		setPromptMessageDto(new PromptMessageDto());
		promptMessageDto.setOperateSuccess(isSuccess);
		if (!isSuccess) {
			promptMessageDto.setErrorMessage(message);
		} else {
			promptMessageDto.setPromptMessage(message);
		}
		promptMessageDto.addOperation(new String[] { "确定",
				"unitAdmin-remoteRegister.action" });
		return PROMPTMSG;
	}

	/**
	 * 导入返回提示信息
	 * 
	 * @param unitRegisterResultDto
	 * @param unitMap
	 * @param userMap
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private String importTip(UnitRegisterResultDto unitRegisterResultDto,
			Map<String, BaseUnit> unitMap, Map<String, User> userMap) {
		if (unitRegisterResultDto.hasError()) {
			String[] unitIds = unitRegisterResultDto.getErrorUnitIds();
			errorList = new ArrayList();
			for (int i = 0; i < unitIds.length; i++) {
				errorList.add(new Object[] { unitMap.get(unitIds[i]).getName(),
						unitRegisterResultDto.getErrorContent()[i] });
			}
			return SUCCESS;
		}

		String[] succUnitIds = unitRegisterResultDto.getRegSuccUnitIds();
		String[] modifyUnitIds = unitRegisterResultDto.getModifyUnitIds();
		String[] errorUnitIds = unitRegisterResultDto.getErrorUnitIds();

		if (succUnitIds != null) {
			succUnitList = baseUnitService.getBaseUnits(succUnitIds);
		}
		if (modifyUnitIds != null) {
			modifyUnitList = baseUnitService.getBaseUnits(modifyUnitIds);
		}
		if (errorUnitIds != null) {
			errorList = baseUnitService.getUnits(errorUnitIds);
		}
		return SUCCESS;
	}

	public String getUnitAudit() {
		String rootName = "请选择欲操作的单位";
		WebTree webTree = baseUnitService.getUnitTreeWithMark(getLoginInfo()
				.getUnitID(), unitIds, rootName, 0);
		xtreeScript = webTree.toString();
		treeName = webTree.getNodeName();
		return SUCCESS;
	}

	// 新增内容 start 2009-1-7
	public String getUnitRecommend() {
		String rootName = "请选择欲操作的单位";
		WebTree webTree = baseUnitService.getUnitTreeWithMark(getLoginInfo()
				.getUnitID(), unitIds, rootName, 1);
		xtreeScript = webTree.toString();
		treeName = webTree.getNodeName();
		return SUCCESS;
	}

	// end 2009-1-7
	public String saveUnitAudit() {
		setPromptMessageDto(new PromptMessageDto());
		String tempIds[] = unitDto.getArrayIds();
		if (tempIds == null || tempIds.length == 0) {
			if (unitIds == null) {
				promptMessageDto.setErrorMessage("请先选择欲操作单位!");
				promptMessageDto.addOperation(new String[] { "返回",
						"unitAdmin-audit.action" });
				return PROMPTMSG;
			}
		} else {
			if (tempIds.length == 1
					&& tempIds[0].equals(getLoginInfo().getUnitID())) {
				promptMessageDto.setErrorMessage("不能对本单位操作！");
				promptMessageDto.addOperation(new String[] { "返回",
						"unitAdmin-audit.action" });
				return PROMPTMSG;
			}
		}

		// 去除当前单位（当前单位由上级部门审核）
		List<String> list = new ArrayList<String>();
		String unitID = this.getLoginInfo().getUnitID();
		for (int i = 0; i < tempIds.length; i++) {
			if (unitID.equals(tempIds[i]))
				continue;
			list.add(tempIds[i]);
		}
		unitIds = new String[list.size()];
		for (int i = 0; i < list.size(); i++) {
			unitIds[i] = list.get(i);
		}
		// unitIds = (String[])(list.toArray());

		String tip = null;

		switch (unitDto.getMark().intValue()) {
		case BaseUnit.UNIT_MARK_NOTAUDIT:
			tip = "未审核";
			break;
		case BaseUnit.UNIT_MARK_NORAML:
			tip = "审核";
			break;
		case BaseUnit.UNIT_MARK_LOCK:
			tip = "锁定";
			break;
		default:
			tip = "操作";
		}

		if (unitIds == null) {
			addActionError("请先选择欲" + tip + "单位！");
			return INPUT;
		}

		StringBuffer unitName = new StringBuffer();
		try {
			Map<String, BaseUnit> unitMap = baseUnitService
					.getBaseUnitMap(unitIds);
			Collection<BaseUnit> unitColl = unitMap.values();
			BaseUnit dto;
			List<String> listOfNeedUpdateUnitId = new ArrayList<String>();
			for (Iterator<BaseUnit> iter = unitColl.iterator(); iter.hasNext();) {
				dto = (BaseUnit) iter.next();
				listOfNeedUpdateUnitId.add(dto.getId());
				unitName.append(dto.getName());
				if (iter.hasNext()) {
					unitName.append("、");
				}
			}

			baseUnitService.updateUnitMark(listOfNeedUpdateUnitId
					.toArray(new String[0]), unitDto.getMark());
		} catch (Exception e) {
			e.printStackTrace();
			addActionError(tip + "单位时出错：" + e.getMessage());
			return INPUT;
		}

		if (unitName.toString().equals("")) {
			promptMessageDto.setPromptMessage(tip + unitName.toString()
					+ "单位成功！");
		} else {
			promptMessageDto.setPromptMessage(tip + unitName.toString()
					+ "单位成功！");
		}

		promptMessageDto.setOperateSuccess(true);
		promptMessageDto.addOperation(new String[] { "确定",
				"unitAdmin-audit.action" });
		return PROMPTMSG;
	}

	/**
	 * 设置非报送单位webcontext地址
	 * 
	 * @param unitDtos
	 */
	private void setWebContext(BaseUnit[] unitDtos) {
		if (unitDtos == null) {
			return;
		}
		String requestURL = getRequest().getRequestURL().toString();
		String requestURI = getRequest().getRequestURI();
		@SuppressWarnings("unused")
		String convertUrl = requestURL.substring(0, requestURL.length()
				- requestURI.length())
				+ getRequest().getContextPath();
	}

	public int getUnitclass_edu() {
		return unitclass_edu;
	}

	public int getUnitclass_school() {
		return unitclass_school;
	}

	public int getUnittype_subedu() {
		return unittype_subedu;
	}
	public int getUnittype_asp() {
		return unittype_asp;
	}
	public int getUnittype_kg() {
		return unittype_kg;
	}
	
	public void setUnitclass_school(int unitclass_school) {
		this.unitclass_school = unitclass_school;
	}

	public void setUnitclass_edu(int unitclass_edu) {
		this.unitclass_edu = unitclass_edu;
	}

	public void setUnitclass_nonedu(int unitclass_nonedu) {
		this.unitclass_nonedu = unitclass_nonedu;
	}

	public void setUnittype_subedu(int unittype_subedu) {
		this.unittype_subedu = unittype_subedu;
	}

	public void setUnittype_asp(int unittype_asp) {
		this.unittype_asp = unittype_asp;
	}

	public void setUnittype_noedu(int unittype_noedu) {
		this.unittype_noedu = unittype_noedu;
	}

	public void setUnittype_kg(int unittype_kg) {
		this.unittype_kg = unittype_kg;
	}

	public void setUnitmark_noaudit(int unitmark_noaudit) {
		this.unitmark_noaudit = unitmark_noaudit;
	}

	public void setUnitmark_normal(int unitmark_normal) {
		this.unitmark_normal = unitmark_normal;
	}

	public void setUnitmark_lock(int unitmark_lock) {
		this.unitmark_lock = unitmark_lock;
	}

	public void setPassword_default(String password_default) {
		this.password_default = password_default;
	}

	public void setUnitCountry(int unitCountry) {
		this.unitCountry = unitCountry;
	}

	public void setModID(String modID) {
		this.modID = modID;
	}

	public void setUnitTypeMcodeId(String unitTypeMcodeId) {
		this.unitTypeMcodeId = unitTypeMcodeId;
	}

	public void setUnitClassMcodeId(String unitClassMcodeId) {
		this.unitClassMcodeId = unitClassMcodeId;
	}

	public void setUnitMarkMcodeId(String unitMarkMcodeId) {
		this.unitMarkMcodeId = unitMarkMcodeId;
	}

	public void setUnitNameLength(int unitNameLength) {
		this.unitNameLength = unitNameLength;
	}

	public void setUnitMarkWithOutAutidt(int unitMarkWithOutAutidt) {
		this.unitMarkWithOutAutidt = unitMarkWithOutAutidt;
	}

	public void setPasswordName(int passwordName) {
		this.passwordName = passwordName;
	}

	public void setPasswordNull(int passwordNull) {
		this.passwordNull = passwordNull;
	}

	public void setPasswordUnionize(int passwordUnionize) {
		this.passwordUnionize = passwordUnionize;
	}

	public static void setEXPORT_FIELD_TITLES(String[] eXPORT_FIELD_TITLES) {
		EXPORT_FIELD_TITLES = eXPORT_FIELD_TITLES;
	}

	public static void setPROPERTY_NAMES(String[] pROPERTY_NAMES) {
		PROPERTY_NAMES = pROPERTY_NAMES;
	}

	public void setListOfUnitDto(List<BaseUnit> listOfUnitDto) {
		this.listOfUnitDto = listOfUnitDto;
	}

	public void setUnitClassList(List<Mcodedetail> unitClassList) {
		this.unitClassList = unitClassList;
	}

	public void setUnitDto(BaseUnit unitDto) {
		this.unitDto = unitDto;
	}

	public void setUserDto(User userDto) {
		this.userDto = userDto;
	}

	public void setUnitIni(UnitIni unitIni) {
		this.unitIni = unitIni;
	}

	public void setTopAdmin(boolean isTopAdmin) {
		this.isTopAdmin = isTopAdmin;
	}

	public void setTopUnit(boolean isTopUnit) {
		this.isTopUnit = isTopUnit;
	}

	public void setSelfDeal(boolean isSelfDeal) {
		this.isSelfDeal = isSelfDeal;
	}

	public void setXtreeScript(String xtreeScript) {
		this.xtreeScript = xtreeScript;
	}

	public void setTreeName(String treeName) {
		this.treeName = treeName;
	}

	public void setSuccUnitList(List<BaseUnit> succUnitList) {
		this.succUnitList = succUnitList;
	}

	public void setModifyUnitList(List<BaseUnit> modifyUnitList) {
		this.modifyUnitList = modifyUnitList;
	}

	public void setErrorList(List errorList) {
		this.errorList = errorList;
	}

	public void setUserMap(Map<String, User> userMap) {
		this.userMap = userMap;
	}

	public String getPassword_default() {
		return password_default;
	}

	public void setUnitIniService(UnitIniService unitIniService) {
		this.unitIniService = unitIniService;
	}

	public UnitIni getUnitIniDto() {
		return unitIni;
	}

	public void setGenericPass(String genericPass) {
		this.genericPass = genericPass;
	}

	public void setConsistentPass(String consistentPass) {
		this.consistentPass = consistentPass;
	}

	public int getPasswordName() {
		return passwordName;
	}

	public int getPasswordNull() {
		return passwordNull;
	}

	public int getPasswordUnionize() {
		return passwordUnionize;
	}

	public int getUnitCountry() {
		return unitCountry;
	}

	public List<Mcodedetail> getUnitClassList() {
		return unitClassList;
	}

	public void setMcodedetailService(McodedetailService mcodedetailService) {
		this.mcodedetailService = mcodedetailService;
	}

	public boolean isTopAdmin() {
		if (getLoginInfo().getUser().getType() == User.USER_TYPE_TOPADMIN) {
			isTopAdmin = true;
		} else {
			isTopAdmin = false;
		}
		return isTopAdmin;
	}

	public void setRegionService(RegionService regionService) {
		this.regionService = regionService;
	}

	public int getUnitclass_nonedu() {
		return unitclass_nonedu;
	}

	public int getUnitNameLength() {
		return unitNameLength;
	}

	public String getUserNameFieldTip() {
		return systemIniService.getValue(User.SYSTEM_NAME_ALERT);
	}

	public String getUserPasswordFieldTip() {
		return systemIniService.getValue(User.SYSTEM_PASSWORD_ALERT);
	}

	public void setConfirmPass(String confirmPass) {
		this.confirmPass = confirmPass;
	}

	public String getConfirmPass() {
		return confirmPass;
	}

	public String getConsistentPass() {
		return consistentPass;
	}

	public String getXtreeScript() {
		return xtreeScript;
	}

	public String getTreeName() {
		return treeName;
	}

	public boolean isEISSUnit() {
		BaseUnit eissUnit = baseUnitService.getBaseUnit(getLoginInfo()
				.getUnitID());
		if (eissUnit != null) {
			if (BaseUnit.TOP_UNIT_GUID.equals(eissUnit.getParentid())
					&& eissUnit.getUnitclass() == BaseUnit.UNIT_CLASS_SCHOOL) {
				isEISSUnit = true;
			} else {
				isEISSUnit = false;
			}
		}
		return isEISSUnit;
	}

	public void setEISSUnit(boolean isEISSUnit) {
		this.isEISSUnit = isEISSUnit;
	}

	public int getUnitMarkWithOutAutidt() {
		return unitMarkWithOutAutidt;
	}

	public UnitRegisterResultDto getUnitRegisterResultDto() {
		return unitRegisterResultDto;
	}

	public void setUnitRegisterResultDto(
			UnitRegisterResultDto unitRegisterResultDto) {
		this.unitRegisterResultDto = unitRegisterResultDto;
	}

	@SuppressWarnings("unchecked")
	public List getErrorList() {
		return errorList;
	}

	public List<BaseUnit> getModifyUnitList() {
		return modifyUnitList;
	}

	public List<BaseUnit> getSuccUnitList() {
		return succUnitList;
	}

	public String[] getUnitIds() {
		return unitIds;
	}

	public void setUnitIds(String[] unitIds) {
		this.unitIds = unitIds;
	}

	public void setSystemIniService(SystemIniService systemIniService) {
		this.systemIniService = systemIniService;
	}

	public void setMcodeService(McodeService mcodeService) {
		this.mcodeService = mcodeService;
	}

	public boolean isTopUnit() {
		BaseUnit unit = baseUnitService.getBaseUnit(getLoginInfo().getUnitID());
		if (unit != null) {
			isTopUnit = unit.getParentid().equals(BaseUnit.TOP_UNIT_GUID);
		}
		return isTopUnit;
	}

	/**
	 * 是否自己查看，还是上级教育局查看
	 * 
	 * @author "yangk" Oct 20, 2010 7:27:33 PM
	 * @return
	 */
	public boolean isSelfDeal() {
		if (unitDto == null) {
			return isSelfDeal;
		}
		if (getLoginInfo().getUnitID().equals(unitDto.getId())) {
			return true;
		}
		return isSelfDeal;
	}
	
    public boolean isConnectPassport() {
        return systemDeployService.isConnectPassport();
    }

	/**
	 * 检查系统参数配置里的"SYSTEM.SMSCENTER"是否启用
	 * 
	 * @return
	 */
	public boolean isUseSmsCenter() {
		String nowValue = systemIniService.getSystemIni("SYSTEM.SMSCENTER")
				.getNowValue();
		if (nowValue != null && nowValue.equals("Y")) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 检查"单位短信配置"是否启用,启用返回帐号,否则返回""
	 * 
	 * @return
	 */
	// public String getSmsUseConfigClientId() {
	// if (currentClientid != null) {
	// return currentClientid;
	// }
	// else {
	// return smsConfigService.getClientId(unitId);
	// }
	//    	
	// }
	/**
	 * 判断顶级单位短信是否启用
	 * 
	 * @return
	 */
	/*
	 * public boolean isTopUnitSmsConfig() { UnitDto unitDto =
	 * serialRegisterService.getTopUnit(); if (unitDto != null &&
	 * !smsConfigService.getClientId(unitDto.getId()).equals("")) { return true; }
	 * else { return false; }
	 *  }
	 */

	/**
	 * 批量注册帐号
	 * 
	 * @return
	 */
	public String makeBatchSmsAccounts() {
		// String[] ids = unitDto.getArrayIds();

		List<String> errorUnits = new ArrayList<String>();

		// for (int i = 0; i < ids.length; i++) {
		// UnitDto accountUnit = unitService.getUnitDto(ids[i]);
		// try {
		// // 检查单位是否已有clientid
		// // if (!smsUseConfigService.isClientIdSaved(accountUnit.getId())) {
		// // // 单位注册帐号,返回帐号
		// // String cliendId = MsgClient.clientUpdate(accountUnit, null);
		// //
		// // // 启用单位短信配置
		// // saveSmsUseConfig(ids[i], cliendId);
		// // }
		//
		// }
		// catch (SmsException e) {
		// // 捕获错误,记录出错的单位
		// errorUnits.add(accountUnit.getName());
		// e.printStackTrace();
		// }
		// catch (Exception en) {
		// addActionError("批量在短信平台注册帐号失败：" + en.getMessage());
		// SystemLog.log(modID, "修改" + unitDto.getName()
		// + "单位,批量在短信平台注册帐号失败！");
		// return unitSaveReturn();
		// }
		// }
		if (!errorUnits.isEmpty()) {
			String errMessage = null;
			for (String ms : errorUnits) {
				if (errMessage == null) {
					errMessage = ms;
				} else {
					errMessage += "," + ms;
				}
			}
			promptMessageDto.setErrorMessage("以下单位短信平台注册帐号失败:" + errMessage);
			promptMessageDto.setOperateSuccess(false);
			promptMessageDto.addOperation(new String[] {
					"确定",
					"unitAdmin.action?ec_p=" + this.getEc_p() + "&&ec_crd="
							+ this.getEc_crd() });
			SystemLog.log(modID, "单位注册失败！");

		} else {
			promptMessageDto.setPromptMessage("短信平台注册帐号成功！");
			promptMessageDto.setOperateSuccess(true);
			promptMessageDto.addOperation(new String[] {
					"确定",
					"unitAdmin.action?unitId=" + unitDto.getParentid()
							+ "&&ec_p=" + this.getEc_p() + "&&ec_crd="
							+ this.getEc_crd() });
			SystemLog.log(modID, "短信平台注册帐号成功！");
		}

		return PROMPTMSG;
	}

	// /**
	// * 启用单位短信配置
	// *
	// * @param unitguid
	// * @param clientId
	// * @return
	// * @throws Exception
	// */
	// private void saveSmsUseConfig(String unitguid, String clientId)
	// throws Exception {
	// Map<String, String> sign = new HashMap<String, String>();
	// sign.put(SmsConstant.CONFIG_SIGN_SMS, "1");
	// sign.put(SmsConstant.CONFIG_SIGN_ARCHIVE_SEND, "1");
	// sign.put(SmsConstant.CONFIG_SIGN_NOTICE_SEND, "1");
	// smsUseConfigService.saveUseConfigInit(unitguid);
	// smsUseConfigService.saveUseConfig(unitguid, sign, clientId);
	// }

	/**
	 * 删除短信平台的帐户信息
	 * 
	 * @return
	 */
	public String delSmsCenterAccounts() {
		return PROMPTMSG;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public int getUnitmark_lock() {
		return unitmark_lock;
	}

	public int getUnitmark_noaudit() {
		return unitmark_noaudit;
	}

	public int getUnitmark_normal() {
		return unitmark_normal;
	}

	public String getExistsUnitOnEdu() {
		return existsUnitOnEdu;
	}

	public void setExistsUnitOnEdu(String existsUnitOnEdu) {
		this.existsUnitOnEdu = existsUnitOnEdu;
	}

	// public void setSmsConfigService(SmsConfigService smsConfigService) {
	// this.smsConfigService = smsConfigService;
	// }

	public void setSyncSmsCenter(String syncSmsCenter) {
		this.syncSmsCenter = syncSmsCenter;
	}

	// public void setSmsUseConfigService(SmsUseConfigService
	// smsUseConfigService) {
	// this.smsUseConfigService = smsUseConfigService;
	// }

	public void setCurrentClientid(String currentClientid) {
		this.currentClientid = currentClientid;
	}

	public String getCurrentClientid() {
		return currentClientid;
	}

	public void setDelsmscenter(String delsmscenter) {
		this.delsmscenter = delsmscenter;
	}

	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	public Map<String, User> getUserMap() {
		return userMap;
	}

	public String getPassword_init() {
		return password_init;
	}

	public void setPassword_init(String password_init) {
		this.password_init = password_init;
	}

	public void setBaseSchoolService(BaseSchoolService baseSchoolService) {
		this.baseSchoolService = baseSchoolService;
	}

	public void setBaseEduInfoService(BaseEduInfoService baseEduInfoService) {
		this.baseEduInfoService = baseEduInfoService;
	}

	public void setBaseUserService(BaseUserService baseUserService) {
		this.baseUserService = baseUserService;
	}

	public void setSystemVersionService(
			SystemVersionService systemVersionService) {
		this.systemVersionService = systemVersionService;
	}

	public void setUnitRemoteService(UnitRemoteService unitRemoteService) {
		this.unitRemoteService = unitRemoteService;
	}

	public boolean isMaintenanceUnit() {
		String nowValue = systemIniService.getSystemIni(
				BasedataConstants.UNIT_MAINTENANCE_SWITCH).getNowValue();
		if (nowValue != null && nowValue.equals("1")) {
			return true;
		} else {
			return false;
		}
	}

	public void setSystemDeployService(SystemDeployService systemDeployService) {
		this.systemDeployService = systemDeployService;
	}

	public void setSystemSubsystemService(
			SystemSubsystemService systemSubsystemService) {
		this.systemSubsystemService = systemSubsystemService;
	}

	public SystemSubsystemService getSystemSubsystemService() {
		return systemSubsystemService;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserName() {
		return userName;
	}

	public Object[] getObjectJson() {
		return objectJson;
	}

	public void setObjectJson(Object[] objectJson) {
		this.objectJson = objectJson;
	}

	public String getUnitUnionId() {
		return unitUnionId;
	}

	public void setUnitUnionId(String unitUnionId) {
		this.unitUnionId = unitUnionId;
	}

	public String getUnitClass() {
		return unitClass;
	}

	public void setUnitClass(String unitClass) {
		this.unitClass = unitClass;
	}

	public String getParentUnitId() {
		return parentUnitId;
	}

	public void setParentUnitId(String parentUnitId) {
		this.parentUnitId = parentUnitId;
	}

	public boolean isNotSch() {
		unitId = getLoginInfo().getUser().getUnitid();
		BaseUnit loginUnitDto = baseUnitService.getBaseUnit(unitId);
		if (loginUnitDto.getUnittype() == BaseUnit.UNIT_NOTEDU_NOTSCH) {
			return true;
		}
		return false;
	}

	public void setNotSch(boolean isNotSch) {
		this.isNotSch = isNotSch;
	}
	
	public void setSchsecuritySubsystemService(
			SchsecuritySubsystemService schsecuritySubsystemService) {
		this.schsecuritySubsystemService = schsecuritySubsystemService;
	}
	
	public void setBaseGradeService(BaseGradeService baseGradeService) {
		this.baseGradeService = baseGradeService;
	}

	public void setSchoolService(SchoolService schoolService) {
		this.schoolService = schoolService;
	}
	/**
	 * 判断学校类别微代码长度是3位还是2位 
	 * 幼儿园
	 * @return
	 */
	public String getUnitTypeKg111(){
		if(isTwoLength()){
			return "111";
		}
		return "61";
	}
	/**
	 * 独立设置的少数民族幼儿园
	 * @return
	 */
	public String getUnitTypeKg112(){
		if(isTwoLength()){
			return "112";
		}
		return "62";
	}
	/**
	 * 附设幼儿班
	 * @return
	 */
	public String getUnitTypeKg119(){
		if(isTwoLength()){
			return "119";
		}
		return "69";
	}
	
	private boolean isTwoLength(){
		String mcodeid = "DM-XXLB"; 
		List<Mcodedetail> mcodeList = mcodedetailService.getMcodeDetails(mcodeid);
		if(CollectionUtils.isNotEmpty(mcodeList)){
			if(mcodeList.get(0).getThisId().length()>2){
				//老数据幼儿园是61，如果长度大于2，是国标形式，如111是幼儿园，
				return true;
			}
		}
		return false;
	}
}
