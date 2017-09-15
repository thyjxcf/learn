/* 
 * @(#)I18NTest.java    Created on Aug 18, 2010
 * Copyright (c) 2010 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.eis.base.example.i18n;

import java.util.Date;
import java.util.Locale;

import org.springframework.context.ApplicationContext;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.context.ContextLoader;

/**
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: Aug 18, 2010 3:15:11 PM $
 */
public class I18NTest {
    private ReloadableResourceBundleMessageSource ms;
    
    public void setMs(ReloadableResourceBundleMessageSource ms) {
        this.ms = ms;
    }
    
    private Date date;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
    
    public String getStr() {
         ms.clearCache();
        
        ApplicationContext context = ContextLoader.getCurrentWebApplicationContext();
        String s = context.getMessage("i18n.service", new Object[] { getDate() }, LocaleContextHolder.getLocale());
        System.out.println(s);
        return s;
    }

    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext(
                new String[] { "classpath:conf/spring/baseTestContext.xml" });
        I18NTest tc = (I18NTest) context.getBean("i18NTest");
        Object[] obj = new Object[] { tc.getDate() };
        String strChinaText = context.getMessage("i18n.service", obj, Locale.CHINA);
        System.out.println(strChinaText);

        String strEnglishText = context.getMessage("i18n.service",obj,Locale.US);
        System.out.println(strEnglishText);

    }

 

}
