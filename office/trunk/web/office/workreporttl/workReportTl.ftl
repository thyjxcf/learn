<#import "/common/htmlcomponent.ftl" as htmlmacro>
<@htmlmacro.showPrompt/>
<@htmlmacro.moduleDiv titleName="">
<script>
$(function(){
	myWorkReport();
});

function myWorkReport(){
	load("#workReportDiv", "${request.contextPath}/office/workreporttl/workReportTl-myWorkReport.action");
}


function workReportSearch(){
	load("#workReportDiv", "${request.contextPath}/office/workreporttl/workReportTl-workReportSearch.action");
}
</script>
<div class="pub-tab mb-15">
	<ul class="pub-tab-list">
		<li class="current" onclick="myWorkReport();">我的工作汇报</li>
		<#if workReportTl&&loginInfo.unitClass==1>
		<li onclick="workReportSearch();">工作汇报管理</li>
		</#if>
	</ul>
</div>
<div id="workReportDiv"></div>
<script type="text/javascript" src="${request.contextPath}/static/js/myscript.js"></script>
</@htmlmacro.moduleDiv>