package net.zdsoft.eis.system.frame.web.ucenter;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.zdsoft.eis.base.common.service.SystemIniService;
import net.zdsoft.eis.base.constant.BaseConstant;
import net.zdsoft.eis.base.constant.UCenterConstant;
import net.zdsoft.eis.frame.client.BaseLoginInfo;
import net.zdsoft.keel.util.FileUtils;
import net.zdsoft.keelcnet.config.ContainerManager;
import net.zdsoft.leadin.util.ConfigFileUtils;
import net.zdsoft.leadin.util.FilterParamUtils;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.hzdysoft.uc.filter.UcFilter;

/**
 * 
 * @author weixh
 * @since 2016-12-2 上午11:12:37
 */
public class UCenterFilter extends UcFilter {
	private SystemIniService systemIniService;
	private String[] ignorePath;
	private String[] passportIgnorePath;
	private Map<String, String[]> pathUserTypeMap = new HashMap<String, String[]>(); // 路径与用户类型的对应关系

	@Override
	public void destroy() {
		ignorePath = null;
		passportIgnorePath = null;
		pathUserTypeMap.clear();
		systemIniService = null;
		super.destroy();
	}

	@Override
	public void doFilter(ServletRequest servletRequest,
			ServletResponse servletResponse, FilterChain filterChain)
			throws IOException, ServletException {
		boolean ucSwitch = systemIniService.getBooleanValue(UCenterConstant.SYSTEM_UCENTER_SWITCH); 
		if(!ucSwitch){
			filterChain.doFilter(servletRequest, servletResponse);
			return;
		}
		HttpServletRequest req = (HttpServletRequest) servletRequest;
		HttpSession session = req.getSession(false);
		BaseLoginInfo login = null;
		if (session != null) {
			login = (BaseLoginInfo) session
					.getAttribute(BaseConstant.SESSION_LOGININFO);
		}
		if(login != null 
				|| FilterParamUtils.containsPath(req.getServletPath(), ignorePath)
				|| FilterParamUtils.containsPath(req.getServletPath(), passportIgnorePath)){
			filterChain.doFilter(servletRequest, servletResponse);
			return;
		}
		
		super.doFilter(servletRequest, servletResponse, filterChain);
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		systemIniService = (SystemIniService) ContainerManager
				.getComponent("systemIniService");
		// 忽略路径
		ignorePath = FilterParamUtils
				.getParamValues(filterConfig, "ignorePath");
		passportIgnorePath = FilterParamUtils.getParamValues(filterConfig,
				"passportIgnorePath");
		
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
		
		super.init(filterConfig);
	}

}
