<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>${webAppTitle}--部门编辑</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<#import "/common/htmlcomponent.ftl" as common />
<script src="${request.contextPath}/static/js/buffalo.js"></script>
<script>
var buffalo = new Buffalo("${request.contextPath}/basedata/sys");
var isSubmited = false;
function save(){
	if(isSubmited) return;
	
	//表单验证
	if(formValidate()){
		var f=document.mForm;
		f.action="module-update.action";		
		f.submit();
		isSubmited = true;
	}
}
function formValidate(){
	//检查关键字段是否必填
	if(!(checkElement($("module.name"),"模块名称") &&　checkElement($("module.subsystem"),"所属子系统") &&　
		checkElement($("module.parentid"),"上级模块")&&　checkElement($("module.unitclass"),"单位类型"))){
		return false;
	}
	//检查排序号是否数字
	if($F('module.orderid')!=''){
		if(!checkNumber($("module.orderid"),"排序号")){
			return false;
		}
	}
	return true;
}
function cancel(){
	window.location.href="${request.contextPath}/basedata/sys/module-list.action?pageIndex=${pageIndex}&unitClass=${unitClass}&subSystemId=${subSystemId}";
}
//更新上级单位列表
function updateParentModule(){
	var subSystemId = $("module.subsystem").value;
	var unitclass=$("module.unitclass").value;
	if(""==subSystemId||""==unitclass){
		clearParentModule();
	}else{
		buffalo.remoteActionCall("module-remote.action", "findParentModules", [${module.parentid},subSystemId,unitclass], function(reply) {
	        showParentModules(reply.getResult());
	    });
	}
}
//显示上级模块内容
function showParentModules(modules){
	clearParentModule()
	var select = $("module.parentid");
	for (i=0; i<modules.length; i++){
		select.add(new Option(modules[i].name, modules[i].id));
	}
}
//清除上级模块的内容
function clearParentModule(){
	var select = $("module.parentid");
	select.options.length = 0;
	select.add(new Option("--请选择--", ""));
}

</script>
</head>
<body>
<form name="mForm" id="mForm" method="POST">
<input name="module.id" type="hidden" value="${module.id}">
<input name="unitClass" type="hidden" value="${unitClass}">
<input name="subSystemId" type="hidden" value="${subSystemId}">
<input name="pageIndex" type="hidden" value="${pageIndex}">
<@common.tableDetail>
	<tr class="first"><th colspan="4" class="tt">模块编辑</th></tr>
		  	  	  	  	<tr>
		  	  	  	  	  <th><font color="red">*</font>模块名称：</th>
		  	  	  	  	  <td>
		  	  	  	  	  	<input name="module.name" id="module.name" type="text" value="${module.name?default('')?trim}" class="input-txt100" />
		  	  	  	  	  </td>
		  	  	  	  	  
		  	  	  	  	  <th><font color="red">*</font>单位类型：</th>
  	  	  				  <td>
						  	<select name="module.unitclass" id="module.unitclass" class="input-sel" onchange="updateParentModule()">
						  	${appsetting.getMcode("DM-DWFL").getHtmlTag(module.unitclass?string)}
						  	</select>
						  </td>
  	  	  				</tr>
  	  	  				
  	  	  				<tr>  	
  	  	  				  <th><font color="red">*</font>所属子系统：</th>
		  	  	  	  	  <td>
		  	  	  	  	  	<select name="module.subsystem" id="module.subsystem" class="input-sel" onchange="updateParentModule()">
		  	  	  	  	  	<#list subSysList as x>
						  		<option value='${x.id}' <#if module.subsystem==x.id>selected</#if> >${x.name}</option>
						  	</#list>
		  	  	  	  	  	</select>
		  	  	  	  	  </td>		  	 
  	  	  				  	  	
  	  	  				  <th><font color="red">*</font>上级模块：</th>
  	  	  				  <td >
  	  	  				  	<select name="module.parentid" id="module.parentid" class="input-sel">
		  	  	  	  	  	<#list moduleList as x>
						  		<option value='${x.id}' <#if module.parentid==x.id>selected</#if> >${x.name}</option>
						  	</#list>
		  	  	  	  	  	</select>
  	  	  				  </td>
  	  	  				</tr>
  	  	 				<tr>
  	  	 				  <th>是否收缩：</th>
  	  	  				  <td >
  	  	  					<select name="module.limit" id="module.limit" class="input-sel">
						  	${appsetting.getMcode("DM-BOOLEAN").getHtmlTag(module.limit?default("0"))}
						  	</select>
  	  	  				  </td>
  	  	  				  <th>排序号：</th>
  	  	  				  <td >
  	  	  					<input type="text" id="module.orderid"  name="module.orderid" value="${module.orderid}"/>
  	  	  				  </td>
  	  	  				</tr>
  	  	  				<tr>
  	  	 				  <th>是否启用：</th>
  	  	  				  <td >
  	  	  					<select name="module.isActive" id="module.isActive" class="input-sel">
						  	${appsetting.getMcode("DM-BOOLEAN").getHtmlTag(module.isActive?string?default("0"))}
						  	</select>
  	  	  				  </td>
  	  	  				  <th></th>
  	  	  				  <td >
  	  	  				  </td>
  	  	  				</tr>
		</@common.tableDetail>	
<@common.ToolbarBlank class="table1-bt t-center">
	<span class="input-btn1" onclick="save();"><button type="button">保存</button></span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	<span class="input-btn1"  onclick="cancel();"><button type="button">取消</button></span>
</@common.ToolbarBlank>

</form>
</body>
</html>