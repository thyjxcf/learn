package net.zdsoft.leadin.util.excel;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.CellStyle;

public enum ZdAlignmentEmun {
    ALIGN_LEFT(CellStyle.ALIGN_LEFT, "Left"), ALIGN_RIGHT(CellStyle.ALIGN_RIGHT, "Right"), ALIGN_CENTER(
            CellStyle.ALIGN_CENTER, "Center");

    private int type;
    private String typeString;

    private ZdAlignmentEmun(int type, String typeString) {
        this.type = type;
        this.typeString = typeString;
    }

    public static ZdAlignmentEmun getEmun(String typeString) {
        for (ZdAlignmentEmun zt : ZdAlignmentEmun.values()) {
            if (StringUtils.equalsIgnoreCase(zt.getTypeString(), typeString))
                return zt;
        }
        return ALIGN_LEFT;
    }

    public static ZdAlignmentEmun getEmun(int type) {
        for (ZdAlignmentEmun zt : ZdAlignmentEmun.values()) {
            if (type == zt.getType())
                return zt;
        }
        return ALIGN_LEFT;
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
