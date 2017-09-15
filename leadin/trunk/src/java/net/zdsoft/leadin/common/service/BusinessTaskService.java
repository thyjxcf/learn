/* 
 * @(#)JobService.java    Created on Feb 13, 2012
 * Copyright (c) 2012 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.leadin.common.service;

import java.util.Date;
import java.util.List;

import net.zdsoft.keel.action.Reply;
import net.zdsoft.leadin.common.entity.BusinessTask;

/**
 * 后台执行的业务任务
 * 
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: Feb 13, 2012 10:52:09 AM $
 */
public interface BusinessTaskService {
	/**
	 * 获取调度令牌代码
	 * @return
	 */
	public String getSchedulerTokenCode();
	
    /**
     * 是否控制在每次并发处理中每个单位只取一条记录
     * 
     * @return
     */
    public boolean isControlOneDataByUnitPerConcurrent();

    /**
     * 取并发数
     * 
     * @return
     */
    public int getConcurrentcyNum();

    /**
     * 获取开启标志
     * 
     * @return
     */
    public String getStartSign();
    
    /**
     * 获取任务中文名称
     * @return
     */
    public String getChineseTaskName();
    
    /**
     * 获取任务名称
     * @return
     */
    public String getTaskName();

    /**
     * 处理任务
     */
    public void saveHandleBusinessTask(BusinessTask job,Reply reply) throws Exception;

    /**
     * 获取接下来要处理的n条记录数
     * 
     * @param cnt
     * @return
     */
    public List<BusinessTask> findNextNoHandJobs(int n);

    /**
     * 更新正在执行的任务为待执行
     * @param resetTime 单位：分钟
     */
    public void updateJobNoHand(int resetTime);

    /**
     * 更新任务完成
     * 
     * @param job
     */
    public void updateJobFinished(BusinessTask job);

    /**
     * 更新任务开始运行时间
     * 
     * @param jobRunTime
     * @param id
     * @return
     */
    public void updateRunTime(int status, Date jobRunTime, BusinessTask job);

}
