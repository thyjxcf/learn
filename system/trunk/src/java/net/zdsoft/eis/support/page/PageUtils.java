/* 
 * @(#)PageUtils.java    Created on 2004-9-21
 * Copyright (c) 2005 ZDSoft Networks, Inc. All rights reserved.
 * $Header: f:/44CVSROOT/exam/src/net/zdsoft/exam/util/PageUtils.java,v 1.2 2007/03/08 02:06:34 linqz Exp $
 */
package net.zdsoft.eis.support.page;

import java.net.URLEncoder;
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

		if (needJump) {
			// pagination
			// .append("第 <input id=\"targetPageIndex\" type=\"text\" size=\"3\"
			// maxlength=\"6\" value=\""
			// + page.getPageIndex()
			// + "\" onfocus=\"this.value = ''\" onkeyup=\"if (event.keyCode ==
			// 13) {jumpPage();}\"> 页");

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
			pagination.append("        alert(\"请输入要跳转的页码\");\n");
			pagination.append("        return;\n");
			pagination.append("    }\n");

			// pagination.append("}\n");
			// pagination.append("</script>\n");
			// pagination.append("<input type='text' size='3' value='" +
			// page.getPageSize() + "' onkeypress=");
			// pagination.append("var crd=parseInt(event.srcElement.value, 10);
			// var pattern=/[0-9]*/;");
			// pagination.append("if(crd <=0 || crd > 1000) return; var keycode
			// = event.keyCode;");
			// pagination.append("if(keycode == 13 || keycode == 3){if
			// (pattern.exec(event.srcElement.value) == null ");
			// pagination.append("|| isNan(crd)){return false;}");
			//            
			if (url.indexOf("?") == -1) {
				pagination.append("    location.href = \"" + url + "?pageIndex"
						+ page.getId() + "=\" + jumpPageIndex + \"&maxRowCount"
						+ page.getId() + "=" + page.getMaxRowCount() + "\";\n");
				pagination.append("}\n");
				pagination.append("</script>\n");
			} else {
				pagination.append("    location.href = \"" + url + "&pageIndex"
						+ page.getId() + "=\" + jumpPageIndex + \"&maxRowCount"
						+ page.getId() + "=" + page.getMaxRowCount() + "\";\n");
				pagination.append("}\n");
				pagination.append("</script>\n");
			}
			/*
			 * <input type='text' size='3' value="20" OnkeyPress="var crd =
			 * parseInt(event.srcElement.value, 10); var pattern=/[0-9]/; if(crd<=0 ||
			 * crd>1000) return; var keycode = event.keyCode; if (keycode == 13 ||
			 * keycode == 3) { if(pattern.exec(event.srcElement.value) == null ||
			 * isNaN(crd)){ return false; }
			 * document.forms.mainform.ec_crd.value=crd;
			 * document.forms.mainform.ec_p.value='1';
			 * document.forms.mainform.setAttribute('action','basicSemesterAdmin.action');
			 * document.forms.mainform.setAttribute('method','post' );
			 * document.forms.mainform.submit()}" >条/页</td>" + "" + "
			 */
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
						+ URLUtils.addQueryString(url, "pageIndex"
								+ page.getId(), String.valueOf(i)) + sortString
						+ "\">[" + i + "]</a>\n");
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

	/**
     * URL 参数转码
     * 
     * @param para
     * @param charset
     * @return
     */
    public static String encodeString(String para, String charset) {
        String temp = para;
        try {
            temp = URLEncoder.encode(para, charset);
        } catch (java.io.UnsupportedEncodingException ex) {
        }
        return temp;
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
				.replaceAll(">", "&gt;").replaceAll("\"", "&quot;").replaceAll(
						"\n", "<br>");
	}

	private PageUtils() {
	}

	public static String pagination(String url, Pagination page,
			boolean needJump) {
		if (page == null) {
			return "";
		}

		StringBuffer pagination = new StringBuffer();
		final String separator = "&nbsp;";
		String sortString = "";
		pagination.append("共" + page.getMaxRowCount() + "条");
		pagination.append(separator);
		pagination.append(page.getMaxPageIndex() + "页, 当前第"
				+ page.getPageIndex() +

				"页");
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
			pagination.append("<font color='gray'>首页</font>");
			pagination.append(separator);
			pagination.append("<font color='gray'>上一页</font>");
			pagination.append(separator);
		}

		if (page.getPageIndex() < page.getMaxPageIndex()) {
			pagination.append("<a href=\""
					+ URLUtils.addQueryString(url, new String[] { "pageIndex" +

					page.getId(), "maxRowCount" + page.getId() }, new String[] {
							String.valueOf(page.getPageIndex() + 1),

							String.valueOf(page.getMaxRowCount()) })
					+ sortString + "\">下一页</a>");
			pagination.append(separator);
			pagination.append("<a href=\""
					+ URLUtils.addQueryString(url, new String[] { "pageIndex" +

					page.getId(), "maxRowCount" + page.getId() }, new String[] {
							String.valueOf(page.getMaxPageIndex()),

							String.valueOf(page.getMaxRowCount()) })
					+ sortString + "\">尾页</a>");
			pagination.append(separator);
		} else {
			pagination.append("<font color='gray'>下一页</font>");
			pagination.append(separator);
			pagination.append("<font color='gray'>尾页</font>");
			pagination.append(separator);
		}

		if (needJump) {
			// pagination
			// .append("第 <input id=\"targetPageIndex\" type=\"text\" size=\"3\"
			// maxlength=\"6\" value=\""
			// + page.getPageIndex()
			// + "\" onfocus=\"this.value = ''\" onkeyup=\"if(event.keyCode ==
			// 13)
			// {jumpPage();}\"> 页");

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
			pagination.append("        alert(\"请输入要跳转的页码\");\n");
			pagination.append("        return;\n");
			pagination.append("    }\n");

			// pagination.append("}\n");
			// pagination.append("</script>\n");
			// pagination.append("<input type='text' size='3' value='"
			// +page.getPageSize() +
			// "' onkeypress=");
			// pagination.append("var crd=parseInt(event.srcElement.value, 10);
			// var
			// pattern=/[0-9]*/;");
			// pagination.append("if(crd <=0 || crd > 1000) return; var keycode
			// =
			// event.keyCode;");
			// pagination.append("if(keycode == 13 || keycode == 3){if
			// (pattern.exec(event.srcElement.value) == null ");
			// pagination.append("|| isNan(crd)){return false;}");
			//            
			if (url.indexOf("?") == -1) {
				pagination.append("    location.href = \"" + url + "?pageIndex"
						+ page.getId() + "=\" + jumpPageIndex + \"&maxRowCount"
						+

						page.getId() + "=" + page.getMaxRowCount() + "\";\n");
				pagination.append("}\n");
				pagination.append("</script>\n");
			} else {
				pagination.append("    location.href = \"" + url + "&pageIndex"
						+ page.getId() + "=\" + jumpPageIndex + \"&maxRowCount"
						+ page.getId() + "=" + page.getMaxRowCount() + "\";\n");
				pagination.append("}\n");
				pagination.append("</script>\n");
			}
			/*
			 * <input type='text' size='3' value="20" OnkeyPress="var crd =
			 * parseInt(event.srcElement.value, 10); var pattern=/[0-9]/; if(crd<=0 ||
			 * crd>1000) return; var keycode = event.keyCode; if (keycode == 13 ||
			 * keycode == 3) { if(pattern.exec(event.srcElement.value) == null ||
			 * isNaN(crd)){ return false; }
			 * document.forms.mainform.ec_crd.value=crd;
			 * document.forms.mainform.ec_p.value='1';
			 * document.forms.mainform.setAttribute
			 * 
			 * ('action','basicSemesterAdmin.action');
			 * document.forms.mainform.setAttribute('method','post' );
			 * document.forms.mainform.submit()}" >条/页</td>" + "" + "
			 */
		}

		return pagination.toString();
	}
}
