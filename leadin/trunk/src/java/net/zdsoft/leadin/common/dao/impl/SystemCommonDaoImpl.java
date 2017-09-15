package net.zdsoft.leadin.common.dao.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.CannotGetJdbcConnectionException;

import net.zdsoft.keel.dao.BasicDAO;
import net.zdsoft.keel.dao.MultiRowMapper;
import net.zdsoft.keel.dao.SingleRowMapper;
import net.zdsoft.keel.util.Pagination;
import net.zdsoft.leadin.common.dao.SystemCommonDao;

public class SystemCommonDaoImpl extends BasicDAO implements SystemCommonDao{
    private static final Logger log = LoggerFactory.getLogger(SystemCommonDaoImpl.class);
    
	public void commonExec(String sql) {
		update(sql);
	}
	
	public List<String[]> getArrayList(String sql, int columnnum) {
		final int colNum = columnnum;
		return query(sql, new MultiRowMapper<String[]>() {

			public String[] mapRow(ResultSet rs, int rowNum) throws SQLException {
				String[] s = new String[colNum];
				for (int i = 0; i < colNum; i++) {
					s[i] = rs.getString(i + 1);
				}
				return s;
			}
		});
	}

    public List<String[]> getArrayList(String sql) throws Exception {
        List<String[]> list = new ArrayList<String[]>();
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            conn = this.getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            ResultSetMetaData rsmd = rs.getMetaData();
            final int colNum = rsmd.getColumnCount();
            while (rs.next()) {
                String[] s = new String[colNum];
                for (int i = 0; i < colNum; i++) {
                    s[i] = rs.getString(i + 1);
                }
                list.add(s);
            }
        } catch (CannotGetJdbcConnectionException e) {
            log.error(e.toString());
            throw e;
        } catch (SQLException e) {
            log.error(e.toString());
            throw e;
        } finally {
            try {
                if (rs != null)
                    rs.close();
                if (stmt != null)
                    stmt.close();
                if (conn != null)
                    this.releaseConnection(conn);
            } catch (SQLException e) {
                log.error(e.toString());
            }
        }
        return list;
    }

    @SuppressWarnings("unchecked")
    public List<String[]> getArrayList(String sql, int columnCount,
            Pagination page) {
        page.setUseCursor(true);
        final int colNum = columnCount;
        List<String[]> list = query(sql, new MultiRowMapper() {

            public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
                String[] s = new String[colNum];
                for (int i = 0; i < colNum; i++) {
                    s[i] = rs.getString(i + 1);
                }
                return s;
            }
        }, page);

        return list;
    }

   public Map<String, String> getHashMap(String sql) throws Exception {
        Map<String, String> map = new HashMap<String, String>();
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            conn = this.getConnection();
            stmt = conn.createStatement();
            String[] sqls = sql.split(";");
            for (String str : sqls) {
                rs = stmt.executeQuery(str);
                ResultSetMetaData rsmd = rs.getMetaData();
                final int colNum = rsmd.getColumnCount();
                while (rs.next()) {
                    String[] s = new String[colNum];
                    for (int i = 0; i < colNum; i++) {
                        s[i] = rs.getString(i + 1);
                    }
                    map.put(s[0], s[1]);
                }
            }
        } catch (CannotGetJdbcConnectionException e) {
            log.error(e.toString());
            throw e;
        } catch (SQLException e) {
            log.error(e.toString());
            throw e;
        } finally {
            try {
                if (rs != null)
                    rs.close();
                if (stmt != null)
                    stmt.close();
                if (conn != null)
                    this.releaseConnection(conn);
            } catch (SQLException e) {
                log.error(e.toString());
            }
        }
        return map;
    }

	public String getOneRecord(String sql) {
		return query(sql, new SingleRowMapper<String>() {

			public String mapRow(ResultSet rs) throws SQLException {
				String result = "";
				Object object = rs.getObject(1);
				if (object != null) {
					result = object.toString();
				}
				return result;
			}
		});
	}
	
	public int batchUpdateBySql(String sql, List<Object[]> args, int[] types){
		int[] cns = this.batchUpdate(sql, args, types);
		int count = 0;
		for(int cn : cns){
			if(cn == -2){
				count ++;
			}
		}
		return count;
	}

	public int batchUpdate(String[] sqls) {
	    if (null == sqls || sqls.length == 0)
            return 0;
	    
		int[] rtn = getJdbcTemplate().batchUpdate(sqls);
    	int count = 0;
    	for (int i : rtn) {
    		if(i>0)
			count += i;
		}
    	return count;
	}

    public int updateSQL(String sql, Object... args) {
        return update(sql, args);
    }

    public int updateSQLForIn(String sql, Object[] prefixArgs, Object[] inArgs) {
        return updateForInSQL(sql, prefixArgs, inArgs);
    }
}
