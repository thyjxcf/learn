package net.zdsoft.eis.system.frame.entity;

import java.util.Date;

import net.zdsoft.eis.frame.client.BaseEntity;

public class UserApp extends BaseEntity {

	private static final long serialVersionUID = -5460887408602735503L;
	/**
	 * 用户id
	 */
	private String userId;
	/**
	 * 模块id
	 */
	private int moduleId;
	/**
	 * 模块parent id
	 */
	private int parentId;
	/**
	 * 子系统
	 */
	private int subsystem;
	/**
	 * 排序号
	 */
	private int orderNo;

	/**
	 * 模块名称
	 */
	private String name;
	
	/**
	 * 模块链接地址
	 */
	private String url;
	
	/**
	 * 图片
	 */
	private String picture;
	
	private String limit;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public int getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(int orderNo) {
		this.orderNo = orderNo;
	}

	public Date getCreationTime() {
		return creationTime;
	}

	public void setCreationTime(Date creationTime) {
		this.creationTime = creationTime;
	}

	public int getModuleId() {
		return moduleId;
	}

	public void setModuleId(int moduleId) {
		this.moduleId = moduleId;
	}

	public int getParentId() {
		return parentId;
	}

	public void setParentId(int parentId) {
		this.parentId = parentId;
	}

	public int getSubsystem() {
		return subsystem;
	}

	public void setSubsystem(int subsystem) {
		this.subsystem = subsystem;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public String getLimit() {
		return limit;
	}

	public void setLimit(String limit) {
		this.limit = limit;
	}
}
