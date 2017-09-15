package net.zdsoft.office.expenditure.entity;

import java.io.Serializable;
import java.util.Date;

import net.zdsoft.eis.frame.client.BaseEntity;
/**
 * office_expenditure_bus_trip
 * @author 
 * 
 */
public class OfficeExpenditureBusTrip extends BaseEntity{
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	private String expenditureId;
	/**
	 * 出差依据
	 */
	private String reason;
	/**
	 * 领队姓名
	 */
	private String leaderName;
	/**
	 * 人数
	 */
	private Integer num;
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
	 * 起点
	 */
	private String startPlace;
	/**
	 * 终点
	 */
	private String endPlace;
	/**
	 * 往返里程
	 */
	private String distance;
	/**
	 * 是否申请派车
	 */
	private Boolean isCar;
	/**
	 * 城市间交通费
	 */
	private Double trafficFee;
	/**
	 * 住宿费
	 */
	private Double hotelFee;
	/**
	 * 伙食费
	 */
	private Double repastFee;
	/**
	 * 市内交通费
	 */
	private Double cityTrafficFee;
	/**
	 * 其他费用
	 */
	private Double otherFee;
	/**
	 * 费用合计
	 */
	private Double sum;

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
	public void setLeaderName(String leaderName){
		this.leaderName = leaderName;
	}
	/**
	 * 获取
	 */
	public String getLeaderName(){
		return this.leaderName;
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
	public void setStartPlace(String startPlace){
		this.startPlace = startPlace;
	}
	/**
	 * 获取
	 */
	public String getStartPlace(){
		return this.startPlace;
	}
	/**
	 * 设置
	 */
	public void setEndPlace(String endPlace){
		this.endPlace = endPlace;
	}
	/**
	 * 获取
	 */
	public String getEndPlace(){
		return this.endPlace;
	}
	/**
	 * 设置
	 */
	public void setDistance(String distance){
		this.distance = distance;
	}
	/**
	 * 获取
	 */
	public String getDistance(){
		return this.distance;
	}
	/**
	 * 设置
	 */
	public void setIsCar(Boolean isCar){
		this.isCar = isCar;
	}
	/**
	 * 获取
	 */
	public Boolean getIsCar(){
		return this.isCar;
	}
	public Double getTrafficFee() {
		return trafficFee;
	}
	public void setTrafficFee(Double trafficFee) {
		this.trafficFee = trafficFee;
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
	public Double getCityTrafficFee() {
		return cityTrafficFee;
	}
	public void setCityTrafficFee(Double cityTrafficFee) {
		this.cityTrafficFee = cityTrafficFee;
	}
	public Double getOtherFee() {
		return otherFee;
	}
	public void setOtherFee(Double otherFee) {
		this.otherFee = otherFee;
	}
	public Double getSum() {
		return sum;
	}
	public void setSum(Double sum) {
		this.sum = sum;
	}
	
}