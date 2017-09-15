/* 
 * @(#)AbstractTask.java    Created on 2007-12-27
 * Copyright (c) 2007 ZDSoft Networks, Inc. All rights reserved.
 * $Id: AbstractTask.java,v 1.1 2008/07/18 09:54:35 huangwj Exp $
 */
package net.zdsoft.keel.util.concurrent;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 描述某个系统任务的抽象基类.
 * 
 * @author huangwj
 * @version $Revision: 1.1 $, $Date: 2008/07/18 09:54:35 $
 */
public abstract class AbstractTask {

    protected Logger log = LoggerFactory.getLogger(getClass());

    private String name;
    private Date timestamp;

    protected volatile boolean isWorking = true;

    public AbstractTask(String name) {
        this.name = name;
        this.timestamp = new Date();
    }

    public String getName() {
        return name;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setWorking(boolean isWorking) {
        this.isWorking = isWorking;
    }

    public boolean isWorking() {
        return isWorking;
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append(getClass().getSimpleName()).append("[");
        sb.append("name=").append(name).append(",");
        sb.append("timestamp=").append(timestamp).append("]");
        return sb.toString();
    }

}
