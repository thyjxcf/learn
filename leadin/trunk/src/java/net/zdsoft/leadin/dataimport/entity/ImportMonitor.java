/* 
 * @(#)ImportMonitor.java    Created on Oct 21, 2010
 * Copyright (c) 2010 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.leadin.dataimport.entity;

/**
 * 导入监视器
 * 
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: Oct 21, 2010 2:22:33 PM $
 */
public class ImportMonitor {
    private ThreadGroup threadGroup;
    private Thread submitThread;
    private Thread takeThread;

    private boolean running;
    private boolean submitTaskRunning;
    private boolean takeTaskRunning;

    private String description;// 描述信息

    public ImportMonitor(ThreadGroup threadGroup, Thread submitThread, Thread takeThread,
            boolean running, boolean submitTaskRunning, boolean takeTaskRunning) {
        super();
        this.threadGroup = threadGroup;
        this.submitThread = submitThread;
        this.takeThread = takeThread;
        this.running = running;
        this.submitTaskRunning = submitTaskRunning;
        this.takeTaskRunning = takeTaskRunning;
    }

    public boolean isRunning() {
        return running;
    }

    public boolean isSubmitTaskRunning() {
        return submitTaskRunning;
    }

    public boolean isTakeTaskRunning() {
        return takeTaskRunning;
    }

    public ThreadGroup getThreadGroup() {
        return threadGroup;
    }

    public Thread getSubmitThread() {
        return submitThread;
    }

    public Thread getTakeThread() {
        return takeThread;
    }

    public String getDescription() {
        StringBuilder sb = new StringBuilder();
        if (null != threadGroup) {
            sb.append("线程组："+threadGroup);
            sb.append("&nbsp;&nbsp; activeCount:" + threadGroup.activeCount());
        }

        threadDesc(sb, submitThread);
        threadDesc(sb, takeThread);

        description = sb.toString();
        return description;
    }

    /**
     * 线程的描述信息
     * 
     * @param sb
     * @param thread
     */
    private void threadDesc(StringBuilder sb, Thread thread) {
        if (null != thread) {
            sb.append("<br>");
            sb.append("线程："+thread);
            sb.append("&nbsp;&nbsp; state:" + thread.getState());
            sb.append("&nbsp;&nbsp; alive:" + thread.isAlive());
            sb.append("&nbsp;&nbsp; interrupted:" + thread.isInterrupted());
            sb.append("<br>");
            sb.append("stackTrace:");
            StackTraceElement[] elements = thread.getStackTrace();
            for (int i = 0; i < elements.length; i++) {
                StackTraceElement stackTraceElement = elements[i];
                sb.append("<br>"+i + ":" + stackTraceElement);
            }
        }
    }
}
