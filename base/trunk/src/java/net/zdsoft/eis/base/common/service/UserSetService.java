package net.zdsoft.eis.base.common.service;

import java.util.Map;

import javax.servlet.ServletContext;

import net.zdsoft.eis.base.common.entity.UserSet;

public interface UserSetService {
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
	 * 根据userId获取用户图像路径
	 * @param userId
	 * @return
	 */
	public String getUserPhotoUrl(String userId);
	
	/**
	 * 获取用户图像路径 key--userId, value--photoUrl
	 * @param userIds
	 * @return
	 */
	public Map<String, String>  getUserPhotoMap(String[] userIds);
	
	/**
	 * 获取用户皮肤
	 * @param servletContext
	 * @param userId
	 * @param ownerType 用户角色
	 * @param refreshCache
	 * @return
	 */
	public String getSkinByUserId(ServletContext servletContext,String userId,int ownerType, boolean refreshCache) ;
	
	/**
	 * 更新头像信息
	 * @param uset
	 */
	public void updateUserSetFile(UserSet uset);
	
	/**
	 * 新增信息
	 * @param userSet
	 * @return
	 */
	public UserSet save(UserSet userSet);
	
	/**
	 * 保存用户桌面背景信息
	 * @param uset
	 */
	public void saveUserSkin(UserSet uset);
}
