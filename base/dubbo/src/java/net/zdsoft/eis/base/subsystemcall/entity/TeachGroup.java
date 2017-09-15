package net.zdsoft.eis.base.subsystemcall.entity;

import net.zdsoft.eis.frame.client.BaseEntity;

public class TeachGroup extends BaseEntity {

	private static final long serialVersionUID = 6175891510651042666L;

	/**
	 * 单位id
	 */
	private String unitId;
	
	/**
	 * 教研室名称
	 */
	private String name;
	/**
	 * 所属院系
	 */
	private String instituteId;
	
	public String getUnitId() {
		return unitId;
	}

	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getInstituteId() {
		return instituteId;
	}

	public void setInstituteId(String instituteId) {
		this.instituteId = instituteId;
	}

}
