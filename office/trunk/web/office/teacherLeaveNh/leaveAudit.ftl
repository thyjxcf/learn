<#import "/common/htmlcomponent.ftl" as common />
<@common.moduleDiv titleName="">
<div class="pub-table-wrap">
	<div class="pub-table-inner">
		<div class="query-builder-nobg mt-15" style="padding:0 0 5px 0;">  
    		<div class="query-part">
				<div class="query-tt ml-10"><span class="fn-left">请假时间：</span></div>
			    <@common.datepicker name="queryStartTime" id="queryStartTime" style="width:120px;"/>
			   	<div class="query-tt">&nbsp;-&nbsp;</div>
			    <@common.datepicker name="queryEndTime" id="queryEndTime" style="width:120px;"/>
				<div class="query-tt ml-10">
					<span class="fn-left">审核状态：</span>
				</div>
				<div class="fn-left">
					<@common.select style="width:110px;" valName="auditStatus" valId="auditStatus" myfunchange="doSearch">
						<a val="0">待审核</a>
						<a val="1">已审核</a>
					</@common.select>
				</div>
				&nbsp;<a href="javascript:void(0);" onclick="doSearch();" class="abtn-blue ml-20">查找</a>
				<div class="fn-clear"></div>
			</div>
    	</div>
    </div>
</div>
<div id="leaveAuditListDiv"></div>
<script type="text/javascript" src="${request.contextPath}/static/js/myscript.js"></script>
<script>
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
	var auditStatus = $("#auditStatus").val();
	var str="?startTime="+startTime+"&endTime="+endTime+"&auditStatus="+auditStatus;
	var url="${request.contextPath}/office/teacherLeaveNh/teacherLeaveNh-leaveAuditList.action"+str;
	load("#leaveAuditListDiv", url);
}

$(document).ready(function(){
	doSearch();
});
</script>
</@common.moduleDiv>