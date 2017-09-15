package net.zdsoft.eis.base.data.action;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.sf.json.JSONObject;
import net.zdsoft.eis.base.common.entity.Family;
import net.zdsoft.eis.base.common.entity.FamilyUser;
import net.zdsoft.eis.base.common.entity.Unit;
import net.zdsoft.eis.base.common.entity.User;
import net.zdsoft.eis.base.common.service.Mcode;
import net.zdsoft.eis.base.common.service.McodeService;
import net.zdsoft.eis.base.common.service.StudentFamilyService;
import net.zdsoft.eis.base.common.service.UnitService;
import net.zdsoft.eis.base.common.util.AppSetting;
import net.zdsoft.eis.base.constant.BaseConstant;
import net.zdsoft.eis.base.constant.enumeration.VersionType;
import net.zdsoft.eis.base.data.BasedataConstants;
import net.zdsoft.eis.base.data.dto.StuAndFamLoginDto;
import net.zdsoft.eis.base.data.dto.StudentUserDto;
import net.zdsoft.eis.base.data.dto.UserTempNameDto;
import net.zdsoft.eis.base.data.service.BaseUserService;
import net.zdsoft.eis.base.deploy.SystemDeployService;
import net.zdsoft.eis.base.simple.entity.SimpleClass;
import net.zdsoft.eis.base.simple.entity.SimpleStudent;
import net.zdsoft.eis.base.util.BusinessUtils;
import net.zdsoft.eis.frame.action.BaseSimpleAction;
import net.zdsoft.eisu.base.common.entity.EisuStudent;
import net.zdsoft.eisu.base.common.service.EisuStudentService;
import net.zdsoft.keel.action.Reply;
import net.zdsoft.keel.util.SpellUtils;
import net.zdsoft.keel.util.UUIDUtils;
import net.zdsoft.keelcnet.config.ContainerManager;
import net.zdsoft.leadin.util.ExportUtil;
import net.zdsoft.leadin.util.PWD;
import net.zdsoft.leadin.util.PinyinUtil;
import net.zdsoft.passport.exception.PassportException;
import net.zdsoft.passport.service.client.PassportClient;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

/* 
 * <p>数字校园2.0</p>
 * 用户开通的Action
 * 家校互联批量开通学生和学生和家长
 * @author fangb
 * @since  2.0
 * @version $Id:,v 2.0  Exp UserOpenAction.java, v 2.0 2008-7-18 下午03:37:47  Exp $
 */

public class UserOpenAction extends BaseSimpleAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = -727693213095552260L;

	private UnitService unitService;
	private StudentFamilyService studentFamilyService;
	private BaseUserService baseUserService;
	private EisuStudentService eisuStudentService;
	private String classId = ""; // 当前选择的班级id
	private String etohSchoolId = ""; // 学校编号
	private String userNamePrefix=""; // 用户名前缀
	private int maxcode = 0;// 班级流水号
	private Map<String, String> usernameIdCardMap = new HashMap<String,String>();//生成临时身份证
	private Map<String,String >  generateTempUserName=new HashMap<String,String>();
	private Map<String, String> ruleRegexMap = new HashMap<String, String>();
	private String unitName = ""; // 单位名称
	private AppSetting appsetting = AppSetting.getInstance();

	private List<StudentUserDto> studentUserList; // 学生用户列表
	private Map<String, List<FamilyUser>> familyUserMap; // 家长用户映射
	private boolean sysAccount = false;
	// private static final String TICKET_KEY = "ticket_key";
	// private static final String USER_NAME_REGEX =
	// "[a-zA-Z_0-9]{1}[a-zA-Z0-9_]{3,19}";
	// private static final String USER_NAME_REGEX =
	// "[a-zA-Z_]{1}[a-zA-Z0-9_]{3,19}";
	public static final String SYSTEM_ACCOUNT = "SYSTEM.ACCOUNT";

	private String studentId;
	private String identityCard;

	private String studentIds;
	private String familyIds;
	private String studentRealName;
	private String studentLogin;
	private String familyLogin;
	private String familyRealName;
	private String fieldErrorKey;
	private String fieldErrorValue;
	private String studentUserRule;
	private User loginUser;

	private McodeService mcodeService;
	/**
	 * 班级编号
	 */
	private String clsCode;
	public String execute() {
		if (!(systemIniService
				.getBooleanValue(BasedataConstants.STUDENT_FAMILY_ACCOUNT_SWITCH))) {
			promptMessageDto.setErrorMessage("对不起，现在没有开通这项功能！");
			return ERROR;
		}

		Unit unit = unitService.getUnit(this.getUnitId());
		if (unit == null) {
			promptMessageDto.setErrorMessage("找不到该单位！");
			return ERROR;
		}
		etohSchoolId = unit.getEtohSchoolId();

		if (unit.getUnitclass() == Unit.UNIT_CLASS_EDU) {
			promptMessageDto.setErrorMessage("对不起教育局用户没有开通这项功能");
			return ERROR;
		}
		return SUCCESS;
	}

	/**
	 * 根据班级ID得到班级下的学生列表
	 * 
	 * @return
	 */
	public String listStudentByClassId() {
		// 这里和上面不是同一个session，所以无法调用，这里先屏蔽
		// if(!ActionContext.getContext().getSession().containsKey(TICKET_KEY)||
		// !ActionContext.getContext().getSession().get(TICKET_KEY).equals(ticket)){
		// promptMessageDto.setErrorMessage("验证失败！请重试！");
		// return this.ERROR;
		// }

		Unit unit = unitService.getUnit(this.getUnitId());
		if (unit != null) {
			etohSchoolId = unit.getEtohSchoolId();
		}
		//用户名前缀
		userNamePrefix=getUserNamePrefix();
		if (StringUtils.isBlank(classId)) {
			return SUCCESS;
		}
		SimpleClass cls = getClassService().getClass(classId);
		clsCode = cls.getClasscode();

		maxcode=getMaxcode();
		List<SimpleStudent> stuInfoList = getStudentService().getStudents(
				classId);
		Map<String, SimpleStudent> studentUserDtoMap = new HashMap<String, SimpleStudent>();
		List<String> studentIds=new ArrayList<String>();
		for (SimpleStudent item : stuInfoList) {
			studentUserDtoMap.put(item.getId(), item);
			studentIds.add(item.getId());
		}

		familyUserMap = studentFamilyService
				.getFamilyUserMapByStuIds(studentIds.toArray(new String[0]));
		try{
			

		//生成规则map；
		setRuleRegexMap(ruleRegexMap);
		studentUserList = getStudentUserDtoByClassId(classId);
		for (Map.Entry<String, List<FamilyUser>> entry : familyUserMap
				.entrySet()) {
			List<FamilyUser> flist = entry.getValue();
			for (FamilyUser fu : flist) {
				if (StringUtils.isNotBlank(fu.getFamilyLoginName())) {
					continue;
				}
				UserTempNameDto temp = new UserTempNameDto();
				temp.setStudentId(fu.getStudentId());
				temp.setType(User.FAMILY_LOGIN);
				temp.setMobile(fu.getMobile());
				temp.setSpellName(PinyinUtil.toHanyuPinyin(fu.getFamilyName(), true));
				if("01".equals(fu.getRelation()) || "51".equals(fu.getRelation())){
					temp.setFamilyType("01");
				}else if("02".equals(fu.getRelation()) || "52".equals(fu.getRelation())){
					temp.setFamilyType("02");
				}else{
					temp.setFamilyType("03");
				}
				
				temp.setEtohSchoolId(etohSchoolId);
				temp.setRealName(fu.getFamilyName());
				temp.setFirstSpell(generateFirstSpell(temp));
				SimpleStudent studentUserDto = studentUserDtoMap.get(entry
						.getKey());
				if (studentUserDto != null) {
					temp.setStuCode(studentUserDto.getStucode());
					temp.setUnitiveCode(studentUserDto.getUnitivecode());
					temp.setTempUserName(generateTempUserName.get(studentUserDto.getId()));
					if(StringUtils.isNotBlank(studentUserDto.getIdentitycard())){
						temp.setIdentitycard(studentUserDto.getIdentitycard());
					}else{
						temp.setBirth(studentUserDto.getBirthday());
						temp.setRegionCode(studentUserDto.getRegionCode());
						temp.setSex(studentUserDto.getSex());
						temp.setTempIdCard(generateTempIdcard(temp));
					}
				}
				
				fu.setTempLoginName(getUserName(temp));
//				fu.setTempLoginName(getTempLoginName(temp));
			}
		}
		}catch(Exception e){
			e.printStackTrace();
			promptMessageDto.setOperateSuccess(false);
			promptMessageDto.setPromptMessage("提示：规则信息错误  ,请重新维护编辑  ");
			return ERROR;
		}
		return SUCCESS;
	}

	public String getStudentIdentCardById() {
		try {
			if (eisuStudentService == null)
				eisuStudentService = (EisuStudentService) ContainerManager
						.getComponent("eisuStudentService");
			identityCard = eisuStudentService.getIdentityTypeCard(studentId,
					"1");
			if (StringUtils.isNotBlank(identityCard)) {
				identityCard = identityCard + "s";
			} else {
				identityCard = "";
			}
			promptMessageDto.setOperateSuccess(true);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			promptMessageDto.setOperateSuccess(false);
			promptMessageDto.setErrorMessage("开通账号失败!");
		}
		promptMessageDto.setBusinessValue(identityCard);
		return SUCCESS;
	}

	public String getStuIdentCardByFamId(String famId) {
		Family family = studentFamilyService.getFamily(famId);
		if (eisuStudentService == null)
			eisuStudentService = (EisuStudentService) ContainerManager
					.getComponent("eisuStudentService");
		EisuStudent stu = eisuStudentService.getStudent(family.getStudentId());
		return stu.getIdentitycard() == null ? "" : stu.getIdentitycard();
	}

	private List<StudentUserDto> getStudentUserDtoByClassId(String classId) {
		if (StringUtils.isBlank(studentUserRule)) {
			studentUserRule = getStudentUserRule();
		}
		List<StudentUserDto> list = null;
		List<SimpleStudent> stuInfoList = getStudentService().getStudents(classId);
	
		if (null == stuInfoList || stuInfoList.isEmpty())
			return new ArrayList<StudentUserDto>();

		List<String> stuIds = new ArrayList<String>();
		for (SimpleStudent stuInfo : stuInfoList)
			stuIds.add(stuInfo.getId());

		Map<String, User> userMap = baseUserService.getUserMapByOwner(
				User.STUDENT_LOGIN, stuIds.toArray(new String[0]));
		Map<String,SimpleStudent> studentExMap=getStudentService().getStudentdexByStudentIds(stuIds.toArray(new String[0]));
		list = new ArrayList<StudentUserDto>();
		for (SimpleStudent stuInfo : stuInfoList) {

			StudentUserDto studentUserDto = new StudentUserDto();
			studentUserDto.setId(stuInfo.getId());
			studentUserDto.setStudentName(stuInfo.getStuname());
			studentUserDto.setStudentCode(stuInfo.getStucode());

			User user = userMap.get(stuInfo.getId());

			if (user != null) {
				studentUserDto.setStudentLoginName(user.getName());
				studentUserDto.setStudentPassword(user.findClearPassword());
				generateTempUserName.put(stuInfo.getId(), user.getName());
			} else {
				maxcode++;
				UserTempNameDto temp = new UserTempNameDto();
				temp.setStudentId(stuInfo.getId());
				temp.setStuCode(stuInfo.getStucode());
				List<FamilyUser> familyUsers=familyUserMap.get(stuInfo.getId());
				if(CollectionUtils.isNotEmpty(familyUsers)){
					for(FamilyUser family:familyUsers){
						if("01".equals(family.getRelation())||"51".equals(family.getRelation())){
							temp.setfMobile(family.getMobile());
						}
					}
				}
				temp.setMaxcode(StringUtils.leftPad(String.valueOf(maxcode), 2, "0"));
				temp.setType(User.STUDENT_LOGIN);
				temp.setEtohSchoolId(etohSchoolId);
				temp.setUserNamePrefix(userNamePrefix);
				if(StringUtils.isNotEmpty(stuInfo.getSpellName())){
					temp.setSpellName(stuInfo.getSpellName());
				}else{
					if(studentExMap.get(stuInfo.getId()) != null){
						temp.setSpellName(studentExMap.get(stuInfo.getId()).getSpellName());
					}
				}
				temp.setRealName(stuInfo.getStuname());
				temp.setFirstSpell(generateFirstSpell(temp));
				temp.setClsCode(clsCode);
				temp.setUnitiveCode(stuInfo.getUnitivecode());
				
				if(StringUtils.isNotBlank(stuInfo.getIdentitycard())){
					temp.setIdentitycard(stuInfo.getIdentitycard());
				}else{
					temp.setBirth(stuInfo.getBirthday());
					temp.setRegionCode(stuInfo.getRegionCode());
					temp.setSex(stuInfo.getSex());
					temp.setTempIdCard(generateTempIdcard(temp));
				}
				studentUserDto.setTempLoginName(getUserName(temp));
//				studentUserDto.setTempLoginName(getTempLoginName(temp));
				generateTempUserName.put(stuInfo.getId(), getUserName(temp));
			}
			list.add(studentUserDto);
		}

		return list;
	}
	private String  generateTempIdcard(UserTempNameDto tempDto){
		if(!usernameIdCardMap.containsKey(tempDto.getStudentId())){
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			String tempCard = BusinessUtils.generateIdentityCardWithVerifyCode(tempDto.getRegionCode(), 
					tempDto.getBirth() == null?null:df.format(tempDto.getBirth()), String.valueOf(tempDto.getSex()), null);
			usernameIdCardMap.put(tempDto.getStudentId(), tempCard);
		}
		return usernameIdCardMap.get(tempDto.getStudentId());
	}
	private String generateFirstSpell(UserTempNameDto tempDto ){
		StringBuffer sb = new StringBuffer();
		boolean hasCh = false;
		for (int i = 0; i < tempDto.getRealName().length(); i++){
			if(!hasCh){
				byte[] bytes = null;
		        try {
		            bytes = (String.valueOf(tempDto.getRealName().charAt(i))).getBytes("GBK");
		        }
		        catch (UnsupportedEncodingException e) {
		            continue;
		        }
		        if (bytes == null || bytes.length > 2 || bytes.length <= 0) { // 错误
		            continue;
		        }
		        if(bytes.length == 2){
		        	sb.append(PinyinUtil.toHanyuPinyin(String.valueOf(tempDto.getRealName().charAt(i)), false));
		        	hasCh = true;
		        } else {
		        	sb.append(tempDto.getRealName().charAt(i));
		        }
			} else {
				sb.append(PinyinUtil.toHanyuPinyin(String.valueOf(tempDto.getRealName().charAt(i)), true));
			}
		}
		return sb.toString();
	}
	private String getTempLoginName(UserTempNameDto temp) {
		if (StringUtils.isBlank(studentUserRule)) {
			studentUserRule = getStudentUserRule();
		}
		String tempLoginName = "";
		String systemDeploySchool = getSystemDeploySchool();
		if (BasedataConstants.SYSTEM_STUDENT_USERNAME_2.equals(studentUserRule)) {//
			if (User.STUDENT_LOGIN == temp.getType()) {// 学生
				tempLoginName = "s" + temp.getStuCode();
			} else {// 家长
				tempLoginName = getDefaultUserName(null, User.FAMILY_LOGIN);
			}
		} else if (BasedataConstants.SYSTEM_STUDENT_USERNAME_ZH_3
				.equals(studentUserRule)
				&& systemDeploySchool
						.equals(BaseConstant.SYS_DEPLOY_SCHOOL_ZHZG)) {
			if (User.STUDENT_LOGIN == temp.getType()) {// 学生
				tempLoginName = temp.getStuCode();
			} else {// 家长
				// F、 M+学号 TODO
				if (BasedataConstants.SYSTEM_FAMILY_TYPE_01.equals(temp
						.getFamilyType())) {
					tempLoginName = "F" + temp.getStuCode();
				} else if (BasedataConstants.SYSTEM_FAMILY_TYPE_02.equals(temp
						.getFamilyType())) {
					tempLoginName = "M" + temp.getStuCode();
				} else {
					// 不是父亲或者母亲不自动生成帐号
				}
			}
		} else {
			if (User.STUDENT_LOGIN == temp.getType()) {// 学生
				tempLoginName = getDefaultUserName(temp.getIdentitycard(),
						User.STUDENT_LOGIN);
			} else {
				tempLoginName = getDefaultUserName(null, User.FAMILY_LOGIN);
			}
		}
		return tempLoginName;
	}
	private void setRuleRegexMap(Map<String, String> ruleRegexMap){
		if (StringUtils.isBlank(studentUserRule)) {
			studentUserRule = getStudentUserRule();
			
		}
		String[] rules = studentUserRule.split("\\},");
		for(String str:rules){
			String rule=str.replaceAll("\\{|\\}", "");
			String right=rule.substring(rule.indexOf("_"));
			String[] role=rule.split("_");
			String headds = role[0].replaceAll("\\(|\\)", "");
			String[] heads=headds.split("/");
			for(String head:heads){
				String urlTrim=head+right;
				if(head.indexOf("s") != -1 || head.indexOf("x") != -1 || head.indexOf("G") != -1 || head.indexOf("N") != -1){
					ruleRegexMap.put("1", urlTrim);
				}else if(head.indexOf("p") != -1){
					ruleRegexMap.put("01", urlTrim);
					ruleRegexMap.put("02", urlTrim);
				}else if( head.indexOf("f") != -1 || head.indexOf("F")!= -1){
					ruleRegexMap.put("01", urlTrim);
				}else if( head.indexOf("m") != -1 || head.indexOf("M")!= -1){
					ruleRegexMap.put("02", urlTrim);
				}else{
					ruleRegexMap.put("03", urlTrim);
				}
			}
			
		}
		
		if(StringUtils.isEmpty(ruleRegexMap.get("03"))){
			ruleRegexMap.put("03", ruleRegexMap.get("01"));
		}
	}
	
	public String getUserName(UserTempNameDto temp) {

		JSONObject json = (JSONObject) JSONObject.fromObject(temp);
		String regex = null;
		Pattern pattern = Pattern.compile("\\d");
		Pattern pattern2 = Pattern.compile("(-)?\\d,\\d{1,2}");
		if (User.STUDENT_LOGIN == temp.getType()) {// 学生
			regex = ruleRegexMap.get(String.valueOf(temp.getType()));
		} else {
			regex = ruleRegexMap.get(temp.getFamilyType());
		}

		String userName = null;
		String[] role = regex.split("_");

		StringBuffer buffer = new StringBuffer();
		int index=0;
		for (int i = 1; i < role.length; i++) {
			Matcher ma = pattern2.matcher(role[i]);
			//生成随机数只能输出 1-9 位随机数3301060001  p330106000138488
			if (role[i].indexOf("random") != -1) {
				Matcher match = pattern.matcher(role[i]);
				int sum = 0;
				if (match.find()) {
					sum = Integer.valueOf(match.group());
				}

				double d =Math.random();
				buffer.append(String.valueOf(d).substring(2, 2+sum));
			} else if (ma.find()) {
				String num = ma.group();
				String[] nums = num.split(",");
				int startIndex = Integer.valueOf(nums[0]);
				int length = Integer.valueOf(nums[1]);
				String[] names = role[i].split("\\[");

				String re = json.getString(names[0]);
				if (startIndex >= 0) {
					int start =(startIndex >re.length())? 0:startIndex;
					if((length-start) <  re.length()){
						buffer.append(re.substring(start,
								length));
					}else{
						buffer.append(re.substring(start));
					}
					
				} else {
					int start = (re.length() - length)>0? (re.length() - length):0;
					buffer.append(re.substring( start));
				}

			} else {
				if(role[i].indexOf("?") != -1){
					String[] params=role[i].split("\\?");
					if(StringUtils.isNotEmpty(json.getString(params[0]))){
						buffer.append(json.getString(params[0]));
					}else{
						buffer.append(json.getString(params[1]));
						index=1;
					}
				}else{
					buffer.append(json.getString(role[i]));
				}

			}
		}
		String[] titles=role[0].split("\\?");
		index=(titles.length==2) ?index:0;
		if (titles[index].indexOf("-") == 0) {
			userName = buffer.toString() + titles[index].substring(1);
		}else if(titles[index].indexOf("N") != -1){
			userName = buffer.toString();
		}else {
			userName =titles[index] + buffer.toString();
		}

		return userName;
	}
	/**
	 * 获取前缀
	 * @return
	 */
	public String getUserNamePrefix(){
		String preVal = systemIniService.getValue(User.SYSTEM_USERNAME_PREFIX);
		if(StringUtils.isNotBlank(preVal)){
			return preVal;
		}
		return "";
	}
	/**
	 * 获取流水号
	 * @return
	 */
	public int getMaxcode(){
		if (StringUtils.isBlank(studentUserRule)) {
			studentUserRule = getStudentUserRule();
			
		}
		String[] rules=studentUserRule.split("},");
		if(rules[0].indexOf("mcode") != -1){
			String[] ru = rules[0].split("_");
			StringBuffer sb = new StringBuffer();

			if(ru.length >2){
				for(int i=1;i<ru.length-1;i++){
					sb.append(ru[i]);
				}
			}
			return baseUserService.getUserNameMaxCodeByClassCode(getUnitId(), sb.toString());
		}else{
			return 0;
		}
	}
	/**
	 * 学生家长默认规则 学生 身份证+s； 家长 p+学校编号+6位随机数
	 * 
	 * @param identitycard
	 * @param type
	 * @return
	 */
	private String getDefaultUserName(String identitycard, int type) {
		StringBuilder sb = new StringBuilder();
		if (User.STUDENT_LOGIN == type) {
			if (StringUtils.isNotBlank(identitycard)) {
				sb.append(identitycard).append("s");
			}
		} else {// 家长
			sb.append("p").append(etohSchoolId);
			String val = String.valueOf(new Random().nextInt(1000000));
			int m = 6 - val.length();
			for (int i = 0; i < m; i++) {
				val = "0" + val;
			}
			sb.append(val);
		}
		return sb.toString();
	}

	private List<StudentUserDto> getStudentUserDtoByClassIdPW(String classId_) {
		List<StudentUserDto> list = null;

		List<SimpleStudent> stuInfoList = getStudentService().getStudents(
				classId_);
		if (null == stuInfoList || stuInfoList.isEmpty())
			return new ArrayList<StudentUserDto>();

		List<String> stuIds = new ArrayList<String>();
		for (SimpleStudent stuInfo : stuInfoList)
			stuIds.add(stuInfo.getId());

		Map<String, User> userMap = baseUserService.getUserMapByOwner(
				User.STUDENT_LOGIN, stuIds.toArray(new String[0]));

		list = new ArrayList<StudentUserDto>();

		for (int i = 0; i < stuInfoList.size(); i++) {
			SimpleStudent stuInfo = stuInfoList.get(i);
			StudentUserDto studentUserDto = new StudentUserDto();
			studentUserDto.setId(stuInfo.getId());
			studentUserDto.setStudentName(stuInfo.getStuname());

			User user = userMap.get(stuInfo.getId());

			if (user != null) {
				studentUserDto.setStudentLoginName(user.getName());
				studentUserDto.setStudentPassword(PWD.decode(user
						.findClearPassword()));
			}
			list.add(studentUserDto);
		}
		return list;
	}

	/**
	 * 远程开通用户的接口
	 * 
	 * @param users
	 * @param schId
	 * @param classId1
	 * @return
	 */
	public String remoteOpenUser() {
		User[] users = createUsers();
		if (!validateUserName(users, null)) {
			return SUCCESS;
		}
		List<String> ownerExistIds = new ArrayList<String>();
		try {// 验证用户账号是否存在
			ownerExistIds = validateUserExist(users);
		} catch (PassportException e) {
			promptMessageDto.setOperateSuccess(false);
			promptMessageDto.setErrorMessage(e.getMessage());
			return SUCCESS;
		}

		// 如果有用户账号存在则返回告知用户
		if (ownerExistIds != null && !ownerExistIds.isEmpty()) {
			for (String ownerId : ownerExistIds) {
				promptMessageDto.setOperateSuccess(false);
				promptMessageDto.addFieldError("login_name_" + ownerId,
						"该账号已存在");
				return SUCCESS;
			}
		}
		// 填充用户的信息
		fillUserDto(users, this.getUnitId(), classId);
		try {
			baseUserService.saveUsers(users);
			promptMessageDto.setOperateSuccess(true);
		} catch (Throwable e) {
			promptMessageDto.setOperateSuccess(false);
			promptMessageDto.setErrorMessage("账号开通出错！错误信息：" + e.getMessage());
		}
		return SUCCESS;
	}

	/**
	 * 导出输入某个班级的所有学生和其对应的家长的登录信息 added by hexq 2009-2-24
	 * 
	 * @return ${classnamedynamic}.xls
	 */
	public String exportStuAndFamInfo() {
		// 表头
		// String head;
		// 标题栏
		String[] fieldTitles;
		// 标题栏的英文表示
		String[] propertyNames;

		fieldTitles = new String[] { "学生姓名", "学生登录帐号", "学生登录密码", "关系", "家长姓名",
				"家长登录帐号", "家长登录密码", "家长手机号码" };
		propertyNames = new String[] { "studentName", "studentLoginName",
				"studentPassword", "relation", "familyName", "familyLoginName",
				"familyPassword", "mobile" };

		// 取得所有的学生用户列表
		studentUserList = getStudentUserDtoByClassIdPW(classId);

		// 取得与学生相应的家长的map
		familyUserMap = studentFamilyService
				.getFamilyUserMapByStuIdsPW(getStuIds(studentUserList));

		List<StuAndFamLoginDto> stuAndFamLoginDtoList = new ArrayList<StuAndFamLoginDto>();
		String mcode = null;
		if (isEisuSchool()) {
			mcode = "DM-CGX";
		} else {
			mcode = "DM-GX";
		}
		Mcode mcodeDetail = mcodeService.getMcode(mcode);
		// 循环迭代将学生和对应的家长信息组装成一个整体
		for (StudentUserDto studentUser : studentUserList) {

			// 如果学生存在家长信息
			if (familyUserMap.containsKey(studentUser.getId())) {
				List<FamilyUser> familyList = familyUserMap.get(studentUser
						.getId());
				for (FamilyUser familyUser : familyList) {
					StuAndFamLoginDto stuAndFam = new StuAndFamLoginDto();
					stuAndFam.setStudentId(studentUser.getId());
					stuAndFam.setStudentName(studentUser.getStudentName());
					stuAndFam.setStudentLoginName(studentUser
							.getStudentLoginName());
					stuAndFam.setStudentPassword(studentUser
							.getStudentPassword());
					stuAndFam.setFamilyId(familyUser.getId());
					stuAndFam.setFamilyName(familyUser.getFamilyName());
					stuAndFam.setFamilyLoginName(familyUser
							.getFamilyLoginName());
					stuAndFam.setFamilyPassword(familyUser.getFamilyPassword());
					stuAndFam.setMobile(familyUser.getMobile());

					String relationCode = familyUser.getRelation();
					stuAndFam.setRelation(mcodeDetail.get(relationCode));
					stuAndFamLoginDtoList.add(stuAndFam);
				}

			} else {
				StuAndFamLoginDto stuAndFam = new StuAndFamLoginDto();
				stuAndFam.setStudentId(studentUser.getId());
				stuAndFam.setStudentName(studentUser.getStudentName());
				stuAndFam
						.setStudentLoginName(studentUser.getStudentLoginName());
				stuAndFam.setStudentPassword(studentUser.getStudentPassword());
				stuAndFamLoginDtoList.add(stuAndFam);
			}
		}

		Map<String, List<StuAndFamLoginDto>> records = new HashMap<String, List<StuAndFamLoginDto>>();

		// 取得班级的实际名称
		SimpleClass dto = getClassService().getClass(classId);

		// 将表名和表的信息放到records中
		records.put(dto.getClassnamedynamic() + "学生家长账号信息",
				stuAndFamLoginDtoList);

		// String fileName = classnamedynamic+"学生和家长登录信息"; 文件名不支持中文！

		// head = dto.getClassnamedynamic() + "学生家长账号信息";

		// excel文件名
		String fileName = "StudentAndFamilyLoginInfo";
		ExportUtil exportUtil = new ExportUtil();
		exportUtil.exportXLSFile(fieldTitles, propertyNames, records, fileName);
		return NONE;
	}

	/**
	 * 验证用户名是否合法
	 * 
	 * @param users
	 * @return
	 */
	private User[] createUsers() {
		List<User> userList = new ArrayList<User>();
		if (StringUtils.isNotBlank(studentIds)) {
			String[] stuIds = StringUtils.split(studentIds, ",");
			String[] stuLoginName = StringUtils.split(studentLogin, ",");
			String[] stuRealName = StringUtils.split(studentRealName, ",");
			if (eisuStudentService == null)
				eisuStudentService = (EisuStudentService) ContainerManager
						.getComponent("eisuStudentService");
			Map<String, EisuStudent> stuMap = eisuStudentService
					.getStudentMap(stuIds);

			for (int i = 0; i < stuIds.length; i++) {
				User user = new User();
				user.setTeacherid(stuIds[i]);
				user.setOwnerType(1);// 1,3
				user.setName(stuLoginName[i]);
				user.setRealname(stuRealName[i]);
				EisuStudent student = stuMap.get(stuIds[i]);
				if (student != null) {
					if (StringUtils.isNotBlank(student.getMobilePhone())) {
						user.setMobilePhone(student.getMobilePhone());
					}
				}

				userList.add(user);
			}
		}
		if (StringUtils.isNotBlank(familyIds)) {
			String[] famIds = StringUtils.split(familyIds, ",");
			String[] famLoginName = StringUtils.split(familyLogin, ",");
			String[] famRealName = StringUtils.split(familyRealName, ",");

			if (studentFamilyService == null)
				studentFamilyService = (StudentFamilyService) ContainerManager
						.getComponent("studentFamilyService");
			Map<String, Family> falyMap = new HashMap<String, Family>();
			List<Family> familyList = studentFamilyService.getFamilies(famIds);
			for (Family fmy : familyList) {
				falyMap.put(fmy.getId(), fmy);
			}

			for (int i = 0; i < famIds.length; i++) {
				User user = new User();
				user.setTeacherid(famIds[i]);
				user.setOwnerType(3);
				user.setName(famLoginName[i]);
				user.setRealname(famRealName[i]);

				Family family = falyMap.get(famIds[i]);
				if (family != null) {
					if (StringUtils.isNotBlank(family.getMobilePhone())) {
						user.setMobilePhone(family.getMobilePhone());
					}
				}
				userList.add(user);
			}
		}
		return userList.toArray(new User[0]);
	}

	private boolean validateUserName(User[] users, Reply reply) {
		for (User userDto : users) {
			if (!validateUserName(userDto.getName())) {
				promptMessageDto.setOperateSuccess(false);
				promptMessageDto.addFieldError("login_name_"
						+ userDto.getTeacherid(), systemIniService
						.getValue(User.SYSTEM_NAME_ALERT));
				return false;
			}
		}
		// 验证是否有用户同名
		int size = users.length;
		for (int i = 0; i < size; ++i) {
			User userDto = users[i];
			for (int j = i + 1; j < size; ++j) {
				User otherUserDto = users[j];
				if (userDto.getName().equals(otherUserDto.getName())) {
					promptMessageDto.setOperateSuccess(false);
					promptMessageDto.addFieldError("login_name_"
							+ userDto.getTeacherid(), "账号重复！");
					return false;
				}
			}
		}

		return true;
	}

	/**
	 * 验证用户名是否合法
	 * 
	 * @param userName
	 * @return
	 */
	// private boolean validateUserName(String userName){
	// return Pattern.matches(USER_NAME_REGEX, userName);
	// }
	private boolean validateUserName(String userName) {
		return Pattern.matches(systemIniService
				.getValue(User.SYSTEM_NAME_EXPRESSION), userName);
	}

	/**
	 * 验证给定的用户名是存在，返回用户的ownerId列表
	 * 
	 * @param users
	 * @return
	 * @throws PassportException
	 */
	private List<String> validateUserExist(User[] users)
			throws PassportException {
		List<String> ownerIds = new ArrayList<String>();

		Set<String> existLoginNameSet = new HashSet<String>();

		List<String> loginNames = new ArrayList<String>();
		for (User user : users)
			loginNames.add(user.getName());

		// 得到在Passport中已经存在的用户账号
		if (systemDeployService.isConnectPassport())
			existLoginNameSet = PassportClient.getInstance()
					.queryExistsAccountUsernames(
							loginNames.toArray(new String[0]));

		// 得到在本城域系统内已经存在的用户账号，放置到存在账号的集合中
		existLoginNameSet.addAll(baseUserService.getUsersMapByName(
				loginNames.toArray(new String[0])).keySet());

		if (existLoginNameSet == null || existLoginNameSet.isEmpty())
			return new ArrayList<String>();

		// 根据已经存在的用户账号，得到账号对应的学生或者家长的id
		for (String loginName : existLoginNameSet) {
			for (User user : users)
				if (loginName.equals(user.getName())) {
					ownerIds.add(user.getTeacherid());
					break;
				}
		}
		return ownerIds;
	}

	/**
	 * 填充用户实体的剩余信息
	 * 
	 * @param users
	 * @param schId
	 * @param classId
	 */
	private void fillUserDto(User[] users, String schId, String classId) {
		if (ArrayUtils.isEmpty(users)) {
			return;
		}
		Unit unitDto = unitService.getUnit(schId);
		for (User user : users) {
			user.setId(UUIDUtils.newId());
			user.setType(User.USER_TYPE_COMMON);
			user.setUnitid(schId);
			// user.setDeptid(classId);
			user.setPassword(BaseConstant.PASSWORD_INIT);
			// user.setUnitintid(unitDto.getUnitintid());
			user.setRegion(unitDto.getRegion());
			user.setIsdeleted(false);
			user.setMark(User.USER_MARK_NORMAL);
		}
	}

	private String[] getStuIds(List<StudentUserDto> studentUserList) {
		if (studentUserList == null || studentUserList.isEmpty())
			return new String[0];

		List<String> idList = new ArrayList<String>();
		for (StudentUserDto stuUser : studentUserList)
			idList.add(stuUser.getId());

		return idList.toArray(new String[0]);

	}

	public void setUnitService(UnitService unitService) {
		this.unitService = unitService;
	}

	public StudentFamilyService getStudentFamilyService() {
		return studentFamilyService;
	}

	public void setStudentFamilyService(
			StudentFamilyService studentFamilyService) {
		this.studentFamilyService = studentFamilyService;
	}

	public String getClassId() {
		return classId;
	}

	public void setClassId(String classId) {
		this.classId = classId;
	}

	public List<StudentUserDto> getStudentUserList() {
		if (studentUserList == null)
			studentUserList = new ArrayList<StudentUserDto>();

		return studentUserList;
	}

	public void setStudentUserList(List<StudentUserDto> studentUserList) {
		this.studentUserList = studentUserList;
	}

	public Map<String, List<FamilyUser>> getFamilyUserMap() {
		if (familyUserMap == null)
			familyUserMap = new HashMap<String, List<FamilyUser>>();

		return familyUserMap;
	}

	public void setFamilyUserMap(Map<String, List<FamilyUser>> familyUserMap) {
		this.familyUserMap = familyUserMap;
	}

	public void setEtohSchoolId(String etohSchoolId) {
		this.etohSchoolId = etohSchoolId;
	}

	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	public String getEtohSchoolId() {
		return etohSchoolId;
	}

	public void setBaseUserService(BaseUserService baseUserService) {
		this.baseUserService = baseUserService;
	}

	public void setEisuStudentService(EisuStudentService eisuStudentService) {
		this.eisuStudentService = eisuStudentService;
	}

	public String getStudentId() {
		return studentId;
	}

	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}

	public boolean getSysAccount() {
		String value = systemIniService.getValue(SYSTEM_ACCOUNT);
		if ("0".equals(value)) {
			sysAccount = true;
		}
		return sysAccount;
	}

	public void setSysAccount(boolean sysAccount) {
		this.sysAccount = sysAccount;
	}

	public String getIdentityCard() {
		return identityCard;
	}

	public void setIdentityCard(String identityCard) {
		this.identityCard = identityCard;
	}

	public String getStudentIds() {
		return studentIds;
	}

	public void setStudentIds(String studentIds) {
		this.studentIds = studentIds;
	}

	public String getFamilyIds() {
		return familyIds;
	}

	public void setFamilyIds(String familyIds) {
		this.familyIds = familyIds;
	}

	public String getStudentLogin() {
		return studentLogin;
	}

	public void setStudentLogin(String studentLogin) {
		this.studentLogin = studentLogin;
	}

	public String getFamilyLogin() {
		return familyLogin;
	}

	public void setFamilyLogin(String familyLogin) {
		this.familyLogin = familyLogin;
	}

	public String getStudentRealName() {
		return studentRealName;
	}

	public void setStudentRealName(String studentRealName) {
		this.studentRealName = studentRealName;
	}

	public String getFamilyRealName() {
		return familyRealName;
	}

	public void setFamilyRealName(String familyRealName) {
		this.familyRealName = familyRealName;
	}

	public String getFieldErrorKey() {
		return fieldErrorKey;
	}

	public void setFieldErrorKey(String fieldErrorKey) {
		this.fieldErrorKey = fieldErrorKey;
	}

	public String getFieldErrorValue() {
		return fieldErrorValue;
	}

	public void setFieldErrorValue(String fieldErrorValue) {
		this.fieldErrorValue = fieldErrorValue;
	}

	// 取得当前用户名生成规则
	public String getStudentUserRule() {
		if (StringUtils.isBlank(studentUserRule)) {
			String val = systemIniService
					.getValue(User.SYSTEM_STUDENT_USERNAME);
			if (StringUtils.isBlank(val)) {
				studentUserRule = BasedataConstants.SYSTEM_STUDENT_USERNAME_1;
			} else {
				studentUserRule = val;
			}
		}
		return studentUserRule;
	}

	public void setMcodeService(McodeService mcodeService) {
		this.mcodeService = mcodeService;
	}
	

	


	/**
	 * 判断是否中职部署
	 * 
	 * @return
	 */
	public boolean isEisuSchool() {
		if (systemDeployService == null) {
			systemDeployService = (SystemDeployService) ContainerManager
					.getComponent("systemDeployService");
		}
		if (VersionType.EISU == systemDeployService.getVersionType()) {
			return true;
		}
		return false;
	}

}
