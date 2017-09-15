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

<jsp:include page="includes/header.jsp" />

<div class="container">
  <div id="header"> 单点登录示例 for Java
    （首页）</div>
  <div id="container">
    <div class="lable">
      <p>欢迎使用单点登录示例 for Java，点击下面相应的链接进行测试。</p>
    </div>
    <div class="tbl" id="userinfo" name="userinfo" style="padding: 10px">
      <p><a href="protected.jsp">点此进入受保护界面&gt;&gt;</a><a href="#"></a></p>
      <p><a href="login.jsp">点此进入登录界面&gt;&gt;</a><a href="#"></a></p>
    </div>
    <div id="userinfo" name="userinfo">
      <p align="center">
      <jsp:include page="includes/userinfo.jsp" />
      </p>
      <p>&nbsp;</p>
    </div>
  </div>
</div>

<jsp:include page="includes/footer.jsp" />