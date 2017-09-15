<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>错误信息</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<style type=text/css>input {
	font-size: 12px
}
td {
	font-size: 12px
}
.p2 {
	font-size: 12px
}

</style>
</head>
<body>
<p align="center">　</p>

<table cellspacing="0" cellpadding="0" width="600" align="center" border="0">
	<tbody>
	<tr>
		<td valign="top"><div align="center"><img height="211" src="${request.contextPath}/static/common/images/error.gif" width="329">
		<table cellspacing="0" cellpadding="4" width="100%" border="1">
			<tbody>
			<tr>
				<td><font class="p2">&nbsp;&nbsp;&nbsp; <font color="#ff0000">
				<img height="13" src="${request.contextPath}/static/common/images/emessage.gif" width="12">
				&nbsp;错误信息：${exception?default("")}
				</font></font></td>
			</tr>
			<tr>
				<td height="4"></td>
			<tr>
			<tr>
				<td><font class="p2">&nbsp;&nbsp;&nbsp; <font color="#ff0000">
				&nbsp;&nbsp;&nbsp;链接地址：${request.requestURL}
				</font></font></td>
			</tr>
			<tr>
				<td height="4"></td>
			<tr height="300">
				<td>
				<font color="#ff0000">详细错误：</font>
				<div style="overflow-y:auto;overflow-x:auto;width:100%;height:95%">
				<pre>${exceptionStack?default("")}</pre>
				  <#if action.hasActionErrors()>
				  </br>
					<#list actionErrors as x>
				         ${x}
				     </#list>
				  </#if>				
				</div></td>
			</tr>
			</tbody>
		</table>
		</div></td>
	</tr>
	<tr>
		<td height="5"></td>
	</tr>
	<tr>
		
	</tr>
	</tbody>
</table>
</body>
</html>