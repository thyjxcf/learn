/* 
 * @(#)ResultPack.java    Created on 2005-5-18
 * Copyright (c) 2005 ZDSoft.net, Inc. All rights reserved.
 * $Header: f:/44cvsroot/stusys/stusys/src/net/zdsoft/stusys/system/systemini/dto/ResultPack.java,v 1.2 2006/12/26 13:33:11 jiangl Exp $
 */
package net.zdsoft.eis.system.frame.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhangzq
 */
public class ResultPack {

    private String[] columnNames = null;
    private List<String[]> records = null;
    private Integer resultCode;

    public ResultPack() {
        records = new ArrayList<String[]>();
    }

    public void setColumnNames(String[] columnNames) {
        this.columnNames = columnNames;
    }

    public String[] getColumnNames() {
        return columnNames;
    }

    public List<String[]> getRecords() {
        return records;
    }

    public int getColumnCount() {
        return columnNames.length;
    }

    public void addRecord(String[] record) {
        if (columnNames.length == record.length) {
            records.add(record);
        }
    }

    public Integer getResultCode() {
        return resultCode;
    }

    public void setResultCode(Integer resultCode) {
        this.resultCode = resultCode;
    }

}
