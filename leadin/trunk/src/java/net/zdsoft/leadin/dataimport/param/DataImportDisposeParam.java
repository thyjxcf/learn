/* 
 * @(#)DataImportDisposeParam.java    Created on Aug 9, 2010
 * Copyright (c) 2010 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.leadin.dataimport.param;

import java.util.List;

/**
 * 导入处理过程中的缓存参数
 * 
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: Aug 9, 2010 10:55:46 AM $
 */
public class DataImportDisposeParam {
    private String jobId; // 导入任务id

    private int errorCount;// 错误数
    private int successCount;// 成功数
    

    // ----------------分批次处理大文件--------------------
    private boolean needCycle;// 是否需要循环导入
    private List<int[]> batches;// 批次数,[0]起始行,[1]结束行
    private int currentBatch = 0;// 当前批次

    /**
     * @return Returns the jobId.
     */
    public String getJobId() {
        return jobId;
    }

    /**
     * @param jobId The jobId to set.
     */
    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    /**
     * @return Returns the errorCount.
     */
    public int getErrorCount() {
        return errorCount;
    }

    /**
     * @param errorCount The errorCount to set.
     */
    public void setErrorCount(int errorCount) {
        this.errorCount = errorCount;
    }

    /**
     * @return Returns the successCountt.
     */
    public int getSuccessCount() {
        return successCount;
    }

    /**
     * @param successCountt The successCountt to set.
     */
    public void setSuccessCount(int successCount) {
        this.successCount = successCount;
    }

    /**
     * @return Returns the needCycle.
     */
    public boolean isNeedCycle() {
        return needCycle;
    }

    /**
     * @param needCycle The needCycle to set.
     */
    public void setNeedCycle(boolean needCycle) {
        this.needCycle = needCycle;
    }

    /**
     * @return Returns the batches.
     */
    public List<int[]> getBatches() {
        return batches;
    }

    /**
     * @param batches The batches to set.
     */
    public void setBatches(List<int[]> batches) {
        this.batches = batches;
    }

    /**
     * @return Returns the currentBatch.
     */
    public int getCurrentBatch() {
        return currentBatch;
    }

    /**
     * @param currentBatch The currentBatch to set.
     */
    public void setCurrentBatch(int currentBatch) {
        this.currentBatch = currentBatch;
    }

}
