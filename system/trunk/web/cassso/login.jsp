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
String cas_login_url = properties.getProperty("ssoservice.cas.login.url");
String service_url = properties.getProperty("ssoservice.service.url");

//如果当前没登录，首先重定向到 CAS看看是否用户已经在其他服务中登录了
boolean cas_return = false;
if (request.getSession().getAttribute("casreturn") != null) {
	request.getSession().removeAttribute("casreturn");
	cas_return = true;
}

String login_url = cas_login_url;
String service = service_url;
String next_page = "";
if (request.getParameterMap().containsKey("nextpage") && request.getParameter("nextpage").toString() != "") {
	next_page = request.getParameter("nextpage");
}

if (next_page != "") 
	service = service + "?nextpage=" + next_page;
else
	service = service + "?nextpage=login.jsp";
login_url =  login_url + "?service=" + URLEncoder.encode(service, "UTF-8");

CASRestful casrest = new CASRestful(request, cas_url, service_url);
if ((!cas_return) && casrest.isSessionAuthenticated() == false)
{
	response.sendRedirect(login_url + "&redirect=true");
	return;
}
%>

<jsp:include page="includes/header.jsp" />

<script type="text/javascript" src="_assets/sso.js"></script>

<div class="container">
	<p><a href="http://localhost/php/login.php" target="_blank">点此进入站点 php DEMO ...( <u>http://localhost/php/login.php</u>)</a></p> 
    <div id="header">
        单点登录示例 for Java
    </div>
    <div id="container">
        <%
        //你也可以用你自己的对用户 session 的判断方法
        if (casrest.isSessionAuthenticated() && (request.getParameter("force") == null)) {
        %>
        <div id="userinfo" name="userinfo" style="padding: 10px">
            <span id="outPut">您已成功登录：</span>
            <hr size="1"/>
            <span class="tbl">账号： <%=casrest.getCurrentUser() %><br/>
            TGT： <%=casrest.getTicketGrantingTicket() %><br/>
            ST： <%=casrest.getServiceTicket() %></span>
            
            <p>&nbsp;</p>
            <p><input type="button" id="logout" name="logout" value="点此注销" />
            </p>
        </div>
        <%
		} 
		else
		{
		%>
        <form action="#">
        <div class="tbl" id="loginform" name="loginform">
            <table width="100%" border="0" cellpadding="0" cellspacing="5">
                <tr>
                    <td>&nbsp;</td>
                    <td>&nbsp;</td>
                </tr>
                <tr>
                    <td width="200">
                        <div align="right">用户名：</div>
                    </td>
                    <td>
                        <input name="username" type="text" id="username">
                    </td>
                </tr>
                <tr>
                    <td width="200">
                        <div align="right">密码：</div>
                    </td>
                    <td>
                        <input name="password" type="password" id="password">
                    </td>
                </tr>
                <tr>
                    <td>&nbsp;</td>
                    <td>&nbsp;</td>
                </tr>
                <tr>
                    <td>&nbsp;</td>
                    <td>
                        <input name="btnLogin" type="button" id="btnLogin" value="登录(&amp;L)">
                    </td>
                </tr>
                <tr>
                    <td width="200">&nbsp;</td>
                    <td></td>
                </tr>
            </table>
        </div>
        </form>
        <%
		} 
        %>
        <p style="padding-left: 10px;"><a href="index.jsp">返回>></a></p>
    </div>
    <script type="text/javascript">

        $(function () {
        	var edusso = new EduSSO("<%=service_url %>", "<%=cas_url%>");
        	
            $("#btnLogin").click(function () {
				edusso.login('test', 'test', 'login_name', $('#username').val(), $('#password').val(), function(result){
					if (result && result.result != "success"){
						alert($.toJSON(result));
					} else {
						location.href="<%=next_page%>";
					}					
				});
            });

            $("#logout").click(function () {
            	edusso.logout(function(result) {
					if (result && result.result != "success"){
						alert($.toJSON(result));
					} else {
						location.reload();
					}
                } );
            });

            $("#relogin").click(function () {
            	location.href = "login.jsp?force=true"
            });
            
        });

    </script>
</div>
<jsp:include page="includes/footer.jsp" />