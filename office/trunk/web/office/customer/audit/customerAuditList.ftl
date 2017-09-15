<#import "/common/htmlcomponent.ftl" as htmlmacro>
<@htmlmacro.showPrompt/>
<@htmlmacro.moduleDiv titleName="">
<script>
function doAudit(id, applyid, auditid){
	var roleCode = '${roleCode!}';
	var stateQuery = '${stateQuery!}';
	var str = "?customer.id="+id+"&applyId="+applyid+"&auditId="+auditid+"&roleCode="+roleCode+"&stateQuery="+stateQuery;
	load("#auditListDiv","${request.contextPath}/office/customer/customer-auditEdit.action"+str);
}

function goDetail(id){
	load("#customerAdminDiv","${request.contextPath}/office/customer/customer-goAuditDetail.action?officeCustomerApply.id="+id);
}

</script>
<div class="pub-table-inner">
<form name="form1" action="" method="post">
	<@htmlmacro.tableList id="tablelist">
	<tr>
		<th width="5%">序号</th>
		<th width="20%">客户名称</th>
		<th width="10%">当前流程</th>
		<th width="10%">申请人</th>
		<th width="12%">所属部门</th>
		<th width="10%">申请时间</th>
		<th width="10%">初审通过时间</th>
		<th width="13%">状态</th>
		<th class="t-center" width="">操作</th>
	</tr>
	<#if customerApplyList?exists && (customerApplyList?size>0)>
		<#list customerApplyList as ent>
		    <tr>
		    	<td>${ent_index+1}</td>
		    	<td>
		    		<#if ent.officeCustomerInfo?exists>
		    		<a href="javascript:void(0)" onClick = "goDetail('${ent.id!}');" class="ml-5" > ${ent.officeCustomerInfo.name!}</a>
		    		</#if>
		    	</td>
		    	<td>
		    		<#if ent.applyType?default("")=="0">新增客户申请
    				 <#elseif ent.applyType?default("")=="1">老客户申请
		    		<#elseif ent.applyType?default("")=="2">延期申请
    				 <#elseif ent.applyType?default("")=="3">分派申请
    				 </#if>
		    	</td>
		    	<td>${ent.applyUserName!}</td>
		    	<td>${ent.deptName!}</td>
		    	<td>${(ent.applyDate?string('yyyy-MM-dd'))?if_exists}</td>
		    	<td>${(ent.firstAuditTime?string('yyyy-MM-dd'))?if_exists}</td>
		    	</td>
				<td><span class="c-blue">
					 <#if ent.state?default(0)=5>待初审
    				 <#elseif ent.state?default(0)==6>待复审
    				 <#elseif ent.state?default(0)==7>初审未通过
    				 <#elseif ent.state?default(0)==8>复审未通过
    				 <#else>复审通过
    				 </#if>
    				 </span>
    				 <#if ent.auditStatus?default("")=="3">
    				 <@htmlmacro.cutOff4List str="${ent.opinion?default('')}" length=4 />
    				 </#if>
				</td>
				<td class="t-center">
					<#if ent.auditStatus="1">
					<a href="javascript:doAudit('${ent.id}', '${ent.applyId}', '${ent.auditId}');"><img alt="审核" src="${request.contextPath}/static/images/icon/check.png"></a>
					<#else>
					<a href="javascript:doAudit('${ent.id}', '${ent.applyId}', '${ent.auditId}');"><img alt="查看" src="${request.contextPath}/static/images/icon/view.png"></a>
					</#if>
				</td>
			</tr>
		</#list>
	<#else>
	   <tr><td colspan="100"><p class="no-data mt-50 mb-50">还没有任何记录哦！</p></td></tr>
	</#if>
	</@htmlmacro.tableList>	
	<@htmlmacro.Toolbar container="#auditListDiv"></@htmlmacro.Toolbar>
</form>
</div>
<script type="text/javascript" src="${request.contextPath}/static/js/myscript.js"></script>
</@htmlmacro.moduleDiv>