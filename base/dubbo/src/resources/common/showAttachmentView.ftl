<#import "/common/flash.ftl" as flash>
<p class="tt"><a href="#" class="close">关闭</a><span>预览</span></p>
<div class="reader-container" align="center">
	<#if "pic"==fileType!>
		<img src="${attachment.downloadPath!}" style="max-width:950px; max-height:500px;"/>
	<#elseif "doc"==fileType!>
		<@flash.documentViewer url="${attachment.swfUrl!}" width="1000" height="567"/>
	</#if>
</div>