package net.zdsoft.office.msgcenter.entity;

import java.io.Serializable;
import java.util.Date;
/**
 * 信息文件夹
 * @author 
 * 
 */
public class OfficeMsgFolder implements Serializable{
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	private String id;
	/**
	 * 文件夹名称
	 */
	private String folderName;
	/**
	 * 创建时间
	 */
	private Date creationTime;
	/**
	 * 排序编号
	 */
	private Integer orderId;
	/**
	 * 用户编号
	 */
	private String userId;

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
	 * 设置文件夹名称
	 */
	public void setFolderName(String folderName){
		this.folderName = folderName;
	}
	/**
	 * 获取文件夹名称
	 */
	public String getFolderName(){
		return this.folderName;
	}
	/**
	 * 设置创建时间
	 */
	public void setCreationTime(Date creationTime){
		this.creationTime = creationTime;
	}
	/**
	 * 获取创建时间
	 */
	public Date getCreationTime(){
		return this.creationTime;
	}
	/**
	 * 设置排序编号
	 */
	public void setOrderId(Integer orderId){
		this.orderId = orderId;
	}
	/**
	 * 获取排序编号
	 */
	public Integer getOrderId(){
		return this.orderId;
	}
	/**
	 * 设置用户编号
	 */
	public void setUserId(String userId){
		this.userId = userId;
	}
	/**
	 * 获取用户编号
	 */
	public String getUserId(){
		return this.userId;
	}
}