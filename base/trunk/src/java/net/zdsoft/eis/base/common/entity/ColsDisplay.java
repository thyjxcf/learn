package net.zdsoft.eis.base.common.entity;

import net.zdsoft.eis.frame.client.BaseEntity;

/**
 * 显示信息(列显示、不显示)设置 明细
 * 
 */
public class ColsDisplay extends BaseEntity {

	private static final long serialVersionUID = 1L;
	private String unitId;
	private String colsType;
	private String colsName;
	private String colsCode;
	private Integer colsOrder;
	private Integer colsConstraint;
	private Integer colsUse;
	private String colsKind;
	private String colsMcode;
	public static final String COLSTYPE_TEACHER = "teacher";
	public static final String COLSTYPE_TEACHER_EX = "teacherEx";
	public static final String COLSTYPE_STUDENT = "student";

	/**
	 * 不启用(学生信息审核配置--未选中需要审核)
	 */
	public static final int COLSUSE_CLOSE = 0;
	/**
	 * 启用(学生信息审核配置--选中需要审核)
	 */
	public static final int COLSUSE_OPEN = 1;

	public ColsDisplay() {
	}

	public String getColsCode() {
		return colsCode;
	}

	public void setColsCode(String colscode) {
		this.colsCode = colscode;
	}

	public String getColsName() {
		return colsName;
	}

	public void setColsName(String colsname) {
		this.colsName = colsname;
	}

	public String getUnitId() {
		return unitId;
	}

	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}

	public Integer getColsConstraint() {
		return colsConstraint;
	}

	public void setColsConstraint(Integer colsConstraint) {
		this.colsConstraint = colsConstraint;
	}

	public Integer getColsUse() {
		return colsUse;
	}

	public void setColsUse(Integer colsUse) {
		this.colsUse = colsUse;
	}

	public String getColsType() {
		return colsType;
	}

	public void setColsType(String colsType) {
		this.colsType = colsType;
	}

	public Integer getColsOrder() {
		return colsOrder;
	}

	public void setColsOrder(Integer colsOrder) {
		this.colsOrder = colsOrder;
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

}
