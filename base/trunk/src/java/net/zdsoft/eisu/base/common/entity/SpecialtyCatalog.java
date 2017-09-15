package net.zdsoft.eisu.base.common.entity;

import net.zdsoft.eis.frame.client.BaseEntity;

public class SpecialtyCatalog extends BaseEntity{
	private static final long serialVersionUID = 8671817464095738554L;
	/**
	 * 单位id
	 */
	private String unitId;
	/**
	 * 专业类别id
	 */
	private String specialtyTypeId;
	/**
	 * 专业目录代码
	 */
	private String specialtyCode;
	/**
	 * 专业目录名称
	 */
	private String specialtyName;
	public String getUnitId() {
		return unitId;
	}
	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}
	public String getSpecialtyTypeId() {
		return specialtyTypeId;
	}
	public void setSpecialtyTypeId(String specialtyTypeId) {
		this.specialtyTypeId = specialtyTypeId;
	}
	public String getSpecialtyCode() {
		return specialtyCode;
	}
	public void setSpecialtyCode(String specialtyCode) {
		this.specialtyCode = specialtyCode;
	}
	public String getSpecialtyName() {
		return specialtyName;
	}
	public void setSpecialtyName(String specialtyName) {
		this.specialtyName = specialtyName;
	}
}
