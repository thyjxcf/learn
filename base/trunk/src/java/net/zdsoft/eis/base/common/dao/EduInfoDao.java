package net.zdsoft.eis.base.common.dao;

import java.util.Map;

import net.zdsoft.eis.base.common.entity.EduInfo;

public interface EduInfoDao {

    public EduInfo getEduInfo(String eduInfoId);

    public Map<String, EduInfo> getEduInfos(String[] eduInfoIds);

}
