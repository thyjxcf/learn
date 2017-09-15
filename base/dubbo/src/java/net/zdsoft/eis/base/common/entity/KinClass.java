package net.zdsoft.eis.base.common.entity;

import java.io.Serializable;
/**
 * 同类班级
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: Dec 8, 2009 5:24:45 PM $
 */
public class KinClass implements Serializable {
    private static final long serialVersionUID = 3132558685742922763L;
    
    private String schid; // 学校ID
	private String subschoolid; // 分校区ID
	private int section; // 所属学段（微代码：0幼儿园，1小学，2初中，3高中）
	private String acadyear; // 入学学年（格式为:2005-2006）
	private int schoolinglen; // 学制
	private int grade; // 年级级别（1，2，3，……）
	private String gradename; // 年级名称（小学一年级/小一，初中二年级/初二，……）
	private String type; // 类型
	private String kinClassCode; // 同类班级代码
	private String kinClassName; // 同类班级名称
	private String rangeType;  //范围类型，普通班级为1，同类班级为2

	public KinClass() {
	}

	public String getAcadyear() {
		return acadyear;
	}

	public void setAcadyear(String acadyear) {
		this.acadyear = acadyear;
	}

	public String getSchid() {
		return schid;
	}

	public void setSchid(String schid) {
		this.schid = schid;
	}

	public int getSection() {
		return section;
	}

	public void setSection(int section) {
		this.section = section;
	}

	public int getGrade() {
		return grade;
	}

	public int getSchoolinglen() {
		return schoolinglen;
	}

	public void setSchoolinglen(int schoolinglen) {
		this.schoolinglen = schoolinglen;
	}

	public void setGrade(int grade) {
		this.grade = grade;
	}

	public String getGradename() {
		return gradename;
	}

	public void setGradename(String gradename) {
		this.gradename = gradename;
	}

	public String getSubschoolid() {
		return subschoolid;
	}

	public void setSubschoolid(String subschoolid) {
		this.subschoolid = subschoolid;
	}

	public String getKinClassCode() {
		return kinClassCode;
	}

	public void setKinClassCode(String kinClassCode) {
		this.kinClassCode = kinClassCode;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getKinClassName() {
		return kinClassName;
	}

	public void setKinClassName(String kinClassName) {
		this.kinClassName = kinClassName;
	}

	public String getRangeType() {
		return rangeType;
	}

	public void setRangeType(String rangeType) {
		this.rangeType = rangeType;
	}
}
