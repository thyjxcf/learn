/* 
 * @(#)TestServiceImpl.java    Created on Mar 10, 2011
 * Copyright (c) 2011 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.eis.base.example.quartz;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: Mar 10, 2011 2:20:20 PM $
 */
public class TestServiceImpl implements TestService {

    /**
     * Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = -5576808979764415696L;

    private static final Logger logger = LoggerFactory.getLogger(TestServiceImpl.class);

    public void testMethod1() {
        // 这里执行定时调度业务
        logger.error("testMethod1.......1");
        System.out.println("testMethod1.......1");
    }

    public void testMethod2() {
        logger.error("testMethod2.......2");
        System.out.println("testMethod2.......2");
    }

}
