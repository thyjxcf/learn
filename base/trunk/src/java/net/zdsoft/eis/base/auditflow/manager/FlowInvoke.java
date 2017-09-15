package net.zdsoft.eis.base.auditflow.manager;

import java.lang.reflect.Method;

public class FlowInvoke {

	/**
	 * 2个参数
	 * @param owner
	 * @param method
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public static String getArrangeId(Object owner, String method,String[] param) throws Exception {
		Class<?> ownerClass = owner.getClass();
		Method m = ownerClass.getDeclaredMethod(method, String.class,String.class);
		return (String)m.invoke(owner, param[0],param[1]);
	}
	
	/**
	 * 3个参数
	 * @param owner
	 * @param method
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public static String getArrangeId2(Object owner, String method,String[] param) throws Exception {
		Class<?> ownerClass = owner.getClass();
		Method m = ownerClass.getDeclaredMethod(method, String.class,String.class,String.class);
		return (String)m.invoke(owner, param[0],param[1],param[2]);
	}
}
