package net.zdsoft.eis.base.common.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import net.zdsoft.eis.base.common.dao.BaseRemoteApCodeDao;
import net.zdsoft.eis.base.common.entity.RemoteApp;
import net.zdsoft.eis.base.common.service.BaseRemoteApCodeService;

@Service
public class BaseRemoteApCodeServiceImpl implements BaseRemoteApCodeService {

    @Resource
    private BaseRemoteApCodeDao baseRemoteApCodeDao;
    
    @Override
    public List<RemoteApp> getAppsByBusinessType(String businessType) {
        return baseRemoteApCodeDao.getAppsByBusinessType(businessType);
    }

}
