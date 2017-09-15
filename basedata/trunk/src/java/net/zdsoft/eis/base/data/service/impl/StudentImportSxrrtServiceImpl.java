package net.zdsoft.eis.base.data.service.impl;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import net.zdsoft.eis.base.common.entity.BasicClass;
import net.zdsoft.eis.base.common.entity.School;
import net.zdsoft.eis.base.common.entity.Unit;
import net.zdsoft.eis.base.common.entity.User;
import net.zdsoft.eis.base.common.service.BasicClassService;
import net.zdsoft.eis.base.common.service.SchoolService;
import net.zdsoft.eis.base.common.service.SystemIniService;
import net.zdsoft.eis.base.common.service.UnitService;
import net.zdsoft.eis.base.constant.BaseConstant;
import net.zdsoft.eis.base.data.BasedataConstants;
import net.zdsoft.eis.base.data.dao.EisBaseStudentDao;
import net.zdsoft.eis.base.data.entity.StudentImport;
import net.zdsoft.eis.base.data.service.BaseUserService;
import net.zdsoft.eis.base.data.service.PassportAccountService;
import net.zdsoft.eis.base.deploy.SystemDeployService;
import net.zdsoft.eis.base.sync.EventSourceType;
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
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 山西人人通定制-学生数据导入
 * 
 * @author weixh
 * @since 2016-3-1 下午2:21:41
 */
public class StudentImportSxrrtServiceImpl extends AbstractDataImportService {
	private Logger log = LoggerFactory.getLogger(getClass());
	
	private SystemIniService systemIniService;
	private UnitService unitService;
	private SchoolService schoolService;
	private BasicClassService basicClassService;
	private BaseUserService baseUserService;
	private SystemDeployService systemDeployService;
	private PassportAccountService passportAccountService;
	private SystemCommonDao systemCommonDao;
	private EisBaseStudentDao eisBaseStudentDao;
	
	private String usernameAlert = null;
	private String pwdAlert = null;
	
	// 用户表的中的字段
	static Set<String> userFieldSet = new HashSet<String>();
	static {
		// 字段信息见配置文件
		userFieldSet.add("username");
		userFieldSet.add("password");
	}
	
	/** 
	 * 导入数据处理
	 */
	@Override
	public void importDatas(DataImportParam param, Reply reply)
			throws ImportErrorException {
		int errPos = 0;
		// 用于记录哪些用户是现在新增的
		int k = 0;
		try {
			// 取得导入文件对象
			ImportData importData = param.getImportData();
			Map<String, String> mapOfParam = param.getCustomParamMap();

			// 参数信息
			String unitId = param.getUnitId();// 单位
			String objectName = param.getObjectName();// 对象名称
			String isConvered = param.getCovered(); // 是否覆盖
			String acadyear = mapOfParam.get("acadyear"); // 学年
			Unit nowUnit = unitService.getUnit(unitId);
			
			Set<String> allAddUsernameSet = new HashSet<String>();
			List<User> addUserList = new ArrayList<User>();
			
			// 取出导入数据list
			List<Object> listOfImportData = importData
					.getListOfImportDataObject();

			// 导入列Set
			List<String> colNames = importData.getListOfImportDataName();// 导入列
			Set<String> colNameSet = new HashSet<String>();
			for (String col : colNames) {
				colNameSet.add(col);
			}
			// 学校信息
			// key = schName
			Map<String, School> schMap = new HashMap<String, School>();
			List<String> schIdList = new ArrayList<String>();
			// 班级信息
			// 教育局学生信息导入专用：key=schName
			Map<String, Set<String>> schClassNameMap = new HashMap<String, Set<String>>();
			Map<String, BasicClass> classMap = new HashMap<String, BasicClass>();
			Set<String> schNameSet = new HashSet<String>();
			Set<String> classNameSet;
			// 如果是教育局端学生信息导入
			if (objectName.equals(BasedataConstants.STUDENT_IMPORT_EDU)) {
				for (int i = 0; i < listOfImportData.size(); i++) {
					StudentImport stu = (StudentImport) listOfImportData.get(i);
					stu.setSchid(stu.getSchid().replaceAll(" ", "")
							.replaceAll(" ", ""));
					schNameSet.add(stu.getSchid());
					classNameSet = schClassNameMap.get(stu.getSchid());
					if (null == classNameSet) {
						classNameSet = new HashSet<String>();
					}
					if (StringUtils.isNotBlank(stu.getClassid())) {
						classNameSet.add(stu.getClassid());
						schClassNameMap.put(stu.getSchid(), classNameSet);
					}
				}
				schMap.putAll(getSchoolMap(schNameSet));
				for (School sch : schMap.values()) {
					Set<String> set = schClassNameMap.get(sch.getName());
					if (CollectionUtils.isEmpty(set)) {
						continue;
					}
					classMap.putAll(getClassMap(sch.getId(), acadyear, set));
				}
				// 此时的unitId就是eduId
				schIdList.addAll(unitService.getSchIdsByUnionCode(nowUnit.getUnionid()));
				// 学校端学生数据导入
			} else {
				classNameSet = new HashSet<String>();
				for (int i = 0; i < listOfImportData.size(); i++) {
					StudentImport stu = (StudentImport) listOfImportData.get(i);
					classNameSet.add(stu.getClassid());
				}
				classMap = getClassMap(unitId, acadyear, classNameSet);
			}
			
			Map<String, User> usernameMap = new HashMap<String, User>(); // 用户名
			getUniqueMap(listOfImportData, objectName, unitId, usernameMap);
			
			// username --> TeacherImport
			Map<String, StudentImport> insertStuMap = new HashMap<String, StudentImport>();
			
			
			// 入学日期
			String toschooldate = acadyear.substring(0, 4) + "-09-01";
			String pwdRule = systemIniService.getValue(User.SYSTEM_PASSWORD_EXPRESSION);
			String usernameRule = systemIniService.getValue(User.SYSTEM_NAME_EXPRESSION);
			StudentImport existsStu = null;
			for(int i = 0; i < listOfImportData.size(); i++) {
				try{
					errPos = i;
					StudentImport stu = (StudentImport) listOfImportData.get(i);
					// 姓名：去掉中英文空格
					if (StringUtils.isNotBlank(stu.getStuname())) {
						stu.setStuname(stu.getStuname().replaceAll(" ", "")
								.replaceAll(" ", ""));
					}
					// 入学年月
					if (StringUtils.isBlank(stu.getToschooldate())) {
						stu.setToschooldate(toschooldate);
					}
					stu.setAcadyear(acadyear);
					stu.setLeavesign(0);// 学籍状态：正常
					stu.setFlowtype("40"); // 同now_state
					
					// ============设置学校信息===========//
					School school = null;
					if (objectName.equals(BasedataConstants.STUDENT_IMPORT_EDU)) {
						school = schMap.get(stu.getSchid());
						if (null == school) {
							throw new ErrorFieldException("在系统中不存在。", "schid");
						} else if (!schIdList.contains(school.getId())) {
							throw new ErrorFieldException("不在本教育局的管辖范围下。",
									"schid");
						}
						stu.setSchid(school.getId());
						// 学校端导入
					} else {
						stu.setSchid(unitId);
					}
					// =============设置班级数据==============//
					BasicClass baseCls = null;
					baseCls = classMap.get(stu.getClassid());
					if (baseCls == null) {
						baseCls = classMap.get(stu.getSchid() + "_"
								+ stu.getClassid());
					}
					String errorCol = "classid";
					String errorName = "所填班级";
					if (null == baseCls) {
						if (BasedataConstants.STUDENT_IMPORT_EDU
								.equals(objectName)) {
							throw new ErrorFieldException(errorName
									+ "在所属学校中不存在。", errorCol);
						} else {
							throw new ErrorFieldException(errorName
									+ "在本校中不存在。", errorCol);
						}
					}
					if ("1".equals(baseCls.getGraduatesign() + "")) {
						throw new ErrorFieldException("该班级已毕业，不能再导入学生。",
								errorCol);
					}
					stu.setClassid(baseCls.getId());
					// 设置下面的一些字段
					if (StringUtils.isBlank(stu.getEnrollyear())) {
						stu.setEnrollyear(baseCls.getAcadyear());
					}
					
					// 用户名
					if ((!StringUtils.isBlank(stu.getUsername())) && (!stu.getUsername().matches(usernameRule))) {
						throw new ErrorFieldException(getUsernameAlert(), "username");
					}
					User user1 = usernameMap.get(stu.getUsername());
					if (null != user1) {
						if (!user1.getRealname().equals(stu.getStuname())) {
							if (user1.getUnitid().equals(stu.getSchid())) {
								throw new ErrorFieldException("已经被本单位 "
										+ user1.getRealname() + " 使用。",
										"username");
							} else {
								throw new ErrorFieldException("已经被 "
										+ unitService.getUnit(
												user1.getUnitid()).getName()
										+ " 单位，姓名：" + user1.getRealname()
										+ " 使用。", "username");
							}
						} else {
							throw new ErrorFieldException("对应的账号已存在，且导入不允许修改学生信息。", "stuname");
						}
					}
//					else {
//						if (null != existsStu) {
//							throw new ErrorFieldException("账号不能修改。", "username");
//						}
//					}
					
					// 密码加密
					if ((!StringUtils.isBlank(stu.getPassword()))
							&& (!stu.getPassword().matches("[\\w\\W]{6,18}"))) {
						throw new ErrorFieldException("密码为6-18个字符，由英文字母（区分大小写）、数字或符号组成。",
								"password");
					}
					PWD pwd = new PWD();
					String password = stu.getPassword();
					if (StringUtils.isBlank(password)) {
						pwd.setPassword(BaseConstant.PASSWORD_INIT);
					} else {
						pwd.setPassword(password);
					}
					password = pwd.encode();
					stu.setPassword(password);
					
					stu.setStuid(UUIDGenerator.getUUID());
					
					// ===========学生信息校验完成==================
					//TODO
					insertStuMap.put(stu.getUsername(), stu);
				
				} catch (ErrorFieldException e) {
					this.disposeError(importData, i, e.getField(),
							e.getMessage());
					continue;
				}
			}
			if (insertStuMap.size() == 0) {
				reply.setValue(DataImportConstants.STATUS_END);
				return;
			}
			
			processTable(isConvered, importData, objectName, reply, insertStuMap);
			
			//TODO
			if (systemDeployService.isConnectPassport()) {

				// 取得需要新增的用户 
				allAddUsernameSet = insertStuMap.keySet();

				log.debug("==============数字校园进行帐户同步================");
				try {

					log.debug("passport增加账户，应该增加：" + allAddUsernameSet.size());

					if (CollectionUtils.isNotEmpty(allAddUsernameSet)) {
						addUserList = baseUserService
								.getUsersByUserNames(allAddUsernameSet
										.toArray(new String[0]));
					}

					for (User user : addUserList) {
						user.setOwnerType(User.STUDENT_LOGIN);
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
//						reply.addActionMessage("本次新增用户失败，请修改导入数据后再操作，错误信息："
//								+ pe.getMessage());
					}
					log.error("本次新增用户失败，请修改导入数据后再操作。", pe);
					throw new OperationNotAllowedException(pe.getMessage());
				}
				
				
			}
		} catch (Exception e) {
			log.error("第 " + errPos + " 个学生错误", e);
			throw new ImportErrorException("导入出错");
		}
	}
	
	/**
	 * 操作表的相关处理
	 * 
	 * @param isConvered
	 * @param importData
	 * @param objectName
	 * @param reply
	 * @param insertStuMap
	 * @throws ParseException
	 * @return
	 */
	protected void processTable(String isConvered,
			ImportData importData, String objectName, Reply reply,
			Map<String, StudentImport> insertStuMap)
			throws ParseException {
		int cnt = 0;
		if(insertStuMap.size() > 0){
			List<String> userSqlList = new ArrayList<String>();
			List<StudentImport> insertList = new ArrayList<StudentImport>();
			Iterator<Entry<String, StudentImport>> it = insertStuMap.entrySet().iterator();
			while(it.hasNext()){
				StudentImport stu = it.next().getValue();
				insertList.add(stu);
				buildInsertSql(stu, importData, objectName, userSqlList);
			}
			eisBaseStudentDao.batchInsertStudent(insertList);
			cnt = systemCommonDao.batchUpdate(userSqlList
					.toArray(new String[0]));
			reply.addActionMessage("新增学生基本信息：" + insertList.size() + " 条。");
			reply.addActionMessage("新增用户信息：" + cnt + " 条。");
		} else {
			reply.addActionMessage("新增学生基本信息：0 条。");
			reply.addActionMessage("新增用户信息：0 条。");
		}
	}
	
	/**
	 * 批量插入
	 * 
	 * @param stu
	 * @param importData
	 * @param userInsertList
	 */
	private void buildInsertSql(StudentImport stu, ImportData importData,
			String objectName, List<String> userInsertList) throws ParseException {
		Map<String, ImportObjectNode> nodeMap = importData.getMapOfNodesName();
		List<String> colNames = importData.getListOfImportDataName();// 导入列

		StringBuffer sql = new StringBuffer();
		StringBuffer sqlValue = new StringBuffer();
		
		// 新增用户信息
		sql.delete(0, sql.length());
		sqlValue.delete(0, sqlValue.length());
		sql.append(" insert into base_user(id, owner_type, real_name, owner_id, unit_id,creation_time,user_type, user_state, is_deleted,event_source, modify_time,display_order,region_code,sex");
		for (String col : colNames) {
			if (StringUtils.isEmpty(col))
				continue;
			Object obj = ObjectUtils.getProperty(stu, col);// 从teacher中取值
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
			sqlValue.append(",'" + stu.getPassword() + "'");
		}

		// 和新增教职工的业务保持一致 2009-10-23 mark = "2" 现在是不用了
		String mark = "1";
		sql.append(") values ('")
				.append(UUIDGenerator.getUUID())
				.append("',")
				.append(User.STUDENT_LOGIN)
				.append(",'")
				.append(stu.getStuname())
				.append("','")
				.append(stu.getStuid())
				.append("','")
				.append(stu.getSchid())
				.append("',sysdate, 2, ")
				.append(mark)
				.append(",0,")
				.append(EventSourceType.LOCAL.getValue())
				.append(",")
				.append(getFormattedTimeForSql())
				.append(",(select max(display_order)+1 from base_user where is_deleted = 0 and unit_id = '")
				.append(stu.getSchid() + "')")
				.append(",'")
				.append(stu.getRegioncode())
				.append("'")
				.append(",")
				.append(stu.getSex())
				.append(sqlValue + ")");
		userInsertList.add(sql.toString());
	}

	/**
	 * 查找所属学校信息
	 * 
	 * @param schoolNameSet
	 * @return
	 */
	private Map<String, School> getSchoolMap(Set<String> schoolNameSet) {
		Map<String, School> map = schoolService
				.getSchoolMapByNames(schoolNameSet.toArray(new String[0]));
		Map<String, School> mapsch = new HashMap<String, School>();
		for (School school : map.values()) {
			mapsch.put(school.getId(), school);
		}
		return mapsch;
	}
	
	/**
	 * 查找所属班级信息(包括已经毕业的)
	 * 
	 * @param unitId
	 * @param acadyear
	 * @param classNameSet
	 * @return
	 */
	private Map<String, BasicClass> getClassMap(String unitId, String acadyear,
			Set<String> classNameSet) {
		Map<String, BasicClass> classMap = new HashMap<String, BasicClass>();
		List<BasicClass> classList = basicClassService.getClasses(unitId,
				acadyear);
		if (null != classList && classList.size() > 0) {
			for (int i = 0; i < classList.size(); i++) {
				BasicClass cls = classList.get(i);
				if (!classNameSet.contains(cls.getClassnamedynamic())) {
					continue;
				}
				classMap.put(unitId + "_" + cls.getClassnamedynamic(), cls);
				classMap.put(cls.getId(), cls);
			}
		}
		return classMap;
	}
	
	/**
	 * 针对导入数据中惟一性的字段进行整理
	 * 
	 * @param listOfImportData
	 * @param objectName
	 * @param unitId
	 * @param usernameMap 用户名
	 */
	private void getUniqueMap(List<Object> listOfImportData, String objectName,
			String unitId, Map<String, User> usernameMap) {
		List<String> usernameList = new ArrayList<String>();
		for (int i = 0; i < listOfImportData.size(); i++) {
			StudentImport stu = (StudentImport) listOfImportData.get(i);
			
			// 用户名
			if (StringUtils.isNotBlank(stu.getUsername())) {
				usernameList.add(stu.getUsername());
			}
		}
		
		// 用户名
		if (usernameList.size() > 0) {
			usernameMap.putAll(getUsernameMap(usernameList));
		}
	}
	
	/**
	 * 登录名查询
	 * 
	 * @param usernameList
	 * @return
	 */
	private Map<String, User> getUsernameMap(List<String> usernameList) {
		return baseUserService.getUsersMapByName(usernameList
				.toArray(new String[0]));
	}
	
	/**
	 * 导出 
	 */
	@Override
	public List<List<String[]>> exportDatas(ImportObject importObject,
			String[] cols) {
		List<List<String[]>> dataList = new ArrayList<List<String[]>>();    
        return dataList;
	}

	/**
	 * 获取用户名错误提示
	 * @return
	 */
	public String getUsernameAlert(){
		if(usernameAlert == null){
			usernameAlert = systemIniService.getValue(User.SYSTEM_NAME_ALERT);
			if(usernameAlert == null){
				usernameAlert = "";
			}
		}
		return usernameAlert;
	}
	
	/**
	 * 获取密码错误提示
	 * @return
	 */
	public String getPwdAlert(){
		if(pwdAlert == null){
			pwdAlert = systemIniService.getValue(User.SYSTEM_PASSWORD_ALERT);
			if(pwdAlert == null){
				pwdAlert = "";
			}
		}
		return pwdAlert;
	}

	public void setSystemIniService(SystemIniService systemIniService) {
		this.systemIniService = systemIniService;
	}

	public void setUnitService(UnitService unitService) {
		this.unitService = unitService;
	}

	public void setSchoolService(SchoolService schoolService) {
		this.schoolService = schoolService;
	}

	public void setBasicClassService(BasicClassService basicClassService) {
		this.basicClassService = basicClassService;
	}

	public void setBaseUserService(BaseUserService baseUserService) {
		this.baseUserService = baseUserService;
	}

	public void setSystemDeployService(SystemDeployService systemDeployService) {
		this.systemDeployService = systemDeployService;
	}

	public void setPassportAccountService(
			PassportAccountService passportAccountService) {
		this.passportAccountService = passportAccountService;
	}

	public void setSystemCommonDao(SystemCommonDao systemCommonDao) {
		this.systemCommonDao = systemCommonDao;
	}

	public void setEisBaseStudentDao(EisBaseStudentDao eisBaseStudentDao) {
		this.eisBaseStudentDao = eisBaseStudentDao;
	}
	
}
