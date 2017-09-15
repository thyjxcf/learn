/* 
 * @(#)PageAction.java    Created on Mar 5, 2007
 * Copyright (c) 2006 ZDSoft Networks, Inc. All rights reserved.
 * $Header: f:/44CVSROOT/exam/src/net/zdsoft/exam/client/PageAction.java,v 1.4 2007/03/08 02:06:34 linqz Exp $
 */
package net.zdsoft.eis.support.page;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.zdsoft.eis.frame.action.BaseAction;
import net.zdsoft.keel.util.Pagination;
import net.zdsoft.keel.util.URLUtils;

/**
 * @deprecated
 * 
 * 只在数字校园2.0中使用
 * @author zhangza
 *
 */
public class PageAction extends BaseAction {
    private static final long serialVersionUID = 1L;

    // 分页的html内容
	private String htmlOfPagination;

	private static final int DEFAULT_PAGE_SIZE = 15;

	private Pagination page = null;

	public PageAction() {
		// 默认不使用游标
		page = new Pagination(DEFAULT_PAGE_SIZE, false);
		page.setPageIndex(1);
		page.setMaxRowCount(0);
		page.initialize();
	}

	public Pagination getPage(int pageSize) {
		Pagination page = getPage();
		if (page != null)
			page.setPageSize(pageSize);
		return page;
	}

	public Pagination getPage() {
		boolean m = false;
		if (page.getPageIndex() == 0)
			page.setPageIndex(1);
		if (page.getMaxRowCount() == 0) {
			page.setMaxRowCount(1);
			// page.initialize();
			m = true;
		}

		if (m) {
			page.setMaxRowCount(0);
			m = false;
		}
		return page;
	}

	public void setPageIndex(int pageIndex) {
		page.setPageIndex(pageIndex);
	}

	public void setMaxRowCount(int maxRowCount) {
		page.setMaxRowCount(maxRowCount);
	}

	public int getMaxRowCount() {

		return page.getMaxRowCount();
	}

	public int getPageIndex() {
		if (page.getPageIndex() == 0)
			setPageIndex(1);
		return page.getPageIndex();
	}

	public void setUseCursor(boolean isUseCursor) {
		page.setUseCursor(isUseCursor);
	}

	/**
	 * @return the htmlOfPagination
	 */
	@SuppressWarnings("unchecked")
	public String getHtmlOfPagination() {
		HttpServletRequest request = getRequest();
		String uri = request.getRequestURI();
		String actionName = "";
		if (uri != null && !uri.equals("")) {
			int pos = uri.lastIndexOf("/");
			if (pos < 0) {
				pos = uri.lastIndexOf("\\");
			}
			if (pos >= 0 && pos != uri.length() - 1) {
				actionName = uri.substring(pos + 1, uri.length());
			}
		}
		// 因为从request中取得的这个map是被锁定的,不能操作,所以另定义一个
		Map<String, String> mapOfParameter = request.getParameterMap();
		Map<String, String> mapOfParameterOpe = new HashMap<String, String>();
		mapOfParameterOpe.putAll(mapOfParameter);
		if (mapOfParameterOpe.containsKey("pageIndex"))
			mapOfParameterOpe.remove("pageIndex");
		String[] keys = mapOfParameterOpe.keySet().toArray(new String[0]);
		Object[] values = new Object[0];
		if (keys != null) {
			values = new Object[keys.length];
			for (int i = 0; i < keys.length; i++) {
				values[i] = mapOfParameterOpe.get(keys[i]);
			}
		}
		String url = URLUtils.addQueryString(actionName, keys, values);
		htmlOfPagination = PageUtils.pagination(url, getPage(), true, request
				.getContextPath()
				+ "/static/images/table");
		if (null == htmlOfPagination)
			htmlOfPagination = "";
		return htmlOfPagination;
	}

	/**
	 * 分页第二种样式
	 * 
	 * @return
	 */	
    public String getHtmlOfPagination2() {
		HttpServletRequest request = getRequest();
		String uri = request.getRequestURI();
		String actionName = "";
		if (uri != null && !uri.equals("")) {
			int pos = uri.lastIndexOf("/");
			if (pos < 0) {
				pos = uri.lastIndexOf("\\");
			}
			if (pos >= 0 && pos != uri.length() - 1) {
				actionName = uri.substring(pos + 1, uri.length());
			}
		}
		// 因为从request中取得的这个map是被锁定的,不能操作,所以另定义一个
		@SuppressWarnings("unchecked")
		Map<String, String> mapOfParameter = request.getParameterMap();
		Map<String, String> mapOfParameterOpe = new HashMap<String, String>();
		mapOfParameterOpe.putAll(mapOfParameter);
		if (mapOfParameterOpe.containsKey("pageIndex"))
			mapOfParameterOpe.remove("pageIndex");
		String[] keys = mapOfParameterOpe.keySet().toArray(new String[0]);
		Object[] values = new Object[0];
		if (keys != null) {
			values = new Object[keys.length];
			for (int i = 0; i < keys.length; i++) {
				values[i] = mapOfParameterOpe.get(keys[i]);
			}
		}
		String url = URLUtils.addQueryString(actionName, keys, values);
		htmlOfPagination = pagination(url, getPage(), true, request
				.getContextPath()
				+ "/exam/images");
		if (null == htmlOfPagination)
			htmlOfPagination = "";
		return htmlOfPagination;
	}

	private String pagination(String url, Pagination page, boolean needJump,
			String imageRoot) {
		if (page == null) {
			return "";
		}

		StringBuffer pagination = new StringBuffer();
		final String separator = "&nbsp;";
		String sortString = "";
		// pagination.append("共" + page.getMaxRowCount() + "个学校");
		// pagination.append(separator);
		// pagination.append("共" + page.getPageIndex() + "/"
		// + page.getMaxPageIndex() + "页");
		pagination.append(separator);

		if (page.getPageIndex() > 1) {
			pagination.append("<a href=\""
					+ URLUtils.addQueryString(url, "pageIndex" + page.getId(),
							String.valueOf(page.getPageIndex() - 1))
					+ sortString + "\"><img title='上一页' src="
					+ "images/prevPage.jpg></a>");
			pagination.append(separator);
		} else {
			pagination.append("<img title='上一页' src=" + "images/prevPage.jpg>");
			pagination.append(separator);
		}

		if (page.getPageIndex() < page.getMaxPageIndex()) {
			pagination.append("<a href=\""
					+ URLUtils.addQueryString(url, "pageIndex" + page.getId(),
							String.valueOf(page.getPageIndex() + 1))
					+ sortString + "\"><img title='下一页' src="
					+ "images/nextPage.jpg></a>");
			pagination.append(separator);
			pagination.append(separator);
		} else {
			pagination.append("<img title='下一页' src=" + "images/nextPage.jpg>");
			pagination.append(separator);
			pagination.append(separator);
		}
		pagination.append("" + page.getMaxRowCount() + "学校");
		pagination.append(separator);
		pagination.append("" + page.getPageIndex() + "/"
				+ page.getMaxPageIndex() + "页");
		return pagination.toString();
	}

	/**
	 * @return the htmlOfPagination
	 */
	@SuppressWarnings("unchecked")
	public String getHtmlOfPaginationForIm() {
		HttpServletRequest request = getRequest();
		String uri = request.getRequestURI();
		String actionName = "";
		if (uri != null && !uri.equals("")) {
			int pos = uri.lastIndexOf("/");
			if (pos < 0) {
				pos = uri.lastIndexOf("\\");
			}
			if (pos >= 0 && pos != uri.length() - 1) {
				actionName = uri.substring(pos + 1, uri.length());
			}
		}
		// 因为从request中取得的这个map是被锁定的,不能操作,所以另定义一个
		Map<String, String> mapOfParameter = request.getParameterMap();
		Map<String, String> mapOfParameterOpe = new HashMap<String, String>();
		mapOfParameterOpe.putAll(mapOfParameter);
		if (mapOfParameterOpe.containsKey("pageIndex"))
			mapOfParameterOpe.remove("pageIndex");
		String[] keys = mapOfParameterOpe.keySet().toArray(new String[0]);
		Object[] values = new Object[0];
		if (keys != null) {
			values = new Object[keys.length];
			for (int i = 0; i < keys.length; i++) {
				values[i] = mapOfParameterOpe.get(keys[i]);
			}
		}
		String url = URLUtils.addQueryString(actionName, keys, values);
		htmlOfPagination = paginationForIm(url, getPage(), true, request
				.getContextPath()
				+ "/fpf/im/images");
		if (null == htmlOfPagination)
			htmlOfPagination = "";
		return htmlOfPagination;
	}

	private String paginationForIm(String url, Pagination page,
			boolean needJump, String imageRoot) {
		if (page == null) {
			return "";
		}

		StringBuffer pagination = new StringBuffer();
		final String separator = "&nbsp;&nbsp;";
		String sortString = "";
		// pagination.append("共" + page.getMaxRowCount() + "条");
		// pagination.append(separator);
		// pagination.append(page.getMaxPageIndex() + "页, 当前第" +
		// page.getPageIndex() + "页");
		// pagination.append(separator);

		if (page.getPageIndex() > 1) {
			pagination.append("<a href=\""
					+ URLUtils.addQueryString(url, "pageIndex" + page.getId(),
							"1") + sortString
					+ "\"><img title='首页' border='0' src=" + imageRoot
					+ "/firstPage.gif></a>");
			pagination.append(separator);
			pagination.append("<a href=\""
					+ URLUtils.addQueryString(url, "pageIndex" + page.getId(),
							String.valueOf(page.getPageIndex() - 1))
					+ sortString + "\"><img title='上一页' border='0' src="
					+ imageRoot + "/prevPage.gif></a>");
			pagination.append(separator);
		} else {
			pagination.append("<img title='首页' border='0' src=" + imageRoot
					+ "/firstPage.gif>");
			pagination.append(separator);
			pagination.append("<img title='上一页' border='0' src=" + imageRoot
					+ "/prevPage.gif>");
			pagination.append(separator);
		}

		pagination.append(page.getPageIndex());
		pagination.append(separator);

		if (page.getPageIndex() < page.getMaxPageIndex()) {
			pagination.append("<a href=\""
					+ URLUtils.addQueryString(url, new String[] {
							"pageIndex" + page.getId(),
							"maxRowCount" + page.getId() }, new String[] {
							String.valueOf(page.getPageIndex() + 1),
							String.valueOf(page.getMaxRowCount()) })
					+ sortString + "\"><img title='下一页' border='0' src="
					+ imageRoot + "/nextPage.gif></a>");
			pagination.append(separator);
			pagination.append("<a href=\""
					+ URLUtils.addQueryString(url, new String[] {
							"pageIndex" + page.getId(),
							"maxRowCount" + page.getId() }, new String[] {
							String.valueOf(page.getMaxPageIndex()),
							String.valueOf(page.getMaxRowCount()) })
					+ sortString + "\"><img title='尾页' border='0' src="
					+ imageRoot + "/lastPage.gif></a>");
			pagination.append(separator);
		} else {
			pagination.append("<img title='下一页' border='0' src=" + imageRoot
					+ "/nextPage.gif>");
			pagination.append(separator);
			pagination.append("<img title='尾页' border='0' src=" + imageRoot
					+ "/lastPage.gif>");
			pagination.append(separator);
		}

		return pagination.toString();
	}

}
