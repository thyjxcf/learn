package net.zdsoft.eis.system.frame.web;

import java.io.OutputStreamWriter;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.buffalo.protocal.BuffaloProtocal;
import net.zdsoft.eis.base.common.entity.Module;
import net.zdsoft.eis.base.common.service.ModuleService;
import net.zdsoft.eis.base.common.service.SystemIniService;
import net.zdsoft.eis.base.constant.BaseConstant;
import net.zdsoft.eis.frame.action.BaseAction;
import net.zdsoft.eis.frame.client.LoginInfo;
import net.zdsoft.eis.frame.util.RedisUtils;
import net.zdsoft.keel.action.Reply;
import net.zdsoft.keelcnet.config.BootstrapManager;
import net.zdsoft.leadin.util.RequestUtils;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.views.freemarker.FreemarkerResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;

/*
 * 用于权限判断的拦截器
 * @author Brave Tao
 * @since 2004-10-10
 * @version $Id: PermissionCheckInterceptor.java,v 1.9 2007/02/01 09:41:00 linqz Exp $
 * @since
 */
public class PermissionCheckInterceptor implements Interceptor {
    /**
     * Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 5020012163916186280L;

    public static final String NOT_PERMITTED = "notpermitted";

    protected Logger log = LoggerFactory.getLogger(this.getClass());

    private static boolean noRuleSkip = false;// 不符合url规则时是否跳过
    private String skipNameSpaces = null;// 跳过的namespace
    private String[] skipNameSpaceArr = null;

    @Resource
    private ModuleService moduleService;
//    @Resource
//    private SubSystemService subSystemService;
    @Resource
    private SystemIniService systemIniService;
//    @Resource
//    private ServerService serverService;

    /*
     * (non-Javadoc)
     * 
     * @see com.opensymphony.xwork2.interceptor.Interceptor#destroy()
     */
    public void destroy() {
        skipNameSpaceArr = null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.opensymphony.xwork2.interceptor.Interceptor#init()
     */
    public void init() {
        if (null != skipNameSpaces)
            skipNameSpaceArr = skipNameSpaces.split(",");
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.opensymphony.xwork2.interceptor.Interceptor#intercept(com.opensymphony
     *      .xwork.ActionInvocation)
     */
    @SuppressWarnings("rawtypes")
    public String intercept(ActionInvocation invocation) throws Exception {
        //-------------------------->>>>>>>>>>>>>>>
        //增加性能检测及后台action跟踪代码 2015-11-22 by linqz
        Long time = 0l;
        String mark = "";
        String paramStr = "";
        String actionMethod = "";
        String ip = "";
        String costTime = systemIniService.getValue("EIS.INTERCEPT.RECORD.MIN.COST.TIME");
        double costTimed = 0.5;
        if(StringUtils.isBlank(costTime)) {
            costTimed = NumberUtils.toDouble(costTime);
        }
        if(costTimed <= 0)
            costTimed = 0.5;
        
        HttpServletRequest req = ServletActionContext.getRequest();
        String today = net.zdsoft.keel.util.DateUtils.date2String(new Date(), "yyyy-MM-dd");
        String actionClassName = invocation.getAction().getClass().getName();
        actionMethod = actionClassName + "!" + invocation.getProxy().getMethod();
        if(BootstrapManager.isDevModel()) {
            time = System.currentTimeMillis();
            mark = net.zdsoft.keel.util.DateUtils.date2String(new Date(time), "HH:mm:ss.SSS");
            ip = RequestUtils.getRealRemoteAddr(req);
            Enumeration e = req.getParameterNames();
            StringBuffer param = new StringBuffer();
            while(e.hasMoreElements()) {
                String key = e.nextElement().toString();
                if(StringUtils.equals("_", key))
                    continue;
                String value = req.getParameter(key);
                if(param.length() > 0) {
                    param.append("&");
                }
                param.append(key).append("=").append(value);
            }
            paramStr = param.toString();
        }
        //--------------------------<<<<<<<<<<<<<<<<<<<<
        LoginInfo loginInfo = null;
        Map<String, Object> session = ActionContext.getContext().getSession();
        if (session.get(BaseConstant.SESSION_LOGININFO) != null) {
            loginInfo = (LoginInfo) session.get(BaseConstant.SESSION_LOGININFO);
        }
        
        if(req.getRequestURI().contains("/desktop/unify")){
        	return invocation.invoke();
        }
        
        String requestUrl = req.getServletPath();
        if (requestUrl.startsWith("/")) {
            requestUrl = requestUrl.substring(1);
        }
        Module module = null;
        if(loginInfo != null)
            module = filterModule(req, loginInfo, loginInfo.getUnitClass(), requestUrl);
            
        if (session.get(BaseConstant.SESSION_BACKGROUND_LOGININFO) != null) {
            String result = invocation.invoke(); 
            doRecord(time, mark, paramStr, actionMethod, ip, today, costTimed, module, req, invocation);
            return result;
        }
        boolean hasRight = false;
        int moduleId = 0;
        {
            // 跳過的url,如框架的url，或 公共功能（全局的通用功能或某子系统某功能块的通用功能）
            String url = req.getServletPath();
            if (null != skipNameSpaceArr) {
                for (String ns : skipNameSpaceArr) {
                    if (url.contains(ns)) {
                        hasRight = true;
                        break;
                    }
                }
            }

            // 模块url
            if (!hasRight) {
                if (module != null) {
                    // 模块的主url
                    if (loginInfo.validateAllModel(module.getId().intValue())) {
                        moduleId = module.getId().intValue();
                        hasRight = true;
//                        // 记录模块日志 
                        //去掉，数据量超大，但是没有什么用。 2015-12-22 by linqz
//                        writeModuleLog(req, tempInfo, m);
                    }
                } else {
                    String actionName = invocation.getProxy().getActionName() + ".action";
                    // 模块中存在重定向的链接，导致requestUrl的后缀与实际的actionName不一致的情况
                    if (!requestUrl.endsWith(actionName)) {
                        requestUrl = requestUrl.substring(0, requestUrl.lastIndexOf("/") + 1)
                                + actionName;
                    }
                    int pos = actionName.lastIndexOf("-");
                    String mainUrl = null;
                    if (pos > -1) {
                        List<Module> modules = null;
                        // 从右至左依次解析，直到找到主模块链接
                        while (pos > -1) {
                            // 符合部分规则（即带“-”）的模块子url
                            mainUrl = requestUrl.substring(0, requestUrl.length()
                                    - actionName.length() + pos)
                                    + ".action";
                            modules = moduleService.getModulesByUrl(loginInfo.getUnitClass(),
                                    mainUrl);
                            if (null == modules || modules.size() == 0) {
                                pos = actionName.substring(0, pos).lastIndexOf("-");
                            } else {
                                break;
                            }
                        }
                        if (null == modules || modules.size() == 0) {
                            hasRight = false;
                        } else {
                            // 任何一个模块有权限即有权限
                            for (Module m : modules) {
                                if (loginInfo.validateAllModel(m.getId().intValue())) {
                                    // mId = module.getMid();
                                    moduleId = m.getId().intValue();
                                    hasRight = true;
                                }
                            }
                        }
                    } else {
                        // 不符合规则的模块的子url
                        if (noRuleSkip) {
                            hasRight = true;
                        }
                    }
                }

                // 功能点权限验证
                if (hasRight) {
                    Object action = invocation.getAction();
                    if (action instanceof BaseAction) {
                        BaseAction baseAction = (BaseAction) action;
                        String oper = baseAction.getModuleOperation();
                        if (moduleId > 0 && StringUtils.isNotEmpty(oper)) {
                            if (loginInfo.validateAllModelOpera(moduleId, oper)) {
                                hasRight = true;
                            } else {
                                hasRight = false;
                            }
                        }
                    }
                }

            }
        }

        if (!hasRight) {
            // buffalo没权限时的提示
            if (req.getHeader("X-Buffalo-Version") != null) {
                HttpServletResponse res = ServletActionContext.getResponse();
                OutputStreamWriter writer = new OutputStreamWriter(res.getOutputStream(), "UTF-8");
                Reply reply = new Reply();
                // reply.setScript("alert(\"对不起！您没有访问该buffalo的权限！\");");
                reply.addActionError("对不起！您没有访问该buffalo的权限！");
                BuffaloProtocal.getInstance().marshall(reply, writer);
                doRecord(time, mark, paramStr, actionMethod, ip, today, costTimed, module, req, invocation);
                return "none";
            }
            doRecord(time, mark, paramStr, actionMethod, ip, today, costTimed, module, req, invocation);
            return BaseAction.NOPERMISSION;
        }

        String result = invocation.invoke(); 
        doRecord(time, mark, paramStr, actionMethod, ip, today, costTimed, module, req, invocation);
        return result;
    }

    private void doRecord(Long time, String mark, String paramStr, String actionMethod,
            String ip, String today, double costTimed, Module module, HttpServletRequest req, ActionInvocation invocation) {
        double d = (System.currentTimeMillis() - time)/1000.0;
        String moduleName = module == null ? "" : "(" + module.getName() + ")";
        String uri = req.getRequestURI();
        String url = req.getRequestURL().toString();
        if(BootstrapManager.isDevModel()) {
            System.out.println(mark + "-地址." + moduleName + uri + (StringUtils.isBlank(paramStr) ? "" : "?" + paramStr) + " (" + ip + ")");
            System.out.println(mark + "-接口." + actionMethod);
            try {
                FreemarkerResult result = (FreemarkerResult) invocation.getResult();
                String location = result.getLocation();
                if(StringUtils.startsWith(location, "/")) {
                    System.out.println(mark + "-页面." + invocation.getResultCode() + ", " + result.getLocation());
                }
                else {
                    System.out.println(mark + "-页面." + invocation.getResultCode() + ", " + invocation.getProxy().getNamespace() + "/" + result.getLocation());
                }
            }
            catch (Exception e) {
            }
            System.out.println(mark + "-耗时." + (d >= costTimed ? "【" + d + "s】" : d + "s"));
            System.out.println("------->>>");
        }
        //记录这个地址的访问次数，按照每一天来记录
        String countUrl = "EIS.INTERCEPT.URL.COUNT@" + today + "@" + url + "@" + (module == null ? "" : module.getName());
        RedisUtils.incrby(countUrl, 1);
        //30天后，如果数据没有被传递走，缓存自动清除
        RedisUtils.expire(countUrl, 2592000);
    }
    /**
     * 过滤模块
     * 
     * @param req
     * @param tempInfo
     * @param unitClass
     * @param url
     * @return
     */
    private Module filterModule(HttpServletRequest req, LoginInfo tempInfo, int unitClass,
            String url) {
        Module m = null;
        List<Module> modules = moduleService.getModulesByUrl(unitClass, url);
        if (modules.size() == 1) {
            m = modules.get(0);// 只有一个模块
        } else {
            // 注：如果搜索出来的模块，有些带参数，有些不带参数（此时程序中可能有默认值），则随机匹配。
            // 如果在这些模块中找到有权限，则返回有权限的模块，否则返回没权限的模块
            for (Module module : modules) {
                String[] params = null;// 参数
                String fullUrl = module.getUrl();// 可能带参数的完整的url
                String[] parts = StringUtils.split(fullUrl, '?');
                if (parts.length == 2) {
                    String paramStr = parts[1];
                    params = StringUtils.split(paramStr, '&');
                }

                if (null != params) {
                    // 匹配参数
                    boolean match = true;
                    for (String param : params) {
                        String[] nameValue = StringUtils.split(param, '=');
                        if (null != nameValue && nameValue.length == 2) {
                            String name = nameValue[0];
                            String value = nameValue[1];
                            if (!(value.equals(req.getParameter(name)))) {
                                match = false;
                                break;
                            }
                        }
                    }
                    if (match) {
                        m = module;
                        if (tempInfo.validateAllModel(module.getId().intValue())) {
                            break;
                        }
                    }
                } else {
                    m = module;
                    if (tempInfo.validateAllModel(module.getId().intValue())) {
                        break;
                    }
                }
            }
        }
        return m;
    }

    public static void setNoRuleSkip(boolean noRuleSkip) {
        PermissionCheckInterceptor.noRuleSkip = noRuleSkip;
    }

    public void setSkipNameSpaces(String skipNameSpaces) {
        this.skipNameSpaces = skipNameSpaces;
    }

    public static boolean isNoRuleSkip() {
        return noRuleSkip;
    }

//    private ModuleLogService moduleLogService;

    /**
     * 记录模块日志
     * 
     * @param req
     * @param info
     * @param module
     */
//    private void writeModuleLog(HttpServletRequest req, LoginInfo info, Module module) {
//        try {
//            SubSystem subSystem = subSystemService.getSubSystem(module.getSubsystem());
//            Server app = serverService.getServerByServerCode(subSystem.getCode());
//            int serverId = 0;
//            int serverTypeId = 0;
//            if(null != app){
//                serverId = Integer.parseInt(app.getId());
//                serverTypeId = Long.valueOf(app.getServerTypeId()).intValue();
//            }
//            User user = info.getUser();
//            Date clickDate = new Date();
//            ModuleLog log = new ModuleLog();
//            log.setAccountId(user.getAccountId());
//            log.setCreationTime(clickDate);
//            log.setModuleId(module.getModelId());
//            log.setRegionCode(user.getRegion());
//            log.setServerId(serverId);
//            log.setServerTypeId(serverTypeId);
//            log.setSubsystemId(module.getSubsystem());
//            log.setUserType(user.getType());
//            log.setUnitId(info.getUnitID());
//            moduleLogService.insertModuleLog(log);
//        } catch (Exception e) {
//            log.error("记录日志出错：" + e.getMessage());
//        }
//    }

    public static void main(String[] args) {
        String a = "aaa/bbb/cccd/a.xml";
        System.out.println(a.lastIndexOf("/"));
        System.out.println(a.substring(0, a.lastIndexOf("/") + 1));
    }

}
