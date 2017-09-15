package net.zdsoft.eis.system.data.entity;

import net.zdsoft.eis.frame.entity.HibernateEntity;

public class UnitChannel extends HibernateEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String unitid ;	//单位id
	private int mark;		//类型标志，0 表示系统默认，1 表示用户设置
	private String name;	//频道名称
	private String url;		//频道地址
	private String code;	//频道代码，如博客为blog等，用于特殊处理
	private int orderid;	//排序号， 1 优先级最高，在频道栏最左边
	private int display;	//是否显示，0 表示不显示，1 表示显示
    private int unitClass;  //0 都适用，1 使用于教育局，2 学校
	private Integer displayLevel; // 显示级别，0 表示适用于自己的桌面，1 表示适用于本单位，2 表示适用于下属单位
	
	public static long getSerialVersionUID() {
		return serialVersionUID;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public int getDisplay() {
		return display;
	}
	public void setDisplay(int display) {
		this.display = display;
	}
	public int getMark() {
		return mark;
	}
	public void setMark(int mark) {
		this.mark = mark;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getOrderid() {
		return orderid;
	}
	public void setOrderid(int orderid) {
		this.orderid = orderid;
	}
	public String getUnitid() {
		return unitid;
	}
	public void setUnitid(String unitid) {
		this.unitid = unitid;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
    public int getUnitClass() {
        return unitClass;
    }
    public void setUnitClass(int unitClass) {
        this.unitClass = unitClass;
    }
	public Integer getDisplayLevel() {
		return displayLevel;
	}

	public void setDisplayLevel(Integer displayLevel) {
		this.displayLevel = displayLevel;
	}
}
