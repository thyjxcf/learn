package net.zdsoft.eis.base.data.service.impl;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.zdsoft.eis.base.common.entity.Dept;
import net.zdsoft.eis.base.common.entity.Mcodedetail;
import net.zdsoft.eis.base.common.entity.Role;
import net.zdsoft.eis.base.common.entity.SchoolSemester;
import net.zdsoft.eis.base.common.entity.Semester;
import net.zdsoft.eis.base.common.entity.SubSchool;
import net.zdsoft.eis.base.common.entity.SysOption;
import net.zdsoft.eis.base.common.entity.SystemIni;
import net.zdsoft.eis.base.common.entity.UnitIni;
import net.zdsoft.eis.base.common.entity.User;
import net.zdsoft.eis.base.common.service.SemesterService;
import net.zdsoft.eis.base.common.service.SystemIniService;
import net.zdsoft.eis.base.constant.BaseConstant;
import net.zdsoft.eis.base.data.BasedataConstants;
import net.zdsoft.eis.base.data.entity.BaseSchool;
import net.zdsoft.eis.base.data.entity.BaseTeacher;
import net.zdsoft.eis.base.data.entity.BaseUnit;
import net.zdsoft.eis.base.data.entity.BaseUnitImport;
import net.zdsoft.eis.base.data.service.BaseMcodeDetailService;
import net.zdsoft.eis.base.data.service.BaseSchoolService;
import net.zdsoft.eis.base.data.service.BaseUnitService;
import net.zdsoft.eis.base.data.service.BaseUserService;
import net.zdsoft.eis.base.data.service.PassportAccountService;
import net.zdsoft.eis.base.deploy.SystemDeployService;
import net.zdsoft.eis.base.subsystemcall.service.SystemSubsystemService;
import net.zdsoft.eis.base.sync.EventSourceType;
import net.zdsoft.eis.system.constant.SystemConstant;
import net.zdsoft.eis.system.data.dao.RolePermDao;
import net.zdsoft.eis.system.data.entity.RolePerm;
import net.zdsoft.eis.system.data.service.RoleService;
import net.zdsoft.keel.action.Reply;
import net.zdsoft.keel.util.DateUtils;
import net.zdsoft.keel.util.ObjectUtils;
import net.zdsoft.keel.util.StringUtils;
import net.zdsoft.keelcnet.entity.DtoAssembler;
import net.zdsoft.leadin.common.dao.SystemCommonDao;
import net.zdsoft.leadin.dataimport.common.DataImportConstants;
import net.zdsoft.leadin.dataimport.core.ImportData;
import net.zdsoft.leadin.dataimport.core.ImportObject;
import net.zdsoft.leadin.dataimport.core.ImportObjectNode;
import net.zdsoft.leadin.dataimport.exception.ErrorFieldException;
import net.zdsoft.leadin.dataimport.exception.ImportErrorException;
import net.zdsoft.leadin.dataimport.param.DataImportParam;
import net.zdsoft.leadin.dataimport.service.impl.AbstractDataImportService;
import net.zdsoft.leadin.exception.OperationNotAllowedException;
import net.zdsoft.leadin.util.PWD;
import net.zdsoft.leadin.util.UUIDGenerator;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.oro.text.regex.MalformedPatternException;
import org.apache.oro.text.regex.Pattern;
import org.apache.oro.text.regex.PatternCompiler;
import org.apache.oro.text.regex.PatternMatcher;
import org.apache.oro.text.regex.Perl5Compiler;
import org.apache.oro.text.regex.Perl5Matcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.support.incrementer.DataFieldMaxValueIncrementer;

public class UnitImportServiceImpl extends AbstractDataImportService {

	private Logger log = LoggerFactory
			.getLogger(UnitImportServiceImpl.class);

	// 单位表中的字段
	static Set<String> unitFieldSet = new HashSet<String>();
	// 学校表中的字段
	static Set<String> schFieldSet = new HashSet<String>();
	// 用户表中的字段
	static Set<String> userFieldSet = new HashSet<String>();

	// 有关联关系的字段
	static Set<String> relatedFieldSet = new HashSet<String>();
	
	static {
		unitFieldSet.add("unitName");
		unitFieldSet.add("unitType");
		unitFieldSet.add("parentId");
		unitFieldSet.add("unitUseType");
		unitFieldSet.add("unitName");
		unitFieldSet.add("unitName");
		
		schFieldSet.add("schCode");
		schFieldSet.add("schType");
		schFieldSet.add("runschtype");
		
		userFieldSet.add("userName");
		userFieldSet.add("password");
	}
	private BaseMcodeDetailService baseMcodeDetailService;
	private SystemCommonDao systemCommonDao; // 通用的处理类
	private BaseUnitService baseUnitService;
	private BaseSchoolService baseSchoolService;
	private BaseUserService baseUserService;
	private SystemSubsystemService systemSubsystemService;
	private RoleService roleService;
	private String unitTypeMcodeId = "DM-DWLX";// 单位类型微代码
	private DataFieldMaxValueIncrementer unitIncre;
	private DataFieldMaxValueIncrementer unitIniIncre;
	private SystemDeployService systemDeployService;

	private RolePermDao rolePermDao;
	private PassportAccountService passportAccountService;
    private SemesterService semesterService;
	private SystemIniService systemIniService;

    private String kgSwitchValue = "";
    private int limitTeacherCount = 0;
    private List<Semester> semesterList;
    private List<SystemIni> sysIniList;
    private List<Role> defaultRoleList;
    public void setBaseMcodeDetailService(
			BaseMcodeDetailService baseMcodeDetailService) {
		this.baseMcodeDetailService = baseMcodeDetailService;
	}
	
	public void initUnitImport(){
		kgSwitchValue = systemIniService.getValue("KG.UNIT.TO.SCHOOL");
		limitTeacherCount = systemSubsystemService.getUserCountLimit();
		semesterList = semesterService.getSemesters();//学年学期
		sysIniList = systemIniService.getSystemIniByViewable(2);//单位系统初始化信息
		defaultRoleList = roleService.getRoles(SystemConstant.DEFAULT_SCHOOL_GUID);//单位默认角色
	}
	/**
	 * 导入
	 */
	@Override
	public void importDatas(DataImportParam param, Reply reply)
			throws ImportErrorException {
		// 错误数据位置
		int errPos = 0;
		initUnitImport();
		try {
			log.debug("==============准备参数信息================");
			// 取得导入文件对象
			ImportData importData = param.getImportData();			// 参数信息
			String unitId = param.getUnitId(); // 单位id
			// 取出导入数据的list
			List<Object> listOfImportData = importData.getListOfImportDataObject();
			BaseUnit loginUnitDto = baseUnitService.getBaseUnit(unitId);
			
			if (loginUnitDto.getUnittype() == BaseUnit.UNIT_NOTEDU_NOTSCH) {
				throw new ImportErrorException("非教育局单位不允许新增下级单位!");
			}
			int topLimit = systemSubsystemService.getUnitCountLimit();
			int allUnit = baseUnitService.getAllUnitCount();
			if (topLimit != 0) {
				if(allUnit >= topLimit){
					throw new ImportErrorException("本平台单位数量已达最大限制:"+topLimit);
				}else{
					if((allUnit+listOfImportData.size())>topLimit){
						throw new ImportErrorException("本平台单位数量最大限制为"+topLimit+"个," +
								"最多还能新增"+(topLimit-allUnit)+"个");
					}
				}
			}
			
			Map<String,BaseUnitImport> insertUnitMap = new HashMap<String,BaseUnitImport>();
			// 单个处理
			for (int i = 0; i < listOfImportData.size(); i++) {
				errPos = i + 1;
				try {
					BaseUnitImport unit = (BaseUnitImport)listOfImportData.get(i);
					BaseUnit unitParent = baseUnitService.getBaseUnit(unit.getParentId());
					//单位名称校验
					if(isContainsEspecial(unit.getUnitName())){
						throw new ErrorFieldException("不能包含特殊字符！","unitName");
					}
					if (StringUtils.getRealLength(unit.getUnitName()) > BaseUnit.NAME_LENGTH) {
						throw new ErrorFieldException("不能超过"+ BaseUnit.NAME_LENGTH
								+ "个字符","unitName");
					}
					unit.setUnitName(unit.getUnitName().replaceAll(" ", ""));
					if (baseUnitService.getCountUnitByName(unit.getUnitName()) > 0) {
						throw new ErrorFieldException("该单位名称已存在！", "unitName");
					}
					//单位类型
					String unitType = unit.getUnitType();
					if(!String.valueOf(BaseUnit.UNIT_SCHOOL_ASP).equals(unitType) 
							&& !String.valueOf(BaseUnit.UNIT_SCHOOL_KINDERGARTEN).equals(unitType)){
						throw new ErrorFieldException("单位类型有误！只能是【托管中小学】或【幼儿园学校】","unitType");
					}
					if(org.apache.commons.lang.StringUtils.isNotBlank(unit.getUnitUseType())){
						if(BasedataConstants.UNIT_USE_TYPE_EDU.equals(unit.getUnitUseType())){
							throw new ErrorFieldException("单位使用类别不能为教育局","unitUseType");
						}
					}
					//学校类型
					String schCode = unit.getSchCode();
					if(String.valueOf(BaseUnit.UNIT_SCHOOL_KINDERGARTEN).equals(unitType)){
//						if(org.apache.commons.lang.StringUtils.isNotBlank(schCode)){
//							throw new ErrorFieldException("当【单位类型】是幼儿园学校时，无需输入！","schCode");
//						}
						if(org.apache.commons.lang.StringUtils.isNotBlank(unit.getSchType()) && unit.getSchType().length()==3){
							// 采用国标形式
							if(!BasedataConstants.SCHOOL_TYPE_KG.equals(unit.getSchType()) 
									&& !BasedataConstants.SCHOOL_TYPE_KG_MINORITY.equals(unit.getSchType())
									&& !BasedataConstants.SCHOOL_TYPE_ATTACHED_KG.equals(unit.getSchType())){
								throw new ErrorFieldException("不属于幼儿园类型！","schType");
							}
						}else{
//							if(!BasedataConstants.SCHOOL_TYPE_KG61.equals(unit.getSchType()) 
//									&& !BasedataConstants.SCHOOL_TYPE_KG_MINORITY62.equals(unit.getSchType())
//									&& !BasedataConstants.SCHOOL_TYPE_ATTACHED_KG69.equals(unit.getSchType())){
								throw new ErrorFieldException("不属于幼儿园类型！","schType");
//							}
						}
					}
					
					// 暂时不做判断，幼儿园直接进学校表 TODO
					if(org.apache.commons.lang.StringUtils.isBlank(schCode)){
						throw new ErrorFieldException("【学校代码】不能为空！","schCode");
					}
					//学校代码
					java.util.regex.Pattern pattern = java.util.regex.Pattern.compile("[0-9]{1,46}");// 验证
					if (!pattern.matcher(schCode).matches()) {
						throw new ErrorFieldException("必须是长度为1-46的数字！","schCode");
					}
					String temp = baseSchoolService.getSchoolIdByCode(schCode);
					if (temp != null) {
						throw new ErrorFieldException("学校代码已存在！请修改","schCode");
					}
					
					if(String.valueOf(BaseUnit.UNIT_SCHOOL_ASP).equals(unitType)){
						if(org.apache.commons.lang.StringUtils.isNotBlank(unit.getSchType()) && unit.getSchType().length()==3){
							// 采用国标形式
							if(BasedataConstants.SCHOOL_TYPE_KG.equals(unit.getSchType()) 
									|| BasedataConstants.SCHOOL_TYPE_KG_MINORITY.equals(unit.getSchType())
									|| BasedataConstants.SCHOOL_TYPE_ATTACHED_KG.equals(unit.getSchType())){
								throw new ErrorFieldException("当【单位类型】为托管中小学时，【学校类别】不能为幼儿园！","schType");
							}
						}else{
//							if(BasedataConstants.SCHOOL_TYPE_KG61.equals(unit.getSchType()) 
//									|| BasedataConstants.SCHOOL_TYPE_KG_MINORITY62.equals(unit.getSchType())
//									|| BasedataConstants.SCHOOL_TYPE_ATTACHED_KG69.equals(unit.getSchType())){
								throw new ErrorFieldException("当【单位类型】为托管中小学时，【学校类别】不能为幼儿园！","schType");
//							}
						}
						
					}
					//用户名规则校验
					String username = unit.getUserName();
					if (!username.matches(systemIniService.getValue(User.SYSTEM_NAME_EXPRESSION))) {
						throw new ErrorFieldException(systemIniService.getValue(User.SYSTEM_NAME_ALERT),"userName");
					}
					if (StringUtils.getRealLength(username) > BaseUnit.NAME_LENGTH) {
						throw new ErrorFieldException(systemIniService.getValue(User.SYSTEM_NAME_ALERT),"userName");
					}
					//用户名是否存在
					if (baseUserService.getUserNameCount(username) >=1){
						throw new ErrorFieldException("该用户名已存在！","userName");
					}
					if (passportAccountService
							.queryAccountByUsername(username) != null) {
						throw new ErrorFieldException("Passport中该用户名已存在！","userName");
					}
					//密码
					if (!unit.getPassword().matches(systemIniService.getValue(User.SYSTEM_PASSWORD_EXPRESSION))) {
						throw new ErrorFieldException(systemIniService.getValue(User.SYSTEM_PASSWORD_ALERT),"password");
					}else {
						if(unit.getPassword().matches(systemIniService.getValue(User.SYSTEM_PASSWORD_STRONG))){
							throw new ErrorFieldException(systemIniService.getValue(User.SYSTEM_PASSWORD_ALERT), "password");
						}
					}
					//生成统一编码
					String unionid = baseUnitService.getUnionidFromCache(unitParent, 
							BaseUnit.UNIT_CLASS_SCHOOL);
					//System.out.println("生成统一编码:"+unionid);
					if (unionid == null || unionid.trim().length() == 0) {
						throw new ErrorFieldException("未生成有效的unionId值","unitName");
					}
					unit.setUnionId(unionid);
					unit.setMark(String.valueOf(BaseUnit.UNIT_MARK_NORAML));// 单位状态
					unit.setUseType(String.valueOf(BaseUnit.UNIT_USETYPE_LOCAL));
					unit.setAuthorized(String.valueOf(BaseUnit.UNIT_AUTHORIZED));
					unit.setRegcode(BaseUnit.UNIT_REGCODE_DEF);
					unit.setUnitUserMark(String.valueOf(User.USER_MARK_NORMAL));
					unit.setUnitUserType(String.valueOf(User.TYPE_ADMIN));
					unit.setLimitTeacher(limitTeacherCount);
					unit.setId(UUIDGenerator.getUUID());
					unit.setOrderId(unionid.replaceAll("[a-zA-Z]+", ""));
					unit.setUnitClass(String.valueOf(BaseUnit.UNIT_CLASS_SCHOOL));  
					unit.setRegionLevel(String.valueOf(unitParent.getRegionlevel()==null?0:unitParent.getRegionlevel()+1));
					unit.setUnitPartitionNum(unitIncre.nextLongValue());
					unit.setRegionCode(takeRegion(unionid));
					unit.setEventSource(EventSourceType.LOCAL.getValue());
					insertUnitMap.put(unit.getUserName(), unit);
					
				} catch (ErrorFieldException e) {
					this.disposeError(importData, i, e.getField(), e.getMessage());
					continue;
				}
			}
			
			if (insertUnitMap.size()==0) {
				reply.setValue(DataImportConstants.STATUS_END);
				return;
			}
			List<String> messageList = processTable(importData,insertUnitMap);
			String[] unitIds = new String[insertUnitMap.size()];
			int n=0;
			for (BaseUnitImport unit : insertUnitMap.values()) {
				unitIds[n] = unit.getId();
				n++;
			}
			List<BaseUnit> units = baseUnitService.getBaseUnits(unitIds);
			for(BaseUnit unit:units){
				baseUnitService.updateUnitCache(unit.getParentid(), unit);
			}
			log.debug("==============导入数据成功================");
			//passport同步
			Set<String> allAddUsernameSet = new HashSet<String>();
			List<User> addUserList = new ArrayList<User>();
			int k =0;
			if (systemDeployService.isConnectPassport()) {
				// 取得需要新增的用户
				allAddUsernameSet = insertUnitMap.keySet();
				log.debug("==============数字校园进行帐户同步================");
				try {
					log.debug("passport增加账户，应该增加：" + allAddUsernameSet.size());
					if (CollectionUtils.isNotEmpty(allAddUsernameSet)) {
						addUserList = baseUserService
								.getUsersByUserNames(allAddUsernameSet
										.toArray(new String[0]));
					}
					User[] retUsers = passportAccountService
							.addAccounts(addUserList.toArray(new User[0]));
					String[] sqls = new String[retUsers.length];
					int i = 0;
					for (User unit : retUsers) {
						sqls[i] = "update base_user set owner_type = "
								+ unit.getOwnerType() + ", account_id = '"
								+ unit.getAccountId() + "',sequence = "
								+ unit.getSequence() +",event_source="+EventSourceType.LOCAL.getValue()+ " where id = '"
								+ unit.getId() + "'";
						i++;
					}
					log.debug("passport增加账户，实际增加:" + sqls.length);
					systemCommonDao.batchUpdate(sqls);

				} catch (OperationNotAllowedException pe) {
					if (CollectionUtils.isNotEmpty(addUserList)) {
						String[] newAccountId = new String[addUserList.size()];
						k = 0;
						for (User user : addUserList) {
							newAccountId[k] = user.getId();
							k++;
						}
						log.error("==============删除不成功的新增用户================");
						baseUserService.deleteUsers(newAccountId);
					}
					log.error("本次新增用户失败，请修改导入数据后再操作。", pe);
					throw new OperationNotAllowedException(pe.getMessage());
				}
			}
			for(String msg:messageList){
				reply.addActionMessage(msg);
			}
		} catch (Exception e) {
			log.error("第 " + errPos + " 个单位错误", e);
			throw new ImportErrorException("第 " + errPos + " 个单位错误,错误原因:"+e.getMessage());
		}
	}
	
	private String takeRegion(String unionId) {
		String region = unionId;
		if (region != null && !"".equals(region)) {
			if (region.length() >= 6) {
				region = region.substring(0, 6);
			} else {
				region = org.apache.commons.lang.StringUtils.rightPad(region, 6,"0");
			}
		}
		return region;
	}
	
	/**
	 * 新增单位信息及跟单位相关联的初始化信息
	 * @param importData
	 * @param insertUnitList
	 * @return
	 * @throws ParseException
	 * @author huy
	 * @date 2014-10-31下午04:39:11
	 */
	private List<String> processTable(ImportData importData,
				Map<String,BaseUnitImport> insertUnitMap) throws ParseException {
		List<String> message = new ArrayList<String>();
		int cnt = 0;
		// 插入操作
		if (insertUnitMap.size() > 0) {
			List<String> unitSqlList = new ArrayList<String>();
			List<String> userSqlList = new ArrayList<String>();
			List<String> deptSqlList = new ArrayList<String>();
			List<String> teacherSqlList = new ArrayList<String>();
			List<String> unitIniSqlList = new ArrayList<String>();
			List<String> roleSqlList = new ArrayList<String>();
			List<String> rolePermsSqlList = new ArrayList<String>();
			List<String> schSqlList = new ArrayList<String>();
			List<String> subschSqlList = new ArrayList<String>();
			List<String> schSemeterSqlList = new ArrayList<String>();
			for (BaseUnitImport unit : insertUnitMap.values()) {
				buildInsertSql(unit, importData, unitSqlList, deptSqlList, userSqlList,
						teacherSqlList, unitIniSqlList, roleSqlList, rolePermsSqlList,
						schSqlList,schSemeterSqlList,subschSqlList);
			}
			if (unitSqlList.size() > 0) {
				cnt = systemCommonDao.batchUpdate(unitSqlList
						.toArray(new String[0]));
				message.add("新增单位信息：" + cnt + " 条。");
			}
			if (deptSqlList.size() > 0) {
				cnt = systemCommonDao.batchUpdate(deptSqlList
						.toArray(new String[0]));
				message.add("新增部门信息：" + cnt + " 条。");
			}
			if (userSqlList.size() > 0) {
				cnt = systemCommonDao.batchUpdate(userSqlList
						.toArray(new String[0]));
				message.add("新增用户信息：" + cnt + " 条。");
			}
			if (teacherSqlList.size() > 0) {
				cnt = systemCommonDao.batchUpdate(teacherSqlList
						.toArray(new String[0]));
				message.add("新增教师信息：" + cnt + " 条。");
			}
			if (unitIniSqlList.size() > 0) {
				cnt = systemCommonDao.batchUpdate(unitIniSqlList
						.toArray(new String[0]));
				message.add("新增单位初始化信息：" + cnt + " 条。");
			}
			if (roleSqlList.size() > 0) {
				cnt = systemCommonDao.batchUpdate(roleSqlList
						.toArray(new String[0]));
				message.add("新增单位角色信息：" + cnt + " 条。");
			}
			if (rolePermsSqlList.size() > 0) {
				cnt = systemCommonDao.batchUpdate(rolePermsSqlList
						.toArray(new String[0]));
				message.add("新增单位权限信息：" + cnt + " 条。");
			}
			if (schSqlList.size() > 0) {
				cnt = systemCommonDao.batchUpdate(schSqlList
						.toArray(new String[0]));
				message.add("新增学校信息：" + cnt + " 条。");
			}
			if (schSemeterSqlList.size() > 0) {
				cnt = systemCommonDao.batchUpdate(schSemeterSqlList
						.toArray(new String[0]));
			}
			if (subschSqlList.size() > 0) {
				cnt = systemCommonDao.batchUpdate(subschSqlList
						.toArray(new String[0]));
				message.add("新增主体校信息：" + cnt + " 条。");
			}
		} else {
			message.add("新增单位信息：0 条。");
		}
		return message;
	}
	
	private void buildInsertSql(BaseUnitImport unit, ImportData importData,
			List<String> unitInsertList, List<String> deptSqlList, List<String> userSqlList,
			List<String> teacherSqlList, List<String> unitIniSqlList, 
			List<String> roleSqlList, List<String> rolePermsSqlList, 
			List<String> schSqlList,List<String> schSemeterSqlList,List<String> subschSqlList) throws ParseException {
		Map<String, ImportObjectNode> nodeMap = importData.getMapOfNodesName();
		List<String> colNames = importData.getListOfImportDataName();// 导入列
		StringBuffer sql = new StringBuffer();
		StringBuffer sqlValue = new StringBuffer();
		// 新增单位信息
		sql.append("insert into base_unit(" +
				"id," +
				"unit_class," +
				"region_level," +
				"region_code," +
//				"serial_number," +
				"display_order," +
				"limit_teacher," +
				"creation_time," +
				"modify_time," +
				"is_deleted," +
				"event_source," +
				"union_code," +
				"poll_code," +
				"unit_state," +
				"use_type," +
				"authorized," +
				"unit_partition_num," +
				"run_school_type," +
				"school_type");
		
		for (String col : colNames) {
			if (org.apache.commons.lang3.StringUtils.isEmpty(col))
				continue;
			Object obj = ObjectUtils.getProperty(unit, col);
			String inValue = String.valueOf(obj);
			ImportObjectNode node = nodeMap.get(col);
			String dbcol = node.getDbname(); // 数据库表使用的字段值
			String outValue = getValueForSQL(node, inValue);
			if (unitFieldSet.contains(col)) {
				sql.append("," + dbcol);
				sqlValue.append("," + outValue);
			}
		}
		sql.append(") values ('")
			.append(unit.getId())
			.append("',")
			.append(unit.getUnitClass())
			.append(",")
			.append(unit.getRegionLevel())
			.append(",'")
			.append(unit.getRegionCode())
			.append("',")
//			.append("S_S_Number.Nextval")
			.append(NumberUtils.toLong(unit.getOrderId()))
			.append(",")
			.append(unit.getLimitTeacher())
			.append(",")
			.append(getFormattedTimeForSql())
			.append(",")
			.append(getFormattedTimeForSql())
			.append(",")
			.append(0)
			.append(",")
			.append(0)
			.append(",'")
			.append(unit.getUnionId())
			.append("','")
			.append(unit.getRegcode())
			.append("',")
			.append(unit.getMark())
			.append(",")
			.append(unit.getUseType())
			.append(",")
			.append(unit.getAuthorized())
			.append(",")
			.append(unit.getUnitPartitionNum())
			.append(",")
			.append(unit.getRunschtype())
			.append(",")
			.append(unit.getSchType());
		sql.append(sqlValue).append(") ");
		unitInsertList.add(sql.toString());
		sql.delete(0, sql.length());
		sqlValue.delete(0, sqlValue.length());
		//学校部门初始化
		String deptId = UUIDGenerator.getUUID();
		String teacherId = UUIDGenerator.getUUID();
		//初始化单位管理组
		Dept dept = new Dept();
		dept.setId(deptId);
		dept.setUnitId(unit.getId());
		dept.setDeptname(Dept.SCH_ADMIN_GROUP_NAME);
		dept.setDeptCode(org.apache.commons.lang.StringUtils.leftPad("1", Dept.GROUPID_LENGTH,"0"));
		dept.setJymark(new Integer(1));// 科室
		dept.setParentid(Dept.TOP_GROUP_GUID);
		dept.setOrderid(1L);
		sql.append("insert into base_dept(" +
					"id," +
					"unit_id," +
					"dept_name," +
					"parent_id," +
					"dept_code," +
					"dept_type," +
					"display_order," +
					"is_default," +
					"creation_time," +
					"modify_time," +
					"is_deleted," +
					"event_source" +
					")values('")
			.append(dept.getId())
			.append("','")
			.append(dept.getUnitId())
			.append("','")
			.append(dept.getDeptname())
			.append("','")
			.append(dept.getParentid())
			.append("','")
			.append(dept.getDeptCode())
			.append("',")
			.append(dept.getJymark())
			.append(",")
			.append(dept.getOrderid())
			.append(",")
			.append(1)
			.append(",")
			.append(getFormattedTimeForSql())
			.append(",")
			.append(getFormattedTimeForSql())
			.append(",")
			.append(0)
			.append(",")
			.append(dept.getEventSourceValue())
			.append(")");
		deptSqlList.add(sql.toString());
		sql.delete(0, sql.length());
		//初始化用户
		User user = new User();
		user.setId(UUIDGenerator.getUUID());
		user.setRealname("管理员");
		user.setUnitid(unit.getId());
		user.setDeptid(deptId);
		user.setTeacherid(teacherId);
		user.setName(unit.getUserName());
		PWD pwd = new PWD();
		pwd.setPassword(unit.getPassword());
		user.setPassword(pwd.encode());
		user.setRegion(unit.getRegionCode());
		user.setAccountId(UUIDGenerator.getUUID());
		user.setOwnerType(User.TEACHER_LOGIN);
		user.setOrderid(1L);
		user.setMark(User.USER_MARK_NORMAL);
		user.setType(User.TYPE_ADMIN);
		sql.append("insert into base_user (")
			.append("id,")
			.append("unit_id,")
			.append("account_id,")
			.append("owner_id,")
			.append("owner_type,")
			.append("username,")
			.append("real_name,")
			.append("user_state,")
			.append("user_type,")
			.append("region_code,")
			.append("display_order,")
			.append("sex,")
			.append("order_status,")
			.append("creation_time,")
			.append("modify_time,")
			.append("is_deleted,")
			.append("event_source,")
			.append("password,")
			.append("dept_id")
			.append(")values('")
			.append(user.getId())
			.append("','")
			.append(user.getUnitid())
			.append("','")
			.append(user.getAccountId())
			.append("','")
			.append(user.getTeacherid())
			.append("',")
			.append(user.getOwnerType())
			.append(",'")
			.append(user.getName())
			.append("','")
			.append(user.getRealname())
			.append("',")
			.append(user.getType())
			.append(",")
			.append(user.getMark())
			.append(",'")
			.append(user.getRegion())
			.append("',")
			.append(user.getOrderid())
			.append(",")
			.append(org.apache.commons.lang.StringUtils.isNotBlank(user.getSex()) ? Integer.valueOf(user.getSex()) : 0 )
			.append(",")
			.append(user.getOrderStatus())
			.append(",")
			.append(getFormattedTimeForSql())
			.append(",")
			.append(getFormattedTimeForSql())
			.append(",")
			.append("0")
			.append(",")
			.append(user.getEventSourceValue())
			.append(",'")
			.append(user.getPassword())
			.append("','")
			.append(user.getDeptid()).append("'")
			.append(")");
		userSqlList.add(sql.toString());
		sql.delete(0, sql.length());
		//初始化教师
		BaseTeacher teacher = new BaseTeacher();
		teacher.setId(teacherId);
		teacher.setTchId("000000");
		teacher.setName(user.getRealname());
		teacher.setSex(BaseConstant.SEX_MALE);
		teacher.setEusing(BasedataConstants.EMPLOYEE_INCUMBENCY);
		teacher.setDeptid(deptId);
		teacher.setUnitid(unit.getId());
		teacher.setCardNumber("");
		teacher.setCreationTime(new Date());
		teacher.setDisplayOrder(1);
		sql.append("insert into base_teacher (")
			.append("id,")
			.append("dept_id,")
			.append("unit_id,")
			.append("teacher_code,")
			.append("teacher_name,")
			.append("sex,")
			.append("incumbency_sign,")
			.append("display_order,")
			.append("charge_number_type")
			.append(")values('")
			.append(teacher.getId())
			.append("','")
			.append(teacher.getDeptid())
			.append("','")
			.append(teacher.getUnitid())
			.append("','")
			.append(teacher.getTchId())
			.append("','")
			.append(teacher.getName())
			.append("',")
			.append(teacher.getSex())
			.append(",'")
			.append(org.apache.commons.lang.StringUtils.isNotBlank(teacher.getEusing())?"":teacher.getEusing())
			.append("',")
			.append(teacher.getDisplayOrder())
			.append(",")
			.append(teacher.getChargeNumberType())
			.append(")");
		teacherSqlList.add(sql.toString());
		sql.delete(0, sql.length());
		
		//单位系统初始化信息
		SystemIni systemIni;
		UnitIni unitIni;
		for (int m = 0; m < sysIniList.size(); m++) {
			systemIni = (SystemIni) sysIniList.get(m);
			unitIni = new UnitIni();
			unitIni.setIniid(systemIni.getIniid());
			unitIni.setName(systemIni.getName());
			unitIni.setDefaultValue(systemIni.getDefaultValue());
			unitIni.setDescription(systemIni.getDescription());
			unitIni.setNowValue(systemIni.getNowValue());
			unitIni.setValidatejs(systemIni.getValidateJS());
			unitIni.setUnitid(unit.getId());
			unitIni.setId(unitIniIncre.nextLongValue());
			sql.append("insert into sys_systemini_unit(")
				.append("id,")
				.append("iniid,")
				.append("name,")
				.append("dvalue,")
				.append("description,")
				.append("nowvalue,")
				.append("unitid,")
				.append("flag,")
				.append("validatejs,")
				.append("coercive,")
				.append("orderid")
				.append(")values('")
				.append(unitIni.getId())
				.append("','")
				.append(unitIni.getIniid())
				.append("','")
				.append(unitIni.getName())
				.append("','")
				.append(unitIni.getDefaultValue())
				.append("','")
				.append(unitIni.getDescription())
				.append("','")
				.append(unitIni.getNowValue())
				.append("','")
				.append(unitIni.getUnitid())
				.append("','")
				.append(org.apache.commons.lang.StringUtils.isNotBlank(unitIni.getFlag())?"":unitIni.getFlag())
				.append("','")
				.append(org.apache.commons.lang.StringUtils.isNotBlank(unitIni.getValidatejs())?"":unitIni.getValidatejs())
				.append("',")
				.append(unitIni.getCoercive())
				.append(",")
				.append(unitIni.getOrderid())
				.append(")");
			unitIniSqlList.add(sql.toString());
			sql.delete(0, sql.length());
		}
		
		List<String> defaultRoleIds = new ArrayList<String>();
		Map<String, Role> relationMap = new HashMap<String, Role>();
		// 单位角色
		for (Iterator<Role> iter = defaultRoleList.iterator(); iter.hasNext();) {
			Role defaultRole = (Role) iter.next();
			defaultRoleIds.add(defaultRole.getId());
			Role newUnitRole = new Role();
			DtoAssembler.toEntity(defaultRole, newUnitRole);
			newUnitRole.setId(UUIDGenerator.getUUID());
			newUnitRole.setUnitid(unit.getId());
			sql.append("insert into sys_role(")
				.append("id,")
				.append("identifier,")
				.append("unitid,")
				.append("name,")
				.append("isactive,")
				.append("description,")
				.append("subsystem,")
				.append("refid,")
				.append("roletype")
				.append(")values('")
				.append(newUnitRole.getId())
				.append("','")
				.append(newUnitRole.getIdentifier())
				.append("','")
				.append(newUnitRole.getUnitid())
				.append("','")
				.append(newUnitRole.getName())
				.append("',")
				.append(newUnitRole.getIsactive()?1:0)
				.append(",'")
				.append(newUnitRole.getDescription())
				.append("','")
				.append(newUnitRole.getSubsystem())
				.append("',")
				.append(newUnitRole.getRefid())
				.append(",")
				.append(newUnitRole.getRoletype())
				.append(")");
			roleSqlList.add(sql.toString());
			sql.delete(0, sql.length());
			relationMap.put(defaultRole.getId(), newUnitRole);
		}
		//单位权限
		if (!roleSqlList.isEmpty()) {
			// 默认系统默认角色权限
			List<RolePerm> defaultRolePermList = rolePermDao
					.getRolePerms(defaultRoleIds.toArray(new String[0]));
			for (Iterator<RolePerm> iter = defaultRolePermList.iterator(); iter
					.hasNext();) {
				RolePerm defaultRolePerm = (RolePerm) iter.next();
				RolePerm newUnitRolePerm = new RolePerm();
				DtoAssembler.toEntity(defaultRolePerm,newUnitRolePerm);
				newUnitRolePerm.setRoleid(relationMap.get(defaultRolePerm.getRoleid()).getId());
				newUnitRolePerm.setId(UUIDGenerator.getUUID());
				sql.append("insert into sys_role_perm (")
					.append("id,")
					.append("moduleid,")
					.append("roleid")
					.append(")values('")
					.append(newUnitRolePerm.getId())
					.append("','")
					.append(newUnitRolePerm.getModuleid())
					.append("','")
					.append(newUnitRolePerm.getRoleid())
					.append("')");
				rolePermsSqlList.add(sql.toString());
				sql.delete(0, sql.length());
			}
		}
		//学校信息
		int[] schoolType = {3, 4, 6, 7};
		String unitType = unit.getUnitType();
	    //幼儿园
	    if(org.apache.commons.lang.StringUtils.equals(String.valueOf(BaseUnit.UNIT_SCHOOL_KINDERGARTEN),unitType)) {
	    	//暂时不使用开关 
//	    	if(org.apache.commons.lang.StringUtils.equals("1", kgSwitchValue)) {
	            schoolType = ArrayUtils.add(schoolType, BaseUnit.UNIT_SCHOOL_KINDERGARTEN);
//	        }
	    }
		if (ArrayUtils.contains(schoolType, Integer.valueOf(unitType))) {
			BaseSchool sch = new BaseSchool();
			sch.setId(unit.getId());
			sch.setName(unit.getUnitName());
			sch.setRegion(unit.getRegionCode());
			sch.setCode(unit.getSchCode());
			sch.setType(unit.getSchType());
			sch.setRunschtype(unit.getRunschtype());
			sch.setGradeyear(6);
			sch.setJunioryear(3);
			sch.setSenioryear(3);
			sch.setRegionPropertyCode(unit.getRegionPropertyCode());
			String schooltype = unit.getSchType();
			if (org.apache.commons.lang.StringUtils.isNotBlank(schooltype)) {
				sch.setSections(baseUnitService.getSections(schooltype));
			}
			sql.append("insert into base_school(")
			.append("id,")
			.append("school_name,")
			.append("school_code,")
			.append("region_code,")
			.append("run_school_type,")
			.append("school_type,")
			.append("grade_year,")
			.append("grade_age,")
			.append("junior_year,")
			.append("junior_age,")
			.append("senior_year,")
			.append("infant_year,")
			.append("infant_age,")
			.append("sections,")
			.append("region_property_code,")
			.append("creation_time,")
			.append("modify_time,")
			.append("is_deleted,")
			.append("event_source")
			.append(")values('")
			.append(sch.getId())
			.append("','")
			.append(sch.getName())
			.append("','")
			.append(sch.getCode()==null?"":sch.getCode())
			.append("','")
			.append(sch.getRegion())
			.append("',")
			.append(sch.getRunschtype())
			.append(",'")
			.append(sch.getType())
			.append("',")
			.append(sch.getGradeyear())
			.append(",")
			.append(sch.getGradeage())
			.append(",")
			.append(sch.getJunioryear())
			.append(",")
			.append(sch.getJuniorage())
			.append(",")
			.append(sch.getSenioryear())
			.append(",")
			.append(sch.getInfantyear())
			.append(",")
			.append(sch.getInfantage())
			.append(",'")
			.append(sch.getSections())
			.append("','")
			.append(sch.getRegionPropertyCode()==null?"":sch.getRegionPropertyCode()) //学校新增 区域属性码
			.append("',")
			.append(getFormattedTimeForSql())
			.append(",")
			.append(getFormattedTimeForSql())
			.append(",")
			.append(0)
			.append(",0")
			.append(")");
			schSqlList.add(sql.toString());
			sql.delete(0, sql.length());
			// 初始化学校学年学期信息
	        for (int i = 0; i < semesterList.size(); i++) {
	            Semester s = (Semester) semesterList.get(i);
	            SchoolSemester b = new SchoolSemester();
	            b.setSchid(sch.getId());
	            b.setAcadyear(s.getAcadyear());
	            b.setSemester(s.getSemester());
	            b.setWorkbegin(s.getWorkBegin());
	            b.setWorkend(s.getWorkEnd());
	            b.setSemesterbegin(s.getSemesterBegin());
	            b.setSemesterend(s.getSemesterEnd());
	            if (s.getRegisterdate() == null)
	                b.setRegisterdate(DateUtils.addDay(s.getSemesterBegin(), 1));
	            else
	                b.setRegisterdate(s.getRegisterdate());
	            b.setClasshour(s.getClasshour());
	            b.setEdudays(s.getEduDays());
	            b.setAmperiods(s.getAmPeriods());
	            b.setPmperiods(s.getPmPeriods());
	            b.setNightperiods(s.getNightPeriods());
	        }
	        sql.append("insert into stusys_semester (id,school_id,acadyear,semester,work_begin,work_end,"
            		+ " semester_begin,semester_end,register_date,edu_days,class_hour,am_periods,"
            		+ " pm_periods,night_periods,is_deleted,updatestamp)"
            		+ " select sys_guid(),'"+sch.getId()+"',acadyear,semester,work_begin,work_end," 
            		+ " semester_begin,semester_end,(case when register_date is null then sysdate else register_date end) as register_date ,edu_days,class_hour,am_periods,"
            		+ " pm_periods,night_periods,0,"+System.currentTimeMillis()+" from base_semester");
	        schSemeterSqlList.add(sql.toString());
            sql.delete(0, sql.length());
	        // 检查有无分校区，若没有则添加默认同名的分校区
	        if (sch.getId() != null && !("".equals(sch.getId()))) {
                SubSchool subsch = new SubSchool();
                subsch.setSchid(sch.getId());
                subsch.setAddress(sch.getAddress());
                subsch.setName("主体校");
                sql.append("insert into stusys_subschool(id,school_id,name,is_deleted,updatestamp,sections,grade_year,grade_age,junior_year,junior_age)")
                .append("values('").append(UUIDGenerator.getUUID()).append("','").append(subsch.getSchid())
                .append("','").append(subsch.getName()).append("',0,").append(System.currentTimeMillis())
                .append(",'").append(sch.getSections()).append("',").append(sch.getGradeyear()).append(",")
                .append(sch.getGradeage()).append(",").append(sch.getJunioryear()).append(",").append(sch.getJuniorage())
                .append(")");
                subschSqlList.add(sql.toString());
	            sql.delete(0, sql.length());
	        }
	        
		}
	    
	}
	@Override
	public Map<String, Map<String, String>> getConstraintFields(DataImportParam param) {
		String region = systemIniService.getValue(SysOption.SYSTEM_DEPLOY_REGION);
		String unitId = param.getUnitId();
		BaseUnit _unitDto = baseUnitService.getBaseUnit(unitId);
		Map<String, Map<String, String>> constrMap = new HashMap<String, Map<String,String>>();
		//获取本单位下所有教育局
		List<BaseUnit> listOfUnitDto = baseUnitService.getBaseUnitsByUnionCodeUnitType(
				_unitDto.getUnionid(), BaseUnit.UNIT_CLASS_EDU,BaseUnit.UNIT_NOTEDU_NOTSCH);
		Map<String, String> unitMap = new LinkedHashMap<String, String>();
		for(BaseUnit unit:listOfUnitDto){
			unitMap.put(unit.getName(), unit.getId());
		}
		constrMap.put("上级单位", unitMap);
		Map<String, String> unitTypeMap = new HashMap<String, String>();
		//可选的单位类型只有“托管中小学”“托管幼儿园”
		unitTypeMap.put(baseMcodeDetailService.getMcodeDetail(unitTypeMcodeId, 
				String.valueOf(BaseUnit.UNIT_SCHOOL_ASP)).getContent(),String.valueOf(BaseUnit.UNIT_SCHOOL_ASP));
//		if(!org.apache.commons.lang.StringUtils.equals(region, BaseConstant.GSLN)){
		Mcodedetail mcodedetail = baseMcodeDetailService.getMcodeDetail(unitTypeMcodeId, 
				String.valueOf(BaseUnit.UNIT_SCHOOL_KINDERGARTEN));
		if(mcodedetail!=null && mcodedetail.getIsUsing()==1)
			unitTypeMap.put(mcodedetail.getContent(),String.valueOf(BaseUnit.UNIT_SCHOOL_KINDERGARTEN));
//		}
		constrMap.put("单位类型", unitTypeMap);
		
		return constrMap;
	}

	public List<ImportObjectNode> getDynamicFields(DataImportParam param) {
		List<ImportObjectNode> list = new ArrayList<ImportObjectNode>();
		return list;
	}

	@Override
	public List<List<String[]>> exportDatas(ImportObject importObject,
			String[] cols) {
		return null;
	}

	public void setSystemCommonDao(SystemCommonDao systemCommonDao) {
		this.systemCommonDao = systemCommonDao;
	}

	public void setBaseUnitService(BaseUnitService baseUnitService) {
		this.baseUnitService = baseUnitService;
	}
	
	
	public static boolean isContainsEspecial(String content) {
        // 如果是存在[].在字符中需用\转义
        // String regularExpression="[@\"*&%$#!$'?/>:;{}<,~`-+\\.\\[\\]]";
        // //"[@\"*&%$#!$']"
        String regularExpression = "[:/@\"*&%$,#;!$'-+\\.\\[\\]]"; // "[@\"*&%$#!$']"
        PatternCompiler compiler = new Perl5Compiler();
        Pattern pattern;
        try {
            pattern = compiler.compile(regularExpression);
            PatternMatcher matcher = new Perl5Matcher();
            return matcher.contains(content, pattern);
        }
        catch (MalformedPatternException e) {
            e.printStackTrace();
        }
        return true;
    }

	public void setBaseSchoolService(BaseSchoolService baseSchoolService) {
		this.baseSchoolService = baseSchoolService;
	}

	public void setBaseUserService(BaseUserService baseUserService) {
		this.baseUserService = baseUserService;
	}

	public void setSystemSubsystemService(
			SystemSubsystemService systemSubsystemService) {
		this.systemSubsystemService = systemSubsystemService;
	}

	public void setSystemIniService(SystemIniService systemIniService) {
		this.systemIniService = systemIniService;
	}

	public void setIncre(DataFieldMaxValueIncrementer unitIncre) {
		this.unitIncre = unitIncre;
	}

	public void setRoleService(RoleService roleService) {
		this.roleService = roleService;
	}

	public void setRolePermDao(RolePermDao rolePermDao) {
		this.rolePermDao = rolePermDao;
	}

	public void setPassportAccountService(
			PassportAccountService passportAccountService) {
		this.passportAccountService = passportAccountService;
	}

	public void setUnitIniIncre(DataFieldMaxValueIncrementer unitIniIncre) {
		this.unitIniIncre = unitIniIncre;
	}

	public DataFieldMaxValueIncrementer getUnitIncre() {
		return unitIncre;
	}

	public void setUnitIncre(DataFieldMaxValueIncrementer unitIncre) {
		this.unitIncre = unitIncre;
	}

	public DataFieldMaxValueIncrementer getUnitIniIncre() {
		return unitIniIncre;
	}

	public void setSystemDeployService(SystemDeployService systemDeployService) {
		this.systemDeployService = systemDeployService;
	}

	public void setSemesterService(SemesterService semesterService) {
		this.semesterService = semesterService;
	}
	
}
