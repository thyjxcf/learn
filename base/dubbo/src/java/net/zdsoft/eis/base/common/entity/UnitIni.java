package net.zdsoft.eis.base.common.entity;

import net.zdsoft.keelcnet.entity.EntityObject;

public class UnitIni extends EntityObject {
    private static final long serialVersionUID = -7943508945931742527L;
    
    private String iniid;
    private String name;
    private String defaultValue;
    private String description;
    private String nowValue;
    private Integer isVisible;
    private String flag;
    private String unitid;
    private String validatejs;
    private Integer coercive;
    private Integer orderid;

	/**
	 * 选课模式切换
	 */
	public static final String ELECTIVE_MODE_SWITCH="ELECTIVE.MODE.SWITCH";

	/**
	 * 新增用户密码生成规则设置
	 */
	public static final String UNIT_PASSWORD_CONFIG = "UNIT.PASSWORD";
    

    public void setValidatejs(String validatejs) {
        this.validatejs = validatejs;
    }

    public void setCoercive(Integer coercive) {
        this.coercive = coercive;
    }

    public String getValidatejs() {
        return validatejs;
    }

    public Integer getCoercive() {
        return coercive;
    }

    public Integer getOrderid() {
        return orderid;
    }

    public void setOrderid(Integer orderid) {
        this.orderid = orderid;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public String getDescription() {
        return description;
    }

    public String getIniid() {
        return iniid;
    }

    public Integer getIsVisible() {
        return isVisible;
    }

    public String getName() {
        return name;
    }

    public String getNowValue() {
        return nowValue;
    }

    public String getUnitid() {
        return unitid;
    }

    public String getFlag() {
        return flag;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setIniid(String iniid) {
        this.iniid = iniid;
    }

    public void setIsVisible(Integer isVisible) {
        this.isVisible = isVisible;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNowValue(String nowValue) {
        this.nowValue = nowValue;
    }

    public void setUnitid(String unitid) {
        this.unitid = unitid;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }
}
