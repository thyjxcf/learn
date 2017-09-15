/*
 * @(#)SpecialDataSource    Created on 2005-1-17
 * Copyright (c) 2005 ZDSoft Networks, Inc. All rights reserved.
 * $Header: /project/keel/src/net/zdsoft/keel/dao/SpecialDataSource.java,v 1.7 2007/01/11 09:15:14 liangxiao Exp $
 */
package net.zdsoft.keel.dao;

import net.zdsoft.keel.util.SecurityUtils;
import net.zdsoft.keel.util.URLUtils;

import org.apache.commons.dbcp.BasicDataSource;

/**
 * 对连接参数中的用户名、密码进行自身混淆加密的数据源，建议在项目中实现，那样加密方式和程度可以控制
 * 
 * @deprecated
 * @author liangxiao
 * @version $Revision: 1.7 $, $Date: 2007/01/11 09:15:14 $
 */
public class SpecialDataSource extends BasicDataSource {

    /**
     * 构造方法
     */
    public SpecialDataSource() {
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.apache.commons.dbcp.BasicDataSource#setUrl(java.lang.String)
     */
    public void setUrl(String url) {
        super.setUrl(URLUtils.decode(url, "8859_1"));
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.apache.commons.dbcp.BasicDataSource#setUsername(java.lang.String)
     */
    public void setUsername(String username) {
        super.setUsername(SecurityUtils.decodeBySelf(username));
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.apache.commons.dbcp.BasicDataSource#setPassword(java.lang.String)
     */
    public void setPassword(String password) {
        super.setPassword(SecurityUtils.decodeBySelf(password));
    }

}
