<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title></title>
<#include "/common/css.ftl">
<#include "/common/js.ftl">
<#include "/common/handlefielderror.ftl">
<script language="JavaScript" src="${request.contextPath}/static/js/buffalo.js"></script>
<#assign pageParam = pageParam>
<#assign viewParam = viewParam>

</head>
<script language="javascript">
jQuery.noConflict();

var buffalo=new Buffalo('');
buffalo.async = true; //同步执行
 	
var oInterval = "";
var replyId = "";
var imported = false;

function template() {
	if(validateTemplate() == false) return;
	document.form1.action="${request.contextPath}${pageParam.namespace}/${pageParam.mainActionName}-columnlist.action?objectName=${objectName}";	
	document.form1.target="_self";	
	document.form1.submit();
}

function templateConfig(){
	document.form1.action="${request.contextPath}/leadin/import/templateConfig.action?importFile=${viewParam.importFile!}&objectName=${objectName}&templateConfig=true";	
	document.form1.target="_self";	
	document.form1.submit();
}

function dataimport() {
	if(imported){
		return;
	}
	imported = true;
	
	clearMessages();

	if(validateImport() == false){
		imported = false;
		return false;
	}
	
	var content = document.form1.uploadfile.value;
	if(content.length==0) {
		addActionError("请先选择导入的数据文件！");
		imported = false;
		return false;
	}

	document.form1.action="${request.contextPath}${pageParam.namespace}/${pageParam.mainActionName}-dataImport.action";	
	document.form1.target="hiddenIframe";	
	document.form1.submit();
	openDiv("#import_panelWindow_tip","#import_panelWindow_tip .close1", "", null, "none", "none"); 
	jQuery("#import_panelWindow_tip").offset({"top":"100"});	
	fnStartInterval();
}

var errorFilePath= "${errorDataPath?default("")}";
function downloadErrorDatas(){
	var errorForm=document.getElementById('errorForm');
	if(errorForm){
		errorForm.action="${request.contextPath}${pageParam.namespace}/${pageParam.mainActionName}-getErrorData.action?errorDataPath=" + errorFilePath+"&objectName=${objectName}";
		errorForm.target="hiddenIframe";
		errorForm.submit();
	}
}

function fnStartInterval(){
	jQuery("#import_panelWindow_tip p:first").html("<font color='blue'>==>开始导入数据，请等待······</font><br>");
	oInterval = window.setTimeout("fnRecycle()",500);
}

function fnRecycle(){
	buffalo.remoteActionCall("${request.contextPath}${pageParam.namespace}/${pageParam.mainActionName}-remoteImport.action","remoteGetReplyById",[document.getElementById("replyCacheId").value],function(reply){
		var result=reply.getResult();
		if(result!=null){
			replyId = result.value;
			drawReplyMsg(reply);						
		}
		if(replyId == "status_end"){
			stopCycle();
		}else{
			oInterval = window.setTimeout("fnRecycle()",500);
		}
	});
}

function stopCycle(){
	buffalo.remoteActionCall("${request.contextPath}${pageParam.namespace}/${pageParam.mainActionName}-remoteImport.action","remoteRemoveReplyById",[document.getElementById("replyCacheId").value],function(reply){
		var result=reply.getResult();
		if(result!=null){
			document.getElementById("replyCacheId").value = result.value;
		}
		
		imported = false;
		var oTd = jQuery("#import_panelWindow_tip p:first");	
		oTd.html(oTd.html() + "<font color='blue'><==数据导入结束。</font>");
		getErrorDataInfo();
	}); 		
}

function getErrorDataInfo(){	
	var ret = "";
 	buffalo.remoteActionCall("${request.contextPath}${pageParam.namespace}/${pageParam.mainActionName}-remoteImport.action","remoteGetErrorData",["${objectName}"],function(reply){
		var result=reply.getResult();
		if(result!=null){
			ret = result;												
		}
		if (ret != ""){
			document.getElementById("errorbtn").style.display = "";
			errorFilePath = ret;
		}else{
			document.getElementById("errorbtn").style.display = "none";
			errorFilePath = ret;
		}
	}); 	
}

function drawReplyMsg(reply){
	var result = reply.getResult();
	var oTd = jQuery("#import_panelWindow_tip p:first");
	oTd.html("<font color='blue'>==>开始导入数据，请等待······</font><br>");
	if (result.actionMessages && result.actionMessages.length > 0) {
		for(var i = 0; i < result.actionMessages.length; i ++){
			oTd.html(oTd.html() + "<font color='blue'>" + result.actionMessages[i] + "</font><br>");
		}
    }
    if (result.actionErrors && result.actionErrors.length > 0) {
    	for(var i = 0; i < result.actionErrors.length; i ++){
    		oTd.html(oTd.html() + "<font color='blue'>" + result.actionErrors[i] + "</font><br>");;
		}
     }
}

function listJobInfo(){
	window.open("${request.contextPath}/leadin/import/listJobInfo.action?objectName=${objectName}");
}

jQuery(".upload-span").mouseover(function(){
	jQuery("#uploadfile").offset({"top":jQuery(".upload-span").offset().top });
})

function uploadFile(){
	jQuery("#uploadFilePath").val(jQuery("#uploadfile").val());
}

jQuery(function(){
	jQuery("#uploadfile").css({"position":"absolute","-moz-opacity":"0","opacity":"0","filter":"alpha(opacity=0)","width":jQuery(".upload-span button").width() + 27,"height":jQuery(".upload-span").height(),"cursor":"pointer"});
	jQuery("#uploadfile").offset({"left":jQuery(".upload-span").offset().left});		
	jQuery("#uploadfile").css({"display":""});
})

</script>
<body>
<form id="errorForm" name="errorForm" target="hiddenIframe" action="${request.contextPath}${pageParam.namespace}/${pageParam.mainActionName}-getErrorData.action?objectName=${objectName}" method="post">
</form>
<form  name="form1"  target="hiddenIframe" action="${request.contextPath}${pageParam.namespace}/${pageParam.mainActionName}-dataImport.action" method="post" ENCTYPE="multipart/form-data">
<input type="hidden" name="objectName" value="${objectName}">
<input type="hidden" name="unitid" value="${unitid?default("")}">
<input type="hidden" name="replyCacheId" id="replyCacheId" value="${replyCacheId?default("")}" >
<div class="import-tt">
	<@displayInfoMacro/> 
    <p>选择文件：<input type="text" id="uploadFilePath" name="uploadFilePath" value="" readonly="true" class="input-txt300 input-readonly"/>
    <span class="input-btn2 ver-mi upload-span"><button type="button">浏览</button></span>
    <input style="display:none" id="uploadfile" name="uploadfile" hidefocus type="file" onchange="uploadFile();" ></p>
    <p <#if pageParam.displayCovered?default(true) == false>style="display:none"</#if>><input type="checkbox" class="chk" name="covered" value="1" <#if covered?default("") == "1" || pageParam.displayCovered?default(true) == false> checked </#if>/>覆盖原有数据</p>
</div>

<div class="tips-p pya-10">
	<p class="b12">说明：</p>
    <@remark/>
    <p class="b12 mt-10">导入文件格式说明：</p>
    <@importDescriptionMacro/> 
</div>
<div class="table1-bt t-center">
	<span class="bspan">&nbsp;</span>
    <span class="input-btn2 showDiv" onclick="dataimport();"><button type="button">开始导入</button></span>
    <#if pageParam.hasTask><span class="input-btn2 ml-20" onclick="listJobInfo();"><button type="button">查看任务</button></span></#if>
    <#if pageParam.hasTemplate?default(true)><span class="input-btn2 ml-20" onclick="template();"><button type="button">模板下载</button></span></#if>
    <@buttonBar/>
   	<span id="errorbtn" style="display:<#if errorDataPath?default("") == "">none</#if>;" class="input-btn2 ml-20" onclick="downloadErrorDatas();"><button type="button">错误数据</button></span>
</div>
<div class="jwindow jwindow-tips" id="import_panelWindow_tip"><a href="#" class="close close1"></a><p class="p-box"></p></div>
<iframe id="hiddenIframe" name="hiddenIframe" style="display:none" />
</body>
<script>
//jQuery(document).ready(function(){
//    jQuery("#mainContent").height(jQuery(".mainFrame", window.parent.document).height() - jQuery('.table1-bt').height());
//})
</script>
</html>