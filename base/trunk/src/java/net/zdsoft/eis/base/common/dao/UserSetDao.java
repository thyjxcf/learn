package net.zdsoft.eis.base.common.dao;

import java.util.Map;

import net.zdsoft.eis.base.common.entity.UserSet;

public interface UserSetDao {
	/**
	 * 获取用户设置
	 * @param userId
	 * @return
	 */
	public UserSet getUserSetByUserId(String userId);
	
	/**
	 * 获取用户设置
	 * @param userIds
	 * @return
	 */
	public Map<String,UserSet> getUserSetMapByUserIds(String[] userIds);
	
	/**
	 * 更新头像信息
	 * @param uset
	 */
	public void updateUserSetFile(UserSet uset);
	
	/**
	 * 更新用户桌面背景信息
	 * @param uset
	 */
	public void updateUserSkin(UserSet uset);
	
	/**
	 * 新增信息
	 * @param userSet
	 * @return
	 */
	public UserSet save(UserSet userSet);

}
