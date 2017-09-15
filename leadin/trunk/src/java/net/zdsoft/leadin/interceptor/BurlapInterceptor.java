package net.zdsoft.leadin.interceptor;

/* 
 * <p>ZDSoft学籍系统（stusys）V3.5</p>
 * @author zhangza
 * @since 1.0
 * @version $Id: BurlapInterceptor.java,v 1.1 2007/01/09 10:07:57 jiangl Exp $
 */

import java.io.OutputStreamWriter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;

import net.buffalo.service.invoker.BuffaloInvoker;

public class BurlapInterceptor implements Interceptor {
    private static final long serialVersionUID = 4062975890557914022L;
    
    private static Logger logger = LoggerFactory.getLogger(BurlapInterceptor.class);
    private static final String OUTPUT_ENCODING = "utf-8";

    public void destroy() {
    }

    public void init() {
    }

    public String intercept(ActionInvocation invocation) throws Exception {
        // 因为Buffalo2.0的协议采用UTF-8编码, 所以必须要修改输出流的编码方式
        ServletActionContext.getResponse().setCharacterEncoding(OUTPUT_ENCODING);

        BuffaloInvoker invoker = BuffaloInvoker.getInstance();
        try {
            OutputStreamWriter out = new OutputStreamWriter(ServletActionContext.getResponse()
                    .getOutputStream(), OUTPUT_ENCODING);
            invoker.invoke(invocation.getAction(), ServletActionContext.getRequest()
                    .getInputStream(), out);
        } catch (Throwable e) {
            logger.error("Could not invoke burlap", e);
            throw new Exception("Could not invoke buffalo", e);
        }
        return null;
    }
}
