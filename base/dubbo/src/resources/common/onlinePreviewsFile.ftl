<#import "/common/flash.ftl" as flash>
<div class="document-reader">
	<div class="fn-clear">
        <div class="doc-tit">
        	<p class="name">
        	<img src="${request.contextPath}/static/images/icon/<#if 'pic'==fileType!>picture.png<#elseif 'video'==fileType!>video.png<#elseif 'audio'==fileType!>audio.png<#elseif 'doc'==fileType!>document.png</#if>" alt="${attachment.extName!}">${attachment.fileName!}
        	<#if down!><a class="f12 ml-10" href="javascript:void(0);"  onclick="doDownload('${attachment.downloadPath!}')" >下载</a></#if></p>
        	<p class="info">上传于${((attachment.creationTime)?string('yyyy-MM-dd'))?if_exists}</p>
        </div>
        <p class="doc-up"><a href="javascript:void(0);"  onclick="back('${retUrl!}')" class="abtn-blue-big">返回</a></p>
    </div>
    	<div  align="center" style="width:1200px;height:475px;">
	    	<#if "pic"==fileType!>
				<img src="${attachment.downloadPath!}"  style="max-width:1200px; max-height:475px;"  />
			<#elseif "video"==fileType!>
				<@flash.videoPlayer url="${attachment.swfUrl!}" extension="flv"  width="1200" height="675" />
			<#elseif "audio"==fileType!>
				<@flash.videoPlayer url="${attachment.downloadPath!}" extension="${attachment.extName!}" width="1200" height="40" />
			<#elseif "doc"==fileType!>
				<@flash.documentViewer url="${attachment.swfUrl!}" width="1200" height="475" />
			</#if>
    	 </div>
</div>
<iframe id="downFrame" name="downFrame" allowTransparency="true" frameBorder="0" width="100%" height="100%" scrolling="auto" src=""></iframe>
<script>
function back(url){
	load("#${divId!}","${request.contextPath}"+url);
}
function doDownload(url){
		document.getElementById('downFrame').src=url;
	}
</script>