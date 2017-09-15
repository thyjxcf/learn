package net.zdsoft.office.dailyoffice.entity;


import java.io.Serializable;
import java.util.Date;
/**
 * office_work_report_tl
 * @author 
 * 
 */
public class OfficeWorkReportTl implements Serializable{
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	private String id;
	/**
	 * 
	 */
	private String year;
	/**
	 * 
	 */
	private Integer semester;
	/**
	 * 
	 */
	private Integer week;
	/**
	 * 
	 */
	private String content;
	/**
	 * 1：未提交，2：已提交
	 */
	private Integer state;
	/**
	 * 
	 */
	private String unitId;
	private String unitName;
	/**
	 * 1：教育局，2：学校
	 */
	private Integer unitClass;
	/**
	 * 
	 */
	private String parentUnitId;
	/**
	 * 
	 */
	private String unitOrderId;
	/**
	 * 
	 */
	private Integer teacherOrderId;
	/**
	 * 
	 */
	private String createUserId;
	private String createUserName;
	/**
	 * 
	 */
	private Date createTime;
	/**
	 * 
	 */
	private String modifyUserId;
	/**
	 * 
	 */
	private Date modifyTime;

	/**
	 * 设置
	 */
	public void setId(String id){
		this.id = id;
	}
	/**
	 * 获取
	 */
	public String getId(){
		return this.id;
	}
	/**
	 * 设置
	 */
	public void setYear(String year){
		this.year = year;
	}
	/**
	 * 获取
	 */
	public String getYear(){
		return this.year;
	}
	/**
	 * 设置
	 */
	public void setSemester(Integer semester){
		this.semester = semester;
	}
	/**
	 * 获取
	 */
	public Integer getSemester(){
		return this.semester;
	}
	/**
	 * 设置
	 */
	public void setWeek(Integer week){
		this.week = week;
	}
	/**
	 * 获取
	 */
	public Integer getWeek(){
		return this.week;
	}
	/**
	 * 设置
	 */
	public void setContent(String content){
		this.content = content;
	}
	/**
	 * 获取
	 */
	public String getContent(){
		return this.content;
	}
	/**
	 * 设置1：未提交，2：已提交
	 */
	public void setState(Integer state){
		this.state = state;
	}
	/**
	 * 获取1：未提交，2：已提交
	 */
	public Integer getState(){
		return this.state;
	}
	/**
	 * 设置
	 */
	public void setUnitId(String unitId){
		this.unitId = unitId;
	}
	/**
	 * 获取
	 */
	public String getUnitId(){
		return this.unitId;
	}
	/**
	 * 设置1：教育局，2：学校
	 */
	public void setUnitClass(Integer unitClass){
		this.unitClass = unitClass;
	}
	/**
	 * 获取1：教育局，2：学校
	 */
	public Integer getUnitClass(){
		return this.unitClass;
	}
	/**
	 * 设置
	 */
	public void setParentUnitId(String parentUnitId){
		this.parentUnitId = parentUnitId;
	}
	/**
	 * 获取
	 */
	public String getParentUnitId(){
		return this.parentUnitId;
	}
	/**
	 * 设置
	 */
	public void setUnitOrderId(String unitOrderId){
		this.unitOrderId = unitOrderId;
	}
	/**
	 * 获取
	 */
	public String getUnitOrderId(){
		return this.unitOrderId;
	}
	/**
	 * 设置
	 */
	public void setTeacherOrderId(Integer teacherOrderId){
		this.teacherOrderId = teacherOrderId;
	}
	/**
	 * 获取
	 */
	public Integer getTeacherOrderId(){
		return this.teacherOrderId;
	}
	/**
	 * 设置
	 */
	public void setCreateUserId(String createUserId){
		this.createUserId = createUserId;
	}
	/**
	 * 获取
	 */
	public String getCreateUserId(){
		return this.createUserId;
	}
	/**
	 * 设置
	 */
	public void setCreateTime(Date createTime){
		this.createTime = createTime;
	}
	/**
	 * 获取
	 */
	public Date getCreateTime(){
		return this.createTime;
	}
	/**
	 * 设置
	 */
	public void setModifyUserId(String modifyUserId){
		this.modifyUserId = modifyUserId;
	}
	/**
	 * 获取
	 */
	public String getModifyUserId(){
		return this.modifyUserId;
	}
	/**
	 * 设置
	 */
	public void setModifyTime(Date modifyTime){
		this.modifyTime = modifyTime;
	}
	/**
	 * 获取
	 */
	public Date getModifyTime(){
		return this.modifyTime;
	}
	public String getCreateUserName() {
		return createUserName;
	}
	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}
	public String getUnitName() {
		return unitName;
	}
	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}
}