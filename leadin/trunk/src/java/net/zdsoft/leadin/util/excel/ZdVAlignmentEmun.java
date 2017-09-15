package net.zdsoft.leadin.util.excel;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.CellStyle;

public enum ZdVAlignmentEmun {
    ALIGN_TOP(CellStyle.VERTICAL_TOP, "Top"), ALIGN_BOTTOM(CellStyle.VERTICAL_BOTTOM, "Bottom"), ALIGN_CENTER(
            CellStyle.VERTICAL_CENTER, "Center");

    private int type;
    private String typeString;

    private ZdVAlignmentEmun(int type, String typeString) {
        this.type = type;
        this.typeString = typeString;
    }

    public static ZdVAlignmentEmun getEmun(String typeString) {
        for (ZdVAlignmentEmun zt : ZdVAlignmentEmun.values()) {
            if (StringUtils.equalsIgnoreCase(zt.getTypeString(), typeString))
                return zt;
        }
        return ALIGN_BOTTOM;
    }

    public static ZdVAlignmentEmun getEmun(int type) {
        for (ZdVAlignmentEmun zt : ZdVAlignmentEmun.values()) {
            if (type == zt.getType())
                return zt;
        }
        return ALIGN_BOTTOM;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getTypeString() {
        return typeString;
    }

    public void setTypeString(String typeString) {
        this.typeString = typeString;
    }
}
