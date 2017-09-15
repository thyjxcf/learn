package net.zdsoft.eis.base.common.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;

import net.zdsoft.eis.base.cache.BaseCacheConstants;
import net.zdsoft.eis.base.common.dao.UserSetDao;
import net.zdsoft.eis.base.common.entity.User;
import net.zdsoft.eis.base.common.entity.UserSet;
import net.zdsoft.eis.base.common.service.UserSetService;
import net.zdsoft.eis.frame.cache.CascadeCacheManager;
import net.zdsoft.eis.frame.util.RedisUtils;

import org.apache.commons.lang3.StringUtils;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

public class UserSetServiceImpl extends CascadeCacheManager implements
		UserSetService {

	private UserSetDao userSetDao;

	private static final String DEFAULT_SKIN = "default";
	private static final String STUDENT_DEFAULT_SKIN = "yellow";
	private static final String FAMILY_DEFAULT_SKIN = "green";

	private static final Map<String, String> skinMap = new HashMap<String, String>();

	public void setUserSetDao(UserSetDao userSetDao) {
		this.userSetDao = userSetDao;
	}

	@Override
	public UserSet getUserSetByUserId(final String userId) {
		return getEntityFromCache(new CacheEntityParam<UserSet>() {

			public UserSet fetchObject() {
				return userSetDao.getUserSetByUserId(userId);
			}

			public String fetchKey() {
				return BaseCacheConstants.EIS_USER_SET_ID + userId;
			}
		});
	}
	
	@Override
	public Map<String,UserSet> getUserSetMapByUserIds(String[] userIds) {
		return userSetDao.getUserSetMapByUserIds(userIds);
	}

	@Override
	protected String fetchCacheEntityKey() {
		return BaseCacheConstants.EIS_USER_SET_ID;
	}

	@Override
	public String getSkinByUserId(ServletContext servletContext, String userId,
			int ownerType, boolean refreshCache) {
		if (refreshCache)
			removeEntityFromCache(userId);
		UserSet set = getUserSetByUserId(userId);
		if (set != null) {
			// 判断是否存在这样的样式css
			String skin = set.getSkin();
			if (!skinMap.containsKey(skin)) {
				Resource res = new DefaultResourceLoader()
						.getResource(ResourceLoader.CLASSPATH_URL_PREFIX
								+ "/net/zdsoft/eis/template/css/" + skin
								+ ".css");
				try {
					res.getInputStream();
					skinMap.put(skin, skin);
					return skin;
				} catch (IOException e) {
					// 开发用
					String contextRoot = servletContext.getRealPath("/");
					File cssFile = new File(new File(contextRoot),
							"static/css/" + skin + ".css");
					if (!cssFile.exists()) {
						if (User.STUDENT_LOGIN == ownerType) {
							return STUDENT_DEFAULT_SKIN;
						} else if(User.FAMILY_LOGIN == ownerType){
							return FAMILY_DEFAULT_SKIN;
						}
						return DEFAULT_SKIN;
					}
					skinMap.put(skin, skin);
					return skin;
				}
			}
			return skin;
		}
		if (User.STUDENT_LOGIN == ownerType) {
			return STUDENT_DEFAULT_SKIN;
		} else if(User.FAMILY_LOGIN == ownerType){
			return FAMILY_DEFAULT_SKIN;
		}
		return DEFAULT_SKIN;
	}
	
	public void updateUserSetFile(UserSet uset){
		removeEntityFromCache(uset.getUserId());
		userSetDao.updateUserSetFile(uset);
	}
	
	public void saveUserSkin(UserSet us){
		if(StringUtils.isBlank(us.getId())){
			UserSet userSet2 = getUserSetByUserId(us.getUserId());
			if(userSet2 != null){
				us.setId(userSet2.getId());
			}
		}
		if(StringUtils.isBlank(us.getId())){
			userSetDao.save(us);
		} else {
			userSetDao.updateUserSkin(us);
		}
		removeEntityFromCache(us.getUserId());
	}
	
	public UserSet save(UserSet userSet){
		return userSetDao.save(userSet);
	}
	
	//获取用户图像路径
	public Map<String, String>  getUserPhotoMap(String[] userIds){
		Map<String, String> map = new HashMap<String, String>();
		Map<String, UserSet> usermap = userSetDao.getUserSetMapByUserIds(userIds);
		for(Map.Entry<String, UserSet> entry : usermap.entrySet()){
			String dirId = entry.getValue().getDirId();
			String dirPath = entry.getValue().getFilePath();
			String domain = RedisUtils.get("EIS.BASE.PATH.V6");
			if(StringUtils.isNotBlank(dirId) && StringUtils.isNotBlank(dirPath) && StringUtils.isNotBlank(domain)){
				String url = domain + "/common/open/base/showPicture.action?dirId="+dirId+"&filePath="+dirPath+"&isDefaultPhoto=true";
				map.put(entry.getKey(), url);
			}
		}
		
		return map;
	}
	
	public String getUserPhotoUrl(String userId){
		UserSet user = userSetDao.getUserSetByUserId(userId);
		if(user != null){
			String domain = RedisUtils.get("EIS.BASE.PATH.V6");
			String dirId = user.getDirId();
			String dirPath = user.getFilePath();
			if(StringUtils.isNotBlank(dirId) && StringUtils.isNotBlank(dirPath) && StringUtils.isNotBlank(domain)){
				String url = domain + "/common/open/base/showPicture.action?dirId="+dirId+"&filePath="+dirPath+"&isDefaultPhoto=true";
				return url;
			}
		}
		return null;
	}
		
}
