<#-- 限宽限高css样式 -->
<#macro ImgStyle name='' width='100' height='100'>
<style>
	#${name}{ width:expression((this.width > ${width}) ? "${width}px" : true);height:expression((this.height > ${height}) ? "${height}px" : true); max-height:${height}px; max-width:${width}px; border:0;}
</style>
</#macro>

<#-- 单文件上传（文件最大限制，处理上传的url，支持文件类型，支持文件类型的描述，回调js方法） -->
<#macro singleUpload index='' total='' filesize='10 MB' action='' fileTypes='*.*' description='所有文件' buttonTitle='请选择图片' callBack='' display=true>
<#if index==total>
	<link rel="stylesheet" type="text/css" href="${request.contextPath}/static/swfupload/common/default.css"/>
</#if>
<script type="text/javascript">
	<#--根据不同的浏览器加载不同的js文件 -->
	var navigatorName = "Microsoft Internet Explorer";  
    if(navigator.appName == navigatorName){   
        $.getScript("${request.contextPath}/static/swfupload/common/swfupload22.js");   
    }else{
        $.getScript("${request.contextPath}/static/swfupload/common/swfupload20.js"); 
    }   
	$.getScript("${request.contextPath}/static/swfupload/common/handlers.js",function(){initSingleUpload();});
	
	var swfu${index};
	var application_url = '${request.contextPath}/static/swfupload/';
	//上传完毕后回调js函数，不论成功还是失败都调用此方法
	var _uploadCallBack${index } = function(file, tempFile){
		document.getElementById("uploadTempFile").value=tempFile;
		${callBack?default('')};
	};
	var onloadUrl${index} = function(){
		swfu${index} = new SWFUpload({
			// Backend Settings
			upload_url: "${action}",
			post_params: {"albumId":"${albumId!}","fileId":"${fileId!}"},

			file_size_limit : "${filesize}",
			file_types : "${fileTypes}",
			file_types_description : "${description}",
			file_upload_limit : 0,

			file_queue_error_handler : fileQueueError,
			file_dialog_complete_handler : fileDialogComplete,
			upload_progress_handler : uploadProgress,
			upload_error_handler : uploadError,
			upload_success_handler : uploadSuccess,
			upload_complete_handler : uploadComplete,

			// Button Settings
			//button_image_url : "${request.contextPath}/static/swfupload1/images/SmallSpyGlassWithTransperancy_17x18.png",
			button_image_url : application_url+"/images/SmallSpyGlassWithTransperancy_17x18.png",
			button_placeholder_id : "spanButtonPlaceholder${index}",
			button_width: 80,
			button_height: 22,
			
			button_text : '<a class="abtn-blue">${buttonTitle! }<#--(最大：${filesize})--></a>',
			button_text_style : '.button { font-family: Helvetica, Arial, sans-serif; font-size: 12pt; } .buttonSmall { font-size: 12pt; }',
			button_text_top_padding: 0,
			button_text_left_padding: 18,
			button_window_mode: SWFUpload.WINDOW_MODE.TRANSPARENT,
			button_cursor: SWFUpload.CURSOR.HAND,
        	button_action : SWFUpload.BUTTON_ACTION.SELECT_FILE,
			// Flash Settings
			flash_url : application_url+"common/swfupload.swf",
			custom_settings : {
				upload_target : "divFileProgressContainer${index}",
				call_black : _uploadCallBack${index}<#-- 回调函数 -->
			},
			
			// Debug Settings
			debug: false
		});
	};
	
	var initSingleUpload = function(){
		<#if index == ''>
			<#-- 单个文件上传 -->
			setTimeout(function(){onloadUrl();},100);
		<#elseif index==total>
			<#-- 有多个上传框，需要依次调用初始化语法 -->
			var total = '${total}';
			for(var i=1; i<=parseInt(total); i++){
				eval("onloadUrl"+i+"()");
			}
		</#if>
	}
</script>
<span id="spanButtonPlaceholder${index}"></span>
<div id="divFileProgressContainer${index}" <#if !display>style="display:none"</#if>></div>

<#-- 上传成功后会在此input中填写临时文件路径 -->
<input type="hidden" name="uploadTempFile" id="uploadTempFile" value="" />
<#if index==total>
	<div id="thumbnails"></div>
</#if>
</#macro>