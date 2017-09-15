<%--
/**
 * Example for a using CAS login
 * Java 中此文件仅用于处理客户端登录时间，单点登出可由 filter 完成
 */
--%>

<%@ page import="java.util.Properties" %>
<%@ page import="java.io.*" %>
<%@ page import="java.net.URLEncoder" %>
<%@ page import="org.jasig.cas.client.rest.CASRestful" %>
<%@ page import="org.jasig.cas.client.util.AbstractCasFilter" %>
<%@ page import="org.jasig.cas.client.validation.Assertion" %>

<%!
private void returnResult(String result, final HttpServletRequest request, final HttpServletResponse response)
{
	try
	{
		String nextPage = "";
		if (request.getParameterMap().containsKey("nextpage") && request.getParameter("nextpage").toString() != "") {
			nextPage = request.getParameter("nextpage").toString();
		}
		
		if (nextPage != "") {
			if (result != "success") {
				request.getSession().setAttribute("casreturn", true);
				nextPage = "login.jsp?nextpage=" + nextPage;
			}
			response.sendRedirect(nextPage);
			return;
		}
			
		//header('Content-Type: text/html; charset=utf-8');
		response.getWriter().write(result);
	}
	catch(Exception ex){
	}
}
%>

<%
Properties properties = CASRestful.getProperties();
String cas_url = properties.getProperty("ssoservice.cas.url");
String cas_login_url = properties.getProperty("ssoservice.cas.login.url");
String service_url = properties.getProperty("ssoservice.service.url");

String service = service_url;
String nextPage = "";
if (request.getParameterMap().containsKey("nextpage") && request.getParameter("nextpage").toString() != "") {
	nextPage = request.getParameter("nextpage").toString();
}
if (nextPage != "") {
	service = service + "?nextpage=" + nextPage;
}

CASRestful casrest = new CASRestful(request, cas_url, service);

String result = "";
String action = "";
if (request.getParameterMap().containsKey("action")) {
	action = request.getParameter("action").toString();
}

String tgt = "";
String st = "";
if (request.getParameterMap().containsKey("tgt")) {
	tgt = request.getParameter("tgt").toString();
}

if (request.getParameterMap().containsKey("ticket")) {
	st = request.getParameter("ticket").toString();
}
if (st != "" || tgt != "") {
	action = "login";
}

// User login
if (action.equals("login")) {
	
	boolean authResult = false;
	if (tgt != "")
	{
		//注意!! 通过 Restful 接口使用 TGT来登录在 CAS 端是没有保存 Cookie 的，
		//  也就是说登录成功后，如果需要访问其他服务，需要每次把 TGT 或 用 TGT 生成一个 ST 作为参数传给相应的服务。
		authResult = casrest.authenticateWithTGT(tgt);
	}
	else if (st != "")
	{
		authResult = casrest.authenticateWithST(st);
	}
	else
	{
		if (!(request.getParameter("username") != null && request.getParameter("password") != null))
		{
			result = "username and password must be provided.";
			returnResult(result, request, response);
			return;
		}
		
		String username = request.getParameter("username").toString();
		String password = request.getParameter("password").toString();
		
		authResult = casrest.authenticate(username, password);
	}
	
	if (authResult == false) {
		returnResult("Invalid username or password", request, response);
		return;
	}
		
	//////////////// TODO ////////////////////
	// 登录成功，应用程序需在此处处理保留用户 session 数据
		
	result = "success";

	// Output result
	returnResult(result, request, response);	
	return;
}

// User logout
if (action.equals("logout")) {

	tgt = casrest.getTicketGrantingTicket();
	if (request.getParameterMap().containsKey("tgt") && request.getParameter("tgt").toString() != "") {
		tgt = request.getParameter("tgt").toString();
	}

	//这步操作仅用于 Restful 接口调用时向 CAS 登出
	casrest.logout(tgt);
	
	//////////////// TODO ////////////////////
	// 应用程序需在此处清除用户 session 数据 

	result = "success";
	
	// Output result
	returnResult(result, request, response);
	return;
}

returnResult("nop", request, response);
%>
