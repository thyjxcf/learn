package net.zdsoft.eis.base.common.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


import com.alibaba.fastjson.TypeReference;
import com.fasterxml.jackson.annotation.JsonProperty;

import net.zdsoft.eis.base.util.SUtils;
import net.zdsoft.eis.frame.client.BaseEntity;


public class Semester extends BaseEntity {

	public static List<Semester> dt(String data) {
		List<Semester> ts = SUtils.dt(data, new TypeReference<List<Semester>>() {
		});
		if (ts == null)
			ts = new ArrayList<Semester>();
		return ts;

	}

/*	public static List<Semester> dt(String data, Pagination page) {
		JSONObject json = JSONObject.parseObject(data);
		List<Semester> ts = SUtils.dt(json.getString("data"), new TypeReference<List<Semester>>() {
		});
		if (ts == null)
			ts = new ArrayList<Semester>();
		if (json.containsKey("count"))
			page.setMaxRowCount(json.getInteger("count"));
		return ts;

	}*/

	public static Semester dc(String data) {
		return SUtils.dc(data, Semester.class);
	}
	
	private static final long serialVersionUID = 1475299699781117878L;

	private String acadyear;
	private String semester;
	private Date workBegin;
	private Date workEnd;
	private Date semesterBegin; //学期开始日期
	private Date semesterEnd; //学期结束日期
	private short eduDays; //周天数
	private short amPeriods; //上午课节数
	private short pmPeriods; //下午课节数    
	private short nightPeriods; //晚上课节数
	@JsonProperty(value = "mornPeriods")
	private short mornperiods;  //早上课节数

	//新加两个字段，为了学校端可以直接读取教育局的数据
	@JsonProperty(value = "registerDate")
	private Date registerdate; //注册日期
	@JsonProperty(value = "classHour")
	private short classhour; //课长

	public String getAcadyear() {
		return acadyear;
	}

	public void setAcadyear(String acadyear) {
		this.acadyear = acadyear;
	}

	public String getSemester() {
		return semester;
	}

	public void setSemester(String semester) {
		this.semester = semester;
	}

	public Date getWorkBegin() {
		return workBegin;
	}

	public void setWorkBegin(Date workBegin) {
		this.workBegin = workBegin;
	}

	public Date getWorkEnd() {
		return workEnd;
	}

	public void setWorkEnd(Date workEnd) {
		this.workEnd = workEnd;
	}

	public Date getSemesterBegin() {
		return semesterBegin;
	}

	public void setSemesterBegin(Date semesterBegin) {
		this.semesterBegin = semesterBegin;
	}

	public Date getSemesterEnd() {
		return semesterEnd;
	}

	public void setSemesterEnd(Date semesterEnd) {
		this.semesterEnd = semesterEnd;
	}

	public short getEduDays() {
		return eduDays;
	}

	public void setEduDays(short eduDays) {
		this.eduDays = eduDays;
	}

	public short getAmPeriods() {
		return amPeriods;
	}

	public void setAmPeriods(short amPeriods) {
		this.amPeriods = amPeriods;
	}

	public short getPmPeriods() {
		return pmPeriods;
	}

	public void setPmPeriods(short pmPeriods) {
		this.pmPeriods = pmPeriods;
	}

	public short getNightPeriods() {
		return nightPeriods;
	}

	public void setNightPeriods(short nightPeriods) {
		this.nightPeriods = nightPeriods;
	}

	public Date getRegisterdate() {
		return registerdate;
	}

	public void setRegisterdate(Date registerdate) {
		this.registerdate = registerdate;
	}

	public short getClasshour() {
		return classhour;
	}

	public void setClasshour(short classhour) {
		this.classhour = classhour;
	}

	public short getMornperiods() {
		return mornperiods;
	}

	public void setMornperiods(short mornperiods) {
		this.mornperiods = mornperiods;
	}
	
	

}