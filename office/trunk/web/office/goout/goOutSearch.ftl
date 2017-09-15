<#import "/common/htmlcomponent.ftl" as common />
<@common.moduleDiv titleName="">
<script>
function doSearch(){
	var states = $("#states").val();
	if(states==''||states==null){
		states="0";
	}
	load("#mySurveyListDiv", "${request.contextPath}/office/goout/goout-goOutList.action?states="+states);
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
		<@common.select style="width:150px;" valId="states" valName="states" myfunchange="doSearch" >
    		<a val="0"><span>待审核</span></a>
    		<a val="1"><span>已审核</span></a>
    		<a val="2"><span>已作废</span></a>
		</@common.select></div>
		<div class="fn-clear"></div>
	</div>
	</div>
	</div>
</div>
<div id="mySurveyListDiv"></div>
<script>
	$(document).ready(function(){
		doSearch();
	});
</script>
</@common.moduleDiv>