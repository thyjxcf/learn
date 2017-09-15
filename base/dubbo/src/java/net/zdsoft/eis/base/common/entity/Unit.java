package net.zdsoft.eis.base.common.entity;

import java.util.ArrayList;
import java.util.List;

import net.zdsoft.eis.base.constant.BaseConstant;
import net.zdsoft.eis.base.util.SUtils;
import net.zdsoft.eis.frame.client.BaseEntity;
import net.zdsoft.keel.util.Pagination;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Unit extends BaseEntity {
    private static final long serialVersionUID = -755236984759007826L;
    
    public static List<Unit> dt(String data) {
		List<Unit> ts = SUtils.dt(data, new TypeReference<List<Unit>>() {
		});
		if (ts == null)
			ts = new ArrayList<Unit>();
		return ts;

	}
    
    public static List<Unit> dt(String data, Pagination page) {
		JSONObject json = JSONObject.parseObject(data);
		List<Unit> ts = SUtils.dt(json.getString("data"), new TypeReference<List<Unit>>() {
		});
		if (ts == null)
			ts = new ArrayList<Unit>();
		if (json.containsKey("count"))
			page.setMaxRowCount(json.getInteger("count"));
		
		return ts;

	}

	public static Unit dc(String data) {
		return SUtils.dc(data, Unit.class);
	}

    /**
     * 单位名称
     */
    @JsonProperty(value="unitName")
    private String name;
    /**
     * 单位编号union_code
     */
    @JsonProperty(value="unionCode")
    @JSONField(name="unionCode")
    private String unionid;
    /**
     *  单位分类 :1=教育局 2=学校
     */
    @JsonProperty(value="unitClass")
    @JSONField(name="unitClass")
    private Integer unitclass;
    /**
     *  单位类型 1=顶经教育局 2=下属教育局 3=托管中小学 4=大中专学校 5幼儿园学校 6=EISS中小学 7=EISV大中专学校8=非教育局单位
     */
    @JsonProperty(value="unitType")
    @JSONField(name="unitType")
    private Integer unittype;
    /**
     * 单位状态unit_state
     */
    @JsonProperty(value="unitState")
    @JSONField(name="unitState")
    private Integer mark;

	/**
     * 上级单位Id
     */
    @JsonProperty(value="parentId")
    @JSONField(name="parentId")
    private String parentid;
    /**
     * 排序号display_order
     */
    @JsonIgnore
    @JSONField(name="displayOrder")
    private Long orderid;
    /**
     * 使用类型：0-顶级平台，1-本平台单位，2-报送单位
     */
    @JsonIgnore
    @JSONField(name="useType")
    private Integer usetype;
    /**
     * 行政区划级别
     */
    @JsonIgnore
    private Integer regionlevel;
    /**
     * 行政区划码region_code(业务用行政区划码)
     */
    @JSONField(name="regionCode")
    private String region;	
    /**
     *  分区号
     */
    @JsonIgnore
    private Long unitPartitionNum;
    /**
     *  ETO学校id = serial_number
     */
    @JsonIgnore
    @JSONField(name="serialNumber")
    private String etohSchoolId;
    /**
     * 是否能发送短信给教师
     */
    @JsonIgnore
    @JSONField(name="isTeacherSms")
    private Integer teacherEnableSms;
    /**
     * 费用类型
     */
    @JsonIgnore
    private Integer feeType;
    /**
     * 联系电话
     */
    private String linkPhone;
    /**
     * 电子邮箱
     */
    private String email;
    /**
     * 邮政编码
     */
    private String postalcode;
    /**
     * 地址
     */
    private String address;
    /**
     * 单位网站首页
     */
    private String homepage;
    /**
     * 
     */
    @JsonIgnore
    private Integer limitTeacher;
    /**
     *  学校类型
     */
    @JsonIgnore
    @JSONField(name="schoolType")
    private String schtype; 
    /**
     * 办学类别
     */
    @JsonIgnore
    @JSONField(name="runSchoolType")
    private String runschtype;
    
    //-----------------------BaseUnit上提 by zhangkc 20150505---------------------------
    /**
     * 注册码poll_code
     */
    @JsonIgnore
    @JSONField(name="pollCode")
    private String regcode;
    @JsonIgnore
    private Integer authorized;
    /**
     * 单位使用类型
     */
    @JsonIgnore
    private String unitusetype;
    @JsonIgnore
    @JSONField(name="isGuestbookSms")
    private Integer guestbookSms;
    @JsonIgnore
    private Long balance;
    /**
     * 二级域名
     */
    @JsonIgnore
    @JSONField(name="secondLevelDomain")
    private String sld;
    /**
     * 传真
     */
    private String fax;
    /**
     * 联系人
     */
    @JsonProperty("contacts")
    private String linkMan;
    /**
     * 手机号
     */
    private String mobilePhone;
    /**
     * 
     */
    @JsonIgnore
    @JSONField(name="isSmsFree")
    private Integer smsFree;
    
    //---------------------------台账属性上提 by zhangkc 20150505--------------------------------------------
	/**
	 * 单位的教育类型
	 */
    @JsonIgnore
	private String unitEducationType;
	/**
	 * 经度(弧度值，十进制)
	 */
    @JsonIgnore
	private double longitude;
	/**
	 * 纬度(弧度值，十进制)
	 */
    @JsonIgnore
	private double latitude;
	/**
	 * 单位负责人
	 */
    @JsonIgnore
	private String unitHeader;

	//======================DTO辅助字段====================
    //add by zhaojt 2014-10-8 青岛市北需求
    @JsonIgnore
    private String organizationCode; //组织机构代码
    @JsonIgnore
    private String unitProperty; //单位性质
    
    /**
     * 短信是否运行发送给教师 对应teacherEnableSms
     */
    public static final int TEACHER_CANNOT_SEND = 0; // 不能够发送给教师
    public static final int TEACHER_CAN_SEND = 1; // 能够发送给教师

    /**
     * 全国顶级教育局（教育部）的regionCode固定长度为六个0
     */
    public static final String TOP_UNIT_REGION_CODE = "000000";

    /**
     * 平台顶级单位guid
     */
    public static final String TOP_UNIT_GUID = BaseConstant.ZERO_GUID;

    /**
     * 本地单位，非报送
     */
    public static final int UNIT_USETYPE_LOCAL = 1;

    /**
     * 报送单位
     */
    public static final int UNIT_USETYPE_REPORT = 2;

    /**
     * 单位状态-未审核
     */
    public static final int UNIT_MARK_NOTAUDIT = 0;

    /**
     * 单位状态-正常
     */
    public static final int UNIT_MARK_NORAML = 1;

    /**
     * 单位状态-锁定
     */
    public static final int UNIT_MARK_LOCK = 2;

    // 单位行政等级
    /**
     * 国家教育局
     */
    public static final int UNIT_REGION_COUNTRY = 1;

    /**
     * 省教育局
     */
    public static final int UNIT_REGION_PROVINCE = 2;

    /**
     * 市教育局
     */
    public static final int UNIT_REGION_CITY = 3;

    /**
     * 区教育局
     */
    public static final int UNIT_REGION_COUNTY = 4;

    /**
     * 乡镇教育局
     */
    public static final int UNIT_REGION_LEVEL = 5;
    
    /**
     * 学校
     */
    public static final int UNIT_REGION_SCHOOL = 6;

    // ----------------------------
    // 单位类型定义
    // ----------------------------
    /**
     * 单位类型－顶级教育局
     */
    public static final int UNIT_EDU_TOP = 1; // 顶级教育局

    /**
     * 单位类型－下属教育局
     */
    public static final int UNIT_EDU_SUB = 2; // 下属教育局

    /**
     * 单位类型－托管中小学
     */
    public static final int UNIT_SCHOOL_ASP = 3; // 托管中小学

    /**
     * 单位类型－托管大中专学校
     */
    public static final int UNIT_SCHOOL_COLLEGE = 4; // 托管大中专学校

    /**
     * 单位类型－托管幼儿园
     */
    public static final int UNIT_SCHOOL_KINDERGARTEN = 5; // 托管幼儿园

    /**
     * 单位类型－EISS中小学
     */
    public static final int UNIT_SCHOOL_EISS = 6; // EISS中小学

    /**
     * 单位类型－EISV大中专学校
     */
    public static final int UNIT_SCHOOL_EISV = 7; // EISV大中专学校

    /**
     * 单位类型-非教育局单位
     */
    public static final int UNIT_NOTEDU_NOTSCH = 8;// 非教育局单位

    /**
     * 单位类型-高职校
     */
    public static final int UNIT_HIGH_SCHOOL = 9;// 高职校

    /**
     * 单位分类－教育局
     */
    public static final int UNIT_CLASS_EDU = 1; // 教育局

    /**
     * 单位分类－学校
     */
    public static final int UNIT_CLASS_SCHOOL = 2; // 学校

    /**
     * 单位分类－学区
     */
    public static final int UNIT_CLASS_SCHDISTRICT = 3; // 学区
    
    /**
	 * 根据行政区划级别获取对应的unionid的长度
	 * @param regionlevel
	 * @return
	 */
    @JsonIgnore
	public static final int getUnionIdLength(int regionlevel) {
		int unionIdLength = 0;
		switch (regionlevel) {
		case 1:
			unionIdLength = 1;
			break;
		case 2:
			unionIdLength = 2;
			break;
		case 3:
			unionIdLength = 4;
			break;
		case 4:
			unionIdLength = 6;
			break;
		case 5:
			unionIdLength = 9;
			break;
		default:
			unionIdLength = 12;
			break;
		}
		return unionIdLength;
	}

    public Integer getMark() {
        return mark;
    }

    public void setMark(Integer mark) {
        this.mark = mark;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getOrderid() {
        return orderid;
    }

    public void setOrderid(Long orderid) {
        this.orderid = orderid;
    }

    public String getParentid() {
        return parentid;
    }

    public void setParentid(String parentid) {
        this.parentid = parentid;
    }

    public String getUnionid() {
        return unionid;
    }

    public void setUnionid(String unionid) {
        this.unionid = unionid;
    }

    public Integer getUnitclass() {
        return unitclass;
    }

    public void setUnitclass(Integer unitclass) {
        this.unitclass = unitclass;
    }

    public Integer getUnittype() {
        return unittype;
    }

    public void setUnittype(Integer unittype) {
        this.unittype = unittype;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public Integer getRegionlevel() {
        return regionlevel;
    }

    public Integer getUsetype() {
        return usetype;
    }

    public void setRegionlevel(Integer regionlevel) {
        this.regionlevel = regionlevel;
    }

    public void setUsetype(Integer usetype) {
        this.usetype = usetype;
    }

    public String getEtohSchoolId() {
        return etohSchoolId;
    }

    public void setEtohSchoolId(String toHSchoolId) {
        etohSchoolId = toHSchoolId;
    }

    public Integer getTeacherEnableSms() {
        return teacherEnableSms;
    }

    public void setTeacherEnableSms(Integer teacherEnableSms) {
        this.teacherEnableSms = teacherEnableSms;
    }

    public Integer getFeeType() {
        return feeType;
    }

    public void setFeeType(Integer feeType) {
        this.feeType = feeType;
    }

    public Long getUnitPartitionNum() {
        return unitPartitionNum;
    }

    public void setUnitPartitionNum(Long unitPartitionNum) {
        this.unitPartitionNum = unitPartitionNum;
    }

    public String getSchtype() {
        return schtype;
    }

    public void setSchtype(String schtype) {
        this.schtype = schtype;
    }

	public String getLinkPhone() {
		return linkPhone;
	}

	public void setLinkPhone(String linkPhone) {
		this.linkPhone = linkPhone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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

	public String getHomepage() {
		return homepage;
	}

	public void setHomepage(String homepage) {
		this.homepage = homepage;
	}

	public Integer getLimitTeacher() {
		return limitTeacher;
	}

	public void setLimitTeacher(Integer limitTeacher) {
		this.limitTeacher = limitTeacher;
	}

	public String getRunschtype() {
		return runschtype;
	}

	public void setRunschtype(String runschtype) {
		this.runschtype = runschtype;
	}

	public String getUnitusetype() {
		return unitusetype;
	}

	public void setUnitusetype(String unitusetype) {
		this.unitusetype = unitusetype;
	}

	public String getOrganizationCode() {
		return organizationCode;
	}

	public void setOrganizationCode(String organizationCode) {
		this.organizationCode = organizationCode;
	}

	public String getUnitProperty() {
		return unitProperty;
	}

	public void setUnitProperty(String unitProperty) {
		this.unitProperty = unitProperty;
	}

	public String getRegcode() {
		return regcode;
	}

	public void setRegcode(String regcode) {
		this.regcode = regcode;
	}

	public Integer getAuthorized() {
		return authorized;
	}

	public void setAuthorized(Integer authorized) {
		this.authorized = authorized;
	}

	public Integer getGuestbookSms() {
		return guestbookSms;
	}

	public void setGuestbookSms(Integer guestbookSms) {
		this.guestbookSms = guestbookSms;
	}

	public Long getBalance() {
		return balance;
	}

	public void setBalance(Long balance) {
		this.balance = balance;
	}

	public String getSld() {
		return sld;
	}

	public void setSld(String sld) {
		this.sld = sld;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getLinkMan() {
		return linkMan;
	}

	public void setLinkMan(String linkMan) {
		this.linkMan = linkMan;
	}

	public String getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	public Integer getSmsFree() {
		return smsFree;
	}

	public void setSmsFree(Integer smsFree) {
		this.smsFree = smsFree;
	}

	public String getUnitEducationType() {
		return unitEducationType;
	}

	public void setUnitEducationType(String unitEducationType) {
		this.unitEducationType = unitEducationType;
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

	public String getUnitHeader() {
		return unitHeader;
	}

	public void setUnitHeader(String unitHeader) {
		this.unitHeader = unitHeader;
	}
}
