<#import "/common/htmlcomponent.ftl" as common />
<@common.moduleDiv titleName="">
<script>
function doSearch(){
	var searchName = $("#searchName").val().trim();
	var startTime = $("#startTime").val();
	var endTime = $("#endTime").val();
	
	if(compareDate(startTime, endTime) > 0 ){
		showMsgWarn("开始时间不能大于结束时间，请重新选择！");
		return;
	}
	load("#deptLogListDiv", "${request.contextPath}/office/schedule/schedule-toDeptLogList.action?searchName="+encodeURIComponent(searchName)+"&startTime="+startTime+"&endTime="+endTime);
}

</script>
<div class="pub-table-wrap">
	<div class="pub-table-inner">
	<div class="query-builder-nobg mt-15" style="padding:0 0 5px 0;">  
	<div class="query-part">
		<div class="query-tt ml-10"><span class="fn-left">姓名：</span></div>
        <input name="searchName" id="searchName" value="${searchName!}" maxLength="30" class="input-txt fn-left" style="width:170px;"/>
		
		<div class="query-tt ml-10"><span class="fn-left">时间：</span></div>
	    <@common.datepicker name="startTime" id="startTime" style="width:120px;" value="${startTime!}"/>
	   	<div class="query-tt">&nbsp;-&nbsp;</div>
	    <@common.datepicker name="endTime" id="endTime" style="width:120px;" value="${endTime!}"/>
		
		<a href="javascript:void(0);" onclick="doSearch();" class="abtn-blue fn-left ml-20">查找</a>
		<div class="fn-clear"></div>
	</div>
	</div>
	</div>
</div>
<div id="deptLogListDiv"></div>
<script>
	$(document).ready(function(){
		doSearch();
	});
</script>
</@common.moduleDiv>