package net.zdsoft.eis.base.common.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.zdsoft.eis.base.common.dao.BaseCommonDao;
import net.zdsoft.eis.frame.client.BaseDao;
import net.zdsoft.keel.dao.MultiRowMapper;
import net.zdsoft.keel.util.Pagination;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.stereotype.Repository;

@Repository
public class BaseCommonDaoImpl extends BaseDao<Object> implements BaseCommonDao {

    public List<Map<String, Object>> getDataMapByParamMap(String physicalTableName,
            Map<String, String> paramMap) {
        if (StringUtils.isBlank(physicalTableName))
            return new ArrayList<Map<String, Object>>();
        String sql = "SELECT * FROM " + physicalTableName;
        return dealDataMapByParam(paramMap, sql);

    }

    protected List<Map<String, Object>> dealDataMapByParam(Map<String, String> paramMap, String sql) {
        String limit = paramMap.get("_limit");
        String page = paramMap.get("_page");
        String modifyTime = paramMap.get("_dataModifyTime");
        List<String> wheres = new ArrayList<String>();
        List<Object> values = new ArrayList<Object>();
        for (String key : paramMap.keySet()) {
            if (!StringUtils.startsWith(key, "_")) {
                wheres.add(key + " = ? ");
                values.add(paramMap.get(key));
            }
        }
        if (StringUtils.isNotBlank(modifyTime)) {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
                values.add(sdf.parse(modifyTime));
                wheres.add("modify_time > ? ");
            }
            catch (ParseException e) {
                e.printStackTrace();
            }
        }

        String where = CollectionUtils.isNotEmpty(wheres) ? " where "
                + StringUtils.join(wheres.toArray(new String[0]), " AND ") : "";
        if (NumberUtils.toInt(limit) > 0) {
            Pagination pagination = new Pagination(NumberUtils.toInt(page),
                                                   NumberUtils.toInt(limit), false);
            List<Map<String, Object>> data = query(sql + where, values.toArray(new Object[0]),
                    new MultiRowMapper<Map<String, Object>>() {
                        @Override
                        public Map<String, Object> mapRow(ResultSet rs, int rowNum)
                                throws SQLException {
                            return setFieldMapWithRs(rs);
                        }
                    }, pagination);
            paramMap.put("_maxRow", "" + pagination.getMaxRowCount());
            paramMap.put("_maxPage", "" + pagination.getMaxPageIndex());
            paramMap.put("_page", "" + pagination.getPageIndex());
            paramMap.put("_limit", "" + pagination.getPageSize());
            return data;
        }
        else {
            List<Map<String, Object>> data = query(sql + where, values.toArray(new Object[0]),
                    new MultiRowMapper<Map<String, Object>>() {
                        @Override
                        public Map<String, Object> mapRow(ResultSet rs, int rowNum)
                                throws SQLException {
                            return setFieldMapWithRs(rs);
                        }
                    });

            paramMap.put("_maxRow", "" + data.size());
            paramMap.put("_maxPage", "1");
            paramMap.put("_page", "" + page);
            paramMap.put("_limit", "" + data.size());
            return data;
        }
    }

    @Override
    public Object setField(ResultSet rs) throws SQLException {
        // TODO Auto-generated method stub
        return null;
    }

}
