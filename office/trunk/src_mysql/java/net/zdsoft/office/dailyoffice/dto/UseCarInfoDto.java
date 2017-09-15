package net.zdsoft.office.dailyoffice.dto;

import java.util.*;

import net.zdsoft.office.dailyoffice.entity.OfficeCarApply;

public class UseCarInfoDto {
	
	private String driverId;
	private String driverName;//驾驶员姓名
	private String carId;
	private String carNumber;//车辆编号
	private int useCarNumber;//出车次数
	private int seating;//可容纳人数
	private Float totalOvertimeNum;
	private float sumSubsidy;//总补贴金额
	
	public float getSumSubsidy() {
		return sumSubsidy;
	}

	public void setSumSubsidy(float sumSubsidy) {
		this.sumSubsidy = sumSubsidy;
	}

	private List<OfficeCarApply> officeCarApplyList;

	public String getDriverId() {
		return driverId;
	}

	public void setDriverId(String driverId) {
		this.driverId = driverId;
	}

	public String getDriverName() {
		return driverName;
	}

	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}

	public String getCarId() {
		return carId;
	}

	public void setCarId(String carId) {
		this.carId = carId;
	}

	public String getCarNumber() {
		return carNumber;
	}

	public void setCarNumber(String carNumber) {
		this.carNumber = carNumber;
	}

	public int getUseCarNumber() {
		return useCarNumber;
	}

	public void setUseCarNumber(int useCarNumber) {
		this.useCarNumber = useCarNumber;
	}
	
	public int getSeating() {
		return seating;
	}

	public void setSeating(int seating) {
		this.seating = seating;
	}

	public Float getTotalOvertimeNum() {
		return totalOvertimeNum;
	}

	public void setTotalOvertimeNum(Float totalOvertimeNum) {
		this.totalOvertimeNum = totalOvertimeNum;
	}

	public List<OfficeCarApply> getOfficeCarApplyList() {
		return officeCarApplyList;
	}

	public void setOfficeCarApplyList(List<OfficeCarApply> officeCarApplyList) {
		this.officeCarApplyList = officeCarApplyList;
	}
	
}