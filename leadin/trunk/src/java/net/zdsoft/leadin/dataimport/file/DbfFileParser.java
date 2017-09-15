/* 
 * @(#)DbfFileParser.java    Created on Apr 29, 2010
 * Copyright (c) 2010 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.leadin.dataimport.file;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import com.linuxense.javadbf.DBFField;
import com.linuxense.javadbf.DBFReader;

import net.zdsoft.keel.action.Reply;
import net.zdsoft.leadin.dataimport.core.ImportData;
import net.zdsoft.leadin.dataimport.file.ImportFileParserParam.InParam;
import net.zdsoft.leadin.dataimport.file.ImportFileParserParam.OutParam;
import net.zdsoft.leadin.dataimport.param.DataImportDisposeParam;
import net.zdsoft.leadin.dataimport.param.DataImportParam;

/**
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: Apr 29, 2010 10:30:05 AM $
 */
public class DbfFileParser implements ImportFileParser {
    public static int COUNT_BY_PER_TIME = ImportData.COUNT_BY_PER_TIME;// 一次处理的记录数
        
    public OutParam parseFile(InParam inParam) throws Exception {
        ImportFileParserParam.OutParam outParam = (new ImportFileParserParam())
                .getOutParamInstance();
        
        //参数
        String importFile = inParam.getImportFile();
        boolean batchImport = inParam.isBatchImport();
        DataImportParam param = inParam.getParam();
        DataImportDisposeParam disposeParam = param.getDisposeParam();
        Reply reply = inParam.getReply();
        
        List<String[]> rowDatas = new ArrayList<String[]>();// 数据
        String[] fields = null;// 列标题

        InputStream fis = null;
        try {
            fis = new FileInputStream(importFile);

            // 根据输入流初始化一个DBFReader实例，用来读取DBF文件信息
            DBFReader reader = new DBFReader(fis);
            reader.setCharactersetName("GBK");

            // 调用DBFReader对实例方法得到path文件中字段的个数
            int fieldsCount = reader.getFieldCount();
            fields = new String[fieldsCount];

            // 取出字段信息
            for (int i = 0; i < fieldsCount; i++) {
                DBFField field = reader.getField(i);
                fields[i] = field.getName().toLowerCase();
            }

            List<int[]> perData = null;
            if (null == disposeParam.getBatches()) {
                perData = new ArrayList<int[]>();            
                int recordCnt = reader.getRecordCount();  //记录数
                if(recordCnt < 0){
                    throw new Exception("不是有效的文件，请确认······");
                }
                if(batchImport){
                    int cntByPerTime = COUNT_BY_PER_TIME;

                    int batchCnt = recordCnt / cntByPerTime ;
                    int i = 0;
                    for (i = 0; i < batchCnt; i++) {
                        perData.add(new int[]{i * cntByPerTime +1,(i+1) * cntByPerTime}); 
                    }

                    int tmp = recordCnt % cntByPerTime ;
                    if(tmp > 0){
                        perData.add(new int[]{i * cntByPerTime +1,recordCnt}); 
                    }

                }else{
                    perData.add(new int[]{1,recordCnt}); 
                }
                disposeParam.setBatches(perData);
            }else{
                perData = disposeParam.getBatches();
            }

            //当前段
            int currentSec = disposeParam.getCurrentBatch();
            disposeParam.setCurrentBatch(currentSec + 1);

            //是否需要重复
            boolean needCycle = true;
            if(currentSec == perData.size() -1){
                needCycle = false;
            }else{
                needCycle = true;
            }
            disposeParam.setNeedCycle(needCycle);
            //测试
//          if(currentSec == 1){
//            disposeParam.setNeedCycle(false);
//          }


            if(needCycle || (!needCycle && currentSec > 0)){
                reply.addActionMessage("正在处理第" + (currentSec + 1) + "批（每批"
                        + COUNT_BY_PER_TIME + "条）数据······");
            }

            int[] sec = perData.get(currentSec); 
            int begin = sec[0];
            int end = sec[1];

            int counter = 0;//计数
            // 一条条取出path文件中记录
            Object[] rowValues;
            while ((rowValues = reader.nextRecord()) != null) {
                counter ++;
                if(counter >= begin && counter <= end){
                    String[] row = new String[rowValues.length];
                    for (int i = 0; i < rowValues.length; i++) {
                        if(null == rowValues[i]){
                            row[i] = "";
                        }else{
                            row[i] = rowValues[i].toString().trim();
                        }
                    }
                    rowDatas.add(row);
                }
            }            
        }finally {
            try {
                fis.close();
            }catch (Exception e) {                
            }
        }

        outParam.setFields(fields);
        outParam.setRowDatas(rowDatas);
        return outParam;
    }

}
