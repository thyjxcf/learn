package net.zdsoft.eis.base.data.entity;

import java.util.Date;

public class CountOnlineTime {

	private String id;
	
	private String sessionId;
	
	private String userId;
	
	private Date loginTime; 
	
	private Date logoutTime;
	
	private int onlineTime;  //以秒数统计
	
	private String unitId;
	
	//辅助字段
	private String name;  //姓名
	
	private String sex;   //性别
	
	private String phone;   //手机号
	
	private String userName;  //用户名
	
	private String unitName; //单位
	
	private String department;  //部门
	
	private String onlineStaus;  //在线状态
	
	private String onlineHourTime;
	
	private int countTime;  //用于排序
	
	
	

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Date getLoginTime() {
		return loginTime;
	}

	public void setLoginTime(Date loginTime) {
		this.loginTime = loginTime;
	}

	public Date getLogoutTime() {
		return logoutTime;
	}

	public void setLogoutTime(Date logoutTime) {
		this.logoutTime = logoutTime;
	}

	public int getOnlineTime() {
		return onlineTime;
	}

	public void setOnlineTime(int onlineTime) {
		this.onlineTime = onlineTime;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getOnlineStaus() {
		return onlineStaus;
	}

	public void setOnlineStaus(String onlineStaus) {
		this.onlineStaus = onlineStaus;
	}

	public String getUnitId() {
		return unitId;
	}

	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}

	public String getOnlineHourTime() {
		return onlineHourTime;
	}

	public void setOnlineHourTime(String onlineHourTime) {
		this.onlineHourTime = onlineHourTime;
	}

	public int getCountTime() {
		return countTime;
	}

	public void setCountTime(int countTime) {
		this.countTime = countTime;
	}


	
	
	
}
