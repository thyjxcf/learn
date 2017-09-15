package net.zdsoft.leadin.util.excel;

import java.util.ArrayList;
import java.util.List;

public class ZdRow {

    private int autoFitHeight;
    private float height;
    private String styleId;
    private int index;
    private List<ZdCell> zdCells = new ArrayList<ZdCell>();
    
    public void addCell(ZdCell zdCell) {
        zdCells.add(zdCell);
    }

    public int getAutoFitHeight() {
        return autoFitHeight;
    }

    public void setAutoFitHeight(int autoFitHeight) {
        this.autoFitHeight = autoFitHeight;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public String getStyleId() {
        return styleId;
    }

    public void setStyleId(String styleId) {
        this.styleId = styleId;
    }

    public List<ZdCell> getZdCells() {
        return zdCells;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

}
