package net.zdsoft.eis.system.constant;

import net.zdsoft.eis.base.constant.BaseConstant;

public class SystemConstant {
	public static final String TEACHER_IMPORT_EDU = "teacher_import_edu"; // 教育局端教职工导入
	public static final String TEACHER_IMPORT_SCH = "teacher_import_sch"; // 学校端教职工导入

	public static final String SMS_CONFIG_FILE = "conclient.properties";
	// ----通信服务属性名称
	public static final String SMS_SERVER = "server";
	public static final String SMS_PORT = "port";
	public static final String SMS_WORKINGSERVERNAME = "workingServerName";
	public static final String SMS_LOCALNAME = "localName";
	public static final String SMS_LOCALPWD = "localPwd";
	public static final String SMS_CLIENT_ID = "clientId";
	public static final String SMS_LOGINNAME_PLATFORM="loginName_platform";
	public static final String SMS_LOGINNAME_REGION="loginName_region";
	
    public static final String DEFAULT_EDU_GUID = BaseConstant.ZERO_GUID;// 初始化数据教育局GUID
    public static final String DEFAULT_SCHOOL_GUID = BaseConstant.SCH_ZERO_GUID;// 初始化数据学校GUID
    
    public static final String SYSTEM_MAXLOG = "SYSTEM.MAXLOG";// 日志提醒最大条数
    
    /**
     * 系统首页引导
     */
    public static final int SYSTEM_USER_GUIDE_HOMEPAGE=-1;
    
    /**
     * 系统修改日志引导
     */
    public static final int SYSTEM_USER_GUIDE_CHANGE_LOG=-2;

}
