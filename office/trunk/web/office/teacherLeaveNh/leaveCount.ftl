<#import "/common/htmlcomponent.ftl" as common>
<@common.moduleDiv titleName="" >
	<div class="pub-table-wrap">
	<div class="pub-table-inner">
		<div class="query-builder-nobg mt-15" style="padding:0 0 5px 0;">  
    		<div class="query-part">
    			<div class="query-tt ml-10"><span class="fn-left">请假时间：</span></div>
			    <@common.datepicker name="queryStartTime" id="queryStartTime" style="width:120px;" value="${(startTime?string('yyyy-MM-dd'))?if_exists}"/>
			   	<div class="query-tt">&nbsp;-&nbsp;</div>
			    <@common.datepicker name="queryEndTime" id="queryEndTime" style="width:120px;" value="${(endTime?string('yyyy-MM-dd'))?if_exists}"/>
				&nbsp;<a href="javascript:void(0);" onclick="doSearch();" class="abtn-blue ml-20">查找</a>
			</div>
    	</div>
    </div>
</div>
<div id="teacherLeaveQuery"></div>
<script>
	$(function(){
		vselect();
		doSearch();
	});
	
	function doSearch(){
		var startTime=$("#queryStartTime").val();
		var endTime=$("#queryEndTime").val();
		if(startTime!=''&&endTime!=''){
			var re = compareDate(startTime,endTime);
			if(re==1){
				showMsgError("结束时间不能早于开始时间，请重新选择！");
				return;
			}
		}
		var str="?startTime="+startTime+"&endTime="+endTime;
		var url="${request.contextPath}/office/teacherLeaveNh/teacherLeaveNh-leaveSummaryList.action"+str;
		load("#teacherLeaveQuery",url);
	}
	
</script>
<script type="text/javascript" src="${request.contextPath}/static/js/myscript.js"></script>
<script type="text/javascript" src="${request.contextPath}/static/js/printarea.js"></script>
</@common.moduleDiv>