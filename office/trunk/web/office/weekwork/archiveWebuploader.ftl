
//附件上传的js
<#macro archiveWebuploaderAttachmentJs  canEdit=true isTrue=false isAudit=false showAttachmentDivId='${showAttachmentDivId!}'  contentId='' editContentDivId='editContentDiv' isSend=true>
<script>	
	var attachmentUploader ;
	$(function(){
		vselect();
	})
	
	//初始化webuploader
	attachmentUploader = WebUploader.create({
        // 不压缩image
        resize: false,
        // swf文件路径
        swf: '${request.contextPath}/office/weekwork/webuploader/Uploader.swf',
        // 文件接收服务端。
        server: '${request.contextPath}/office/weekwork/weekwork-saveFileAttach.action',
        // 选择文件的按钮。可选。
        // 内部根据当前运行是创建，可能是input元素，也可能是flash.
        pick: {
        	id:'#attachmentUploader',
        	multiple :true 
        },
        fileVal :'uploadAttFile',
        fileNumLimit :9,
        fileSizeLimit:1048576000,
        fileSingleSizeLimit:${fileSize!}*1024*1024,
        formData :{
        	detailId:'${officeWorkArrangeDetail.id!}'
        },
        auto :false 	//设置为 true 后，不需要手动调用上传，有文件选择即开始上传
    });
    
    // 当有文件添加进来之前处理
    attachmentUploader.on( 'beforeFileQueued', function( file ) {
		file.ext =  file.ext.toLowerCase( );
		//doAddBackground();
		return true;
    });
    
    //该方法用来处理错误事件
    attachmentUploader.on('error', function(errorStr,max,file){
    	if(errorStr=='Q_EXCEED_NUM_LIMIT'){
    		showMsgError("上传附件的总个数不能超过"+max);
    	}else if(errorStr=='Q_EXCEED_SIZE_LIMIT'){
    		showMsgError("上传附件的总大小不能超过"+max/1024/1024+"M");
    	}else if(errorStr=='F_EXCEED_SIZE'){
    		showMsgError("上传的文件大小不能超过"+max/1024/1024+"M");
    	}
    });
    
    
    // 当有文件添加进来的时候
    attachmentUploader.on( 'fileQueued', function( file ) {
		if(file.name.length>33){
		   var fileName = file.name.substr(0,33)+'...'; 
		}else{
			fileName = file.name;
		}
		var innerHTML = '<li  id="'+file.id+'"><img src="'+_contextPath+'/static/images/icon/file/'+getFileType(file.ext)+'">';
        innerHTML+='<span style="width:280px;float:left;" title="'+file.name+'">'+fileName+'</span>';            
		innerHTML+='<span id="'+file.id+'toSave"></span></li>';
        $('#attachmentP').html($('#attachmentP').html()+innerHTML);
        attachmentUploader.upload();
    });
    
    // 文件上传过程中创建进度条实时显示。
    attachmentUploader.on( 'uploadProgress', function( file, percentage ) {
        closeTip();
        showTip("文件上传中....当前进度"+Math.floor(percentage*100) + "%");
        if(Math.floor(percentage*100)==100){
        	closeTip();
        	showTip("文件保存中...");
        }
    });
	
	//上传成功
    attachmentUploader.on( 'uploadSuccess', function( file  ,data) {
    	closeTip();
    	if(data.operateSuccess){
    		showMsgSuccess("文件保存完成");
			if(data.businessValue){
				var returnValue = data.businessValue.split("*");
			    var innerHtml =   '<a href="javascript:void(0);"  onclick="doDownload(\''+returnValue[1]+'\');" >下载</a>&nbsp;';
			    if('${extNames}'.indexOf(file.ext)!=-1){
			       	innerHtml += '<a href="javascript:void(0);" class="att_show" style="margin-left:10px;" dataId="'+returnValue[0]+'" onclick="viewAttachment(\''+returnValue[0]+'\',\''+file.ext+'\')">预览</a>';
			    } 
			       /* if(name.toUpperCase()=='GIF'||name.toUpperCase()=='JPG'||name.toUpperCase()=='JPEG'||name.toUpperCase()=='BMP'||name.toUpperCase()=='PNG'){
			        	innerHtml += '<a href="javascript:void(0);" class="att_show" dataId="'+returnValue[0]+'" onclick="showAttachment(\''+returnValue[0]+'\')">预览</a>';
			        }*/
			        innerHtml += '<a href="javascript:void(0);" style="margin-left:19px;"  onclick="doDeleteAtt(\'\',\''+returnValue[0]+'\',\''+file.id+'\')">删除</a> ';       		
				$("#"+file.id+"toSave").html(innerHtml);
			}
    	}else{
    		showMsgError("文件保存出错");
    	}
       
    });
	
	//上传出错
    attachmentUploader.on( 'uploadError', function( file ,data ) {
    	closeTip();
    	showMsgError("文件上传出错");
    });
	
	//无论上传是否成功 都会在上传完成时执行该事件
    attachmentUploader.on( 'uploadComplete', function( file ) {
        
    });
	
	//所有的事件执行都会触发这个事件
    attachmentUploader.on( 'all', function( type ) {
    
    });

</script>	
	
</#macro>

<#macro archiveWebuploaderEditViewer canEdit=true isTrue=false  isAudit=false showAttachmentDivId='showAttDiv' editContentDivId='editContentDiv' isSend=true loadDiv=true>
	<script type="text/javascript" src="${request.contextPath}/static/webuploader/webuploader.withoutimage.min.js"></script>
	<link rel="stylesheet" type="text/css" href="${request.contextPath}/static/webuploader/webuploader.css"/>
	<table border="0" cellspacing="0" cellpadding="0" class="table-list" style="margin-top:-1px;">
	<#if officeWorkArrangeOutline.attachmentContent?exists>
	<li class="view" id="attPcontent" dataExtName='${officeWorkArrangeOutline.attachmentContent.extName!}' dataValue="${officeWorkArrangeOutline.attachmentContent.id!}" >
	</#if>
    	<tr>
        	<td style="background:#fff;">
                <ul class="acc-wrap acc-wrap-dongguan">
                    <li class="fn-clear">
                    	<p class="tt">附<span class="ml-20">件</span>：</p>
                        <div class="dd doc-list">
				            <ul class="fn-clear" id="attachmentP">
				            	<#if officeWorkArrangeDetailList?exists &&officeWorkArrangeDetailList?size gt 0>
				            	<#list officeWorkArrangeDetailList as detail>
					                 <#if detail.attachments?exists>
										<#list detail.attachments as att>
											<li id="attP${att_index}">
							                    <img src="${request.contextPath}/static/images/icon/file/
							                    <#if att.extName=='pdf'>
												pdf.png<#elseif att.extName=='doc'||att.extName=='docx'>
												word.png<#elseif att.extName=='ppt'||att.extName=='pptx'>
												ppt.png<#elseif att.extName=='xls'||att.extName=='xlsx'>
												xls.png<#elseif att.extName=='csv'>
												csv.png<#elseif att.extName=='rtf'>
												rtf.png<#elseif att.extName=='wav'||att.extName=='mp3'>
												music.png<#elseif att.extName=='txt'>
												txt.png<#elseif att.extName=='mp4'||att.extName=='avi'||att.extName=='mov'>
												move.png<#elseif att.extName=='png'||att.extName=='jpg'||att.extName=='jpeg'||att.extName=='gif'||att.extName=='bmp'>
												jpg.png<#else>other.png</#if>">
							                    <span  style="width:280px;float:left;" title="${att.fileName!}"><@htmlmacro.cutOff str='${att.fileName!}' length=33/></span>
							                    <span id="${att.id}toSave">
							                        <a href="javascript:void(0);"  onclick="doDownload('${att.downloadPath!}');">下载</a>&nbsp;
							                        <#if extNames.indexOf(att.extName)!=-1>
					                					<a href="javascript:void(0);" class="att_show" style="margin-left:4px;" dataId="${att.id!}"   onclick="viewAttachment('${att.id!}','${att.extName!}')">预览</a>
						                			</#if>
					                				<#if !isTrue><a href="javascript:void(0);" style="margin-left:13px;" onclick="doDeleteAtt('${att_index}','${att.id!}','')">删除</a></#if>
							                    </span> 
							                </li>
										</#list>
									</#if>
								</#list>
								</#if>
				            </ul>																				
                            <#if canEdit && !isTrue &&!isAudit>  
	                            <div class="fn-clear">
									<p class="upload-span2" style="margin-top:5px;"><span id="attachmentUploader">上传附件</span></p>       
	                            </div>
							</#if>
                        </div>
                    </li>
                </ul>
            </td>
        </tr>
    </table>
    <div id="selectSign" class="popUp-layer showSgParam" style="display:none;width:50%;"/>
    <iframe id="downloadFrame" name="downloadFrame" allowTransparency="true" frameBorder="0" width="00%" height="00%" scrolling="auto" src="" style="display:none;"></iframe>
    <div id="remove_att" display="none"></div>
	<@archiveWebuploaderAttachmentJs canEdit=canEdit isTrue=isTrue isAudit=true showAttachmentDivId='${showAttachmentDivId!}' isSend=isSend/>
	
	<div class="docReader readerDiv" id="useShowDiv" >
	 		<div class="docReader " style="width:855px;height:740px;margin:0 auto;" id="showAttDiv" style="overflow-x:auto;height:738px" >
	 			<p class="noData">请点击附件预览按钮</p>
	 		</div>
			<div class="docReader" id="editContentDiv" style="width:853px;overflow-x:auto;display:none" ></div>
	</div>
	
    <!--<div class=" popUp-layer contentDivPop keep-div" id="fwLayer" style="display:none;z-index:998;">
		<p id="divTt" class="tt"><span href="javascript:void(0);"  onclick="closePop()" class="fn-right">关闭</span><span>附件/预览</span></p>
	    <div class="docReader" id="showAttDiv" style="width:855px;height:700px;display:none;"></div>
		<div class="docReader" id="editContentDiv" style="width:855px;height:700px;display:none;"></div>
	</div>-->
    <script>
    	//以下方法用来判断浏览器类型 控件使用 针对不同浏览器要使用不同的方法
		var browser;	//浏览器对象
		var version;	//浏览器版本
		
		// 请勿修改，否则可能出错 初始化方法用来判断浏览器类型
		var userAgent = navigator.userAgent, rMsie = /(msie\s|trident.*rv:)([\w.]+)/, rFirefox = /(firefox)\/([\w.]+)/, rOpera = /(opera).+version\/([\w.]+)/, rChrome = /(chrome)\/([\w.]+)/, rSafari = /version\/([\w.]+).*(safari)/;
		
		var ua = userAgent.toLowerCase();
		function uaMatch(ua) {
			var match = rMsie.exec(ua);
			if (match != null) {
				return {
					browser : "IE",
					version : match[2] || "0"
				};
			}
			var match = rFirefox.exec(ua);
			if (match != null) {
				return {
					browser : match[1] || "",
					version : match[2] || "0"
				};
			}
			var match = rOpera.exec(ua);
			if (match != null) {
				return {
					browser : match[1] || "",
					version : match[2] || "0"
				};
			}
			var match = rChrome.exec(ua);
			if (match != null) {
				return {
					browser : match[1] || "",
					version : match[2] || "0"
				};
			}
			var match = rSafari.exec(ua);
			if (match != null) {
				return {
					browser : match[2] || "",
					version : match[1] || "0"
				};
			}
			if (match != null) {
				return {
					browser : "",
					version : "0"
				};
			}
		}
		var browserMatch = uaMatch(userAgent.toLowerCase());
		if (browserMatch.browser) {
			browser = browserMatch.browser;
			version = browserMatch.version;
		}
    	
    	function doDownload(url){
    		document.getElementById('downloadFrame').src=url;
    	}
		
		//下载 url下载路径 conStatus 附件状态  extName 附件的扩展名 attId 附件id 
    	function doPdfDownload(url,conStatus,attId){
			if(url==null||url==''||url=="null"){
				showMsgError("文件下载的路径不存在");
			}else{
				if(conStatus==2||conStatus==9){
					document.getElementById('downloadFrame').src=url;
				}else if(conStatus==0||conStatus==1||conStatus==4){
					if(confirm("文件待转换或者文档转换中,无法提供下载,是否使用控件打开本文档")){
						window.open("${request.contextPath}/officedoc/common/remote-previewShow.action?id="+attId);
					}
				}else if(conStatus==3){
					if(confirm("文件转换失败,无法提供下载,是否使用控件打开本文档")){
						window.open("${request.contextPath}/officedoc/common/remote-previewShow.action?id="+attId);
					}
				}
			}
		}
		
		
    	//获得标签图片
    	function getFileType(fileType){
		    fileType = fileType.toLowerCase( );
		    var typeName='other.png';
		    if(fileType=='pdf'){typeName='pdf.png';} 
		    if(fileType=='doc'||fileType=='docx'){typeName='word.png';}
		    if(fileType=='ppt'||fileType=='pptx'){typeName='ppt.png';}
			if(fileType=='mp4'||fileType=='avi'||fileType=='mov'){typeName='move.png';}
			if(fileType=='txt'){typeName='txt.png';}
			if(fileType=='png'||fileType=='jpg'||fileType=='jpeg'||fileType=='bmp'||fileType=='gif'){typeName='jpg.png';}
			if(fileType=='csv'){typeName='csv.png';}
			if(fileType=='mp3'||fileType=='mav'){typeName='music.png';}
			if(fileType=='rtf'){typeName='rtf.png';}
			if(fileType=='xls'||fileType=='xlsx'){typeName='xls.png';}
			return typeName;
		}
		
		function doDeleteContentAtt(num,attId,fileId){
			 if(confirm("确认要删除该正文,该操作会删除服务器上的正文?")){
			 	doDelete(num,attId,fileId,1,1);
			 }
		}
		
		function doDeleteAtt(num,attId,fileId){
			 if(confirm("确认要删除该附件,该操作会删除服务器上的附件?")){
			 	doDelete(num,attId,fileId,0,0);
			 }
		}
		
		//删除附件
		function doDelete(num,attId,fileId,type,isContent,isNotClearHtml){
			if(isNotClearHtml){
				isNotClearHtml = true;
			}else{
				isNotClearHtml = false;
			}
			$.ajax({
				type: "POST",
				url: "${request.contextPath}/office/weekwork/weekwork-deleteFileAttach.action",
				data: $.param( {removeAttachmentId:attId},true),
				dataType: "json",
				success: function(data){
					if(data.operateSuccess){
						showMsgSuccess(data.promptMessage);
						if(!isNotClearHtml){
							if(num!=""){
								$('#attP'+num).remove();
							}else{
								$('#'+fileId).remove();
							}
						}
						if(type==1){
							if(fileId!=""){
								uploader.removeFile( fileId ,true);
							}
						}else{
							if(fileId!=""){
								attachmentUploader.removeFile( fileId ,true);
							}
						}
						hideDiv(attId);
						if(isContent==1){
						<#if isSend&&canEdit>
							$(".edit-content").show();
						</#if>
						}
					}else{
						showMsgError(data.errorMessage);
					}
				},
				error: function(XMLHttpRequest, textStatus, errorThrown){alert(XMLHttpRequest.status);}
			});
		}
		
		$(function(){
			vselect();
			$(".table-list th").css("font-size","12px");
			$(".table-list td").css("font-size","12px");
			
			if($("#attPcontent").size()>0){
					var attachmentId = $("#attPcontent").attr("dataValue");
					var dataExtName = $("#attPcontent").attr("dataExtName")
					viewAttachment(attachmentId,dataExtName);
				}else{
					$("#${showAttachmentDivId!}").hide();
					doAddBackground();
				}
			//默认加载预览;
		})
		
		
		//显示附件预览的错误
		function showAttachmentError(msg,attachmentId,extName){
			editDivHide();
			var strHtml = '<p class="noData">'+msg+'</p>';
			$("#${showAttachmentDivId!}").html(strHtml).show();
			$("#fwLayer").show();
			$("#divTt").show();
			$("#fwLayer").css("width","855px").css("height","764px");
			$("#fwLayer").css("top","190px").css("left","18%" );
		}
		
		//显示预览的附件
		function showAttachment(attachmentId,extName) {
			$("#${showAttachmentDivId!}").attr('attId',attachmentId).show();
			if(extName=='png'||extName=='jpg'||extName=='jpeg'||extName=='bmp'||extName=='gif'){
				$("#${showAttachmentDivId!}").css("overflow-x","auto");
			}else{
				$("#${showAttachmentDivId!}").css("overflow-x","");
			}
			editDivHide();
			doRemoveBackground();
			$("#fwLayer").show();
			$("#divTt").show();
			$("#fwLayer").css("width","855px").css("height","764px");
			$("#fwLayer").css("top","190px").css("left","18%" );
			//$("#${editContentDivId!}").hide();
			load("#${showAttachmentDivId!}","${request.contextPath}/office/common/showAttachment.action?attachmentId="+attachmentId);
		}
		
		//针对正文的 删除 附件的删除 进行修改
		function hideDiv(attachmentId){
			if($("#${showAttachmentDivId!}").attr('attId')==attachmentId){
				$("#${showAttachmentDivId!}").hide();
			}
			if($("#${editContentDivId!}").attr('attId')==attachmentId){
				editDivHide();
			}
			doAddBackground();
		}
		
		function doAddBackground(){
			editDivHide();
			var strHtml = '<p class="noData">请选中附件预览按钮进行查看</p>';
			$("#${showAttachmentDivId!}").html(strHtml);
			//$("#${showAttachmentDivId!}").html(strHtml).show();
			//$("#fwLayer").show();
			//$("#divTt").show();
			//$("#fwLayer").css("width","855px").css("height","764px");
			//$("#fwLayer").css("top","190px").css("left","18%" );
		}
		
		//预览附件 用来获取附件状态
		function viewAttachment(attachmentId,extName){
			$.ajax({
		        url:"${request.contextPath}/office/common/checkAttachment.action",
		        dataType:"json",
		        data:{"attachmentId":attachmentId},
		        type:"post",
		        success:function(data){
			        if(data.operateSuccess){
		   				showAttachment(data.businessValue,extName);
		   			}else{
		   				showAttachmentError(data.errorMessage,attachmentId,extName);
		   				return;
		   			}  
		        }
		    });
		}
		
		function doRemoveBackground(){
			$(".docReader .noData").hide();
		}
		
		function closePop(){
			editDivHide();
			$("#showAttDiv").hide();
			if (browser == "IE") {
				$("#fwLayer").hide();
			}else{
				$("#fwLayer").css("width","0px").css("height","0px");
			}
		}
		
		
		function editDivHide(){
		if (browser == "IE") {
			if('${editContentDivId!}'==""){
				$("#editContentDiv").hide();
				//$("#divTt").hide();
			}else{
				$("#${editContentDivId!}").hide();
				//$("#divTt").hide();
			}
		}else{
			if('${editContentDivId!}'==""){
				$("#editContentDiv").css("width","0px").css("height","0px");
				$("#editOcx").css("height","0px");
				$("#redSpan").hide();
				$("#editButtonDiv").hide();
				$("#divTt").hide();
			}else{
				$("#${editContentDivId!}").css("width","0px").css("height","0px");
				$("#editOcx").css("height","0px");
				$("#redSpan").hide();
				$("#editButtonDiv").hide();
				$("#divTt").hide();
			}
		}
	}
    </script>
    

</#macro>