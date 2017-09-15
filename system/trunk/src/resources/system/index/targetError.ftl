<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${webAppTitle}--错误信息</title>
<link rel="stylesheet" href="${request.contextPath}/static/css/layout.css" type="text/css">
</head>
<body>
<#if action.hasActionErrors()>
	 <br><br><br><br><br>
	 <table width="307" border="0" align="center" cellpadding="0" cellspacing="0">
	  <tr>
	    <td height="1" class="border_black2">&nbsp;</td>
	  </tr>
	  <tr>
	    <td height="200" valign="middle" class="border_black"><table width="100%" border="0" cellspacing="0" cellpadding="0">
	      <tr>
	        <td width="38%" align="center"><img src="${request.contextPath}/static/images/wind_icon2.gif" width="110" height="100"></td>
	        <td width="62%">
	          <#list actionErrors as x>
		      	${x?default("")}
		      </#list>
	        </td>
	      </tr>
	    </table></td></tr>
	</table>   
</#if>  
</body>
</html>


