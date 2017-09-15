package net.zdsoft.office.dailyoffice.entity;

import java.io.Serializable;
import java.util.Date;
/**
 * office_car_info
 * @author 
 * 
 */
public class OfficeCarInfo implements Serializable{
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	private String id;
	/**
	 * 
	 */
	private String unitId;
	/**
	 * 
	 */
	private String carNumber;
	/**
	 * 
	 */
	private String carType;
	/**
	 * 
	 */
	private Date buyDate;
	/**
	 * 
	 */
	private Integer buyPrice;
	/**
	 * 
	 */
	private Integer seating;
	/**
	 * 
	 */
	private Boolean isDeleted;
	/**
	 * 
	 */
	private String remark;

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
	public void setUnitId(String unitId){
		this.unitId = unitId;
	}
	/**
	 * 获取
	 */
	public String getUnitId(){
		return this.unitId;
	}
	/**
	 * 设置
	 */
	public void setCarNumber(String carNumber){
		this.carNumber = carNumber;
	}
	/**
	 * 获取
	 */
	public String getCarNumber(){
		return this.carNumber;
	}
	/**
	 * 设置
	 */
	public void setCarType(String carType){
		this.carType = carType;
	}
	/**
	 * 获取
	 */
	public String getCarType(){
		return this.carType;
	}
	/**
	 * 设置
	 */
	public void setBuyDate(Date buyDate){
		this.buyDate = buyDate;
	}
	/**
	 * 获取
	 */
	public Date getBuyDate(){
		return this.buyDate;
	}
	/**
	 * 设置
	 */
	public void setBuyPrice(Integer buyPrice){
		this.buyPrice = buyPrice;
	}
	/**
	 * 获取
	 */
	public Integer getBuyPrice(){
		return this.buyPrice;
	}
	/**
	 * 设置
	 */
	public void setSeating(Integer seating){
		this.seating = seating;
	}
	/**
	 * 获取
	 */
	public Integer getSeating(){
		return this.seating;
	}
	/**
	 * 设置
	 */
	public void setIsDeleted(Boolean isDeleted){
		this.isDeleted = isDeleted;
	}
	/**
	 * 获取
	 */
	public Boolean getIsDeleted(){
		return this.isDeleted;
	}
	/**
	 * 设置
	 */
	public void setRemark(String remark){
		this.remark = remark;
	}
	/**
	 * 获取
	 */
	public String getRemark(){
		return this.remark;
	}
}