package net.zdsoft.leadin.interceptor;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.opensymphony.xwork2.util.profiling.UtilTimerStack;

/**
 * In spring context config, interceptor
 * 
 * <pre>
 *  &lt;bean id=&quot;profilingInterceptor&quot; class=&quot;net.zdsoft.cnet3.framework.interceptor.SpringProfilingInterceptor&quot; /&gt;
 *  &lt;pre&gt;
 *  @author Brave Tao
 *  @since 2004-5-15
 *  @version $Id: SpringProfilingInterceptor.java,v 1.1 2007/01/09 10:07:57 jiangl Exp $
 * 
 */
public class SpringProfilingInterceptor implements MethodInterceptor {
    protected Logger log = LoggerFactory.getLogger(this.getClass());
    private boolean opened = false;

    /*
     * (non-Javadoc)
     * 
     * @see org.aopalliance.intercept.MethodInterceptor#invoke(org.aopalliance.intercept.MethodInvocation)
     */
    public Object invoke(MethodInvocation invocation) throws Throwable {
        if (opened == false) {
            return invocation.proceed();
        }

        if (log.isDebugEnabled()) {
            log.debug("Entering SpringProfilingInterceptor");
        }

        String name = "AOP: "
                + getClassName(invocation.getMethod().getDeclaringClass())
                + "." + invocation.getMethod().getName() + "()";
        UtilTimerStack.push(name);

        Object rval = invocation.proceed();
        UtilTimerStack.pop(name);

        if (log.isDebugEnabled()) {
            log.debug("Leaving SpringProfilingInterceptor");
        }

        return rval;
    }

    @SuppressWarnings("unchecked")
    public static String getClassName(Class clazz) {
        String name = clazz.getName();

        if (name.indexOf(".") >= 0) {
            name = name.substring(name.lastIndexOf(".") + 1);
        }

        return name;
    }

    /**
     * @param opened
     *            The opened to set.
     */
    public void setOpened(boolean opened) {
        this.opened = opened;
    }
}
