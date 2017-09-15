/* 
 * @(#)BaseConstant.java    Created on Nov 17, 2009
 * Copyright (c) 2009 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.eis.base.constant;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * base 主constant
 * 
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: Nov 17, 2009 2:09:43 PM $
 */
public final class BaseConstant {
    /**
     * 是否
     */
    public static final String STR_YES = "1";// 是
    public static final String STR_NO = "0";// 否
    public static final int INT_YES = 1;// 是
    public static final int INT_NO = 0;// 否

    /**
     * 标点符号
     */
    public static final String COMMA = ","; // 逗号
    public static final String COMMA_SPACE = ", "; // 逗号+空格，页面传值

	/**
	 * 初始化数据GUID 32个0组成的字符串
	 */
	public static final String ZERO_GUID = "00000000000000000000000000000000";
	
	public static final String SCH_ZERO_GUID = "00000000000000000000000000000001";

    /**
     * 公文和办公的消息、通知，短信接收对象选择范围（可多选，多选时用“,”号分隔）: 1所有单位、2本单位、3同级单位、4下级单位、5直接上级单位、6直属单位
     */
    public static final String RECEIVER_RANGE_ALL = "1";
    public static final String RECEIVER_RANGE_SELF = "2";
    public static final String RECEIVER_RANGE_SAME = "3";
    public static final String RECEIVER_RANGE_LOWER = "4";
    public static final String RECEIVER_RANGE_DIR_HIGHER = "5";
    public static final String RECEIVER_RANGE_DIR_UNDER = "6";

    /**
     * 所有单位的intid时，用此常量表示
     */
    public static final String ALL_UNIT_INTID = "ALL_UNIT_INTID";

    public static final String SEX_MALE = "1";
    public static final String SEX_FEMALE = "2";

	/*
	 * 密码相关
	 */
	public static final String PASSWORD_INIT = "12345678"; // 初始化密码
	public static final int PASSWORD_GENERIC_RULE_NULL = 0;// 如不输入密码,则为空
	public static final int PASSWORD_GENERIC_RULE_NAME = 1;// 如不输入密码,则为登录名
	public static final int PASSWORD_GENERIC_RULE_UNIONIZE = 2;// 如不输入密码,则为本单位默认密码
	public static final String PASSWORD_VIEWABLE = "************";
	public static final int TEACHER_PASSWORD_RULE_SYSTEM = 1;//教职工密码生成规则-系统默认
	public static final int TEACHER_PASSWORD_RULE_GENERIC = 3;//教职工密码生成规则-用户名后六位(不足六位，前面补0)
	public static final String TEACHER_PASSWORD_RULE_NOWVALUE = "2";
	public static final String TEACHER_USERNAME_IDCARD_START = "G";//教职工用户名前缀-由身份证号组成的用户名前缀是G
	public static final String TEACHER_USERNAME_TEMPCARD_START = "J";//教职工用户名前缀-由临时身份证号组成的用户名时前缀是J
	/**
	 * 教职工用户名生成规则开关
	 */
	public static final int TEACHER_USERNAME_RULE_SYSTEM = 1;//教职工用户名生成规则-系统默认
	
	
	// ----------------------------
	// 在Session的登陆信息 系统相关常量
	// ----------------------------
	public static final String SESSION_LOGININFO = "LOGININFO";
	public static final String SESSION_LOGINUSER = "LOGINUSER";
	public static final String SESSION_BACKGROUND_LOGININFO = "BACKGROUND_LOGININFO";
	public static final String SESSION_STUINFO = "STUINFO";
	public static final String SESSION_KGENROLL_STUINFO = "KGENROLLLSTUIFNO";
	public static final String SESSION_ENROLL_STUINFO = "ROLLLSTUIFNO";//小学  初中招生
	public static final String SESSION_LKRECRUIT_STUINFO = "LKRECRUITSTUIFNO";

    public static final String SYSTEM_SMARTUPLOAD_MAXFILESIZE = "SYSTEM.SMARTUPLOAD.MAXFILESIZE";// 附件大小
   
    public static final String SYSTEM_FAVORITE_SWITCH = "SYSTEM.FAVORITE.SWITCH"; // 是否启用收藏夹功能
    
    public static final String SYSTEM_USERPWD_MODIFY_SWITCH = "SYSTEM.USERPWD.MODIFY.SWITCH";// 是否允许修改密码

    public static final String SYSTEM_USERNAME_CASE_SENSITIVE_SWITCH = "SYSTEM.USERNAME.CASE.SENSITIVE.SWITCH"; // 用户名大小写是否敏感
    
    //成绩单是否开启其他比例、其他成绩
    public static final String ACHIEVEMENT_OTHER = "ACHIEVEMENT.OTHER";
    
    //是否显示院系的开关
    public static final String SWITCH_INSTITUTE = "SWITCH.INSTITUTE";
    
    public static final String VERIFY_CODE = "verifyCode";// 验证码
    
    public static final String VERIFY_CODE_SWITCH = "VERIFYCODE.SWITCH";//是否启用验证码
    
	public static final String SYSTEM_FEEDBACK_SWITCH = "SYSTEM.FEEDBACK.SWITCH";// 是否启用反馈
	
	//如果是作为第三方系统接入的话 就配置成不需要显示首页 否则配置成显示首页
	public static final String DEPLOY_AS_APP = "DEPLOY.AS.APP";// 作为app接入
	
	public static final String SYSTEM_SKINSET_SWITCH = "SYSTEM.SKINSET.SWITCH";// 是否启用换肤功能
	public static final String SYSTEM_LOGOUT_SWITCH = "SYSTEM.LOGOUT";// 是否显示退出按钮
	//是否开放学生维护个人信息
	public static final String STUDENT_EDITINFO = "STUDENT.EDITINFO";
	/**
	 * 系统部署学校
	 */
	public static final String SYSTEM_DEPLOY_SCHOOL = "SYSTEM.DEPLOY.SCHOOL";
	/**
	 * 首页title名称显示方式
	 */
	public static final String SHOW_TITLE_NAME_SWITH = "SHOW.TITLE.NAME.SWITH";
	
	/**
	 * 统一用户平台对接使用-6.0域名
	 */
	public static final String EIS6_DOMAIN = "EIS6.DOMAIN";
	
	/**
	 * 统一用户平台对接使用-5.0域名
	 */
	public static final String EIS5_DOMAIN = "EIS5.DOMAIN";
	
	/**
	 * 统一用户平台对接使用-正方认证域名
	 */
	public static final String ZFTYYHPT_DOMAIN = "ZFTYYHPT.DOMAIN";
	
	
    //通知公告是否需要审核
    public static final String BULLETIN_AUDIT = "BULLETIN.NEED.AUDIT";
    //报修是否需要打印，全部数据界面加载会比较卡，默认不加载打印
    public static final String REPAIRE_NEED_PRINT = "REPAIRE.NEED.PRINT";
    public static final String IM_TIME = "IM.TIME";
	public static final String MAINID = "mainId";
	
	/*
	 * 发消息是否使用标准版
	 */
	public static final String SENDNOTE_STANDARD = "SEND.NOTE.STANDARD";
	
	/**
	 * 微办公手机端版本号(每次修改微办公相关js或者css时修改版本号version_time)
	 */
	public static final String OA_APP_VERSION = "v5.0_20170615";
	/**
	 * 日常消息是否显示手机oa apk二维码
	 */
	public static final String NEEDQRCODE = "NEEDQRCODE";
	/**
	 * 消息是否推送微课
	 */
	public static final String NEED_PUSH_WK = "NEED_PUSH_WK";
	
	/**
	 * 消息推送微课相关参数
	 */
	public static final String WK_URL = "WK.URL";
	public static final String WK_APPID = "WK.APPID";
	public static final String WK_PUBLICID = "WK.PUBLICID";
	
	/**
	 * 消息推送微课相关参数(公众号)
	 */
	public static final String WK_PUSH_URL = "WK.PUSH.URL";
	public static final String WK_PUSH_APPID = "WK.PUSH.APPID";
	public static final String WK_PUSH_PUBLICID_OFFICEDOC = "WK.PUSH.PUBLICID.OFFICEDOC";
	public static final String WK_PUSH_PUBLICID_OFFICE = "WK.PUSH.PUBLICID.OFFICE";

	/**
	 * 成功或失败
	 */
	public static final String SUCCESS = "success";// 成功
	public static final String FAIL = "fail";// 失败
	/**
	 * 远程服务测试返回字符串
	 */
	public static final String TEST_STRING = "test";
	
	/** 学段 幼儿园 */
	public static final String SECTION_INFANT = "0";
	/** 学段 小学 */
	public static final String SECTION_PRIMARY = "1";
	/** 学段 初中 */
	public static final String SECTION_JUNIOR = "2";
	/** 学段 高中 */
	public static final String SECTION_HIGH_SCHOOL = "3";
	/** 学段 职业初中 */
	public static final String SECTION_JUNIOR_CAREER = "4";
	/** 学段 中职 */
	public static final String SECTION_SECONDARY_VOCATIONAL = "5";
	/** 学段 特教 */
	public static final String SECTION_SPECIAL_EDUCATION = "6";
	/** 学段 成人中学 */
	public static final String SECTION_ADULT_MIDDLE_SCHOOL = "7";
	/** 学段 工读学校 */
	public static final String SECTION_REFORM_SCHOOL = "8";
	/** 学段 大学专科 */
	public static final String SECTION_COLLEGE = "9";
	/** 学段 大学本科 */
	public static final String SECTION_UNIVERSITY = "10";
	/** 学段 研究生 */
	public static final String SECTION_GRADUATE = "11";
	/** 学段 其他 */
	public static final String SECTION_OTHER = "99";
	
	public static final Map<Integer, String> sectionMap;
	static {
		sectionMap = new HashMap<Integer, String>();
		sectionMap.put(1, SECTION_PRIMARY);
		sectionMap.put(2, SECTION_JUNIOR);
		sectionMap.put(3, SECTION_HIGH_SCHOOL);

    }
    
    public static final Map<String, String> sectionNameMap;
    static {
        sectionNameMap = new HashMap<String, String>();
        sectionNameMap.put(SECTION_PRIMARY, "小学");
        sectionNameMap.put(SECTION_JUNIOR, "初中");
        sectionNameMap.put(SECTION_HIGH_SCHOOL, "高中");
    }
    
    /**
     * 上级类型:学校
     */
    public static final int PARENT_SCHOOL = 1;

    /**
     * 上级类型:院系
     */
    public static final int PARENT_INSTITUTE = 2;

    /**
     * 上级类型:部门
     */
    public static final int PARENT_DEPT = 3;
    
    /**
     * 平台标识：教师平台
     */
    public static final int PLATFORM_TEACHER = 0;
    
    /**
     * 平台标识：学生平台
     */
    public static final int PLATFORM_STUPLATFORM = -1;

    /**
     * 平台标识：管理平台
     */
    public static final int PLATFORM_BACKGROUND = -2;
    
    /**
	 * 平台标志：家长平台
	 */
	public static final int PLATFORM_FAMPLATFORM = -3;
	/**
	 *  未入学
	 */
	public static final int LEAVESIGN_NOT_IN = 9; 
	/**
	 *  在校
	 */
	public static final int LEAVESIGN_IN = 0; 
	
	/** 民族*/
	public static final String MZ_HZ = "01";//汉族
	
	public static final String NCXX_A = "210";//农村学校
	
	public static final String NCXX_B = "220";//农村学校
	/**
	 * 已离校（休学、保存入学资格，统称为已离校,不再使用）
	 */
	public static final int LEAVESIGN_OUT = 1; 
	
	/**
	 *系统部署地区 杭州中策
	 */
	public static final String SYS_DEPLOY_SCHOOL_HZZC="hzzc";
	
	/**
	 *系统部署地区 宁海职高
	 */
	public static final String SYS_DEPLOY_SCHOOL_NHZG="nhzg";
	
	/**
	 *系统部署地区 宁波职校
	 */
	public static final String SYS_DEPLOY_SCHOOL_NBZX="nbzx";
	
	/**
	 *系统部署地区镇海职高
	 */
	public static final String SYS_DEPLOY_SCHOOL_ZHZG="zhzg";
	
	/**
	 *系统部署地区广东东莞
	 */
	public static final String SYS_DEPLOY_SCHOOL_GDDG="gddg";
	
	/**
	 *系统部署地区西安
	 */
	public static final String SYS_DEPLOY_SCHOOL_XIAN="xian";
	
	/**
	 *系统部署地区四川德阳
	 */
	public static final String SYS_DEPLOY_SCHOOL_SCDY="scdy";
	/**
	 *系统部署地区四川德阳旌阳区
	 */
	public static final String SYS_DEPLOY_SCHOOL_SCDYJY="scdyjy";
	/**
	 *系统部署地区四川广元市
	 */
	public static final String SYS_DEPLOY_SCHOOL_SCGY="scgy";
	/**
	 *系统部署地区四川南充市嘉陵区
	 */
	public static final String SYS_DEPLOY_SCHOOL_SCNCJL="scncjl";
	/**
	 *系统部署地区四川南充市顺庆区
	 */
	public static final String SYS_DEPLOY_SCHOOL_SCNCSQ="scncsq";
	/**
	 *系统部署地区四川南充市高坪区
	 */
	public static final String SYS_DEPLOY_SCHOOL_SCNCGP="scncgp";
	/**
	 *系统部署地区 浙江省厅校安
	 */
	public static final String SYS_DEPLOY_SCHOOL_ZJSTXA="zjstxa";
	
	/**
	 *系统部署地区 山西人人通
	 */
	public static final String SYS_DEPLOY_SCHOOL_SXRRT="sxrrt";
	
	/**
	 *系统部署地区 新疆教育厅
	 */
	public static final String SYS_DEPLOY_SCHOOL_XINJIANG="xinjiang";
	
	/**
	 *系统部署地区 邯郸教育局
	 */
	public static final String SYS_DEPLOY_SCHOOL_HDJY="hdjy";
	
	/**
	 *系统部署地区 cixi
	 */
	public static final String SYS_DEPLOY_SCHOOL_CIXI="cixi";
	
	/**
	 *系统部署地区 广东茂名教育局
	 */
	public static final String SYS_DEPLOY_SCHOOL_GDMMJY="gdmmjy";
	/**
	 *系统部署地区 杭州滨江
	 */
	public static final String SYS_DEPLOY_SCHOOL_HZBJ="hzbj";
	/**
	 *系统部署地区 四川自贡
	 */
	public static final String SYS_DEPLOY_SCHOOL_SCZG="sczg";
	/**
	 *系统部署地区 四川青神
	 */
	public static final String SYS_DEPLOY_SCHOOL_SCQS="scqs";
	/**
	 *系统部署地区 四川南充
	 */
	public static final String SYS_DEPLOY_SCHOOL_NC="nc";
	/**
	 *系统部署地区 吉林
	 */
	public static final String SYS_DEPLOY_SCHOOL_JILIN="jilin";
	/**
	 *系统部署地区 吉安
	 */
	public static final String SYS_DEPLOY_SCHOOL_JIAN="jian";
	/**
	 * 湖州交通技师学院
	 */
	public static final String SYS_DEPLOY_SCHOOL_HZJTJSXY="hzjtjsxy";
	/**
	 * 黄山智慧校园平台
	 */
	public static final String SYS_DEPLOY_SCHOOL_HSEDU="hsedu";
	
	/**
	 *系统部署地区 富春三中
	 */
	public static final String SYS_DEPLOY_SCHOOL_FCSZ="fcsz";
	
	/**
	 *系统部署地区 枣庄薛城区教育局
	 */
	public static final String SYS_DEPLOY_SCHOOL_ZZXCJY="zzxcjy";
	
	/**
	 *系统部署地区 平度教育局
	 */
	public static final String SYS_DEPLOY_SCHOOL_PINGDU="pingdu";
	
	/**
	 * 系统部署地区 山东平邑
	 */
	public static final String SYS_DEPLOY_SCHOOL_PINGYI="pingyi";
	
	/**
	 * 系统部署地区 四川崇州
	 */
	public static final String SYS_DEPLOY_SCHOOL_SCCZ="sccz";
	
	/** 校安Cookie标识*/
	public static final String SCHSECURITY_COOKIE_ID = "schsecurity_app_login_user_id";
	//消息来源类型
	public static final Integer MSG_TYPE_NOTE = 1;//消息
	public static final Integer MSG_TYPE_ARCHIVE = 2;//公文
	public static final Integer MSG_TYPE_CAR = 3;//车辆
	public static final Integer MSG_TYPE_REPAIRE = 4;//报修
	public static final Integer MSG_TYPE_ATTENDANCE = 5;//考勤
	public static final Integer MSG_TYPE_MEETING = 6;//会议
	public static final Integer MSG_TYPE_EDUADM = 11;//教务
	public static final Integer MSG_TYPE_BULLETIN=13;//通知公告
	
	public static final Integer MSG_TYPE_LEAVE = 21;//请假
	public static final Integer MSG_TYPE_GO_OUT = 22;//外出
	public static final Integer MSG_TYPE_EVECTION = 23;//出差
	public static final Integer MSG_TYPE_EXPENSE = 24;//报销
	public static final Integer MSG_TYPE_GOODS = 25;//物品管理
	public static final Integer MSG_TYPE_WORK_REPORT = 26;//工作汇报
	public static final Integer MSG_TYPE_ATTENDLECTURE = 27;//听课
	public static final Integer MSG_TYPE_SEAL = 28;//用印
	public static final Integer MSG_TYPE_JTGOOUT = 29;//集体外出管理
	
	public static final Integer MSG_TYPE_OFFICE = 99;//办公（除消息、公文的所有）
	
	//微课推送类型--目前：公文、报修、请假、外出、出差、报销、物品管理、工作汇报
	public static final Set<Integer> WK_PUSH_TYPES_SET = new HashSet<Integer>();
	static{
		WK_PUSH_TYPES_SET.add(MSG_TYPE_ARCHIVE);
		WK_PUSH_TYPES_SET.add(MSG_TYPE_REPAIRE);
		WK_PUSH_TYPES_SET.add(MSG_TYPE_LEAVE);
		WK_PUSH_TYPES_SET.add(MSG_TYPE_GO_OUT);
		WK_PUSH_TYPES_SET.add(MSG_TYPE_EVECTION);
		WK_PUSH_TYPES_SET.add(MSG_TYPE_EXPENSE);
		WK_PUSH_TYPES_SET.add(MSG_TYPE_GOODS);
		WK_PUSH_TYPES_SET.add(MSG_TYPE_WORK_REPORT);
		WK_PUSH_TYPES_SET.add(MSG_TYPE_ATTENDLECTURE);
	}
	
	
	//转换相关参数
	// 'SYSTEM.CONVERTOR.TYPE', '默认使用的转换服务'
	// 'SYSTEM.CONVERTOR.WINDOW.URL', 'WINDOW转换服务地址
	// 'SYSTEM.CONVERTOR.WINDOW.SERVER', 'WINDOW转换服务地址SERVERID
	public static final String SYSTEM_CONVERTOR_TYPE = "SYSTEM.CONVERTOR.TYPE";
	public static final String SYSTEM_CONVERTOR_WINDOW_URL = "SYSTEM.CONVERTOR.WINDOW.URL";
	public static final String SYSTEM_CONVERTOR_WINDOW_SERVER = "SYSTEM.CONVERTOR.WINDOW.SERVER";
	public static final String SYSTEM_CONVERTOR_EIS_URL = "SYSTEM.CONVERTOR.EIS.URL";
}

