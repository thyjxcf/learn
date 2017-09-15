package net.zdsoft.eis.base.common.entity;

import java.io.Serializable;

import net.zdsoft.eis.frame.client.BaseEntity;
import net.zdsoft.keel.dao.UUIDGenerator;


public class SystemIni extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 2806479867082969428L;

	public static final String SYSTEM_PASSPORT_SWITCH = "SYSTEM.PASSPORT.SWITCH";
	public static final String SYSTEM_USE_EISLOGIN_FOR_PASSPORT = "SYSTEM.USE.EISLOGIN.FOR.PASSPORT";
	private String iniid;
	private String name;
	private String defaultValue;
	private String description;
	private String nowValue;
	private Integer isVisible;
	private String validateJS;
	private Integer orderId;
	private String subSystemId;
	private Integer coercive;

	public SystemIni() {
	}

	public String getDescription() {
		if (description != null) {
			return description.trim();
		}
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getIniid() {
		if (iniid != null) {
			return iniid.trim();
		}
		return iniid;
	}

	public void setIniid(String iniid) {
		this.iniid = iniid;
	}

	public int getIsVisible() {
		return isVisible;
	}

	public void setIsVisible(int isVisible) {
		this.isVisible = isVisible;
	}

	public String getName() {
		if (name != null) {
			return name.trim();
		}
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNowValue() {
		if (nowValue != null) {
			return nowValue.trim();
		}
		return nowValue;
	}

	public void setNowValue(String nowValue) {
		this.nowValue = nowValue;
	}

	public String getDefaultValue() {
		if (defaultValue != null) {
			return defaultValue.trim();
		}
		return defaultValue;
	}

	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}

	public String getValidateJS() {
		return validateJS;
	}

	public void setValidateJS(String validateJS) {
		this.validateJS = validateJS;
	}

	public String getSubSystemId() {
		return subSystemId;
	}

	public void setSubSystemId(String subSystemId) {
		this.subSystemId = subSystemId;
	}

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public Integer getCoercive() {
		return coercive;
	}

	public void setCoercive(Integer coercive) {
		this.coercive = coercive;
	}

	public void setIsVisible(Integer isVisible) {
		this.isVisible = isVisible;
	}

	public static void main(String[] args) {
	    UUIDGenerator uuid = new UUIDGenerator();
	    for (int i = 0; i < 100; i++) {
	        System.out.println(uuid.generateHex());
        }
	    
    }
}
