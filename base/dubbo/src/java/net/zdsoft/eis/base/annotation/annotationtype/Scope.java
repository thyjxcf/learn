/**
 * 
 */
package net.zdsoft.eis.base.annotation.annotationtype;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 对注解类的注解，表明该注解为一个范围控制的注解
 * @author zhangkc
 * @date 2015年4月24日 下午2:36:41
 */
@Target(java.lang.annotation.ElementType.ANNOTATION_TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Scope {
	
	/**
	 * 范围控制的注解方法名称。默认为一个名为<code>scope</code>的方法<br/>
	 * @return
	 * @author zhangkc
	 * @date 2015年4月24日 下午2:42:40
	 */
	String scopeMethod() default "scope";
}
