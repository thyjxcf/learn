<#import "/common/htmlcomponent.ftl" as htmlmacro>
<@htmlmacro.showPrompt/>
<@htmlmacro.moduleDiv titleName="">
<script>
$(function(){
	load("#workReportDiv", "${request.contextPath}/office/workreport/workReport-myWorkReportList.action");
});

function myWorkReport(){
	var states =$("#states").val();
	if(states==null||states==""){
		states="";
	}
	var reportTypes=$("#reportTypes").val();
	if(reportTypes==null||reportTypes==""){
		reportTypes="";
	}
	var contents = $("#contents").val();
	var beginTimes = $("#beginTimes").val();
	var endTimes = $("#endTimes").val();
	
	if(beginTimes==null||beginTimes==""){
		beginTimes="";
	}
	if(endTimes==null||endTimes==""){
		endTimes="";
	}
	if(contents==null||contents==""){
		contents="";
	}
	if(compareDate(beginTimes, endTimes) > 0 ){
		showMsgWarn("开始时间不能大于结束时间，请重新选择！");
		return;
	}
	load("#workReportDiv", "${request.contextPath}/office/workreport/workReport-myWorkReportList.action?contents="+encodeURIComponent(contents)+"&beginTimes="+beginTimes+"&endTimes="+endTimes+"&states="+states+"&reportTypes="+reportTypes);
}

function myWorkReportTab(){
	load("#workReportDiv", "${request.contextPath}/office/workreport/workReport-myWorkReportList.action");
}

function workReportSearchTab(){
	load("#workReportDiv", "${request.contextPath}/office/workreport/workReport-workReportSearchList.action");
}


function workReportSearch(){

	var reportTypes=$("#reportTypes").val();
	if(reportTypes==null||reportTypes==null){
		reportTypes="";
	}
	
	var contents = $("#contents").val();
	if(contents==null||contents==""){
		contents="";
	}
	var createUserNames=$("#createUserNames").val();
	if(createUserNames==null||createUserNames==""){
		createUserNames="";
	}
	var beginTimes = $("#beginTimes").val();
	if(beginTimes==null||beginTimes==""){
		beginTimes="";
	}
	var endTimes = $("#endTimes").val();
	if(endTimes==null||endTimes==""){
		endTimes="";
	}

	
	if(compareDate(beginTimes, endTimes) > 0 ){
		showMsgWarn("开始时间不能大于结束时间，请重新选择！");
		return;
	}
	load("#workReportDiv", "${request.contextPath}/office/workreport/workReport-workReportSearchList.action?contents="+encodeURIComponent(contents)+"&beginTimes="+beginTimes+"&endTimes="+endTimes+"&createUserNames="+encodeURIComponent(createUserNames)+"&reportTypes="+reportTypes);
	
	//load("#workReportDiv", "${request.contextPath}/office/workreport/workReport-workReportSearch.action");
}
</script>
<div class="pub-tab mb-15">
	<ul class="pub-tab-list">
		<li class="current" onclick="myWorkReportTab();">我的工作汇报</li>
		<li onclick="workReportSearchTab();">工作汇报查询</li>
	</ul>
</div>
<div id="workReportDiv"></div>
<script type="text/javascript" src="${request.contextPath}/static/js/myscript.js"></script>
</@htmlmacro.moduleDiv>