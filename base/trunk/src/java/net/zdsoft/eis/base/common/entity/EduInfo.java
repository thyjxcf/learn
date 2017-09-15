package net.zdsoft.eis.base.common.entity;

import net.zdsoft.eis.frame.client.BaseEntity;

public class EduInfo extends BaseEntity {
	private static final long serialVersionUID = 3875862536808158723L;

	private String id; // 教育局ID
	private String principal; // 负责人
	private int nationPoverty; // 是否贫困县
	private Boolean isAutonomy; // 是否民族自治区
	private Boolean isFrontier; // 是否边疆县
	private String homepage;//主页
	private String manager; //局负责人
	private String director;//管负责人
	private String statistician; //统计负责人
	private String eduCode; //统计负责人
	private String domainUrl; //域名url
	private Boolean isUseDomain; //使用域名

	//=====================辅助字段=====================
	private String name; // 教育局名称
	private String unionid; // 单位统一编号
	private String telephone; // 电话号码
	private String postalcode; // 邮政编码
	private String address; // 地址
	private String fax; //传真
	private String email; //email

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPrincipal() {
		return principal;
	}

	public void setPrincipal(String principal) {
		this.principal = principal;
	}

	public Boolean getIsAutonomy() {
		return isAutonomy;
	}

	public void setIsAutonomy(Boolean isAutonomy) {
		this.isAutonomy = isAutonomy;
	}

	public Boolean getIsFrontier() {
		return isFrontier;
	}

	public void setIsFrontier(Boolean isFrontier) {
		this.isFrontier = isFrontier;
	}

	public String getHomepage() {
		return homepage;
	}

	public void setHomepage(String homepage) {
		this.homepage = homepage;
	}

	public String getManager() {
		return manager;
	}

	public void setManager(String manager) {
		this.manager = manager;
	}

	public String getDirector() {
		return director;
	}

	public void setDirector(String director) {
		this.director = director;
	}

	public String getStatistician() {
		return statistician;
	}

	public void setStatistician(String statistician) {
		this.statistician = statistician;
	}

	public String getEduCode() {
		return eduCode;
	}

	public void setEduCode(String eduCode) {
		this.eduCode = eduCode;
	}

	public String getDomainUrl() {
		return domainUrl;
	}

	public void setDomainUrl(String domainUrl) {
		this.domainUrl = domainUrl;
	}

	public Boolean getIsUseDomain() {
		return isUseDomain;
	}

	public void setIsUseDomain(Boolean isUseDomain) {
		this.isUseDomain = isUseDomain;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUnionid() {
		return unionid;
	}

	public void setUnionid(String unionid) {
		this.unionid = unionid;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getPostalcode() {
		return postalcode;
	}

	public void setPostalcode(String postalcode) {
		this.postalcode = postalcode;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getNationPoverty() {
		return nationPoverty;
	}

	public void setNationPoverty(int nationPoverty) {
		this.nationPoverty = nationPoverty;
	}

}
