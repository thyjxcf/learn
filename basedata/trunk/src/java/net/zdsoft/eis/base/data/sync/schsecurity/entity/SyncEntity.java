package net.zdsoft.eis.base.data.sync.schsecurity.entity;

import java.util.Date;

public class SyncEntity {
	
	/**
	 * id
	 */
	public String id;
	
	/**
	 * 同步时间
	 */
	private Date syncTime;
	
	/**
	 * 同步结果：0未同步，1同步成功，2同步失败，3后续版本已处理
	 */
	private String syncResult;
	
	/**
	 * 错误信息
	 */
	private String exception;
	
	/**
	 * 同步版本号
	 */
	private Integer syncVersion;
	
	/**
	 * 修改时间
	 */
	private Date modifyTime;
	
	/**
	 * 数据变化类型
	 */
	public String SJBHLX;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getSyncTime() {
		return syncTime;
	}

	public void setSyncTime(Date syncTime) {
		this.syncTime = syncTime;
	}

	public String getSyncResult() {
		return syncResult;
	}

	public void setSyncResult(String syncResult) {
		this.syncResult = syncResult;
	}

	public String getException() {
		return exception;
	}

	public void setException(String exception) {
		this.exception = exception;
	}

	public Integer getSyncVersion() {
		return syncVersion;
	}

	public void setSyncVersion(Integer syncVersion) {
		this.syncVersion = syncVersion;
	}

	public String getSJBHLX() {
		return SJBHLX;
	}

	public void setSJBHLX(String sJBHLX) {
		SJBHLX = sJBHLX;
	}

	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}
}
