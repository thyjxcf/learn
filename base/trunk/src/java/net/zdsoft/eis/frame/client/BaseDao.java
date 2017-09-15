package net.zdsoft.eis.frame.client;

import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.zdsoft.keel.dao.BasicDAO;
import net.zdsoft.keel.dao.MapRowMapper;
import net.zdsoft.keel.dao.MultiRowMapper;
import net.zdsoft.keel.dao.SingleRowMapper;
import net.zdsoft.leadin.util.UUIDGenerator;

import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.support.incrementer.DataFieldMaxValueIncrementer;

public abstract class BaseDao<T> extends BasicDAO {
    private DataFieldMaxValueIncrementer incre; // 主键值产生器

    public String getString(ResultSet rs, String columnName) throws SQLException {
        return rs.getString(columnName);
        // Integer index = initColumnMap(rs).get(columnName.toUpperCase());
        // if(index == null) {
        // return null;
        // }
        // else {
        // return rs.getString(index);
        // }
    }

    public int getInt(ResultSet rs, String columnName) throws SQLException {
        // Integer index = initColumnMap(rs).get(columnName.toUpperCase());
        // if(index == null) {
        // return 0;
        // }
        // else {
        // return rs.getInt(index);
        // }
        return rs.getInt(columnName);
    }

    public long getLong(ResultSet rs, String columnName) throws SQLException {
        // Integer index = initColumnMap(rs).get(columnName.toUpperCase());
        // if(index == null) {
        // return 0;
        // }
        // else {
        // return rs.getLong(index);
        // }
        return rs.getLong(columnName);
    }

    public int getInt(ResultSet rs, int columnIndex) throws SQLException {
        return rs.getInt(columnIndex);
    }

    public float getFloat(ResultSet rs, String columnName) throws SQLException {
        // Integer index = initColumnMap(rs).get(columnName.toUpperCase());
        // if(index == null) {
        // return 0;
        // }
        // else {
        // return rs.getFloat(index);
        // }
        return rs.getFloat(columnName);
    }

    public double getDouble(ResultSet rs, String columnName) throws SQLException {
        // Integer index = initColumnMap(rs).get(columnName.toUpperCase());
        // if(index == null) {
        // return 0;
        // }
        // else {
        // return rs.getDouble(index);
        // }
        return rs.getDouble(columnName);
    }

    public Date getDate(ResultSet rs, String columnName) throws SQLException {
        // Integer index = initColumnMap(rs).get(columnName.toUpperCase());
        // if(index == null) {
        // return null;
        // }
        // else {
        // return rs.getDate(index);
        // }
        return rs.getDate(columnName);
    }

    public Timestamp getTimestamp(ResultSet rs, String columnName) throws SQLException {
        // Integer index = initColumnMap(rs).get(columnName.toUpperCase());
        // if(index == null) {
        // return null;
        // }
        // else {
        // return rs.getTimestamp(index);
        // }
        return rs.getTimestamp(columnName);
    }

    /**
     * 数据库字段与对象要严格一致。
     * 性能比一般的写法要慢一点，适用于访问量不是很大的功能
     * 
     * @param rs
     * @param t
     * @return
     * @throws SQLException
     */
    protected T setFieldWithRs(ResultSet rs, T t) throws SQLException {
        Map<String, Integer> map = initColumnMap(rs);
        Method[] methods = t.getClass().getMethods();
        Map<String, Method> methodMap = new HashMap<String, Method>();
        for (Method m : methods) {
            String name = m.getName();
            if (!StringUtils.startsWith(name, "set")) {
                continue;
            }
            methodMap.put(m.getName(), m);
        }
        for (String columnName : map.keySet()) {
            String entityName = "";
            String[] ss = columnName.toLowerCase().split("_");
            for (String s : ss) {
                entityName += StringUtils.substring(s, 0, 1).toUpperCase()
                        + StringUtils.substring(s, 1);
            }
            try {
                String name = "set" + entityName;
                Method m = methodMap.get(name);
                if (m == null)
                    continue;
                Class[] classes = m.getParameterTypes();
                List<Object> args = new ArrayList<Object>();
                for (Class c : classes) {
                    if (StringUtils.equals(c.getName(), "java.lang.String")) {
                        args.add(rs.getString(columnName));
                    }
                    else if (StringUtils.equals(c.getName(), "java.lang.Integer")
                            || StringUtils.equals(c.getName(), "int")) {
                        args.add(rs.getInt(columnName));
                    }
                    else if (StringUtils.equals(c.getName(), "java.lang.Double")
                            || StringUtils.equals(c.getName(), "double")) {
                        args.add(rs.getDouble(columnName));
                    }
                    else if (StringUtils.equals(c.getName(), "java.lang.Float")
                            || StringUtils.equals(c.getName(), "float")) {
                        args.add(rs.getFloat(columnName));
                    }
                    else if (StringUtils.equals(c.getName(), "java.lang.Long")
                            || StringUtils.equals(c.getName(), "long")) {
                        args.add(rs.getLong(columnName));
                    }
                    else {
                        args.add(rs.getObject(columnName));
                    }
                }

                m.invoke(t, args.toArray(new Object[0]));
                // MethodUtils.invokeMethod(t, "set" +entityName,
                // rs.getObject(map.get(columnName)));
            }
            catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        return t;
    }

    protected Map<String, Object> setFieldMapWithRs(ResultSet rs) throws SQLException {
        Map<String, Integer> map = initColumnMap(rs);
        Map<String, Object> retMap = new HashMap<String, Object>();
        for (String columnName : map.keySet()) {
            retMap.put(columnName, rs.getObject(map.get(columnName)));
        }
        return retMap;
    }

    private Map<String, Integer> initColumnMap(ResultSet rs) throws SQLException {
        Map<String, Integer> columnMap = new HashMap<String, Integer>();
        ResultSetMetaData resultSetMetaData = rs.getMetaData();
        for (int i = 1; i <= resultSetMetaData.getColumnCount(); i++) {
            String cn = resultSetMetaData.getColumnName(i);
            columnMap.put(cn.toUpperCase(), i);
        }
        return columnMap;
    }

    public void setIncre(DataFieldMaxValueIncrementer incre) {
        this.incre = incre;
    }

    public long getIncrementerKey() {
        return incre.nextLongValue();
    }

    public class MultiRow implements MultiRowMapper<T> {
        public T mapRow(ResultSet rs, int numRow) throws SQLException {
            return setField(rs);
        }
    }

    public class MapRow implements MapRowMapper<String, T> {
        public String mapRowKey(ResultSet rs, int rowNum) throws SQLException {
            return rs.getString("id");
        }

        public T mapRowValue(ResultSet rs, int numRow) throws SQLException {
            return setField(rs);
        }
    }

    /**
     * 自定义KEY的MapRow
     * 
     * @author zhaosf
     * @version $Revision: 1.0 $, $Date: Oct 13, 2010 4:59:08 PM $
     */
    public abstract class MapRowCustomKey implements MapRowMapper<String, T> {
        public abstract String getKey();

        public String mapRowKey(ResultSet rs, int rowNum) throws SQLException {
            return rs.getString(getKey());
        }

        public T mapRowValue(ResultSet rs, int numRow) throws SQLException {
            return setField(rs);
        }
    }

    public class SingleRow implements SingleRowMapper<T> {
        public T mapRow(ResultSet rs) throws SQLException {
            return setField(rs);
        }
    }

    public abstract T setField(ResultSet rs) throws SQLException;

    public String getGUID() {
        return UUIDGenerator.getUUID();
    }

    public long getUpdatestamp() {
        return System.currentTimeMillis();
    }

    protected Date getCreateDate() {
        return Calendar.getInstance().getTime();
    }

    protected Date getModifyDate() {
        return Calendar.getInstance().getTime();
    }

    protected Date getCurrentDate() {
        return Calendar.getInstance().getTime();
    }

}
