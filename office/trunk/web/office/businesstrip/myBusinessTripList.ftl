<#import "/common/htmlcomponent.ftl" as htmlmacro>
<#include "/common/handlefielderror.ftl">
<@htmlmacro.moduleDiv titleName="">
<div class="pub-table-wrap">
	<div class="pub-table-inner">
	<div class="query-builder-nobg mt-15" style="padding:0 0 5px 0;">  
	<div class="query-part">
		<div class="query-tt ml-10">
			<span class="fn-left">审核状态：</span>
		</div>
		<div class="select_box fn-left mr-10">
		<@htmlmacro.select style="width:150px;" valId="states" valName="states" myfunchange="myBusinessTrip" >
			<a val="" <#if states?default("") == "">class="selected"</#if>><span>全部</span></a>
    		<a val="1" <#if states?default("") == "1">class="selected"</#if>><span>待提交</span></a>
    		<a val="2" <#if states?default("") == "2">class="selected"</#if>><span>审核中</span></a>
    		<a val="3" <#if states?default("") == "3">class="selected"</#if>><span>审核通过</span></a>
    		<a val="4" <#if states?default("") == "4">class="selected"</#if>><span>审核不通过</span></a>
		</@htmlmacro.select></div>
		<a href="javascript:void(0);" onclick="doBusinessTripAdd();" class="abtn-orange-new fn-right mr-10">新增出差申请</a>
		<div class="fn-clear"></div>
	</div>
	</div>
	</div>
</div>
<div id="mySurveyListDiv"></div>
<@htmlmacro.tableList class="public-table table-list table-list-edit mt-5">
  	<tr>
    	<th width="6%">序号</th>
    	<th width="18%">出差地点</th>
    	<th width="10%">开始时间</th>
    	<th width="10%">结束时间</th>
    	<th width="8%">出差天数</th>
    	<th width="30%">出差事由</th>
    	<th width="8%">审核状态</th>
    	<th class="t-center" width="10%">操作</th>
    </tr>
    <#if officeBusList?exists && officeBusList?size gt 0>
    	<#list officeBusList as bussinessTrip>
    		<tr>
    			<td >${bussinessTrip_index+1}</td>
    			<td >${bussinessTrip.place!}</td>
    			<td >${(bussinessTrip.beginTime?string('yyyy-MM-dd'))?if_exists}</td>
    			<td >${(bussinessTrip.endTime?string('yyyy-MM-dd'))?if_exists}</td>
		    	<td >${bussinessTrip.days!}</td>
		    	<td>${bussinessTrip.tripReason!}</td>
		    	<td >
		    		<#if bussinessTrip.state=='1'>
		    			待提交
		    		<#elseif bussinessTrip.state=='2'>
		    			审核中
		    		<#elseif bussinessTrip.state=='3'>
		    			审核通过
		    		<#elseif bussinessTrip.state=='4'>
		    			审核不通过
		    		</#if>
		    	</td>
		    	<td class="t-center">
		    		<#if bussinessTrip.state?default('1')=='1'>
		    			<!--<a href="javascript:void(0);" onclick="doEdit('${bussinessTrip.id!}');"><img src="${request.contextPath}/static/images/icon/edit.png" alt="编辑"></a>
		    			<a href="javascript:void(0);" onclick="doDelete('${bussinessTrip.id!}');"><img src="${request.contextPath}/static/images/icon/del2.png" alt="删除"></a>-->
		    			<a href="javascript:void(0);" onclick="doEdit('${bussinessTrip.id!}');">编辑</a>
		    			<a href="javascript:void(0);" onclick="doDelete('${bussinessTrip.id!}');">删除</a>
		    		<#elseif bussinessTrip.state?default('1')=='2'>
		    		<a href="javascript:void(0);" onclick="doInfo('${bussinessTrip.id!}');">查看</a>
	    			<a href="javascript:void(0);" onclick="doRevoke('${bussinessTrip.id!}');">撤销</a>
		    		<#else>
		    			<!--<a href="javascript:void(0);" onclick="doInfo('${bussinessTrip.id!}');"><img src="${request.contextPath}/static/images/icon/view.png" alt="查看"></a>-->
		    			<a href="javascript:void(0);" onclick="doInfo('${bussinessTrip.id!}');">查看</a>
		    		</#if>
		    	</td>
    		</tr>
    	</#list>
    <#else>
    	<tr>
    		<td colspan='8'><p class="no-data mt-50 mb-50">还没有任何记录哦！</p></td>
	 	</tr>
    </#if>
</@htmlmacro.tableList>
<#if officeBusList?exists && officeBusList?size gt 0>
<@htmlmacro.Toolbar container="#businessTripeDiv">
</@htmlmacro.Toolbar>
</#if>
<script>
	function doBusinessTripAdd(){
	load("#businessTripeDiv", "${request.contextPath}/office/businesstrip/businessTrip-myBusinessTripEdit.action");
	}
	function doDelete(id){
		if(showConfirm("确定要删除该出差申请")){
			$.getJSON("${request.contextPath}/office/businesstrip/businessTrip-myBusinessTripDelete.action",{"officeBusinessTrip.id":id},function(data){
				if(data.operateSuccess){
					showMsgSuccess(data.promptMessage,"",myBusinessTrip);
				}else{
					showMsgError(data.errorMessage);
					return;
				}
		   }).error(function(XMLHttpRequest, textStatus, errorThrown){alert(XMLHttpRequest.status);});
		}
	}
	function doEdit(id){
		load("#businessTripeDiv","${request.contextPath}/office/businesstrip/businessTrip-myBusinessTripEdit.action?officeBusinessTrip.id="+id);
	}
	function doInfo(id){
		load("#businessTripeDiv","${request.contextPath}/office/businesstrip/businessTrip-myBusinessTripView.action?fromTab=1&&officeBusinessTrip.id="+id);
	}

	function doRevoke(id){
		if(showConfirm("确定要撤销该出差申请")){
			$.getJSON("${request.contextPath}/office/businesstrip/businessTrip-revokeBusinessTrip.action",{"businessTripId":id},function(data){
				if(data.operateSuccess){
					showMsgSuccess(data.promptMessage,"",myBusinessTrip);
				}else{
					showMsgError(data.errorMessage);
					return;
				}
		   }).error(function(XMLHttpRequest, textStatus, errorThrown){alert(XMLHttpRequest.status);});
		}
	}
</script>
<script type="text/javascript" src="${request.contextPath}/static/js/myscript.js"></script>
</@htmlmacro.moduleDiv>