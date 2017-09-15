/* 
 * @(#)I18NP1Action.java    Created on Aug 18, 2010
 * Copyright (c) 2010 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.eis.base.example.i18n.p1;

import com.opensymphony.xwork2.util.LocalizedTextUtil;

import net.zdsoft.eis.base.example.i18n.I18NAction;

/**
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: Aug 18, 2010 1:25:34 PM $
 */
public class I18NP1Action extends I18NAction {
    @Override
    public String execute() throws Exception {
        LocalizedTextUtil.reset();
        return SUCCESS;
    }
}
