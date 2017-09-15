/* 
 * @(#)ChinaExcelRow.java    Created on 2005-11-9
 * Copyright (c) 2005 ZDSoft Networks, Inc. All rights reserved.
 * $Header$
 */
package net.zdsoft.eis.frame.chinaexcel;

import java.util.ArrayList;
import java.util.List;

import org.dom4j.Element;
import org.dom4j.dom.DOMElement;

public class ChinaExcelRow {
    private Element row;
    
    /**
     * 报表标题，无边框
     */
    public static final int ROWTYPE_TITLE = 1;
    /**
     * 报表行头，有背景边框
     */
    public static final int ROWTYPE_HEADER = 2;
    /**
     * 整行合并，无边框
     */
    public static final int ROWTYPE_UNITE = 3;
    /**
     * 正常行，无背景有边框
     */
    public static final int ROWTYPE_NORMAL = 4;

    /**
     * 
     * @param aRow
     *            第几行
     * @param cols
     *            共几列
     * @param rowType
     *            行类型
     */
    @SuppressWarnings("unchecked")
    public ChinaExcelRow(int aRow, int cols,List data, int rowType) {
        row = new DOMElement("Row" + aRow);
        row.addElement("Cou").addText(String.valueOf(cols));
        for (int i = 1; i <= cols; i++) {
            Element cell = row.addElement("CL" + i);
            cell.addElement("Row").addText(String.valueOf(aRow));

            switch (rowType) {
            case ROWTYPE_TITLE:
                if (i == 1) {
                    cell.addElement("Cols").addText(String.valueOf(cols - 1));
                    cell.addElement("hAg").addText("6");
                    cell.addElement("vAg").addText("6");
                    if(data != null && data.size() > 0)
                        cell.addElement("t").addText((String)data.get(0));
                }else{
                    cell.addElement("Rows").addText("-"+aRow);
                    cell.addElement("Cols").addText("-1");
                }
                //题头的字体设置
                cell.addElement("FtId").addText("2"); // 题头的字体设置
                break;
            case ROWTYPE_UNITE:
                if (i == 1) {
                    cell.addElement("Cols").addText(String.valueOf(cols - 1));
                    cell.addElement("hAg").addText("6");
                    cell.addElement("vAg").addText("6");
                    if(data != null && data.size() > 0)
                        cell.addElement("t").addText((String)data.get(0));
                }else{
                    cell.addElement("Rows").addText("-"+aRow);
                    cell.addElement("Cols").addText("-1");
                }
                //字体设置
                cell.addElement("FtId").addText("3");
                break;
            case ROWTYPE_HEADER:
                // 设边框
                cell.addElement("TPenId").addText("0");
                cell.addElement("BPenId").addText("0");
                cell.addElement("LPenId").addText("0");
                cell.addElement("RPenId").addText("0");
                // 题头的背景色设置
                cell.addElement("BrId").addText("0");
                //字体设置
                cell.addElement("FtId").addText("3");
                if(data != null && data.size() >= i)
                    cell.addElement("t").addText((String)data.get(i - 1));                
                break;
            case ROWTYPE_NORMAL:
                // 设边框
                cell.addElement("TPenId").addText("0");
                cell.addElement("BPenId").addText("0");
                cell.addElement("LPenId").addText("0");
                cell.addElement("RPenId").addText("0");
                //字体设置
                cell.addElement("FtId").addText("3");
                if(data != null && data.size() >= i)
                    cell.addElement("t").addText((String)data.get(i - 1));                
                break;
            }
        }
    }
    
    public Element getRow(){
        return row;
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
		List<String> data2 = new ArrayList<String>();
		data2.add("班级");
		data2.add("人数");
		data2.add("最高分");
		data2.add("最低分");
		data2.add("60以下");
		
		ChinaExcelRow cr = new ChinaExcelRow(1,5,data2,ROWTYPE_TITLE);
		System.out.println(cr.getRow().toString());

    }

}
