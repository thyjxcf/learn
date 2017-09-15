/* 
 * <p>ZDSoft学籍系统（stusys）V3.5</p>
 * @author zhangza
 * @since 1.0
 * @version $Id$
 */
package net.zdsoft.eis.system.frame.web;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.buffalo.protocal.BuffaloProtocal;
import net.zdsoft.eis.base.common.entity.User;
import net.zdsoft.eis.base.common.service.SysOptionService;
import net.zdsoft.eis.base.common.service.SystemIniService;
import net.zdsoft.eis.base.common.service.UserService;
import net.zdsoft.eis.base.constant.BaseConstant;
import net.zdsoft.eis.base.constant.UCenterConstant;
import net.zdsoft.eis.base.deploy.SystemDeployService;
import net.zdsoft.eis.base.subsystemcall.service.OfficeSubsystemService;
import net.zdsoft.eis.frame.client.BaseLoginInfo;
import net.zdsoft.eis.frame.util.RedisUtils;
import net.zdsoft.eis.remote.SchxConstant;
import net.zdsoft.eis.system.data.service.UserLoginService;
import net.zdsoft.keel.action.Reply;
import net.zdsoft.keel.util.FileUtils;
import net.zdsoft.keel.util.URLUtils;
import net.zdsoft.keelcnet.config.BootstrapManager;
import net.zdsoft.keelcnet.config.ContainerManager;
import net.zdsoft.leadin.util.ConfigFileUtils;
import net.zdsoft.leadin.util.FilterParamUtils;
import net.zdsoft.passport.service.client.PassportClient;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.StringEscapeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PermissionFilter implements Filter {
	
	private static final Logger LOG = LoggerFactory.getLogger(PermissionFilter.class);
	
	private static final String P3P_HEADER = "CP=\"NOI CURa ADMa DEVa TAIa OUR"
			+ " BUS IND UNI COM NAV INT\"";
	private static final String OUTPUT_ENCODING = "utf-8";

	private String[] passportIgnorePath;
	private String[] eisIgnorePath;
	private String[] ignorePath;
	private String[] wenxuanIgnorePath;

	private Map<String, String[]> pathUserTypeMap = new HashMap<String, String[]>(); // 路径与用户类型的对应关系

	private String eisLoginPostfix = "/fpf/homepage/eisLogin.action";// eis登录页
	private String eisLoginForPassportPostfix = "/fpf/login/loginForPassport.action";// eis登录页

	private SystemDeployService systemDeployService;
	private SystemIniService systemIniService;
	private UserService userService;
	private UserLoginService userLoginService;
	private SysOptionService sysOptionService;
	
	private boolean isOaQrCode;//true:需要开放二维码且没有初始化QrCodeService,false:不需要开发二维码或已经初始化QrCodeService

	public void init(FilterConfig filterConfig) throws ServletException {
		systemDeployService = (SystemDeployService) ContainerManager
				.getComponent("systemDeployService");
		systemIniService = (SystemIniService) ContainerManager
				.getComponent("systemIniService");
		if (sysOptionService == null) {
			sysOptionService = (SysOptionService) ContainerManager
					.getComponent("sysOptionService");
		}
		
		userService = (UserService) ContainerManager.getComponent("userService");
		userLoginService = (UserLoginService) ContainerManager.getComponent("userLoginService");
		
		try {
			isOaQrCode = "1".equals(systemIniService.getValue(BaseConstant.NEEDQRCODE))?true:false;
		} catch (Exception e) {
			isOaQrCode = false;
			System.out.println("============获取移动OA二维码开关失败============");
		}
		
		// 忽略路径
		ignorePath = FilterParamUtils
				.getParamValues(filterConfig, "ignorePath");
		passportIgnorePath = FilterParamUtils.getParamValues(filterConfig,
				"passportIgnorePath");
		eisIgnorePath = FilterParamUtils.getParamValues(filterConfig,
				"eisIgnorePath");
		wenxuanIgnorePath = FilterParamUtils.getParamValues(filterConfig,
				"wenxuanIgnorePath");

		String tmp = FileUtils.readProperties(
				ConfigFileUtils
						.getFileStream("globalMessages_zh_CN.properties"))
				.getProperty("eis.login.postfix");
		if (StringUtils.isNotEmpty(tmp)) {
			eisLoginPostfix = "/" + tmp;
		}

		// 读取各用户的权限配置文件：有独立登录的子系统，如招生
		List<InputStream> subystemPerms = ConfigFileUtils
				.getConfigFileStreams("*_permission.properties");
		for (InputStream is : subystemPerms) {
			Properties prop = FileUtils.readProperties(is);

			String _sessionName = prop.getProperty("session.name");
			if (StringUtils.isBlank(_sessionName)) {
				System.out.println("错误：权限配置文件中的session.name不能为空");
			}

			String _2domainName = prop.getProperty("2domain.name");

			String _loginPath = prop.getProperty("login.path");
			if (StringUtils.isBlank(_loginPath)) {
				System.out.println("错误：权限配置文件中的login.path不能为空");
			} else {
				if (!(_loginPath.startsWith("/")))
					_loginPath = "/" + _loginPath;
			}

			String[] _ignorePaths = FilterParamUtils.splitByComma(prop
					.getProperty("ignore.path"));
			String[] _checkPaths = FilterParamUtils.splitByComma(prop
					.getProperty("check.path"));

			// 加入到忽略路径中
			ignorePath = (String[]) ArrayUtils.addAll(ignorePath, _ignorePaths);
			ignorePath = (String[]) ArrayUtils.add(ignorePath, _loginPath);

			for (String cp : _checkPaths) {
				if (pathUserTypeMap.containsKey(cp)) {
					// 也不能有包含关系，暂不校验
					System.out.println("警告：权限配置文件中的check.path重复 [" + cp
							+ "]对应[" + pathUserTypeMap.get(cp) + "]与["
							+ _loginPath + "]");
				}
				pathUserTypeMap.put(cp, new String[] { _sessionName,
						_loginPath, _2domainName });
			}
		}
	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {

		if (request instanceof HttpServletRequest) {
			// String basePath = RedisUtils.get("EIS.BASE.PATH.DGSTUSYS");
			HttpServletRequest req = (HttpServletRequest) request;
			// if(StringUtils.isBlank(basePath)) {
			String basePath = req.getScheme() + "://" + req.getServerName()
					+ ":" + req.getServerPort() + req.getContextPath();
			RedisUtils.set("EIS.BASE.PATH.V6", basePath);
			// }
			
			
			/*
			 * 初始化二维码
			 * 每次启动程序后第一次set EIS.BASE.PATH.V6时 自动生成二维码（qrcodeservice依赖EIS.BASE.PATH.V6）
			 */
			if(isOaQrCode && StringUtils.isNotBlank(basePath)){
				OfficeSubsystemService officeSubsystemService = (OfficeSubsystemService) ContainerManager.getComponent("officeSubsystemService");
				officeSubsystemService.initQrCode();
				//只调用一次
				isOaQrCode = false;
			}
			
			HttpServletResponse res = (HttpServletResponse) response;
			HttpSession session = req.getSession(false);
			String servletPath = req.getServletPath();

			String[] userTypes = getUserType(servletPath, pathUserTypeMap);
			BaseLoginInfo login = null;
			// 设置sessionstatus为空
			res.setHeader("sessionstatus", "");
			
			String uri = req.getRequestURI();
			if(StringUtils.contains(uri, "schsecurity/mobileh5")){
				login = dealSchsecurityAppLogin(req);
				if(login == null){
					res.sendRedirect(basePath+"/schsecurity/mobileh5/login.html");
				}
				session = req.getSession(false);
			}
			
			//
			if(BootstrapManager.isDevModel()){
				if(StringUtils.contains(uri, "/desktop/unify")){
					chain.doFilter(req, res);
					return ;
				}
			}
			
			if (null != session) {
				if (null != userTypes) {
					login = (BaseLoginInfo) session.getAttribute(userTypes[0]);
				} else {
					login = (BaseLoginInfo) session
							.getAttribute(BaseConstant.SESSION_LOGININFO);

					// 管理平台：可能会引用到一些没有在check.path中配置的路径
					if (null == login) {
						login = (BaseLoginInfo) session
								.getAttribute(BaseConstant.SESSION_BACKGROUND_LOGININFO);
					}
				}
			}
			
			if (login == null
					&& !FilterParamUtils.containsPath(servletPath, ignorePath)) {

				String loginUrl = null;// 登录地址
				boolean ignore = false;// 是否忽略

				if (null != userTypes) {
					loginUrl = basePath + userTypes[1];
					String _2domainName = userTypes[2];
					if (StringUtils.isNotBlank(_2domainName)) {
						loginUrl += "?" + userTypes[2] + "="
								+ req.getParameter(_2domainName);
					}
				} else {
					// 四川定制 登陆跳转
					String systemDeploySchool = systemIniService
							.getValue(BaseConstant.SYSTEM_DEPLOY_SCHOOL);
					if(!FilterParamUtils.containsPath(servletPath, passportIgnorePath) 
							&& !FilterParamUtils.containsPath(servletPath, eisIgnorePath) 
							&& BaseConstant.SYS_DEPLOY_SCHOOL_SCZG.equals(systemDeploySchool)){
						loginUrl = systemIniService
								.getValue(SchxConstant.SYSSTEMINI_SSO_INDEX_URL);
					}
					if (StringUtils.isBlank(loginUrl)) {
						boolean ucSwitch = systemIniService.getBooleanValue(UCenterConstant.SYSTEM_UCENTER_SWITCH); 
						if(ucSwitch){
							if(FilterParamUtils.containsPath(servletPath,
									passportIgnorePath)){
								ignore = true;
							}
							if (!ignore) {
								String uid = (String) req.getAttribute("uid");
								if (StringUtils.isNotBlank(uid)) {
									res.sendRedirect(req.getContextPath()
											+ "/common/open/loginRedirect.action?uid="
											+ uid);
									return;
								}
							}
						} else {
							boolean wen = sysOptionService
									.getBooleanValue("SYSTEM.WENXUAN.SWITCH");
							if (wen) {// 文轩登录
								// 共享session
								res.setHeader("P3P", P3P_HEADER);
								if (FilterParamUtils.containsPath(servletPath,
										wenxuanIgnorePath)) {
									ignore = true;
								}
								loginUrl = getWenxuanLoginUrl(req, basePath);
							} else if (systemDeployService.isConnectPassport()) {// passport登录
								// 共享session
								res.setHeader("P3P", P3P_HEADER);
	
								if (FilterParamUtils.containsPath(servletPath,
										passportIgnorePath)) {
									ignore = true;
								}
								if (!ignore) {
									loginUrl = getPassportLoginUrl(req, basePath);
								}
							} else {
								loginUrl = basePath + eisLoginPostfix;
								if (FilterParamUtils.containsPath(servletPath,
										eisIgnorePath)) {
									ignore = true;
								}
							}
						}
					}
				}

				if (!ignore) {
					// 判断是否是buffalo请求，有X-Buffalo-Version头的为buffalo请求
					if (req.getHeader("X-Buffalo-Version") != null) {
						OutputStreamWriter writer = new OutputStreamWriter(
								res.getOutputStream(), OUTPUT_ENCODING);
						Reply reply = new Reply();
						reply.setScript(this.getTimeoutScript(loginUrl));
						reply.addActionError("已经超时，请重新登录！");
						BuffaloProtocal.getInstance().marshall(reply, writer);
						return;
					}

					// prototype.js中的ajax请求
					if (req.getHeader("X-Prototype-Version") != null) {
						res.setContentType("text/javascript");
						res.setCharacterEncoding("utf-8");
						res.getWriter().write("超时或者尚未登录，请重新登录");
						return;
					}
					// jquery ajax 请求
					if (req.getHeader("x-requested-with") != null
							&& req.getHeader("x-requested-with")
									.equalsIgnoreCase("XMLHttpRequest")) {
						res.setHeader("sessionstatus", "timeout");// 在响应头设置session状态]
						return;
					}
					res.sendRedirect(loginUrl);
					return;
				}
			}
		}
		chain.doFilter(request, response);
	}
	
	/**
	 * 取文轩登录地址
	 * 
	 * @param req
	 * @param basePath
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public String getWenxuanLoginUrl(HttpServletRequest req, String basePath)
			throws UnsupportedEncodingException {
		// url是否直接进入指定的地址，否则默认进入首页
		// backURL 登录后返回的地址
		// input 指定的登录界面，不指定则使用预先定义好的登录界面
		// 注意配置office对接及eis城域登陆的时候首页一定要配置正确否则会死循环参数相互不影响
		String servletPath = req.getServletPath();
		String backUrl = basePath + servletPath;
		@SuppressWarnings("unchecked")
		Enumeration<String> e = req.getParameterNames();
		String params = "";
		boolean b = false;
		while (e.hasMoreElements()) {
			if (b) {
				params += "&";
			}
			String p = e.nextElement();
			params += p + "=" + req.getParameter(p);
			b = true;
		}
		if (StringUtils.isNotEmpty(params)) {
			backUrl += "?" + params;
		}

		String wenxuanURL = sysOptionService
				.getValue("SYSTEM.WENXUAN.LOGINURL");
		backUrl = URLEncoder.encode(backUrl, "utf-8");
		wenxuanURL = URLUtils.addQueryString(wenxuanURL, "url=" + backUrl);

		return wenxuanURL;
	}

	/**
	 * 取passport登录地址
	 * 
	 * @param req
	 * @param basePath
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public String getPassportLoginUrl(HttpServletRequest req, String basePath)
			throws UnsupportedEncodingException {

		String servletPath = req.getServletPath();

		// url是否直接进入指定的地址，否则默认进入首页
		String t = req.getParameter("rd");
		if (StringUtils.isBlank(t)) {
			t = "y";
		}
		String redirectURL = null;
		boolean r = false;
		if (t.equals("y")) {
			r = true;
			redirectURL = servletPath;

			@SuppressWarnings("unchecked")
			Enumeration<String> e = req.getParameterNames();
			if (!e.hasMoreElements()) {
				r = false;
			}
			String params = "";
			boolean b = false;
			while (e.hasMoreElements()) {
				if (b) {
					params += "&";
				}
				String p = e.nextElement();
				params += p + "=" + req.getParameter(p);
				b = true;
			}

			if (StringUtils.isNotEmpty(params)) {
				redirectURL += "?" + params;
			}
		}

		// 判断取内网地址还是缺省的第一个地址;
		String eisURL = systemDeployService.getEisUrl();
		// String eisuSecondURL = systemDeployService.getEisSecondUrl();
		String homepage = systemDeployService.getIndexUrl();
		if (eisURL != null && eisURL.indexOf(req.getServerName()) < 0) {
			homepage = systemDeployService.getIndexSecondUrl();
			eisURL = systemDeployService.getIndexSecondUrl();
		}
		
		String backUrl = basePath + servletPath;
		
		//接入7.0桌面
		String url = req.getParameter("url");
		if(org.apache.commons.lang3.StringUtils.isNotBlank(url))
		backUrl = URLUtils.addQueryString(backUrl, "url="+url);

		if (systemDeployService.isConnectOffice()) {
			// 跳转到办公平台
			if (!r) {
				redirectURL = homepage;
			} else {
				if (StringUtils.isBlank(redirectURL)) {
					redirectURL = basePath + eisLoginPostfix;
					backUrl = redirectURL;
				} else {
					backUrl = basePath + redirectURL;
					if (StringUtils.indexOf(redirectURL, "http:") != 0) {
						redirectURL = basePath + servletPath;
					}
				}
			}
		} else {
			if (systemDeployService.isEisLoginForPassport()) {
				if (StringUtils.isNotBlank(eisURL)) {
					redirectURL = eisURL + eisLoginForPassportPostfix;
				} else {
					redirectURL = basePath + eisLoginForPassportPostfix;
				}
			} else {// passport页面登陆
				backUrl = basePath + redirectURL;
				redirectURL = null;
			}
		}
		
		String passportURL = PassportClient.getInstance()
				.getLoginURL(req.getServerName(), backUrl,
						req.getContextPath(),redirectURL);

		// passportURL举例：http://192.168.0.184/login?server=51301&url=http://localhost:7080/fpf/homepage/eisLogin.action?root=1&auth=b584384a31618e8f35cd57d1a184f069
		passportURL = URLEncoder.encode(passportURL, "utf-8");
		String loginUrl = basePath
				+ "/fpf/homepage/redirectUrl.action?redirectUrl=" + passportURL;
		return StringEscapeUtils.escapeHtml4(loginUrl);
	}

	/**
	 * ajax访问，超时的时候客户端执行的脚本
	 */
	private String getTimeoutScript(String url) {
		StringBuilder sb = new StringBuilder();
		sb.append("alert(\"超时或者尚未登录，请重新登录\");");
		sb.append("var child = top;");
		sb.append("var args = child.dialogArguments;");
		sb.append("var parent;");
		sb.append("if(args){ parent = args.handle;}");
		sb.append("else{ parent = child.opener;}");
		sb.append("while(parent && !parent.top.closed){");
		sb.append("child.close();");
		sb.append("child = parent.top;");
		sb.append("args = child.dialogArguments;");
		sb.append("if(args){parent = args.handle;}");
		sb.append("else{parent = child.opener;}");
		sb.append("}");
		sb.append("child.location.replace(");
		sb.append(url + ");");
		sb.append("child.focus();");
		return sb.toString();
	}

	public void destroy() {
		ignorePath = null;
		passportIgnorePath = null;
		eisIgnorePath = null;

		// 当程序结束的时候，应该关闭 PassportClient 服务
		if (systemDeployService.isConnectPassport()) {
			PassportClient.getInstance().shutdown();
		}

		systemDeployService = null;
	}

	/**
	 * 层层解析路径查找用户对应的信息
	 * 
	 * @param servletPath
	 * @param pathUserTypeMap
	 * @return
	 */
	private static String[] getUserType(String servletPath,
			Map<String, String[]> pathUserTypeMap) {
		String[] userTypes = null;
		String currentPath = servletPath;
		int pos = -1;
		do {
			String path = currentPath;
			if (!(currentPath.endsWith(".action"))) {
				path = currentPath + "/";
			}
			userTypes = pathUserTypeMap.get(path);
			pos = currentPath.lastIndexOf("/");
			if (pos != -1) {
				currentPath = currentPath.substring(0, pos);
			}
		} while (null == userTypes && pos != -1);
		return userTypes;
	}

	private BaseLoginInfo dealSchsecurityAppLogin(HttpServletRequest req){
		HttpSession session = req.getSession(false);
		BaseLoginInfo login = null;
		
		if(session != null){
			login = (BaseLoginInfo) session.getAttribute(BaseConstant.SESSION_LOGININFO);
			if(login != null){
				return login;
			}
		}
		String userId = null;
		Cookie[] cookies = req.getCookies();
		for(Cookie cookie : cookies){
			if(StringUtils.equals(cookie.getName(),"schsecurity_app_login_user_id")
					&& StringUtils.isNotBlank(cookie.getValue())){
				userId = cookie.getValue();
				break;
			}
		}
		LOG.info("[ 登录失效，检查Cookie, userId = "+userId+" ]");
		System.out.println("[ 登录失效，检查Cookie, userId = "+userId+" ]");
		try {
			if(StringUtils.isNotBlank(userId)){
				//HomepageAction loginAction = (HomepageAction)ContainerManager.getComponent("homePageAction");
				//Map<String,Object> actions = ServletActionContext.getActionContext(req).getContextMap();
				//ServletActionContext.getContext().getContextMap();
				//System.out.println(CollectionUtils.size(actions));
				LOG.info("[尝试内部登录...]");
				login = initAccount(userId);
				if(session != null){
					session.setAttribute(BaseConstant.SESSION_LOGININFO, login);
				}else{
					session = req.getSession();
					session.setAttribute(BaseConstant.SESSION_LOGININFO, login);
				}
				return login;
				
			}
		} catch (Exception e) {
			LOG.error("[校安app后台通过cookie尝试登录失败！]",e);
		}
		
		return (BaseLoginInfo) req.getSession().getAttribute(BaseConstant.SESSION_LOGININFO);
	}
	
	private BaseLoginInfo initAccount(String userId){
		BaseLoginInfo login = null;
		//校验密码
		User user = userService.getUser(userId);
		if(user != null){
			login = userLoginService.initLoginInfo(user.getName());
		}
		return login;
	}
	
	public static void main(String[] args) {
		String[] a = new String[] { "1", "2", "3" };
		String[] b = new String[] { "4", "5", "6" };
		a = (String[]) ArrayUtils.addAll(a, b);
		System.out.println(a.length);
	}
}
