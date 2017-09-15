<#import "/common/htmlcomponent.ftl" as htmlmacro>
<#include "/common/handlefielderror.ftl">
<@htmlmacro.moduleDiv titleName="">
<div class="query-builder-no">
	<div class="query-part fn-rel fn-clear promt-div">
		<span id="flowTypeSpan" class="user-sList user-sList-radio">
	    	<span <#if states?default("0")=="0"> class="current"</#if> key="0">待我审核</span>
	    	<span <#if states?default("0")=="3"> class="current"</#if> key="3">我已审核</span>
	    	<span <#if states?default("0")=="1"> class="current"</#if> key="1">审核结束</span>
	    	<span <#if states?default("0")=="2"> class="current"</#if> key="2">已作废</span>
	    </span>
    </div>
</div>
<div id="mySurveyListDiv"></div>
<@htmlmacro.tableList class="public-table table-list table-list-edit mt-5">
  	<tr>
    	<th width="4%">序号</th>
    	<th width="7%">申请人</th>
    	<th width="9%">部门（科室）</th>
    	<th width="8%">出差地点</th>
    	<th width="7%">开始时间</th>
    	<th width="7%">结束时间</th>
    	<th width="7%">出差天数</th>
    	<th <#if states?default('0')=='2'>width="28%" <#else>width="35%"</#if>>出差事由</th>
    	<th width="8%">审核状态</th>
    	<#if states?default('0')=='2'>
    	<th width="7%">作废人</th>
    	</#if>
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
		    	<#if states=='2'>
		    	<td >${bussinessTrip.invalidUserName!}</td>
		    	</#if>
		    	<td class="t-center">
		    		<#if bussinessTrip.state=='2' && bussinessTrip.taskId??>
		    			<a href="javascript:void(0);" onclick="doAudit('${bussinessTrip.id!}','${bussinessTrip.taskId!}');">审核</a>
		    		<#else>
		    			<a href="javascript:void(0);" onclick="doInfo('${bussinessTrip.id!}');">查看</a>
		    			<#if bussinessTrip.state=='3'>
			    			<a href="javascript:void(0);" onclick="doInvalid('${bussinessTrip.id!}');">作废</a>
			    		</#if>
		    		</#if>
		    	</td>
    		</tr>
    	</#list>
    <#else>
    	<tr>
    		<#if states?default('0')=='2'>
    		<td colspan='11'><p class="no-data mt-50 mb-50">还没有任何记录哦！</p></td>
    		<#else>
    		<td colspan='10'><p class="no-data mt-50 mb-50">还没有任何记录哦！</p></td>
    		</#if>
	 	</tr>
    </#if>
</@htmlmacro.tableList>
<#if officeBusList?exists && officeBusList?size gt 0>
</#if>
<@htmlmacro.Toolbar container="#businessTripeDiv">
</@htmlmacro.Toolbar>
<script>

	function doAudit(id,taskId){
		load("#businessTripeDiv","${request.contextPath}/office/businesstrip/businessTrip-businessTripAudit.action?officeBusinessTrip.id="+id+"&taskId="+taskId);
	}
	function doInfo(id){
		load("#businessTripeDiv","${request.contextPath}/office/businesstrip/businessTrip-businessTripView.action?fromTab=2&&officeBusinessTrip.id="+id);
	}
function doInvalid(id){
		if(showConfirm("确定要作废该出差申请")){
			$.getJSON("${request.contextPath}/office/businesstrip/businessTrip-invalidBusinessTrip.action",{businessTripId:id},function(data){
				if(data.operateSuccess){
					showMsgSuccess(data.promptMessage,"",BusinessTripSearch);
				}else{
					showMsgError(data.errorMessage);
					return;
				}
		   }).error(function(XMLHttpRequest, textStatus, errorThrown){alert(XMLHttpRequest.status);});
		}
	}
	
	$(document).ready(function(){
		vselect();
		$('.user-sList-radio span').click(function(){
			$(this).addClass('current').siblings('span').removeClass('current');
			var states=$('#flowTypeSpan.user-sList-radio span.current').attr("key");
			load("#businessTripeDiv", "${request.contextPath}/office/businesstrip/businessTrip-businessTripList.action?states="+states);
		});
	});	
</script>
<script type="text/javascript" src="${request.contextPath}/static/js/myscript.js"></script>
</@htmlmacro.moduleDiv>