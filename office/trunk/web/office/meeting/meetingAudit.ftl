<#import "/common/htmlcomponent.ftl" as common />
<@common.moduleDiv titleName="">
<script>
function sear(){
	var state = document.getElementById("state").value;
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
	var url="${request.contextPath}/office/meeting/workmeeting-meetingAuditList.action?state="+state+"&meetingName="+meetingName+"&startTime="+startTime+"&endTime="+endTime;
	load("#meetingAuditListDiv", url);
}
</script>
<div class="pub-table-wrap">
	<div class="pub-table-inner">
	<div class="query-builder-nobg mt-15" style="padding:0 0 5px 0;">  
	<div class="query-part">
		<div class="query-tt ml-10">审核状态：</div>
	    <div class="fn-left">
    		<@common.select style="width:100px;" valName="meetingState" valId="state" myfunchange="sear">
				<a val="-1" <#if state == '-1'>class="selected"</#if>>全部</a>
				<a val="2" <#if state == '2'>class="selected"</#if>>待审核</a>
				<a val="3" <#if state == '3'>class="selected"</#if>>已通过</a>
				<a val="4" <#if state == '4'>class="selected"</#if>>未通过</a>
		</@common.select>
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
<div id="meetingAuditListDiv"></div>
<script>
	$(document).ready(function(){
		sear();
	});
</script>
</@common.moduleDiv>