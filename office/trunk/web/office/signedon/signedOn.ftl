<#import "/common/htmlcomponent.ftl" as htmlmacro>
<@htmlmacro.showPrompt/>
<@htmlmacro.moduleDiv titleName="">
<script>
$(function(){
	signedOn();
});

function signedOn(){
	load("#workReportDiv", "${request.contextPath}/office/signmanage/signmanage-signmanage.action");
}


function signedOnCount(){
	load("#workReportDiv", "${request.contextPath}/office/signmanage/signmanage-signmanageCount.action");
}
function signedOnTimeSet(){
	load("#workReportDiv", "${request.contextPath}/office/signmanage/signmanage-signmanageTimeSet.action");
}
</script>
<div class="pub-tab mb-15">
	<ul class="pub-tab-list">
		<li class="current" onclick="signedOn();">签到详情</li>
		<li onclick="signedOnCount();">签到统计</li>
		<#if officeSignOn>
		<li onclick="signedOnTimeSet();">签到时间设置</li>
		</#if>
	</ul>
</div>
<div id="workReportDiv"></div>
<script type="text/javascript" src="${request.contextPath}/static/js/myscript.js"></script>
</@htmlmacro.moduleDiv>