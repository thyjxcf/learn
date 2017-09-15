/* 
 * @(#)I18NAction.java    Created on Aug 18, 2010
 * Copyright (c) 2010 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.eis.base.example.i18n;

import com.opensymphony.xwork2.util.LocalizedTextUtil;

import net.zdsoft.eis.frame.action.BaseAction;

/**
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: Aug 18, 2010 11:35:41 AM $
 */
public class I18NAction extends BaseAction {
    private I18NTest i18NTest;

    public void setI18NTest(I18NTest test) {
        i18NTest = test;
    }

    @Override
    public String execute() throws Exception {        
        i18NTest.getStr();
        
        LocalizedTextUtil.reset();

        return SUCCESS;
    }
}
