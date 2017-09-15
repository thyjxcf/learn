package net.zdsoft.eis.base.common.entity;

public class SimpleModule extends BasicModule {
	private static final long serialVersionUID = 1L;

	private int platform;// 所属平台

	private String parentModName; // 所属父级模块名称

	private String parentModType;// 所属父级模块类型

	public int getPlatform() {
		return platform;
	}

	public void setPlatform(int platform) {
		this.platform = platform;
	}

	public String getParentModName() {
		return parentModName;
	}

	public void setParentModName(String parentModName) {
		this.parentModName = parentModName;
	}

	public String getParentModType() {
		return parentModType;
	}

	public void setParentModType(String parentModType) {
		this.parentModType = parentModType;
	}
}
