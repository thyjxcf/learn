<#import "/common/htmlcomponent.ftl" as htmlmacro>
<@htmlmacro.showPrompt/>
<@htmlmacro.moduleDiv titleName="">
<script>
function doView(workReportId){
	load("#wirkContainer", "${request.contextPath}/office/workreporttl/workReportTl-workReportSearchView.action?officeWorkReportTl.id="+workReportId);
}
function doEdit(workReportId){
	load("#wirkContainer", "${request.contextPath}/office/workreporttl/workReportTl-workReportSearchEdit.action?officeWorkReportTl.id="+workReportId);
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

    

<div class="pub-table-wrap" id="wirkContainer">
<form name="form1" action="" method="post">
	<@htmlmacro.tableList id="tablelist">
	<tr>
		<th width="5%">学年</th>
		<th width="7%">学期</th>
		<th width="6%">周次</th>
		<th width="45%">汇报内容</th>
		<th width="10%">汇报人</th>
		<th width="7%">单位类别</th>
		<th width="10%">汇报人单位</th>
		<th class="t-center" width="10%">操作</th>
	</tr>
	<#if officeWorkReportList?exists && (officeWorkReportList?size>0)>
		<#list officeWorkReportList as officeWorkReport>
		    <tr>
                <td>${officeWorkReport.year?default("")}</td>
                <td>第${officeWorkReport.semester?default("")}学期</td>
				<td>第${officeWorkReport.week?default("")}周</td>
				<td>${officeWorkReport.content?default('')}</td>
                <td>
               		${officeWorkReport.createUserName?default("")}
                </td>
				<td>
					<#if officeWorkReport.unitClass?default(1) == 1>教育局
					<#elseif officeWorkReport.unitClass?default(2) == 2>学校
					</#if>
				</td>
				<td>
					${officeWorkReport.unitName?default("")}
				</td>
				<td class="t-center">
					<a href="javascript:doView('${officeWorkReport.id!}');">查看</a>
					<a href="javascript:doEdit('${officeWorkReport.id!}');">编辑</a>
					<a href="javascript:doDelete('${officeWorkReport.id!}');">删除</a>
				</td>
			</tr>
		</#list>
	<#else>
	   <tr><td colspan="8"> <p class="no-data mt-50 mb-50">还没有任何记录哦！</p></td></tr>
	</#if>
	</@htmlmacro.tableList>	
	<@htmlmacro.Toolbar container="#wirkContainer"></@htmlmacro.Toolbar>
</form>
</div>
</div>
<script type="text/javascript" src="${request.contextPath}/static/js/myscript.js"></script>
</@htmlmacro.moduleDiv>