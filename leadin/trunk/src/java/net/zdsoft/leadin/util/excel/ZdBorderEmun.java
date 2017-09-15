package net.zdsoft.leadin.util.excel;


public enum ZdBorderEmun {
    BOTTOM("Bottom"), TOP("Top"), LEFT("Left"), RIGHT("Right");

    private int type;
    private String typeString;

    private ZdBorderEmun(String typeString) {
        this.typeString = typeString;
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
