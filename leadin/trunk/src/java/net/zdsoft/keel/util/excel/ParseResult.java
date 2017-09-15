/* 
 * @(#)ParseResult.java    Created on 2007-3-21
 * Copyright (c) 2007 ZDSoft Networks, Inc. All rights reserved.
 * $Id: ParseResult.java,v 1.1 2007/04/28 03:46:25 huangwj Exp $
 */
package net.zdsoft.keel.util.excel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 解析XLS文件所得的结果集类.
 * 
 * @author huangwj
 * @version $Revision: 1.1 $, $Date: 2007/04/28 03:46:25 $
 */
public class ParseResult {

    private List<String> errorList;
    private List<String> messageList;
    private Map<String, List<Object>> recordListMap;
    private Map<String, List<Map<String, Object>>> recordMapListMap;

    public ParseResult() {
        messageList = new ArrayList<String>();
        errorList = new ArrayList<String>();
        recordListMap = new LinkedHashMap<String, List<Object>>();
        recordMapListMap = new LinkedHashMap<String, List<Map<String, Object>>>();
    }

    public void addError(String error) {
        errorList.add(error);
    }

    public void addMessage(String message) {
        messageList.add(message);
    }

    /**
     * 添加工作表的记录值对象列表.
     * 
     * @param sheetName
     *            工作表名称
     * @param recordList
     *            记录值对象列表
     */
    public void addRecordList(String sheetName, List<Object> recordList) {
        recordListMap.put(sheetName, recordList);
    }

    public void addRecordMapList(String sheetName, List<Map<String, Object>> recordMapList) {
        recordMapListMap.put(sheetName, recordMapList);
    }

    public String[] getAllErrors() {
        return (String[]) errorList.toArray(new String[errorList.size()]);
    }

    public String[] getAllMessages() {
        return (String[]) messageList.toArray(new String[messageList.size()]);
    }

    // 暂缓未处理泛式
    public List<Object> getAllRecordList() {
        List<Object> allRecordList = new ArrayList<Object>();

        Collection<List<Object>> c = recordListMap.values();
        for (Iterator<List<Object>> iter = c.iterator(); iter.hasNext();) {
            List<Object> recordList = iter.next();
            allRecordList.addAll(recordList);
        }

        return allRecordList;
    }

    // 暂缓未处理泛式
    public List<Map<String, Object>> getAllRecordMapList() {
        List<Map<String, Object>> allRecordMapList = new ArrayList<Map<String, Object>>();

        Collection<List<Map<String, Object>>> c = recordMapListMap.values();
        for (Iterator<List<Map<String, Object>>> iter = c.iterator(); iter.hasNext();) {
            List<Map<String, Object>> recordMapList = iter.next();
            allRecordMapList.addAll(recordMapList);
        }

        return allRecordMapList;
    }

    /**
     * 获取所有工作表名称.
     * 
     * @return
     */
    public String[] getAllSheetNames() {
        String[] sheetNames = (String[]) recordListMap.keySet().toArray(
                new String[recordListMap.size()]);
        if (sheetNames.length == 0) {
            sheetNames = (String[]) recordMapListMap.keySet().toArray(
                    new String[recordMapListMap.size()]);
        }

        return sheetNames;
    }

    /**
     * 获取工作表中的记录值对象列表.
     * 
     * @param sheetName
     *            工作表名称
     * @return
     */
    public List<Object> getRecordList(String sheetName) {
        return recordListMap.get(sheetName) == null ? new ArrayList<Object>()
                : recordListMap.get(sheetName);
    }

    public List<Map<String, Object>> getRecordMapList(String sheetName) {
        return recordMapListMap.get(sheetName) == null ? new ArrayList<Map<String, Object>>()
                : recordMapListMap.get(sheetName);
    }

    public boolean hasErrors() {
        return !errorList.isEmpty();
    }

    public boolean hasMessages() {
        return !messageList.isEmpty();
    }

    public boolean hasRecordList() {
        return !recordListMap.isEmpty();
    }

    public boolean hasRecordMapList() {
        return !recordMapListMap.isEmpty();
    }

    public void removeError(String error) {
        errorList.remove(error);
    }

    public void removeMessage(String message) {
        messageList.remove(message);
    }

    public void removeRecordList(String sheetName) {
        recordListMap.remove(sheetName);
    }

    public void removeRecordMapList(String sheetName) {
        recordMapListMap.remove(sheetName);
    }

}
