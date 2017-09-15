<#assign importType2=stack.findValue("@net.zdsoft.leadin.dataimport.common.DataImportConstants@IMPORT_TYPE_TEMP_2")>
<script language="javascript">
function doDelete(type){
	var flag = false;
	var form1=document.getElementById('form1');
	var singleCheckboxes = document.getElementsByName("jobId");
    for (i = 0; i < singleCheckboxes.length; i++) {
    	if(singleCheckboxes[i].checked){
    		flag = true;
			break;
    	}
    }
	if(!flag){
		showMsgWarn("请选择要删除的任务!");
		return;
	}
	if(!showConfirm("确定要删除选中的任务吗？")){
	    return;
	}
	
	var jsonUrl = "${request.contextPath}/leadin/import/jsonDeleteJob.action?objectName="+type;
	
	<#if importType?default('') == importType2>
		jsonUrl = "${request.contextPath}/${userDefinedUrl!}-jsonDeleteJob.action"
	</#if>
	
	$("#form1").ajaxSubmit({
	     type: "post",
	     url: jsonUrl,
	     dataType: "json",
	     success: function(result){
	     	showMsgSuccess("删除成功","",function(){
	     		load("#importDiv","${request.contextPath}/leadin/import/listJobInfo.action?objectName="+type+"&importType=${importType!}&url="+encodeURIComponent('${url}')+"&userDefinedUrl=${userDefinedUrl!}");
	     	});
	     },
	     error:function(response){
	     	showMsgError("删除失败:" + response.responseText);
	     }
	 });
}

function doViewRecord(jobId) {
	var param = "?objectName=${objectName?default('')}&importType=${importType!}&url="+encodeURIComponent('${url}') 
		+ "&userDefinedUrl=${userDefinedUrl!}&covered="+jobId;
	load("#importDiv","${request.contextPath}/${userDefinedUrl!}.action"+param);
}

function getImportFile(jobId){	
	var errorForm=document.getElementById('errorForm');
	if(errorForm){
		errorForm.action="${request.contextPath}/leadin/import/getImportFile.action?covered=" + jobId;
		errorForm.target="hiddenIframe";
		errorForm.submit();
	}
}
//全选
function onSelectAll(){	
	if( document.getElementById("selectAll").checked){
		checkAllByStatus('jobId','checked');
	}
	else{
		checkAllByStatus('jobId','');
	}
}

function doBack(){
	load('#importDiv','${url}');
}

function doRefresh(){
	load("#importDiv","${request.contextPath}/leadin/import/listJobInfo.action?objectName=${objectName?default('')}&importType=${importType!}&url="+encodeURIComponent('${url}')+ "&userDefinedUrl=${userDefinedUrl!}");
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
<form id="errorForm" name="errorForm" target="hiddenIframe" action="${request.contextPath}/leadin/import/getImportFile.action" method="post"></form>
<form id="form1" name="form1" method="post">


<table border="0" cellspacing="0" cellpadding="0" class="public-table table-list">
    <tr>
        <th width="5%">选择</th>
        <th>序号</th>
        <th>所处位置</th>
        <th>文件名称</th>
        <th>任务状态</th>
        <th width="30%">任务结果</th>
        <th>导入时间</th>
        <th>操作人</th>
        <th>错误数据文件</th>
        <#if importType?default('') == importType2>
        	<th width="8%">操作</th>
        </#if>
    </tr>
    <#if listOfJob?exists && listOfJob?size gt 0>
    <#list listOfJob as job>				
		<tr>
			<td class="t-center"><p><span class="ui-checkbox"><input type="checkbox" id="jobId "name="jobId" value="${job.id!}" class="chk"></span></p></td>
			<td>${job_index + 1}</td>
			<td><#if job.status?default(0) == 0>${job.jobPos}</#if></td>
			<td title="${job.name?default("无名称")}">${cutOut(job.name?default("无名称"), 30)}</td>
			<td>${appsetting.getMcode("DM-DRRWZT").get(job.status?default(0)?string)}</td>
			<td title="${job.resultMsg?default("")}" width="450" style="word-break: break-all;">${cutOut(job.resultMsg?default(""), 300)}</td>
			<td>${job.jobStartTime?string("yyyy-MM-dd HH:mm")}</td>
			<td>${job.userId?default("")}</td>
			<#if job.errorFullFile?default("") != "">
			<td><a href="${request.contextPath}/leadin/import/getErrorData.action?errorDataPath=${job.errorFullFile?default("")}&objectName=${job.objectName?default("")}">下载</a></td>
			<#else>
			<td>&nbsp;</td>
			</#if>
			<#if importType?default('') == importType2>
	        	<td><a href="javascript:void(0);" class="abtn-blue" onclick="doViewRecord('${job.id!}');">查看</a></td>
	        </#if>
		</tr>		
	</#list>
	<#else>
	<tr>
	<td colspan=55><p class="no-data mt-50 mb-50">还没有任何记录哦！</p></td>
	</tr>
	</#if>	
</table>
<#if listOfJob?exists && listOfJob?size gt 0>
<div class="base-operation">
	<p class="opt">
    	<span class="ui-checkbox ui-checkbox-all" data-all="no"><input type="checkbox" class="chk" id="selectAll" onclick="onSelectAll();" >全选</span>
        <a href="javascript:void(0);" class="abtn-blue" onclick="doDelete('${objectName?default('')}');">删除</a>
    </p>
</div>
</#if>
 <p class="t-center pt-30">
	 <a href="javascript:void(0);" class="abtn-blue-big" onclick="doRefresh();">刷新</a>
    <a href="javascript:void(0);" class="abtn-blue-big" onclick="doBack();">返回</a>
</p>
</form>
<iframe id="hiddenIframe" name="hiddenIframe" style="display:none"></iframe>

<script type="text/javascript" src="${request.contextPath}/static/js/myscript.js"></script>
<script language="javascript">
$(function(){
	vselect();
})
</script>