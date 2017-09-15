package net.zdsoft.eis.base.common.service.impl;

import java.util.Map;

import net.zdsoft.eis.base.common.dao.EduInfoDao;
import net.zdsoft.eis.base.common.entity.EduInfo;
import net.zdsoft.eis.base.common.service.EduInfoService;

public class EduInfoServiceImpl implements EduInfoService {

    private EduInfoDao eduInfoDao;

    public void setEduInfoDao(EduInfoDao eduInfoDao) {
        this.eduInfoDao = eduInfoDao;
    }

    public EduInfo getEduInfo(String eduid) {
        EduInfo edu = eduInfoDao.getEduInfo(eduid);
        return edu;
    }

    public Map<String, EduInfo> getEduInfos(String[] eduInfoIds) {
        return eduInfoDao.getEduInfos(eduInfoIds);
    }
}
