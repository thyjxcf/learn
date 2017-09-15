package net.zdsoft.eis.base.common.entity;

import net.zdsoft.keelcnet.entity.EntityObject;

public class BasicModule extends EntityObject {
    private static final long serialVersionUID = 1L;
    private String mid;
    private Long parentid;
    private Integer orderid;
    private String name;
    private String type;
    private String picture;
    private String url;
    private Integer subsystem;
    private String usertype;// 用户操作类型
    private Integer unitclass;// 单位分类（教育局、学校）
    private Integer isassigned;
    private String description;
    private String limit;
    private Integer uselevel;
    private Integer actionenable;
    private boolean common; // 是否是公共模块
    private Integer isActive;// 对应数据库中的mark
    private String dataSubsystems;// 该模块中的数据涉及到哪些子系统
    private String win;

    public Integer getActionenable() {
        return actionenable;
    }

    public void setActionenable(Integer actionenable) {
        this.actionenable = actionenable;
    }

    public boolean isCommon() {
        return common;
    }

    public void setCommon(boolean common) {
        this.common = common;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getIsassigned() {
        return isassigned;
    }

    public void setIsassigned(Integer isassigned) {
        this.isassigned = isassigned;
    }

    public String getLimit() {
        return limit;
    }

    public void setLimit(String limit) {
        this.limit = limit;
    }

    public String getMid() {
        return mid;
    }

    public void setMid(String mid) {
        this.mid = mid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getOrderid() {
        return orderid;
    }

    public void setOrderid(Integer orderid) {
        this.orderid = orderid;
    }

    public Long getParentid() {
        return parentid;
    }

    public void setParentid(Long parentid) {
        this.parentid = parentid;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public Integer getSubsystem() {
        return subsystem;
    }

    public void setSubsystem(Integer subsystem) {
        this.subsystem = subsystem;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getUnitclass() {
        return unitclass;
    }

    public void setUnitclass(Integer unitclass) {
        this.unitclass = unitclass;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getUselevel() {
        return uselevel;
    }

    public void setUselevel(Integer uselevel) {
        this.uselevel = uselevel;
    }

    public String getUsertype() {
        return usertype;
    }

    public void setUsertype(String usertype) {
        this.usertype = usertype;
    }

    public Integer getIsActive() {
        return isActive;
    }

    public void setIsActive(Integer isActive) {
        this.isActive = isActive;
    }

    public String getDataSubsystems() {
        return dataSubsystems;
    }

    public void setDataSubsystems(String dataSubsystems) {
        this.dataSubsystems = dataSubsystems;
    }

	public String getWin() {
		return win;
	}

	public void setWin(String win) {
		this.win = win;
	}

}
