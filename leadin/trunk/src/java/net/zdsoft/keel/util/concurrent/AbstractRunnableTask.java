/* 
 * @(#)AbstractRunnableTask.java    Created on 2007-12-5
 * Copyright (c) 2007 ZDSoft Networks, Inc. All rights reserved.
 * $Id: AbstractRunnableTask.java,v 1.2 2008/07/18 12:30:40 huangwj Exp $
 */
package net.zdsoft.keel.util.concurrent;

/**
 * 描述某个系统任务的抽象基类.
 * 
 * @author huangwj
 * @version $Revision: 1.2 $, $Date: 2008/07/18 12:30:40 $
 * @see {@link AbstractCallableTask}
 */
public abstract class AbstractRunnableTask extends AbstractTask implements
        Runnable {

    public AbstractRunnableTask(String name) {
        super(name);
    }

    /**
     * 处理任务执行的方法.
     * 
     * @throws Exception
     *             执行异常时抛出
     */
    public abstract void processTask() throws Exception;

    /**
     * 任务线程执行的方法, 直接调用任务处理方法.
     */
    public void run() {
        if (!isWorking) {
            return;
        }

        try {
            if (log.isDebugEnabled())
                log.debug("Task[" + getName() + "] begin");

            long current = System.currentTimeMillis();
            processTask();

            if (log.isInfoEnabled()) {
                long elapsed = System.currentTimeMillis() - current;
                log.info("Task[" + getName() + "] finished, elapsed " + elapsed
                        + " ms");
            }
        }
        catch (Exception e) {
            log.error("Process task[" + getName() + "] error", e);
        }
    }

}
