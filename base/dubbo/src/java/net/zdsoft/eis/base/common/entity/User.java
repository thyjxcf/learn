package net.zdsoft.eis.base.common.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.annotation.JSONField;

import net.zdsoft.eis.base.common.service.SystemIniService;
import net.zdsoft.eis.base.constant.BaseConstant;
import net.zdsoft.eis.base.util.SUtils;
import net.zdsoft.eis.frame.client.BaseEntity;
import net.zdsoft.keel.util.Pagination;
import net.zdsoft.keelcnet.config.ContainerManager;
import net.zdsoft.leadin.util.PWD;

public class User extends BaseEntity {

	/**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = 9043875958860254933L;

	public static final int TYPE_TOPADMIN = 0;// 用户类型-顶级单位用户
	public static final int TYPE_ADMIN = 1;// 用户类型-下级单位用户
	public static final int TYPE_NORMAL = 2;// 用户类型-个人用户

	public static final int NAME_MAX_LENGTH = 20;
	public static final int NAME_MIN_LENGTH = 3;
	public static final int REALNAME_LENGTH = 150;// 真实名称长度
	public static final int PASSWORD_MAX_LENGTH = 20;// 密码最大长度
	public static final int PASSWORD_MIN_LENGTH = 4;// 密码最小长度

//	public static final String NAME_EXPRESSION = "[a-zA-Z0-9_]{"
//			+ NAME_MIN_LENGTH + "," + NAME_MAX_LENGTH + "}"; // 用户名验证，正则表达式
//	public static final String NAME_EXPRESSION_NUMBER = "[0-9]{"
//		+ NAME_MIN_LENGTH + "," + NAME_MAX_LENGTH + "}"; // 用户名验证，正则表达式
//	public static final String PASSWORD_EXPRESSION = "[a-zA-Z0-9_]{"
//			+ PASSWORD_MIN_LENGTH + "," + PASSWORD_MAX_LENGTH + "}"; // 密码验证，正则表达式

//	public static final String NAME_ALERT = "账号必须是" + NAME_MIN_LENGTH + "-"
//			+ NAME_MAX_LENGTH + "个英文(A-Z，a-z)或数字(0-9)及下划线。(暂不支持纯数字)"; // 用户名称输入格式提示
//	public static final String NAME_ALERT_NUM = "账号必须是" + NAME_MIN_LENGTH + "-"
//	+ NAME_MAX_LENGTH + "个英文(A-Z，a-z)或数字(0-9)及下划线。(支持纯数字)"; // 用户名称输入格式提示
	
//	public static final String PASSWORD_ALERT = "密码必须是" + PASSWORD_MIN_LENGTH
//			+ "-" + PASSWORD_MAX_LENGTH + "位的英文(A-Z，a-z)或数字(0-9)及下划线。"; // 密码输入格式提示
	
	/** 用户名前缀*/
	public static final String SYSTEM_USERNAME_PREFIX = "SYSTEM.USERNAME.PREFIX";//用户名前缀
	/** 用户名生成规则 */
	public static final String SYSTEM_USERNAME = "SYSTEM.USERNAME";//教职工用户名生成规则
	public static final String SYSTEM_STUDENT_USERNAME = "SYSTEM.STUDENT.USERNAME";//学生用户名生成规则
	/** 用户名生成规则-系统默认规则 */
	public static final String SYSTEM_USERNAME_1 = "1";
	/** 用户名生成规则-杭州英特用规则，姓名拼音 */
	public static final String SYSTEM_USERNAME_2 = "2";
	/** 学生家长用户名生成规则-前缀+学号*/
	public static final String SYSTEM_USERNAME_3 = "3";
	/** 学生家长用户名生成规则-学生 x+父亲手机号， 父亲 f+自己手机号, 母亲 m+自己手机号*/
	public static final String SYSTEM_USERNAME_4 = "4";
	/** 学生家长用户名生成规则-学生 学生所在班级编号+两位流水号，父亲 f+学生用户名，母亲：m+学生用户名*/
	public static final String SYSTEM_USERNAME_5 = "5";
	/** 学生 G+身份证号（无身份证号 J+临时身份证号）， 父亲 F+学生身份证号， 母亲 M+学生身份证号， 监护人  H+学生身份证号*/
	public static final String SYSTEM_USERNAME_6 = "6";
	/** 学生 s+身份证号， 父亲 f+学生身份证号， 母亲 m+学生身份证号， 监护人  g+学生身份证号*/
	public static final String SYSTEM_USERNAME_7 = "7";
	/** 学生 s+学籍号， 父亲 f+学生学籍号， 母亲 m+学生学籍号， 监护人及其他  g+学生学籍号*/
	public static final String SYSTEM_USERNAME_8 = "8";

	/** 教职工用户名采用教育部标准(G+身份证或J+临时身份证)*/
	public static final String SYSTEM_TEACHER_USERNAME_3 = "3";
	
	/** 学生家长密码生成规则 */
	public static final String SYSTEM_STUDENT_PASSWORD = "SYSTEM.STUDENT.PASSWORD";//学生密码生成规则
	
	public static final String SYSTEM_TEACHER_USERNAME = "SYSTEM.TEACHER.USERNAME";//系统管理-教职工维护-教职工用户名规则开关
	public static final String SYSTEM_TEACHER_PASSWORD = "SYSTEM.TEACHER.PASSWORD";//系统管理-教职工维护-教职工密码规则开关
	
	
	/**
	 * 用户密码初始化是否同步修改其他账号密码
	 */
	public static final String SYSTEM_PASSWORD_INIT_SYNCHRONOUS = "SYSTEM.PASSWORD.INIT.SYNCHRONOUS";//密码初始化是否同步修改手机号相同的其他用户
	
	/** 用户名规则 */
	public static final String SYSTEM_NAME_EXPRESSION = "SYSTEM.NAME.EXPRESSION";
	/** 用户名错误提示消息 */
	public static final String SYSTEM_NAME_ALERT = "SYSTEM.NAME.ALERT";
	
	/** 密码规则 */
	public static final String SYSTEM_PASSWORD_EXPRESSION = "SYSTEM.PASSWORD.EXPRESSION";
	/** 密码错误提示消息 */
	public static final String SYSTEM_PASSWORD_ALERT = "SYSTEM.PASSWORD.ALERT";
	/** 密码强度 */
	public static final String SYSTEM_PASSWORD_STRONG = "SYSTEM.PASSWORD.STRONG";
	
	/** 学生家长密码规则-默认密码12345678*/
	public static final String SYSTEM_STUDENT_PASSWORD_1 = "1";
	/** 学生家长密码规则-学生身份证号后六位，为空或不足六位补0*/
	public static final String SYSTEM_STUDENT_PASSWORD_2 = "2";
	/** 学生家长密码规则-用户名后6位（如果不足6位，在前面补0）*/
	public static final String SYSTEM_STUDENT_PASSWORD_3 = "3";
	/** 学生家长密码规则-随机6位数字*/
	public static final String SYSTEM_STUDENT_PASSWORD_4 = "4";
	
	public static final int STUDENT_LOGIN = 1; // 学生登陆
	public static final int TEACHER_LOGIN = 2; // 教师登陆
	public static final int FAMILY_LOGIN = 3; // 家长登陆
	public static final int TEACHER_EDU_LOGIN = 4; // 教育局职工登陆（运营平台专用，学籍中还是标志为2，和unitclass配合可以知道是否为教育局职工）
	public static final int OTHER_LOGIN = 9; // 其他类型用户登陆

	@JSONField(name="username")
	private String name;
	private String password;
	@JSONField(name="userState")
	private Integer mark;// 0-未审核 1-正常 2-锁定 3-离职锁定
	@JSONField(name="userType")
	private Integer type;// 0-顶级单位用户 1-下级单位用户 2-个人用户
	@JSONField(name="realName")
	private String realname;
	@JSONField(name="ownerId")
	private String teacherid;// 关联教职工guid
	private String unitid;// 所属单位
	@JSONField(name="deptId")
	private String deptid;// 所属部门
	private String email;
	@JSONField(name="displayOrder")
	private Long orderid;
	@JSONField(name="regionCode")
	private String region;// 行政区划
	private String etohSchoolId;// 家校互联id

	private Integer ownerType;
	private Long sequence; // IM专用
	private String accountId; // Passport Id

	private String chargeNumber;
	private Integer chargeNumberType;
	
	private int orderStatus;
    private String nickName; 
    
	// ==============================辅助字段==========================================
	private String[] arrayIds;
	private String inteName;// 真实姓名

	private String checked;
	private Integer role;// 用户角色
	private String unitName;

	private String deptName;// 所属部门名称
	private String confirmPassword;
	private String markName;

	// 扩展信息
	private String sex;
	private String homepage;
	private String homePhone;
	private String officePhone;
	private String mobile;
	private String msn;
	private String qq;
	private Date birthday;
	
	private String extraId;//学生用户存放学号,用于passport 
	
	private String instituteId;// 院系id，eisu使用
	
	private String identitycard;//密码规则 取身份证后6位
	private String className;//
	
	//公文使用 同义词同步
	private String officeTel;
	private String mobilePhone;
	
	//通讯录头像
	private UserSet userSet;
	private String dutyNames;//所属职务
	
	private String pinyinAll;//拼音（包括简拼，全拼，混拼），通讯录快速搜索使用
	
	private String groupid;//组id 通讯录调用缓存使用 实际使用中一个人对应多个groupid本字段无用
	
	
	//--------手机短号 2016-10-12--------------
	private String mobileCornet;

	/**
	 * 顶级单位管理员
	 */
	public static final int USER_TYPE_TOPADMIN = 0;

	/**
	 * 下级单位管理员
	 */
	public static final int USER_TYPE_SUBADMIN = 1;

	/**
	 * 普通用户
	 */
	public static final int USER_TYPE_COMMON = 2;

	/**
	 * 用户状态-未审核
	 */
	public static final int USER_MARK_NOT_APPLY = 0;

	/**
	 * 用户状态-正常
	 */
	public static final int USER_MARK_NORMAL = 1;

	/**
	 * 用户状态-锁定
	 */
	public static final int USER_MARK_LOCK = 2;

	/**
	 * 用户状态-离职锁定
	 */
	public static final int USER_MARK_OUT = 3;

	private static SystemIniService systemIniService;
	
	public static List<User> dt(String data) {
		List<User> ts = SUtils.dt(data, new TypeReference<List<User>>() {
		});
		if (ts == null)
			ts = new ArrayList<User>();
		
		return ts;

	}

	public static List<User> dt(String data, Pagination page) {
		JSONObject json = JSONObject.parseObject(data);
		List<User> ts = SUtils.dt(json.getString("data"), new TypeReference<List<User>>() {
		});
		if (ts == null)
			ts = new ArrayList<User>();
		if (json.containsKey("count"))
			page.setMaxRowCount(json.getInteger("count"));
		
		return ts;

	}

	public static User dc(String data) {
		User user = SUtils.dc(data, User.class);
		return user;
	}

	
	
	/**
     * 用户名是否不区分大小写
     * 
     * @return true 不区分，false 区分
     */
    public static boolean isUsernameNotCaseSensitive() {
        if (null == systemIniService) {
            systemIniService = (SystemIniService) ContainerManager.getComponent("systemIniService");
        }
        return !(systemIniService.getBooleanValue(BaseConstant.SYSTEM_USERNAME_CASE_SENSITIVE_SWITCH));
    }
	
	// 导入公共框架中专用，2009-10-10 hexq
	public String getGuid() {
		return getId();
	}

	/**
	 * 为了兼容新的导入框架而引入的方法，在2.5中是无用的
	 * 
	 * @Deprecated
	 */
	public Long getIntId() {
		return Long.valueOf(0);
	}

	// Constructors

	/** default constructor */
	public User() {
	}

	// Property accessors

	public String getName() {
	    if (isUsernameNotCaseSensitive()) {
            if (null != this.name) {
                this.name = this.name.toLowerCase();
            }
        }
		return this.name;
	}

	public void setName(String name) {
	    if (isUsernameNotCaseSensitive()) {
            if (null != name) {
                name = name.toLowerCase();
            }
        }
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * 如果有密码的话，返回的是解码之后的明码
	 *
	 *@author "yangk"
	 * Sep 8, 2010 3:17:53 PM
	 * @return
	 */
	public String findClearPassword() {
		if (StringUtils.isNotBlank(this.password)) {
			return (PWD.decode(this.password));
		}
		return this.password;
	}

	/**
	 * 返回加密后的密码
	 *
	 *@author "yangk"
	 * Oct 22, 2010 1:53:37 PM
	 * @return
	 */
	public String findEncodePassword() {
		//如果password有值且长度小于18则进行加密操作
		if (StringUtils.isNotBlank(this.password)
				&& this.password.length() <= 18) {
			PWD p = new PWD(this.password);
			return p.encode();
		}
		return this.password;
	}

	public Integer getMark() {
		return this.mark;
	}

	public void setMark(Integer mark) {
		this.mark = mark;
	}

	public Integer getType() {
		return this.type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getRealname() {
		return this.realname;
	}

	public void setRealname(String realname) {
		this.realname = realname;
	}

	public String getTeacherid() {
		return teacherid;
	}

	public void setTeacherid(String teacherid) {
		this.teacherid = teacherid;
	}

	public String getUnitid() {
		return this.unitid;
	}

	public void setUnitid(String unitid) {
		this.unitid = unitid;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	// public Date getCreatedate() {
	// return this.createdate;
	// }
	//
	// public void setCreatedate(Date createdate) {
	// this.createdate = createdate;
	// }
	//
	// public Integer getIsdeleted() {
	// return this.isdeleted;
	// }
	//
	// public void setIsdeleted(Integer isdeleted) {
	// this.isdeleted = isdeleted;
	// }

	public Long getOrderid() {
		return this.orderid;
	}

	public void setOrderid(Long orderid) {
		this.orderid = orderid;
	}

	public String[] getArrayIds() {
		return this.arrayIds;
	}

	public void setArrayIds(String[] arrayIds) {
		this.arrayIds = arrayIds;
	}

	public String getInteName() {
		return inteName;
	}

	public void setInteName(String inteName) {
		this.inteName = inteName;
	}

	public String getChecked() {
		return checked;
	}

	public void setChecked(String checked) {
		this.checked = checked;
	}

	public Integer getRole() {
		return role;
	}

	public void setRole(Integer role) {
		this.role = role;
	}

	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	public String getMarkName() {
		return markName;
	}

	public void setMarkName(String markName) {
		this.markName = markName;
	}

	/**
	 * 默认是教师
	 *
	 *@author "yangk"
	 * Aug 23, 2010 4:38:29 PM
	 * @return
	 */
	public Integer getOwnerType() {
		if (null == ownerType) {
			setOwnerType(TEACHER_LOGIN);
		}
		return ownerType;
	}

	public void setOwnerType(Integer ownerType) {
		this.ownerType = ownerType;
	}

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	public Long getSequence() {
		return sequence;
	}

	public void setSequence(Long sequence) {
		this.sequence = sequence;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getHomepage() {
		return homepage;
	}

	public void setHomepage(String homepage) {
		this.homepage = homepage;
	}

	/**不再使用，请用 getMobilePhone代替 
     * 
     * @deprecated
     * @param mobile
     */
	public String getMobile() {
	    if(StringUtils.isBlank(mobile))
	        mobile = getMobilePhone();
		return mobile;
	}

	/**不再使用，请用 setMobilePhone代替 
	 * 
	 * @deprecated
	 * @param mobile
	 */
	public void setMobile(String mobile) {
	    if(StringUtils.isBlank(getMobilePhone()))
            setMobilePhone(mobile);
		this.mobile = mobile;
	}

	public String getMsn() {
		return msn;
	}

	public void setMsn(String msn) {
		this.msn = msn;
	}

	public String getQq() {
		return qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public String getOfficePhone() {
		return officePhone;
	}

	public void setOfficePhone(String officePhone) {
		this.officePhone = officePhone;
	}

	public String getHomePhone() {
		return homePhone;
	}

	public void setHomePhone(String homePhone) {
		this.homePhone = homePhone;
	}

	public String getChargeNumber() {
		return chargeNumber;
	}

	public void setChargeNumber(String chargeNumber) {
		this.chargeNumber = chargeNumber;
	}

	public Integer getChargeNumberType() {
		return chargeNumberType;
	}

	public void setChargeNumberType(Integer chargeNumberType) {
		this.chargeNumberType = chargeNumberType;
	}

	public String getDeptid() {
		return deptid;
	}

	public void setDeptid(String deptid) {
		this.deptid = deptid;
	}

	public String getEtohSchoolId() {
		return etohSchoolId;
	}

	public void setEtohSchoolId(String etohSchoolId) {
		this.etohSchoolId = etohSchoolId;
	}

	public int getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(int orderStatus) {
		this.orderStatus = orderStatus;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

    public String getInstituteId() {
        return instituteId;
    }

    public void setInstituteId(String instituteId) {
        this.instituteId = instituteId;
    }

	public String getExtraId() {
		return extraId;
	}

	public void setExtraId(String extraId) {
		this.extraId = extraId;
	}

public String getIdentitycard() {
		return identitycard;
	}

	public void setIdentitycard(String identitycard) {
		this.identitycard = identitycard;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getOfficeTel() {
		return officeTel;
	}

	public void setOfficeTel(String officeTel) {
		this.officeTel = officeTel;
	}

	public String getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	public UserSet getUserSet() {
		return userSet;
	}

	public void setUserSet(UserSet userSet) {
		this.userSet = userSet;
	}

	public String getDutyNames() {
		return dutyNames;
	}

	public void setDutyNames(String dutyNames) {
		this.dutyNames = dutyNames;
	}

	public String getPinyinAll() {
		return pinyinAll;
	}

	public void setPinyinAll(String pinyinAll) {
		this.pinyinAll = pinyinAll;
	}

	public String getGroupid() {
		return groupid;
	}

	public void setGroupid(String groupid) {
		this.groupid = groupid;
	}

	public String getMobileCornet() {
		return mobileCornet;
	}

	public void setMobileCornet(String mobileCornet) {
		this.mobileCornet = mobileCornet;
	}
	
}
