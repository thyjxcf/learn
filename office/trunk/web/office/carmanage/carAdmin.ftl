<#import "/common/htmlcomponent.ftl" as common />
<@common.moduleDiv titleName="">
<script>
function myCarUseDetail(){
	var url="${request.contextPath}/office/carmanage/carmanage-myCarUseDetail.action";
	load("#carAdminDiv", url);
}
function carApply(){
	var url="${request.contextPath}/office/carmanage/carmanage-apply.action";
	load("#carAdminDiv", url);
}
function carAudit(){
	var url="${request.contextPath}/office/carmanage/carmanage-carAudit.action";
	load("#carAdminDiv", url);
}
function carManage(){
	var url="${request.contextPath}/office/carmanage/carmanage-carManage.action";
	load("#carAdminDiv", url);
}
function driverManage(){
	var url="${request.contextPath}/office/carmanage/carmanage-driverManage.action";
	load("#carAdminDiv", url);
}
function subsidyManage(){
	var url="${request.contextPath}/office/carmanage/carmanage-subsidyManage.action";
	load("#carAdminDiv", url);
}
function carUseSummary(){
	var url="${request.contextPath}/office/carmanage/carmanage-carUseSummary.action";
	load("#carAdminDiv", url);
}
function carOverTime(){
	var url="${request.contextPath}/office/carmanage/carmanage-carOverTime.action";
	load("#carAdminDiv", url);
}

$(document).ready(function(){
	myCarUseDetail();
});
</script>
<div class="pub-tab">
	<ul class="pub-tab-list">
	<li class="current" onclick="myCarUseDetail();">我的出车情况</li>
	<#if carApply>
	<li onclick="carApply();">车辆申请</li>
	</#if>
	<#if officeCarAdmin || deptHead>
	<li onclick="carAudit();">单位审核</li>
	</#if>
	<#if officeCarAdmin>
	<li onclick="carManage();">车辆管理</li>
	<li onclick="driverManage();">驾驶员管理</li>
	<li onclick="subsidyManage();">出车补贴设置</li>
	</#if>
	<#if officeCarAdmin || deptHead>
	<li onclick="carUseSummary();">用车情况汇总</li>
	<li onclick="carOverTime();">用车加班时间</li>
	</#if>
	</ul>
</div>
<div id="carAdminDiv"></div>
</@common.moduleDiv>