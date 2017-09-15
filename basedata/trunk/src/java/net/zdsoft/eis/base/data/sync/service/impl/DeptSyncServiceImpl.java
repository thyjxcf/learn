/* 
 * @(#)UnitSyncServiceImpl.java    Created on Dec 9, 2010
 * Copyright (c) 2010 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.eis.base.data.sync.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.winupon.syncdata.basedata.entity.son.MqDept;

import net.zdsoft.eis.base.common.entity.Dept;
import net.zdsoft.eis.base.data.service.BaseDeptService;
import net.zdsoft.eis.base.sync.AbstractHandlerTemplate;
import net.zdsoft.eis.base.sync.BatchHandlable;
import net.zdsoft.eis.base.sync.EventSourceType;
import net.zdsoft.leadin.exception.BusinessErrorException;

/**
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: Dec 9, 2010 10:29:49 AM $
 */
public class DeptSyncServiceImpl extends AbstractHandlerTemplate<Dept, MqDept> implements
        BatchHandlable<Dept> {
    private BaseDeptService baseDeptService;

    public void setBaseDeptService(BaseDeptService baseDeptService) {
        this.baseDeptService = baseDeptService;
    }

    @Override
    public void addData(Dept e) throws BusinessErrorException {
        setExtFieldsData(e);
        List<Dept> list=new ArrayList<Dept>();
        list.add(e);
        baseDeptService.saveBatchDepts4Mq(list);
    }

    @Override
    public void deleteData(String id, EventSourceType eventSource) throws BusinessErrorException {
        baseDeptService.deleteDept(new String[] { id }, eventSource);

    }

    private void setExtFieldsData(Dept e) {
       e.setMark(1);
    }
    
    @Override
    public void updateData(Dept e) throws BusinessErrorException {
    	List<Dept> list=new ArrayList<Dept>();
        list.add(e);
        baseDeptService.updateBatchDepts4Mq(list);

    }

    @Override
    public void addDatas(List<Dept> depts) throws BusinessErrorException {
        for (Dept e : depts) {
            setExtFieldsData(e);
        }
        baseDeptService.saveBatchDepts4Mq(depts);

    }

    @Override
    public void deleteDatas(String[] ids, EventSourceType eventSource)
            throws BusinessErrorException {
        baseDeptService.deleteDept(ids, eventSource);

    }

    @Override
    public void updateDatas(List<Dept> depts) throws BusinessErrorException {
        baseDeptService.updateBatchDepts4Mq(depts);
    }

    @Override
    public Dept fetchOldEntity(String id) {
        return null;
    }

    @Override
    public Map<String, Dept> fetchOldEntities(String[] ids) {
        return null;
    }

}
