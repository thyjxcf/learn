/*
 * Created Thu Aug 10 14:45:04 CST 2006 by MyEclipse Hibernate Tool.
 */
package net.zdsoft.eis.base.common.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.annotation.JSONField;

import net.zdsoft.eis.base.util.SUtils;
import net.zdsoft.eis.frame.client.BaseEntity;

/**
 * A class that represents a row in the 'base_school' table. This class may be
 * customized as it is never re-generated after being created.
 */
public class School extends BaseEntity {
    private static final long serialVersionUID = -6397155841727944668L;
    
    /**
     * 学校名称
     */
    @JSONField(name="schoolName")
    private String name;
	/**
	 * 学校统一编号
	 */
    @JSONField(name="schoolCode")
    private String code;
    /**
	 * 本地自建统计区划（业务用行政区划码，region.type=TYPE_BUSINESS）
	 */
    @JSONField(name="regionCode")
    private String region;
    /**
     * 学校校长
     */
    
    private String shoolmaster;
    /**
     *  学校办别码 run_school_type
     */
    @JSONField(name="runSchoolType")
    private String runschtype;
    /**
     * 学校类别码 school_type
     */
    @JSONField(name="schoolType")
    private String type;
    /**
     * 学区id
     */
    @JSONField(name="schoolDistrictId")
    private String schdistrictid;
    /**
     * 学段集合
     */
    private String sections;
    /**
     * 机构驻地城乡类别
     */
    @JSONField(name="regionType")
    private String regiontype;
    /**
	 * 组织机构证号 organization_code
	 */
    @JSONField(name="organizationCode")
    private String organizationcode;
    
    //-------------------BaseSchool属性上提 by zhangkc 20150505---------------------
	/**
	 *  幼儿园规定年制 
	 */
    @JSONField(name="infantYear")
	private int infantyear;
	/**
	 *  幼儿园入学年龄 
	 */
    @JSONField(name="infantAge")
	private int infantage;
	/**
	 *  小学规定年制 
	 */
	@JSONField(name="gradeYear")
	private int gradeyear; 
	/**
	 * 小学入学年龄 
	 */
	@JSONField(name="gradeAge")
	private int gradeage;
	/**
	 *  初中规定学制 junior_year
	 */
	@JSONField(name="juniorYear")
	private int junioryear; 
	/**
	 * 初中入学年龄 
	 */
	@JSONField(name="juniorAge")
	private int juniorage;
	/**
	 *  高中规定年制 
	 */
	@JSONField(name="seniorYear")
	private int senioryear; 
	/**
	 * 学校英文名称 
	 */
	@JSONField(name="englishName")
	private String englishname;
	/**
	 * 学校地址 
	 */
	
	private String address;
	/**
	 * 党组织负责人 
	 */
	@JSONField(name="partyMaster")
	private String partymaster;
	/**
	 * 建校年月 build_date 
	 */
	@JSONField(name="buildDate")
	private Date datecreated;
	/**
	 * 校庆日 
	 */
	private String anniversary;
	/**
	 * 学校主管部门 
	 */
	private String governor;
	/**
	 * 所在地区经济属性码 
	 */
	@JSONField(name="regionEconomy")
	private String regioneconomy;
	/**
	 * 所在地区民族属性码 
	 */
	@JSONField(name="regionNation")
	private String regionnation;
	/**
	 * 主教学语言码 
	 */
	@JSONField(name="primaryLang")
	private String primarylang;
	/**
	 * 辅教育语言码 secondary_lang
	 */
	@JSONField(name="secondaryLang")
	private String secondarylang;
	/**
	 * 招生区域 recruit_region
	 */
	@JSONField(name="recruitRegion")
	private String recruitregion;
	/**
	 * 邮政编码 
	 */
	
	private String postalcode;
	/**
	 * 联系电话 
	 */
	@JSONField(name="linkPhone")
	private String linkphone;
	/**
	 * 传真电话 
	 */
	private String fax;
	/**
	 * 电子信箱 
	 */
	private String email;
	/**
	 * 主页地址 
	 */
	private String homepage;
	/**
	 * 历史沿革 
	 */
	private String introduction;
	/**
	 * 占地面积 
	 */
	private String area; 
	/**
	 * 学校id（家校互联使用）serial_number 
	 */
	@JSONField(name="serialNumber")
	private String etohSchoolId; 
	/**
	 * 重点级别 
	 */
	private String emphases; 
	/**
	 * 是否农民工子女定点学校 
	 */
	@JSONField(name="isBoorish")
	private Integer boorish; 
	/**
	 * 示范等级 
	 */
	private String demonstration;
	/**
	 * 学校简称 
	 */
	private String shortName;
    
    //----------------------台帐属性上提 by zhangkc 20150505----------------------
	/**
	 * 统计用学校性质
	 */
	private String schoolPropStat;
	/**
	 * 学校性质分组
	 */
	private String schoolTypeGroup;
	/**
	 * 学校（机构）属地管理教育行政部门
	 */
	private String regionManageDept;
	/**
	 * 学校（机构）属地管理教育行政部门代码
	 */
	private String regionManageDeptCode;
	/**
	 * 学校地址码
	 */
	private String addressCode;
	/**
	 * 法人登记证号
	 */
	private String legaRegistrationNumber;
	/**
	 * 学校产权土地证号
	 */
	private String landCertificateNo;
	/**
	 * 学校所在地经度
	 */
	private double longitude;
	/**
	 * 学校所在地纬度
	 */
	private double latitude;
	/**
	 * 填表人电子邮箱
	 */
	private String fillEmail;
	/**
	 * 学校统计人员姓名
	 */
	private String statStuffName;
	/**
	 * 学校统计人员联系方式
	 */
	private String statStuffContact;
	/**
	 * 是否学区长学校
	 */
	@JSONField(name="isSchDistrictChief")
	private boolean isSchDistrictChief;
	/**
	 * 学校所属标准行政区划（标准行政区划码，含教育局设置的行政区划region.type=TYPE_STAT）
	 */
	private String statRegionCode;
	/** 本地自建统计行政区划（本地特色的行政区划，是某些地区特有的自己设置的行政区划region.type=TYPE_SPECIAL） */
	private String specialRegionCode;
	/** 学校特色 */
	private String feature;
	/** 是否公办民助 */
	private Integer publicAssist;
	/** 手机号码 */
	private String mobilePhone;
	/** 区域属性码 */
	private String regionPropertyCode;
	/** 性质类别码 */
	private String natureType;
	/** 是否独立设置少数民族学校 */
	private boolean isMinoritySchool;
	/** 幼儿园级别 */
	private String kgLevel;
	/** 学校举办者名称 */
	private String schoolSetupName;
	//add by chens type字段被ledgerSchoolType这个字段数据填充
	private String schoolType;

    // ================================dto辅助字段====================
	/**
	 * 所在行政区名称
	 */
    private String regionname; 

    public String getSections() {
        return sections;
    }

    public void setSections(String sections) {
        this.sections = sections;
    }

    /**
     * Simple constructor of AbstractBasicSchoolinfo instances.
     */
    public School() {
    }

    /**
     * Return the value of the name column.
     * 
     * @return String
     */
    public String getName() {
        return this.name;
    }

    /**
     * Set the value of the name column.
     * 
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Return the value of the code column.
     * 
     * @return String
     */
    public String getCode() {
        return this.code;
    }

    /**
     * Set the value of the code column.
     * 
     * @param code
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * Return the value of the region column.
     * 
     * @return String
     */
    public String getRegion() {
        return this.region;
    }

    /**
     * Set the value of the region column.
     * 
     * @param region
     */
    public void setRegion(String region) {
        this.region = region;
    }

    /**
     * Return the value of the shoolmaster column.
     * 
     * @return String
     */
    public String getShoolmaster() {
        return this.shoolmaster;
    }

    /**
     * Set the value of the shoolmaster column.
     * 
     * @param shoolmaster
     */
    public void setShoolmaster(String shoolmaster) {
        this.shoolmaster = shoolmaster;
    }

    /**
     * Return the value of the runschtype column.
     * 
     * @return String
     */
    public String getRunschtype() {
        return this.runschtype;
    }

    /**
     * Set the value of the runschtype column.
     * 
     * @param runschtype
     */
    public void setRunschtype(String runschtype) {
        this.runschtype = runschtype;
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

    public String getSchdistrictid() {
        return schdistrictid;
    }

    public void setSchdistrictid(String schdistrictid) {
        this.schdistrictid = schdistrictid;
    }

    public String getRegionname() {
        return regionname;
    }

    public void setRegionname(String regionname) {
        this.regionname = regionname;
    }

	public String getRegiontype() {
		return regiontype;
	}

	public void setRegiontype(String regiontype) {
		this.regiontype = regiontype;
	}

	public String getOrganizationcode() {
		return organizationcode;
	}

	public void setOrganizationcode(String organizationcode) {
		this.organizationcode = organizationcode;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getGovernor() {
		return governor;
	}

	public void setGovernor(String governor) {
		this.governor = governor;
	}

	public String getPostalcode() {
		return postalcode;
	}

	public void setPostalcode(String postalcode) {
		this.postalcode = postalcode;
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

	public String getHomepage() {
		return homepage;
	}

	public void setHomepage(String homepage) {
		this.homepage = homepage;
	}

	public String getSchoolPropStat() {
		return schoolPropStat;
	}

	public void setSchoolPropStat(String schoolPropStat) {
		this.schoolPropStat = schoolPropStat;
	}

	public String getSchoolTypeGroup() {
		return schoolTypeGroup;
	}

	public void setSchoolTypeGroup(String schoolTypeGroup) {
		this.schoolTypeGroup = schoolTypeGroup;
	}

	/**
	 * @deprecated 用getType()代替
	 * @return
	 */
	public String getLedgerSchoolType() {
	    return getType();
	}

	/**
     * @deprecated 用setType(String)代替
     * @return
     */
	public void setLedgerSchoolType(String ledgerSchoolType) {
		setType(ledgerSchoolType);
	}

	public String getRegionManageDept() {
		return regionManageDept;
	}

	public void setRegionManageDept(String regionManageDept) {
		this.regionManageDept = regionManageDept;
	}

	public String getRegionManageDeptCode() {
		return regionManageDeptCode;
	}

	public void setRegionManageDeptCode(String regionManageDeptCode) {
		this.regionManageDeptCode = regionManageDeptCode;
	}

	public String getAddressCode() {
		return addressCode;
	}

	public void setAddressCode(String addressCode) {
		this.addressCode = addressCode;
	}

	public String getLegaRegistrationNumber() {
		return legaRegistrationNumber;
	}

	public void setLegaRegistrationNumber(String legaRegistrationNumber) {
		this.legaRegistrationNumber = legaRegistrationNumber;
	}

	public String getLandCertificateNo() {
		return landCertificateNo;
	}

	public void setLandCertificateNo(String landCertificateNo) {
		this.landCertificateNo = landCertificateNo;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public String getFillEmail() {
		return fillEmail;
	}

	public void setFillEmail(String fillEmail) {
		this.fillEmail = fillEmail;
	}

	public String getStatStuffName() {
		return statStuffName;
	}

	public void setStatStuffName(String statStuffName) {
		this.statStuffName = statStuffName;
	}

	public String getStatStuffContact() {
		return statStuffContact;
	}

	public void setStatStuffContact(String statStuffContact) {
		this.statStuffContact = statStuffContact;
	}

	public boolean isSchDistrictChief() {
		return isSchDistrictChief;
	}

	public void setSchDistrictChief(boolean isSchDistrictChief) {
		this.isSchDistrictChief = isSchDistrictChief;
	}

	public String getStatRegionCode() {
		return statRegionCode;
	}

	public void setStatRegionCode(String statRegionCode) {
		this.statRegionCode = statRegionCode;
	}

	public String getSpecialRegionCode() {
		return specialRegionCode;
	}

	public void setSpecialRegionCode(String specialRegionCode) {
		this.specialRegionCode = specialRegionCode;
	}

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public String getAnniversary() {
		return anniversary;
	}

	public void setAnniversary(String anniversary) {
		this.anniversary = anniversary;
	}

	public String getIntroduction() {
		return introduction;
	}

	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getFeature() {
		return feature;
	}

	public void setFeature(String feature) {
		this.feature = feature;
	}

	public Integer getPublicAssist() {
		return publicAssist;
	}

	public void setPublicAssist(Integer publicAssist) {
		this.publicAssist = publicAssist;
	}

	public String getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	public String getRegionPropertyCode() {
		return regionPropertyCode;
	}

	public void setRegionPropertyCode(String regionPropertyCode) {
		this.regionPropertyCode = regionPropertyCode;
	}

	public String getNatureType() {
		return natureType;
	}

	public void setNatureType(String natureType) {
		this.natureType = natureType;
	}

	public boolean isMinoritySchool() {
		return isMinoritySchool;
	}

	public void setMinoritySchool(boolean isMinoritySchool) {
		this.isMinoritySchool = isMinoritySchool;
	}

	public String getKgLevel() {
		return kgLevel;
	}

	public void setKgLevel(String kgLevel) {
		this.kgLevel = kgLevel;
	}

	public String getSchoolSetupName() {
		return schoolSetupName;
	}

	public void setSchoolSetupName(String schoolSetupName) {
		this.schoolSetupName = schoolSetupName;
	}

	public int getGradeyear() {
		return gradeyear;
	}

	public void setGradeyear(int gradeyear) {
		this.gradeyear = gradeyear;
	}

	public int getJunioryear() {
		return junioryear;
	}

	public void setJunioryear(int junioryear) {
		this.junioryear = junioryear;
	}

	public int getSenioryear() {
		return senioryear;
	}

	public void setSenioryear(int senioryear) {
		this.senioryear = senioryear;
	}

	public String getEnglishname() {
		return englishname;
	}

	public void setEnglishname(String englishname) {
		this.englishname = englishname;
	}

	public String getPartymaster() {
		return partymaster;
	}

	public void setPartymaster(String partymaster) {
		this.partymaster = partymaster;
	}

	public Date getDatecreated() {
		return datecreated;
	}

	public void setDatecreated(Date datecreated) {
		this.datecreated = datecreated;
	}

	public String getRegioneconomy() {
		return regioneconomy;
	}

	public void setRegioneconomy(String regioneconomy) {
		this.regioneconomy = regioneconomy;
	}

	public String getRegionnation() {
		return regionnation;
	}

	public void setRegionnation(String regionnation) {
		this.regionnation = regionnation;
	}

	public int getGradeage() {
		return gradeage;
	}

	public void setGradeage(int gradeage) {
		this.gradeage = gradeage;
	}

	public int getJuniorage() {
		return juniorage;
	}

	public void setJuniorage(int juniorage) {
		this.juniorage = juniorage;
	}

	public String getPrimarylang() {
		return primarylang;
	}

	public void setPrimarylang(String primarylang) {
		this.primarylang = primarylang;
	}

	public String getSecondarylang() {
		return secondarylang;
	}

	public void setSecondarylang(String secondarylang) {
		this.secondarylang = secondarylang;
	}

	public String getRecruitregion() {
		return recruitregion;
	}

	public void setRecruitregion(String recruitregion) {
		this.recruitregion = recruitregion;
	}

	public String getLinkphone() {
		return linkphone;
	}

	public void setLinkphone(String linkphone) {
		this.linkphone = linkphone;
	}

	public String getEtohSchoolId() {
		return etohSchoolId;
	}

	public void setEtohSchoolId(String etohSchoolId) {
		this.etohSchoolId = etohSchoolId;
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

	public String getSchoolType() {
		return schoolType;
	}

	public void setSchoolType(String schoolType) {
		this.schoolType = schoolType;
	}

	
	
	public static School dc(String data) {
		return SUtils.dc(data, School.class);
	}
	
	public static List<School> dt(String data) {
		List<School> ts = SUtils.dt(data, new TypeReference<List<School>>() {
		});
		if (ts == null)
			ts = new ArrayList<School>();
		return ts;

	}
}
