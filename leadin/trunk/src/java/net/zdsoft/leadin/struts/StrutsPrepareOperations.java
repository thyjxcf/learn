/* 
 * @(#)StrutsPrepareOperations.java    Created on Jul 7, 2010
 * Copyright (c) 2010 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.leadin.struts;

import java.util.HashMap;

import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.dispatcher.Dispatcher;
import org.apache.struts2.dispatcher.StaticContentLoader;
import org.apache.struts2.dispatcher.ng.PrepareOperations;
import org.apache.struts2.dispatcher.ng.filter.FilterHostConfig;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.util.ValueStack;
import com.opensymphony.xwork2.util.ValueStackFactory;

/**
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: Jul 7, 2010 9:55:17 PM $
 */
public class StrutsPrepareOperations extends PrepareOperations {
    private ServletContext servletContext;
    private Dispatcher dispatcher;
    private FilterHostConfig config;

    public StrutsPrepareOperations(ServletContext servletContext, Dispatcher dispatcher,
            FilterConfig filterConfig) {
//        super(servletContext, dispatcher);
    	super(dispatcher);
        this.dispatcher = dispatcher;
        this.servletContext = servletContext;
        this.config = new FilterHostConfig(filterConfig);
    }

    // 重写doFilter，由于在struts配置文件重新加载后，静态资源有问题
    /**
     * Creates the action context and initializes the thread local
     */
    public ActionContext createActionContext(HttpServletRequest request,
            HttpServletResponse response) {
        ActionContext ctx;
        Integer counter = 1;
        Integer oldCounter = (Integer) request.getAttribute(CLEANUP_RECURSION_COUNTER);
        if (oldCounter != null) {
            counter = oldCounter + 1;
        }

        ActionContext oldContext = ActionContext.getContext();
        if (oldContext != null) {
            // detected existing context, so we are probably in a forward
            ctx = new ActionContext(new HashMap<String, Object>(oldContext.getContextMap()));
        } else {
            ValueStack stack = dispatcher.getContainer().getInstance(ValueStackFactory.class)
                    .createValueStack();
//            stack.getContext().putAll(dispatcher.createContextMap(request, response, null, servletContext));
            stack.getContext().putAll(dispatcher.createContextMap(request, response, null));
            ctx = new ActionContext(stack.getContext());

            // add by zhaosf
            StaticContentLoader staticResourceLoader = dispatcher.getContainer().getInstance(
                    StaticContentLoader.class);
            staticResourceLoader.setHostConfig(config);
        }
        request.setAttribute(CLEANUP_RECURSION_COUNTER, counter);
        ActionContext.setContext(ctx);
        return ctx;
    }

}
