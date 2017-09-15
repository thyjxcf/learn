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
		<th width="41%">用印事由</th>
		<th width="10%">时间</th>
		<th width="10%">分管校长</th>
		<th width="10%">印章类型</th>
		<th width="10%">审核状态</th>
		<th width="14%">操作</th>
	</tr>
	<#if officeSealList?exists && (officeSealList?size>0)>
		<#list officeSealList as officeSeal>
		    <tr>
                <td>${officeSeal_index+1}</td>
                <td>${officeSeal.applyOpinion!}</td>
				<td>${(officeSeal.createTime?string('yyyy-MM-dd'))?if_exists}</td>
				<td>${officeSeal.auditUserName!}</td>
				<td>${officeSeal.sealName!}</td>
				<td>
					<#if officeSeal.state?default("1") == "1">未提交
					<#elseif officeSeal.state == NEEDAUDIT+''>
						待审核
	        		<#elseif officeSeal.state == PASS+''>
	                	已通过
	                <#elseif officeSeal.state == UNPASS+''>
	                	未通过
	                </#if>
				</td>
				<td>
					<#if officeSeal.state?exists && officeSeal.state == "1">
						<a href="javascript:doSubmit('${officeSeal.id!}');">提交</a> &nbsp;
						<a href="javascript:doSealEdit('${officeSeal.id!}');">编辑</a> &nbsp;
						<a href="javascript:doSealDelete('${officeSeal.id!}');">删除</a> &nbsp;
					<#else>
						<a href="javascript:doView('${officeSeal.id!}');">查看</a>
					</#if>
				</td>
			</tr>
		</#list>
	<#else>
	   <tr><td colspan="7"> <p class="no-data mt-50 mb-50">还没有任何记录哦！</p></td></tr>
	</#if>
	</@htmlmacro.tableList>
	<@htmlmacro.Toolbar container="#myWorkReportDiv"></@htmlmacro.Toolbar>
</form>
</div>
</div>
<script type="text/javascript" src="${request.contextPath}/static/js/myscript.js"></script>
</@htmlmacro.moduleDiv>
<script>
function doSealEdit(sealId){
	//openDiv("#sealAddLayer", "#sealAddLayer .close,#sealAddLayer .submit,#sealAddLayer .reset", "${request.contextPath}/office/sealmanage/sealmanage-addSeal.action?officeSealId="+sealId, null, null, "900px");
	load("#sealDiv","${request.contextPath}/office/sealmanage/sealmanage-addSeal.action?officeSealId="+sealId);
}
function doView(sealId){
	//openDiv("#sealAddLayer", "#sealAddLayer .close,#sealAddLayer .submit,#sealAddLayer .reset", "${request.contextPath}/office/sealmanage/sealmanage-viewSeal.action?officeSealId="+sealId, null, null, "900px");
	load("#sealDiv","${request.contextPath}/office/sealmanage/sealmanage-viewSeal.action?officeSealId="+sealId);
}

function doSubmit(sealId){
	if(!showConfirm("确认要提交吗?")){
		return;
	}
	$.getJSON("${request.contextPath}/office/sealmanage/sealmanage-submitSeal.action", 
		{"officeSealId":sealId}, function(data){
		if(!data.operateSuccess){
		   if(data.errorMessage!=null&&data.errorMessage!=""){
			   showMsgError(data.errorMessage);
			   return;
		   }else{
		   	   showMsgError(data.promptMessage);
			   return;
		   }	
		}else{
	   		showMsgSuccess(data.promptMessage,"",function(){
	   			doQueryChange();
			});
			return;
		}
	}).error(function(XMLHttpRequest, textStatus, errorThrown){alert(XMLHttpRequest.status);});
}

function doSealDelete(sealId){
	if(!showConfirm('您确认要删除用印记录吗？')){
		return;
	}
	$.getJSON("${request.contextPath}/office/sealmanage/sealmanage-deleteSeal.action", 
		{"officeSealId":sealId}, function(data){
		if(!data.operateSuccess){
		   	if(data.errorMessage!=null&&data.errorMessage!=""){
			   	showMsgError(data.errorMessage);
			   	return;
		   	}
		}else{
	   		showMsgSuccess(data.promptMessage,"",function(){
	   			//load("#myWorkReportDiv", "${request.contextPath}/office/workreport/workReport-myWorkReportList.action");
	   			doQueryChange();
			});
			return;
		}
	}).error(function(XMLHttpRequest, textStatus, errorThrown){alert(XMLHttpRequest.status);});
}

</script>
