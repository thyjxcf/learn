/* 
 * @(#)AbstractDAO.java    Created on 2005-9-28
 * Copyright (c) 2005 ZDSoft.net, Inc. All rights reserved.
 * $Header: /project/keel/src/net/zdsoft/keel/dao/SlowBasicDAO.java,v 1.3 2007/01/11 09:15:14 liangxiao Exp $
 */
package net.zdsoft.keel.dao;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import net.zdsoft.keel.util.JdbcUtils;
import net.zdsoft.keel.util.ObjectUtils;
import net.zdsoft.keel.util.StringUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

/**
 * 全部都通过反射来实现，不需要RowMapper，开销会大一些，尚未完成
 * 
 * @deprecated
 * @author liangxiao
 * @version $Revision: 1.3 $, $Date: 2007/01/11 09:15:14 $
 */
@SuppressWarnings("unchecked")
public class SlowBasicDAO extends BasicDAO {

    private static Logger logger = LoggerFactory.getLogger(SlowBasicDAO.class);

    private class ObjectResultSetExtractor implements ResultSetExtractor {

        private Object entity;

        public ObjectResultSetExtractor(Object entity) {
            this.entity = entity;
        }

        public Object extractData(ResultSet rs) throws SQLException,
                DataAccessException {
            ResultSetMetaData rsmd = rs.getMetaData();
            int columnCount = rsmd.getColumnCount();
            String[] columnNames = new String[columnCount];
            int[] columnTypes = new int[columnCount];

            for (int i = 1; i <= columnCount; i++) {
                columnNames[i - 1] = rsmd.getColumnName(i);
                columnTypes[i - 1] = rsmd.getColumnType(i);
            }

            ArrayList list = new ArrayList();
            while (rs.next()) {
                Object newEntity = ObjectUtils
                        .createInstance(entity.getClass());
                for (int i = 0; i < columnNames.length; i++) {
                    ObjectUtils.setProperty(newEntity, columnNames[i],
                            JdbcUtils.getColumnValueFromResultSet(i + 1,
                                    columnTypes[i], rs));
                }
                list.add(newEntity);
            }

            return list;
        }
    }

    public SlowBasicDAO() {
    }

    public Object slowQuery(String sql, Object entity) {
        List list = slowQueryForList(sql, entity);
        return list.isEmpty() ? null : list.get(0);
    }

    public List slowQueryForList(String sql, Object entity) {
        String[] argNames = JdbcUtils.getSQLArgNames(sql);
        Object[] args = new Object[argNames.length];
        for (int i = 0; i < args.length; i++) {
            args[i] = ObjectUtils.getProperty(entity, StringUtils
                    .underline2Uppercase(argNames[i]));
        }
        if (logger.isDebugEnabled()) {
            logger.debug(JdbcUtils.getSQL(sql, args));
        }
        return (List) getJdbcTemplate().query(sql, args,
                new ObjectResultSetExtractor(entity));
    }

    public int slowUpdate(String sql, Object entity) {
        String[] argNames = JdbcUtils.getSQLArgNames(sql);
        Object[] args = new Object[argNames.length];
        for (int i = 0; i < args.length; i++) {
            args[i] = ObjectUtils.getProperty(entity, StringUtils
                    .underline2Uppercase(argNames[i]));
        }
        if (logger.isDebugEnabled()) {
            logger.debug(JdbcUtils.getSQL(sql, args));
        }
        return getJdbcTemplate().update(sql, args);
    }

    public Object slowFindById(Object entity) {
        String sql = "SELECT * FROM " + "entity.getTableName()" + " WHERE id=?";
        return slowQuery(sql, entity);
    }

    public List slowFindAll(Object entity) {
        String sql = "SELECT * FROM " + "entity.getTableName()";
        return slowQueryForList(sql, entity);
    }

    public int slowInsert(Object entity, boolean ignoreNull) {
        return 0;
    }

}
