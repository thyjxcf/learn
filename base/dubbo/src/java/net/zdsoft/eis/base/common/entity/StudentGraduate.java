package net.zdsoft.eis.base.common.entity;

import java.util.Date;

import net.zdsoft.eis.frame.client.BaseEntity;

/* 
 * <p>ZDSoft学籍系统（stusys）V3.5</p>
 * @author Dongzk
 * @since 1.0
 * @version $Id: StudentGraduate.java,v 1.2 2007/01/09 10:03:49 jiangl Exp $
 */
public class StudentGraduate extends BaseEntity {
    private static final long serialVersionUID = 354457204054553264L;
    
    private String schid; // 学校ID
    private String stuid; // 学生ID
    private String bytype; // 毕业类型（微代码DM-JYJG）
    private Date bydate; // 毕业日期
    private String bynumber; // 毕业证书号码
    private String byto; // 毕业去向(微代码DM-BYQX)
    private String flowexplain; // 毕业评语
    private String acadyear; // 毕业学年
    private String semester; // 毕业学期
    private String operator; // 操作人姓名
    private String operateunit; // 操作人单位名称

    private String stuname; // 学生姓名
    private String stucode; // 学号
    private String unitivecode; // 学籍号
    private String classid; // 所在班级编号毕业班级
    private String stuClassid;// 指的是basestudent表中的班级id 用来来做逻辑判断是否可以修改now_state状态
    
    private String graduateReason; // 结束学业说明
	
    public String getStuClassid() {
		return stuClassid;
	}

	public void setStuClassid(String stuClassid) {
		this.stuClassid = stuClassid;
	}

	

    public StudentGraduate() {
    }

    public Date getBydate() {
        return bydate;
    }

    public void setBydate(Date bydate) {
        this.bydate = bydate;
    }

    public String getBynumber() {
        return bynumber;
    }

    public void setBynumber(String bynumber) {
        this.bynumber = bynumber;
    }

    public String getByto() {
        return byto;
    }

    public void setByto(String byto) {
        this.byto = byto;
    }

    public String getBytype() {
        return bytype;
    }

    public void setBytype(String bytype) {
        this.bytype = bytype;
    }

    public String getFlowexplain() {
        return flowexplain;
    }

    public void setFlowexplain(String flowexplain) {
        this.flowexplain = flowexplain;
    }

    public String getOperateunit() {
        return operateunit;
    }

    public void setOperateunit(String operateunit) {
        this.operateunit = operateunit;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getSchid() {
        return schid;
    }

    public void setSchid(String schid) {
        this.schid = schid;
    }

    public String getStuid() {
        return stuid;
    }

    public void setStuid(String stuid) {
        this.stuid = stuid;
    }

    public String getStucode() {
        return stucode;
    }

    public void setStucode(String stucode) {
        this.stucode = stucode;
    }

    public String getStuname() {
        return stuname;
    }

    public void setStuname(String stuname) {
        this.stuname = stuname;
    }

    public String getUnitivecode() {
        return unitivecode;
    }

    public void setUnitivecode(String unitivecode) {
        this.unitivecode = unitivecode;
    }

    public String getClassid() {
        return classid;
    }

    public void setClassid(String classid) {
        this.classid = classid;
    }

    public String getGraduateReason() {
        return graduateReason;
    }

    public void setGraduateReason(String graduateReason) {
        this.graduateReason = graduateReason;
    }

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

}
