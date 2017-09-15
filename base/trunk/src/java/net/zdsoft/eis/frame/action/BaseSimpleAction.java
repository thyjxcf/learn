/* 
 * @(#)BaseSimpleAction.java    Created on Jun 16, 2011
 * Copyright (c) 2011 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.eis.frame.action;

import net.zdsoft.eis.base.common.entity.SystemVersion;
import net.zdsoft.eis.base.common.service.BasicClassService;
import net.zdsoft.eis.base.simple.entity.SimpleClass;
import net.zdsoft.eis.base.simple.service.AbstractClassService;
import net.zdsoft.eis.base.simple.service.SimpleClassService;
import net.zdsoft.eis.base.simple.service.SimpleStudentService;

/**
 * 提供基础的共用service的action
 * 
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: Jun 16, 2011 3:49:54 PM $
 */
public class BaseSimpleAction extends PageSemesterAction {

    /**
     * Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 894207313784399067L;

    private SimpleClassService simpleClassService;
    private BasicClassService basicClassService;
    private SimpleStudentService simpleStudentService;

    public void setSimpleClassService(SimpleClassService simpleClassService) {
        this.simpleClassService = simpleClassService;
    }

    public void setBasicClassService(BasicClassService basicClassService) {
        this.basicClassService = basicClassService;
    }

    public void setSimpleStudentService(SimpleStudentService simpleStudentService) {
        this.simpleStudentService = simpleStudentService;
    }

    protected AbstractClassService<? extends SimpleClass> getClassService() {
        if (SystemVersion.PRODUCT_EIS.equals(systemDeployService.getProductId())) {
            return basicClassService;
        } else {
            return simpleClassService;
        }
    }

    public SimpleStudentService getStudentService() {
        return simpleStudentService;
    }

}
