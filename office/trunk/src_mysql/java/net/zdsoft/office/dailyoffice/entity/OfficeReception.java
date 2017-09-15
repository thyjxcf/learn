package net.zdsoft.office.dailyoffice.entity;


import java.io.Serializable;
import java.util.Date;
/**
 * office_reception
 * @author 
 * 
 */
public class OfficeReception implements Serializable{
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	private String id;
	/**
	 * 部门编号
	 */
	private String unitId;
	/**
	 * 接待地点
	 */
	private String place;
	/**
	 * 接待时间
	 */
	private Date receptionDate;
	/**
	 * 开始工作负责人
	 */
	private String startWorkUserId;
	/**
	 * 内容
	 */
	private String content;
	/**
	 * 是否用餐
	 */
	private Boolean isDining;
	/**
	 * 用餐人数
	 */
	private Integer personNumber;
	/**
	 * 用餐标准
	 */
	private String standard;
	/**
	 * 用车情况
	 */
	private String carSituation;
	/**
	 * 是否住宿
	 */
	private Boolean isAcommodation;
	/**
	 * 接待人
	 */
	private String receptionUserId;
	/**
	 * 陪同人
	 */
	private String accompanyPerson;
	/**
	 * 拍照人员
	 */
	private String camemaPerson;
	/**
	 * 创建用户
	 */
	private String createUserId;
	/**
	 * 创建时间
	 */
	private Date createTime;
	
	//辅助字段
	private String receptionUserName;
	private String  accompanyPersonName;
	private String camemaPersonName;
	private String startWorkUserName;
	
	
	

	public String getAccompanyPersonName() {
		return accompanyPersonName;
	}
	public void setAccompanyPersonName(String accompanyPersonName) {
		this.accompanyPersonName = accompanyPersonName;
	}
	public String getCamemaPersonName() {
		return camemaPersonName;
	}
	public void setCamemaPersonName(String camemaPersonName) {
		this.camemaPersonName = camemaPersonName;
	}
	public String getStartWorkUserName() {
		return startWorkUserName;
	}
	public void setStartWorkUserName(String startWorkUserName) {
		this.startWorkUserName = startWorkUserName;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getReceptionUserName() {
		return receptionUserName;
	}
	public void setReceptionUserName(String receptionUserName) {
		this.receptionUserName = receptionUserName;
	}
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
	public void setStartWorkUserId(String startWorkUserId){
		this.startWorkUserId = startWorkUserId;
	}
	public Date getReceptionDate() {
		return receptionDate;
	}
	public void setReceptionDate(Date receptionDate) {
		this.receptionDate = receptionDate;
	}
	/**
	 * 获取
	 */
	public String getStartWorkUserId(){
		return this.startWorkUserId;
	}
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
	/**
	 * 设置
	 */
	public void setIsDining(Boolean isDining){
		this.isDining = isDining;
	}
	/**
	 * 获取
	 */
	public Boolean getIsDining(){
		return this.isDining;
	}
	/**
	 * 设置
	 */
	public void setPersonNumber(Integer personNumber){
		this.personNumber = personNumber;
	}
	/**
	 * 获取
	 */
	public Integer getPersonNumber(){
		return this.personNumber;
	}
	/**
	 * 设置
	 */
	public void setStandard(String standard){
		this.standard = standard;
	}
	/**
	 * 获取
	 */
	public String getStandard(){
		return this.standard;
	}
	/**
	 * 设置
	 */
	public void setCarSituation(String carSituation){
		this.carSituation = carSituation;
	}
	/**
	 * 获取
	 */
	public String getCarSituation(){
		return this.carSituation;
	}
	/**
	 * 设置
	 */
	public void setIsAcommodation(Boolean isAcommodation){
		this.isAcommodation = isAcommodation;
	}
	/**
	 * 获取
	 */
	public Boolean getIsAcommodation(){
		return this.isAcommodation;
	}
	/**
	 * 设置
	 */
	public void setReceptionUserId(String receptionUserId){
		this.receptionUserId = receptionUserId;
	}
	/**
	 * 获取
	 */
	public String getReceptionUserId(){
		return this.receptionUserId;
	}
	/**
	 * 设置
	 */
	public void setAccompanyPerson(String accompanyPerson){
		this.accompanyPerson = accompanyPerson;
	}
	/**
	 * 获取
	 */
	public String getAccompanyPerson(){
		return this.accompanyPerson;
	}
	/**
	 * 设置
	 */
	public void setCamemaPerson(String camemaPerson){
		this.camemaPerson = camemaPerson;
	}
	/**
	 * 获取
	 */
	public String getCamemaPerson(){
		return this.camemaPerson;
	}
	/**
	 * 设置
	 */
	public void setCreateUserId(String createUserId){
		this.createUserId = createUserId;
	}
	/**
	 * 获取
	 */
	public String getCreateUserId(){
		return this.createUserId;
	}
	/**
	 * 设置
	 */
	public void setCreateTime(Date createTime){
		this.createTime = createTime;
	}
	/**
	 * 获取
	 */
	public Date getCreateTime(){
		return this.createTime;
	}
}