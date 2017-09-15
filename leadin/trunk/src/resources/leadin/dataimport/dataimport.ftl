<div id="importDiv">
<#import "/common/htmlcomponent.ftl" as htmlmacro>
<#assign pageParam = pageParam>
<#assign viewParam = viewParam>
<script language="javascript">
var oInterval = "";
var replyId = "";
var imported = false;

function template() {
	if(validateTemplate() == false) return;
	load('#importDiv',"${request.contextPath}${pageParam.namespace}/${pageParam.mainActionName}-columnlist.action",null,null,null,$("form[name='form1']").serializeArray());
}

function templateConfig(){
	document.form1.action="${request.contextPath}/leadin/import/templateConfig.action?importFile=${viewParam.importFile?default('')}&objectName=${objectName}&templateConfig=true";	
	document.form1.target="_self";	
	document.form1.submit();
}

function dataimport() {
	if(imported){
		return;
	}
	imported = true;

	if(validateImport() == false){
		imported = false;
		return false;
	}
	
	var content = $("#uploadFilePath").html();
	if(content.length==0) {
		addActionError("请先选择导入的数据文件！");
		imported = false;
		return false;
	}

	document.form1.action="${request.contextPath}${pageParam.namespace}/${pageParam.mainActionName}-dataImport.action";	
	document.form1.target="hiddenIframe";	
	document.form1.submit();	
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
	$("#import_panelWindow_tip").html("<b>导入处理：</b>"+"<p class='c-blue'>==>开始导入数据，请等待······</p>");
	oInterval = window.setTimeout("fnRecycle()",500);
}

function fnRecycle(){
	$.post("${request.contextPath}${pageParam.namespace}/${pageParam.mainActionName}-jsonGetReplyById.action", {'replyId':document.getElementById("replyCacheId").value}, function(data) {
		var result=data.reply;
		if(result!=null){
			replyId = result.value;
			drawReplyMsg(result);						
		}
		if(replyId == "status_end"){
			stopCycle();
		}else{
			oInterval = window.setTimeout("fnRecycle()",500);
		}
	}, 'json').error(function() {
		showMsgError("jsonGetReplyById error");
    }); 
}

function stopCycle(){
	$.post("${request.contextPath}${pageParam.namespace}/${pageParam.mainActionName}-jsonRemoveReplyById.action",{'replyId':document.getElementById("replyCacheId").value},function(data){
		var result=data.reply;
		if(result!=null){
			document.getElementById("replyCacheId").value = result.value;
		}
		
		imported = false;
		var oTd = $("#import_panelWindow_tip");	
		oTd.html(oTd.html() + "<p class='c-blue'><==数据导入结束。</p>");
		getErrorDataInfo();
	}, 'json').error(function() {
		showMsgError("jsonRemoveReplyById error");
    }); 		
}

function getErrorDataInfo(){	
	var ret = "";
 	$.post("${request.contextPath}${pageParam.namespace}/${pageParam.mainActionName}-jsonGetErrorData.action",{'objectName':"${objectName}"},function(data){
		var result=data.filePath;
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
	}, 'json').error(function() {
		showMsgError("jsonGetErrorData error");
    });  	
}

function drawReplyMsg(result){
	var oTd = $("#import_panelWindow_tip");
	oTd.html("<b>导入处理：</b><br>"+"<p class='c-blue'>==>开始导入数据，请等待······</p>");
	if (result.actionMessages && result.actionMessages.length > 0) {
		for(var i = 0; i < result.actionMessages.length; i ++){
			oTd.html(oTd.html() + "<p class='c-blue'>" + result.actionMessages[i] + "</p>");
		}
    }
    if (result.actionErrors && result.actionErrors.length > 0) {
    	for(var i = 0; i < result.actionErrors.length; i ++){
    		oTd.html(oTd.html() + "<p class='c-blue'>" + result.actionErrors[i] + "</p>");
		}
     }
}

function listJobInfo(){
	var params = "";
	<#list paramsList as x>
		params += "&${x[0]?default("")}=${x[1]?default("")}";
	</#list>
	var url ="${request.contextPath}${pageParam.namespace}/${pageParam.mainActionName}.action?objectName=${objectName}"+params;
	var actionParam = "?objectName=${objectName}&importType=${importType!}&url="+encodeURIComponent(url) + "&userDefinedUrl=${pageParam.userDefinedUrl!}";

	load("#importDiv","${request.contextPath}/leadin/import/listJobInfo.action"+actionParam);
}

</script>

<form id="errorForm" name="errorForm" target="hiddenIframe" action="${request.contextPath}${pageParam.namespace}/${pageParam.mainActionName}-getErrorData.action?objectName=${objectName}" method="post">
</form>
<form  name="form1"  target="hiddenIframe" action="${request.contextPath}${pageParam.namespace}/${pageParam.mainActionName}-dataImport.action" method="post" ENCTYPE="multipart/form-data">
<input type="hidden" name="objectName" value="${objectName}">
<input type="hidden" name="unitid" value="${unitid?default("")}">
<input type="hidden" name="replyCacheId" id="replyCacheId" value="${replyCacheId?default("")}" >
<div class="query-builder">
  <@displayInfoMacro/> 
  <div class="query-part">
	<!--input、a、span是内链元素，同行对齐不需要左浮动和有浮动-->
    <span>文件导入：</span>
    <a href="#" class="acc-link upfile-name" style="display:none;" id="uploadFilePath"></a>
    <span class="upload-span"><a href="#" class="abtn-blue upfile-btn">选择文件</a></span>       
    <input style="display:none" id="uploadfile" name="uploadfile" hidefocus type="file" onchange="uploadFile();" >
    <span class="ui-checkbox ml-30 <#if covered?default("") == "1" || pageParam.displayCovered?default(true) == false>ui-checkbox-current</#if>" <#if pageParam.displayCovered?default(true) == false>style="display:none"</#if>><input type="checkbox" class="chk" name="covered" value="1" <#if covered?default("") == "1" || pageParam.displayCovered?default(true) == false> checked </#if>/>覆盖原有数据</span>
   </div>
</div>
</form>
<p class="t-center pt-30">
	<a href="javascript:void(0);" class="abtn-blue-big" onclick="dataimport();">开始导入</a>
    <#if pageParam.hasTemplate?default(true)><a href="javascript:void(0);" class="abtn-blue-big" onclick="template();">模板下载</a></#if>
    <#if pageParam.hasTask><a href="javascript:void(0);" class="abtn-blue-big" onclick="listJobInfo();">查看任务</a></#if>
    <@buttonBar/>
    <a href="javascript:void(0);" class="abtn-gray-big" id="errorbtn" onclick="downloadErrorDatas();" style="display:<#if errorDataPath?default("") == "">none</#if>;">错误数据</a>
</p>    
<div class="import-explain">
	<p class="mt-20" id="import_panelWindow_tip"></p>
	<p class="mt-20"><b>说明：</b></p>
	<p><@remark/></p>
    <p class="mt-20"><b>导入文件格式说明：</b></p>
    <p><@importDescriptionMacro/></p>
</div>
<iframe id="hiddenIframe" name="hiddenIframe" style="display:none" />

<script language="javascript">
$(function(){
	vselect();

	$('.upfile-name').click(function(){
		$('.upfile-btn').text('上传文件');
		$(this).hide();
		$("#uploadFilePath").html('');
		$("#uploadfile").val('');
		resetFilePos();
	});
	
	$(".upload-span").mouseover(function(){
		$("#uploadfile").offset({"top":$(".upload-span").offset().top });
	});
	
	resetFilePos();
})

function uploadFile(){
	$("#uploadFilePath").html($("#uploadfile").val());
	$('.upfile-name').show();
	$('.upfile-btn').text('重新上传');	
	resetFilePos();
}

function resetFilePos(){
	$("#uploadfile").css({"position":"absolute","-moz-opacity":"0","opacity":"0","filter":"alpha(opacity=0)","width":$(".upload-span a").width() + 27,"height":$(".upload-span").height(),"cursor":"pointer"});
	$("#uploadfile").offset({"left":$(".upload-span").offset().left});		
	$("#uploadfile").css({"display":""});
}
</script>
</div>
