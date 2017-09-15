/**
 * 
 */
package net.zdsoft.eis.base.annotation.field;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import net.zdsoft.eis.base.annotation.annotationtype.Scope;
import net.zdsoft.eis.base.constant.enumeration.OrganizerType;

/**
 * 学校办别适应范围
 * @author zhangkc
 * @date 2015年4月24日 下午1:43:12
 */
@Target(java.lang.annotation.ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Scope
public @interface OrganizerScope{

	/**
	 * 学校办别适用范围，默认所有办别
	 * @return
	 * @author zhangkc
	 * @date 2015年4月24日 下午1:44:04
	 */
	OrganizerType[] scope() default {
		OrganizerType.PROVINCIAL_EDUCATION_SECTOR, OrganizerType.PROVINCIAL_OTHER_SECTOR, 
		OrganizerType.MUNICIPAL_EDUCATION_SECTOR, OrganizerType.MUNICIPAL_OTHER_SECTOR, 
		OrganizerType.COUNTY_EDUCATION_SECTOR, OrganizerType.COUNTY_OTHER_SECTOR, 
		OrganizerType.LOCAL_COMPANY, OrganizerType.INSTITUTION, OrganizerType.TROOP, 
		OrganizerType.GROUP, OrganizerType.NONGOVERNMENTAL};
}
