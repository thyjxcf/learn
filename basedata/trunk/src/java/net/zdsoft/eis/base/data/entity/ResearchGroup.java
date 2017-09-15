package net.zdsoft.eis.base.data.entity;

import java.util.Date;

public class ResearchGroup{
	
	private static final long serialVersionUID = 1L;
	protected String id;
	private String schoolId;
	private String teachGroupName;
	private String subjectId;
	private String principal;
	private String member;
	protected Date creationTime;
	protected Date modifyTime;
	private Integer isdeleted;
	
	
	public String getSchoolId() {
		return schoolId;
	}
	public void setSchoolId(String schoolId) {
		this.schoolId = schoolId;
	}
	public String getTeachGroupName() {
		return teachGroupName;
	}
	public void setTeachGroupName(String teachGroupName) {
		this.teachGroupName = teachGroupName;
	}
	public String getSubjectId() {
		return subjectId;
	}
	public void setSubjectId(String subjectId) {
		this.subjectId = subjectId;
	}
	public String getPrincipal() {
		return principal;
	}
	public void setPrincipal(String principal) {
		this.principal = principal;
	}
	public String getMember() {
		return member;
	}
	public void setMember(String member) {
		this.member = member;
	}
	public Integer getIsdeleted() {
		return isdeleted;
	}
	public void setIsdeleted(Integer isdeleted) {
		this.isdeleted = isdeleted;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Date getCreationTime() {
		return creationTime;
	}
	public void setCreationTime(Date creationTime) {
		this.creationTime = creationTime;
	}
	public Date getModifyTime() {
		return modifyTime;
	}
	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}
	
}
