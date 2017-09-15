<#import "/common/htmlcomponent.ftl" as htmlmacro>
<@htmlmacro.showPrompt/>
<@htmlmacro.moduleDiv titleName="">
<script>
//撤销
function doCancel(officeWorkReportId){
	if(!showConfirm("确定要撤回吗?")){
		return;
	}
	$.getJSON("${request.contextPath}/office/workreport/workReport-myWorkReport-cancel.action", 
		{"officeWorkReport.id":officeWorkReportId}, function(data){
		if(!data.operateSuccess){
		   	if(data.errorMessage!=null&&data.errorMessage!=""){
			   	showMsgError(data.errorMessage);
			   	return;
		   	}
		}else{
	   		showMsgSuccess(data.promptMessage,"",function(){
	   			workReportSearchTab();
			});
			return;
		}
	}).error(function(XMLHttpRequest, textStatus, errorThrown){alert(XMLHttpRequest.status);});
}

function doDelete(id){
	if(!showConfirm("确认要删除吗?")){
		return;
	}
	$.getJSON("${request.contextPath}/office/workreport/workReport-myWorkReport-delete.action", 
		{"officeWorkReport.id":id}, function(data){
		if(!data.operateSuccess){
		   	if(data.errorMessage!=null&&data.errorMessage!=""){
			   	showMsgError(data.errorMessage);
			   	return;
		   	}
		}else{
	   		showMsgSuccess(data.promptMessage,"",function(){
	   			//load("#myWorkReportDiv", "${request.contextPath}/office/workreport/workReport-myWorkReportList.action");
	   			workReportSearchTab();
			});
			return;
		}
	}).error(function(XMLHttpRequest, textStatus, errorThrown){alert(XMLHttpRequest.status);});
}

function doView(workReportId){
	load("#workReportDiv", "${request.contextPath}/office/workreport/workReport-workReportSearchView.action?officeWorkReport.id="+workReportId);
}

</script>
<div class="pub-table-inner">
<div class="query-builder-nobg mt-15" style="padding:0 0 5px 0;">
<div class="query-part">
    	<div class="query-tt ml-10"><span class="fn-left">汇报类型：</span></div>
    	<div class="ui-select-box fn-left" style="width:120px;">
            <input type="text" class="ui-select-txt" value=""  readonly/>
            <input name="reportTypes" id="reportTypes" type="hidden" value=""  class="ui-select-value" />
            <a class="ui-select-close"></a>
            <div class="ui-option" myfunchange="workReportSearch">
        		<div class="a-wrap">
            	<a val=""><span>全部</span></a>
            	<a val="1" <#if reportTypes?default("") == "1">class="selected"</#if>><span>周报</span></a>
                <a val="2" <#if reportTypes?default("") == "2">class="selected"</#if>><span>月报</span></a>
                </div>
            </div>
	    </div>
    	<div class="query-tt ml-10"><span class="fn-left">汇报内容：</span></div>
        <input name="contents" id="contents" value="${contents!}" maxLength="30" class="input-txt fn-left" style="width:120px;"/>
    	<div class="query-tt ml-10"><span class="fn-left">汇报人：</span></div>
        <input name="createUserNames" id="createUserNames" value="${createUserNames!}" maxLength="30" class="input-txt fn-left" style="width:120px;"/>
	    <div class="query-tt ml-10"><span class="fn-left">开始日期：</span></div>
	    <@htmlmacro.datepicker name="beginTimes" id="beginTimes" style="width:120px;" value="${(beginTimes)?if_exists}"/>
	   	<div class="query-tt">&nbsp;-&nbsp;</div>
	    <@htmlmacro.datepicker name="endTimes" id="endTimes" style="width:120px;" value="${(endTimes)?if_exists}"/>
	    <a href="javascript:void(0);" onclick="workReportSearch();" class="abtn-blue fn-left ml-20">查找</a>
        <div class="fn-clear"></div>
    </div>
</div>
<div class="pub-table-wrap" id="wirkContainer">
<form name="form1" action="" method="post">
	<@htmlmacro.tableList id="tablelist">
	<tr>
		<th width="8%">开始日期</th>
		<th width="8%">结束日期</th>
		<th width="10%">汇报类型</th>
		<th width="32%">汇报内容</th>
		<th width="10%">汇报人</th>
		<th width="10%">汇报人部门</th>
		<th width="10%">汇报人单位</th>
		<th class="t-center" width="12%">操作</th>
	</tr>
	<#if officeWorkReportList?exists && (officeWorkReportList?size>0)>
		<#list officeWorkReportList as officeWorkReport>
		    <tr>
                <td>${officeWorkReport.beginTime?string('yyyy-MM-dd')?if_exists}</td>
                <td>${officeWorkReport.endTime?string('yyyy-MM-dd')?if_exists}</td>
				<td>
					<#if officeWorkReport.reportType?default("1") == "1">周报
					<#elseif officeWorkReport.reportType?default("2") == "2">月报
					</#if>
				</td>
				<td>${officeWorkReport.content?default("")}</td>
                <td>
               		${officeWorkReport.createUserName?default("")}
                </td>
				<td>
					${officeWorkReport.deptId?default("")}
				</td>
				<td>
					${officeWorkReport.unitId?default("")}
				</td>
				<td class="t-center">
					<a href="javascript:doView('${officeWorkReport.id!}');">查看</a>&nbsp;
					<#if canDelete>
					<a href="javascript:doDelete('${officeWorkReport.id!}');">删除</a>&nbsp;
					</#if>
				</td>
			</tr>
		</#list>
	<#else>
	   <tr><td colspan="8"> <p class="no-data mt-50 mb-50">还没有任何记录哦！</p></td></tr>
	</#if>
	</@htmlmacro.tableList>	
	<@htmlmacro.Toolbar container="#workReportDiv"></@htmlmacro.Toolbar>
</form>
</div>
</div>
<script type="text/javascript" src="${request.contextPath}/static/js/myscript.js"></script>
</@htmlmacro.moduleDiv>