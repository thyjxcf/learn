package net.zdsoft.eis.system.frame.service;

import java.util.List;

import net.zdsoft.eis.system.frame.entity.LogConfig;

public interface LogConfigService {
    /**
     * 保存所有子系统日志设置
     * 
     * @param logConfigs
     */
    public void saveLogConfigs(LogConfig[] logConfigs);

    /**
     * 取得所有子系统日志设置
     * 
     * @param unitClass
     * @param unitType
     * @return
     */
    public List<LogConfig> getLogConfigs(int unitClass, int unitType);

}
