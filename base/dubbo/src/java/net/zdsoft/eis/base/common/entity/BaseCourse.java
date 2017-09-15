package net.zdsoft.eis.base.common.entity;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.TypeReference;

import net.zdsoft.eis.base.util.SUtils;
import net.zdsoft.eis.frame.client.BaseEntity;

public class BaseCourse extends BaseEntity{
	
	
	public static List<BaseCourse> dt(String data) {
		List<BaseCourse> ts = SUtils.dt(data, new TypeReference<List<BaseCourse>>() {
		});
		if (ts == null)
			ts = new ArrayList<BaseCourse>();
		return ts;

	}

	public static BaseCourse dc(String data) {
		return SUtils.dc(data, BaseCourse.class);
	}
	
	private String subjectCode;
	
	private String subjectName;
	
	private Integer isUsing;
	
	private String unitId;

	public String getUnitId() {
		return unitId;
	}

	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}

	public String getSubjectCode() {
		return subjectCode;
	}

	public void setSubjectCode(String subjectCode) {
		this.subjectCode = subjectCode;
	}

	public String getSubjectName() {
		return subjectName;
	}

	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}

	public Integer getIsUsing() {
		return isUsing;
	}

	public void setIsUsing(Integer isUsing) {
		this.isUsing = isUsing;
	}
	
}
