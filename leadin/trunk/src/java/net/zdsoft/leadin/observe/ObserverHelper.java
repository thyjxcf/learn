/* 
 * @(#)ObserverHelper.java    Created on Sep 14, 2010
 * Copyright (c) 2010 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.leadin.observe;

import java.util.Collections;
import java.util.List;
import java.util.Observer;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: Sep 14, 2010 2:22:04 PM $
 */
public class ObserverHelper {
    private static ConcurrentMap<Integer, List<Observer>> observerMap = new ConcurrentHashMap<Integer, List<Observer>>();

    /**
     * 增加某key组中的观察者
     * 
     * @param key
     * @param o
     */
    public static void addObserver(int key, Observer o) {
        if (o == null)
            return;

        List<Observer> observers = observerMap.get(key);
        if (null == observers) {
            observers = new CopyOnWriteArrayList<Observer>();
        }
        if (!observers.contains(o)) {
            observers.add(o);
            observerMap.put(key, observers);
        }
    }

    /**
     * 获取某key组观察者
     * 
     * @param key
     * @return
     */
    public static List<Observer> getObservers(int key) {
        List<Observer> list = observerMap.get(key);
        if (null == list)
            list = Collections.emptyList();
        return list;
    }
}
