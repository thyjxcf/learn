/**
 * 
 */
package net.zdsoft.eis.base.constant.enumeration;

import java.util.Map;

import net.zdsoft.eis.base.constant.enumerable.CodeEnumerable;
import net.zdsoft.eis.base.util.CodeEnumUtils;


/**
 * 身份证件类型
 * @author zhangkc
 * @date 2015年4月23日 上午10:05:20
 */
public enum IdentityCardType implements CodeEnumerable<String, IdentityCardType>{
	/** 居民身份证 */
	RESIDENT_IDENTITY_CARD("1", "居民身份证"),
	/** 军官证 */
	MILITARY_IDENTITY_CARD("2", "军官证"),
	/** 士兵证 */
	SOLDIERIDENTITY_CARD("3", "士兵证"),
	/** 文职干部证 */
	CIVILIAN_CADRES_CARD("4", "文职干部证"),
	/** 部队离退休证 */
	RETIRED_ARMY_CERTIFICATE("5", "部队离退休证"),
	/** 香港特区护照/身份证明 */
	HK_SAR_PASSPORT("6", "香港特区护照/身份证明"),
	/** 澳门特区护照/身份证明 */
	MACAO_SAR_PASSPORT("7", "澳门特区护照/身份证明"),
	/** 台湾居民来往大陆通行证 */
	MAINLAND_TRAVEL_PERMIT_FOR_TAIWAN_RESIDENTS("8", "台湾居民来往大陆通行证"),
	/** 境外永久居住证 */
	PERMANENT_RESIDENCE_ABROAD("9", "境外永久居住证"),
	/** 护照 */
	PASSPORT("A", "护照"),
	/** 户口薄 */
	ACCOUNT_THIN("B", "户口薄"),
	/** 其他 */
	OTHER("Z", "其他");
	
	private String code;
	private String desc;
	
	//缓存数组
	private static Map<String, IdentityCardType> typeMap = CodeEnumUtils.getCacheMap(IdentityCardType.class);
	/**
	 * @param code
	 * @param desc
	 */
	private IdentityCardType(String code, String desc) {
		this.code = code;
		this.desc = desc;
	}

	@Override
	public String toString() {
		return CodeEnumUtils.formattedToString(this);
	}

	public String getCode() {
		return this.code;
	}

	public String getDesc() {
		return this.desc;
	}

	/**
	 * 由code转化为枚举对象
	 * 
	 * @param code
	 * @return
	 * @author zhangkc
	 * @date 2015年3月30日 下午6:09:02
	 */
	public static IdentityCardType fromCode(String code) {
		return typeMap.get(code);
	}
}
