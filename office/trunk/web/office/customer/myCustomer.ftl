<#import "/common/htmlcomponent.ftl" as htmlmacro>
<#include "/common/handlefielderror.ftl">
<@htmlmacro.moduleDiv titleName="">
<form id="queryForm">
<div class="pub-table-wrap">
	<div class="pub-table-inner">
	<div class="query-builder-nobg mt-15" style="padding:0 0 5px 0;">  
	<div class="query-part">
	
		<div class="query-tt ml-10">
			<span class="fn-left">客户名称：</span>
		</div>
		<div class="fn-left">
			<input style="width:200px;" class="input-txt"  type="text" id="searchCustomer.name" name="searchCustomer.name" value="${searchCustomer.name!}">
		</div>
		
		<div class="query-tt ml-10">
			<span class="fn-left">跟进人：</span>
		</div>
		<div class="fn-left">
			<input style="width:100px;" class="input-txt"  type="text" id="searchCustomer.followerName" name="searchCustomer.followerName" value="${searchCustomer.followerName!}">
		</div>
		
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
    	<th width="15%">客户名称</th>
    	<th width="10%">跟进人</th>
    	<th width="10%">当前流程</th>
    	<th width="12%">状态</th>
    	<th width="8%">有效期</th>
    	<th width="15%">截止日期</th>
    	<th class="t-center">操作</th>
    </tr>
    <#if customerApplyList?exists && customerApplyList?size gt 0>
    	<#list customerApplyList as item>
    		<tr>	
    			<td> ${item_index+1}</td>    		
    			<td style="word-break:break-all; word-wrap:break-word;"> 
    				<#if  item.oneSelf?default(false)&& item.applyType?default("")!="2"&&(item.officeCustomerInfo.progressState?default("")!="05"||item.officeCustomerInfo.progressState?default("")!="09")><a href="javascript:void(0)" onClick = "edit('${item.id!}','${item.applyId!}');" class="ml-5" > ${item.officeCustomerInfo.name!}</a>
    				<#else><a href="javascript:void(0)" onClick = "goDetailRecord('${item.id!}','${item.applyId!}');" class="ml-5" >${item.officeCustomerInfo.name!}</a></#if></td>   
				<td> ${item.followerName!}</td> 		
    			<td> <#if item.applyType?default("")!="2"|| item.state?default(-1)==9>客户跟进
    				 <#else>延期申请
    				 </#if>
    			</td>    		
    			<td><span class="c-blue"><#if item.applyType?default("")=="2"&& item.state?default(-1)!=9>
	    				 <#if item.state?default(-1)==5>待初审
	    				 <#elseif item.state?default(-1)==6>待复审
	    				 <#elseif item.state?default(-1)==7>初审未通过
	    				 <#elseif item.state?default(-1)==8>复审未通过
	    				 <#--<#elseif item.state?default(-1)==9>复审通过-->
	    				 </#if></span>
	    				 <#if item.state?default(-1)==7>
	    				 	<@htmlmacro.cutOff4List str="${item.deptOpinion?default('')}" length=4 />
	    				 <#elseif item.state?default(-1)==8>
	    				 	<@htmlmacro.cutOff4List str="${item.operateOpinion?default('')}" length=4 />
	    				 </#if>
    			 <#else>${appsetting.getMcode("DM-JZZT").get(item.officeCustomerInfo.progressState?default(""))}
    			 </#if>
    			 </td>   		
			     <#if item.officeCustomerInfo.progressState?default("")=='06'||item.officeCustomerInfo.progressState?default("")=='07'||item.officeCustomerInfo.progressState?default("")=='08'><td>不限</td>   		
    				<td>----</td> 
    			<#else><td><#if item.validTime<=5><span class="mr-5 c-red">${item.validTime!}</span><#else>${item.validTime!}</#if></td>   		
    				   <td>${((item.deadline)?string('yyyy-MM-dd'))?if_exists}</td> 
    			</#if>  		
    			<td style="word-break:break-all; word-wrap:break-word;" class="t-center">
    				<#if  item.oneSelf?default(false)>
    					<#if item.applyType?default("")!="2" || item.state?default(0)==9>
    						<#if item.officeCustomerInfo.progressState?default("")!="05"&& item.officeCustomerInfo.progressState?default("")!="09">
    							<a href="javascript:void(0)" onClick = "edit('${item.id!}');" class="ml-5" > 编辑</a>
    							<a href="javascript:void(0)" onClick = "addRecord('${item.id!}');" class="ml-5" > 添加跟进记录</a>
    							<#if item.validTime?default(-1)<0 && item.applyType?default("")!="2" &&item.officeCustomerInfo.progressState?default("")!="06" && item.officeCustomerInfo.progressState?default("")!="07" && item.officeCustomerInfo.progressState?default("")!="08">
    								<a href="javascript:void(0)" onClick = "putOffApply('${item.id!}','putOff');" class="ml-5" > 延期申请</a>
    							</#if>
    						<#else><a href="javascript:void(0)" onClick = "goDetailRecord('${item.id!}');" class="ml-5" >查看</a>	
    						</#if>
    						<#if (regionLeader||deptLeader)&& (item.officeCustomerInfo.progressState?default("")=="04"|| item.officeCustomerInfo.progressState?default("")=="03"|| item.officeCustomerInfo.progressState?default("")=="02"|| item.officeCustomerInfo.progressState?default("")=="01")>
    							<a href="javascript:void(0)" onClick = "editTrans('${item.id!}','trans');" class="ml-5" >分派客户</a>
    						</#if>
    					<#else>
							<a href="javascript:void(0)" onClick = "goDetailRecord('${item.id!}');" class="ml-5" >查看</a>
							<#-- <#if item.state?default(0)==7><a href="javascript:void(0)" onClick = "seeReason('${item.deptOpinion!}');" class="ml-5" >不通过原因</a>
							<#elseif item.state?default(0)==8><a href="javascript:void(0)" onClick = "seeReason('${item.operateOpinion!}');" class="ml-5" >不通过原因</a>
							</#if>-->
    					</#if>
    				<#else>
    					<a href="javascript:void(0)" onClick = "goDetailRecord('${item.id!}');" class="ml-5" >查看</a>
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
<#if customerApplyList?exists     && customerApplyList?size gt 0>
	<@htmlmacro.Toolbar container="#showListDiv">
	</@htmlmacro.Toolbar>
</#if> 
<script>
	function putOffApply(id,transType){
		load("#showListDiv","${request.contextPath}/office/customer/customer-editMyCustomer.action?officeCustomerApply.id="+id+"&transType="+transType+"&"+jQuery("#queryForm").serialize());
	}
	function addRecord(id){
		load("#showListDiv","${request.contextPath}/office/customer/customer-addRecord.action?officeCustomerApply.id="+id+"&"+jQuery("#queryForm").serialize());
	}
	function goDetailRecord(id){
		load("#showListDiv","${request.contextPath}/office/customer/customer-goDetailRecord.action?officeCustomerApply.id="+id+"&"+jQuery("#queryForm").serialize());
	}
	function goDetail(id){
		load("#showListDiv","${request.contextPath}/office/customer/customer-goDetail.action?officeCustomerApply.id="+id+"&"+jQuery("#queryForm").serialize());
	}
	function edit(id){
		load("#showListDiv","${request.contextPath}/office/customer/customer-editMyCustomer.action?officeCustomerApply.id="+id+"&"+jQuery("#queryForm").serialize());
	}
	function editTrans(id,transType){
		load("#showListDiv","${request.contextPath}/office/customer/customer-editMyCustomer.action?officeCustomerApply.id="+id+"&transType="+transType+"&"+jQuery("#queryForm").serialize());
	}
	function doSearch(){
		load("#showListDiv","${request.contextPath}/office/customer/customer-myCustomerList.action?"+jQuery("#queryForm").serialize());
	}
</script>
</@htmlmacro.moduleDiv>