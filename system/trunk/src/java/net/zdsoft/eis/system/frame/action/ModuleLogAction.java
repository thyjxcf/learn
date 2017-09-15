package net.zdsoft.eis.system.frame.action;

import java.util.Date;

import org.apache.commons.lang.StringUtils;

import net.zdsoft.eis.base.common.entity.Server;
import net.zdsoft.eis.base.common.entity.SubSystem;
import net.zdsoft.eis.base.common.entity.Unit;
import net.zdsoft.eis.base.common.service.ServerService;
import net.zdsoft.eis.base.common.service.SubSystemService;
import net.zdsoft.eis.base.common.service.UnitService;
import net.zdsoft.eis.frame.action.BaseAction;
import net.zdsoft.eis.frame.client.LoginInfo;
import net.zdsoft.eis.system.frame.entity.ModuleLog;
import net.zdsoft.eis.system.frame.service.ModuleLogService;

/**
 * @deprecated 已合并到PermissionCheckInterceptor中
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: Aug 16, 2010 11:03:17 AM $
 */
public class ModuleLogAction extends BaseAction {
    private static final long serialVersionUID = -2170563904754449584L;
    String url;
    String appId;
    String modelId;
    private ModuleLogService moduleLogService;
    private UnitService unitService;
    private SubSystemService subSystemService;
    private ServerService serverService;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getModelId() {
        return modelId;
    }

    public void setModelId(String modelId) {
        this.modelId = modelId;
    }

    public void setModuleLogService(ModuleLogService moduleLogService) {
        this.moduleLogService = moduleLogService;
    }

    public void setUnitService(UnitService unitService) {
        this.unitService = unitService;
    }

    public void setSubSystemService(SubSystemService subSystemService) {
        this.subSystemService = subSystemService;
    }

    public void setServerService(ServerService serverService) {
        this.serverService = serverService;
    }

    public String execute() throws Exception {
        try {
            LoginInfo info = getLoginInfo();

            int serverId = 0;
            int serverTypeId = 0;
            if (!StringUtils.isEmpty(appId)) {
                SubSystem subSystem = subSystemService.getSubSystem(Integer.parseInt(appId));
                Server app = serverService.getServerByServerCode(subSystem.getCode());
                if (null != app) {
                    serverId = Integer.parseInt(app.getId());
                    serverTypeId = Long.valueOf(app.getServerTypeId()).intValue();
                }
            }

            info.getUser().getId();
            Date clickDate = new Date();
            String unitId = info.getUnitID();
            Unit unit = unitService.getUnit(unitId);
            ModuleLog log = new ModuleLog();
            log.setAccountId(info.getUser().getAccountId());
            log.setCreationTime(clickDate);
            log.setModuleId(modelId);
            log.setRegionCode(unit.getRegion());
            log.setServerId(serverId);
            log.setServerTypeId(serverTypeId);
            if (!StringUtils.isEmpty(appId))
                log.setSubsystemId(Integer.parseInt(appId));
            log.setUserType(info.getUser().getType());
            log.setUnitId(info.getUnitID());
            moduleLogService.insertModuleLog(log);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (url.contains("?")) {
            url += "&appId=" + appId;
        } else {
            url += "?appId=" + appId;
        }
        return SUCCESS;
    }

}
