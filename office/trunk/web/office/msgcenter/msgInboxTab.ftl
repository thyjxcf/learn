<script type="text/javascript" src="${request.contextPath}/static/js/myscript.js"></script>
<script>
$(document).ready(function() {
	loadMsgInbox('${receiveType?default(2)}');
});
function loadMsgInbox(receiveType){
	var url = "";
	if(receiveType == 2){
		url = "${request.contextPath}/office/msgcenter/msgcenter-msgInboxUser.action?readType=${readType!}";
	}else{
		url = "${request.contextPath}/office/msgcenter/msgcenter-msgInboxOther.action?receiveType="+receiveType+"&readType=${readType!}";
	}
	load("#msgInboxDiv", url);
}
</script>
<div class="msg-content">
	<ul class="msg-title pub-tab-list">
    	<li <#if receiveType?default(2) == 2>class="current"</#if> id="userTabLi" onclick="loadMsgInbox(2);">个人收件箱(<span <#if unReadNum gt 0>style="color:red;"</#if>>${unReadNum?default(0)}</span>/${totalNum?default(0)})</li>
        <#if deptReceiver>
        <li <#if receiveType?default(2) == 4>class="current"</#if> id="deptTabLi" onclick="loadMsgInbox(4);">部门收件箱(<span <#if dUnReadNum gt 0>style="color:red;"</#if>>${dUnReadNum?default(0)}</span>/${dTotalNum?default(0)})</li>
        </#if>
        <#if unitReceiver>
        <#if standard>
        	<li <#if receiveType?default(2) == 5>class="current"</#if> id="unitTabLi" onclick="loadMsgInbox(5);">单位收件箱(<span <#if uUnReadNum gt 0>style="color:red;"</#if>>${uUnReadNum?default(0)}</span>/${uTotalNum?default(0)})</li>
    	</#if>
    	</#if>
    </ul>
    <div class="msg-module" id="msgInboxDiv"></div>
</div>