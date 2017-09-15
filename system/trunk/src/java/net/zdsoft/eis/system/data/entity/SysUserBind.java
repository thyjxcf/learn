package net.zdsoft.eis.system.data.entity;

import java.io.Serializable;
import java.util.Date;
/**
 * sys_user_bind
 * @author 
 * 
 */
public class SysUserBind implements Serializable{
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	private String remoteUserId;
	/**
	 * 
	 */
	private String userId;
	/**
	 * 
	 */
	private Date creationTime;
	/**
	 * 
	 */
	private Date modifyTime;
	// 20170112
	private String remoteUsername;
	private String remotePwd;

	/**
	 * 设置
	 */
	public void setRemoteUserId(String remoteUserId){
		this.remoteUserId = remoteUserId;
	}
	/**
	 * 获取
	 */
	public String getRemoteUserId(){
		return this.remoteUserId;
	}
	/**
	 * 设置
	 */
	public void setUserId(String userId){
		this.userId = userId;
	}
	/**
	 * 获取
	 */
	public String getUserId(){
		return this.userId;
	}
	/**
	 * 设置
	 */
	public void setCreationTime(Date creationTime){
		this.creationTime = creationTime;
	}
	/**
	 * 获取
	 */
	public Date getCreationTime(){
		return this.creationTime;
	}
	/**
	 * 设置
	 */
	public void setModifyTime(Date modifyTime){
		this.modifyTime = modifyTime;
	}
	/**
	 * 获取
	 */
	public Date getModifyTime(){
		return this.modifyTime;
	}
	public String getRemoteUsername() {
		return remoteUsername;
	}
	public void setRemoteUsername(String remoteUsername) {
		this.remoteUsername = remoteUsername;
	}
	public String getRemotePwd() {
		return remotePwd;
	}
	public void setRemotePwd(String remotePwd) {
		this.remotePwd = remotePwd;
	}
	
}