/* 
 * @(#)BaseSubsystemServiceImpl.java    Created on May 28, 2010
 * Copyright (c) 2010 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.eis.base.subsystemcall.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.zdsoft.eis.base.common.entity.Mcodedetail;
import net.zdsoft.eis.base.common.entity.Region;
import net.zdsoft.eis.base.common.entity.SystemIni;
import net.zdsoft.eis.base.common.entity.Unit;
import net.zdsoft.eis.base.common.entity.User;
import net.zdsoft.eis.base.common.service.McodedetailService;
import net.zdsoft.eis.base.common.service.RegionService;
import net.zdsoft.eis.base.common.service.SystemIniService;
import net.zdsoft.eis.base.common.service.UnitService;
import net.zdsoft.eis.base.common.service.UserService;
import net.zdsoft.leadin.dataimport.subsystemcall.BaseSubsystemService;

/**
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: May 28, 2010 2:25:12 PM $
 */
public class BaseSubsystemServiceImpl implements BaseSubsystemService {
    private static final Logger log = LoggerFactory.getLogger(BaseSubsystemServiceImpl.class);

    private static final String CONCURRENCY_NUM_KEY = "SYSTEM.DATAIMPORT.CONCURRENCY.NUM";

    private RegionService regionService;
    private McodedetailService mcodedetailService;
    private SystemIniService systemIniService;
    protected UserService userService;
    private UnitService unitService;

    public void setUnitService(UnitService unitService) {
        this.unitService = unitService;
    }

    public void setRegionService(RegionService regionService) {
        this.regionService = regionService;
    }

    public void setMcodedetailService(McodedetailService mcodedetailService) {
        this.mcodedetailService = mcodedetailService;
    }

    public void setSystemIniService(SystemIniService systemIniService) {
        this.systemIniService = systemIniService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    // ================以上为set=========

    public List<String[]> getRegions() {
        List<String[]> rtnList = new ArrayList<String[]>();
        List<Region> list = regionService.getRegions();
        for (int i = 0; i < list.size(); i++) {
            Region region = (Region) list.get(i);
            rtnList.add(new String[] { region.getFullCode(), region.getFullName() });
        }
        return rtnList;
    }

    public Map<String, String> getCodeMap(String mcodeId) {
        Map<String, String> map = new LinkedHashMap<String, String>();
        List<Mcodedetail> details = mcodedetailService.getMcodeDetails(mcodeId);
        for (Mcodedetail detail : details) {
            map.put(detail.getContent(), detail.getThisId());
        }
        return map;
    }

    public int getDataImportConcurrentcyNum() {
        int concurrentcyNum = 1;// 并发数
        SystemIni ini = systemIniService.getSystemIni(CONCURRENCY_NUM_KEY);
        if (null != ini) {
            concurrentcyNum = Integer.parseInt(ini.getNowValue());
        }
        return concurrentcyNum;
    }

    public Map<String, String> getUsersMap(String[] userIds) {
        Map<String, String> userNameMap = new HashMap<String, String>();
        List<User> userList = userService.getUsers(userIds);
        for (User user : userList) {
            String name = null;
            if (user.getRealname() != null) {
                name = user.getRealname();
            } else {
                name = user.getName();
            }
            userNameMap.put(user.getId(), name);
        }
        return userNameMap;
    }

    public Map<String, Integer> getParentUnitMap(String unitId) {
        Map<String, Integer> map = new HashMap<String, Integer>();
        int order = 1;
        while (!Unit.TOP_UNIT_GUID.equals(unitId)) {
            Unit unit = unitService.getUnit(unitId);
            if (null == unit) {
                log.error("根据单位id得不到单位信息： unitId=" + unitId);
                break;
            }
            map.put(unit.getId(), order++);
            unitId = unit.getParentid();
        }
        return map;
    }
}
