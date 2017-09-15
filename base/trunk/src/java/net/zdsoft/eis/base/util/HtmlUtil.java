package net.zdsoft.eis.base.util;

import java.util.Iterator;
import java.util.List;

/* 
 * 生成页面中Html语句的一些通用方法
 * <p>ZDSoft学籍系统（stusys）V3.5</p>
 * @author Dongzk
 * @since 1.0
 * @version $Id: HtmlUtil.java,v 1.2 2006/09/06 10:18:50 dongzk Exp $
 */
public class HtmlUtil {
	
	/**
	 * 根据list（内容为String[]对象）,生成一份页面上用的Select Html标签语句
	 * @param arrayList 生成选择框的源列表
	 * @param defaultSelect 是否添加默认选择项，true添加，false不添加
	 * @return String
	 */
	public static <E> String getSelectHtmlTag(List<E> arrayList, boolean defaultSelect) {
	    return getSelectHtmlTag(null, arrayList, defaultSelect);
	  }
	
	
	/**
	 * 根据list（内容为String[]对象）,生成一份页面上用的Select Html标签语句
	 * @param currentValue 当前选项的值
	 * @param arrayList 生成选择框的源列表
	 * @param defaultSelect 是否添加默认选择项，true添加，false不添加
	 * @return String
	 */
	public static <E> String getSelectHtmlTag(String currentValue, List<E> arrayList,
			boolean defaultSelect) {
	    return getSelectHtmlTag("", "--请选择--", currentValue, arrayList, defaultSelect);
	  }
	
	
	/**
	 * 根据list（内容为String[]对象）,生成一份页面上用的Select Html标签语句 
	 * @param defaultValue 默认选项的值
	 * @param defaultData 默认选项的显示值
	 * @param currentValue 当前选项的值
	 * @param arrayList 生成选择框的源列表
	 * @param defaultSelect 是否添加默认选择项，true添加，false不添加
	 * @return String
	 */
	public static <E> String getSelectHtmlTag(String defaultValue, String defaultData,
			String currentValue, List<E> arrayList, boolean defaultSelect) {
		StringBuffer htmlBuf = new StringBuffer();
		if (arrayList != null && arrayList.size() > 0) {
			if (defaultSelect) {
				htmlBuf.append("<a val='");
				htmlBuf.append(defaultValue).append("'><span>");
				if (defaultData == null) {
					htmlBuf.append("--请选择--");
				} else {
					htmlBuf.append(defaultData);
				}
				htmlBuf.append("</span></a>");
			}
			for (Iterator<E> ite = arrayList.iterator(); ite.hasNext();) {
				String[] s = (String[]) ite.next();
				htmlBuf.append("<a val='");
				htmlBuf.append(s[0]);
				htmlBuf.append("'");
				if (currentValue != null && s[0].equals(currentValue))
					htmlBuf.append(" class='selected' ");
				htmlBuf.append("><span>");
				htmlBuf.append(s[1]);
				htmlBuf.append("</span></a>");
			}
		} else {
			htmlBuf.append("<a val=''><span>--没有可选数据--</span></a>");
		}
		return htmlBuf.toString();
	}
}
