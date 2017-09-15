<#import "/common/htmlcomponent.ftl" as htmlmacro>

<script language="javascript">

function changeJobSign(){ 
	$.post("${request.contextPath}/leadin/import/jsonChangeJobSign.action",{},function(){
		showMsgSuccess("操作成功");
		window.setTimeout("document.location.href = document.location.href",1000);
	}, 'json').error(function() {
		showMsgError("jsonChangeJobSign error");
    }); 
} 

function stopSubmitTaskSign(){
	$.post("${request.contextPath}/leadin/import/jsonStopSubmitTaskSign.action",{},function(){
		showMsgSuccess("设置从数据库取任务停止标志成功");
		window.setTimeout("document.location.href = document.location.href",1000);
	}, 'json').error(function() {
		showMsgError("jsonStopSubmitTaskSign error");
    }); 	
}

function stopTakeTaskSign(){
	$.post("${request.contextPath}/leadin/import/jsonStopTakeTaskSign.action",{},function(){
		showMsgSuccess("设置从处理任务停止标志成功");
		window.setTimeout("document.location.href = document.location.href",1000);
	}, 'json').error(function() {
		showMsgError("jsonStopTakeTaskSign error");
    }); 
}
</script>
<#function cutOut orgString maxLen>
	<#if orgString?length gt maxLen>
		<#return orgString?substring(0,maxLen)+"...">
	<#else>
		<#return orgString>
	</#if>
</#function>
<body> 
<form  name="form1">
<div class="head-tt">
<div class="tt-le">
 导入是否启动：${importMonitor.running?string}&nbsp;&nbsp;
<#if importMonitor.running>
	<span class="input-btn2" onClick="changeJobSign();"><button type="button">停止</button></span>
<#else>
	<span class="input-btn2" onClick="changeJobSign();"><button type="button">启动</button></span>
</#if>
&nbsp;&nbsp;从数据库取任务是否在运行：${importMonitor.submitTaskRunning?string}&nbsp;&nbsp;
<span class="input-btn2" onClick="stopSubmitTaskSign();"><button type="button">测试停止</button></span>
&nbsp;&nbsp;处理任务是否在运行：${importMonitor.takeTaskRunning?string}&nbsp;&nbsp;	  
<span class="input-btn2" onClick="stopTakeTaskSign();"><button type="button">测试停止</button></span> 
</div>
<div class="clr"></div>
</div>
<div id="d1">	 
   线程描述：${importMonitor.description} 
</div>
<div class="clr"></div>
<br/>
<p id="p1">正在排队的任务列表： ${ququeJobList.size()}</p>
		<@common.tableList divId="table1">
			<tr>
				<th>ID</td>
				<th>序号</td>				
				<th >文件名称</td>
				<th>任务状态</td>
				<th>文件上传时间</td>
				<th>任务开始时间</td>
				<th>任务结束时间</td>
				<th>任务结果</td>
			</tr>
			<#list ququeJobList as job>
			<tr>
				<td>${job.id}</td>
				<td>${job_index + 1}</td>
				<td title="${job.name?default("无名称")}">${cutOut(job.name?default("无名称"), 30)}</td>
				<td>${appsetting.getMcode("DM-DRRWZT").get(job.status?default(0)?string)}</td>
				<td>${job.jobStartTime?string("yy-MM-dd HH:mm")}</td>				
				<td>${((job.jobRunTime)?string('yy-MM-dd HH:mm'))?if_exists}</td>
				<td>${job.jobEndTime?string("yy-MM-dd HH:mm")}</td>
				<td title="${job.resultMsg?default("")}">${cutOut(job.resultMsg?default(""), 300)}</td>
			</tr>		
			</#list>	
			</@common.tableList>
<p id="p2">数据库中未处理及正在处理的任务列表： ${listOfJob.size()}</p>
		<@common.tableList divId="table2">
			<tr>
				<th >ID</th>
				<th>序号</th>				
				<th>文件名称</th>
				<th>任务状态</th>				
				<th>文件上传时间</th>
				<th>任务开始时间</th>
				<th>任务结束时间</th>
				<th>任务结果</th>		
			</tr>
			<#list listOfJob as job>
			<#if job_index == 50><#break></#if>
			<tr>
				<td>${job.id}</td>
				<td>${job_index + 1}</td>
				<td title="${job.name?default("无名称")}">${cutOut(job.name?default("无名称"), 30)}</td>
				<td>${appsetting.getMcode("DM-DRRWZT").get(job.status?default(0)?string)}</td>				
				<td>${job.jobStartTime?string("yy-MM-dd HH:mm")}</td>
				<td>${((job.jobRunTime)?string('yy-MM-dd HH:mm'))?if_exists}</td>
				<td>${job.jobEndTime?string("yy-MM-dd HH:mm")}</td>
				<td title="${job.resultMsg?default("")}">${cutOut(job.resultMsg?default(""), 300)}</td>
			</tr>					
			</#list>
		</@common.tableList>	
</form>
