<#import "/common/htmlcomponent.ftl" as common />
<@common.moduleDiv titleName="">
<script>
function meetManage(){
	var url="${request.contextPath}/office/executiveMeet/executiveMeet-meetManage.action";
	load("#executiveMeetDiv", url);
}
function myMeet(){
	var url="${request.contextPath}/office/executiveMeet/executiveMeet-myMeet.action";
	load("#executiveMeetDiv", url);
}
function issueApply(){
	var url="${request.contextPath}/office/executiveMeet/executiveMeet-issueApply.action";
	load("#executiveMeetDiv", url);
}
function issueAudit(){
	var url="${request.contextPath}/office/executiveMeet/executiveMeet-issueAudit.action";
	load("#executiveMeetDiv", url);
}
function minutesManage(){
	var url="${request.contextPath}/office/executiveMeet/executiveMeet-minutesManage.action";
	load("#executiveMeetDiv", url);
}
function parameterSet(){
	var url="${request.contextPath}/office/executiveMeet/executiveMeet-parameterSet.action";
	load("#executiveMeetDiv", url);
}
</script>
<div class="popUp-layer schedule-layer-meet" id="minutesLayer" style="display:none;width:750px;"></div>
<div class="popUp-layer" id="attachViewDiv" style="display:none;width:1000px;height:600px;">
<div id="dsfsdfsdf"></div>
</div>
<div class="pub-tab">
	<ul class="pub-tab-list">
		<li class="current" onclick="myMeet();">我的会议</li>
		<#if hasPermission>
		<li onclick="meetManage();">会议添加</li>
		</#if>
		<li onclick="issueApply();">议题提报</li>
		<#if hasPermission>
		<li onclick="issueAudit();">议题审核</li>
		</#if>
		<#if canManageMinutes>
		<li onclick="minutesManage();">纪要维护</li>
		</#if>
		<#if hasPermission>
		<li onclick="parameterSet();">权限设置</li>
		</#if>
	</ul>
</div>
<div id="executiveMeetDiv"></div>
<iframe name="downloadFrame" id="downloadFrame" style="display:none;"></iframe>
<script type="text/javascript" src="${request.contextPath}/static/js/myscript.js"></script>
<script>
	$(document).ready(function(){
		myMeet();
	});
	//预览附件 用来获取附件状态
	function viewAttachment(id){
		$.ajax({
	        url:"${request.contextPath}/common/convertSuccess.action",
	        dataType:"json",
	        data:{"id":id},
	        type:"post",
	        success:function(data){
		        if(data.operateSuccess){
	   				showAttachment(id);
	   			}else{
	   				showMsgError(data.errorMessage);
	   				return;
	   			}
	        }
	    });
	}
	
	//显示预览的附件
	function showAttachment(id) {
		var url = "${request.contextPath}/common/showAttachmentView.action?id="+id;
		openDiv("#attachViewDiv", "#attachViewDiv .close", url, null, null, "1000px");
	}
	
	function doDownload(url){
		document.getElementById('downloadFrame').src=url;
	}
</script>
</@common.moduleDiv>