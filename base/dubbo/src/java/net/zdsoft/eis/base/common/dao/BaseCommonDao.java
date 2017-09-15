package net.zdsoft.eis.base.common.dao;

import java.util.List;
import java.util.Map;

public interface BaseCommonDao {

    public List<Map<String, Object>> getDataMapByParamMap(String tableName,
            Map<String, String> paramMap);
}
