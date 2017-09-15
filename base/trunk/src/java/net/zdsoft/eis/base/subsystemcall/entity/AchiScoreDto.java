package net.zdsoft.eis.base.subsystemcall.entity;

import java.util.List;

/**
 * 奖学金查看学生成绩使用
 * @author wanglp
 *
 */
public class AchiScoreDto {
	
	private String studentId;//学生id
	private String subjectId;//课程id
	private String stucode;//学号
	private String stuname;//学生名字
	private String subjectName;//课程名
	
	private Double totalScore;//总分
	private Double avgScore;//平均分
	private Double totalPoint;//绩点总分
	private Double avgPoint;//平均绩点
	private Double moralScore;//德育成绩
	//存放学生各科目成绩 object[0]:subjectId,object[1]:subjectName,object[2]:score(成绩),object[3]:point(绩点)
	private List<Object[]> subjScore;
	
	
	public String getStudentId() {
		return studentId;
	}
	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}
	public String getSubjectId() {
		return subjectId;
	}
	public void setSubjectId(String subjectId) {
		this.subjectId = subjectId;
	}
	public String getStucode() {
		return stucode;
	}
	public void setStucode(String stucode) {
		this.stucode = stucode;
	}
	public String getSubjectName() {
		return subjectName;
	}
	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}
	public Double getTotalScore() {
		return totalScore;
	}
	public void setTotalScore(Double totalScore) {
		this.totalScore = totalScore;
	}
	public Double getAvgScore() {
		return avgScore;
	}
	public void setAvgScore(Double avgScore) {
		this.avgScore = avgScore;
	}
	public Double getAvgPoint() {
		return avgPoint;
	}
	public void setAvgPoint(Double avgPoint) {
		this.avgPoint = avgPoint;
	}
	public Double getMoralScore() {
		return moralScore;
	}
	public void setMoralScore(Double moralScore) {
		this.moralScore = moralScore;
	}
	public List<Object[]> getSubjScore() {
		return subjScore;
	}
	public void setSubjScore(List<Object[]> subjScore) {
		this.subjScore = subjScore;
	}
	public Double getTotalPoint() {
		return totalPoint;
	}
	public void setTotalPoint(Double totalPoint) {
		this.totalPoint = totalPoint;
	}
	public String getStuname() {
		return stuname;
	}
	public void setStuname(String stuname) {
		this.stuname = stuname;
	}

}
