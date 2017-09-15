package net.zdsoft.eis.system.frame.dao.impl;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import net.zdsoft.eis.system.frame.dao.ExceptionDataDao;
import net.zdsoft.eis.system.frame.dto.ResultPack;
import net.zdsoft.keel.dao.BasicDAO;
import net.zdsoft.keel.util.Validators;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

public class ExceptionDataDaoImpl extends BasicDAO implements ExceptionDataDao {

    @SuppressWarnings("unchecked")
    public ResultPack executeQuery(String sql) {
        final ResultPack resultPack = new ResultPack();

        return (ResultPack) getJdbcTemplate().query(sql, new ResultSetExtractor() {
            public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
                ResultSetMetaData rsmd = rs.getMetaData();

                int columnCount = rsmd.getColumnCount();
                String[] columnNames = new String[columnCount];

                for (int i = 0; i < columnCount; i++) {
                    String columnName = rsmd.getColumnName(i + 1);

                    if (Validators.isEmpty(columnName)) {
                        columnName = String.valueOf(i + 1);
                    }
                    columnNames[i] = columnName;
                }

                resultPack.setColumnNames(columnNames);

                while (rs.next()) {
                    String[] record = new String[columnCount];
                    for (int i = 0; i < columnCount; i++) {
                        record[i] = rs.getString(i + 1);
                    }
                    resultPack.addRecord(record);
                }

                return resultPack;
            }
        });
    }

    public Integer executeUpdate(final String sql) throws SQLException {
        int result = getJdbcTemplate().update(sql);
        return result;
    }

}
