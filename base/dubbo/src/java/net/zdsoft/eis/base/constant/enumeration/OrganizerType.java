/**
 * 
 */
package net.zdsoft.eis.base.constant.enumeration;

import java.util.Map;

import net.zdsoft.eis.base.constant.enumerable.CodeEnumerable;
import net.zdsoft.eis.base.util.CodeEnumUtils;

/**
 * 学校举办者类型/学校办别
 * @author zhangkc
 * @date 2015年4月24日 上午11:46:12
 */
public enum OrganizerType implements CodeEnumerable<String, OrganizerType> {
	/** 省级教育部门 */
	PROVINCIAL_EDUCATION_SECTOR("811", "省级教育部门"),
	/** 省级其他部门 */
	PROVINCIAL_OTHER_SECTOR("812", "省级其他部门"),
	/** 地级教育部门 */
	MUNICIPAL_EDUCATION_SECTOR("821", "地级教育部门"),
	/** 地级其他部门 */
	MUNICIPAL_OTHER_SECTOR("822", "地级其他部门"),
	/** 县级教育部门 */
	COUNTY_EDUCATION_SECTOR("831", "县级教育部门"),
	/** 县级其他部门 */
	COUNTY_OTHER_SECTOR("832", "县级其他部门"),
	/** 地方企业 */
	LOCAL_COMPANY("891", "地方企业"),
	/** 事业单位 */
	INSTITUTION("892", "事业单位"),
	/** 部队 */
	TROOP("893", "部队"),
	/** 集体 */
	GROUP("894", "集体"),
	/** 民办 */
	NONGOVERNMENTAL("999", "民办")
	;

	private String code;
	private String desc;
	
	public static Map<String, OrganizerType> typeMap = CodeEnumUtils.getCacheMap(OrganizerType.class);
	
	private OrganizerType(String code, String desc) {
		this.code = code;
		this.desc = desc;
	}

	@Override
	public String getCode() {
		return code;
	}

	@Override
	public String getDesc() {
		return desc;
	}
	
	@Override
	public String toString() {
		return CodeEnumUtils.formattedToString(this);
	}
	
	public static OrganizerType fromCode(String code){
		return typeMap.get(code);
	}
}
