<#import "/common/htmlcomponent.ftl" as htmlmacro>
<@htmlmacro.showPrompt/>
<@htmlmacro.moduleDiv titleName="">
<script>
$(function(){
	myApply();
});

function myApply(){
	load("#sealDiv", "${request.contextPath}/office/sealmanage/sealmanage-mySealAdmin.action");
}
function sealManage(){
	load("#sealDiv", "${request.contextPath}/office/sealmanage/sealmanage-sealManageAdmin.action");
}
function sealType(){
	load("#sealDiv", "${request.contextPath}/office/sealmanage/sealmanage-sealType.action");
}
</script>
<div class="pub-tab mb-15">
	<ul class="pub-tab-list">
		<li class="current" onclick="myApply();">我的申请</li>
		<#if deputyHead||sealManager>
		<li onclick="sealManage();">用印管理</li>
		</#if>
		<#if sealManager>
		<li onclick="sealType();">印章类型</li>
		</#if>
	</ul>
</div>
<div id="sealDiv"></div>
<script type="text/javascript" src="${request.contextPath}/static/js/myscript.js"></script>
</@htmlmacro.moduleDiv>