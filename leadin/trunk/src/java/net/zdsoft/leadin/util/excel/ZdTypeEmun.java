package net.zdsoft.leadin.util.excel;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;

public enum ZdTypeEmun {
    DATATIME(Cell.CELL_TYPE_NUMERIC, "DateTime"), NUMBER(Cell.CELL_TYPE_NUMERIC, "Number"), STRING(
            Cell.CELL_TYPE_STRING, "String");

    private int type;
    private String typeString;

    private ZdTypeEmun(int type, String typeString) {
        this.type = type;
        this.typeString = typeString;
    }

    public static ZdTypeEmun getZdType(String typeString) {
        for (ZdTypeEmun zt : ZdTypeEmun.values()) {
            if (StringUtils.equalsIgnoreCase(zt.getTypeString(), typeString))
                return zt;
        }
        return STRING;
    }

    public static ZdTypeEmun getZdType(int type) {
        for (ZdTypeEmun zt : ZdTypeEmun.values()) {
            if (type == zt.getType())
                return zt;
        }
        return STRING;
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
