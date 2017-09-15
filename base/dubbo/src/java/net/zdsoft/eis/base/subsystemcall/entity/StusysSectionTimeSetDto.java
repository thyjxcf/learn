package net.zdsoft.eis.base.subsystemcall.entity;

import java.util.Calendar;

/**
 * 节次时间设置
 * 
 * @author weixh
 * @since 2014-1-23
 */
public class StusysSectionTimeSetDto {
	private String unitId;
	private Integer sectionNumber;// 节次
	private String beginTime;// 开始时间 eg: 10:24,15:30
	private String endTime;// 结束时间
	
	private Calendar beginCt;
	private Calendar endCt;

	public Integer getSectionNumber() {
		return sectionNumber;
	}

	public void setSectionNumber(Integer sectionNumber) {
		this.sectionNumber = sectionNumber;
	}

	public String getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(String beginTime) {
		this.beginTime = beginTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getUnitId() {
		return unitId;
	}

	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}

	public Calendar getBeginCt() {
		return beginCt;
	}

	public void setBeginCt(Calendar beginCt) {
		this.beginCt = beginCt;
	}

	public Calendar getEndCt() {
		return endCt;
	}

	public void setEndCt(Calendar endCt) {
		this.endCt = endCt;
	}

}
