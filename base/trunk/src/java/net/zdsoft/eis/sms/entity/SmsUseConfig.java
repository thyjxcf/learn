package net.zdsoft.eis.sms.entity;

import net.zdsoft.eis.frame.client.BaseEntity;

/**
 * 单位短信启用配置表
 * 
 * @author jiangf
 * @version 创建时间：2011-8-5 下午03:18:24
 */

public class SmsUseConfig extends BaseEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6976151375398698905L;

	private String unitId;

	private String sign;

	private String name;

	private int isused;

	private String nowvalue;

	public String getUnitId() {
		return unitId;
	}

	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getIsused() {
		return isused;
	}

	public void setIsused(int isused) {
		this.isused = isused;
	}

	public String getNowvalue() {
		return nowvalue;
	}

	public void setNowvalue(String nowvalue) {
		this.nowvalue = nowvalue;
	}
}
