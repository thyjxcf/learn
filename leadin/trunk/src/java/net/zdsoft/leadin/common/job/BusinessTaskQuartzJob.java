/* 
 * @(#)DataImportQuartzJob.java    Created on Jan 22, 2008
 * Copyright (c) 2006 ZDSoft Networks, Inc. All rights reserved.
 * $Header$
 */
package net.zdsoft.leadin.common.job;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.TimerTask;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicBoolean;

import net.zdsoft.keel.action.Reply;
import net.zdsoft.leadin.cache.SimpleCacheManager;
import net.zdsoft.leadin.common.entity.BusinessTask;
import net.zdsoft.leadin.common.service.BusinessTaskService;
import net.zdsoft.leadin.common.service.SchedulerTokenService;
import net.zdsoft.leadin.dataimport.common.TaskUtil;
import net.zdsoft.leadin.dataimport.entity.ImportMonitor;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.TransactionException;

public class BusinessTaskQuartzJob extends TimerTask {
    private final Logger log = LoggerFactory.getLogger(BusinessTaskQuartzJob.class);

    // 是否正在运行
    private final AtomicBoolean running = new AtomicBoolean(false);
    private boolean start = false;// 是否启动

    // ---------------------监控使用------------------------
    private final AtomicBoolean submitTaskRunning = new AtomicBoolean(false);// 从数据库取任务是否正在运行
    private final AtomicBoolean takeTaskRunning = new AtomicBoolean(false);// 从处理任务是否正在运行

    // 处理任务的所在单位 key=value=unitId
    private ConcurrentMap<String, String> unitIdMap = new ConcurrentHashMap<String, String>();

    private BusinessTaskService businessTaskService;
    private SimpleCacheManager simpleCacheManager;
    private SchedulerTokenService schedulerTokenService;

    private BlockingQueue<Future<BusinessTask>> finishTasks = null;// 完成任务队列
    private BlockingQueue<BusinessTask> jobs = null;// 阻塞队列
    CompletionService<BusinessTask> completionService = null;

    // 提交任务和处理任务线程
    private ThreadGroup threadGroup = null;
    private Thread submitThread = null;
    private Thread takeThread = null;

    private String tokenCode;
    
    /**
     * 定时轮循长时间处于“预执行”和“正在执行”状态的任务，将其改为“未处理”
     */
	public void updateJobNoHand() {
		if (null == tokenCode) {
			tokenCode = businessTaskService.getSchedulerTokenCode();
		}
		int resetTime = schedulerTokenService.getResetSecond(tokenCode);
		businessTaskService.updateJobNoHand(resetTime);
	}
    
    /**
     * 主过程，只会由定时器调用一次
     */
    public void run() {
        String taskName = businessTaskService.getChineseTaskName();
        synchronized (this) {
            if (!start) {
                System.out.println("提示：" + taskName + " 任务没有启动");
                return;
            } else {
                running.compareAndSet(false, true);
            }
        }

        System.out.println("提示：" + taskName + " 任务启动");
        runExec();
    }

    // ---------------------------多线程方式-----------------------------------
    private void runExec() {
        int concurrentcyNum = 1;// 并发数
        concurrentcyNum = businessTaskService.getConcurrentcyNum();

        String taskName = businessTaskService.getTaskName();
        
        //2012-07-05 允许多台服务器进行任务处理时，也须定时轮循长时间处于“预执行”和“正在执行”状态的任务，将其改为“未处理”
        //// 第一次运行前将正在处理的记录改为未处理，由于在处理过程中服务可能断掉，所以并未执行完
        updateJobNoHand();

        // 任务队列
        jobs = new LinkedBlockingQueue<BusinessTask>(concurrentcyNum);
        finishTasks = new LinkedBlockingQueue<Future<BusinessTask>>(concurrentcyNum);
        ExecutorService executor = Executors.newCachedThreadPool();
        completionService = new ExecutorCompletionService<BusinessTask>(executor, finishTasks);

        // 线程组
        threadGroup = new ThreadGroup("ThreadGroup-"+taskName);

        // 取导入任务并提交任务线程
        submitThread = new Thread(threadGroup, new ImportDataJobSumbit(concurrentcyNum),
                "Thread-"+taskName+"-submit");
        submitThread.start();
        
        // 处理导入任务
        takeThread = new Thread(threadGroup, new ImportDataJobTake(), "Thread-"+taskName+"-take");
        takeThread.start();

    }

    /**
     * 处理任务
     * 
     * @author zhaosf
     * @version $Revision: 1.0 $, $Date: Oct 21, 2010 11:22:48 AM $
     */
    private class ImportDataJobTake implements Runnable {
        public void run() {

            // 结果反馈
            while (true && running.get()) {
                takeTaskRunning.set(true);

                BusinessTask job = null;
                String error = "";
                Exception ex = null;
                Future<BusinessTask> future = null;
                try {
                    future = completionService.take();
                    if (null != future)
                        job = future.get(1800, TimeUnit.SECONDS);// 30分钟
                } catch (InterruptedException e) {
                    error = "中断异常";
                    ex = e;
                    Thread.currentThread().interrupt();
                } catch (ExecutionException e) {
                    error = "执行异常";
                    ex = e;
                } catch (TimeoutException e) {
                    error = "超时异常";
                    ex = e;
                }

                if (null != ex) {
                    log.error(ex.toString());

                    if (null != future) {
                        future.cancel(true);
                    }

                    if (null != job) {
                        job.setStatus(BusinessTask.TASK_STATUS_ERROR);
                        job.setJobEndTime(new Date());
                        job.setResultMsg(error + ": " + ex.getMessage());
                        businessTaskService.updateJobFinished(job);
                    }
                }

                // 任务完成时，去掉该任务所属单位，以便下次加入该单位的其它任务
                if (null != job) {
                    if (businessTaskService.isControlOneDataByUnitPerConcurrent()) {
                        synchronized (unitIdMap) {
                            unitIdMap.remove(job.getUnitId(), job.getUnitId());
                        }
                    }
                }

                log.debug("================任务完成去掉前");
                // 去掉任务
                try {
                    // 注：执行完成的任务与去除的任务并非同一个
                    jobs.take();
                    log.debug("================任务完成");

                } catch (InterruptedException e) {
                    log.error(e.toString());
                    Thread.currentThread().interrupt();
                }

                // 2010-10-26 中断线程，以便线程从sleep状态中苏醒过来
//                submitThread.interrupt();
            }
        }
    }

    /**
     * 从数据库中取任务并提交任务
     * 
     * @author zhaosf
     * @version $Revision: 1.0 $, $Date: May 6, 2010 2:54:35 PM $
     */
    private class ImportDataJobSumbit implements Runnable {
        private final int concurrentcyNum;// 并发数

        public ImportDataJobSumbit(int concurrentcyNum) {
            this.concurrentcyNum = concurrentcyNum;
        }

        public void run() {
            while (true && running.get()) {
                submitTaskRunning.set(true);

                boolean submit = false;// 是否提交

                final List<BusinessTask> businessTasks = new ArrayList<BusinessTask>();
                try {
                	schedulerTokenService.handleBusinessWithTicket(new SchedulerTokenService.BusinessHandle() {
						
						@Override
						public String getSchedulerTokenCode() {							
							return tokenCode;
						}
						
						@Override
						public void doThing() {
						
		                    // 2010-10-26
		                    // 从数据库取任务, 多预取1倍。否则在并发数较少时（如1条），容易出现此种情况：
		                    // 从数据库取记录A -> 提交任务，同时并发框架处理任务（更新记录状态为“正在处理”）-> 继续从数据库记录
		                    // （结果在记录A的状态未更新为“正在处理”前又把A记录取出来了，导致是同一个单位的记录（或同条记录），所以跳过并引起sleep）
							//2012-07-05 增加“预处理”状态后不再会出现此种情况 //concurrentcyNum * 2
		                    List<BusinessTask> jobList = businessTaskService
		                            .findNextNoHandJobs(concurrentcyNum);
		                    log.debug("================取任务: jobList.size=" + jobList.size());
		                    for (final BusinessTask job : jobList) {
	                            if (businessTaskService.isControlOneDataByUnitPerConcurrent()) {
	                                // 一个单位在一次并发中只执行一个任务
	                                if (unitIdMap.containsKey(job.getUnitId()))
	                                    continue;
	                            }
	
	                            // --------判断该任务是否正在执行中---------------
	                            if (jobs.contains(job)) {
	                                log.debug("================任务已在等待提交或执行中");
	                                continue;
	                            }
			                    	//--------更新记录状态为预处理 20120705------------
	                            	businessTaskService.updateRunTime(
	                            		BusinessTask.TASK_STATUS_PRE_HAND, new Date(), job);
			                        
	                            	businessTasks.add(job);
			                    }
								
							}
					});
                	
                    for (final BusinessTask job : businessTasks) {
                       
                    	log.debug("================任务准备加入");
                    	boolean putJob = false;
                    	while(!putJob){
                    		 try {
                    			 jobs.put(job);    
                    			 putJob = true;
                    		 } catch (InterruptedException e) {
                                 //log.error(e.toString());
                    			 putJob = false;
                              }                    		 
                       	}
                        
                        if (businessTaskService.isControlOneDataByUnitPerConcurrent()) {
                            synchronized (unitIdMap) {
                                unitIdMap.putIfAbsent(job.getUnitId(), job.getUnitId());
                            }
                        }

                        // 提交任务
                        Callable<BusinessTask> c = new Callable<BusinessTask>() {
                            public BusinessTask call() throws Exception {
                                return runSingleJob(job);
                            }
                        };
                        completionService.submit(c);
                        submit = true;
                        log.debug("================提交任务");
                       
                    }

                } catch (Exception e) {
                    log.error("提交任务出错！", e);
                }

                if (!submit) {
                    try {
                        Thread.sleep(10000);// 10秒钟后轮询
                    } catch (InterruptedException e) {
                         //log.error(e.toString());
                    }
                }
            }
        }
    }

    /**
     * 处理单个任务
     * 
     * @param job
     * @return
     */
    private BusinessTask runSingleJob(BusinessTask job) {
        Reply reply = new Reply();
        String replyId = job.getId();
        log.debug("===========正在处理的任务id="+replyId);

        try {
            log.debug("================任务更新状态为 正在处理 前");
            // 更新任务开始时间
            businessTaskService.updateRunTime(BusinessTask.TASK_STATUS_IN_HAND, new Date(), job);
            log.debug("================任务更新状态为 正在处理 后");

            // 处理业务数据
            businessTaskService.saveHandleBusinessTask(job,reply);

            // System.out.println("该任务导入完成,jobId=" + job.getId());
        } catch (TransactionException e){
        	e.printStackTrace();
            reply.addActionError("数据提交失败，请检查数据是否正确！");
        } catch (Exception e) {
        	e.printStackTrace();
            reply.addActionError(e.getMessage());
        } finally {
            // 有错误时
            if (null != reply.getActionErrors() && reply.getActionErrors().size() > 0) {
                Collection<String> c = reply.getActionErrors();
                String msg = TaskUtil.collection2HtmlStr(c);

                job.setStatus(BusinessTask.TASK_STATUS_ERROR);
                job.setJobEndTime(new Date());
                log.error(businessTaskService.getChineseTaskName()+"出错:" + msg);// 方便查找问题，由于消息可能被截断
                job.setResultMsg(msg);
                businessTaskService.updateJobFinished(job);
            }
            reply.setValue(BusinessTask.STATUS_END);
            simpleCacheManager.put(replyId, reply);
        }
        return job;
    }

    public void changeRunning() {
        boolean sign = false;// 原来的标志
        synchronized (this) {
            sign = running.get();
            running.set(!sign);
        }

        if (sign) {// 停止
            // 生产和消费线程的循环中有判断是否停止
            submitTaskRunning.set(false);
            takeTaskRunning.set(false);
            submitThread.interrupt();
            takeThread.interrupt();
        } else {// 启动
            runExec();
        }
    }

    // ---------------------监控使用------------------------
    public ImportMonitor getImportMonitor() {
        ImportMonitor monitor = new ImportMonitor(threadGroup, submitThread, takeThread, running
                .get(), submitTaskRunning.get(), takeTaskRunning.get());
        return monitor;
    }

    public void setSubmitTaskRunning(boolean sign) {
        submitTaskRunning.set(sign);
    }

    public void setTakeTaskRunning(boolean sign) {
        takeTaskRunning.set(sign);
    }

    // ---------------------------------------------

    /**
     * 初始化
     */
    public void init() {
        // 是否开启导入
        String start = System.getProperty(businessTaskService.getStartSign());
        if (StringUtils.isBlank(start)) {
            start = "false";
        }
        this.start = Boolean.parseBoolean(start);
    }

    public List<BusinessTask> getJobs() {
        List<BusinessTask> jobList = new ArrayList<BusinessTask>();
        if (null != jobs) {
            for (BusinessTask job : jobs) {
                jobList.add(job);
            }
        }
        return jobList;
    }

    public void setBusinessTaskService(BusinessTaskService businessTaskService) {
        this.businessTaskService = businessTaskService;
    }

    public void setSimpleCacheManager(SimpleCacheManager simpleCacheManager) {
        this.simpleCacheManager = simpleCacheManager;
    }

	public void setSchedulerTokenService(SchedulerTokenService schedulerTokenService) {
		this.schedulerTokenService = schedulerTokenService;
	}

}
