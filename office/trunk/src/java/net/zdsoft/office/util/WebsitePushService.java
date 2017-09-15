/* 
 * @(#)WebsitePushService.java    Created on 2009-8-11
 * Copyright (c) 2009 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.office.util;

/**
 * <pre>
 * 网站生成系统提供的接口,由于是.NET提供的代码,所以命名习惯上与JAVA有些不同。
 * 有可能再提供一个本地代理接口更合理些。
 * </pre>
 * 
 * @author zhuhf
 * @version $Revision: 1.0 $, $Date: 2009-8-11 下午06:29:47 $
 */
public interface WebsitePushService {

    /**
     * @return
     */
    String GetServiceList();

    /**
     * @param serviceCode
     * @param ticket
     * @return
     */
    int RegisterService(String serviceCode, String ticket);

    /**
     * @param regServiceCode
     * @return
     */
    String GetTicket(String regServiceCode);

    /**
     * @param serviceCode
     * @param ticket
     * @return
     */
    String GetServiceClassList(String serviceCode, String ticket);

    /**
     * @param serviceCode
     * @param ticket
     * @param title
     * @param body
     * @param annex
     * @param username
     * @param id
     * @param datetime
     * @param classId
     * @return
     */
    int PostArticle(String serviceCode, String ticket, String title,
            String body, String annex, String username, int id,
            String datetime, int classId);

    /**
     * @param ticket
     * @return
     */
    String RemoveTicket(String ticket);

    /**
     * @param serviceCode
     * @param ticket
     * @param title
     * @param body
     * @param annex
     * @param username
     * @param id
     * @param datetime
     * @param classId
     * @param userpwd
     * @param realname
     * @param sex
     * @param unitName
     * @param departmentName
     * @param isListTitle
     * @return
     */
    int PostArticleForOffice(String serviceCode, String ticket, String title,
            String body, String annex, String username, int id,
            String datetime, int classId, String userpwd, String realname,
            int sex, String unitName, String departmentName, int isListTitle);
    
 

}
