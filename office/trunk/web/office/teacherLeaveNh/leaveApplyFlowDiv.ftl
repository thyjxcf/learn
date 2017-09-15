<#import "/common/htmlcomponent.ftl" as htmlmacro>
<@htmlmacro.select style="width:40%;" valName="officeTeacherLeaveNh.flowId" valId="flowId" myfunchange="flowChange" msgName="请假流程">
	<#if flowList?exists && flowList?size gt 0>
		<#list flowList as flow>
			<a val="${flow.flowId!}"><span>${flow.flowName!}</span></a>
		</#list>
	</#if>
</@htmlmacro.select>
<script>
	$(document).ready(function(){
		$("#flowType").val('${flowType!}');
		setTimeout(flowChange,200);
	});
</script>