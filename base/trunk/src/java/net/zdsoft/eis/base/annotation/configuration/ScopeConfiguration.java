/**
 * 
 */
package net.zdsoft.eis.base.annotation.configuration;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import net.zdsoft.eis.base.annotation.annotationtype.Scope;
import net.zdsoft.eis.base.annotation.type.ScopeConfigured;
import net.zdsoft.eis.base.common.entity.SchoolBuildingArea;
import net.zdsoft.eis.base.constant.enumerable.SchoolTypeContainable;
import net.zdsoft.eis.base.constant.enumeration.SchoolType;
import net.zdsoft.eis.base.constant.enumeration.SchoolTypeGroup;

import org.apache.commons.lang.ArrayUtils;
import org.springframework.web.context.ContextLoader;

/**
 * 范围控制配置
 * @author zhangkc
 * @date 2015年4月24日 下午3:16:43
 */
public class ScopeConfiguration{
	
	private static final ScopeConfiguration instance = new ScopeConfiguration();
	private ScopeConfiguration(){
		autoWire();
	}
	public static ScopeConfiguration getInstance(){
		return instance;
	}
	
	/** 范围控制注解及其对应的范围控制方法Map */
	private Map<Class<? extends Annotation>, Method> annotationMethods = new HashMap<Class<? extends Annotation>, Method>();
	
	private Map<Field, Map<Class<?>, Object>> fieldConfiguredInfo = new HashMap<Field, Map<Class<?>,Object>>();
	
	/**
	 * 终极方法：获取配置字段
	 * @param entityClass 实体类型
	 * @param scopeClass 配置类型
	 * @param param 配置值
	 * @return
	 * @author zhangkc
	 * @date 2015年4月25日 下午5:30:17
	 */
	public Set<Field> findScopeFields(Class<?> entityClass, Class<?> scopeClass, Object param){
		
		Set<Field> fields = new HashSet<Field>();
		
		for(Entry<Field, Map<Class<?>, Object>> entry : fieldConfiguredInfo.entrySet()){
			Map<Class<?>, Object> scope = entry.getValue();
			if(scope.containsKey(scopeClass) && 
					entry.getKey().getDeclaringClass().equals(entityClass) && 
					isInScope(scope.get(scopeClass), param)){
				
					fields.add(entry.getKey());
			}
		}
		return fields;
	}
	/**
	 * 终极方法：获取配置字段名称
	 * @param entityClass 实体类型
	 * @param scopeClass 配置类型
	 * @param param 配置值
	 * @return
	 * @author zhangkc
	 * @date 2015年4月25日 下午5:30:17
	 */
	public Set<String> findScopeFieldNames(Class<?> entityClass, Class<?> scopeClass, Object param){
		Set<String> fields = new HashSet<String>();
		
		for(Entry<Field, Map<Class<?>, Object>> entry : fieldConfiguredInfo.entrySet()){
			Map<Class<?>, Object> scope = entry.getValue();
			if(scope.containsKey(scopeClass) &&
					entry.getKey().getDeclaringClass().isAssignableFrom(entityClass)&& 
					isInScope(scope.get(scopeClass), param)){
				
					fields.add(entry.getKey().getName());
			}
		}
		return fields;
	}
	
	/**
	 * 自动装配
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @author zhangkc
	 * @date 2015年4月25日 下午4:32:41
	 */
	private void autoWire(){
		//全局查找被注解为ScopeConfigured的类
		Map<String, Object> scopeConfiguredMap = ContextLoader.getCurrentWebApplicationContext().getBeansWithAnnotation(ScopeConfigured.class);
		for(Entry<String, Object> entry : scopeConfiguredMap.entrySet()){
			Object entity = entry.getValue();
			ScopeConfigured scopeConfigured = ContextLoader.getCurrentWebApplicationContext().findAnnotationOnBean(entry.getKey(), ScopeConfigured.class);
			//类被标注了的所有范围控制标注类型
			Class<? extends Annotation>[] annotationClasses = scopeConfigured.scopeAnnotation();
			initScopeAnnotations(annotationClasses);
			
			Field[] fields = entity.getClass().getDeclaredFields();
			//初始化实体类中出现的对字段的范围控制注解
			for(Field field : fields){
				
				Map<Class<?>, Object> fieldConfiguration = new HashMap<Class<?>, Object>();
				
				for(Class<? extends Annotation> annotationClass : annotationClasses){
					Annotation annotation = field.getAnnotation(annotationClass);
					Method method = annotationMethods.get(annotationClass);
					Class<?> returnType = getRealReturnType(method);
					
					//字段被标注，取标注的设置值
					if(annotation != null){
						try {
							fieldConfiguration.put(returnType, method.invoke(annotation));
						} catch (Exception e) {
							throw new RuntimeException("获取注解方法值错误：" + annotation.annotationType().getCanonicalName() + "." + method.getName() + "()", e);
						}
					}else if(!scopeConfigured.verbose()){
						//非详细模式，需要补充默认值范围
						fieldConfiguration.put(returnType, method.getDefaultValue());
					}
				}
				
				fieldConfiguredInfo.put(field, fieldConfiguration);
			}
		}
		
	}
	
	/**
	 * 获取方法的真实返回值：如果是数组形式的，则返回数组值的类型；其他返回方法实际返回值类型
	 * @param returnType
	 * @return
	 * @author zhangkc
	 * @date 2015年4月25日 下午5:06:08
	 */
	private Class<?> getRealReturnType(Method method) {
		Class<?> returnType = method.getReturnType();
		//数组类型返回值，需要得到数组内值的类型
		if(returnType.isArray()){
			returnType = returnType.getComponentType();
		}
		return returnType;
	}
	
	/**
	 * 使用Spring上下文获取被Scope标记的控制范围注解
	 * @return
	 * @author zhangkc
	 * @date 2015年4月25日 上午10:54:34
	 */
	@SuppressWarnings("unused")
	private Collection<Annotation> getScopeAnnotationFromContext(){
		Map<String, Object> scopeAnnotationMap = ContextLoader.getCurrentWebApplicationContext().getBeansWithAnnotation(Scope.class);
		Collection<Annotation> scopeAnotations = new HashSet<Annotation>();
		for(Object scopeAnnotation : scopeAnnotationMap.values()){
			if(scopeAnnotation instanceof Annotation){
				scopeAnotations.add((Annotation) scopeAnnotation);
			}else{
				throw new RuntimeException("错误的范围控制注解["+scopeAnotations.getClass().getCanonicalName()+"]，不是一个注解类型！");
			}
		}
		return scopeAnotations;
	}
	
	/**
	 * 初始化一个注解类，获取其表示范围的方法集合
	 * @param annotationClass
	 * @return
	 * @author zhangkc
	 * @date 2015年4月25日 上午11:13:13
	 */
	private void initScopeAnnotation(Class<? extends Annotation> annotationClass){
		if(!annotationClass.isAnnotationPresent(Scope.class)){
			throw new RuntimeException("错误的范围控制注解["+annotationClass.getCanonicalName()+"]，没有标注@Scope！");
		}
		
		Scope scope = annotationClass.getAnnotation(Scope.class);
		//控制范围注解的控制范围方法
		String scopeMethodName = scope.scopeMethod();
		Method scopeMethod = null;
		try {
			scopeMethod = annotationClass.getMethod(scopeMethodName);
		} catch (NoSuchMethodException e) {
			throw new RuntimeException("错误的范围控制注解["+annotationClass.getCanonicalName()+"]，标注的scopeMethods=["+scopeMethodName+"]不存在！", e);
		}
		annotationMethods.put(annotationClass, scopeMethod);
	}
	
	/**
	 * 初始化范围控制注解，获取注解的控制方法
	 * @param annotations
	 * @author zhangkc
	 * @date 2015年4月25日 上午10:44:44
	 */
	@SuppressWarnings("unused")
	private void initScopeAnnotations(Collection<Annotation> annotations){
		for(Annotation annotation : annotations){
			Class<? extends Annotation> annotationClass = annotation.getClass();  
			if(!annotationMethods.containsKey(annotationClass)){
				initScopeAnnotation(annotationClass);
			}
		}
	}
	/**
	 * 初始化范围控制注解，获取注解的控制方法
	 * @param annotations
	 * @author zhangkc
	 * @date 2015年4月25日 上午10:44:44
	 */
	private void initScopeAnnotations(Class<? extends Annotation>[] annotations){
		for(Class<? extends Annotation> annotationClass : annotations){
			if(!annotationMethods.containsKey(annotationClass)){
				initScopeAnnotation(annotationClass);
			}
		}
	}
	
	/**
	 * 是否在范围内
	 * @param scope
	 * @param argument
	 * @return
	 * @author zhangkc
	 * @date 2015年4月24日 下午5:32:16
	 */
	private boolean isInScope(Object scope, Object argument){
		if(scope == null || argument == null){
			return false;
		}
		if(scope.getClass().isArray() && ArrayUtils.isNotEmpty((Object[])scope)){
			//学校类型可多级判断
			if(((Object[])scope)[0] instanceof SchoolTypeContainable &&
					argument instanceof SchoolType){
				for(SchoolTypeContainable container : (SchoolTypeContainable[])scope){
					if(container.contains((SchoolType)argument)){
						return true;
					}
				}
			}
			return ArrayUtils.contains(((Object[])scope), argument);
		}
		return scope.equals(argument);
	}
	
	public static void main(String[] args) throws ClassNotFoundException, IOException, IllegalArgumentException, IllegalAccessException, InvocationTargetException {
		ScopeConfiguration conf = new ScopeConfiguration();
		Set<Field> fields = conf.findScopeFields(SchoolBuildingArea.class, SchoolTypeGroup.class, SchoolTypeGroup.SECONDARY_VOCATIONAL);
		for(Field field : fields){
			System.out.println(field.getDeclaringClass().getSimpleName() + "." + field.getName());
		}
	}
}
