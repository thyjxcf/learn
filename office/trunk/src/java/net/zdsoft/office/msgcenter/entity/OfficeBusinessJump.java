package net.zdsoft.office.msgcenter.entity;

import java.io.Serializable;
import java.util.Date;

import net.zdsoft.eis.base.common.entity.Module;
/**
 * office_business_jump
 * @author 
 * 
 */
public class OfficeBusinessJump implements Serializable{
	private static final long serialVersionUID = 1L;

	public static final int OFFICE_TEACHER_LEAVE = 1; //教师请假
	public static final int OFFICE_GO_OUT = 2; //外出管理
	public static final int OFFICE_EVECTION = 3; //出差管理
	public static final int OFFICE_EXPENSE = 4; //报销管理
	public static final int OFFICE_GOODS = 5; //物品管理
	public static final int OFFICE_REPAIRE = 6; //报修管理
	public static final int OFFICE_ROOM_ORDER = 7; //教室预约
	public static final int OFFICE_ASSET = 8; //资产管理
	public static final int OFFICE_CUSTOMER = 9; //客户管理
	public static final int OFFICE_MEETING = 10; //会议室管理
	public static final int OFFICE_SEAL_MANAGE = 28; //用印管理
	public static final int OFFICE_JT_GOOUT = 12; //集体外出
	public static final int OFFICE_BULLETIN=13;//通知公告
	
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
	private String msgId;
	/**
	 * 
	 */
	private String receivers;
	/**
	 * 
	 */
	private String receiverType;
	/**
	 * 
	 */
	private String modules;
	/**
	 * 
	 */
	private String content;
	/**
	 * 
	 */
	private Date createTime;
	
	//辅助字段
	private Module module;//跳转的模块
	private String index;//跳转的模块处理页中切换到tab页的序号，从0开始，-1表示不处理
	private String loadUrl;
	
	private boolean bulletin;//是否通知公告模块
	private String bulletinId;

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
	public void setMsgId(String msgId){
		this.msgId = msgId;
	}
	/**
	 * 获取
	 */
	public String getMsgId(){
		return this.msgId;
	}
	/**
	 * 设置
	 */
	public void setReceivers(String receivers){
		this.receivers = receivers;
	}
	/**
	 * 获取
	 */
	public String getReceivers(){
		return this.receivers;
	}
	/**
	 * 设置
	 */
	public void setReceiverType(String receiverType){
		this.receiverType = receiverType;
	}
	/**
	 * 获取
	 */
	public String getReceiverType(){
		return this.receiverType;
	}
	/**
	 * 设置
	 */
	public void setModules(String modules){
		this.modules = modules;
	}
	/**
	 * 获取
	 */
	public String getModules(){
		return this.modules;
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
	public void setCreateTime(Date createTime){
		this.createTime = createTime;
	}
	/**
	 * 获取
	 */
	public Date getCreateTime(){
		return this.createTime;
	}
	public Module getModule() {
		return module;
	}
	public void setModule(Module module) {
		this.module = module;
	}
	public String getIndex() {
		return index;
	}
	public void setIndex(String index) {
		this.index = index;
	}
	public String getLoadUrl() {
		return loadUrl;
	}
	public void setLoadUrl(String loadUrl) {
		this.loadUrl = loadUrl;
	}
	public boolean isBulletin() {
		return bulletin;
	}
	public void setBulletin(boolean bulletin) {
		this.bulletin = bulletin;
	}
	public String getBulletinId() {
		return bulletinId;
	}
	public void setBulletinId(String bulletinId) {
		this.bulletinId = bulletinId;
	}
}