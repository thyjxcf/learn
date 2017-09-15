/* 
 * @(#)ImportThread.java    Created on 2007-3-22
 * Copyright (c) 2006 ZDSoft Networks, Inc. All rights reserved.
 * $Header: f:/44CVSROOT/exam/src/net/zdsoft/exam/dataimport/ImportThread.java,v 1.1 2007/03/26 13:05:01 linqz Exp $
 */
package net.zdsoft.leadin.dataimport.core;

import net.zdsoft.keel.action.Reply;
import net.zdsoft.keelcnet.config.ContainerManager;
import net.zdsoft.leadin.cache.SimpleCacheManager;
import net.zdsoft.leadin.dataimport.common.DataImportConstants;
import net.zdsoft.leadin.dataimport.exception.ImportPromptException;
import net.zdsoft.leadin.dataimport.param.DataImportParam;

import org.springframework.transaction.TransactionException;

public class ImportThread extends Thread {

    private DataImportParam param;
    private Reply reply;
    private String fileName;
    private String initFile;

    private static SimpleCacheManager simpleCacheManager;

    public ImportThread(String fileName, DataImportParam param, Reply reply, String initFile) {
        this.param = param;
        this.reply = reply;
        this.fileName = fileName;
        this.initFile = initFile;
    }
    
    @Override
    public void run() {
        if (null == simpleCacheManager) {
            simpleCacheManager = (SimpleCacheManager) ContainerManager
                    .getComponent("simpleCacheManager");
        }

        ImportJobProcess handle = new ImportJobProcess(fileName, param, reply, initFile);
        try {
            handle.process();
        } catch (ImportPromptException e) {
        	reply.addActionMessage(e.getMessage());
        }
        catch (TransactionException e){
        	e.printStackTrace();
            reply.addActionError("数据提交失败，请检查数据是否正确！");
        } catch (Exception e) {
        	e.printStackTrace();
            reply.addActionError(e.getMessage());
        } finally {
            reply.setValue(DataImportConstants.STATUS_END);
            String replyId = param.getReplyId();
            simpleCacheManager.put(replyId, reply);
        }
    }
}
