/* 
 * @(#)BasedataConstant.java    Created on Oct 18, 2010
 * Copyright (c) 2010 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.eis.base.data;


/**
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: Oct 18, 2010 10:22:27 AM $
 */
public class BasedataConstants {

    public static final String FPF_USER_REGISTER_ACTIVE = "SYSTEM.USER.REGISTER.ACTIVE";// 注册新用户激活选项
    public static final int FPF_USER_REGISTER_ACTIVE_IMM = 3;// 新注册用户，立即激活
    public static final int FPF_USER_REGISTER_ACTIVE_EMAIL = 2;// 新注册用户，邮件激活
    public static final int FPF_USER_REGISTER_ACTIVE_ADMIN = 1;// 新注册用户，由管理员激活
    
    public static final String UNIT_SYSTEM_VERSION = "SYSTEM.UNIT.SYSTEM.VERSION";// 单位使用当前产品版本
    
    public static final Integer SYSTEMINI_VALUE_MAXLENGTH = 255;// 系统设置表中个选项取值的最大长度
    
    public static final String UNIT_MAINTENANCE_SWITCH = "SYSTEM.UNIT.MAINTENANCE.SWITCH";// 是否允许维护单位信息
    
    public static final String MEETEXAM_SYSTEM_SWITCH = "SYSTEM.MEETEXAM.SWITCH"; // 是否启用会考系统
    
    public static final String STUDENT_FAMILY_ACCOUNT_SWITCH = "SYSTEM.STUDENT.FAMILY.ACCOUNT.SWITCH"; // 是否允许开通学生和家长账号

    /**
     * 职工在职标记微代码
     */
    public static final String EMPLOYEE_DIMISSION = "01";		// 离休
    public static final String EMPLOYEE_RETIRE = "02";			// 退休
    public static final String EMPLOYEE_STELLENBOSCH = "03";	// 调离
    public static final String EMPLOYEE_PLURALITY = "10";		// 兼职
    public static final String EMPLOYEE_INCUMBENCY = "11";		// 在职
    public static final String EMPLOYEE_PROBATION = "12";		// 试用
    public static final String EMPLOYEE_BORROW = "13";			// 借用
    public static final String EMPLOYEE_TEMP = "14";			// 临时
    public static final String EMPLOYEE_DEAD = "21";			// 死亡
    public static final String EMPLOYEE_REMOVE = "22";			// 除名
    public static final String EMPLOYEE_RESIGNATION = "23";		// 辞职
    public static final String EMPLOYEE_OTHER_DIMISSION = "41"; // 其他离职
    
    //中职学生家长用户名规则 用这里的定义的 User.java定义的为城域的学生家长规则 2014-11-11 add by like
  	/** 学生家长用户名生成规则-（系统默认规则） 学生 身份证号+s， 家长 p+学校编号+6位随机数 */
  	public static final String SYSTEM_STUDENT_USERNAME_1 = "1";
  	/** 学生家长用户名生成规则-学生 s+学号， 家长 p+学校编号+6位随机数 */
  	public static final String SYSTEM_STUDENT_USERNAME_2 = "2";
  	/** 镇海定制---学生家长用户名生成规则-学生 学号， 家长 F、 M+学号*/
  	public static final String SYSTEM_STUDENT_USERNAME_ZH_3 = "3";
  	
  	//家长与学生关系
  	/**
  	 * 父亲
  	 */
  	public static final String SYSTEM_FAMILY_TYPE_01="01";
  	/**
  	 * 母亲
  	 */
  	public static final String SYSTEM_FAMILY_TYPE_02="02";
  	
    
    public static final String SCHOOL_TYPE_KG = "111";//幼儿园
    public static final String SCHOOL_TYPE_KG_MINORITY = "112";//独立设置的少数民族幼儿园
    public static final String SCHOOL_TYPE_ATTACHED_KG = "119";//附设幼儿园
    public static final String UNIT_USE_TYPE_EDU = "01";//单位使用类型-教育局
    
    public static final String STUDENT_IMPORT_EDU = "student_import_edu"; //教育局端学生信息导入
    public static final String STUDENT_IMPORT_SCH = "student_import_sch"; //学校端学生信息导入

    // 在校离校状态
 	public static final int LEAVESIGN_NOT_IN = 9; // 未入学
 	public static final int LEAVESIGN_IN = 0; // 在校
 	public static final int LEAVESIGN_OUT = 1; // 已离校
 	
 	// 新生状态
 	public static final int ISFRESHMAN_NOT = 9; // 不是新生，老生
 	
 	// 学生状态
 	public static final String DJ = "40";// 默认登记状态
 	
 	/**
 	 * 邮箱正则
 	 */
 	public static final String EMAIL_REGEX = "[\\w-.]+@\\w+\\.\\w{2,4}(\\.{0,1}\\w{2}){0,1}";
}
