package net.zdsoft.office.dailyoffice.sync.entity;


public class DoorRecord {
	
	private String StaffID;
	private String DataDate;
	private String DataTime;
	private String ControllerID;
	private Integer DataDoorNo;
	private Integer ReaderID;
	
	public String getStaffID() {
		return StaffID;
	}
	public void setStaffID(String staffID) {
		StaffID = staffID;
	}
	public String getDataDate() {
		return DataDate;
	}
	public void setDataDate(String dataDate) {
		DataDate = dataDate;
	}
	public String getDataTime() {
		return DataTime;
	}
	public void setDataTime(String dataTime) {
		DataTime = dataTime;
	}
	public Integer getDataDoorNo() {
		return DataDoorNo;
	}
	public void setDataDoorNo(Integer dataDoorNo) {
		DataDoorNo = dataDoorNo;
	}
	public String getControllerID() {
		return ControllerID;
	}
	public void setControllerID(String controllerID) {
		ControllerID = controllerID;
	}
	public Integer getReaderID() {
		return ReaderID;
	}
	public void setReaderID(Integer readerID) {
		ReaderID = readerID;
	}
}
