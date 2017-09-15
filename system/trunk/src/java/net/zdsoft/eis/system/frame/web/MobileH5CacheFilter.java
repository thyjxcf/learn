package net.zdsoft.eis.system.frame.web;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.regex.Matcher;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;
import net.zdsoft.eis.base.common.entity.User;
import net.zdsoft.eis.base.common.service.UnitService;
import net.zdsoft.eis.base.common.service.UserService;
import net.zdsoft.eis.base.subsystemcall.service.OfficeSubsystemService;
import net.zdsoft.eis.base.util.MD5Builder;
import net.zdsoft.keelcnet.config.ContainerManager;
import net.zdsoft.leadin.util.FilterParamUtils;

import org.apache.commons.lang.StringUtils;

/**
 * 微课对接手机端html、js、css文件版本号控制
 * @author like 2017-06-19
 */
public class MobileH5CacheFilter implements Filter {
	/*
	 * 手机端相关的html、js、css文件版本号控制
	 * 1、启动服务时 初始化需要控制版本号的html、js、css文件  并存放到对应的map（key：文件路径，value：该文件的md5摘要信息  不同的内容对应不同的md5摘要信息）
	 * 2、请求html、js、css资源时，判断版本号  若一致则放行，否则替换为新的版本号
	 * 
	 * fliter控制版本号可行性分析：
	 * 1、客户端第一次访问html文件以及页面上引用的js、css文件 会走进过滤器，过滤器会添加一个版本号并重定向
	 * 2、浏览器中对于重定向之前的url链接不会缓存  且没有response信息，而会对重定向之后的url链接做缓存
	 * 3、客户端第二次访问html页面以及页面上引用的js、css时 会用页面上引用的js、css地址请求服务器资源 因为这些地址没有做过缓存，所以依旧会进入过滤器
	 * 4、若第二次服务器重定向的地址（主要是版本号）跟浏览器中缓存的地址相同  则浏览器取缓存中的文件信息， 否则取服务器上的资源
	 * 
	 *  备注：理论上新增的页面都没有问题，若是之前已开发的手机端页面 第一次使用MobileH5CacheFilter进行版本号控制，则需要修改html页面上引用的js、css地址
 	 *  以保证浏览器中没有html中所引用的js、css地址的缓存（这样才能进入过滤器进行版本号控制）
	 */
	
	private static final String VERSION_KEY = "fvd";//filter-version-degist
	//提示信息页面
	private static String errorHtmlSrc = "/office/mobileh5/error.html";
	
	private UserService  userService;
	private OfficeSubsystemService officeSubsystemService;
	
	//需要过滤的路径
	private String[] filterPaths;
	
	private Map<String, String> htmlMap;
	private Map<String, String> jsMap;//key统一转换为‘/’网络路径 value=版本号
	private Map<String, String> cssMap;

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		System.out.println("===================初始化MobileH5CacheFilter==开始==================");
		
		userService = (UserService) ContainerManager.getComponent("userService");
		officeSubsystemService = (OfficeSubsystemService) ContainerManager.getComponent("officeSubsystemService");
		
		// 忽略路径
		filterPaths = FilterParamUtils.getParamValues(filterConfig, "filterPaths");
		htmlMap = new HashMap<String, String>();
		jsMap = new HashMap<String, String>();
		cssMap = new HashMap<String, String>();
		
		if(filterPaths==null || filterPaths.length==0)
			return;
		String rootPath = filterConfig.getServletContext().getRealPath("/");  
		File file = null;
		
		long start = System.currentTimeMillis();
		for(String filterPath : filterPaths){
			file = new File(rootPath+filterPath);
			if(file.exists()){
				addVersionToMap(rootPath, file);
			}else{
				if(filterPath.startsWith("/static")){
					//static目录特殊处理
					addVersionToMapStatic(rootPath, filterPath);
				}else{
					continue;
				}
			}
		}
		
		System.out.println("时间："+(System.currentTimeMillis()-start));
		
//		for(String key : jsMap.keySet()){
//			System.out.println("移动端js:"+key+"==="+jsMap.get(key));
//		}
		
		System.out.println("===================初始化MobileH5CacheFilter==结束==================");
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain filterChain) throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		
		String servletPath = httpRequest.getServletPath();
		
		if(FilterParamUtils.containsPath(servletPath, filterPaths)){
			/*
			 * 手机端相关的html、js、css文件
			 * 版本号为该文件md5摘要，若返回的版本号跟浏览器中缓存的js版本号一致 则浏览器取缓存中的文件
			 */
			String requestUrl = httpRequest.getRequestURL().toString();
			if(!servletPath.startsWith("/")){
				servletPath = "/"+servletPath;
			}
			
			String requestVersion = httpRequest.getParameter(VERSION_KEY);
			String version = "v1.0_20170619";//默认版本号 
			if(servletPath.contains(".html")){
				if(htmlMap.containsKey(servletPath)){
					version = htmlMap.get(servletPath);
				}
			}else if(servletPath.contains(".js")){
				if(jsMap.containsKey(servletPath)){
					version = jsMap.get(servletPath);
				}
			}else if(servletPath.contains(".css")){
				if(cssMap.containsKey(servletPath)){
					version = cssMap.get(servletPath);
				}
			}
			
			/*
			 * 版本号一致则放行
			 */
			if(version.equals(requestVersion)){
				filterChain.doFilter(request, response);
				return;
			}
			
			/*
			 * 更新版本号
			 */
			String queryStr = httpRequest.getQueryString();
			if(StringUtils.isBlank(queryStr)){
				requestUrl += "?"+VERSION_KEY+"="+version;
			}else{
				requestUrl += "?" + queryStr;
				if(requestUrl.indexOf(VERSION_KEY) == -1){
					requestUrl += "&"+VERSION_KEY+"="+version;
				}else{
					requestUrl = replaceAccessTokenReg(requestUrl, VERSION_KEY, version);//替换参数
				}
			}
			
			/*
			 * html页面 追加上下文参数contextPath userId unitId等信息 (微课对接手机端专用)
			 */
			if(servletPath.contains(".html")){
				String contextPath = httpRequest.getContextPath();
				if(requestUrl.indexOf("contextPath") == -1){
					requestUrl += "&contextPath="+contextPath;
				}else{
					requestUrl = replaceAccessTokenReg(requestUrl, "contextPath", contextPath);//替换参数
				}
				
				
				
				/*
				 * 包含syncUserId  则转化成html页面所需要的unitId userId通用字段信息
				 * 一般只有直接暴露给微课的url地址 才会有syncUserId字段信息
				 */
				String syncUserId = httpRequest.getParameter("syncUserId");
				if(StringUtils.isNotBlank(syncUserId)){
					User user = userService.getUser(syncUserId);
					if(user!=null){
						String userId = user.getId();
						String unitId = user.getUnitid();
						String userName = URLEncoder.encode(user.getRealname(), "utf-8");
						
						int weike = 2;//新版微课标识
						requestUrl += "&userId="+userId + "&unitId="+unitId + "&userName="+userName+"&weike="+weike;
						
						//预校验
						JSONObject json = officeSubsystemService.validateH5(unitId, userId, httpRequest);
						if(json!=null){
							if(json.containsKey("type")){
								int type = json.getInt("type");
								String errorMsg = json.containsKey("msg")?json.getString("msg"):"";
								if(-1 == type){//调整到提示页面
									requestUrl = errorHtmlUrl(httpRequest, errorMsg);
								}
							}
						}
						
					}else{
						requestUrl = errorHtmlUrl(httpRequest, "用户信息不存在或非教师登录!");
					}
				}
			}
			
			httpResponse.sendRedirect(requestUrl);
			return;
		}
		filterChain.doFilter(request, response);
	}

	private String errorHtmlUrl(HttpServletRequest httpRequest, String errorMsg)
			throws UnsupportedEncodingException {
		String requestUrl;
		String basePath = httpRequest.getScheme() + "://" + httpRequest.getServerName()
				+ ":" + httpRequest.getServerPort() + httpRequest.getContextPath();
		requestUrl = basePath + errorHtmlSrc + "?errorMsg="+URLEncoder.encode(errorMsg, "utf-8");
		return requestUrl;
	}	
	
	@Override
	public void destroy() {

	}
	
	/**
	 * 初始化map，取配置目录下的html、js、css文件
	 * @param rootPath
	 * @param file
	 */
	private void addVersionToMap(String rootPath, File file){
		if(file==null || !file.exists())
			return;
		if(file.isDirectory()){
			//遍历目录下所有js文件
			File[] files = file.listFiles();
			if(files==null)
				return;
			for(File f : files){
				if(f.isDirectory()){
					addVersionToMap(rootPath, f);
				}else{
					String path = f.getAbsolutePath().replace(rootPath, "");
					if(StringUtils.isNotBlank(path)){//统一转换为网络路径
						path = path.replaceAll(Matcher.quoteReplacement(File.separator), "/");		
						if(!path.startsWith("/")){
							path = "/" + path;
						}
					}
					
					//文件摘要信息
					String md5digest = MD5Builder.getMD5(f);
					if(f.getName().endsWith(".html")){
						htmlMap.put(path, md5digest);
					}else if(f.getName().endsWith(".js")){
						jsMap.put(path, md5digest);
					}else if(f.getName().endsWith(".css")){
						cssMap.put(path, md5digest);
					}
				}
			}
		}
	}
	
	private void addVersionToMapStatic(String rootPath, String filterPath) {
		String jarPath = rootPath + "/WEB-INF/lib/base-9.0.jar";
		String staticPath = filterPath.replace("/static", "/net/zdsoft/eis/template");
		try {
			JarFile jarFiles = new JarFile(jarPath);
			Enumeration<JarEntry> entries = jarFiles.entries();
			while(entries.hasMoreElements()){
				JarEntry ent = entries.nextElement();
				
				String pathname = ent.getName();
				if(!pathname.startsWith("/")){
					pathname = "/" + pathname;
				}
				if(pathname.contains(staticPath)){
					String md5degist = MD5Builder.getMD5(jarFiles.getInputStream(ent));
					String path = pathname.replace("/net/zdsoft/eis/template", "/static");
					if(pathname.endsWith(".html")){
						htmlMap.put(path, md5degist);
					}else if(pathname.endsWith(".js")){
						jsMap.put(path, md5degist);
					}else if(pathname.endsWith(".css")){
						cssMap.put(path, md5degist);
					}
				}
			}
			
		} catch (IOException e) {//开发环境请忽略此问题
			System.out.println("ERROR "+e.getMessage());
		}
	}

	
	/**
	 * 替换参数的值
	 * @param url
	 * @param name
	 * @param accessToken
	 * @return
	 */
	private static String replaceAccessTokenReg(String url, String name,
			String accessToken) {
		if (StringUtils.isNotBlank(url) && StringUtils.isNotBlank(accessToken)) {
			url = url.replaceAll("(" + name + "=[^&]*)", name + "="
					+ accessToken);
		}
		return url;
	}
}
