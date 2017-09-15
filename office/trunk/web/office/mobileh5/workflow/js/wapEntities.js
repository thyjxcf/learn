var WapConstants = {
	//列表搜索类型
	DATA_TYPE_1 : '1',//待我审批的
	DATA_TYPE_2 : '2',//我已审批的
	DATA_TYPE_0 : '0',//我发起的
	DATA_TYPE_99 : '99',//返回到客户端
	
	//是否返回操作
	BACK_LIST_GLAG_0 : '0',
	BACK_LIST_GLAG_1 : '1',
	
	//需跟ConvertFlowConstants类中定义的要一致
	OFFICE_ALL : '0',
	OFFICE_TEACHER_LEAVE : 1, //教师请假
	OFFICE_GO_OUT : 2, //外出管理
	OFFICE_GO_OUT_JT : 8, //集体外出管理(要跟pc端一致)
	OFFICE_EVECTION : 3, //出差管理
	OFFICE_EXPENSE : 4, //报销管理
	OFFICE_GOODS : 5, //物品管理
	OFFICE_ATTENDANCE: 9, //教师考勤-补卡申请
	OFFICE_ATTEND_LECTURE : 6, //听课管理
	
	/**
     * 物品审核状态
     */
    GOODS_NOT_AUDIT : 0,// 等待审核
    GOODS_AUDIT_PASS : 1,// 审核通过
    GOODS_AUDIT_NOT_PASS : 2,// 审核未通过
    GOODS_HAS_RETURNED : 3,// 已归还
    GOOGS_AUDIT_MODE : "auditMode",
    
    BOOLEAN_0 : 0,
	BOOLEAN_1 : 1,
	
	SEARCH_DATA_TYPE : "searchDataType",
	SEARCH_TYPE : "searchType",
	SEARCH_NAME : "searchName",
	/**
     * 集体外出
     */
	ADDRESS_TYPE_JT : 'jt_address_type',
	ADDRESS_TYPE_JT_1 : 'jt_address_activity',
	ADDRESS_TYPE_JT_2 : 'jt_address_teacher',
	ADDRESS_TYPE_JT_3 : 'jt_address_teachers',
	ADDRESS_TYPE_JT_4 : 'jt_address_person',
	
	//集体外出流程，集体外出类型
	JT_TYPES : 'jt_types',
	JT_FLOWS : 'jt_flows',
	
	ADDRESS_USERIDS_JT_1 : 'jt_address_userid_1',
	ADDRESS_USERNAMES_JT_1 : 'jt_address_username_1',
	ADDRESS_USERIDS_JT_2 : 'jt_address_userid_2',
	ADDRESS_USERNAMES_JT_2 : 'jt_address_username_2',
	ADDRESS_USERIDS_JT_3 : 'jt_address_userid_3',
	ADDRESS_USERNAMES_JT_3 : 'jt_address_username_3',
	ADDRESS_USERIDS_JT_4 : 'jt_address_userid_4',
	ADDRESS_USERNAMES_JT_4 : 'jt_address_username_4',
	
	//申请信息
	LEAVE_EDIT_ID : 'jt_edit_id',
	LEAVE_EDIT_USERID : 'jt_edit_userid',
	LEAVE_EDIT_UNITID : 'jt_edit_unitid',
	LEAVE_EDIT_CREATETIME : 'jt_edit_createTime',
	LEAVE_EDIT_APPLYUSERID : 'jt_edit_applyUserId',
	LEAVE_EDIT_START_DATE : 'jt_edit_startdate',
	LEAVE_EDIT_END_DATE : 'jt_edit_enddate',
	LEAVE_EDIT_END_GOOUTJTTYPE : 'jt_edit_goOutJtType',
	LEAVE_EDIT_END_ORGANIZE : 'jt_edit_organize',
	LEAVE_EDIT_END_ACTIVITYNUMBER : 'jt_edit_activityNumber',
	LEAVE_EDIT_END_PLACE : 'jt_edit_place',
	LEAVE_EDIT_END_SCONTENT : 'jt_edit_scontent',
	LEAVE_EDIT_END_VEHICLE : 'jt_edit_vehicle',
	LEAVE_EDIT_END_ISDRIVINGLICENCE : 'jt_edit_isDrivinglicence',
	LEAVE_EDIT_END_ISORGANIZATION : 'jt_edit_isOrganization',
	LEAVE_EDIT_END_TRAVEUNIT : 'jt_edit_traveUnit',
	LEAVE_EDIT_END_TRAVELINKPERSON : 'jt_edit_travelinkPerson',
	LEAVE_EDIT_END_TRAVELINKPHONE : 'jt_edit_travelinkPhone',
	LEAVE_EDIT_END_ISINSURANCE: 'jt_edit_isInsurance',
	LEAVE_EDIT_END_ACTIVITYLEADERID : 'jt_edit_activityleaderId',
	LEAVE_EDIT_END_ACTIVITYLEADERNAME: 'jt_edit_activityleaderName',
	LEAVE_EDIT_END_ACTIVITYLEADERPHONE: 'jt_edit_activityleaderPhone',
	LEAVE_EDIT_END_LEADGROUPID: 'jt_edit_leadGroupId',
	LEAVE_EDIT_END_LEADGROUPNAME: 'jt_edit_leadGroupName',
	LEAVE_EDIT_END_LEADGROUPPHONE: 'jt_edit_leadGroupPhone',
	LEAVE_EDIT_END_OTHERTEACHERID: 'jt_edit_otherTeacherId',
	LEAVE_EDIT_END_OTHERTEACHERNAMES: 'jt_edit_otherTeacherNames',
	LEAVE_EDIT_END_ACTIVITYIDEAL: 'jt_edit_activityIdeal',
	LEAVE_EDIT_END_SAFTIDEAL: 'jt_edit_saftIdeal',
	LEAVE_EDIT_END_TCONTENT: 'jt_edit_tcontent',
	LEAVE_EDIT_END_PARTAKEPERSONID: 'jt_edit_partakePersonId',
	LEAVE_EDIT_END_PARTAKEPERSONNAMES: 'jt_edit_partakePersonNames',
	LEAVE_EDIT_END_GOOUTJTFLOW: 'jt_edit_gooutJtFlow',
	
    /**
     * 教师请假
     */
  //通讯录
	ADDRESS_TYPE : 'tl_address_type',
	ADDRESS_TYPE_1 : 'tl_address_apply',
	ADDRESS_TYPE_2 : 'tl_address_notice',
	
	ADDRESS_USERIDS_1 : 'tl_address_userid_1',
	ADDRESS_USERNAMES_1 : 'tl_address_username_1',
	ADDRESS_USERIDS_2 : 'tl_address_userid_2',
	ADDRESS_USERNAMES_2 : 'tl_address_username_2',
	
	//请假流程，请假类型
	LEAVE_TYPES : 'tl_leave_types',
	LEAVE_FLOWS : 'tl_leave_flows',
	
	//申请信息
	LEAVE_EDIT_ID : 'tl_edit_id',
	LEAVE_EDIT_USERID : 'tl_edit_userid',
	LEAVE_EDIT_UNITID : 'tl_edit_unitid',
	LEAVE_EDIT_DEPTID : 'tl_edit_deptid',
	LEAVE_EDIT_START_DATE : 'tl_edit_stratdate',
	LEAVE_EDIT_END_DATE : 'tl_edit_enddate',
	LEAVE_EDIT_LEAVE_DAYS : 'tl_edit_leavedays',
	LEAVE_EDIT_LEAVE_TYPE : 'tl_edit_leavetype',
	LEAVE_EDIT_REMARK : 'tl_edit_remark',
	LEAVE_EDIT_LEAVEFLOW : 'tl_edit_leaveflow',
	
	//缓存key
	OFFICE_LIST_NUM : 'office_convert_flow_list_num',
	
	//班级数组
	OFFICE_CLASS_ARRAY:'office_class_array',
	//模块权限
	OFFICE_MAIN_MODEL:'office_main_model',

}