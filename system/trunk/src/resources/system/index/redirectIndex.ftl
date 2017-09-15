<html>
  <head>
  	<link rel="stylesheet" href="${request.contextPath}/static/css/layout.css" type="text/css">
    <title>正在转接</title>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    <!-- <META HTTP-EQUIV="REFRESH" CONTENT="0; URL=system/login.action"> -->
<script language="JavaScript">
	window.location.href="${redirectUrl?default(action.getText('eis.login.postfix'))}";
</script>

  </head>
  <body>
正在转向……，请稍后<br>
如果无法转向，请点击<a href="${redirectUrl?default(action.getText('eis.login.postfix'))}">这里</a>进入系统
  </body>
</html>
