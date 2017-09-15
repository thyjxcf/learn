package net.zdsoft.eis.base.common.entity;

import net.zdsoft.eis.frame.client.BaseEntity;


/**
 * 《行政区域表》
 */
public class Region extends BaseEntity{
    private static final long serialVersionUID = -1596438571866202758L;

    private String regionCode;
    private String regionName;
    private String fullName;
    private String fullCode;
    
    private String postalcode;
    private String comName;
    private String romeLetter;    
    private String letterCode;
    
    /** 行政区划码类型 */
    private String type;
    
    /** 行政区划码类型-业务用（默认） */
    public static final String TYPE_BUSINESS = "1";
    /** 行政区划码类型-统计用（即全国标准行政区划码） */
    public static final String TYPE_STAT = "2";
    /** 行政区划码类型-本地特有（全国不存在，属于地方特有的） */
    public static final String TYPE_SPECIAL = "3";
    
    /** 行政区划码长度-省级 */
    public static final int REGION_LENGHT_PROVINCE = 2;
    /** 行政区划码长度-市级 */
    public static final int REGION_LENGHT_CITY = 4;
    /** 行政区划码长度-区县级 */
    public static final int REGION_LENGHT_COUNTY = 6;

    public String getRegionCode() {
        return regionCode;
    }

    public void setRegionCode(String regionCode) {
        this.regionCode = regionCode;
    }

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getFullCode() {
        return fullCode;
    }

    public void setFullCode(String fullCode) {
        this.fullCode = fullCode;
    }

    public String getPostalcode() {
        return postalcode;
    }

    public void setPostalcode(String postalcode) {
        this.postalcode = postalcode;
    }

    public String getComName() {
        return comName;
    }

    public void setComName(String comName) {
        this.comName = comName;
    }

    public String getRomeLetter() {
        return romeLetter;
    }

    public void setRomeLetter(String romeLetter) {
        this.romeLetter = romeLetter;
    }

    public String getLetterCode() {
        return letterCode;
    }

    public void setLetterCode(String letterCode) {
        this.letterCode = letterCode;
    }

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
