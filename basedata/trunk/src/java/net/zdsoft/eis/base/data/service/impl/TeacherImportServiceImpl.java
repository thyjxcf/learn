package net.zdsoft.eis.base.data.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import net.zdsoft.eis.base.common.entity.Dept;
import net.zdsoft.eis.base.common.entity.Mcodedetail;
import net.zdsoft.eis.base.common.entity.Student;
import net.zdsoft.eis.base.common.entity.Teacher;
import net.zdsoft.eis.base.common.entity.Unit;
import net.zdsoft.eis.base.common.entity.User;
import net.zdsoft.eis.base.common.service.StudentService;
import net.zdsoft.eis.base.common.service.SystemIniService;
import net.zdsoft.eis.base.constant.BaseConstant;
import net.zdsoft.eis.base.data.BasedataConstants;
import net.zdsoft.eis.base.data.entity.BaseTeacher;
import net.zdsoft.eis.base.data.entity.BaseTeacherDuty;
import net.zdsoft.eis.base.data.entity.BaseUnit;
import net.zdsoft.eis.base.data.entity.ResearchGroup;
import net.zdsoft.eis.base.data.entity.ResearchGroupEx;
import net.zdsoft.eis.base.data.entity.TeacherImport;
import net.zdsoft.eis.base.data.service.BaseDeptService;
import net.zdsoft.eis.base.data.service.BaseMcodeDetailService;
import net.zdsoft.eis.base.data.service.BaseTeacherDutyService;
import net.zdsoft.eis.base.data.service.BaseTeacherService;
import net.zdsoft.eis.base.data.service.BaseUnitService;
import net.zdsoft.eis.base.data.service.BaseUserService;
import net.zdsoft.eis.base.data.service.PassportAccountService;
import net.zdsoft.eis.base.data.service.ResearchGroupService;
import net.zdsoft.eis.base.deploy.SystemDeployService;
import net.zdsoft.eis.base.sync.EventSourceType;
import net.zdsoft.eis.base.util.BusinessUtils;
import net.zdsoft.eis.system.constant.SystemConstant;
import net.zdsoft.keel.action.Reply;
import net.zdsoft.keel.util.DateUtils;
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
import org.apache.oro.text.regex.MalformedPatternException;
import org.apache.oro.text.regex.PatternCompiler;
import org.apache.oro.text.regex.PatternMatcher;
import org.apache.oro.text.regex.Perl5Compiler;
import org.apache.oro.text.regex.Perl5Matcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * 
 * @author hexq
 * @version $Revision: 1.0 $, $Date: Oct 19, 2009 12:21:37 PM $
 */
public class TeacherImportServiceImpl extends AbstractDataImportService {

	private Logger log = LoggerFactory
			.getLogger(TeacherImportServiceImpl.class);

	// 用户表的中的字段
	static Set<String> userFieldSet = new HashSet<String>();

	// 教职工表中的字段
	static Set<String> teacherFieldSet = new HashSet<String>();

	// 有关联关系的字段
	static Set<String> relatedFieldSet = new HashSet<String>();

	static {

		// 字段信息见配置文件 teacher_import.xml
		userFieldSet.add("username");
		userFieldSet.add("password");
		userFieldSet.add("deptid");

		// 29个字段
		teacherFieldSet.add("unitid");
		teacherFieldSet.add("teachercode");
		teacherFieldSet.add("teachername");
		teacherFieldSet.add("oldname");
		teacherFieldSet.add("sex");
		teacherFieldSet.add("birthday");
		teacherFieldSet.add("identitycard");
		teacherFieldSet.add("deptid");
		teacherFieldSet.add("incumbencysign");
		teacherFieldSet.add("workdate");

		teacherFieldSet.add("title"); // 职称
		// teacherFieldSet.add("duty"); //职务
		teacherFieldSet.add("mobilephone");
		teacherFieldSet.add("officetel");
		teacherFieldSet.add("nativeplace");
		teacherFieldSet.add("nation");
		teacherFieldSet.add("country");
		teacherFieldSet.add("polity");
		teacherFieldSet.add("polityjoin");
		teacherFieldSet.add("academicqualification");
		teacherFieldSet.add("major");

		teacherFieldSet.add("graduateschool");
		teacherFieldSet.add("graduatetime");
		teacherFieldSet.add("registertype");
		teacherFieldSet.add("registerplace");
		teacherFieldSet.add("linkaddress");
		teacherFieldSet.add("homepage");
		teacherFieldSet.add("email");
		teacherFieldSet.add("remark");
		teacherFieldSet.add("chargenumber"); // 扣费号码

		teacherFieldSet.add("teachStatus");
		teacherFieldSet.add("weaveType");
		teacherFieldSet.add("returnedChinese");
		teacherFieldSet.add("multiTitle");
		
		teacherFieldSet.add("workTeachDate");
		teacherFieldSet.add("oldAcademicQualification");
		teacherFieldSet.add("specTechnicalDuty");
		teacherFieldSet.add("specTechnicalDutyDate");
		teacherFieldSet.add("homeAddress");
		teacherFieldSet.add("generalCard");
		teacherFieldSet.add("linkPhone");
//		teacherFieldSet.add("titleCode");
//		teacherFieldSet.add("titleDate");
//		teacherFieldSet.add("titleDept");
//		teacherFieldSet.add("specJobLevel");
//		teacherFieldSet.add("manageJobLevel");
//		
//		teacherFieldSet.add("appDate");
//		teacherFieldSet.add("adminPost");
//		teacherFieldSet.add("appYear");
//		teacherFieldSet.add("adminPostLevel");
//		teacherFieldSet.add("teachSubject");
//
//		teacherFieldSet.add("degree");
//		teacherFieldSet.add("firstStulive");
//		teacherFieldSet.add("highestStulive");
//		teacherFieldSet.add("highestStuliveSch");
//		teacherFieldSet.add("highestStuliveMajor");
//
//		teacherFieldSet.add("highestStuliveDate");
//		teacherFieldSet.add("teacherLevel");
//		teacherFieldSet.add("certificateName");
//		teacherFieldSet.add("approvalDept");
//		teacherFieldSet.add("certificateLevel");
//
//		teacherFieldSet.add("firstContractStart");
//		teacherFieldSet.add("firstContractEnd");
//		teacherFieldSet.add("secondContractStart");
//		teacherFieldSet.add("secondContractEnd");
//		teacherFieldSet.add("thirdContractStart");
//
//		teacherFieldSet.add("thirdContractEnd");
//		teacherFieldSet.add("fourthContractStart");
//		teacherFieldSet.add("fourthContractEnd");
//		teacherFieldSet.add("QQ");

		
		// ----- 关联字段验证----------
		// 如果身份证号和性别或身份证号或出生日期或三个全部都存在，则验证身份信息
		relatedFieldSet.add("identitycard");
		relatedFieldSet.add("sex");
		relatedFieldSet.add("birthday");

		
	}

	private BaseUnitService baseUnitService; // 单位信息
	private PassportAccountService passportAccountService; // 账号信息
	private SystemCommonDao systemCommonDao; // 通用的处理类
	private SystemDeployService systemDeployService;
	private BaseMcodeDetailService baseMcodeDetailService;
	private BaseTeacherDutyService baseTeacherDutyService;
	private BaseDeptService baseDeptService;
	private BaseUserService baseUserService;
	private SystemIniService systemIniService;
	private BaseTeacherService baseTeacherService;
	private ResearchGroupService researchGroupService; 
	
	private StudentService studentService;

	public void setStudentService(StudentService studentService) {
		this.studentService = studentService;
	}

	/**
	 * 设置systemIniService
	 * @param systemIniService systemIniService
	 */
	public void setSystemIniService(SystemIniService systemIniService) {
	    this.systemIniService = systemIniService;
	}

	public void setBaseTeacherService(BaseTeacherService baseTeacherService) {
		this.baseTeacherService = baseTeacherService;
	}

	public void setBaseUserService(BaseUserService baseUserService) {
		this.baseUserService = baseUserService;
	}

	public void setBaseDeptService(BaseDeptService baseDeptService) {
		this.baseDeptService = baseDeptService;
	}

	public void setSystemDeployService(SystemDeployService systemDeployService) {
		this.systemDeployService = systemDeployService;
	}

	public void setBaseUnitService(BaseUnitService baseUnitService) {
		this.baseUnitService = baseUnitService;
	}

	public void setPassportAccountService(
			PassportAccountService passportAccountService) {
		this.passportAccountService = passportAccountService;
	}

	public void setSystemCommonDao(SystemCommonDao systemCommonDao) {
		this.systemCommonDao = systemCommonDao;
	}

	public void setBaseMcodeDetailService(
			BaseMcodeDetailService baseMcodeDetailService) {
		this.baseMcodeDetailService = baseMcodeDetailService;
	}

	public void setBaseTeacherDutyService(
			BaseTeacherDutyService baseTeacherDutyService) {
		this.baseTeacherDutyService = baseTeacherDutyService;
	}

	/**
	 * 导出
	 */
	public List<List<String[]>> exportDatas(ImportObject importObject,
			String[] cols) {
		List<List<String[]>> dataList = new ArrayList<List<String[]>>();
		return dataList;
	}

	/**
	 * 导入
	 */
	@SuppressWarnings("unchecked")
	public void importDatas(DataImportParam param, Reply reply)
			throws ImportErrorException {

		Map<String, Integer> limitTeacherInUnitMap = new HashMap<String, Integer>(); // 每个单位的教职工限额

		// 用于检查每个单位的教职工限额
		// unit_id -->Set<username>
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

			StringBuffer sb = new StringBuffer();
			// 插入更新等sql列表
			List<String> listOfSQL = new ArrayList<String>();

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
			Iterator it = colNames.iterator();
	        while(it.hasNext()){
	            if (it.next().equals("teachGroupName")) {
	            	it.remove();
	            }
	        }
			for (String col : colNames) {
				colNameSet.add(col);
			}

			log.debug("==============所属单位和所在部门首先特殊处理================");
			// 如果是教育局端教职工导入，则取得所有下级单位id
			// 进行了重写，性能会有很大提高
			// unit_name-->unit_id+regioncode
			Unit u = baseUnitService.getUnit(unitId);

			Map<String, Unit> allSubUnitMap = new HashMap<String, Unit>();
			if (SystemConstant.TEACHER_IMPORT_EDU.equals(objectName)) {
				allSubUnitMap.put(u.getName(), u); // 先把本单位加进去
				allSubUnitMap.putAll(getAllSubUnitMap(unitId));
			}

			// 所在部门信息
			// unitId_deptName ----> deptId
			Map<String, String> deptNameMap = new HashMap<String, String>();
			Map<String, Long> deptOrderIdMap = new HashMap<String, Long>();
			Map<String, String> deptIdMap = new HashMap<String, String>();

			// 职务信息--由于使用了系统微代码，这里暂时注释掉
			// Map<String, String> titleNameMap = new HashMap<String, String>();

			// 惟一性数据
			Map<String, BaseTeacher> tchCodeMap = new HashMap<String, BaseTeacher>(); // 职工编号
			Map<String, User> usernameMap = new HashMap<String, User>(); // 用户名
			Map<String, User> stuIdentityCardMap = new HashMap<String, User>(); // 学生身份证号码
			Map<String, User> tchIdentityCardMap = new HashMap<String, User>(); // 职工身份证号码

			getUniqueMap(listOfImportData, objectName, unitId, allSubUnitMap,
					tchCodeMap, usernameMap, stuIdentityCardMap,
					tchIdentityCardMap);

			log.debug("==============验证数据有效性并导入数据================");

			// 更新和新增
			List<TeacherImport> updateTeacherList = new ArrayList<TeacherImport>();
			// username --> TeacherImport
			Map<String, TeacherImport> insertTeacherMap = new HashMap<String, TeacherImport>();
			Set<String> teacherIdSet = new HashSet<String>();
			List<Mcodedetail> mcodeList = baseMcodeDetailService
					.getMcodeDetails("DM-XXZW");
			Map<String, String> dutyMap = new HashMap<String, String>();
			for (Mcodedetail md : mcodeList) {
				dutyMap.put(md.getContent(), md.getThisId());
			}
			List<BaseTeacherDuty> dutyList = new ArrayList<BaseTeacherDuty>();
			String todeptId = "no";// 用来代替第二次以后的部门编号,no表示第一次无需代替
			// 单个处理
			for (int i = 0; i < listOfImportData.size(); i++) {
				errPos = i;
				TeacherImport teacher = (TeacherImport) listOfImportData.get(i);

				// 所属单位: 去掉中英文空格
				if (colNameSet.contains("unitid")) {
					teacher.setUnitid(teacher.getUnitid().replaceAll(" ", "")
							.replaceAll(" ", ""));
				}

				// 姓名：去掉中英文空格
				teacher.setTeachername(teacher.getTeachername()
						.replaceAll(" ", "").replaceAll(" ", ""));

				// 所属部门：去掉中英文空格
				teacher.setDeptid(teacher.getDeptid().replaceAll(" ", "")
						.replaceAll(" ", ""));

				TeacherImport existsTch = null;
				try {
					// 第一部分

					// --------单位，部门和职务处理---------------------------------
					// 如果是教育局端教职工导入
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

					// 设置部门Id到数组：如果部门不存在，则生成一个
					sb.delete(0, sb.length());
					// 特殊字符验证
					if (StringUtils.isNotBlank(teacher.getDeptid())) {
						if (isContainsEspecial(teacher.getDeptid())) {
							throw new ErrorFieldException("部门名称存在特殊字符。",
									"deptid");
						}
					}
					String deptId = processDept(teacher.getDeptid(),
							teacher.getUnitid(), sb, deptNameMap,
							deptOrderIdMap, deptIdMap, todeptId);
					todeptId = deptId.split("!")[1];
					if (!todeptId.equals("no")) {
						todeptId = String.format(
								"%0" + todeptId.length() + "d",
								Integer.valueOf(todeptId) + 1);
					}
					teacher.setDeptid(deptId.split("!")[0]);
					listOfSQL.add(sb.toString());

					// 第二部分
					// 姓名验证
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
					if (StringUtils.isNotBlank(teacher.getOldname())) {
						Pattern pattern = Pattern
								.compile("[a-zA-Z\u4e00-\u9fa5]{1}|[a-zA-Z\u4e00-\u9fa5]{1}[\u4e00-\u9fa5\\w·\\. ]*[\u4e00-\u9fa5\\w]{1}");// 验证
						if (!pattern.matcher(teacher.getOldname()).matches()) {
							throw new ErrorFieldException(
									"只能由汉字、字母、数字、点号、空格和下划线，且须以汉字或字母开头、非空格结尾",
									"oldname");
						}
					}
					if (StringUtils.isBlank(teacher.getIncumbencysign())) {
						throw new ErrorFieldException("在职标记不能为空","incumbencysign");
					}
					// ----------------主页地址的验证---------------
					if (StringUtils.isNotBlank(teacher.getHomepage())) {
						Pattern pattern = Pattern
								.compile("^http[s]?:\\/\\/([\\w-]+\\.)+[\\w-]+([\\w-./:?%&=]*)?$");// 验证url
						if (!pattern.matcher(teacher.getHomepage()).matches()) {
							throw new ErrorFieldException("格式无效。必须以http://或https://开头，括号内为允许输入的特殊符号（  - . / : ? % & = ）", "homepage");
						}
					}

					// ----------关联字段判断-----------------------------------
					validateRelatedField(colNameSet, teacher, importData, i);

					// ------------------判断职工编号，用户名，身份证号------------------------------------
					// 更新的依据是所属单位、职工姓名和职工编号的组合
					String unitid_tchcode = teacher.getUnitid() + "_"
							+ teacher.getTeachercode();
					BaseTeacher tch = tchCodeMap.get(unitid_tchcode);
					// 更新
					if (null != tch) {
						if (!tch.getName().equals(teacher.getTeachername())) {
							if (SystemConstant.TEACHER_IMPORT_EDU
									.equals(objectName)) {
								throw new ErrorFieldException(
										"与所属单位和编号对应的教职工其系统中的姓名和导入文件不一致。系统中："
												+ tch.getName() + "。",
										"teachername");
							} else {
								throw new ErrorFieldException(
										"与编号对应的教职工其系统中的姓名和导入文件不一致。系统中："
												+ tch.getName() + "。",
										"teachername");
							}
						} else {
							teacher.setId(tch.getId());
							existsTch = new TeacherImport();
						}
						// 新增
					} else {
						teacher.setId(UUIDGenerator.getUUID());
					}

					// 用户名
					if ((!StringUtils.isBlank(teacher.getUsername())) && (!teacher.getUsername().matches(systemIniService.getValue(User.SYSTEM_NAME_EXPRESSION)))) {
						throw new ErrorFieldException(systemIniService.getValue(User.SYSTEM_NAME_ALERT), "username");
					}

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
					} else {
						if (null != existsTch) {
							throw new ErrorFieldException("账号不能修改", "username");
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
					
					if ((!StringUtils.isBlank(teacher.getPassword())) && (!teacher.getPassword().matches(systemIniService.getValue(User.SYSTEM_PASSWORD_EXPRESSION)))) {
//						throw new ErrorFieldException(systemIniService.getValue(User.SYSTEM_PASSWORD_ALERT), "password");
						throw new ErrorFieldException("密码为6-18个字符，由英文字母（区分大小写）、数字或符号组成。", "password");
					}
					
					password = pwd.encode();
					teacher.setPassword(password);

					// 身份证号
					if (StringUtils.isNotBlank(teacher.getIdentitycard())) {
						User stuUser = stuIdentityCardMap.get(teacher
								.getIdentitycard());
						if (null != stuUser) {
							throw new ErrorFieldException("已经 "
									+ baseUnitService.getUnit(
											stuUser.getUnitid()).getName()
									+ " 学校，姓名：" + stuUser.getRealname()
									+ " 使用。", "identitycard");
						}

						User tchUser = tchIdentityCardMap.get(teacher
								.getIdentitycard());
						if (null != tchUser) {
							if (!tchUser.getTeacherid().equals(teacher.getId())) {
								if (tchUser.getUnitid().equals(
										teacher.getUnitid())) {
									throw new ErrorFieldException("已经被本单位 "
											+ tchUser.getRealname() + " 使用。",
											"identitycard");
								} else {
									throw new ErrorFieldException("已经被 "
											+ baseUnitService.getUnit(
													tchUser.getUnitid())
													.getName() + " 单位，姓名："
											+ tchUser.getRealname() + " 使用。",
											"identitycard");
								}
							}
						}
					}
					// 职务处理
					if (importData.getListOfImportDataName().contains("duty")) {
						if (StringUtils.isNotBlank(teacher.getDuty())) {
							String dutystr = teacher.getDuty().replaceAll("，", ",");
							String[] dutyNames = dutystr.split(",");
							for (int count = 0; count < dutyNames.length; count++) {
								if (dutyMap.containsKey(dutyNames[count])) {
									BaseTeacherDuty duty = new BaseTeacherDuty();
									duty.setTeacherId(teacher.getId());
									duty.setDutyCode(dutyMap
											.get(dutyNames[count]));
									if (null != existsTch) {
										if ("1".equals(isConvered)) {
											dutyList.add(duty);
											teacherIdSet.add(teacher.getId());
										}
									} else {
										dutyList.add(duty);
										teacherIdSet.add(teacher.getId());
									}
								} else {
									throw new ErrorFieldException(
											"在系统中不存在该职务： " + dutyNames[count],
											"duty");
								}
							}
						}
					}
					
					if (StringUtils.isNotBlank(teacher.getBirthday())) {
						Date birthday = DateUtils.string2Date(teacher.getBirthday());
						if (birthday.after(new Date())) {
							throw new ErrorFieldException("出生日期不能晚于当前日期","birthday");
						} 
						if (colNameSet.contains("polityjoin")){
							if (StringUtils.isNotBlank(teacher.getPolityjoin())) {
								if (birthday.after(DateUtils.string2Date(teacher.getPolityjoin()))) {
									throw new ErrorFieldException("加入时间不能早于出生年月","polityjoin");
								}
							}
						}
						if (StringUtils.isNotBlank(teacher.getGraduatetime())) {
							if (birthday.after(DateUtils.string2Date(teacher.getGraduatetime()))) {
								throw new ErrorFieldException("毕业时间不能早于出生年月","graduatetime");
							}
						}
						if (StringUtils.isNotBlank(teacher.getWorkdate())) {
							if (birthday.after(DateUtils.string2Date(teacher.getWorkdate()))) {
								throw new ErrorFieldException("参加工作时间不能早于出生年月","workdate");
							}
						}
					}
					
					/*String startDate = null;
					String endDate = null; 
					SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
					if (colNameSet.contains("contractStartDate")){
						if (StringUtils.isNotBlank(teacher.getContractStartDate())){
							startDate = teacher.getContractStartDate();
							if (colNameSet.contains("contractEndDate")){
								if (StringUtils.isNotBlank(teacher.getContractEndDate())){
									endDate = teacher.getContractEndDate();
									if (startDate.compareTo(endDate) > 0){
										throw new ErrorFieldException(
												"合同开始时间 与 合同终止时间 之间 前后时间不合逻辑，请更正！",
												"contractEndDate");
									}
								}else{
									if (tch != null && tch.getContractEndDate() != null){
										endDate = format.format(tch.getContractEndDate());
										if (startDate.compareTo(endDate) > 0){
											throw new ErrorFieldException(
													"合同开始时间 与 合同终止时间 之间 前后时间不合逻辑，请更正！",
													"contractStartDate");
										}
									}
								}
							}
						}
					}else if (colNameSet.contains("contractEndDate")){
						if (StringUtils.isNotBlank(teacher.getContractEndDate())){
							endDate = teacher.getContractEndDate();
							if (tch != null && tch.getContractStartDate() != null){
								startDate = format.format(tch.getContractStartDate());
								if (startDate.compareTo(endDate) > 0){
									throw new ErrorFieldException(
											"合同开始时间 与 合同终止时间 之间 前后时间不合逻辑，请更正！",
											"contractEndDate");
								}
							}
						}
						
					}
					
					List<String> contractList = new ArrayList<String>();
					contractList.add("first");
					contractList.add("second");
					contractList.add("third");
					contractList.add("fourth");
					
					for (String key : contractList) {
						String keyStart = key + "ContractStart";
						String keyEnd = key + "ContractEnd";
						if (colNameSet.contains(keyStart)){
							startDate = (String)getKeyValue(keyStart, teacher);
							if (StringUtils.isNotBlank(startDate)){
								if (colNameSet.contains(keyEnd)){
									endDate = (String) getKeyValue(keyEnd, teacher);
									if (StringUtils.isNotBlank(endDate)){
										if (startDate.compareTo(endDate) > 0){
											throw new ErrorFieldException(
													"合同开始时间 与 合同终止时间 之间 前后时间不合逻辑，请更正！",
													"contractEndDate");
										}
									}else{
										if (tch != null)
											if (getKeyValue(keyEnd, tch) != null){
												endDate = format.format((Date)getKeyValue(keyEnd, tch));
												if (startDate.compareTo(endDate) > 0){
													throw new ErrorFieldException(
														"合同开始时间 与 合同终止时间 之间 前后时间不合逻辑，请更正！",
														keyStart);
											}
										}
									}
								}
							}
						}else if (colNameSet.contains(keyEnd)){
							endDate = (String)getKeyValue(keyEnd, teacher);
							if (StringUtils.isNotBlank(endDate)){
								if (tch != null && getKeyValue(keyStart, tch) != null){
									startDate = format.format((Date)getKeyValue(keyStart, tch));
									if (startDate.compareTo(endDate) > 0){
										throw new ErrorFieldException(
												"合同开始时间 与 合同终止时间 之间 前后时间不合逻辑，请更正！",
												"contractEndDate");
									}
								}
							}
							
						}
					}*/
					if (StringUtils.isNotBlank(teacher.getTeachGroupName())) {
						String teachGroupName =  teacher.getTeachGroupName();
						List<ResearchGroup> researchGroupList = researchGroupService.getUseResearchGroups(teacher.getUnitid()); 
						List<ResearchGroupEx> researchGroupExList = new ArrayList<ResearchGroupEx>();
						List<ResearchGroupEx> researchGroupExUseList = new ArrayList<ResearchGroupEx>();
						boolean haveGroup = false;
						String groupId = "";
						for (ResearchGroup group : researchGroupList) {
							if (teachGroupName.equals(group.getTeachGroupName())) {
								researchGroupExList = researchGroupService.getResearchGroupExs(group.getId());
								groupId = group.getId();
								continue;
							}
						}
						if (researchGroupExList.isEmpty()) {
							throw new ErrorFieldException("请填写正确的教研组名称","teachGroupName");
						}
						researchGroupExList = new ArrayList<ResearchGroupEx>();
						for (ResearchGroup group : researchGroupList) {
							researchGroupExList = researchGroupService.getResearchGroupExs(group.getId());
							for (ResearchGroupEx ex : researchGroupExList) {
								researchGroupExUseList.add(ex);
							}
						}
						for (ResearchGroupEx ex : researchGroupExUseList) {
							if (ex.getTeacherId().equals(teacher.getId())) {
								researchGroupService.deleteByid(ex.getId());
							}
						}
						for (ResearchGroupEx ex : researchGroupExList) {
							if (ex.getTeacherId().equals(teacher.getId())) {
								haveGroup = true;
								continue;
							}
						}
						if (!haveGroup) {
							researchGroupService.saveOne(groupId, teacher.getId(), 0);
						}
						
					}
					
					// -------------------------------组装数据-------------------------------------
					// 更新
					if (null != existsTch) {
						updateTeacherList.add(teacher);
						allModifyUsernameSet.add(teacher.getUsername());
						// 新增
					} else {
						insertTeacherMap.put(teacher.getUsername(), teacher);
						Set<String> set = addUsernameMap.get(teacher
								.getUnitid());
						if (set == null) {
							set = new HashSet<String>();
						}
						set.add(teacher.getUsername());
						addUsernameMap.put(teacher.getUnitid(), set);
					}

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

			if (insertTeacherMap.size() == 0 && updateTeacherList.size() == 0) {
				reply.setValue(DataImportConstants.STATUS_END);
				return;
			}

			// log.debug("==============开始导入数据:【部门表】，职工表，用户表================");
			// 如果导入的教职工的指定的部门不存在，则生成一个新的部门
			List<String> execSQLList = new ArrayList<String>();
			for (String s : listOfSQL) {
				if (StringUtils.isBlank(s))
					continue;
				execSQLList.add(s);
				if (execSQLList.size() > 0 && execSQLList.size() % 500 == 0) { // 默认每五百条提交一次
					systemCommonDao.batchUpdate(execSQLList
							.toArray(new String[0]));
					execSQLList.clear();
				}

			}
			if (execSQLList.size() > 0) {
				systemCommonDao.batchUpdate(execSQLList.toArray(new String[0]));
			}

			// 处理教师表和用户表
			List<String> messageList =processTable(importData, reply, isConvered, objectName,
					insertTeacherMap, updateTeacherList);

			// 处理教师职务表 先删除后增加
			baseTeacherDutyService.deleteTeacherDutiesByTeacherIds(
					teacherIdSet.toArray(new String[0]), EventSourceType.LOCAL);
			baseTeacherDutyService.addTeacherDuties(dutyList);
			log.debug("==============导入数据成功================");

			// 第三部分
 
			// 给没有设置角色的用户设置默认角色
			systemCommonDao
					.commonExec("insert into sys_user_role (id,userid, roleid, flag) select sys_guid() ,base_user.id, "
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
					for (User unit : retUsers) {
						sqls[i] = "update base_user set owner_type = "
								+ unit.getOwnerType() + ", account_id = '"
								+ unit.getAccountId() + "',sequence = "
								+ unit.getSequence() + ",event_source="
								+ EventSourceType.LOCAL.getValue()
								+ " where id = '" + unit.getId() + "'";
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
						log.error("本此修改用户失败，请修改导入数据后再操作。", pe);
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

	/**
	 * 验证关联字段
	 * 
	 * @param colNameSet
	 * @param teacher
	 * @throws ErrorFieldException
	 */
	private void validateRelatedField(Set<String> colNameSet,
			TeacherImport teacher, ImportData importData, int i)
			throws ErrorFieldException {
		String _field = "username";
		List<String> cols = new ArrayList<String>();
		List<String> errs = new ArrayList<String>();

		// 验证身份证号是否是有效的
		if (StringUtils.isNotBlank(teacher.getIdentitycard())) {
			String ret = BusinessUtils.validateIdentityCard(teacher
					.getIdentitycard());
			if (StringUtils.isNotBlank(ret)) {
				_field = "identitycard";
				cols.add(_field);
				errs.add("不是有效的身份证号。");
			}
		
			if (cols.size() > 0) {
				disposeError(importData, i, cols.toArray(new String[0]),
						errs.toArray(new String[0]));
				throw new ErrorFieldException();
			}

			// 如果性别不为空，则验证身份证中的性别信息和导入文件中指定的性别是否一致
			if (StringUtils.isNotBlank(teacher.getSex())) {
				int sexInt = 0;
				if (teacher.getIdentitycard().length() == 18) {
					sexInt = Integer.valueOf(teacher.getIdentitycard()
							.substring(16, 17));
				} else {
					sexInt = Integer.valueOf(teacher.getIdentitycard()
							.substring(14));
				}
				int temp = 1;// 男
				// 18位身份证第17位如果是奇数，表示男，是偶数表示女
				// 15位身份证第15位如果是奇数，表示男，是偶数表示女
				if (sexInt % 2 == 0) {
					temp = 2;// 女
				}

//				if (temp != Integer.valueOf(teacher.getSex())) {
//					_field = "sex";
//					cols.add(_field);
//					errs.add("身份证中的性别信息和教职工的性别不一致。");
//				}
//
//				if (cols.size() > 0) {
//					disposeError(importData, i, cols.toArray(new String[0]),
//							errs.toArray(new String[0]));
//					throw new ErrorFieldException();
//				}
			}else{
				int sexInt = 0;
				if (teacher.getIdentitycard().length() == 18) {
					sexInt = Integer.valueOf(teacher.getIdentitycard()
							.substring(16, 17));
				} else {
					sexInt = Integer.valueOf(teacher.getIdentitycard()
							.substring(14));
				}
				int temp = 1;// 男
				// 18位身份证第17位如果是奇数，表示男，是偶数表示女
				// 15位身份证第15位如果是奇数，表示男，是偶数表示女
				if (sexInt % 2 == 0) {
					temp = 2;// 女
				}
				teacher.setSex(String.valueOf(temp));
			}
			
			String birth = BusinessUtils.getDateStrFromIdentityNo(teacher
					.getIdentitycard());
			// 核对身份证中出生日期和教职工指定的出生日期是否一致
			if (StringUtils.isNotBlank(teacher.getBirthday())) {
				String stuBirth = teacher.getBirthday();
				if (teacher.getBirthday() != null) {
					if (stuBirth.split("-").length == 3) {
						String bir2 = stuBirth.split("-")[1];
						String bir3 = stuBirth.split("-")[2];
						if (bir2.length() == 1)
							bir2 = "0" + bir2;
						if (bir3.length() == 1)
							bir3 = "0" + bir3;
						stuBirth = stuBirth.split("-")[0] + "-" + bir2 + "-"
								+ bir3;
					}
				}

//				
//				if (null != birth && !(birth.equals(stuBirth))) {
//					_field = "birthday";
//					cols.add(_field);
//					errs.add("身份证中的出生日期和教职工的出生日期不一致。");
//				}
//
//				if (cols.size() > 0) {
//					disposeError(importData, i, cols.toArray(new String[0]),
//							errs.toArray(new String[0]));
//					throw new ErrorFieldException();
//				}
				//根据身份证号自动生成 出生日期
			}else{
				teacher.setBirthday(birth);
			}
		}
	}

	/**
	 * 增加修改教职工用户记录
	 * 
	 * @param importData
	 * @param reply
	 * @param isCovered
	 * @param insertTeacherMap
	 * @param updateTeacherList
	 * @throws ParseException
	 */
	private List<String> processTable(ImportData importData, Reply reply,
			String isCovered, String objectName,
			Map<String, TeacherImport> insertTeacherMap,
			List<TeacherImport> updateTeacherList) throws ParseException {
		List<String> message = new ArrayList<String>();
		// 更新操作
		int cnt = 0;
		if ("1".equals(isCovered) && updateTeacherList.size() > 0) {
			List<String> teacherSqlList = new ArrayList<String>();
			List<String> userSqlList = new ArrayList<String>();
			for (TeacherImport teacher : updateTeacherList) {
				buildUpdateSql(teacher, importData, teacherSqlList, userSqlList);
			}

			if (teacherSqlList.size() > 0) {
				cnt = systemCommonDao.batchUpdate(teacherSqlList
						.toArray(new String[0]));
				message.add("更新教职工信息：" + cnt + " 条。");
			}

			if (userSqlList.size() > 0) {
				cnt = systemCommonDao.batchUpdate(userSqlList
						.toArray(new String[0]));
				message.add("更新用户信息：" + cnt + " 条。");
			}
		}

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
		Iterator it = colNames.iterator();
        while(it.hasNext()){
            if (it.next().equals("teachGroupName")) {
            	it.remove();
            }
        }
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
		sql.append(",weave_unit_id,in_preparation ");
		sqlValue.append(",'"+teacher.getUnitid()+"'");
		sqlValue.append(",'1'");
		// 在职标记
		if (!colNames.contains("incumbencysign")) {
			ImportObjectNode node = nodeMap.get("incumbencysign");
			String dbcol = node.getDbname();
			sql.append("," + dbcol);
			sqlValue.append(",'11'");
		} else {
			ImportObjectNode node = nodeMap.get("incumbencysign");
			String dbcol = node.getDbname();
			sql.append("," + dbcol);
			sqlValue.append(",'" + teacher.getIncumbencysign() + "'");
		}

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

	/**
	 * 批量更新
	 * 
	 * @param teacher
	 * @param importData
	 * @param teacherUpdateList
	 * @param userUpdateList
	 * @throws ParseException
	 */
	private void buildUpdateSql(TeacherImport teacher, ImportData importData,
			List<String> teacherUpdateList, List<String> userUpdateList)
			throws ParseException {

		Map<String, ImportObjectNode> nodeMap = importData.getMapOfNodesName();
		List<String> colNames = importData.getListOfImportDataName();// 导入列
		Iterator it = colNames.iterator();
        while(it.hasNext()){
            if (it.next().equals("teachGroupName")) {
            	it.remove();
            }
        }
		StringBuffer sql = new StringBuffer();

		// 更新教职工信息
		sql.append("UPDATE base_teacher SET event_source =")
				.append(EventSourceType.LOCAL.getValue())
				.append(",modify_time = ").append(getFormattedTimeForSql());
		for (String col : colNames) {
			if (StringUtils.isEmpty(col))
				continue;
			Object obj = ObjectUtils.getProperty(teacher, col);
			String inValue = String.valueOf(obj);
			ImportObjectNode node = nodeMap.get(col);
			String dbcol = node.getDbname();
			String outValue = null;
			if("YearMonth".equalsIgnoreCase(node.getType())){
				outValue = getFormattedDateByDayForSQL(inValue);
			}else{
				outValue = getValueForSQL(node, inValue);
			}

			// 过滤掉单位id
			if (!"unitid".equals(col) && teacherFieldSet.contains(col)) {
				sql.append("," + dbcol + " = " + outValue);
			}
		}

		sql.append(" WHERE unit_id = '").append(teacher.getUnitid())
				.append("' AND is_deleted = 0 AND teacher_code = '")
				.append(teacher.getTeachercode()).append("'"); // 注意：base_teacher表中是没有username的

		teacherUpdateList.add(sql.toString());

		// 更新用户信息
		sql.delete(0, sql.length());
		sql.append("update base_user set event_source=")
				.append(EventSourceType.LOCAL.getValue())
				.append(",modify_time = ").append(getFormattedTimeForSql());
		for (String col : colNames) {
			if (StringUtils.isEmpty(col))
				continue;
			Object obj = ObjectUtils.getProperty(teacher, col);
			String inValue = String.valueOf(obj);
			ImportObjectNode node = nodeMap.get(col);
			String dbcol = node.getDbname();
			String outValue = getValueForSQL(node, inValue);

			// 过滤掉用户名
			if (!"username".equals(col) && userFieldSet.contains(col)) {
				sql.append("," + dbcol + " = " + outValue);
			}
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

		sql.append(",user_state = '").append(mark)
				.append("' where username = '").append(teacher.getUsername())
				.append("' and is_deleted = 0 and unit_id = '")
				.append(teacher.getUnitid()).append("'");

		userUpdateList.add(sql.toString());
	}

	/**
	 * 针对导入数据中惟一性的字段进行整理
	 * 
	 * @param listOfImportData
	 * @param objectName
	 * @param unitId
	 * @param allSubUnitMap
	 * @param tchCodeMap
	 * @param usernameMap
	 * @param stuIdentityCardMap
	 * @param tchIdentityCardMap
	 */

	@SuppressWarnings("unchecked")
	private void getUniqueMap(List<Object> listOfImportData, String objectName,
			String unitId, Map<String, Unit> allSubUnitMap, // 拿来使用的
			Map<String, BaseTeacher> tchCodeMap, // 职工编号(按单位来分)
			Map<String, User> usernameMap, // 用户名
			Map<String, User> stuIdentityCardMap, // 学生身份证号码
			Map<String, User> tchIdentityCardMap // 职工身份证号码
	) {

		Map<String, Set<String>> tmpTchCodeMap = new HashMap<String, Set<String>>();
		List<String> usernameList = new ArrayList<String>();
		List<String> identityCardList = new ArrayList<String>();

		for (int i = 0; i < listOfImportData.size(); i++) {
			TeacherImport teacher = (TeacherImport) listOfImportData.get(i);

			// 某单位的职工编号
			// 如果是教育局端导入
			if (SystemConstant.TEACHER_IMPORT_EDU.equals(objectName)) {
				teacher.setUnitid(teacher.getUnitid().replaceAll(" ", "")
						.replaceAll(" ", ""));
				Unit u = allSubUnitMap.get(teacher.getUnitid());
				if (null != u) {
					Set<String> tempSet = tmpTchCodeMap.get(u.getId());
					if (null == tempSet) {
						tempSet = new HashSet<String>();
					}
					tempSet.add(teacher.getTeachercode());
					tmpTchCodeMap.put(u.getId(), tempSet);

					// 用户名
					if (StringUtils.isNotBlank(teacher.getUsername())) {
						usernameList.add(teacher.getUsername());
					}

					// 身份证号
					if (StringUtils.isNotBlank(teacher.getIdentitycard())) {
						identityCardList.add(teacher.getIdentitycard());
					}
				}

				// 学校端导入
			} else if (StringUtils.isNotBlank(teacher.getTeachercode())) {
				Set<String> tempSet = tmpTchCodeMap.get(unitId);
				if (null == tempSet) {
					tempSet = new HashSet<String>();
				}
				tempSet.add(teacher.getTeachercode());
				tmpTchCodeMap.put(unitId, tempSet);

				// 用户名
				if (StringUtils.isNotBlank(teacher.getUsername())) {
					usernameList.add(teacher.getUsername());
				}

				// 身份证号
				if (StringUtils.isNotBlank(teacher.getIdentitycard())) {
					identityCardList.add(teacher.getIdentitycard());
				}
			}
		}

		// 职工编号
		if (tmpTchCodeMap.size() > 0) {
			for (String uId : tmpTchCodeMap.keySet()) {
				tchCodeMap.putAll(getTchCodeMap(uId, tmpTchCodeMap.get(uId)));
			}
		}

		// 用户名
		if (usernameList.size() > 0) {
			usernameMap.putAll(getUsernameMap(usernameList));
		}

		// 身份证号
		if (identityCardList.size() > 0) {
			stuIdentityCardMap.putAll(getStuIdentityCardMap(identityCardList));
			tchIdentityCardMap.putAll(getTchIdentityCardMap(identityCardList));
		}
	}

	/**
	 * 处理部门信息
	 * 
	 * @param deptName
	 * @param unitId
	 * @param sb
	 * @param deptNameMap
	 * @param deptOrderIdMap
	 * @param deptIdMap
	 * @return
	 */
	private String processDept(String deptName, String unitId, StringBuffer sb,
			Map<String, String> deptNameMap, Map<String, Long> deptOrderIdMap,
			Map<String, String> deptIdMap, String todeptId) {

		String deptOrderIdStr = deptIdMap.get("deptOrderId");
		Long deptOrderId;
		if (deptOrderIdStr != null)
			deptOrderId = Long.valueOf(deptOrderIdStr);
		String deptId = deptIdMap.get("deptId");
		String groupId = deptNameMap.get(unitId + "_" + deptName);
		if (groupId == null || "".equals(groupId)) {
			Dept dept = baseDeptService.getDept(unitId, deptName);
			if (dept != null)
				groupId = dept.getId();
			if (deptOrderIdMap.get(unitId) == null) {
				deptOrderId = baseDeptService.getAvaOrder(unitId);
				deptOrderIdMap.put(unitId, deptOrderId);
			}

			if (groupId != null && !"".equals(groupId)) {
				deptNameMap.put(unitId + "_" + deptName, groupId);
			} else {
				groupId = UUIDGenerator.getUUID();
				deptNameMap.put(unitId + "_" + deptName, groupId);
				String parentId = BaseConstant.ZERO_GUID;
				if (deptId == null || deptId.equals("")) {
					deptId = baseDeptService.getAvaDeptCode(unitId);
					deptOrderId = baseDeptService.getAvaOrder(unitId);
					deptOrderIdMap.put(unitId, deptOrderId);
				} else {
					if (deptOrderIdMap.get(unitId) == null) {
						deptOrderId = 1L;
					} else {
						deptOrderId = deptOrderIdMap.get(unitId);
					}
					deptOrderIdMap.put(unitId, deptOrderId);
				}
				try {
					deptId = String.valueOf(Integer.parseInt(deptId));
				} catch (Exception e) {
					deptId = "1";
					deptOrderId = 1L;
					deptOrderIdMap.put(unitId, deptOrderId);
					log.error("fail to parse deptId to integer", e);
				}

				int length = String.valueOf(deptId).length();
				for (int i = 0; i < 6 - length; i++) {
					deptId = "0" + deptId;
				}
				if (!todeptId.equals("no")) {
					deptId = todeptId;
				}
				if (!isDeptExist(deptName, unitId)) {
					sb.append(
							"insert into base_dept(display_order, dept_type, id, unit_id, dept_name, dept_code, is_deleted, modify_time,creation_time,parent_id,event_source) values (")
							.append(deptOrderIdMap.get(unitId))
							.append(",1, '")
							.append(groupId)
							.append("', '")
							.append(unitId)
							.append("', '")
							.append(deptName)
							.append("', '")
							.append(deptId)
							.append("', '0', ")
							.append(getFormattedTimeForSql())
							.append("," + getFormattedTimeForSql())
							.append(", '" + parentId)
							.append("'," + EventSourceType.LOCAL.getValue()
									+ ")");
				}
			}
		}
		deptIdMap.put("deptOrderId", deptOrderIdStr);
		deptIdMap.put("deptId", deptId);
		if (deptId == null) {
			deptId = "no";
		}
		return groupId + "!" + deptId;
	}

	/**
	 * 判断部门是否存在
	 */
	private boolean isDeptExist(String groupName, String unitId) {
		return baseDeptService.isExistsDeptName(groupName, unitId);
	}

	/**
	 * 职工编号查询
	 * 
	 * @param unitId
	 * @param tchCodeSet
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private Map<String, BaseTeacher> getTchCodeMap(String unitId,
			Set<String> tchCodeSet) {
		return baseTeacherService.getTeacherMap(unitId,
				tchCodeSet.toArray(new String[0]));
	}

	/**
	 * 登录名查询
	 * 
	 * @param usernameList
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private Map<String, User> getUsernameMap(List<String> usernameList) {
		return baseUserService.getUsersMapByName(usernameList
				.toArray(new String[0]));
	}

	/**
	 * 学生身份证号查询
	 * 
	 * @param identityCardList
	 * @return
	 */
	private Map<String, User> getStuIdentityCardMap(
			List<String> identityCardList) {
		List<Student> stuList = studentService
				.getStudentsByUnitiveCodes(identityCardList
						.toArray(new String[0]));
		Map<String, User> userMap = new HashMap<String, User>();
		User u = null;
		for (Student stu : stuList) {
			u = new User();
			u.setUnitid(stu.getSchid());
			u.setRealname(stu.getStuname());
			userMap.put(stu.getIdentitycard(), u);
		}
		return userMap;
	}

	/**
	 * 职工身份证号查询
	 * 
	 * @param identityCardList
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private Map<String, User> getTchIdentityCardMap(
			List<String> identityCardList) {
		List<Teacher> teaList = baseTeacherService
				.getTeacherMapByIdentityCards(identityCardList
						.toArray(new String[0]));
		Map<String, User> userMap = new HashMap<String, User>();
		User u = null;
		for (Teacher tea : teaList) {
			u = new User();
			u.setTeacherid(tea.getId());
			u.setUnitid(tea.getUnitid());
			u.setRealname(tea.getName());
			userMap.put(tea.getIdcard(), u);
		}
		return userMap;
	}

	/**
	 * 出于性能的考虑，只取有用的值
	 * 
	 * @param unitId
	 */
	@SuppressWarnings("unchecked")
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

	/**
	 * 取得教育下指定类型的直接下属单位id list
	 * 
	 * @param unitId
	 * @param type
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private Map<String, Unit> getDirSubUnitMap(String unitId, Integer type) {
		List<Unit> unitList = baseUnitService.getUnderlingUnits(unitId, type);
		Map<String, Unit> unitMap = new HashMap<String, Unit>();
		for (Unit unit : unitList) {
			unitMap.put(unit.getName(), unit);
		}
		return unitMap;
	}

	public List<ImportObjectNode> getDynamicFields(DataImportParam param) {
		List<ImportObjectNode> list = new ArrayList<ImportObjectNode>();
		return list;
	}

	@Override
	public List<ImportObjectNode> getRedefineFields(
			Map<String, ImportObjectNode> nodeMap, DataImportParam param) {
		List<ImportObjectNode> nodes = new ArrayList<ImportObjectNode>();
		// 用户名是否区分大小写
		ImportObjectNode node = nodeMap.get("username");
		if (node != null) {
			node.setCaseSensitive(User.isUsernameNotCaseSensitive() ? "N" : "Y");
			nodes.add(node);
		}
		return nodes;
	}

	public static boolean isContainsEspecial(String content) {
		// 如果是存在[].在字符中需用\转义
		// String regularExpression="[@\"*&%$#!$'?/>:;{}<,~`-+\\.\\[\\]]";
		// //"[@\"*&%$#!$']"
		String regularExpression = "[:/@\"*&%$,#;!$'-+\\.\\[\\]]"; // "[@\"*&%$#!$']"
		PatternCompiler compiler = new Perl5Compiler();
		org.apache.oro.text.regex.Pattern pattern;
		try {
			pattern = compiler.compile(regularExpression);
			PatternMatcher matcher = new Perl5Matcher();
			return matcher.contains(content, pattern);
		} catch (MalformedPatternException e) {
			e.printStackTrace();
		}
		return true;
	}
	
	/**
	 * 获取值
	 * @param key
	 * @param o
	 * @return
	 */
	private Object getKeyValue(String key, Object o){
		Object value = null;
		try {
			String firstLetter = key.substring(0, 1).toUpperCase();       
			String getter = "get" + firstLetter + key.substring(1);       
			Method method = o.getClass().getMethod(getter, new Class[] {});
			value = method.invoke(o, new Object[] {});
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}       
		return value;
	}

	public ResearchGroupService getResearchGroupService() {
		return researchGroupService;
	}

	public void setResearchGroupService(ResearchGroupService researchGroupService) {
		this.researchGroupService = researchGroupService;
	}
	
	
}
