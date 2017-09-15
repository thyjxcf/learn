/* 
 * @(#)DeleteDataServiceImpl.java    Created on Jan 7, 2010
 * Copyright (c) 2010 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.eis.frame.service.impl;

import java.util.Date;
import java.util.List;

import net.zdsoft.eis.frame.service.DeleteXmlDataService;
import net.zdsoft.eis.frame.util.DeleteXMLDecoder;
import net.zdsoft.leadin.common.dao.SystemCommonDao;

/**
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: Jan 7, 2010 12:02:08 PM $
 */
public class DeleteXmlDataServiceImpl implements DeleteXmlDataService {

    private SystemCommonDao systemCommonDao;

    public void setSystemCommonDao(SystemCommonDao systemCommonDao) {
        this.systemCommonDao = systemCommonDao;
    }

    public void delete(String configFile, String arg) {
        List<List<String>> sqlArrList = DeleteXMLDecoder.decodeDeleteXml(configFile);
        if (sqlArrList.size() == 3) {
            List<String> delList = sqlArrList.get(0);
            List<String> updatestampList = sqlArrList.get(1);
            List<String> modifytimeList = sqlArrList.get(2);

            for (String sql : delList) {
                systemCommonDao.updateSQL(sql, arg);
            }

            for (String sql : updatestampList) {
                systemCommonDao.updateSQL(sql, new Object[] { System.currentTimeMillis(), arg },
                        arg);
            }

            for (String sql : modifytimeList) {
                systemCommonDao.updateSQL(sql, new Object[] { new Date(), arg });
            }
        }
    }

    public void delete(String configFile, String[] args) {
        List<List<String>> sqlArrList = DeleteXMLDecoder.decodeDeleteXml(configFile);
        if (sqlArrList.size() == 3) {
            List<String> delList = sqlArrList.get(0);
            List<String> updatestampList = sqlArrList.get(1);
            List<String> modifytimeList = sqlArrList.get(2);

            for (String sql : delList) {
                systemCommonDao.updateSQLForIn(sql, null, args);
            }

            for (String sql : updatestampList) {
                systemCommonDao.updateSQLForIn(sql, new Object[] { System.currentTimeMillis() },
                        args);
            }

            for (String sql : modifytimeList) {
                systemCommonDao.updateSQLForIn(sql, new Object[] { new Date() }, args);
            }
        }
    }

}
