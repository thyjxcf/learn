/* 
 * @(#)PageUtils.java    Created on 2004-9-21
 * Copyright (c) 2005 ZDSoft Networks, Inc. All rights reserved.
 * $Header: f:/44CVSROOT/exam/src/net/zdsoft/exam/util/PageUtils.java,v 1.2 2007/03/08 02:06:34 linqz Exp $
 */
package net.zdsoft.eis.frame.action;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import net.zdsoft.keel.util.DateUtils;
import net.zdsoft.keel.util.Pagination;
import net.zdsoft.keel.util.ServletUtils;
import net.zdsoft.keel.util.URLUtils;
import net.zdsoft.keel.util.Validators;

/**
 * 主要用于页面显示的工具类.
 * 
 * @author liangxiao
 * @author huangwj
 * @version $Revision: 1.2 $, $Date: 2007/03/08 02:06:34 $
 */
public final class PageUtils {

	public static String getDate(Date date) {
		if (date == null) {
			return "";
		}

		return DateUtils.date2StringByDay(date);
	}

	public static Object ignoreNull(Object value) {
		return (value == null) ? "" : value;
	}

	public static String ignoreZero(float value) {
		return (value == 0) ? "" : String.valueOf(value);
	}

	public static String ignoreZero(int value) {
		return (value == 0) ? "" : String.valueOf(value);
	}

	public static boolean isPost(HttpServletRequest request) {
		return ServletUtils.isPost(request);
	}

	/**
	 * @deprecated 不再使用
	 * @param url
	 * @param page
	 * @return
	 */
	public static String pagination(String url, Pagination page) {
		if (page == null) {
			return "";
		}

		StringBuffer pagination = new StringBuffer();
		final String separator = "&nbsp;";

		String sortString = "";
		pagination.append("共" + page.getMaxRowCount() + "条记录");
		pagination.append(separator);
		pagination.append("这是" + page.getPageIndex() + "/"
				+ page.getMaxPageIndex() + "页");
		pagination.append(separator);

		if (page.getPageIndex() > 1) {
			pagination.append("<a href=\""
					+ URLUtils.addQueryString(url, "pageIndex" + page.getId(),
							"1") + sortString + "\">首页</a>");
			pagination.append(separator);
			pagination.append("<a href=\""
					+ URLUtils.addQueryString(url, "pageIndex" + page.getId(),
							String.valueOf(page.getPageIndex() - 1))
					+ sortString + "\">上一页</a>");
			pagination.append(separator);
		} else {
			pagination.append("首页");
			pagination.append(separator);
			pagination.append("上一页");
			pagination.append(separator);
		}

		if (page.getPageIndex() < page.getMaxPageIndex()) {
			pagination.append("<a href=\""
					+ URLUtils.addQueryString(url, "pageIndex" + page.getId(),
							String.valueOf(page.getPageIndex() + 1))
					+ sortString + "\">下一页</a>");
			pagination.append(separator);
			pagination.append("<a href=\""
					+ URLUtils.addQueryString(url, "pageIndex" + page.getId(),
							String.valueOf(page.getMaxPageIndex()))
					+ sortString + "\">尾页</a>");
			pagination.append(separator);
		} else {
			pagination.append("下一页");
			pagination.append(separator);
			pagination.append("尾页");
			pagination.append(separator);
		}

		return pagination.toString();
	}

	/**
	 * 城域5.0采用的新样式
	 * 
	 * @param url
	 * @param page
	 * @return
	 */
	public static String paginationDispatch(String url, Pagination page) {
		return createPagination(url, page).append(dispatchContent(url, page))
				.toString();
	}

	public static String paginationLoad(String url, Pagination page) {
		return createPagination(url, page).append(loadContent(url, page))
				.toString();
	}

	private static StringBuffer createPagination(String url, Pagination page) {

		// int curRow = page.getCurRowNum();
		int maxPage = page.getMaxPageIndex();
		int maxRow = page.getMaxRowCount();
		int curPage = page.getPageIndex();
		int pageSize = page.getPageSize();
		// 分页栏当前页延续的长度
		int m = 1;
		int sec = 10;//以间隔10到两百
		int lastValue=0;
		int[] paginalValues=new int[]{10,20,50,100,150,200};
		StringBuffer sb = new StringBuffer();
		sb.append("<div class=\"page\">");
		
	    
		//跳转		
		sb.append("<input name=\"targetPageIndex\" id=\"targetPageIndex\" type=\"hidden\"/>");
		sb.append("<div class=\"page-select\">")
		   .append("<div class=\"ui-select-box fn-right mt-15\" style=\"width:50px;\">")
		   .append("<input type=\"text\" class=\"ui-select-txt\" value=\"\" />")
		   .append("<input type=\"hidden\" value=\"\" class=\"ui-select-value\" name=\"tarPageSize\" id=\"tarPageSize\" />")
		   .append("<a class=\"ui-select-close\"></a>")
		   .append("<div class=\"ui-option\" myfunchange=\"jumpPage(1);\" >")
		   .append("<div class=\"a-wrap\">");
		/*
		   for (int i = 1; i <= 20; i++) {
				if (i * sec > pageSize && pageSize > (i - 1) * sec) {					
					sb.append("<a val=\"").append(pageSize)
							.append("\" class=\"selected\" >").append("<span>").append(pageSize)
							.append("</span></a>");
				}
				sb.append("<a val=\"")
						.append(i * sec)
						.append("\""+ (pageSize == (i * sec) ? " class=\"selected\" " : "")
								+ ">").append("<span>").append(i * sec).append("</span></a>");
			}*/
		   
		   for(int val : paginalValues){
			   if(val> pageSize && pageSize >lastValue){
				   sb.append("<a val=\"").append(pageSize)
					.append("\" class=\"selected\" >").append("<span>").append(pageSize)
					.append("</span></a>");
			   }
			   sb.append("<a val=\"")
				.append(val)
				.append("\""+ (pageSize == (val) ? " class=\"selected\" " : "")
						+ ">").append("<span>").append(val).append("</span></a>");
			   
			   lastValue=val;
		   }
		   sb.append("</div>")
		   .append("</div>")
		   .append("</div>")
		   .append("<span class=\"page-select-tips\">每页显示：</span>")
		   .append("</div>");
		   
		//页数
		sb.append("<div class=\"page-list\">");		
		sb.append("\r\n").append("<span class=\"total\">共<span>").append(maxRow).append("</span>条</span>");
		
		if (curPage > 1)
			sb.append("\r\n").append("<a href=\"javascript:jumpPage("
					+ (curPage - 1 < 1 ? 1 : curPage - 1) + ");\">上一页 </a>");			
		else
			sb.append("\r\n").append("<a>上一页 </a>");
		boolean firstDot = true;
		boolean lastDot = true;
		StringBuffer sb2 = new StringBuffer();
		if (maxPage > 2) {
			if (curPage - m > 0) {
				for (int i = (curPage + m > maxPage ? maxPage - m * 2 : curPage
						- m); i <= (curPage + m > maxPage ? maxPage : curPage
						+ m); i++) {
					if (i == maxPage - 1)
						lastDot = false;
					if (i == 2)
						firstDot = false;
					if (i <= 0 || i == 1 || i == maxPage) {
						continue;
					}
					if(curPage == i){
						sb2.append("\r\n").append("<span class=\"current\">")
						.append(i).append("</span>");
					}else{
						sb2.append("\r\n").append("<a href=\"javascript:jumpPage(" + i + ");\"")
						.append(">")
						.append(i).append("</a>");
					}					
				}
			} else {
				for (int i = 1; i <= (2 * m + 1 > maxPage ? maxPage : 2 * m + 1); i++) {
					if (i == maxPage - 1)
						lastDot = false;
					if (i == 2)
						firstDot = false;
					if (i == 1 || i == maxPage)
						continue;
					if(curPage == i){
						sb2.append("\r\n").append("<span class=\"current\">")
						.append(i).append("</span>");
					}else{
						sb2.append("\r\n").append("<a href=\"javascript:jumpPage(" + i + ");\"")
						.append(">")
						.append(i).append("</a>");
					}
					
				}
			}
		}

		if(curPage == 1){
			sb.append("\r\n").append("<span class=\"current\">")
			.append(1)
			.append(maxPage == 0 || maxPage == 1 || maxPage == 2
					|| !firstDot ? "" : "...").append("</span>");
		}else{
			sb.append("\r\n").append("<a href=\"javascript:jumpPage(1);\"")
			.append(">")
			.append(1)
			.append(maxPage == 0 || maxPage == 1 || maxPage == 2
					|| !firstDot ? "" : "...").append("</a>");
		}
		
		sb.append(sb2.toString());
		if (maxPage >= 2) {
			if(curPage == maxPage){
				sb.append("\r\n").append("<span class=\"current\">")
				.append((maxPage == 2 || !lastDot ? "" : "..."))
				.append(maxPage).append("</span>");
			}else{
				sb.append("\r\n").append("<a href=\"javascript:jumpPage(" + maxPage + ");\"")
				.append(">"
						+ (maxPage == 2 || !lastDot ? "" : "..."))
				.append(maxPage).append("</a>");
			}
			
		}
		if (maxPage == 0 || curPage == maxPage)
			sb.append("\r\n").append("<a> 下一页</a>");
		else
			sb.append("\r\n").append("<a href=\"javascript:jumpPage("
					+ (curPage + 1 > maxPage ? maxPage : curPage + 1)
					+ ");\"> 下一页</a>");
		
		sb.append("</div>");
	
		
		sb.append("</div>");
		return sb;
	}

	private static StringBuffer loadContent(String url, Pagination page) {
		StringBuffer sb = new StringBuffer();
		sb.append("<script language=\"javascript\">\n");
		sb.append("function jumpPage(pp) {\n");
		sb.append("   if(pp){jumpPageIndex = pp;} else{jumpPageIndex = document.getElementById(\"targetPageIndex\").value;}\n");
		sb.append("   var tarPageSize = document.getElementById(\"tarPageSize\").value; \n if (isNaN(parseInt(jumpPageIndex))) {\n");
		sb.append("        showMsgWarn(\"请输入要跳转的页码\");\n");
		sb.append("        return;\n");
		sb.append(
				"    }\n document.getElementById(\"targetPageIndex\").value = parseInt(jumpPageIndex); jumpPageIndex = parseInt(jumpPageIndex); if(parseInt(jumpPageIndex) > ")
				.append(page.getMaxPageIndex() == 0 ? 1 : page
						.getMaxPageIndex())
				.append("){\n showMsgWarn(\"不能超过最大页码数\"); \n return;} \n");
		sb.append("   if (parseInt(jumpPageIndex)==0) {showMsgWarn(\"请输入大于0的页码\");return;}\n");
		if (url.indexOf("?") == -1) {
			sb.append("    load(reloadDataContainer, \"" + url
					+ "?pageSize=\" + tarPageSize + \"&pageIndex"
					+ page.getId() + "=\" + jumpPageIndex);\n");
			sb.append("}\n");
			sb.append("</script>\n");
		} else {
			sb.append("    load(reloadDataContainer, \"" + url
					+ "&pageSize=\" + tarPageSize + \"&pageIndex"
					+ page.getId() + "=\" + jumpPageIndex);\n");
			sb.append("}\n");
			sb.append("</script>\n");
		}
		return sb;
	}

	private static StringBuffer dispatchContent(String url, Pagination page) {
		StringBuffer sb = new StringBuffer();
		sb.append("<script language=\"javascript\">\n");
		sb.append("function jumpPage(pp) {\n");
		sb.append("   if(pp){jumpPageIndex = pp;} else{jumpPageIndex = document.getElementById(\"targetPageIndex\").value;}\n");
		sb.append("   var tarPageSize = document.getElementById(\"tarPageSize\").value; \n if (isNaN(parseInt(jumpPageIndex))) {\n");
		sb.append("        showMsgWarn(\"请输入要跳转的页码\");\n");
		sb.append("        return;\n");
		sb.append(
				"    }\n document.getElementById(\"targetPageIndex\").value = parseInt(jumpPageIndex); jumpPageIndex = parseInt(jumpPageIndex); if(parseInt(jumpPageIndex) > ")
				.append(page.getMaxPageIndex() == 0 ? 1 : page
						.getMaxPageIndex())
				.append("){\n showMsgWarn(\"不能超过最大页码数\"); \n return;} \n");
		sb.append("   if (parseInt(jumpPageIndex)==0) {showMsgWarn(\"请输入大于0的页码\");return;}\n");
		if (url.indexOf("?") == -1) {
			sb.append("    location.href = \"" + url
					+ "?pageSize=\" + tarPageSize + \"&pageIndex"
					+ page.getId() + "=\" + jumpPageIndex;\n");
			sb.append("}\n");
			sb.append("</script>\n");
		} else {
			sb.append("    location.href = \"" + url
					+ "&pageSize=\" + tarPageSize + \"&pageIndex"
					+ page.getId() + "=\" + jumpPageIndex;\n");
			sb.append("}\n");
			sb.append("</script>\n");
		}
		return sb;
	}

	public static String pagination(String url, Pagination page,
			boolean needJump, String imageRoot) {
		if (page == null) {
			return "";
		}

		StringBuffer pagination = new StringBuffer();
		final String separator = "&nbsp;";
		String sortString = "";
		pagination.append("共" + page.getMaxRowCount() + "条");
		pagination.append(separator);
		pagination.append(page.getMaxPageIndex() + "页, 当前第"
				+ page.getPageIndex() + "页");
		pagination.append(separator);

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

		if (page.getPageIndex() < page.getMaxPageIndex()) {
			pagination.append("<a href=\""
					+ URLUtils.addQueryString(url, new String[] { "pageIndex"
							+ page.getId() }, new String[] { String
							.valueOf(page.getPageIndex() + 1) }) + sortString
					+ "\"><img title='下一页' border='0' src=" + imageRoot
					+ "/nextPage.gif></a>");
			pagination.append(separator);
			pagination.append("<a href=\""
					+ URLUtils.addQueryString(url, new String[] { "pageIndex"
							+ page.getId() }, new String[] { String
							.valueOf(page.getMaxPageIndex()) }) + sortString
					+ "\"><img title='尾页' border='0' src=" + imageRoot
					+ "/lastPage.gif></a>");
			pagination.append(separator);
		} else {
			pagination.append("<img title='下一页' border='0' src=" + imageRoot
					+ "/nextPage.gif>");
			pagination.append(separator);
			pagination.append("<img title='尾页' border='0' src=" + imageRoot
					+ "/lastPage.gif>");
			pagination.append(separator);
		}

		if (needJump) {
			// pagination
			// .append("第 <input id=\"targetPageIndex\" type=\"text\" size=\"3\" maxlength=\"6\" value=\""
			// + page.getPageIndex()
			// +
			// "\" onfocus=\"this.value = ''\" onkeyup=\"if (event.keyCode == 13) {jumpPage();}\"> 页");

			pagination
					.append("转<select id=\"targetPageIndex\"  onchange=\"jumpPage();\" >");
			for (int i = 1; i < page.getMaxPageIndex() + 1; i++) {
				pagination.append("<option value=\"");
				pagination.append(i);
				pagination.append("\"");
				if (i == page.getPageIndex())
					pagination.append("selected");
				pagination.append(">");
				pagination.append(i);
				pagination.append("</option>");
			}
			pagination.append("</select>页");
			pagination.append("&nbsp;&nbsp;&nbsp;");

			// pagination.append(" <a href=\"javascript:jumpPage()\">跳转</a>");

			pagination.append("<script language=\"javascript\">\n");
			pagination.append("function jumpPage() {\n");
			pagination
					.append("    var jumpPageIndex = document.getElementById('targetPageIndex').value;\n");

			pagination.append("    if (isNaN(parseInt(jumpPageIndex))) {\n");
			pagination.append("        showMsgWarn(\"请输入要跳转的页码\");\n");
			pagination.append("        return;\n");
			pagination.append("    }\n");
			if (url.indexOf("?") == -1) {
				pagination.append("    location.href = \"" + url + "?pageIndex"
						+ page.getId() + "=\" + jumpPageIndex;\n");
				pagination.append("}\n");
				pagination.append("</script>\n");
			} else {
				pagination.append("    location.href = \"" + url + "&pageIndex"
						+ page.getId() + "=\" + jumpPageIndex;\n");
				pagination.append("}\n");
				pagination.append("</script>\n");
			}
		}

		return pagination.toString();
	}

	public static String pageNumber(String url, Pagination page) {
		StringBuffer pageNumber = new StringBuffer();
		final int levelSize = 20;

		int pageIndex = page.getPageIndex();
		int maxPageIndex = page.getMaxPageIndex();

		String sortString = "";

		int level = (pageIndex - 1) / levelSize;
		int startPage = level * levelSize + 1;
		int endPage = level * levelSize + levelSize;
		endPage = endPage > maxPageIndex ? maxPageIndex : endPage;

		if (pageIndex > 2) {
			pageNumber.append("<a href=\""
					+ URLUtils.addQueryString(url, "pageIndex" + page.getId(),
							String.valueOf(1)) + sortString + "\">[首页]</a>\n");
		}

		if (pageIndex > 1) {
			pageNumber.append("<a href=\""
					+ URLUtils.addQueryString(url, "pageIndex" + page.getId(),
							String.valueOf(pageIndex - 1)) + sortString
					+ "\">上一页</a>\n");
		}

		// if (level > 0) {
		// pageNumber.append("<a href=\""
		// + URLUtils.addQueryString(url, "pageIndex" + page.getId(),
		// String.valueOf(startPage - 1)) + sortString
		// + "\">&lt;&lt;</a>\n");
		// }

		for (int i = startPage; i <= endPage; i++) {
			if (pageIndex == i) {
				pageNumber.append(i + "\n");
			} else {
				pageNumber.append("<a href=\""
						+ URLUtils.addQueryString(url,
								"pageIndex" + page.getId(), String.valueOf(i))
						+ sortString + "\">[" + i + "]</a>\n");
			}
		}

		if (pageIndex < maxPageIndex) {
			pageNumber.append("<a href=\""
					+ URLUtils.addQueryString(url, "pageIndex" + page.getId(),
							String.valueOf(pageIndex + 1)) + sortString
					+ "\">下一页</a>\n");
		}

		if (pageIndex + 1 < maxPageIndex) {
			pageNumber.append("<a href=\""
					+ URLUtils.addQueryString(url, "pageIndex" + page.getId(),
							String.valueOf(maxPageIndex)) + sortString
					+ "\">[尾页]</a>\n");
		}

		return pageNumber.toString();
	}

	public static String pagination(String url, Pagination page, int width,
			boolean isFirst, String imageRoot) {
		StringBuffer pagination = new StringBuffer();

		int startLeft = 0;
		if (page.getMaxPageIndex() <= 1) {
			startLeft = 0;
		} else {
			startLeft = (page.getPageIndex() - 1) * (width - 9)
					/ (page.getMaxPageIndex() - 1);
		}

		pagination.append("<script  language=\"javascript\">\n");
		if (isFirst) {
			pagination.append("var  maxPageIndex = new Array();\n");
			pagination.append("var  pageIndex = new Array();\n");
			pagination.append("var  pageUrl = new Array();\n");
			pagination.append("var  dragApproved = false;\n");
			pagination.append("var  movingPageId;\n");
			pagination.append("function showLabel(pageId) {\n");
			pagination
					.append("var pageLabel = document.getElementById(\"pageLabel\" + pageId);\n");
			pagination
					.append("pageLabel.style.left = getPositionX(document.getElementById(\"slidingFlag\" + pageId)) - 20;\n");
			pagination
					.append("pageLabel.style.top = getPositionY(document.getElementById(\"slidingBar\" + pageId)) + 25;\n");
			pagination
					.append("pageLabel.innerHTML = \"第\" + pageIndex[pageId] + \"页\";\n");
			pagination.append("pageLabel.style.display = \"block\";\n");
			pagination.append("}\n");
			pagination.append("function hideLabel(pageId) {\n");
			pagination
					.append("var pageLabel = document.getElementById(\"pageLabel\" + pageId);\n");
			pagination.append("pageLabel.style.display = \"none\";\n");
			pagination.append("}\n");
			pagination.append("function calculatePageIndex(pageId, left) {\n");
			pagination
					.append("var rate = (maxPageIndex[pageId] - 1) * (left) / ("
							+ width + " - 9);\n");
			// pagination.append("window.status = rate + \" : \" +
			// Math.round(rate);\n");
			pagination.append("pageIndex[pageId] = Math.round(rate) + 1;\n");
			pagination.append("}\n");
			pagination.append("function getPageUrl(pageId) {\n");
			pagination.append("if (pageUrl[pageId].indexOf(\"?\") != -1) {\n");
			pagination
					.append("return pageUrl[pageId] + \"&pageIndex\" + pageId + \"=\" + pageIndex[pageId] + \"#\" + pageId;\n");
			pagination.append("}\n");
			pagination.append("else {\n");
			pagination
					.append("return pageUrl[pageId] + \"?pageIndex\" + pageId + \"=\" + pageIndex[pageId] + \"#\" + pageId;\n");
			pagination.append("}\n");
			pagination.append("}\n");
			pagination.append("function moving(){\n");
			pagination.append("if (dragApproved){\n");
			pagination
					.append("var newLeft = startLeft + event.clientX - startX;\n");
			pagination.append("if (newLeft >= 0 && newLeft <= " + width
					+ " - 9) {\n");
			pagination.append("slidingFlag.style.left = newLeft;\n");
			pagination.append("if (maxPageIndex[movingPageId] > 0) {\n");
			pagination.append("calculatePageIndex(movingPageId, newLeft);\n");
			pagination.append("}\n");
			pagination.append("showLabel(movingPageId);\n");
			pagination.append("}\n");
			pagination.append("return false;\n");
			pagination.append("}\n");
			pagination.append("}\n");
			pagination.append("function startDrag(){\n");
			pagination.append("var firedObj = event.srcElement;");
			pagination
					.append("if (firedObj.className == \"dragable\") {       \n");
			pagination.append("dragApproved = true;\n");
			pagination
					.append("movingPageId = firedObj.id.substring(\"slidingFlag\".length);\n");
			pagination
					.append("slidingFlag = document.getElementById(\"slidingFlag\" + movingPageId);\n");
			pagination.append("slidingFlag.style.cursor = \"default\";\n");
			pagination
					.append("startLeft = parseInt(slidingFlag.style.left + 0);\n");
			pagination
					.append("startTop = parseInt(slidingFlag.style.top + 0);\n");
			pagination.append("startX = event.clientX;\n");
			pagination.append("startY = event.clientY;\n");
			pagination.append("document.onmousemove=moving;\n");
			pagination.append("return false;\n");
			pagination.append("}\n");
			pagination.append("}\n");
			pagination.append("document.onmousedown=startDrag;\n");
			pagination.append("function stopDrag() {\n");
			pagination.append("if (dragApproved) {\n");
			pagination.append("dragApproved=false;\n");
			pagination
					.append("var slidingFlag = document.getElementById(\"slidingFlag\" + movingPageId);\n");
			pagination.append("slidingFlag.style.cursor = \"w-resize\";\n");
			pagination
					.append("var newLeft = parseInt(slidingFlag.style.left + 0);\n");
			pagination.append("if (maxPageIndex[movingPageId] > 0) {\n");
			pagination.append("calculatePageIndex(movingPageId, newLeft);\n");
			pagination.append("}\n");
			pagination.append("hideLabel(movingPageId);\n");
			pagination.append("location.href = getPageUrl(movingPageId);\n");
			pagination.append("}\n");
			pagination.append("}\n");
			pagination.append("document.onmouseup=stopDrag;\n");
			pagination.append("function mouseDown(e, pageId) {\n");
			pagination
					.append("var  slidingFlag = document.getElementById(\"slidingFlag\" + pageId);\n");
			pagination
					.append("var newLeft = e.clientX - getPositionX(document.getElementById(\"slidingBar\" + pageId));\n");
			pagination
					.append("var oldLeft = parseInt(slidingFlag.style.left + 0);\n");
			pagination
					.append("if (newLeft >= oldLeft && newLeft <= oldLeft + 9) {\n");
			pagination.append("return;\n");
			pagination.append("}\n");
			pagination.append("newLeft = newLeft - 5;\n");
			pagination.append("if (newLeft < 0) {\n");
			pagination.append("newLeft = 0;\n");
			pagination.append("}\n");
			pagination.append("if (newLeft > " + width + " - 9) {\n");
			pagination.append("newLeft = " + width + " - 9;\n");
			pagination.append("}\n");
			pagination.append("slidingFlag.style.left= newLeft;\n");
			pagination.append("if   (maxPageIndex[pageId] > 0) {\n");
			pagination.append("calculatePageIndex(pageId, newLeft);\n");
			pagination.append("}\n");
			pagination.append("location.href = getPageUrl(pageId);\n");
			pagination.append("}\n");
			pagination.append("function getPositionX(obj) {\n");
			pagination.append("l = obj.offsetLeft;\n");
			pagination.append("while( obj=obj.offsetParent ) {\n");
			pagination.append("l += obj.offsetLeft;\n");
			pagination.append("}\n");
			pagination.append("return l;\n");
			pagination.append("}\n");
			pagination.append("function getPositionY(obj) {\n");
			pagination.append("t = obj.offsetTop;\n");
			pagination.append("while( obj=obj.offsetParent ){\n");
			pagination.append("t += obj.offsetTop;\n");
			pagination.append("}\n");
			pagination.append("return t;\n");
			pagination.append("}\n");
		}
		pagination.append("maxPageIndex[\"" + page.getId() + "\"]  = "
				+ page.getMaxPageIndex() + ";\n");
		pagination.append("pageIndex[\"" + page.getId() + "\"] = "
				+ page.getPageIndex() + ";\n");
		pagination.append("pageUrl[\"" + page.getId() + "\"] = \"" + url
				+ "\";\n");
		pagination.append("</script>\n");
		pagination
				.append("<div style=\"height:24px; text-align:center; background:url('"
						+ imageRoot + "sliding_bar_bg.gif') repeat-x;\">\n");
		pagination.append("<div style=\"width:" + (width + 48) + "px;\">\n");
		pagination
				.append("<div style=\"width:24px; height:24px; float:left;\"><a href=\"#\"><img alt=\"上一页\" src=\""
						+ imageRoot
						+ "sliding_bar_prev.gif\" height=\"24\" width=\"24\" border=\"0\"></a></div>\n");
		pagination
				.append("<div id=\"slidingBar"
						+ page.getId()
						+ "\" onmousedown=\"mouseDown(event, '"
						+ page.getId()
						+ "')\" style=\"width:"
						+ width
						+ "px; height:24px; float:left; text-align:left; background:url('"
						+ imageRoot + "sliding_bar_axle.gif')   repeat-x;\">\n");
		pagination
				.append("<div class=\"dragable\" id=\"slidingFlag"
						+ page.getId()
						+ "\" onmouseover=\"showLabel('"
						+ page.getId()
						+ "')\" style=\"width:9px; height:14px; left:"
						+ startLeft
						+ "px; top:5px; background:url('"
						+ imageRoot
						+ "sliding_bar_flag.gif')    no-repeat; cursor:w-resize; position:relative;\" onmouseout=\"hideLabel('"
						+ page.getId() + "')\"></div>\n");
		pagination.append("</div>\n");
		pagination
				.append("<div style=\"width:24px; height:24px; float:left;\"><a href=\"#\"><img alt=\"下一页\" src=\""
						+ imageRoot
						+ "sliding_bar_next.gif\" height=\"24\" width=\"24\" border=\"0\"></a></div>\n");
		pagination.append("</div>\n");
		pagination.append("</div>\n");
		pagination
				.append("<div id=\"pageLabel"
						+ page.getId()
						+ "\" style=\"position:absolute; color:#666666; font-size:12px; font-weight:bold; width:50px; height:29px; text-align:center; padding-top:8px; background:url('"
						+ imageRoot
						+ "sliding_bar_label.gif') no-repeat; display:none;\"></div>\n");

		return pagination.toString();
	}

	public static java.util.Date parseExample(String dateStr) {
		// String ds = "November 1, 2000";

		DateFormat fullDateFormat = DateFormat.getDateTimeInstance();

		try {
			java.util.Date d = fullDateFormat.parse(dateStr);
			return d;
		} catch (ParseException e) {
			System.out.println("不能转换 " + dateStr);
		}
		return null;
	}

	/**
	 * 与StringUtils.htmlFilter 的区别是空格不作转换
	 * 
	 * @param value
	 * @return
	 */
	public static String htmlFilter(String value) {
		if (Validators.isEmpty(value)) {
			return "&nbsp;";
		}

		return value.replaceAll("&", "&amp;").replaceAll("<", "&lt;")
				.replaceAll(">", "&gt;").replaceAll("\"", "&quot;")
				.replaceAll("\n", "<br>");
	}
	
	public static StringBuffer addPreviousNodev4(StringBuffer pagination, String url, Pagination page, String div) {
        if (page.getPageIndex() > 1) {
            // <a href="#" class="prev">上一页</a>
            pagination.append("<a class=\"prev f-right\" href=\"javascript:void(0)\"");
            addAjaxFlushScriptv4(pagination,
                    URLUtils.addQueryString(url, "pageIndex" + page.getId(), String.valueOf(page.getPageIndex() - 1)),
                    div);
            pagination.append(" ><span>上一页</span></a>");
        }
        else {
            pagination
                    .append("<a class=\"prev f-right\" href=\"javascript:void(0)\"> <span class=\"f-right\">&nbsp;&nbsp</span></a>");
        }
        return pagination;
    }

	public static StringBuffer addNextNodev4(StringBuffer pagination, String url, Pagination page, String div) {
        if (page.getPageIndex() < page.getMaxPageIndex()) {
            // <a href="#" class="next">下一页</a></span>
            pagination.append("<a class=\"next f-right\" href=\"javascript:void(0)\"");
            addAjaxFlushScriptv4(pagination,
                    URLUtils.addQueryString(url, "pageIndex" + page.getId(), String.valueOf(page.getPageIndex() + 1)),
                    div);
            pagination.append(" ><span>下一页</span></a>");
        }
        else {
            pagination
                    .append("<a class=\"next f-right\" href=\"javascript:void(0)\"> <span class=\"f-right\">&nbsp;&nbsp</span></a>");
        }
        return pagination;
    }
    
    private static StringBuffer addAjaxFlushScriptv4(StringBuffer pagination, String fullUrl, String div) {
        // $('#memo-box').load('/memo/listMemos.htm', function() {});
        pagination.append(" onclick=\"javascript:$('#");
        pagination.append(div).append("').load('").append(fullUrl);
        pagination.append("', function() {});\"");
        return pagination;
    }

	private PageUtils() {
	}

}
