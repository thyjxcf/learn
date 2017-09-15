/* 
 * @(#)SimpleClass.java    Created on May 14, 2011
 * Copyright (c) 2011 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.eis.base.simple.entity;

import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;

import net.zdsoft.eis.frame.client.BaseEntity;

/**
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: May 14, 2011 2:14:10 PM $
 */
public class SimpleClass extends BaseEntity {

	/**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = 7225889384428503792L;

	@JSONField(name="schoolId")
	private String schid; // 学校代码
	private String classcode; // 班级代码
	private String classname; // 班级名称
	private String acadyear; // 入学学年
	private String classtype; // 班级类型
	private Date datecreated; // 建班年月
	private int graduatesign; // 毕业标志
	private Date graduatedate; // 毕业日期
	@JSONField(name="schoolingLength")
	private int schoolinglen; // 学制
	private String teacherid; // 班主任id
	private String stuid; // 班长id
	private String subschoolid; // 校区id
	private String gradeId; // 班级所在年级id
	private String viceTeacherId; // 副班主任

	private long displayOrder; // 排序号
	private String lifeGuideTeacherId;//生活指导老师
	private String campusId; //分校区id
	 /**
     * 所属学段（微代码表示：0幼儿园，1小学，2初中，3高中）
     */
    private int section;
    
	// =====================辅助字段==========================
	private String classnamedynamic; // 根据生成规则班级名称动态生成的部分
	private String gradeName; // 班级所在年级名称
	private int stucount; // 班级人数
	private String teachername; // 班主任姓名
	private String viceTeacherName;// 副班主任姓名
	private String monitor; // 班长名字
	private String subschoolName; // 分校区名称
	private String checkbox; // 标识角色是否对其有权限
	private Integer state;// 是否有效
	
	private String partnership;//合作性质
    private String partners;//合作单位
    private String contacts;//单位联系人
    private String remark;// 备注
    
    public int getSection() {
		return section;
	}

	public void setSection(int section) {
		this.section = section;
	}

	//2015-05-12  add by like
    private String lifeGuideTeacherName;
    
    public String getLifeGuideTeacherId() {
		return lifeGuideTeacherId;
	}

	public void setLifeGuideTeacherId(String lifeGuideTeacherId) {
		this.lifeGuideTeacherId = lifeGuideTeacherId;
	}

	public String getLifeGuideTeacherName() {
		return lifeGuideTeacherName;
	}

	public void setLifeGuideTeacherName(String lifeGuideTeacherName) {
		this.lifeGuideTeacherName = lifeGuideTeacherName;
	}

	public String getPartnership() {
		return partnership;
	}

	public void setPartnership(String partnership) {
		this.partnership = partnership;
	}

	public String getPartners() {
		return partners;
	}

	public void setPartners(String partners) {
		this.partners = partners;
	}

	public String getContacts() {
		return contacts;
	}

	public void setContacts(String contacts) {
		this.contacts = contacts;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public String getSchid() {
		return schid;
	}

	public void setSchid(String schid) {
		this.schid = schid;
	}

	public String getClasscode() {
		return classcode;
	}

	public void setClasscode(String classcode) {
		this.classcode = classcode;
	}

	public String getClassname() {
		return classname;
	}

	public void setClassname(String classname) {
		this.classname = classname;
	}

	public String getClasstype() {
		return classtype;
	}

	public void setClasstype(String classtype) {
		this.classtype = classtype;
	}

	public String getAcadyear() {
		return acadyear;
	}

	public void setAcadyear(String acadyear) {
		this.acadyear = acadyear;
	}

	public Date getDatecreated() {
		return datecreated;
	}

	public void setDatecreated(Date datecreated) {
		this.datecreated = datecreated;
	}

	public int getGraduatesign() {
		return graduatesign;
	}

	public void setGraduatesign(int graduatesign) {
		this.graduatesign = graduatesign;
	}

	public int getSchoolinglen() {
		return schoolinglen;
	}

	public void setSchoolinglen(int schoolinglen) {
		this.schoolinglen = schoolinglen;
	}

	public String getTeacherid() {
		return teacherid;
	}

	public void setTeacherid(String teacherid) {
		this.teacherid = teacherid;
	}

	public String getStuid() {
		return stuid;
	}

	public void setStuid(String stuid) {
		this.stuid = stuid;
	}

	public String getSubschoolid() {
		return subschoolid;
	}

	public void setSubschoolid(String subschoolid) {
		this.subschoolid = subschoolid;
	}

	public String getGradeId() {
		return gradeId;
	}

	public void setGradeId(String gradeId) {
		this.gradeId = gradeId;
	}

	public String getViceTeacherId() {
		return viceTeacherId;
	}

	public void setViceTeacherId(String viceTeacherId) {
		this.viceTeacherId = viceTeacherId;
	}

	public long getDisplayOrder() {
		return displayOrder;
	}

	public void setDisplayOrder(long displayOrder) {
		this.displayOrder = displayOrder;
	}

	public Date getGraduatedate() {
		return graduatedate;
	}

	public void setGraduatedate(Date graduatedate) {
		this.graduatedate = graduatedate;
	}

	public String getGradeName() {
		return gradeName;
	}

	public void setGradeName(String gradeName) {
		this.gradeName = gradeName;
	}

	public int getStucount() {
		return stucount;
	}

	public void setStucount(int stucount) {
		this.stucount = stucount;
	}

	public String getTeachername() {
		return teachername;
	}

	public void setTeachername(String teachername) {
		this.teachername = teachername;
	}

	public String getViceTeacherName() {
		return viceTeacherName;
	}

	public void setViceTeacherName(String viceTeacherName) {
		this.viceTeacherName = viceTeacherName;
	}

	public String getMonitor() {
		return monitor;
	}

	public void setMonitor(String monitor) {
		this.monitor = monitor;
	}

	public String getSubschoolName() {
		return subschoolName;
	}

	public void setSubschoolName(String subschoolName) {
		this.subschoolName = subschoolName;
	}

	public String getCheckbox() {
		return checkbox;
	}

	public void setCheckbox(String checkbox) {
		this.checkbox = checkbox;
	}

	public String getClassnamedynamic() {
		return classnamedynamic;
	}

	public void setClassnamedynamic(String classnamedynamic) {
		this.classnamedynamic = classnamedynamic;
	}
	
	public String getCampusId() {
		return campusId;
	}

	public void setCampusId(String campusId) {
		this.campusId = campusId;
	}
}
