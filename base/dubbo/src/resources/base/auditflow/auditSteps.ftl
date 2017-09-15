<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>异动审核进度</title>
</head>
<#macro showStepsTemplate steps>
	<ul class="audit">
		<li><p class="first">开始</p></li>
		<li class="jt1"></li>	    
	<#list showSteps as step>
		<li <#if step_index gt 2>class="f-right"</#if>>
	    	<#if step.auditOrder==-1>
	    	<p class="bg bg-err">${step.roleName}</p>
	    	<#else>
	    	<p class="bg">${step.roleName}</p>
	    	</#if>
	    	<p class="name">${step.auditUsername?default('')}</p>
	        <@resultTip result=step.status order=step.auditOrder />
	    </li>
	    <#if !step_has_next>
	    	<#assign hasNext='false' />
	    </#if>
	    <@nextArrow index=step_index hasMore="${hasNext?default('true')}" />
	</#list>
		<li <#if showSteps?size gt 2>class="f-right"<#else>class="f-left"</#if>><p class="last">结束</p></li>
	</ul>
</#macro>

<#macro resultTip result order>
	<#if order ==-1>
	<img src="${request.contextPath}/static/images/audit_8.gif" class="hover-img" /><br />
	<span class="tips">审核未通过</span>
	<#else>
		<#if result==1>
		<img src="${request.contextPath}/static/images/audit_7.gif" class="hover-img" /><br />
		<span class="tips">审核中</span>
		<#else>
		<img src="${request.contextPath}/static/images/audit_6.gif" class="hover-img" /><br />
		<span class="tips">审核通过</span>
		</#if>
	</#if>
</#macro>

<#--下个箭头-->
<#macro nextArrow index hasMore='true'>
	<#if index lt 2>
	<li class="jt1"></li>
	<#elseif index==2>
		<#if hasMore=='true'>
		<li class="jt2 f-left"></li>
		<#else>
		<li class="jt1"></li>
		</#if>
	<#else>
	<li class="jt3 f-right"></li>
	</#if>
</#macro>

<body>
	<#if showSteps??>
		<@showStepsTemplate steps= showSteps/>
	<#else>
		<table id="stepTable" border="0">
			<tr><td width="100%" height="100%" align="center" valign="middle" class="b12">
			<img src="${request.contextPath}/static/images/tips.gif" class="t-img" />
			没有步骤
			</td></tr>
		</table>
	</#if>
	<div class="table1-bt t-center"><span class="input-btn1 close-btn" onclick="doClose();"><button type="button">关闭</button></span></div>
<script>
<#if showSteps??>
<@common.scrollHeight scrollName=".audit" totalHeight="jQuery('#panelWindow').height()" minusHeight="jQuery('.table1-bt').height()" trimming="0"/>
<#else>
jQuery(document).ready(function(){
	jQuery('#stepTable').height(jQuery('#panelWindow').height()-jQuery('.table1-bt').height());
	jQuery('#stepTable').width(jQuery('#panelWindow').width());
})
</#if>
function doClose() {
  	closeDiv("#panelWindow");
}
</script>
</body>
</html>
