package net.zdsoft.leadin.util.excel;

import org.apache.commons.lang3.StringUtils;

public class ZdInterior {
    private String color;
    private String pattern;
    public String getColor() {
        if(StringUtils.length(color) == 4) {
            color = "#" + color.substring(1, 2) + color.substring(1, 2) + color.substring(2, 3) + color.substring(2, 3) + color.substring(3, 4) + color.substring(3, 4);
        }
        return color;
    }
    public void setColor(String color) {
        this.color = color;
    }
    public String getPattern() {
        return pattern;
    }
    public void setPattern(String pattern) {
        this.pattern = pattern;
    }
    
}
