/**
 * 
 */
package net.zdsoft.leadin.common.entity;

import java.util.Date;

/**
 * @author zhaosf
 * 
 */
public class SchedulerToken {
	private String id;
	private String code;// 代码：惟一，建议以子系统code打头
	private String name;// 名称
	private int status;// 状态
	private int resetSecond;// 重置时间（单位：分钟）
	private Date modifyTime;// 修改时间
	private String remark;// 说明

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getResetSecond() {
		return resetSecond;
	}

	public void setResetSecond(int resetSecond) {
		this.resetSecond = resetSecond;
	}

	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}
