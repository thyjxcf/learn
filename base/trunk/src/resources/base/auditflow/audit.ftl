<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<#import "/common/htmlcomponent.ftl" as common />
<#if businessMacroPage?exists><#include businessMacroPage /></#if>
<head>
<title>${webAppTitle}--审核申请列表</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<#include "/common/css.ftl" />
<#include "/common/js.ftl">
<#include "/common/handlefielderror.ftl"> <#-- 导入出错提示信息 -->
<script language="javascript">
jQuery(function(){
	var queryButtonDiv = document.getElementById("queryButtonDiv");
	if(queryButtonDiv){
		queryButtonDiv.innerHTML = "<span class='input-btn1' onclick='dosearch();' id='queryButton'><button type='button'>查找</button></span>";
	}
});
var isSubmitting = false;
function formAction(url,status){
	var frm = document.getElementById("form1");
	var flag = false;	
	var items = document.getElementsByName("ids");
	var opinion = document.getElementById("audit.opinion");
	for(var i=0;i<items.length;i++){		
		if(items[i].checked){
			flag = true;
			break;
		}
	}
	
	if(!flag){
		showMsgWarn("没有选要操作的行，请先选择！","#allSelect");
		return;
	}
	if(isSubmitting) {
    	return ;
    }  	
	if(checkAllValidate() && window.confirm("确认审核吗？")){
		isSubmitting = true;
		frm.action = url+"?audit.status="+ status;
		frm.submit();	
	}
}

// 审批
function audit(url){
 	document.form1.action = url;
	document.form1.submit();
}

function doEdit(id){
<#if status == stack.findValue("@net.zdsoft.eis.base.auditflow.manager.entity.FlowAudit@STATUS_CHECKING")>
    return; 
<#else>
	var obj = event.srcElement ? event.srcElement : event.target;
 	if (obj.type =="checkbox" || obj.name =="idsTd" || obj.name =="ids" || obj.name =="viewAuditBtn" || obj.name=="speed"){ 
 		return false;
 	}
 	var frm = document.getElementById("form1"); 	
	frm.action="audit-edit.action?audit.id="+id; 
	frm.submit();
</#if>	
}

function selectAll(){
	<@common.doCheckbox checkboxname="ids"/>
}

function dosearch(){
	document.form1.action = "audit-list.action";
	document.form1.submit();
}

// 查看审核进度
function viewAudit(id) {
	var url ="${request.contextPath}/base/auditflow/showSteps.action?ids="+id;
	var height = 380;//jQuery(".table-content").height()-100;
	var width = 780;//jQuery(".table-content").width()-100;
	openDiv("#panelWindow", null, "#viewAuditBtn", url,width,height);
}
</script>
	</head>
	<body>
		<div class="jwindow" id="panelWindow"></div>
		<form name="form1" id="form1" method="post">
		
		<#if businessMacroPage?exists><@queryCond /><@auditQueryCond /></#if>
		
		<@common.tableList id="tablelist">
			<tr id="pri_tr">
				<#if status == stack.findValue("@net.zdsoft.eis.base.auditflow.manager.entity.FlowAudit@STATUS_CHECKING")>
					<th width="30">选择</th>			
					<th width="40">处理</th>
				</#if>
				<#if status != stack.findValue("@net.zdsoft.eis.base.auditflow.manager.entity.FlowAudit@STATUS_AUDIT_FINISH")>
				<th width="70">审核进度</th>	
				</#if>
				<th width="100">申请单位</th>
				<#list ownerListFieldHeads?if_exists as head>
					<th width="70">${head}</th>
				</#list>
				<#list listFieldHeads?if_exists as head>
					<th width="70">${head.define}</th>
				</#list>
				<th width="70">原因</th>
				<th width="30">类型</th>
				<th width="70"><#if status == stack.findValue("@net.zdsoft.eis.base.auditflow.manager.entity.FlowAudit@STATUS_AUDIT_PASS") || status == stack.findValue("@net.zdsoft.eis.base.auditflow.manager.entity.FlowAudit@STATUS_AUDIT_FINISH")>审核日期<#else>申请日期</#if></th>
			</tr>
			<#if applys?exists>
				<#list applys as apply>
				  <#assign x = apply.business>
				  <#assign owner = apply.owner>
				  <#assign audit = apply.audits?first>
					<tr	onclick="doEdit('${audit.id}')" style="cursor:pointer;">
						<#if status == stack.findValue("@net.zdsoft.eis.base.auditflow.manager.entity.FlowAudit@STATUS_CHECKING")>
							<td name="idsTd"><input type="checkbox" name="ids" value="${audit.id}" /></td>
							<td><span class="input-btn3 addDiv" name="speed" onclick="javascript:audit('audit-edit.action?audit.id=${audit.id}');"><button type="button" name="speed">处理</button></span></td>
						</#if>
  				  	 	 <#if status != stack.findValue("@net.zdsoft.eis.base.auditflow.manager.entity.FlowAudit@STATUS_AUDIT_FINISH")>
  				  	 	 <td>					  	          				  	
  				  	 	 	<span class="input-btn3 addDiv" id="viewAuditBtn" name="viewAuditBtn" onclick="viewAudit('${apply.id}');"><button type="button" name="viewAuditBtn">查看</button></span>						  	          				  	 	 	
  				  	 	 </td>															
						</#if>
						<td style="word-break:break-all; word-wrap:break-word;">${apply.applyUnitName?default(' ')}</td>
						<#if owner.code?exists>
						<td style="word-break:break-all; word-wrap:break-word;">${owner.code}</td>
						</#if>
						<td style="word-break:break-all; word-wrap:break-word;">${owner.name}</td>
						<#list x.listFieldValues as field>
							<td style="word-break:break-all; word-wrap:break-word;">${field.wrappedValue?default('')}<#if field.childField?exists && (field.childField.wrappedValue)?has_content>(${field.childField.wrappedValue})</#if></td>
						</#list>
						<td style="word-break:break-all; word-wrap:break-word;"><@common.cutOff4List str=apply.reason length=20 /></td>
						<td style="word-break:break-all; word-wrap:break-word;">
							${stack.findValue("@net.zdsoft.eis.base.auditflow.manager.entity.FlowApply@OPERATE_TYPE_MAP").get(apply.operateType?default("0"))}
						</td>
						<td style="word-break:break-all; word-wrap:break-word;">
							<#if status == stack.findValue("@net.zdsoft.eis.base.auditflow.manager.entity.FlowAudit@STATUS_AUDIT_PASS") || status == stack.findValue("@net.zdsoft.eis.base.auditflow.manager.entity.FlowAudit@STATUS_AUDIT_FINISH")>
							  <#if apply.auditDate?exists>${apply.auditDate?string("yyyy-MM-dd")}<#else>&nbsp;</#if>
							<#else>
							  <#if apply.applyDate?exists>${apply.applyDate?string("yyyy-MM-dd")}<#else>&nbsp;</#if>
							</#if>
						</td>
					</tr>
				</#list>
			</#if>
		</@common.tableList>		
		<@common.Toolbar>
		  <#if status == stack.findValue("@net.zdsoft.eis.base.auditflow.manager.entity.FlowAudit@STATUS_CHECKING") && (applys?exists && (applys?size>0))>
			<input type='checkbox' id='allSelect' onclick='javascript:	selectAll();'>全选&nbsp;&nbsp;
			审核意见：<input type='text' id='audit.opinion' name='audit.opinion' class='input-txt100' maxLength='240' value='' notNull="true" msgName="审核意见">&nbsp;
			<span class='input-btn2 ml-10' name='authorize1' onclick='formAction("audit-save.action",${stack.findValue("@net.zdsoft.eis.base.auditflow.manager.entity.FlowAudit@STATUS_AUDIT_PASS")});'><button type='button'>审核通过</button></span>
			<span class='input-btn2 ml-10' name='authorize2' onclick='formAction("audit-save.action",${stack.findValue("@net.zdsoft.eis.base.auditflow.manager.entity.FlowAudit@STATUS_AUDIT_REJECT")});'><button type='button'>不&nbsp;通&nbsp;过</button></span>
		  </#if>
		</@common.Toolbar>
		</form>
	</body>
	<script>
		var totalHeight ;
		var minusHeight ;
		if(!jQuery('.mainFrame',window.parent.document)){
			totalHeight = jQuery('.mainFrame',window.parent.document).height();
		    minusHeight = parent.jQuery('.tab-bg').height()+jQuery('.head-tt').height()+jQuery('.table1-bt').height();
		}else{
			totalHeight = jQuery(window.document).height();
		    minusHeight = jQuery('.head-tt').height()+jQuery('.table1-bt').height();
		}
		var trimming = 7;
		jQuery(document).ready(function(){
			$t_c_width=jQuery(".table-content").width();
			$t_c_width=$t_c_width-16;
			jQuery(".table-content").height(totalHeight - (minusHeight) - (trimming))
			jQuery(".table-header").width($t_c_width);
		})
	</script>
</html>