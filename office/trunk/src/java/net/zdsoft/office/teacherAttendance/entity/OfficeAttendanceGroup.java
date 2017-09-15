package net.zdsoft.office.teacherAttendance.entity;


import java.io.Serializable;
import java.util.Date;

import net.zdsoft.eis.frame.client.BaseEntity;
/**
 * 考勤组
 * @author 
 * 
 */
public class OfficeAttendanceGroup extends BaseEntity{
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	private String unitId;
	/**
	 * 
	 */
	private String name;
	/**
	 * 考勤制度id
	 */
	private String attSetId;
	
	/**
	 * 办公地点（可多个，逗号隔开）
	 */
	private String placeIds;
	
	/**
	 * 考勤制定名称
	 */
	private String attSetName;
	/**
	 * 办公地点
	 */
	private String placeName;
	/**
	 * 用户名称
	 */
	private String userNames;
	/**
	 * 用户ids
	 */
	private String teacherIds;

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
	public void setAttSetId(String attSetId){
		this.attSetId = attSetId;
	}
	/**
	 * 获取
	 */
	public String getAttSetId(){
		return this.attSetId;
	}
	/**
	 * 设置
	 */
	public void setPlaceIds(String placeIds){
		this.placeIds = placeIds;
	}
	/**
	 * 获取
	 */
	public String getPlaceIds(){
		return this.placeIds;
	}
	public String getAttSetName() {
		return attSetName;
	}
	public void setAttSetName(String attSetName) {
		this.attSetName = attSetName;
	}
	public String getPlaceName() {
		return placeName;
	}
	public void setPlaceName(String placeName) {
		this.placeName = placeName;
	}
	public String getUserNames() {
		return userNames;
	}
	public void setUserNames(String userNames) {
		this.userNames = userNames;
	}
	public String getTeacherIds() {
		return teacherIds;
	}
	public void setTeacherIds(String teacherIds) {
		this.teacherIds = teacherIds;
	}
	
	
}