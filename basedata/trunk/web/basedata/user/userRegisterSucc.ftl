<#import "/common/product.ftl" as productmacro>
<head>
<title>${webAppTitle}--用户注册成功</title>
<link rel="stylesheet" href="${request.contextPath}/static/css/extremecomponents.css" type="text/css">
<link rel="stylesheet" href="${request.contextPath}/static/css/layout.css" type="text/css">
</head>
<body style="background-color: #EBEEFF;">
<table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
<@productmacro.logPic />
<tr><td valign="top" height="100%"><table width="100%" height="100%" border="0" cellpadding="0" cellspacing="0">
<tr>
   <td>
	<table width="100%" height="100%" border="0" align="center" cellpadding="0" cellspacing="8">
	<tr>
    <td width="40%" valign="top">
<table width="100%" height="100%" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td width="103"><table width="103" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td width="103" align="center" valign="bottom" class="blue_bg">新用户注册</td>
      </tr>
    </table></td>
    <td width="413" class="border_black2" >&nbsp;</td>
  </tr>
  <tr>
    <td height="100%" colspan="2" valign="top" class="border_line" style="background-color:#FFFFFF;">
      <table width="93%" border="0" cellspacing="0" cellpadding="0" style="padding-left:5px;">
        <tr>
          <td>
		    <table width="100%" height="150" valign="middle" border="0"cellpadding="0" cellspacing="0">
	        <tr>
	          <td align="center">恭喜您注册成功，
	         	<#switch registerActive?if_exists> 
	         		<#case FPF_USER_REGISTER_ACTIVE_ADMIN>请等待管理员审核<#break>
	         		<#case FPF_USER_REGISTER_ACTIVE_EMAIL>请查收邮件点击激活该账号<#break>
	         		<#case FPF_USER_REGISTER_ACTIVE_IMM>该账号已可正常使用<#break>
	         	</#switch>
	          </td>
	        </tr>
	        <tr>
	          <td align="center"><a href="${request.contextPath}/basedata/account/login.action">如果您的浏览器没有自动跳转，请点击此处跳转</a></td>
	        </tr>
	        <tr>
	          <td>&nbsp;</td>
	        </tr>
	        </table>
    	  </td>
    	</tr>
    	<tr style="height:0px;"><td id="actionTip"></td></tr>
    	<tr><td>&nbsp;</td></tr>    	     	
      </table>      
    </td></tr>
</table>
</td>
  </tr>
  </table></td></tr></table></td></tr>
  <@productmacro.copyright />
</table>
<script>
	window.setTimeout("window.location.href='${request.contextPath}/basedata/account/login.action'",3000);
</script>
</body>
</html>

