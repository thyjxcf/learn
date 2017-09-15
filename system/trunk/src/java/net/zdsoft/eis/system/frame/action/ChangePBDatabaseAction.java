/* 
 * @(#)ChangePBDatabase.java    Created on Jan 29, 2007
 * Copyright (c) 2006 ZDSoft Networks, Inc. All rights reserved.
 * $Header: f:/44cvsroot/stusys/stusys/src/net/zdsoft/stusys/system/pbdatabase/ChangePBDatabaseAction.java,v 1.1 2007/01/30 01:23:22 linqz Exp $
 */
package net.zdsoft.eis.system.frame.action;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

import net.zdsoft.eis.frame.action.BaseAction;
import net.zdsoft.keel.util.FileUtils;
import net.zdsoft.leadin.util.PWD;

public class ChangePBDatabaseAction extends BaseAction {

    /**
     * 
     */
    private static final long serialVersionUID = 5290885912147300972L;
    private static final String PROPERTIES = "/WEB-INF/classes/conf/database.properties";
    private static final String PB_PROPERTIES = "/app_x/dbparm/public.ssc";

    /**
     * 更改pb数据库链接密码
     * 
     * @return
     */
    public String changePBDBPWD() {
        Properties properties = new Properties();

        InputStream input = getServletContext().getResourceAsStream(PROPERTIES);
        try {
            properties.load(input);
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        String urlValueStr = properties.getProperty(
                "hibernate.connection.password").trim();
        PWD pwd = new PWD(urlValueStr);
        String realPath = getServletContext().getRealPath(PB_PROPERTIES);
        File file = new File(realPath);
        InputStreamReader read;
        try {
            read = new InputStreamReader(new FileInputStream(file));
            BufferedReader br = null;
            br = new BufferedReader(read);
            String line = null;
            StringBuffer sb = new StringBuffer();
            while ((line = br.readLine()) != null) {
                if (line.trim().indexOf("logpass=") == 0) {
                    line = "logpass=" + pwd.encode();
                }
                sb.append(line).append("\r\n");
            }            
            FileUtils.writeString(realPath, sb.toString(), false);
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return SUCCESS;
    }
}
