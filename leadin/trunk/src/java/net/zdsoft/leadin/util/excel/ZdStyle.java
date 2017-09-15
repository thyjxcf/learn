package net.zdsoft.leadin.util.excel;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class ZdStyle {
    private short fontSize;
    private short fontColor;
    private short cellWidth;
    private long commonStyle;
    private short backgroundColor;

    private List<ZdBorder> zdBorders = new ArrayList<ZdBorder>();
    private ZdFont zdFont;
    private ZdAlignment zdAlignment;
    private String numberFormat;
    private ZdInterior zdInterior;

    public String styleId;
    
    @JsonIgnore
    public String getStyleMark() {
        String s = "_" + getFontSize() + getFontColor() + getCellWidth()
                + getCommonStyle() + backgroundColor + getNumberFormat();
        for (ZdBorder b : zdBorders) {
            s += b.getLineStyle() + b.getPosition() + b.getWeight();
        }
        if (zdFont != null)
            s += StringUtils.substring(zdFont.getColor(), 1, 6) + zdFont.getBold() + zdFont.getCharset()
                    + zdFont.getItalic() + zdFont.getSize()
                    + zdFont.getUnderline();
        if (zdAlignment != null)
            s += zdAlignment.getHorizontal() + zdAlignment.getVertical()
                    + zdAlignment.getWrapText();
        if (zdInterior != null)
            s += StringUtils.substring(zdInterior.getColor(), 1, 6)  + zdInterior.getPattern();
        return s;
    }

    /** 文字加粗 */
    public static long BOLD = 1;

    /** 水平居中 */
    public static long ALIGN_CENTER = 10;

    /** 水平居右 */
    public static long ALIGN_RIGHT = 100;

    /** 有边框 */
    public static long BORDER = 1000;

    /** 文字自动换行 */
    public static long WRAP_TEXT = 10000;

    /** 垂直居中 */
    public static long VERTICAL_TOP = 100000;

    public ZdStyle() {

    }

    /**
     * 单元格样式
     * 
     * @param commonStyle
     *            样式控制
     * @param fontSize
     *            文字大小
     */
    public ZdStyle(long commonStyle, int fontSize) {
        this.commonStyle = commonStyle;
        this.fontColor = Font.COLOR_NORMAL;
        this.fontSize = (short) fontSize;
    }

    /**
     * 单元格样式
     * 
     * @param commonStyle
     *            样式控制
     */
    public ZdStyle(long commonStyle) {
        this.commonStyle = commonStyle;
        this.fontColor = Font.COLOR_NORMAL;
        this.fontSize = 10;
    }

    public void addBorder(ZdBorder zdBorder) {
        zdBorders.add(zdBorder);
    }

    public short getVertical() {
        if ((commonStyle & VERTICAL_TOP) == VERTICAL_TOP)
            return CellStyle.VERTICAL_TOP;
        return CellStyle.VERTICAL_CENTER;
    }

    public short getAlign() {
        if ((commonStyle & ALIGN_CENTER) == ALIGN_CENTER)
            return CellStyle.ALIGN_CENTER;
        else if ((commonStyle & ALIGN_RIGHT) == ALIGN_RIGHT)
            return CellStyle.ALIGN_RIGHT;
        return CellStyle.ALIGN_GENERAL;
    }

    public boolean isBorder() {
        return (commonStyle & BORDER) == BORDER;
    }

    public boolean isBold() {
        return (commonStyle & BOLD) == BOLD;
    }

    public boolean iswWrapText() {
        return (commonStyle & WRAP_TEXT) == WRAP_TEXT;
    }

    public short getFontSize() {
        return fontSize;
    }

    public int getFontColor() {
        return fontColor;
    }

    public long getCommonStyle() {
        return commonStyle;
    }

    public void setCommonStyle(long commonStyle) {
        this.commonStyle = commonStyle;
    }

    public void setFontSize(short fontSize) {
        this.fontSize = fontSize;
    }

    public void setFontColor(short fontColor) {
        this.fontColor = fontColor;
    }

    public short getCellWidth() {
        return cellWidth;
    }

    public void setCellWidth(short cellWidth) {
        this.cellWidth = cellWidth;
    }

    public short getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(short backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public List<ZdBorder> getZdBorders() {
        return zdBorders;
    }

    public void setZdBorders(List<ZdBorder> zdBorders) {
        this.zdBorders = zdBorders;
    }

    public ZdAlignment getZdAlignment() {
        return zdAlignment;
    }

    public void setZdAlignment(ZdAlignment zdAlignment) {
        this.zdAlignment = zdAlignment;
    }

    public ZdFont getZdFont() {
        return zdFont;
    }

    public void setZdFont(ZdFont zdFont) {
        this.zdFont = zdFont;
    }

    public String getStyleId() {
        return styleId;
    }

    public void setStyleId(String styleId) {
        this.styleId = styleId;
    }

    public String getNumberFormat() {
        return numberFormat;
    }

    public void setNumberFormat(String numberFormat) {
        this.numberFormat = numberFormat;
    }

    public ZdInterior getZdInterior() {
        return zdInterior;
    }

    public void setZdInterior(ZdInterior zdInterior) {
        this.zdInterior = zdInterior;
    }
}
