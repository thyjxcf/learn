package net.zdsoft.eis.base.common.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import net.zdsoft.eis.base.common.dao.BaseStringDao;
import net.zdsoft.eis.frame.client.BaseDao;

public class BaseStringDaoImpl extends BaseDao<String> implements BaseStringDao {

    private final String SQL_GET_VALUE = "SELECT value FROM base_string where code = ?";

    @Override
    public String getValue(String code) {
        return query(SQL_GET_VALUE, code, new SingleRow());
    }

    @Override
    public String setField(ResultSet rs) throws SQLException {
        return rs.getString("value");
    }

}
