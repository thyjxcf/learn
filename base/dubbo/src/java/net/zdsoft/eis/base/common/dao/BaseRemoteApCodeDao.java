package net.zdsoft.eis.base.common.dao;

import java.util.List;

import net.zdsoft.eis.base.common.entity.RemoteApp;

public interface BaseRemoteApCodeDao {
    /**
     * 根据业务类型，获取接入ap信息
     * @param businessType
     * @return
     */
    List<RemoteApp> getAppsByBusinessType(String businessType);

}
