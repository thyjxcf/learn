package net.zdsoft.eis.base.data.service.impl;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import net.zdsoft.eis.base.common.entity.Dept;
import net.zdsoft.eis.base.common.entity.Unit;
import net.zdsoft.eis.base.common.entity.User;
import net.zdsoft.eis.base.common.service.SystemIniService;
import net.zdsoft.eis.base.constant.BaseConstant;
import net.zdsoft.eis.base.data.BasedataConstants;
import net.zdsoft.eis.base.data.entity.BaseTeacher;
import net.zdsoft.eis.base.data.entity.BaseUnit;
import net.zdsoft.eis.base.data.entity.TeacherImport;
import net.zdsoft.eis.base.data.service.BaseDeptService;
import net.zdsoft.eis.base.data.service.BaseTeacherService;
import net.zdsoft.eis.base.data.service.BaseUnitService;
import net.zdsoft.eis.base.data.service.BaseUserService;
import net.zdsoft.eis.base.data.service.PassportAccountService;
import net.zdsoft.eis.base.deploy.SystemDeployService;
import net.zdsoft.eis.base.sync.EventSourceType;
import net.zdsoft.eis.system.constant.SystemConstant;
import net.zdsoft.keel.action.Reply;
import net.zdsoft.keel.util.ObjectUtils;
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
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TeacherSxrrtImportServiceImpl extends AbstractDataImportService{

	private Logger log = LoggerFactory
			.getLogger(TeacherSxrrtImportServiceImpl.class);
	
	// 用户表的中的字段
	static Set<String> userFieldSet = new HashSet<String>();

	// 教职工表中的字段
	static Set<String> teacherFieldSet = new HashSet<String>();

	static {

		// 字段信息见配置文件 teacher_import.xml
		userFieldSet.add("username");
		userFieldSet.add("password");

		// 3个字段
		teacherFieldSet.add("unitid");
		teacherFieldSet.add("teachername");
		teacherFieldSet.add("sex");
		teacherFieldSet.add("email");

	}
	
	private BaseUnitService baseUnitService;
	private BaseUserService baseUserService;
	private BaseTeacherService baseTeacherService;
	private BaseDeptService baseDeptService;
	private SystemIniService systemIniService;
	private SystemCommonDao systemCommonDao; // 通用的处理类
	private SystemDeployService systemDeployService;
	private PassportAccountService passportAccountService;
	
	public void setBaseUnitService(BaseUnitService baseUnitService) {
		this.baseUnitService = baseUnitService;
	}

	public void setBaseUserService(BaseUserService baseUserService) {
		this.baseUserService = baseUserService;
	}

	public void setBaseTeacherService(BaseTeacherService baseTeacherService) {
		this.baseTeacherService = baseTeacherService;
	}

	public void setBaseDeptService(BaseDeptService baseDeptService) {
		this.baseDeptService = baseDeptService;
	}

	public void setSystemIniService(SystemIniService systemIniService) {
		this.systemIniService = systemIniService;
	}

	public void setSystemCommonDao(SystemCommonDao systemCommonDao) {
		this.systemCommonDao = systemCommonDao;
	}

	public void setSystemDeployService(SystemDeployService systemDeployService) {
		this.systemDeployService = systemDeployService;
	}

	public void setPassportAccountService(
			PassportAccountService passportAccountService) {
		this.passportAccountService = passportAccountService;
	}

	@Override
	public void importDatas(DataImportParam param, Reply reply)
			throws ImportErrorException {
		
		Map<String, Integer> limitTeacherInUnitMap = new HashMap<String, Integer>(); // 每个单位的教职工限额
		Map<String, Set<String>> addUsernameMap = new HashMap<String, Set<String>>();
		
		// 下面四个用于数字校园部署中，新增用户或更新用户
		Set<String> allAddUsernameSet = new HashSet<String>();
		Set<String> allModifyUsernameSet = new HashSet<String>();
		List<User> addUserList = new ArrayList<User>();
		List<User> modifyUserList = new ArrayList<User>();
		
		// 用于记录哪些用户是现在新增的
		int k = 0;
		
		// 错误数据位置
		int errPos = 0;
		try {
			log.debug("==============准备参数信息================");

			// 取得导入文件对象
			ImportData importData = param.getImportData();

			// 参数信息
			String unitId = param.getUnitId(); // 单位id
			String objectName = param.getObjectName(); // 对象名称
			String isConvered = param.getCovered(); // 是否覆盖
			
			// 取出导入数据的list
			List<Object> listOfImportData = importData
					.getListOfImportDataObject();

			// 导入列map
			Set<String> colNameSet = new HashSet<String>();
			List<String> colNames = importData.getListOfImportDataName();// 导入列
			for (String col : colNames) {
				colNameSet.add(col);
			}
			
			log.debug("==============所属单位和所在部门首先特殊处理================");
			
			Unit u = baseUnitService.getUnit(unitId);
			
			Map<String, Unit> allSubUnitMap = new HashMap<String, Unit>();
			if (SystemConstant.TEACHER_IMPORT_EDU.equals(objectName)) {
				allSubUnitMap.put(u.getName(), u); // 先把本单位加进去
				allSubUnitMap.putAll(getAllSubUnitMap(unitId));
			}
			
			// 惟一性数据
			Map<String, Set<String>> tchCodeMap = new HashMap<String, Set<String>>(); // 单位下用职工编号MAP
			Map<String, User> usernameMap = new HashMap<String, User>(); // 用户名
			
			getUniqueMap(listOfImportData, objectName, unitId, allSubUnitMap,
					tchCodeMap, usernameMap);
			
			log.debug("==============验证数据有效性并导入数据================");
			
			// 新增
			Map<String, TeacherImport> insertTeacherMap = new HashMap<String, TeacherImport>();
			
			List<String> allTeaCode = getAllTeaCode();
			for (int i = 0; i < listOfImportData.size(); i++) {
				errPos = i;
				TeacherImport teacher = (TeacherImport) listOfImportData.get(i);

				// 所属单位: 去掉中英文空格
				if (colNameSet.contains("unitid")) {
					teacher.setUnitid(teacher.getUnitid().replaceAll(" ", ""));
				}

				// 姓名：去掉中英文空格
				teacher.setTeachername(teacher.getTeachername().replaceAll(" ", ""));
				
				try {
					if (SystemConstant.TEACHER_IMPORT_EDU.equals(objectName)) {
						Unit unit = allSubUnitMap.get(teacher.getUnitid());
						if (null == unit) {
							throw new ErrorFieldException(
									"在系统中不存在或者不是用户所在单位及其下属单位。", "unitid");
						} else {
							teacher.setUnitid(unit.getId());
							teacher.setRegioncode(unit.getRegion());
						}

						// 学校端导入
					} else {
						teacher.setUnitid(unitId);
						teacher.setRegioncode(u.getRegion());
					}
					
					// 设置每个单位限制的人数
					Integer limitTeacher = 0;
					if (!limitTeacherInUnitMap.keySet().contains(
							teacher.getUnitid())) {
						BaseUnit bu = baseUnitService.getBaseUnit(teacher
								.getUnitid());
						limitTeacher = bu.getLimitTeacher();
						// 如果没有设置或者设置为0的，都是不受人数限制的
						if (limitTeacher != null && limitTeacher != 0) {
							Integer count = baseUserService.getUserCount(
									teacher.getUnitid(), User.TEACHER_LOGIN,
									User.USER_TYPE_COMMON);
							limitTeacherInUnitMap.put(teacher.getUnitid(),
									limitTeacher - count);
						}
					}
					
					//设置部门为单位默认部门,若无默认部门，则设置为32个0
					Dept dept = baseDeptService.getDefaultDept(teacher.getUnitid());
					if(dept != null){
						teacher.setDeptid(dept.getId());
					} else {
						teacher.setDeptid(BaseConstant.ZERO_GUID);
					}
					
					//默认为“在职”
					teacher.setIncumbencysign(BasedataConstants.EMPLOYEE_INCUMBENCY);
					
					// 姓名校验
					if (StringUtils.isNotBlank(teacher.getTeachername())) {
						Pattern pattern = Pattern
								.compile("[a-zA-Z\u4e00-\u9fa5]{1}|[a-zA-Z\u4e00-\u9fa5]{1}[\u4e00-\u9fa5\\w·\\. ]*[\u4e00-\u9fa5\\w]{1}");// 验证
						if (!pattern.matcher(teacher.getTeachername())
								.matches()) {
							throw new ErrorFieldException(
									"只能由汉字、字母、数字、点号、空格和下划线，且须以汉字或字母开头、非空格结尾",
									"teachername");
						}
					}
					
					teacher.setId(UUIDGenerator.getUUID());
					
					// 设置教工编号
					Set<String> codeSet = tchCodeMap.get(teacher.getUnitid());
					List<String> list = new ArrayList<String>();
					list.addAll(allTeaCode);
					String teaCode = this.getAvaTeaCode(list, codeSet);
					teacher.setTeachercode(teaCode);
					codeSet.add(teaCode);
					tchCodeMap.put(teacher.getUnitid(), codeSet);
					
					// 用户名校验
					if ((!StringUtils.isBlank(teacher.getUsername())) && (!teacher.getUsername().matches(systemIniService.getValue(User.SYSTEM_NAME_EXPRESSION)))) {
						throw new ErrorFieldException(systemIniService.getValue(User.SYSTEM_NAME_ALERT), "username");
					}
					
					// 账号校验
					User user1 = usernameMap.get(teacher.getUsername());
					if (null != user1) {
						if (!user1.getTeacherid().equals(teacher.getId())) {
							if (user1.getUnitid().equals(teacher.getUnitid())) {
								throw new ErrorFieldException("已经被本单位 "
										+ user1.getRealname() + " 使用。",
										"username");
							} else {
								throw new ErrorFieldException("已经被 "
										+ baseUnitService.getUnit(
												user1.getUnitid()).getName()
										+ " 单位，姓名：" + user1.getRealname()
										+ " 使用。", "username");
							}
						}
					} 
					
					// 密码加密
					PWD pwd = new PWD();
					String password = teacher.getPassword();
					if (StringUtils.isBlank(password)) {
						pwd.setPassword(BaseConstant.PASSWORD_INIT);
					} else {
						pwd.setPassword(password);
					}
					
					if (StringUtils.isNotBlank(teacher.getPassword())) {
						if(!teacher.getPassword().matches(systemIniService.getValue(User.SYSTEM_PASSWORD_EXPRESSION)))
							throw new ErrorFieldException("密码为6-18个字符，由英文字母（区分大小写）、数字或符号组成。", "password");
					}
					
					password = pwd.encode();
					teacher.setPassword(password);
					
					//均做新增操作
					insertTeacherMap.put(teacher.getUsername(), teacher);
					Set<String> set = addUsernameMap.get(teacher
							.getUnitid());
					if (set == null) {
						set = new HashSet<String>();
					}
					set.add(teacher.getUsername());
					addUsernameMap.put(teacher.getUnitid(), set);
					
				} catch (ErrorFieldException e) {
					this.disposeError(importData, i, e.getField(),
							e.getMessage());
					continue;
				}
			}
			
			// 验证每个单位新增的人数是否超过限制的人数
			for (String uId : addUsernameMap.keySet()) {
				Integer limitTeacher = limitTeacherInUnitMap.get(uId);
				Set<String> addUsernameSet = addUsernameMap.get(uId);
				if (CollectionUtils.isNotEmpty(addUsernameSet)) {
					if (limitTeacher != null
							&& limitTeacher < addUsernameSet.size()) {
						if (SystemConstant.TEACHER_IMPORT_EDU
								.equals(objectName)) {
							reply.addActionMessage("-->单位["
									+ baseUnitService.getUnit(uId).getName()
									+ "]只能再增加 " + limitTeacher
									+ " 个教师用户信息，本次导入新增 "
									+ addUsernameSet.size() + " 名教师信息，无法再导入");
							log.warn("单位["
									+ baseUnitService.getUnit(uId).getName()
									+ "]只能再增加" + limitTeacher
									+ "个教师用户信息，本次导入新增" + addUsernameSet.size()
									+ "名教师信息，该单位导入无效");
						} else {
							reply.addActionMessage("-->本单位只能再增加 "
									+ limitTeacher + " 个教师用户信息，本次导入新增 "
									+ addUsernameSet.size() + " 名教师信息，无法再导入");
							log.warn("本单位只能再增加" + limitTeacher
									+ "个教师用户信息，本次导入新增" + addUsernameSet.size()
									+ "名教师信息，该单位导入无效");
						}
						addUsernameMap.remove(uId);
						// 只对新增进行处理
						for (String username : addUsernameSet) {
							if (insertTeacherMap.keySet().contains(username)) {
								insertTeacherMap.remove(username);
							}
						}
						continue;
					}
				}
			}

			if (insertTeacherMap.size() == 0) {
				reply.setValue(DataImportConstants.STATUS_END);
				return;
			}
			
			// log.debug("==============开始导入数据:职工表，用户表================");
			// 处理教师表和用户表
			List<String> messageList =processTable(importData, reply, isConvered, objectName,
					insertTeacherMap);
			
			log.debug("==============导入数据成功================");
			
			// 给没有设置角色的用户设置默认角色
			systemCommonDao
					.commonExec("insert into sys_user_role (id,userid, roleid, flag) select sys_guid(),base_user.id, "
							+ "sys_role.id, 0 from base_user, sys_role where base_user.unit_id = sys_role.unitid and sys_role.identifier='default' "
							+ "and base_user.is_deleted = '0' and base_user.modify_time >= sysdate and base_user.id not in (select userid from sys_user_role)");
			log.debug("给没有设置角色的用户设置默认角色：");

			// 数字校园进行帐户同步
			if (systemDeployService.isConnectPassport()) {

				// 取得需要新增的用户
				allAddUsernameSet = insertTeacherMap.keySet();

				log.debug("==============数字校园进行帐户同步================");
				try {

					log.debug("passport增加账户，应该增加：" + allAddUsernameSet.size());

					if (CollectionUtils.isNotEmpty(allAddUsernameSet)) {
						addUserList = baseUserService
								.getUsersByUserNames(allAddUsernameSet
										.toArray(new String[0]));
					}

					for (User user : addUserList) {
						user.setOwnerType(User.TEACHER_LOGIN);
						user.setAccountId(UUIDGenerator.getUUID());
					}

					User[] retUsers = passportAccountService
							.addAccounts(addUserList.toArray(new User[0]));
					String[] sqls = new String[retUsers.length];
					int i = 0;
					for (User user : retUsers) {
						sqls[i] = "update base_user set owner_type = "
								+ user.getOwnerType() + ", account_id = '"
								+ user.getAccountId() + "',sequence = "
								+ user.getSequence() + ",event_source="
								+ EventSourceType.LOCAL.getValue()
								+ " where id = '" + user.getId() + "'";
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
//									reply.addActionMessage("本次新增用户失败，请修改导入数据后再操作，错误信息："
//											+ pe.getMessage());
					}
					log.error("本次新增用户失败，请修改导入数据后再操作。", pe);
					throw new OperationNotAllowedException(pe.getMessage());
				}

				if ("1".equals(isConvered)) {
					try {
						log.debug("passport修改账户，应该修改："
								+ allModifyUsernameSet.size());
						if (CollectionUtils.isNotEmpty(allModifyUsernameSet)) {
							modifyUserList = baseUserService
									.getUsersByUserNames(allModifyUsernameSet
											.toArray(new String[0]));
						}
						passportAccountService.modifyAccounts(
								modifyUserList.toArray(new User[0]), null);
						log.debug("passport修改账户，实际修改：" + modifyUserList.size());
					} catch (OperationNotAllowedException pe) {
						log.error("本次修改用户失败，请修改导入数据后再操作。", pe);
						throw new OperationNotAllowedException(pe.getMessage());
					}
				}
			}
			
			for(String msg:messageList){
				reply.addActionMessage(msg);
			}
		} catch (Exception e) {
			log.error("第 " + errPos + " 个教职工错误", e);
			throw new ImportErrorException("导入出错:" + e.getMessage());
		}
		
	}

	@Override
	public List<List<String[]>> exportDatas(ImportObject importObject,
			String[] cols) {
		List<List<String[]>> dataList = new ArrayList<List<String[]>>();
		return dataList;
	}

	private Map<String, Unit> getAllSubUnitMap(String unitId) {
		Map<String, Unit> temp = null;
		// 获取当前教育局的直属学校
		Map<String, Unit> unitNameMap = getDirSubUnitMap(unitId,
				Unit.UNIT_CLASS_SCHOOL);

		// 获取下属教育局单位的直属学校
		Map<String, Unit> eduNameMap = getDirSubUnitMap(unitId,
				Unit.UNIT_CLASS_EDU);
		if (eduNameMap != null && eduNameMap.size() > 0) {
			for (String unitName : eduNameMap.keySet()) {
				Unit u = eduNameMap.get(unitName);
				unitNameMap.put(unitName, u); // 教育局的信息先加进去
				temp = getAllSubUnitMap(u.getId());
				if (temp != null && temp.size() > 0) {
					unitNameMap.putAll(temp);
				}
			}
		}
		return unitNameMap;
	}
	
	private Map<String, Unit> getDirSubUnitMap(String unitId, Integer type) {
		List<Unit> unitList = baseUnitService.getUnderlingUnits(unitId, type);
		Map<String, Unit> unitMap = new HashMap<String, Unit>();
		for (Unit unit : unitList) {
			unitMap.put(unit.getName(), unit);
		}
		return unitMap;
	}
	
	private void getUniqueMap(List<Object> listOfImportData, String objectName,
			String unitId, Map<String, Unit> allSubUnitMap, // 拿来使用的
			Map<String, Set<String>> tchCodeMap, // 职工编号(按单位来分)
			Map<String, User> usernameMap // 用户名
	) {

		Set<String> unitIds = new HashSet<String>();
		List<String> usernameList = new ArrayList<String>();

		for (int i = 0; i < listOfImportData.size(); i++) {
			TeacherImport teacher = (TeacherImport) listOfImportData.get(i);

			// 某单位的职工编号
			// 如果是教育局端导入
			if (SystemConstant.TEACHER_IMPORT_EDU.equals(objectName)) {
				teacher.setUnitid(teacher.getUnitid().replaceAll(" ", ""));
				Unit u = allSubUnitMap.get(teacher.getUnitid());
				if (null != u) {
					unitIds.add(u.getId());

					// 用户名
					if (StringUtils.isNotBlank(teacher.getUsername())) {
						usernameList.add(teacher.getUsername());
					}
				}

				// 学校端导入
			} else {
				unitIds.add(unitId);

				// 用户名
				if (StringUtils.isNotBlank(teacher.getUsername())) {
					usernameList.add(teacher.getUsername());
				}
			}
		}

		// 职工编号
		tchCodeMap.putAll(baseTeacherService.getTeacherCodeByUnitIds(unitIds.toArray(new String[0])));

		// 用户名
		if (usernameList.size() > 0) {
			usernameMap.putAll(getUsernameMap(usernameList));
		}

	}
	
	private String getAvaTeaCode(List<String> list, Set<String> codeSet){
		list.removeAll(codeSet);
		if (CollectionUtils.isEmpty(list)) {
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < BaseTeacher.EMPID_LENGTH - 1; i++) {
				sb.append("0");
			}
			return sb.append("1").toString();
		}
		return (String) list.get(0);
	}
	
	private List<String> getAllTeaCode(){
		List<String> list = new LinkedList<String>();
		double length = Math.pow(10, BaseTeacher.EMPID_LENGTH);
		StringBuffer sb;
		for (int i = 1; i < length; i++) {
			sb = new StringBuffer();
			for (int j = 0; j < BaseTeacher.EMPID_LENGTH
					- String.valueOf(i).length(); j++) {
				sb.append("0");
			}
			sb.append(String.valueOf(i));
			list.add(sb.toString());
		}
		return list;
	}
	
	private Map<String, User> getUsernameMap(List<String> usernameList) {
		return baseUserService.getUsersMapByName(usernameList
				.toArray(new String[0]));
	}
	
	/**
	 * 增加修改教职工用户记录
	 * 
	 * @param importData
	 * @param reply
	 * @param isCovered
	 * @param insertTeacherMap
	 * @throws ParseException
	 */
	private List<String> processTable(ImportData importData, Reply reply,
			String isCovered, String objectName,
			Map<String, TeacherImport> insertTeacherMap) throws ParseException {
		List<String> message = new ArrayList<String>();
		int cnt = 0;
		// 插入操作
		if (insertTeacherMap.size() > 0) {
			List<String> teacherSqlList = new ArrayList<String>();
			List<String> userSqlList = new ArrayList<String>();
			for (TeacherImport teacher : insertTeacherMap.values()) {
				buildInsertSql(teacher, importData, objectName, teacherSqlList,
						userSqlList);
			}

			if (teacherSqlList.size() > 0) {
				cnt = systemCommonDao.batchUpdate(teacherSqlList
						.toArray(new String[0]));
				message.add("新增教职工信息：" + cnt + " 条。");
			}

			if (userSqlList.size() > 0) {
				cnt = systemCommonDao.batchUpdate(userSqlList
						.toArray(new String[0]));
				message.add("新增用户信息：" + cnt + " 条。");
			}
		} else {
			message.add("新增教职工信息：0 条。");
			message.add("新增用户信息：0 条。");
		}
		return message;
	}
	
	/**
	 * 批量插入
	 * 
	 * @param teacher
	 * @param importData
	 * @param teacherInsertList
	 * @param userInsertList
	 */

	private void buildInsertSql(TeacherImport teacher, ImportData importData,
			String objectName, List<String> teacherInsertList,
			List<String> userInsertList) throws ParseException {

		Map<String, ImportObjectNode> nodeMap = importData.getMapOfNodesName();
		List<String> colNames = importData.getListOfImportDataName();// 导入列

		StringBuffer sql = new StringBuffer();
		StringBuffer sqlValue = new StringBuffer();

		// 新增教职工信息
		if (SystemConstant.TEACHER_IMPORT_EDU.equals(objectName)) {
			sql.append("insert into base_teacher(id, is_deleted,event_source, modify_time,creation_time, region_code ");
		} else {
			sql.append("insert into base_teacher(id, unit_id, is_deleted,event_source, modify_time, creation_time, region_code ");
		}

		for (String col : colNames) {
			if (StringUtils.isEmpty(col))
				continue;
			Object obj = ObjectUtils.getProperty(teacher, col);// 从teacher中取值
			String inValue = String.valueOf(obj);
			ImportObjectNode node = nodeMap.get(col);
			String dbcol = node.getDbname(); // 数据库表使用的字段值
			String outValue = null;
			if("YearMonth".equalsIgnoreCase(node.getType())){
				outValue = getFormattedDateByDayForSQL(inValue);
			}else{
				outValue = getValueForSQL(node, inValue);
			}

			if (!"incumbencysign".equals(col) && teacherFieldSet.contains(col)) {
				sql.append("," + dbcol);
				sqlValue.append("," + outValue);
			}

		}
		
		// 教师编号
		sql.append(",teacher_code");
		sqlValue.append(",'" + teacher.getTeachercode() + "'");
		
		// 部门
		if(StringUtils.isNotBlank(teacher.getDeptid())){
			sql.append(",dept_id");
			sqlValue.append(",'" + teacher.getDeptid() + "'");
		}
		
		// 在职标记
		sql.append(",incumbency_sign");
		sqlValue.append(",'" + teacher.getIncumbencysign() + "'");

		sql.append(") values ('").append(teacher.getId());
		if (SystemConstant.TEACHER_IMPORT_EDU.equals(objectName)) {
			sql.append("',0,").append(EventSourceType.LOCAL.getValue())
					.append(",");
		} else {
			sql.append("','").append(teacher.getUnitid()).append("',0,")
					.append(EventSourceType.LOCAL.getValue()).append(",");
		}
		sql.append(getFormattedTimeForSql()).append(",")
				.append(getFormattedTimeForSql()).append(",'")
				.append(teacher.getRegioncode()).append("'").append(sqlValue)
				.append(") ");

		teacherInsertList.add(sql.toString());

		// ---------------------------------------------------

		// 新增用户信息
		sql.delete(0, sql.length());
		sqlValue.delete(0, sqlValue.length());
		sql.append(" insert into base_user(id, owner_type, real_name, owner_id, unit_id,creation_time,user_type, user_state, is_deleted,event_source, modify_time,display_order,region_code,sex,email");
		for (String col : colNames) {
			if (StringUtils.isEmpty(col))
				continue;
			Object obj = ObjectUtils.getProperty(teacher, col);// 从teacher中取值
			String inValue = String.valueOf(obj);
			ImportObjectNode node = nodeMap.get(col);
			String dbcol = node.getDbname(); // 数据库表使用的字段值
			String outValue = getValueForSQL(node, inValue);

			if (userFieldSet.contains(col)) {
				sql.append("," + dbcol);
				sqlValue.append("," + outValue);
			}
		}

		// 密码
		if (!colNames.contains("password")) {
			sql.append(",password");
			sqlValue.append(",'" + teacher.getPassword() + "'");
		}
		
		if(StringUtils.isNotBlank(teacher.getDeptid())){
			sql.append(",dept_id");
			sqlValue.append(",'" + teacher.getDeptid() + "'");
		}
		
		// 和新增教职工的业务保持一致 2009-10-23 mark = "2" 现在是不用了
		String mark = "1";
		if (StringUtils.isNotBlank(teacher.getIncumbencysign())) {
			String eusing = teacher.getIncumbencysign();
			if (ArrayUtils.contains(new String[] {
					BasedataConstants.EMPLOYEE_DIMISSION,
					BasedataConstants.EMPLOYEE_RETIRE,
					BasedataConstants.EMPLOYEE_STELLENBOSCH,
					BasedataConstants.EMPLOYEE_DEAD,
					BasedataConstants.EMPLOYEE_REMOVE,
					BasedataConstants.EMPLOYEE_RESIGNATION,
					BasedataConstants.EMPLOYEE_OTHER_DIMISSION }, eusing)) {
				mark = "3";
			}
		}

		sql.append(") values ('")
				.append(UUIDGenerator.getUUID())
				.append("',")
				.append(User.TEACHER_LOGIN)
				.append(",'")
				.append(teacher.getTeachername())
				.append("','")
				.append(teacher.getId())
				.append("','")
				.append(teacher.getUnitid())
				.append("',sysdate, 2, ")
				.append(mark)
				.append(",0,")
				.append(EventSourceType.LOCAL.getValue())
				.append(",")
				.append(getFormattedTimeForSql())
				.append(",(select max(display_order)+1 from base_user where is_deleted = 0 and unit_id = '")
				.append(teacher.getUnitid() + "')")
				.append(",'")
				.append(teacher.getRegioncode())
				.append("'")
				.append(",")
				.append(teacher.getSex())
				.append(",'")
				.append(StringUtils.isBlank(teacher.getEmail()) ? "" : teacher
						.getEmail()).append("'").append(sqlValue + ")");
		userInsertList.add(sql.toString());

	}
	
}
