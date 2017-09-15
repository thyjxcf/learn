/*
* Project: v6
* Author : shenke
* @(#) SchsecurityAppInterceptor.java Created on 2016-11-29
* @Copyright (c) 2016 ZDSoft Inc. All rights reserved
*/
package net.zdsoft.eis.system.frame.web;

import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import net.zdsoft.eis.base.common.entity.Module;
import net.zdsoft.eis.base.common.service.ModuleService;
import net.zdsoft.eis.base.constant.BaseConstant;
import net.zdsoft.eis.frame.action.BaseAction;
import net.zdsoft.eis.frame.client.LoginInfo;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import com.google.common.collect.Lists;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;

/**
 * @description: 校安手机app拦截器，处理PC和手机端URL不同的权限问题
 * @author: shenke
 * @version: 1.0
 * @date: 2016-11-29下午3:31:54
 */
public class SchsecurityAppInterceptor implements Interceptor{

	/**
	 * 
	 */
	private static final long serialVersionUID = 892328473376279609L;

	@Resource
	private ModuleService moduleService;
	
	private String[] urls;
	
	private String models;
	
	@Override
	public void destroy() {
		urls = null;
		models = null;
	}

	@Override
	public void init() {
		urls = StringUtils.split(models,",");
	}

	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		
		Map<String, Object> session = ActionContext.getContext().getSession();
		LoginInfo info = (LoginInfo) session.get(BaseConstant.SESSION_LOGININFO);
		
		Set<Integer> modeIds = info.getAllModSet();
		
		List<Module> allModules = moduleService.getModules(set2String(modeIds), info.getUnitClass());
		if(CollectionUtils.isNotEmpty(allModules)){
			for(Module m : allModules){
				if(contain(m.getUrl())){
					return invocation.invoke();
				}
			}
		}
		
		return BaseAction.NOPERMISSION;
	}

	private boolean contain(String search){
		if(ArrayUtils.isEmpty(urls)) return false;
		boolean ok = false;
		for(String str : urls){
			if(StringUtils.contains(search, str)){
				ok = true;
				break;
			}
		}
		return ok;
	}
	
	private String[] set2String(Set<Integer> ids){
		if(CollectionUtils.isEmpty(ids)){
			return new String[0];
		}
		List<String> newIds = Lists.newArrayList();
		for(Integer i : ids){
			newIds.add(i.toString());
		}
		return newIds.toArray(new String[0]);
	}
	
	public void setModels(String models) {
		this.models = models;
	}

}
