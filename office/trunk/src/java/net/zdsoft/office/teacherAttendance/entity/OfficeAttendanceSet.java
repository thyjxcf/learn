package net.zdsoft.office.teacherAttendance.entity;

import org.apache.commons.lang.StringUtils;

import net.zdsoft.eis.frame.client.BaseEntity;
/**
 * office_attendance_set(考勤制度设置)
 * @author 
 * 
 */
public class OfficeAttendanceSet extends BaseEntity{
	private static final long serialVersionUID = 1L;
	
	/**
	 * 名称
	 */
	private String name;
	private String unitId;
	/**
	 * 上班时间
	 */
	private String startTime;
	/**
	 * 下午上班时间
	 */
	private String pmTime;
	/**
	 * 下班时间
	 */
	private String endTime;
	/**
	 * 0:非弹性工作制，1:弹性工作制
	 */
	private Boolean isElastic;
	/**
	 * 上班弹性时间范围（分）
	 */
	private String elasticRange;
	/**
	 * 下班弹性时间范围（分）
	 */
	private String endElasticRange;
	/**
	 * 上班可提前打卡时间（小时）
	 */
	private String startRange;
	

	/**
	 * 获取上班时间（秒）
	 * @return
	 */
	public String getStartTimeStr(){
		if(StringUtils.isNotBlank(startTime)){
			return this.startTime+":00";
		}
		return "";
	}
	/**
	 * 获取下午上班时间（秒）
	 * @return
	 */
	public String getPmTimeStr(){
		if(StringUtils.isNotBlank(pmTime)){
			return this.pmTime+":00";
		}
		return "";
	}
	/**
	 * 获取下班时间（秒）
	 * @return
	 */
	public String getEndTimeStr(){
		if(StringUtils.isNotBlank(endTime)){
			return this.endTime+":00";
		}
		return "";
	}
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
	public void setPmTime(String pmTime){
		this.pmTime = pmTime;
	}
	/**
	 * 获取
	 */
	public String getPmTime(){
		return this.pmTime;
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
	 * 设置0:非弹性工作制，1:弹性工作制
	 */
	public void setIsElastic(Boolean isElastic){
		this.isElastic = isElastic;
	}
	/**
	 * 获取0:非弹性工作制，1:弹性工作制
	 */
	public Boolean getIsElastic(){
		return this.isElastic;
	}
	/**
	 * 设置
	 */
	public void setElasticRange(String elasticRange){
		this.elasticRange = elasticRange;
	}
	/**
	 * 获取
	 */
	public String getElasticRange(){
		return this.elasticRange;
	}
	/**
	 * 设置
	 */
	public void setStartRange(String startRange){
		this.startRange = startRange;
	}
	/**
	 * 获取
	 */
	public String getStartRange(){
		return this.startRange;
	}
	public String getEndElasticRange() {
		return endElasticRange;
	}
	public void setEndElasticRange(String endElasticRange) {
		this.endElasticRange = endElasticRange;
	}
	
}