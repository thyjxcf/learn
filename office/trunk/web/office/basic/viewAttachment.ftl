<#import "/common/flash.ftl" as flash>
<div class="reader-container" align="center">
	<#if "pic"==fileType!>
		<img src="${attachment.downloadPath!}" style="max-width:1200px; max-height:733px;"/>
	<#elseif "video"==fileType!>
		<@flash.videoPlayer url="${attachment.swfUrl!}" extension="flv"  width="1200" height="675" />
	<#elseif "audio"==fileType!>
		<@flash.videoPlayer url="${attachment.downloadPath!}" extension="${attachment.extName!}" width="1200" height="40" />
	<#elseif "doc"==fileType!>
		<@flash.documentViewer url="${attachment.swfUrl!}" width="0" height="733" />
	</#if>
</div>
