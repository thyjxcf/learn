package net.zdsoft.office.boardroom.entity;

import java.io.Serializable;
import java.util.Date;
/**
 * office_boardroom_xj
 * @author 
 * 
 */
public class OfficeBoardroomXj implements Serializable{
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	private String id;
	/**
	 * 
	 */
	private String unitId;
	/**
	 * 
	 */
	private String name;
	/**
	 * 
	 */
	private String needAudit;
	/**
	 * 
	 */
	private String startTime;
	/**
	 * 
	 */
	private String endTime;
	/**
	 * 
	 */
	private String timeInterval;
	/**
	 * 
	 */
	private String noonStartTime;
	/**
	 * 
	 */
	private String noonEndTime;
	/**
	 * 
	 */
	private Integer maxNumber;
	/**
	 * 
	 */
	private String address;
	/**
	 * 
	 */
	private Integer rostrum;
	/**
	 * 
	 */
	private Integer conferenceSeats;
	/**
	 * 
	 */
	private Integer tableType;
	/**
	 * 
	 */
	private Integer attendNumber;
	/**
	 * 
	 */
	private Boolean isProjector;
	/**
	 * 
	 */
	private String remark;
	/**
	 * 
	 */
	private Date createTime;
	
	/**
	 * 辅助字段
	 */
	private String content;
	/**
	 * 设置
	 */
	public void setId(String id){
		this.id = id;
	}
	/**
	 * 获取
	 */
	public String getId(){
		return this.id;
	}
	/**
	 * 设置
	 */
	public void setUnitId(String unitId){
		this.unitId = unitId;
	}
	/**
	 * 获取
	 */
	public String getUnitId(){
		return this.unitId;
	}
	/**
	 * 设置
	 */
	public void setName(String name){
		this.name = name;
	}
	/**
	 * 获取
	 */
	public String getName(){
		return this.name;
	}
	/**
	 * 设置
	 */
	public void setNeedAudit(String needAudit){
		this.needAudit = needAudit;
	}
	/**
	 * 获取
	 */
	public String getNeedAudit(){
		return this.needAudit;
	}
	/**
	 * 设置
	 */
	public void setStartTime(String startTime){
		this.startTime = startTime;
	}
	/**
	 * 获取
	 */
	public String getStartTime(){
		return this.startTime;
	}
	/**
	 * 设置
	 */
	public void setEndTime(String endTime){
		this.endTime = endTime;
	}
	/**
	 * 获取
	 */
	public String getEndTime(){
		return this.endTime;
	}
	/**
	 * 设置
	 */
	public void setTimeInterval(String timeInterval){
		this.timeInterval = timeInterval;
	}
	/**
	 * 获取
	 */
	public String getTimeInterval(){
		return this.timeInterval;
	}
	/**
	 * 设置
	 */
	public void setNoonStartTime(String noonStartTime){
		this.noonStartTime = noonStartTime;
	}
	/**
	 * 获取
	 */
	public String getNoonStartTime(){
		return this.noonStartTime;
	}
	/**
	 * 设置
	 */
	public void setNoonEndTime(String noonEndTime){
		this.noonEndTime = noonEndTime;
	}
	/**
	 * 获取
	 */
	public String getNoonEndTime(){
		return this.noonEndTime;
	}
	/**
	 * 设置
	 */
	public void setMaxNumber(Integer maxNumber){
		this.maxNumber = maxNumber;
	}
	/**
	 * 获取
	 */
	public Integer getMaxNumber(){
		return this.maxNumber;
	}
	/**
	 * 设置
	 */
	public void setAddress(String address){
		this.address = address;
	}
	/**
	 * 获取
	 */
	public String getAddress(){
		return this.address;
	}
	/**
	 * 设置
	 */
	public void setRostrum(Integer rostrum){
		this.rostrum = rostrum;
	}
	/**
	 * 获取
	 */
	public Integer getRostrum(){
		return this.rostrum;
	}
	/**
	 * 设置
	 */
	public void setConferenceSeats(Integer conferenceSeats){
		this.conferenceSeats = conferenceSeats;
	}
	/**
	 * 获取
	 */
	public Integer getConferenceSeats(){
		return this.conferenceSeats;
	}
	/**
	 * 设置
	 */
	public void setTableType(Integer tableType){
		this.tableType = tableType;
	}
	/**
	 * 获取
	 */
	public Integer getTableType(){
		return this.tableType;
	}
	/**
	 * 设置
	 */
	public void setAttendNumber(Integer attendNumber){
		this.attendNumber = attendNumber;
	}
	/**
	 * 获取
	 */
	public Integer getAttendNumber(){
		return this.attendNumber;
	}
	/**
	 * 设置
	 */
	public void setIsProjector(Boolean isProjector){
		this.isProjector = isProjector;
	}
	/**
	 * 获取
	 */
	public Boolean getIsProjector(){
		return this.isProjector;
	}
	/**
	 * 设置
	 */
	public void setRemark(String remark){
		this.remark = remark;
	}
	/**
	 * 获取
	 */
	public String getRemark(){
		return this.remark;
	}
	/**
	 * 设置
	 */
	public void setCreateTime(Date createTime){
		this.createTime = createTime;
	}
	/**
	 * 获取
	 */
	public Date getCreateTime(){
		return this.createTime;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
}