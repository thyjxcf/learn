package net.zdsoft.eis.system.frame.entity;

import net.zdsoft.eis.frame.client.BaseEntity;

public class FlowDiagram extends BaseEntity {

	private static final long serialVersionUID = -1810219541152824311L;

	private String tfdName;// 业务流程名称
	private int subsystem;// 所属子系统id
	private int isUsing;// 是否使用
	private int unitClass;
	
	//页面展示用
	private String html;
	
	public static final int IS_USING_USE=1;

	/**
	 * 西安统计平台有数据学校流程图
	 */
	public static final String FLOW_DIAGRAM_ID_DATACENTER_HAVEDATA="M9610000000000000000000000000001";
	
	/**
	 * 西安统计平台无数据学校流程图
	 */
	public static final String FLOW_DIAGRAM_ID_DATACENTER_NODATA="M9610000000000000000000000000002";

	public String getTfdName() {
		return tfdName;
	}

	public void setTfdName(String tfdName) {
		this.tfdName = tfdName;
	}


	public int getSubsystem() {
		return subsystem;
	}

	public void setSubsystem(int subsystem) {
		this.subsystem = subsystem;
	}

	public int getUnitClass() {
		return unitClass;
	}

	public void setUnitClass(int unitClass) {
		this.unitClass = unitClass;
	}

	public int getIsUsing() {
		return isUsing;
	}

	public void setIsUsing(int isUsing) {
		this.isUsing = isUsing;
	}

	public String getHtml() {
		return html;
	}

	public void setHtml(String html) {
		this.html = html;
	}

}
