/* 
 * <p>移动商务系统</p>
 * <p>Commerce1.0</p>
 * <p>Copyright (c) 1997-2005</p>
 * <p>Company: 浙大网络</p>
 * @author luxingmu
 * @since 1.0
 * @version $Id: AssembleTool.java,v 1.3 2006/12/06 05:57:40 luxm Exp $
 */
package net.zdsoft.leadin.util;


public class AssembleTool {

	/**
	 * 将数组objects的内容，以分隔符 seperator 间隔拼装成一个String
	 * 
	 * @param list
	 * @param seperator
	 * @return
	 */
	public static String getAssembledString(Object[] objects, String seperator) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < objects.length; i++) {
			sb.append(objects[i]);
			if (i < objects.length - 1) {
				sb.append(seperator);
			}
		}
		return sb.toString();
	}

	public static String getSqlInString(Object[] objects, String seperator) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < objects.length; i++) {
			sb.append("'"+objects[i]+"'");
			if (i < objects.length - 1) {
				sb.append(seperator);
			}
		}
		return sb.toString();
	}
	
	/**
	 * 将数组objects的内容，以分隔符 seperator 间隔拼装成一个String
	 * 
	 * @param objects
	 * @param seperator
	 * @param sideChar objects 数组内元素在组装时两边还需额外添加的字符
	 * @return
	 */
	public static String getAssembledString(Object[] objects, String seperator,
			String sideChar) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < objects.length; i++) {
			sb.append(sideChar).append(objects[i]).append(sideChar);
			if (i < objects.length - 1) {
				sb.append(seperator);
			}
		}
		return sb.toString();
	}

}
