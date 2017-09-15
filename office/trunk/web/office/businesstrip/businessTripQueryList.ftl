<#import "/common/htmlcomponent.ftl" as htmlmacro>
<@htmlmacro.moduleDiv titleName="">
<#include "/common/handlefielderror.ftl">
<#import "/common/commonmacro.ftl" as commonmacro>
<div class="pub-table-wrap">
	<div class="pub-table-inner">
		<div class="query-builder-nobg mt-15" style="padding:0 0 5px 0;">  
    		<div class="query-part">
    			<div class="query-tt ml-10">
			<span class="fn-left">审核状态：</span>
		</div>
		<div class="select_box fn-left mr-10">
		<@htmlmacro.select style="width:150px;" valId="states" valName="states" myfunchange="doSearch" >
			<a val="" <#if states?default("") == "">class="selected"</#if>><span>全部</span></a>
    		<a val="2" <#if states?default("") == "2">class="selected"</#if>><span>审核中</span></a>
    		<a val="3" <#if states?default("") == "3">class="selected"</#if>><span>审核通过</span></a>
    		<a val="4" <#if states?default("") == "4">class="selected"</#if>><span>审核不通过</span></a>
		</@htmlmacro.select></div>
    			<div class="query-tt ml-10">
					<span class="fn-left">开始时间：</span>
				</div>
				<div class="fn-left">
    			<@htmlmacro.datepicker class="input-txt" style="width:100px;" id="startTime" value="${((startTime)?string('yyyy-MM-dd'))?if_exists}"/>
				</div>
    			<div class="query-tt ml-10">
					<span class="fn-left">结束时间：</span>
				</div>
				<div class="fn-left">
    			<@htmlmacro.datepicker class="input-txt" style="width:100px;" id="endTime" value="${((endTime)?string('yyyy-MM-dd'))?if_exists}"/>
				</div>
	    			<div class="query-tt ml-10">
						<span class="fn-left">申请人：</span>
					</div>
					<div class="fn-left">
						<input type="input" class="input-txt" style="width:100px;" id="applyUserName" value="${applyUserName!}">
					</div>
				&nbsp;&nbsp;<a href="javascript:void(0);" onclick="doSearch();" class="abtn-blue">查找</a>
				&nbsp;&nbsp;<a href="javascript:void(0);" onclick="doExport();" class="abtn-blue">导出</a>
			</div>
    	</div>
    </div>
</div>
<table border="0" cellspacing="0" cellpadding="0" class="public-table table-list">
    <tr>
    	<th width="4%">序号</th>
    	<th width="8%">申请人</th>
    	<th width="9%">部门（科室）</th>
    	<th width="8%">出差地点</th>
    	<th width="7%">开始时间</th>
    	<th width="7%">结束时间</th>
    	<th width="7%">出差天数</th>
    	<th width="34%">出差事由</th>
    	<th width="8%">审核状态</th>
    	<th class="t-center" width="8%">操作</th>
    </tr>
    <#if officeBusList?exists && officeBusList?size gt 0>
    	<#list officeBusList as bussinessTrip>
    		<tr>
    			<td >${bussinessTrip_index+1}</td>
    			<td >${bussinessTrip.applyUserName!}</td>
    			<td >${bussinessTrip.deptName!}</td>
    			<td >${bussinessTrip.place!}</td>
    			<td >${(bussinessTrip.beginTime?string('yyyy-MM-dd'))?if_exists}</td>
    			<td >${(bussinessTrip.endTime?string('yyyy-MM-dd'))?if_exists}</td>
		    	<td >${bussinessTrip.days!}</td>
		    	<td>${bussinessTrip.tripReason!}</td>
		    	<td >
		    		<#if bussinessTrip.state=='2'>
		    			待审核
		    		<#elseif bussinessTrip.state=='3'>
		    			审核通过
		    		<#elseif bussinessTrip.state=='4'>
		    			审核不通过
		    		<#elseif bussinessTrip.state=='8'>
		    			已作废
		    		</#if>
		    	</td>
		    	<td class="t-center">
		    		<a href="javascript:void(0);" onclick="doInfo('${bussinessTrip.id!}');">查看</a>
		    	</td>
    		</tr>
    	</#list>
    <#else>
    	<tr>
    		<td colspan='10'><p class="no-data mt-50 mb-50">还没有任何记录哦！</p></td>
	 	</tr>
    </#if>
</table>
<@htmlmacro.Toolbar container="#businessTripeDiv">
</@htmlmacro.Toolbar>
<script>
$(document).ready(function(){
	vselect();
});
function doSearch(){
	var startTime=$("#startTime").val();
	var endTime=$("#endTime").val();
	if(startTime!=''&&endTime!=''){
		var re = compareDate(startTime,endTime);
		if(re==1){
			showMsgError("结束时间不能早于开始时间，请重新选择！");
			return;
		}
	}
	var states=$("#states").val();
	var applyUserName=$("#applyUserName").val();
	var str="?startTime="+startTime+"&endTime="+endTime+"&states="+states+"&applyUserName="+encodeURIComponent(applyUserName);
	load("#businessTripeDiv","${request.contextPath}/office/businesstrip/businessTrip-businessTripQueryList.action"+str);
}
function doInfo(id){
		load("#businessTripeDiv","${request.contextPath}/office/businesstrip/businessTrip-businessTripView.action?fromTab=3&&officeBusinessTrip.id="+id);
	}
function doExport(){
	var startTime=$("#startTime").val();
	var endTime=$("#endTime").val();
	if(startTime!=''&&endTime!=''){
		var re = compareDate(startTime,endTime);
		if(re==1){
			showMsgError("结束时间不能早于开始时间，请重新选择！");
			return;
		}
	}
	var states=$("#states").val();
	var applyUserName=$("#applyUserName").val();
	var str="?startTime="+startTime+"&endTime="+endTime+"&states="+states+"&applyUserName="+encodeURIComponent(applyUserName);
	location.href="${request.contextPath}/office/businesstrip/businessTrip-businessTripExport.action"+str;
}
</script>
<script type="text/javascript" src="${request.contextPath}/static/js/myscript.js"></script>
</@htmlmacro.moduleDiv>
