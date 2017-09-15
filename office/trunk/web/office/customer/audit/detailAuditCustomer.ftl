<#import "/common/htmlcomponent.ftl" as htmlmacro>
<#import "/common/commonmacro.ftl" as commonmacro>
<#import "/office/customer/customerCommon.ftl" as commonmacro1>
<@htmlmacro.moduleDiv titleName="客户查看">
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
			<th width="30%"><span class="mr-5 c-red">*</span>客户单位官方名称：</th>
			<td>${officeCustomerInfo.name!}</td>
		</tr>
		<tr>
			<th>客户单位地方名称：</span></th>
			<td>${officeCustomerInfo.nickName!}</td>
		</tr>
		<tr>
			<th><span class="mr-5 c-red">*</span><span>客户所在区域：</span></th>
			<td>${officeCustomerInfo.regionName!}</td>
		</tr>
		<tr>
			<th><span class="mr-5 c-red">*</span><span>客户类别：</span></th>
			<td>${appsetting.getMcode("DM-OAKHLB").get(officeCustomerInfo.type?default(""))}</td>
		</tr>
		<tr>
			<th><span class="mr-5 c-red">*</span><span>客户信息来源：</span></th>
			<td>${officeCustomerInfo.infoSource!}</td>
		</tr>
		<tr>
			<th><span class="mr-5 c-red">*</span><span>客户背景信息：</span></th>
			<td>${officeCustomerInfo.backgroundInfo!}</td>
		</tr>
		<tr>
			<th><span class="mr-5 c-red">*</span><span>进展情况：</span></th>
			<td>${appsetting.getMcode("DM-JZZT").get(officeCustomerInfo.progressState?default(""))}</td>
		</tr>
		<tr>
			<th><span class="mr-5 c-red">*</span><span>意向合作产品：</span></th>
			<td><#if officeCustomerInfo.productArray?exists && officeCustomerInfo.productArray?size gt 0>
			<#list officeCustomerInfo.productArray as item>${appsetting.getMcode("DM-OAYXHZCP").get(item?default(""))}&nbsp;</#list></#if></td>
		</tr>
		<tr>
			<th><span class="mr-5 c-red">*</span><span>联系人：</span></th>
			<td>${officeCustomerInfo.contact!}</td>
		</tr>
		<tr>
			<th><span class="mr-5 c-red">*</span><span>主要联系方式：</span></th>
			<td>${officeCustomerInfo.phone!}</td>
		</tr>
		<tr>
			<th>次要联系方式：</span></th>
			<td>${officeCustomerInfo.contactInfo!}</td>
		</tr>
		<input type="hidden" id="transType" value="${transType?default('')}">
	</table>
</div>
	<div id="footer"><p class="pt-20 t-center">
	    <a class="abtn-blue-big ml-5" h   ref="javascript:void(0);" onclick="doBack(0);">取消</a>
    </p>
    </div>
<script>
	function doBack(type){
		load("#customerAdminDiv","${request.contextPath}/office/customer/customer-audit.action");
	}	
</script>
</@htmlmacro.moduleDiv >