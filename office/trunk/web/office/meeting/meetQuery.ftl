<#import "/common/htmlcomponent.ftl" as common>
<@common.moduleDiv titleName="">
<script>
	$(function(){
		vselect();
		doSearch();
	});
	function doSearch(){
		var meetName=$("#meetingName").val();
		var startTime=$("#queryStartTime").val();
		var endTime=$("#queryEndTime").val();
		if(startTime!=''&&endTime!=''){
			var re=compareDate(startTime,endTime);
			if(re==1){
				showMsgWarn("结束时间不能小于开始时间！");
				return;
			}
		}
		var str="?meetingName="+meetName+"&startTime="+startTime+"&endTime="+endTime;
		var url="${request.contextPath}/office/meeting/workmeeting-queryMeetingList.action"+str;
		load("#meetQueryListDiv",url);
	}
</script>

<div class="pub-table-wrap">
	<div class="pub-table-inner">
		<div class="query-builder-nobg mt-15" style="padding:0 0 5px 0;">  
    		<div class="query-part">
				<div class="query-tt ml-10">
					<span class="fn-left">会议名称：</span>
				</div>
				<div class="fn-left">
					<input type="text" class="input-txt" id="meetingName" name="meetingName" value="${meetingName!}"></input>
				</div>
    			<div class="query-tt ml-10"><span class="fn-left">会议日期：</span></div>
			    <@common.datepicker name="queryStartTime" id="queryStartTime" style="width:120px;" value="${startTime!}"/>
			   	<div class="query-tt">&nbsp;-&nbsp;</div>
			    <@common.datepicker name="queryEndTime" id="queryEndTime" style="width:120px;" value="${endTime!}"/>
				&nbsp;<a href="javascript:void(0);" onclick="doSearch();" class="abtn-blue ml-20">查找</a>
			</div>
    	</div>
    </div>
</div>

<div id="meetQueryListDiv"></div>
<script type="text/javascript" src="${request.contextPath}/static/js/myscript.js"></script>
</@common.moduleDiv>