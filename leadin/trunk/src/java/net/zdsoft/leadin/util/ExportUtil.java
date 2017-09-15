package net.zdsoft.leadin.util;

import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.imageio.ImageIO;

import net.zdsoft.keel.action.Printable;
import net.zdsoft.keel.util.FileUtils;
import net.zdsoft.keel.util.ObjectUtils;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExportUtil implements Printable {
    
    /**
     * Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 3835156164181768242L;
    
    private static final Logger log = LoggerFactory.getLogger(ExportUtil.class);
    
    public static final short DATE_FORMAT = HSSFDataFormat
            .getBuiltinFormat("m/d/yy");

    private short dateFormat = DATE_FORMAT; // 默认日期格式
    private boolean printEnabled = false; // 默认为非printable action

    public ExportUtil() {
    }

    public short getDateFormat() {
        return dateFormat;
    }

    public String getPrintContent() {
        return null;
    }

    public boolean isPrintEnabled() {
        return printEnabled;
    }

    public void setDateFormat(short dateFormat) {
        this.dateFormat = dateFormat;
    }

    public void setPrintEnabled(boolean printEnabled) {
        this.printEnabled = printEnabled;
    }

    public void exportXLSFile(String fileName,
            Map<String, List<String>> fieldTitleMap,
            Map<String, List<Map<String, String>>> sheetName2RecordListMap) {
        printEnabled = true; // 设置为printable

        HSSFWorkbook workbook = new HSSFWorkbook();
        /**
         * 由于style创建不能超过4000个所以把这个style放到循环外头  modify by zhuw 2013-3-29
         * **/
        HSSFCellStyle cellDateStyle = workbook
                .createCellStyle();
        cellDateStyle.setDataFormat(dateFormat);
        int i = 0;
        for (Iterator<Entry<String, List<Map<String, String>>>> iter = sheetName2RecordListMap.entrySet().iterator(); iter
                .hasNext();) {
            Map.Entry<String, List<Map<String, String>>> entry = (Entry<String, List<Map<String, String>>>) iter.next();
            String sheetName = entry.getKey(); // key 工作表名称
            List<Map<String, String>> recordList = entry.getValue(); // value 工作表上的记录

            workbook.createSheet();
            sheetName = StringUtils.replaceEach(sheetName, new String[]{"\\","/","?","*","[","]"}, new String[]{"","","","","",""});
            if (StringUtils.EMPTY.equals(sheetName)) {
    			sheetName = " ";
    		}
            workbook.setSheetName(i++, sheetName);

            // 创建首行信息栏
            HSSFRow row = workbook.getSheetAt(i - 1).createRow(0);
            List<String> fieldTitleList = fieldTitleMap.get(entry.getKey());
            String[] fieldTitles = fieldTitleList.toArray(new String[0]);
            for (int j = 0; j < fieldTitles.length; j++) {
                HSSFCell cell = row.createCell(j);
                cell.setCellType(HSSFCell.CELL_TYPE_STRING);
                cell.setCellValue(new HSSFRichTextString(fieldTitles[j]));
            }

            // 写入每条记录
            int rowNum = 1; // 行号
            for (int j = 0, m = recordList.size(); j < m; j++) {
                HSSFRow _row = workbook.getSheetAt(i - 1).createRow(rowNum++);
                Map<String, String> field2ValueMap = recordList.get(j);

                for (int k = 0; k < fieldTitles.length; k++) {
                    HSSFCell cell = _row.createCell(k);
                    Object value = field2ValueMap.get(fieldTitles[k]);

                    if (value == null) {
                        cell.setCellType(
                                HSSFCell.CELL_TYPE_BLANK);
                    }
                    else if (value instanceof String) {
                        cell.setCellType(
                                HSSFCell.CELL_TYPE_STRING);
                        cell.setCellValue((String) value);
                    }
                    else if (value instanceof Boolean) {
                        cell.setCellType(
                                HSSFCell.CELL_TYPE_BOOLEAN);
                        cell.setCellValue(
                                ((Boolean) value).booleanValue());
                    }
                    else if (value instanceof Integer) {
                        cell.setCellValue(
                                ((Integer) value).intValue());
                    }
                    else if (value instanceof Double) {
                        cell.setCellValue(
                                ((Double) value).doubleValue());
                    }
                    else if (value instanceof Date) {
//                        HSSFCellStyle cellDateStyle = workbook
//                                .createCellStyle();
//                        cellDateStyle.setDataFormat(dateFormat);
                        cell.setCellStyle(cellDateStyle);
                        cell.setCellValue((Date) value);
                    }
                    else {
                        cell.setCellValue(value.toString());
                    }
                }
            }
        }

        outputData(workbook, fileName); // 导出文件
    }

    /**
     * 将记录信息导出为xls格式文件. (未使用reflection来实现的方法)
     * 
     * @param fileName
     *            导出文件的名称
     * @param fieldTitles
     *            工作表的字段标题数组(首行)
     * @param sheetName2RecordListMap
     *            记录集map, 其中key为工作表名称, value为一个list(内含字段标题-字段值的map).
     */
    protected void exportXLSFile(String fileName, String[] fieldTitles,
            Map<String, List<Map<String,Object>>> sheetName2RecordListMap) {
        printEnabled = true; // 设置为printable

        HSSFWorkbook workbook = new HSSFWorkbook();
        /**
         * 由于style创建不能超过4000个所以把这个style放到循环外头  modify by zhuw 2013-3-29
         * **/
        HSSFCellStyle cellDateStyle = workbook
                .createCellStyle();
        cellDateStyle.setDataFormat(dateFormat);
        int i = 0;
        for (Iterator<Entry<String, List<Map<String,Object>>>> iter = sheetName2RecordListMap.entrySet().iterator(); iter
                .hasNext();) {
            Map.Entry<String, List<Map<String,Object>>> entry = iter.next();
            String sheetName = entry.getKey(); // key 工作表名称
            List<Map<String,Object>> recordList = entry.getValue(); // value 工作表上的记录

            workbook.createSheet();
            sheetName = StringUtils.replaceEach(sheetName, new String[]{"\\","/","?","*","[","]"}, new String[]{"","","","","",""});
            if (StringUtils.EMPTY.equals(sheetName)) {
    			sheetName = " ";
    		}
            workbook.setSheetName(i++, sheetName);

            // 创建首行信息栏
            HSSFRow row = workbook.getSheetAt(i - 1).createRow(0);
            for (int j = 0; j < fieldTitles.length; j++) {
                HSSFCell cell = row.createCell(j);
                cell.setCellType(HSSFCell.CELL_TYPE_STRING);
                cell.setCellValue(new HSSFRichTextString(fieldTitles[j]));
            }

            // 写入每条记录
            int rowNum = 1; // 行号
            for (int j = 0, m = recordList.size(); j < m; j++) {
                HSSFRow _row = workbook.getSheetAt(i - 1).createRow(rowNum++);
                Map<String,Object> field2ValueMap = recordList.get(j);

                for (int k = 0; k < fieldTitles.length; k++) {
                    HSSFCell cell = _row.createCell(k);
                    Object value = field2ValueMap.get(fieldTitles[k]);

                    if (value == null) {
                        cell.setCellType(
                                HSSFCell.CELL_TYPE_BLANK);
                    }
                    else if (value instanceof String) {
                        cell.setCellType(
                                HSSFCell.CELL_TYPE_STRING);
                        cell.setCellValue((String) value);
                    }
                    else if (value instanceof Boolean) {
                        cell.setCellType(
                                HSSFCell.CELL_TYPE_BOOLEAN);
                        cell.setCellValue(
                                ((Boolean) value).booleanValue());
                    }
                    else if (value instanceof Integer) {
                        cell.setCellValue(
                                ((Integer) value).intValue());
                    }
                    else if (value instanceof Double) {
                        cell.setCellValue(
                                ((Double) value).doubleValue());
                    }
                    else if (value instanceof Date) {
//                        HSSFCellStyle cellDateStyle = workbook
//                                .createCellStyle();
//                        cellDateStyle.setDataFormat(dateFormat);
                        cell.setCellStyle(cellDateStyle);
                        cell.setCellValue((Date) value);
                    }
                    else {
                        cell.setCellValue(value.toString());
                    }
                }
            }
        }

        outputData(workbook, fileName); // 导出文件
    }
    
    /**
     * 将记录信息导出为xls格式文件. (使用reflection来实现的方法)
     * 
     * @param fieldTitles
     *            工作表的字段标题数组(首行)
     * @param propertyNames
     *            每行记录对应的值对象的属性名称数组
     * @param records
     *            记录集map, 其中key为工作表名称, value为属于该工作表的值对象列表
     * @param fileName
     *            导出文件的名称
     */
    @SuppressWarnings("unchecked")
    public void exportXLSFile(String[] fieldTitles, String[] propertyNames,
            Map records, String fileName, String title) {
        printEnabled = true; // 设置为printable

        if (fieldTitles.length != propertyNames.length) {
            throw new IllegalArgumentException("工作表的字段标题列数必须和值对象的属性数相等");
        }

        HSSFWorkbook workbook = new HSSFWorkbook();

        int i = 0;
        for (Iterator iter = records.entrySet().iterator(); iter.hasNext();) {
            Map.Entry entry = (Entry) iter.next();
            String sheetName = (String) entry.getKey(); // key 工作表名称
            List sheetRecords = (List) entry.getValue(); // value 工作表上的记录

            workbook.createSheet();
            sheetName = StringUtils.replaceEach(sheetName, new String[]{"\\","/","?","*","[","]"}, new String[]{"","","","","",""});
            if (StringUtils.EMPTY.equals(sheetName)) {
    			sheetName = " ";
    		}
            workbook.setSheetName(i++, sheetName);

            // 创建首行信息栏
            HSSFSheet sheet = workbook.getSheetAt(i - 1);
            HSSFRow row = sheet.createRow(0);
            CellRangeAddress region = new CellRangeAddress(0, (short) 0, 0, (short) (fieldTitles.length - 1));
            HSSFCellStyle cellStyle = workbook.createCellStyle();
            cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
            cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
            HSSFFont titlefont = workbook.createFont(); 
            titlefont.setFontName("宋体");    
            titlefont.setFontHeightInPoints((short) 12);// 字体大小    
            titlefont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);// 加粗
            cellStyle.setFont(titlefont);
            HSSFCell cell = row.createCell(0);
            cell.setCellStyle(cellStyle);
            cell.setCellValue(new HSSFRichTextString(title));
            sheet.addMergedRegion(region);
            
            HSSFCellStyle headCellStyle = workbook.createCellStyle();
            HSSFFont headfont = workbook.createFont(); 
            headfont.setFontName("宋体");    
            headfont.setFontHeightInPoints((short) 10);// 字体大小    
            headfont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);// 加粗
            headCellStyle.setFont(headfont);
            row = workbook.getSheetAt(i - 1).createRow(1);
            for (int j = 0; j < fieldTitles.length; j++) {
                cell = row.createCell(j);
                cell.setCellType(HSSFCell.CELL_TYPE_STRING);
                cell.setCellValue(new HSSFRichTextString(fieldTitles[j]));
                cell.setCellStyle(headCellStyle);
            }

            // 写入每条记录
            int rowNum = 2; // 行号
            for (int j = 0, m = sheetRecords.size(); j < m; j++) {
                HSSFRow _row = workbook.getSheetAt(i - 1).createRow(rowNum++);
                Object obj = sheetRecords.get(j);
                for (int k = 0; k < fieldTitles.length; k++) {
                    cell = _row.createCell(k);

                    Object value = ObjectUtils.getNestedProperty(obj,
                            propertyNames[k]);

                    if (value == null) {
                        cell.setCellType(
                                HSSFCell.CELL_TYPE_BLANK);
                    }
                    else if (value instanceof String) {
                        cell.setCellType(
                                HSSFCell.CELL_TYPE_STRING);
                        cell.setCellValue((String) value);
                    }
                    else if (value instanceof Boolean) {
                        cell.setCellType(
                                HSSFCell.CELL_TYPE_BOOLEAN);
                        cell.setCellValue(
                                ((Boolean) value).booleanValue());
                    }
                    else if (value instanceof Integer) {
                        cell.setCellValue(
                                ((Integer) value).intValue());
                    }
                    else if (value instanceof Double) {
                        cell.setCellValue(
                                ((Double) value).doubleValue());
                    }
                    else if (value instanceof Date) {
                        HSSFCellStyle cellDateStyle = workbook
                                .createCellStyle();
                        cellDateStyle.setDataFormat(dateFormat);
                        cell.setCellStyle(cellDateStyle);
                        cell.setCellValue((Date) value);
                    }
                    else {
                        cell.setCellValue(value.toString());
                    }
                }
            }
        }

        outputData(workbook, fileName); // 导出文件

    }

    /**
     * 将记录信息导出为xls格式文件. (使用reflection来实现的方法)
     * 
     * @param fieldTitles
     *            工作表的字段标题数组(首行)
     * @param propertyNames
     *            每行记录对应的值对象的属性名称数组
     * @param records
     *            记录集map, 其中key为工作表名称, value为属于该工作表的值对象列表
     * @param fileName
     *            导出文件的名称
     */
    @SuppressWarnings("unchecked")
    public void exportXLSFile(String[] fieldTitles, String[] propertyNames,
            Map records, String fileName) {
        printEnabled = true; // 设置为printable

        if (fieldTitles.length != propertyNames.length) {
            throw new IllegalArgumentException("工作表的字段标题列数必须和值对象的属性数相等");
        }

        HSSFWorkbook workbook = new HSSFWorkbook();

        int i = 0;
        for (Iterator iter = records.entrySet().iterator(); iter.hasNext();) {
            Map.Entry entry = (Entry) iter.next();
            String sheetName = (String) entry.getKey(); // key 工作表名称
            List sheetRecords = (List) entry.getValue(); // value 工作表上的记录
            workbook.createSheet();
            sheetName = StringUtils.replaceEach(sheetName, new String[]{"\\","/","?","*","[","]"}, new String[]{"","","","","",""});
            if (StringUtils.EMPTY.equals(sheetName)) {
				sheetName = " ";
			}
			workbook.setSheetName(i++, sheetName);

            // 创建首行信息栏
			HSSFSheet sheetAt = workbook.getSheetAt(i - 1);
            HSSFRow row = sheetAt.createRow(0);
            for(int j=0;j<fieldTitles.length;j++){
            	sheetAt.setColumnWidth(j, 15 * 256);
            }
            HSSFCellStyle titleStyle=workbook.createCellStyle(); 
            HSSFFont headfont = workbook.createFont();    
            headfont.setFontName("宋体");    
            headfont.setFontHeightInPoints((short) 12);// 字体大小    
            headfont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);// 加粗
            titleStyle.setFont(headfont);
            //放在循环内  当导出数据过多时会出现报错
            HSSFCellStyle cellDateStyle = workbook.createCellStyle();
            cellDateStyle.setDataFormat(dateFormat);
            for (int j = 0; j < fieldTitles.length; j++) {
                HSSFCell cell = row.createCell(j);
                cell.setCellType(HSSFCell.CELL_TYPE_STRING);
                cell.setCellValue(new HSSFRichTextString(fieldTitles[j]));
                cell.setCellStyle(titleStyle);
            }
            
            HSSFPatriarch patriarch = workbook.getSheetAt(i - 1).createDrawingPatriarch();
            
            // 写入每条记录
            int rowNum = 1; // 行号
            for (int j = 0, m = sheetRecords.size(); j < m; j++) {
                HSSFRow _row = workbook.getSheetAt(i - 1).createRow(rowNum++);
                Object obj = sheetRecords.get(j);
                for (int k = 0; k < fieldTitles.length; k++) {
                    HSSFCell cell = _row.createCell(k);
                    Object value =null;
                    try {
                    	value = ObjectUtils.getNestedProperty(obj,
                                propertyNames[k]);
					} catch (Exception e) {
					}
                    if (value == null) {
                        cell.setCellType(
                                HSSFCell.CELL_TYPE_BLANK);
                    }
                    else if (value instanceof String) {
                        cell.setCellType(
                                HSSFCell.CELL_TYPE_STRING);
                        cell.setCellValue((String) value);
                    }
                    else if (value instanceof Boolean) {
                        cell.setCellType(
                                HSSFCell.CELL_TYPE_BOOLEAN);
                        cell.setCellValue(
                                ((Boolean) value).booleanValue());
                    }
                    else if (value instanceof Integer) {
                        cell.setCellValue(
                                ((Integer) value).intValue());
                    }
                    else if (value instanceof Double) {
                        cell.setCellValue(
                                ((Double) value).doubleValue());
                    }
                    else if (value instanceof Date) {
//                    	HSSFCellStyle cellDateStyle = workbook.createCellStyle();
//                        cellDateStyle.setDataFormat(dateFormat);
                        cell.setCellStyle(cellDateStyle);
                        cell.setCellValue((Date) value);
                    }else if (value instanceof File) {
                    	File photo = (File)value;
                    	if(null !=photo && photo.exists()) {
                			ByteArrayOutputStream byteArrayOut = new ByteArrayOutputStream();
                			try {
								BufferedImage bufferImg = ImageIO.read(photo);
								ImageIO.write(bufferImg, "jpg", byteArrayOut);
							} catch (IOException e) {
								e.printStackTrace();
							}
                			HSSFClientAnchor anchor = new HSSFClientAnchor(0, 0, 0, 0,
                					(short) k, rowNum-1, (short) (k+1), rowNum);// 控制图片的左上角,右下角的位置
                			anchor.setAnchorType(2);
                			patriarch.createPicture(anchor, workbook.addPicture(byteArrayOut
                					.toByteArray(), HSSFWorkbook.PICTURE_TYPE_JPEG));
                    	}

                    }
                    else {
                        cell.setCellValue(value.toString());
                    }
                }
            }
        }

        outputData(workbook, fileName); // 导出文件

    }

    /**
     * 将记录信息导出为xls格式文件.
     * 
     * @param fieldTitles
     *            工作表的字段标题数组(首行)
     * @param sheetNames
     *            工作表名
     * @param fileName
     *            导出文件的名称
     */
    protected void exportXLSTemplet(String[] fieldTitles, String[] sheetNames,
            String fileName) {
        printEnabled = true; // 设置为printable

        HSSFWorkbook workbook = new HSSFWorkbook();
        for (int i = 0, length = sheetNames.length; i < length; i++) {

            workbook.createSheet();
            String sheetName = StringUtils.replaceEach(sheetNames[i], new String[]{"\\","/","?","*","[","]"}, new String[]{"","","","","",""});
            if (StringUtils.EMPTY.equals(sheetName)) {
    			sheetName = " ";
    		}
            workbook.setSheetName(i, sheetName);

            // 创建首行信息栏
            HSSFRow row = workbook.getSheetAt(i).createRow(0);
            for (int j = 0; j < fieldTitles.length; j++) {
                HSSFCell cell = row.createCell(j);
                cell.setCellType(HSSFCell.CELL_TYPE_STRING);
                cell.setCellValue(new HSSFRichTextString(fieldTitles[j]));
            }

        }

        outputData(workbook, fileName); // 导出文件
    }

    public static void outputData(HSSFWorkbook workbook, String fileName) {
//        fileName = StringUtils.encode(fileName + ".xls", "utf-8");
    	try {
			fileName = URLEncoder.encode(fileName + ".xls", "utf-8");
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        ServletActionContext.getResponse().setHeader("Cache-Control", "");
        ServletActionContext.getResponse().setContentType(
                "application/data;charset=UTF-8");
        ServletActionContext.getResponse().setHeader("Content-Disposition",
                "attachment; filename=" + fileName);
        OutputStream out = null;
        try {
            out = new BufferedOutputStream(ServletActionContext.getResponse()
                    .getOutputStream());
            workbook.write(out); // 输出文件
            out.flush();
        }
        catch (IOException e) {
            log.error(e.toString());
        }
        finally {
            FileUtils.close(out);
        }
    }
    
    /**
  	 * 组织文件下载
  	 * @param file 文件
  	 * @param fileName 下载的文件名
  	 * @author zhangkc
  	 * @throws IOException 
  	 * @date 2014年8月11日 下午3:16:08
  	 */
    public static void outputFile(File file, String fileName) throws IOException{
    	ServletActionContext.getResponse().setHeader("Cache-Control", "");
		ServletActionContext.getResponse().setContentType("application/data;charset=UTF-8");
		ServletActionContext.getResponse().setHeader("Content-Disposition", "attachment; filename="+ URLEncoder.encode(fileName, "UTF-8"));
		
		FileInputStream fromStream = null;
		BufferedOutputStream buff = null;
		try {
			buff = new BufferedOutputStream(ServletActionContext.getResponse()
					.getOutputStream());
			fromStream = new FileInputStream(file);
			byte[] buffer = new byte[4096];
			int bytes_read;
			while ((bytes_read = fromStream.read(buffer)) != -1) {
				buff.write(buffer, 0, bytes_read);
			}
			buff.flush();
		} finally {
			FileUtils.close(buff);
			FileUtils.close(fromStream);
		}
    }
    /**
	 * 组织文件下载
	 * @param filePath 文件绝对路径
	 * @param fileName 下载的文件名称
	 * @author zhangkc
	 * @throws IOException 
	 * @date 2014年8月11日 下午3:16:08
	 */
	public static void outputFile(String filePath, String fileName) throws IOException{
		outputFile(new File(filePath), fileName);
	}
	 /**
	 * 组织文件下载，以文件的原名称作为下载的文件名称
	 * @param filePath 文件的绝对路径
	 * @author zhangkc
	 * @throws IOException 
	 * @date 2014年8月11日 下午3:16:08
	 */
	public static void outputFile(String filePath) throws IOException{
		File file = new File(filePath);
		outputFile(file, file.getName());
	}
}
