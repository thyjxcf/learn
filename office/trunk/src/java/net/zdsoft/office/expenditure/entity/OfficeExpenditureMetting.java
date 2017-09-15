package net.zdsoft.office.expenditure.entity;

import java.util.Date;

import net.zdsoft.eis.frame.client.BaseEntity;
/**
 * 会议费
 * @author 
 * 
 */
public class OfficeExpenditureMetting extends BaseEntity{
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	private String expenditureId;
	/**
	 * 会议依据
	 */
	private String reason;
	/**
	 * 规格
	 */
	private String spec;
	/**
	 * 名称
	 */
	private String name;
	/**
	 * 地点
	 */
	private String place;
	/**
	 * 开始时间
	 */
	private Date startTime;
	/**
	 * 结束时间
	 */
	private Date endTime;
	/**
	 * 天数
	 */
	private Integer days;
	/**
	 * 人数
	 */
	private Integer num;
	/**
	 * 合计
	 */
	private Double sum;
	/**
	 * 住宿费
	 */
	private Double hotelFee;
	/**
	 * 就餐费
	 */
	private Double repastFee;
	/**
	 * 会场费
	 */
	private Double placeFee;
	/**
	 * 用品费
	 */
	private Double goodsFee;
	/**
	 * 文印费
	 */
	private Double indiaFee;
	/**
	 * 交通费
	 */
	private Double trafficFee;
	/**
	 * 劳务费
	 */
	private Double laborFee;
	/**
	 * 宣传费
	 */
	private Double adviertisementFee;
	/**
	 * 其他费
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
	public void setSpec(String spec){
		this.spec = spec;
	}
	/**
	 * 获取
	 */
	public String getSpec(){
		return this.spec;
	}
	/**
	 * 设置
	 */
	public void setName(String name){
		this.name = name;
	}
	/**
	 * 获取
	 */
	public String getName(){
		return this.name;
	}
	/**
	 * 设置
	 */
	public void setPlace(String place){
		this.place = place;
	}
	/**
	 * 获取
	 */
	public String getPlace(){
		return this.place;
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
	public void setNum(Integer num){
		this.num = num;
	}
	/**
	 * 获取
	 */
	public Integer getNum(){
		return this.num;
	}
	public Double getSum() {
		return sum;
	}
	public void setSum(Double sum) {
		this.sum = sum;
	}
	public Double getHotelFee() {
		return hotelFee;
	}
	public void setHotelFee(Double hotelFee) {
		this.hotelFee = hotelFee;
	}
	public Double getRepastFee() {
		return repastFee;
	}
	public void setRepastFee(Double repastFee) {
		this.repastFee = repastFee;
	}
	public Double getPlaceFee() {
		return placeFee;
	}
	public void setPlaceFee(Double placeFee) {
		this.placeFee = placeFee;
	}
	public Double getGoodsFee() {
		return goodsFee;
	}
	public void setGoodsFee(Double goodsFee) {
		this.goodsFee = goodsFee;
	}
	public Double getIndiaFee() {
		return indiaFee;
	}
	public void setIndiaFee(Double indiaFee) {
		this.indiaFee = indiaFee;
	}
	public Double getTrafficFee() {
		return trafficFee;
	}
	public void setTrafficFee(Double trafficFee) {
		this.trafficFee = trafficFee;
	}
	public Double getLaborFee() {
		return laborFee;
	}
	public void setLaborFee(Double laborFee) {
		this.laborFee = laborFee;
	}
	public Double getAdviertisementFee() {
		return adviertisementFee;
	}
	public void setAdviertisementFee(Double adviertisementFee) {
		this.adviertisementFee = adviertisementFee;
	}
	public Double getOtherFee() {
		return otherFee;
	}
	public void setOtherFee(Double otherFee) {
		this.otherFee = otherFee;
	}
	
}