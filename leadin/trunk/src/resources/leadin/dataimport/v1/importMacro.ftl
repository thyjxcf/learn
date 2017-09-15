<#include "/leadin/dataimport/dataimport.ftl"/> 
<#-- 参数信息 -->
<#macro displayInfoMacro>
<script>
function init(){
	alert("请填写初始化方法");
}
window.attachEvent("onload", init);

function validateTemplate(){
	alert("请填写模板下载时需要进行的验证");	
	return true;	
}

function validateImport(){
	alert("请填写导入时需要进行的验证");	
	return true;	
}

</script>



</#macro>

<#-- 说明信息 -->
<#macro importDescriptionMacro>
1、请填写说明信息。<br>
</#macro>

<#-- 按钮栏 -->
<#macro buttonBar>
1、一些独有的操作：如删除等
</#macro>
<#-- 下方的说明信息 -->
<#macro remark>

</#macro>
