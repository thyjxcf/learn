<#import "/common/htmlcomponent.ftl" as common />
<@common.moduleDiv titleName="">
<script>
function toLatestRepaire(){
	var url="${request.contextPath}/office/repaire/repaire-latestRepaire.action";
	load("#repaireDiv", url);
}
function toMyRepaire(){
	var url="${request.contextPath}/office/repaire/repaire-myRepaire.action";
	load("#repaireDiv", url);
}
function toAudit(){
	var url="${request.contextPath}/office/repaire/repaire-audit.action";
	load("#repaireDiv", url);
}
function toManage(){
	var url="${request.contextPath}/office/repaire/repaire-manage.action";
	load("#repaireDiv", url);
}
function toTypeManage(){
	var url="${request.contextPath}/office/repaire/repaire-typeManage.action";
	load("#repaireDiv", url);
}
function toStatistics(){
	var url="${request.contextPath}/office/repaire/repaire-statisticAdmin.action";
	load("#repaireDiv", url);
}
function view(id){
	var url="${request.contextPath}/office/repaire/repaire-view.action?id="+id;
   	openDiv("#classLayer3", "",url, false,"","300px");
}
</script>
<div class="popUp-layer" id="classLayer3" style="display:none;width:600px;margin-top:45px;"></div>
<div class="pub-tab">
	<ul class="pub-tab-list">
	<li class="current" onclick="toLatestRepaire();">最新报修</li>
	<li onclick="toMyRepaire();">我的报修</li>
	<#if hasMange>
	<li onclick="toManage();">报修管理</li>
	</#if>
	<#if hasUser>
	<li onclick="toAudit();">维修权限设置</li>
	<li onclick="toTypeManage();">二级类别维护</li>
	</#if>
	<li onclick="toStatistics();">报修统计</li>
	</ul>
</div>
<div id="repaireDiv"></div>
<script type="text/javascript" src="${request.contextPath}/static/js/myscript.js"></script>
<script>
	$(document).ready(function(){
		toLatestRepaire();
	});
</script>
</@common.moduleDiv>