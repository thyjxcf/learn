package net.zdsoft.eis.base.cache;

public class BaseCacheConstants {
	// --------------------命名原则---------------------
	// 1、前缀：eis_service名字(去掉service)
	// 2、缓存id时，紧跟“.id”; 缓存实体时，后面不跟
	// 3、如果是列表：紧跟“_list”;如果是Map：紧跟“_map”;
	// 4、如果有参数：紧跟“_参数_”
	// 5、base库，由于多个平台可能同时维护基础数据，导致缓存不统一，后面可能会统一提供缓存jar

	// --------------------deploy------------------
	/**
	 * 是否独立部署
	 */
	public static final String EIS_DEPLOY_INDEPENDENCE = "eis_deploy_independence";

	// --------------------app entity------------------
	/**
	 * 根据AP的code，取出AP，存实体
	 */
	public static final String EIS_APP_CODE = "eis_app_code_";

	// --------------------baseSysOption entity------------------
	/**
	 * 根据选项的code，取出选项，存实体
	 */
	public static final String EIS_OPTION_CODE = "eis_option_code_";

	// --------------------unit id---------------------
	/**
	 * 顶级单位，存id
	 */
	public static final String EIS_UNITIDTOP = "eis_unit.id.top";

	/**
	 * 根据上级单位id取出下级单位，存id
	 */
	public static final String EIS_UNITID_LIST_PARENTID = "eis_unit.id_list_parent.id_";

	// --------------------mcodedetail entity---------------------
	/**
	 * 根据微代码的mcodeId和本身的id，取出微代码信息，存实体
	 */
	public static final String EIS_MCODEDETAIL_LIST_MCODEIDTHISID = "eis_mcodedetail_mcode.this.id_";
	/**
	 * 根据微代码的mcodeId和本身的id以及配置区域信息，取出微代码信息，存实体
	 */
	public static final String EIS_MCODEDETAIL_LIST_FIELD_MCODEIDTHISID = "eis_mcodedetail_mcode.field.this.id_";
	
	/**
	 * 从base_string表中获取字符内容
	 */
	public static final String EIS_STRING_CODE = "eis_string.code_";

	/**
	 * 根据微代码mcodeId取出微代码信息，实体
	 */
	public static final String EIS_MCODEDETAIL_LIST_MCODEID_ = "eis_mcodedetail_list_mcode.id_";
	/**
	 * 根据微代码mcodeId以及区域配置信息取出微代码信息，实体
	 */
	public static final String EIS_MCODEDETAIL_LIST_FIELD_MCODEID_ = "eis_mcodedetail_list_mcode.field.id_";
	/**
	 * 根据微代码的mcodeId取出所有的微代码信息，包含停用的，存实体
	 */
	public static final String EIS_MCODEDETAILALL_LIST_MCODEID_ = "eis_mcodedetail.all_list_mcode.id_";

	// --------------------subsystem entity---------------------

	/**
	 * 根据子系统code，如system，检索子系统信息，存实体
	 */
	public static final String EIS_SYSTEM_CODE = "eis_new_system_code_";

	/**
	 * 检索本系统所有的子系统，存数字型id
	 */
	public static final String EIS_SYSTEM_LIST = "eis_new_system_list";
	
	public static final String EIS_THIRD_PART_SYSTEM_LIST = "eis_third_part_system_list";

	// --------------------systemIni entity---------------------

	/**
	 * 根据系统参数设置的描述id，检索系统参数信息，存实体
	 */
	public static final String EIS_SYSTEMOPTION_OPTIONID = "eis_systemini_option.id_";

	// --------------------coderule entity---------------------
	/**
	 * 类CoderuleServiceImpl中，编号生成规则缓存
	 */
	public static final String EIS_CODERULE = "eis_coderule_";

	// --------------------region entity---------------------
	/**
	 * 根据code检索行政区划，存实体
	 */
	public static final String EIS_REGION_CODE = "eis_region_code_";
	public static final String EIS_REGION_NAME = "eis_region_name_";

	/**
	 * 根据fullcode检索行政区划，存实体
	 */
	public static final String EIS_REGION_FULLCODE = "eis_region_fullcode_";

	/**
	 * 下属行政区划列表,存实体
	 */
	public static final String EIS_REGION_LIST_LEVEL_CODE = "eis_region_list_level_code_";

	// --------------------module entity---------------------
	/**
	 * 根据url，检索模块信息
	 */
	public static final String EIS_MODULE_URL = "eis_module_url_";

	/**
	 * 根据modId,unitclass，检索模块信息
	 */
	public static final String EIS_MODULE_MID_UNITCLASS = "eis_module_mid_unit.class_";

	/**
	 * 检索本系统所有可用的模块，存实体
	 */
	public static final String EIS_MODULE_LIST = "eis_module_list";

	/**
	 * 检索本系统所有可用的模块id，存id
	 */
	public static final String EIS_MODULE_ID_LIST = "eis_module_ID_list";

	/**
	 * 检索本系统所有可用的模块id，存实体
	 */
	public static final String EIS_MODULE_MAP = "eis_module_map";

	/**
	 * 根据适用单位类型检索模块信息，存实体
	 */
	public static final String EIS_MODULE_UNITCLASS_ = "eis_module_unit.class_";

	// --------------------semester entity---------------------
	/**
	 * 当前学年学期
	 */
	public static final String EIS_SEMESTER_CURRENT = "eis_semester_current";

	// --------------------simplemodule entity------------------
	/**
	 * 根据模块所属子系统和parent取出下属模块列表，存实体
	 */
	public static final String EIS_SIMPLE_MODULE_LIST_APPID_PARENTID = "eis_simple_module_list_appid_parentid_";

	public static final String EIS_SIMPLE_MODULE_MAP = "eis_simple_module_map_";
	// --------------------storagedir entity---------------------

	/**
	 * 根据类型检索存储激活的目录信息，存实体
	 */
	public static final String EIS_STORAGEDIR_ACTIVE_TYPE = "eis_storagedir_type_";

	// --------------------流程图 entity---------------------
	/**
	 * flowDiagram
	 */
	public static final String EIS_FLOW_DIAGRAM_LIST = "eis_flow_diagram_list";
	
	/**
	 * flowDiagram
	 */
	public static final String EIS_FLOW_DIAGRAM_LIST_BY_MODULEID = "eis_flow_diagram_list_by_module_id";
	
	/**
	 * flowDiagram
	 */
	public static final String EIS_FLOW_DIAGRAM_MAP_BY_SUBSYSTEM = "eis_flow_diagram_map_by_subsystem";
	/**
	 * flowDiagramDetail
	 */
	public static final String EIS_FLOW_DIAGRAM_DETAIL_LIST = "eis_flow_diagram_detail_list";
	
	/**
	 * 根据用户检索其设置
	 */
	public static final String EIS_USER_SET_ID = "new_eis_userset_id_";
	
	// --------------------pattern object---------------------
	 /**
     * 类ManagePatternServiceImpl中Patterns缓存map
     */
    public static final String EIS_PATTERN_MAP_ALL = "eis_pattern_map_all";
	
	//---------------------------自定义审核流程-------------------------------
	/**
	 * 根据申请实体类型取实体列
	 */
	public static final String EIS_BUSINESS_TYPE = "eis_business_type_";
	
	/**
	 * 根据申请实体类型取实体列
	 */
	public static final String EIS_STEP_BY_BUSINESS_TYPE = "eis_step_by_business_type_";
	
	// --------------------自定义角色---------------------
	/**
	 * 根据申请实体类型取实体列
	 */
	public static final String EIS_CUSTOM_ROLE_LIST = "eis_custom_role_list_";
	
	// --------------------桌面app---------------------
	/**
	 * 根据申请实体类型取实体列
	 */
	public static final String EIS_DESKTOP_APP_LIST = "eis_desktop_app_list_";
	
	public static final String EIS_DESKTOP_APP_KEYS = "eis_desktop_app_keys";
	
}
