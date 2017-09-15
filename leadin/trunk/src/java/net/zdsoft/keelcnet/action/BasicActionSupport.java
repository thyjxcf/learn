package net.zdsoft.keelcnet.action;

import java.text.MessageFormat;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.struts2.config.DefaultSettings;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.apache.struts2.interceptor.SessionAware;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.opensymphony.util.TextUtils;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;

/**
 * Webwork的action基类，实现了SessionAware，ServletRequestAware，ServletResponseAware
 * Preparable，ActionTransactionAware接口，通过继承该类，可以获取session，request，response实例
 * 同时通过preparable接口可以在action初始化后，execute方法执行之前通过重载prepare方法来初始化其它变量
 * 
 * @author Brave Tao
 * @since 2004-5-15
 * @version $Id: BasicActionSupport.java,v 1.1 2006/12/20 11:09:15 liangxiao Exp $
 */
public class BasicActionSupport extends ActionSupport implements SessionAware,
        ServletRequestAware, ServletResponseAware, Preparable {
    /**
     * Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = -1987500607261618408L;
    
    public static final String CANCEL = "cancel";
    private static final String URL_STACK = "cnet.default.URLStack";
    protected transient final Logger log = LoggerFactory.getLogger(getClass());
    private String cancel;
    private boolean permissionCheck = false;

    /**
     * The HttpServletRequest object - since this is protected, action authors
     * can just refer to 'request' in their Actions. This variable is set by the
     * WebWork container.
     */
    protected HttpServletRequest request;

    /**
     * The HttpServletResponse object - since this is protected, action authors
     * can just refer to 'response' in their Actions. This variable is set by
     * the WebWork container.
     */
    protected HttpServletResponse response;

    /**
     * A Map object that wraps a HttpSession object. - since this is protected,
     * action authors can just refer to 'session' in their Actions. This
     * variable is set by the WebWork container.
     */
    protected Map<String, Object> session;

    // protected boolean isInTransaction = true;
    // protected boolean transactionReadOnly = false;

    public BasicActionSupport() {
    }

    /**
     * 通过Interceptor执行调用，能够在execute执行之前进行action的初始化工作
     * 
     * @see com.opensymphony.xwork.Preparable#prepare()
     */
    public void prepare() throws Exception {
    }

    // /**
    // * @param isInTransaction
    // * The isInTransaction to set.
    // */
    // protected void setInTransaction(boolean isInTransaction) {
    // this.isInTransaction = isInTransaction;
    // }
    //
    // /**
    // * 用于判断action是否需要事务处理
    // *
    // * @return
    // */
    // public boolean isInTransaction() {
    // return isInTransaction;
    // }
    //
    // protected void setTransactionReadOnly(boolean transactionReadOnly) {
    // this.transactionReadOnly = transactionReadOnly;
    // }
    //
    // /*
    // * (non-Javadoc)
    // *
    // * @see
    // net.zdsoft.cnet3.framework.actions.ActionTransactionAware#isReadOnly()
    // */
    // public boolean isTransactionReadOnly() {
    // return transactionReadOnly;
    // }

    public void setCancel(String cancel) {
        this.cancel = cancel;
    }

    public boolean isCanceled() {
        return TextUtils.stringSet(cancel);
    }

    public boolean isPermitted() {
        // if (!BootstrapUtils.getBootstrapManager().isBootstrapped()) {
        // return true;
        // }
        //
        // return true;
        return true;
    }

    public boolean isPermissionCheck() {
        return permissionCheck;
    }

    public void setPermissionCheck(boolean permissionCheck) {
        this.permissionCheck = permissionCheck;
    }

    public String isPermittedAsString() {
        return isPermitted() ? "true" : "false";
    }

    public String doDefault() throws Exception {
        return INPUT;
    }

    public String getText(String key, Object[] args) {
        String result = getText(key);

        if (result == null) {
            return result;
        }
        else {
            return MessageFormat.format(result, args);
        }
    }

    /**
     * 直接调用，在action中声明会出现null HttpRequest的错误
     * 
     * @return
     */
    public final Map<String, Object> getSession() {
        return this.session;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.opensymphony.webwork.interceptor.SessionAware#setSession(java.util.Map)
     */
    public void setSession(Map<String, Object> session) {
        this.session = session;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.opensymphony.webwork.interceptor.ServletRequestAware#setServletRequest(javax.servlet.http.HttpServletRequest)
     */
    public void setServletRequest(HttpServletRequest request) {
        this.request = request;
    }

    /**
     * Returns a reference to the HttpServletRequest object.
     * 
     * @see javax.servlet.http.HttpServletRequest
     * @return a reference to the HttpServletReqeust object.
     */
    public HttpServletRequest getServletRequest() {
        return request;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.opensymphony.webwork.interceptor.ServletResponseAware#setServletResponse(javax.servlet.http.HttpServletResponse)
     */
    public void setServletResponse(HttpServletResponse response) {
        this.response = response;
    }

    /**
     * Returns a reference to the HttpServletResponse object.
     * 
     * @see javax.servlet.http.HttpServletResponse
     * @return a reference to the HttpServletResponse object.
     */
    public HttpServletResponse getServletResponse() {
        return response;
    }

    public String getPreviousURL() {
        return getPreviousURL(request);
    }

    public void pushPreviousURL() {
        pushPreviousURL(request);
    }

    public String getPageURL() {
        StringBuffer page = new StringBuffer();
        page.append(this.getServletRequest().getRequestURI());

        String queryString = this.getServletRequest().getQueryString();

        if ((queryString != null) && !"".equals(queryString.trim())) {
            page.append('?').append(queryString);
        }

        return page.toString();
    }

    /**
     * Returns the URL of the last content page the user was on. A major page is
     * one of the content pages like the forum or thread page. The URL that is
     * returned will just be the name of the action, it's extension (.action
     * usually) and the query string (if any). Example:
     * <ul>
     * <code>
     *
     *      actionname.action?id=1
     *
     * </code>
     * </ul>
     * Note, the application's context is not part of the URL (ie, "/cnet/").
     * 
     * @return the URL of the last content page the user was on.
     */    
    public static String getPreviousURL(HttpServletRequest request) {
        HttpSession session = request.getSession();
        @SuppressWarnings("unchecked")
        List<String> urlStack = (List<String>) session.getAttribute(URL_STACK);
        String URL = null;

        if ((urlStack != null) && (urlStack.size() > 0)) {
            // return the last element (pop)
            URL = (String) urlStack.get(urlStack.size() - 1);
        }

        // Remove the stack from the session if it's size 0
        if ((urlStack != null) && (urlStack.size() == 0)) {
            session.removeAttribute(URL_STACK);
        }

        return URL;
    }

    /**
     * Updates the page the user is currently on - the value is stored in the
     * session.
     */
    public static void pushPreviousURL(HttpServletRequest request) {
        // Create the current URL and set it in the session:
        StringBuffer page = new StringBuffer();
        page.append(ActionContext.getContext().getName()).append('.');
        //page.append(Configuration.getString("webwork.action.extension"));
        
        @SuppressWarnings("deprecation")
        DefaultSettings a = new DefaultSettings();
        String actionExt = a.get("struts.action.extension");
        page.append(actionExt);
        

        String queryString = request.getQueryString();

        if ((queryString != null) && !"".equals(queryString.trim())) {
            page.append('?').append(queryString);
        }

        // See if there is already a stack in the session:
        HttpSession session = request.getSession();
        @SuppressWarnings("unchecked")
        List<String> urlStack = (List<String>) session.getAttribute(URL_STACK);

        if (urlStack == null) {
            // create a stack
            urlStack = new java.util.LinkedList<String>();
        }

        // get the current item on the top of the stack
        String top = null;

        if (urlStack.size() > 0) {
            top = (String) urlStack.get(urlStack.size() - 1);
        }

        // push the value on the stack but make sure the current value
        // isn't already there
        if (!page.toString().equals(top)) {
            urlStack.add(page.toString());

            // stack size check - if the size is greater than 2, remove from
            // bottom
            while (urlStack.size() > 2) {
                urlStack.remove(0);
            }

            session.setAttribute(URL_STACK, urlStack);
        }
    }

    public static int getNumPreviousURLs(HttpServletRequest request) {
        @SuppressWarnings("unchecked")
        List<String> urlStack = (List<String>) request.getSession().getAttribute(URL_STACK);

        if (urlStack != null) {
            return urlStack.size();
        }

        return 0;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
