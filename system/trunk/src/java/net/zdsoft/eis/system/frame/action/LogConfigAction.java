package net.zdsoft.eis.system.frame.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.zdsoft.eis.base.util.SystemLog;
import net.zdsoft.eis.frame.action.BaseAction;
import net.zdsoft.eis.frame.dto.PromptMessageDto;
import net.zdsoft.eis.system.frame.entity.LogConfig;
import net.zdsoft.eis.system.frame.service.LogConfigService;

import com.opensymphony.xwork2.ModelDriven;

public class LogConfigAction extends BaseAction implements ModelDriven<LogConfig[]> {

    /**
     * 
     */
    private static final long serialVersionUID = -821467697769244000L;
    private String modID = "SYS001";
    private Integer logConfigMaxValue = 365;
    private LogConfigService logConfigService;
    private List<LogConfig> logConfigList;
    private LogConfig[] logConfigDtos;
    private Map<Object,Object> returnJsonData = new HashMap<Object,Object>();

	public String execute() {

        return null;
    }

    public String getLogConfig() {
        logConfigList = logConfigService.getLogConfigs(getLoginInfo().getUnitClass(),
                getLoginInfo().getUnitType());
        return SUCCESS;
    }

    public String saveLogConfig() {
        setPromptMessageDto(new PromptMessageDto());
        try {
            logConfigService.saveLogConfigs(logConfigDtos);

            promptMessageDto.setOperateSuccess(true);
            promptMessageDto.setPromptMessage("保存成功!");
        } catch (Exception e) {
            log.error(e.toString());
            SystemLog.log(modID, "保存日志清理配置失败！");
            promptMessageDto.setOperateSuccess(false);
            promptMessageDto.setPromptMessage("保存失败!");
        }
        SystemLog.log(modID, "保存日志清理配置成功！");
        promptMessageDto.addOperation(new String[] { "确定", "platformInfoAdmin-logAdmin.action"});
        returnJsonData.put("promptMessageDto", promptMessageDto);
        return SUCCESS;
    }

    public void setLogConfigService(LogConfigService logConfigService) {
        this.logConfigService = logConfigService;
    }

    public LogConfig[] getModel() {

        //只清理当前安装和启用的子系统的日志信息        
        List<LogConfig> list = logConfigService.getLogConfigs(getLoginInfo().getUnitClass(),
                getLoginInfo().getUnitType());

        logConfigDtos = new LogConfig[list.size()];
        for (int i = 0; i < logConfigDtos.length; i++) {
            logConfigDtos[i] = new LogConfig();
        }
        return this.logConfigDtos;
    }

    public List<LogConfig> getLogConfigList() {
        return logConfigList;
    }

    public LogConfig[] getLogConfigDtos() {
        return logConfigDtos;
    }

    public Integer getLogConfigMaxValue() {
        return logConfigMaxValue;
    }
    
    public Map<Object, Object> getReturnJsonData() {
		return returnJsonData;
	}

	public void setReturnJsonData(Map<Object, Object> returnJsonData) {
		this.returnJsonData = returnJsonData;
	}

}
