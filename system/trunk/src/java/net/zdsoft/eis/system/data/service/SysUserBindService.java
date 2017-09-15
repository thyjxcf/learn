package net.zdsoft.eis.system.data.service;

import net.zdsoft.eis.system.data.entity.SysUserBind;
/**
 * sys_user_bind
 * @author 
 * 
 */
public interface SysUserBindService{

	/**
	 * 保存信息sys_user_bind
	 * @param sysUserBind
	 */
	public void save(SysUserBind sysUserBind);

	/**
	 * 根据remoteUserIds数组删除sys_user_bind数据
	 * @param remoteUserIds
	 * @return
	 */
	public Integer delete(String[] remoteUserIds);

	/**
	 * 根据remoteUserId获取sys_user_bind
	 * @param remoteUserId
	 * @return
	 */
	public SysUserBind getSysUserBindById(String remoteUserId);
	
	/**
	 * 根据remoteUsername获取sys_user_bind
	 * @param remoteUsername
	 * @return
	 */
	public SysUserBind getSysUserBindByUsername(String remoteUsername);
	
	/**
	 * 根据userId获取sys_user_bind
	 * @param userId
	 * @return
	 */
	public SysUserBind getSysUserBindByUserId(String userId);
	
	/**
	 * 获取remoteUserId
	 * @param userId
	 * @return
	 */
	public String getRemoteUserIdByUserId(String userId);

}