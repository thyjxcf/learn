package net.zdsoft.eis.base.data.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import net.zdsoft.eis.base.common.entity.ColsDisplay;
import net.zdsoft.eis.base.common.entity.Dept;
import net.zdsoft.eis.base.common.entity.SystemIni;
import net.zdsoft.eis.base.common.entity.Unit;
import net.zdsoft.eis.base.common.entity.UnitIni;
import net.zdsoft.eis.base.common.entity.User;
import net.zdsoft.eis.base.common.service.ColsDisplayService;
import net.zdsoft.eis.base.common.service.DeptService;
import net.zdsoft.eis.base.common.service.McodeService;
import net.zdsoft.eis.base.common.service.UnitIniService;
import net.zdsoft.eis.base.common.service.UserService;
import net.zdsoft.eis.base.constant.BaseConstant;
import net.zdsoft.eis.base.constant.UCenterConstant;
import net.zdsoft.eis.base.constant.enumeration.VersionType;
import net.zdsoft.eis.base.data.BasedataConstants;
import net.zdsoft.eis.base.data.entity.BaseTeacher;
import net.zdsoft.eis.base.data.entity.BaseUnit;
import net.zdsoft.eis.base.data.entity.Duty;
import net.zdsoft.eis.base.data.entity.ResearchGroup;
import net.zdsoft.eis.base.data.entity.ResearchGroupEx;
import net.zdsoft.eis.base.data.service.BaseTeacherService;
import net.zdsoft.eis.base.data.service.BaseUnitService;
import net.zdsoft.eis.base.data.service.DutyService;
import net.zdsoft.eis.base.data.service.ResearchGroupService;
import net.zdsoft.eis.base.deploy.SystemDeployService;
import net.zdsoft.eis.base.storage.StorageFileUtils;
import net.zdsoft.eis.base.sync.EventSourceType;
import net.zdsoft.eis.base.util.BusinessUtils;
import net.zdsoft.eis.base.util.SystemLog;
import net.zdsoft.eis.frame.action.PageAction;
import net.zdsoft.eis.frame.client.LoginInfo;
import net.zdsoft.eis.frame.dto.PromptMessageDto;
import net.zdsoft.eis.system.frame.web.ucenter.UcWebServiceClient;
import net.zdsoft.eisu.base.common.entity.Institute;
import net.zdsoft.eisu.base.common.service.InstituteService;
import net.zdsoft.keelcnet.action.UploadFile;
import net.zdsoft.keelcnet.config.ContainerManager;
import net.zdsoft.leadin.exception.FileUploadFailException;
import net.zdsoft.leadin.exception.ItemExistsException;
import net.zdsoft.leadin.util.ExportUtil;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

import com.opensymphony.xwork2.ModelDriven;

public class TeacherAction extends PageAction implements
		ModelDriven<BaseTeacher> {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7506804962877651746L;
	private String topDeptId = Dept.TOP_GROUP_GUID;
	private String topDeptName = "(请选择所在部门)";
	private String modID = "SYS004";
//	private String userNameFieldTip = User.NAME_ALERT_NUM;
//	private String userPasswordFieldTip = User.PASSWORD_ALERT;
	private String tchId_regex = "[0-9]{8}";

	private Integer PASSWORD_GENERIC_NULL_RULE = -1;// 密码规则尚未设置
	private Integer PASSWORD_GENERIC_RULE_NULL = BaseConstant.PASSWORD_GENERIC_RULE_NULL;
	private Integer PASSWORD_GENERIC_RULE_NAME = BaseConstant.PASSWORD_GENERIC_RULE_NAME;
	private Integer PASSWORD_GENERIC_RULE_UNIONIZE = BaseConstant.PASSWORD_GENERIC_RULE_UNIONIZE;

	private String password_init = BaseConstant.PASSWORD_INIT;

	public List<BaseTeacher> teacherList;
	private List<ColsDisplay> columnsList;
	private List<ColsDisplay> columnsHiddenList;
	public List<Dept> deptList;
	public List<User> userList;
	// 调整排序中使用
	public String[] teacherids;

	public BaseTeacher teacher = new BaseTeacher();
	public User user = new User();
	private boolean insertWithUser;
	public String deptidnow;
	private String unitId;
	private LoginInfo loginInfo;
	public String pwdGenericRule;

	private BaseTeacherService baseTeacherService;
	private BaseUnitService baseUnitService;
	private DeptService deptService;
	private UserService userService;
	private UnitIniService unitIniService;
	private McodeService mcodeService;
	private ColsDisplayService colsDisplayService;
	private DutyService dutyService;
	private InstituteService instituteService;

	private List<BaseTeacher> teachersList;
	private String queryTchId;
	private String queryTchName;
	private String queryTchUserName;
	private String queryTchCard = "";
	private String eToHSchoolId;
	private List<Duty> listOfDutyDto;

	private String adminTeacherId; // 管理员关联职工的ID

	private UploadFile pictureFile;
	private String signaturePath;

	private String information;

	private String returnActionUrl;
	//树的部门条件
	private String deName;
	
	private String systemTemplatePrefix;
	
	private boolean isDesktop;
	
	private boolean isSXDeploy;//判断是否为陕西人人通
	
	//2017-08-02 duhc 教研组
	private ResearchGroupService researchGroupService; 
	private List<ResearchGroup> researchGroupList;
	private List<ResearchGroupEx> researchGroupExList;

	public String getDeName() {
		return deName;
	}

	public void setDeName(String deName) {
		this.deName = deName;
	}

	public String getEToHSchoolId() {
		return eToHSchoolId;
	}

	public String getQueryTchCard() {
		if ("%".equals(queryTchCard) || "".equals(queryTchCard))
			queryTchCard = null;
		return queryTchCard;
	}

	public String getQueryTchId() {
		if ("%".equals(queryTchId) || "".equals(queryTchId))
			queryTchId = null;
		return queryTchId;
	}

	public String getQueryTchName() {
		if ("%".equals(queryTchName) || "".equals(queryTchName))
			queryTchName = null;
		return queryTchName;
	}

	public String getTeacherAdmin() {
		loginInfo = getLoginInfo();
		if (StringUtils.isBlank(unitId))
			unitId = loginInfo.getUser().getUnitid();

		deptList = deptService.getDepts(unitId);
		return SUCCESS;
	}

	@SuppressWarnings("unchecked")
	public String doTeacherList() {
		loginInfo = getLoginInfo();
		if (StringUtils.isBlank(unitId))
			unitId = loginInfo.getUser().getUnitid();
		// 得到当前部门管理员关联职工的ID（added by dongzk 2007-8-31）
		User adminUserDto = userService.getUnitAdmin(unitId);
		adminTeacherId = adminUserDto.getTeacherid();
		BaseUnit unit = baseUnitService.getBaseUnit(unitId);
		List<User> userList = userService
				.getUsers(unitId, new String[] { "1" });
		if (unit != null && unit.getLimitTeacher() != null
				&& unit.getLimitTeacher() > 0) {
			information = "普通教职工账号上限：<font color='blue'><strong>"
					+ unit.getLimitTeacher()
					+ "</strong></font>个，普通正常账号：<font color='blue'><strong>"
					+ userList.size() + "</strong></font>个";
		}
		if (org.apache.commons.lang.StringUtils.isBlank(queryTchId)
				&& org.apache.commons.lang.StringUtils.isBlank(queryTchName)
				&& org.apache.commons.lang.StringUtils.isBlank(queryTchCard)
				&& org.apache.commons.lang.StringUtils.isBlank(queryTchUserName)) {
			teacherList = baseTeacherService.getBaseTeachers(unitId, deptidnow);
		} else {
			String _queryTchId = queryConvert(queryTchId);
			String _queryTchName = queryConvert(queryTchName);
			String _queryTchCard = queryConvert(queryTchCard);
//			String _queryTchUserName = queryConvert(queryTchUserName);
			String _queryTchUserName = queryTchUserName;
			if("00000000000000000000000000000000".equals(deptidnow)){
				deptidnow="";
			}
			teacherList = baseTeacherService.getTeachersFaintness(unitId,
					_queryTchId, _queryTchName, _queryTchCard, deptidnow, _queryTchUserName);
		}
		return SUCCESS;
	}
	//可通过账号或姓名来获取教师分页列表
	public String doTeacherListByPage() {
		loginInfo = getLoginInfo();
		if (StringUtils.isBlank(unitId))  unitId = loginInfo.getUser().getUnitid();
		
		teacherList = baseTeacherService.getTeachersFaintnessByPage(unitId,queryTchName,queryTchUserName,getPage());
		return SUCCESS;
	}
	private void setPromptMsgOperation(PromptMessageDto promptMessageDto) {
		if (!StringUtils.isEmpty(returnActionUrl)) {
			promptMessageDto
					.addOperation(new String[] { "确定", returnActionUrl });
			return;
		}

		String returnUrl = "teacherAdmin-list.action?deptidnow=" + deptidnow
				+ "&&ec_p=" + getEc_p() + "&&ec_crd=" + getEc_crd();
		promptMessageDto.addOperation(new String[] { "确定", returnUrl });
	}

	// 执行排序操作
	public String getOrderTeatcherDatas() {
		promptMessageDto = new PromptMessageDto();
		try {
			if (teacherids != null) {
				String[] orderids=new String[teacherids.length];
				for(int i=0;i<teacherids.length;i++){
					orderids[i]=String.valueOf(i+1);
				}
				baseTeacherService.updateTeacherAsOrder(teacherids,orderids);
			}
		} catch (Exception e) {
			promptMessageDto.setErrorMessage("职工排序失败!");
			promptMessageDto.setOperateSuccess(false);
			SystemLog.log(modID, "职工排序失败！");
			return SUCCESS;
		}

		promptMessageDto.setPromptMessage("职工排序成功！");
		promptMessageDto.setOperateSuccess(true);
		SystemLog.log(modID, "职工排序成功！");
		return SUCCESS;
	}

	// 将oracle中使用的单个和多个模糊查询符号加上转义符
	private String queryConvert(String str) {
		if (str == null || str.equals("")) {
			return str;
		}

		if (str.indexOf("%") != -1) {
			str = str.replace("%", "\\%");
		}
		if (str.indexOf("_") != -1) {
			str = str.replace("_", "\\_");
		}
		if (str.indexOf("'") != -1) {
			str = str.replace("'", "''");
		}
		return str;
	}

	@SuppressWarnings("unchecked")
	public String exportTeacher() {
		String[] fieldTitles = null;
		String[] propertyNames = null;
		Map<String, String> multiTitleMap = null;
		Map<String, String> teachStatusMap = null;
		Map<String, String> sexMap = null;
		Map<String, String> returnedChineseMap = null;
		Map<String, String> weaveTypeMap = null;
		Map<String, String> eusingMap = null;
		Map<String, String> titleMap = null;
		Map<String, String> polityMap = null;
		Map<String, String> countryMap = null;
		Map<String, String> nationMap = null;
		Map<String, String> stuliveMap = null;
		Map<String, String> majorMap = null;
		Map<String, String> registertypeMap = null;
		Map<String, String> jobMap = null;
		columnsList = colsDisplayService.getColsDisplays(getLoginInfo()
				.getUnitID(), ColsDisplay.COLSTYPE_TEACHER,
				ColsDisplay.COLSUSE_OPEN);
		List<String> fieldTitlesList = new ArrayList<String>();
		List<String> propertyNamesList = new ArrayList<String>();
		if (CollectionUtils.isNotEmpty(columnsList)) {
			for (ColsDisplay column : columnsList) {
				String colsCode = column.getColsCode();
				fieldTitlesList.add(column.getColsName());
				if ("empId".equals(colsCode)) {
					propertyNamesList.add("tchId");
				} else if ("address".equals(colsCode)) {
					propertyNamesList.add("linkAddress");
				} else if ("groupid".equals(colsCode)) {
					propertyNamesList.add("deptName");
				} else if ("duty".equals(colsCode)) {
					propertyNamesList.add("dutyName");
				} else if ("registertype".equals(colsCode)) {
					registertypeMap = mcodeService.getMcode("DM-HKXZ")
							.getCodeMap();
					propertyNamesList.add("registertypeContent");
				} else {
					if ("sex".equals(colsCode)) {
						sexMap = mcodeService.getMcode("DM-XB").getCodeMap();
					} else if ("multiTitle".equals(colsCode)) {
						multiTitleMap = mcodeService.getMcode("DM-BOOLEAN")
								.getCodeMap();
					} else if ("teachStatus".equals(colsCode)) {
						teachStatusMap = mcodeService.getMcode("DM-JSSKZT")
								.getCodeMap();
					} else if ("returnedChinese".equals(colsCode)) {
						returnedChineseMap = mcodeService.getMcode("DM-GATQ")
								.getCodeMap();
					} else if ("weaveType".equals(colsCode)) {
						weaveTypeMap = mcodeService.getMcode("DM-BZLB")
								.getCodeMap();
					} else if ("eusing".equals(colsCode)) {
						eusingMap = mcodeService.getMcode("DM-JSZZBJ")
								.getCodeMap();
					} else if ("title".equals(colsCode)) {
						titleMap = mcodeService.getMcode("DM-JSZCM")
								.getCodeMap();
					} else if ("polity".equals(colsCode)) {
						polityMap = mcodeService.getMcode("DM-ZZMM")
								.getCodeMap();
					} else if ("country".equals(colsCode)) {
						countryMap = mcodeService.getMcode("DM-COUNTRY")
								.getCodeMap();
					} else if ("nation".equals(colsCode)) {
						nationMap = mcodeService.getMcode("DM-MZ").getCodeMap();
					} else if ("stulive".equals(colsCode)) {
						stuliveMap = mcodeService.getMcode("DM-XL")
								.getCodeMap();
					} else if ("major".equals(colsCode)) {
						majorMap = mcodeService.getMcode("DM-ZYLB")
								.getCodeMap();
					}else if("job".equals(colsCode)){
						jobMap = mcodeService.getMcode("DM-GZGW").getCodeMap();
					}
					propertyNamesList.add(colsCode);
				}
			}
			fieldTitlesList.add("帐号");
			propertyNamesList.add("loginName");
		}
		fieldTitles = fieldTitlesList
				.toArray(new String[fieldTitlesList.size()]);
		propertyNames = propertyNamesList.toArray(new String[propertyNamesList
				.size()]);
		Map<String, List> records = new HashMap<String, List>();
		List<BaseTeacher> teacherList = baseTeacherService.getBaseTeachers(
				getLoginInfo().getUnitID(), deptidnow);
		for (BaseTeacher tea : teacherList) {
			if (multiTitleMap != null
					&& StringUtils.isNotBlank(tea.getMultiTitle())) {
				tea.setMultiTitle(multiTitleMap.get(tea.getMultiTitle().trim()));
			}
			if (teachStatusMap != null
					&& StringUtils.isNotBlank(tea.getTeachStatus())) {
				tea.setTeachStatus(teachStatusMap.get(tea.getTeachStatus()
						.trim()));
			}
			if (sexMap != null && StringUtils.isNotBlank(tea.getSex())) {
				tea.setSex(sexMap.get(tea.getSex().trim()));
			}
			if (returnedChineseMap != null
					&& StringUtils.isNotBlank(tea.getReturnedChinese())) {
				tea.setReturnedChinese(returnedChineseMap.get(tea
						.getReturnedChinese().trim()));
			}
			if (weaveTypeMap != null
					&& StringUtils.isNotBlank(tea.getWeaveType())) {
				tea.setWeaveType(weaveTypeMap.get(tea.getWeaveType().trim()));
			}
			if (eusingMap != null && StringUtils.isNotBlank(tea.getEusing())) {
				tea.setEusing(eusingMap.get(tea.getEusing().trim()));
			}
			if (titleMap != null && StringUtils.isNotBlank(tea.getTitle())) {
				tea.setTitle(titleMap.get(tea.getTitle().trim()));
			}
			if (polityMap != null && StringUtils.isNotBlank(tea.getPolity())) {
				tea.setPolity(polityMap.get(tea.getPolity().trim()));
			}
			if (countryMap != null && StringUtils.isNotBlank(tea.getCountry())) {
				tea.setCountry(countryMap.get(tea.getCountry().trim()));
			}
			if (nationMap != null && StringUtils.isNotBlank(tea.getNation())) {
				tea.setNation(nationMap.get(tea.getNation().trim()));
			}
			if (stuliveMap != null && StringUtils.isNotBlank(tea.getStulive())) {
				tea.setStulive(stuliveMap.get(tea.getStulive().trim()));
			}
			if (majorMap != null && StringUtils.isNotBlank(tea.getMajor())) {
				tea.setMajor(majorMap.get(tea.getMajor().trim()));
			}
			if (registertypeMap != null && tea.getRegistertype() != null
					&& tea.getRegistertype() != 0) {
				tea.setRegistertypeContent(registertypeMap.get(tea
						.getRegistertype().toString()));
			}
			if(jobMap != null && tea.getJob() != null){
				tea.setJob(jobMap.get(tea.getJob()));
			}
		}
		records.put("教职工列表", teacherList);
		String fileName = "TchInformation";
		ExportUtil exportUtil = new ExportUtil();
		exportUtil.exportXLSFile(fieldTitles, propertyNames, records, fileName);
		return NONE;
	}

	public String getTeacherNew() {
		loginInfo = getLoginInfo();
		if (StringUtils.isBlank(unitId))
			unitId = loginInfo.getUser().getUnitid();
		BaseUnit unit = baseUnitService.getBaseUnit(unitId);
		if (unit != null) {
			eToHSchoolId = unit.getEtohSchoolId();
			Integer limitTeacher = unit.getLimitTeacher();
			if (limitTeacher != null && limitTeacher != 0) {
				List<User> userList = userService.getUsers(unitId,
						new String[] { "1" });
				if (CollectionUtils.isNotEmpty(userList)) {
					if (userList.size() >= limitTeacher) {
						promptMessageDto
								.setPromptMessage("本单位状态正常的普通教师帐号已经达到上限，不能再增加！");
						promptMessageDto.setOperateSuccess(false);
						this.setPromptMsgOperation(promptMessageDto);
						return PROMPTMSG;
					}
				}
			}
		}

		if (deptidnow == null || "".equals(deptidnow)) {
			deptidnow = BaseConstant.ZERO_GUID;
			teacher.setDeptid(deptidnow);
		}
		// groupList = groupService.getAllUnitGroup(unitId);
		if (!Dept.TOP_GROUP_GUID.equals(deptidnow)) {
			Dept deptDto = deptService.getDept(deptidnow);
			if (deptDto != null) {
				teacher.setDeptid(deptidnow);
				teacher.setDeptName(deptDto.getDeptname());
				List<BaseTeacher> btsList = baseTeacherService.getBaseTeachersByDeptId(deptidnow);
				if(btsList!=null){
					teacher.setDisplayOrder(btsList.size()+1);
				}
			} else {
				teacher.setDeptid(BaseConstant.ZERO_GUID);
			}
		}

		teacher.setUnitid(unitId);
		teacher.setTchId(baseTeacherService.getAvaTeacherCode(unitId));
		teacher.setInsertWithUser(true);
		teacher.setEusing(BasedataConstants.EMPLOYEE_INCUMBENCY);// 职工在职标记
//		if (teacher.getSex() == null) {
//			teacher.setSex(BaseConstant.SEX_MALE);
//		}
		user = userService.getUserNew(unitId);

		columnsList = colsDisplayService.getColsDisplays(getUnitId(),
				ColsDisplay.COLSTYPE_TEACHER, ColsDisplay.COLSUSE_OPEN);
		columnsHiddenList = colsDisplayService.getColsDisplays(getUnitId(),
				ColsDisplay.COLSTYPE_TEACHER, ColsDisplay.COLSUSE_CLOSE);
		if(VersionType.EIS == systemDeployService.getVersionType()){
			researchGroupList = researchGroupService.getResearchGroupsByUnitId(getUnitId());
			researchGroupExList = researchGroupService.getresearchGroupExList(teacher.getId());
			if (!researchGroupExList.isEmpty()) {
				String[] researchGroupIds = new String[researchGroupExList.size()];
				int i = 0;
				for (ResearchGroupEx ex : researchGroupExList) {
					researchGroupIds[i] = ex.getTeachGroupId();
					i++;
				}
				List<ResearchGroup> researchGroupLists = researchGroupService.getResearchGroupByIds(researchGroupIds);
				if (!researchGroupLists.isEmpty()) {
					teacher.setResearchGroupId(researchGroupLists.get(0).getId());
				}
			}
		}
		return SUCCESS;
	}

	public String getTeacherEdit() {
		loginInfo = getLoginInfo();
		if (StringUtils.isBlank(unitId))
			unitId = loginInfo.getUser().getUnitid();

		teacher = baseTeacherService.getBaseTeacher(teacher.getId());
		Dept deptDto = deptService.getDept(teacher.getDeptid());
		if (deptDto != null) {
			teacher.setDeptName(deptDto.getDeptname());
		}
		teacher.setOldMobile(teacher.getPersonTel());

		// 得到当前部门管理员关联职工的ID（added by dongzk 2007-8-31）
		User adminUserDto = userService.getUnitAdmin(unitId);
		adminTeacherId = adminUserDto.getTeacherid();

		columnsList = colsDisplayService.getColsDisplays(getUnitId(),
				ColsDisplay.COLSTYPE_TEACHER, ColsDisplay.COLSUSE_OPEN);
		columnsHiddenList = colsDisplayService.getColsDisplays(getUnitId(),
				ColsDisplay.COLSTYPE_TEACHER, ColsDisplay.COLSUSE_CLOSE);

		if(VersionType.EIS == systemDeployService.getVersionType()){
			researchGroupList = researchGroupService.getResearchGroupsByUnitId(getUnitId());
			researchGroupExList = researchGroupService.getresearchGroupExList(teacher.getId());
			if (!researchGroupExList.isEmpty()) {
				String[] researchGroupIds = new String[researchGroupExList.size()];
				int i = 0;
				for (ResearchGroupEx ex : researchGroupExList) {
					researchGroupIds[i] = ex.getTeachGroupId();
					i++;
				}
				List<ResearchGroup> researchGroupLists = researchGroupService.getResearchGroupByIds(researchGroupIds);
				if (!researchGroupLists.isEmpty()) {
					teacher.setResearchGroupId(researchGroupLists.get(0).getId());
				}
			}
		}
		
		return SUCCESS;
	}

	public String setTeacherInsert() {
		loginInfo = getLoginInfo();
		if (StringUtils.isBlank(unitId))
			unitId = loginInfo.getUser().getUnitid();
		teacher.setUnitid(unitId);
		teacher.setCreationTime(new Date());
		promptMessageDto =validateTeacher(teacher);
		if (!promptMessageDto.getOperateSuccess()) {
			return SUCCESS;
		}

		try {
			pictureFile = StorageFileUtils.handleFile(new String[] { "gif",
					"bmp", "jpg", "jpeg", "png" }, 0);
		} catch (FileUploadFailException e) {
			log.error(e.getMessage(), e);
			promptMessageDto.setOperateSuccess(false);
			promptMessageDto.setErrorMessage(e.getMessage());
			return SUCCESS;
		}
			

		try {
			if (teacher.isInsertWithUser()) {
				promptMessageDto =validateUser(user);
				if (!promptMessageDto.getOperateSuccess()) {
					return SUCCESS;
				}
				user.setDeptid(teacher.getDeptid());
				if (pictureFile == null)
					baseTeacherService.saveTeacher(teacher, user);
				else
					baseTeacherService.saveTeacher(teacher, user, pictureFile);
			} else {
				if (pictureFile == null)
					baseTeacherService.saveTeacher(teacher);
				else
					baseTeacherService.saveTeacher(teacher, pictureFile);
			}

			promptMessageDto.setPromptMessage("新增" + teacher.getName()
					+ "职员成功！");
			promptMessageDto.setOperateSuccess(true);
			SystemLog.log(modID, "新增" + teacher.getName() + "职工成功！");
			return SUCCESS;
		} catch (ItemExistsException itemE) {
			promptMessageDto.setOperateSuccess(false);
			promptMessageDto.setErrorMessage(itemE.getMessage());
			return SUCCESS;
		} catch (Exception e) {
			log.error(e.toString());
			e.printStackTrace();
			promptMessageDto.setOperateSuccess(false);
			promptMessageDto.setErrorMessage("新增" + teacher.getName() + "职工时出错：" + e.getMessage());
			return SUCCESS;
		}
	}

	public String setTeacherDelete() {
		promptMessageDto = new PromptMessageDto();
		try {
			String[] ids = teacher.getArrayIds();
        	Map<String, Object> verifyDataDeleteMap = userService.getVerifyDelete(User.TEACHER_LOGIN,ids);
        	String[] ownerIds=(String[]) verifyDataDeleteMap.get("yesIds");
    		String msgStr=(String) verifyDataDeleteMap.get("msg");
    		
			baseTeacherService.deleteTeacher(ownerIds, EventSourceType.LOCAL);
			promptMessageDto.setPromptMessage("操作成功！"+msgStr);
			promptMessageDto.setOperateSuccess(true);
			this.setPromptMsgOperation(promptMessageDto);
			SystemLog.log(modID, "删除职工信息！"+msgStr);
			return SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			promptMessageDto.setErrorMessage("删除职工信息失败!");
			promptMessageDto.setOperateSuccess(false);
			this.setPromptMsgOperation(promptMessageDto);
			return SUCCESS;
		}
	}

	public String setTeacherUpdate() {
		promptMessageDto = new PromptMessageDto();
		loginInfo = getLoginInfo();
		if (StringUtils.isBlank(unitId))
			unitId = loginInfo.getUser().getUnitid();

		// 判断教职工的数量是否已达到最大值
		String[] normal = new String[] { BasedataConstants.EMPLOYEE_INCUMBENCY,
				BasedataConstants.EMPLOYEE_TEMP,
				BasedataConstants.EMPLOYEE_PLURALITY,
				BasedataConstants.EMPLOYEE_PROBATION,
				BasedataConstants.EMPLOYEE_BORROW };
		if (ArrayUtils.contains(normal, teacher.getEusing())) {
			BaseUnit unit = baseUnitService.getBaseUnit(unitId);
			if (unit != null) {
				eToHSchoolId = unit.getEtohSchoolId();
				Integer limitTeacher = unit.getLimitTeacher();
				if (limitTeacher != null && limitTeacher != 0) {
					List<User> userList = userService.getUsers(unitId,
							new String[] { "1" });
					if (CollectionUtils.isNotEmpty(userList)) {
						if (userList.size() >= limitTeacher) {
							BaseTeacher dto = baseTeacherService
									.getBaseTeacher(teacher.getId());
							if (dto != null) {
								if (!ArrayUtils.contains(normal,
										dto.getEusing())) {
									String msg = "本单位状态正常的普通教师帐号已经达到上限，改"
											+ "教职工在职状态修改会导致对应用户状态也修改，当前操作失败！";
									promptMessageDto.setPromptMessage(msg);
									promptMessageDto.setOperateSuccess(false);
									return SUCCESS;
								}
							}
						}
					}
				}
			}
		}

		teacher.setCreationTime(new Date());
		promptMessageDto =validateTeacher(teacher);
		if (!promptMessageDto.getOperateSuccess()) {
			return SUCCESS;
		}

		try {
			pictureFile = StorageFileUtils.handleFile(new String[] { "gif",
					"bmp", "jpg", "jpeg", "png" }, 0);
		} catch (FileUploadFailException e) {
			log.error(e.getMessage(), e);
			promptMessageDto.setOperateSuccess(false);
			promptMessageDto.setErrorMessage(e.getMessage());
			return SUCCESS;
		}

		BaseTeacher oldTeacher = baseTeacherService.getBaseTeacher(teacher.getId());
		if (!oldTeacher.getTchId().trim().equals(teacher.getTchId().trim())) {
			if (baseTeacherService.isExistsTeacherCode(teacher.getUnitid(), teacher
					.getTchId())) {
				promptMessageDto.setErrorMessage("该编号已存在！");
				promptMessageDto.setOperateSuccess(false);
				return SUCCESS;
			}
		}
		try {
			if (pictureFile == null){
				baseTeacherService.updateTeacher(teacher);
			}else{
				baseTeacherService.updateTeacher(teacher, pictureFile);
			}
			
			if(VersionType.EIS == systemDeployService.getVersionType()){
				boolean contains = false;
				if (StringUtils.isNotBlank(teacher.getResearchGroupId())) {
					researchGroupExList = researchGroupService.getResearchGroupExs(teacher.getResearchGroupId());
					for (ResearchGroupEx ex : researchGroupExList) {
						if (ex.getTeacherId().equals(teacher.getId())) {
							contains = true;
						}
					}
					if (!contains) {
						researchGroupService.saveOne(teacher.getResearchGroupId(),teacher.getId(),0);
					}
				}
			}
			// 鼎永修改手机号 
			if(isDesktop 
					&& systemIniService.getBooleanValue(UCenterConstant.SYSTEM_UCENTER_SWITCH) 
					&& !StringUtils.equals(teacher.getOldMobile(), teacher.getPersonTel())){
				try {
					UcWebServiceClient client = UcWebServiceClient.getInstance();
					List<User> uts = userService.getUsersByOwner(teacher.getId());
					if(CollectionUtils.isNotEmpty(uts)){
						for(User us : uts){
							String un = us.getName();
							client.updateTel(un, teacher.getPersonTel());
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
					log.error("同步修改手机号失败："+e.getMessage(), e);
				}
			}
		} catch (ItemExistsException itemE) {
			promptMessageDto.setPromptMessage(itemE.getMessage());
			promptMessageDto.setErrorMessage(itemE.getMessage());
			promptMessageDto.setOperateSuccess(false);
			return SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			promptMessageDto.setPromptMessage("修改职工信息时出错：" + e.getMessage());
			promptMessageDto.setOperateSuccess(false);
			return SUCCESS;
		}
		promptMessageDto.setPromptMessage("修改" + teacher.getName() + "职工成功！");
		promptMessageDto.setOperateSuccess(true);
		SystemLog.log(modID, "修改" + teacher.getName() + "职工信息成功！");
		return SUCCESS;
	}

	public Integer getCurrentUnitClass() {
		Unit unit = baseUnitService.getUnit(unitId);
		return unit.getUnitclass();
	}

	public void setBaseTeacherService(BaseTeacherService baseTeacherService) {
		this.baseTeacherService = baseTeacherService;
	}

	public BaseTeacher getModel() {
		return teacher;
	}

	public void setBaseUnitService(BaseUnitService baseUnitService) {
		this.baseUnitService = baseUnitService;
	}

	public void setDeptService(DeptService deptService) {
		this.deptService = deptService;
	}

	public List<Dept> getDeptList() {
		return deptList;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getUnitId() {
		return unitId;
	}

	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}

	public boolean isInsertWithUser() {
		return insertWithUser;
	}

	public void setInsertWithUser(boolean insertWithUser) {
		this.insertWithUser = insertWithUser;
	}

	public void setDeptidnow(String deptidnow) {
		this.deptidnow = deptidnow;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public static final String MSG_INVALID_USERNAME = "姓名包含汉字、字母、"
			+ "数字、点号、空格和下划线，且须以汉字或字母开头、非空格结尾";

	/**
	 * 校验用户的姓名. <br>
	 * 用户姓名只能包含汉字、字母、数字、点号、空格和下划线,<br>
	 * 并必须以汉字或者字母开头, 不能以空格结尾.
	 * 
	 * @param username
	 *            用户名
	 * @return 用户名合法返回 <code>true</code>, 否则返回 <code>false</code>.
	 */
	public static boolean isValidName(String username) {
		if (username == null || "".equals(username)) {
			return false;
		}

		String regex = "[a-zA-Z\u4e00-\u9fa5]{1}|"
				+ "[a-zA-Z\u4e00-\u9fa5]{1}[\u4e00-\u9fa5\\w\\.\\·]*[\u4e00-\u9fa5\\w]{1}";
		return java.util.regex.Pattern.matches(regex, username);
	}

	private PromptMessageDto validateTeacher(BaseTeacher teacher) {
		promptMessageDto = new PromptMessageDto();

		if (net.zdsoft.keel.util.StringUtils.getRealLength(teacher
				.getHomepage()) > 40) {
			promptMessageDto.setErrorMessage("主页地址最长不能超过40");
			return promptMessageDto;
		}
		if (StringUtils.isNotBlank(teacher.getHomepage())) {
			Pattern pattern = Pattern
					.compile("^http[s]?:\\/\\/([\\w-]+\\.)+[\\w-]+([\\w-./:?%&=]*)?$");// 验证url
			if (!pattern.matcher(teacher.getHomepage()).matches()) {
				promptMessageDto.setErrorMessage("主页地址格式无效。必须以http://或https://开头，括号内为允许输入的特殊符号（  - . / : ? % & = ）");
				return promptMessageDto;
			}
		}
		if (StringUtils.isNotBlank(teacher.getOfficeTel())) {
			Pattern pattern = Pattern.compile("^[0-9]{1,20}$");// 验证
			if (!pattern.matcher(teacher.getOfficeTel()).matches()) {
				promptMessageDto.setErrorMessage("请输入正确的办公电话(不能超过20个数字)。如：057156686686。");
				return promptMessageDto;
			}
		}
		if (StringUtils.isNotBlank(teacher.getMobileCornet())) {
			Pattern pattern = Pattern.compile("^[0-9]{1,10}$");// 验证
			if (!pattern.matcher(teacher.getMobileCornet()).matches()) {
				promptMessageDto.setErrorMessage("请输入正确的手机短号(不能超过10个数字)。如：123456。");
				return promptMessageDto;
			}
		}
		/*if (!RegUtils.regexValidator(tchId_regex, teacher.getTchId())) {
			promptMessageDto.setErrorMessage("请确认编号只能是"
					+ BaseTeacher.EMPID_LENGTH + "位整数");
			return promptMessageDto;
		} else*/ if (teacher.getName() == null || !isValidName(teacher.getName())) {
			promptMessageDto.setErrorMessage(MSG_INVALID_USERNAME);
			return promptMessageDto;
		} else if (teacher.getSex() == null) {
			promptMessageDto.setErrorMessage("请选择职工性别");
			return promptMessageDto;
		} else if (deptService.getDept(teacher.getDeptid()) == null) {
			promptMessageDto.setErrorMessage("该部门暂时不可用,请重新选择所在部门");
			return promptMessageDto;
		} else if (org.apache.commons.lang.StringUtils.isNotBlank(teacher
				.getIdcard())) {
			if (org.apache.commons.lang.StringUtils.isNotBlank(BusinessUtils
					.validateIdentityCard(teacher.getIdcard()))) {
				promptMessageDto.setErrorMessage("请确认身份证号码填写正确");
				return promptMessageDto;
			}
			
			boolean resultBool = baseTeacherService.isExistsIdCard(
					teacher.getId(), teacher.getIdcard());
			if (resultBool) {
				promptMessageDto.setErrorMessage("身份证号码已存在，请重新录入");
				return promptMessageDto;
			}
		}
		// 点到卡号，先屏蔽，后面定下来基础库后，再看怎么处理
		/*
		 * if (teacher.getCardNumber() != null &&
		 * teacher.getCardNumber().trim().length() > 0) { if
		 * (!Validators.isNumber(teacher.getCardNumber())) {
		 * addFieldError("teacher.cardNumber", "点到卡号必须是整数,请重新录入"); return false;
		 * } if (teacher.getCardNumber().substring(0, 1).equals("0")) {
		 * addFieldError("teacher.cardNumber", "点到卡号不能以0开头，请重新录入"); return
		 * false; } boolean resultBool =
		 * baseTeacherService.isExistsCardNumber(teacher .getId(),
		 * teacher.getCardNumber()); if (resultBool) {
		 * addFieldError("teacher.cardNumber", "点到卡号已存在，请重新录入"); return false; }
		 * }
		 */
		if (teacher.getBirthday() != null) {
			if (teacher.getBirthday().after(new Date())) {
				promptMessageDto.setErrorMessage( "出生日期不能晚于当前日期");
				return promptMessageDto;
			}
			if (teacher.getPolityJoin() != null) {
				if (teacher.getBirthday().after(teacher.getPolityJoin())) {
					promptMessageDto.setErrorMessage("加入时间不能早于出生年月");
					return promptMessageDto;
				}
			}
			if (teacher.getFstime() != null) {
				if (teacher.getBirthday().after(teacher.getFstime())) {
					promptMessageDto.setErrorMessage("毕业时间不能早于出生年月");
					return promptMessageDto;
				}
			}
			if (teacher.getWorkdate() != null) {
				if (teacher.getBirthday().after(teacher.getWorkdate())) {
					promptMessageDto.setErrorMessage("参加工作时间不能早于出生年月");
					return promptMessageDto;
				}
			}
		}
		
		// 核对身份证中出生日期和教职工指定的出生日期是否一致
//		if (teacher.getBirthday()!=null) {
//			String stuBirth = DateUtils.date2StringByDay(teacher.getBirthday());
//			if (teacher.getBirthday() != null) {
//				if (stuBirth.split("-").length == 3) {
//					String bir2 = stuBirth.split("-")[1];
//					String bir3 = stuBirth.split("-")[2];
//					if (bir2.length() == 1)
//						bir2 = "0" + bir2;
//					if (bir3.length() == 1)
//						bir3 = "0" + bir3;
//					stuBirth = stuBirth.split("-")[0] + "-" + bir2 + "-"
//							+ bir3;
//				}
//			}
//
//			String birth = BusinessUtils.getDateStrFromIdentityNo(teacher
//					.getIdcard());
//			if (null != birth && !(birth.equals(stuBirth))) {
//				promptMessageDto.setErrorMessage("身份证中的出生日期和教职工的出生日期不一致。");
//				return promptMessageDto;
//			}
//		}
//		
		promptMessageDto.setOperateSuccess(true);
		return promptMessageDto;
	}

	private PromptMessageDto validateUser(User userDto) {
		promptMessageDto = new PromptMessageDto();
		if (userDto.getName() == null) {
			promptMessageDto.setErrorMessage("请输入账号");
			return promptMessageDto;
		} else if (!userDto.getName().matches(systemIniService.getValue(User.SYSTEM_NAME_EXPRESSION))) {
			promptMessageDto.setErrorMessage(systemIniService.getValue(User.SYSTEM_NAME_ALERT));
			return promptMessageDto;
		}
		if (userDto.getPassword() != null
				&& userDto.getConfirmPassword() != null) {
			if (userDto.findClearPassword().trim().length() > 0
					|| userDto.getConfirmPassword().trim().length() > 0) {
				if (!userDto.findClearPassword().matches(systemIniService.getValue(User.SYSTEM_PASSWORD_EXPRESSION))) {
					promptMessageDto.setErrorMessage(systemIniService.getValue(User.SYSTEM_PASSWORD_ALERT));
					return promptMessageDto;
				}else {
					if(userDto.findClearPassword().matches(systemIniService.getValue(User.SYSTEM_PASSWORD_STRONG))){
						promptMessageDto.setErrorMessage(systemIniService.getValue(User.SYSTEM_PASSWORD_ALERT));
						return promptMessageDto;
					}
				}
		
				if (!userDto.findClearPassword().equals(
						userDto.getConfirmPassword())) {
					promptMessageDto.setErrorMessage("请确认密码填写一致");
					return promptMessageDto;
				}
			}
		}
		promptMessageDto.setOperateSuccess(true);
		return promptMessageDto;
	}

	/**
	 * 职工关联用户
	 * 
	 * @return
	 */
	public List<User> getUserList() {
		if (teacher == null) {
			return null;
		}
		return userService.getUsersByOwner(teacher.getId());
	}
	
	/**
	 * 系统部署学校
	 */
	public String getSystemTemplatePrefix(){
		if(StringUtils.isBlank(systemTemplatePrefix)){
			systemTemplatePrefix=getSystemDeploySchool();
		}
		return systemTemplatePrefix;
	}
	
	/**
	 * 判断是否中职部署
	 * @return
	 */
	public boolean isEisuSchool(){
		if (systemDeployService == null) {
			systemDeployService = (SystemDeployService) ContainerManager
					.getComponent("systemDeployService");
		}
		if(VersionType.EISU == systemDeployService.getVersionType()){
			return true;
		}
		return false;
	}

	public List<Institute> getInstituteList() {
		return instituteService.getInstitutesByUnitId(getUnitId());
	}

	public String getTopDeptId() {
		return topDeptId;
	}

	public String getTopDeptName() {
		return topDeptName;
	}

	public String getUserNameFieldTip() {
		return systemIniService.getValue(User.SYSTEM_NAME_ALERT);
	}

	public String getUserPasswordFieldTip() {
		return systemIniService.getValue(User.SYSTEM_PASSWORD_ALERT);
	}

	public String getPwdGenericRule() {
		if (teacher == null) {
			return null;
		}
		String pwdGen = unitIniService.getUnitOptionValue(teacher.getUnitid(),
				UnitIni.UNIT_PASSWORD_CONFIG);
		return pwdGen == null ? "-1" : pwdGen;

	}

	public void setUnitIniService(UnitIniService unitIniService) {
		this.unitIniService = unitIniService;
	}

	public Integer getPASSWORD_GENERIC_RULE_NAME() {
		return PASSWORD_GENERIC_RULE_NAME;
	}

	public Integer getPASSWORD_GENERIC_RULE_NULL() {
		return PASSWORD_GENERIC_RULE_NULL;
	}

	public Integer getPASSWORD_GENERIC_RULE_UNIONIZE() {
		return PASSWORD_GENERIC_RULE_UNIONIZE;
	}

	public Integer getPASSWORD_GENERIC_NULL_RULE() {
		return PASSWORD_GENERIC_NULL_RULE;
	}

	public List<BaseTeacher> getTeachersList() {
		return teachersList;
	}

	public String getDeptidnow() {
		return deptidnow;
	}

	public void setColsDisplayService(ColsDisplayService colsDisplayService) {
		this.colsDisplayService = colsDisplayService;
	}

	public void setMcodeService(McodeService mcodeService) {
		this.mcodeService = mcodeService;
	}

	public void setQueryTchCard(String queryTchCard) {
		this.queryTchCard = queryTchCard;
	}

	public void setQueryTchId(String queryTchId) {
		this.queryTchId = queryTchId;
	}

	public void setQueryTchName(String queryTchName) {
		this.queryTchName = queryTchName;
	}

	public String getPassword_init() {
		return password_init;
	}

	public void setPassword_init(String password_init) {
		this.password_init = password_init;
	}

	public List<ColsDisplay> getColumnsHiddenList() {
		if (null == columnsHiddenList) {
			columnsHiddenList = colsDisplayService.getColsDisplays(getUnitId(),
					ColsDisplay.COLSTYPE_TEACHER, ColsDisplay.COLSUSE_CLOSE);
		}
		return columnsHiddenList;
	}

	public void setColumnsHiddenList(List<ColsDisplay> columnsHiddenList) {
		this.columnsHiddenList = columnsHiddenList;
	}

	public List<ColsDisplay> getColumnsList() {
		if (null == columnsList) {
			columnsList = colsDisplayService.getColsDisplays(getUnitId(),
					ColsDisplay.COLSTYPE_TEACHER, ColsDisplay.COLSUSE_OPEN);
		}
		return columnsList;
	}

	public void setColumnsList(List<ColsDisplay> columnsList) {
		this.columnsList = columnsList;
	}

	public List<Duty> getListOfDutyDto() {
		if (null == listOfDutyDto) {
			listOfDutyDto = dutyService.getDuties(getLoginInfo().getUnitID());
		}
		if (null == listOfDutyDto) {
			listOfDutyDto = new ArrayList<Duty>();
		}
		return listOfDutyDto;
	}

	public void setListOfDutyDto(List<Duty> listOfDutyDto) {
		this.listOfDutyDto = listOfDutyDto;
	}

	public void setDutyService(DutyService dutyService) {
		this.dutyService = dutyService;
	}

	public String getAdminTeacherId() {
		return adminTeacherId;
	}

	public void setAdminTeacherId(String adminTeacherId) {
		this.adminTeacherId = adminTeacherId;
	}

	/**
	 * 得到手写签名的图片下载全路径 如果职工没有维护手写签名则返回""
	 * 
	 * @return
	 */
	public String getSignaturePath() {
		if (signaturePath != null)
			return signaturePath;

		if (teacher == null) {
			signaturePath = "";
			return signaturePath;
		}

		if (org.apache.commons.lang.StringUtils.isBlank(teacher
				.getPhysicsFilePath()))
			return "";

		signaturePath = teacher.getDownloadPath();
		return signaturePath;
	}

	public Integer getUnitClass() {
		return getLoginInfo().getUnitClass();
	}

	public String getInformation() {
		return information;
	}

	public void setInformation(String information) {
		this.information = information;
	}

	public List<BaseTeacher> getTeacherList() {
		return teacherList;
	}

	public BaseTeacher getTeacher() {
		return teacher;
	}

	public void setTeacher(BaseTeacher teacher) {
		this.teacher = teacher;
	}

	public boolean isConnectPassport() {
		return this.systemDeployService.isConnectPassport();
	}

	public void setReturnActionUrl(String returnActionUrl) {
		this.returnActionUrl = returnActionUrl;
	}

	public String getReturnActionUrl() {
		return this.returnActionUrl;
	}

	public void setInstituteService(InstituteService instituteService) {
		this.instituteService = instituteService;
	}

	public String getQueryTchUserName() {
		return queryTchUserName;
	}

	public void setQueryTchUserName(String queryTchUserName) {
		this.queryTchUserName = queryTchUserName;
	}

	public boolean getIsDesktop() {
		return isDesktop;
	}

	public void setIsDesktop(boolean isDesktop) {
		this.isDesktop = isDesktop;
	}

	public boolean getIsSXDeploy() {
		//判断是否为陕西人人通
		SystemIni systemIni = systemIniService.getSystemIni(BaseConstant.SYSTEM_DEPLOY_SCHOOL);
		if(systemIni != null && BaseConstant.SYS_DEPLOY_SCHOOL_SXRRT.equals(systemIni.getNowValue()))
			isSXDeploy = true;
		else
			isSXDeploy = false;
		
		return isSXDeploy;
	}

	public void setIsSXDeploy(boolean isSXDeploy) {
		this.isSXDeploy = isSXDeploy;
	}

	public ResearchGroupService getResearchGroupService() {
		return researchGroupService;
	}

	public void setResearchGroupService(ResearchGroupService researchGroupService) {
		this.researchGroupService = researchGroupService;
	}

	public List<ResearchGroup> getResearchGroupList() {
		return researchGroupList;
	}

	public void setResearchGroupList(List<ResearchGroup> researchGroupList) {
		this.researchGroupList = researchGroupList;
	}

	public List<ResearchGroupEx> getResearchGroupExList() {
		return researchGroupExList;
	}

	public void setResearchGroupExList(List<ResearchGroupEx> researchGroupExList) {
		this.researchGroupExList = researchGroupExList;
	}
	
	
	
}
