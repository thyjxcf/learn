package net.zdsoft.leadin.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;

/**
 * 环绕的Interceptor基类
 * 
 * @author liangxiao
 * @version $Revision: 1.7 $, $Date: 2007/01/11 09:15:13 $
 */
public abstract class AroundInterceptor implements Interceptor {

    protected transient Logger log = LoggerFactory.getLogger(getClass());

    public void destroy() {
    }

    public void init() {
    }

    public String intercept(ActionInvocation invocation) throws Exception {
        String result = null;

        before(invocation);
        result = invocation.invoke();
        after(invocation, result);

        return result;
    }

    /**
     * Called after the invocation has been executed.
     *
     * @param result the result value returned by the invocation
     */
    protected abstract void after(ActionInvocation dispatcher, String result) throws Exception;

    /**
     * Called before the invocation has been executed.
     */
    protected abstract void before(ActionInvocation invocation) throws Exception;

}
