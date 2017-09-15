<#import "/common/htmlcomponent.ftl" as common />
<@common.moduleDiv titleName="">
<script>

function stuLeaveApply(){
	var url="${request.contextPath}/office/studentLeave/studentLeave-apply.action";
	load("#studentAdminDiv",url);
}
function leaveType(){
	var url="${request.contextPath}/office/studentLeave/studentLeave-leaveType.action";
	load("#studentAdminDiv",url);
}
function studentLeaveApprove(){
	var url="${request.contextPath}/office/studentLeave/studentLeave-approve.action";
	load("#studentAdminDiv",url);
}
function studentLeaveQuery(){
	var url="${request.contextPath}/office/studentLeave/studentLeave-leaveQuery.action";
	load("#studentAdminDiv",url);
}
function studentLeaveCount(){
	var url="${request.contextPath}/office/studentLeave/studentLeave-leaveCount.action";
	load("#studentAdminDiv",url);
}
$(document).ready(function(){
	stuLeaveApply();
});

</script>
<div class="pub-tab">
	<ul class="pub-tab-list">
	<li class="current" onclick="stuLeaveApply();">学生请假登记</li>
	<#if canAudit>
	<li onclick="studentLeaveApprove();">学生请假审核</li>
	<li onclick="leaveType();">请假类型维护</li>
	</#if>
	<li onclick="studentLeaveQuery();">学生请假查询</li>
	<li onclick="studentLeaveCount();">学生请假统计</li>
	</ul>
</div>
<div id="studentAdminDiv"></div>
<script type="text/javascript" src="${request.contextPath}/static/js/myscript.js"></script>
</@common.moduleDiv>