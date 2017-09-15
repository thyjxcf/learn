package net.zdsoft.office.dailyoffice.entity;

import java.io.Serializable;
/**
 * web_bookroom
 * @author 
 * 
 */
public class WebBookroom implements Serializable{
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	private String staffid;
	/**
	 * 
	 */
	private String controllerid;
	/**
	 * 
	 */
	private Integer doorno;
	/**
	 * 
	 */
	private String begindatetime;
	/**
	 * 
	 */
	private String enddatetime;
	/**
	 * 
	 */
	private Integer sendflag;

	/**
	 * 设置
	 */
	public void setStaffid(String staffid){
		this.staffid = staffid;
	}
	/**
	 * 获取
	 */
	public String getStaffid(){
		return this.staffid;
	}
	/**
	 * 设置
	 */
	public void setControllerid(String controllerid){
		this.controllerid = controllerid;
	}
	/**
	 * 获取
	 */
	public String getControllerid(){
		return this.controllerid;
	}
	/**
	 * 设置
	 */
	public void setDoorno(Integer doorno){
		this.doorno = doorno;
	}
	/**
	 * 获取
	 */
	public Integer getDoorno(){
		return this.doorno;
	}
	/**
	 * 设置
	 */
	public void setBegindatetime(String begindatetime){
		this.begindatetime = begindatetime;
	}
	/**
	 * 获取
	 */
	public String getBegindatetime(){
		return this.begindatetime;
	}
	/**
	 * 设置
	 */
	public void setEnddatetime(String enddatetime){
		this.enddatetime = enddatetime;
	}
	/**
	 * 获取
	 */
	public String getEnddatetime(){
		return this.enddatetime;
	}
	/**
	 * 设置
	 */
	public void setSendflag(Integer sendflag){
		this.sendflag = sendflag;
	}
	/**
	 * 获取
	 */
	public Integer getSendflag(){
		return this.sendflag;
	}
}