<#import "/common/htmlcomponent.ftl" as htmlmacro>
<#assign NEEDAUDIT = stack.findValue("@net.zdsoft.office.util.Constants@APPLY_STATE_NEED_AUDIT") >
<#assign PASS = stack.findValue("@net.zdsoft.office.util.Constants@APPLY_STATE_PASS") >
<#assign UNPASS = stack.findValue("@net.zdsoft.office.util.Constants@APPLY_STATE_NOPASS") >
<@htmlmacro.showPrompt/>
<@htmlmacro.moduleDiv titleName="">
<div class="pub-table-inner">
<div class="pub-table-wrap" id="myWorkReportDiv">
<form name="form1" action="" method="post">
	<@htmlmacro.tableList id="tablelist">
	<tr>
		<th width="5%">序号</th>
		<th width="8%">签章人</th>
		<th width="10%">所属部门</th>
		<th width="26%">用印事由</th>
		<th width="10%">印章类型</th>
		<th width="10%">时间</th>
		<th width="8%">分管校长</th>
		<th width="7%">审核状态</th>
		<th width="8%">经办人</th>
		<th width="8%">操作</th>
	</tr>
	<#if officeSealList?exists && (officeSealList?size>0)>
		<#list officeSealList as officeSeal>
		    <tr>
                <td>${officeSeal_index+1}</td>
                <td>${officeSeal.createUserName!}</td>
                <td>${officeSeal.deptName!}</td>
                <td>${officeSeal.applyOpinion!}</td>
                <td>${officeSeal.sealName!}</td>
				<td>${(officeSeal.createTime?string('yyyy-MM-dd'))?if_exists}</td>
				<td>${officeSeal.auditUserName!}</td>
				<td>
					<#if officeSeal.state == NEEDAUDIT+''>
						待审核
	        		<#elseif officeSeal.state == PASS+''>
	                	已通过
	                <#elseif officeSeal.state == UNPASS+''>
	                	未通过
	                </#if>
				</td>
				<td>${officeSeal.manageUserName!}</td>
				<td>
					<#if officeSeal.state == NEEDAUDIT+''&&officeSeal.mark?default("")=="1">
					<a href="javascript:doSealAudit('${officeSeal.id!}');"><img src="${request.contextPath}/static/images/icon/check.png" alt="审核"></a> &nbsp;
					</#if>
					<#if sealManager&&(officeSeal.state == PASS+''||officeSeal.state == UNPASS+'')&&(officeSeal.manageUserId!)=="">
					<a href="javascript:doSealView('${officeSeal.id!}');"><img src="${request.contextPath}/static/images/icon/view.png" alt="查看"></a> &nbsp;
					</#if>
				</td>
			</tr>
		</#list>
	<#else>
	   <tr><td colspan="10"> <p class="no-data mt-50 mb-50">还没有任何记录哦！</p></td></tr>
	</#if>
	</@htmlmacro.tableList>
	<@htmlmacro.Toolbar container="#myWorkReportDiv"></@htmlmacro.Toolbar>
</form>
</div>
</div>
<script type="text/javascript" src="${request.contextPath}/static/js/myscript.js"></script>
</@htmlmacro.moduleDiv>
<script>
function doSealAudit(sealId){
	//openDiv("#sealAddLayer", "#sealAddLayer .close,#sealAddLayer .submit,#sealAddLayer .reset", "${request.contextPath}/office/sealmanage/sealmanage-sealManageAudit.action?officeSealId="+sealId, null, null, "900px");
	load("#sealDiv","${request.contextPath}/office/sealmanage/sealmanage-sealManageAudit.action?officeSealId="+sealId);
}

function doSealView(sealId){
	//openDiv("#sealAddLayer", "#sealAddLayer .close,#sealAddLayer .submit,#sealAddLayer .reset", "${request.contextPath}/office/sealmanage/sealmanage-sealManageView.action?officeSealId="+sealId, null, null, "900px");
	load("#sealDiv","${request.contextPath}/office/sealmanage/sealmanage-sealManageView.action?officeSealId="+sealId);
}

</script>
