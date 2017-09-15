<#import "/common/htmlcomponent.ftl" as common />
<@common.moduleDiv titleName="">
<script>
function searchLeaderSchedule(){
	var startTime= document.getElementById("startTime").value;
	var endTime= document.getElementById("endTime").value;
	if(startTime!=''&&endTime!=''){
		var re = compareDate(startTime,endTime);
		if(re==1){
			showMsgWarn("结束时间不能小于开始时间！");
			return;
		}
	}
	var url="${request.contextPath}/office/schedule/schedule-deptLeaderScheduleList.action?startTime="+startTime+"&endTime="+endTime;
	load("#deptLeaderScheduleList", url);
}
</script>
<div class="pub-table-wrap">
	<div class="pub-table-inner">
	<div class="query-builder-nobg mt-15" style="padding:0 0 5px 0;">  
	<div class="query-part">
		<div class="query-tt ml-10"><span class="fn-left">时间：</span></div>
	    <div class="fn-left">
			<@common.datepicker class="input-txt" style="width:100px;" id="startTime" 
		   	value="${startTime!}"/>
		</div>
		<div class="query-tt ml-10 mt-5"><span class="fn-left">-&nbsp</span></div>
	    <div class="fn-left">
            <@common.datepicker class="input-txt" style="width:100px;" id="endTime" 
	   		value="${endTime!}"/>
		</div>
		<a href="javascript:void(0)" onclick="searchLeaderSchedule();" class="abtn-blue fn-left ml-20">查找</a>
		<div class="fn-clear"></div>
	</div>
	</div>
	</div>
</div>
<div id="deptLeaderScheduleList"></div>
<script>
	$(document).ready(function(){
		searchLeaderSchedule();
	});
</script>
</@common.moduleDiv>