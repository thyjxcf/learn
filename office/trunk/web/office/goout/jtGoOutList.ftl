<#import "/common/htmlcomponent.ftl" as htmlmacro>
<#include "/common/handlefielderror.ftl">
<@htmlmacro.moduleDiv titleName="">

<@htmlmacro.tableList class="public-table table-list table-list-edit mt-5">
  	<tr>
    	<th width="5%">序号</th>
    	<th width="10%">外出时间</th>
    	<th width="10%">外出类型</th>
    	<th width="20%">外出人员</th>
    	<th width="35%">外出事由</th>
    	<th width="8%">审核状态</th>
    	<th class="t-center" width="10%">操作</th>
    </tr>
    <#if officeJtgoOuts?exists && officeJtgoOuts?size gt 0>
    	<#list officeJtgoOuts as officeJtgoOut>
    		<tr>
    			<td >${officeJtgoOut_index+1}</td>
		    	<td >${officeJtgoOut.days!}</td>
		    	<td >${appsetting.getMcode("DM-JTWC").get(officeJtgoOut.outType!)}</td>
		    	<td>${officeJtgoOut.tripPerson!}</td>
		    	<td title="${officeJtgoOut.tripReason!}"><@htmlmacro.cutOff str='${officeJtgoOut.tripReason!}' length=30/></td>
		    	<td >
		    		<#if officeJtgoOut.state=='1'>
		    			待提交
		    		<#elseif officeJtgoOut.state=='2'>
		    			审核中
		    		<#elseif officeJtgoOut.state=='3'>
		    			审核通过
		    		<#elseif officeJtgoOut.state=='4'>
		    			审核不通过
		    		</#if>
		    	</td>
		    	<td class="t-center">
		    		<#if officeJtgoOut.state?default('1')=='1'>
		    			<a href="javascript:void(0);" onclick="doEdit('${officeJtgoOut.id!}');">编辑</a>
		    			<a href="javascript:void(0);" onclick="doDelete('${officeJtgoOut.id!}');">删除</a>
		    		<#elseif officeJtgoOut.state?default('1')=='2'>
		    		<a href="javascript:void(0);" onclick="doInfo('${officeJtgoOut.id!}');">查看</a>
	    			<a href="javascript:void(0);" onclick="doRevoke('${officeJtgoOut.id!}');">撤销</a>
		    		<#else>
		    			<a href="javascript:void(0);" onclick="doInfo('${officeJtgoOut.id!}');">查看</a>
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
<#if officeJtgoOuts?exists && officeJtgoOuts?size gt 0>
<@htmlmacro.Toolbar container="#jtGoOutListDiv">
</@htmlmacro.Toolbar>
</#if>
<script>

	function doDelete(id){
		if(showConfirm("确定要删除该外出申请")){
			$.getJSON("${request.contextPath}/office/goout/goout-jtGoOutDelete.action",{"officeJtgoOut.id":id},function(data){
				if(data.operateSuccess){
					showMsgSuccess(data.promptMessage,"",doSearch);
				}else{
					showMsgError(data.errorMessage);
					return;
				}
		   }).error(function(XMLHttpRequest, textStatus, errorThrown){alert(XMLHttpRequest.status);});
		}
	}
	function doEdit(id){
		load("#jtGoOutListDiv","${request.contextPath}/office/goout/goout-jtGoOutEdit.action?officeJtgoOut.id="+id);
	}
	function doInfo(id){
		load("#jtGoOutListDiv","${request.contextPath}/office/goout/goout-jtGoOutView.action?officeJtgoOut.id="+id);
	}
	function doRevoke(id){
		if(showConfirm("确定要撤销该外出申请")){
			$.getJSON("${request.contextPath}/office/goout/goout-jtGoOutDelete.action",{"officeJtgoOut.id":id},function(data){
				if(data.operateSuccess){
					showMsgSuccess(data.promptMessage,"",doSearch);
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