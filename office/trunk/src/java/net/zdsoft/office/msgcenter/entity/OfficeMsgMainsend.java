package net.zdsoft.office.msgcenter.entity;

import java.io.Serializable;
/**
 * 信息主送信息表
 * @author 
 * 
 */
public class OfficeMsgMainsend implements Serializable{
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	private String id;
	/**
	 * 消息id
	 */
	private String messageId;
	/**
	 * 接受者id
	 */
	private String receiverId;
	/**
	 * 接受者名字
	 */
	private String receiverName;
	/**
	 * 接受者类型
	 */
	private Integer receiverType;

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
	 * 设置消息id
	 */
	public void setMessageId(String messageId){
		this.messageId = messageId;
	}
	/**
	 * 获取消息id
	 */
	public String getMessageId(){
		return this.messageId;
	}
	/**
	 * 设置接受者id
	 */
	public void setReceiverId(String receiverId){
		this.receiverId = receiverId;
	}
	/**
	 * 获取接受者id
	 */
	public String getReceiverId(){
		return this.receiverId;
	}
	/**
	 * 设置接受者名字
	 */
	public void setReceiverName(String receiverName){
		this.receiverName = receiverName;
	}
	/**
	 * 获取接受者名字
	 */
	public String getReceiverName(){
		return this.receiverName;
	}
	/**
	 * 设置接受者类型
	 */
	public void setReceiverType(Integer receiverType){
		this.receiverType = receiverType;
	}
	/**
	 * 获取接受者类型
	 */
	public Integer getReceiverType(){
		return this.receiverType;
	}
}