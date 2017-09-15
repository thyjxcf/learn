package net.zdsoft.eis.base.common.entity;

import net.zdsoft.eis.frame.client.BaseEntity;

/**
 * 
 * @author weixh
 * @since 2013-3-7
 */
@SuppressWarnings("serial")
public class FieldsDisplay extends BaseEntity {
	private String unitId;
	private String colsType;
	private String parentId;
	private int isExistsSubitem;
	private String colsName;
	private String colsCode;
	private String colsKind;
	private String colsMcode;
	private int colsOrder;
	private int colsUse;

	public String getUnitId() {
		return unitId;
	}

	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}

	public String getColsType() {
		return colsType;
	}

	public void setColsType(String colsType) {
		this.colsType = colsType;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public int getIsExistsSubitem() {
		return isExistsSubitem;
	}

	public void setIsExistsSubitem(int isExistsSubitem) {
		this.isExistsSubitem = isExistsSubitem;
	}

	public String getColsName() {
		return colsName;
	}

	public void setColsName(String colsName) {
		this.colsName = colsName;
	}

	public String getColsCode() {
		return colsCode;
	}

	public void setColsCode(String colsCode) {
		this.colsCode = colsCode;
	}

	public String getColsKind() {
		return colsKind;
	}

	public void setColsKind(String colsKind) {
		this.colsKind = colsKind;
	}

	public String getColsMcode() {
		return colsMcode;
	}

	public void setColsMcode(String colsMcode) {
		this.colsMcode = colsMcode;
	}

	public int getColsOrder() {
		return colsOrder;
	}

	public void setColsOrder(int colsOrder) {
		this.colsOrder = colsOrder;
	}

	public int getColsUse() {
		return colsUse;
	}

	public void setColsUse(int colsUse) {
		this.colsUse = colsUse;
	}

}
