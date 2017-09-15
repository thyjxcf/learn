/**
 * 
 */
package net.zdsoft.eis.base.util;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import net.zdsoft.eis.base.constant.enumerable.CodeEnumerable;

/**
 * 枚举对象工具
 * @author zhangkc
 * @date 2015年4月23日 上午10:48:08
 */
public class CodeEnumUtils {
	
	/**
	 * 将枚举类的所有对象，按照Code值缓存到Map中去。会去检测枚举类中的关键属性值是否有重复
	 * @param enumClass
	 * @see #getCacheMap(Class, boolean)
	 * @return
	 * @author zhangkc
	 * @date 2015年4月23日 上午11:03:19
	 */
	public static <K,E extends Enum<E> & CodeEnumerable<K, E>> Map<K,E> getCacheMap(Class<E> enumClass){
		return getCacheMap(enumClass, true);
	}
	/**
	 * 将枚举类的所有对象，按照Code值缓存到Map中去，根据需要检测枚举类中的关键属性值是否有重复 
	 * @param enumClass 枚举类
	 * @param checkCode 是否检测重复
	 * @return
	 * @author zhangkc
	 * @throws IllegalArgumentException 枚举类中关键属性值重复时
	 * @date 2015年4月23日 上午11:14:28
	 */
	public static <K,E extends Enum<E> & CodeEnumerable<K, E>> Map<K,E> getCacheMap(Class<E> enumClass, boolean checkCode) throws IllegalArgumentException{
		Map<K, E> map = new HashMap<K, E>();
		E[] eArray = enumClass.getEnumConstants();
		if(eArray == null){
			return map;
		}
		for(E e: eArray){
			map.put(e.getCode(), e);
		}
		if(checkCode && eArray.length != map.size()){
			throw new IllegalArgumentException("枚举类["+enumClass.getCanonicalName()+"]中，关键属性值有重复code=[" + findRepeatedCode(enumClass) + "]！");
		}
		return map;
	}
	
	/**
	 * 查找枚举类中重复的Code
	 * @param enumClass
	 * @return 如果没有重复则返回null；否则返回重复的
	 * @author zhangkc
	 * @date 2015年4月23日 上午11:24:56
	 */
	public static <K,E extends Enum<E> & CodeEnumerable<K, E>> K findRepeatedCode(Class<E> enumClass){
		E[] EArray = enumClass.getEnumConstants();
		if(EArray == null){
			return null;
		}
		Set<E> eSet = new HashSet<E>();
		for(E e : EArray){
			if(!eSet.add(e)){
				return e.getCode();
			}
		}
		return null;
	}
	
	/**
	 * 格式化枚举对象的toString方法
	 * @param e 枚举对象
	 * @return
	 * @author zhangkc
	 * @date 2015年4月23日 上午11:49:54
	 */
	public static <K,E extends Enum<E> & CodeEnumerable<K, E>> String formattedToString(E e){
		return e.getDesc() + "(code=" + e.getCode().toString() + ")";
	}
	
}
