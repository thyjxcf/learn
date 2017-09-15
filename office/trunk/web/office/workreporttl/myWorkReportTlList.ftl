<#import "/common/htmlcomponent.ftl" as htmlmacro>
<@htmlmacro.showPrompt/>
<@htmlmacro.moduleDiv titleName="">
<script>
function doEdit(workReportId){
	load("#myWorkReportDiv", "${request.contextPath}/office/workreporttl/workReportTl-myWorkReportEdit.action?officeWorkReportTl.id="+workReportId);
}
function doView(workReportId){
	load("#myWorkReportDiv", "${request.contextPath}/office/workreporttl/workReportTl-myWorkReportView.action?officeWorkReportTl.id="+workReportId);
}
//提交
function doSubmit(officeWorkReportId){
	if(!showConfirm("确认要提交吗?")){
		return;
	}
	$.getJSON("${request.contextPath}/office/workreporttl/workReportTl-myWorkReportSubmit.action", 
		{"officeWorkReportTl.id":officeWorkReportId}, function(data){
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
	   			//load("#workReportDiv", "${request.contextPath}/office/meetingmanage/meetingmanage-meetingList.action?doAction=apply");
	   			//load("#myWorkReportDiv", "${request.contextPath}/office/workreport/workReport-myWorkReportList.action");
	   			doQueryChange();
			});
			return;
		}
	}).error(function(XMLHttpRequest, textStatus, errorThrown){alert(XMLHttpRequest.status);});
}
//撤销
function doCancel(officeWorkReportId){
	if(!showConfirm("确定要撤回吗?")){
		return;
	}
	$.getJSON("${request.contextPath}/office/workreporttl/workReportTl-myWorkReport-cancel.action", 
		{"officeWorkReportTl.id":officeWorkReportId}, function(data){
		if(!data.operateSuccess){
		   	if(data.errorMessage!=null&&data.errorMessage!=""){
			   	showMsgError(data.errorMessage);
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
//删除
function doDelete(officeWorkReportId){
	if(!showConfirm("确认要删除吗?")){
		return;
	}
	$.getJSON("${request.contextPath}/office/workreporttl/workReportTl-myWorkReport-delete.action", 
		{"officeWorkReportTl.id":officeWorkReportId}, function(data){
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
<div class="pub-table-inner">

<div class="pub-table-wrap" id="myWorkReportDiv">
<form name="form1" action="" method="post">
	<@htmlmacro.tableList id="tablelist">
	<tr>
		<th width="10%">学年</th>
		<th width="10%">学期</th>
		<th width="10%">周次</th>
		<th width="52%">汇报内容</th>
		<th width="6%">状态</th>
		<th class="t-center" width="12%">操作</th>
	</tr>
	<#if officeWorkReportList?exists && (officeWorkReportList?size>0)>
		<#list officeWorkReportList as officeWorkReport>
		    <tr>
                <td>${officeWorkReport.year!}</td>
                <td>第${officeWorkReport.semester!}学期</td>
				<td>第${officeWorkReport.week!}周</td>
				<!--<td title="${officeWorkReport.content?default('')}"><@htmlmacro.cutOff str="${officeWorkReport.content?default('')}" length=50/></td>-->
				<td>${officeWorkReport.content?default('')}</td>
                <td>
               		<#if officeWorkReport.state?default(1) == 1>未提交
					<#elseif officeWorkReport.state==2>已提交
					<#else>已撤回
					</#if>
                </td>
				<td class="t-center" style="margin:20px;">
					<#if officeWorkReport.state?exists && officeWorkReport.state == 8>
						<a href="javascript:doSubmit('${officeWorkReport.id!}');">提交</a>&nbsp;
						<a href="javascript:doEdit('${officeWorkReport.id!}');">编辑</a>&nbsp; 
						<a href="javascript:doView('${officeWorkReport.id!}');">查看</a>&nbsp;
					<#else>
					<#if officeWorkReport.state?exists && officeWorkReport.state == 1>
						<a href="javascript:doSubmit('${officeWorkReport.id!}');">提交</a>&nbsp;
						<a href="javascript:doEdit('${officeWorkReport.id!}');">编辑</a>&nbsp;
						<a href="javascript:doDelete('${officeWorkReport.id!}');">删除</a>&nbsp;
					<#else>
						<a href="javascript:doView('${officeWorkReport.id!}');">查看</a>
						<a href="javascript:doCancel('${officeWorkReport.id!}');">撤回</a>
					</#if>
					</#if>
				</td>
			</tr>
		</#list>
	<#else>
	   <tr><td colspan="6"> <p class="no-data mt-50 mb-50">还没有任何记录哦！</p></td></tr>
	</#if>
	</@htmlmacro.tableList>	
	<@htmlmacro.Toolbar container="#myWorkReportDiv"></@htmlmacro.Toolbar>
</form>
</div>
</div>
<script type="text/javascript" src="${request.contextPath}/static/js/myscript.js"></script>
</@htmlmacro.moduleDiv>