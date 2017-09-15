package net.zdsoft.office.jtgoout.entity;

import java.io.Serializable;
/**
 * goout_teacher_ex
 * @author 
 * 
 */
public class GooutTeacherEx implements Serializable{
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
	private String jtgooutId;
	/**
	 * 
	 */
	private String content;
	/**
	 * 
	 */
	private String partakePersonId;
	private String partakePersonNames;

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
	public void setJtgooutId(String jtgooutId){
		this.jtgooutId = jtgooutId;
	}
	/**
	 * 获取
	 */
	public String getJtgooutId(){
		return this.jtgooutId;
	}
	/**
	 * 设置
	 */
	public void setContent(String content){
		this.content = content;
	}
	/**
	 * 获取
	 */
	public String getContent(){
		return this.content;
	}
	/**
	 * 设置
	 */
	public void setPartakePersonId(String partakePersonId){
		this.partakePersonId = partakePersonId;
	}
	/**
	 * 获取
	 */
	public String getPartakePersonId(){
		return this.partakePersonId;
	}
	public String getPartakePersonNames() {
		return partakePersonNames;
	}
	public void setPartakePersonNames(String partakePersonNames) {
		this.partakePersonNames = partakePersonNames;
	}
}