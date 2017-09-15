/* 
 * @(#)CodeBuilder.java    Created on 2005-10-12
 * Copyright (c) 2005 ZDSoft.net, Inc. All rights reserved.
 * $Header: /project/keel/src/net/zdsoft/keel/dao/CodeBuilder.java,v 1.10 2007/08/21 08:29:10 liangxiao Exp $
 */
package net.zdsoft.keel.dao;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;

import javax.sql.DataSource;

import net.zdsoft.keel.util.Validators;

import org.springframework.context.support.FileSystemXmlApplicationContext;

/**
 * 代码生成工具，用于生成DAO、Entity和RowMapper的部分代码
 * 
 * @author liangxiao
 * @version $Revision: 1.10 $, $Date: 2007/08/21 08:29:10 $
 */
public class CodeBuilder {

    private static final String NEWLINE = "\"\n\t + \"";
    private static final String SQL_HEAD = "private static final String SQL_";

    private static final String ID = "id";
    private static final String CREATIONTIME = "creationtime";

    private String tableName;
    private String entityName;

    private ArrayList<String> names = new ArrayList<String>();
    private ArrayList<Integer> dataTypes = new ArrayList<Integer>();

    boolean hasId = false;
    boolean hasCreationTime = false;

    /**
     * 构造方法
     * 
     * @param tableName
     *            数据库的表名
     * @param entityName
     *            Entity的名称
     */
    public CodeBuilder(String tableName, String entityName) {
        this.tableName = tableName;
        this.entityName = entityName;

        FileSystemXmlApplicationContext ctx = new FileSystemXmlApplicationContext(
                "/web/WEB-INF/applicationContext.xml");

        DataSource dataSource = (DataSource) ctx.getBean("dataSource");

        try {
            Connection conn = dataSource.getConnection();
            @SuppressWarnings("unused")
            DatabaseMetaData dmd = conn.getMetaData();

            // ResultSet rs = conn.getMetaData().getColumns(null,
            // dmd.getUserName(), tableName, "%");

            ResultSet rs = conn.getMetaData().getColumns(null, null, tableName,
                    "%");

            while (rs.next()) {
                String name = rs.getString("COLUMN_NAME");
                int dataType = rs.getInt("DATA_TYPE");

                names.add(name);
                dataTypes.add(new Integer(dataType));

                if (ID.equalsIgnoreCase(name)) {
                    hasId = true;
                }
                if (CREATIONTIME.equalsIgnoreCase(name)) {
                    hasCreationTime = true;
                }
            }

            rs.close();
            conn.close();

            if (!hasId) {
                System.out
                        .println("Table[" + tableName + "] has no column[id]");
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private String getJavaType(int dataType) {
        if (dataType == Types.INTEGER) {
            return "int";
        }
        else if (dataType == Types.TIMESTAMP || dataType == Types.DATE
                || dataType == Types.TIME) {
            return "Date";
        }
        else {
            return "String";
        }
    }

    private String getJavaMethod(int dataType) {
        if (dataType == Types.INTEGER) {
            return "getInt";
        }
        else if (dataType == Types.TIMESTAMP || dataType == Types.DATE
                || dataType == Types.TIME) {
            return "getTimestamp";
        }
        else {
            return "getString";
        }
    }

    private String getSQLType(int dataType) {
        if (dataType == Types.INTEGER) {
            return "Types.INTEGER";
        }
        else if (dataType == Types.CHAR) {
            return "Types.CHAR";
        }
        else if (dataType == Types.VARCHAR) {
            return "Types.VARCHAR";
        }
        else if (dataType == Types.DOUBLE) {
            return "Types.DOUBLE";
        }
        else if (dataType == Types.TIMESTAMP) {
            return "Types.TIMESTAMP";
        }
        else if (dataType == Types.NUMERIC) {
            return "Types.NUMERIC";
        }
        else if (dataType == Types.FLOAT) {
            return "Types.FLOAT";
        }
        else if (dataType == Types.DATE) {
            return "Types.DATE";
        }
        else if (dataType == Types.TIME) {
            return "Types.TIME";
        }
        else {
            return "Types.UNDEFINED";
        }
    }

    private String getQuestionMark(int size) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < size; i++) {
            if (i > 0) {
                sb.append(",");
            }
            sb.append("?");
        }
        return sb.toString();
    }

    /**
     * 需要改进
     * 
     * @param str
     * @return
     */
    private String initialToUpperCase(String str) {
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }

    /**
     * 需要改进
     * 
     * @param str
     * @return
     */
    private String initialToLowerCase(String str) {
        return str.substring(0, 1).toLowerCase() + str.substring(1);
    }

    private String getSetMethod(String name) {
        StringBuffer sb = new StringBuffer();
        sb.append(initialToLowerCase(entityName));
        sb.append(".set");
        sb.append(initialToUpperCase(name));
        return sb.toString();
    }

    private String getGetMethod(String name) {
        StringBuffer sb = new StringBuffer();
        sb.append(initialToLowerCase(entityName));
        sb.append(".get");
        sb.append(initialToUpperCase(name));
        sb.append("()");
        return sb.toString();
    }

    /**
     * 生成Entity的部分代码
     * 
     * @return
     */
    public String getEntityCode() {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < names.size(); i++) {
            String name = (String) names.get(i);

            // 设置Entity的属性名称, 更符合Java的命名规则
            name = generateJavaPropertyName(name);

            int dataType = ((Integer) dataTypes.get(i)).intValue();

            sb.append("private ");
            sb.append(getJavaType(dataType));
            sb.append(" ");
            sb.append(name);
            sb.append(";\n");
        }
        return sb.toString();
    }

    /**
     * 根据数据库表中的字段名称, 生成符合Java的命名规范的Entity属性名称
     * 
     * @param fieldName
     *            数据库表中的字段名称
     * @return Entity属性名称
     */
    private String generateJavaPropertyName(String fieldName) {
        if (Validators.isEmpty(fieldName)) {
            return fieldName;
        }

        String propertyName = null;

        // 去掉字段名称前面的"c_"前缀, 如果存在的话
        if (fieldName.indexOf("c_") != -1) {
            propertyName = fieldName.substring(2);
        }
        else {
            propertyName = fieldName;
        }

        // 去掉字段名称中的'_'符号, 并将其后的字母大写
        StringBuffer propertyNameBuffer = new StringBuffer();
        for (int i = 0; i < propertyName.length(); i++) {
            char c = propertyName.charAt(i); // 当前字符
            char nc = (i < propertyName.length() - 1) ? propertyName
                    .charAt(i + 1) : (char) -1; // 下一个字符

            if (c == '_' && nc >= 'a' && nc <= 'z') {
                propertyNameBuffer.append(String.valueOf(nc).toUpperCase());
                i++;
            }
            else if (c != '_') {
                propertyNameBuffer.append(c);
            }
        }

        // 将"id"转化为"Id"
        propertyName = propertyNameBuffer.toString();

        return propertyName;
    }

    /**
     * 生成RowMapper的部分代码
     * 
     * @return
     */
    public String getRowMapping() {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < names.size(); i++) {
            String name = (String) names.get(i);

            int dataType = ((Integer) dataTypes.get(i)).intValue();

            sb.append(getSetMethod(generateJavaPropertyName(name)));
            sb.append("(rs.");
            sb.append(getJavaMethod(dataType));

            sb.append("(\"");
            sb.append(name);
            sb.append("\"));\n");
        }

        return sb.toString();
    }

    /**
     * 生成Insert的sql
     * 
     * @return
     */
    public String getAddSQL() {
        StringBuffer sb = new StringBuffer();
        sb.append(SQL_HEAD);
        sb.append("INSERT_");
        sb.append(entityName.toUpperCase());
        sb.append(" = \"INSERT INTO ");
        sb.append(tableName);
        sb.append("(");

        for (int i = 0; i < names.size(); i++) {
            String name = (String) names.get(i);
            @SuppressWarnings("unused")
            int dataType = ((Integer) dataTypes.get(i)).intValue();

            if (i > 0) {
                sb.append(",");
            }
            if (i == 3 || i == 10 || i == 17) {
                sb.append(NEWLINE);
            }
            sb.append(name);
        }

        sb.append(") ");
        sb.append(NEWLINE);
        sb.append("VALUES(");
        sb.append(getQuestionMark(names.size()));
        sb.append(")\";\n");

        return sb.toString();
    }

    /**
     * 生成delete的sql
     * 
     * @return
     */
    public String getRemoveSQL() {
        StringBuffer sb = new StringBuffer();
        sb.append(SQL_HEAD);
        sb.append("DELETE_");
        sb.append(entityName.toUpperCase());
        sb.append(" = \"DELETE FROM ");
        sb.append(tableName);
        sb.append(" WHERE id=?\";\n");
        return sb.toString();
    }

    /**
     * 生成update的sql
     * 
     * @return
     */
    public String getModifySQL() {
        StringBuffer sb = new StringBuffer();
        sb.append(SQL_HEAD);
        sb.append("UPDATE_");
        sb.append(entityName.toUpperCase());
        sb.append(" = \"UPDATE ");
        sb.append(tableName);
        sb.append(" SET ");

        int index = 0;
        for (int i = 0; i < names.size(); i++) {
            String name = (String) names.get(i);

            if (ID.equalsIgnoreCase(name)
                    || CREATIONTIME.equalsIgnoreCase(name)) {
                continue;
            }

            if (index > 0) {
                sb.append(",");
            }

            if (index == 2 || index == 9 || index == 16) {
                sb.append(NEWLINE);
            }
            sb.append(name);
            sb.append("=?");

            index++;
        }

        sb.append(" WHERE id=?\";\n");

        return sb.toString();
    }

    /**
     * 生成findById的sql
     * 
     * @return
     */
    public String getFindByIdSQL() {
        StringBuffer sb = new StringBuffer();
        sb.append(SQL_HEAD);
        sb.append("FIND_");
        sb.append(entityName.toUpperCase());
        sb.append("_BY_ID");
        sb.append(" = \"SELECT * FROM ");
        sb.append(tableName);
        sb.append(" WHERE id=?\";\n");
        return sb.toString();
    }

    /**
     * 生成findByIds的sql
     * 
     * @return
     */
    public String getFindByIdsSQL() {
        StringBuffer sb = new StringBuffer();
        sb.append(SQL_HEAD);
        sb.append("FIND_");
        sb.append(entityName.toUpperCase());
        sb.append("S_BY_IDS");
        sb.append(" = \"SELECT * FROM ");
        sb.append(tableName);
        sb.append(" WHERE id IN\";\n");
        return sb.toString();
    }

    /**
     * 生成insert的方法代码
     * 
     * @return
     */
    public String getAddMethod() {
        StringBuffer sb = new StringBuffer();
        sb.append("public void insert");
        sb.append(entityName);
        sb.append("(");
        sb.append(entityName);
        sb.append(" ");
        sb.append(initialToLowerCase(entityName));
        sb.append(") {\n\t");

        sb.append(getSetMethod("id"));
        sb.append("(createId());\n\t");
        sb.append(getSetMethod("creationtime"));
        sb.append("(new Date());\n\t");

        sb.append("update(SQL_INSERT_");
        sb.append(entityName.toUpperCase());
        sb.append(", new Object[] {");

        for (int i = 0; i < names.size(); i++) {
            String name = (String) names.get(i);

            name = generateJavaPropertyName(name);

            int dataType = ((Integer) dataTypes.get(i)).intValue();

            if (i > 0) {
                sb.append(",");
            }

            if (dataType == Types.INTEGER) {
                sb.append("new Integer(");
                sb.append(getGetMethod(name));
                sb.append(")");
            }
            else {
                sb.append(getGetMethod(name));
            }
        }

        sb.append("}, new int[] {");

        for (int i = 0; i < names.size(); i++) {
            @SuppressWarnings("unused")
            String name = (String) names.get(i);
            int dataType = ((Integer) dataTypes.get(i)).intValue();

            if (i > 0) {
                sb.append(",");
            }

            sb.append(getSQLType(dataType));
        }

        sb.append("});\n");
        sb.append("}\n");

        return sb.toString();
    }

    /**
     * 生成delete的方法代码
     * 
     * @return
     */
    public String getRemoveMethod() {
        StringBuffer sb = new StringBuffer();
        sb.append("public void delete");
        sb.append(entityName);
        sb.append("(String ");
        sb.append(initialToLowerCase(entityName));
        sb.append("Id");
        sb.append(") {\n\t");

        sb.append("update(SQL_DELETE_");
        sb.append(entityName.toUpperCase());
        sb.append(",");
        sb.append(initialToLowerCase(entityName));
        sb.append("Id);\n");
        sb.append("}\n");

        return sb.toString();
    }

    /**
     * 生成update的方法代码
     * 
     * @return
     */
    public String getModifyMethod() {
        StringBuffer sb = new StringBuffer();
        sb.append("public void update");
        sb.append(entityName);
        sb.append("(");
        sb.append(entityName);
        sb.append(" ");
        sb.append(initialToLowerCase(entityName));
        sb.append(") {\n\t");

        sb.append("update(SQL_UPDATE_");
        sb.append(entityName.toUpperCase());
        sb.append(", new Object[] {");

        int idIndex = -1;
        int creationTimeIndex = -1;

        int index = 0;
        for (int i = 0; i < names.size(); i++) {
            String name = (String) names.get(i);
            int dataType = ((Integer) dataTypes.get(i)).intValue();

            if (ID.equalsIgnoreCase(name)) {
                idIndex = i;
                continue;
            }

            if (CREATIONTIME.equalsIgnoreCase(name)) {
                creationTimeIndex = i;
                continue;
            }

            if (index > 0) {
                sb.append(",");
            }

            name = generateJavaPropertyName(name);

            if (dataType == Types.INTEGER) {
                sb.append("new Integer(");
                sb.append(getGetMethod(name));
                sb.append(")");
            }
            else {
                sb.append(getGetMethod(name));
            }

            index++;
        }

        sb.append(",");
        sb.append(getGetMethod(ID));
        sb.append("}, new int[] {");

        index = 0;
        for (int i = 0; i < names.size(); i++) {
            @SuppressWarnings("unused")
            String name = (String) names.get(i);
            int dataType = ((Integer) dataTypes.get(i)).intValue();

            if (i == idIndex || i == creationTimeIndex) {
                continue;
            }

            if (index > 0) {
                sb.append(",");
            }

            sb.append(getSQLType(dataType));

            index++;
        }

        sb.append(", Types.CHAR");
        sb.append("});\n");
        sb.append("}\n");

        return sb.toString();
    }

    /**
     * 生成findById的方法代码
     * 
     * @return
     */
    public String getFindByIdMethod() {
        StringBuffer sb = new StringBuffer();
        sb.append("public ");
        sb.append(entityName);
        sb.append(" find");
        sb.append(entityName);
        sb.append("ById(String ");
        sb.append(initialToLowerCase(entityName));
        sb.append("Id");
        sb.append(") {\n\t");

        sb.append("return (");
        sb.append(entityName);
        sb.append(") query(SQL_FIND_");
        sb.append(entityName.toUpperCase());
        sb.append("_BY_ID, ");
        sb.append(initialToLowerCase(entityName));
        sb.append("Id, new ");
        sb.append(entityName);
        sb.append("SingleReader());\n");
        sb.append("}\n");

        return sb.toString();
    }

    /**
     * 生成findByIds的方法代码
     * 
     * @return
     */
    public String getFindByIdsMethod() {
        StringBuffer sb = new StringBuffer();
        sb.append("public Map find");
        sb.append(entityName);
        sb.append("sById(String[] ");
        sb.append(initialToLowerCase(entityName));
        sb.append("Ids");
        sb.append(") {\n\t");

        sb.append("return (");
        sb.append(entityName);
        sb.append(") queryForInSQL(SQL_FIND_");
        sb.append(entityName.toUpperCase());
        sb.append("S_BY_IDS, ");
        sb.append(initialToLowerCase(entityName));
        sb.append("Ids, new ");
        sb.append(entityName);
        sb.append("MapReader());\n");
        sb.append("}\n");

        return sb.toString();
    }

    /**
     * 打印生成的所有代码
     */
    public void printCode() {
        System.out.println(getEntityCode());
        System.out.println(getRowMapping());

        System.out.println(getAddSQL());
        System.out.println(getRemoveSQL());
        System.out.println(getModifySQL());
        System.out.println(getFindByIdSQL());
        System.out.println(getFindByIdsSQL());

        System.out.println(getAddMethod());
        System.out.println(getRemoveMethod());
        System.out.println(getModifyMethod());
        System.out.println(getFindByIdMethod());
        System.out.println(getFindByIdsMethod());
    }

    public static void main(String[] args) {
        CodeBuilder builder = new CodeBuilder("ds_student", "Company");
        builder.printCode();
    }

}
