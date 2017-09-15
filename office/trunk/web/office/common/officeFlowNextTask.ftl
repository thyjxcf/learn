<#import "/common/htmlcomponent.ftl" as htmlmacro>
<#include "/common/handlefielderror.ftl">
<@htmlmacro.moduleDiv titleName="">
<div style="position:absolute;z-index:-1;width:101%;height:101%;margin-left:-0.5%;">  
	<iframe style="width:100%;height:100%;border:0;filter:alpha(opacity=0);-moz-opacity:0"></iframe>  
</div>
<input type="hidden" id="taskId" name="taskId" value="${taskId!}"/>
<input type="hidden" id="nextStepChangeUserId" name="nextStepChangeUserId" value=""/>
<input type="hidden" id="nextStepChangeTaskId" name="nextStepChangeTaskId" value=""/>
<input type="hidden" id="nextStepChangeStepName" name="nextStepChangeStepName" value=""/>
<input type="hidden" id="nextStepChangeStepType" name="nextStepChangeStepType" value=""/>
<p class="tt"><a href="javascript:void(0)" onclick="onDivClose()" class="close">关闭</a><span>修改下一步负责人</p>
<div id="nextStepLayerListDiv">
<table border="0" cellspacing="0" cellpadding="0" class="public-table table-list table-list-edit mt-5">
    <tr>
    	<th width="15%">下一步骤名称</th>
    	<th width="25%">原负责人</th>
    	<th width="">修改后负责人</th>
    	<th width="15%" class="t-center">操作</th>
    </tr>
    <#list taskDescList as item>
    <tr class="${item.taskDefinitionKey!}">
    	<td>${item.taskName!}</td>
    	<td>${item.assigneeName!}</td>
    	<td class="changeNameStr"></td>
    	<td class="t-center">
			<a href="javascript:void(0)" class="change" dataTaskName="${item.taskName!}" dataUserIdVal="${item.assigneeId!}" dataUserNameVal="${item.assigneeName!}" dataUserDetailNameVal="${item.assigneeDetailNames!}" dataCheckHalfUsers="${item.checkHalfUsers!}" dataTaskId="${item.taskId!}" dataStep ="${item.taskDefinitionKey!}" dataStepName="${item.taskDefinitionKey!}" changeNameStr="${item.assigneeName!}" dataTaskType="modify" onclick="changeNextStepUser(this)" >修改</a>
    	</td>
    </tr>
    </#list>
    <tr>
    	<td colspan="4" class="t-center " >
    		<a class="abtn-blue ml-20" onclick="saveNextStepUsers()" href="javascript:void(0)" >确定修改</a>
    		<a class="abtn-blue close ml-20" href="javascript:void(0)" >返回</a>
    	</td>
    </tr>
</table>
</div>
<div id="nextStepLayerDiv" style="display:none;"></div>
<script>
function onDivClose(){
	$("#nextStepLayerListDiv").show();
	$("#nextStepLayerDiv").hide();
}

function changeNextStepUser(target){
	$("#nextStepLayerListDiv").hide();
	$("#nextStepLayerDiv").show();
	load("#nextStepLayerDiv","${request.contextPath}/office/common/changeNextStep.action?flowId=${flowId!}&currentStepId="+$(target).attr("dataStep")+"&checkHalfUsers="+$(target).attr("dataCheckHalfUsers"));
}

function saveNextStepUsers(){
	var flowId = "${flowId!}";
	var taskId = $("#taskId").val();
	var nextStepChangeUserId = $("#nextStepChangeUserId").val();
	var nextStepChangeTaskId = $("#nextStepChangeTaskId").val();
	var nextStepChangeStepName = $("#nextStepChangeStepName").val();
	var nextStepChangeStepType = $("#nextStepChangeStepType").val();
	$.post("${request.contextPath}/office/common/saveNextStepUsers.action", 
		{
			flowId:flowId,
			taskId:taskId,
			nextStepChangeUserId:nextStepChangeUserId,
			nextStepChangeTaskId:nextStepChangeTaskId,
			nextStepChangeStepName:nextStepChangeStepName,
			nextStepChangeStepType:nextStepChangeStepType
		}, function(data){
			if(!data.operateSuccess){
				if(data.errorMessage!=null&&data.errorMessage!=""){
			   		showMsgError(data.errorMessage);
			   		return;
		   		}else{
		   	   		showMsgError(data.promptMessage);
			   		return;
		   		}
			}else{
	   			showMsgSuccess("修改成功！","",function(){
					if($("#flowShow").length > 0 ){
						load("#flowShow","${request.contextPath}/jbmp/editor/wfPreview.action?id=${flowId!}&subsystemId=70&instanceType=instance&currentStepId="+data.promptMessage);
					}
				});
			}
	});
}
</script>
</@htmlmacro.moduleDiv>