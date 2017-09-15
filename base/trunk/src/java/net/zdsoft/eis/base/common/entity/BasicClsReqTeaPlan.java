package net.zdsoft.eis.base.common.entity;

import net.zdsoft.eis.frame.client.BaseEntity;

public class BasicClsReqTeaPlan extends BaseEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String unitId;
	private String acadyear;
	private String semster;
	
	private String claid;
	private String subid;
	//中职对应多个id
	private String teaid;
	//周课时  实际上是CourseHour
	private int weekCourseHour;
	//是否需要通过电子版本打卡
	private int punchCard;
	
	public int getPunchCard() {
		return punchCard;
	}
	public void setPunchCard(int punchCard) {
		this.punchCard = punchCard;
	}
	public int getWeekCourseHour() {
		return weekCourseHour;
	}
	public void setWeekCourseHour(int weekCourseHour) {
		this.weekCourseHour = weekCourseHour;
	}
	public String getClaid() {
		return claid;
	}
	public void setClaid(String claid) {
		this.claid = claid;
	}
	public String getTeaid() {
		return teaid;
	}
	public void setTeaid(String teaid) {
		this.teaid = teaid;
	}
	public String getSubid() {
		return subid;
	}
	public void setSubid(String subid) {
		this.subid = subid;
	}
	public String getUnitId() {
		return unitId;
	}
	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}
	public String getAcadyear() {
		return acadyear;
	}
	public void setAcadyear(String acadyear) {
		this.acadyear = acadyear;
	}
	public String getSemster() {
		return semster;
	}
	public void setSemster(String semster) {
		this.semster = semster;
	}
	
}
