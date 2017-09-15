/**
 * 
 */
package net.zdsoft.eis.base.annotation.field;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import net.zdsoft.eis.base.annotation.annotationtype.Scope;
import net.zdsoft.eis.base.constant.enumeration.UnitEducationType;

/**
 * 单位类型范围
 * @author zhangkc
 * @date 2015年4月25日 下午4:25:12
 */
@Target(java.lang.annotation.ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Scope
public @interface UnitTypeScope {
	/**
	 * 单位类型范围，默认所有学校型的单位类型
	 * @return
	 * @author zhangkc
	 * @date 2015年4月24日 上午11:24:02
	 */
	UnitEducationType[] scope() default {
		UnitEducationType.INFANT, UnitEducationType.PRIMARY, 
		UnitEducationType.SECONDARY, UnitEducationType.HIGHER, 
		UnitEducationType.SPECIAL, UnitEducationType.OTHERS
	};
}
