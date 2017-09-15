package net.zdsoft.office.desktop.entity;

import java.io.Serializable;
/**
 * office_memo_ex
 * @author 
 * 
 */
public class OfficeMemoEx implements Serializable{
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	private String id;
	/**
	 * 
	 */
	private String useUserId;
	/**
	 * 
	 */
	private String memoId;
	
	private String send;

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
	public void setUseUserId(String useUserId){
		this.useUserId = useUserId;
	}
	/**
	 * 获取
	 */
	public String getUseUserId(){
		return this.useUserId;
	}
	/**
	 * 设置
	 */
	public void setMemoId(String memoId){
		this.memoId = memoId;
	}
	/**
	 * 获取
	 */
	public String getMemoId(){
		return this.memoId;
	}
	
	public String getSend() {
		return send;
	}
	public void setSend(String send) {
		this.send = send;
	}
}