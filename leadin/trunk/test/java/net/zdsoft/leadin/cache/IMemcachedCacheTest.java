/* 
 * @(#)IMemcachedCacheTest.java    Created on Nov 30, 2010
 * Copyright (c) 2010 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.leadin.cache;

import org.springframework.beans.factory.annotation.Autowired;

import com.alisoft.xplatform.asf.cache.IMemcachedCache;

import net.zdsoft.leadin.LeadinTestCase;

/**
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: Nov 30, 2010 4:58:02 PM $
 */
public class IMemcachedCacheTest extends LeadinTestCase {

    private static final String key = "eis1.incr6";

    @Autowired
    private IMemcachedCache memCache;

    public void testAddOrIncr() {
        // memCache.remove(key);
        // memCache.put(key, "0");

        Runnable b = new Runnable() {
            @Override
            public void run() {
                long value = -1;
                try{
                    // long value = memCache.addOrIncr(key, 1);
                    value = memCache.incr(key, 1);
                    while (value == -1) {
                        boolean sign = memCache.add(key, 0);
                        // if (sign) {
                        // value = (Integer)memCache.get(key);
                        // System.out.println("!!!!!!!!!!!!!!777777="+value);
                        // value = memCache.incr(key, 1);
                        // System.out.println("!!!!!!!!!!!!!!!!!!!!="+value);
                        // } else {
                        // value = memCache.incr(key, 1);
                        // }
                        try {
                            Thread.sleep(10);
                        } catch (InterruptedException e) {
                           // e.printStackTrace();
                        }
                        value = memCache.incr(key, 1);
                    }                    
                }catch (Exception e) {
                    System.out.println(e);
                }
                System.out.println(Thread.currentThread().getName() + "====" + value);
            }
        };

        for (int i = 0; i < 200; i++) {
            new Thread(b, "t1" + (i + 1)).start();
        }
    }
}
