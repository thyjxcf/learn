<#import "/common/htmlcomponent.ftl" as common />
<@common.moduleDiv titleName="">
<script>
function doSearch(){
	var queryStartTime = $("#queryStartTime").val();
	var queryEndTime = $("#queryEndTime").val();
	var queryState = $("#queryState").val();
	if("" != queryStartTime && ""!=queryEndTime){
		if(compareDate(document.getElementById("queryStartTime"), document.getElementById("queryEndTime")) > 0){
			showMsgWarn("用车时间  前后时间不合逻辑，请更正！");
			return;
		}
	}
	var str = "?queryStartTime="+queryStartTime+"&queryEndTime="+queryEndTime+"&queryState="+queryState;
	var url="${request.contextPath}/office/carmanage/carmanage-carAuditList.action"+str;
	load("#carAuditListDiv", url);
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
    			<div class="query-tt ml-10"><span class="fn-left">用车时间：</span></div>
			    <@common.datepicker name="queryStartTime" id="queryStartTime" style="width:120px;" value="${queryStartTime!}"/>
			   	<div class="query-tt">&nbsp;-&nbsp;</div>
			    <@common.datepicker name="queryEndTime" id="queryEndTime" style="width:120px;" value="${queryEndTime!}"/>
				<div class="query-tt ml-10">
					<span class="fn-left">审核状态：</span>
				</div>
				<div class="fn-left">
					<@common.select style="width:110px;" valName="queryState" valId="queryState" myfunchange="doSearch">
						<a val="">请选择</a>
						<a val="2">待审核</a>
						<a val="3">通过</a>
						<a val="4">未通过</a>
						<a val="5">撤销待审核</a>
						<a val="6">撤销通过</a>
					</@common.select>
				</div>
				&nbsp;<a href="javascript:void(0);" onclick="doSearch();" class="abtn-blue ml-20">查找</a>
				<div class="fn-clear"/>
			</div>
    	</div>
    </div>
</div>
<div id="carAuditListDiv"></div>
<script type="text/javascript" src="${request.contextPath}/static/js/myscript.js"></script>
</@common.moduleDiv>