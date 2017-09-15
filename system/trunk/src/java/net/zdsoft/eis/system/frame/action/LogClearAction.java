package net.zdsoft.eis.system.frame.action;

import java.util.List;

import net.zdsoft.eis.base.common.service.UserLogService;
import net.zdsoft.eis.frame.action.BaseAction;
import net.zdsoft.eis.system.frame.entity.LogConfig;
import net.zdsoft.eis.system.frame.service.LogConfigService;

public class LogClearAction extends BaseAction {

    /**
     * 
     */
    private static final long serialVersionUID = -83913692979478237L;
    private UserLogService userLogService;
    private LogConfigService logConfigService;

    public void logClear() {
        //只清理当前安装和启用的子系统的日志信息
        List<LogConfig> configList = logConfigService.getLogConfigs(getLoginInfo().getUnitClass(),
                getLoginInfo().getUnitType());
        LogConfig logConfigDto;
        log.debug("It's time to clear logs;Begin----------");
        for (int i = 0; i < configList.size(); i++) {
            logConfigDto = configList.get(i);
            if (logConfigDto.getDays() > 0) {
                log.debug("clear logs where SubSystem in"
                        + logConfigDto.getSubSystem() + " and logTime before "
                        + logConfigDto.getDays());
                try {
                    userLogService.deleteUserLogs(logConfigDto
                            .getSubSystem(), logConfigDto.getDays());
                }
                catch (Exception e) {
                    log.error(e.toString());
                }
            }
        }
        log.debug("-----------clear logs end.");
    }
   

    public void setUserLogService(UserLogService userLogService) {
        this.userLogService = userLogService;
    }

    public void setLogConfigService(LogConfigService logConfigService) {
        this.logConfigService = logConfigService;
    }
}
