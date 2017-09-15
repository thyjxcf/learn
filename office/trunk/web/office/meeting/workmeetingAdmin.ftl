<#import "/common/htmlcomponent.ftl" as common />
<@common.moduleDiv titleName="">
<script>
function toMyMeeting(){
	var url="${request.contextPath}/office/meeting/workmeeting-myMeeting.action";
	load("#workmeetingDiv", url);
}
function toMeetingApply(){
	var url="${request.contextPath}/office/meeting/workmeeting-meetingApply.action";
	load("#workmeetingDiv", url);
}
function toMeetingAudit(){
	var url="${request.contextPath}/office/meeting/workmeeting-meetingAudit.action";
	load("#workmeetingDiv", url);
}
function meetingQuery(){
	var url="${request.contextPath}/office/meeting/workmeeting-queryMeeting.action";
	load("#workmeetingDiv",url);
}

function meetingMinutesManage(){
	var url="${request.contextPath}/office/meeting/workmeeting-meetingMinutesManage.action";
	load("#workmeetingDiv",url);
}

function meetingAuth(){
	var url="${request.contextPath}/office/meeting/workmeeting-meetAuthList.action";
	load("#workmeetingDiv",url);
}
</script>
<div class="popUp-layer" id="classLayer3" style="display:none;width:600px;"></div>
<div class="pub-tab">
	<ul class="pub-tab-list">
	<li class="current" onclick="toMyMeeting();">我的会议</li>
	<li onclick="toMeetingApply();">会议申请</li>
	<#if hasEdit>
	<li onclick="toMeetingAudit();">会议审核</li>
	</#if>
	<li onclick="meetingQuery();">会议查询</li>
	<li onclick="meetingMinutesManage();">会议纪要管理</li>
	<li onclick="meetingAuth();">权限设置</li>
	</ul>
</div>
<div id="workmeetingDiv"></div>
<script type="text/javascript" src="${request.contextPath}/static/js/myscript.js"></script>
<script>
	$(document).ready(function(){
		toMyMeeting();
	});
</script>
</@common.moduleDiv>