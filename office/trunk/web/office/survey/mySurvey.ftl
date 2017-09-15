<#import "/common/htmlcomponent.ftl" as common />
<@common.moduleDiv titleName="">
<script>
function doSearch(){
	var searchType = $("#searchType").val();
	load("#mySurveyListDiv", "${request.contextPath}/office/survey/surveyManage-mySurvey-list.action?searchType="+searchType);
}

function doSurveyAdd(){
	openDiv("#mySurveyLayer", "#mySurveyLayer .close,#mySurveyLayer .submit,#mySurveyLayer .reset", "${request.contextPath}/office/survey/surveyManage-mySurvey-add.action", null, null, "900px");
}

function doSurveyEdit(surveyId){
	openDiv("#mySurveyLayer", "#mySurveyLayer .close,#mySurveyLayer .submit,#mySurveyLayer .reset", "${request.contextPath}/office/survey/surveyManage-mySurvey-edit.action?officeSurveyApply.id="+surveyId, null, null, "900px");
}

function doSurveyView(surveyId){
	openDiv("#mySurveyLayer", "#mySurveyLayer .close,#mySurveyLayer .submit,#mySurveyLayer .reset", "${request.contextPath}/office/survey/surveyManage-mySurvey-view.action?officeSurveyApply.id="+surveyId, null, null, "900px");
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
			<a val=""><span>全部</span></a>
    		<a val="0"><span>未提交</span></a>
    		<a val="1"><span>待审核</span></a>
    		<a val="2"><span>审核通过</span></a>
    		<a val="3"><span>审核不通过</span></a>
		</@common.select></div>
		<a href="javascript:void(0);" onclick="doSurveyAdd();" class="abtn-orange-new fn-right mr-10">新增调研申请</a>
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