package net.zdsoft.office.studentLeave.entity;

import java.io.Serializable;
import java.util.Date;
/**
 * office_hwstudent_leave
 * @author 
 * 
 */
public class OfficeHwstudentLeave implements Serializable{
	private static final long serialVersionUID = 1L;

	private String id;
	private String unitId;
	private String applyUserId;
	private Date applyDate;
	/**
	 * 杭外学生请假类型：1普通请假2临时出校3暂时通校、住校4长期通校
	 */
	private String type;
	private String state;
	private String flowId;
	private boolean isDeleted;
	private Date creationTime;
	private Date modifyTime;
	private String classId;
	private String studentId;

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
	 * 设置
	 */
	public void setApplyUserId(String applyUserId){
		this.applyUserId = applyUserId;
	}
	/**
	 * 获取
	 */
	public String getApplyUserId(){
		return this.applyUserId;
	}
	/**
	 * 设置
	 */
	public void setApplyDate(Date applyDate){
		this.applyDate = applyDate;
	}
	/**
	 * 获取
	 */
	public Date getApplyDate(){
		return this.applyDate;
	}
	/**
	 * 设置
	 */
	public void setType(String type){
		this.type = type;
	}
	/**
	 * 获取
	 */
	public String getType(){
		return this.type;
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
	public void setFlowId(String flowId){
		this.flowId = flowId;
	}
	/**
	 * 获取
	 */
	public String getFlowId(){
		return this.flowId;
	}
	/**
	 * 设置
	 */
	public void setIsDeleted(boolean isDeleted){
		this.isDeleted = isDeleted;
	}
	/**
	 * 获取
	 */
	public boolean getIsDeleted(){
		return this.isDeleted;
	}
	/**
	 * 设置
	 */
	public void setCreationTime(Date creationTime){
		this.creationTime = creationTime;
	}
	/**
	 * 获取
	 */
	public Date getCreationTime(){
		return this.creationTime;
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
	/**
	 * 设置
	 */
	public void setClassId(String classId){
		this.classId = classId;
	}
	/**
	 * 获取
	 */
	public String getClassId(){
		return this.classId;
	}
	/**
	 * 设置
	 */
	public void setStudentId(String studentId){
		this.studentId = studentId;
	}
	/**
	 * 获取
	 */
	public String getStudentId(){
		return this.studentId;
	}
}