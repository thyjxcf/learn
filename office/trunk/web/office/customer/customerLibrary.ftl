<#import "/common/htmlcomponent.ftl" as htmlmacro>
<#import "/common/commonmacro.ftl" as commonmacro>
<#include "/common/handlefielderror.ftl">
<@htmlmacro.moduleDiv titleName="">
<form id="queryForm">
<div class="pub-table-wrap">
	<div class="pub-table-inner">
	<div class="query-builder-nobg mt-15" style="padding:0 0 5px 0;">  
	<div class="query-part">
	
		<div class="query-tt ml-10">客户名称：</div>
		<div class="fn-left">
			<input style="width:200px;" class="input-txt"  type="text" id="searchCustomer.name" name="searchCustomer.name" value="${searchCustomer.name!}">
		</div>
		
		<div class="query-tt ml-30">所在区域：</div>
		<table width="180px" class="fn-left">
			<td width="100%">
				<@commonmacro.selectTree idObjectId="region" nameObjectId="regionName" treeUrl=request.contextPath+"/common/xtree/regionTree.action" width="400">
							<input name="searchCustomer.regionName" id="regionName"  msgName="户口所在地"  notNull="true" value="${searchCustomer.regionName?default('')?trim}" readonly type="text" class="input-txt f-left input-readonly" style="width:100%;" />
							<input name="searchCustomer.region" id="region" type="hidden" class="input" value="${searchCustomer.region?default('')}"> 
				</@commonmacro.selectTree>
			</td>
		</table>
		</div>
		 <div class="query-tt ml-10">客户类别：</div>
          <@htmlmacro.select  style="width:100px;" valName="searchCustomer.type" valId="type" >
          			<a val="" ><span>全部</span></a>
					${appsetting.getMcode("DM-OAKHLB").getHtmlTag(searchCustomer.type?default(''),false)}
		  </@htmlmacro.select> 
	    <a href="javascript:void(0)" onclick="doSearch();" class="abtn-blue fn-left ml-20">查找</a>
		<div class="fn-clear"></div>
	</div>
	</div>
	</div>
</div>
</form>
<@htmlmacro.tableList class="public-table table-list table-list-edit">
  	<tr>
  		<th width="5%">序号</th>
    	<th width="20%">客户名称</th>
    	<th width="20%">区域</th>
    	<th width="15%">客户类别</th>
    	<th width="15%">入库时间</th>
    	<th class="t-center">操作</th>
    </tr>
    <#if customerInfoList?exists && customerInfoList?size gt 0>
    	<#list customerInfoList as item>
    		<tr>	
    			<td> ${item_index+1}</td>    		
    			<td style="word-break:break-all; word-wrap:break-word;"><a href="javascript:void(0)" <#if clientManager>onClick = "doTransApply('${item.id!}','trans');"<#else>onClick = "doApply('${item.id!}');"</#if> class="ml-5" > ${item.name!}</a></td>    		
    			<td>${item.regionName!}</td>
    			<td>${appsetting.getMcode("DM-OAKHLB").get(item.type?default(""))} 
    			</td>
    			<td>${((item.addTime)?string('yyyy-MM-dd'))?if_exists}</td>   		
    			<td  class="t-center">
    					<#if clientManager><a href="javascript:void(0)" onClick = "doTransApply('${item.id!}','trans');" class="ml-5" >分派客户</a>
    					<#else><a href="javascript:void(0)" onClick = "doApply('${item.id!}');" class="ml-5" >客户申请</a>
    							<#if regionLeader||deptLeader><a href="javascript:void(0)" onClick = "doTransApply('${item.id!}','trans');" class="ml-5" >分派客户申请</a></#if>
    			 		</#if>
    			 </td>    		
    		</tr>
    	</#list>
    <#else>
    	<tr>
    		<td colspan='6'><p class="no-data mt-50 mb-50">还没有任何记录哦！</p></td>
	 	</tr>
    </#if>
</@htmlmacro.tableList>
<#if customerInfoList?exists && customerInfoList?size gt 0>
	<@htmlmacro.Toolbar container="#showListDiv">
	</@htmlmacro.Toolbar>
</#if> 
<script>
	function doSearch(){
		load("#showListDiv","${request.contextPath}/office/customer/customer-customerLibraryList.action?"+jQuery("#queryForm").serialize());
	}
	function doApply(id){
		load("#showListDiv","${request.contextPath}/office/customer/customer-goDetail.action?officeCustomerInfo.id="+id+"&"+jQuery("#queryForm").serialize()+"&libraryApply=apply");
	}
	function doTransApply(id,transType){
		load("#showListDiv","${request.contextPath}/office/customer/customer-goDetail.action?officeCustomerInfo.id="+id+"&transType="+transType+"&"+jQuery("#queryForm").serialize()+"&libraryApply=apply");
	}
</script>
</@htmlmacro.moduleDiv>