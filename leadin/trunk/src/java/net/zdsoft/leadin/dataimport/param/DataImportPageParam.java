/* 
 * @(#)DataImportViewParam.java    Created on Aug 4, 2010
 * Copyright (c) 2010 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.leadin.dataimport.param;

import net.zdsoft.leadin.util.Assert;

/**
 * 数据导入页面参数
 * 
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: Aug 4, 2010 3:16:59 PM $
 */
public class DataImportPageParam {
    private String namespace;
    private String mainActionName;// 主页面的action名称

    private boolean displayCovered = true;// 是否显示覆盖复选框
    private boolean hasTemplate = true;// 是否需要模板

    private boolean hasTask = false;// 是否走任务形式
    private String userDefinedUrl;//在导入记录里要跳转的url
    

    public DataImportPageParam(String namespace, String mainActionName) {
        Assert.notEmpty(namespace, "namespace must not be null and empty string");
        Assert.notEmpty(mainActionName, "mainActionName must not be null and empty string");

        this.namespace = namespace;
        this.mainActionName = mainActionName;
    }
    
    public DataImportPageParam setDisplayCovered(boolean displayCovered) {
        this.displayCovered = displayCovered;
        return this;
    }

    public DataImportPageParam setHasTemplate(boolean hasTemplate) {
        this.hasTemplate = hasTemplate;
        return this;
    }

    public DataImportPageParam setHasTask(boolean hasTask) {
        this.hasTask = hasTask;
        return this;
    }
    
    public String getNamespace() {
        return namespace;
    }

    public String getMainActionName() {
        return mainActionName;
    }

    public boolean isDisplayCovered() {
        return displayCovered;
    }

    public boolean isHasTemplate() {
        return hasTemplate;
    }

    public boolean isHasTask() {
        return hasTask;
    }

	/**
	 * 获取userDefinedUrl
	 * @return userDefinedUrl
	 */
	public String getUserDefinedUrl() {
	    return userDefinedUrl;
	}

	/**
	 * 设置userDefinedUrl
	 * @param userDefinedUrl userDefinedUrl
	 */
	public void setUserDefinedUrl(String userDefinedUrl) {
	    this.userDefinedUrl = userDefinedUrl;
	}
}
