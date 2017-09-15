package net.zdsoft.eis.base.data.entity;

import net.zdsoft.eis.frame.client.BaseEntity;

public class BaseTeacherDuty extends BaseEntity{

	private static final long serialVersionUID = -6770111417600552879L;
	
	private String teacherId;
	
	private String dutyCode;

	public String getTeacherId() {
		return teacherId;
	}

	public void setTeacherId(String teacherId) {
		this.teacherId = teacherId;
	}

	public String getDutyCode() {
		return dutyCode;
	}

	public void setDutyCode(String dutyCode) {
		this.dutyCode = dutyCode;
	}
}
