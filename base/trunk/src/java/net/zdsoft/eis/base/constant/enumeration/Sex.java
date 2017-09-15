/**
 * 
 */
package net.zdsoft.eis.base.constant.enumeration;

import java.util.Map;

import net.zdsoft.eis.base.constant.enumerable.CodeEnumerable;
import net.zdsoft.eis.base.util.CodeEnumUtils;

/**
 * 性别
 * @author zhangkc
 * @date 2015年4月23日 上午10:02:42
 */
public enum Sex implements CodeEnumerable<String, Sex>{
	MALE("1", "男"),
	FEMALE("2", "女");
	
	private String code;
	private String desc;
	
	//缓存数组
	private static Map<String, Sex> typeMap = CodeEnumUtils.getCacheMap(Sex.class);
	/**
	 * @param code
	 * @param desc
	 */
	private Sex(String code, String desc) {
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
	public static Sex fromCode(String code){
		return typeMap.get(code);
	}
}
