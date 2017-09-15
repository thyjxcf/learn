<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>		
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<#include "/common/css.ftl" />
<#include "/common/js.ftl">	
<#include "/base/auditflow/businessMacro.ftl" />
<#if businessMacroPage?exists><#include businessMacroPage /></#if>
<#if ownerMacroPage?exists><#include ownerMacroPage /></#if>

<#include "/common/handlefielderror.ftl">
<#import "/common/htmlcomponent.ftl" as h>
<#import "/common/commonmacro.ftl" as commonmacro>

<#assign canAudit = false><#-- 是否审核中-->
<#if audit.status == stack.findValue("@net.zdsoft.eis.base.auditflow.manager.entity.FlowAudit@STATUS_CHECKING")>
<#assign canAudit = true>
</#if>

<script>
function cancelOperate() {
	document.form1.action="audit-list.action";
	document.form1.submit();
}

var isSubmit = false;
function save(){
	if (isSubmit){
		return false;
	}
	
	if(!checkAllValidate()){
		isSubmit = false;
		return false;
	}
	
	var action = "audit-save.action";
	var commitForm = document.getElementById("form1");	
	var res=document.getElementsByName("audit.status");
	var resVal="2";
	for(j=0;j<res.length;j++){
		if(res[j].checked){
			resVal=res[j].value;
		}
	}
	if(window.confirm("确认审核吗？")){
		isSubmit = true;
		commitForm.action = action;	
		commitForm.submit();
	}else{
		isSubmit = false;
	}		
}
function downloadEvi(){
    document.getElementById('downEdiA').click();
}
</script>
</head>
<body>
<form name="form1" id="form1" method="post">
	<input type="hidden" name="apply.operateType" value="${apply.operateType}">	
	<input type="hidden" name="audit.id" value="${audit.id}">
	<input type="hidden" name="status" value="${status?default(0)}">
	<input type="hidden" name="businessType" value="${businessType}">
	<input type="hidden" name="modId" value="${modId?default('')}">
	
	<div class="table-all">
	<@h.tableDetail>
		<#if !apply.business.owner><@ownerSelect /></#if>
		<@businessCommonDetail/>
		<#-- businessDetail这是申请和审核共用的 businessDetailForAudit 是申请专用 -->
		<#if businessMacroPage?exists><@businessDetailForAudit/><@businessDetail/></#if>
		<#if canAudit && apply.operateType==stack.findValue("@net.zdsoft.eis.base.auditflow.manager.entity.FlowApply@OPERATE_TYPE_MODIFY")>
		<tr class="first"><th colspan="4" class="tt">修改信息：</th></tr>
			<#list apply.business.fieldModifications as x >
				<tr>
					<th style="width:15%;">
						${x.define}：
					</td>
					<td style="width:25%;" colspan="3">
						${x.wrappedOldValue} <b>改为</b> ${x.wrappedValue}
					</td>
				</tr>
			</#list>
		</#if>
		<tr class="first"><th colspan="4" class="tt">
				申请信息：
			</th>
		</tr>											
		<tr>
			<th style="width:15%;">
				类&nbsp;&nbsp;&nbsp;&nbsp;型：
			</th>
			<td style="width:25%;" colspan="3">
				${stack.findValue("@net.zdsoft.eis.base.auditflow.manager.entity.FlowApply@OPERATE_TYPE_MAP.get(apply.operateType)")}
			</td>
		</tr>
		<tr> 
			<th>
				原&nbsp;&nbsp;&nbsp;&nbsp;因：
			</th>
			<td colspan="3" style="word-break:break-all; word-wrap:break-word;">
				${apply.reason?default('')}
			</td>
		</tr>
		<tr class="first">
			<th colspan="4" class="tt">
				审核信息：
			</th>
		</tr>
		<tr>
			<th style="width:15%;">
				审核结果：
			</th>
			<td colspan="3">
				<#if canAudit>
				<input type="radio" name="audit.status" value="${stack.findValue("@net.zdsoft.eis.base.auditflow.manager.entity.FlowAudit@STATUS_AUDIT_PASS")}" checked>通过&nbsp;&nbsp;&nbsp;&nbsp;
				<input type="radio" name="audit.status" value="${stack.findValue("@net.zdsoft.eis.base.auditflow.manager.entity.FlowAudit@STATUS_AUDIT_REJECT")}">不通过
				<#else>
				<#if audit.status == stack.findValue("@net.zdsoft.eis.base.auditflow.manager.entity.FlowAudit@STATUS_AUDIT_PASS")>通过
				<#elseif audit.status == stack.findValue("@net.zdsoft.eis.base.auditflow.manager.entity.FlowAudit@STATUS_AUDIT_REJECT")>不通过
				</#if>
				</#if>
			</td>
		</tr>
		<#if audit.status == stack.findValue("@net.zdsoft.eis.base.auditflow.manager.entity.FlowAudit@STATUS_AUDIT_PASS")
			|| audit.status == stack.findValue("@net.zdsoft.eis.base.auditflow.manager.entity.FlowAudit@STATUS_AUDIT_REJECT")>
		<tr>
			<th>审核人：</th>
			<td>${audit.auditUsername?default('')}</td>
			<th>审核日期</th>
			<td>${(audit.auditDate?string('yyyy-MM-dd'))?if_exists}</td>
		</tr>
		</#if>
		<tr>
			<th style="width:15%;">
				<span>*</span>审核意见：
			</td>
			<td colspan="3">
				<#if canAudit>
				<textarea id="audit.opinion" msgName="审核意见" notNull="true" maxLength="240" name="audit.opinion"  class="area300" style="width:480px;" rows="4" cols="70" fieldtip="审核意见不能为空">${audit.opinion?default('')}</textarea>
				<br>(限240个字符。<a href="javascript:computeInputLength(document.getElementById('audit.opinion'));">计算字数</a>)
				<#else>
				${audit.opinion?default('')?replace("\r","<br>")}
				</#if>
			</td>
		</tr>											
	</@h.tableDetail>
</div>
<div class="table1-bt t-center">
	<#if canAudit>
	<span class='input-btn1 ml-10' id="Submit" name="Submit" onclick="save();"><button type="button">保存</button></span>
	</#if>
	<span class='input-btn1 ml-10' id="Submit2" name="Submit2" onclick="cancelOperate();"><button type="button">返回</button></span>						
</div>
</form>
<script>
<@h.scrollHeight totalHeight="jQuery(window.parent.document).height()" minusHeight="parent.jQuery('.tab-bg').height()+jQuery('.table1-bt').height()" trimming="7"/>
<#if businessMacroPage?exists><@scollHeightForEdit/></#if>
jQuery(document).ready(function(){	
	jQuery("#businessSpan").hide();	
	jQuery("#ownerSpan").hide();
	jQuery(".editSpan").remove();
	jQuery(".viewSpan").show();
})
</script>
</body>	
</html>