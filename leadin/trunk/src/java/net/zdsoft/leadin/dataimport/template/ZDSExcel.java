package net.zdsoft.leadin.dataimport.template;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
//import org.apache.poi.hssf.util.Region;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.util.CellRangeAddress;

/**
 * <p>
 * Title: 操作Excel的通用类
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2004
 * </p>
 * <p>
 * Company: ZDsoft
 * </p>
 * 
 * @author yehr, lvl
 * @version 1.0
 */

public class ZDSExcel {
    protected HSSFWorkbook wb = null;
    protected HSSFSheet sheet = null;
    protected HSSFRow row = null;
    protected HSSFCell cell = null;
    protected FileInputStream fis = null;

    public ZDSExcel() {
    }

    /**
     * 创建workbook
     * 
     * @return 返回创建的workbook
     */
    public HSSFWorkbook createWB() {
        wb = new HSSFWorkbook();
        return wb;
    }

    /**
     * 创建workbook
     * 
     * @param template 模板（完全路径）
     * @return 返回创建的workbook
     */
    public HSSFWorkbook createWB(String template) throws IOException {
        fis = new FileInputStream(template);
        wb = new HSSFWorkbook(fis);
        return wb;
    }

    /**
     * 创建sheet。
     * 
     * @param sheetName sheet名称
     * @return 返回创建的sheet
     */
    public HSSFSheet createSheet(String sheetName) {
        sheet = wb.createSheet(sheetName);
        return sheet;
    }

    /**
     * 修改sheet名称。
     * 
     * @sheet index sheet编号
     * @param sheetName sheet名称
     */
    public void createSheet(int sheetIndex, String sheetName) {
    	sheetName = StringUtils.replaceEach(sheetName, new String[]{"\\","/","?","*","[","]"}, new String[]{"","","","","",""});
        if (StringUtils.EMPTY.equals(sheetName)) {
			sheetName = " ";
		}
    	wb.setSheetName(sheetIndex, sheetName);
    }

    public HSSFSheet getSheet(String sheetName) {
        sheet = wb.getSheet(sheetName);
        return sheet;
    }

    public HSSFRow createRow(int rowNum) {
        row = sheet.createRow(rowNum);
        return row;
    }

    public HSSFRow getRow(int rowNum) {
        row = sheet.getRow(rowNum);
        if (row == null)
            createRow(rowNum);
        return row;
    }

    public HSSFCell createCell(int col) {
        cell = row.createCell(col);
        return cell;
    }

    public HSSFCell getCell(int col) {
        cell = row.getCell(col);
        if (cell == null)
            createCell(col);
        return cell;
    }

    public int getLastCellNum(int rownum) {
        for (int i = 0; i < 9999; i++) {
            String val = this.getValue(rownum, (short) i);
            if (val == null) {
                val = "";
            } else {
                val = val.trim();
            }
            if (val.equals(""))
                return i;
        }
        return 0;
    }

    public String getValue(int rownum, short colnum) {
        getRow(rownum);
        getCell(colnum);
        return cell.getStringCellValue();
    }

    /**
     * 设置单元格的值，如果为Null或空串设置为“-”
     * 
     * @param rowNum int 行
     * @param colNum short 列
     * @param value String 值
     */
    public void setCellValue(int rowNum, short colNum, String value) {
        getRow(rowNum);
        getCell(colNum);
//        cell.setCellType(HSSFCell.CELL_TYPE_STRING);
        cell.setCellType(CellType.STRING);
        value = Util.stringParse(value);
        cell.setCellValue(new HSSFRichTextString(value));
    }

    /**
     * 设置单元格的值，如果为Null则设置为默认值
     * 
     * @param rowNum int 行
     * @param colNum short 列
     * @param value String 值
     * @param tostring String 默认值
     */
    public void setCellValue(int rowNum, short colNum, String value, String tostring) {
        getRow(rowNum);
        getCell(colNum);
//        cell.setCellType(HSSFCell.CELL_TYPE_STRING);
        cell.setCellType(CellType.STRING);
        value = Util.isNullTo(value, tostring);
        cell.setCellValue(new HSSFRichTextString(value));
    }

    /**
     * 设置sheet某区域的值，如果为Null或空串则设置为“-”
     * 
     * @param rowFrom int 起始行
     * @param columnFrom short 起始列
     * @param vals List 值
     */
    public void setValue(int rowFrom, short columnFrom, List<String[]> vals) throws Exception {
        for (int i = 0; i < vals.size(); i++) {
            getRow(rowFrom + i);
            String[] value = (String[]) vals.get(i);
            for (int j = 0; j < value.length; j++) {
                getCell((short) (columnFrom + j));
//                cell.setCellType(HSSFCell.CELL_TYPE_STRING);
                cell.setCellType(CellType.STRING);
                value[j] = Util.stringParse(value[j]);
                cell.setCellValue(new HSSFRichTextString(value[j]));
            }
        }
    }

    /**
     * 设置sheet某区域的值
     * 
     * @param rowFrom int 行
     * @param columnFrom short 列
     * @param vals List 值
     * @param tostring String 值为null时的默认值
     */
    public void setValue(int rowFrom, short columnFrom, List<String[]> vals, String tostring)
            throws Exception {
        for (int i = 0; i < vals.size(); i++) {
            getRow(rowFrom + i);
            String[] value = (String[]) vals.get(i);
            for (int j = 0; j < value.length; j++) {
                getCell((short) (columnFrom + j));
//                cell.setCellType(HSSFCell.CELL_TYPE_STRING);
                cell.setCellType(CellType.STRING);
                value[j] = Util.isNullTo(value[j], tostring);
                cell.setCellValue(new HSSFRichTextString(value[j]));
            }
        }
    }

    /**
     * 设置sheet某区域的值，如果为Null或空串则设置为“-”
     * 
     * @param rowFrom int 起始行
     * @param columnFrom short 起始列
     * @param rowHeight short 行高
     * @param vals List 值
     */
    public void setValue(int rowFrom, short columnFrom, short rowHeight, List<String[]> vals)
            throws Exception {
        for (int i = 0; i < vals.size(); i++) {
            getRow(rowFrom + i);
            row.setHeight(rowHeight);
            String[] value = (String[]) vals.get(i);
            for (int j = 0; j < value.length; j++) {
                getCell((short) (columnFrom + j));
//                cell.setCellType(HSSFCell.CELL_TYPE_STRING);
                cell.setCellType(CellType.STRING);
                value[j] = Util.stringParse(value[j]);
                cell.setCellValue(new HSSFRichTextString(value[j]));
            }
        }
    }

    /**
     * 设置sheet某区域的值
     * 
     * @param rowFrom int 行
     * @param columnFrom short 列
     * @param rowHeight short 行高
     * @param vals List 值
     * @param tostring String 值为null时的默认值
     */
    public void setValue(int rowFrom, short columnFrom, short rowHeight, List<String[]> vals,
            String tostring) throws Exception {
        for (int i = 0; i < vals.size(); i++) {
            getRow(rowFrom + i);
            row.setHeight(rowHeight);
            String[] value = (String[]) vals.get(i);
            for (int j = 0; j < value.length; j++) {
                getCell((short) (columnFrom + j));
//                cell.setCellType(HSSFCell.CELL_TYPE_STRING);
                cell.setCellType(CellType.STRING);
                value[j] = Util.isNullTo(value[j], tostring);
                cell.setCellValue(new HSSFRichTextString(value[j]));
            }
        }
    }

    /**
     * 合并单元格并设置类型
     * @param firstRow 第一行
     *@param lastRow 最后一行
     * @param firstCol 第一列
     * @param lastCol 最后一列
     * @param text String 合并后设置的值
     * @param cs HSSFCellStyle 单元格类型
     */
    @SuppressWarnings("deprecation")
    public void fillMergedRegion( int firstRow, int lastRow, int firstCol, int lastCol, String text, HSSFCellStyle cs) {
        CellRangeAddress cellRangeAddress = new CellRangeAddress(firstRow,lastRow,firstCol,lastCol);

        setRegionStyle(firstRow,lastRow,firstCol,lastCol, cs);
        getRow(firstRow);
        getCell(firstCol);
        cell.setCellType(HSSFCell.CELL_TYPE_STRING);
        cell.setCellValue(new HSSFRichTextString(text));
        sheet.addMergedRegion(cellRangeAddress);
    }

    /**
     * 设置某区域的类型
     *
     *
     * @param firstRow 第一行
     *@param lastRow 最后一行
     * @param firstCol 第一列
     * @param lastCol 最后一列
     * @param cs HSSFCellStyle 指定类型对象
     */
    @SuppressWarnings("deprecation")
    private void setRegionStyle( int firstRow, int lastRow, int firstCol, int lastCol , HSSFCellStyle cs) {
        for (int i = firstRow; i <= lastRow; i++) {
            getRow(i);
            for (int j = firstCol; j <= lastCol; j++) {
                getCell((short) j);
                cell.setCellStyle(cs);
            }
        }
    }

    // 关闭文件流
    public void close() {
        try {
            if (fis != null)
                fis.close();
        } catch (IOException ex) {
        }
    }

    /**
     * 保存Excel到文件
     * 
     * @param os OutputStream
     * @throws IOException
     */
    public void export(OutputStream os) throws IOException {
        wb.write(os);
        os.flush();
        os.close();
    }

}
