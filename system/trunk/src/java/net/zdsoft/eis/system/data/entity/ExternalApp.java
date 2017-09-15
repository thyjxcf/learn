package net.zdsoft.eis.system.data.entity;

import net.zdsoft.eis.base.photo.PhotoEntity;

public class ExternalApp extends PhotoEntity{

	private static final long serialVersionUID = -981898879826112467L;
	
	public static final int EXTERNAL_APP =1;
	
	public static final int BROADCAST_CLASSROOM =2;
	
	private String unitId;
	
	private int type;
	
	private String appName;
	
	private String appUrl;
	
	private int orderNo;
	private boolean temp = false;// 是否临时，默认否
	
	//辅助字段
	private String unitName;

	public String getUnitId() {
		return unitId;
	}

	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public String getAppUrl() {
		return appUrl;
	}

	public void setAppUrl(String appUrl) {
		this.appUrl = appUrl;
	}

	public int getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(int orderNo) {
		this.orderNo = orderNo;
	}

	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	@Override
	public String getObjectType() {
		return "externalApp";
	}

	public boolean getTemp() {
		return temp;
	}

	public void setTemp(boolean temp) {
		this.temp = temp;
	}

}
