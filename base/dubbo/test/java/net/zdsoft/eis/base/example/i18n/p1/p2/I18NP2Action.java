/* 
 * @(#)I18NP2Action.java    Created on Aug 18, 2010
 * Copyright (c) 2010 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.eis.base.example.i18n.p1.p2;

import com.opensymphony.xwork2.util.LocalizedTextUtil;

import net.zdsoft.eis.base.example.i18n.I18NAware;
import net.zdsoft.eis.frame.action.BaseAction;

/**
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: Aug 18, 2010 1:25:55 PM $
 */
public class I18NP2Action extends BaseAction implements I18NAware {
    @Override
    public String execute() throws Exception {
        LocalizedTextUtil.reset();
        return SUCCESS;
    }
}
