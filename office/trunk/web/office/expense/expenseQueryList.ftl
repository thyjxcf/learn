<#import "/common/htmlcomponent.ftl" as common />
<@common.moduleDiv titleName="">
<form name="expenseAuditForm" id="expenseAuditForm" action="" method="post">
<@common.tableList id="tablelist">
	<tr>
		<th width="4%">序号</th>
		<th width="8%">申请人</th>
		<th width="10%">部门科室</th>
		<th width="8%">报销金额</th>
		<th width="10%">报销类别</th>
		<th width="40%">费用明细</th>
		<th width="10%">审核状态</th>
		<th width="10%" class="t-center">操作</th>
	</tr>
	<#if officeExpenseList?exists && officeExpenseList?size gt 0>
		<#list officeExpenseList as item>
			<tr style="word-break:break-all; word-wrap:break-word;">
				<td>${item_index+1}</td>
				<td>${item.applyUserName!}</td>
				<td>${item.deptName!}</td>
				<td>${(item.expenseMoney?string('0.00'))?if_exists}</td>
				<td>${item.expenseType!}</td>
				<td title="${item.detail!}"><@common.cutOff str='${item.detail!}' length=35/></td>
				<td><#if item.state == "2">待审核
					<#elseif item.state == "3">审核通过
					<#elseif item.state == "4">审核不通过
					<#else></#if>
				</td>
				<td class="t-center">
					<a href="javascript:doExpenseQueryView('${item.id!}');"><img alt="查看" src="${request.contextPath}/static/images/icon/view.png"></a>
		     	</td>
			</tr>
		</#list>
	<#else>
		<tr>
	        <td colspan="8"><p class="no-data mt-50 mb-50">还没有数据哦！</p></td>
	   	</tr>
	</#if>
</@common.tableList>
<#if officeExpenseList?exists && officeExpenseList?size gt 0>
<@common.Toolbar container="#myExpenseListDiv"/>
</#if>
</form>
<script>
function doExpenseQueryView(id){
	load("#adminDiv", "${request.contextPath}/office/expense/expense-myExpense-edit.action?officeExpense.id="+id+"&viewOnly=true&fromTab=3");
}
</script>
<script type="text/javascript" src="${request.contextPath}/static/js/myscript.js"></script>
</@common.moduleDiv>