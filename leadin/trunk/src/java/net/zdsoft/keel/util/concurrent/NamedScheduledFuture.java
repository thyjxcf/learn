/* 
 * @(#)NamedScheduledFuture.java    Created on 2007-12-5
 * Copyright (c) 2007 ZDSoft Networks, Inc. All rights reserved.
 * $Id: NamedScheduledFuture.java,v 1.1 2008/07/18 09:54:35 huangwj Exp $
 */
package net.zdsoft.keel.util.concurrent;

import java.util.concurrent.Delayed;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.RunnableScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * 带有名字修饰的定时任务描述类.
 * 
 * @author huangwj
 * @version $Revision: 1.1 $, $Date: 2008/07/18 09:54:35 $
 * @param <V>
 */
public class NamedScheduledFuture<V> implements RunnableScheduledFuture<V> {

    private String name;
    private AbstractTask task;
    private RunnableScheduledFuture<V> future;

    public NamedScheduledFuture(AbstractTask task,
            RunnableScheduledFuture<V> future) {
        this.name = task.getName();
        this.task = task;
        this.future = future;
    }

    public String getName() {
        return name;
    }

    public AbstractTask getTask() {
        return task;
    }

    public boolean isPeriodic() {
        return future.isPeriodic();
    }

    public void run() {
        future.run();
    }

    public boolean cancel(boolean mayInterruptIfRunning) {
        return future.cancel(mayInterruptIfRunning);
    }

    public V get() throws InterruptedException, ExecutionException {
        return future.get();
    }

    public V get(long timeout, TimeUnit unit) throws InterruptedException,
            ExecutionException, TimeoutException {
        return future.get(timeout, unit);
    }

    public boolean isCancelled() {
        return future.isCancelled();
    }

    public boolean isDone() {
        return future.isDone();
    }

    public long getDelay(TimeUnit unit) {
        return future.getDelay(unit);
    }

    public int compareTo(Delayed o) {
        return future.compareTo(o);
    }

}
