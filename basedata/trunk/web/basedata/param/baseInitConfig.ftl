<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<#include "/common/public.ftl">
<head>
<title>${webAppTitle}--Base基础信息设置</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" language="javascript" src="${request.contextPath}/static/js/validate.js"></script>
<script type="text/javascript" language="javascript" src="${request.contextPath}/system/js/systeminiValidate.js"></script>
</head>
<body>
<script>
function submitForm(){
	var validate = checkform();
  	if (!validate) return;
  	showSaveTip();
  	document.getElementById("configform").submit();
}
function checkform(){
	<#list baseOptionArray as x>
		<#if x.validateJS?exists>if(!${x.validateJS?default()}(document.getElementById('baseOptionArray[${x_index}].nowValue'),'${x.name?default('')}')){return false;}	
		</#if>
	</#list>
	return true;
}
function setDefaultValue(index){
	var nameValue=document.getElementById('baseOptionArray['+index+'].name');
	if(window.confirm('确定要将'+nameValue.value+'还原默认值？')){
		var defaultValue=document.getElementById('baseOptionArray['+index+'].defaultValue');
		var nowValue=document.getElementById('baseOptionArray['+index+'].nowValue');
		nowValue.value=defaultValue.value;
		submitForm();
	}
}
</script>
<form action="platformInfoAdmin-systemIniConfigSave.action?moduleID=${moduleID?default('')}" method="POST" name="configform" id="configform" >
  	<div class="table-content">
  	  <table  width="100%" height="100%"  border="0" class="table3 table-vline">
  	  	<#list baseOptionArray as x>
  	  	<tr>
  	  	  <td  width="170">${x.name?default('')}：</td>
  	  	  <td>
  	  	  	  <input name="name" id="baseOptionArray[${x_index}].name" type="hidden" value="${x.name?default('')?trim}">
  	  	  	  <input name="optionCode" type="hidden" value="${x.optionCode?default('')?trim}">
  	  	  	  <input name="defaultValue" id="baseOptionArray[${x_index}].defaultValue" type="hidden" value="${x.defaultValue?default('')?trim}">
  	  	  	  <input name="nowValue" id="baseOptionArray[${x_index}].nowValue" class="input-txt300" type="text" value="${x.nowValue?default('')?trim}" size="25" maxlength="255">
  	  	  </td>
  	  	  <td >${x.description?default('')?trim}
  	  	  	<input name="description" id="baseOptionArray[${x_index}].description" type="hidden" value="${x.description?default('')}">
  	  	  </td>
  	  	  <td width="100">
			<span class="input-btn2" title="还原默认值"><button type="button" onclick="setDefaultValue(${x_index});">还原默认值</button></span>  	  	  		  	  	  
  	  	  </td>
  	  	</tr>
  	  	</#list>  	  	
  	  </table>
  	</div>
  <div class="table1-bt t-center">
  	<span class="input-btn1 save-btn" onclick="javascript:submitForm();"><button type="button">保存</button></span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	<span class="input-btn1 closeDiv"><button type="reset">重置</button></span>
</div>
</form>
<script>
jQuery(function(){
	$t_c_width=jQuery(".table-content").width();
	$t_c_width=$t_c_width-16;
	jQuery(".table-content").height(jQuery(".mainFrame", window.parent.parent.document).height()  - jQuery('.table1-bt').height()  -5)
	jQuery(".table-header").width($t_c_width);
})
</script>
</body>
</html>
