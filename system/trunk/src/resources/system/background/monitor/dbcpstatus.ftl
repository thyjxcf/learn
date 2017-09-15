<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>DBCP的状态</title>
</head>

<body>
<ul>
<#list dbcpStatus as b>
  <li>${b}
</#list>
</ul>  


<br>
<input type="button" value="刷新" Onclick="javascript:window.location.reload();">
</body>
</html>
