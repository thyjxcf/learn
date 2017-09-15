/* 
 * @(#)StrutsFilter.java    Created on Jul 7, 2010
 * Copyright (c) 2010 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.leadin.struts;

import javax.servlet.FilterConfig;
import javax.servlet.ServletException;

import org.apache.struts2.dispatcher.Dispatcher;
import org.apache.struts2.dispatcher.ng.ExecuteOperations;
import org.apache.struts2.dispatcher.ng.InitOperations;
import org.apache.struts2.dispatcher.ng.filter.FilterHostConfig;
import org.apache.struts2.dispatcher.ng.filter.StrutsPrepareAndExecuteFilter;

/**
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: Jul 7, 2010 9:23:49 PM $
 */
public class StrutsFilter extends StrutsPrepareAndExecuteFilter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        InitOperations init = new InitOperations();
        try {
            FilterHostConfig config = new FilterHostConfig(filterConfig);
            init.initLogging(config);
            Dispatcher dispatcher = init.initDispatcher(config);
            init.initStaticContentLoader(config, dispatcher);

            prepare = new StrutsPrepareOperations(filterConfig.getServletContext(), dispatcher,filterConfig);
//         execute = new ExecuteOperations(filterConfig.getServletContext(), dispatcher);
            execute = new ExecuteOperations(dispatcher);
            this.excludedPatterns = init.buildExcludedPatternsList(dispatcher);

            postInit(dispatcher, filterConfig);
        } finally {
            init.cleanup();
        }
    }

}
