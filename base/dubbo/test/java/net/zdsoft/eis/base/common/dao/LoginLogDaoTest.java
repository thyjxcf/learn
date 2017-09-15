package net.zdsoft.eis.base.common.dao;

import java.util.Date;

import net.zdsoft.eis.base.common.dao.LoginLogDao;
import net.zdsoft.eis.base.common.entity.LoginLog;

public class LoginLogDaoTest extends BaseDaoTestCase {

    private LoginLogDao loginLogDao;

    public void setLoginLogDao(LoginLogDao loginLogDao) {
        this.loginLogDao = loginLogDao;
    }

    public void testInsertLoginLog() {
        LoginLog log = new LoginLog();
        Date clickDate = new Date();
        log.setAccountId("402880132031c738012031c88ada0002");
        log.setCreationTime(clickDate);
        log.setLoginTime(clickDate);
        log.setRegionCode("3301");
        log.setRemoteIp("192.168.0.25");
        // log.setServerId(GlobalConstant.EIS_SERVER_ID);
        // log.setServerTypeId(GlobalConstant.EIS_SERVER_TYPE_ID);
        log.setUnitId("402880132031c238012041c88ada0002");
        log.setUserType(2);

        loginLogDao.insertLoginLog(log);
        // this.setComplete();
    }

}
