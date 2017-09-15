/* 
 * @(#)DataImportListenerImpl.java    Created on 2008-10-22
 * Copyright (c) 2008 ZDSoft Networks, Inc. All rights reserved.
 * $Id: DataImportListenerImpl.java 25066 2008-10-28 04:28:08Z zhaosf $
 */
package net.zdsoft.leadin.dataimport.core;

import java.io.File;
import java.io.OutputStream;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.tools.zip.ZipOutputStream;

import net.zdsoft.keel.action.Reply;
import net.zdsoft.leadin.cache.SimpleCacheManager;
import net.zdsoft.leadin.dataimport.common.DataImportConstants;
import net.zdsoft.leadin.dataimport.common.TaskUtil;
import net.zdsoft.leadin.dataimport.common.ZipUtil;
import net.zdsoft.leadin.dataimport.entity.ImportDataJob;
import net.zdsoft.leadin.dataimport.event.DataImportEvent;
import net.zdsoft.leadin.dataimport.param.DataImportDisposeParam;
import net.zdsoft.leadin.dataimport.param.DataImportParam;
import net.zdsoft.leadin.dataimport.service.DataImportService;
import net.zdsoft.leadin.dataimport.service.ImportDataJobService;
import net.zdsoft.leadin.dataimport.template.ExcelTemplateUtil;

/**
 * @author zhaosf
 * @version $Revision: 25066 $, $Date: 2008-10-28 12:28:08 +0800 $
 */
public class DataImportListenerImpl implements DataImportListener {
    private ImportDataJobService importDataJobService;
    private SimpleCacheManager simpleCacheManager;

    public void setLeadinImportDataJobService(ImportDataJobService importDataJobService) {
        this.importDataJobService = importDataJobService;
    }

    public void setSimpleCacheManager(SimpleCacheManager simpleCacheManager) {
        this.simpleCacheManager = simpleCacheManager;
    }

    public void doAfter(DataImportEvent event) throws Exception {
        DataImportParam param = event.getParam();
        if (param == null)
            return;

        DataImportDisposeParam disposeParam = param.getDisposeParam();
        DataImportService dataImportService = param.getDataImportService();

        String jobId = disposeParam.getJobId();
        String replyId = null;

        // 走任务时，前面解析的消息都不再显示
        Reply reply = new Reply();
        if (StringUtils.isNotEmpty(jobId)) {
            reply = new Reply();
            replyId = jobId;
        } else {
            reply = event.getReply();
            replyId = param.getReplyId();
        }

        // --------------数据导入----------------
        dataImportService.importDatas(param, reply);

        // --------------错误数据处理--------------
        // 文件路径
        String objectName = param.getObjectName();
        String path = TaskUtil.createStoreSubdir(new String[] { DataImportConstants.FILE_PATH_ERROR,
                objectName });

        String fileNameNoPostfix = param.getUnitId();
        if (StringUtils.isNotEmpty(jobId)) {
            fileNameNoPostfix = jobId;

        }
        String fileName = fileNameNoPostfix + ".xls";
        String filePath = TaskUtil.getFilePath(new String[] { path, fileName });
        String zipPath = TaskUtil.getFilePath(new String[] { path, fileNameNoPostfix });

        // 针对分批次导入时，将错误文件打成zip包
        boolean batchImport = param.isBatchImport();
        boolean needCycle = false;
        int currentSec = 1;
        if (batchImport) {
            currentSec = disposeParam.getCurrentBatch();
            // 只是要分批处理，都打成压缩包
            needCycle = disposeParam.isNeedCycle();

            // if(needCycle || (!needCycle && currentSec != 1)){
            File file = new File(zipPath);
            if (!file.exists()) {
                file.mkdir();
            }
            filePath = TaskUtil.getFilePath(new String[] { zipPath,
                    String.valueOf(currentSec) + ".xls" });
            // }
            // }
        }

        int errorCnt = disposeParam.getErrorCount();

        // 生成错误文件
        ImportData importData = param.getImportData();
        boolean hasSubtitle = param.isHasSubtitle();
        List<String[]> errorDatas = importData.getErrorDataList();
        if (errorDatas != null && (!errorDatas.isEmpty())) {
            int colTitlePos = 0;
            int errorSize = errorDatas.size();
            if (hasSubtitle) {
                colTitlePos = 1;
                errorDatas.add(0, new String[] { param.getSubtitle() });
            }

            errorCnt = errorCnt + errorSize;
            disposeParam.setErrorCount(errorCnt);

            List<String> listOfDefine = importData.getFileDefineList();
            listOfDefine.add("错误列名");
            listOfDefine.add("错误信息");
            errorDatas.add(colTitlePos, listOfDefine.toArray(new String[0]));
            ExcelTemplateUtil excel = new ExcelTemplateUtil(errorDatas, importData
                    .getObjectDefine(), hasSubtitle,param.isHasTitle());
            excel.writeErrorFileToFile(filePath);
        }

        // 将错误文件打包
        if (batchImport && !needCycle) {
            @SuppressWarnings("unused")
            int sucCnt = disposeParam.getSuccessCount();

            // 了" + sucCnt + "条记录
            reply.addActionMessage("成功导入。");

            String zipFile = TaskUtil
                    .getFilePath(new String[] { path, param.getUnitId() + ".zip" });
            filePath = zipFile;
            if (0 != errorCnt) {
                OutputStream os = new ZipOutputStream(new File(zipFile));
                ZipUtil.makeZip(zipPath, (ZipOutputStream) os); // 压缩包
                ZipUtil.deleteFile(zipPath);
            }
        }

        if (needCycle)
            return;

        // --------------回显消息--------------
        if (0 != errorCnt) {
            // 任务形式
            if (StringUtils.isNotEmpty(jobId)) {
                reply.addActionMessage("有<b><font color='red'>" + errorCnt + "</font></b>条错误记录。");
            } else {
                reply.addActionMessage("有<font color='red'>" + errorCnt
                        + "</font>条错误记录，点击【错误数据】进行查看。");
            }
        } else {
            File f = new File(filePath);
            if (f.exists()) {
                f.delete();
            }
            filePath = null;
        }

        reply.setValue(DataImportConstants.STATUS_END);
        simpleCacheManager.put(replyId, reply);

        // --------------更新任务--------------
        if (StringUtils.isNotEmpty(jobId)) {

            @SuppressWarnings("unchecked")
            Collection<String> c = reply.getActionMessages();

            String msg = TaskUtil.collection2HtmlStr(c);

            ImportDataJob job = new ImportDataJob();
            job.setId(jobId);
            job.setStatus(DataImportConstants.TASK_STATUS_SUCCESS);
            job.setJobEndTime(new Date());
            job.setResultMsg(msg);
            if (null != filePath) {
                job.setErrorFile(TaskUtil.getFilePath(new String[] { DataImportConstants.FILE_PATH_ERROR,
                        objectName, fileName }));
            }
            importDataJobService.updateJobFinished(job);
        }
    }

    public void doBefore(DataImportEvent event) throws Exception {

    }

}
