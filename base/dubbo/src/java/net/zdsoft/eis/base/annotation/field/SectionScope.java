/**
 * 
 */
package net.zdsoft.eis.base.annotation.field;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import net.zdsoft.eis.base.annotation.annotationtype.Scope;
import net.zdsoft.eis.base.constant.enumeration.Section;
/**
 * 适用范围
 * @author zhangkc
 * @date 2015年4月23日 下午5:16:20
 */
@Target(java.lang.annotation.ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Scope
public @interface SectionScope {
	/**
	 * 适用的学段值数组，默认幼儿园、小学、初中、高中、中职、特教
	 * @return
	 * @author zhangkc
	 * @date 2015年4月23日 下午5:18:25
	 */
	@SuppressWarnings("deprecation")
	Section[] scope() default {Section.INFANT, Section.PRIMARY, Section.JUNIOR, Section.SENIOR, 
		Section.SECONDARY_VOCATIONAL, Section.SPECIAL_EDUCATION};
}
