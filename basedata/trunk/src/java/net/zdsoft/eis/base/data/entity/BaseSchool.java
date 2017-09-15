/*
 * Created Thu Aug 10 14:45:04 CST 2006 by MyEclipse Hibernate Tool.
 */
package net.zdsoft.eis.base.data.entity;

import java.util.Date;

import net.zdsoft.eis.base.common.entity.School;

/**
 * A class that represents a row in the 'base_school' table. 
 * This class may be customized as it is never re-generated 
 * after being created.
 */
public class BaseSchool extends School {
    private static final long serialVersionUID = -6397155841727944668L;
    
    private String type;            //学校类别码
    private int gradeyear; // 小学规定年制
    private int junioryear; // 初中规定学制
    private int senioryear; // 高中规定年制
    private String englishname;		//学校英文名称
    private String address;			//学校地址
    private String partymaster;		//党组织负责人
    private Date datecreated;		//建校年月
    private String anniversary;		//校庆日		
    private String governor;		//学校主管部门
    private String regiontype;		//所在地区类别码
    private String regioneconomy;	//所在地区经济属性码
    private String regionnation;	//所在地区民族属性码
    private int gradeage;			//小学入学年龄
    private int juniorage;			//初中入学年龄
    private String primarylang;		//主教学语言码
    private String secondarylang;	//辅教育语言码
    private String recruitregion;	//招生区域
    private String postalcode;		//邮政编码
    private String linkphone;		//联系电话
    private String fax;				//传真电话
    private String email;			//电子信箱
    private String homepage;		//主页地址
    private String introduction;		//历史沿革
    private String area; //占地面积
    private String etohSchoolId; //学校id（家校互联使用）
    private String emphases; //重点级别
    private Integer boorish; //是否农民工子女定点学校
    private String demonstration; //示范等级
    private String shortName;
    private int infantyear;
    private int infantage;
    
    //===================================dto====================
    private String schdistrictname; //所在学区名称   
    private String synchroSchDistrict;     //是否同步学生的应服务学区标记，如果
                                           //值为"synchroSchDistrict"则同步,否则不同步   
    private String educode;//主管部门代码 
    
    
    public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}


	public String getEtohSchoolId() {
        return etohSchoolId;
    }


    public void setEtohSchoolId(String schoolId) {
        this.etohSchoolId = schoolId;
    }


    public String getArea() {
        return area;
    }


    public void setArea(String area) {
        this.area = area;
    }


    /**
     * Simple constructor of AbstractBasicSchoolinfo instances.
     */
    public BaseSchool(){}
   
    /**
     * Return the value of the englishname column.
     * @return String
     */
    public String getEnglishname()
    {
        return this.englishname;
    }

    /**
     * Set the value of the englishname column.
     * @param englishname
     */
    public void setEnglishname(String englishname)
    {
        this.englishname = englishname;
    }

    /**
     * Return the value of the address column.
     * @return String
     */
    public String getAddress()
    {
        return this.address;
    }

    /**
     * Set the value of the address column.
     * @param address
     */
    public void setAddress(String address)
    {
        this.address = address;
    }
  
    /**
     * Return the value of the partymaster column.
     * @return String
     */
    public String getPartymaster()
    {
        return this.partymaster;
    }

    /**
     * Set the value of the partymaster column.
     * @param partymaster
     */
    public void setPartymaster(String partymaster)
    {
        this.partymaster = partymaster;
    }

    /**
     * Return the value of the datecreated column.
     * @return java.util.Date
     */
    public java.util.Date getDatecreated()
    {
        return this.datecreated;
    }

    /**
     * Set the value of the datecreated column.
     * @param datecreated
     */
    public void setDatecreated(java.util.Date datecreated)
    {
        this.datecreated = datecreated;
    }

    /**
     * Return the value of the anniversary column.
     * @return String
     */
    public String getAnniversary()
    {
        return this.anniversary;
    }

    /**
     * Set the value of the anniversary column.
     * @param anniversary
     */
    public void setAnniversary(String anniversary)
    {
        this.anniversary = anniversary;
    }

    /**
     * Return the value of the governor column.
     * @return String
     */
    public String getGovernor()
    {
        return this.governor;
    }

    /**
     * Set the value of the governor column.
     * @param governor
     */
    public void setGovernor(String governor)
    {
        this.governor = governor;
    }
 
    /**
     * Return the value of the regiontype column.
     * @return String
     */
    public String getRegiontype()
    {
        return this.regiontype;
    }

    /**
     * Set the value of the regiontype column.
     * @param regiontype
     */
    public void setRegiontype(String regiontype)
    {
        this.regiontype = regiontype;
    }

    /**
     * Return the value of the regioneconomy column.
     * @return String
     */
    public String getRegioneconomy()
    {
        return this.regioneconomy;
    }

    /**
     * Set the value of the regioneconomy column.
     * @param regioneconomy
     */
    public void setRegioneconomy(String regioneconomy)
    {
        this.regioneconomy = regioneconomy;
    }

    /**
     * Return the value of the regionnation column.
     * @return String
     */
    public String getRegionnation()
    {
        return this.regionnation;
    }

    /**
     * Set the value of the regionnation column.
     * @param regionnation
     */
    public void setRegionnation(String regionnation)
    {
        this.regionnation = regionnation;
    }

    /**
     * Return the value of the gradeage column.
     * @return int
     */
    public int getGradeage()
    {
        return this.gradeage;
    }

    /**
     * Set the value of the gradeage column.
     * @param gradeage
     */
    public void setGradeage(int gradeage)
    {
        this.gradeage = gradeage;
    }

    /**
     * Return the value of the juniorage column.
     * @return int
     */
    public int getJuniorage()
    {
        return this.juniorage;
    }

    /**
     * Set the value of the juniorage column.
     * @param juniorage
     */
    public void setJuniorage(int juniorage)
    {
        this.juniorage = juniorage;
    }

    /**
     * Return the value of the primarylang column.
     * @return String
     */
    public String getPrimarylang()
    {
        return this.primarylang;
    }

    /**
     * Set the value of the primarylang column.
     * @param primarylang
     */
    public void setPrimarylang(String primarylang)
    {
        this.primarylang = primarylang;
    }

    /**
     * Return the value of the secondarylang column.
     * @return String
     */
    public String getSecondarylang()
    {
        return this.secondarylang;
    }

    /**
     * Set the value of the secondarylang column.
     * @param secondarylang
     */
    public void setSecondarylang(String secondarylang)
    {
        this.secondarylang = secondarylang;
    }

    /**
     * Return the value of the recruitregion column.
     * @return String
     */
    public String getRecruitregion()
    {
        return this.recruitregion;
    }

    /**
     * Set the value of the recruitregion column.
     * @param recruitregion
     */
    public void setRecruitregion(String recruitregion)
    {
        this.recruitregion = recruitregion;
    }

    /**
     * Return the value of the postalcode column.
     * @return String
     */
    public String getPostalcode()
    {
        return this.postalcode;
    }

    /**
     * Set the value of the postalcode column.
     * @param postalcode
     */
    public void setPostalcode(String postalcode)
    {
        this.postalcode = postalcode;
    }

    /**
     * Return the value of the linkphone column.
     * @return String
     */
    public String getLinkphone()
    {
        return this.linkphone;
    }

    /**
     * Set the value of the linkphone column.
     * @param linkphone
     */
    public void setLinkphone(String linkphone)
    {
        this.linkphone = linkphone;
    }

    /**
     * Return the value of the fax column.
     * @return String
     */
    public String getFax()
    {
        return this.fax;
    }

    /**
     * Set the value of the fax column.
     * @param fax
     */
    public void setFax(String fax)
    {
        this.fax = fax;
    }

    /**
     * Return the value of the email column.
     * @return String
     */
    public String getEmail()
    {
        return this.email;
    }

    /**
     * Set the value of the email column.
     * @param email
     */
    public void setEmail(String email)
    {
        this.email = email;
    }

    /**
     * Return the value of the homepage column.
     * @return String
     */
    public String getHomepage()
    {
        return this.homepage;
    }

    /**
     * Set the value of the homepage column.
     * @param homepage
     */
    public void setHomepage(String homepage)
    {
        this.homepage = homepage;
    }

    /**
     * Return the value of the introduction column.
     * @return String
     */
    public String getIntroduction()
    {
        return this.introduction;
    }

    /**
     * Set the value of the introduction column.
     * @param introduction
     */
    public void setIntroduction(String introduction)
    {
        this.introduction = introduction;
    }

    public String getEmphases() {
        return emphases;
    }


    public void setEmphases(String emphases) {
        this.emphases = emphases;
    }


    public Integer getBoorish() {
		return boorish;
	}

	public void setBoorish(Integer boorish) {
		this.boorish = boorish;
	}

	public String getDemonstration() {
        return demonstration;
    }

    public void setDemonstration(String demonstration) {
        this.demonstration = demonstration;
    }

	public int getInfantyear() {
		return infantyear;
	}

	public void setInfantyear(int infantyear) {
		this.infantyear = infantyear;
	}

	public int getInfantage() {
		return infantage;
	}

	public void setInfantage(int infantage) {
		this.infantage = infantage;
	}

    public String getSchdistrictname() {
        return schdistrictname;
    }

    public void setSchdistrictname(String schdistrictname) {
        this.schdistrictname = schdistrictname;
    }

    public String getSynchroSchDistrict() {
        return synchroSchDistrict;
    }

    public void setSynchroSchDistrict(String synchroSchDistrict) {
        this.synchroSchDistrict = synchroSchDistrict;
    }

    public String getEducode() {
        return educode;
    }

    public void setEducode(String educode) {
        this.educode = educode;
    }
	

    /**
     * Return the value of the type column.
     * 
     * @return String
     */
    public String getType() {
        return this.type;
    }

    /**
     * Set the value of the type column.
     * 
     * @param type
     */
    public void setType(String type) {
        this.type = type;
    }
    

    /**
     * Return the value of the gradeyear column.
     * 
     * @return int
     */
    public int getGradeyear() {
        return this.gradeyear;
    }

    /**
     * Set the value of the gradeyear column.
     * 
     * @param gradeyear
     */
    public void setGradeyear(int gradeyear) {
        this.gradeyear = gradeyear;
    }

    /**
     * Return the value of the junioryear column.
     * 
     * @return int
     */
    public int getJunioryear() {
        return this.junioryear;
    }

    /**
     * Set the value of the junioryear column.
     * 
     * @param junioryear
     */
    public void setJunioryear(int junioryear) {
        this.junioryear = junioryear;
    }

    /**
     * Return the value of the senioryear column.
     * 
     * @return int
     */
    public int getSenioryear() {
        return this.senioryear;
    }

    /**
     * Set the value of the senioryear column.
     * 
     * @param senioryear
     */
    public void setSenioryear(int senioryear) {
        this.senioryear = senioryear;
    }

}
