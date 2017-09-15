<#import "/common/htmlcomponent.ftl" as common>
<@common.moduleDiv titleName="消息提醒">
<p class="tt t-center"><a href="javascript:void(0);" onclick="showMsgClose();" class="close">关闭</a><span>消息提醒</span></p>
<div class="wrap mt-10" >
	<div class="set-wrap" >
	<#if (msgURN + officeURN + docURN) gt 0>
    	<#if msgURN gt 0>
        	<div class="t-center mt-10">
        		<a href="javascript:void(0);" onclick="go2Module('${msgModule.url}?desktopIn=1','${msgModule.subsystem}','${msgModule.id}','${msgModule.parentid}','${msgModule.name}','desktop');showMsgClose();">
        		<span style="font-size:15px">您有<span style="color:red;">${msgURN?default(0)}</span>条新的邮件消息。</span></a>
        	</div>
        </#if>
        <#if officeURN gt 0>
        	<div class="t-center mt-10"><a href="javascript:void(0);" onclick="go2Module('${msgModule.url}?desktopIn=1','${msgModule.subsystem}','${msgModule.id}','${msgModule.parentid}','${msgModule.name}','desktop');showMsgClose();">
        	<span style="font-size:15px">您有<span style="color:red;">${officeURN?default(0)}</span>条新的办公消息。</span></a></div>
        </#if>
        <#if docURN gt 0>
        	<div class="t-center mt-10"><a href="javascript:void(0);" onclick="go2Module('${msgModule.url}?desktopIn=1','${msgModule.subsystem}','${msgModule.id}','${msgModule.parentid}','${msgModule.name}','desktop');showMsgClose();">
        	<span style="font-size:15px">您有<span style="color:red;">${docURN?default(0)}</span>条新的公文消息。</span></a></div>
        </#if>
    <#else>
    	<div class="t-center mt-20"><span style="font-size:15px">您没有未读的新消息。</span></div>
    </#if>
    </div>
</div>
<script>
function showMsgClose(){
	$('#msgLayer').remove();
}


$(document).ready(function(){
	var myheight = jQuery("#msgLayer").height();
	if(myheight < 100){
		myheight = 100;
	}
	jQuery("#msgLayer").height(myheight+5);
});
</script>
</@common.moduleDiv>