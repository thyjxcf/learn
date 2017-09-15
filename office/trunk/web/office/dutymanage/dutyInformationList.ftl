<#import "/common/htmlcomponent.ftl" as htmlmacro>
<@htmlmacro.showPrompt/>
<@htmlmacro.moduleDiv titleName="">
<script>
function doEdit(dutyId){
	openDiv("#classLayer", "#classLayer .close,#classLayer .submit,#classLayer .reset", "${request.contextPath}/office/dutymanage/dutymanage-dutyInformationEdit.action?officeDutyInformationSet.id="+dutyId, null, null, "500px");
}
function doView(workReportId){
	$("div.pub-tab .pub-tab-list li:eq(1)").addClass("current").siblings("li").removeClass("current");
	load("#dutyDiv", "${request.contextPath}/office/dutymanage/dutymanage-dutyInformationApply.action?dutyId="+workReportId);
}

function doOnlyView(dutyId){
	openDiv("#classLayer", "#classLayer .close,#classLayer .submit,#classLayer .reset", "${request.contextPath}/office/dutymanage/dutymanage-dutyInformationView.action?officeDutyInformationSet.id="+dutyId, null, null, "500px");
}

//删除
function doDelete(dutyId){
	if(!showConfirm("确认要删除吗?")){
		return;
	}
	$.getJSON("${request.contextPath}/office/dutymanage/dutymanage-dutyInformationDelete.action", 
		{"officeDutyInformationSet.id":dutyId}, function(data){
		if(!data.operateSuccess){
		   	if(data.errorMessage!=null&&data.errorMessage!=""){
			   	showMsgError(data.errorMessage);
			   	return;
		   	}
		}else{
	   		showMsgSuccess(data.promptMessage,"",function(){
	   			dutySet();
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
		<th width="15%">值班名称</th>
		<th width="15%">值班开始时间</th>
		<th width="15%">值班结束时间</th>
		<th width="10%">报名开始时间</th>
		<th width="10%">报名结束时间</th>
		<th width="10%">类型</th>
		<th width="10%">报名结果</th>
		<th class="t-center" width="15%">操作</th>
	</tr>
	<#if officeDutyInformationSets?exists && (officeDutyInformationSets?size>0)>
		<#list officeDutyInformationSets as officeDutyInformationSet>
		    <tr>
                <td>${officeDutyInformationSet.dutyName!}</td>
                <td>${(officeDutyInformationSet.dutyStartTime?string('yyyy-MM-dd'))?if_exists}</td>
				<td>${(officeDutyInformationSet.dutyEndTime?string('yyyy-MM-dd'))?if_exists}</td>
				<td>${(officeDutyInformationSet.registrationStartTime?string('yyyy-MM-dd'))?if_exists}</td>
                <td>${(officeDutyInformationSet.registrationEndTime?string('yyyy-MM-dd'))?if_exists}</td>
                <td>
                	<#if officeDutyInformationSet.type?exists && officeDutyInformationSet.type =='0'>
                		上下午
                	<#elseif officeDutyInformationSet.type?exists && officeDutyInformationSet.type =='1'>
                		一天
                	</#if>
                </td>
                <td class="t-center">
                	<a href="javascript:doView('${officeDutyInformationSet.id!}');">查看</a> &nbsp;
                </td>
				<td class="t-center">
					<#if !officeDutyInformationSet.onlyView>
					<a href="javascript:doEdit('${officeDutyInformationSet.id!}');">编辑</a>&nbsp;
					<a href="javascript:doDelete('${officeDutyInformationSet.id!}');">删除</a>&nbsp;
					<#else>
						<a href="javascript:doOnlyView('${officeDutyInformationSet.id!}');">查看</a> &nbsp;
					</#if>
				</td>
			</tr>
		</#list>
	<#else>
	   <tr><td colspan="8"> <p class="no-data mt-50 mb-50">还没有任何记录哦！</p></td></tr>
	</#if>
	</@htmlmacro.tableList>	
	<@htmlmacro.Toolbar container="#myWorkReportDiv"></@htmlmacro.Toolbar>
</form>
</div>
</div>
<script type="text/javascript" src="${request.contextPath}/static/js/myscript.js"></script>
</@htmlmacro.moduleDiv>