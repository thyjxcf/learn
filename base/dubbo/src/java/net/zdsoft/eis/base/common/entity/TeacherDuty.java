package net.zdsoft.eis.base.common.entity;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;

import net.zdsoft.eis.base.util.SUtils;
import net.zdsoft.eis.frame.client.BaseEntity;
import net.zdsoft.keel.util.Pagination;

public class TeacherDuty extends BaseEntity{

	private static final long serialVersionUID = -6770111417600552879L;
	
	private String teacherId;
	
	private String dutyCode;
	
	
	public static List<TeacherDuty> dt(String data) {
		List<TeacherDuty> ts = SUtils.dt(data, new TypeReference<List<TeacherDuty>>() {
		});
		if (ts == null)
			ts = new ArrayList<TeacherDuty>();
		
		return ts;

	}

	public static TeacherDuty dc(String data) {
		TeacherDuty teacherDuty = SUtils.dc(data, TeacherDuty.class);
		return teacherDuty;
	}
	
	

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
