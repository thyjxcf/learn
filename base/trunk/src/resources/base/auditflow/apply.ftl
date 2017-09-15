<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<#import "/common/htmlcomponent.ftl" as common />
<#if businessMacroPage?exists><#include businessMacroPage /></#if>
<head>
<title>${webAppTitle}--申请列表</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<#include "/common/css.ftl" />
<#include "/common/js.ftl">
<#include "/common/handlefielderror.ftl">
<script type="text/javascript">   
jQuery(function(){
	var addButtonDiv = document.getElementById("addButtonDiv");
	var queryButtonDiv = document.getElementById("queryButtonDiv");
	if(addButtonDiv){
		addButtonDiv.innerHTML = "<span class='input-btn2 addDiv' onClick='add();'><button type='button'>新增</button></span>";
	}
	if(queryButtonDiv){
		queryButtonDiv.innerHTML = "<span class='input-btn1' onclick='query();' id='queryButton'><button type='button'>查找</button></span>";
	}
});
//新增
function add(){
	var businessType = document.getElementById("businessType").value;
	document.location.href = "apply-add.action?businessType=" + businessType + "&modId=${modId!}";
}

function query(){	
	document.form1.action = "apply-list.action";
	document.form1.submit();
}

// 编辑
function doEdit(applyId,event){
	var frm = document.getElementById("form1");
	var obj = event.srcElement ? event.srcElement : event.target;
 	if (obj.type =="checkbox" || obj.name =="ids" || obj.name =="idsTd" || obj.name =="viewAuditTD" || obj.name =="viewAuditBtn"){ 
 		return false;
 	}
	frm.action="apply-edit.action?apply.id="+applyId;
	frm.submit();
}

var isSubmitting = false;
function checkForm(){
	var frm = document.getElementById("form1");	
	var flag = false;	
	var items = document.getElementsByName("ids");
	
	for(i=0;i<items.length;i++){
		if(items[i].checked){
			flag = true;
			break;
		}
	}
	
	if(!flag){
		showMsgWarn("没有选要操作的行，请先选择！");
		return false;
	}
	return true;
}

// 验证表单
function formAction(frmAction,msg){
	if(checkForm()==false){
        return;
	}
	
	
	if(isSubmitting) {		
    	return;
    }
    
	//覆盖使用
	if(submitValidate()==false){
		return;
	}
	
    if(!confirm("确认要进行\""+msg+"\"操作吗？")){
    	return ;
    }
 	
	isSubmitting = true;
	var frm = document.getElementById("form1");
	frm.action = frmAction;
	frm.submit();

}
//特殊需求 比如不能删除之类的 就override method
function submitValidate(){
	return true;
}
function selectAll(){
	<@common.doCheckbox checkboxname="ids"/>
}	

// 查看审核进度
function viewAudit(id) {
	var url ="${request.contextPath}/base/auditflow/showSteps.action?ids="+id;
	var height = 380;//jQuery(".table-content").height()-100;
	var width = 780;//jQuery(".table-content").width()-100;
	openDiv("#panelWindow", null, "#speed", url,width,height);
}
	
</script>
</head>
<body>
	<div class="jwindow" id="panelWindow"></div>
	<form name="form1" id="form1" action="" method="post" onsubmit="return false;">
	
	  <#if businessMacroPage?exists><@queryCond /><@applyQueryCond /></#if>
	  
	    <@common.tableList id="tablelist">
			<tr id="pri_tr">
				<#if status != stack.findValue("@net.zdsoft.eis.base.auditflow.manager.entity.FlowApply@STATUS_AUDIT_PASS")>
					<th width="30">选择</th>
				</#if>
				<#list ownerListFieldHeads?if_exists as head>
					<th width="70">${head}</th>
				</#list>
				<#list listFieldHeads?if_exists as head>
					<th width="70">${head.define}</th>
				</#list>
				<th width="70">原因</th>
				<th width="30">类型</th>
				<th width="70"><#if status == stack.findValue("@net.zdsoft.eis.base.auditflow.manager.entity.FlowApply@STATUS_AUDIT_PASS")>审核日期<#else>申请日期</#if></th>
  				<#if status != stack.findValue("@net.zdsoft.eis.base.auditflow.manager.entity.FlowApply@STATUS_PREPARING") && status != stack.findValue("@net.zdsoft.eis.base.auditflow.manager.entity.FlowApply@STATUS_AUDIT_PASS")>																	
				 <th width="80">审核进度</th>
				</#if>
			</tr>
			<#if applys?exists>
				<#list applys as apply>
				    <#assign x = apply.business>
				    <#assign owner = apply.owner>
					<tr	onclick="doEdit('${apply.id}',event)" style="cursor:pointer;">
						<#if status != stack.findValue("@net.zdsoft.eis.base.auditflow.manager.entity.FlowApply@STATUS_AUDIT_PASS")>
							<td name="idsTd"><input type="checkbox" name="ids" value="${apply.id}" /></td>
						</#if>
						<#if owner.code?exists>
						<td style="word-break:break-all; word-wrap:break-word;">${owner.code}</td>
						</#if>
						<td style="word-break:break-all; word-wrap:break-word;">${owner.name}</td>
						<#list x.listFieldValues as field>
						<td style="word-break:break-all; word-wrap:break-word;">
							${field.wrappedValue?default('')}<#if field.childField?exists && (field.childField.wrappedValue)?has_content>(${field.childField.wrappedValue})</#if>
						</td>
						</#list>
						<td style="word-break:break-all; word-wrap:break-word;"><@common.cutOff4List str=apply.reason length=20 /></td>
						<td style="word-break:break-all; word-wrap:break-word;">
							${stack.findValue("@net.zdsoft.eis.base.auditflow.manager.entity.FlowApply@OPERATE_TYPE_MAP").get(apply.operateType)}
						</td>
						<td style="word-break:break-all; word-wrap:break-word;">
						  <#if status == stack.findValue("@net.zdsoft.eis.base.auditflow.manager.entity.FlowApply@STATUS_AUDIT_PASS")>
							<#if apply.auditDate?exists>${apply.auditDate?string("yyyy-MM-dd")}<#else>&nbsp;</#if>
						  <#else>
							${apply.applyDate?string("yyyy-MM-dd")}
						  </#if>
						</td>
  				  	 	 <#if status != stack.findValue("@net.zdsoft.eis.base.auditflow.manager.entity.FlowApply@STATUS_PREPARING") && status != stack.findValue("@net.zdsoft.eis.base.auditflow.manager.entity.FlowApply@STATUS_AUDIT_PASS")>
  				  	 	 <td name="viewAuditTD" style="word-break:break-all; word-wrap:break-word;">
  				  	 	 	<span class="input-btn3 addDiv" name="viewAuditBtn" onclick="viewAudit('${apply.id}');"><button type='button' name="viewAuditBtn">查看</button></span>
  				  	 	 </td>	
  				  	 	 </#if>									  	          				  	 	 																	
					</tr>
				</#list>
			</#if>
		</@common.tableList>
		<#-- 待处理 -->
		<#if status != stack.findValue("@net.zdsoft.eis.base.auditflow.manager.entity.FlowApply@STATUS_AUDIT_PASS") && applys?exists && applys?size != 0>
			<#assign selectAllStr ="<input type='checkbox' id='allSelect' onclick='javascript:selectAll();'><label for='allSelect'>&nbsp;全选&nbsp;&nbsp;</label>" >
			<#assign deleteButton ="<span class='input-btn2 ml-10' onclick='formAction(\"apply-delete.action\",\"删除申请\");'><button type='button'>删除</button></span>" >
			<#assign submitButton ="<span class='input-btn2 ml-10' onclick='formAction(\"apply-submit.action\",\"提交申请\");'><button type='button'>提交申请</button></span>" >
			<#assign cancelButton ="<span class='input-btn2 ml-10' onclick='formAction(\"apply-cancel.action\",\"撤消申请\");'><button type='button'>撤消申请</button></span>" >
			<#assign reapplyButton ="<span class='input-btn2 ml-10' onclick='formAction(\"apply-submit.action\",\"重新申请\");'><button type='button'>重新申请</button></span>" >
			<#assign htmlStr="">
			
			<#if showAllSelectButton>
				<#assign htmlStr=selectAllStr>
			</#if>
			
			<#if showDeleteButton>
				<#assign htmlStr = htmlStr + deleteButton >
			</#if>
			
			
			<#if showSubmitButton>
				<#assign htmlStr = htmlStr + submitButton >
			</#if>
			
			
			<#if showCanelApplyButton>
				<#assign htmlStr = htmlStr + cancelButton >
			</#if>
			
			<#if showReApplyButton>
				<#assign htmlStr = htmlStr + reapplyButton >
			</#if>
			
		</#if>
		
		<@common.Toolbar>
			${htmlStr?default('&nbsp;')}
		</@common.Toolbar>
		</form>
	</body>
<script>
<#if businessMacroPage?exists><@scollHeightForList/></#if>
</script>
</html>