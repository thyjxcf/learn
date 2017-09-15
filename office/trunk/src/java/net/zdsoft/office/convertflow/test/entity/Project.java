package net.zdsoft.office.convertflow.test.entity;


public class Project {
	
	private int type;//早班，晚班
	private int number;//班次需要多少人
	private String typeName;//班次名称
	
	
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	
}
