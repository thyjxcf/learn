package net.zdsoft.leadin.util.excel;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Font;

public enum ZdFontEmun {
    SINGLE(Font.U_SINGLE, "Single"), DOUBLE(Font.U_DOUBLE, "Double"), NONE(0, null);

    private int type;
    private String typeString;

    private ZdFontEmun(int type, String typeString) {
        this.type = type;
        this.typeString = typeString;
    }

    public static ZdFontEmun getEmun(String typeString) {
        for (ZdFontEmun zt : ZdFontEmun.values()) {
            if (StringUtils.equalsIgnoreCase(zt.getTypeString(), typeString))
                return zt;
        }
        return NONE;
    }

    public static ZdFontEmun getEmun(int type) {
        for (ZdFontEmun zt : ZdFontEmun.values()) {
            if (type == zt.getType())
                return zt;
        }
        return NONE;
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
