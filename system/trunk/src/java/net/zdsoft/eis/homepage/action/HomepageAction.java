package net.zdsoft.eis.homepage.action;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;
import net.zdsoft.eis.base.common.entity.LoginLog;
import net.zdsoft.eis.base.common.entity.Module;
import net.zdsoft.eis.base.common.entity.Region;
import net.zdsoft.eis.base.common.entity.School;
import net.zdsoft.eis.base.common.entity.Server;
import net.zdsoft.eis.base.common.entity.SystemIni;
import net.zdsoft.eis.base.common.entity.Teacher;
import net.zdsoft.eis.base.common.entity.Unit;
import net.zdsoft.eis.base.common.entity.User;
import net.zdsoft.eis.base.common.entity.UserSet;
import net.zdsoft.eis.base.common.service.LoginLogService;
import net.zdsoft.eis.base.common.service.ModuleService;
import net.zdsoft.eis.base.common.service.ProductParamService;
import net.zdsoft.eis.base.common.service.RegionService;
import net.zdsoft.eis.base.common.service.SchoolService;
import net.zdsoft.eis.base.common.service.ServerService;
import net.zdsoft.eis.base.common.service.SimpleModuleService;
import net.zdsoft.eis.base.common.service.TeacherService;
import net.zdsoft.eis.base.common.service.UnitService;
import net.zdsoft.eis.base.common.service.UserService;
import net.zdsoft.eis.base.common.service.UserSetService;
import net.zdsoft.eis.base.constant.BaseConstant;
import net.zdsoft.eis.base.constant.UCenterConstant;
import net.zdsoft.eis.base.data.entity.CountOnlineTime;
import net.zdsoft.eis.base.data.service.CountOnlineTimeService;
import net.zdsoft.eis.base.deploy.SystemDeployUtils;
import net.zdsoft.eis.frame.action.BaseAction;
import net.zdsoft.eis.frame.client.LoginInfo;
import net.zdsoft.eis.frame.dto.JSONMessageDto;
import net.zdsoft.eis.frame.passport.SessionManager;
import net.zdsoft.eis.remote.AESUtil;
import net.zdsoft.eis.remote.CASDto;
import net.zdsoft.eis.remote.SchxConstant;
import net.zdsoft.eis.system.data.entity.SysUserBind;
import net.zdsoft.eis.system.data.service.SysUserBindService;
import net.zdsoft.eis.system.data.service.UserLoginService;
import net.zdsoft.eis.system.frame.serial.SerialManager;
import net.zdsoft.eis.system.frame.web.ucenter.UCenterFilter;
import net.zdsoft.keel.util.MessageDigestUtils;
import net.zdsoft.keel.util.ServletUtils;
import net.zdsoft.keelcnet.util.GeneralUtil;
import net.zdsoft.leadin.dataimport.subsystemcall.LoginUser;
import net.zdsoft.passport.entity.Account;
import net.zdsoft.passport.service.client.PassportClient;
import net.zdsoft.smsplatform.client.ZDConstant;
import net.zdsoft.smsplatform.client.ZDUtils;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.struts2.ServletActionContext;
import org.jasig.cas.client.rest.CASRestful;

import com.google.common.collect.Lists;

/**
 * 登录首页
 * 
 */
public class HomepageAction extends BaseAction {

	private static final long serialVersionUID = -872007588908179083L;

	private static final String SYSTEM_EISS = "SYSTEM.EISS";// 是否允许非托管单位登录
	private static final String SHOW_FOOT = "SHOW.FOOT";// 是否显示页脚
	private static final String SYSTEM_EISS_ON = "1";// 允许非托管单位登录

	private String uid;// 用户名
	private String pwd;// 密码
	private int serverId = 0; // 默认系统管理
	private String redirectUrl; // 转向到passport的url地址
	private String username;// 用户名,IM那边传过来单点登录使用
	private String moduleIDStr;// 包含教育局跟学校的moduleID，中间用-隔开

	// -----------------从passport登录--------------------------------
	private String ticket; // 本次登录的唯一标识(32位)
	private String url; // 登录后应该转向的url
	private String auth; // 验证信息（确定是跳转来源）
	private String input;// 指定的登录界面
	private String src;// 跳转过来的源头服务器id
	private String loginoutUrl;
	public static String REDIRECT_SRC = "REDIRECT_SRC";

	private UserService userService;
	private UnitService unitService;
	private LoginLogService loginLogService;// 用户登录日志
	private UserLoginService userLoginService;
	private ServerService serverService;
	private SimpleModuleService simpleModuleService;
	private ProductParamService productParamService;
	private UserSetService userSetService;
	private SerialManager serialManager;
	private SchoolService schoolService;
	private RegionService regionService;
	private CountOnlineTimeService countOnlineTimeService;
	private ModuleService moduleService;
	private SysUserBindService sysUserBindService;
	private TeacherService teacherService;

	private String uucToken;
	private boolean needRegister;
	private JSONMessageDto jsonMessageDto = new JSONMessageDto();

	private Map<String, Object> jsonMap = new HashMap<String, Object>();

	private Module module;

	private List<User> showus;
	private String checkUserId;
	private String remoteUserId;
	private User exUser;
	private CASDto casDto = new CASDto();
	private String apCode;
	
	private String asApp = "false";

	/**
	 * 2017年1月11日 是否为第三方跳转过来
	 */
	private boolean isRemote;

	// ===================set==============

	public void setProductParamService(ProductParamService productParamService) {
		this.productParamService = productParamService;
	}

	public void setCountOnlineTimeService(
			CountOnlineTimeService countOnlineTimeService) {
		this.countOnlineTimeService = countOnlineTimeService;
	}

	public void setSimpleModuleService(SimpleModuleService simpleModuleService) {
		this.simpleModuleService = simpleModuleService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public void setUnitService(UnitService unitService) {
		this.unitService = unitService;
	}

	public void setLoginLogService(LoginLogService loginLogService) {
		this.loginLogService = loginLogService;
	}

	public void setUserLoginService(UserLoginService userLoginService) {
		this.userLoginService = userLoginService;
	}

	public void setServerService(ServerService serverService) {
		this.serverService = serverService;
	}

	/**
	 * 重定向到passport登录页面，或空跳转
	 * 
	 * @return
	 * @throws Exception
	 */
	public String redirectLogin() throws Exception {
		return SUCCESS;
	}
	
	/**
	 * 验证服务存在，一般用于第三方调用验证此服务是否仍正常，且自动单点登录
	 * @return
	 */
	public String checkValidate(){
		try {
			ServletUtils.print(getResponse(), SUCCESS);
		} catch (IOException e) {
		}
		return SUCCESS;
	}

	/**
	 * 被passport调用的校验
	 */
	public String verifyPassport() throws Exception {
		log.debug("ticket[" + ticket + "],url[" + url + "],input[" + input
				+ "],src[" + src + "],auth[" + auth + "]");

		if (StringUtils.isBlank(ticket) || StringUtils.isBlank(auth)) {
			addActionError("ticket为空");
			return ERROR;
		}

		Account account = PassportClient.getInstance().checkTicket(ticket);
		if (null == account) {
			addActionError("根据ticket取到的帐户信息为空");
			return ERROR;
		}

		log.debug("Account[" + account.getId() + "]");

		// 如果session中含有ticket的话，确保这个ticket会从SessionManager的ticket2SessionMap清除
		HttpSession session = ServletActionContext.getRequest().getSession();
		session.removeAttribute("ticket");
		session.setAttribute("ticket", ticket);
		session.setAttribute("sessionId", account.getSessionId());

		// 在SessionManager里登记，放到ticket2SessionMap里，key为ticket
		SessionManager.putTicketInMap(ticket, session);

		uid = account.getUsername();
		if (User.isUsernameNotCaseSensitive()) {
			uid = uid.toLowerCase();// 登录名
		}
		pwd = "";// 目前暂不校验城域密码，同szxy2.0

		if (!StringUtils.isBlank(src)) {
			session.setAttribute(REDIRECT_SRC, src);
		}

		if (StringUtils.isBlank(url)) {
			addActionError("登录后应该转向的url为空，请检查passport的相关配置是否正确");
			return ERROR;
		}

		if (!verifyEIS(false)) {// 不带密码登录验证
			return ERROR;// 如果不存在此用户，则跳转至登录页面并显示错误信息（正常情况下不会出现此种情况）(会出现此种情况，如用户所在单位被锁定)
		}

		// 初始化登录信息
		initLoginInfo();

		log.debug("登录后最终转向的地址[" + url + "]");
		getResponse().sendRedirect(url);

		return null;
	}

	/**
	 * IM消息弹出单点登录并跳转
	 * 
	 * @return
	 * @throws Exception
	 */
	public String verifyIM() throws Exception {
		String error = "";
		// username是im那边传过来的，城域里面都是用uid，这边赋值下
		uid = username;
		User user = userService.getUserByUserName(uid);
		if (user != null) {
			String pwd = user.findClearPassword();
			if (pwd == null) {
				pwd = "";
			}
			String encodePwd = MessageDigestUtils.encode(pwd, "MD5")
					+ MessageDigestUtils.encode(pwd, "SHA-1");

			if (!(encodePwd.equalsIgnoreCase(auth))) {
				error = "密码错误";
			}
		} else {
			error = "用户信息不存在";
		}

		if (StringUtils.isNotBlank(error)) {
			String out = "[error]\r\nmessage=" + error;
			ServletUtils.print(this.getResponse(), out);
		} else {
			// 初始化登录信息
			initLoginInfo();
			if (appId != 0) {
				if (url.indexOf("?") > 0) {
					url += "&appId=" + appId;
				} else {
					url += "?appId=" + appId;
				}
				if (StringUtils.isNotBlank(moduleIDStr)
						&& moduleIDStr.indexOf("-") > 0) {
					String[] moduleIDs = moduleIDStr.split("-");
					Integer num = 0;
					try {
						if (getLoginInfo().getUnitClass() == Unit.UNIT_CLASS_EDU) {
							num = Integer.parseInt(moduleIDs[0]);
						} else {
							num = Integer.parseInt(moduleIDs[1]);
						}
					} catch (Exception e) {
						System.out.println("传入的moduleIDStr值不对");
						num = 0;
					}
					setModuleID(num);
				}
				if (getModuleID() != 0) {
					url += "&moduleID=" + getModuleID();
					module = moduleService.getModuleByIntId(getModuleID());
				}
			}
			getResponse().sendRedirect(url);
		}

		return NONE;
	}

	/**
	 * 城域内部校验
	 * 
	 * @param isCheckPwd
	 *            是否校验城域数据库用户密码
	 */
	private boolean verifyEIS(boolean isCheckPwd) {
		String verifyMsg = serialManager.verifySerial();
		if (!verifyMsg.equals("")) {
			this.needRegister = true;
			addActionError(verifyMsg);
			return false;
		}

		if (StringUtils.isBlank(uid)) {
			addActionError("账号不能为空，请输入！");
			return false;
		}
		// 清空session
		/** 注意: ActionContext为LocalThread类型，显然删除Session仅仅是删除了本线程的session * */
		HttpSession session = ServletActionContext.getRequest().getSession();
		session.removeAttribute(BaseConstant.SESSION_LOGININFO);
		User user = null;
		try {
			user = userService.getUserByUserName(uid);
			exUser = user;
		} catch (Exception e) {
			addActionError("取用户信息出错: " + e.getMessage());
			return false;
		}
		String password = null;
		if (null != user) {
			/** password城域库中密码, pwd为用户输入密码 * */
			password = user.findClearPassword();
			if ("".equals(password)) {
				password = null;
			}
		}

		int result;// 1:用户名密码正确；-1：用户名不存在；-2：密码错误；-3:用户状态不正常
		if (null == user || user.getName() == null) {
			result = -1;
		} else if (user.getMark() == null
				|| user.getMark() != User.USER_MARK_NORMAL) {
			result = -3;// 用户状态不正常(如: 未审核，锁定等)
		} else if ((password == null && (StringUtils.isBlank(pwd)))
				|| pwd.equals(password)) {
			result = 1;
		} else {
			if (!isCheckPwd) { // 不校验密码
				result = 1;
			} else {
				result = -2;
			}
		}

		// 用户校验正常情况下还需校验其所属单位信息是否正常
		if (result == 1) {
			Unit unit = unitService.getUnit(user.getUnitid());
			if (unit == null || unit.getIsdeleted()) {
				addActionError("用户所属单位信息不存在或已经删除！");
				return false;
			} else {
				int mark = unit.getMark().intValue();
				if (Unit.UNIT_MARK_NORAML != mark) {
					addActionError("用户所属单位信息未审核或已锁定！");
					return false;
				}
				// 报送单位
				if (null == unit.getUsetype()) {
					addActionError("用户所属单位信息的报送类别为空！");
					return false;
				}
				if (Unit.UNIT_USETYPE_REPORT == unit.getUsetype().intValue()) {
					// 是否允许非托管单位登录
					SystemIni iniDto = systemIniService
							.getSystemIni(SYSTEM_EISS);
					if (null == iniDto
							|| !SYSTEM_EISS_ON.equals(iniDto.getNowValue())) {
						addActionError("用户所属单位为非托管单位，本系统不允许非托管单位登录！");
						return false;
					}
				}
			}
		} else if (result == -3) {
			addActionError("该账号未审核或已锁定，请联系单位管理员或上级单位管理员！");
			return false;
		} else {
			addActionError("账号或密码错误，请重新输入！");
			return false;
		}

		return true;
	}

	// 初始化用户登录信息
	private LoginInfo initLoginInfo() {
		LoginInfo loginInfo = null;

		// 学生登录
		User user = userService.getUserByUserName(uid);
		Set<Integer> activeSubSytem = new HashSet<Integer>();
		Unit unit = unitService.getUnit(user.getUnitid());
		switch (user.getOwnerType()) {
		case User.STUDENT_LOGIN:
			platform = BaseConstant.PLATFORM_STUPLATFORM;
			activeSubSytem = simpleModuleService.getActiveSubsytems(platform);
			loginInfo = new LoginInfo(user, unit, activeSubSytem);
			break;
		case User.FAMILY_LOGIN:
			platform = BaseConstant.PLATFORM_FAMPLATFORM;
			activeSubSytem = simpleModuleService.getActiveSubsytems(platform);
			loginInfo = new LoginInfo(user, unit, activeSubSytem);
			break;
		case User.OTHER_LOGIN:
			break;
		default:
			loginInfo = userLoginService.initLoginInfo(uid);
			break;
		}
		int serverId = 0;
		int serverTypeId = 0;
		Server app = serverService.getServerByServerCode(SystemDeployUtils
				.getCurrentDeployAppCode());
		if (null != app) {
			serverId = Integer.parseInt(app.getId());
			serverTypeId = Long.valueOf(app.getServerTypeId()).intValue();
		}

		try {
			// 写入登录日志
			String remoteAddr = ServletActionContext.getRequest()
					.getRemoteAddr();
			Date clickDate = new Date();
			LoginLog log = new LoginLog();
			if (StringUtils.isNotBlank(loginInfo.getUser().getAccountId())) {
				log.setAccountId(loginInfo.getUser().getAccountId());
				log.setCreationTime(clickDate);
				log.setLoginTime(clickDate);
				log.setRegionCode(loginInfo.getUser().getRegion());
				log.setRemoteIp(remoteAddr);
				log.setServerId(serverId);
				log.setServerTypeId(serverTypeId);
				log.setUnitId(loginInfo.getUnitID());
				log.setUserType(loginInfo.getUser().getOwnerType());
				loginLogService.insert(log);
			} else {
				this.log.error("user:" + loginInfo.getUser().getName()
						+ " have no accountId!");
			}

		} catch (Exception e) {
			log.error("add login log error!", e);
		}

		// 记录登录ip
		loginInfo
				.setClientIP(ServletActionContext.getRequest().getRemoteAddr());

		/** loginInfo 存入本线程的session中 * */
		setSession(BaseConstant.SESSION_LOGININFO, loginInfo);
		// 记录登录时间
		CountOnlineTime countOnlineTime = new CountOnlineTime();
		countOnlineTime.setUserId(loginInfo.getUser().getId());
		countOnlineTime.setSessionId(ServletActionContext.getRequest()
				.getSession().getId());
		countOnlineTime.setLogoutTime(null);
		countOnlineTime.setLoginTime(new Date());
		countOnlineTime.setOnlineTime(0);
		countOnlineTime.setUnitId(loginInfo.getUser().getUnitid());
		countOnlineTimeService.save(countOnlineTime);
		// 为导入使用
		LoginUser loginUser = new LoginUser();
		loginUser.setUnitId(loginInfo.getUnitID());
		loginUser.setUserId(loginInfo.getUser().getGuid());
		loginUser.setUserIntId(loginInfo.getUser().getIntId());
		loginUser.setSkin(userSetService.getSkinByUserId(getServletContext(),
				getLoginInfo().getUser().getId(), getLoginInfo().getUser()
						.getOwnerType(), true));
		UserSet us = userSetService.getUserSetByUserId(getLoginInfo().getUser()
				.getId());
		if (us == null) {
			us = new UserSet();
		}
		if (StringUtils.isBlank(us.getLayout())) {
			us.setLayout(UserSet.LAYOUT_UP);
			us.setTheme(UserSet.THEME_DEFAULT);
		}
		loginUser.setBgColor(us.getBackgroundColor());
		loginUser.setBgImg(us.getBackgroundImg());
		loginUser.setLayout(us.getLayout());
		loginUser.setTheme(us.getTheme());
		setSession(BaseConstant.SESSION_LOGINUSER, loginUser);

		Cookie c = new Cookie("user_login_id", "");
		c.setVersion(0);
		c.setMaxAge(86400);
		c.setValue(GeneralUtil.urlEncode(uid));
		getResponse().addCookie(c);

		return loginInfo;
	}

	/**
	 * 城域主页面
	 */
	@Override
	public String execute() throws Exception {
		LoginInfo loginInfo = this.getLoginInfo();
		String deploySchool = getSystemDeploySchool();
		if (loginInfo != null) {
			if (systemDeployService.isConnectPassport()
					&& systemDeployService.isConnectOffice()) {// 从passport登录
				officeRecridt();
			}
			String layout = getLoginUser().getLayout();
			if (UserSet.LAYOUT_LEFT.equals(layout)) {
				if ("tianchang".equals(deploySchool)) {
					return "left-tc";
				}
				return "left";
			}
			return SUCCESS;
		} else {
			String verifyMsg = serialManager.verifySerial();
			if (!verifyMsg.equals("")) {
				this.needRegister = true;
				addActionError(verifyMsg);
			}

			if (BaseConstant.SYS_DEPLOY_SCHOOL_HZZC
					.equalsIgnoreCase(deploySchool)
					|| BaseConstant.SYS_DEPLOY_SCHOOL_NBZX
							.equalsIgnoreCase(deploySchool)
					|| BaseConstant.SYS_DEPLOY_SCHOOL_NHZG
							.equalsIgnoreCase(deploySchool)
					|| BaseConstant.SYS_DEPLOY_SCHOOL_XIAN
							.equalsIgnoreCase(deploySchool)
					|| BaseConstant.SYS_DEPLOY_SCHOOL_ZJSTXA
							.equalsIgnoreCase(deploySchool)
					|| BaseConstant.SYS_DEPLOY_SCHOOL_HDJY
							.equalsIgnoreCase(deploySchool)
					|| BaseConstant.SYS_DEPLOY_SCHOOL_CIXI
							.equalsIgnoreCase(deploySchool)
					|| BaseConstant.SYS_DEPLOY_SCHOOL_GDMMJY
							.equalsIgnoreCase(deploySchool)
					|| BaseConstant.SYS_DEPLOY_SCHOOL_FCSZ
							.equalsIgnoreCase(deploySchool)
					|| BaseConstant.SYS_DEPLOY_SCHOOL_XINJIANG
							.equalsIgnoreCase(deploySchool)
					|| BaseConstant.SYS_DEPLOY_SCHOOL_NC
							.equalsIgnoreCase(deploySchool)
					|| BaseConstant.SYS_DEPLOY_SCHOOL_JIAN
							.equalsIgnoreCase(deploySchool)
					|| BaseConstant.SYS_DEPLOY_SCHOOL_PINGYI
							.equalsIgnoreCase(deploySchool)){
				return deploySchool;
			}
			if (BaseConstant.SYS_DEPLOY_SCHOOL_SCZG.equals(deploySchool)) {
				getResponse()
						.sendRedirect(
								systemIniService
										.getValue(SchxConstant.SYSSTEMINI_SSO_INDEX_URL));
				return NONE;
			}
			return LOGIN;
		}
	}

	/**
	 * eis登录验证
	 * 
	 * @return
	 * @throws Exception
	 */
	public String loginForEisOnly() throws Exception {
		String deploySchool = getSystemDeploySchool();
		if(getModuleID() != 0){
			module = moduleService.getModuleByIntId(getModuleID());
		}
		if (this.getLoginInfo() != null) {
			String layout = getLoginUser().getLayout();
			if ("tianchang".equals(deploySchool)) {
				return "left-tc";
			}
			if (UserSet.LAYOUT_LEFT.equals(layout)) {
				return "left";
			}
			return SUCCESS;
		}
		if (!verifyEIS(true)) {
			if (BaseConstant.SYS_DEPLOY_SCHOOL_HZZC
					.equalsIgnoreCase(deploySchool)
					|| BaseConstant.SYS_DEPLOY_SCHOOL_NBZX
							.equalsIgnoreCase(deploySchool)
					|| BaseConstant.SYS_DEPLOY_SCHOOL_NHZG
							.equalsIgnoreCase(deploySchool)
					|| BaseConstant.SYS_DEPLOY_SCHOOL_XIAN
							.equalsIgnoreCase(deploySchool)
					|| BaseConstant.SYS_DEPLOY_SCHOOL_ZJSTXA
							.equalsIgnoreCase(deploySchool)
					|| BaseConstant.SYS_DEPLOY_SCHOOL_HDJY
							.equalsIgnoreCase(deploySchool)
					|| BaseConstant.SYS_DEPLOY_SCHOOL_CIXI
							.equalsIgnoreCase(deploySchool)
					|| BaseConstant.SYS_DEPLOY_SCHOOL_GDMMJY
							.equalsIgnoreCase(deploySchool)
					|| BaseConstant.SYS_DEPLOY_SCHOOL_FCSZ
							.equalsIgnoreCase(deploySchool)
					|| BaseConstant.SYS_DEPLOY_SCHOOL_XINJIANG
							.equalsIgnoreCase(deploySchool)
					|| BaseConstant.SYS_DEPLOY_SCHOOL_NC
							.equalsIgnoreCase(deploySchool)
					|| BaseConstant.SYS_DEPLOY_SCHOOL_JIAN
							.equalsIgnoreCase(deploySchool)) {
				return deploySchool;
			}
			if (BaseConstant.SYS_DEPLOY_SCHOOL_SCZG.equals(deploySchool)) {
				getResponse()
						.sendRedirect(
								systemIniService
										.getValue(SchxConstant.SYSSTEMINI_SSO_INDEX_URL));
				return NONE;
			}
			return LOGIN;
		}

		// 初始化登录信息
		LoginInfo loginInfo = initLoginInfo();
		switch (loginInfo.getUser().getOwnerType()) {
		case User.STUDENT_LOGIN:
		case User.FAMILY_LOGIN:
		case User.OTHER_LOGIN:
		default:
			break;
		}

		String layout = getLoginUser().getLayout();
		if ("tianchang".equals(deploySchool)) {
			return "left-tc";
		}
		if (UserSet.LAYOUT_LEFT.equals(layout)) {
			return "left";
		}
		return SUCCESS;
	}

	/**
	 * 1.如果连接passport，则登陆passport<br>
	 * 2.初始化登陆信息<br>
	 * 3.登陆成功后跳转首页桌面
	 * 
	 * @return
	 */
	public String loginIndex() {
		try {
			if (systemDeployService.isConnectPassport()) {
				// String passPwd = PWD.decode(pwd);
				// net.zdsoft.passport.entity.LoginInfo in =
				if (StringUtils.isEmpty(pwd)) {
					if (exUser == null) {
						exUser = userService.getUserByUserName(uid);
					}
					pwd = exUser.findClearPassword();
				}
				// pwd = "cc12345678";
				promptMessageDto.setOperateSuccess(true);
				return SUCCESS;
				// PassportClient.getInstance().clientLogin(uid, pwd);
			}
			// 初始化登录信息
			initLoginInfo();
			getResponse().sendRedirect(
					getRequest().getContextPath()
							+ "/fpf/homepage/loginForEisOnly.action");
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			promptMessageDto.setErrorMessage("跳转登录失败：" + e.getMessage());
			return PROMPTMSG;
		}
		return NONE;
	}

	// 四川中职鸿信跳转
	@SuppressWarnings("unchecked")
	public String loginForSchx() {
		if (StringUtils.isBlank(uucToken)) {
			promptMessageDto.setErrorMessage("跳转失败：用户令牌参数没有传值！");
			return PROMPTMSG;
		}
		try {
			String authHost = systemIniService
					.getValue(SchxConstant.SYSTEMINI_SSO_REMOTE_HOST);
			// authHost = "http://118.116.7.11/uuccpmain";
			if (StringUtils.isBlank(authHost)) {
				promptMessageDto.setErrorMessage("跳转失败：没有配置对接的系统域名！");
				return PROMPTMSG;
			}

			String authUrl = authHost + SchxConstant.AUTH_URL;
			// authUrl =
			// "http://202.102.101.96:12000/uuccpMain/token/singleAuth";
			Map<String, String> pas = new HashMap<String, String>();
			pas.put("param", getToParamValue());
			System.out.println("getuserurl=" + authUrl + "?param="
					+ pas.get("param"));
			String res = ZDUtils.readContent(authUrl, ZDConstant.METHOD_POST,
					pas, null, "UTF-8");
			System.out.println("uuctoken=" + uucToken + "，result=" + res);
			JSONObject json = JSONObject.fromObject(res);
			if (!SchxConstant.RESULT_SUC.equals(json.get("tsr_result"))) {
				promptMessageDto.setErrorMessage("跳转失败：" + json.get("tsr_msg"));
				return PROMPTMSG;
			}
			Map<String, String> bodyMap = (Map<String, String>) json
					.get("body");
			if (bodyMap == null) {
				promptMessageDto.setErrorMessage("跳转失败：没有获取到用户信息！");
				return PROMPTMSG;
			}
			remoteUserId = bodyMap.get("userId");
			return redirectLocal();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			promptMessageDto.setErrorMessage("跳转失败：" + e.getMessage());
			return PROMPTMSG;
		}
	}

	// 本地跳转绑定或者绑定后直接登录
	public String redirectLocal() {
		try {
			if (StringUtils.isBlank(remoteUserId)) {
				promptMessageDto.setErrorMessage("跳转失败：没有获取到用户Id！");
				return PROMPTMSG;
			}
			SysUserBind bin = sysUserBindService
					.getSysUserBindById(remoteUserId);
			if (bin == null) {
				this.addActionError("还没有绑定系统用户，请输入用户名密码进行绑定");//
				return INPUT;
			}

			checkUserId = bin.getUserId();
			User us = userService.getUser(checkUserId);
			uid = us.getName();
			pwd = us.findClearPassword();
			if (verifyEIS(false)) {
				return loginIndex();
			}
			return INPUT;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			promptMessageDto.setErrorMessage("跳转失败：" + e.getMessage());
			return PROMPTMSG;
		}
	}

	// 用户绑定
	public String bindUser() {
		if (verifyEIS(true)) {
			if (StringUtils.isBlank(checkUserId)) {
				if (exUser == null) {
					exUser = userService.getUserByUserName(uid);
				}
				checkUserId = exUser.getId();
			}
			String exrid = sysUserBindService
					.getRemoteUserIdByUserId(checkUserId);
			if (StringUtils.isNotBlank(exrid)) {
				checkUserId = null;
				// this.addActionError("跳转失败：该用户已经被绑定了，不能再绑定其他账号！");
				// return INPUT;
				promptMessageDto.setErrorMessage("该用户已经被绑定了，不能再绑定其他账号！");
				return SUCCESS;
			}
			SysUserBind bi = new SysUserBind();
			bi.setRemoteUserId(remoteUserId);
			bi.setUserId(checkUserId);
			sysUserBindService.save(bi);
			promptMessageDto.setOperateSuccess(true);
			return loginIndex();
		} else {
			if (hasActionErrors()) {
				Iterator<String> it = this.getActionErrors().iterator();
				promptMessageDto.setErrorMessage(it.next());
			}
			promptMessageDto.setOperateSuccess(false);
			return SUCCESS;
		}
	}

	/**
	 * 加密访问参数
	 * 
	 * @return
	 */
	private String getToParamValue() {
		JSONObject obj = new JSONObject();
		obj.put("uucToken", uucToken);
		obj.put("accessSysCode",
				systemIniService.getValue(SchxConstant.SYSTEMINI_SYS_CODE));// //"1009"
		// obj.put("accessSysCode", "1002");
		System.out.println("paras=" + obj.toString());
		return AESUtil.encrypt(obj.toString());
	}

	// ===================================黄山与安徽省平台====================================

	/**
	 * 校验apcode
	 * 
	 * @param apCode
	 * @return
	 */
	@SuppressWarnings("unused")
	private boolean validateApCode(String apCode) {
		// RemoteService remoteService = (RemoteService)
		// ContainerManager.getComponent("remoteService");
		// if (StringUtils.startsWith(apCode, "WinuponTestApCode"))
		// return true;
		// List<String> lists = RedisUtils.lrange(AccessData.REMOTE_LIST + "_" +
		// "VALIDATE_APCODES");
		// if (CollectionUtils.isEmpty(lists)) {
		// lists = remoteService.getValidateApCode();
		// if (CollectionUtils.isNotEmpty(lists)) {
		// RedisUtils.lpush(AccessData.REMOTE_LIST + "_" + "VALIDATE_APCODES",
		// lists.toArray(new String[0]));
		// }
		// }
		// return lists.contains(apCode);
		return true;
	}

	//
	public String redirectToAp() {
		try {
			// apCode校验
			// if(StringUtils.isBlank(apCode)){
			// promptMessageDto.setErrorMessage("没有获取到应用系统的编号！");
			// return PROMPTMSG;
			// }
			// if(!validateApCode(apCode)){
			// promptMessageDto.setErrorMessage("编号为：" + apCode + "的应用系统未接入！");
			// return PROMPTMSG;
			// }
			// 获取绑定数据
			checkUserId = getLoginInfo().getUser().getId();
			SysUserBind ub = sysUserBindService
					.getSysUserBindByUserId(checkUserId);
			if (ub == null) {
				Teacher tea = teacherService.getTeacher(getLoginInfo()
						.getUser().getTeacherid());
				if (StringUtils.isBlank(tea.getIdcard())) {
					promptMessageDto.setErrorMessage("还没有维护身份证号，请先维护！");
					return PROMPTMSG;
				}
				this.addActionError("还没有绑定平台账号，请先输入账号信息进行绑定");
				return INPUT;// 跳转到绑定页面
			}
			uid = ub.getRemoteUsername();
			// 获取token
			if (PROMPTMSG.equals(dealWithToken())) {
				return PROMPTMSG;
			}
			if (PROMPTMSG.equals(dealWithPwd())) {
				this.addActionError(promptMessageDto.getErrorMessage());
				return INPUT;
			}
		} catch (Exception e) {
			promptMessageDto.setErrorMessage("跳转失败！");
			log.error(e.getMessage(), e);
			return PROMPTMSG;
		}
		return jumpToAp();
	}

	// 跳转
	public String jumpToAp() {
		try {
			Properties properties = CASRestful.getProperties();
			String casUrl = properties.getProperty("ssoservice.cas.url");
			String serviceUrl = properties
					.getProperty("ssoservice.service.url");
			CASRestful restfull = new CASRestful(getRequest(), casUrl,
					serviceUrl);
			if (restfull.isSessionAuthenticated()) {// cas已经登录了，直接跳转到首页
				String cuid = restfull.getCurrentUser();
				if (StringUtils.equals(uid, cuid)) {
					this.getResponse().sendRedirect(
							"http://www.ahedu.cn/SNS/index.php");
					return NONE;
				}
				// String tgt = restfull.getTicketGrantingTicket();
				// restfull.logout(tgt);
			}
			casDto.setCasUrl(casUrl);
			casDto.setServiceUrl(serviceUrl);
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		}
		return SUCCESS;
	}

	/**
	 * 获取token
	 * 
	 * @return
	 * @throws Exception
	 */
	private String dealWithToken() throws Exception {
		String url = "http://www.ahedu.cn/jgkj/authorization-token/oauth/token?"
				+ "client_id=anqing&client_secret=874895e5c922ded5&grant_type=client_credentials";
		String res = ZDUtils.readContent(url, ZDConstant.METHOD_GET, null,
				null, "UTF-8");
		JSONObject json = JSONObject.fromObject(res);
		uucToken = (String) json.get("access_token");
		if (StringUtils.isBlank(uucToken)) {
			promptMessageDto.setErrorMessage("获取token失败！");
			return PROMPTMSG;
		}
		return "";
	}

	/**
	 * 获取用户信息
	 * 
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	private String dealWithPwd() throws Exception {
		String url = "http://www.ahedu.cn/SNS/index.php?"
				+ "app=mobileapi&mod=Researchaq&act=getUserinfoByKey&key=login_name"
				+ "&value=" + uid + "&service=sns.aq.getUserInfo&appkey=anqing"
				+ "&token=" + uucToken;
		String res = ZDUtils.readContent(url);
		JSONObject json = JSONObject.fromObject(res);
		Integer sts = (Integer) json.get("status");
		if (200 != sts) {
			promptMessageDto.setErrorMessage("获取用户信息失败！");
			return PROMPTMSG;
		}
		Map<String, String> bodyMap = (Map<String, String>) json.get("data");
		if (bodyMap == null) {
			promptMessageDto.setErrorMessage("没有获取到用户信息！");
			return PROMPTMSG;
		}
		pwd = "IFLYTEK_ENCODE_" + bodyMap.get("userPassword");
		return "";
	}

	/**
	 * 从各子系统返回首页
	 * 
	 * @return
	 * @throws Exception
	 */
	public String backToHomepage() throws Exception {
		if (systemDeployService.isConnectPassport()
				&& systemDeployService.isConnectOffice()) {// 从passport登录
			officeRecridt();
		}
		return SUCCESS;
	}

	private void officeRecridt() throws Exception {
		HttpServletRequest req = ServletActionContext.getRequest();
		// 判断取内网地址还是缺省的第一个地址;
		String eisURL = systemDeployService.getEisUrl();
		String homepage = systemDeployService.getOfficeUrl();
		if (isDeployAsApp()) {
			getResponse().sendRedirect(homepage);
		} else {
			if (eisURL.indexOf(req.getServerName()) < 0) {
				homepage = systemDeployService.getOfficeSecondUrl();
			}
			getResponse().sendRedirect(homepage);
		}
	}

	public String subsystem() {
		if (getModuleID() != 0) {
			module = moduleService.getModuleByIntId(getModuleID());
		}
		String layout = getLoginUser().getLayout();
		String deploySchool = getSystemDeploySchool();
		if ("tianchang".equals(deploySchool)) {
			return "left";
		}
		if (UserSet.LAYOUT_LEFT.equals(layout)) {
			return "left";
		}
		return SUCCESS;
	}

	/**
	 * 注销
	 */
	public String logout() throws Exception {
		HttpServletRequest req = ServletActionContext.getRequest();
		HttpSession session = req.getSession();

		session.removeAttribute(BaseConstant.SESSION_LOGININFO);
		session.removeAttribute(BaseConstant.SESSION_LOGINUSER);

		boolean ucSwitch = systemIniService
				.getBooleanValue(UCenterConstant.SYSTEM_UCENTER_SWITCH);
		// ucSwitch = true;
		if (systemDeployService.isConnectPassport()) {// 从passport登录
			String ticket = (String) session.getAttribute("ticket");
			if (!StringUtils.isBlank(ticket)) {
				// 从ticket2SessionMap中清除ticket
				SessionManager.removeTicketInMap(ticket);
				// 清除ticket是为了在session失效的时候不再重复通知中心
				PassportClient.getInstance().invalidate(ticket);
			}
			String homepage = systemDeployService.getIndexUrl();
			if (StringUtils.isNotBlank(homepage)
					&& homepage.indexOf(req.getServerName()) < 0) {
				homepage = systemDeployService.getIndexSecondUrl();
			}
			if (!systemDeployService.isConnectOffice()) {
				// 判断取内网地址还是缺省的第一个地址;
				// String eisuSecondURL = systemDeployService.getEisSecondUrl();
				// homepage = systemDeployService.getIndexUrl();
				// if (eisURL.indexOf(req.getServerName()) < 0) {
				// homepage = systemDeployService.getIndexSecondUrl();
				// }
				// 根据配置文件配置的参数跳转
				if (StringUtils.isBlank(homepage)) {
					homepage = req.getScheme() + "://" + req.getServerName()
							+ ":" + req.getServerPort() + req.getContextPath()
							+ "/" + getText("eis.login.postfix");
				}
			}
			if ("hzbj".equalsIgnoreCase(getSystemDeploySchool())) {
				homepage = "http://www.bjqjyj.cn/index.aspx";
			}
			// else
			// if(BaseConstant.SYS_DEPLOY_SCHOOL_SCZG.equals(getSystemDeploySchool())){
			// homepage = systemIniService
			// .getValue(SchxConstant.SYSSTEMINI_SSO_INDEX_URL);
			// }TODO
			if (StringUtils.isNotBlank(loginoutUrl)) {
				homepage = loginoutUrl;
			}
			String passportURL = PassportClient.getInstance().getLogoutURL(
					req.getServerName(), ticket, homepage, null);
			session.removeAttribute("ticket");
			session.invalidate();

			if (!ucSwitch) {
				getResponse().sendRedirect(passportURL);
			} else {
				UCenterFilter.SignOut(getRequest(), getResponse());
			}
		} else {// 产品登录
			session.invalidate();
			String deploySchool = getSystemDeploySchool();
			if (BaseConstant.SYS_DEPLOY_SCHOOL_ZJSTXA
					.equalsIgnoreCase(deploySchool)) {
				String eisDomain = systemIniService
						.getValue(BaseConstant.EIS5_DOMAIN);
				getResponse().sendRedirect(
						eisDomain + "/fpf/login/invalidate.action");
			}
			if (!ucSwitch) {
				return SUCCESS;
			} else {
				UCenterFilter.SignOut(getRequest(), getResponse());
			}
		}

		return null;

	}

	public String getBackUrl() {
		return getIndexUrl() + "/fpf/homepage/loginForEisOnly.action";
	}

	public String getUnitNameByUserName() {
		if (StringUtils.isNotBlank(uid)) {
			if (uid.length() == 6) {
				Region reg = regionService.getRegionByFullCode(uid,
						Region.TYPE_BUSINESS);
				if (reg != null) {
					jsonMessageDto.setSuccess(true);
					jsonMessageDto.put("unitName", reg.getFullName() + "教育局");
				}
			} else if (uid.length() == 10) {
				School sch = schoolService.getSchoolBy10Code(uid);
				if (sch != null) {
					jsonMessageDto.setSuccess(true);
					jsonMessageDto.put("unitName", sch.getName());
				}
			}
		}
		return SUCCESS;
	}

	public String loginAcrossDomainJson() throws Exception {
		String error = verifyAcrossDomain();
		if (StringUtils.isNotBlank(error)) {
			jsonMap.put("result", 0);// 失败
			jsonMap.put("msg", error);
		} else {
			// 初始化登录信息
			initLoginInfo();
			LoginInfo loginInfo = getLoginInfo();
			jsonMap.put("result", 1);// 成功
			jsonMap.put("userId", loginInfo.getUser().getId());
			jsonMap.put("userName", loginInfo.getUser().getName());
			jsonMap.put("unitClass", loginInfo.getUnitClass());
		}
		return SUCCESS;
	}

	public String loginSchsecurityApp() throws Exception {
		// String error = verifyAcrossDomain();
		User user = userService.getUserByUserName(StringUtils.trim(uid));
		int result = 0;
		String msg = "";

		if (user == null || user.getName() == null) {
			jsonMap.put("result", 0);// 失败
			jsonMap.put("msg", "找不到该用户");
			return SUCCESS;
		}
		String password = user.findClearPassword();
		if (user.getMark() == null || user.getMark() != User.USER_MARK_NORMAL) {
			result = 0;
			msg = "用户状态异常";
		} else if ((password == null && (StringUtils.isBlank(pwd)))
				|| pwd.equals(password)) {
			result = 1;
		} else {
			result = 0;
			msg = "密码错误";
		}
		if (result != 1) {
			jsonMap.put("result", new Integer(result));
			jsonMap.put("msg", msg);
			return SUCCESS;
		}

		String error = null;
		try {
			if (StringUtils.isNotBlank(error)) {
				jsonMap.put("result", 0);// 失败
				jsonMap.put("msg", error);
			} else {
				// 初始化登录信息

				initLoginInfo();
				LoginInfo loginInfo = getLoginInfo();

				if (!checkModuleSecurity(loginInfo)) {
					jsonMap.put("result", 0);// 失败
					jsonMap.put("msg", "对不起，权限不足");
					return SUCCESS;
				}

				jsonMap.put("result", 1);// 成功
				jsonMap.put("userId", loginInfo.getUser().getId());
				jsonMap.put("userName", loginInfo.getUser().getName());
				jsonMap.put("unitClass", loginInfo.getUnitClass());

				// 处理Cookies
				Cookie cookie = new Cookie(BaseConstant.SCHSECURITY_COOKIE_ID,
						loginInfo.getUser().getId());
				cookie.setMaxAge(30 * 24 * 60 * 60);// 一个月
				cookie.setVersion(0);
				cookie.setPath("/schsecurity/mobileh5");
				getResponse().addCookie(cookie);
			}
		} catch (Exception e) {
			LOG.error("校安app登录失败", ExceptionUtils.getFullStackTrace(e));
			jsonMap.put("result", 0);// 失败
			jsonMap.put("msg", "服务器异常，请联系管理员");
		}

		return SUCCESS;
	}

	private boolean checkModuleSecurity(LoginInfo loginInfo) {
		boolean isOk = false;
		Set<Integer> ids = loginInfo.getAllModSet();
		if (CollectionUtils.isEmpty(ids)) {
			return isOk;
		}
		List<String> newIds = Lists.newArrayList();
		for (Integer i : ids) {
			newIds.add(Integer.toString(i));
		}
		List<Module> modules = moduleService.getModules(
				newIds.toArray(new String[0]), loginInfo.getUnitClass());

		if (CollectionUtils.isEmpty(modules))
			return isOk;
		for (Module module : modules) {
			if (StringUtils.contains(module.getUrl(), "hiddenDangerCheckMain")) {
				isOk = true;
				break;
			}
		}
		return isOk;
	}

	public String redirect() {
		return SUCCESS;
	}

	/**
	 * 跨域登陆
	 * 
	 * @return
	 * @throws Exception
	 */
	public String verifyAcrossDomain() throws Exception {
		String error = "";
		pwd = "";
		if (User.isUsernameNotCaseSensitive()) {
			uid = uid.toLowerCase();// 登录名
		}

		if (!verifyEIS(false)) {// 不带密码登录验证
			Collection<String> c = getActionErrors();
			for (String s : c) {
				error += s;
			}
		} else {
			User user = userService.getUserByUserName(uid);
			String pwd = "";
			if (null != user) {
				if (systemDeployService.isConnectPassport()) {
					Account account = PassportClient.getInstance()
							.queryAccountByUsername(uid);
					if (null == account) {
						error = "根据用户名取到的帐户信息为空";
					} else {
						pwd = account.getPassword();
					}
				} else {
					pwd = user.findClearPassword();
				}

				if (pwd == null) {
					pwd = "";
				}
			}

			String encodePwd = MessageDigestUtils.encode(pwd, "MD5")
					+ MessageDigestUtils.encode(pwd, "SHA-1");
			if (!(encodePwd.equalsIgnoreCase(auth))) {
				// serror = "密码错误";
			}
		}

		return error;
	}
	
	/**
	 * 跨域单点登陆 TODO
	 * @return
	 * @throws Exception
	 */
	public String loginForCrossDomain() throws Exception{
		String ownerType = getRequest().getParameter("ownerType");
		String ownerId = getRequest().getParameter("ownerId");
		if(StringUtils.isBlank(ownerType) || StringUtils.isBlank(ownerType)){
			addActionError("缺少账户信息！");
			return getloginIndexName();
		}
		List<User> us = userService.getUsersByOwner(Integer.parseInt(ownerType), new String[]{ownerId});
		if (CollectionUtils.isEmpty(us)) {
			addActionError("找不到该用户！");
			return getloginIndexName();
		}
		User u = us.get(0);
		pwd = u.findClearPassword();
		String encodePwd = MessageDigestUtils.encode(pwd, "MD5")
				+ MessageDigestUtils.encode(pwd, "SHA-1");
		if (!(encodePwd.equalsIgnoreCase(auth))) {
			addActionError("密码错误！");
			return getloginIndexName();
		}
		
		HttpSession session = getRequest().getSession();
		session.removeAttribute(BaseConstant.SESSION_LOGININFO);
		session.removeAttribute(BaseConstant.SESSION_LOGINUSER);
		
		uid = u.getName();
		if (!verifyEIS(false)) {// 不带密码登录验证
			return getloginIndexName();
		}
		
		initLoginInfo();
		if(appId != 0){
			getResponse().sendRedirect(getRequest().getContextPath() + "/fpf/homepage/loginForEisOnly.action?asApp=true&appId="+appId);
		}else{
			getResponse().sendRedirect(getRequest().getContextPath() + "/fpf/homepage/loginForEisOnly.action");
		}
		return SUCCESS;
	}
	
	public String getloginIndexName() throws Exception{
		String deploySchool = getSystemDeploySchool();
		if (BaseConstant.SYS_DEPLOY_SCHOOL_HZZC
				.equalsIgnoreCase(deploySchool)
				|| BaseConstant.SYS_DEPLOY_SCHOOL_NBZX
						.equalsIgnoreCase(deploySchool)
				|| BaseConstant.SYS_DEPLOY_SCHOOL_NHZG
						.equalsIgnoreCase(deploySchool)
				|| BaseConstant.SYS_DEPLOY_SCHOOL_XIAN
						.equalsIgnoreCase(deploySchool)
				|| BaseConstant.SYS_DEPLOY_SCHOOL_ZJSTXA
						.equalsIgnoreCase(deploySchool)
				|| BaseConstant.SYS_DEPLOY_SCHOOL_HDJY
						.equalsIgnoreCase(deploySchool)
				|| BaseConstant.SYS_DEPLOY_SCHOOL_CIXI
						.equalsIgnoreCase(deploySchool)
				|| BaseConstant.SYS_DEPLOY_SCHOOL_GDMMJY
						.equalsIgnoreCase(deploySchool)
				|| BaseConstant.SYS_DEPLOY_SCHOOL_FCSZ
						.equalsIgnoreCase(deploySchool)
				|| BaseConstant.SYS_DEPLOY_SCHOOL_XINJIANG
						.equalsIgnoreCase(deploySchool)
				|| BaseConstant.SYS_DEPLOY_SCHOOL_NC
						.equalsIgnoreCase(deploySchool)
				|| BaseConstant.SYS_DEPLOY_SCHOOL_JIAN
						.equalsIgnoreCase(deploySchool)) {
			return deploySchool;
		}
		if (BaseConstant.SYS_DEPLOY_SCHOOL_SCZG.equals(deploySchool)) {
			getResponse()
					.sendRedirect(
							systemIniService
									.getValue(SchxConstant.SYSSTEMINI_SSO_INDEX_URL));
			return NONE;
		}
		return LOGIN;
	}

	/**
	 * 身份切换
	 * 
	 * @return
	 * @throws Exception
	 */
	public String loadMultipleIdentities() throws Exception {
		User u = this.getLoginInfo().getUser();
		List<User> us = this.userService.getUserByMobilePhone(
				u.getMobilePhone(), -1);
		showus = new ArrayList<User>();
		if (CollectionUtils.isNotEmpty(us)) {
			String upwd = u.findClearPassword();
			if (StringUtils.isBlank(upwd)) {
				upwd = "";
			}
			for (User u1 : us) {
				String password = null;
				/** password城域库中密码, pwd为用户输入密码 * */
				password = u1.findClearPassword();
				if ("".equals(password)) {
					password = null;
				}
				if ((password == null && (StringUtils.isBlank(u.getPassword())))
						|| upwd.equals(password)) {
					showus.add(u1);
				}
			}
		} else {
			showus.add(u);
		}
		checkUserId = u.getId();
		return SUCCESS;
	}

	public String redIdentitie() throws Exception {
		HttpServletRequest req = ServletActionContext.getRequest();
		HttpSession session = req.getSession();

		session.removeAttribute(BaseConstant.SESSION_LOGININFO);
		session.removeAttribute(BaseConstant.SESSION_LOGINUSER);

		if (systemDeployService.isConnectPassport()) {// 从passport登录
			String ticket = (String) session.getAttribute("ticket");
			if (!StringUtils.isBlank(ticket)) {
				// 从ticket2SessionMap中清除ticket
				SessionManager.removeTicketInMap(ticket);
				// 清除ticket是为了在session失效的时候不再重复通知中心
				PassportClient.getInstance().invalidate(ticket);
			}
			session.removeAttribute("ticket");
			session.invalidate();
		}

		User u = userService.getUser(checkUserId);
		jsonMessageDto.put("username", u.getName());
		jsonMessageDto.put("password", u.findClearPassword());
		jsonMessageDto.put("isconPassport",
				systemDeployService.isConnectPassport());
		return SUCCESS;
	}

	public boolean isShowVerifyCode() {
		String showVerifyCode = systemIniService
				.getValue(BaseConstant.VERIFY_CODE_SWITCH);
		return "Y".equals(showVerifyCode) ? true : false;
	}

	public boolean isConnectPassport() {
		return systemDeployService.isConnectPassport();
	}

	public String getPassportUrl() {
		if (isSecondUrl())
			return systemDeployService.getPassportSecondUrl();
		else
			return systemDeployService.getPassportUrl();
	}

	public int getServerId() {
		if (serverId == 0) {
			String appCode = SystemDeployUtils.getCurrentDeployAppCode();
			Server app = serverService.getServerByServerCode(appCode);
			if (null != app) {
				serverId = Integer.parseInt(app.getId());
			}
		}
		return serverId;
	}

	public Map<String, String> getProductParamMap() {
		return productParamService.getProductParamCodeValueMap();
	}

	/**
	 * 是否显示公司版权
	 * 
	 * @return
	 */
	public boolean isShowFoot() {
		boolean sign = false;
		String param = systemIniService.getValue(SHOW_FOOT);
		if (StringUtils.isNotEmpty(param) && "1".equals(param)) {
			sign = true;
		}
		return sign;
	}

	/**
	 * 作为app接入
	 * 
	 * @return
	 */
	public boolean isDeployAsApp() {
		if ("fkxt".equals(getPlatFormSubsystem())) {
			return true;
		}
		boolean sign = false;
		String param = systemIniService.getValue(BaseConstant.DEPLOY_AS_APP);
		if (StringUtils.isNotEmpty(param) && "1".equals(param)) {
			sign = true;
		}
		return sign;
	}

	public String getSystemVersion() {
		String contextRoot = getServletContext().getRealPath("/");
		File libDir = new File(new File(contextRoot), "META-INF/MANIFEST.MF");
		String line = "";
		String version = "";
		try {
			BufferedReader fr = new BufferedReader(new FileReader(libDir));
			while ((line = fr.readLine()) != null) {
				version += line + "\r\n";
			}
		} catch (FileNotFoundException e) {
		} catch (IOException e) {
		}
		return version;
	}

	// some setters & getters
	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getTicket() {
		return ticket;
	}

	public void setTicket(String ticket) {
		this.ticket = ticket;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getAuth() {
		return auth;
	}

	public void setAuth(String auth) {
		this.auth = auth;
	}

	public String getInput() {
		return input;
	}

	public void setInput(String input) {
		this.input = input;
	}

	public String getSrc() {
		return src;
	}

	public void setSrc(String src) {
		this.src = src;
	}

	public String getRedirectUrl() {
		return redirectUrl;
	}

	public void setRedirectUrl(String redirectUrl) {
		this.redirectUrl = redirectUrl;
	}

	public void setSerialManager(SerialManager serialManager) {
		this.serialManager = serialManager;
	}

	public SerialManager getSerialManager() {
		return this.serialManager;
	}

	public void setNeedRegister(boolean needRegister) {
		this.needRegister = needRegister;
	}

	public boolean getNeedRegister() {
		return this.needRegister;
	}

	public void setUserSetService(UserSetService userSetService) {
		this.userSetService = userSetService;
	}

	public int getCurrentMonth() {
		Calendar calendar = Calendar.getInstance();
		return calendar.get(Calendar.MONTH) + 1;
	}

	public void setSchoolService(SchoolService schoolService) {
		this.schoolService = schoolService;
	}

	public JSONMessageDto getJsonMessageDto() {
		return jsonMessageDto;
	}

	public void setJsonMessageDto(JSONMessageDto jsonMessageDto) {
		this.jsonMessageDto = jsonMessageDto;
	}

	public void setRegionService(RegionService regionService) {
		this.regionService = regionService;
	}

	public Map<String, Object> getJsonMap() {
		return jsonMap;
	}

	public void setJsonMap(Map<String, Object> jsonMap) {
		this.jsonMap = jsonMap;
	}

	public Module getModule() {
		return module;
	}

	public void setModule(Module module) {
		this.module = module;
	}

	public String getModuleIDStr() {
		return moduleIDStr;
	}

	public void setModuleIDStr(String moduleIDStr) {
		this.moduleIDStr = moduleIDStr;
	}

	public void setModuleService(ModuleService moduleService) {
		this.moduleService = moduleService;
	}

	public List<User> getShowus() {
		return showus;
	}

	public String getCheckUserId() {
		return checkUserId;
	}

	public void setCheckUserId(String checkUserId) {
		this.checkUserId = checkUserId;
	}

	public String getLoginoutUrl() {
		return loginoutUrl;
	}

	public void setLoginoutUrl(String loginoutUrl) {
		this.loginoutUrl = loginoutUrl;
	}

	public String getUucToken() {
		return uucToken;
	}

	public void setUucToken(String uucToken) {
		this.uucToken = uucToken;
	}

	public void setSysUserBindService(SysUserBindService sysUserBindService) {
		this.sysUserBindService = sysUserBindService;
	}

	public String getRemoteUserId() {
		return remoteUserId;
	}

	public void setRemoteUserId(String remoteUserId) {
		this.remoteUserId = remoteUserId;
	}

	public boolean isRemote() {
		return isRemote;
	}

	public void setRemote(boolean isRemote) {
		this.isRemote = isRemote;
	}

	public CASDto getCasDto() {
		return casDto;
	}

	public void setCasDto(CASDto casDto) {
		this.casDto = casDto;
	}

	public String getApCode() {
		return apCode;
	}

	public void setApCode(String apCode) {
		this.apCode = apCode;
	}

	public void setTeacherService(TeacherService teacherService) {
		this.teacherService = teacherService;
	}

	public String getAsApp() {
		return asApp;
	}

	public void setAsApp(String asApp) {
		this.asApp = asApp;
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
