package net.zdsoft.eis.base.common.entity;

/* 
 * <p>数字校园2.0</p>
 * @author fangb
 * @since  2.0
 * @version $Id:,v 2.0  Exp TeacherUserDto.java, v 2.0 2008-7-21 下午01:51:41  Exp $
 */

public class FamilyUser {
	
	private String id;	//家长id，家长信息表的主键
	private String familyName;	//家长的姓名
	private String familyLoginName;	//家长的登录帐户
	private String familyPassword;	//家长登陆的密码
	private String relation;	//家长与学生的关系
	private String mobile;	//家长的手机号码
	private String studentId;	//关联的学生的学生id
	private String tempLoginName;
	private String identitycard;//身份证号
	
	private String studentUserName;//学生账号
	private String chargeNumber;
	private String email; // 电子邮箱
	
	private String userId;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getFamilyName() {
		return familyName;
	}
	public void setFamilyName(String familyName) {
		this.familyName = familyName;
	}
	public String getFamilyLoginName() {
		return familyLoginName;
	}
	public void setFamilyLoginName(String familyLoginName) {
		this.familyLoginName = familyLoginName;
	}
	public String getFamilyPassword() {
		return familyPassword;
	}
	public void setFamilyPassword(String familyPassword) {
		this.familyPassword = familyPassword;
	}
	public String getRelation() {
		return relation;
	}
	public void setRelation(String relation) {
		this.relation = relation;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getStudentId() {
		return studentId;
	}
	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}
	
	public String getTempLoginName() {
		return tempLoginName;
	}
	public void setTempLoginName(String tempLoginName) {
		this.tempLoginName = tempLoginName;
	}
	
	public String getIdentitycard() {
		return identitycard;
	}
	public void setIdentitycard(String identitycard) {
		this.identitycard = identitycard;
	}
	/**
	 * 该家长是否开通用户帐户
	 * @return
	 */
	public boolean isOpen(){
		return familyLoginName==null||familyLoginName.equals("")?false:true;
	}
	
	public String getStudentUserName() {
		return studentUserName;
	}
	public void setStudentUserName(String studentUserName) {
		this.studentUserName = studentUserName;
	}
	public String getChargeNumber() {
		return chargeNumber;
	}
	public void setChargeNumber(String chargeNumber) {
		this.chargeNumber = chargeNumber;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
}
