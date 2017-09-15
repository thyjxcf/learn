<#import "/common/htmlcomponent.ftl" as common />
<@common.moduleDiv titleName="">
<script>
function toOutline() {
	var url = "${request.contextPath}/office/weekwork/weekwork-outlineList.action";
    load("#weekworkContainer", url);
}

function toArrange() {
	var url = "${request.contextPath}/office/weekwork/weekwork-arrangeList.action";
    load("#weekworkContainer", url);
}

function toAudit() {
    var url = "${request.contextPath}/office/weekwork/weekwork-arrangeAudit.action";
    load("#weekworkContainer", url); 
}

function toReport() {
	var url = "${request.contextPath}/office/weekwork/weekwork-arrangeReportAdmin.action";
    load("#weekworkContainer", url);
}

function toQuery() {
    var url = "${request.contextPath}/office/weekwork/weekwork-arrangeQuery.action?workOutlineId=${workOutlineId!}"+"&arrangeQuery="+${arrangeQuery?string('true','false')};
    load("#weekworkContainer", url);
}

</script>
<div class="popUp-layer" id="classLayer3" style="display:none;width:500px;"></div>
<div class="popUp-layer" id="bulletinLayer" style="display:none;top:100px;left:300px;width:700px;height:580px;"></div>
<div class="pub-tab" id="test">
	<ul class="pub-tab-list">
	<#if weekworkManage>
	<li onclick="toOutline();">周工作大纲</li>
	</#if>
	<#if deptManage>
	<li onclick="toArrange();">周工作上报</li>
	</#if>
	<#if weekworkManage>
	<li onclick="toAudit();">周工作审核</li>
	</#if>
	<li onclick="toReport();">周工作汇总表</li>
	<li id="arrangeQueryLi" onclick="toQuery();">周工作查询</li>
	</ul>
</div>

<div id="weekworkContainer"></div>

<script type="text/javascript" src="${request.contextPath}/static/js/myscript.js"></script>
<script type="text/javascript">
$(function(){
    //默认选择第一tab
    <#if arrangeQuery?default(false)>
        $('#test #arrangeQueryLi').click();
    <#else>
    	$('#test ul li:first-child').click();
    </#if>
});
</script>
</@common.moduleDiv>