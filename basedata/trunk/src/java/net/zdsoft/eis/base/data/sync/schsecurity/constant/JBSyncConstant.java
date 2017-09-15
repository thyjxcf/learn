package net.zdsoft.eis.base.data.sync.schsecurity.constant;

import java.util.HashMap;

import net.zdsoft.eis.base.common.entity.User;

public class JBSyncConstant {

	/**系统参数-是否开启同一用户平台基础数据同步*/
	public static final String JB_SYNC_KG = "JB.SYNC.KG";
	
	/**系统参数-上一次同步用户表的时间*/
	public static final String JB_SYNC_TIME_USER = "JB.SYNC.TIME.USER";
	
	/**系统参数-上一次同步机构表的时间*/
	public static final String JB_SYNC_TIME_UNIT = "JB.SYNC.TIME.UNIT";
	
	/**系统参数-上一次同步学校表的时间*/
	public static final String JB_SYNC_TIME_SCHOOL = "JB.SYNC.TIME.SCHOOL";
	
	
	/**
	 * 数据变化类型-新增
	 */
	public static final String SJBHLX_INSERT = "I";
	
	/**
	 * 数据变化类型-修改
	 */
	public static final String SJBHLX_UPDATE = "U";
	
	
	/**
	 * 同步结果-未同步
	 */
	public static final String SYNC_RESULT_INIT = "0";
	
	/**
	 * 同步结果-同步成功
	 */
	public static final String SYNC_RESULT_SUCCESS = "1";
	
	/**
	 * 同步结果-同步失败
	 */
	public static final String SYNC_RESULT_FAIL = "2";
	
	/**
	 * 同步结果-同步关闭（后续版本已处理）
	 */
	public static final String SYNC_RESULT_CLOSE = "3";
	
	
	/**
	 * 用户属性-机构管理员
	 */
	public static final String YHSX_JGGLY = "1";
	
	/**
	 * 用户属性-机构教职工
	 */
	public static final String YHSX_JGJZG = "21";
	
	/**
	 * 用户属性-学校教职工
	 */
	public static final String YHSX_XXJZG = "23";
	
	/**
	 * 用户属性-学校管理员
	 */
	public static final String YHSX_XXGLY = "3";
	
	
	/**
	 * 机构类别码-省教育厅
	 */
	public static final String JGLBM_SJYT = "611";
	
	/**
	 * 机构类别码-设区市教育局
	 */
	public static final String JGLBM_SJYJ = "621";
	
	/**
	 * 机构类别码-县（市、区）教育局
	 */
	public static final String JGLBM_XJYJ = "631";
	
	/**
	 * 校安管理员(教育局)角色码
	 */
	public static final String ROLE_IDENTITY = "schsecurity";
	
	/**
	 * 把子系统对应的模块id缓存在redis中
	 */
	public static final String SUBSYSTEM_MODULE_CLASS = "subsystem_module_class";
	
	/**
	 * 统一用户平台机构有效标识-正常
	 */
	public static final String ZF_JGYXBS_ZC = "1";
	
	/**
	 * 统一用户平台机构有效标识-待审核
	 */
	public static final String ZF_JGYXBS_DSH = "2";
	
	/**
	 * 统一用户平台机构有效标识-已撤销
	 */
	public static final String ZF_JGYXBS_CX = "3";
	
	/**
	 * 统一用户平台机构有效标识-已删除
	 */
	public static final String ZF_JGYXBS_SC = "4";
	
	/**
	 * 统一用户平台机构有效标识-已合并
	 */
	public static final String ZF_JGYXBS_HB = "5";
	
	
	/**
	 * 统一用户平台账号状态-正常
	 */
	public static final String ZF_USER_STATUS_ZC = "1";
	
	/**
	 * 统一用户平台账号状态-离校
	 */
	public static final String ZF_USER_STATUS_LX = "2";
	
	/**
	 * 统一用户平台账号状态-锁定
	 */
	public static final String ZF_USER_STATUS_SD = "3";
	
	/**
	 * 统一用户平台账号状态-删除
	 */
	public static final String ZF_USER_STATUS_SC = "4";
	
	/**
	 * 统一用户平台账号状态-调出
	 */
	public static final String ZF_USER_STATUS_DC = "5";
	
	public static final HashMap<String, Integer> STATUS_RELATION_MAP;
	static {
		STATUS_RELATION_MAP = new HashMap<String, Integer>();
		STATUS_RELATION_MAP.put(ZF_USER_STATUS_ZC, User.USER_MARK_NORMAL);
		STATUS_RELATION_MAP.put(ZF_USER_STATUS_LX, User.USER_MARK_OUT);
		STATUS_RELATION_MAP.put(ZF_USER_STATUS_SD, User.USER_MARK_LOCK);
		STATUS_RELATION_MAP.put(ZF_USER_STATUS_SC, User.USER_MARK_OUT);
		STATUS_RELATION_MAP.put(ZF_USER_STATUS_DC, User.USER_MARK_OUT);
	}
	
}
