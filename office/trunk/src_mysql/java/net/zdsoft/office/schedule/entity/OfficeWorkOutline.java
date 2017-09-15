package net.zdsoft.office.schedule.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
/**
 * office_work_outline
 * @author 
 * 
 */
public class OfficeWorkOutline implements Serializable{
	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	private String id;
	/**
	 * 与职工表(base_employee)的id关联
	 */
	private String operator;
	/**
	 * 与单位表(base_unit)的id关联
	 */
	private String unitId;
	/**
	 * 日程地点
	 */
	private String place;
	
	
	/**
	 * 内容
	 */
	private String content;
	/**
	 * 备注
	 */
	private String remark;
	/**
	 * 0表示未删除，1表示已删除，默认值为0
	 */
	private int isDeleted;
	/**
	 * 更新戳
	 */
	private Date modifyTime;
	/**
	 * 日程时间
	 */
	private Date calendarTime;
	/**
	 * 0:不提醒
		1:提醒
	 */
	private int isSmsAlarm;
	/**
	 * 
	 */
	private Date smsAlarmTime;
	/**
	 * 
	 */
	private String version;
	/**
	 * 日程安排天数（以半天为单位）
	 */
	private Integer halfDays;
	/**
	 * 
	 */
	private Date endTime;
	/**
	 * 0全天，1上午，2中午，3下午，4晚上
	 */
	private int period;
	/**
	 * 部门id
	 */
	private String createDept;
	/**
	 * 
	 */
	private String state;
	/**
	 * 
	 */
	private String auditRemark;
	
	//---help
	private String deptName;//科室名称
	private List<OfficeWorkOutline> outlineList;
	private boolean isAllDayEvent;//是否全天事件
	private boolean isOperator=false;//是否维护人
	private boolean isHasAuthToOperate=false;
	/**
	 * 设置主键
	 */
	public void setId(String id){
		this.id = id;
	}
	/**
	 * 获取主键
	 */
	public String getId(){
		return this.id;
	}
	/**
	 * 设置与职工表(base_employee)的id关联
	 */
	public void setOperator(String operator){
		this.operator = operator;
	}
	/**
	 * 获取与职工表(base_employee)的id关联
	 */
	public String getOperator(){
		return this.operator;
	}
	/**
	 * 设置与单位表(base_unit)的id关联
	 */
	public void setUnitId(String unitId){
		this.unitId = unitId;
	}
	/**
	 * 获取与单位表(base_unit)的id关联
	 */
	public String getUnitId(){
		return this.unitId;
	}
	/**
	 * 设置日程地点
	 */
	public void setPlace(String place){
		this.place = place;
	}
	/**
	 * 获取日程地点
	 */
	public String getPlace(){
		return this.place;
	}
	
	/**
	 * 设置内容
	 */
	public void setContent(String content){
		this.content = content;
	}
	/**
	 * 获取内容
	 */
	public String getContent(){
		return this.content;
	}
	/**
	 * 设置备注
	 */
	public void setRemark(String remark){
		this.remark = remark;
	}
	/**
	 * 获取备注
	 */
	public String getRemark(){
		return this.remark;
	}
	/**
	 * 设置0表示未删除，1表示已删除，默认值为0
	 */
	public void setIsDeleted(int isDeleted){
		this.isDeleted = isDeleted;
	}
	/**
	 * 获取0表示未删除，1表示已删除，默认值为0
	 */
	public int getIsDeleted(){
		return this.isDeleted;
	}
	/**
	 * 设置更新戳
	 */
	public void setModifyTime(Date modifyTime){
		this.modifyTime = modifyTime;
	}
	/**
	 * 获取更新戳
	 */
	public Date getModifyTime(){
		return this.modifyTime;
	}
	/**
	 * 设置日程时间
	 */
	public void setCalendarTime(Date calendarTime){
		this.calendarTime = calendarTime;
	}
	/**
	 * 获取日程时间
	 */
	public Date getCalendarTime(){
		return this.calendarTime;
	}
	/**
	 * 设置0:不提醒
1:提醒
	 */
	public void setIsSmsAlarm(int isSmsAlarm){
		this.isSmsAlarm = isSmsAlarm;
	}
	/**
	 * 获取0:不提醒
1:提醒
	 */
	public int getIsSmsAlarm(){
		return this.isSmsAlarm;
	}
	/**
	 * 设置
	 */
	public void setSmsAlarmTime(Date smsAlarmTime){
		this.smsAlarmTime = smsAlarmTime;
	}
	/**
	 * 获取
	 */
	public Date getSmsAlarmTime(){
		return this.smsAlarmTime;
	}
	/**
	 * 设置
	 */
	public void setVersion(String version){
		this.version = version;
	}
	/**
	 * 获取
	 */
	public String getVersion(){
		return this.version;
	}
	/**
	 * 设置日程安排天数（以半天为单位）
	 */
	public void setHalfDays(Integer halfDays){
		this.halfDays = halfDays;
	}
	/**
	 * 获取日程安排天数（以半天为单位）
	 */
	public Integer getHalfDays(){
		return this.halfDays;
	}
	/**
	 * 设置
	 */
	public void setEndTime(Date endTime){
		this.endTime = endTime;
	}
	/**
	 * 获取
	 */
	public Date getEndTime(){
		return this.endTime;
	}
	/**
	 * 设置0全天，1上午，2中午，3下午，4晚上
	 */
	public void setPeriod(int period){
		this.period = period;
	}
	/**
	 * 获取0全天，1上午，2中午，3下午，4晚上
	 */
	public int getPeriod(){
		return this.period;
	}
	/**
	 * 设置
	 */
	public void setCreateDept(String createDept){
		this.createDept = createDept;
	}
	/**
	 * 获取
	 */
	public String getCreateDept(){
		return this.createDept;
	}
	/**
	 * 设置
	 */
	public void setState(String state){
		this.state = state;
	}
	/**
	 * 获取
	 */
	public String getState(){
		return this.state;
	}
	/**
	 * 设置
	 */
	public void setAuditRemark(String auditRemark){
		this.auditRemark = auditRemark;
	}
	/**
	 * 获取
	 */
	public String getAuditRemark(){
		return this.auditRemark;
	}
	public List<OfficeWorkOutline> getOutlineList() {
		return outlineList;
	}
	public void setOutlineList(List<OfficeWorkOutline> outlineList) {
		this.outlineList = outlineList;
	}
	public String getDeptName() {
		return deptName;
	}
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	public boolean getIsAllDayEvent() {
		return isAllDayEvent;
	}
	public void setIsAllDayEvent(boolean isAllDayEvent) {
		this.isAllDayEvent = isAllDayEvent;
	}
	public boolean getIsOperator() {
		return isOperator;
	}
	public void setIsOperator(boolean isOperator) {
		this.isOperator = isOperator;
	}
	public boolean getIsHasAuthToOperate() {
		return isHasAuthToOperate;
	}
	public void setIsHasAuthToOperate(boolean isHasAuthToOperate) {
		this.isHasAuthToOperate = isHasAuthToOperate;
	}
	
	
}