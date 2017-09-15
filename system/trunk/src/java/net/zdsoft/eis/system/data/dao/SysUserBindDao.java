package net.zdsoft.eis.system.data.dao;

import net.zdsoft.eis.system.data.entity.SysUserBind;
/**
 * sys_user_bind
 * @author 
 * 
 */
public interface SysUserBindDao{

	/**
	 * 新增sys_user_bind
	 * @param sysUserBind
	 * @return
	 */
	public SysUserBind save(SysUserBind sysUserBind);

	/**
	 * 根据remoteUserIds数组删除sys_user_bind
	 * @param remoteUserIds
	 * @return
	 */
	public Integer delete(String[] remoteUserIds);
	
	/**
	 * 更新sys_user_bind
	 * @param sysUserBind
	 * @return
	 */
	public Integer update(SysUserBind sysUserBind);

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
	
	public String getRemoteUserIdByUserId(String userId);

}