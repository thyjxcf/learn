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
		<@htmlmacro.select style="width:150px;" valId="states" valName="states" myfunchange="myGoOut" >
			<a val="" <#if states?default("") == "">class="selected"</#if>><span>全部</span></a>
    		<a val="1" <#if states?default("") == "1">class="selected"</#if>><span>待提交</span></a>
    		<a val="2" <#if states?default("") == "2">class="selected"</#if>><span>审核中</span></a>
    		<a val="3" <#if states?default("") == "3">class="selected"</#if>><span>审核通过</span></a>
    		<a val="4" <#if states?default("") == "4">class="selected"</#if>><span>审核不通过</span></a>
		</@htmlmacro.select></div>
		<a href="javascript:void(0);" onclick="doGoOutAdd();" class="abtn-orange-new fn-right mr-10">新增外出申请</a>
		<div class="fn-clear"></div>
	</div>
	</div>
	</div>
</div>
<div id="mySurveyListDiv"></div>
<@htmlmacro.tableList class="public-table table-list table-list-edit mt-5">
  	<tr>
    	<th width="5%">序号</th>
    	<th width="10%">开始时间</th>
    	<th width="10%">结束时间</th>
    	<th width="10%">外出时间(小时)</th>
    	<th width="10%">外出类型</th>
    	<th width="35%">外出事由</th>
    	<th width="10%">审核状态</th>
    	<th class="t-center" width="10%">操作</th>
    </tr>
    <#if officeGoOutList?exists && officeGoOutList?size gt 0>
    	<#list officeGoOutList as officeGoOut>
    		<tr>
    			<td >${officeGoOut_index+1}</td>
    			<td >${(officeGoOut.beginTime?string('yyyy-MM-dd HH:mm'))?if_exists}</td>
    			<td >${(officeGoOut.endTime?string('yyyy-MM-dd HH:mm'))?if_exists}</td>
		    	<td >${(officeGoOut.hours)?string('0.0')!}</td>
		    	<td ><#if officeGoOut.outType?default("")=='1'>因公外出<#elseif officeGoOut.outType?default("")=='2'>因私外出</#if></td>
		    	<td title="${officeGoOut.tripReason!}"><@htmlmacro.cutOff str='${officeGoOut.tripReason!}' length=30/></td>
		    	<td >
		    		<#if officeGoOut.state=='1'>
		    			待提交
		    		<#elseif officeGoOut.state=='2'>
		    			审核中
		    		<#elseif officeGoOut.state=='3'>
		    			审核通过
		    		<#elseif officeGoOut.state=='4'>
		    			审核不通过
		    		</#if>
		    	</td>
		    	<td class="t-center">
		    		<#if officeGoOut.state?default('1')=='1'>
		    			<a href="javascript:void(0);" onclick="doEdit('${officeGoOut.id!}');">编辑</a>
		    			<a href="javascript:void(0);" onclick="doDelete('${officeGoOut.id!}');">删除</a>
		    		<#elseif officeGoOut.state?default('1')=='2'>
		    		<a href="javascript:void(0);" onclick="doInfo('${officeGoOut.id!}');">查看</a>
	    			<a href="javascript:void(0);" onclick="doRevoke('${officeGoOut.id!}');">撤销</a>
		    		<#else>
		    			<a href="javascript:void(0);" onclick="doInfo('${officeGoOut.id!}');">查看</a>
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
<#if officeGoOutList?exists && officeGoOutList?size gt 0>
<@htmlmacro.Toolbar container="#goOutDiv">
</@htmlmacro.Toolbar>
</#if>
<script>
	function doGoOutAdd(){
		load("#goOutDiv", "${request.contextPath}/office/goout/goout-myGoOutEdit.action");
	}
	function doDelete(id){
		if(showConfirm("确定要删除该外出申请")){
			$.getJSON("${request.contextPath}/office/goout/goout-myGoOutDelete.action",{"officeGoOut.id":id},function(data){
				if(data.operateSuccess){
					showMsgSuccess(data.promptMessage,"",myGoOut);
				}else{
					showMsgError(data.errorMessage);
					return;
				}
		   }).error(function(XMLHttpRequest, textStatus, errorThrown){alert(XMLHttpRequest.status);});
		}
	}
	function doEdit(id){
		load("#goOutDiv","${request.contextPath}/office/goout/goout-myGoOutEdit.action?officeGoOut.id="+id);
	}
	function doInfo(id){
		load("#goOutDiv","${request.contextPath}/office/goout/goout-myGoOutView.action?officeGoOut.id="+id);
	}
	function doRevoke(id){
		if(showConfirm("确定要撤销该外出申请")){
			$.getJSON("${request.contextPath}/office/goout/goout-myGoOutRevoke.action",{"officeGoOut.id":id},function(data){
				if(data.operateSuccess){
					showMsgSuccess(data.promptMessage,"",myGoOut);
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