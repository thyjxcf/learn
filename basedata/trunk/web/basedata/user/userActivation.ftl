<#import "/common/product.ftl" as productmacro>
<head>
<title>用户激活<#if activationResult==1>成功<#else>失败</#if></title>
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
        <td width="103" align="center" valign="bottom" class="blue_bg">用户激活</td>
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
	          <td align="center">
	         	<#if activationResult==1>恭喜您激活账号成功，点击以下链接登录系统！
	          	<#elseif activationResult==-1>${activationMessage?if_exists}
	          	</#if>
	          </td>
	        </tr>
	        <#if activationResult==1>
	        <tr>
	          <td align="center"><a href="${request.contextPath}/basedata/account/login.action">如果您的浏览器没有自动跳转，请点击此处跳转</a></td>
	        </tr>
	        </#if>
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

