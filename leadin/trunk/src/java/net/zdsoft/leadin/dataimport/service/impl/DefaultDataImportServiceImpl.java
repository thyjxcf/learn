/* 
 * @(#)DataImportServiceImpl.java    Created on 2008-10-27
 * Copyright (c) 2008 ZDSoft Networks, Inc. All rights reserved.
 * $Id: DataImportServiceImpl.java 25035 2008-10-27 11:23:11Z zhaosf $
 */
package net.zdsoft.leadin.dataimport.service.impl;

import java.util.List;

import net.zdsoft.keel.action.Reply;
import net.zdsoft.leadin.dataimport.core.ImportObject;
import net.zdsoft.leadin.dataimport.exception.ImportErrorException;
import net.zdsoft.leadin.dataimport.param.DataImportParam;

/**
 * 
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: Aug 20, 2010 2:20:23 PM $
 */
public class DefaultDataImportServiceImpl extends AbstractDataImportService {

    @Override
    public List<List<String[]>> exportDatas(ImportObject importObject, String[] cols) {
        return null;
    }

    @Override
    public void importDatas(DataImportParam param, Reply reply) throws ImportErrorException {

    }

}
