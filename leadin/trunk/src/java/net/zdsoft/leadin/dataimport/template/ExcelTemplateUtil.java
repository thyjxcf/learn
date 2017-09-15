package net.zdsoft.leadin.dataimport.template;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.zdsoft.leadin.dataimport.common.DataImportConstants;
import net.zdsoft.leadin.dataimport.common.TaskUtil;
import net.zdsoft.leadin.dataimport.common.ZipUtil;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.DVConstraint;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataValidation;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.util.HSSFColor;
//import org.apache.poi.hssf.util.Region;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.tools.zip.ZipOutputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExcelTemplateUtil extends ZDSExcel {
    private static final int DEFAULT_ROWS = 500;// 默认数据行数
        
    @SuppressWarnings("unchecked")
    private List vals;
    private String fileName;
    private static final Logger log = LoggerFactory.getLogger(ExcelTemplateUtil.class);
    private boolean hasTitle;// 标题
    private boolean hasSubtitle;// 副标题
    private boolean hasMoreFileData;// 包含多文件数据
    private String unitId;// 单位id
    private Map<String, String[]> defineMcodeMap;// 微代码: key=中文列名; value=微代码的value
    private String[] letters = new String[]{"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"};

    @SuppressWarnings("unchecked")
    public ExcelTemplateUtil(List vals, String fileName) {
        this.vals = vals;
        this.fileName = fileName;
    }

    @SuppressWarnings("unchecked")
    public ExcelTemplateUtil(List vals, String fileName, boolean hasSubtitle,boolean hasTitle) {
        this.vals = vals;
        this.fileName = fileName;
        this.hasSubtitle = hasSubtitle;
        this.hasTitle = hasTitle;
    }

    @SuppressWarnings("unchecked")
    public ExcelTemplateUtil(List vals, String fileName,boolean hasSubtitle, boolean hasTitle, String unitId,
            boolean hasMoreFileData, Map<String, String[]> defineMcodeMap) {
        this.vals = vals;
        this.fileName = fileName;
        this.hasSubtitle = hasSubtitle;
        this.hasTitle = hasTitle;
        this.unitId = unitId;
        this.hasMoreFileData = hasMoreFileData;
        this.defineMcodeMap = defineMcodeMap;
    }

    public String getFileName() {
        return fileName;
    }

    /**
     * 设置数据有效性
     * @param cols
     */
    private void setDataValidation(String[] cols, int beginRow, int endRow) {
        if (defineMcodeMap == null)
            return;

        if (endRow - beginRow < DEFAULT_ROWS) {
            endRow = beginRow + DEFAULT_ROWS;
        }
        
        //微代码数量
        int mcodeNum = 0;
        
        for (int j = 0; j < cols.length; j++) {
        	String[] texts=null;
        	if(StringUtils.isNotBlank(cols[j]))
        		texts = defineMcodeMap.get(cols[j].replace("*", ""));
            if(j == 59){
            	System.out.println("-----");
            }
            if (null != texts && texts.length > 0) {
                // 指定第几列默认num行,都设置为含有下拉列表的格式，加入数据有效性到当前sheet对象
                try {
                	mcodeNum ++;
                    HSSFDataValidation datavalidation = createDataValidation(texts, beginRow, j,
                            endRow - 1, j, mcodeNum-1);
                    sheet.addValidationData(datavalidation);
                } catch (Exception e) {
                	e.printStackTrace();
                    // 数据量太多时会出错：java.lang.IllegalArgumentException:
                    // String literals in formulas can't be bigger than 255
                    // characters ASCII
                    // log.error(e);
                }

            }
        }
        
       //隐藏微代码EXCEL
       if (mcodeNum > 0){
        	wb.setSheetHidden(1, true);
        }
    }

    /**
     * 设置数据有效性
     * 
     * @param textList
     * @param firstRow
     * @param firstCol
     * @param endRow
     * @param endCol
     * @return
     */
    @SuppressWarnings("deprecation")
    private HSSFDataValidation createDataValidation(String[] textList, int firstRow, int firstCol,
            int endRow, int endCol, int mcodeNum) {
        HSSFSheet hidden = wb.getSheet("hidden");
    	if (hidden==null){
        	hidden = wb.createSheet("hidden");
        }
    	for (int i = 0; i < textList.length; i++) {
			String name = textList[i];
			HSSFRow row = hidden.getRow(i);
			if (row == null){
				row = hidden.createRow(i);
			}
			HSSFCell cell = row.createCell(mcodeNum);
			cell.setCellValue(name);
		}
    	
    	// 加载下拉列表内容
//    	char col = (char)('A' + mcodeNum);
    	//2014-12-17 modify by like 
    	//功能完善--当微代码的数量超过26个时  理论上支持（26+1）*26个
    	String col = "";
    	if(mcodeNum < 26){
    		col = String.valueOf((char)('A' + mcodeNum));
    	}else{
    		int m = mcodeNum%26;
    		int n = mcodeNum/26;
    		col = letters[n-1]+String.valueOf((char)('A' + m));
    	}
    	DVConstraint constraint = DVConstraint.createFormulaListConstraint("hidden!$" + col + "$1:$" + col + "$" + textList.length);
//      DVConstraint constraint = DVConstraint.createExplicitListConstraint(textList);
        // 设置数据有效性加载在哪个单元格上。
        // 四个参数分别是：起始行、终止行、起始列、终止列
//        org.apache.poi.hssf.util.CellRangeAddressList regions = new org.apache.poi.hssf.util.CellRangeAddressList(
//                firstRow, endRow, firstCol, endCol);
        CellRangeAddressList cellRangeAddressList = new CellRangeAddressList(firstRow, endRow, firstCol, endCol);

        // 数据有效性对象
        HSSFDataValidation data_validation = new HSSFDataValidation(cellRangeAddressList, constraint);
        return data_validation;
    }

    /**
     * 设置单元格格式
     * 
     * @param fontSize short 单元格字体大小
     */
    private void setTemplateStyle(HSSFCellStyle cs, short fontSize, boolean isBold) {
        // 水平居中
//        cs.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        cs.setAlignment(HorizontalAlignment.CENTER);
        // 垂直居中
//        cs.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        cs.setVerticalAlignment(VerticalAlignment.CENTER);
        // 创建字体类
        HSSFFont font = wb.createFont();
        // 粗体
        if (isBold)
//            font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
            font.setBold(true);
        font.setFontHeightInPoints(fontSize);
        cs.setFont(font);
    }

    @SuppressWarnings("unchecked")
    private void createDataExcel(List vals, String tostring) {
		int rowOrder = 0;// 行号
		// 表头
		String[] tablehead = (String[]) vals.get(0);
		tablehead[0] = Util.isNullTo(tablehead[0], tostring);
		if (StringUtils.isEmpty(wb.getSheetName(0))
				&& StringUtils.isNotBlank(tablehead[0])) {
			wb.setSheetName(0, tablehead[0]);
		}
        int num = 1;
        if (hasSubtitle)
            num = 2;
        // 列名
        String[] cols = (String[]) vals.get(num);

        if(hasTitle){
	        // 根据列名数合并表头单元格
//	        Region region = new Region(0, (short) 0, 0, (short) (cols.length - 1));
//            CellRangeAddress cellRangeAddress = new CellRangeAddress(0,0,0,(cols.length - 1));
            HSSFCellStyle cellStyle1 = wb.createCellStyle();
	        setTemplateStyle(cellStyle1, (short) 12, true);
	        fillMergedRegion(0,0,0,(cols.length - 1),tablehead[0], cellStyle1);
	        getRow(rowOrder++);
	        row.setHeight((short) 500);
        }
        if (hasSubtitle) {
            HSSFCellStyle cellStyleSubTitle = wb.createCellStyle();
            setTemplateStyle(cellStyleSubTitle, (short) 10, false);
//            Region region1 = new Region(1, (short) 0, 1, (short) (cols.length - 1));
            CellRangeAddress cellRangeAddress = new CellRangeAddress(1,0,1,(cols.length - 1));
            String[] tablehead1 = (String[]) vals.get(1);
            tablehead1[0] = Util.isNullTo(tablehead1[0], tostring);
            fillMergedRegion(1,0,1,(cols.length - 1), tablehead1[0], cellStyleSubTitle);
            getRow(rowOrder++);
            row.setHeight((short) 400);
        }

        // 设置模板的列名
        HSSFCellStyle cellStyle2 = wb.createCellStyle();
        setTemplateStyle(cellStyle2, (short) 10, true);
        getRow(rowOrder++);

        row.setHeight((short) 350);
        for (int i = 0; i < cols.length; i++) {
            getCell((short) i);
//            cell.setCellType(HSSFCell.CELL_TYPE_STRING);
            cell.setCellType(CellType.STRING);
            cols[i] = Util.isNullTo(cols[i], tostring);
            cell.setCellValue(new HSSFRichTextString(cols[i]));
            cell.setCellStyle(cellStyle2);
        }

        HSSFCellStyle cellStyle3;
        cellStyle3 = wb.createCellStyle();
        setTemplateStyle(cellStyle3, (short) 10, false);
        for (int j = rowOrder; j < vals.size(); j++) {
            String[] dataCols = (String[]) vals.get(j);
            getRow(j);
            row.setHeight((short) 350);
            for (int i = 0; i < dataCols.length; i++) {//                
                getCell((short) i);
//                cell.setCellType(HSSFCell.CELL_TYPE_STRING);
                cell.setCellType(CellType.STRING);
                dataCols[i] = Util.isNullTo(dataCols[i], tostring);
                
                cell.setCellValue(new HSSFRichTextString(dataCols[i]));
                //数据区域需要设置为文本--by zhangkc 20140902
                cellStyle3.setDataFormat((short)BuiltinFormats.getBuiltinFormat("text"));
                cell.setCellStyle(cellStyle3);
            }
        }
        
        setDataValidation(cols, rowOrder, vals.size());
    }

    @SuppressWarnings("unchecked")
    private void createErrorDataExcel(List vals, String tostring) {
        int rowOrder = 0;// 行号

        // 表头
        String[] tablehead = (String[]) vals.get(0);
        tablehead[0] = Util.isNullTo(tablehead[0], tostring);
        int num = 1;
        if (hasSubtitle)
            num = 2;

        // 列名
        String[] cols = (String[]) vals.get(num);
        // 根据列名数合并表头单元格
//        Region region = new Region(0, (short) 0, 0, (short) (cols.length - 1));
        CellRangeAddress cellRangeAddress = new CellRangeAddress(0,0,0,(cols.length - 1)) ;
        HSSFCellStyle cellStyle1 = wb.createCellStyle();
        setTemplateStyle(cellStyle1, (short) 12, true);
        fillMergedRegion(0,0,0,(cols.length - 1), tablehead[0], cellStyle1);
        getRow(rowOrder);
        row.setHeight((short) 500);

        if (hasSubtitle) {
            HSSFCellStyle cellStyleSubTitle = wb.createCellStyle();
            setTemplateStyle(cellStyleSubTitle, (short) 10, false);
//            Region region1 = new Region(1, (short) 0, 1, (short) (cols.length - 1));
//            CellRangeAddress cellRangeAddress1 = new CellRangeAddress(1,0,1,(cols.length - 1));
//            CellRangeAddress cellRangeAddress1 = new CellRangeAddress(1,0,1,(cols.length - 1));
            String[] tablehead1 = (String[]) vals.get(1);
            tablehead1[0] = Util.isNullTo(tablehead1[0], tostring);
            fillMergedRegion(1,0,1,(cols.length - 1), tablehead1[0], cellStyleSubTitle);
            getRow(++rowOrder);
            row.setHeight((short) 400);
        }

        // 设置模板的列名
        HSSFCellStyle cellStyle2 = wb.createCellStyle();
        setTemplateStyle(cellStyle2, (short) 10, true);
        getRow(++rowOrder);
        row.setHeight((short) 350);
        for (int i = 0; i < cols.length; i++) {
            getCell((short) i);
//            cell.setCellType(HSSFCell.CELL_TYPE_STRING);
            cell.setCellType(CellType.STRING);
            cols[i] = Util.isNullTo(cols[i], tostring);
            cell.setCellValue(new HSSFRichTextString(cols[i]));
            cell.setCellStyle(cellStyle2);
        }

        HSSFCellStyle cellStyle5 = wb.createCellStyle();
        setTemplateStyle(cellStyle5, (short) 10, false);
        HSSFCellStyle cellStyle3;
        cellStyle3 = wb.createCellStyle();
        setTemplateStyle(cellStyle3, (short) 10, false);
//        cellStyle3.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        cellStyle3.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        cellStyle3.setFillForegroundColor(HSSFColor.LIGHT_TURQUOISE.index);
//        cellStyle3.setBorderLeft(HSSFCellStyle.BORDER_MEDIUM);
        cellStyle3.setBorderLeft(BorderStyle.MEDIUM);
//        cellStyle3.setBorderRight(HSSFCellStyle.BORDER_MEDIUM);
        cellStyle3.setBorderRight(BorderStyle.MEDIUM);
//        cellStyle3.setBorderBottom(HSSFCellStyle.BORDER_MEDIUM);
        cellStyle3.setBorderBottom(BorderStyle.MEDIUM);
//        cellStyle3.setBorderTop(HSSFCellStyle.BORDER_MEDIUM);
        cellStyle3.setBorderTop(BorderStyle.MEDIUM);
        cellStyle3.setLeftBorderColor(HSSFColor.BLUE.index);
        cellStyle3.setRightBorderColor(HSSFColor.BLUE.index);
        cellStyle3.setTopBorderColor(HSSFColor.BLUE.index);
        cellStyle3.setBottomBorderColor(HSSFColor.BLUE.index);

        HSSFCellStyle cellStyle4;
        cellStyle4 = wb.createCellStyle();
        setTemplateStyle(cellStyle4, (short) 10, false);
//        cellStyle4.setBorderLeft(HSSFCellStyle.BORDER_MEDIUM);
//        cellStyle4.setBorderRight(HSSFCellStyle.BORDER_MEDIUM);
//        cellStyle4.setBorderBottom(HSSFCellStyle.BORDER_MEDIUM);
//        cellStyle4.setBorderTop(HSSFCellStyle.BORDER_MEDIUM);
        cellStyle4.setBorderLeft(BorderStyle.MEDIUM);
        cellStyle4.setBorderRight(BorderStyle.MEDIUM);
        cellStyle4.setBorderBottom(BorderStyle.MEDIUM);
        cellStyle4.setBorderTop(BorderStyle.MEDIUM);
        cellStyle4.setLeftBorderColor(HSSFColor.RED.index);
        cellStyle4.setRightBorderColor(HSSFColor.RED.index);
        cellStyle4.setTopBorderColor(HSSFColor.RED.index);
        cellStyle4.setBottomBorderColor(HSSFColor.RED.index);

        for (int j = rowOrder + 1; j < vals.size(); j++) {
            cols = (String[]) vals.get(j);
            getRow(j);
            row.setHeight((short) 350);
            for (int i = 0; i < cols.length; i++) {
                getCell((short) i);
//                cell.setCellType(HSSFCell.CELL_TYPE_STRING);
                cell.setCellType(CellType.STRING);
                cols[i] = Util.isNullTo(cols[i], tostring);
                if (i >= cols.length - 1) {
                    cell.setCellValue(new HSSFRichTextString(cols[i]));
                    cell.setCellStyle(cellStyle3);
                } else {
                    if (cols[i].indexOf("$$havaErrors") >= 0) {
                        if (cols[i].indexOf("$$havaErrors") == cols[i].length()
                                - "$$havaErrors".length()) {

                            cols[i] = cols[i].substring(0, cols[i].indexOf("$$havaErrors"));
                            cell.setCellValue(new HSSFRichTextString(cols[i]));
                            cell.setCellStyle(cellStyle4);
                        } else {
                            cell.setCellValue(new HSSFRichTextString(cols[i]));
                            cell.setCellStyle(cellStyle5);
                        }
                    } else {
                        cell.setCellValue(new HSSFRichTextString(cols[i]));
                        cell.setCellStyle(cellStyle5);
                    }
                }
            }
        }
    }
    
    @SuppressWarnings("unchecked")
    public void createErrorDataExcelToFile(List vals, String tostring, String title) {

        int rowOrder = 0;// 行号
        int index = 1;// 
        int num = 0;
        if (hasSubtitle)
            num = 1;

        // 列名
        String[] cols = (String[]) vals.get(num);
        // 根据列名数合并表头单元格
        if(hasTitle){
//	        Region region = new Region(0, (short) 0, 0, (short) (cols.length - 2));
//            CellRangeAddress cellRangeAddress1 = new CellRangeAddress(0,0,0,(cols.length - 2));
	        HSSFCellStyle cellStyle1 = wb.createCellStyle();
	        setTemplateStyle(cellStyle1, (short) 12, true);
	        fillMergedRegion(0,0,0,(cols.length - 2), title, cellStyle1);
	        getRow(rowOrder++);
	        row.setHeight((short) 500);
        }
        if (hasSubtitle) {
            HSSFCellStyle cellStyleSubTitle = wb.createCellStyle();
            setTemplateStyle(cellStyleSubTitle, (short) 10, false);
//            Region region1 = new Region(1, (short) 0, 1, (short) (cols.length - 2));
//            CellRangeAddress cellRangeAddress1 = new CellRangeAddress(1,0,1,(cols.length - 2));
            String[] tablehead1 = (String[]) vals.get(1);
            tablehead1[0] = Util.isNullTo(tablehead1[0], tostring);
            fillMergedRegion(1,0,1,(cols.length - 2), tablehead1[0], cellStyleSubTitle);
            getRow(rowOrder++);
            index++;
            row.setHeight((short) 400);
        }

        // 设置模板的列名
        HSSFCellStyle cellStyle2 = wb.createCellStyle();
        setTemplateStyle(cellStyle2, (short) 10, true);
        getRow(rowOrder++);
        row.setHeight((short) 350);

        for (int i = 0; i < cols.length - 1; i++) {
            if (i == cols.length - 2) {
                getCell((short) (0));// 第一列为错误列
            } else {
                getCell((short) (i + 1));
            }

//            cell.setCellType(HSSFCell.CELL_TYPE_STRING);
            cell.setCellType(CellType.STRING);
            if (i == cols.length - 2)
                cols[i] = Util.isNullTo(cols[i + 1], tostring);
            cell.setCellValue(new HSSFRichTextString(cols[i]));
            cell.setCellStyle(cellStyle2);
        }

        HSSFCellStyle cellStyle3 = wb.createCellStyle();
//        cellStyle3.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        cellStyle3.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        cellStyle3.setFillForegroundColor(HSSFColor.LIGHT_TURQUOISE.index);
//        cellStyle3.setBorderLeft(HSSFCellStyle.BORDER_MEDIUM);
//        cellStyle3.setBorderRight(HSSFCellStyle.BORDER_MEDIUM);
//        cellStyle3.setBorderBottom(HSSFCellStyle.BORDER_MEDIUM);
//        cellStyle3.setBorderTop(HSSFCellStyle.BORDER_MEDIUM);
        cellStyle3.setBorderLeft(BorderStyle.MEDIUM);
        cellStyle3.setBorderRight(BorderStyle.MEDIUM);
        cellStyle3.setBorderBottom(BorderStyle.MEDIUM);
        cellStyle3.setBorderTop(BorderStyle.MEDIUM);
        cellStyle3.setLeftBorderColor(HSSFColor.BLUE.index);
        cellStyle3.setRightBorderColor(HSSFColor.BLUE.index);
        cellStyle3.setTopBorderColor(HSSFColor.BLUE.index);
        cellStyle3.setBottomBorderColor(HSSFColor.BLUE.index);
        setTemplateStyle(cellStyle3, (short) 10, false);

        HSSFCellStyle cellStyle4 = wb.createCellStyle();
//        cellStyle4.setBorderLeft(HSSFCellStyle.BORDER_MEDIUM);
//        cellStyle4.setBorderRight(HSSFCellStyle.BORDER_MEDIUM);
//        cellStyle4.setBorderBottom(HSSFCellStyle.BORDER_MEDIUM);
//        cellStyle4.setBorderTop(HSSFCellStyle.BORDER_MEDIUM);
        cellStyle4.setBorderLeft(BorderStyle.MEDIUM);
        cellStyle4.setBorderRight(BorderStyle.MEDIUM);
        cellStyle4.setBorderBottom(BorderStyle.MEDIUM);
        cellStyle4.setBorderTop(BorderStyle.MEDIUM);
        cellStyle4.setLeftBorderColor(HSSFColor.RED.index);
        cellStyle4.setRightBorderColor(HSSFColor.RED.index);
        cellStyle4.setTopBorderColor(HSSFColor.RED.index);
        cellStyle4.setBottomBorderColor(HSSFColor.RED.index);
        setTemplateStyle(cellStyle4, (short) 10, false);
        for (int j = index; j < vals.size(); j++) {
            if (vals.get(j) == null) {
                continue;
            }
            cols = (String[]) vals.get(j);
            getRow(rowOrder++);
            row.setHeight((short) 350);
            for (int i = 0; i < cols.length - 1; i++) {
                if (i == cols.length - 2) {
                    getCell((short) (0));// 第一列为错误列
                } else {
                    getCell((short) (i + 1));
                }
//                cell.setCellType(HSSFCell.CELL_TYPE_STRING);
                cell.setCellType(CellType.STRING);
                cols[i] = Util.isNullTo(cols[i], tostring);
                if (i >= cols.length - 2) {
                    cell.setCellStyle(cellStyle3);
                    cols[i] = cols[i + 1];

                } else {
                    if (cols[cols.length - 2].indexOf("|" + i + "|") >= 0) {
                        cell.setCellStyle(cellStyle4);
                    }
                }
                cell.setCellValue(new HSSFRichTextString(cols[i]));
            }
        }

    }

    // 生成数据导出文件
    @SuppressWarnings("unchecked")
    public void writeDataFile(OutputStream os) {
        try {
            List headList = new ArrayList();
            int rowOrder = 0;// 行号
            if (hasSubtitle) {
                headList.add((String[]) vals.get(rowOrder++));// 标题
                headList.add(null);// 标题题
                headList.add((String[]) vals.get(rowOrder++));// 列标题
            }

            if (hasMoreFileData) {// 多文件
                hasSubtitle = true;// 手工分为多文件时必须有副标题

                // 创建目录
                String path = TaskUtil.createStoreSubdir(new String[] {
                        DataImportConstants.FILE_PATH_EXPORT_TEMPLATE, fileName, unitId });
                File file = null;

                for (int i = rowOrder; i < vals.size(); i++) {
                    List<String[]> subList = (List<String[]>) vals.get(i);
                    List list = new ArrayList();
                    String[] subTitle = subList.get(0);
                    headList.set(1, subTitle);// 副标题
                    list.addAll(headList);
                    list.addAll(subList.subList(1, subList.size()));

                    String subFileName = subTitle[0];
                    if (null == subFileName || subFileName.length() == 0) {
                        subFileName = String.valueOf(i - rowOrder + 1);
                    }
                    createWB();
                    createSheet("Sheet1");
                    createDataExcel(list, "");
                    String filePath = path + File.separator + fileName + "_" + subFileName + ".xls";
                    file = new File(filePath);
                    FileOutputStream fos = new FileOutputStream(file);
                    export(fos);
                }
                // 压缩包
                ZipUtil.makeZip(path, (ZipOutputStream) os);

                ZipUtil.deleteFile(path);// 先删再建，以便打包
                file = new File(path);
                file.delete();

            } else {// 单个文件数据

                createWB();
                createSheet("Sheet1");
                if (hasSubtitle) {
                    List list = new ArrayList();
                    List<String[]> subList = (List<String[]>) vals.get(rowOrder);// 只有一个文件
                    String[] subTitle = subList.get(0);
                    headList.set(1, subTitle);// 副标题
                    list.addAll(headList);
                    list.addAll(subList.subList(1, subList.size()));
                    createDataExcel(list, "");
                } else {
                    createDataExcel(vals, "");
                }
                export(os);
            }
        } catch (Exception ex) {
            log.error(ex.toString());
        }
    }

    // 生成错误数据文件
    public void writeErrorFile(OutputStream os) {
        createWB();
        createSheet("Sheet1");
        try {
            createErrorDataExcel(vals, "");
            export(os);
        } catch (Exception ex) {
            log.debug(ex.getMessage());
        }
    }

    public void writeErrorFileToFile(String filePath) {
        createWB();
        createSheet("Sheet1");
        createErrorDataExcelToFile(vals, "", fileName);
        FileOutputStream fOut;
        try {
            fOut = new FileOutputStream(filePath);
            wb.write(fOut);
            fOut.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public boolean isHasSubtitle() {
        return hasSubtitle;
    }

    public boolean isHasMoreFileData() {
        return hasMoreFileData;
    }

}
