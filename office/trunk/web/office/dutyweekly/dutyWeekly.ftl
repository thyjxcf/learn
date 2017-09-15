<#import "/common/htmlcomponent.ftl" as htmlmacro>
<@htmlmacro.showPrompt/>
<@htmlmacro.moduleDiv titleName="">
<script>
$(function(){
	<#if admin>
	load("#workReportDiv", "${request.contextPath}/office/dutyweekly/dutyweekly-dutyweeklyList.action");
	<#else>
	load("#workReportDiv", "${request.contextPath}/office/dutyweekly/dutyweekly-weeklyapplyList.action");
	</#if>
});

function dutyweekly(){
	load("#workReportDiv", "${request.contextPath}/office/dutyweekly/dutyweekly-dutyweeklyList.action");
}

function weeklyapply(){
	load("#workReportDiv", "${request.contextPath}/office/dutyweekly/dutyweekly-weeklyapplyList.action");
}

function workReportSearchTab(){
	load("#workReportDiv", "${request.contextPath}/office/dutyweekly/dutyweekly-weeklyQueryList.action");
}

function dutyproject(){
	load("#workReportDiv", "${request.contextPath}/office/dutyweekly/dutyweekly-dutyproject.action");
}

function dutyprojectCount(){
	load("#workReportDiv", "${request.contextPath}/office/dutyweekly/dutyweekly-weeklyQueryCount.action?years="+'${year!}'+"&semesters="+'${semester!}');
}
</script>
<div class="pub-tab mb-15">
	<ul class="pub-tab-list">
		<#if admin>
		<li class="current" onclick="dutyweekly();">值周安排</li>
		</#if>
		<li <#if !admin> class="current"</#if> onclick="weeklyapply();">值周登记</li>
		<li onclick="workReportSearchTab();">值周查询</li>
		<#if admin>
		<li onclick="dutyproject();">检查项目</li>
		</#if>
		<li onclick="dutyprojectCount();">值周统计</li>
	</ul>
</div>
<div id="workReportDiv"></div>
<script type="text/javascript" src="${request.contextPath}/static/js/myscript.js"></script>
</@htmlmacro.moduleDiv>