<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<title>任务状态</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<#include "/common/css.ftl">
<#include "/common/js.ftl">
<script language="JavaScript" src="${request.contextPath}/static/js/buffalo.js"></script>
<#import "/common/htmlcomponent.ftl" as common />
<#include "/common/handlefielderror.ftl"> 
<script language="javascript">
var buffalo=new Buffalo('');
buffalo.async = false; //同步执行 

function changeJobSign(){ 
	buffalo.remoteActionCall("remoteImport.action","changeJobSign",[],function(reply){
		var result=reply.getResult();
		showMsgSuccess("操作成功");
		window.setTimeout("document.location.href = document.location.href",1000);
		//document.location.href = document.location.href;
	}); 	
} 

function stopSubmitTaskSign(){
	buffalo.remoteActionCall("remoteImport.action","stopSubmitTaskSign",[],function(reply){
		var result=reply.getResult();
		showMsgSuccess("设置从数据库取任务停止标志成功");
		window.setTimeout("document.location.href = document.location.href",1000);
		//document.location.href = document.location.href;
	}); 		
}

function stopTakeTaskSign(){
	buffalo.remoteActionCall("remoteImport.action","stopTakeTaskSign",[],function(reply){
		var result=reply.getResult();
		showMsgSuccess("设置从处理任务停止标志成功");
		window.setTimeout("document.location.href = document.location.href",1000);
	}); 		
}
 jQuery(document).ready(function(){
	jQuery("#table1").height((jQuery("#subIframe", window.parent.document).height()-jQuery('.head-tt').height() - jQuery("#p1").height()-jQuery("#p2").height()-jQuery("#d1").height()-5)/2);
	jQuery("#table2").height(jQuery("#table1").height()-25);
})
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
</body>
</html>