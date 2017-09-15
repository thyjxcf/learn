/* 
 * @(#)BusinessTask.java    Created on Feb 13, 2012
 * Copyright (c) 2012 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.leadin.common.entity;

import java.util.Date;

/**
 * 后台执行的业务任务
 * 
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: Feb 13, 2012 11:00:24 AM $
 */
public class BusinessTask {

    public static final int TASK_STATUS_NO_HAND = 0;// 等待执行
    public static final int TASK_STATUS_IN_HAND = 1;// 正在执行
    public static final int TASK_STATUS_SUCCESS = 2;// 执行成功 swf状态
    public static final int TASK_STATUS_ERROR = 3;// 执行失败
    public static final int TASK_STATUS_PRE_HAND = 4;//预处理，和待执行和正在执行区分，以便于消息提示
    public static final int TASK_STATUS_WINDOW_HAND = 5;//预处理，和待执行和正在执行区分，以便于消息提示
    public static final int TASK_STATUS_NOT_NEED_HAND = 9;//不需要处理

    /**
     * 执行状态
     */
    public static final String STATUS_END = "status_end";

    private String id;// 表的UUID主键
    private String name;
    private int status;// 状态
    private Date jobStartTime;
    private Date jobRunTime;// 开始运行时间
    private Date jobEndTime;// 结束时间
    private String resultMsg; // 结果信息
    private String unitId;// 单位id
    private String filePath;// 文件路径
    private String fileType;// 文件类型

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }    

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getJobStartTime() {
        if(jobStartTime == null){
            jobStartTime = new Date();
        }
        return jobStartTime;
    }

    public void setJobStartTime(Date jobStartTime) {
        this.jobStartTime = jobStartTime;
    }
    
    public Date getJobRunTime() {
        return jobRunTime;
    }

    public void setJobRunTime(Date jobRunTime) {
        this.jobRunTime = jobRunTime;
    }

    public Date getJobEndTime() {
    	if(jobEndTime == null){
            jobEndTime = new Date();
        }
        return jobEndTime;
    }

    public void setJobEndTime(Date jobEndTime) {
        this.jobEndTime = jobEndTime;
    }

    public String getResultMsg() {
        return resultMsg;
    }

    public void setResultMsg(String resultMsg) {
        this.resultMsg = resultMsg;
    }

    public String getUnitId() {
        return unitId;
    }

    public void setUnitId(String unitId) {
        this.unitId = unitId;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    @Override
    public boolean equals(Object obj) {
        return this.id.equals(((BusinessTask) obj).getId());
    }
}
