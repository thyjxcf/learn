<#import "/common/htmlcomponent.ftl" as common />
<@common.moduleDiv titleName="">
<script>
function doSearch(){
	var searchType = $("#searchType").val();
	if(searchType==''||searchType==null){
		searchType=0;
	}
	var searchPlace = $("#searchPlace").val();
	load("#mySurveyListDiv", "${request.contextPath}/office/survey/surveyManage-surveyAuditList.action?searchType="+searchType+"&searchPlace="+searchPlace);
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
		<@common.select style="width:150px;" valId="searchType" valName="searchType" myfunchange="doSearch" >
    		<a val="0"><span>待审核</span></a>
    		<a val="1"><span>已审核</span></a>
		</@common.select></div>
		
		<#if placeByCode>
		<div class="query-tt ml-10">
			<span class="fn-left">调研地点：</span>
		</div>
		<div class="select_box fn-left mr-10">
		<@common.select style="width:150px;" valId="searchPlace" valName="searchPlace" myfunchange="doSearch" >
			<a val=""><span>全部</span></a>
			${appsetting.getMcode("DM-DYDD").getHtmlTag("${searchPlace?default('')}",false)}
		</@common.select></div>
		<#else>
			<input type="hidden" id="searchPlace" name="searchPlace" value="" />
		</#if>
		<div class="fn-clear"></div>
	</div>
	</div>
	</div>
</div>
<div id="mySurveyListDiv"></div>
<div class="popUp-layer" id="mySurveyLayer" style="display:none;width:700px;"></div>
<script>
	$(document).ready(function(){
		doSearch();
	});
</script>
</@common.moduleDiv>