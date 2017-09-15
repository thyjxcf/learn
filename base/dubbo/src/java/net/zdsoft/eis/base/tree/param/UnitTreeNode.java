package net.zdsoft.eis.base.tree.param;

public class UnitTreeNode extends TreeNode {

	private static final long serialVersionUID = -8743045095975729848L;
	/**
	 * 单位的教育类型
	 */
	private String unitEducationType;

	/**
	 * 统计用学校性质
	 */
	private String schoolPropStat;
	/**
	 * 学校性质分组
	 */
	private String schoolTypeGroup;
	/**
	 * 台帐用学校类型码
	 */
	private String ledgerSchoolType;

	public String getUnitEducationType() {
		return unitEducationType;
	}

	public void setUnitEducationType(String unitEducationType) {
		this.unitEducationType = unitEducationType;
	}

	public String getSchoolPropStat() {
		return schoolPropStat;
	}

	public void setSchoolPropStat(String schoolPropStat) {
		this.schoolPropStat = schoolPropStat;
	}

	public String getSchoolTypeGroup() {
		return schoolTypeGroup;
	}

	public void setSchoolTypeGroup(String schoolTypeGroup) {
		this.schoolTypeGroup = schoolTypeGroup;
	}

	public String getLedgerSchoolType() {
		return ledgerSchoolType;
	}

	public void setLedgerSchoolType(String ledgerSchoolType) {
		this.ledgerSchoolType = ledgerSchoolType;
	}

}
