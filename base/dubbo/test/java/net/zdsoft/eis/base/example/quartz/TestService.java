/* 
 * @(#)TestService.java    Created on Mar 10, 2011
 * Copyright (c) 2011 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.eis.base.example.quartz;

import java.io.Serializable;

/**
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: Mar 10, 2011 2:19:10 PM $
 */
public interface TestService extends Serializable {
    public void testMethod1();

    public void testMethod2();
}
