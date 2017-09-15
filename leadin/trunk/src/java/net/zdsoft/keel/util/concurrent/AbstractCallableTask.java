/* 
 * @(#)AbstractCallableTask.java    Created on 2007-12-27
 * Copyright (c) 2007 ZDSoft Networks, Inc. All rights reserved.
 * $Id: AbstractCallableTask.java,v 1.2 2008/07/18 12:30:58 huangwj Exp $
 */
package net.zdsoft.keel.util.concurrent;

import java.util.concurrent.Callable;

/**
 * 描述某个系统任务的抽象基类.和 <code>AbstractRunnableTask</code> 的区别是此任务带有执行的结果.
 * 
 * @author huangwj
 * @version $Revision: 1.2 $, $Date: 2008/07/18 12:30:58 $
 * @see {@link AbstractRunnableTask}
 */
public abstract class AbstractCallableTask<V> extends AbstractTask implements
        Callable<V> {

    public AbstractCallableTask(String name) {
        super(name);
    }

    /**
     * 处理任务执行的方法.
     * 
     * @return 任务执行结果
     * @throws Exception
     *             执行异常时抛出
     */
    public abstract V processTask() throws Exception;

    /**
     * 任务线程执行的方法, 直接调用任务处理方法.
     */
    public V call() {
        if (!isWorking) {
            return null;
        }

        V result = null;

        try {
            if (log.isDebugEnabled())
                log.debug("Task[" + getName() + "] begin");

            long current = System.currentTimeMillis();
            result = processTask();

            if (log.isInfoEnabled()) {
                long elapsed = System.currentTimeMillis() - current;
                log.info("Task[" + getName() + "] finished, elapsed " + elapsed
                        + " ms");
            }
        }
        catch (Exception e) {
            log.error("Process task[" + getName() + "] error", e);
        }

        return result;
    }

}
