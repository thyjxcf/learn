/**
 * 
 */
package net.zdsoft.eis.base.annotation.type;

import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 实体类被范围控制的注解类。支持继承
 * @author zhangkc
 * @date 2015年4月24日 下午2:03:51
 */
@Target(java.lang.annotation.ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ScopeConfigured {
	
	/**
	 * 含有哪些控制范围的注解
	 * @return
	 * @author zhangkc
	 * @date 2015年4月24日 下午2:06:16
	 */
	Class<? extends Annotation>[] scopeAnnotation() default {};
	/**
	 * 是否详细模式（每个字段都必须配置，不管是否默认scope），默认非详细模式
	 * @return
	 * @author zhangkc
	 * @date 2015年4月24日 下午2:09:28
	 */
	boolean verbose() default false;
}
