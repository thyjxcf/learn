package net.zdsoft.eis.base.subsystemcall.entity;
import java.util.Date;

import net.zdsoft.eis.frame.client.BaseEntity;

public class StusysTimeFrame extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	private String familyId;
	/**
	 * 居住证时段01，莞购买过社保02
	 */
	private String timeType;
	/**
	 * 
	 */
	private Date startTime;
	/**
	 * 
	 */
	private  Date endTime;
	public String getFamilyId() {
		return familyId;
	}
	public void setFamilyId(String familyId) {
		this.familyId = familyId;
	}
	public String getTimeType() {
		return timeType;
	}
	public void setTimeType(String timeType) {
		this.timeType = timeType;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

}
