<#import "/common/htmlcomponent.ftl" as htmlmacro>
<@htmlmacro.showPrompt/>
<@htmlmacro.moduleDiv titleName="">
<script>
$(function(){
	<#if admin>
	dutySet();
	<#else>
		dutyApply();
	</#if>
});

function dutySet(){
	load("#dutyDiv", "${request.contextPath}/office/dutymanage/dutymanage-dutyInformationAdmin.action");
}

function dutyApply(){
	load("#dutyDiv", "${request.contextPath}/office/dutymanage/dutymanage-dutyInformationApply.action");
}
function patrolPlace(){
	load("#dutyDiv", "${request.contextPath}/office/dutymanage/dutymanage-patrolPlaceList.action");
}
function dutyRegister(){
	<#if admin>
	load("#dutyDiv", "${request.contextPath}/office/dutymanage/dutymanage-dutyRegisterAdmin.action");
	<#else>
	load("#dutyDiv", "${request.contextPath}/office/dutymanage/dutymanage-dutyRegister.action");
	</#if>
}
function dutyQuery(){
	load("#dutyDiv", "${request.contextPath}/office/dutymanage/dutymanage-dutyQuery.action");
}
</script>
<div class="pub-tab mb-15">
	<ul class="pub-tab-list">
		<#if admin>
		<li class="current" onclick="dutySet();">值班设置</li>
		</#if>
		<li <#if !admin>class="current"</#if> onclick="dutyApply();">值班报名</li>
		<#if admin>
		<li onclick="patrolPlace();">巡查地点</li>
		</#if>
		<li onclick="dutyRegister();">值班情况登记</li>
		<li onclick="dutyQuery();">值班统计查询</li>
	</ul>
</div>
<div id="dutyDiv"></div>
<script type="text/javascript" src="${request.contextPath}/static/js/myscript.js"></script>
</@htmlmacro.moduleDiv>