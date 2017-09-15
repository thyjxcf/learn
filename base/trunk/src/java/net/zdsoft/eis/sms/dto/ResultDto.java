package net.zdsoft.eis.sms.dto;

/* 
 * <p>ZDSoft电子政务系统V3.6</p>
 * 短信发送结果
 * @author lilj
 * @since 1.0
 * @version $Id: ResultDto.java,v 1.1 2006/10/11 03:32:44 lilj Exp $
 */

public class ResultDto {
	/**
	 * 短信发送结果，成功还是失败
	 */
	private boolean operateResult;
	/**
	 * 短信发送结果描述
	 */
	private String operateResultMsg;
	/**
	 * 发送的短信条数
	 */
	private int smsCount;
	/**
	 * 发送的手机接收者个数
	 */
	private int phoneCount;
	private String batchid;
	public boolean isOperateResult() {
		return operateResult;
	}
	public void setOperateResult(boolean operateResult) {
		this.operateResult = operateResult;
	}
	public String getOperateResultMsg() {
		return operateResultMsg;
	}
	public void setOperateResultMsg(String operateResultMsg) {
		this.operateResultMsg = operateResultMsg;
	}
	public int getPhoneCount() {
		return phoneCount;
	}
	public void setPhoneCount(int phoneCount) {
		this.phoneCount = phoneCount;
	}
	public int getSmsCount() {
		return smsCount;
	}
	public void setSmsCount(int smsCount) {
		this.smsCount = smsCount;
	}
	public String getBatchid() {
		return batchid;
	}
	public void setBatchid(String batchid) {
		this.batchid = batchid;
	}

}
