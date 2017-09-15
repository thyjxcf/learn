/**
 * 
 */
package net.zdsoft.eis.base.constant.enumerable;

/**
 * 拥有关键属性(类主键)的可枚举的接口
 * @param <K> 枚举类的关键属性类型，关键属性不可重复
 * @param <E> 枚举类型
 * @author zhangkc
 * @date 2015年4月23日 上午11:07:51
 */
public interface CodeEnumerable<K, E extends Enum<E>> {
	
	/**
	 * 获取关键属性值
	 * @return
	 * @author zhangkc
	 * @date 2015年4月23日 上午11:10:20
	 */
	K getCode();
	
	/**
	 * 获取枚举对象的描述性文字
	 * @return
	 * @author zhangkc
	 * @date 2015年4月23日 上午11:10:29
	 */
	String getDesc();
	/**
	 * Returns the name of this enum constant, exactly as declared in its
     * enum declaration.
	 * @return
	 * @author zhangkc
	 * @date 2015年5月14日 下午6:31:49
	 */
	String name();
}
