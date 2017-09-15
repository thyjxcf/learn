<#import "/common/htmlcomponent.ftl" as htmlmacro>
<script type="text/javascript" src="${request.contextPath}/static/js/printarea.js"></script>
<@htmlmacro.showPrompt/>
<@htmlmacro.moduleDiv titleName="">
<script>
$(function(){
	jtGoOut();
});

function jtGoOut(){
	load("#goOutDiv", "${request.contextPath}/office/jtgooutmanage/jtgooutmanage-jtGoOutList.action");
}

function jtGoOutSearch(){
	load("#goOutDiv", "${request.contextPath}/office/jtgooutmanage/jtgooutmanage-jtGoOutAuditList.action");
}
function jtGoOutStatistics(){
	load("#goOutDiv", "${request.contextPath}/office/jtgooutmanage/jtgooutmanage-jtGoOutQueryList.action");
}
</script>
<div class="pub-tab mb-15">
	<ul class="pub-tab-list">
		<#--<#if loginInfo.unitClass==2>-->
		<li onclick="jtGoOut();" class="current">集体外出申请</li>
		<#--</#if>-->
		<li onclick="jtGoOutSearch();">集体外出审核</li>
		<li onclick="jtGoOutStatistics();">集体外出查询</li>
	</ul>
</div>
<div id="goOutDiv"></div>
<script type="text/javascript" src="${request.contextPath}/static/js/myscript.js"></script>
</@htmlmacro.moduleDiv>