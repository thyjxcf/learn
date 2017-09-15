package net.zdsoft.leadin.util.excel;

import java.awt.Color;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import net.zdsoft.keel.util.FileUtils;
import net.zdsoft.leadin.doc.DocumentHandler;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.poi.hssf.usermodel.HSSFPalette;
import org.apache.poi.hssf.usermodel.HSSFSheet;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.hssf.util.HSSFRegionUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.RegionUtil;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.struts2.ServletActionContext;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.fasterxml.jackson.databind.ObjectMapper;

public class ZdExcel {

    private List<ZdCell> heads;
    private boolean isXlsx = false;
    private Map<Integer, Integer> rowsMap;
    private Workbook wb;

    private List<ZdRow> rows = new ArrayList<ZdRow>();
    private Map<String, ZdStyle> styleMap = new HashMap<String, ZdStyle>();
    public Map<Integer, ZdColumn> columnMap = new HashMap<Integer, ZdColumn>();

    public Map<String, String> styleMarkMap = new HashMap<String, String>();

    /**
     * 创建Excel对象
     */
    public ZdExcel() {
        heads = new ArrayList<ZdCell>();
        rowsMap = new HashMap<Integer, Integer>();
    }

    /**
     * 根据参数，如果是true，则创建xlsx格式的excel文件（可支持100W行）
     * 
     * @param xslx
     */
    public ZdExcel(boolean xlsx) {
        heads = new ArrayList<ZdCell>();
        rowsMap = new HashMap<Integer, Integer>();
        this.isXlsx = xlsx;
    }

    /**
     * 这个mark后面要作为页面输出的classid
     * @param styleMark
     * @return
     */
    public String getMark(String styleMark) {
        String mark = styleMarkMap.get(styleMark);
        if (StringUtils.isBlank(mark)) {
            mark = "_" + UUID.randomUUID().toString();
            styleMarkMap.put(styleMark, mark);
        }
        return mark;
    }

    public void addColumnMap(Integer index, ZdColumn zdColumn) {
        columnMap.put(index, zdColumn);
    }

    public void addStyle(ZdStyle zdStyle) {
        styleMap.put(zdStyle.getStyleId(), zdStyle);
    }

    private int getRowIndex(int colIndex) {
        Integer rowI = rowsMap.get(colIndex);
        if (rowI == null)
            rowI = 0;
        return rowI;
    }

    private void setRowIndex(int colIndex, int rowIndex) {
        rowsMap.put(colIndex, rowIndex);
    }

    public void add(ZdRow zdRow) {
        rows.add(zdRow);
    }

    /**
     * 一行增加一个单元格
     * 
     * @param zdCell
     */
    public void add(ZdCell zdCell) {
        zdCell.setPosy(getRowIndex(zdCell.getPosx()));
        for (int i = 0; i < zdCell.getCols(); i++) {
            if (!ZdCell.ZDCELL_VALUE_NULL.equals(zdCell.getValue()))
                setRowIndex(zdCell.getPosx() + i,
                        getRowIndex(zdCell.getPosx() + i) + zdCell.getRows());
        }
        heads.add(zdCell);
    }

    /**
     * 在同一行内，增加多个单元格
     * 
     * @param zdCells
     */
    public void add(ZdCell[] zdCells) {
        int colIndex = 0;
        for (ZdCell zdCell : zdCells) {
            zdCell.setPosx(colIndex);
            colIndex += zdCell.getCols();
            zdCell.setPosy(getRowIndex(zdCell.getPosx()));
            for (int i = 0; i < zdCell.getCols(); i++) {
                if (!ZdCell.ZDCELL_VALUE_NULL.equals(zdCell.getValue()))
                    setRowIndex(zdCell.getPosx() + i,
                            getRowIndex(zdCell.getPosx() + i) + zdCell.getRows());
                else
                    setRowIndex(zdCell.getPosx() + i, getRowIndex(zdCell.getPosx() + i));
            }
            if (!ZdCell.ZDCELL_VALUE_NULL.equals(zdCell.getValue()))
                heads.add(zdCell);
        }
    }

    /**
     * 设置宽度
     * 
     * @param s
     *            要设置的sheet
     * @param cellIndex
     *            列的索引，从0开始
     * @param multipleDefaultWidth
     *            默认宽度的倍数
     */
    public void setCellWidth(Sheet s, int cellIndex, double multipleDefaultWidth) {
        s.setColumnWidth(
                cellIndex,
                Math.round(s.getDefaultColumnWidth() * 256
                        * NumberUtils.toFloat(multipleDefaultWidth + "")));
    }

    private byte transf(String src, String[] srcs, byte[] descs) {
        if (StringUtils.isBlank(src))
            return 0;
        for (int i = 0; i < srcs.length; i++) {
            if (StringUtils.equals(src, srcs[i])) {
                return descs[i];
            }
        }
        return 0;
    }

    private short transf(String src, String[] srcs, short[] descs) {
        if (StringUtils.isBlank(src))
            return 0;
        for (int i = 0; i < srcs.length; i++) {
            if (StringUtils.equalsIgnoreCase(src, srcs[i])) {
                return descs[i];
            }
        }
        return 0;
    }

    public CellStyle convertStyle(Sheet sheet, ZdStyle zdStyle) {
        if (wb == null) {
            wb = isXlsx ? new XSSFWorkbook() : new HSSFWorkbook();
        }
        wb = sheet.getWorkbook();
        CellStyle style = wb.createCellStyle();
        ZdAlignment align = zdStyle.getZdAlignment();
        if (align != null) {
            short s = transf(align.getHorizontal(), new String[] { "Left", "Right", "Center" },
                    new short[] { CellStyle.ALIGN_LEFT, CellStyle.ALIGN_RIGHT,
                            CellStyle.ALIGN_CENTER });
            style.setAlignment(s);

            s = transf(align.getVertical(), new String[] { "Top", "Center", "Bottom" },
                    new short[] { CellStyle.VERTICAL_TOP, CellStyle.VERTICAL_CENTER,
                            CellStyle.VERTICAL_BOTTOM });
            style.setVerticalAlignment(s);

            style.setWrapText(BooleanUtils.toBoolean(align.getWrapText()));
        }

        List<ZdBorder> zdBorders = zdStyle.getZdBorders();
        for (ZdBorder zdBorder : zdBorders) {
            if (zdBorder.getWeight() > 0) {
                if (StringUtils.equalsIgnoreCase(zdBorder.getPosition(), "Bottom")) {
                    style.setBorderBottom(CellStyle.BORDER_THIN);
                }
                else if (StringUtils.equalsIgnoreCase(zdBorder.getPosition(), "Top")) {
                    style.setBorderTop(CellStyle.BORDER_THIN);
                }
                else if (StringUtils.equalsIgnoreCase(zdBorder.getPosition(), "Left")) {
                    style.setBorderLeft(CellStyle.BORDER_THIN);
                }
                else if (StringUtils.equalsIgnoreCase(zdBorder.getPosition(), "Right")) {
                    style.setBorderRight(CellStyle.BORDER_THIN);
                }
            }
        }

        String numberFormat = zdStyle.getNumberFormat();
        if (StringUtils.isNotBlank(numberFormat)) {
            DataFormat format = wb.createDataFormat();
            style.setDataFormat(format.getFormat(numberFormat));
        }

        ZdInterior interior = zdStyle.getZdInterior();
        if (interior != null) {
            String color = interior.getColor();
            if (StringUtils.isNotBlank(color)) {
                int red = Integer.parseInt(StringUtils.substring(color, 1, 3), 16);
                int green = Integer.parseInt(StringUtils.substring(color, 3, 5), 16);
                int blue = Integer.parseInt(StringUtils.substring(color, 5, 7), 16);
                if (sheet instanceof HSSFSheet) {
                    HSSFPalette cusColor = ((HSSFWorkbook) sheet.getWorkbook()).getCustomPalette();
                    style.setFillForegroundColor(cusColor.findSimilarColor(red, green, blue).getIndex());
                    style.setFillPattern(CellStyle.SOLID_FOREGROUND);
                }
                else {
                    ((XSSFCellStyle) style).setFillForegroundColor(new XSSFColor(
                                                                                 Color.decode(color)));
                    ((XSSFCellStyle) style).setFillPattern(CellStyle.SOLID_FOREGROUND);
                }
            }
        }

        ZdFont zdFont = zdStyle.getZdFont();
        if (zdFont != null) {
            Font font = wb.createFont();
            font.setFontHeightInPoints((short) zdFont.getSize());
            try {
                font.setCharSet(zdFont.getCharset());
            }
            catch (Exception e) {
                font.setCharSet(Font.DEFAULT_CHARSET);
            }
            font.setFontName(zdFont.getName());
            if (zdFont.getBold() == 1) {
                font.setBoldweight(Font.BOLDWEIGHT_BOLD);
            }
            if (zdFont.getItalic() == 1) {
                font.setItalic(true);
            }
            if (StringUtils.isNotBlank(zdFont.getUnderline())) {
                font.setUnderline(transf(zdFont.getUnderline(),
                        new String[] { "Single", "Double" }, new byte[] { Font.U_SINGLE,
                                Font.U_DOUBLE }));
            }

            String color = zdFont.getColor();
            if (StringUtils.isNotBlank(color)) {
                int red = Integer.parseInt(StringUtils.substring(color, 1, 3), 16);
                int green = Integer.parseInt(StringUtils.substring(color, 3, 5), 16);
                int blue = Integer.parseInt(StringUtils.substring(color, 5, 7), 16);
                if (sheet instanceof HSSFSheet) {
                    HSSFPalette cusColor = ((HSSFWorkbook) sheet.getWorkbook()).getCustomPalette();
                    font.setColor(cusColor.findSimilarColor(red, green, blue).getIndex());
                }
                else {
                    if (StringUtils.equals("#000000", color))
                        color = "#000001";
                    ((XSSFFont) font).setColor(new XSSFColor(Color.decode(color)));
                }
            }
            style.setFont(font);
        }
        return style;
    }

    public void syncXml(InputStream is) throws Exception {
        SAXReader saxReader = new SAXReader();
        Document document = saxReader.read(is);
        readXml(document);
    }

    public void syncExcel(File xlsFile, String jsonStr) throws Exception {
        Map dataMap = null;
        if (StringUtils.isNotBlank(jsonStr)) {
            ObjectMapper mapper = new ObjectMapper();
            dataMap = mapper.readValue(jsonStr, HashMap.class);
        }
        int colSize = 0;
        int rowSize = 0;
        Workbook wb;
        if (FilenameUtils.getExtension(xlsFile.getName()).equalsIgnoreCase("xlsx")) {
            wb = new XSSFWorkbook(new FileInputStream(xlsFile));
        }
        else {
            wb = new HSSFWorkbook(new FileInputStream(xlsFile));
        }

        Sheet sheet = wb.getSheetAt(0);
        rowSize = sheet.getLastRowNum();

        Map<String, String> regionMap = new HashMap<String, String>();
        Set<String> cellIgnoreSet = new HashSet<String>();
        int sheetMergerCount = sheet.getNumMergedRegions();
        int maxColumn = 0;
        for (int i = 0; i < sheetMergerCount; i++) {
            CellRangeAddress cra = (CellRangeAddress) sheet.getMergedRegion(i);
            int firstRow = cra.getFirstRow(); // 合并单元格CELL起始行
            int firstCol = cra.getFirstColumn(); // 合并单元格CELL起始列
            int lastRow = cra.getLastRow();
            int lastCol = cra.getLastColumn();
            for (int j = firstRow; j <= lastRow; j++) {
                for (int k = firstCol; k <= lastCol; k++) {
                    if (j == firstRow && k == firstCol)
                        continue;
                    cellIgnoreSet.add(j + "_" + k);
                }
            }
            regionMap.put(firstRow + "_" + firstCol,
                    (cra.getLastRow() - firstRow) + "_" + (cra.getLastColumn() - firstCol));
        }

        int rowIndex = 0;
        //适用于格式规则的表格
        int maxColumnSize = 0;
        for (int i = 0; i <= rowSize; i++) {
            ZdRow zdRow = new ZdRow();
            Row row = sheet.getRow(i);
            if (row == null) {
                rowIndex = i + 1;
                continue;
            }
            short lastCell = row.getLastCellNum();
            if (lastCell > maxColumn)
                maxColumn = lastCell;
            // zdRow.setAutoFitHeight(autoFitHeight)
            zdRow.setHeight(row.getHeightInPoints());
            zdRow.setIndex(rowIndex);
            rowIndex = 0;
            colSize = row.getLastCellNum();
            if(maxColumnSize < colSize) {
                maxColumnSize = colSize;
            }
            else {
                colSize = maxColumnSize;
            }
            for (int j = 0; j < colSize; j++) {
                if (cellIgnoreSet.contains(i + "_" + j)) {
                    continue;
                }
                ZdStyle zdStyle = new ZdStyle();
                ZdCell zdCell = new ZdCell();
                Cell cell = row.getCell(j);
                if (cell == null) {
                    cell = row.createCell(j);
                }
                CellStyle style = cell.getCellStyle();
                style.getAlignment();

                ZdAlignment zdAlignment = new ZdAlignment();
                zdAlignment.setVertical(ZdVAlignmentEmun.getEmun(style.getVerticalAlignment()).getTypeString());
                zdAlignment.setHorizontal(ZdAlignmentEmun.getEmun(style.getAlignment()).getTypeString());
                zdAlignment.setWrapText(BooleanUtils.toInteger(style.getWrapText()));
                zdStyle.setZdAlignment(zdAlignment);

                short s = style.getBorderBottom();
                if (s > 0) {
                    ZdBorder border = new ZdBorder();
                    border.setPosition(ZdBorderEmun.BOTTOM.getTypeString());
                    border.setWeight(CellStyle.BORDER_THIN);
                    zdStyle.addBorder(border);
                }
                s = style.getBorderLeft();
                if (s > 0) {
                    ZdBorder border = new ZdBorder();
                    border.setPosition(ZdBorderEmun.LEFT.getTypeString());
                    border.setWeight(CellStyle.BORDER_THIN);
                    zdStyle.addBorder(border);
                }
                s = style.getBorderRight();
                if (s > 0) {
                    ZdBorder border = new ZdBorder();
                    border.setPosition(ZdBorderEmun.RIGHT.getTypeString());
                    border.setWeight(CellStyle.BORDER_THIN);
                    zdStyle.addBorder(border);
                }
                s = style.getBorderTop();
                if (s > 0) {
                    ZdBorder border = new ZdBorder();
                    border.setPosition(ZdBorderEmun.TOP.getTypeString());
                    border.setWeight(CellStyle.BORDER_THIN);
                    zdStyle.addBorder(border);
                }

                Font font = wb.getFontAt(style.getFontIndex());

                ZdFont zdFont = new ZdFont();
                zdFont.setName(font.getFontName());
                zdFont.setBold(font.getBoldweight() == Font.BOLDWEIGHT_BOLD ? 1 : 0);
                zdFont.setCharset(font.getCharSet());
                zdFont.setItalic(BooleanUtils.toInteger(font.getItalic()));
                zdFont.setSize(font.getFontHeightInPoints());
                String colorHex;
                if (wb instanceof HSSFWorkbook) {
                    HSSFPalette cusColor = ((HSSFWorkbook) sheet.getWorkbook()).getCustomPalette();
                    HSSFColor color = cusColor.getColor(font.getColor());
					if (color == null || color.getIndex() == HSSFColor.AUTOMATIC.index)
						colorHex = "#000000";// black
					else {
						short[] rgb = color.getTriplet();
						colorHex = "#"
								+ StringUtils.leftPad(
										Integer.toHexString(rgb[0]), 2,
										Integer.toHexString(rgb[0]))
								+ StringUtils.leftPad(
										Integer.toHexString(rgb[1]), 2,
										Integer.toHexString(rgb[1]))
								+ StringUtils.leftPad(
										Integer.toHexString(rgb[2]), 2,
										Integer.toHexString(rgb[2]));
					}
                }
                else {
                    XSSFFont xf = (XSSFFont) font;
                    int length = StringUtils.length(xf.getXSSFColor().getARGBHex());
                    colorHex = "#"
                            + StringUtils.substring(xf.getXSSFColor().getARGBHex(),
                                    (length >= 6 ? length : 6) - 6, length);
                }
                zdFont.setColor(colorHex);
                zdFont.setUnderline(ZdFontEmun.getEmun(font.getUnderline()).getTypeString());
                zdStyle.setZdFont(zdFont);

                colorHex = null;
                ZdInterior interior = new ZdInterior();
                if (wb instanceof HSSFWorkbook) {
                    HSSFColor color = (HSSFColor) style.getFillForegroundColorColor();

                    if (color != null) {
                        if (color.getIndex() == HSSFColor.AUTOMATIC.index)
                            colorHex = "#FFFFFF";
                        else {
                            short[] rgb = color.getTriplet();
                            colorHex = "#"
                                    + StringUtils.leftPad(Integer.toHexString(rgb[0]), 2,
                                            Integer.toHexString(rgb[0]))
                                    + StringUtils.leftPad(Integer.toHexString(rgb[1]), 2,
                                            Integer.toHexString(rgb[1]))
                                    + StringUtils.leftPad(Integer.toHexString(rgb[2]), 2,
                                            Integer.toHexString(rgb[2]));
                        }
                    }
                }
                else {
                    XSSFColor color = (XSSFColor) style.getFillForegroundColorColor();
                    if (color != null) {
                        int length = StringUtils.length(color.getARGBHex());
                        colorHex = "#"
                                + StringUtils.substring(color.getARGBHex(), (length >= 6 ? length
                                        : 6) - 6, length);
                    }
                }

                interior.setColor(colorHex);
                zdStyle.setZdInterior(interior);

                String mark = getMark(zdStyle.getStyleMark());
                if (styleMap.get(mark) == null) {
                    styleMap.put(mark, zdStyle);
                }
                zdCell.setStyleId(mark);
                String result = regionMap.get(i + "_" + j);
                if (StringUtils.isNotBlank(result)) {
                    zdCell.setMergeAcross(NumberUtils.toInt(StringUtils.substringAfter(result, "_")));
                    zdCell.setMergeDown(NumberUtils.toInt(StringUtils.substringBefore(result, "_")));
                }
                zdCell.setIndex(j + 1);
                cell.setCellType(Cell.CELL_TYPE_STRING);
                String value = cell.getStringCellValue();
                if (dataMap != null && StringUtils.contains(value, "${")
                        && StringUtils.contains(value, "}")) {
                    String ver = StringUtils.substringBetween(value, "${", "}");
                    String v = value;
                    while (StringUtils.isNotBlank(ver)) {
                        String[] splits = StringUtils.split(ver, ".");
                        Map dataMap2 = dataMap;
                        Object o = null;
                        for (int spi = 0; spi < splits.length; spi++) {
                            o = dataMap2.get(splits[spi]);
                            if (o != null) {
                                if (o instanceof Map) {
                                    dataMap2 = (Map) o;
                                }
                            }
                        }
                        if (o != null) {
                            v = StringUtils.replaceOnce(v, "${" + ver + "}", o.toString());
                        }
                        ver = StringUtils.substringBetween(v, "${", "}");
                    }
                    value = v;
                }
                zdCell.setValue(value);
                zdCell.setType(ZdTypeEmun.getZdType(cell.getCellType()).getTypeString());

                zdRow.addCell(zdCell);
            }
            add(zdRow);
        }

        for (int i = 0; i < maxColumn; i++) {
            ZdColumn column = new ZdColumn();
            column.setWidth(sheet.getColumnWidth(i));
            addColumnMap(i, column);
        }
        return;

    }

    public void syncExcel(File xlsFile) throws Exception {
        syncExcel(xlsFile, null);
    }

    public void syncXml(File xmlFile) throws Exception {
        SAXReader saxReader = new SAXReader();
        Document document = saxReader.read(xmlFile);
        readXml(document);
    }

    @SuppressWarnings("unchecked")
    public void readXml(Document document) throws Exception {
        Element root = document.getRootElement();
        Element styless = root.element("Styles");
        if (styless != null) {
            List<Element> styles = styless.elements("Style");
            for (Element style : styles) {
                ZdStyle zdStyle = new ZdStyle();
                Attribute attribute = style.attribute("ID");
                if (attribute != null)
                    zdStyle.setStyleId(attribute.getValue());

                Element alignment = style.element("Alignment");
                if (alignment != null) {
                    ZdAlignment zdAlignment = new ZdAlignment();
                    attribute = alignment.attribute("Vertical");
                    if (attribute != null)
                        zdAlignment.setVertical(attribute.getValue());
                    attribute = alignment.attribute("Horizontal");
                    if (attribute != null)
                        zdAlignment.setHorizontal(attribute.getValue());
                    attribute = alignment.attribute("WrapText");
                    if (attribute != null)
                        zdAlignment.setWrapText(NumberUtils.toInt(attribute.getValue()));

                    zdStyle.setZdAlignment(zdAlignment);
                }

                Element borderss = style.element("Borders");
                if (borderss != null) {
                    List<Element> borders = borderss.elements("Border");
                    for (Element border : borders) {
                        ZdBorder zdBorder = new ZdBorder();
                        attribute = border.attribute("Position");
                        if (attribute != null)
                            zdBorder.setPosition(attribute.getValue());
                        attribute = border.attribute("LineStyle");
                        if (attribute != null)
                            zdBorder.setLineStyle(attribute.getValue());
                        attribute = border.attribute("Weight");
                        if (attribute != null)
                            zdBorder.setWeight(NumberUtils.toInt(attribute.getValue()));
                        zdStyle.addBorder(zdBorder);
                    }
                }

                Element font = style.element("Font");
                if (font != null) {
                    ZdFont zdFont = new ZdFont();

                    attribute = font.attribute("FontName");
                    if (attribute != null)
                        zdFont.setName(attribute.getValue());

                    attribute = font.attribute("Bold");
                    if (attribute != null)
                        zdFont.setBold(NumberUtils.toInt(attribute.getValue()));
                    attribute = font.attribute("Underline");
                    if (attribute != null)
                        zdFont.setUnderline(attribute.getValue());
                    attribute = font.attribute("Italic");
                    if (attribute != null)
                        zdFont.setItalic(NumberUtils.toInt(attribute.getValue()));

                    attribute = font.attribute("Color");
                    if (attribute != null)
                        zdFont.setColor(attribute.getValue());

                    attribute = font.attribute("CharSet");
                    if (attribute != null)
                        zdFont.setCharset(NumberUtils.toShort(attribute.getValue()));

                    attribute = font.attribute("Size");
                    if (attribute != null)
                        zdFont.setSize(NumberUtils.toShort(attribute.getValue()));

                    zdStyle.setZdFont(zdFont);
                }

                Element numberFormat = style.element("NumberFormat");
                if (numberFormat != null) {
                    attribute = numberFormat.attribute("Format");
                    if (attribute != null)
                        zdStyle.setNumberFormat(attribute.getValue());
                }

                Element interior = style.element("Interior");
                if (interior != null) {
                    ZdInterior zdInterior = new ZdInterior();

                    attribute = interior.attribute("Color");
                    if (attribute != null)
                        zdInterior.setColor(attribute.getValue());
                    attribute = interior.attribute("Pattern");
                    if (attribute != null)
                        zdInterior.setPattern(attribute.getValue());
                    zdStyle.setZdInterior(zdInterior);
                }

                addStyle(zdStyle);
            }
        }

        Element ele = root.element("Worksheet");
        Element table = ele.element("Table");
        List<Element> columns = table.elements("Column");

        int index = 0;
        for (Element col : columns) {
            ZdColumn zdColumn = new ZdColumn();
            Attribute attribute = col.attribute("Index");
            if (attribute != null) {
                index = NumberUtils.toInt(attribute.getValue());
            }
            attribute = col.attribute("Width");
            if (attribute != null) {
                zdColumn.setWidth(NumberUtils.toFloat(attribute.getValue()) * 50);
            }
            attribute = col.attribute("AutoFitWidth");
            if (attribute != null) {
                zdColumn.setAutoFitWidth(NumberUtils.toInt(attribute.getValue()));
            }
            attribute = col.attribute("StyleID");
            if (attribute != null) {
                zdColumn.setStyleId(attribute.getValue());
            }
            addColumnMap(index, zdColumn);
            index++;

        }
        List<Element> rows = table.elements("Row");
        for (Element row : rows) {
            ZdRow zdRow = new ZdRow();
            Attribute attribute = row.attribute("AutoFitHeight");
            if (attribute != null)
                zdRow.setAutoFitHeight(NumberUtils.toInt(attribute.getValue()));
            attribute = row.attribute("Height");
            if (attribute != null)
                zdRow.setHeight(NumberUtils.toFloat(attribute.getValue()));
            attribute = row.attribute("Index");
            if (attribute != null)
                zdRow.setIndex(NumberUtils.toInt(attribute.getValue()));
            attribute = row.attribute("StyleID");
            if (attribute != null)
                zdRow.setStyleId(attribute.getValue());
            List<Element> cells = row.elements("Cell");
            for (Element cell : cells) {
                ZdCell zdCell = new ZdCell();
                attribute = cell.attribute("MergeAcross");
                if (attribute != null)
                    zdCell.setMergeAcross(NumberUtils.toInt(attribute.getValue()));
                attribute = cell.attribute("MergeDown");
                if (attribute != null)
                    zdCell.setMergeDown(NumberUtils.toInt(attribute.getValue()));
                attribute = cell.attribute("Index");
                if (attribute != null)
                    zdCell.setIndex(NumberUtils.toInt(attribute.getValue()));
                attribute = cell.attribute("StyleID");
                if (attribute != null)
                    zdCell.setStyleId(attribute.getValue());
                Element data = cell.element("Data");
                if (data != null) {
                    zdCell.setValue(data.getText());
                    attribute = data.attribute("Type");
                    if (attribute != null)
                        zdCell.setType(attribute.getValue());
                }
                zdRow.addCell(zdCell);
            }
            add(zdRow);
        }
    }

    public String createHtml(String templateFile, Map<String, Object> dataMap) {
        InputStream is;
        try {
            is = DocumentHandler.createDocInputStream(dataMap, templateFile);
            syncXml(is);
            return createHtml();
        }
        catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public String createHtml() {
        return createHtml(null);
    }

    public String createHtml(String sheetName) {
        Sheet s = null;
        if (StringUtils.isNotBlank(sheetName)) {
            if (wb == null) {
                wb = isXlsx ? new XSSFWorkbook() : new HSSFWorkbook();
            }
            s = wb.getSheet(sheetName);
        }

        if (s == null) {
            Workbook workbook = new HSSFWorkbook();
            s = workbook.createSheet();
        }
        StringBuffer sb = new StringBuffer();
        sb.append("<style>");
        ZdStyle zdStyle = null;
        String styleStr;
        for (String styleId : styleMap.keySet()) {
            styleStr = "";
            sb.append("." + styleId + "{");
            zdStyle = styleMap.get(styleId);
            List<ZdBorder> borders = zdStyle.getZdBorders();
            if (CollectionUtils.isNotEmpty(borders))
                styleStr += "border-color:#000;";
            for (ZdBorder border : borders) {
                if (StringUtils.equals(border.getPosition(), "Bottom")) {
                    styleStr += "border-bottom-width:1px;border-bottom-style:solid;";
                }
                else if (StringUtils.equals(border.getPosition(), "Top")) {
                    styleStr += "border-top-width:1px;border-top-style:solid;";
                }
                else if (StringUtils.equals(border.getPosition(), "Left")) {
                    styleStr += "border-left-width:1px;border-left-style:solid;";
                }
                else if (StringUtils.equals(border.getPosition(), "Right")) {
                    styleStr += "border-right-width:1px;border-right-style:solid;";
                }
            }
            ZdAlignment align = zdStyle.getZdAlignment();
            if (align != null) {
                styleStr += " text-align:" + align.getHorizontal() + ";vertical-align:"
                        + align.getVertical() + ";";
            }

            ZdInterior zdInterior = zdStyle.getZdInterior();
            if (zdInterior != null) {
                styleStr += "background-color:" + zdInterior.getColor() + ";";
            }

            ZdFont zdFont = zdStyle.getZdFont();
            if (zdFont != null) {
                if (zdFont.getBold() == 1) {
                    styleStr += "font-weight:bold;";
                }
                if (StringUtils.isNotBlank(zdFont.getColor())) {
                    styleStr += "color:" + zdFont.getColor() + ";";
                }
                if (StringUtils.isNotBlank(zdFont.getName())) {
                    styleStr += "font-family:" + zdFont.getName() + ";";
                }
                int size = zdFont.getSize();
                if (size > 0) {
                    styleStr += "font-size:" + size + "pt;";
                }
                if (zdFont.getItalic() == 1) {
                    styleStr += "font-style:italic;";
                }
            }
            sb.append(styleStr).append("}");
        }
        sb.append("</style>");
        sb.append("<span id='htmlTable'><table width='100%' style=\"border-width:1px 0 0 1px;margin:2px 0 2px 0;border-collapse:collapse;\">");
        int rowIndex = 0;
        List<String> points = new ArrayList<String>();
        Map<Integer, Integer> columnWidthMap = new HashMap<Integer, Integer>();

        for (int index : columnMap.keySet()) {
            ZdColumn zdColumn = columnMap.get(index);
            int columnWidth;
            if (zdColumn == null || zdColumn.getWidth() == 0) {
                columnWidth = s.getDefaultColumnWidth();
            }
            else {
                columnWidth = (int) zdColumn.getWidth();
            }
            columnWidthMap.put(index, columnWidth);
        }

        for (int i = 0; i < rows.size(); i++) {
            ZdRow row = rows.get(i);
            if (row.getIndex() > 0) {
                for (int in = rowIndex; in < row.getIndex(); in++) {
                    sb.append("<tr height='").append(s.getDefaultRowHeightInPoints()).append(
                            "'><td></td><tr>");
                }
            }
            else {
                row.setIndex(rowIndex + 1);
            }

            if (row.getHeight() > 0)
                sb.append("<tr height='").append(row.getHeight()).append("'>");
            else
                sb.append("<tr height='").append(s.getDefaultRowHeightInPoints()).append("'>");

            List<ZdCell> cells = row.getZdCells();
            int colIndex = 0;
            String tdStr = "";

            for (int j = 0; j < cells.size(); j++) {
                ZdCell cell = cells.get(j);
                if (cell.getIndex() > 0) {
                    for (int in = colIndex; in < cell.getIndex() - 1; in++) {
                        if (!points.contains(rowIndex + "-" + in)) {
                            sb.append("<td></td>");
                        }
                    }
                }
                else {
                    cell.setIndex(colIndex + 1);
                }

                tdStr += "<td class='" + cell.getStyleId() + "'";

                Integer columnWidth = 0;
                if (cell.getMergeAcross() > 0) {
                    for (int io = cell.getIndex(); io < cell.getIndex() + cell.getMergeAcross() + 1; io++) {
                        columnWidth += columnWidthMap.get(io - 1) == null ? s.getDefaultColumnWidth()
                                : columnWidthMap.get(io - 1);
                    }
                }
                else {
                    columnWidth = columnWidthMap.get(cell.getIndex() - 1);
                }
                if (columnWidth == null)
                    columnWidth = s.getDefaultColumnWidth() * 5;
                tdStr += " width='" + (columnWidth * 1.5) + "'";
                int index = cell.getIndex();
                if (index == 0) {
                    index = j;
                }

                if (cell.getMergeAcross() > 0) {
                    tdStr += " colspan='" + (cell.getMergeAcross() + 1) + "'";
                    if (cell.getMergeDown() == 0) {
                        for (int ii = colIndex; ii < colIndex + cell.getMergeAcross() + 1; ii++) {
                            points.add(rowIndex + "-" + ii);
                        }
                    }
                }
                if (cell.getMergeDown() > 0) {
                    tdStr += " rowspan='" + (cell.getMergeDown() + 1) + "'";
                    if (cell.getMergeAcross() == 0) {
                        int tempIndex = cell.getIndex() > 0 ? cell.getIndex() - 1 : colIndex;
                        for (int ii = rowIndex; ii < rowIndex + cell.getMergeDown() + 1; ii++) {
                            points.add(ii + "-" + tempIndex);
                        }
                    }
                }

                if (cell.getMergeAcross() > 0 && cell.getMergeDown() > 0) {
                    int tempIndex = cell.getIndex() > 0 ? cell.getIndex() - 1 : colIndex;
                    for (int ii = rowIndex; ii < rowIndex + cell.getMergeDown() + 1; ii++) {
                        for (int ic = tempIndex; ic < tempIndex + cell.getMergeAcross() + 1; ic++) {
                            points.add(ii + "-" + ic);
                        }
                    }
                }

                String value = StringUtils.equals(cell.getValue(), ZdCell.ZDCELL_VALUE_NULL) ? ""
                        : cell.getValue();
                if (StringUtils.equals(cell.getType(), "DateTime")
                        && StringUtils.contains(value, "T00:00:00.000")) {
                    value = value.replace("T00:00:00.000", "");
                }
                tdStr += ">" + value + "</td>";

                if (cell.getIndex() > 0)
                    colIndex = cell.getIndex() - 1;

                if (cell.getMergeAcross() > 0)
                    colIndex += cell.getMergeAcross();
                colIndex++;
            }
            sb.append(tdStr).append("</tr>");
            if (row.getIndex() > 0)
                rowIndex = row.getIndex() - 1;
            rowIndex++;
        }
        sb.append("</table></span>");
        return sb.toString();
    }

    public Sheet createSheetByXml(String sheetName, String templateFile, Map<String, Object> dataMap) {
        InputStream is;
        try {
            is = DocumentHandler.createDocInputStream(dataMap, templateFile);
            syncXml(is);
            return createSheetByXml(sheetName);
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Sheet createSheetByXml(String sheetName) {
        if (wb == null) {
            wb = isXlsx ? new XSSFWorkbook() : new HSSFWorkbook();
        }
        Sheet s = wb.createSheet();
        Row r = null;
        Cell c = null;
        wb.setSheetName(wb.getNumberOfSheets() - 1, sheetName);
        Map<String, CellStyle> cellStyleMap = new HashMap<String, CellStyle>();
        for (int i = 0; i < rows.size(); i++) {
            ZdRow row = rows.get(i);
            r = s.createRow(row.getIndex() > 0 ? row.getIndex() : i);
            if (row.getHeight() > 0)
                r.setHeightInPoints(row.getHeight());
            List<ZdCell> cells = row.getZdCells();
            int colIndex = 0;
            for (int j = 0; j < cells.size(); j++) {
                ZdCell cell = cells.get(j);
                if (cell.getIndex() > 0)
                    colIndex = cell.getIndex() - 1;
                c = r.createCell(colIndex);
                colIndex += 1 + cell.getMergeAcross();
                if (StringUtils.isNotBlank(cell.getType()))
                    c.setCellType(ZdTypeEmun.getZdType(cell.getType()).getType());

                int index = cell.getIndex();
                if (index == 0) {
                    index = c.getColumnIndex();
                }
                if (cell.getMergeAcross() > 0 || cell.getMergeDown() > 0) {
                    s.addMergedRegion(new CellRangeAddress(c.getRowIndex(), c.getRowIndex()
                            + cell.getMergeDown(), c.getColumnIndex(), c.getColumnIndex()
                            + cell.getMergeAcross()));
                }

                String styleId = cell.getStyleId();
                CellStyle style = cellStyleMap.get(styleId);
                ZdStyle zdStyle = styleMap.get(styleId);
                if (style == null) {
                    if (zdStyle != null) {
                        style = convertStyle(s, zdStyle);
                        cellStyleMap.put(styleId, style);
                    }
                }
                if (style != null) {
                    c.setCellStyle(style);
                }
                String value = StringUtils.equals(cell.getValue(), ZdCell.ZDCELL_VALUE_NULL) ? ""
                        : cell.getValue();
                if (StringUtils.equals(cell.getType(), "DateTime")
                        && StringUtils.contains(value, "T00:00:00.000")) {
                    value = value.replace("T00:00:00.000", "");
                }

                c.setCellValue(value);
            }
        }
        for (int index : columnMap.keySet()) {
            s.setColumnWidth(index, (int) columnMap.get(index).getWidth());
        }

        int sheetMergerCount = s.getNumMergedRegions();
        for (int i = 0; i < sheetMergerCount; i++) {
            CellRangeAddress cra = (CellRangeAddress) s.getMergedRegion(i);
            int firstRow = cra.getFirstRow(); // 合并单元格CELL起始行
            int firstCol = cra.getFirstColumn(); // 合并单元格CELL起始列
            CellStyle style = s.getRow(firstRow).getCell(firstCol).getCellStyle();
            if (style != null) {
                RegionUtil.setBorderBottom((int) style.getBorderBottom(), cra, s, wb);
                RegionUtil.setBorderTop((int) style.getBorderTop(), cra, s, wb);
                RegionUtil.setBorderLeft((int) style.getBorderLeft(), cra, s, wb);
                RegionUtil.setBorderRight((int) style.getBorderRight(), cra, s, wb);
            }
        }

        return s;
    }

    /**
     * 单元格增加完毕后，创建sheet
     * 
     * @param sheetName
     * @throws IOException
     */
    public Sheet createSheet(String sheetName) {
        if (wb == null) {
            wb = isXlsx ? new XSSFWorkbook() : new HSSFWorkbook();
        }
        Sheet s = wb.createSheet();
        Row r = null;
        Cell c = null;
        wb.setSheetName(wb.getNumberOfSheets() - 1, sheetName);

        Map<Integer, Row> map = new HashMap<Integer, Row>();
        for (ZdCell zdcell : heads) {
            r = map.get(zdcell.getPosy());
            if (r == null) {
                r = s.createRow(zdcell.getPosy());
                map.put(zdcell.getPosy(), r);
            }
            c = r.createCell((short) zdcell.getPosx());
            c.setCellValue(zdcell.getValue());

            if (zdcell.getRows() > 1 || zdcell.getCols() > 1) {
                s.addMergedRegion(new CellRangeAddress(zdcell.getPosy(), zdcell.getPosy()
                        + zdcell.getRows() - 1, zdcell.getPosx(), zdcell.getPosx()
                        + zdcell.getCols() - 1));
            }

            // if (zdcell.getCols() > 1) {
            // s.autoSizeColumn(zdcell.getPosx(), true);
            // }
            // else {
            // s.autoSizeColumn(c.getColumnIndex());
            // }
            if (s.getColumnWidth(c.getColumnIndex()) < s.getDefaultColumnWidth() * 256) {
                s.setColumnWidth(c.getColumnIndex(), s.getDefaultColumnWidth() * 256);
            }
        }
        Map<String, CellStyle> cellStyleMap = new HashMap<String, CellStyle>();

        for (ZdCell zdcell : heads) {
            r = map.get(zdcell.getPosy());
            c = r.getCell(zdcell.getPosx());
            ZdStyle zdstyle = zdcell.getZdStyle();
            if (zdcell.getRows() > 1 || zdcell.getCols() > 1) {
                CellRangeAddress region = new CellRangeAddress(zdcell.getPosy(), // first
                                                                                 // row(0-based)
                                                               zdcell.getPosy() + zdcell.getRows()
                                                                       - 1, // last
                                                                            // row
                                                               zdcell.getPosx(), // first
                                                                                 // column
                                                                                 // (0-based)
                                                               zdcell.getPosx() + zdcell.getCols()
                                                                       - 1 // last
                                                                           // column
                );
                if (!isXlsx && zdstyle != null && zdstyle.isBorder()) {
                    HSSFRegionUtil.setBorderBottom((int) CellStyle.BORDER_THIN, region,
                            (HSSFSheet) s, (HSSFWorkbook) wb);
                    HSSFRegionUtil.setBorderTop((int) CellStyle.BORDER_THIN, region, (HSSFSheet) s,
                            (HSSFWorkbook) wb);
                    HSSFRegionUtil.setBorderLeft((int) CellStyle.BORDER_THIN, region,
                            (HSSFSheet) s, (HSSFWorkbook) wb);
                    HSSFRegionUtil.setBorderRight((int) CellStyle.BORDER_THIN, region,
                            (HSSFSheet) s, (HSSFWorkbook) wb);
                }
            }
            if (zdstyle != null) {
                String fontMark = getMark(zdstyle.getStyleMark());
                CellStyle cs = cellStyleMap.get(fontMark);
                if (cs == null) {
                    cs = wb.createCellStyle();
                    Font f = wb.createFont();
                    if (zdstyle.isBold()) {
                        f.setBoldweight(Font.BOLDWEIGHT_BOLD);
                    }
                    f.setFontHeightInPoints(zdstyle.getFontSize());
                    cs.setAlignment(zdstyle.getAlign());
                    cs.setFont(f);
                    cs.setVerticalAlignment(zdstyle.getVertical());
                    if (zdstyle.isBorder()) {
                        cs.setBorderBottom(CellStyle.BORDER_THIN);
                        cs.setBorderTop(CellStyle.BORDER_THIN);
                        cs.setBorderLeft(CellStyle.BORDER_THIN);
                        cs.setBorderRight(CellStyle.BORDER_THIN);
                    }
                    if (zdstyle.iswWrapText()) {
                        cs.setWrapText(true);
                    }
                    cellStyleMap.put(fontMark, cs);
                }
                c.setCellStyle(cs);
            }
            // if (zdcell.getCols() > 1) {
            // s.autoSizeColumn(zdcell.getPosx(), true);
            // }
            // else {
            // s.autoSizeColumn(c.getColumnIndex());
            // }
            if (s.getColumnWidth(c.getColumnIndex()) < s.getDefaultColumnWidth() * 256) {
                s.setColumnWidth(c.getColumnIndex(), s.getDefaultColumnWidth() * 256);
            }
        }
        heads = new ArrayList<ZdCell>();
        rowsMap = new HashMap<Integer, Integer>();
        return s;
    }

    /**
     * 导出到具体路径
     * 
     * @param fileName
     *            文件名(可以不带后缀，自动根据类型加上)
     * @param dirPath
     *            路径名
     */
    public void exportToFile(String fileName, String dirPath) {
        if (wb == null) {
            wb = isXlsx ? new XSSFWorkbook() : new HSSFWorkbook();
        }
        String postfix = "";
        if (wb instanceof XSSFWorkbook) {
            postfix = ".xlsx";
        }
        else {
            postfix = ".xls";
        }
        FileOutputStream out;
        try {
            int lastIndex = fileName.lastIndexOf(".");
            String postfix2 = fileName.substring(lastIndex + 1, fileName.length());
            if (!"xlsx".equalsIgnoreCase(postfix2) && !"xls".equalsIgnoreCase(postfix2)) {
                fileName = fileName + postfix;
            }
            out = new FileOutputStream(dirPath + File.separator + fileName);
            wb.write(out);
            out.close();
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 导出到具体路径
     * 
     * @param fileFullName
     *            完整路径(可以不带后缀，自动根据类型加上)
     */
    public void exportToFile(String fileFullName) {
        if (wb == null) {
            wb = isXlsx ? new XSSFWorkbook() : new HSSFWorkbook();
        }
        String postfix = "";
        if (wb instanceof XSSFWorkbook) {
            postfix = ".xlsx";
        }
        else {
            postfix = ".xls";
        }
        FileOutputStream out;
        try {
            int lastIndex = fileFullName.lastIndexOf(".");
            String postfix2 = fileFullName.substring(lastIndex + 1, fileFullName.length());
            if (!"xlsx".equalsIgnoreCase(postfix2) && !"xls".equalsIgnoreCase(postfix2)) {
                fileFullName = fileFullName + postfix;
            }
            out = new FileOutputStream(fileFullName);
            wb.write(out);
            out.close();
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 导出excel
     * 
     * @param fileName
     *            文件名
     */
    public void export(String fileName) {
        if (wb == null) {
            wb = isXlsx ? new XSSFWorkbook() : new HSSFWorkbook();
        }
        String postfix = "";

        if (wb instanceof XSSFWorkbook) {
            postfix = ".xlsx";
        }
        else {
            postfix = ".xls";
        }

        try {
            fileName = java.net.URLEncoder.encode(fileName, "utf-8");
        }
        catch (UnsupportedEncodingException e1) {
            try {
                fileName = new String(fileName.getBytes("utf-8"));
            }
            catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }

        fileName = fileName + postfix;

        ServletActionContext.getResponse().setHeader("Cache-Control", "");
        ServletActionContext.getResponse().setContentType("application/data;charset=UTF-8");
        ServletActionContext.getResponse().setHeader("Content-Disposition",
                "attachment; filename=" + fileName);
        OutputStream out = null;
        try {
            out = new BufferedOutputStream(ServletActionContext.getResponse().getOutputStream());
            wb.write(out); // 输出文件
            out.flush();
        }
        catch (IOException e) {
        }
        finally {
            FileUtils.close(out);
        }
    }

    private static class T {
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

    }

    private static class E {
        private T t;
        private String name;

        public T getT() {
            return t;
        }

        public void setT(T t) {
            this.t = t;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public static void main(String[] args) throws Exception {

        T t = new T();
        t.setName("ttt");

        E e = new E();
        e.setName("eee");
        e.setT(t);

        ObjectMapper om = new ObjectMapper();
        String sss = om.writeValueAsString(e);

        ZdExcel zdExcel = new ZdExcel(false);
        try {
            zdExcel.syncExcel(new File("E:\\2.xls"));
            // zdExcel.syncXml(new File("E:\\1.xml"));

            // Excel
            // zdExcel.createSheetByXml("Test");
            // 生成文件
            // zdExcel.exportToFile(filePath);
            // 直接通过response输出到页面
            // zdExcel.export(fileName);
            // 根据xml内容，生成html
            String s = zdExcel.createHtml("2");
            org.apache.commons.io.FileUtils.writeStringToFile(new File("E:\\2.html"),
                    "<html><body>" + s + "</body></html>");
            zdExcel.createSheetByXml("6");
            zdExcel.exportToFile("E:\\6");

            zdExcel = new ZdExcel(false);
            zdExcel.syncExcel(new File("E:\\1.xls"));
            s = zdExcel.createHtml("1");
            org.apache.commons.io.FileUtils.writeStringToFile(new File("E:\\1.html"),
                    "<html><body>" + s + "</body></html>");

            zdExcel = new ZdExcel(true);
            zdExcel.syncExcel(new File("E:\\1.xls"));
            zdExcel.createSheetByXml("5");
            zdExcel.exportToFile("E:\\5.xlsx");

            zdExcel = new ZdExcel(false);
            zdExcel.syncExcel(new File("E:\\3.xls"));
            s = zdExcel.createHtml("3");
            org.apache.commons.io.FileUtils.writeStringToFile(new File("E:\\3.html"),
                    "<html><body>" + s + "</body></html>");

            zdExcel = new ZdExcel(false);
            zdExcel.syncExcel(new File("E:\\5.xlsx"));
            s = zdExcel.createHtml("4");
            org.apache.commons.io.FileUtils.writeStringToFile(new File("E:\\4.html"),
                    "<html><body>" + s + "</body></html>");
        }
        catch (Exception e1) {
            e1.printStackTrace();
        }
        long time = System.currentTimeMillis();
        ZdExcel zdExcel2 = new ZdExcel(false);
        zdExcel2.add(new ZdCell("标题", 3, 1, new ZdStyle(ZdStyle.ALIGN_CENTER, 20)));
        zdExcel2.add(new ZdCell[] { new ZdCell("姓名", new ZdStyle(ZdStyle.BORDER | ZdStyle.BOLD)),
                new ZdCell("性别", new ZdStyle(ZdStyle.BORDER | ZdStyle.BOLD)),
                new ZdCell("出生日期", new ZdStyle(ZdStyle.BORDER | ZdStyle.BOLD)) });
        for (int i = 0; i < 30000; i++)
            zdExcel2.add(new ZdCell[] { new ZdCell("Name" + i, new ZdStyle(ZdStyle.BORDER)),
                    new ZdCell("男", new ZdStyle(ZdStyle.BORDER)),
                    new ZdCell("2015-01-01", new ZdStyle(ZdStyle.BORDER)) });
        zdExcel2.createSheet("tt");
        zdExcel2.exportToFile("E:\\tt4");
        System.out.println(System.currentTimeMillis() - time);
    }

    public Workbook getWb() {
        return wb;
    }
}
