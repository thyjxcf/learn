package net.zdsoft.eis.base.data.dto;

/* 
 * <p>数字校园2.0</p>
 * @author fangb
 * @since  2.0
 * @version $Id:,v 2.0  Exp TeacherUserDto.java, v 2.0 2008-7-21 下午01:51:41  Exp $
 */

public class StuAndFamLoginDto {
	
	private String studentId;	    //关联到学生信息表的id，而不是用户表的id
	private String studentName;	    //学生的姓名
	private String studentLoginName;//学生的登录帐户
	private String studentPassword;	//学生帐户的登录密码
	private String familyId;	    //家长id，家长信息表的主键
	private String familyName;	    //家长的姓名
	private String familyLoginName;	//家长的登录帐户
	private String familyPassword;	//家长登陆的密码
	private String relation;	    //家长与学生的关系
	private String mobile;	        //家长的手机号码
	

	/**
	 * 该家长是否开通用户帐户
	 * @return
	 */
	public boolean isOpen(){
		return familyLoginName==null||familyLoginName.equals("")?false:true;
	}


	public String getFamilyId() {
		return familyId;
	}


	public void setFamilyId(String familyId) {
		this.familyId = familyId;
	}


	public String getFamilyLoginName() {
		return familyLoginName;
	}


	public void setFamilyLoginName(String familyLoginName) {
		this.familyLoginName = familyLoginName;
	}


	public String getFamilyName() {
		return familyName;
	}


	public void setFamilyName(String familyName) {
		this.familyName = familyName;
	}


	public String getFamilyPassword() {
		return familyPassword;
	}


	public void setFamilyPassword(String familyPassword) {
		this.familyPassword = familyPassword;
	}


	public String getMobile() {
		return mobile;
	}


	public void setMobile(String mobile) {
		this.mobile = mobile;
	}


	public String getRelation() {
		return relation;
	}


	public void setRelation(String relation) {
		this.relation = relation;
	}


	public String getStudentId() {
		return studentId;
	}


	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}


	public String getStudentLoginName() {
		return studentLoginName;
	}


	public void setStudentLoginName(String studentLoginName) {
		this.studentLoginName = studentLoginName;
	}


	public String getStudentName() {
		return studentName;
	}


	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}


	public String getStudentPassword() {
		return studentPassword;
	}


	public void setStudentPassword(String studentPassword) {
		this.studentPassword = studentPassword;
	}
}
