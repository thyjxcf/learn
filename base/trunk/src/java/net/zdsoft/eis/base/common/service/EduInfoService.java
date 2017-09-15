package net.zdsoft.eis.base.common.service;

import java.util.Map;

import net.zdsoft.eis.base.common.entity.EduInfo;

public interface EduInfoService {

    public EduInfo getEduInfo(String eduid);

    /**
     * 根据教育局id，得到教育局信息
     * 
     * @param ids
     * @return
     */
    public Map<String, EduInfo> getEduInfos(String[] eduInfoIds);
}
