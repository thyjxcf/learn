<%--
/**
 * Example for a using CAS login
 * jhzhang: 我从 PHP 代码改写的，仅供参考
 */
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.Properties" %>
<%@ page import="java.io.*" %>
<%@ page import="java.net.URLEncoder" %>
<%@ page import="org.jasig.cas.client.rest.CASRestful" %>

<%
Properties properties = CASRestful.getProperties();
String cas_url = properties.getProperty("ssoservice.cas.url");
String service_url = properties.getProperty("ssoservice.service.url");

String serviceTicket = request.getParameter("st");

CASRestful restfull = new CASRestful(request, cas_url, service_url);
if (!restfull.authenticateWithST(serviceTicket))
{
	response.sendRedirect("login.jsp");
	return;
}
%>

<jsp:include page="includes/header.jsp" />

<div class="container">
  <div id="header"> 单点登录示例 for Java
    （受保护页面）</div>
  <div id="container">
    <div class="lable">
      <p>欢迎使用单点登录示例 for Java，这是受保护页面！</p>
    </div>
    <div id="userinfo" name="userinfo">
      <p align="center">
      <jsp:include page="includes/userinfo.jsp" />
      </p>
      <p style="padding-left: 10px;"><a href="index.jsp">返回>></a></p>
    </div>
  </div>
</div>

<jsp:include page="includes/footer.jsp" />