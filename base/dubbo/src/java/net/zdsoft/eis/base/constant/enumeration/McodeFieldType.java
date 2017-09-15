/**
 * 
 */
package net.zdsoft.eis.base.constant.enumeration;

import java.util.Map;

import net.zdsoft.eis.base.constant.enumerable.CodeEnumerable;
import net.zdsoft.eis.base.util.CodeEnumUtils;

/**
 * 微代码配置区域类型
 * @author zhangkc
 * @date 2015年3月30日 下午5:10:14
 */
public enum McodeFieldType implements CodeEnumerable<String, McodeFieldType>{
	SECTION("1", "学段"), 
	SCHOOL_TYPE("2", "学校类型"),
	UNIT_ID("3", "单位Id"),
	SCHOOL_TYPE_GROUP("4", "学校类型分组"),
	UNIT_TYPE("5", "单位类型"),
	STAT_LEVEL("6", "统计等级"),
	STUDENT_CHANGE_TYPE_GROUP("7", "学生异动类型分组"),//0减少，1增加
	;
	
	private String code;
	private String desc;
	
	//缓存数组
	private static Map<String, McodeFieldType> typeMap = CodeEnumUtils.getCacheMap(McodeFieldType.class);
	/**
	 * @param code
	 * @param desc
	 */
	private McodeFieldType(String code, String desc) {
		this.code = code;
		this.desc = desc;
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
	/**
	 * 由code转化为枚举对象
	 * @param code
	 * @return
	 * @author zhangkc
	 * @date 2015年3月30日 下午6:09:02
	 */
	public static McodeFieldType fromCode(String code){
		return typeMap.get(code);
	}
}
