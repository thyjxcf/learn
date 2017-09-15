package net.zdsoft.eis.base.util;

import java.lang.reflect.Array;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import com.google.common.collect.Lists;

import net.sf.cglib.beans.BeanCopier;
import net.sf.cglib.beans.BeanMap;

public class EntityUtils {

	/**
	 * 重组列表，根据列表中的某些属性，组成新的列表
	 * 
	 * @param os
	 *            原始列表
	 * @param xpath
	 *            列表属性，譬如： student.id 则最终组成的是id值的一个list
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "hiding" })
	public static <T, O> List<T> getList(List<O> os, String xpath) {
		if (CollectionUtils.isEmpty(os)) {
			return new ArrayList<T>();
		}
		ExpressionParser parser = new SpelExpressionParser();
		EvaluationContext context = new StandardEvaluationContext();
		context.setVariable("list", os);
		return parser.parseExpression("#list.![#this." + xpath + "]").getValue(context, List.class);
	}

	/**
	 * 重组列表，将列表中的某些属性，组成新的set
	 * 
	 * @param os
	 *            原始列表
	 * @param xpath
	 *            列表属性，譬如： student.id 则最终组成的是id值的一个set
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T, O> Set<T> getSet(List<O> os, String xpath) {
		if (CollectionUtils.isEmpty(os)) {
			return new HashSet<T>();
		}
		ExpressionParser parser = new SpelExpressionParser();
		EvaluationContext context = new StandardEvaluationContext();
		context.setVariable("list", os);
		return parser.parseExpression("#list.![#this." + xpath + "]").getValue(context, Set.class);
	}

	/**
	 * 取出对象的某个属性值
	 * 
	 * @param o
	 *            对象
	 * @param xpath
	 *            属性路径， student.id
	 * @return
	 */
	@SuppressWarnings({ "hiding", "unchecked" })
	public static <T, O> T getValue(O o, String xpath) {
		if (o == null)
			return null;
		return (T) getValue(o, xpath, null);
	}

	/**
	 * 取出对象的某个属性，并做泛型转换
	 * 
	 * @param o
	 *            对象
	 * @param xpath
	 *            属性路径， student.id
	 * @param clazz
	 *            要转化的对象
	 * @return
	 */
	public static <T, O> T getValue(O o, String xpath, Class<T> clazz) {
		if (o == null)
			return null;
		ExpressionParser parser = new SpelExpressionParser();
		EvaluationContext context = new StandardEvaluationContext();
		context.setVariable("o", o);
		if (clazz == null) {
			return (T) parser.parseExpression("#o." + xpath).getValue(context);
		} else {
			return parser.parseExpression("#o." + xpath).getValue(context, clazz);
		}
	}

	/**
	 * 判断List中是否包含 指定属性是否为指定值的某个元素
	 * 
	 * @param os
	 * @param xpath
	 * @param value
	 * @return
	 */
	public static <T, O> boolean isContainValue(List<O> os, String xpath, String value) {
		if (CollectionUtils.isEmpty(os))
			return false;
		ExpressionParser parser = new SpelExpressionParser();
		EvaluationContext context = new StandardEvaluationContext();
		context.setVariable("list", os);
		return parser.parseExpression("#list.![#this." + xpath + "=='" + value + "']").getValue(context, Boolean.class);
	}

	public static <O, K, V> Map<K, V> getMap(List<O> os, String keyXpath) {
		return getMap(os, keyXpath, null);
	}

	@SuppressWarnings("unchecked")
	public static <O, K, V> Map<K, V> getMap(List<O> os, String keyXpath, String valueXpath) {
		if (CollectionUtils.isEmpty(os)) {
			return new HashMap<K, V>();
		}
		ExpressionParser parser = new SpelExpressionParser();
		EvaluationContext context = new StandardEvaluationContext();
		context.setVariable("list", os);
		List<K> keys = parser
				.parseExpression("#list.![#this" + (StringUtils.isNotBlank(keyXpath) ? ("." + keyXpath) : "") + "]")
				.getValue(context, List.class);
		List<V> values;
		if (StringUtils.isBlank(valueXpath)) {
			values = parser.parseExpression("#list.![#this]").getValue(context, List.class);
		} else {
			values = parser.parseExpression("#list.![#this." + valueXpath + "]").getValue(context, List.class);
		}
		Map<K, V> map = new HashMap<K, V>();
		if (values.size() == keys.size()) {
			for (int i = 0; i < keys.size(); i++) {
				map.put(keys.get(i), values.get(i));
			}
		}
		return map;
	}

	public static <T> T getGeneric(Type type) {
		Type[] generics = ((ParameterizedType) type).getActualTypeArguments();
		@SuppressWarnings("unchecked")
		Class<T> mTClass = (Class<T>) (generics[0]);
		T t2 = (T) BeanUtils.instantiateClass(mTClass);
		return (T) t2;
	}

	public static String[] createArray(String... ss) {
		return ss;
	}

	public static String underline2Camel(String param) {
		param = param.toLowerCase();
		Pattern p = Pattern.compile("_[a-z]");
		if (param == null || param.equals("")) {
			return "";
		}
		StringBuilder builder = new StringBuilder(param);
		Matcher mc = p.matcher(param);
		while (mc.find()) {
			builder.replace(mc.start(), mc.end(), mc.group().toUpperCase().replaceFirst("_", ""));
			mc = p.matcher(builder.toString());
		}

		if ('_' == builder.charAt(0)) {
			builder.deleteCharAt(0);
		}
		return builder.toString();
	}

	/**
	 * 类属性复制
	 * 
	 * @param dest
	 * @param ori
	 * @param ignoreNull
	 *            ，如果为true，则将源对象中属性为null的值不进行复制，
	 *            如果是整个对象属性一致的，则调用其他copyProperties方法，以提高效率
	 * @return
	 */
	public static <T, K> K copyProperties(T source, K target, boolean ignoreNull) {
		if (source == null)
			return target;
		if (target == null)
			return null;

		if (!ignoreNull)
			return copyProperties(source, target);
		else {
			BeanMap map = BeanMap.create(BeanUtils.instantiateClass(target.getClass()));
			map.setBean(source);

			List<String> getters = new ArrayList<String>();

			for (Object key : map.keySet()) {
				Object v = map.get(key);
				if (v == null) {
					getters.add(key.toString());
				}
			}
			BeanUtils.copyProperties(source, target, getters.toArray(new String[0]));
			return target;
		}
	}

	/**
	 * 复制对象属性
	 * 
	 * @param source
	 * @param target
	 * @return
	 */
	public static <T, K> K copyProperties(T source, K target) {
		if (source == null)
			return target;
		if (target == null)
			return null;

		BeanCopier bc = BeanCopier.create(source.getClass(), target.getClass(), false);
		try {
			bc.copy(source, target, null);
			return target;
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 复制对象属性
	 * 
	 * @param source
	 * @param classK
	 * @return
	 */
	public static <T, K> K copyProperties(T source, Class<K> targetClass) {
		if (source == null)
			return null;
		BeanCopier bc = BeanCopier.create(source.getClass(), targetClass, false);
		try {
			K k = BeanUtils.instantiateClass(targetClass);
			bc.copy(source, k, null);
			return k;
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 批量复制对象属性
	 * 
	 * @param dest
	 * @param classt
	 * @param classK
	 * @return
	 */
	public static <T, K> List<K> copyProperties(List<T> source, Class<T> resourceClass, Class<K> targetClass) {
		if (CollectionUtils.isEmpty(source))
			return new ArrayList<K>();
		BeanCopier bc = BeanCopier.create(resourceClass, targetClass, false);
		List<K> list = new ArrayList<K>();
		try {
			for (T t : source) {
				K k = BeanUtils.instantiateClass(targetClass);
				bc.copy(t, k, null);
				list.add(k);
			}
			return list;
		} catch (Exception e) {
			return new ArrayList<K>();
		}
	}

	/**
	 * 对数据进行位置变更操作
	 * 
	 * @param list
	 * @param t
	 * @param upSize
	 *            移动的位置，可以为负数
	 * @return
	 */
	public static <T> List<T> changePosition(List<T> list, T t, int upSize) {
		if (upSize == 0) {
			return list;
		}
		int index = list.indexOf(t);
		if (index < 0) {
			return list;
		}
		list.remove(t);
		list.add(index + upSize < 0 ? 0 : index + upSize, t);
		return list;
	}

	public static <T> List<T> removeEmptyElement(T[] elements) {
		checkNotNull(elements);
		List<T> os = Lists.newArrayList();
		for (T t : elements) {
			if (t != null) {
				if (t instanceof String) {
					if (StringUtils.isNotEmpty((String) t)) {
						os.add(t);
					}
					continue;
				}
				os.add(t);
			}
		}
		return os;
	}

	/**
	 *
	 * @param elements
	 * @param type
	 * @param <T>
	 * @return
	 **/
	public static <T> T[] removeEmptyElement(T[] elements, Class<T> type) {
		T[] array = (T[]) Array.newInstance(type, 1);
		List<T> os = removeEmptyElement(elements);
		return os.toArray(array);
	}

	private static <T> boolean checkNotNull(T t) {
		if (t == null) {
			throw new IllegalArgumentException("can not null");
		}
		return true;
	}
	
	/**
	 * 获取对象的标志信息（不同的内容会产生惟一标识信息）
	 * @param data
	 * @return
	 */
	public static String getCode(String data){
		return DigestUtils.md5Hex(data) + DigestUtils.shaHex(data);
	}

}
