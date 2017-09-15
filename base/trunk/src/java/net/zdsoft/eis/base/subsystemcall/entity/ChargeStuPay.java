package net.zdsoft.eis.base.subsystemcall.entity;

import java.util.Date;
import net.zdsoft.eis.frame.client.BaseEntity;
public class ChargeStuPay  extends BaseEntity {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1132205284531621006L;
	private String acadyear; 			  //学年（yyyy-yyyy）	acadyear
	private String term;				  //学期（DM-XQXN)	term
	private String stuId;				  //学生id	stu_id
	private String chargeItemId;	      //收费项目id	charge_item_id
	private double stdMoney;			  //标准金额（收费标准中确定的收费金额）	std_money
	private double factMoney;			  //应缴金额（标准金额-减免金额 或 标准金额+附加金额）	fact_money
	private double needMoney;			  //实缴金额		need_money
	private Date payDate; 			  //缴费日期		pay_date
	private String teachId;				  //收款教师id	teach_id
	private String teachName;			  //收款教师姓名	teach_name
	private String payStatus;			  //缴费标志（0未缴费，1已缴费，2已打印票据）	pay_status
	private String billId;				  //票据id	bill_id
	private String note;				  //备注		note
	private String unitId;
	
	//扩展属性---------------------------------
	private String chargeItemName; // 项目名称
	private String chargeItemKind;	//项目类型
	
	
	public ChargeStuPay(){
	}
	
	public String getAcadyear() {
		return acadyear;
	}

	public void setAcadyear(String acadyear) {
		this.acadyear = acadyear;
	}

	public String getTerm() {
		return term;
	}

	public void setTerm(String term) {
		this.term = term;
	}

	public String getStuId() {
		return stuId;
	}

	public void setStuId(String stuId) {
		this.stuId = stuId;
	}

	public String getChargeItemId() {
		return chargeItemId;
	}

	public void setChargeItemId(String chargeItemId) {
		this.chargeItemId = chargeItemId;
	}

	public double getStdMoney() {
		return stdMoney;
	}

	public void setStdMoney(double stdMoney) {
		this.stdMoney = stdMoney;
	}
	public double getFactMoney() {
		return factMoney;
	}

	public void setFactMoney(double factMoney) {
		this.factMoney = factMoney;
	}
	public double getNeedMoney() {
		return needMoney;
	}

	public void setNeedMoney(double needMoney) {
		this.needMoney = needMoney;
	}
	
	public Date getPayDate() {
		return payDate;
	}

	public void setPayDate(Date payDate) {
		this.payDate = payDate;
	}
	

	public String getTeachId() {
		return teachId;
	}

	public void setTeachId(String teachId) {
		this.teachId = teachId;
	}

	public String getTeachName() {
		return teachName;
	}

	public void setTeachName(String teachName) {
		this.teachName = teachName;
	}

	public String getPayStatus() {
		return payStatus;
	}

	public void setPayStatus(String payStatus) {
		this.payStatus = payStatus;
	}

	public String getBillId() {
		return billId;
	}

	public void setBillId(String billId) {
		this.billId = billId;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getUnitId() {
		return unitId;
	}

	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}
	public String getChargeItemName() {
		return chargeItemName;
	}

	public void setChargeItemName(String chargeItemName) {
		this.chargeItemName = chargeItemName;
	}

	public String getChargeItemKind() {
		return chargeItemKind;
	}

	public void setChargeItemKind(String chargeItemKind) {
		this.chargeItemKind = chargeItemKind;
	}
}
