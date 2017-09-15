package net.zdsoft.eis.base.subsystemcall.entity;

import net.zdsoft.eis.frame.client.BaseEntity;

/**
 * desktop_app
 * 
 * @author
 * 
 */
public class DesktopAppDto extends BaseEntity{
	private static final long serialVersionUID = 1L;

	private String unitId;
	
	private Integer ownerType;//拥有者类型
	
	private int unitClass;

	private String name;

	private String code;

	private String url;
	
	private String dataUrl;

	private Integer subsystem;

	private String interiorStyle;
	
	private String exteriorStyle;
	
	private String innerStyle;

	private String picture;
	
	private Integer orderId;
	
	private String sourceType;
	
	private String theme;//主题

	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}

	public String getUnitId() {
		return this.unitId;
	}

	public Integer getOwnerType() {
		return ownerType;
	}

	public void setOwnerType(Integer ownerType) {
		this.ownerType = ownerType;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUrl() {
		return this.url;
	}

	public void setSubsystem(Integer subsystem) {
		this.subsystem = subsystem;
	}

	public Integer getSubsystem() {
		return this.subsystem;
	}

	public String getInteriorStyle() {
		return interiorStyle;
	}

	public void setInteriorStyle(String interiorStyle) {
		this.interiorStyle = interiorStyle;
	}

	public String getExteriorStyle() {
		return exteriorStyle;
	}

	public void setExteriorStyle(String exteriorStyle) {
		this.exteriorStyle = exteriorStyle;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public String getPicture() {
		return this.picture;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public Integer getOrderId() {
		return this.orderId;
	}

	public String getSourceType() {
		return sourceType;
	}

	public void setSourceType(String sourceType) {
		this.sourceType = sourceType;
	}

	public int getUnitClass() {
		return unitClass;
	}

	public void setUnitClass(int unitClass) {
		this.unitClass = unitClass;
	}

	public String getDataUrl() {
		return dataUrl;
	}

	public void setDataUrl(String dataUrl) {
		this.dataUrl = dataUrl;
	}

	public String getInnerStyle() {
		return innerStyle;
	}

	public void setInnerStyle(String innerStyle) {
		this.innerStyle = innerStyle;
	}

	public String getTheme() {
		return theme;
	}

	public void setTheme(String theme) {
		this.theme = theme;
	}
}