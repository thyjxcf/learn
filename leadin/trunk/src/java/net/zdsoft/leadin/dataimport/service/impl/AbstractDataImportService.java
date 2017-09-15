/* 
 * @(#)DataImportServiceImpl.java    Created on 2008-10-27
 * Copyright (c) 2008 ZDSoft Networks, Inc. All rights reserved.
 * $Id: DataImportServiceImpl.java 25035 2008-10-27 11:23:11Z zhaosf $
 */
package net.zdsoft.leadin.dataimport.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.zdsoft.keel.util.DateUtils;
import net.zdsoft.leadin.dataimport.core.ImportData;
import net.zdsoft.leadin.dataimport.core.ImportObject;
import net.zdsoft.leadin.dataimport.core.ImportObjectNode;
import net.zdsoft.leadin.dataimport.param.DataImportParam;
import net.zdsoft.leadin.dataimport.service.DataImportService;
import net.zdsoft.leadin.util.GetValueMethod;

/**
 * @author zhaosf
 * @version $Revision: 25035 $, $Date: 2008-10-27 19:23:11 +0800 $
 */
public abstract class AbstractDataImportService implements DataImportService {
    private Logger log = LoggerFactory.getLogger(AbstractDataImportService.class);

    public List<ImportObjectNode> getDynamicFields(DataImportParam param) {
        List<ImportObjectNode> list = new ArrayList<ImportObjectNode>();
        return list;
    }

    @Override
	public Map<String, Map<String, String>> getConstraintFields(DataImportParam param) {
		return new HashMap<String, Map<String,String>>();
	}

	@Override
    public Set<String> getFilterDefineFields() {
        return new HashSet<String>();
    }
    
    @Override
    public List<ImportObjectNode> getRedefineFields(Map<String, ImportObjectNode> nodeMap, DataImportParam param) {
        return null;
    }

    protected Method[] getMethods(ImportObject importObject, String[] cols, Object instance) {
        Method[] methods = new Method[cols.length];
        for (int i = 0; i < cols.length; i++) {
        	String col=cols[i];
			if(StringUtils.isNotBlank(col)){
				col =col.replace("*", "");
			}
        	ImportObjectNode node = importObject.getMapOfDefineObjectNode().get(col);
            methods[i] = GetValueMethod.getMethod(instance, node.getName(), null);
        }
        return methods;
    }

    /**
     * 反射
     * 
     * @param obj
     * @param methods
     * @return
     */
    protected String[] getRowValue(Object obj, Method[] methods) {
        String[] rowValue = new String[methods.length];
        for (int i = 0; i < methods.length; i++) {
            try {
                rowValue[i] = methods[i].invoke(obj, new Object[] {}).toString();
            } catch (IllegalArgumentException e) {
                log.error(e.toString());
            } catch (IllegalAccessException e) {
                log.error(e.toString());
            } catch (InvocationTargetException e) {
                log.error(e.toString());
            } catch (Exception e) {
                // log.error(e.toString());
            }
        }
        return rowValue;
    }

    /**
     * 处理字段错误
     * 
     * @param colMap
     * @param defineList
     * @param oriDatas
     * @param colNames
     * @param errors
     * @param errorList
     */
    private void disposeError(Map<String, Integer> colMap, List<String> defineList,
            String[] oriDatas, String[] colNames, String[] errors, List<String[]> errorList) {
        int len = oriDatas.length;
        String errorCol = oriDatas[len - 2];// 错误列
        String errorValue = oriDatas[len - 1];// 错误信息
        if (null == errorCol)
            errorCol = "";
        if (null == errorValue)
            errorValue = "";

        for (int i = 0; i < colNames.length; i++) {
            int colNum = colMap.get(colNames[i]);// 列号
            errorCol += "|" + colNum + "|";
            errorValue += "【" + defineList.get(colNum) + "】列值" + errors[i];
        }

        oriDatas[len - 2] = errorCol;
        oriDatas[len - 1] = errorValue;

        errorList.add(oriDatas);
    }

    protected void disposeError(ImportData importData, int i, String colName, String error) {
        if (StringUtils.isEmpty(colName))
            return;

        if (StringUtils.isEmpty(error))
            return;
        disposeError(importData, i, new String[] { colName }, new String[] { error });
    }

    protected void disposeError(ImportData importData, int i, String[] colNames, String[] errors) {
        // 导入文件中的原数据
        Map<Integer, String[]> mapOfOriData = importData.getOriDataMap();
        String[] oriDatas = mapOfOriData.get(i);// 原始数据

        // 导入列信息
        List<String> listOfDefines = importData.getFileDefineList();// 取出导入列中文名称
        Map<String, Integer> mapOfName = importData.getColNumMap();// 字段所在列号

        // 错误的list
        List<String[]> listOfErrorImportData = importData.getErrorDataList();

        disposeError(mapOfName, listOfDefines, oriDatas, colNames, errors, listOfErrorImportData);
    }

    protected String getValueForSQL(ImportObjectNode node, String inValue) {
        String outValue = null;
        String type = node.getType();
        // 数值
        if (type.equalsIgnoreCase("Integer") || type.equalsIgnoreCase("Long")
                || type.indexOf("Numeric") == 0) {
            if (StringUtils.isNotBlank(inValue)) {
                outValue = inValue;
            }
            // 日期
        } else if (type.equalsIgnoreCase("Date")) {
            if (StringUtils.isNotBlank(inValue)) {
                outValue = getFormattedDateByDayForSQL(inValue);
            }
            // 字符串
        } else {
            if (StringUtils.isNotBlank(inValue)) {
                outValue = "'" + inValue + "'";
            }
        }
        return outValue;
    }

    /**
     * 仅支持 yyyy-MM-dd 格式的字符串日期
     * 
     * @param dateStr
     * @return
     */
    protected static String getFormattedDateByDayForSQL(String dateStr) {
        return "to_date('" + dateStr + "','yyyy-mm-dd')";
    }

    protected static String getFormattedTimeForSql(Date date) {
        String formattedTime = "to_date('" + DateUtils.date2StringBySecond(date)
                + "','yyyy-mm-dd hh24:mi:ss')";
        return formattedTime;
    }

    protected static String getFormattedTimeForSql() {
        return getFormattedTimeForSql(new Date());
    }
}
