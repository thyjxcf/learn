package net.zdsoft.eis.system.desktop.action;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;

import net.zdsoft.eis.base.common.entity.AppRegistry;
import net.zdsoft.eis.base.common.entity.Module;
import net.zdsoft.eis.base.common.entity.StorageDir;
import net.zdsoft.eis.base.common.entity.SubSystem;
import net.zdsoft.eis.base.common.entity.SystemIni;
import net.zdsoft.eis.base.common.entity.Unit;
import net.zdsoft.eis.base.common.entity.User;
import net.zdsoft.eis.base.common.entity.UserSet;
import net.zdsoft.eis.base.common.service.LoginLogService;
import net.zdsoft.eis.base.common.service.ServerService;
import net.zdsoft.eis.base.common.service.StorageDirService;
import net.zdsoft.eis.base.common.service.SubSystemService;
import net.zdsoft.eis.base.common.service.UnitService;
import net.zdsoft.eis.base.common.service.UserService;
import net.zdsoft.eis.base.common.service.UserSetService;
import net.zdsoft.eis.base.constant.BaseConstant;
import net.zdsoft.eis.base.subsystemcall.entity.DesktopAppDto;
import net.zdsoft.eis.base.subsystemcall.service.BaseDataSubsystemService;
import net.zdsoft.eis.base.subsystemcall.service.DesktopSubsystemService;
import net.zdsoft.eis.base.subsystemcall.service.OfficeSubsystemService;
import net.zdsoft.eis.base.util.BusinessUtils;
import net.zdsoft.eis.frame.action.PageSemesterAction;
import net.zdsoft.eis.system.data.entity.ExternalApp;
import net.zdsoft.eis.system.data.service.ExternalAppService;
import net.zdsoft.eis.system.frame.entity.UserApp;
import net.zdsoft.eis.system.frame.service.UserAppService;
import net.zdsoft.keel.util.DateUtils;
import net.zdsoft.keel.util.SecurityUtils;
import net.zdsoft.keelcnet.config.BootstrapManager;
import net.zdsoft.leadin.dataimport.subsystemcall.LoginUser;
import net.zdsoft.leadin.util.PWD;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

public class SystemAppAction extends PageSemesterAction {

	private static final long serialVersionUID = -3769559466973976893L;

	private SubSystemService subSystemService;
	private UserAppService userAppService;
	private UserService userService;
	private ServerService serverService;
	private BaseDataSubsystemService baseDataSubsystemService;
	private StorageDirService storageDirService;
	private ExternalAppService externalAppService;
	private UnitService unitService;
	private LoginLogService loginLogService;
	private DesktopSubsystemService desktopSubsystemService;
	private OfficeSubsystemService officeSubsystemService;
	// 是否签到
	private boolean canSignedIn = false;
	private boolean registOff = false;// 是否開啟
	private boolean signedIn = false;
	private boolean modelEsist = false;

	private String message;

	private String moduleIds;

	private String subsystemId;

	private String newPassword;

	private String confirmPassword;

	private String oldPassword;

	private String officeUrl;

	private String resourceUrl;

	private String istudyUrl;

	private String assetUrl;

	private UserSet userSet;

	private String unitName;

	private Date latestLoginTime;

	public List<UserApp> userAppList = new ArrayList<UserApp>();

	public List<Module> moduleList = new ArrayList<Module>();

	public List<ExternalApp> externalAppList = new ArrayList<ExternalApp>();

	public List<ExternalApp> broadcastList = new ArrayList<ExternalApp>();

	public List<AppRegistry> userSystemList = new ArrayList<AppRegistry>();

	private List<SubSystem> thirdPartAppList = new ArrayList<SubSystem>();

	private String teacherId;
	private UserSet userSkin = new UserSet();
	private String defaultSkin;
	private boolean appAll = false;// 自定义链接和常用操作一起
	private String appIds;
	private String pwdRuleStr;// 密码规则描述

	private String username;
	private String teacherName;
	private String identityCard;
	private String mobilePhone;
	private String externalType = "1";
	private String externalAppName;

	private String divId;

	public String execute() {
		// Server assetServer = serverService.getServerByServerCode("asset");
		// assetUrl = assetServer.getUrl() + assetServer.getIndexUrl();
		// officeUrl = serverService.getServerByServerCode("message").getUrl();
		// Server resourceServer =
		// serverService.getServerByServerCode("resource");
		// resourceUrl = resourceServer.getUrl() + resourceServer.getIndexUrl();
		// Server istudyServer = serverService.getServerByServerCode("istudy");
		// istudyUrl = istudyServer.getUrl() + istudyServer.getIndexUrl();
		return SUCCESS;
	}

	public String commonSystem() {
		return SUCCESS;
	}

	public String userApp() {
		boolean hasDefault = false;
		if ("tianchang".equals(getSystemDeploySchool())) {
			hasDefault = true;
		}
		userAppList = userAppService.getUserAppList(getLoginInfo().getUser()
				.getId(), getLoginInfo().getUser().getOwnerType(),
				getLoginInfo().getAllModSet(), hasDefault, false,isSecondUrl());
		if (BaseConstant.SYS_DEPLOY_SCHOOL_NBZX.equals(getSystemDeploySchool())) {
			externalAppList = externalAppService.getExternalAppListByUnitId(
					getUnitId(), ExternalApp.EXTERNAL_APP, 0, false);
			for (ExternalApp ea : externalAppList) {
				String url = ea.getAppUrl();
				String password = getLoginInfo().getUser().getPassword();
				password = PWD.decode(password);
				String username = getLoginInfo().getUser().getName();
				if (StringUtils.contains(url, "{username}"))
					url = StringUtils.replace(url, "{username}", username);
				if (StringUtils.contains(url, "{password}"))
					url = StringUtils.replace(url, "{password}", password);
				if (StringUtils.contains(url, "{password_md5}"))
					url = StringUtils.replace(url, "{password_md5}",
							SecurityUtils.encodeByMD5(password));
				if (StringUtils.contains(url, "{password_wp}")) {
					PWD pwd = new PWD(password);
					url = StringUtils.replace(url, "{password_wp}",
							pwd.encode());
				}
				ea.setAppUrl(url);
			}
		}
		return SUCCESS;
	}

	public String userAppSet() {
		boolean showAll = false;
		boolean hasDefault = false;
		if ("tianchang".equals(getSystemDeploySchool())) {
			hasDefault = true;
			if (getLoginInfo().getUser().getType() == 0) {
				showAll = true;
			}
		}
		Set<Integer> idSet = getLoginInfo().getAllSubSystemSet();
		Integer[] ids = null;
		if (CollectionUtils.isNotEmpty(idSet)) {
			ids = idSet.toArray(new Integer[0]);
		}
		String contextPath = getRequest().getContextPath();
		if (null == contextPath) {
			contextPath = "";
		}
		userSystemList = subSystemService.getUserSystemList(contextPath,
				getLoginInfo().getUser().getOwnerType(), ids, showAll);

		userAppList = userAppService.getUserAppList(getLoginInfo().getUser()
				.getId(), getLoginInfo().getUser().getOwnerType(),
				getLoginInfo().getAllModSet(), showAll, showAll,isSecondUrl());
		moduleList = userAppService.getModuleList(getLoginInfo().getUser()
				.getId(), subsystemId, getLoginInfo().getUser().getOwnerType(),
				getLoginInfo().getUnitClass(), getLoginInfo().getAllModSet(),
				hasDefault, showAll);
		return SUCCESS;
	}

	public String userAppSave() {
		String[] ids = null;
		if (StringUtils.isNotBlank(moduleIds)) {
			ids = moduleIds.split(",");
		}
		userAppService.addUserApps(getLoginInfo().getUser().getId(),
				getLoginInfo().getUser().getOwnerType(), ids);
		return "message";
	}

	public String userAppTcSave() {
		String[] ids = null;
		if (StringUtils.isNotBlank(moduleIds)) {
			ids = moduleIds.split(",");
		}
		if (getLoginInfo().getUser().getType() == 0) {
			userAppService.addUserApps(BaseConstant.ZERO_GUID, getLoginInfo()
					.getUser().getOwnerType(), ids);
		} else {
			userAppService.addUserApps(getLoginInfo().getUser().getId(),
					getLoginInfo().getUser().getOwnerType(), ids);
		}
		return "message";
	}

	public String userAppDelete() {
		// appIds 仅一个id
		try {
			jsonError = "";
			userAppService.deleteUserApp(appIds);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			jsonError = "删除失败！";
		}
		return SUCCESS;
	}

	public String userInfo() {
		userSet = userSetService.getUserSetByUserId(getLoginUser().getUserId());
		if (userSet != null) {
			userSet.setDirPath(storageDirService.getDir(userSet.getDirId()));
		} else {
			userSet = new UserSet();
		}
		latestLoginTime = loginLogService.getUserLastLoginTime(getLoginInfo()
				.getUser().getAccountId());
		String time = DateUtils.date2String(new Date(), "HH:mm");
		canSignedIn = officeSubsystemService.getOfficeSigntimeSet(getUnitId(),
				time);
		return SUCCESS;
	}

	public String thirdPartApp() {
		String basePath = BootstrapManager.getBaseUrl();
		String downloadPath = basePath
				+ "/common/downloadFile.action?filePath=";
		StorageDir storageDir = storageDirService.getActiveStorageDir();
		String thirdAppImagePath = storageDir.getDir() + File.separator
				+ "thirdPartApp" + File.separator + "images" + File.separator;
		thirdPartAppList = subSystemService
				.getThirdPartSubSystems(SubSystem.SOURCE_THIRD_PART_SPECIAL);
		for (SubSystem system : thirdPartAppList) {
			system.setImage(downloadPath + thirdAppImagePath
					+ system.getImage());
		}
		return SUCCESS;
	}

	public String userInfoSet() {
		teacherId = getLoginInfo().getUser().getTeacherid();
		pwdRuleStr = systemIniService.getValue(User.SYSTEM_PASSWORD_ALERT);
		unitName = getLoginInfo().getUnitName();
		return SUCCESS;
	}

	public int getOwnerType() {
		return getLoginInfo().getUser().getOwnerType();
	}

	public String userPasswordSet() {
		teacherId = getLoginInfo().getUser().getTeacherid();
		pwdRuleStr = systemIniService.getValue(User.SYSTEM_PASSWORD_ALERT);
		// 浙江校安定制（是否在强制密码修改页面完善教师姓名、身份证号、手机号并且根据姓名首字母和身份证后6为重置用户名）
		if (systemIniService.getBooleanValue("IS.MAINTAIN.USERINFO")) {
			return "zjxa";
		}
		unitName = getLoginInfo().getUnitName();
		return SUCCESS;
	}

	public String userPhotoSet() {
		return SUCCESS;
	}

	@SuppressWarnings("static-access")
	public String updatePassword() {
		User userDto = userService.getUser(getLoginInfo().getUser().getId());
		String password = userDto.getPassword();
		PWD pwdUtil = new PWD();
		password = pwdUtil.decode(password);// 解密为明文
		if (password == null)
			password = "";
		if (newPassword == null)
			newPassword = "";
		if (!password.equals(oldPassword)) {
			this.message = "error&输入的原密码不正确";
			return "message";
		}
		if (StringUtils.isBlank(newPassword)) {
			this.message = "error&新密码不能为空";
			return "message";
		}
		if (!newPassword.equals(confirmPassword)) {
			this.message = "error&两次输入的新密码不相同";
			return "message";
		}
		if (newPassword.equals(oldPassword)) {
			this.message = "error&输入的新密码不能与原密码相同";
			return "message";
		}
		if (!newPassword.matches(systemIniService
				.getValue(User.SYSTEM_PASSWORD_EXPRESSION))) {
			this.message = "error&"
					+ systemIniService.getValue(User.SYSTEM_PASSWORD_ALERT);
			return "message";
		} else {
			if (StringUtils.isNotBlank(systemIniService
					.getValue(User.SYSTEM_PASSWORD_STRONG))) {
				if (newPassword.matches(systemIniService
						.getValue(User.SYSTEM_PASSWORD_STRONG))) {
					this.message = "error&"
							+ systemIniService
									.getValue(User.SYSTEM_PASSWORD_ALERT);
					return "message";
				}

			}
		}

		try {
			baseDataSubsystemService.updatePassword(getLoginInfo().getUser()
					.getId(), oldPassword, newPassword, !isEisDeploy());
			this.message = "success&修改密码成功！";
		} catch (Exception e) {
			this.message = "error&修改个人信息出错：" + e.getMessage();
		}
		return "message";
	}

	@SuppressWarnings("static-access")
	public String checkPassword() {
		User userDto = userService.getUser(getLoginInfo().getUser().getId());
		String password = userDto.getPassword();
		PWD pwdUtil = new PWD();
		password = pwdUtil.decode(password);// 解密为明文
		// password="1";
		if (!password.matches(systemIniService
				.getValue(User.SYSTEM_PASSWORD_EXPRESSION))) {
			this.message = "error&"
					+ systemIniService.getValue(User.SYSTEM_PASSWORD_ALERT);
		} else {
			if (StringUtils.isNotBlank(systemIniService
					.getValue(User.SYSTEM_PASSWORD_STRONG))) {
				if (password.matches(systemIniService
						.getValue(User.SYSTEM_PASSWORD_STRONG))) {
					this.message = "error&"
							+ systemIniService
									.getValue(User.SYSTEM_PASSWORD_ALERT);
				} else {
					this.message = "success&密码强度为强！";
				}
			} else {
				this.message = "success&密码强度为强！";
			}
		}
		return "message";
	}

	@SuppressWarnings("static-access")
	public String updateEasyPassword() {
		User userDto = userService.getUser(getLoginInfo().getUser().getId());
		String password = userDto.getPassword();
		PWD pwdUtil = new PWD();
		password = pwdUtil.decode(password);// 解密为明文
		if (password == null)
			password = "";
		if (newPassword == null)
			newPassword = "";
		if (!password.equals(oldPassword)) {
			this.message = "error&输入的原密码不正确";
			return "message";
		}
		if (StringUtils.isBlank(newPassword)) {
			this.message = "error&新密码不能为空";
			return "message";
		}
		if (!newPassword.equals(confirmPassword)) {
			this.message = "error&两次输入的新密码不相同";
			return "message";
		}
		if (newPassword.equals(oldPassword)) {
			this.message = "error&输入的新密码不能与原密码相同";
			return "message";
		}

		if (!newPassword.matches(systemIniService
				.getValue(User.SYSTEM_PASSWORD_EXPRESSION))) {
			this.message = "error&"
					+ systemIniService.getValue(User.SYSTEM_PASSWORD_ALERT);
			return "message";
		} else {
			if (StringUtils.isNotBlank(systemIniService
					.getValue(User.SYSTEM_PASSWORD_STRONG))) {
				if (newPassword.matches(systemIniService
						.getValue(User.SYSTEM_PASSWORD_STRONG))) {
					this.message = "error&"
							+ systemIniService
									.getValue(User.SYSTEM_PASSWORD_ALERT);
					return "message";
				}
			}
		}

		try {
			baseDataSubsystemService.updatePassword(getLoginInfo().getUser()
					.getId(), oldPassword, newPassword, !isEisDeploy());
			this.message = "success&修改密码成功！";
		} catch (Exception e) {
			this.message = "error&修改个人信息出错：" + e.getMessage();
		}
		return "message";
	}

	@SuppressWarnings("static-access")
	public String updateEasyPasswordZJXA() {
		User userDto = userService.getUser(getLoginInfo().getUser().getId());
		if (org.apache.commons.lang.StringUtils.isNotBlank(BusinessUtils
				.validateIdentityCard(identityCard))) {
			this.message = "error&请确认身份证号码填写正确&identityCard";
			return "message";
		}

		boolean resultBool = baseDataSubsystemService.isExistsIdCard(
				getLoginInfo().getUser().getTeacherid(), identityCard);
		if (resultBool) {
			this.message = "error&身份证号码已存在，请重新录入&identityCard";
			return "message";
		}
		String password = userDto.getPassword();
		PWD pwdUtil = new PWD();
		password = pwdUtil.decode(password);// 解密为明文
		if (password == null)
			password = "";
		if (newPassword == null)
			newPassword = "";
		if (!password.equals(oldPassword)) {
			this.message = "error&输入的原密码不正确&oldPassword";
			return "message";
		}
		if (StringUtils.isBlank(newPassword)) {
			this.message = "error&新密码不能为空&newPassword";
			return "message";
		}
		if (!newPassword.equals(confirmPassword)) {
			this.message = "error&两次输入的新密码不相同&confirmPassword";
			return "message";
		}
		if (newPassword.equals(oldPassword)) {
			this.message = "error&输入的新密码不能与原密码相同&newPassword";
			return "message";
		}

		if (!newPassword.matches(systemIniService
				.getValue(User.SYSTEM_PASSWORD_EXPRESSION))) {
			this.message = "error&"
					+ systemIniService.getValue(User.SYSTEM_PASSWORD_ALERT)
					+ "&newPassword";
			return "message";
		} else {
			if (StringUtils.isNotBlank(systemIniService
					.getValue(User.SYSTEM_PASSWORD_STRONG))) {
				if (newPassword.matches(systemIniService
						.getValue(User.SYSTEM_PASSWORD_STRONG))) {
					this.message = "error&"
							+ systemIniService
									.getValue(User.SYSTEM_PASSWORD_ALERT)
							+ "&newPassword";
					return "message";
				}
			}
		}

		try {
			baseDataSubsystemService.updatePersonInfo(getLoginInfo().getUser()
					.getId(), oldPassword, newPassword, !isEisDeploy(),
					username, teacherName, mobilePhone, identityCard);
			this.message = "success&修改个人信息成功！用户名被重置为[" + username
					+ "],请用新的用户名重新登录";
		} catch (Exception e) {
			e.printStackTrace();
			this.message = "error&修改信息出错：" + e.getMessage() + "&teachName";
		}
		return "message";
	}

	public String externalAppList() {
		externalAppList = externalAppService.getExternalAppListByUnitId(
				getUnitId(), ExternalApp.EXTERNAL_APP, 0, false);
		Unit topUnit = unitService.getTopEdu();
		if (!getUnitId().equals(topUnit.getId())) {
			List<ExternalApp> externalAppList4Top = externalAppService
					.getExternalAppListByUnitId(topUnit.getId(),
							ExternalApp.EXTERNAL_APP, 0, false);
			externalAppList.addAll(externalAppList4Top);
		}

		if (appAll) {
			userAppList = userAppService.getUserAppList(getLoginInfo()
					.getUser().getId(),
					getLoginInfo().getUser().getOwnerType(), getLoginInfo()
							.getAllModSet(), false,false,isSecondUrl());
			return "appAll";
		}
		return SUCCESS;
	}

	public String externalAppSetList() {
		boolean temp = appAll ? true : false;
		int type = ExternalApp.EXTERNAL_APP;
		if (StringUtils.isNotBlank(externalType)) {
			type = Integer.valueOf(externalType);
		}
		externalAppList = externalAppService.getExternalAppListByUnitId(
				getUnitId(), type, 0, temp);
		if (appAll) {
			return NONE;
		}
		return SUCCESS;
	}

	public String externalAppSet() {
		if (StringUtils.isNotBlank(divId)) {
			DesktopAppDto desktopAppDto = desktopSubsystemService
					.getDesktopApp(BaseConstant.ZERO_GUID, getLoginInfo()
							.getUnitClass(), divId);
			if (desktopAppDto != null) {
				externalAppName = desktopAppDto.getName();
			}
		}
		if (!appAll) {
			return SUCCESS;
		}
		int type = ExternalApp.EXTERNAL_APP;
		if (StringUtils.isNotBlank(externalType)) {
			type = Integer.valueOf(externalType);
		}
		externalAppList = externalAppService.getExternalAppListByUnitId(
				getUnitId(), type, 0, true);
		moduleList = userAppService.getModuleList(getLoginInfo().getUser()
				.getId(), subsystemId, getLoginInfo().getUser().getOwnerType(),
				getLoginInfo().getUnitClass(), getLoginInfo().getAllModSet(),
				false, false);
		return "appAll";
	}

	public String showApp() {
		jsonError = "";
		if (StringUtils.isNotEmpty(appIds)) {
			try {
				externalAppService.updateExternalAppNotTemp(StringUtils.split(
						appIds, ","));
			} catch (Exception e) {
				jsonError = "应用设置失败！";
				log.error(e.getMessage(), e);
			}
		}
		String[] ids = null;
		if (StringUtils.isNotBlank(moduleIds)) {
			ids = moduleIds.split(",");
		}
		try {
			userAppService.addToUserApps(getLoginInfo().getUser().getId(),
					getLoginInfo().getUser().getOwnerType(), ids);
		} catch (Exception e) {
			jsonError = "应用设置失败！";
			log.error(e.getMessage(), e);
		}
		return SUCCESS;
	}

	// ===========皮肤设置============
	public String skinSet() {
		String userId = getLoginInfo().getUser().getId();
		userSkin = userSetService.getUserSetByUserId(userId);
		if (userSkin == null) {
			userSkin = new UserSet();
		}
		if (StringUtils.isBlank(userSkin.getSkin())) {// 皮肤设置为空
			userSkin.setLayout(UserSet.LAYOUT_UP);
			userSkin.setUserId(userId);
			userSkin.setSkin(getDefaultSkin());
			userSkin.setTheme(UserSet.THEME_DEFAULT);
		}
		return SUCCESS;
	}

	public String skinSetSave() {
		try {
			jsonError = "";
			userSetService.saveUserSkin(userSkin);
			LoginUser loginUser = getLoginUser();
			loginUser.setBgColor(userSkin.getBackgroundColor());
			loginUser.setBgImg(userSkin.getBackgroundImg());
			loginUser.setLayout(userSkin.getLayout());
			loginUser.setSkin(userSkin.getSkin());
			loginUser.setTheme(userSkin.getTheme());
			setSession(BaseConstant.SESSION_LOGINUSER, loginUser);
		} catch (Exception e) {
			jsonError = "保存失败！";
			log.error(e.getMessage(), e);
		}
		return SUCCESS;
	}

	public String broadcast() {
		if (Unit.UNIT_CLASS_EDU == getLoginInfo().getUnitClass()) {
			Unit unit = unitService.getUnit(getUnitId());
			broadcastList = externalAppService.getExternalAppListByUnionId(
					unit.getUnionid(), ExternalApp.BROADCAST_CLASSROOM, 6,
					false);
			for (ExternalApp app : broadcastList) {
				app.setAppName(app.getUnitName() + app.getAppName());
			}
		} else {
			broadcastList = externalAppService.getExternalAppListByUnitId(
					getUnitId(), ExternalApp.BROADCAST_CLASSROOM, 6, false);
		}
		return SUCCESS;
	}

	public String broadcastSet() {
		return SUCCESS;
	}

	public String broadcastSetList() {
		broadcastList = externalAppService.getExternalAppListByUnitId(
				getUnitId(), ExternalApp.BROADCAST_CLASSROOM, 0, false);
		return SUCCESS;
	}

	public String broadcastList() {
		Unit unit = unitService.getUnit(getUnitId());
		broadcastList = externalAppService.getExternalAppListByCondition(
				unit.getUnionid(), ExternalApp.BROADCAST_CLASSROOM, unitName,
				false, getPage());
		if (Unit.UNIT_CLASS_EDU == getLoginInfo().getUnitClass()) {
			for (ExternalApp app : broadcastList) {
				app.setAppName(app.getUnitName() + app.getAppName());
			}
		}
		return SUCCESS;
	}

	public List<AppRegistry> getUserSystemList() {
		return userSystemList;
	}

	public List<UserApp> getUserAppList() {
		return userAppList;
	}

	public List<Module> getModuleList() {
		return moduleList;
	}

	public void setSubSystemService(SubSystemService subSystemService) {
		this.subSystemService = subSystemService;
	}

	public void setUserAppService(UserAppService userAppService) {
		this.userAppService = userAppService;
	}

	public void setUserSetService(UserSetService userSetService) {
		this.userSetService = userSetService;
	}

	public String getModuleIds() {
		return moduleIds;
	}

	public void setModuleIds(String moduleIds) {
		this.moduleIds = moduleIds;
	}

	public String getSubsystemId() {
		return subsystemId;
	}

	public void setSubsystemId(String subsystemId) {
		this.subsystemId = subsystemId;
	}

	public String getMessage() {
		return message;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public void setServerService(ServerService serverService) {
		this.serverService = serverService;
	}

	public void setBaseDataSubsystemService(
			BaseDataSubsystemService baseDataSubsystemService) {
		this.baseDataSubsystemService = baseDataSubsystemService;
	}

	public String getAssetUrl() {
		return assetUrl;
	}

	public String getIstudyUrl() {
		return istudyUrl;
	}

	public String getOfficeUrl() {
		return officeUrl;
	}

	public String getResourceUrl() {
		return resourceUrl;
	}

	public void setStorageDirService(StorageDirService storageDirService) {
		this.storageDirService = storageDirService;
	}

	public UserSet getUserSet() {
		return userSet;
	}

	public void setUserSet(UserSet userSet) {
		this.userSet = userSet;
	}

	public List<SubSystem> getThirdPartAppList() {
		return thirdPartAppList;
	}

	public List<ExternalApp> getExternalAppList() {
		return externalAppList;
	}

	public void setExternalAppService(ExternalAppService externalAppService) {
		this.externalAppService = externalAppService;
	}

	public String getTeacherId() {
		return teacherId;
	}

	public void setTeacherId(String teacherId) {
		this.teacherId = teacherId;
	}

	public void setUnitService(UnitService unitService) {
		this.unitService = unitService;
	}

	public UserSet getUserSkin() {
		return userSkin;
	}

	public void setUserSkin(UserSet userSkin) {
		this.userSkin = userSkin;
	}

	public String getDefaultSkin() {
		if (StringUtils.isBlank(defaultSkin)) {
			if (getLoginInfo().getUser().getOwnerType() == User.STUDENT_LOGIN) {
				defaultSkin = UserSet.SKIN_DEFAULT_STUDENT;
			} else if (getLoginInfo().getUser().getOwnerType() == User.FAMILY_LOGIN) {
				defaultSkin = UserSet.SKIN_DEFAULT_FAMILY;
			} else {
				defaultSkin = UserSet.SKIN_DEFAULT;
			}
		}
		return defaultSkin;
	}

	public boolean isRegistOff() {
		return officeSubsystemService.getOfficeSigned("SIGN.IN.SYSTEM");
	}

	public void setRegistOff(boolean registOff) {
		this.registOff = registOff;
	}

	public boolean isAppAll() {
		return appAll;
	}

	public void setAppAll(boolean appAll) {
		this.appAll = appAll;
	}

	public String getAppIds() {
		return appIds;
	}

	public void setAppIds(String appIds) {
		this.appIds = appIds;
	}

	public String getPwdRuleStr() {
		return pwdRuleStr;
	}

	public void setPwdRuleStr(String pwdRuleStr) {
		this.pwdRuleStr = pwdRuleStr;
	}

	public Date getLatestLoginTime() {
		return latestLoginTime;
	}

	public void setLatestLoginTime(Date latestLoginTime) {
		this.latestLoginTime = latestLoginTime;
	}

	public void setLoginLogService(LoginLogService loginLogService) {
		this.loginLogService = loginLogService;
	}

	public String getTeacherName() {
		return teacherName;
	}

	public void setTeacherName(String teacherName) {
		this.teacherName = teacherName;
	}

	public String getIdentityCard() {
		return identityCard;
	}

	public void setIdentityCard(String identityCard) {
		this.identityCard = identityCard;
	}

	public String getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	public List<ExternalApp> getBroadcastList() {
		return broadcastList;
	}

	public String getExternalType() {
		return externalType;
	}

	public void setExternalType(String externalType) {
		this.externalType = externalType;
	}

	public String getDivId() {
		return divId;
	}

	public void setDivId(String divId) {
		this.divId = divId;
	}

	public boolean isCanSignedIn() {
		return canSignedIn;
	}

	public void setCanSignedIn(boolean canSignedIn) {
		this.canSignedIn = canSignedIn;
	}

	public String getExternalAppName() {
		return externalAppName;
	}

	public void setExternalAppName(String externalAppName) {
		this.externalAppName = externalAppName;
	}

	public void setDesktopSubsystemService(
			DesktopSubsystemService desktopSubsystemService) {
		this.desktopSubsystemService = desktopSubsystemService;
	}

	public boolean isSignedIn() {
		String years = this.getCurrentSemester().getAcadyear();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Calendar c = Calendar.getInstance();
		String times = df.format(c.getTime());
		return signedIn = officeSubsystemService.getOfficeSignedOnList(
				getLoginUser().getUserId(), years, this.getCurrentSemester()
						.getSemester(), getUnitId(), times);
	}

	public void setSignedIn(boolean signedIn) {
		this.signedIn = signedIn;
	}

	public void setOfficeSubsystemService(
			OfficeSubsystemService officeSubsystemService) {
		this.officeSubsystemService = officeSubsystemService;
	}

	public boolean isModelEsist() {
		Set<Integer> set = getLoginInfo().getAllModSet();
		if (CollectionUtils.isNotEmpty(set)) {
			for (Integer integer : set) {
				if (StringUtils.equals("70028", String.valueOf(integer))
						|| StringUtils.equals("70528", String.valueOf(integer))) {
					return true;
				}
			}
		}
		return false;
	}

	public void setModelEsist(boolean modelEsist) {
		this.modelEsist = modelEsist;
	}

	/**
	 * 是否显示用户绑定
	 * @return
	 */
	public boolean isShowUserBind(){
		return BaseConstant.SYS_DEPLOY_SCHOOL_HSEDU.equals(getSystemDeploySchool());
	}
	
	public boolean isShowPwdModify(){
		SystemIni ini = systemIniService.getSystemIni(BaseConstant.SYSTEM_USERPWD_MODIFY_SWITCH);
		if(ini == null){
			return true;
		}
		String value = ini.getNowValue();
        if (StringUtils.isEmpty(value)) {
            value = ini.getDefaultValue();
        }
		return BaseConstant.STR_YES.equals(value);
	}
	
}
