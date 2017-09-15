<#import "/common/htmlcomponent.ftl" as common />
<@common.moduleDiv titleName="">
<script>
function sear(){
	var meetingName = document.getElementById("meetingName").value;
	var startTime= document.getElementById("startTime").value;
	var endTime= document.getElementById("endTime").value;
	if(startTime!=''&&endTime!=''){
		var re = compareDate(startTime,endTime);
		if(re==1){
			showMsgWarn("结束时间不能小于开始时间！");
			return;
		}
	}
	var url="${request.contextPath}/office/meeting/workmeeting-myMeetingList.action?meetingName="+meetingName+"&startTime="+startTime+"&endTime="+endTime+"&show=${show!}";
	load("#myMeetingListDiv", url);
}

function queryMeeting(show){
	var meetingName = document.getElementById("meetingName").value;
	var startTime= document.getElementById("startTime").value;
	var endTime= document.getElementById("endTime").value;
	if(startTime!=''&&endTime!=''){
		var re = compareDate(startTime,endTime);
		if(re==1){
			showMsgWarn("结束时间不能小于开始时间！");
			return;
		}
	}
	var url="${request.contextPath}/office/meeting/workmeeting-myMeeting.action?meetingName="+meetingName+"&startTime="+startTime+"&endTime="+endTime+"&show="+show;
	load("#workmeetingDiv", url);
}
</script>
<div class="pub-table-wrap">
	<div class="pub-table-inner">
	<div class="query-builder-nobg mt-15" style="padding:0 0 5px 0;">  
	<div class="query-part">
		<div class="query-tt" style="margin-top:-3px;">
			<input type="hidden" name="show" id="show" value="${show!}">
		    	<span class="user-sList user-sList-radio fn-left">
		    		<span <#if show?default("1")=="1">class="current"</#if> data-select="1" onclick="queryMeeting('1')">待参加会议</span>
		    		<span <#if show?default("")=="0">class="current"</#if> data-select="0" onclick="queryMeeting('0')">已参加会议</span>
		    	</span>
		</div>
		<div class="query-tt ml-10">
			<span class="fn-left">会议名称：</span>
		</div>
	    <div class="fn-left">
			<input id="meetingName" type="text" class="input-txt" style="width:200px;" value="${meetingName!}">
		</div>
		<div class="query-tt ml-10"><span class="fn-left">会议日期：</span></div>
	    <div class="fn-left">
			<@common.datepicker class="input-txt" style="width:100px;" id="startTime" 
		   	value="${(startTime?string('yyyy-MM-dd'))?if_exists}"/>
		</div>
		<div class="query-tt ml-10 mt-5"><span class="fn-left">-&nbsp</span></div>
	    <div class="fn-left">
	            <@common.datepicker class="input-txt" style="width:100px;" id="endTime" 
	   		value="${(endTime?string('yyyy-MM-dd'))?if_exists}"/>
		</div>
		<a href="javascript:void(0)" onclick="sear();" class="abtn-blue fn-left ml-20">查找</a>
		<div class="fn-clear"></div>
	</div>
	</div>
	</div>
</div>
<div id="myMeetingListDiv"></div>
<script>
	$(document).ready(function(){
		sear();
	});
</script>
</@common.moduleDiv>