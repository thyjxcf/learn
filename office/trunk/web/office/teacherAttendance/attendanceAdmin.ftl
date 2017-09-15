 <#import "/common/htmlcomponent.ftl" as htmlmacro>
 <#import "/common/commonmacro.ftl" as commonmacro>
 <#include "/common/handlefielderror.ftl">
<@htmlmacro.moduleDiv titleName="">
<div class="pub-tab">
	<ul class="pub-tab-list">
		<#if teacherAttenceAdmin>
		<li onclick="onClick('1');">考勤设置</li>
		</#if>
		<li  onclick="onClick('4');" class="">补卡审批</li>
		<li class="current"  onclick="onClick('5');" class="">考勤查询</li>
		<li  onclick="onClick('6');" class="">考勤统计</li>
	</ul>
</div>
<div id="contentDiv">
</div>
<script>
$(function(){
	onClick("5");
});

function onClick(type){
	if("1"==type){
		load("#contentDiv","${contextPath!}/office/teacherAttendance/teacherAttendance-set.action");
	}else if("4"==type){
		load("#contentDiv","${request.contextPath}/office/teacherAttendance/teacherAttendance-auditList.action");
	}else if("5"==type){
		load("#contentDiv","${request.contextPath}/office/teacherAttendance/teacherAttendance-infoList.action");
	}else if("6"==type){
		load("#contentDiv","${request.contextPath}/office/teacherAttendance/teacherAttendance-statisticsMain.action");
	}
}
</script>
<script type="text/javascript" src="${request.contextPath}/static/js/myscript.js"></script>
</@htmlmacro.moduleDiv>