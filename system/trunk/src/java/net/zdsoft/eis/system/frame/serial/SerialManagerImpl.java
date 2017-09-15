/* 
 * @(#)SerialManagerImpl.java    Created on Jul 7, 2010
 * Copyright (c) 2010 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.eis.system.frame.serial;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.zdsoft.eis.base.common.entity.Module;
import net.zdsoft.eis.base.common.entity.SubSystem;
import net.zdsoft.eis.base.common.service.ModuleService;
import net.zdsoft.eis.base.common.service.SubSystemService;
import net.zdsoft.eis.base.common.service.SystemIniService;
import net.zdsoft.license.LicenseInfo;
import net.zdsoft.license.service.LicenseService;

import org.apache.commons.lang.StringUtils;

/**
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: Jul 7, 2010 5:15:41 PM $
 */
public class SerialManagerImpl implements SerialManager {

    private static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private ModuleService moduleService;
    private SubSystemService subSystemService;
    private LicenseService licenseService;
    private SystemIniService systemIniService;

    public void setModuleService(ModuleService moduleService) {
        this.moduleService = moduleService;
    }

    public void setSubSystemService(SubSystemService subSystemService) {
        this.subSystemService = subSystemService;
    }

    public void setLicenseService(LicenseService licenseService) {
        this.licenseService = licenseService;
    }

    public void setSystemIniService(SystemIniService systemIniService) {
        this.systemIniService = systemIniService;
    }

    private boolean needCheckLicense() {
        return systemIniService.getBooleanValue("SYSTEM.CHECKLICENSE");
    }

    public String verifySerial() {
//        if (!needCheckLicense())
//            return "";
//
//        LicenseInfo licenseInfo = licenseService.getLicense();
//        if (licenseInfo == null)
//            return "没有注册信息,请先注册";
//        if (licenseInfo.getExpireDate().compareTo(new Date()) < 0) {
//            return "注册信息已过期, 请重新注册";
//        }
        return "";
    }

    public String getExpireDate() {
        if (!needCheckLicense())
            return null;
        
        LicenseInfo licenseInfo = licenseService.getLicense();
        if (licenseInfo == null)
            return null;
        String expDate = dateFormat.format(licenseInfo.getExpireDate());
        return expDate;
    }

    public Set<Integer> getModuleIds() {
        Set<Integer> set = new HashSet<Integer>();

        // TODO 根据文件解析出来的模块，暂时取数据库
        List<Integer> list = moduleService.getAllModuleIds();

        // 取模块Map
        Map<Integer, Module> modules = moduleService.getAllModuleMap();

        // 取启用了哪些子系统
        Set<Integer> subsystemSet = getSubsystems();

        // 过滤：某模块的数据所包括的子系统中只要其中一个子系统没有上，则不显示该模块
        for (Integer id : list) {
            Module m = modules.get(id);
            String dss = m.getDataSubsystems();
            boolean find = true;
            if (StringUtils.isNotBlank(dss)) {
                String[] subsystemIds = m.getDataSubsystems().split(",");
                for (String subsystemId : subsystemIds) {
                    if (!(subsystemSet.contains(Integer.valueOf(subsystemId.trim())))) {
                        find = false;
                        break;
                    }
                }
            }
            if (find) {
                set.add(id);
            }
        }
        return set;
    }

    public int getUserCountLimit() {
        if (!needCheckLicense()) 
            return 0;

        LicenseInfo licenseInfo = licenseService.getLicense();
        return licenseInfo.getUserNumPerUnit();
    }

    public int getUnitCountLimit() {
        if (!needCheckLicense())
            return 0;
        
        LicenseInfo licenseInfo = licenseService.getLicense();
        return licenseInfo.getUnitNum();
    }

    public boolean isOverUnitCountLimit(int cnt) {
        int limit = getUnitCountLimit();
        if (0 == limit) {
            return true;
        } else {
            return limit > cnt;
        }
    }

    public Set<Integer> getSubsystems() {

        if (!needCheckLicense()) {
            Set<Integer> set = new HashSet<Integer>();
            List<SubSystem> subsystems = subSystemService.getSubSystems();
            for (SubSystem subSystem : subsystems) {
                set.add(subSystem.getId().intValue());
            }
            return set;
        } else {
            List<SubSystem> subsystems = subSystemService.getSubSystems();
            LicenseInfo licenseInfo = licenseService.getLicense();
            Set<Integer> result = new HashSet<Integer>();
            if (licenseInfo == null) {
                return result;
            }
            Set<String> codes = licenseInfo.getAvailableSubsystems();
            for (SubSystem subsystem : subsystems) {
                if (codes.contains(subsystem.getCode())) {
                    result.add(subsystem.getId().intValue());
                }
            }
            return result;
        }
    }

}
