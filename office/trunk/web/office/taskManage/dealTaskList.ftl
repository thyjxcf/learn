<#import "/common/htmlcomponent.ftl" as htmlmacro>
<#import "/common/commonmacro.ftl" as commonmacro>
<@htmlmacro.showPrompt/>
<script>
function doDownload(url){
	document.getElementById('downloadFrame').src=url;
}

function doQuery(){
	var beginDate = document.getElementById("queryBeginDate");
	var endDate = document.getElementById("queryEndDate");
	if(!(checkAfterDateWithMsg(beginDate,endDate,"任务开始日期要小于任务结束日期，请更正！"))) return;
	var queryBeginDate = beginDate.value;
	var queryEndDate = endDate.value;
	load("#cDiv", "${request.contextPath}/office/taskManage/taskManage-dealTask-list.action?pageIndex=${pageIndex?default(1)}&queryBeginDate="+queryBeginDate+"&queryEndDate="+queryEndDate);
}

function doEdit(id){
	var queryBeginDate = $("#queryBeginDate").val();
	var queryEndDate = $("#queryEndDate").val();
	load("#cDiv", "${request.contextPath}/office/taskManage/taskManage-dealTask-edit.action?pageIndex=${pageIndex?default(1)}&isView=false&officeTaskManage.id="+id+"&queryBeginDate="+queryBeginDate+"&queryEndDate="+queryEndDate);
}
</script>
<@htmlmacro.moduleDiv titleName="">
<div class="pub-table-wrap" id="dealTaskListDiv">
<div class="pub-table-inner">
<div class="query-builder-nobg mt-15" style="padding:0 0 5px 0;">
    <div class="query-part">
	    <div class="query-tt ml-10">任务时间：</div>
        <@htmlmacro.datepicker msgName="任务起始日期" name="queryBeginDate" id="queryBeginDate" value="${queryBeginDate?default('')}" size="20" dateFmt="yyyy-MM-dd"/>
        <div class="query-tt">&nbsp;&nbsp;至&nbsp;&nbsp;</div>
        <@htmlmacro.datepicker msgName="任务结束日期" name="queryEndDate" id="queryEndDate" value="${queryEndDate?default('')}" size="20" dateFmt="yyyy-MM-dd" />
        
        <div class="fn-left ml-30">
	    	<a href="javascript:doQuery()" class="abtn-blue ml-30 fn-left">查找</a>
	    </div>
	    <div class="fn-clear"></div>	
	</div>
</div>

<form name="mainform" id="mainform" action="" method="post">
 <@htmlmacro.tableList id="tablelist">
    <tr>
		<th width="20%">任务名称</th>
    	<th width="12%">规定完成时间</th>
    	<th width="12%">实际完成时间</th>
    	<th width="12%">发起任务附件</th>
    	<th width="12%">完成任务附件</th>
    	<th width="20%">备注</th>
	    <th class="t-center" width="12%">操作</th>
    </tr>
   	<#if officeTaskManageList?exists&&(officeTaskManageList?size>0)>
    
       <#list officeTaskManageList as task >
        <tr>
        <td title="${task.taskName!}"><span <#if task.remindNumber==2>class="c-red"<#elseif task.remindNumber==1>class="c-orange"<#else></#if> style="line-height:20px;"><@htmlmacro.cutOff4List str="${task.taskName!}" length=25/></span></td>
        <td>${(task.completeTime?string('yyyy-MM-dd HH:mm'))?if_exists}</td>
        <td>${((task.actualFinishTime)?string('yyyy-MM-dd HH:mm'))?if_exists}</td>
        <td>
        	<#if task.leaderAttachment?exists>
 				<span id="span_${task.leaderAttachment.id!}">
					<a href="javascript:doDownload('${task.leaderAttachment.downloadPath!}');"><@htmlmacro.cutOff4List str="${task.leaderAttachment.fileName!}" length=15/></a>
				</span>
     		</#if>
     	</td>
     	<td>
     		<#if task.dealUserAttachment?exists>
 				<span id="span_${task.dealUserAttachment.id!}">
					<a href="javascript:doDownload('${task.dealUserAttachment.downloadPath!}');"><@htmlmacro.cutOff4List str="${task.dealUserAttachment.fileName!}" length=15/></a>
				</span>
     		</#if>
        </td>
    	<td title="${task.remark!}"><@htmlmacro.cutOff4List str="${task.remark!}" length=20/></td>
    	<td class="t-center">
    	<#if task.isDelay>
    		已过提交时间
    	<#else>
    		<#if task.state == 3>完成任务<#else><a href="javascript:void(0);" onclick="doEdit('${task.id}');">处理任务</a></#if>
    	</#if>
    	</td>
		</tr>
       </#list>
    <#else>
    <tr>
    	<td colspan="7">
    	<p class="no-data mt-20 mb-20">还没有任何记录哦！</p></td>
    </tr>
	</#if>
</@htmlmacro.tableList>
<#if officeTaskManageList?exists && officeTaskManageList?size gt 0>
<@htmlmacro.Toolbar container="#dealTaskListDiv" />
</#if>
</form>
</div>
</div>
<iframe name="downloadFrame" id="downloadFrame" style="display:none;"></iframe>
<script>
vselect();
</script>
<script src="${request.contextPath}/static/js/myscript.js"></script>
</@htmlmacro.moduleDiv>