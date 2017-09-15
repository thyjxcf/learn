/**
 * 
 */
package net.zdsoft.eis.base.constant.enumeration;

import java.util.EnumSet;
import java.util.Map;

import net.zdsoft.eis.base.constant.enumerable.CodeEnumerable;
import net.zdsoft.eis.base.constant.enumerable.SchoolTypeContainable;
import net.zdsoft.eis.base.util.CodeEnumUtils;

/**
 * 学校类型分组
 * @author zhangkc
 * @date 2015年4月22日 下午2:15:40
 */
public enum SchoolTypeGroup implements CodeEnumerable<String, SchoolTypeGroup>, SchoolTypeContainable{
	/** 幼儿园 */
	PREEDU("11", "幼儿园"),
	/** 小学 */
	PRIMARY("21", "小学"),
	/** 成人小学 */
	PRIMARY_ADULT("22", "成人小学"),
	/** 普通初中 */
	JUNIOR("31", "普通初中"),
	/** 职业初中 */
	JUNIOR_CAREER("32", "职业初中"),
	/** 成人初中 */
	JUNIOR_ADULT("33", "成人初中"),
	/** 普通高中 */
	SENIOR("34", "普通高中"),
	/** 成人高中 */
	SENIOR_ADULT("35", "成人高中"),
	/** 中等职业学校 */
	SECONDARY_VOCATIONAL("36", "中等职业学校"),
	/** 工读学校 */
	REFORM_SCHOOL("37", "工读学校"),
	/** 普通高等学校 */
	HIGHER_EDUCATION("41", "普通高等学校"),
	/** 成人高等学校 */
	ADULT_HIGHER_EDUCATION("42", "成人高等学校"),
	/** 特殊教育学校 */
	SPECIAL_EDUCATION("51", "特殊教育学校"),
	/** 培养研究生的科研机构 */
	POSTGRADUATE("91", "培养研究生的科研机构"),
	/** 民办的其他高等教育机构 */
	OTHER_HIGHER_EDUCATION("92", "民办的其他高等教育机构"),
	/** 中等职业培训机构 */
	MEDIUM_CAREER_EDUCATION("93", "中等职业培训机构");
	
	
	private String code;
	private String desc;
	/** 所属单位类型 */
	private UnitEducationType unitEducationType;
	//缓存数组
	private static Map<String, SchoolTypeGroup> typeMap = CodeEnumUtils.getCacheMap(SchoolTypeGroup.class);
	
	private SchoolTypeGroup(String code, String desc) {
		this.code = code;
		this.desc = desc;
		this.unitEducationType = UnitEducationType.fromCode(code.substring(0, 1));//单位类型即代码第一位
	}
	
	@Override
	public String toString() {
		return CodeEnumUtils.formattedToString(this);
	}
	
	public String getCode(){
		return this.code;
	}
	
	public String getDesc(){
		return this.desc;
	}
	public UnitEducationType getUnitEducationType() {
		return unitEducationType;
	}
	/**
	 * 由code转化为枚举对象
	 * @param code
	 * @return
	 * @author zhangkc
	 * @date 2015年3月30日 下午6:09:02
	 */
	public static SchoolTypeGroup fromCode(String code){
		return typeMap.get(code);
	}
	/**
	 * 获取学校类型组下的所有学校类型
	 * @return
	 * @author zhangkc
	 * @date 2015年4月23日 下午3:03:25
	 */
	public EnumSet<SchoolType> getSchoolTypes(){
		EnumSet<SchoolType> set = EnumSet.noneOf(SchoolType.class);
		for(SchoolType schoolType : SchoolType.typeMap.values()){
			if(this.equals(schoolType.getSchoolTypeGroup())){
				set.add(schoolType);
			}
		}
		return set;
	}
	
	@Override
	public boolean contains(SchoolType schoolType) {
		return this.equals(schoolType.getSchoolTypeGroup()); 
	}
}
