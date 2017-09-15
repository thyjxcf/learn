<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<#include "/common/css.ftl">
<#include "/common/js.ftl">
<#include "/common/public.ftl">
<#import "../../common/htmlcomponent.ftl" as common />
<#assign ec=JspTaglibs["/WEB-INF/tld/extremecomponents.tld"]>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>异动审核步骤设定管理</title>
<style type="text/css"> </style>
<script>
   function gotoPage(auditType) {
   		var radios = document.getElementsByName("selectNode");
   		var radioValue;
		for( var i = 0; i < radios.length; i++ ){
			if ( radios[i].checked ){
				radioValue = radios[i].value;
			}
		}
//   		var url = "showAuditFlowList.action?selectType="+radioValue+"&auditType="+auditType+"&section=1";
   		var formfl = document.getElementById("flowAction");
   		document.getElementById("selectType").value=radioValue;
   		document.getElementById("auditType").value=auditType;
   		<#if showSections>
   		document.getElementById("section").value=1;
   		</#if>
   		formfl.submit();
// 		window.document.location.href=url;
   }
   function doType(){
   		var radios = document.getElementsByName("selectNode");
		for( var i = 0; i < radios.length; i++ ){
			if ( radios[i].checked ){
				document.getElementById("InputAll_"+radios[i].value).style.display = "";
			}else{
				document.getElementById("InputAll_"+radios[i].value).style.display = "none";
			}
		}
   }
</script>
</head>
<body>
<form name="flowAction" id="flowAction" action="showAuditFlowList.action" method="post">
<input type="hidden" name="selectType" id="selectType" value="">
<input type="hidden" name="regionLevel" id="regionLevel"  value="${regionLevel}">
<input type="hidden" name="nowRegionLevel" id="nowRegionLevel"  value="${nowRegionLevel}">
<input type="hidden" name="auditType" id="auditType" value="">
<input type="hidden" name="section" id="section" value="${section!}">
<input type="hidden" name="businessType" id="businessType" value="${businessType}">
<input type="hidden" name="showFlowType" id="showFlowType" value="${showFlowType?string}">
<input type="hidden" name="showSections" id="showSections" value="${showSections?string}">
<input type="hidden" name="schConfirm" id="schConfirm" value="${schConfirm?string}">
<input type="hidden" name="showDefault" id="showDefault" value="${showDefault?string}">
<input type="hidden" name="outerframe" id="outerframe" value="${outerframe}">
</form>
<div class="head-tt " <#if !showFlowType>style="display:none;"</#if>>
    <div class="tt-le">
    	<input type="radio" name="selectNode" value="1" id="radio_1"  <#if selectType == "1"> checked </#if> onclick="doType();"> 转学异动类型</input>
		<input type="radio" name="selectNode" value="2" id="radio_2"  <#if selectType == "2"> checked </#if> onclick="doType();">其他异动类型</input>
    </div>
</div>
请选择相关的学校类型进行查看或者修改    
    	<table id="InputAll_1" width="600" cellpadding="0" cellspacing="0" class="table1 table-vline" <#if selectType == "2">  style="display:none;" </#if>>

		<#if auditTypeList?exists>
			<#list auditTypeList as x>
			<#if x[1] == "0">
				<tr class="Title">
					<td>${x[0]?default("")}</td>
					<td>${x[2]?default("")}</td>
					<#if x[4]?exists>
					<td>${x[4]?default("")}</td>
					</#if>
					<#if x[6]?exists>
					<td>${x[6]?default("")}</td>
					</#if>
					<#if x[8]?exists>
					<td>${x[8]?default("")}</td>
					</#if>
				</tr>
			<#else>
				<tr class="Content">
					<#if x[1] == "-1">
					<td>${x[0]?default("")}</td>
					<#else>
					<td><a href="javascript:gotoPage('${x[1]?default("")}');">${x[0]?default("")}</a></td>
					</#if>
					<td><a href="javascript:gotoPage('${x[3]?default("")}');">${x[2]?default("")}</a></td>
					<#if x[4]?exists>
					<td><a href="javascript:gotoPage('${x[5]?default("")}');">${x[4]?default("")}</a></td>
					</#if>
					<#if x[6]?exists>
					<td><a href="javascript:gotoPage('${x[7]?default("")}');">${x[6]?default("")}</a></td>
					</#if>
					<#if x[8]?exists>
					<td><a href="javascript:gotoPage('${x[9]?default("")}');">${x[8]?default("")}</a></td>
					</#if>
				</tr>
			</#if>

			</#list>
		</#if>
		
	</table>
    </div>
	</div>
	
	<table id="InputAll_2" width="600" cellpadding="0" cellspacing="0" class="table1 table-vline" <#if selectType == "1">  style="display:none;" </#if>>
		<#if auditTypeList?exists>
			<#list auditTypeList as x>
				<#if x[1] == "0">
					<tr class="Content">
						<td><a href="javascript:gotoPage('${x[1]?default("")}');">${x[0]?default("")}</a></td>
						<td><a href="javascript:gotoPage('${x[3]?default("")}');">${x[2]?default("")}</a></td>
						<#if x[4]?exists>
						<td><a href="javascript:gotoPage('${x[5]?default("")}');">${x[4]?default("")}</a></td>
						</#if>
						<#if x[6]?exists>
						<td><a href="javascript:gotoPage('${x[7]?default("")}');">${x[6]?default("")}</a></td>
						</#if>
						<#if x[8]?exists>
						<td><a href="javascript:gotoPage('${x[9]?default("")}');">${x[8]?default("")}</a></td>
						</#if>
					</tr>
				</#if>
			</#list>
		</#if>
	</table>
	<script>
jQuery(document).ready(function(){
	$t_c_width=jQuery(".table-content").width();
	$t_c_width=$t_c_width-16;
	//jQuery("#InputAll_1").height(jQuery(".mainFrame", window.parent.document).height() - jQuery('.head-tt').height())
	//jQuery("#InputAll_2").height(jQuery(".mainFrame", window.parent.document).height()-jQuery('.head-tt').height())
	jQuery(".table-header").width($t_c_width);
})	
	</script>
</body>	
</html>
