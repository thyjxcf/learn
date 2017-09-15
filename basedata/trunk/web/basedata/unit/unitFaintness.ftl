<#assign ec=JspTaglibs["/WEB-INF/tld/extremecomponents.tld"]>
<#import "../../common/htmlcomponent.ftl" as common>
<html>
<head>
<title>${webAppTitle}--单位查询--${faintnessName}</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="${request.contextPath}/static/css/extremecomponents.css" type="text/css">
<link rel="stylesheet" href="${request.contextPath}/static/css/layout.css" type="text/css">
<script>
function clickItem(val){
	window.returnValue=val;
	window.close();
}
</script>
</head>
<body>
<table height="100%" width="100%" cellspacing="0" cellpadding="0" border="0" class="YecSpec">
  <tr>
  	<td class="send_titlefont" >名称符合&nbsp;<font color="red">${faintnessName?default('')}</font>&nbsp;的单位有：</td>
  </tr>
  <tr>
  	<td bgcolor="#FCFFFF" height="100%" valign="top">
  	<div class="content_div">
  	  <table cellspacing="1" cellpadding="1" border="0" width="100%">
  	  	<#list listOfUnitDto as unit>
  	  	  <tr>
  	  	  	<td class="send_font_no_width" style="text-align:center;" width="100%"><a href="" onclick="clickItem('${unit.id?default('')}');">${unit.name?replace(faintnessName,'<font color="red">'+faintnessName+'</font>')}</a></td>
  	  	  </tr>
  	  	</#list>
  	  </table>
  	</div>
  	</td>
  </tr>
  <tr><td bgcolor="#C2CDF7" height="1"></td></tr>
  <tr><td bgcolor="#ffffff" height="1"></td></tr>
  <tr>
  	<td bgcolor="#C2CDF7" height="32" class="padding_left4">
  	  <table>
	  <tr>
		<td><div class="comm_button21" onMouseover = "this.className = 'comm_button21';" onMousedown= "this.className = 'comm_button22';"onMouseout= "this.className = 'comm_button21';" onClick="window.close();window.returnValue=null;">返回</div></td>
	  </tr>
	  </table>
	</td>
  </tr>
</table>
</body>
</html>