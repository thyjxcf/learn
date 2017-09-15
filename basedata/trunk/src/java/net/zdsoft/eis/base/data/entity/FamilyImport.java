package net.zdsoft.eis.base.data.entity;


/**
 *
 * @author hexq
 * @version $Revision: 1.0 $, $Date: 2009-10-28 下午04:35:31 $
 */
public class FamilyImport {
	
		private String stucode;			//学生学号
    	private String unitivecode; 	//学生学籍号
    	private String stuname;			//学生姓名
    	private String realname;		//家长姓名
    	private String sex;				//性别
    	private String birthday;		//出生日期
    	private String nation;			//民族
    	private String linkphone;		//联系电话
    	private String relation;		//关系
    	private String workcode;		//职业
    	private String duty;			//职务
    	private String professioncode;	//专业技术职务
    	private String dutylevel;		//职务级别
    	private String maritalstatus;	//婚姻状况
    	private String company;			//单位
    	private String politicalstatus;	//政治面貌
    	private String culture;			//文化程度
    	private String emigrationplace;	//侨居地
    	private String isguardian;		//是否监护人
    	private String homepage;		//个人主页
    	private String postalcode;		//邮政编码
    	private String linkaddress;		//联系地址
    	private String mobilephone;		//手机号码
    	private String email;			//电子邮箱
    	private String officetel;		//办公电话
    	private String remark;			//备注
    	private String identitycard;	//身份证号
    	private String chargenumber;	//扣费号码
    	//中策全国模板字段维护 add by like 2014-12-3
        private String identitycardType;//成员身份证件类型
    	private String health;//成员健康状况
    	
    	
//    	隐含字段
    	private String stuid;      		//学生id
    	private String schid;			//学校id
    	private String regioncode;		//学校的地区码
    	
		public String getBirthday() {
			return birthday;
		}
		public void setBirthday(String birthday) {
			this.birthday = birthday;
		}
		public String getChargenumber() {
			return chargenumber;
		}
		public void setChargenumber(String chargenumber) {
			this.chargenumber = chargenumber;
		}
		public String getCompany() {
			return company;
		}
		public void setCompany(String company) {
			this.company = company;
		}
		public String getCulture() {
			return culture;
		}
		public void setCulture(String culture) {
			this.culture = culture;
		}
		public String getDuty() {
			return duty;
		}
		public void setDuty(String duty) {
			this.duty = duty;
		}
		public String getDutylevel() {
			return dutylevel;
		}
		public void setDutylevel(String dutylevel) {
			this.dutylevel = dutylevel;
		}
		public String getEmail() {
			return email;
		}
		public void setEmail(String email) {
			this.email = email;
		}
		public String getEmigrationplace() {
			return emigrationplace;
		}
		public void setEmigrationplace(String emigrationplace) {
			this.emigrationplace = emigrationplace;
		}
		public String getHomepage() {
			return homepage;
		}
		public void setHomepage(String homepage) {
			this.homepage = homepage;
		}
		public String getIdentitycard() {
			return identitycard;
		}
		public void setIdentitycard(String identitycard) {
			this.identitycard = identitycard;
		}
		public String getIsguardian() {
			return isguardian;
		}
		public void setIsguardian(String isguardian) {
			this.isguardian = isguardian;
		}
		public String getLinkaddress() {
			return linkaddress;
		}
		public void setLinkaddress(String linkaddress) {
			this.linkaddress = linkaddress;
		}
		public String getLinkphone() {
			return linkphone;
		}
		public void setLinkphone(String linkphone) {
			this.linkphone = linkphone;
		}
		public String getMaritalstatus() {
			return maritalstatus;
		}
		public void setMaritalstatus(String maritalstatus) {
			this.maritalstatus = maritalstatus;
		}
		public String getMobilephone() {
			return mobilephone;
		}
		public void setMobilephone(String mobilephone) {
			this.mobilephone = mobilephone;
		}
		public String getNation() {
			return nation;
		}
		public void setNation(String nation) {
			this.nation = nation;
		}
		public String getOfficetel() {
			return officetel;
		}
		public void setOfficetel(String officetel) {
			this.officetel = officetel;
		}
		public String getPoliticalstatus() {
			return politicalstatus;
		}
		public void setPoliticalstatus(String politicalstatus) {
			this.politicalstatus = politicalstatus;
		}
		public String getPostalcode() {
			return postalcode;
		}
		public void setPostalcode(String postalcode) {
			this.postalcode = postalcode;
		}
		public String getProfessioncode() {
			return professioncode;
		}
		public void setProfessioncode(String professioncode) {
			this.professioncode = professioncode;
		}
		public String getRealname() {
			return realname;
		}
		public void setRealname(String realname) {
			this.realname = realname;
		}
		public String getRelation() {
			return relation;
		}
		public void setRelation(String relation) {
			this.relation = relation;
		}
		public String getRemark() {
			return remark;
		}
		public void setRemark(String remark) {
			this.remark = remark;
		}
		public String getSchid() {
			return schid;
		}
		public void setSchid(String schid) {
			this.schid = schid;
		}
		public String getSex() {
			return sex;
		}
		public void setSex(String sex) {
			this.sex = sex;
		}
		
		public String getUnitivecode() {
			return unitivecode;
		}
		public void setUnitivecode(String unitivecode) {
			this.unitivecode = unitivecode;
		}
		public String getStuid() {
			return stuid;
		}
		public void setStuid(String stuid) {
			this.stuid = stuid;
		}
		public String getStuname() {
			return stuname;
		}
		public void setStuname(String stuname) {
			this.stuname = stuname;
		}
		public String getWorkcode() {
			return workcode;
		}
		public void setWorkcode(String workcode) {
			this.workcode = workcode;
		}
		public String getRegioncode() {
			return regioncode;
		}
		public void setRegioncode(String regioncode) {
			this.regioncode = regioncode;
		}
		public String getStucode() {
			return stucode;
		}
		public void setStucode(String stucode) {
			this.stucode = stucode;
		}
		public String getIdentitycardType() {
			return identitycardType;
		}
		public void setIdentitycardType(String identitycardType) {
			this.identitycardType = identitycardType;
		}
		public String getHealth() {
			return health;
		}
		public void setHealth(String health) {
			this.health = health;
		}
    	
		
		
    	
}