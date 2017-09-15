/* 
 * @(#)CustomHttpState.java    Created on 2008-7-23
 * Copyright (c) 2008 ZDSoft Networks, Inc. All rights reserved.
 * $Id: CustomHttpState.java,v 1.1 2008/07/23 02:46:00 huangwj Exp $
 */
package net.zdsoft.keel.integration.spring.httpclient;

import org.apache.commons.httpclient.Credentials;
import org.apache.commons.httpclient.HttpState;
import org.apache.commons.httpclient.auth.AuthScope;
import org.springframework.beans.factory.InitializingBean;

/**
 * 此类基于Jakarta Commons HttpClient中的{@link HttpState}类，为了能够在Spring Context中定义而创建.
 * 
 * @author huangwj
 * @version $Revision: 1.1 $, $Date: 2008/07/23 02:46:00 $
 */
public class CustomHttpState extends HttpState implements InitializingBean {

    private AuthScope authScope;
    private Credentials credentials;

    public void setAuthScope(AuthScope authScope) {
        this.authScope = authScope;
    }

    public void setCredentials(Credentials credentials) {
        this.credentials = credentials;
    }

    public void afterPropertiesSet() throws Exception {
        authScope = authScope == null ? AuthScope.ANY : authScope;
        setCredentials(authScope, credentials);
    }

}
