<#import "/common/htmlcomponent.ftl" as common />
<@common.moduleDiv titleName="">
<script>
function doSearch(){
	var searchType = $("#searchType").val();
	var searchName = $("#searchName").val().trim();
	var searchStartTime = $("#searchStartTime").val();
	var searchEndTime = $("#searchEndTime").val();
	var searchPlace = $("#searchPlace").val();
	
	if(compareDate(searchStartTime, searchEndTime) > 0 ){
		showMsgWarn("开始时间不能大于结束时间，请重新选择！");
		return;
	}
	
	load("#surveyQueryListDiv", "${request.contextPath}/office/survey/surveyManage-surveyQuery-list.action?searchType="+searchType+"&searchName="+encodeURIComponent(searchName)+"&searchStartTime="+searchStartTime+"&searchEndTime="+searchEndTime+"&searchPlace="+searchPlace);
}
</script>
<div class="pub-table-wrap">
	<div class="pub-table-inner">
	<div class="query-builder-nobg mt-15" style="padding:0 0 5px 0;">  
	<div class="query-part">
		<div class="query-tt ml-10">
			<span class="fn-left">审核状态：</span>
		</div>
		<div class="select_box fn-left mr-10">
		<@common.select style="width:150px;" valId="searchType" valName="searchType" myfunchange="doSearch" curVal="${searchType?default('9')}">
    		<a val="9"><span>全部</span></a>
    		<a val="1"><span>待审核</span></a>
    		<a val="2"><span>审核通过</span></a>
    		<a val="3"><span>审核不通过</span></a>
		</@common.select></div>
		
		<div class="query-tt ml-10"><span class="fn-left">申请人姓名：</span></div>
        <input name="searchName" id="searchName" value="${searchName!}" maxLength="30" class="input-txt fn-left" style="width:170px;"/>
		
		<div class="query-tt ml-10"><span class="fn-left">调研时间：</span></div>
	    <@common.datepicker name="searchStartTime" id="searchStartTime" style="width:120px;" value="${(searchStartTime?string('yyyy-MM-dd'))?if_exists}"/>
	   	<div class="query-tt">&nbsp;-&nbsp;</div>
	    <@common.datepicker name="searchEndTime" id="searchEndTime" style="width:120px;" value="${(searchEndTime?string('yyyy-MM-dd'))?if_exists}"/>
		
		<#if placeByCode>
		<div class="query-tt ml-10"><span class="fn-left">调研地点：</span></div>
		<@common.select style="width:150px;" valId="searchPlace" valName="searchPlace" myfunchange="doSearch" >
			<a val=""><span>全部</span></a>
			${appsetting.getMcode("DM-DYDD").getHtmlTag("${searchPlace?default('')}",false)}
		</@common.select></div>
		<#else>
			<input type="hidden" id="searchPlace" name="searchPlace" value="" />
		</#if>
		
		<a href="javascript:void(0);" onclick="doSearch();" class="abtn-blue fn-left ml-20">查找</a>
		<div class="fn-clear"></div>
	</div>
	</div>
	</div>
</div>
<div id="surveyQueryListDiv"></div>
<script>
	$(document).ready(function(){
		doSearch();
	});
</script>
</@common.moduleDiv>