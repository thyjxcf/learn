package net.zdsoft.office.goodmanage.constant;

public class OfficeGoodsConstants {

	/**
	 * 物品归还状态
	 */
	public static final Integer GOODS_HAS_REQ = 1;
    public static final Integer GOODS_HAS_NOT_REQ = 0;
    /**
     * 物品管理权限、物品审核权限
     */
    public static final String OFFICE_GOODS_MANAGE = "office_goods_manage";
	public static final String OFFICE_GOODS_AUDIT = "office_goods_audit";
    
    /**
     * 变动类型
     */
    public static final String GOODS_REGISTER = "物品登记";
    public static final String GOODS_MANAGE = "物品管理";
    public static final String GOODS_GET = "物品领用";
    public static final String GOODS_RETURN = "物品归还";
    public static final String GOODS_GIVE = "物品发放";
    
    /**
     * 审核状态
     */
    public static final Integer GOODS_NOT_AUDIT = 0;// 等待审核
    public static final Integer GOODS_AUDIT_PASS = 1;// 审核通过
    public static final Integer GOODS_AUDIT_NOT_PASS = 2;// 审核未通过
    public static final Integer GOODS_HAS_RETURNED = 3;// 已归还
    public static final Integer GOODS_AUDIT_ALL = 9;//全部审核列表（包含0、1、2状态）
    
    /**
	 * 是否走审核模式
	 */
	public static final String GOODS_AUDIT_MODEL="GOODS.AUDIT.MODEL";
}
