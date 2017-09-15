package net.zdsoft.eis.base.util;

import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionContext;

import net.zdsoft.eis.base.common.entity.Module;
import net.zdsoft.eis.base.common.entity.UserLog;
import net.zdsoft.eis.base.common.service.ModuleService;
import net.zdsoft.eis.base.common.service.UserLogService;
import net.zdsoft.eis.base.constant.BaseConstant;
import net.zdsoft.eis.frame.client.LoginInfo;
import net.zdsoft.keel.util.StringUtils;
import net.zdsoft.keelcnet.config.ContainerManager;
import net.zdsoft.leadin.util.RequestUtils;

public class SystemLog {

    /**
     * 日志描述最大长度
     */
    private static final int LOG_DESCRIPTION_LENGTH = 1023;

    private static final Logger log = LoggerFactory.getLogger(SystemLog.class);
    
    private static UserLogService userLogService;
    private static ModuleService moduleService;

    /**
     * 日志添加
     * 
     * @param modId模块号
     * @param description功能描述
     */
    public static void log(String modId, String description) {
        if (null == moduleService) {
            moduleService = (ModuleService) ContainerManager.getComponent("moduleService");
        }
        if (null == userLogService) {
            userLogService = (UserLogService) ContainerManager.getComponent("userLogService");
        }

        if (modId == null) {
            log.error("null of modId");
            return;
        }
        if (StringUtils.getRealLength(description) > LOG_DESCRIPTION_LENGTH) {
            log.error("description's length out of bound");
            return;
        }

        Map<String, Object> session = ActionContext.getContext().getSession();
        if (session == null) {
            return;
        }
        LoginInfo loginInfo = (LoginInfo) session.get(BaseConstant.SESSION_LOGININFO);
        if (loginInfo == null) {
            return;
        }

        String[] modIds = modId.split(",");
        Module module = moduleService.getModule(modIds[0], loginInfo.getUnitClass());
        if (module == null) {
            return;
        }

        UserLog userLog = new UserLog();
        userLog.setDescription(description);
        userLog.setModId(modId);
        userLog.setSubSystem(module.getSubsystem());

        userLog.setLogTime(new Date());
        userLog.setUserId(loginInfo.getUser().getId());
        userLog.setUnitid(loginInfo.getUnitID());

        String userName = loginInfo.getUser().getName();
        //修改了RequestUtils.getRealRemoteAddr的接口，增加了一个多级代理的情况
        String ipAddress = RequestUtils.getRealRemoteAddr(ServletActionContext.getRequest());
        userLog.setUserName(userName + "(" + ipAddress + ")");

        userLogService.insertUserLog(userLog);
    }
}
