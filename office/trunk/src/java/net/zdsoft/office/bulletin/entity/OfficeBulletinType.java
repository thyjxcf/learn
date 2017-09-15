package net.zdsoft.office.bulletin.entity;

import java.io.Serializable;
/**
 * office_bulletin_type
 * @author 
 * 
 */
public class OfficeBulletinType implements Serializable{
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	private String id;
	/**
	 * 
	 */
	private String name;
	/**
	 * 
	 */
	private String type;
	
	private boolean isShow;//是否在桌面显示
	
	private boolean needSms;//是否需要发短信
	
	public static final String ZCWJ = "4";//政策文件
	public static final String JYXX = "12";//教育信息，新疆定制
	
	
	public boolean getNeedSms() {
			return needSms;
	}
	public void setNeedSms(boolean needSms) {
		this.needSms = needSms;
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
	public void setType(String type){
		this.type = type;
	}
	/**
	 * 获取
	 */
	public String getType(){
		return this.type;
	}
	public boolean isShow() {
		return isShow;
	}
	public void setShow(boolean isShow) {
		this.isShow = isShow;
	}
}