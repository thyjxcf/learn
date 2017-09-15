package net.zdsoft.eis.frame.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.zdsoft.keel.util.Pagination;
import net.zdsoft.keel.util.URLUtils;

public class PageAction extends BaseAction {
	private static final long serialVersionUID = 932727783029064317L;

	// 分页的html内容
	private String htmlOfPaginationLoad;
	private String htmlOfPaginationDispatch;
	private String htmlOfPaginationDiv;

	private Pagination page = null;

	public PageAction() {
		// 默认不使用游标
		page = new Pagination(Pagination.DEFAULT_PAGE_SIZE, false);
		page.setPageIndex(1);
		page.setMaxRowCount(0);
		page.initialize();
	}

	public Pagination getPage(int pageSize) {
		if (pageSize == Pagination.DEFAULT_PAGE_SIZE) {
			return page;
		}
		Pagination page = new Pagination(pageSize, false);
		page.setPageIndex(1);
		page.setMaxRowCount(0);
		boolean m = false;
		if (page.getPageIndex() == 0)
			page.setPageIndex(1);
		if (page.getMaxRowCount() == 0) {
			page.setMaxRowCount(1);
			page.initialize();
			m = true;
		}

		if (m) {
			page.setMaxRowCount(0);
			m = false;
		}
		return page;
	}

	public Pagination getPage() {
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
	
	public void setPageSize(int pageSize){
		page.setPageSize(pageSize);		
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
	
	// 上下页
    public String getHtmlOfPaginationDiv() {
    	String div = "memocontent";
    	HttpServletRequest request = getRequest();
    	 String queryString = request.getQueryString();
         queryString = queryString == null ? "1=1" : queryString.replaceAll(//
                 "&pageIndex=[0-9].*", "");
        String url = request.getRequestURL() + "?" + queryString;
        StringBuffer pagination = new StringBuffer();
        PageUtils.addPreviousNodev4(pagination, url, page, div);
        PageUtils.addNextNodev4(pagination, url, page, div);
        htmlOfPaginationDiv = pagination.toString();
        return htmlOfPaginationDiv;
    }
    
}
