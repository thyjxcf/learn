<#import "/common/htmlcomponent.ftl" as htmlmacro>
<@htmlmacro.showPrompt/>
<@htmlmacro.moduleDiv titleName="">
<script>
$(function(){
	smsSend();
});

function smsSend(){
	load("#smsSendDiv", "${request.contextPath}/office/msgcenter/msgcenter-msgSmsSend.action");
}
function smsRecoder(){
	load("#smsSendDiv", "${request.contextPath}/office/msgcenter/msgcenter-msgSmsAdmin.action");
}

</script>
<div class="msg-content">
	<ul class="msg-title pub-tab-list">
		<li class="current" onclick="smsSend();">发短信</li>
		<li onclick="smsRecoder();">短信记录</li>
	</ul>
</div>
<div id="smsSendDiv" class="msg-module"></div>
<script type="text/javascript" src="${request.contextPath}/static/js/myscript.js"></script>
</@htmlmacro.moduleDiv>