package net.zdsoft.eis.base.subsystemcall.entity; 

import net.zdsoft.eis.frame.client.BaseEntity;

/**
 * @description: 课程
 * @date: 2014-4-1
 */
public class EduadmSubjectDto extends BaseEntity{
	
	private static final long serialVersionUID = -6291314665209669454L;
	
	private String unitId;
	/**
	 * 编号
	 */
	private String subjectNo;
	/**
	 * 课程简称
	 */
	private String subjectName;
	/**
	 * 课程类别ID
	 */
	private String subjectTypeId;
	/**
	 * 所在教研组
	 */
	private String groupId;
	/**
	 * 周课时
	 */
	private Float weekPeriod;
	/**
	 * 考试方式
	 */
	private String examMode;
	/**
	 * 分制类型
	 */
	private String scodeTypeId;
	/**
	 * 是否含上机课
	 */
	private boolean computerClass;
	/**
	 * 适用对象
	 */
	private String suitableObj;
	/**
	 * 简介
	 */
	private String subjComment;
	/**
	 * 状态
	 */
	private boolean state;
	
	private String subjectNameAll;//课程名称
	
//	除了数据库以外的字段
	/**
	 * 课程类别名称
	 */
	private String subjectTypeName;
	/**
	 * 所在教研组名称
	 */
	private String groupName;
	/**
	 * 分制类型名称
	 */
	private String scodeTypeName;
	/**
	 * 状态对应微代码名称
	 * @return
	 */
	private String stateName;
	/**
	 * 考试方式对应微代码名称
	 * @return
	 */
	private String examModeName;
	
	private Integer rowJoint;
	
	private Integer rowNumber;
	
	private String suitableObjIds;//适用对象
	private String suitableObjNames;
	
	
	
	public String getSubjectNameAll() {
		return subjectNameAll;
	}
	public void setSubjectNameAll(String subjectNameAll) {
		this.subjectNameAll = subjectNameAll;
	}
	public String getUnitId() {
		return unitId;
	}
	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}
	public String getSubjectNo() {
		return subjectNo;
	}
	public void setSubjectNo(String subjectNo) {
		this.subjectNo = subjectNo;
	}
	public String getSubjectName() {
		return subjectName;
	}
	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}
	public String getSubjectTypeId() {
		return subjectTypeId;
	}
	public void setSubjectTypeId(String subjectTypeId) {
		this.subjectTypeId = subjectTypeId;
	}
	public String getGroupId() {
		return groupId;
	}
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	public String getExamMode() {
		return examMode;
	}
	public void setExamMode(String examMode) {
		this.examMode = examMode;
	}
	public String getScodeTypeId() {
		return scodeTypeId;
	}
	public void setScodeTypeId(String scodeTypeId) {
		this.scodeTypeId = scodeTypeId;
	}
	public String getSuitableObj() {
		return suitableObj;
	}
	public void setSuitableObj(String suitableObj) {
		this.suitableObj = suitableObj;
	}
	public String getSubjComment() {
		return subjComment;
	}
	public void setSubjComment(String subjComment) {
		this.subjComment = subjComment;
	}
	public boolean isComputerClass() {
		return computerClass;
	}
	public void setComputerClass(boolean computerClass) {
		this.computerClass = computerClass;
	}
	public boolean isState() {
		return state;
	}
	public void setState(boolean state) {
		this.state = state;
	}
	public Float getWeekPeriod() {
		return weekPeriod;
	}
	public void setWeekPeriod(Float weekPeriod) {
		this.weekPeriod = weekPeriod;
	}
	public String getSubjectTypeName() {
		return subjectTypeName;
	}
	public void setSubjectTypeName(String subjectTypeName) {
		this.subjectTypeName = subjectTypeName;
	}
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public String getScodeTypeName() {
		return scodeTypeName;
	}
	public void setScodeTypeName(String scodeTypeName) {
		this.scodeTypeName = scodeTypeName;
	}
	public String getStateName() {
		return stateName;
	}
	public void setStateName(String stateName) {
		this.stateName = stateName;
	}
	public String getExamModeName() {
		return examModeName;
	}
	public void setExamModeName(String examModeName) {
		this.examModeName = examModeName;
	}
	public String getSuitableObjIds() {
		return suitableObjIds;
	}
	public void setSuitableObjIds(String suitableObjIds) {
		this.suitableObjIds = suitableObjIds;
	}
	public String getSuitableObjNames() {
		return suitableObjNames;
	}
	public void setSuitableObjNames(String suitableObjNames) {
		this.suitableObjNames = suitableObjNames;
	}
	public Integer getRowJoint() {
		return rowJoint;
	}
	public void setRowJoint(Integer rowJoint) {
		this.rowJoint = rowJoint;
	}
	public Integer getRowNumber() {
		return rowNumber;
	}
	public void setRowNumber(Integer rowNumber) {
		this.rowNumber = rowNumber;
	}
}
