<#import "/common/htmlcomponent.ftl" as common />
<@common.moduleDiv titleName="">
<script>
function sear(){
	var queryName = document.getElementById("queryName").value;
	var startTime= document.getElementById("startTime").value;
	var endTime= document.getElementById("endTime").value;
	if(startTime!=''&&endTime!=''){
		var re = compareDate(startTime,endTime);
		if(re==1){
			showMsgWarn("结束时间不能小于开始时间！");
			return;
		}
	}
	var url="${request.contextPath}/office/executiveMeet/executiveMeet-meetManageList.action?queryName="+queryName+"&startTime="+startTime+"&endTime="+endTime;
	load("#meetManageListDiv", url);
}

function doEdit(meetId){
	var url="${request.contextPath}/office/executiveMeet/executiveMeet-meetEdit.action?meetId="+meetId;
	load("#meetManageListDiv", url);
}

function meetIssueAdd(meetId){
	var url="${request.contextPath}/office/executiveMeet/executiveMeet-meetIssueAdd.action?meetId="+meetId;
	load("#meetManageListDiv", url);
}

function manageIssues(meetId){
	var url="${request.contextPath}/office/executiveMeet/executiveMeet-manageIssues.action?meetId="+meetId;
	load("#meetManageListDiv", url);
}
function viewIssues(meetId){
	var url="${request.contextPath}/office/executiveMeet/executiveMeet-viewIssues.action?meetId="+meetId;
	load("#meetManageListDiv", url);
}
function viewMinutes(meetId){
	openDiv("#minutesLayer", "#minutesLayer .close,#minutesLayer .submit,#minutesLayer .reset", "${request.contextPath}/office/executiveMeet/executiveMeet-minutesView.action?meetId="+meetId, null, null, "700px");
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
			<input id="queryName" type="text" class="input-txt" style="width:200px;" value="">
		</div>
		<div class="query-tt ml-10"><span class="fn-left">会议日期：</span></div>
	    <div class="fn-left">
			<@common.datepicker class="input-txt" style="width:100px;" id="startTime" 
		   	value=""/>
		</div>
		<div class="query-tt ml-10 mt-5"><span class="fn-left">-&nbsp</span></div>
	    <div class="fn-left">
            <@common.datepicker class="input-txt" style="width:100px;" id="endTime" 
	   		value=""/>
		</div>
		<a href="javascript:void(0)" onclick="sear();" class="abtn-blue fn-left ml-20">查找</a>
		<a href="javascript:void(0);" class="abtn-orange-new fn-right" onclick="doEdit('');">新增会议</a>
		<div class="fn-clear"></div>
	</div>
	</div>
	</div>
</div>
<div id="meetManageListDiv"></div>
<script>
	$(document).ready(function(){
		sear();
	});
</script>
</@common.moduleDiv>