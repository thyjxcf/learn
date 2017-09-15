package net.zdsoft.eis.base.common.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.zdsoft.eis.base.common.dao.BaseCommonDao;
import net.zdsoft.eis.base.common.service.BaseCommonService;

import org.springframework.stereotype.Service;

@Service
public class BaseCommonServiceImpl implements BaseCommonService {

    @Resource
    private BaseCommonDao baseCommonDao;

    @Override
    public List<Map<String, Object>> getDataMapByParamMap(String physicalTableName,
            Map<String, String> paramMap) {
        return baseCommonDao.getDataMapByParamMap(physicalTableName, paramMap);
    }

}
