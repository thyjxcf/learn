<#import "/common/htmlcomponent.ftl" as htmlmacro>
<@htmlmacro.showPrompt/>
<@htmlmacro.moduleDiv titleName="">
<script>
function doEdit(workReportId){
	//openDiv("#classLayer", "#classLayer .close,#classLayer .submit,#classLayer .reset", "${request.contextPath}/office/meetingmanage/meetingmanage-meetingEdit.action?officeMeetingApply.id="+applyId+"&readonlyStyle="+readonlyStyle, null, null, "500px");
	//load("#workReportDiv", "${request.contextPath}/office/meetingmanage/meetingmanage-meetingEdit.action?officeMeetingApply.id="+applyId+"&readonlyStyle="+readonlyStyle);
	load("#workReportDiv", "${request.contextPath}/office/workreport/workReport-myWorkReportEdit.action?officeWorkReport.id="+workReportId);
}
function doView(workReportId){
	load("#workReportDiv", "${request.contextPath}/office/workreport/workReport-myWorkReportView.action?officeWorkReport.id="+workReportId);
}
//提交
function doSubmit(officeWorkReportId){
	if(!showConfirm("确认要提交吗?")){
		return;
	}
	$.getJSON("${request.contextPath}/office/workreport/workReport-myWorkReportSubmit.action", 
		{"officeWorkReport.id":officeWorkReportId}, function(data){
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
	   			myWorkReport();
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
	$.getJSON("${request.contextPath}/office/workreport/workReport-myWorkReport-cancel.action", 
		{"officeWorkReport.id":officeWorkReportId}, function(data){
		if(!data.operateSuccess){
		   	if(data.errorMessage!=null&&data.errorMessage!=""){
			   	showMsgError(data.errorMessage);
			   	return;
		   	}
		}else{
	   		showMsgSuccess(data.promptMessage,"",function(){
	   			myWorkReport();
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
	$.getJSON("${request.contextPath}/office/workreport/workReport-myWorkReport-delete.action", 
		{"officeWorkReport.id":officeWorkReportId}, function(data){
		if(!data.operateSuccess){
		   	if(data.errorMessage!=null&&data.errorMessage!=""){
			   	showMsgError(data.errorMessage);
			   	return;
		   	}
		}else{
	   		showMsgSuccess(data.promptMessage,"",function(){
	   			//load("#myWorkReportDiv", "${request.contextPath}/office/workreport/workReport-myWorkReportList.action");
	   			myWorkReport();
			});
			return;
		}
	}).error(function(XMLHttpRequest, textStatus, errorThrown){alert(XMLHttpRequest.status);});
}

function doApply(readonlyStyle){
	//openDiv("#classLayer", "#classLayer .close,#classLayer .submit,#classLayer .reset", "${request.contextPath}/office/meetingmanage/meetingmanage-meetingEdit.action?readonlyStyle="+readonlyStyle, null, null, "500px");
	load("#workReportDiv", "${request.contextPath}/office/workreport/workReport-myWorkReportEdit.action?readonlyStyle="+readonlyStyle);
}
</script>
<div class="pub-table-inner">
<div class="query-builder-nobg mt-15" style="padding:0 0 5px 0;">
    <div class="query-part">
    	<div class="query-tt ml-10"><span class="fn-left">状态：</span></div>
    	<div class="ui-select-box fn-left" style="width:120px;">
            <input type="text" class="ui-select-txt" value=""  readonly/>
            <input name="states" id="states" type="hidden" value=""  class="ui-select-value" />
            <a class="ui-select-close"></a>
            <div class="ui-option" myfunchange="myWorkReport">
        		<div class="a-wrap">
            	<a val=""><span>全部</span></a>
            	<a val="1" <#if states?default("") == "1">class="selected"</#if>><span>未提交</span></a>
                <a val="2" <#if states?default("") == "2">class="selected"</#if>><span>已提交</span></a>
                <a val="8" <#if states?default("") == "8">class="selected"</#if>><span>已撤回</span></a>
                </div>
            </div>
	    </div>
    	<div class="query-tt ml-10"><span class="fn-left">汇报类型：</span></div>
    	<div class="ui-select-box fn-left" style="width:120px;">
            <input type="text" class="ui-select-txt" value=""  readonly/>
            <input name="reportTypes" id="reportTypes" type="hidden" value=""  class="ui-select-value" />
            <a class="ui-select-close"></a>
            <div class="ui-option" myfunchange="myWorkReport">
        		<div class="a-wrap">
            	<a val=""><span>全部</span></a>
            	<a val="1" <#if reportTypes?default("") == "1">class="selected"</#if>><span>周报</span></a>
                <a val="2" <#if reportTypes?default("") == "2">class="selected"</#if>><span>月报</span></a>
                </div>
            </div>
	    </div>
    	<div class="query-tt ml-10"><span class="fn-left">汇报内容：</span></div>
        <input name="contents" id="contents" value="${contents!}" maxLength="30" class="input-txt fn-left" style="width:120px;"/>
	    <div class="query-tt ml-10"><span class="fn-left">开始日期：</span></div>
	    <@htmlmacro.datepicker name="beginTimes" id="beginTimes" style="width:120px;" value="${(beginTimes)?if_exists}"/>
	   	<div class="query-tt">&nbsp;-&nbsp;</div>
	    <@htmlmacro.datepicker name="endTimes" id="endTimes" style="width:120px;" value="${(endTimes)?if_exists}"/>
	    <a href="javascript:void(0);" onclick="myWorkReport();" class="abtn-blue fn-left ml-20">查找</a>
        <a href="javascript:void(0);" onclick="doApply('false');" class="abtn-orange-new fn-right applyForBtn" style="width:58px;">工作汇报</a>
        <div class="fn-clear"></div>
    </div>
</div>
<div id="wirkReportDiv"></div>
<div class="pub-table-wrap" id="myWorkReportDiv">
<form name="form1" action="" method="post">
	<@htmlmacro.tableList id="tablelist">
	<tr>
		<th width="10%">开始日期</th>
		<th width="10%">结束日期</th>
		<th width="10%">汇报类型</th>
		<th width="45%">汇报内容</th>
		<th width="9%">状态</th>
		<th class="t-center" width="16%">操作</th>
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
               		<#if officeWorkReport.state?default("1") == "1">未提交
					<#elseif officeWorkReport.state=="2">已提交
					<#else>已撤回
					</#if>
                </td>
				<td class="t-center">
					<#if officeWorkReport.state?exists && officeWorkReport.state == "8">
						<a href="javascript:doSubmit('${officeWorkReport.id!}');">提交</a> &nbsp;
						<a href="javascript:doEdit('${officeWorkReport.id!}');">编辑</a> &nbsp; 
						<a href="javascript:doView('${officeWorkReport.id!}');">查看</a>&nbsp;
					<#else>
						<#if officeWorkReport.state?exists && officeWorkReport.state == "1">
							<a href="javascript:doSubmit('${officeWorkReport.id!}');">提交</a> &nbsp;
							<a href="javascript:doEdit('${officeWorkReport.id!}');">编辑</a> &nbsp;
							<a href="javascript:doDelete('${officeWorkReport.id!}');">删除</a> &nbsp;
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
	<@htmlmacro.Toolbar container="#workReportDiv"></@htmlmacro.Toolbar>
</form>
</div>
</div>
<script type="text/javascript" src="${request.contextPath}/static/js/myscript.js"></script>
</@htmlmacro.moduleDiv>