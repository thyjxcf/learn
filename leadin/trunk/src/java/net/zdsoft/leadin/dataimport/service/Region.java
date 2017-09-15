package net.zdsoft.leadin.dataimport.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.zdsoft.keelcnet.config.ContainerManager;
import net.zdsoft.leadin.dataimport.subsystemcall.BaseSubsystemService;

public class Region {
    private static Map<String, String> fullNameMapByCodeName = new HashMap<String, String>();
    private static BaseSubsystemService regionService;

    public Region() {

    }

    /**
     * 通过行政区划名称得到行政区划码
     * 
     * @param regionName 行政区划名称
     * @return 行政区划码
     */
    public static String getRegionCodeByFullName(String regionName) {
        if (fullNameMapByCodeName.get(regionName) == null) {
            if (null == regionService) {
                regionService = (BaseSubsystemService) ContainerManager
                        .getComponent("baseSubsystemService");
            }
            List<String[]> regionList = regionService.getRegions();
            for (String region[] : regionList) {
                fullNameMapByCodeName.put(region[1], region[0]);
            }
        }
        return (String) fullNameMapByCodeName.get(regionName);
    }
}
