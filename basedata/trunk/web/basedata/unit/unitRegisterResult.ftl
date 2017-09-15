<#assign ec=JspTaglibs["/WEB-INF/tld/extremecomponents.tld"]>
<#import "../../common/commonmacro.ftl" as common>
<html>
<head>
<title>${webAppTitle}--单位注册结果</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="${request.contextPath}/static/css/extremecomponents.css" type="text/css">
<link rel="stylesheet" href="${request.contextPath}/static/css/layout.css" type="text/css">
<script>
function cancel(){
	document.forms.form1.action="unitAdmin-remoteRegister.action";
	document.forms.form1.method="POST";
	document.forms.form1.submit();
}
</script>
</head>
<body>
<form name="form1" action="" method="POST">
<table width="100%" height="100%" cellspacing="0" cellpadding="0" border="0" class="YecSpec16">
  <tr><td class="send_titlefont" height="20px">远程注册单位结果</td></tr>
  <tr>
  	<td valign="top">
  	  <div class="content_div">	
  	  <table align="center" width="80%" cellspacing="0" cellpadding="0" border="0">  		
  		<#if errorList?exists>
  		<tr><td>
  		  <table width="100%" cellspacing="0" cellpadding="0" border="0">
  		  	<tr>
  		  	  <td style="color:red;">以下单位有错误，导致本次注册无效：
  		  	  	<#list model.arrayIds?if_exists as id>
  		  	  	  <input name="unitIds" value="${id?default('')}" type="hidden">
  		  	  	</#list>
  		  	  </td>  		  	  
  		  	</tr>
  		  	<tr>
  		  	  <td bgcolor="#FCFFFF">
  		  	  	<table width="100%" height="100%" cellspacing="1" cellpadding="1" border="0">
  		  	  	  <#list errorList?if_exists as error>
  		  	  	  	<tr>
  		  	  	  	  <#list error?if_exists as er>
  		  	  	  	  	<td class="send_padding_no_width">${er?default('')}</td>
  		  	  	  	  </#list>
  		  	  	  	</tr>
  		  	  	  </#list>
  		  	  	</table>
  		  	  </td>
  		  	</tr>
  		  </table>
  		</td></tr>
  		<#else>
  		<#if succUnitList?exists>
  		<tr><td>
  		  <table width="100%" cellspacing="0" cellpadding="0" border="0">
  		  	<tr>
  		  	  <td style="color:#29248A;">远程注册以下单位成功：</td>  		  	  
  		  	</tr>
  		  	<tr>
  		  	  <td bgcolor="#FCFFFF">
  		  	  	<table width="100%" cellspacing="1" cellpadding="1" border="0">
  		  	  	  <#list succUnitList as succ>
  		  	  	  	<tr><td class="send_padding_no_width">${succ.name?default('')}</td></tr>
  		  	  	  </#list>
  		  	  	</table>
  		  	  </td>
  		  	</tr>
  		  </table>
  		</td></tr>
  		</#if>
  		<#if modifyUnitList?exists>
  		<tr><td>
  		  <table width="100%" cellspacing="0" cellpadding="0" border="0">
  		  	<tr>
  		  	  <td style="color:#29248A">修改以下已注册单位：</td>  		  	  
  		  	</tr>
  		  	<tr>
  		  	  <td bgcolor="#FCFFFF">
  		  	  	<table width="100%" height="100%" cellspacing="1" cellpadding="1" border="0">
  		  	  	  <#list modifyUnitList as modify>
  		  	  	  	<tr><td class="send_padding_no_width">${modify.name?default('')}</td></tr>
  		  	  	  </#list>
  		  	  	</table>
  		  	  </td>
  		  	</tr>
  		  </table>
  		</td></tr>
  		</#if>
  		</#if>  		
  	  </table>
  	  </div>
  	</td>
  </tr>
  <tr><td height="12" style="padding-left:5px;color:#29248A;"><font color="red">*</font>单位远程注册成功。</td></tr>
  <tr><td bgcolor="#C2CDF7" height="1"></td></tr>
  <tr><td bgcolor="#ffffff" height="1"></td></tr>
  <tr>
  	<td bgcolor="#C2CDF7" height="32" class="padding_left4"><table width="219" border="0" cellspacing="0" cellpadding="0">
      <tr>
		<td width="60" align="center"><label>
		  <input type="button" name="Submit" value="返回"class="del_button1" tabindex="16" onmouseover = "this.className = 'del_button3';" onmousedown= "this.className = 'del_button2';" onmouseout = "this.className = 'del_button1';" onClick="cancel();"/>
        </label></td>		
	  </tr>
	</table></td>
  </tr>
</table>
</form>
</body>
</html>
