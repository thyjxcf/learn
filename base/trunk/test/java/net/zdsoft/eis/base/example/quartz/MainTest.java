/* 
 * @(#)MainTest.java    Created on Mar 10, 2011
 * Copyright (c) 2011 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.eis.base.example.quartz;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MainTest {

    /**
     * @param args
     */
    public static void main(String[] args) {
        ApplicationContext springContext = new ClassPathXmlApplicationContext(new String[] {
                "classpath:conf/spring/applicationContext.xml", "classpath:conf/spring/scheduler/baseScheduleTestContext.xml" });
        
//        System.out.println(springContext.getBean("testService"));
//        TestService testService = (TestService) springContext.getBean("testService");
//        testService.testMethod2();
        
//        springContext.getBean("simpleService");
//        SimpleService simpleService = (SimpleService) springContext.getBean("simpleService");
//        simpleService.testMethod2();
    }

}
