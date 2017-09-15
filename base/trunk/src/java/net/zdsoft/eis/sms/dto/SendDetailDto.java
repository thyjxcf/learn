package net.zdsoft.eis.sms.dto;

import java.io.Serializable;


public class SendDetailDto implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private String id;
	private Integer businessType; //短信业务类型：1：按条计费
    private String mobile; //接收者手机号码
    private Integer itemCount;
    private String status;
    private String unitId;
    private String receiverId; //接收者用户id
    private String receiverName; //接收者真实名称
    private Integer receiverType; //接收者owner_type，教师为2
	private String accountId; //接收者accountId
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Integer getBusinessType() {
		return businessType;
	}
	public void setBusinessType(Integer businessType) {
		this.businessType = businessType;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public Integer getItemCount() {
		return itemCount;
	}
	public void setItemCount(Integer itemCount) {
		this.itemCount = itemCount;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getUnitId() {
		return unitId;
	}
	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}
	public String getReceiverId() {
		return receiverId;
	}
	public void setReceiverId(String receiverId) {
		this.receiverId = receiverId;
	}
	public String getReceiverName() {
		return receiverName;
	}
	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}

	public Integer getReceiverType() {
		return receiverType;
	}
	public void setReceiverType(Integer receiverType) {
		this.receiverType = receiverType;
	}
	public String getAccountId() {
		return accountId;
	}
	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}
	

	
}
