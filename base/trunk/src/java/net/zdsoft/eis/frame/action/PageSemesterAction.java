/* 
 * @(#)PageAction.java    Created on Mar 5, 2007
 * Copyright (c) 2006 ZDSoft Networks, Inc. All rights reserved.
 * $Header: f:/44CVSROOT/exam/src/net/zdsoft/exam/client/PageAction.java,v 1.4 2007/03/08 02:06:34 linqz Exp $
 */
package net.zdsoft.eis.frame.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.zdsoft.keel.util.Pagination;
import net.zdsoft.keel.util.URLUtils;

public class PageSemesterAction extends BaseSemesterAction {

    /**
     * Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = -3812317298714304406L;

    // 分页的html内容
	private String htmlOfPaginationLoad;
	private String htmlOfPaginationDispatch;

    private Pagination page = null;

    public PageSemesterAction() {
        // 默认不使用游标
        page = new Pagination(Pagination.DEFAULT_PAGE_SIZE, false);
    }

    public Pagination getPage() {
        boolean m = false;
        if (page.getPageIndex() == 0)
            page.setPageIndex(1);
        if(page.getMaxRowCount() == 0){
            page.setMaxRowCount(1);
            m = true;
        }
        //page.initialize(); //注释by chenzy 在basicDAO调用该方法比较合适.
        if (m){
            page.setMaxRowCount(0);
        }
        return page;
    }

    @SuppressWarnings("unchecked")
    public List getPageList(List rs) {
		page = getPage();
		page.setMaxRowCount(rs.size());
		page.initialize();
		List results = new ArrayList();
		if (page.getMaxRowCount() > 0) {
			int fromIndex = (page.getPageIndex() - 1) * page.getPageSize();
			int toIndex = page.getPageIndex() * page.getPageSize();
			if(toIndex+1>=page.getMaxRowCount())
                toIndex=page.getMaxRowCount();
			results.addAll(rs.subList(fromIndex, toIndex));
		}

		return results;
	}
    
    public void setPageIndex(int pageIndex) {
        page.setPageIndex(pageIndex);
    }
    
    public void setPageSize(int pageSize){
    	page.setPageSize(pageSize);
    }

    public int getPageIndex() {
        return page.getPageIndex();
    }

    public void setUseCursor(boolean isUseCursor) {
        page.setUseCursor(isUseCursor);
    }

    public String getHtmlOfPaginationDispatch() {
		if(htmlOfPaginationDispatch != null)
			return htmlOfPaginationDispatch;
		
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
		Map mapOfParameter = request.getParameterMap();
		Map<String, String> mapOfParameterOpe = new HashMap<String, String>();
		mapOfParameterOpe.putAll(mapOfParameter);
		if (mapOfParameterOpe.containsKey("pageIndex")) {
			mapOfParameterOpe.remove("pageIndex");
		}
		if (mapOfParameterOpe.containsKey("pageSize")) {
			mapOfParameterOpe.remove("pageSize");
		}
		
		String[] keys = mapOfParameterOpe.keySet().toArray(new String[0]);
		Object[] values = new Object[0];
		if (keys != null) {
			values = new Object[keys.length];
			for (int i = 0; i < keys.length; i++) {
				values[i] = mapOfParameterOpe.get(keys[i]);
			}
		}
		String url = URLUtils.addQueryString(actionName, keys, values);
		htmlOfPaginationDispatch = PageUtils.paginationDispatch(url, page);
		if (null == htmlOfPaginationDispatch)
			htmlOfPaginationDispatch = "";
		return htmlOfPaginationDispatch;
	}

	public String getHtmlOfPaginationLoad() {
		if (htmlOfPaginationLoad != null)
			return htmlOfPaginationLoad;
		
		HttpServletRequest request = getRequest();
		String uri = request.getRequestURI();
		String actionName = uri;
//		if (uri != null && !uri.equals("")) {
//			int pos = uri.lastIndexOf("/");
//			if (pos < 0) {
//				pos = uri.lastIndexOf("\\");
//			}
//			if (pos >= 0 && pos != uri.length() - 1) {
//				actionName = uri.substring(pos + 1, uri.length());
//			}
//		}
		// 因为从request中取得的这个map是被锁定的,不能操作,所以另定义一个
		Map mapOfParameter = request.getParameterMap();
		Map<String, String> mapOfParameterOpe = new HashMap<String, String>();
		mapOfParameterOpe.putAll(mapOfParameter);
		
		//zengzt 2014-07-18特殊处理错误显示消息不需带到下一页中
		if(mapOfParameterOpe.containsKey("redirectResultMsg")){
			mapOfParameterOpe.remove("redirectResultMsg");
		}

		if (mapOfParameterOpe.containsKey("pageIndex")) {
			mapOfParameterOpe.remove("pageIndex");
		}
		if (mapOfParameterOpe.containsKey("pageSize")) {
			mapOfParameterOpe.remove("pageSize");
		}
		
		String[] keys = mapOfParameterOpe.keySet().toArray(new String[0]);
		Object[] values = new Object[0];
		if (keys != null) {
			values = new Object[keys.length];
			for (int i = 0; i < keys.length; i++) {
				values[i] = mapOfParameterOpe.get(keys[i]);
			}
		}
		String url = URLUtils.addQueryString(actionName, keys, values);
		htmlOfPaginationLoad = PageUtils.paginationLoad(url, page);
		if (null == htmlOfPaginationLoad)
			htmlOfPaginationLoad = "";
		return htmlOfPaginationLoad;
	}
}
