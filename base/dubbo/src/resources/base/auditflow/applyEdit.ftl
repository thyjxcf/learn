<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<#import "/common/htmlcomponent.ftl" as h>
<#include "/base/auditflow/businessMacro.ftl" />
<#if businessMacroPage?exists><#include businessMacroPage /></#if>
<#if ownerMacroPage?exists><#include ownerMacroPage /></#if>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<#include "/common/css.ftl" />
<#include "/common/js.ftl">
<#include "/common/handlefielderror.ftl">
<#import "/common/commonmacro.ftl" as commonmacro>
<script language="JavaScript" src="${request.contextPath}/static/js/buffalo.js"></script>

<#assign canModify=false><#-- 是否可修改信息-->
<#assign addApply=false><#-- 是否是增加申请-->
<#if apply.status == stack.findValue("@net.zdsoft.eis.base.auditflow.manager.entity.FlowApply@STATUS_PREPARING") || apply.status == stack.findValue("@net.zdsoft.eis.base.auditflow.manager.entity.FlowApply@STATUS_AUDIT_REJECT")>
	<#assign canModify=true>
</#if>
<#if apply.id?default('') == ''>
	<#assign addApply=true>
</#if>

<script>
<!--返回-->
function cancelOperate() {
	document.form1.action="apply-list.action";
	document.form1.submit();
}

//保存
var isSubmit = false;
function save(){
	if (isSubmit){
		return false;
	}
	isSubmit = true;
	
	var operateType = jQuery("#operateType").val();
	
	//修改和删除：必须选择某条业务
	if(operateType != ${stack.findValue("@net.zdsoft.eis.base.auditflow.manager.entity.FlowApply@OPERATE_TYPE_ADD")}){
		if(jQuery('#businessId').val() == ''){
			showMsgWarn("请选择要操作的记录");
			isSubmit = false;
			return;
		}
	}
	
	//公共校验，自身业务的一些校验
	if(operateType != ${stack.findValue("@net.zdsoft.eis.base.auditflow.manager.entity.FlowApply@OPERATE_TYPE_DELETE")}){ 
		if(!checkAllValidate()){
			isSubmit = false;
			return false;
		}
		<#if businessMacroPage?exists>
		if(!validate()){
			isSubmit = false;
			return false;
		}
		</#if>
	}else{
		if (jQuery('#reason').val() == ""){
			addFieldError('reason', "原因 不能为空！");
			isSubmit = false;
			return false;
		}
		
		var valueLength = getLength(jQuery('#reason').val());
		if(valueLength > 240){
			addFieldError('reason', "原因 长度为" + valueLength + "个字符，超出了最大长度限制：240个字符");
			isSubmit = false;
			return false;
		}
		jQuery('.editSpan :input').attr("disabled",false);
	}
	var action = "apply-save.action";
	var commitForm = document.getElementById("form1");	
	commitForm.action = action;	
	commitForm.submit();		
}

function formAction1(frmAction){
	<#if apply.status == stack.findValue("@net.zdsoft.eis.base.auditflow.manager.entity.FlowApply@STATUS_IN_AUDIT") >
	if(!confirm("确认进行“撤消申请”操作")){
		return ;
	}
	</#if>
	<#if apply.status == stack.findValue("@net.zdsoft.eis.base.auditflow.manager.entity.FlowApply@STATUS_PREPARING")
			|| apply.status == stack.findValue("@net.zdsoft.eis.base.auditflow.manager.entity.FlowApply@STATUS_AUDIT_REJECT")>
	var operateType = jQuery("#operateType").val();
	if(operateType != ${stack.findValue("@net.zdsoft.eis.base.auditflow.manager.entity.FlowApply@OPERATE_TYPE_ADD")}){
		if(jQuery('#businessId').val() == ''){
			showMsgWarn("请选择要操作的记录");
			isSubmit = false;
			return;
		}
	}
	
	//公共校验，自身业务的一些校验
	if(operateType != ${stack.findValue("@net.zdsoft.eis.base.auditflow.manager.entity.FlowApply@OPERATE_TYPE_DELETE")}){ 
		if(!checkAllValidate()){
			isSubmit = false;
			return false;
		}
		<#if businessMacroPage?exists>
		if(!validate()){
			isSubmit = false;
			return false;
		}
		</#if>
	}else{
		if (jQuery('#reason').val() == ""){
			addFieldError('reason', "原因 不能为空！");
			isSubmit = false;
			return false;
		}
		
		var valueLength = getLength(jQuery('#reason').val());
		if(valueLength > 240){
			addFieldError('reason', "原因 长度为" + valueLength + "个字符，超出了最大长度限制：240个字符");
			isSubmit = false;
			return false;
		}
		jQuery('.editSpan :input').attr("disabled",false);
	}
	</#if>
	if(isSubmit) {		
    	return;
    }
	isSubmit = true;
	var commitForm = document.getElementById("form1");	
	commitForm.action = frmAction;	
	commitForm.submit();
}

function changeOperateType(){
	businessControl();
	clearBusiness();
}
function downloadEvi(){
    document.getElementById('downEdiA').click();
}

function delFile(businessType,employeeAwardDtoId){
	var buffalo=new Buffalo('');
    buffalo.async = false; //同步执行 
    var downdiv=document.getElementById("downfileDiv");
	var updiv=document.getElementById("upfileDiv");
    downdiv.style.display="none";
    updiv.style.display="block";
	buffalo.remoteActionCall("${request.contextPath}/personnel/applyaudit/apply-buffalo.action","deleteFile",[businessType,employeeAwardDtoId],function(reply){
		var result=reply.getResult();
		
		showMsgSuccess("附件删除成功");
	});
}

</script>
</head>
<body>
<form name="form1" id="form1" method="post" enctype="multipart/form-data">
	<input type="hidden" name="ids" value="${apply.id?default('')}">
	<input type="hidden" name="apply.id" value="${apply.id?default('')}">
	<input type="hidden" name="apply.status" value="${apply.status}">
	<input type="hidden" name="apply.businessType" value="${apply.businessType}">
	<input type="hidden" name="apply.business.flowTypeValue" id ="flowTypeValue" value="">
	<input type="hidden" name="apply.business.organizeUnitId" id ="organizeUnitId" value="">
	<input type="hidden" name="status" value="${status?default(0)}">
	<input type="hidden" name="businessType" value="${businessType?default(defaultBusinessType!)}">
	<input type="hidden" name="modId" value="${modId?default('')}">
	<div class="table-all">
	<@h.tableDetail>
	<#-- 状态：不是未提交 -->
	<#if apply.status != stack.findValue("@net.zdsoft.eis.base.auditflow.manager.entity.FlowApply@STATUS_PREPARING")>
		<tr class="first"><th colspan="4" class="tt">审核信息：</th></tr> 
		<#if apply.audits?exists>
			<#list apply.audits as r>
				<tr class="first"><th colspan="4" class="tt">[${r.auditUnitName}]单位的审核信息：</th></tr>		
				<tr> 
					<th style="width:15%;">审核结果：</th>
					<td colspan="3">
						<#if r.status == stack.findValue("@net.zdsoft.eis.base.auditflow.manager.entity.FlowAudit@STATUS_AUDIT_PASS")>通过
						<#elseif r.status == stack.findValue("@net.zdsoft.eis.base.auditflow.manager.entity.FlowAudit@STATUS_AUDIT_REJECT")>不通过
						<#else>未审核</#if>
					</td>
				</tr>		
				<tr> 
					<th style="width:15%;">审核意见：</th>
					<td colspan="3">${r.opinion?default('')?replace("\r","<br>")}</td>
				</tr>		
			</#list>
		</#if>
	</#if>
		<tr class="first"><th colspan="4" class="tt"> 申请信息：</th></tr>
		<tr>
			<th style="width:15%;">
				<span>*</span>类&nbsp;&nbsp;&nbsp;&nbsp;型：
			</th>
			<td style="width:75%;" colspan="3">
				<#if addApply>
				<select id="operateType" name="apply.operateType" onChange="changeOperateType();" msgName="类型" notNull="true" class="input-sel100">
				  <#list operateTypes as x>
					<option value="${x[0]}" <#if apply.operateType?string==x[0]>selected</#if>>${x[1]}</option>
				  </#list>
				</select>
				<#else>
				<input type="hidden" id="operateType" name="apply.operateType" value="${apply.operateType}">
				${stack.findValue("@net.zdsoft.eis.base.auditflow.manager.entity.FlowApply@OPERATE_TYPE_MAP.get(apply.operateType)")}
				</#if>
			</td>
		</tr>
		<tr> 
			<th><span>*</span>原&nbsp;&nbsp;&nbsp;&nbsp;因：</th>
			<td colspan="3" class="send_padding_no_width" <#if !canModify>style="word-break:break-all; word-wrap:break-word;"</#if>>
			<#if canModify>
				<textarea id="reason" name="apply.reason" msgName="原因" notNull="true" class="area300" maxLength="240" rows="4" cols="69" style="width:480px;" />${apply.reason?default('')}</textarea>
				<br>(限240个字符。<a href="javascript:computeInputLength(document.getElementById('reason'));">计算字数</a>)
			<#else>
				${apply.reason?default('')}
			</#if>
			</td>
		</tr>
		<#if !apply.business.owner><@ownerSelect /></#if>
		<@businessCommonDetail/>
		<#-- businessDetail这是申请和审核共用的 businessDetailForApply 是申请专用 -->
		<#if businessMacroPage?exists><@businessDetailForApply/><@businessDetail/></#if>
	</@h.tableDetail>
	</div>
	<div class="table1-bt t-center">
		<#if canModify>
		<span class='input-btn1 ml-10' ><button onclick="save();" type="button">保存</button></span>
		</#if>
		<#if apply.status == stack.findValue("@net.zdsoft.eis.base.auditflow.manager.entity.FlowApply@STATUS_IN_AUDIT")>
		<span class='input-btn1 ml-10' ><button onclick="formAction1('apply-cancel.action');" type="button">撤消申请</button></span>
		</#if>
		<#if apply.status == stack.findValue("@net.zdsoft.eis.base.auditflow.manager.entity.FlowApply@STATUS_PREPARING")
			|| apply.status == stack.findValue("@net.zdsoft.eis.base.auditflow.manager.entity.FlowApply@STATUS_AUDIT_REJECT")>
		<span class='input-btn1 ml-10' >
			<button type="button" onclick="formAction1('apply-submit.action');">
				<#if apply.status == stack.findValue("@net.zdsoft.eis.base.auditflow.manager.entity.FlowApply@STATUS_PREPARING")>提交申请<#else>重新申请</#if>
			</button>
		</span>
		</#if>
		<span class='input-btn1 ml-10' ><button type="button" onclick="cancelOperate();">返回</button></span>
	</div>				
</form>	
<script>
<@h.scrollHeight totalHeight="jQuery(window.parent.document).height()" minusHeight="parent.jQuery('.tab-bg').height()+jQuery('.table1-bt').height()" trimming="7"/>
<#if businessMacroPage?exists><@scollHeightForEdit/></#if>
jQuery(document).ready(function(){
	jQuery("#businessSpan").hide();	
	jQuery("#ownerSpan").hide();
	
	businessControl();
	
	if(jQuery.isFunction(window.alonebusinessControl)){
		alonebusinessControl();
	}
	
})

function businessControl(){
	<#if canModify>
		<#if addApply>
			jQuery("#ownerSpan").show();
		</#if>
			
		var operateType = jQuery("#operateType").val();
		if(operateType == ${stack.findValue("@net.zdsoft.eis.base.auditflow.manager.entity.FlowApply@OPERATE_TYPE_ADD")}){
			jQuery("#businessSpan").hide();
		}else{
			<#if addApply>
			jQuery("#businessSpan").show();
			</#if>
		}
		 
		if(operateType == ${stack.findValue("@net.zdsoft.eis.base.auditflow.manager.entity.FlowApply@OPERATE_TYPE_DELETE")}){
			jQuery('.editSpan :input').attr("disabled",true);
		}else{
			jQuery('.editSpan :input').attr("disabled",false);
		}
		
		jQuery(".editSpan").show();
		jQuery(".viewSpan").hide();	
	<#else>
		jQuery(".editSpan").remove();
		jQuery(".viewSpan").show();
	</#if>
}
</script>
</body>	
</html>