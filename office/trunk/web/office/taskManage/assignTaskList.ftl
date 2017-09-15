<#import "/common/htmlcomponent.ftl" as htmlmacro>
<#import "/common/commonmacro.ftl" as commonmacro>
<@htmlmacro.showPrompt/>
<script>
function doDelete(id){
	if(!showConfirm('您确认要撤销该任务吗？')){
		return;
	}
	var options = {
       url:'${request.contextPath}/office/taskManage/taskManage-assignTask-delete.action?officeTaskManage.id='+id, 
       success : showReply,
       dataType : 'json',
       clearForm : false,
       resetForm : false,
       type : 'post',
       timeout : 3000
    };
	
    $('#mainform').ajaxSubmit(options);
}

function showReply(data){
	if(!data.operateSuccess){
		   if(data.errorMessage!=null&&data.errorMessage!=""){
			   showMsgError(data.errorMessage);
			   return;
		   }else if(data.fieldErrorMap!=null){
			  $.each(data.fieldErrorMap,function(key,value){
				   addFieldError(key,value+"");
			  });
		   }else{
		   	   showMsgError(data.promptMessage);
			   return;
		   }
	}else{
	   		showMsgSuccess("撤销任务成功! ","",function(){
	   			load("#cDiv", "${request.contextPath}/office/taskManage/taskManage-assignTask-list.action?pageIndex=${pageIndex?default(1)}&queryBeginDate=${queryBeginDate!}&queryEndDate=${queryEndDate!}");
			});
			return;
	}
}

function doDownload(url){
	document.getElementById('downloadFrame').src=url;
}

function doQuery(){
	var beginDate = document.getElementById("queryBeginDate");
	var endDate = document.getElementById("queryEndDate");
	if(!(checkAfterDateWithMsg(beginDate,endDate,"任务开始日期要小于任务结束日期，请更正！"))) return;
	var queryBeginDate = beginDate.value;
	var queryEndDate = endDate.value;
	load("#cDiv", "${request.contextPath}/office/taskManage/taskManage-assignTask-list.action?pageIndex=${pageIndex?default(1)}&queryBeginDate="+queryBeginDate+"&queryEndDate="+queryEndDate);
}

function doEdit(id){
	var queryBeginDate = $("#queryBeginDate").val();
	var queryEndDate = $("#queryEndDate").val();
	load("#cDiv", "${request.contextPath}/office/taskManage/taskManage-assignTask-edit.action?pageIndex=${pageIndex?default(1)}&isView=false&officeTaskManage.id="+id+"&queryBeginDate="+queryBeginDate+"&queryEndDate="+queryEndDate);
}

function doAdd(){
	var queryBeginDate = $("#queryBeginDate").val();
	var queryEndDate = $("#queryEndDate").val();
	load("#cDiv", "${request.contextPath}/office/taskManage/taskManage-assignTask-add.action?pageIndex=${pageIndex?default(1)}&queryBeginDate="+queryBeginDate+"&queryEndDate="+queryEndDate);
}

function doView(id){
	var queryBeginDate = $("#queryBeginDate").val();
	var queryEndDate = $("#queryEndDate").val();
	load("#cDiv", "${request.contextPath}/office/taskManage/taskManage-assignTask-edit.action?pageIndex=${pageIndex?default(1)}&isView=true&officeTaskManage.id="+id+"&queryBeginDate="+queryBeginDate+"&queryEndDate="+queryEndDate);
}
</script>
<@htmlmacro.moduleDiv titleName="">
<div class="pub-table-wrap" id="assignTaskListDiv">
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
	    <div class="fn-right">
	    	<a href="javascript:doAdd()" class="abtn-orange-new mr-15">发布任务</a>
	    </div>
	    <div class="fn-clear"></div>	
	</div>    
</div>
<form name="mainform" id="mainform" action="" method="post">
 <@htmlmacro.tableList id="tablelist">
    <tr>
		<th width="15%">任务名称</th>
    	<th width="7%">负责人</th>
    	<th width="13%">规定完成时间</th>
    	<th width="13%">实际完成时间</th>
    	<th width="12%">发起任务附件</th>
    	<th width="12%">完成任务附件</th>
    	<th width="8%"><nobr>延时情况</nobr></th>
    	<th width="11%">完成情况</th>
	    <th class="t-center" width="9%">操作</th>
    </tr>
   	<#if officeTaskManageList?exists&&(officeTaskManageList?size>0)>
    
       <#list officeTaskManageList as task >
        <tr>
        <td title="${task.taskName!}"><span <#if task.remindNumber==2>class="c-red"<#elseif task.remindNumber==1>class="c-orange"<#else></#if> style="line-height:20px;"><@htmlmacro.cutOff4List str="${task.taskName!}" length=25/></span></td>
        <td>${task.dealUserName?default('')}</td>
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
        <td><#if task.isDelay>延时</#if></td>
    	<td title="${task.finishRemark!}"><@htmlmacro.cutOff4List str="${task.finishRemark!}" length=15/></td>
    	<td class="t-center">
    	<#if task.state == 1>
     		<a href="javascript:void(0);" onclick="doEdit('${task.id}')"><img alt="编辑" src="${request.contextPath}/static/images/icon/edit.png"></a>
     		<a href="javascript:void(0);" onclick="doDelete('${task.id}')" class="ml-10"><img alt="删除" src="${request.contextPath}/static/images/icon/del2.png"></a>
     	<#else>
     		<a href="javascript:void(0);" onclick="doView('${task.id}')"><img alt="查看" src="${request.contextPath}/static/images/icon/view.png"></a>
     	</#if>
    	</td>
		</tr>
       </#list>
    <#else>
    <tr>
    	<td colspan="9">
    	<p class="no-data mt-20 mb-20">还没有任何记录哦！</p></td>
    </tr>
	</#if>
</@htmlmacro.tableList>
<#if officeTaskManageList?exists && officeTaskManageList?size gt 0>
<@htmlmacro.Toolbar container="#assignTaskListDiv" />
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