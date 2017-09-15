<#import "/common/htmlcomponent.ftl" as htmlmacro>
<#import "/common/commonmacro.ftl" as commonmacro>
<#import "/office/customer/customerCommon.ftl" as commonmacro1>
<link rel="stylesheet" type="text/css" href="${request.contextPath}/static/css/public.css">
<link rel="stylesheet" type="text/css" href="${request.contextPath}/static/css/layout.css">
<link rel="stylesheet" type="text/css" href="${request.contextPath}/static/css/layout-default.css">
<link rel="stylesheet" type="text/css" href="${request.contextPath}/static/css/default.css">
<@htmlmacro.moduleDiv titleName="客户查看">
<form id="queryForm">
	<input type="hidden" value="${searchCustomer.name!}" name="searchCustomer.name">
	<input type="hidden" value="${searchCustomer.followerName!}" name="searchCustomer.followerName">
</form>
<div id="container">
	<div class="t-center data-total"><p class="table-dt fb18">客户详情</p></div>
	<table    class="table-form table-list-edit" id="devTable">
		<#-- <#if officeCustomerApply.id?default('')!=''>
		<tr>
			<th width="140">状态：</th>
			<td><#if officeCustomerApply.state?default()==5>初审中
				<#elseif officeCustomerApply.state?default()==6>终审中<#else>终审通过</#if></td>
		</tr>
		</#if>-->
		<tr>
			<th width="20%"><span class="mr-5 c-red">*</span>客户单位官方名称：</th>
			<td width="30%">${officeCustomerInfo.name!}</td>
			<th width="20%">客户单位地方名称：</span></th>
			<td width="30%">${officeCustomerInfo.nickName!}</td>
		</tr>
		<tr>
			<th><span class="mr-5 c-red">*</span><span>客户所在区域：</span></th>
			<td>${officeCustomerInfo.regionName!}</td>
			<th><span class="mr-5 c-red">*</span><span>客户类别：</span></th>
			<td>${appsetting.getMcode("DM-OAKHLB").get(officeCustomerInfo.type?default(""))}</td>
		</tr>
		<tr>
			<th><span class="mr-5 c-red">*</span><span>客户信息来源：</span></th>
			<td>${officeCustomerInfo.infoSource!}</td>
			<th><span class="mr-5 c-red">*</span><span>客户背景信息：</span></th>
			<td>${officeCustomerInfo.backgroundInfo!}</td>
		</tr>
		<#if libraryApply?default("")=='apply'>
			<tr>
				<th valign="top" class="pt-50"><span class="mr-5 c-red">*</span><span>进展情况：</span></th>
				<td valign="top" class="" colspan="3">
				<#if mcodedetailList?exists&& mcodedetailList?size gt 0>
				<ul class="ui-axis-x">
					<#list mcodedetailList as item>
					<li class="thisId <#if officeCustomerInfo.progressStateInt?default(0)!=0 &&officeCustomerInfo.progressStateInt?default(0) gt (item_index+1)>over<#elseif officeCustomerInfo.progressStateInt?default(0)==(item_index+1)> current </#if><#if (item_index+1)==mcodedetailList?size>last</#if>" id="${item.thisId!}">
						<p class="layer"><i></i><span>
							<#if item.thisId?default('')=='01'>了解客户基本意向
							<#elseif item.thisId?default('')=='02'>客户同意开设机构<#elseif item.thisId?default('')=='03'>客户已开通机构
							<#elseif item.thisId?default('')=='04'>客户已开设试听课程<#elseif item.thisId?default('')=='05'>未与用户签订合同
							<#elseif item.thisId?default('')=='06'>与用户签订合同<#elseif item.thisId?default('')=='07'>客户开设第一期正式课
							<#elseif item.thisId?default('')=='08'>客户在合同期内持续开正式课<#elseif item.thisId?default('')=='09'>合同期满，客户未续约
							</#if></span></p>
						<a href="#" class="round"></a>
						<p class="tt">${item.content!}</p>
					</li>
					</#list>
				</ul>
				<span id="errorPro" class="field_tip input-txt-warn-tip"></span>
				</#if>
			</td>
			</tr>
			<tr>
				<th><span class="mr-5 c-red">*</span><span>意向合作产品：</span></th>
				<td colspan='3'><#if officeCustomerInfo.productArray?exists && officeCustomerInfo.productArray?size gt 0>
				<#list officeCustomerInfo.productArray as item>${appsetting.getMcode("DM-OAYXHZCP").get(item?default(""))}&nbsp;</#list></#if></td>
			</tr>
		<#else>
			<tr>
				<th><span class="mr-5 c-red">*</span><span>进展情况：</span></th>
				<td>${appsetting.getMcode("DM-JZZT").get(officeCustomerInfo.progressState?default(""))}</td>
				<th><span class="mr-5 c-red">*</span><span>意向合作产品：</span></th>
				<td><#if officeCustomerInfo.productArray?exists && officeCustomerInfo.productArray?size gt 0>
				<#list officeCustomerInfo.productArray as item>${appsetting.getMcode("DM-OAYXHZCP").get(item?default(""))}&nbsp;</#list></#if></td>
			</tr>
		</#if>
		<tr>
			<th><span class="mr-5 c-red">*</span><span>联系人：</span></th>
			<td>${officeCustomerInfo.contact!}</td>
			<th><span class="mr-5 c-red">*</span><span>主要联系方式：</span></th>
			<td>${officeCustomerInfo.phone!}</td>
		</tr>
		<tr>
			<th>次要联系方式：</span></th>
			<td colspan='3'>${officeCustomerInfo.contactInfo!}</td>
		</tr>
		<#if transType?default("")=="trans">
		<tr>
			<th><#if clientManager><span class="mr-5 c-red">*</span></#if><span>跟进人：</span></th>
			<td colspan='3'><@commonmacro1.selectFollowUser idObjectId="followerId" nameObjectId="followerName" >
  	   			<input type="hidden" name="followerId" id="followerId" value="${officeCustomerApply.followerId?default('')}"> 
  	   			<input type="text" name="followerName" id="followerName" value="${officeCustomerApply.followerName?default('')}" class="select_current02" style="width:150px;" readonly="readonly">
				</@commonmacro1.selectFollowUser>
			</td>
		</tr>
		</#if>
		<input type="hidden" id="transType" value="${transType?default('')}">
	</table>
</div>
	<div id="footer"><p class="pt-20 t-center">
		<#if officeCustomerApply.id?default('')==''>
			<#if clientManager><a class="abtn-blue-big ml-5" href="javascript:void(0);" onclick="goTransNosubmit('${officeCustomerInfo.id!}');">确定</a>
	   		<#elseif transType?default("")=="trans"><a class="abtn-blue-big ml-5" href="javascript:void(0);" onclick="goApplyLibrary('${officeCustomerInfo.id!}');">提交审核</a>
	   		<#else><a class="abtn-blue-big ml-5" href="javascript:void(0);" onclick="goApplyLibrary('${officeCustomerInfo.id!}');">申请该客户</a>
	    	</#if>
	    	<a class="abtn-blue-big ml-5" href="javascript:void(0);" onclick="doBack(1);">取消</a>
	    <#else>
	    <a class="abtn-blue-big ml-5" h   ref="javascript:void(0);" onclick="doBack(0);">取消</a>
		</#if>
    </p>
    </div>
<script>
	var isSubmit = false;
	function goTransNosubmit(id){
		if(isSubmit){
			return;
		}
		var followerId=$("#followerId").val();
		if(followerId==''|| followerId==null){
			showMsgError("请选择跟进人");
			return ;
		}
		var progressState=$(".thisId.current").attr("id");
		if(!confirm("确认分派?")){
			return;
		}
		isSubmit = true;
		jQuery.ajax({
			url:"${request.contextPath}/office/customer/customer-addCustomerNoAudit.action?officeCustomerApply.followerId="+followerId+"&officeCustomerInfo.progressState="+progressState,
			type:"post", 
			data:{"officeCustomerInfo.id":id},
			dataType:"JSON",
			async:false,
			success:function(data){
				if(data.operateSuccess){
				 	showMsgSuccess(data.promptMessage,"",function(){
					load("#showListDiv","${request.contextPath}/office/customer/customer-customerLibraryList.action");			
					});
				}else{
					showMsgError(data.errorMessage);
					isSubmit = false;
				}
			}
 		});	
	}
	function goApplyLibrary(id){
		if(isSubmit){
			return;
		}
		if(!confirm("确认申请该客户?")){
			return;
		}
		var followerId="";
		if($("#transType").val()=="trans"){
			followerId=$("#followerId").val();
		}
		var progressState=$(".thisId.current").attr("id");
		isSubmit = true;
		jQuery.ajax({
			url:"${request.contextPath}/office/customer/customer-libraryApply.action?officeCustomerInfo.auditState=1&officeCustomerApply.followerId="+followerId+"&officeCustomerInfo.progressState="+progressState,
			type:"post", 
			data:{"officeCustomerInfo.id":id},
			dataType:"JSON",
			async:false,
			success:function(data){
				if(data.operateSuccess){
				 	showMsgSuccess(data.promptMessage,"",function(){
					load("#showListDiv","${request.contextPath}/office/customer/customer-customerLibraryList.action");			
					});
				}else{
					showMsgError(data.errorMessage);
					isSubmit = false;
				}
			}
 		});	
	}
	function doBack(type){
		if(type==0){
		 	load("#showListDiv","${request.contextPath}/office/customer/customer-applyList.action?"+jQuery("#queryForm").serialize());
		}else if(type==1){
			load("#showListDiv","${request.contextPath}/office/customer/customer-customerLibraryList.action?"+jQuery("#queryForm").serialize());
		}
	}	
	$(function(){
		$('.ui-axis-x li .round').click(function(e){
			e.preventDefault();
			var $li=$(this).parent('li');
			var myIndex=$li.index();
			$li.addClass('current').siblings('li').removeClass('current');
			$('.ui-axis-x li:lt('+myIndex+')').addClass('over');
			$('.ui-axis-x li:gt('+myIndex+')').removeClass('over');
		});
	});
</script>
</@htmlmacro.moduleDiv >