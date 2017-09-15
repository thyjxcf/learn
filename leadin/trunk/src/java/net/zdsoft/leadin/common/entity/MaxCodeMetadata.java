/* 
 * @(#)MaxCodeMetadata.java    Created on Oct 14, 2010
 * Copyright (c) 2010 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.leadin.common.entity;

import org.apache.commons.lang.StringUtils;

import net.zdsoft.leadin.util.Assert;

/**
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: Oct 14, 2010 5:46:42 PM $
 */
/**
 * 数据库元数据
 * 
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: Oct 14, 2010 2:42:53 PM $
 */
public class MaxCodeMetadata {
    private String sql;
    private String table;// 表
    private String codeCol;// 号码列
    private String unitIdCol;// 单位列
    private String deletedCol;// 软删除列

    public MaxCodeMetadata(String table, String codeCol) {
        Assert.notEmpty(table, "table must not be null and empty string");
        Assert.notEmpty(codeCol, "codeCol must not be null and empty string");
        this.table = table;
        this.codeCol = codeCol;
    }

    public MaxCodeMetadata setUnitIdCol(String unitIdCol) {
        this.unitIdCol = unitIdCol;
        return this;
    }

    public MaxCodeMetadata setDeletedCol(String deletedCol) {
        this.deletedCol = deletedCol;
        return this;
    }

    // 构造sql
    public String getSql() {
        if (null != sql)
            return sql;

        StringBuilder postfix = new StringBuilder();
        postfix.append("substr(");
        postfix.append(codeCol);
        postfix.append(", length(:prefix)+1 , length(");
        postfix.append(codeCol);
        postfix.append(") - length(:prefix)+1)");

        StringBuilder sb = new StringBuilder();
        sb.append("SELECT max(");
        sb.append(postfix);
        sb.append(") FROM ");
        sb.append(table);
        sb.append(" WHERE ");
        sb.append(codeCol);
        sb.append(" LIKE :prefix||'%'  and length(");
        sb.append(postfix);
        sb.append(") = :serialLen");

        // 单位条件
        if (StringUtils.isNotBlank(unitIdCol)) {
            sb.append(" AND ");
            sb.append(unitIdCol);
            sb.append(" = :unitId");
        }

        // 软删除条件
        if (StringUtils.isNotBlank(deletedCol)) {
            sb.append(" AND ");
            sb.append(deletedCol);
            sb.append(" = 0");
        }

        this.sql = sb.toString();
        return sql;
    }
    
    // 构造sql
    public String getNoPrefixSql() {
        if (null != sql)
            return sql;

        StringBuilder postfix = new StringBuilder();
        postfix.append(codeCol);
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT max(");
        sb.append(postfix);
        sb.append(") FROM ");
        sb.append(table);
        sb.append(" WHERE ");
        sb.append(codeCol);
        sb.append(" LIKE :prefix||'%'  and length(");
        sb.append(postfix);
        sb.append(") = :serialLen");

        // 单位条件
        if (StringUtils.isNotBlank(unitIdCol)) {
            sb.append(" AND ");
            sb.append(unitIdCol);
            sb.append(" = :unitId");
        }

        // 软删除条件
        if (StringUtils.isNotBlank(deletedCol)) {
            sb.append(" AND ");
            sb.append(deletedCol);
            sb.append(" = 0");
        }

        this.sql = sb.toString();
        return sql;
    }

    public String getTable() {
        return table;
    }

    public String getCodeCol() {
        return codeCol;
    }

    public String getUnitIdCol() {
        return unitIdCol;
    }

    public String getDeletedCol() {
        return deletedCol;
    }

}

