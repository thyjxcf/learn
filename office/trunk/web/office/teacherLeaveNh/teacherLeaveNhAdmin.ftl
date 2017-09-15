 <#import "/common/htmlcomponent.ftl" as htmlmacro>
 <#include "/common/handlefielderror.ftl">
<@htmlmacro.moduleDiv titleName="">
<div class="pub-tab">
	<ul class="pub-tab-list">
		<li class="current" onclick="leaveApply();">请假申请</li>
		<#if canAudit>
		<li onclick="leaveAudit();" class="">请假审核</li>
		<li onclick="leaveTypeList();">请假类型维护</li>
		</#if>
		<li onclick="leaveSearch();" class="">请假查询</li>
		<li onclick="leaveSummary();" class="">请假统计</li>
	</ul>
</div>
<div id="teacherLeaveNhDiv"></div>
<script>
	function leaveApply(){
		load("#teacherLeaveNhDiv","${request.contextPath}/office/teacherLeaveNh/teacherLeaveNh-leaveApply.action");
	}
	function leaveAudit(){
		load("#teacherLeaveNhDiv","${request.contextPath}/office/teacherLeaveNh/teacherLeaveNh-leaveAudit.action");
	}
	function leaveTypeList(){
		load("#teacherLeaveNhDiv","${request.contextPath}/office/teacherLeaveNh/teacherLeaveNh-leaveTypeList.action");
	}
	function leaveSearch(){
		load("#teacherLeaveNhDiv","${request.contextPath}/office/teacherLeaveNh/teacherLeaveNh-leaveSearch.action");
	}
	function leaveSummary(){
		load("#teacherLeaveNhDiv","${request.contextPath}/office/teacherLeaveNh/teacherLeaveNh-leaveSummary.action");
	}
	$(document).ready(function(){
		leaveApply();
	});
</script>
<script type="text/javascript" src="${request.contextPath}/static/js/myscript.js"></script>
</@htmlmacro.moduleDiv>