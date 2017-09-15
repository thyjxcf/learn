<#import "/common/htmlcomponent.ftl" as htmlmacro>
<@htmlmacro.moduleDiv titleName="客户查看">
<form id="queryForm">
	<input type="hidden" value="${searchCustomer.name!}" name="searchCustomer.name">
	<input type="hidden" value="${searchCustomer.followerName!}" name="searchCustomer.followerName">
	<input type="hidden" value="${searchCustomer.regionName!}" name="searchCustomer.regionName">
	<input type="hidden" value="${searchCustomer.region!}" name="searchCustomer.region">
	<input type="hidden" value="${searchCustomer.type!}" name="searchCustomer.type">
	<input type="hidden" value="${searchCustomer.deptId!}" name="searchCustomer.deptId">
	<input type="hidden" value="${(searchCustomer.startTime?string('yyyy-MM-dd HH:mm:ss'))?if_exists}" name="searchCustomer.startTime">
	<input type="hidden" value="${(searchCustomer.endTime?string('yyyy-MM-dd HH:mm:ss'))?if_exists}" name="searchCustomer.endTime">
	
</form>
<#if recordList?exists && recordList?size gt 0 && officeCustomerApply.applyType!='2'>
<div id="container">
	<p class="pt-10 pb-15"><span class="ui-tt">跟进记录</span></p>
	<table class="table-noborder">
		<tr>
			<th width="100">跟进人：</th>
			<td>${officeCustomerApply.followerName!}</td>
		</tr>
		<tr>
			<th>客户名称：</th>
			<td>${officeCustomerInfo.name!}</td>
		</tr>
	</table>
	<ul class="ui-axis">
	<#list recordList as item>
    	<li >
        	<span class="icon-round1"></span>
            <div class="item f-13">
            	<i></i>
                <p class="li"><span class="time f-10">${(item.createTime?string('yyyy-MM-dd HH:mm'))?default('')}</span><#if item.carbonCopyName?exists><span class="dt">抄送：</span><span class="dd">${item.carbonCopyName!}</span></p></#if>
                <p class="li"><span class="dt">跟进状态：</span><span class="dd">${appsetting.getMcode("DM-JZZT").get(item.progressState?default(""))}</span></p>
                <p class="li"><span class="dt">跟进信息：</span><span class="dd">${item.remark!}</span></p>
            </div>
        </li>
    </#list>
    </ul>
<p class="line my-20"></p>
</#if>

<div id="container">
	<p class="pt-10 pb-15"><span class="ui-tt">客户详情</span></p>
	<table    class="table-form table-list-edit" id="devTable">
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
		<tr>
			<th><span class="mr-5 c-red">*</span><span>进展情况：</span></th>
			<td>${appsetting.getMcode("DM-JZZT").get(officeCustomerInfo.progressState?default(""))}</td>
			<th><span class="mr-5 c-red">*</span><span>意向合作产品：</span></th>
			<td><#if officeCustomerInfo.productArray?exists && officeCustomerInfo.productArray?size gt 0>
			<#list officeCustomerInfo.productArray as item>${appsetting.getMcode("DM-OAYXHZCP").get(item?default(""))}&nbsp;</#list></#if></td>
		</tr>
		<tr>
			<th><span class="mr-5 c-red">*</span><span>联系人：</span></th>
			<td>${officeCustomerInfo.contact!}</td>
			<th><span class="mr-5 c-red">*</span><span>主要联系方式：</span></th>
			<td>${officeCustomerInfo.phone!}</td>
		</tr>
		<tr>
			<th>次要联系方式：</span></th>
			<td >${officeCustomerInfo.contactInfo!}</td>
			<th>申请时间：</span></th>
			<td>${(officeCustomerApply.applyDate?string("yyyy-MM-dd"))?if_exists}</td>
		</tr>
		<tr>
			<th>初审通过时间：</span></th>
			<td>${(officeCustomerApply.firstAuditTime?string("yyyy-MM-dd"))?if_exists}</td>
			<th>复审通过时间：</span></th>
			<td>${(officeCustomerApply.finallyAuditTime?string("yyyy-MM-dd"))?if_exists}</td>
		</tr>
	</table>
</div>
	<div id="footer"><p class="pt-20 t-center">
	    <a class="abtn-blue-big ml-5" href="javascript:void(0);" onclick="back('${placeType!}');">取消</a></p>
    </div>
<script>
	function back(placeType){
		if(placeType=="allCustomer"){
			load("#showListDiv","${request.contextPath}/office/customer/customer-getAllList.action?"+jQuery("#queryForm").serialize());
		}else{
			load("#showListDiv","${request.contextPath}/office/customer/customer-myCustomerList.action?"+jQuery("#queryForm").serialize());
		}
	}
</script>
</@htmlmacro.moduleDiv >