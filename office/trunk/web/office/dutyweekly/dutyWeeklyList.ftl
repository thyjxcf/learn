<#import "/common/htmlcomponent.ftl" as htmlmacro>
<@htmlmacro.showPrompt/>
<@htmlmacro.moduleDiv titleName="">
<script>
function doEdit(officeDutyWeekId){
	openDiv("#classLayer", "#classLayer .close,#classLayer .submit,#classLayer .reset", "${request.contextPath}/office/dutyweekly/dutyweekly-dutyweeklyEdit.action?officeDutyWeekId="+officeDutyWeekId, null, null, "500px");
	//load("#workReportDiv", "${request.contextPath}/office/workreport/workReport-myWorkReportEdit.action?officeWorkReport.id="+workReportId);
}

//删除
function doDelete(officeDutyWeekId){
	if(!showConfirm("确认要删除吗?")){
		return;
	}
	$.getJSON("${request.contextPath}/office/dutyweekly/dutyweekly-dutyweeklyDelete.action", 
		{"officeDutyWeekId":officeDutyWeekId}, function(data){
		if(!data.operateSuccess){
		   	if(data.errorMessage!=null&&data.errorMessage!=""){
			   	showMsgError(data.errorMessage);
			   	return;
		   	}
		}else{
	   		showMsgSuccess(data.promptMessage,"",function(){
	   			//load("#myWorkReportDiv", "${request.contextPath}/office/workreport/workReport-myWorkReportList.action");
	   			dutyweekly();
			});
			return;
		}
	}).error(function(XMLHttpRequest, textStatus, errorThrown){alert(XMLHttpRequest.status);});
}

function doApply(){
	openDiv("#classLayer", "#classLayer .close,#classLayer .submit,#classLayer .reset", "${request.contextPath}/office/dutyweekly/dutyweekly-dutyweeklyEdit.action", null, null, "500px");
}
</script>
<div class="popUp-layer" id="classLayer3" style="display:none;width:460px;"></div>
<div class="popUp-layer" id="classLayer" style="display:none;top:200px;left:300px;width:700px;height:365px;"></div>
<div class="pub-table-inner">
<div class="query-builder-nobg mt-15" style="padding:0 0 5px 0;">
    <div class="query-part">
        <a href="javascript:void(0);" onclick="doApply();" class="abtn-orange-new fn-right applyForBtn" style="width:75px;">值周安排新增</a>
        <div class="fn-clear"></div>
    </div>
</div>
<div id="wirkReportDiv"></div>
<div class="pub-table-wrap" id="myWorkReportDiv">
<form name="form1" action="" method="post">
	<@htmlmacro.tableList id="tablelist">
	<tr>
		<th width="10%">周次</th>
		<th width="10%">开始时间</th>
		<th width="10%">结束时间</th>
		<th width="15%">值周班级</th>
		<th width="39%">值班登记教师</th>
		<th class="t-center" width="16%">操作</th>
	</tr>
	<#if officeDutyWeeklies?exists && (officeDutyWeeklies?size>0)>
		<#list officeDutyWeeklies as officeDutyWeekly>
		    <tr>
		    	<td>第${officeDutyWeekly.week!}周</td>
                <td>${officeDutyWeekly.weekStartTime?string('yyyy-MM-dd')?if_exists}</td>
                <td>${officeDutyWeekly.weekEndTime?string('yyyy-MM-dd')?if_exists}</td>
				<td>${officeDutyWeekly.dutyClassName?default("")}</td>
                <td>${officeDutyWeekly.dutyTeacherNames!}</td>
				<td class="t-center">
					<a href="javascript:doEdit('${officeDutyWeekly.id!}');">编辑</a> &nbsp; 
					<a href="javascript:doDelete('${officeDutyWeekly.id!}');">删除</a>&nbsp;
				</td>
			</tr>
		</#list>
	<#else>
	   <tr><td colspan="6"> <p class="no-data mt-50 mb-50">还没有任何记录哦！</p></td></tr>
	</#if>
	</@htmlmacro.tableList>	
	<@htmlmacro.Toolbar container="#workReportDiv"></@htmlmacro.Toolbar>
</form>
</div>
</div>
<script type="text/javascript" src="${request.contextPath}/static/js/myscript.js"></script>
</@htmlmacro.moduleDiv>