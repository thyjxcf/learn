 <#import "/common/htmlcomponent.ftl" as htmlmacro>
 <#include "/common/handlefielderror.ftl">
<@htmlmacro.moduleDiv titleName="">
<div class="pub-tab">
	<ul class="pub-tab-list">
		<li class="current" onclick="onApplyLeave();">请假申请</li>
		<li  onclick="onApplyAudit();" class="">请假审核</li>
		<li  onclick="onApplyQuery();" class="">请假查询</li>
		<li  onclick="onApplySummary();" class="">请假统计</li>
	</ul>
</div>
<div id="showListDiv"></div>
<script>
	function onApplyLeave(){
		load("#showListDiv","${request.contextPath}/office/teacherLeave/teacherLeave-applyList.action");
	}
	function onApplyAudit(){
		load("#showListDiv","${request.contextPath}/office/teacherLeave/teacherLeave-auditList.action");
	}
	function onApplyQuery(){
		load("#showListDiv","${request.contextPath}/office/teacherLeave/teacherLeave-applyQueryList.action");
	}
	function onApplySummary(){
		load("#showListDiv","${request.contextPath}/office/teacherLeave/teacherLeave-applySummary.action");
	}
	$(document).ready(function(){
		onApplyLeave();
	});
</script>
<script type="text/javascript" src="${request.contextPath}/static/js/myscript.js"></script>
</@htmlmacro.moduleDiv>