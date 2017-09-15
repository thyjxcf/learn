package net.zdsoft.eis.base.data.dto;

/* 
 * <p>数字校园2.0</p>
 * 学生用户的信息
 * @author fangb
 * @since  2.0
 * @version $Id:,v 2.0  Exp StudentUserDto.java, v 2.0 2008-7-21 下午01:18:12  Exp $
 */

public class StudentUserDto {
	private String id;	//关联到学生信息表的id，而不是用户表的id
	private String studentName;	//学生的姓名
	private String studentCode;//学号
	private String studentLoginName;	//学生的登陆帐户
	private String studentPassword;	//学生帐户的登陆密码
	
	private String tempLoginName;   //生成临时账户
	
	private String stuCode;
	private String identitycard;
	/**
	 * 临时用户名
	 */
	private String tempUserName;
	/**
	 * 临时身份证号
	 */
	private String tempIdCard;
	/**
	 * 学籍号
	 */
	private String unitiveCode;
	public String getStudentCode() {
		return studentCode;
	}
	public void setStudentCode(String studentCode) {
		this.studentCode = studentCode;
	}
	public String getTempLoginName() {
		return tempLoginName;
	}
	public void setTempLoginName(String tempLoginName) {
		this.tempLoginName = tempLoginName;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getStudentName() {
		return studentName;
	}
	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}
	public String getStudentLoginName() {
		return studentLoginName;
	}
	public void setStudentLoginName(String studentLoginName) {
		this.studentLoginName = studentLoginName;
	}
	public String getStudentPassword() {
		return studentPassword;
	}
	public void setStudentPassword(String studentPassword) {
		this.studentPassword = studentPassword;
	}
	
	public String getStuCode() {
		return stuCode;
	}
	public void setStuCode(String stuCode) {
		this.stuCode = stuCode;
	}
	public String getIdentitycard() {
		return identitycard;
	}
	public void setIdentitycard(String identitycard) {
		this.identitycard = identitycard;
	}
	public String getTempUserName() {
		return tempUserName;
	}
	public void setTempUserName(String tempUserName) {
		this.tempUserName = tempUserName;
	}
	public String getTempIdCard() {
		return tempIdCard;
	}
	public void setTempIdCard(String tempIdCard) {
		this.tempIdCard = tempIdCard;
	}
	
	public String getUnitiveCode() {
		return unitiveCode;
	}
	public void setUnitiveCode(String unitiveCode) {
		this.unitiveCode = unitiveCode;
	}
	/**
	 * 该学生是否开通
	 * @return
	 */
	public boolean isOpen(){
		return studentLoginName==null||studentLoginName.equals("")?false:true;
	}
	
	
	
}
