/* 
 * @(#)ImportJobProcess.java    Created on Sep 20, 2009
 * Copyright (c) 2009 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.leadin.dataimport.core;

import java.io.File;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.zdsoft.keel.action.Reply;
import net.zdsoft.leadin.dataimport.event.DataImportDispatcher;
import net.zdsoft.leadin.dataimport.event.DataImportEvent;
import net.zdsoft.leadin.dataimport.exception.ImportErrorException;
import net.zdsoft.leadin.dataimport.exception.ImportPromptException;
import net.zdsoft.leadin.dataimport.param.DataImportDisposeParam;
import net.zdsoft.leadin.dataimport.param.DataImportParam;

/**
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: Sep 20, 2009 5:11:33 PM $
 */
public class ImportJobProcess {
    private static Logger log = LoggerFactory.getLogger(ImportJobProcess.class);
    
    private DataImportParam param;
    private Reply reply;
    private String fileName;
    private String initFile;
    
    public ImportJobProcess(String fileName, DataImportParam param,
            Reply reply, String initFile) {
        this.param = param;
        this.reply = reply;
        this.fileName = fileName;
        this.initFile = initFile;
    }
    
    public void process() throws Exception {
        try{            
            if (initFile == null || "".equals(initFile)) {
                throw new ImportErrorException("-->找不到配置文件······");
            }   

            long begin = System.currentTimeMillis();
            log.debug("begintime:" + begin);
            
            DataImportDisposeParam disposeParam = param.getDisposeParam();
            
            //是否需要重复
            boolean needCycle = true;
            while (needCycle) {
                long perbegin = System.currentTimeMillis();                
                Integer currentSec = disposeParam.getCurrentBatch();              
                log.debug("第"+currentSec+"批　begintime:" + perbegin);
                
                ImportData importData = new ImportData(initFile, param, reply, fileName);
                importData.startImport();

                needCycle = disposeParam.isNeedCycle();
                
                List<String> listenerList = importData.getListeners();
                
                List<Object> listOfImportData = importData.getListOfImportDataObject();
                List<String[]> listOfErrorImportData = importData
                        .getErrorDataList();
                    
                if (listOfImportData.size() > 0 || listOfErrorImportData.size() > 0) {
                    DataImportEvent event = new DataImportEvent(param, reply,
                            DataImportEvent.DO_AFTER);
                    DataImportDispatcher.dispatchEvent(listenerList, event);
                }else{
                    throw new ImportPromptException("没有可处理的数据!");
                }      
                               
                log.debug("第"+currentSec+"批　totaltime:" + (System.currentTimeMillis() -perbegin));
            }
            
            long end = System.currentTimeMillis();
            log.debug("totaltime : " + (end - begin));
           
        } finally{
            try{
                File file = new File(fileName);
                if(file.exists())
                    file.delete();
            }catch (Exception e) {
                log.error("删除文件出错",e);
            }
        }
    }
}


