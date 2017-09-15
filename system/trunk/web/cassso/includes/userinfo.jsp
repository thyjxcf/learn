<%--
/**
 * Example for a using CAS login
 */
--%>

<%@ page import="java.util.Properties" %>
<%@ page import="java.io.*" %>
<%@ page import="java.net.URLEncoder" %>
<%@ page import="org.jasig.cas.client.rest.CASRestful" %>

<%
Properties properties = CASRestful.getProperties();
String cas_url = properties.getProperty("ssoservice.cas.url");
String service_url = properties.getProperty("ssoservice.service.url");

CASRestful restfull = new CASRestful(request, cas_url, service_url);
if (restfull.isSessionAuthenticated())
{
	response.getWriter().write("当前登录用户信息！\r\n");
	response.getWriter().write("<p>\r\n");
	response.getWriter().write("TGT：" + restfull.getTicketGrantingTicket() + "<br/>");
	response.getWriter().write("ST：" + restfull.getServiceTicket() + "<br/>");
	response.getWriter().write("用户名：" + restfull.getCurrentUser() + "<br/>");
}
else
{
	response.getWriter().write("当前尚未登录！");
}

%>