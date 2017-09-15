package net.zdsoft.office.expenditure.entity;

import java.io.Serializable;
import java.util.Date;

import net.zdsoft.eis.frame.client.BaseEntity;
/**
 * office_expenditure_reception
 * @author 
 * 
 */
public class OfficeExpenditureReception extends BaseEntity{
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	private String expenditureId;
	/**
	 * 来宾单位
	 */
	private String guestUnitName;
	/**
	 * 来宾姓名
	 */
	private String guestName;
	/**
	 * 来宾职务
	 */
	private String guestDuty;
	/**
	 * 接待函件
	 */
	private String letters;
	/**
	 * 人数
	 */
	private Integer num;
	/**
	 * 来往开始时间
	 */
	private Date startTime;
	/**
	 * 来往结束时间
	 */
	private Date endTime;
	/**
	 * 来往天数
	 */
	private Integer days;
	/**
	 * 陪同人数
	 */
	private Integer accompanyNum;
	/**
	 * 费用合计
	 */
	private Double sum;
	/**
	 * 住宿标准费用
	 */
	private Double hotelStandardFee;
	/**
	 * 住宿费
	 */
	private Double hotelFee;
	/**
	 * 就餐标准费用
	 */
	private Double repastStandardFee;
	/**
	 * 就餐费用
	 */
	private Double repastFee;
	/**
	 * 其他费用
	 */
	private Double otherFee;

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
	public void setGuestUnitName(String guestUnitName){
		this.guestUnitName = guestUnitName;
	}
	/**
	 * 获取
	 */
	public String getGuestUnitName(){
		return this.guestUnitName;
	}
	/**
	 * 设置
	 */
	public void setGuestName(String guestName){
		this.guestName = guestName;
	}
	/**
	 * 获取
	 */
	public String getGuestName(){
		return this.guestName;
	}
	/**
	 * 设置
	 */
	public void setGuestDuty(String guestDuty){
		this.guestDuty = guestDuty;
	}
	/**
	 * 获取
	 */
	public String getGuestDuty(){
		return this.guestDuty;
	}
	/**
	 * 设置
	 */
	public void setLetters(String letters){
		this.letters = letters;
	}
	/**
	 * 获取
	 */
	public String getLetters(){
		return this.letters;
	}
	/**
	 * 设置
	 */
	public void setNum(Integer num){
		this.num = num;
	}
	/**
	 * 获取
	 */
	public Integer getNum(){
		return this.num;
	}
	/**
	 * 设置
	 */
	public void setStartTime(Date startTime){
		this.startTime = startTime;
	}
	/**
	 * 获取
	 */
	public Date getStartTime(){
		return this.startTime;
	}
	/**
	 * 设置
	 */
	public void setEndTime(Date endTime){
		this.endTime = endTime;
	}
	/**
	 * 获取
	 */
	public Date getEndTime(){
		return this.endTime;
	}
	/**
	 * 设置
	 */
	public void setDays(Integer days){
		this.days = days;
	}
	/**
	 * 获取
	 */
	public Integer getDays(){
		return this.days;
	}
	/**
	 * 设置
	 */
	public void setAccompanyNum(Integer accompanyNum){
		this.accompanyNum = accompanyNum;
	}
	/**
	 * 获取
	 */
	public Integer getAccompanyNum(){
		return this.accompanyNum;
	}
	public Double getSum() {
		return sum;
	}
	public void setSum(Double sum) {
		this.sum = sum;
	}
	public Double getHotelStandardFee() {
		return hotelStandardFee;
	}
	public void setHotelStandardFee(Double hotelStandardFee) {
		this.hotelStandardFee = hotelStandardFee;
	}
	public Double getHotelFee() {
		return hotelFee;
	}
	public void setHotelFee(Double hotelFee) {
		this.hotelFee = hotelFee;
	}
	public Double getRepastStandardFee() {
		return repastStandardFee;
	}
	public void setRepastStandardFee(Double repastStandardFee) {
		this.repastStandardFee = repastStandardFee;
	}
	public Double getRepastFee() {
		return repastFee;
	}
	public void setRepastFee(Double repastFee) {
		this.repastFee = repastFee;
	}
	public Double getOtherFee() {
		return otherFee;
	}
	public void setOtherFee(Double otherFee) {
		this.otherFee = otherFee;
	}
	
}