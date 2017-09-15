package net.zdsoft.eis.system.frame.dao;

import java.util.List;

import net.zdsoft.eis.system.frame.entity.LogConfig;

public interface LogConfigDao {
    /**
     * 增加
     * 
     * @param logConfig
     */
    public void insertLogConfig(LogConfig logConfig);

    /**
     * 更新
     * 
     * @param logConfig
     */
    public void updateLogConfig(LogConfig logConfig);

    /**
     * 批量更新系统日志设置天数
     * 
     * @param logConfigs
     */
    public void saveLogConfigDays(LogConfig logConfig);

    /**
     * 
     * @param logConfigId
     * @return
     */
    public LogConfig getLogConfig(long logConfigId);

    /**
     * 得到所有日志配置
     * 
     * @return
     */
    public List<LogConfig> getLogConfigs();

}
