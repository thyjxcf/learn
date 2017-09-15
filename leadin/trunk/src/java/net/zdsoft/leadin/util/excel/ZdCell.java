package net.zdsoft.leadin.util.excel;
import org.apache.commons.lang.math.NumberUtils;

public class ZdCell {

	private String value;
	private int posx;
	private int posy;
	private int cols;
	private int rows;
	private ZdStyle zdStyle;
	
	private String styleId;
	private String type;
	private int mergeAcross;
	private int mergeDown;
	private int index;
	
	public static String ZDCELL_VALUE_NULL = "zdCell_value_null";

	/**
	 * 创建单元格
	 * @param value 显示内容
	 * @param cols 合并列数
	 * @param rows 合并行数
	 */
	public ZdCell(String value, int cols, int rows) {
		this.value = value;
		this.cols = NumberUtils.max(new int[]{cols, 1});
		this.rows = NumberUtils.max(new int[]{rows, 1});
	}

	/**
	 * 创建单元格
	 * @param value 显示内容
	 * @param cols 合并列数
	 * @param rows 合并行数
	 * @param zdStyle 样式控制
	 */
	public ZdCell(String value, int cols, int rows, ZdStyle zdStyle) {
		this.value = value;
		this.cols = NumberUtils.max(new int[]{cols, 1});
		this.rows = NumberUtils.max(new int[]{rows, 1});
		this.zdStyle = zdStyle;
	}

	/**
	 * 创建单元格
	 * @param value 显示内容
	 * @param cols 合并列数
	 */
	public ZdCell(String value, int cols) {
		this.value = value;
		this.cols = NumberUtils.max(new int[]{cols, 1});
		this.rows = 1;
	}

	/**
	 * 创建单元格
	 * @param value 显示内容
	 * @param cols 合并列数
	 * @param zdStyle 样式控制
	 */
	public ZdCell(String value, int cols, ZdStyle zdStyle) {
		this.value = value;
		this.cols = NumberUtils.max(new int[]{cols, 1});
		this.rows = 1;
		this.zdStyle = zdStyle;
	}

	
	/**
	 * 创建单位格
	 * @param value 显示内容
	 */
	public ZdCell(String value) {
		this.value = value;
		this.cols = 1;
		this.rows = 1;
	}

	/**
	 * 创建单位格
	 * @param value 显示内容
	 * @param zdStyle 样式控制
	 */
	public ZdCell(String value, ZdStyle zdStyle) {
		this.value = value;
		this.cols = 1;
		this.rows = 1;
		this.zdStyle = zdStyle;
	}
	
	/**
	 * 空单元，用于被合并列覆盖的单元
	 */
	public ZdCell(){
		this.value = ZDCELL_VALUE_NULL;
		this.cols = 1;
		this.rows = 1;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public int getCols() {
		return cols;
	}

	public void setCols(int cols) {
		this.cols = cols;
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public ZdStyle getZdStyle() {
		return zdStyle;
	}

	public void setZdStyle(ZdStyle zdStyle) {
		this.zdStyle = zdStyle;
	}

	public int getPosx() {
		return posx;
	}

	public void setPosx(int posx) {
		this.posx = posx;
	}

	public int getPosy() {
		return posy;
	}

	public void setPosy(int posy) {
		this.posy = posy;
	}

    public String getStyleId() {
        return styleId;
    }

    public void setStyleId(String styleId) {
        this.styleId = styleId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getMergeAcross() {
        return mergeAcross;
    }

    public void setMergeAcross(int mergeAcross) {
        this.mergeAcross = mergeAcross;
    }

    public int getMergeDown() {
        return mergeDown;
    }

    public void setMergeDown(int mergeDown) {
        this.mergeDown = mergeDown;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

}
