<#-- 审核意见  查看-->
<#macro auditView>
	<@h.tableDetail>
	<tr class="first"><th colspan="4" class="tt">审核信息：<#if apply.status == "1">还未审核</#if></th></tr> 
	<#if apply.audits?exists>
		<#list apply.audits as x>
			<tr class="first"><th colspan="4" class="tt">[${r.auditUnitName}]单位的审核信息：</th></tr>		
			<tr> 
				<th style="width:15%;">审核结果：</th>
				<td colspan="3">
					<#if x.status == stack.findValue("@net.zdsoft.eis.base.auditflow.manager.entity.FlowAudit@STATUS_AUDIT_PASS")>通过
					<#elseif x.status == stack.findValue("@net.zdsoft.eis.base.auditflow.manager.entity.FlowAudit@STATUS_AUDIT_REJECT")>不通过
					<#else>未审核</#if>
				</td>
			</tr>		
			<tr> 
				<th style="width:15%;">审核意见：</th>
				<td colspan="3">${x.opinion?default('')?replace("\r","<br>")}</td>
			</tr>		
		</#list>
	</#if>
	</@h.tableDetail>
</#macro>

<#-- 审核意见  维护-->
<#macro auditView>
</#macro>