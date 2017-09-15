package net.zdsoft.office.dailyoffice.dto;

import java.util.List;

import net.zdsoft.office.dailyoffice.entity.OfficeCarApply;


public class OfficeCarApplyDto {
	private String driverName;//驾驶员
	private String driverId;
	private String count;//出车次数
	private List<OfficeCarApply> officeCarApplyList;
	private Float overtimeNumber;//加班时间 
	private float subsidy;//补贴金额总数
	public String getDriverName() {
		return driverName;
	}
	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}
	public String getCount() {
		return count;
	}
	public void setCount(String count) {
		this.count = count;
	}
	public List<OfficeCarApply> getOfficeCarApplyList() {
		return officeCarApplyList;
	}
	public void setOfficeCarApplyList(List<OfficeCarApply> officeCarApplyList) {
		this.officeCarApplyList = officeCarApplyList;
	}
	public Float getOvertimeNumber() {
		return overtimeNumber;
	}
	public void setOvertimeNumber(Float overtimeNumber) {
		this.overtimeNumber = overtimeNumber;
	}
	public String getDriverId() {
		return driverId;
	}
	public void setDriverId(String driverId) {
		this.driverId = driverId;
	}
	public float getSubsidy() {
		return subsidy;
	}
	public void setSubsidy(float subsidy) {
		this.subsidy = subsidy;
	}
}
