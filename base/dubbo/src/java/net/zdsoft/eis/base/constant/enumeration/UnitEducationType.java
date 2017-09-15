/**
 * 
 */
package net.zdsoft.eis.base.constant.enumeration;

import java.util.Map;

import net.zdsoft.eis.base.constant.enumerable.CodeEnumerable;
import net.zdsoft.eis.base.constant.enumerable.SchoolTypeContainable;
import net.zdsoft.eis.base.util.CodeEnumUtils;

/**
 * 单位教育类型
 * @author zhangkc
 * @date 2015年4月22日 下午2:03:03
 */
public enum UnitEducationType implements CodeEnumerable<String, UnitEducationType>, SchoolTypeContainable{
	/** 教育主管机构 */
	EDUCATION("10","教育主管机构"),
	/** 教育机构独立法人事业单位 */
	INSTITUTIONS("11","教育机构独立法人事业单位"), 
	/** 学前教育 */
	INFANT("1","学前教育"),        
	/** 初等教育 */
	PRIMARY("2","初等教育"),       
	/** 中等教育 */
	SECONDARY("3","中等教育"),     
	/** 高等教育 */
	HIGHER("4","高等教育"),        
	/** 特殊教育 */
	SPECIAL("5","特殊教育"),       
	/** 其他教育 */
	OTHERS("9","其他教育");
	
	private String code;
	private String desc;
	//缓存数组
	private static Map<String, UnitEducationType> typeMap = CodeEnumUtils.getCacheMap(UnitEducationType.class);
	/**
	 * @param code
	 * @param desc
	 */
	private UnitEducationType(String code, String desc) {
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
	public static UnitEducationType fromCode(String code){
		return typeMap.get(code);
	}
	
	@Override
	public boolean contains(SchoolType schoolType) {
		return this.equals(schoolType.getSchoolTypeGroup().getUnitEducationType());
	}
}
