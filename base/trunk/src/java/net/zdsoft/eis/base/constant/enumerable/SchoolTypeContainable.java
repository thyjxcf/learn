/**
 * 
 */
package net.zdsoft.eis.base.constant.enumerable;

import net.zdsoft.eis.base.constant.enumeration.SchoolType;


/**
 * 能容纳学校类型的。作为学校类型或之上的接口存在
 * @author zhangkc
 * @date 2015年4月24日 上午10:57:53
 */
public interface SchoolTypeContainable {
	
	/**
	 * 是否包含指定的学校类型
	 * @param schoolType 学校类型枚举类 
	 * @return
	 * @author zhangkc
	 * @date 2015年4月24日 上午11:09:50
	 */
	boolean contains(SchoolType schoolType);
}
