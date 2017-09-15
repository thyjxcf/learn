package net.zdsoft.office.survey.constant;

public class OfficeSurveyConstants {
	
	/**
	 * 调研审核权限
	 */
	public static final String OFFCIE_SURVEY_AUDIT = "office_survey_audit";
	
	/**
	 * 调研查询权限
	 */
	public static final String OFFCIE_SURVEY_QUERY = "office_survey_query";
	
	/**
	 * 审核状态
	 */
	public static final int OFFCIE_SURVEY_NOT_SUBMIT = 0;//未提交
	public static final int OFFCIE_SURVEY_WAIT = 1;//待审核
	public static final int OFFCIE_SURVEY_PASS = 2;//审核通过
	public static final int OFFCIE_SURVEY_FAILED = 3;//审核不通过
	public static final int OFFCIE_SURVEY_ALL = 9;//全部
}
