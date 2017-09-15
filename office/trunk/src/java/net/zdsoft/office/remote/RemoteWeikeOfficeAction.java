package net.zdsoft.office.remote;

import net.zdsoft.eis.base.common.entity.User;
import net.zdsoft.eis.base.common.service.UserService;
import net.zdsoft.eis.frame.action.RemoteBaseAction;

import org.apache.commons.lang3.StringUtils;

public class RemoteWeikeOfficeAction extends RemoteBaseAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4742562525407979652L;
	
	private static final String OFFCIE_PARAM = "office_mobile";
	
	private UserService userService;
	
	// 微课自动加上的参数
	private String syncUserId;
//	private String token;
	private String appId;
	private long salt;
	private String key;
	
	private String userId;
	private String unitId;
	private String userName;
	
	private String appType;
	
	public String execute() {
		return SUCCESS;
	}

	public String verifyToken() {
		try {
			if (StringUtils.isBlank(syncUserId)) {
				promptMessageDto.setErrorMessage("syncUserId为空！");
				return NONE;
			}
//			String ownerId = WeiKeyUtils.decodeByDes(token);// base_user.owner_id
//			List<User> users = userService.getUsersByOwner(ownerId);
			User user = userService.getUser(syncUserId);
			if (user == null) {
				promptMessageDto.setErrorMessage("用户信息不存在或非教师登录！");
				return NONE;
			}

//			userId = "402880FB448A92BC01448AAE04920119";//like
//			User user = userService.getUser(userId);
			
//			userId = "FF8080812E945A2D012E9467F23A000A";//show
//			User user = userService.getUser(userId);
			
//			userId = "4028801055234FF80155238815CB0025";//shengld
//			User user = userService.getUser(userId);
		} catch (Exception e) {
			e.printStackTrace();
			promptMessageDto.setErrorMessage("发生错误：" + e.getMessage());
			return NONE;
		}
		
		return SUCCESS;
	}

	public long getSalt() {
		return salt;
	}

	public void setSalt(long salt) {
		this.salt = salt;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUnitId() {
		return unitId;
	}

	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getAppType() {
		return appType;
	}

	public void setAppType(String appType) {
		this.appType = appType;
	}

	public String getSyncUserId() {
		return syncUserId;
	}

	public void setSyncUserId(String syncUserId) {
		this.syncUserId = syncUserId;
	}
	
	
}
