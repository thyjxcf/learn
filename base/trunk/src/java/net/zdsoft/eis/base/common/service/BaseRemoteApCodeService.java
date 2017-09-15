package net.zdsoft.eis.base.common.service;

import java.util.List;

import net.zdsoft.eis.base.common.entity.RemoteApp;

public interface BaseRemoteApCodeService {
    /**
     * 根据业务类型，获取接入ap信息
     * @param businessType
     * @return
     */
    List<RemoteApp> getAppsByBusinessType(String businessType);
}
