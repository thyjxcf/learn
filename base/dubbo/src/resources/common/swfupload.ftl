<#macro swfupload uploadUrl="${request.contextPath}/common/swfUpload.action" removeUrl="${request.contextPath}/common/swfUpload-remote!deleteFile.action">
<link rel="stylesheet" type="text/css" href="${request.contextPath}/static/swfupload/css/swfupload.css">
<script type="text/javascript" src="${request.contextPath}/static/swfupload/js/swfupload.js"></script>
<script type="text/javascript" src="${request.contextPath}/static/swfupload/js/swfupload.queue.js"></script>
<script type="text/javascript" src="${request.contextPath}/static/swfupload/js/fileprogress.js"></script>
<script type="text/javascript" src="${request.contextPath}/static/swfupload/js/handlers.js" charset="utf-8"></script>
<style>
.input-btns,.input-btns button{border:0;cursor:pointer;outline:none;font-size:12px;}
.input-btns{padding-left:13px;height:26px;width:50px;display:inline-block;}
.input-btns button{height:26px;font-size:12px;padding-right:12px;}
.input-btns{background:url(${request.contextPath}/static/images/button_bg.gif) no-repeat top right;}
.input-btns button{background:url(${request.contextPath}/static/images/button_bg.gif) no-repeat top right;color:black;}
</style>
<script type="text/javascript">
var swfUpload;
var delAttachIds = new Array();

window.onload = function() {
	var settings = {
			flash_url : "${request.contextPath}/static/swfupload/swf/swfupload.swf",
			upload_url: "${uploadUrl!};jsessionid=${request.getSession().getId()!}",
			post_params: {"albumId" : "${albumId?default('')}"},
			file_size_limit : "${fileSizeLimit}",
			file_types : "${fileTypes}",
			file_post_name: "uploadFile",
			file_types_description : "All Files",
			file_upload_limit : ${fileUploadLimit?default(0)},
			file_queue_limit : 0,
			custom_settings : {
				progressTarget : "swfUploadProgress",
				cancelButtonId : "swfBtnCancel"
			},
			debug: false,//是否显示调试的textarea
			<#if uploadDirect>//直接上传
			// Button settings
				//button_image_url: "${request.contextPath}/static/swfupload/images/XPButtonUpload.png",
				button_image_url: "${request.contextPath}/static/images/button_bg.png",
				button_width: "70",
				button_height: "26",
				button_placeholder_id: "spanButtonPlaceHolder",
				button_text: '<span class="input-btnn"><button type="button">上  传</button></span>',
				button_text_style: ".input-btnn { font-size:12px; height:26px; display:inline-block; }",
				//button_text_style: ".theFont { font-size:12px; }",
				button_text_left_padding: 12,
				button_text_top_padding: 3,
			<#else>
				// Button settings
				button_image_url: "${request.contextPath}/static/images/button_bg.png",
				button_width: "70",
				button_height: "26",
				button_placeholder_id: "spanButtonPlaceHolder",
				button_text: '<span class="input-btnn">浏  览</span>',
				button_text_style: ".input-btnn { font-size:12px; height:26px; display:inline-block; }",
				button_text_left_padding: 12,
				button_text_top_padding: 3,
			</#if>
			// The event handler functions are defined in handlers.js
			file_queued_handler : fileQueued,
			file_queue_error_handler : fileQueueError,
			file_dialog_complete_handler : fileDialogComplete,
			upload_start_handler : uploadStart,
			upload_progress_handler : uploadProgress,
			upload_error_handler : uploadError,
			upload_success_handler : uploadSuccess,
			upload_complete_handler : uploadComplete
			//queue_complete_handler : queueComplete
	};
	//自定义属性，是否停止上传
	swfUpload = new SWFUpload(settings);
	swfUpload.stopped = false;
	//初始化数据
	initAttachment();
 };
<#if !uploadDirect>//非直接上传
 function fileDialogComplete(numberselected, numberqueued) {
     if (swfUpload.getStats().files_queued > 0) {
    	 document.getElementById("swfBtnCancel").disabled = false;
     }
 }
</#if>
 function upload() {
	 if (swfUpload.getStats().files_queued > 0) {
	 	swfUpload.startUpload();
	 } else {
		 alert("请选择要上传的文件!");
	 }
 }
 
 function stop() {
 	if (swfUpload) {
		swfUpload.stopUpload();
	}
 }
 
 function remove(id,fileId,isTemp) {
 	if(isTemp){
 		getJSON("${removeUrl!}",{"albumId":'${albumId?default('')}',"fileId":fileId});
 	}else{
 		//把fileid记录下来;
 		delAttachIds.push(fileId);
 	}	
 		jQuery("#"+id).remove();
 }
 
 function download(filePath) {
 	document.location.href=filePath;
 }
 
 function initAttachment(){
 	<#if attachments?exists>
	<#list attachments as item>
		FileLoad("${item.id}","${item.fileName}","${item.fileSize}","${item.urlPath}","swfUploadProgress");		
	</#list>
	</#if>
}	
</script>
<div id="content">
	<input type="hidden" name="albumId" id="albumId" value="${albumId?default('')}" /> 
	<form id="uploadfrm" method="post" enctype="multipart/form-data">
		<div class="fieldset flash" id="swfUploadProgress">
			<span class="legend"></span>
		</div>
		<div id="divMovieContainer">
			<input id="filenamelist" type="hidden" name="filenamelist" />
			<span id="spanButtonPlaceHolder"></span>
			<#if !uploadDirect>
			&nbsp;&nbsp;&nbsp;<span class="input-btns pl-10" style="width:50px;font-weight:0;background:url(${request.contextPath}/static/images/button_bg.png) repeat-x center top;" onclick="upload();"><button type="button">上  传</button></span>
			<#--input type="button" value="上  传" onclick="upload();" style="background:url(${request.contextPath}/static/images/button_bg.png) repeat-x center top;width:61px;margin-left: 10px; font-size: 8pt; height: 27px;" /-->
			</#if>
			&nbsp;&nbsp;&nbsp;<span class="input-btns pl-10" onclick="stop();" style="display:none;width:50px;font-weight:0;background:url(${request.contextPath}/static/images/button_bg.png) repeat-x center top;"><button type="button">停  止</button></span>
			&nbsp;&nbsp;&nbsp;<span class="input-btns pl-10" id="swfBtnCancel" onclick="swfUpload.cancelQueue();" disabled="disabled" style="display:none;width:50px;font-weight:0;background:url(${request.contextPath}/static/images/button_bg.png) repeat-x center top;"><button type="button">取消所有</button></span>
			<#--input type="button" value="停  止" onclick="stop();" style="display: none; margin-left: 2px; font-size: 8pt; height: 29px;" />
			<input id="swfBtnCancel" type="button" value="取消所有" onclick="swfUpload.cancelQueue();" disabled="disabled" style="display:none;margin-left: 2px; font-size: 8pt; height: 29px;" /-->
		</div>
	</form>
	<div id="divFileProgressContainer" style="height:75px;"></div>
</div>
</#macro>