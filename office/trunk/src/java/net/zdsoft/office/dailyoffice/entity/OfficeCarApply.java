package net.zdsoft.office.dailyoffice.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
/**
 * office_car_apply
 * @author 
 * 
 */
public class OfficeCarApply implements Serializable{
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	private String id;
	/**
	 * 
	 */
	private String unitId;
	/**
	 * 
	 */
	private String applyUserId;
	/**
	 * 
	 */
	private Date applyTime;
	/**
	 * 
	 */
	private String deptId;
	/**
	 * 
	 */
	private String linkUserId;
	/**
	 * 
	 */
	private String mobilePhone;
	/**
	 * 
	 */
	private Integer personNumber;
	/**
	 * 
	 */
	private String reason;
	/**
	 * 
	 */
	private String carLocation;
	/**
	 * 
	 */
	private Boolean isGoback;
	/**
	 * 
	 */
	private Boolean isNeedWaiting;
	/**
	 * 
	 */
	private Timestamp waitingTime;
	/**
	 * 
	 */
	private Timestamp useTime;
	/**
	 * 
	 */
	private Boolean isDeleted;
	/**
	 * 
	 */
	private String auditUserId;
	/**
	 *  1：未提交
		2：待审核
		3：已通过
		4：未通过
	 */
	private Integer state;
	/**
	 * 
	 */
	private Date auditTime;
	/**
	 * 
	 */
	private String area;
	private String areaStr;
	private float areaSubsidy;
	private String xinqi2;
	private boolean timeOut;
	
	public String getXinqi2() {
		return xinqi2;
	}
	public void setXinqi2(String xinqi2) {
		this.xinqi2 = xinqi2;
	}
	/**
	 * 
	 */
	private String carId;
	/**
	 * 
	 */
	private String driverId;
	/**
	 * 
	 */
	private String remark;
	/**
	 * 
	 */
	private Float overtimeNumber;
	
	private boolean isOvertime;
	
	private String applyUserName;//申请人姓名
	private String deptName;//用车部门
	private String linkUserName;//乘车联系人
	private String auditUserName;//审核人姓名
	private String carNumber;//车牌号
	private String driverName;//驾驶员
	private float subsidy;//补贴金额
	private String xinqi;
	public String getXinqi() {
		return xinqi;
	}
	public void setXinqi(String xinqi) {
		this.xinqi = xinqi;
	}
	public float getSubsidy() {
		return subsidy;
	}
	public void setSubsidy(float subsidy) {
		this.subsidy = subsidy;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUnitId() {
		return unitId;
	}
	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}
	public String getApplyUserId() {
		return applyUserId;
	}
	public void setApplyUserId(String applyUserId) {
		this.applyUserId = applyUserId;
	}
	public Date getApplyTime() {
		return applyTime;
	}
	public void setApplyTime(Date applyTime) {
		this.applyTime = applyTime;
	}
	public String getDeptId() {
		return deptId;
	}
	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}
	public String getLinkUserId() {
		return linkUserId;
	}
	public void setLinkUserId(String linkUserId) {
		this.linkUserId = linkUserId;
	}
	public String getMobilePhone() {
		return mobilePhone;
	}
	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}
	public Integer getPersonNumber() {
		return personNumber;
	}
	public void setPersonNumber(Integer personNumber) {
		this.personNumber = personNumber;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public String getCarLocation() {
		return carLocation;
	}
	public void setCarLocation(String carLocation) {
		this.carLocation = carLocation;
	}
	public Boolean getIsGoback() {
		return isGoback;
	}
	public void setIsGoback(Boolean isGoback) {
		this.isGoback = isGoback;
	}
	public Boolean getIsNeedWaiting() {
		return isNeedWaiting;
	}
	public void setIsNeedWaiting(Boolean isNeedWaiting) {
		this.isNeedWaiting = isNeedWaiting;
	}
	public Timestamp getWaitingTime() {
		return waitingTime;
	}
	public void setWaitingTime(Timestamp waitingTime) {
		this.waitingTime = waitingTime;
	}
	public Timestamp getUseTime() {
		return useTime;
	}
	public void setUseTime(Timestamp useTime) {
		this.useTime = useTime;
	}
	public Boolean getIsDeleted() {
		return isDeleted;
	}
	public void setIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
	}
	public String getAuditUserId() {
		return auditUserId;
	}
	public void setAuditUserId(String auditUserId) {
		this.auditUserId = auditUserId;
	}
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	public Date getAuditTime() {
		return auditTime;
	}
	public void setAuditTime(Date auditTime) {
		this.auditTime = auditTime;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public String getCarId() {
		return carId;
	}
	public void setCarId(String carId) {
		this.carId = carId;
	}
	public String getDriverId() {
		return driverId;
	}
	public void setDriverId(String driverId) {
		this.driverId = driverId;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Float getOvertimeNumber() {
		return overtimeNumber;
	}
	public void setOvertimeNumber(Float overtimeNumber) {
		this.overtimeNumber = overtimeNumber;
	}
	public String getApplyUserName() {
		return applyUserName;
	}
	public void setApplyUserName(String applyUserName) {
		this.applyUserName = applyUserName;
	}
	public String getLinkUserName() {
		return linkUserName;
	}
	public void setLinkUserName(String linkUserName) {
		this.linkUserName = linkUserName;
	}
	public String getAuditUserName() {
		return auditUserName;
	}
	public void setAuditUserName(String auditUserName) {
		this.auditUserName = auditUserName;
	}
	public String getCarNumber() {
		return carNumber;
	}
	public void setCarNumber(String carNumber) {
		this.carNumber = carNumber;
	}
	public String getDriverName() {
		return driverName;
	}
	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}
	public String getDeptName() {
		return deptName;
	}
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	public boolean getIsOvertime() {
		return isOvertime;
	}
	public void setIsOvertime(boolean isOvertime) {
		this.isOvertime = isOvertime;
	}
	public String getAreaStr() {
		return areaStr;
	}
	public void setAreaStr(String areaStr) {
		this.areaStr = areaStr;
	}
	public float getAreaSubsidy() {
		return areaSubsidy;
	}
	public void setAreaSubsidy(float areaSubsidy) {
		this.areaSubsidy = areaSubsidy;
	}
	public void setOvertime(boolean isOvertime) {
		this.isOvertime = isOvertime;
	}
	public boolean isTimeOut() {
		return timeOut;
	}
	public void setTimeOut(boolean timeOut) {
		this.timeOut = timeOut;
	}
	
}