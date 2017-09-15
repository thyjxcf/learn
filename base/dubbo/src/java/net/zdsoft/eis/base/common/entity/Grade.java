/* 
 * @(#)BasicGrade.java    Created on 2007-7-17
 * Copyright (c) 2006 ZDSoft Networks, Inc. All rights reserved.
 * $Header$
 */
package net.zdsoft.eis.base.common.entity;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.annotation.JSONField;

import net.zdsoft.eis.base.util.SUtils;
import net.zdsoft.eis.frame.client.BaseEntity;

public class Grade extends BaseEntity {
	
	public static List<Grade> dt(String data) {
		List<Grade> ts = SUtils.dt(data, new TypeReference<List<Grade>>() {
		});
		if (ts == null)
			ts = new ArrayList<Grade>();
		return ts;

	}

	public static Grade dc(String data) {
		return SUtils.dc(data, Grade.class);
	}
	
    private static final long serialVersionUID = 1206770150063040398L;
    
    /**
     * 年级名称
     */
    private String gradename;
    /**
     * 学校Id
     */
    @JSONField(name = "schoolId")
    private String schid;
    /**
     * 班主任Id
     */
    private String teacherId;
    /**
     * 上午课程数
     */
    private int amLessonCount;
    /**
     * 下午课程数
     */
    private int pmLessonCount;
    /**
     * 晚上课程数
     */
    private int nightLessonCount;
    /**
     * 开设学年openAcadyear
     */
    @JSONField(name = "openAcadyear")
    private String acadyear;
    /**
     * 学制
     */
    @JSONField(name = "schoolinglength")
    private int schoolinglen;
    /**
     * 学段
     */
    private int section;
    /**
     * 是否毕业
     */
    private Integer isGraduated;
    /**
     * 排序号
     */
    private long displayOrder;
    /**
     * 年级代码：学段 + 几年级
     */
    private String gradeCode;

    //=======================台账属性上提 by zhangkc 2015-05-06========================
    /**
     * 分校区ID（主体校或附设班）
     */
    private String subschoolId; 
    
    //==================辅助字段====================
    private String currAcadyear;//当前选择得学年
    private String teacherName;
    private int grade; // 年级级别（1，2，3，……）

    public int getAmLessonCount() {
        return amLessonCount;
    }

    public void setAmLessonCount(int am_lessonCount) {
        this.amLessonCount = am_lessonCount;
    }


    public Integer getIsGraduated() {
        return isGraduated;
    }

    public void setIsGraduated(Integer isGraduated) {
        this.isGraduated = isGraduated;
    }

    public String getGradename() {
        return gradename;
    }

    public void setGradename(String name) {
        this.gradename = name;
    }

    public int getNightLessonCount() {
        return nightLessonCount;
    }

    public void setNightLessonCount(int nightLessonCount) {
        this.nightLessonCount = nightLessonCount;
    }

    public String getAcadyear() {
        return acadyear;
    }

    public void setAcadyear(String openAcadyear) {
        this.acadyear = openAcadyear;
    }

    public int getPmLessonCount() {
        return pmLessonCount;
    }

    public void setPmLessonCount(int pm_lessonCount) {
        this.pmLessonCount = pm_lessonCount;
    }

    public String getSchid() {
        return schid;
    }

    public void setSchid(String schoolId) {
        this.schid = schoolId;
    }

   

    public int getSection() {
        return section;
    }

    public void setSection(int section) {
        this.section = section;
    }

    public int getSchoolinglen() {
        return schoolinglen;
    }

    public void setSchoolinglen(int structure) {
        this.schoolinglen = structure;
    }

    public String getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId;
    }

    public long getDisplayOrder() {
        return displayOrder;
    }

    public void setDisplayOrder(long displayOrder) {
        this.displayOrder = displayOrder;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public String getSubschoolId() {
        return subschoolId;
    }

    public void setSubschoolId(String subschoolId) {
        this.subschoolId = subschoolId;
    }

    public String getCurrAcadyear() {
        return currAcadyear;
    }

    public void setCurrAcadyear(String currAcadyear) {
        this.currAcadyear = currAcadyear;
    }
    
    public int getGradeInt(String curAcadyear){
        // 取得年级级别     
        int endyear = Integer.parseInt(curAcadyear.substring(5));
        int startyear = Integer.parseInt(acadyear.substring(0, 4));
        int gradeInt = endyear - startyear;
        return gradeInt;
    }

    public String getGradeCode() {
        return gradeCode;
    }

    public void setGradeCode(String gradeCode) {
        this.gradeCode = gradeCode;
    }  
    
    public void setGradeCodeDyn(int grade) {
        this.gradeCode = String.valueOf(section) + String.valueOf(grade);
    }  
    
    public void setGradeCodeDyn( String curAcadyear) {
        int gradeInt = getGradeInt(curAcadyear);
        setGradeCodeDyn(gradeInt);
    }  
}
