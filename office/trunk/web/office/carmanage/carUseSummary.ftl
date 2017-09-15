<#import "/common/htmlcomponent.ftl" as common />
<@common.moduleDiv titleName="">
<script>

function doSearch(){
	var queryStartTime = $("#queryStartTime").val();
	var queryEndTime = $("#queryEndTime").val();
	if("" != queryStartTime && ""!=queryEndTime){
		if(compareDate(document.getElementById("queryStartTime"), document.getElementById("queryEndTime")) > 0){
			showMsgWarn("用车时间  前后时间不合逻辑，请更正！");
			return;
		}
	}
	var querySumType = $("#querySumType").val();
	var str = "?queryStartTime="+queryStartTime+"&queryEndTime="+queryEndTime+"&querySumType="+querySumType;
	var url="${request.contextPath}/office/carmanage/carmanage-carUseSummaryList.action"+str;
	load("#carUseSummaryListDiv", url);
}

$(document).ready(function(){
	doSearch();
});
</script>

<div class="popUp-layer" id="classLayer3" style="display:none;width:500px;"></div>
<div class="popUp-layer" id="bulletinLayer" style="display:none;top:100px;left:300px;width:700px;height:580px;"></div>
<div class="pub-table-wrap">
	<div class="pub-table-inner">
		<div class="query-builder-nobg mt-5" style="padding:0 0 5px 0;">  
    		<div class="query-part">
    			<div class="query-tt ml-10">用车时间：</div>
			    <@common.datepicker name="queryStartTime" id="queryStartTime" style="width:120px;" value="${queryStartTime!}"/>
			   	<div class="query-tt">&nbsp;-&nbsp;</div>
			    <@common.datepicker name="queryEndTime" id="queryEndTime" style="width:120px;" value="${queryEndTime!}"/>
				<div class="query-tt ml-10">
					<span class="fn-left">汇总类型：</span>
				</div>
				<div class="fn-left">
					<@common.select style="width:110px;" valName="querySumType" valId="querySumType" myfunchange="doSearch">
						<a val="">请选择</a>
						<a val="1">车辆</a>
						<a val="2">驾驶员</a>
					</@common.select>
				</div>
				<a href="javascript:void(0);" onclick="doSearch();" class="abtn-blue ml-20">查找</a>
			</div>
    	</div>
    </div>
</div>
<div id="carUseSummaryListDiv"></div>
<script type="text/javascript" src="${request.contextPath}/static/js/myscript.js"></script>
</@common.moduleDiv>