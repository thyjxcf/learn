package net.zdsoft.eis.base.data.entity;

import net.zdsoft.eis.frame.client.BaseEntity;

public class ResearchGroupEx extends BaseEntity{
	
	private static final long serialVersionUID = 1L;
	private String teachGroupId;
	private Integer type;
	private String teacherId;
	
	public String getTeachGroupId() {
		return teachGroupId;
	}
	public void setTeachGroupId(String teachGroupId) {
		this.teachGroupId = teachGroupId;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public String getTeacherId() {
		return teacherId;
	}
	public void setTeacherId(String teacherId) {
		this.teacherId = teacherId;
	}
	
}
