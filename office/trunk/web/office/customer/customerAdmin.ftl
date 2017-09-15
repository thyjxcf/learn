<#import "/common/htmlcomponent.ftl" as htmlmacro>
<#include "/common/handlefielderror.ftl">
<@htmlmacro.moduleDiv titleName="">
<div class="pub-tab">
	<ul class="pub-tab-list">
		<#--<input type="hidden" value="${clientManager?string('true','false')}" id="clientManager">-->
		<#if !clientManager><li class="current" onclick="sellCustomer(1);">客户申请</li>
							<li  onclick="sellCustomer(3);" class="">我的客户</li>
		<#--<#else><li class="current" onclick="sellCustomer(2);">新增客户</li>-->
		</#if>
		<#if clientManager><li   onclick="sellCustomer(4);" class="current">所有客户</li>
		<#elseif deptLeader><li  onclick="sellCustomer(4);" class="">本部客户</li>
		<#elseif regionLeader><li  onclick="sellCustomer(4);" class="">地区客户</li>
		</#if>
		<#if clientManager||deptLeader><li  onclick="sellCustomer(7);" class="">客户审核</li></#if>
		<li  onclick="sellCustomer(8);" class="">客户资源库</li>
	</ul>
</div>
<div id="showListDiv"></div>
<script>
	$(document).ready(function(){
		if(${clientManager?string('true','false')}==true){
			sellCustomer(4);
		}else{
			sellCustomer(1);
		}
	});
	function sellCustomer(state){
		switch(state){
			case 1:	load("#showListDiv","${request.contextPath}/office/customer/customer-applyList.action");
					break;
			case 2:	load("#showListDiv","${request.contextPath}/office/customer/customer-addCustomer.action");
					break;
			case 3:	load("#showListDiv","${request.contextPath}/office/customer/customer-myCustomerList.action");
					break;
			case 4:	load("#showListDiv","${request.contextPath}/office/customer/customer-getAllList.action");
					break;
			case 7:	load("#showListDiv","${request.contextPath}/office/customer/customer-audit.action");
					break;
			case 8:	load("#showListDiv","${request.contextPath}/office/customer/customer-customerLibraryList.action");
					break;
			default:
					break;
		}
	}
</script>
<script type="text/javascript" src="${request.contextPath}/static/js/myscript.js"></script>
</@htmlmacro.moduleDiv>