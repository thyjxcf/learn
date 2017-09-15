<#import "/common/htmlcomponent.ftl" as htmlmacro>
<@htmlmacro.showPrompt/>
<@htmlmacro.moduleDiv titleName="">
<script>
	
function doAuditSave(){
	clearMessages();
	if(!isActionable("#btnSave")){
		return;
	}
	
	
	//if(!checkAllValidate("#auditEditDiv")){
	//	return;
	//}
	
	var status = $('input[name="customer.auditStatus"]:checked').val();
	var opinion = $("#opinion").val();
	if("" != status && "3" == status){ 
		if(trim(opinion) == ""){
			showMsgWarn("审核不通过时意见不能为空");
			return;
		}
	}else{
	
	}
	
	if(!checkOverLen(document.getElementById("opinion"), 250, "审核意见")){
		return false;
	}
	
	$("#btnSave").attr("class", "abtn-unable-big");
	var options = {
       url:'${request.contextPath}/office/customer/customer-saveAudit.action', 
       success : showReply,
       dataType : 'json',
       clearForm : false,
       resetForm : false,
       type : 'post'
    };
	
    $('#mainform').ajaxSubmit(options);
}

function showReply(data){
	if(!data.operateSuccess){
	   if(data.promptMessage!=null&&data.promptMessage!=""){
		   showMsgError(data.promptMessage);
		   $("#btnSave").attr("class", "abtn-blue-big");
		   return;
	   }
	}else{
		showMsgSuccess("操作成功！","",function(){
			$("#btnSave").attr("class", "abtn-blue-big");
		  	doCancel();
		});
		return;
	}
}

function doCancel(){
	var roleCode = '${roleCode!}';
	var stateQuery = '${stateQuery!}';
	load("#auditListDiv", "${request.contextPath}/office/customer/customer-auditList.action?roleCode="+roleCode+"&stateQuery="+stateQuery);
}
	
</script>
<div id="auditEditDiv">
<form action="" method="post" name="mainform" id="mainform">
<input type="hidden" id="id" name="customer.id" value="${customer.id?default('')}"/>
<input type="hidden" id="auditId" name="customer.auditId" value="${customer.auditId?default('')}"/>
<input type="hidden" id="customerId" name="customer.customerId" value="${customer.customerId?default('')}"/>
<input type="hidden" id="applyType" name="customer.applyType" value="${customer.applyType?default('')}"/>
<input type="hidden" id="roleCode" name="roleCode" value="${roleCode?default('')}"/>
<p class="table-dt">审核详情</p>
	<table border="0" cellspacing="0" cellpadding="0" class="table-form">
			<#if customer.officeCustomerInfo?exists>
				<tr>
					<th width="20%"><span class="mr-5 c-red">*</span>客户单位官方名称：</th>
					<td width="30%">${customer.officeCustomerInfo.name!}</td>
					<th width="20%">客户单位地方名称：</span></th>
					<td width="30%">${customer.officeCustomerInfo.nickName!}</td>
				</tr>
				<tr>
					<th><span class="mr-5 c-red">*</span><span>客户所在区域：</span></th>
					<td>${customer.officeCustomerInfo.regionName!}</td>
					<th><span class="mr-5 c-red">*</span><span>客户类别：</span></th>
					<td>${appsetting.getMcode("DM-OAKHLB").get(customer.officeCustomerInfo.type?default(""))}</td>
				</tr>
				<tr>
					<th><span class="mr-5 c-red">*</span><span>客户信息来源：</span></th>
					<td>${customer.officeCustomerInfo.infoSource!}</td>
					<th><span class="mr-5 c-red">*</span><span>客户背景信息：</span></th>
					<td>${customer.officeCustomerInfo.backgroundInfo!}</td>
				</tr>
				<tr>
					<th><span class="mr-5 c-red">*</span><span>进展情况：</span></th>
					<td>${appsetting.getMcode("DM-JZZT").get(customer.officeCustomerInfo.progressState?default(""))}</td>
					<th><span class="mr-5 c-red">*</span><span>意向合作产品：</span></th>
					<td><#if customer.officeCustomerInfo.productArray?exists && customer.officeCustomerInfo.productArray?size gt 0>
					<#list customer.officeCustomerInfo.productArray as item>${appsetting.getMcode("DM-OAYXHZCP").get(item?default(""))}&nbsp;</#list></#if></td>
				</tr>
				<tr>
					<th><span class="mr-5 c-red">*</span><span>联系人：</span></th>
					<td>${customer.officeCustomerInfo.contact!}</td>
					<th><span class="mr-5 c-red">*</span><span>主要联系方式：</span></th>
					<td>${customer.officeCustomerInfo.phone!}</td>
				</tr>
				<tr>
					<th>次要联系方式：</span></th>
					<td colspan="3">${customer.officeCustomerInfo.contactInfo!}</td>
				</tr>
			</#if>
				<th colspan="4" class="t-center">申请信息</th>
				<tr>
					<th><span class="mr-5 c-red">*</span>申请人：</th>
					<td>${customer.applyUserName!}</td>
					<th><span class="mr-5 c-red">*</span>所属部门：</th>
					<td>${customer.deptName!}</td>
				</tr>
				<tr>
					<th><span class="mr-5 c-red">*</span>当前流程：</th>
					<td <#if customer.applyType?default("") != "3">colspan="3"</#if>>
						<#if customer.applyType?default("")=="0">新增客户申请
	    				 <#elseif customer.applyType?default("")=="1">老客户申请
	    				 <#elseif customer.applyType?default("")=="2">延期申请
	    				 <#elseif customer.applyType?default("")=="3">分派申请
	    				 </#if>
					</td>
					<#if customer.applyType?default("") == "3">
						<th><span class="mr-5 c-red">*</span>分派对象：</th>
						<td>${customer.followerName!}</td>
					</#if>
					
				</tr>
				
				<#if !businessTypeTwo?default(false)>
				<tr>
		            <th rowspan="2"><span class="mr-5 c-red">*</span>部门负责人意见：</th>
		            <#if roleCode?default('') == 'dept_head' && customer.auditStatus=="1">
		            <td colspan="3">
		                <span class="ui-radio <#if customer.auditStatus != "3">ui-radio-current</#if>" data-name="a"><input name="customer.auditStatus" type="radio" class="radio" value="2" <#if customer.auditStatus != "3">checked</#if>>初审通过</span>
		                <span class="ui-radio <#if customer.auditStatus = "3">ui-radio-current</#if>" data-name="a"><input name="customer.auditStatus" type="radio" class="radio" value="3" <#if customer.auditStatus=="3">checked</#if>>初审不通过</span>
		            </td>
		            </#if>
		        </tr>
		        <tr>
		            <td colspan="3"><textarea name="<#if roleCode?default('') == 'dept_head'>customer.opinion</#if>" <#if roleCode?default('') == 'dept_head' && customer.auditStatus=="1"> id="opinion"  notNull="" msgName="审核意见"<#else>readonly</#if> class="text-area my-10"  maxlength="250" style="width:850px;">${customer.deptOpinion?default('')}</textarea>
		            </td>
		        </tr>
		        </#if>
		        <tr>
		            <th rowspan="2"><span class="mr-5 c-red">*</span>运营人员意见：</th>
		            <#if roleCode?default('') == 'office_clientManager' && customer.auditStatus=="1">
		            <td colspan="3">
		                <span class="ui-radio <#if customer.auditStatus != "3">ui-radio-current</#if>" data-name="a"><input name="customer.auditStatus" type="radio" class="radio" value="2" <#if customer.auditStatus != "3">checked</#if>>复审通过</span>
		                <span class="ui-radio <#if customer.auditStatus = "3">ui-radio-current</#if>" data-name="a"><input name="customer.auditStatus" type="radio" class="radio" value="3" <#if customer.auditStatus=="3">checked</#if>>复审不通过</span>
		            </td>
		            </#if>
		        </tr>
		        <tr>
		            <td colspan="3"><textarea name="<#if roleCode?default('') == 'office_clientManager'>customer.opinion</#if>" <#if roleCode?default('') == 'office_clientManager' && customer.auditStatus=="1">id="opinion"  notNull="" msgName="审核意见"<#else>readonly</#if> class="text-area my-10"  maxlength="250" style="width:850px;">${customer.operateOpinion?default('')}</textarea>
		            </td>
		        </tr>
        </tr>
    </table>
     <p class="t-center pt-20">
     <#if customer.auditStatus == "1">
        <a href="javascript:void(0);" id="btnSave" onclick="doAuditSave();" class="abtn-blue-big">保存</a>
       </#if> 
        <a href="javascript:doCancel();" class="abtn-blue-big ml-10">返回</a>
    </p>
</form>
</div>
</@htmlmacro.moduleDiv>