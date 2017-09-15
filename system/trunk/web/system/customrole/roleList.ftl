<#import "/common/htmlcomponent.ftl" as htmlmacro>
<#import "/common/commonmacro.ftl" as commonmacro>
<@htmlmacro.moduleDiv titleName="${webAppTitle}--角色管理">
<script>
   //角色委派
	function saveCustomRole(objectId){
		var roleId =$("#roleId").val();
		var userIds=$("#userId").val();
		var userNames=$("#userName").val();
		$("#"+roleId+"UserName").html(userNames);
		$("#"+roleId+"UserId").val(userIds);
		getJSON("${request.contextPath}/system/customrole/customRole-remote!save.action",{"roleId":roleId,"userIds":userIds},"showMessage(data)","post");
	}
	
	function showMessage(data){
		if(!data.operateSuccess){
			    if(data.errorMessage!=null&&data.errorMessage!=""){
				    showMsgError(data.errorMessage);
				    return;
			    }
		}else{
	   		//showMsgSuccess("角色对应的用户设置成功！","",function(){});
			return;
		}
	}
	
	function changeUser(roleId){
		$("#userId").val($("#"+roleId+"UserId").val());
		$("#userName").val($("#"+roleId+"UserName").html());
		$("#roleId").val(roleId);
		$("#pop-user").click();
	}
	
</script>
<div class="jwindow" id="panelWindow"></div>
<form name="form1" id="form1" action="" method="post">
<@htmlmacro.tableList id="tablelist">
	<tr>
		<th width="20%">角色名称</th>
		<th width="70%">用户</th>
		<th width="10%" class="t-center">操作</th>
	</tr>
<#list roleList as role>
	<tr>
		<td >${role.roleName!}</td>
		<td id="${role.id}UserName"><#if role.type ==0>${role.userNames!}<#else>${role.remark!}</#if></td>
		<td class="t-center"><#if role.type ==0>
		<input type="hidden" name="${role.id}UserId" id="${role.id}UserId" value="${role.userIds!}">
		<a href="javascript:void(0);" onclick="changeUser('${role.id}')" class="edit-class">设置</a>
		</#if>
		</td>
	</tr>
</#list>
</@htmlmacro.tableList>
<@commonmacro.selectObject useCheckbox="true" idObjectId="userId" nameObjectId="userName" url="${request.contextPath}/common/getUserDataPopup.action" otherParam="showLetterIndex=true" width=800 callback="saveCustomRole" switchSelector=".edit-class">
	<input type="hidden" name="userId" id="userId" value="">
	<input type="hidden" name="userName" id="userName" value="">
	<input type="hidden" name="roleId" id="roleId" value="">
	<a id="pop-user"></a>
</@commonmacro.selectObject>
</form>
<script type="text/javascript" src="${request.contextPath}/static/js/myscript.js"></script>
</@htmlmacro.moduleDiv>