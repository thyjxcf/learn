<#import "/common/htmlcomponent.ftl" as htmlmacro>
<@htmlmacro.moduleDiv titleName="平台基础信息设置">
  	<input name="background" type="hidden" value="${background!}">
  	<@htmlmacro.tableList addClass="mt-15">
  	<#assign isTopUnit = action.isTopUnit()>
  		<#list systemIniDtos as x>
  	  	<tr>
  	  	  <td><span>${x.name?default('')}</span></td>
  	  	  <td>
  	  	  	<#if isTopUnit>
  	  	  	<input class="input-txt" name="name" id="${x_index}_name" type="hidden" value="${x.name?default('')?trim}">
  	  	  	<input class="input-txt" name="iniid" id="${x_index}_iniid" type="hidden" value="${x.iniid?default('')?trim}">
  	  	  	<input class="input-txt" name="defaultValue" id="${x_index}_defaultValue" type="hidden" value="${x.defaultValue?default('')?trim}">
  	  	  	<input class="input-txt" style="width:300px;" name="nowValue" maxLength="125" id="${x_index}_nowValue" type="text" value="${x.nowValue?default('')?trim}" onblur="<#if x.validateJS?exists>if(!${x.validateJS?default()}($('#${x_index}_nowValue')[0],'${x.name?default('')}')){return false;}</#if>">
  	  	  	<#else>
  	  	  	  ${x.nowValue?default('')}
  	  	  	</#if>
  	  	  </td>
  	  	  <td >${x.description?default('')?trim}</td>
  	  	  <#if isTopUnit>
  	  	  	<td width="180">
  	  	  		<a href="javascript:void(0);" id="saveOne${x_index}" onclick="saveNowValueOne(${x_index});" class="abtn-orange mr-10">保存</a>
  	  	  		<a href="javascript:void(0);" onclick="saveDefaultOne(${x_index});" class="abtn-orange mr-10">还原默认</a>
  	  	  	</td></#if>
  	  	</tr>
  	  	</#list>  
  	  </@htmlmacro.tableList>
  	<div class="t-center mt-10">
		<#if isTopUnit>
			<a href="javascript:void(0);" id="btnSaveAll" class="abtn-blue-big" onclick="javascript:saveAll();">保存所有</a>
		</#if>
	</div>
<script type="text/javascript" language="javascript" src="${request.contextPath}/system/js/systeminiValidate.js"></script>
<script>
function checkform(index){
	if(!index){
	<#list systemIniDtos as x>
		<#if x.validateJS?exists>if(!${x.validateJS?default()}($('#${x_index}_nowValue')[0],'${x.name?default('')}')){return false;}	
		</#if>
	</#list>
	}
	else{
		<#list systemIniDtos as x>
			if(index == "${x_index}"){
			<#if x.validateJS?exists>if(!${x.validateJS?default()}($('#${x_index}_nowValue')[0],'${x.name?default('')}')){return false;}	
			</#if>
			}
		</#list>
	}
	return true;
}

//设置默认值
function saveDefaultOne(index){
	var nameValue = $('#' + index + '_name').val();
	var defaultValue = $('#' + index + '_defaultValue').val();
	var nowValue = $('#' + index + '_nowValue').val();
	if(nowValue == defaultValue){
		showMsgWarn('当前值：' + nowValue + '\r\n默认值：' + defaultValue + '\r\n当前值和默认值一样，无需还原！');
		return false;
	}
	if(!showConfirm('当前值：' + nowValue + '\r\n默认值：' + defaultValue + 
		'\r\n确定要将【' + nameValue + '】还原为系统默认值？\r\n注意：确定后，将直接保存！')){
		return false;
	}
	$('#' + index + '_nowValue').val(defaultValue);
	saveNowValueOne(index);
}
//保存
function saveNowValueOne(index){
    clearMessages();
	//如果已经变灰色了，则不能操作
	if(!isActionable("#saveOne" + index)){
		return false;
	}
	//通用性输入验证
	if(!checkAllValidate()){
		return false;
	}
	//专用校验
	if(!checkform(index)){
		return false;
	}
	//点击后，按钮样式变为灰色
	$("#saveOne" + index).attr("class", "abtn-unable mr-10");
	$.getJSON("${request.contextPath}/system/admin/platformInfoAdmin-j-saveOne.action", {
		"iniid":$("#" + index + "_iniid").val(),
		"nowValue":$("#" + index + "_nowValue").val()
		}, function(data){
			showMsgSuccess("保存成功！", "提示", function(){
			//成功后，变回样式
			$("#saveOne" + index).attr("class", "abtn-orange mr-10");
			});
			
	});
}
function saveAll() {
    clearMessages();
	//如果已经变灰色了，则不能操作
	if(!isActionable("#btnSaveAll")){
		return false;
	}
	//通用性输入验证
	if(!checkAllValidate()){
		return false;
	}
	//专用校验
	if(!checkform()){
		return false;
	}
	//点击后，按钮样式变为灰色
	$("#btnSaveAll").attr("class", "abtn-unable-big");
	//组织json数据
	var dataMap = {};
	for(i = 0; i < ${systemIniDtos?size}; i ++){
		dataMap[$("#" + i + "_iniid").val()] = $("#" + i + "_nowValue").val();
	}
  	$.getJSON("${request.contextPath}/system/admin/platformInfoAdmin-j-saveAll.action", {
		"jsonString":JSON.stringify(dataMap)
		}, function(data){
			showMsgSuccess("保存所有！", "提示", function(){
			//成功后，变回样式
			$("#btnSaveAll").attr("class", "abtn-blue-big");
			});
			
	});
	
}
</script>
</@htmlmacro.moduleDiv>