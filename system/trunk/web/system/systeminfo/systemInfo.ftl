<#assign ec=JspTaglibs["/WEB-INF/tld/extremecomponents.tld"]>
<#import "../../common/commonmacro.ftl" as common>
<html>
<head>
<title>${webAppTitle}--系统版本信息</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="${request.contextPath}/static/css/extremecomponents.css" type="text/css">
<link rel="stylesheet" href="${request.contextPath}/static/css/layout.css" type="text/css">
</head>                
<body>
  <table border="0" cellspacing="0" cellpadding="0" width="100%" height="100%" class="YecSpec_background" align="center" valign="middle">
  	<tr>
  	  <td height="30" bgcolor="#AABBFF" class="padding_right2">&nbsp;</td>
  	</tr>
  	<tr height="30%"><td>
  	  <table border="0" cellspacing="0" width="90%" align="center">
  	  	<tr><td class="send_titlefont">系统版本信息：</td></tr>
  	  	<tr><td bgcolor="#FCFFFF">
  	  	  <table border="0" cellspacing="1" cellpadding="1" width="100%" height="100%" align="center">
  	  	  	<tr>
  	  	  	  <td align="center" width="15%" style="background:#E8EBFE;">系统名称</td>
  	  	  	  <td align="center" width="15%" style="background:#E8EBFE;">当前版本</td>
  	  	  	  <td align="center" width="15%" style="background:#E8EBFE;">创建时间</td>
  	  	  	  <td align="center" width="15%" style="background:#E8EBFE;">升级前版本</td>
  	  	  	  <td align="center" width="25%" style="background:#E8EBFE;">描述</td>
  	  	  	</tr>
  	  	  	<#if systemVersionDto?exists>
  	  	  	<tr>
  	  	  	  <td align="center" class="send_padding_no_width">${systemVersionDto.name?default('&nbsp') }</td>
  	  	  	  <td align="center" class="send_padding_no_width">${systemVersionDto.curversion?default('&nbsp') }</td>
  	  	  	  <td align="center" class="send_padding_no_width">${systemVersionDto.createdate?default('&nbsp') }</td>
  	  	  	  <td align="center" class="send_padding_no_width">${systemVersionDto.porversion?default('&nbsp') }</td>
  	  	  	  <td align="center" class="send_padding_no_width"><#if systemVersionDto.description?exists&&(systemVersionDto.description?length>10)>${systemVersionDto.description.substring(0,10)}…<#else>${systemVersionDto.description?default('')}</#if></td>
  	  	  	</tr>
  	  	  	</#if>
  	  	  </table>
  	  	</td></tr>
  	  </table>
  	</td></tr>
  	<tr height="70%"><td valign="top">
  	  <table border="0" cellspacing="0" width="90%" align="center">
  	  	<tr><td class="send_titlefont">补丁升级历史情况：</td></tr>
  	  	<tr><td bgcolor="#FCFFFF">
  	  	  <table border="0" cellspacing="1" cellpadding="1" width="100%" height="100%" align="center">
  	  	  	<tr>
  	  	  	  <td align="center" width="15%" style="background:#E8EBFE">补丁/升级包名称</td>
  	  	  	  <td align="center" width="15%" style="background:#E8EBFE">版本</td>
  	  	  	  <td align="center" width="15%" style="background:#E8EBFE">安装时间</td>
  	  	  	  <td align="center" width="15%" style="background:#E8EBFE">前版本</td>
  	  	  	  <td align="center" width="25%" style="background:#E8EBFE">描述</td>
  	  		</tr>
  	  		<#list systemPatchList as systemPatch>
  	  		<tr>
  	  	  	  <td align="center" class="send_padding_no_width">${systemPatch.patchname?default('&nbsp')}</td>
  	  	  	  <td align="center" class="send_padding_no_width">${systemPatch.patchversion?default('&nbsp')}</td>
  	  	  	  <td align="center" class="send_padding_no_width">${systemPatch.createtime?default('&nbsp')}</td>
  	  	  	  <td align="center" class="send_padding_no_width">${systemPatch.proversion?default('&nbsp')}</td>
  	  	  	  <td align="center" class="send_padding_no_width"><#if systemPatch.description?exists&&(systemPatch.description?length>10)>${systemPatch.description.substring(0,10)}…<#else>${systemPatch.description?default('&nbsp')}</#if></td>
  	  		</tr>
  	  		</#list>
  	  	  </table>  	  	  
  	  	</td></tr>			
  	  </table>
  	</td></tr>
  </table>
</body>