package net.zdsoft.keelcnet.interceptor;

import net.zdsoft.keelcnet.action.BasicActionSupport;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.hibernate.HibernateException;
import org.springframework.dao.DataAccessException;
import org.springframework.transaction.TransactionException;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.config.ConfigurationException;
import com.opensymphony.xwork2.interceptor.Interceptor;

/**
 * Interceptor of xwork, add in xwork.xml
 * 
 * &lt;interceptor name="exception"
 * class="net.zdsoft.cnet3.framework.interceptor.ActionExceptionInterceptor"/>
 * 
 * @author Brave Tao
 * @since 2004-5-15
 * @version $Id: ActionExceptionInterceptor.java,v 1.1 2006/12/20 11:09:15 liangxiao Exp $
 */
public class ActionExceptionInterceptor implements Interceptor {
    /**
     * Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 5388185458512023065L;
    
    protected Logger log = LoggerFactory.getLogger(this.getClass());

    /*
     * (non-Javadoc)
     * 
     * @see com.opensymphony.xwork.interceptor.Interceptor#destroy()
     */
    public void destroy() {
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.opensymphony.xwork.interceptor.Interceptor#init()
     */
    public void init() {
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.opensymphony.xwork.interceptor.Interceptor#intercept(com.opensymphony.xwork.ActionInvocation)
     */
    public String intercept(ActionInvocation invocation) throws Exception {
        Action action = (Action) invocation.getAction();

        if (!(action instanceof BasicActionSupport)) {
            return invocation.invoke();
        }

        try {
            return invocation.invoke();
        }
        catch (Exception e) {
            if (!invocation.isExecuted()) {
                handleException(e, (BasicActionSupport) action);
            }

            return "error";
        }
    }

    public Exception handleException(Exception e, BasicActionSupport action) {
        if (e instanceof ConfigurationException) {
            log.error("webwork/xwork配置文件错误", e);
        }
        else if (e instanceof DataAccessException) {
            log.error("数据库操作错误", e);
        }
        else if (e instanceof TransactionException) {
            log.error("事务处理错误", e);
        }
        else if (e instanceof HibernateException) {
            log.error("Hibernate错误", e);
        }
        else {
            log.error("未知错误", e);
        }

        // new ExceptionAnalyzer().reportException(e, System.err);

        String error = "执行操作时发生错误！<br>URL请求：" + action.getPageURL()
                + "<br>错误信息：" + e.toString();
        action.addActionError(error);

        // CNetActionSupport curAction = (CNetActionSupport) action;
        // //curAction.addActionError("原始错误信息：" + e.getMessage());
        // //处理出错信息
        // e = ExceptionHandler.handleException(e);
        // curAction.addActionError("错误信息：" + e.getMessage());
        // curAction.addActionError(HttpUtil.createRequestInfo(
        // ServletActionContext.getRequest(), true));
        // curAction.addActionError(HttpUtil.createContextInfo(
        // ServletActionContext.getServletContext()));
        // curAction.addActionError(HttpUtil.createSessionInfo(
        // ServletActionContext.getRequest().getSession()));
        // curAction.addActionError(HttpUtil.createEnvironmentInfo());
        // curAction.addActionError(HttpUtil.createVersionInfo());
        // throw e;
        return e;
    }

}
