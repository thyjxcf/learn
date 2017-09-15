/* 
 * @(#)TeachPlaceResServiceImpl.java    Created on May 13, 2011
 * Copyright (c) 2011 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.eisu.base.common.service.impl;

import java.util.List;
import java.util.Map;

import net.zdsoft.eisu.base.common.dao.TeachPlaceResDao;
import net.zdsoft.eisu.base.common.entity.TeachPlaceRes;
import net.zdsoft.eisu.base.common.entity.TeachRes;
import net.zdsoft.eisu.base.common.service.TeachPlaceResService;
import net.zdsoft.eisu.base.common.service.TeachResService;

/**
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: May 13, 2011 8:19:14 PM $
 */
public class TeachPlaceResServiceImpl implements TeachPlaceResService {
    private TeachPlaceResDao teachPlaceResDao;
    private TeachResService teachResService;

    public void setTeachResService(TeachResService teachResService) {
        this.teachResService = teachResService;
    }

    public void setTeachPlaceResDao(TeachPlaceResDao teachPlaceResDao) {
        this.teachPlaceResDao = teachPlaceResDao;
    }

    public TeachPlaceRes getTeachPlaceRes(String teachPlaceResId) {
        return teachPlaceResDao.getTeachPlaceRes(teachPlaceResId);
    }

    public Map<String, TeachPlaceRes> getTeachPlaceResMap(String[] teachPlaceResIds) {
        return teachPlaceResDao.getTeachPlaceResMap(teachPlaceResIds);
    }

    public List<TeachPlaceRes> getTeachPlaceResesByPlaceId(String placeId) {
        List<TeachPlaceRes> list = teachPlaceResDao.getTeachPlaceResesByPlaceId(placeId);
        wrappTeachPlaceReses(list);
        return list;
    }

    public List<TeachPlaceRes> getTeachPlaceResesByUnitId(String unitId) {
        return teachPlaceResDao.getTeachPlaceResesByUnitId(unitId);
    }

    private void wrappTeachPlaceReses(List<TeachPlaceRes> list) {
        Map<String, TeachRes> resMap = teachResService.getTeachResMap();
        for (TeachPlaceRes teachPlaceRes : list) {
            TeachRes res = resMap.get(teachPlaceRes.getTeachResId());
            if (null != res) {
                teachPlaceRes.setTeachResName(res.getResName());
            }
        }
    }
}
