package net.zdsoft.leadin.util.excel;

import org.apache.commons.lang3.StringUtils;

public class ZdFont {
    private String name;
    private int charset;
    private String color;
    private int size;
    private int bold;
    private int italic;
    private String underline;
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getCharset() {
        return charset;
    }
    public void setCharset(int charset) {
        this.charset = charset;
    }
    public String getColor() {
        if(StringUtils.length(color) == 4) {
            color = "#" + color.substring(1, 2) + color.substring(1, 2) + color.substring(2, 3) + color.substring(2, 3) + color.substring(3, 4) + color.substring(3, 4);
        }
        return color;
    }
    public void setColor(String color) {
        this.color = color;
    }
    public int getSize() {
        if(size == 0)
            size = 10;
        return size;
    }
    public void setSize(int size) {
        this.size = size;
    }
    public int getBold() {
        return bold;
    }
    public void setBold(int bold) {
        this.bold = bold;
    }
    public int getItalic() {
        return italic;
    }
    public void setItalic(int italic) {
        this.italic = italic;
    }
    public String getUnderline() {
        return underline;
    }
    public void setUnderline(String underline) {
        this.underline = underline;
    }
    
    
}
