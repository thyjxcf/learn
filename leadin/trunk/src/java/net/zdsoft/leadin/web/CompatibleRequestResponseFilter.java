/*
 * Created on 2004-1-7
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package net.zdsoft.leadin.web;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.zdsoft.leadin.util.FilterParamUtils;

/**
 * @author liangxiao
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class CompatibleRequestResponseFilter implements Filter {

	
	/**
	 * Take this filter out of service.
	 */
	public void destroy() {

	}

	/**
	 * Select and set (if specified) the character encoding to be used to
	 * interpret request parameters for this request.
	 *
	 * @param request The servlet request we are processing
	 * @param result The servlet response we are creating
	 * @param chain The filter chain we are processing
	 *
	 * @exception IOException if an input/output error occurs
	 * @exception ServletException if a servlet error occurs
	 */
	public void doFilter(
			ServletRequest request,
			ServletResponse response,
			FilterChain chain) throws IOException, ServletException {

		
		//将*.jsp;*.do的response设为不缓存    
		if (response instanceof HttpServletResponse) {
            HttpServletRequest req = (HttpServletRequest) request;
            HttpServletResponse res = (HttpServletResponse) response;
            String servletPath = req.getServletPath();
            ((HttpServletResponse)response).setHeader("X-UA-Compatible", "IE=5");
		}
       
		chain.doFilter(request, response);
	}

	/**
	 * Place this filter into service.
	 * @param filterConfig The filter configuration object
	 */
	public void init(FilterConfig filterConfig) throws ServletException {

	}

	
}
