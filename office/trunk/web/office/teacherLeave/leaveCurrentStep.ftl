<#import "/common/htmlcomponent.ftl" as htmlmacro>
<#import "/common/commonmacro.ftl" as commonmacro>
<script type="text/javascript" src="${request.contextPath}/static/jbmp/editor/js/flow.js"></script>
<link rel="stylesheet" type="text/css" href="${request.contextPath}/static/boxy/boxy.css"/>
<script type="text/javascript" src="${request.contextPath}/static/boxy/jquery.boxy.js"></script>

<@htmlmacro.moduleDiv titleName="">
<input id="wf-actionUrl" value="" type="hidden" />
<input id="wf-taskHandlerSaveJson" value="" type="hidden" />
<div class="boxy" id="wfEditorDiv" style="display:none;"></div>
<script>
	$(document).ready(function(){
		vselect();
	 	doFlowChange('${taskDefinitionKey!}','${processInstanceId!}');
	});
	
	function doFlowChange(taskId,flowId){
		var easyLevel = '${easyLevel!}';
		formUrl = '${request.contextPath}/jbmp/editor/wfEdit.action';
		businessType = '7001';
		operation = 'modify';
		instanceType ='instance';
		id = flowId;  
		actionUrl ="${request.contextPath}/office/teacherLeave/teacherLeave-changeCurrentstep.action?currentStepId="+taskId;
		callBackJs = 'flowSuccess'; 
		taskHandlerSaveJson = JSON.stringify(taskHandlerSave);
		currentStepId = taskId;
		develop="false";
		subsystemId = 70;
		openOfficeWin(formUrl,businessType,operation,instanceType,id,actionUrl,callBackJs,taskHandlerSaveJson,currentStepId,develop,subsystemId,easyLevel);
	}
	
	function flowSuccess() {
	    showMsgSuccess("修改流程保存成功！", "", savePass);
	}
	
	function savePass(){
		closeDiv("#classLayer");
		reload('${leaveId!}','${taskId!}');
	}
	
	function closeChange(){
		closeDiv("#classLayer");
	}
</script>
<script type="text/javascript" src="${request.contextPath}/static/js/myscript.js"></script>
</@htmlmacro.moduleDiv>