package net.zdsoft.eis.base.common.service;

import java.util.List;
import java.util.Map;

public interface BaseCommonService {

    /**
     * 远程数据接口专用，其他业务慎用此接口
     * 
     * @param map
     *            包含参数的map
     * @return
     */
    public List<Map<String, Object>> getDataMapByParamMap(String physicalTableName,
            Map<String, String> paramMap);
}
