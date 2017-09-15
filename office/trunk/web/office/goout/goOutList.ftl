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
    	<th width="5%">序号</th>
    	<th width="7%">申请人</th>
    	<th width="10%">开始时间</th>
    	<th width="10%">结束时间</th>
    	<th width="10%">外出时间(小时)</th>
    	<th width="9%">外出类型</th>
    	<th <#if states=='2'>width="28%"<#else>width="30%"</#if>>外出事由</th>
    	<th width="7%">审核状态</th>
    	<#if states=='2'>
    	<th width="7%">作废人</th>
    	</#if>
    	<th class="t-center" >操作</th>
    </tr>
    <#if officeGoOutList?exists && officeGoOutList?size gt 0>
    	<#list officeGoOutList as officeGoOut>
    		<tr>
    			<td >${officeGoOut_index+1}</td>
    			<td >${officeGoOut.applyUserName!}</td>
    			<td >${(officeGoOut.beginTime?string('yyyy-MM-dd HH:mm'))?if_exists}</td>
    			<td >${(officeGoOut.endTime?string('yyyy-MM-dd HH:mm'))?if_exists}</td>
		    	<td >${officeGoOut.desHours!}</td>
		    	<td ><#if officeGoOut.outType?default("")=='1'>因公外出<#elseif officeGoOut.outType?default("")=='2'>因私外出
		    		 <#elseif officeGoOut.outType?default("")=="3">春秋游<#elseif officeGoOut.outType?default("")=="4">教师培训</#if></td>
		    	<td title="${officeGoOut.tripReason!}"><@htmlmacro.cutOff str='${officeGoOut.tripReason!}' length=30/></td>
		    	<td >
		    		<#if officeGoOut.state=='2'>
		    			待审核
		    		<#elseif officeGoOut.state=='3'>
		    			审核通过
		    		<#elseif officeGoOut.state=='4'>
		    			审核不通过
		    		<#elseif officeGoOut.state=='8'>
		    			已作废
		    		</#if>
		    	</td>
		    	<#if states=='2'>
		    	<td >${officeGoOut.invalidUserName!}</td>
		    	</#if>
		    	<td class="t-center">
		    		<#if officeGoOut.state=='2'  && officeGoOut.taskId??>
		    			<a href="javascript:void(0);" onclick="doAudit('${officeGoOut.id!}','${officeGoOut.taskId!}','${officeGoOut.type!}');">审核</a>
		    		<#else>
		    			<a href="javascript:void(0);" onclick="doInfo('${officeGoOut.id!}','${officeGoOut.type!}');">查看</a>
			    		<#if officeGoOut.state=='3'>
			    			<a href="javascript:void(0);" onclick="doInvalid('${officeGoOut.id!}','${officeGoOut.type!}');">作废</a>
			    		</#if>
		    		</#if>
		    	</td>
    		</tr>
    	</#list>
    <#else>
    	<tr>
    		<td colspan='10'><p class="no-data mt-50 mb-50">还没有任何记录哦！</p></td>
	 	</tr>
    </#if>
</@htmlmacro.tableList>
<#if officeGoOutList?exists && officeGoOutList?size gt 0>
<@htmlmacro.Toolbar container="#goOutDiv">
</@htmlmacro.Toolbar>
</#if>
<script>

	function doAudit(id,taskId,type){
		if(type==0){
			load("#goOutDiv","${request.contextPath}/office/goout/goout-goOutAudit.action?officeGoOut.id="+id+"&taskId="+taskId);
		}
		if(type==1){
			load("#goOutDiv","${request.contextPath}/office/goout/goout-jtGoOutAudit.action?officeJtgoOut.id="+id+"&taskId="+taskId);
		}
	}
	function doInfo(id,type){
		if(type==0){
			load("#goOutDiv","${request.contextPath}/office/goout/goout-goOutView.action?officeGoOut.id="+id);
		}
		if(type==1){
			load("#goOutDiv","${request.contextPath}/office/goout/goout-jtGoOutView.action?officeJtgoOut.id="+id);
		}
	}
	function doInvalid(id,type){
		if(type==0){
			if(showConfirm("确定要作废该外出申请")){
				$.getJSON("${request.contextPath}/office/goout/goout-invalidLeave.action",{"officeGoOut.id":id},function(data){
					if(data.operateSuccess){
						showMsgSuccess(data.promptMessage,"",goOutSearch);
					}else{
						showMsgError(data.errorMessage);
						return;
					}
			   }).error(function(XMLHttpRequest, textStatus, errorThrown){alert(XMLHttpRequest.status);});
			}
		}
		if(type==1){
			if(showConfirm("确定要作废该集体外出申请")){
				$.getJSON("${request.contextPath}/office/goout/goout-invalidJtGoOut.action",{"officeJtgoOut.id":id},function(data){
					if(data.operateSuccess){
						showMsgSuccess(data.promptMessage,"",goOutSearch);
					}else{
						showMsgError(data.errorMessage);
						return;
					}
			   }).error(function(XMLHttpRequest, textStatus, errorThrown){alert(XMLHttpRequest.status);});
			}
		}
	}
	
	$(document).ready(function(){
		vselect();
		$('.user-sList-radio span').click(function(){
			$(this).addClass('current').siblings('span').removeClass('current');
			var states=$('#flowTypeSpan.user-sList-radio span.current').attr("key");
			load("#goOutDiv", "${request.contextPath}/office/goout/goout-goOutList.action?states="+states);
		});
	});
	
</script>
<script type="text/javascript" src="${request.contextPath}/static/js/myscript.js"></script>
</@htmlmacro.moduleDiv>