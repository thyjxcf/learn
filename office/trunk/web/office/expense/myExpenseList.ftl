<#import "/common/htmlcomponent.ftl" as common />
<@common.moduleDiv titleName="">
<form name="myExpenseForm" id="myExpenseForm" action="" method="post">
<@common.tableList id="tablelist">
	<tr>
		<th class="t-center" width="9%"><nobr>选择</nobr></th>
		<th width="12%">报销金额</th>
		<th width="12%">报销类别</th>
		<th width="40%">费用明细</th>
		<th width="12%">审核状态</th>
		<th width="15%" class="t-center">操作</th>
	</tr>
	<#if officeExpenseList?exists && officeExpenseList?size gt 0>
		<#list officeExpenseList as item>
			<tr style="word-break:break-all; word-wrap:break-word;">
				<td class="t-center"><#if item.state == "1"><p><span class="ui-checkbox">
		    		<input type="checkbox" class="chk" name="checkid" value="${item.id?default('')}">
		    		</span></p></#if>
		    	</td>
				<td>${(item.expenseMoney?string('0.00'))?if_exists}</td>
				<td>${item.expenseType!}</td>
				<td title="${item.detail!}"><@common.cutOff str='${item.detail!}' length=35/></td>
				<td><#if item.state == "1">待提交
					<#elseif item.state == "2">审核中
					<#elseif item.state == "3">审核通过
					<#elseif item.state == "4">审核不通过
					<#else></#if>
				</td>
				<td class="t-center">
				<#if item.state == "1">
					<a href="javascript:doExpenseEdit('${item.id!}');"><img alt="修改" src="${request.contextPath}/static/images/icon/edit.png"></a>
				<#else>
					<a href="javascript:doExpenseView('${item.id!}');"><img alt="查看" src="${request.contextPath}/static/images/icon/view.png"></a>
		     	</#if>
		     	</td>
			</tr>
		</#list>
	<#else>
		<tr>
	        <td colspan="6"><p class="no-data mt-50 mb-50">还没有数据哦！</p></td>
	   	</tr>
	</#if>
</@common.tableList>
<#if officeExpenseList?exists && officeExpenseList?size gt 0>
<@common.Toolbar container="#myExpenseListDiv">
	<span class="ui-checkbox ui-checkbox-all" data-all="no"><input type="checkbox" class="chk">全选</input></span>
	<a href="javascript:void(0);" id="btnDelete" class="abtn-blue"  onclick="doDelete()"> 删除</a>
</@common.Toolbar>
</#if>
</form>
<script>
function doDelete(){
	if(isCheckBoxSelect($("[name='checkid']")) == false){
		showMsgWarn("没有选要删除的报销记录，请先选择!");
		return;
	}
	
	if(!showConfirm('您确认要删除报销记录吗？')){
		return;
	}
	$("#btnDelete").attr("class", "abtn-unable");	
	var options = {
       url:'${request.contextPath}/office/expense/expense-myExpense-delete.action', 
       success : showReply,
       dataType : 'json',
       clearForm : false,
       resetForm : false,
       type : 'post'
    };
	
    $('#myExpenseForm').ajaxSubmit(options);
}
function showReply(data){
	$("#btnDelete").attr("class", "abtn-blue");
	if(!data.operateSuccess){
		   if(data.errorMessage!=null&&data.errorMessage!=""){
			   showMsgError(data.errorMessage);
			   return;
		   }else if(data.fieldErrorMap!=null){
			  $.each(data.fieldErrorMap,function(key,value){
				   addFieldError(key,value+"");
			  });
		   }else{
		   	   showMsgError(data.promptMessage);
			   return;
		   }
	}else{
	   		showMsgSuccess("删除成功! ","",doSearch);
			return;
	}
}
</script>
<script type="text/javascript" src="${request.contextPath}/static/js/myscript.js"></script>
</@common.moduleDiv>