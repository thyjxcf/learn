<#import "/common/htmlcomponent.ftl" as htmlmacro>
<@htmlmacro.moduleDiv titleName="">
<script>   
//学生或家长
<#if stack.findValue('@net.zdsoft.eis.base.common.entity.Ware@ROLE_TYPE_STUDENT') == roleType ||
   stack.findValue('@net.zdsoft.eis.base.common.entity.Ware@ROLE_TYPE_FAMILY') == roleType>		
	<#assign groupTypeName = "班级">
<#else>
	<#assign groupTypeName = "部门">
</#if>

//保存
function validateform(type){
	if(isCheckBoxSelect($("[name='groupIds']")) == false){
		showMsgWarn("请先选择想要进行操作的${groupTypeName}！");
		return;
	}
	var groupIds = [];
	var serverIds = [];
	var i=0;
	var j=0;
	$("input[name='groupIds'][checked='checked']").each(function(){
		groupIds[i] = $(this).val();
		i++;
	});
	$("input[name='serverIds'][type='hidden']").each(function(){
		serverIds[j] = $(this).val();
		j++;
	});
	var roleType = '${roleType}';
	$.ajax({
		type: "POST",
		url: "${request.contextPath}/basedata/order/serverAuthorize-saveByServer.action",
		data: $.param( {groupIds:groupIds,serverIds:serverIds, saveByGroupType:type, roleType:roleType},true),
		success: function(data){
			if(data.jsonError != null && data.jsonError != ""){
					showMsgError(data.jsonError);
					return;
				}else{
					showMsgSuccess(data.promptMessage, "提示", function(){
						load("#listDiv", "${request.contextPath}/basedata/order/serverAuthorize-serverList.action");
					});
					return;
				}
		},
		dataType: "json",
		error: function(XMLHttpRequest, textStatus, errorThrown){alert(XMLHttpRequest.status);}
	});
}
</script>
<form name="form1" id="form1" action="" method="post">
<#list serverIds as serverId>
	<input type="hidden" name="serverIds" value="${serverId}">
</#list>
<@htmlmacro.tableList id="tablelist">
	<th width="5%">选择</th>
	<th width="30%">${groupTypeName}代码</th>
	<th width="30%">${groupTypeName}名称</th>
<#list groups as x>
	<tr>	
		<td class="t-center"><p><span class="ui-checkbox">
			<input type="checkbox" class="chk" name="groupIds" id="groupIds" value="${x.id}" />   	
		</span></p></td>
		<td >
			${x.groupCode?default('')}
		</td>
		<td >
			${x.groupName?default('')}
		</td>
	</tr>
</#list>		
</@htmlmacro.tableList>
<div class="base-operation">
    <p class="opt">
	<span class="ui-checkbox ui-checkbox-all"  data-all="no"><input type="checkbox" class="chk">全选</span>
	<a href="javascript:validateform(1);" class="abtn-blue">授权</a>
	<a href="javascript:validateform(2);" class="abtn-blue">退订</a>
	<a href="javascript:parent.cancel();;" class="abtn-blue">返回</a>
</div>		
</form>
<script type="text/javascript" src="${request.contextPath}/static/js/myscript.js"></script>
<script>vselect();</script>
</@htmlmacro.moduleDiv>