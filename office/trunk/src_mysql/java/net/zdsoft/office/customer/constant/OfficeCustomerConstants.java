package net.zdsoft.office.customer.constant;

public class OfficeCustomerConstants {

	/**
	 * subsystem
	 */
	public static final String SUBSYSTEM_ID = "70";
	
	/**
	 * 客户报备流程(销售--事业部负责人--运营人员)
	 */
	public static final int BUSINESS_TYPE = 7002;
	
	/**
	 * 客户报备流程(事业部负责人--运营人员)
	 */
	public static final int BUSINESS_TYPE_2 = 7003;
	
	/**
	 * 地区负责人
	 */
	public static final String OFFICE_REGION_LEADER = "office_regionLeader";
	
	/**
	 * 部门负责人(事业部负责人)
	 */
	public static final String OFFCIE_DEPT_LEADER = "dept_head";
	
	/**
	 * 运营人员
	 */
	public static final String OFFICE_CLIENT_MANAGER = "office_clientManager";
	//申请类型
    public static final String APPLY_NEW="0";//新增客户申请
    public static final String APPLY_OLD="1";//资源库客户申请
    public static final String APPLY_PUT_OFF="2";//延期申请
    public static final String APPLY_TRANS="3";//转发申请
    //客户信息表状态
    public static final Integer INFO_APPLY=0;//客户申请中
    public static final Integer INFO_FLOWING=1;//跟进中
    public static final Integer INFO_LIBARAY=2;//在资源库
    
    //审核状态
	  public static final Integer LEVEL_APPLY_FLOWING =5;//审核中-初审中
	  public static final Integer LEVEL_APPLY_FLOW_FIRST_PASS =6;//审核中-终审中
	  public static final Integer LEVEL_APPLY_FLOW_FIRST_UNPASS =7;//未通过-初审未通过
	  public static final Integer LEVEL_APPLY_FLOW_FINALLY_UNPASS =8;//未通过-终审未通过
	  public static final Integer LEVEL_APPLY_FLOW_FINALLY_PASS =9;//通过-终审通过
	  
	  //已开过一下次试听课
	  public static final String OPEN_LECTURE_ONCE="1";
}
