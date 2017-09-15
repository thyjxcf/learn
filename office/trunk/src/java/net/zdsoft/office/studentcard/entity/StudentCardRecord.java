package net.zdsoft.office.studentcard.entity;

import java.io.Serializable;
import java.util.Date;

public class StudentCardRecord implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String id;
	private String studentId;
	private Date refshCardDate;
	//进出标志:2、出校，1、进校
	private Integer flag;
	
	public static final int OUT_SCHOOL = 2;//出校
	public static final int IN_SCHOOL = 1;//进校
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getStudentId() {
		return studentId;
	}
	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}
	public Date getRefshCardDate() {
		return refshCardDate;
	}
	public void setRefshCardDate(Date refshCardDate) {
		this.refshCardDate = refshCardDate;
	}
	public Integer getFlag() {
		return flag;
	}
	public void setFlag(Integer flag) {
		this.flag = flag;
	}
	

}
