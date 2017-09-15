/* 
 * @(#)CodeBuilder.java    Created on 2005-10-12
 * Copyright (c) 2005 ZDSoft.net, Inc. All rights reserved.
 * $Header: f:/44cvsroot/stusys/stusys/src/net/zdsoft/stusys/system/util/CodeBuilder.java,v 1.2 2007/01/04 11:22:57 songzc Exp $
 */
package temptools;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;

import javax.sql.DataSource;

import org.apache.commons.lang.StringUtils;
import org.springframework.context.support.FileSystemXmlApplicationContext;

/**
 * @author liangxiao
 * @version $Revision: 1.2 $, $Date: 2007/01/04 11:22:57 $
 */
public class BaseCodeBuilder {

    private static final String NEWLINE = "\"\n\t + \"";
    private static final String SQL_HEAD = "private static final String SQL_";

    private static final String ID = "id";
    private static final String CREATIONDATE = "creationTime";

    protected String tableName;
    protected String entityName;

    protected ArrayList<String> fields = new ArrayList<String>();
    protected ArrayList<Integer> dataTypes = new ArrayList<Integer>();

    boolean hasId = false;
    boolean hasCreationDate = false;
    private String defaultConfigFilePath ="/web/WEB-INF/classes/conf/spring/applicationContext.xml";
    public  BaseCodeBuilder(){
        
    }
    public  BaseCodeBuilder(String configFilePath,String tableName, String entityName){
        if (configFilePath.equals("")){
            configFilePath=defaultConfigFilePath;
        }else{
            defaultConfigFilePath = configFilePath;
        }
        
        tableName = tableName.toUpperCase();
        this.tableName = tableName.toLowerCase();
        this.entityName = entityName;

        FileSystemXmlApplicationContext ctx = new FileSystemXmlApplicationContext(
                defaultConfigFilePath);
        DataSource dataSource = (DataSource) ctx.getBean("dataSource");

        try {
            Connection conn = dataSource.getConnection();
            DatabaseMetaData dmd = conn.getMetaData();

//             ResultSet rs = conn.getMetaData().getColumns(null,
//             dmd.getUserName(), tableName, "%");

            ResultSet rs = conn.getMetaData().getColumns(null, dmd.getUserName(), tableName,
                    "%");

            while (rs.next()) {
                String name = rs.getString("COLUMN_NAME").toLowerCase();
                int dataType = rs.getInt("DATA_TYPE");

                fields.add(name);
                dataTypes.add(new Integer(dataType));

                if (ID.equalsIgnoreCase(name)) {
                    hasId = true;
                }
                if (CREATIONDATE.equalsIgnoreCase(name)) {
                    hasCreationDate = true;
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
    public BaseCodeBuilder(String tableName, String entityName) {
        this("",tableName,entityName);
    }

    private String getJavaType(int dataType) {
        if (dataType == Types.INTEGER || dataType == Types.DECIMAL) {
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
        if (dataType == Types.INTEGER || dataType == Types.DECIMAL) {
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
        if (dataType == Types.INTEGER || dataType == Types.DECIMAL) {
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

    private String initialToUpperCase(String str) {
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }

    protected String initialToLowerCase(String str) {
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

    public String getEntityCode() {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < fields.size(); i++) {
            String name = fields.get(i);

            // 设置Entity的属性名称, 更符合Java的命名规则
            name = generateJavaPropertyName(name);

            int dataType = dataTypes.get(i).intValue();

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
        if (StringUtils.isBlank(fieldName)) {
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

    public String getRowMapping() {
        StringBuffer sb = new StringBuffer();
        sb.append("public ");
        sb.append(entityName);
        sb.append(" setField(ResultSet rs) throws SQLException {\n");
        sb.append("    " + entityName + " " + initialToLowerCase(entityName)
                + " = new " + entityName + "();\n");      
        
        for (int i = 0; i < fields.size(); i++) {
            String name = fields.get(i);

            int dataType = dataTypes.get(i).intValue();
            sb.append("    ");
            sb.append(getSetMethod(generateJavaPropertyName(name)));
            sb.append("(rs.");
            sb.append(getJavaMethod(dataType));

            sb.append("(\"");
            sb.append(name);
            sb.append("\"));\n");
        }
        sb.append("    return " + initialToLowerCase(entityName) + ";\n");       
        sb.append("}\n");
        
        return sb.toString();
    }

    public String getAddSQL() {
        StringBuffer sb = new StringBuffer();
        sb.append(SQL_HEAD);
        sb.append("INSERT_");
        sb.append(entityName.toUpperCase());
        sb.append(" = \"INSERT INTO ");
        sb.append(tableName);
        sb.append("(");

        for (int i = 0; i < fields.size(); i++) {
            String name = fields.get(i);
//            int dataType = dataTypes.get(i).intValue();

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
        sb.append(getQuestionMark(fields.size()));
        sb.append(")\";\n");

        return sb.toString();
    }

    public String getRemoveSQL() {
        StringBuffer sb = new StringBuffer();
        sb.append(SQL_HEAD);
        sb.append("DELETE_");
        sb.append(entityName.toUpperCase());
        sb.append(" = \"UPDATE ");
        sb.append(tableName);
        sb.append(" SET is_deleted = 1 WHERE id=?\";\n");
        return sb.toString();
    }

    public String getRemovesSQL() {
        StringBuffer sb = new StringBuffer();
        sb.append(SQL_HEAD);
        sb.append("DELETE_");
        sb.append(entityName.toUpperCase());
        sb.append("_BY_IDS = \"UPDATE ");
        sb.append(tableName);
        sb.append(" SET is_deleted = 1 WHERE id IN \";\n");
        return sb.toString();
    }
    
    public String getModifySQL() {
        StringBuffer sb = new StringBuffer();
        sb.append(SQL_HEAD);
        sb.append("UPDATE_");
        sb.append(entityName.toUpperCase());
        sb.append(" = \"UPDATE ");
        sb.append(tableName);
        sb.append(" SET ");

        int index = 0;
        for (int i = 0; i < fields.size(); i++) {
            String name = fields.get(i);

            if (ID.equalsIgnoreCase(name)
                    || CREATIONDATE.equalsIgnoreCase(name)) {
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

    public String getFindSQL() {
        StringBuffer sb = new StringBuffer();
        sb.append(SQL_HEAD);
        sb.append("FIND_");
        sb.append(entityName.toUpperCase());
        sb.append("S = \"SELECT * FROM ");
        sb.append(tableName);
        sb.append(" WHERE is_deleted = 0\";\n");
        return sb.toString();
    }

    public String getFindByIdsSQL() {
        StringBuffer sb = new StringBuffer();
        sb.append(SQL_HEAD);
        sb.append("FIND_");
        sb.append(entityName.toUpperCase());
        sb.append("S_BY_IDS");
        sb.append(" = \"SELECT * FROM ");
        sb.append(tableName);
        sb.append(" WHERE is_deleted = 0 AND id IN\";\n");
        return sb.toString();
    }

    public String getAddMethod() {
        StringBuffer sb = new StringBuffer();
        sb.append("public void add");
        sb.append(entityName);
        sb.append("(");
        sb.append(entityName);
        sb.append(" ");
        sb.append(initialToLowerCase(entityName));
        sb.append(") {\n\t");

        sb.append(getSetMethod("id"));
        sb.append("(createId());\n\t");
        sb.append(getSetMethod("creationTime"));
        sb.append("(new Date());\n\t");

        sb.append("update(SQL_INSERT_");
        sb.append(entityName.toUpperCase());
        sb.append(", new Object[] {");

        for (int i = 0; i < fields.size(); i++) {
            String name = fields.get(i);

            name = generateJavaPropertyName(name);

            int dataType = dataTypes.get(i).intValue();

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

        for (int i = 0; i < fields.size(); i++) {
//            String name = fields.get(i);
            int dataType = dataTypes.get(i).intValue();

            if (i > 0) {
                sb.append(",");
            }

            sb.append(getSQLType(dataType));
        }

        sb.append("});\n");
        sb.append("}\n");

        return sb.toString();
    }

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

    public String getRemovesMethod() {
        StringBuffer sb = new StringBuffer();
        sb.append("public void delete");
        sb.append(entityName);
        sb.append("(String[] ");
        sb.append(initialToLowerCase(entityName));
        sb.append("Ids");
        sb.append(") {\n\t");

        sb.append("updateForInSQL(SQL_DELETE_");
        sb.append(entityName.toUpperCase());
        sb.append("_BY_IDS, null, ");
        sb.append(initialToLowerCase(entityName));
        sb.append("Ids);\n");
        sb.append("}\n");

        return sb.toString();
    }
    
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
        int creationDateIndex = -1;

        int index = 0;
        for (int i = 0; i < fields.size(); i++) {
            String name = fields.get(i);
            int dataType = dataTypes.get(i).intValue();

            if (ID.equalsIgnoreCase(name)) {
                idIndex = i;
                continue;
            }

            if (CREATIONDATE.equalsIgnoreCase(name)) {
                creationDateIndex = i;
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
        for (int i = 0; i < fields.size(); i++) {
//            String name = fields.get(i);
            int dataType = dataTypes.get(i).intValue();

            if (i == idIndex || i == creationDateIndex) {
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

    public String getFindByIdMethod() {
        StringBuffer sb = new StringBuffer();
        sb.append("public ");
        sb.append(entityName);
        sb.append(" get");
        sb.append(entityName);
        sb.append("(String ");
        sb.append(initialToLowerCase(entityName));
        sb.append("Id");
        sb.append(") {\n\t");

        sb.append("return ");
        sb.append("query(SQL_FIND_");
        sb.append(entityName.toUpperCase());
        sb.append("_BY_ID, ");
        sb.append(initialToLowerCase(entityName));
        sb.append("Id, new ");
//        sb.append(entityName);
        sb.append("SingleRow());\n");
        sb.append("}\n");

        return sb.toString();
    }

    public String getFindByIdsMethod() {
        StringBuffer sb = new StringBuffer();
        sb.append("public Map<String, "+entityName+"> get");
        sb.append(entityName);
        sb.append("Map(String[] ");
        sb.append(initialToLowerCase(entityName));
        sb.append("Ids");
        sb.append(") {\n\t");

        sb.append("return ");
        sb.append("queryForInSQL(SQL_FIND_");
        sb.append(entityName.toUpperCase());
        sb.append("S_BY_IDS,null, ");
        sb.append(initialToLowerCase(entityName));
        sb.append("Ids, new ");
//        sb.append(entityName);
        sb.append("MapRow());\n");
        sb.append("}\n");

        return sb.toString();
    }

    public String getFindListMethod() {
        StringBuffer sb = new StringBuffer();
        sb.append("public List<");
        sb.append(entityName);
        sb.append("> get");
        sb.append(entityName);
        sb.append("s(");
        sb.append(") {\n\t");
        sb.append("return ");
        sb.append("query(SQL_FIND_");
        sb.append(entityName.toUpperCase());
        sb.append("S, ");
        sb.append("null,");// new String[] {}
        sb.append("null");// new int[] {}
        sb.append(", new ");
//        sb.append(entityName);
        sb.append("MultiRow());\n");
        sb.append("}\n");

        return sb.toString();
    }

    public String getSingleRowMapping() {
        StringBuffer sb = new StringBuffer();
        sb.append("private static class " + entityName
                + "SingleRowMapper implements  SingleRowMapper {\n");
        sb.append("public Object mapRow(ResultSet rs) throws SQLException {\n");
        sb.append("        return setField(rs);\n");
        sb.append("   }\n }\n");
        return sb.toString();
    }

    public String getMapRowMapper() {
        StringBuffer sb = new StringBuffer();
        sb.append("private static class " + entityName
                + "MapRowMapper implements  MapRowMapper {\n");
        sb
                .append("  public Object mapRowKey(ResultSet rs, int rowNum) throws SQLException { \n");
        sb.append("    return rs.getString(\"id\");\n");
        sb.append("  }\n");

        sb
                .append("  public Object mapRowValue(ResultSet rs, int numRow) throws SQLException {\n");
        sb.append("        return setField(rs);\n");
        sb.append("   }\n }\n");
        return sb.toString();
    }

    public String getMultiRowMapping() {
        StringBuffer sb = new StringBuffer();
        sb.append("private static class " + entityName
                + "MultiRowMapper implements  MultiRowMapper {\n");
        sb
                .append("  public Object mapRow(ResultSet rs, int numRow) throws SQLException {\n");
        
        sb.append("        return setField(rs);\n");
        sb.append("   }\n }\n");
        return sb.toString();
    }

    /* start bean to print */
    /**
     * 打印BEAN方法:FindById
     * @return
     */
    public String getFindByIdMethodForBean() {
        StringBuffer sb = new StringBuffer();
        sb.append("public ");
        sb.append(entityName);
        sb.append(" get");
        sb.append(entityName);
        sb.append("(String ");
        sb.append(initialToLowerCase(entityName));
        sb.append("Id");
        sb.append(") {\n\t");

        sb.append("return ");
        sb.append(initialToLowerCase(entityName));
        sb.append("Dao.");
        sb.append("get");
        sb.append(entityName);
        sb.append("(");
        sb.append(initialToLowerCase(entityName));
        sb.append("Id);\n");
        sb.append("}\n");
        return sb.toString();
    }

    /**
     * 打印BEAN方法:FindByList
     * @return
     */
    public String getFindListMethodForBean() {
        StringBuffer sb = new StringBuffer();
        sb.append("public List<" + entityName + "> get" + entityName
                + "s(Pagination page){ \n");
        sb.append("      List<" + entityName + "> "
                + initialToLowerCase(entityName) + "s = "
                + initialToLowerCase(entityName) + "Dao.get" + entityName
                + "s();\n");
        sb.append("      " + initialToLowerCase(entityName)
                + "s = ArrayUtils.subList(" + initialToLowerCase(entityName)
                + "s, page);\n");
        sb.append(" return " + initialToLowerCase(entityName) + "s; \n  }\n");
        return sb.toString();
    }

    public String getDAOForBean() {
        StringBuffer sb = new StringBuffer();
        sb.append("private "+entityName + "Dao " + initialToLowerCase(entityName)
                + "Dao; \n");
        return sb.toString();
    }

    /* start springConfigure to print */
    /**
     * 打印Spring配置语句
     * @return
     */
    public String getBeanConfigure() {
        StringBuffer sb = new StringBuffer();
        sb.append("<bean id=\"" + initialToLowerCase(entityName)
                + "Service\" class=\"net.zdsoft.eis.base.service.impl." + entityName
                + "ServiceImpl\" />\n");
//        sb.append("  <property name=\"" + initialToLowerCase(entityName)
//                + "Dao\">\n");
//        sb.append("     <ref local=\"" + initialToLowerCase(entityName)
//                + "Dao\" />\n");
//        sb.append("  </property>\n");
//        sb.append("</bean>\n");
        return sb.toString();
    }

    /**
     * 打印DAO配置
     * @return
     */
    public String getDAOConfigure() {
        StringBuffer sb = new StringBuffer();
        sb.append("<bean id=\"" + initialToLowerCase(entityName)
                + "Dao\" class=\"net.zdsoft.eis.base.dao." + entityName
                + "DaoImpl\" parent=\"baseDao\" />\n");
        return sb.toString();
    }

    /* start action to print */
    /**
     * 打印ACTION语句
     * @return
     */
    public String getStartAction() {
        StringBuffer sb = new StringBuffer();
        sb.append(entityName + "Bean " + initialToLowerCase(entityName)
                + "Bean;\n");
        sb.append("try{\n");
        sb.append("  " + initialToLowerCase(entityName) + "Bean = ("
                + entityName + "Bean)this.getBean(\""
                + initialToLowerCase(entityName) + "Bean\");\n");
        sb.append("}catch(Exception e){\n");
        sb.append("  return INPUT;\n");
        sb.append("}\n");
        return sb.toString();
    }

    public void printCode() {
        System.out.println(getEntityCode());        
        
        System.out.println(getAddSQL());
//        System.out.println(getRemoveSQL());
        System.out.println(getRemovesSQL());
        System.out.println(getModifySQL());
        System.out.println(getFindByIdSQL());
        System.out.println(getFindByIdsSQL());
        System.out.println(getFindSQL());
        
        System.out.println(getRowMapping());
//        System.out.println(getMultiRowMapping());
//        System.out.println(getMapRowMapper());
//        System.out.println(getSingleRowMapping());
        
        System.out.println(getAddMethod());
//        System.out.println(getRemoveMethod());
        System.out.println(getRemovesMethod());
        System.out.println(getModifyMethod());
        System.out.println(getFindByIdMethod());
        System.out.println(getFindByIdsMethod());        
        System.out.println(getFindListMethod());

//        System.out.println("/*start bean to print*/");

//        System.out.println(getDAOForBean());
//        System.out.println(getFindByIdMethodForBean());
//        System.out.println(getFindListMethodForBean());

//        System.out.println("/*start springConfigure to print*/");

//        System.out.println(getBeanConfigure());
//        System.out.println(getDAOConfigure());

    }

    public static void main(String[] args) {
        BaseCodeBuilder builder = new BaseCodeBuilder("base_student",
                "BaseStudent");
        
        builder.printCode();
    }

}
