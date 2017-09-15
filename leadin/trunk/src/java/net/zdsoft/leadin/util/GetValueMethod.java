package net.zdsoft.leadin.util;

import java.lang.reflect.Method;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author yangm
 * @version $Revision: 1.1 $, $Date: 2006/09/27 11:53:12 $
 */
public final class GetValueMethod {

	static Logger logger = LoggerFactory.getLogger(GetValueMethod.class);

	/**
	 * 适用于entity中成员的get方法
	 * 
	 * @param object
	 * @param propertyName
	 * @param partypes
	 * @param arglist
	 * @return
	 */
	public static Object getValue(Object object, String propertyName,
			Class<?>[] partypes, Object[] arglist) {

		if (propertyName == null || propertyName.length() == 0) {
			return null;
		}
		String propertyNameUpper = propertyName.substring(0, 1).toUpperCase()
				+ propertyName.substring(1);

		String methodName = "";
		String[] methodNames = {"get" + propertyNameUpper,
				"is" + propertyNameUpper};

		Object objectRtn = new Object();
		boolean sign = false;
		for (int i = 0; i < methodNames.length; i++) {
			methodName = methodNames[i];
			try {
				Method method = object.getClass().getMethod(methodName,
						partypes);
				objectRtn = method.invoke(object, arglist);
			} catch (Exception e) {
//				logger.error("获取" + methodName + "失败:"+e.getMessage());
			}			
			sign = true;
			break; // 调用成功时退出
		}
		if(!sign){
			logger.error("获取" + methodName + "失败");
		}
			
		return objectRtn;
	}
	 public static Method getMethod(Object object, String propertyName,
				Class<?>[] partypes) {
			if (propertyName == null || propertyName.length() == 0) {
				return null;
			}
			String propertyNameUpper = propertyName.substring(0, 1).toUpperCase()
					+ propertyName.substring(1);

			String methodName = "get" + propertyNameUpper;
			Method method = null;
			try {
				method = object.getClass().getMethod(methodName, partypes);
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			}
			return method;
		}
}
