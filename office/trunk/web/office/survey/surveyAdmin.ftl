<#import "/common/htmlcomponent.ftl" as htmlmacro>
<@htmlmacro.showPrompt/>
<@htmlmacro.moduleDiv titleName="">
<script>
$(function(){
	getMySurvey();
});

function getMySurvey(){
	load("#adminDiv", "${request.contextPath}/office/survey/surveyManage-mySurvey.action");
}

function doSurveyAudit(){
	load("#adminDiv", "${request.contextPath}/office/survey/surveyManage-surveyAudit.action");
}

function doSurveyQuery(){
	load("#adminDiv", "${request.contextPath}/office/survey/surveyManage-surveyQuery.action");
}

</script>
<div class="pub-tab mb-15">
	<ul class="pub-tab-list">
		<li class="current" onclick="getMySurvey();">我的调研</li>
		<#if surveyAuditAuth>
		<li onclick="doSurveyAudit();">调研审核</li>
		</#if>
		<#if surveyQueryAuth>
		<li onclick="doSurveyQuery();">调研查询</li>
		</#if>
	</ul>
</div>
<div id="adminDiv"></div>
<script type="text/javascript" src="${request.contextPath}/static/js/myscript.js"></script>
</@htmlmacro.moduleDiv>