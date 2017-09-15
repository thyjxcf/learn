<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>${webTitle?default("任务记录")}</title>
<#include "/common/css.ftl">
<#include "/common/js.ftl">
</head>
<#include "/common/handlefielderror.ftl">
<script language="javascript">
<!-- 删除 -->
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
	if(!confirm("确定要删除选中的任务吗？")){
	    return;
	}
	form1.action="deleteJob.action?objectName="+type;
	form1.submit();
}

function getImportFile(jobId){	
	var errorForm=document.getElementById('errorForm');
	if(errorForm){
		errorForm.action="getImportFile.action?covered=" + jobId;
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

jQuery(document).ready(function(){
	var $window_h=jQuery(window).height(); //可视窗口高度
	var $header_h=jQuery('.table-header').height();
	var $header_b=jQuery('.table1-bt').height(); 
	var $mainFrame_h=$window_h-$header_h-$header_b-6;
	jQuery('.table-content').height($mainFrame_h);
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
<form id="errorForm" name="errorForm" target="hiddenIframe" action="getImportFile.action" method="post"></form>
<form id="form1" name="form1" method="post">
<div class="table-all">
    <div class="table-header">
        <!--[if lte IE 6]>
    	<div style="position:absolute;z-index:-1;width:851px;height:30px;">  
        	<iframe style="width:851px;height:30px;border:0;filter:alpha(opacity=0);-moz-opacity:0"></iframe>  
        </div> 
        <![endif]-->
        <table width="100%" border="0" cellspacing="0" cellpadding="0" class="table1">
            <tr id="copy_tr">
            </tr>
        </table>
    </div>
    <div class="table-content">
    	<!--[if lte ie 8]> <div style="+zoom:1"><![endif]-->
        <table width="100%" border="0" cellspacing="0" cellpadding="0" class="table1 table-vline">
            <tr id="pri_tr">
                <th width="7%">选择</th>
                <th width="5%">序号</th>
                <th width="5%">所处位置</th>
                <th width="15%">文件名称</th>
                <th width="10%">任务状态</th>
                <th width="23%">任务结果</th>
                <th width="10%">导入时间</th>
                <th width="10%">操作人</th>
                <th width="20%">错误数据文件</th>
            </tr>
            <#list listOfJob as job>				
				<tr>
					<td><input type="checkbox" id="jobId "name="jobId" value="${job.id!}" /></td>
					<td>${job_index + 1}</td>
					<td><#if job.status?default(0) == 0>${job.jobPos}</#if></td>
					<td title="${job.name?default("无名称")}">${cutOut(job.name?default("无名称"), 30)}</td>
					<td>${appsetting.getMcode("DM-DRRWZT").get(job.status?default(0)?string)}</td>
					<td title="${job.resultMsg?default("")}">${cutOut(job.resultMsg?default(""), 300)}</td>
					<td>${job.jobStartTime?string("yyyy-MM-dd HH:mm")}</td>
					<td>${job.userId?default("")}</td>
					<#if job.errorFullFile?default("") != "">
					<td><a href="getErrorData.action?errorDataPath=${job.errorFullFile?default("")}&objectName=${job.objectName?default("")}">下载</a></td>
					<#else>
					<td>&nbsp;</td>
					</#if>
				</tr>		
			</#list>	
        </table>
        <!--[if lte ie 8]></div><![endif]-->
    </div>
</div>
<div class="table1-bt">
	<div class="f-left pl-20">
		<input type="checkbox" id="selectAll" onclick="onSelectAll();" />&nbsp;全选&nbsp;&nbsp;
    	<span class="input-btn2" onclick="doDelete('${objectName?default('')}');"><button type="button">删除</button></span>
    </div>
    <div class="clr"></div>
</div>
</form>
<iframe id="hiddenIframe" name="hiddenIframe" style="display:none"></iframe>
</body>
</html>