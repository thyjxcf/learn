<#import "/common/htmlcomponent.ftl" as htmlmacro>
<SCRIPT src="${request.contextPath}/static/js/table-split-resize.js"></SCRIPT>
<@htmlmacro.moduleDiv titleName="${webAppTitle}--${operationName?default('')}">
<script>
function validateform(){
	if(!isActionable("#btnSave")){
		return false;
	}
	var flag= false;
	var length = document.roleform.elements.length;
	var allUserIds= new Array();
    var noSelectedUserIds= new Array();
    var y=0;
    var z=0;

	for(i=0;i<length;i++){
		if(roleform.elements[i].name == "userids" ){
			flag = true;
			allUserIds[y]=roleform.elements[i].value;
			y++;
		}
	}
	
	document.getElementById("allUserIds").value = allUserIds;
	
	var roleids = $("#roleids").val();
	var userids = [];
	var deptId = $("#deptId").val();
	var operation = $("#operation").val();
	var i = 0;
	$("[name='userids']:checked").each(function(){
		userids[i] = $(this).val();
		i++;
	});
	if(userids.length==0){
		showMsgError("没有选择保存的用户！");
		return;
	}
	$("#btnSave").attr("class", "abtn-unable");
	$.ajax({
		type: "POST",
		url: "${request.contextPath}/system/role/roleAdmin-accredit.action",
		data: $.param( {roleids:roleids,userids:userids, deptId:deptId, allUserIds:allUserIds, operation:operation},true),
		success: function(data){
			if(!data.operateSuccess){
					showMsgError(data.errorMessage, "提示", function(){
						load("#containerRole", "${request.contextPath}/system/role/roleAdmin.action?moduleID=${moduleID!}");
					});
					$("#btnSave").attr("class", "abtn-blue");
					return;
				}else{
					showMsgSuccess(data.promptMessage, "提示", function(){
						load("#containerRole", "${request.contextPath}/system/role/roleAdmin.action?moduleID=${moduleID!}");
					});
					$("#btnSave").attr("class", "abtn-blue");
					return;
				}
		},
		dataType: "json",
		error: function(XMLHttpRequest, textStatus, errorThrown){alert(XMLHttpRequest.status);}
	});
}

function cancel(){
	load("#containerRole", "${request.contextPath}/system/role/roleAdmin.action?moduleID=${moduleID!}");
}
</script>
<p class="table-dt">请选择需要委派角色的用户</p>
<form action="roleAdmin-accredit.action" method="post" name="roleform" id="roleform">
<input type="hidden" name="roleids" id="roleids" value="${roleids}">
<input type="hidden" name="operation" id="operation" value="save">
<input type="hidden" name="deptId" id="deptId" value="${deptId!}">  
<input type="hidden" name="allUserIds" id="allUserIds" value="">
<table border="0" cellspacing="0" cellpadding="0" class="table-form">
	<#if userList?exists &&(userList?size>0)>
	<#assign seq = userList> 
  	<#list seq?chunk(3) as row>
  		<tr>
  	  	<#list row as user>
		  <td width="33%" class="t-center"><p><span class="ui-checkbox <#if user.checked?default("") == "checked">ui-checkbox-current</#if>">
			  <label for="${user.id?default("")}">
				<input class="chk" type="checkbox" id="${user.id?default("")}" name="userids" value="${user.id?default("")}" <#if user.checked?default("") == "checked">checked="checked"</#if> />
				${user.name}<#if user.realname?exists>(${user.realname})</#if>
			  </label>
		  </span></p></td>
		</#list>
  	  	</tr>
  	</#list>
  	<#else>
         <tr>
           <td> <p class="no-data mt-50 mb-50">还没有任何信息哦！</p></td>
    	</tr>
         </#if>
</table>
<@htmlmacro.ToolbarBlank>
	<span class="ui-checkbox ui-checkbox-all" data-all="no"><input type="checkbox" class="chk">全选</span>
    <a id="btnSave" href="javascript:validateform();" class="abtn-blue">保存</a>
    <a id="cancelButton" href="javascript:cancel();" class="abtn-blue">返回</a>
</@htmlmacro.ToolbarBlank>
</form>
<script>vselect();</script>
</@htmlmacro.moduleDiv>