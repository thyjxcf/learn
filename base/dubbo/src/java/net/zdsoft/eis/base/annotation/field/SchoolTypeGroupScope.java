/**
 * 
 */
package net.zdsoft.eis.base.annotation.field;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import net.zdsoft.eis.base.annotation.annotationtype.Scope;
import net.zdsoft.eis.base.constant.enumeration.SchoolTypeGroup;

/**
 * 学校类型分组范围
 * @author zhangkc
 * @date 2015年4月25日 下午4:25:00
 */
@Target(java.lang.annotation.ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Scope
public @interface SchoolTypeGroupScope {
	/**
	 * 学校类型分组范围, 默认所有学校类型分组
	 * @return
	 * @author zhangkc
	 * @date 2015年4月24日 上午11:23:48
	 */
	SchoolTypeGroup[] scope() default {
		SchoolTypeGroup.PREEDU, SchoolTypeGroup.PRIMARY, SchoolTypeGroup.PRIMARY_ADULT,
		SchoolTypeGroup.JUNIOR, SchoolTypeGroup.JUNIOR_ADULT, SchoolTypeGroup.JUNIOR_CAREER, 
		SchoolTypeGroup.SENIOR, SchoolTypeGroup.SENIOR_ADULT, SchoolTypeGroup.SECONDARY_VOCATIONAL, 
		SchoolTypeGroup.REFORM_SCHOOL, SchoolTypeGroup.HIGHER_EDUCATION, SchoolTypeGroup.ADULT_HIGHER_EDUCATION, 
		SchoolTypeGroup.SPECIAL_EDUCATION,  SchoolTypeGroup.POSTGRADUATE, SchoolTypeGroup.OTHER_HIGHER_EDUCATION, 
		SchoolTypeGroup.MEDIUM_CAREER_EDUCATION
	};
}
