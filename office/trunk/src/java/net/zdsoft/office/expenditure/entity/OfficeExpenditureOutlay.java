package net.zdsoft.office.expenditure.entity;

import java.io.Serializable;
import java.util.Date;

import net.zdsoft.eis.frame.client.BaseEntity;
/**
 * office_expenditure_outlay
 * @author 
 * 
 */
public class OfficeExpenditureOutlay extends BaseEntity{
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	private String expenditureId;
	/**
	 * 申请原因
	 */
	private String reason;
	/**
	 * 费用名称
	 */
	private String feeName;
	/**
	 * 费用合计
	 */
	private Double sum;
	/**
	 * 内容
	 */
	private String content;

	/**
	 * 设置
	 */
	public void setExpenditureId(String expenditureId){
		this.expenditureId = expenditureId;
	}
	/**
	 * 获取
	 */
	public String getExpenditureId(){
		return this.expenditureId;
	}
	/**
	 * 设置
	 */
	public void setReason(String reason){
		this.reason = reason;
	}
	/**
	 * 获取
	 */
	public String getReason(){
		return this.reason;
	}
	/**
	 * 设置
	 */
	public void setFeeName(String feeName){
		this.feeName = feeName;
	}
	/**
	 * 获取
	 */
	public String getFeeName(){
		return this.feeName;
	}
	/**
	 * 设置
	 */
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
	public Double getSum() {
		return sum;
	}
	public void setSum(Double sum) {
		this.sum = sum;
	}
}