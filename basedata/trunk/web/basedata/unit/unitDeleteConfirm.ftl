<html>
<head>
<title>${webAppTitle}--删除单位信息</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="${request.contextPath}/static/css/extremecomponents.css" type="text/css">
<link rel="stylesheet" href="${request.contextPath}/static/css/layout.css" type="text/css">
<script type="text/javascript" language="javascript" src="${request.contextPath}/static/js/calendarDlg.js"></script>
<script type="text/javascript" language="javascript" src="${request.contextPath}/static/js/validate.js"></script>
<script type="text/javascript" language="javascript" src="${request.contextPath}/static/js/prototype.js"></script>
<script type="text/javascript" language="javascript" src="${request.contextPath}/static/js/buffalo.js"></script>
<script>
function unitDelete(){
	window.location.href="unitAdmin-unitDelete.action?unitId=${unitDto.id?default('')}&&ec_p=${ec_p?default('')}&&ec_crd=${ec_crd?default('')}";
}
function existDelete(){
	window.location.href="unitAdmin.action?ec_p=${ec_p?default('')}&&ec_crd=${ec_crd?default('')}";
}
</script>
</head>
<body>
<table width="100%" height="100%" cellspacing="0" cellpadding="0" border="0" class="YecSpec16">
  <tr>
  	<td style="font-weight:bold;padding-left:5px;color:red;" align="left">确认要删除${unitDto.name?default('')}单位吗？</td>
  </tr>
  <tr>
  	<td align='center' valign='middle'>
  	  	
  	  <table id="deleteUnit" width="99%" height="99%" cellspacing="0" cellpadding="0" border="0" class="YecSpec10">
  	  	<tr>
  	  	  <td width="113" align="center"><img src="${request.contextPath}/static/images/wind_icon.gif" width="79" height="100" /></td>
  	  	  <td>
  	  	  	<table width="90%"  border="0" align="center" cellpadding="0" cellspacing="4">
<#if errorMessage?exists && errorMessage != "">
  	  	  	  <tr>
  	  	  	  	<td style="color:red;">·${errorMessage}</td>
  	  	  	  </tr>
</#if>  	  	  	
<#if existsUnitOnEdu?default("0") == "1">
  	  	  	  <tr>
  	  	  	  	<td style="color:red;">·单位${unitDto.name?default('')}已经在上级教育局中注册过，如果删除后，再次新增此单位进行远程注册，可能会导致存在相同的单位而无法注册成功，建议同时删除上级教育局下该单位！</td>
  	  	  	  </tr>
</#if>  	  	  	
  	  	  	  <tr>
  	  	  	  	<td style="color:red;">·删除${unitDto.name?default('')}的同时，该单位及其下属单位所有相关信息都将删除而且无法恢复！</td>
  	  	  	  </tr>
  	  	  	  <tr>
  	  	  	  	<td class="fontblue">·如果您确定要删除该单位，请选择“确定”；</td>
  	  	  	  </tr>
  	  	  	  <tr>
  	  	  	  	<td class="fontblue">·如果您不想删除该单位，请选择“取消”，当然该单位也不会删除。</td>
  	  	  	  </tr>
  	  	  	  <tr>
  	  	  	  	<td align="right">
  	  	  	  	  <table cellspacing="0" cellpadding="0" border="0">
  	  	  	  	  	<tr>
  	  	  	  	  	  <td><div class="comm_button21" onMouseover = "this.className = 'comm_button21';" onMousedown= "this.className = 'comm_button22';"onMouseout= "this.className = 'comm_button21';" onClick="unitDelete();">确定</div></td>
					  <td style="padding-left:5px"><div class="comm_button21" onMouseover = "this.className = 'comm_button21';" onMousedown= "this.className = 'comm_button22';"onMouseout= "this.className = 'comm_button21';" onClick="existDelete();">取消</div></td>
  	  	  	  	  	</tr>
  	  	  	  	  </table>
  	  	  	  	</td>
  	  	  	  </tr>
  	  	  	</table>
  	  	  </td>
  	  	</tr>
  	  </table>	  
  	</td>
  </tr>
</table>
</body>
</html>