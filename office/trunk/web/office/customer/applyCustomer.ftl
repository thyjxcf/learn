<#import "/common/htmlcomponent.ftl" as htmlmacro>
<#include "/common/handlefielderror.ftl">
<@htmlmacro.moduleDiv titleName="">
<div class="pub-table-wrap">
	<div class="pub-table-inner">
	<div class="query-builder-nobg mt-15" style="padding:0 0 5px 0;">  
	<div class="query-part">
	    <div class="fn-right ml-10">
	    	<a href="javascript:doAdd()" class="abtn-orange-new fn-right" >新增客户</a>
	    </div>
		<div class="fn-clear"></div>
	</div>
	</div>
	</div>
</div>
<@htmlmacro.tableList class="public-table table-list table-list-edit">
  	<tr>
  		<th width="5%">序号</th>
    	<th width="25%">客户名称</th>
    	<th width="20%">当前流程</th>
    	<th width="20%">状态</th>
    	<th class="t-center">操作</th>
    </tr>
    <#if customerApplyList?exists && customerApplyList?size gt 0>
    	<#list customerApplyList as item>
    		<tr>	
    			<td> ${item_index+1}</td>    		
    			<td style="word-break:break-all; word-wrap:break-word;">
    			<#if item.applyType?default("")=="0">
    				<#if item.state?default(-1)==5||item.state?default(-1)==6||item.state?default(-1)==9><a href="javascript:void(0)" onClick = "goDetail('${item.id!}');" class="ml-5" > ${item.officeCustomerInfo.name!}</a>
    				<#else><a href="javascript:void(0)" onClick = "edit('${item.id!}','${item.applyId}');" class="ml-5" >${item.officeCustomerInfo.name!}</a> </#if>   		
    			<#else><a href="javascript:void(0)" onClick = "goDetail('${item.id!}');" class="ml-5" > ${item.officeCustomerInfo.name!}</a>
    			</#if>
    			</td> 
    			<td> <#if item.applyType?default("")=="0">新增客户申请
    				 <#elseif item.applyType?default("")=="1">老客户申请
    				 <#elseif item.applyType?default("")=="3">分派申请
    				 </#if>
    			</td>    		
    			<td> <span class="c-blue">
    				 <#if item.state?default(-1)==5>待初审
    				 <#elseif item.state?default(-1)==6>待复审
    				 <#elseif item.state?default(-1)==7>初审未通过
    				 <#elseif item.state?default(-1)==8>复审未通过
    				 <#elseif item.state?default(-1)==9>复审通过
    				 <#else>未提交审核
    				 </#if>
    				 </span>
    				  <#if item.state?default(-1)==7>
    				 	<@htmlmacro.cutOff4List str="${item.deptOpinion?default('')}" length=4 />
    				  <#elseif item.state?default(-1)==8>
    				 	<@htmlmacro.cutOff4List str="${item.operateOpinion?default('')}" length=4 />
    				  </#if>
    			</td>   		
    			<td style="word-break:break-all; word-wrap:break-word;" class="t-center">
    				<#if  item.applyType?default("")=="0">
	    				 <#if item.status?default(-1)==0>
	    					<a href="javascript:void(0)" onClick = "edit('${item.id!}','${item.applyId}');" class="ml-5" >编辑</a>  
    						<a href="javascript:void(0)" onClick = "goApply('${item.id!}','${item.applyId}');" class="ml-5" >提交</a> 
	    					<a href="javascript:void(0)" onClick = "deleteC('${item.id!}','${item.applyId}');" class="ml-5" >删除</a>  
	    				 <#elseif item.state?default(-1)==5||item.state?default(-1)==6||item.state?default(-1)==9>
	    				 	<a href="javascript:void(0)" onClick = "goDetail('${item.id!}');" class="ml-5" >查看</a>  
	    				 <#elseif item.state?default(-1)==7||item.state?default(-1)==8>
	    					<a href="javascript:void(0)" onClick = "edit('${item.id!}','${item.applyId}');" class="ml-5" >编辑</a>  
	    					<a href="javascript:void(0)" onClick = "deleteC('${item.id!}','${item.applyId}');" class="ml-5" >删除</a>  
	    					<#--<a href="javascript:void(0)" onClick = "seeReason('<#if item.state==7>${item.deptOpinion!}<#else>${item.operateOpinion!}</#if>');" class="ml-5" >不通过原因</a>-->  
	    				 </#if>
	    			<#elseif item.applyType?default("")=="1">
    						<a href="javascript:void(0)" onClick = "goDetail('${item.id!}');" class="ml-5" >查看</a>
							<#--<#if item.state?default(-1)==7>
								 <#if item.officeCustomerInfo.state?default(-1)=2>
								<a href="javascript:void(0)" onClick = "applyAgain('${item.id!}','${item.applyId}');" class="ml-5" >再次申请</a> 
								<a href="javascript:void(0)" onClick = "giveUp('${item.id!}','${item.applyId}');" class="ml-5" >放弃申请</a> 
								</#if>
		    					<a href="javascript:void(0)" onClick = "seeReason('${item.deptOpinion!}');" class="ml-5" >不通过原因</a>  
							<#elseif item.state==8><a href="javascript:void(0)" onClick = "seeReason('${item.operateOpinion!}');" class="ml-5" >不通过原因</a>
							</#if> -->
					<#elseif item.applyType?default("")=="3">
						<a href="javascript:void(0)" onClick = "goDetail('${item.id!}');" class="ml-5" >查看</a>
						<#-- <#if item.state?default(-1)==7||item.state?default(-1)==8>
						<a href="javascript:void(0)" onClick = "seeReason('<#if item.state==7>${item.deptOpinion!}<#else>${item.operateOpinion!}</#if>');" class="ml-5" >不通过原因</a></#if>-->
    				</#if>
    			 </td>    		
    		</tr>
    	</#list>
    <#else>
    	<tr>
    		<td colspan='5'><p class="no-data mt-50 mb-50">还没有任何记录哦！</p></td>
	 	</tr>
    </#if>
</@htmlmacro.tableList>
<#if customerApplyList?exists && customerApplyList?size gt 0>
	<@htmlmacro.Toolbar container="#showListDiv">
	</@htmlmacro.Toolbar>
</#if> 
<script>
	var isSubmit = false;
	function applyAgain(id,applyId){
		if(isSubmit){
			return;
		}
		if(!confirm("确认再次申请?")){
			return;
		}
		var progressState=$(".thisId.current").attr("id");
		isSubmit = true;
		jQuery.ajax({
			url:"${request.contextPath}/office/customer/customer-applyAgain.action?applyId="+applyId,
			type:"post", 
			data:{"officeCustomerApply.id":id},
			dataType:"JSON",
			async:false,
			success:function(data){
				if(data.operateSuccess){
					showMsgSuccess(data.promptMessage,"",function(){
						load("#showListDiv","${request.contextPath}/office/customer/customer-applyList.action");
					});			
				}else{
					showMsgError(data.errorMessage);
					isSubmit = false;
				}
			}
 		});	
	}
	function giveUp(id,applyId){
		if(isSubmit){
			return;
		}
		if(!confirm("确认放弃申请?")){
			return;
		}
		isSubmit = true;
		jQuery.ajax({
			url:"${request.contextPath}/office/customer/customer-giveUp.action?applyId="+applyId,
			type:"post", 
			data:{"officeCustomerApply.id":id},
			dataType:"JSON",
			async:false,
			success:function(data){
				if(data.operateSuccess){
				 	showMsgSuccess(data.promptMessage,"",function(){
					load("#showListDiv","${request.contextPath}/office/customer/customer-applyList.action");			
					});
				}else{
					showMsgError(data.errorMessage);
					isSubmit = false;
				}
			}
 		});	
	}
	function goApply(id,applyId){
		if(isSubmit){
			return;
		}
		if(!confirm("确认提交?")){
			return;
		}
		isSubmit = true;
		jQuery.ajax({
			url:"${request.contextPath}/office/customer/customer-goApply.action?applyId="+applyId,
			type:"post", 
			data:{"officeCustomerApply.id":id},
			dataType:"JSON",
			async:false,
			success:function(data){
				if(data.operateSuccess){
				 	showMsgSuccess(data.promptMessage,"",function(){
					load("#showListDiv","${request.contextPath}/office/customer/customer-applyList.action");			
					});
				}else{
					showMsgError(data.errorMessage);
					isSubmit = false;
				}
			}
 		});	
	}
    function deleteC(id,applyId){
		if(isSubmit){
			return;
		}
		if(!confirm("确认删除?")){
			return;
		}
		isSubmit = true;
		jQuery.ajax({
			url:"${request.contextPath}/office/customer/customer-delete.action?applyId="+applyId,
			type:"post", 
			data:{"officeCustomerApply.id":id},
			dataType:"JSON",
			async:false,
			success:function(data){
				if(data.operateSuccess){
				 	showMsgSuccess(data.promptMessage,"",function(){
					load("#showListDiv","${request.contextPath}/office/customer/customer-applyList.action");			
					});
				}else{
					showMsgError(data.errorMessage);
					isSubmit = false;
				}
			}
 		});	
    }
	function edit(id,applyId){
		load("#showListDiv","${request.contextPath}/office/customer/customer-addCustomer.action?officeCustomerApply.id="+id+"&applyId="+applyId);
	}
	function goDetail(id){
		load("#showListDiv","${request.contextPath}/office/customer/customer-goDetail.action?officeCustomerApply.id="+id);
	}
	function doAdd(){
		load("#showListDiv","${request.contextPath}/office/customer/customer-addCustomer.action");
	}
</script>
</@htmlmacro.moduleDiv>