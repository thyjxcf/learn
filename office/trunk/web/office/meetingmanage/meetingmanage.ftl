<#import "/common/htmlcomponent.ftl" as htmlmacro>
<@htmlmacro.showPrompt/>
<@htmlmacro.moduleDiv titleName="">
<script>
$(function(){
	load("#contectDiv", "${request.contextPath}/office/meetingmanage/meetingmanage-meetingList.action?doAction=apply");
});

function doApplyAudit(val){
	if(val == "apply"){
		load("#contectDiv", "${request.contextPath}/office/meetingmanage/meetingmanage-meetingList.action?doAction="+val);
	}else{
		load("#contectDiv", "${request.contextPath}/office/meetingmanage/meetingmanage-auditList.action?doAction="+val);
	}	
}
</script>
<div id="meetingEditDiv">
<div class="pub-tab mb-15">
		<ul class="pub-tab-list">
			<li class="current" onclick="doApplyAudit('apply');">申请管理</li>
			<#if rightOfMeetingAudit?default(false)>
			<li onclick="doApplyAudit('audit');">申请审核</li>
			</#if>
		</ul>
</div>
<div id="contectDiv"></div>
</div>
</@htmlmacro.moduleDiv>