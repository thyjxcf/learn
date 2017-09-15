<#import "/common/htmlcomponent.ftl" as common />
<@common.moduleDiv titleName="">
<script>
function doSearch(){
	var itemId=$("#itemId").val();
	if(itemId==null||itemId==''){
		showMsgWarn("请选择值班名称！");
			return;
	}
	var queryStartTime = $("#queryStartTime").val();
	var queryEndTime = $("#queryEndTime").val();
	if("" != queryStartTime && ""!=queryEndTime){
		if(compareDate(document.getElementById("queryStartTime"), document.getElementById("queryEndTime")) > 0){
			showMsgWarn("日期  前后时间不合逻辑，请更正！");
			return;
		}
	}
	var str = "?queryStartTime="+queryStartTime+"&queryEndTime="+queryEndTime+"&itemId="+itemId;
	var url="${request.contextPath}/office/dutymanage/dutymanage-dutyQueryList.action"+str;
	load("#myCarUseDetailListDiv", url);
}

function doExport(){
	var itemId=$("#itemId").val();
	if(itemId==null||itemId==''){
		showMsgWarn("请选择值班名称！");
			return;
	}
	var queryStartTime = $("#queryStartTime").val();
	var queryEndTime = $("#queryEndTime").val();
	if("" != queryStartTime && ""!=queryEndTime){
		if(compareDate(document.getElementById("queryStartTime"), document.getElementById("queryEndTime")) > 0){
			showMsgWarn("日期  前后时间不合逻辑，请更正！");
			return;
		}
	}
	var str = "?queryStartTime="+queryStartTime+"&queryEndTime="+queryEndTime+"&itemId="+itemId;
	var url="${request.contextPath}/office/dutymanage/dutymanage-dutyExport.action"+str;
	location.href=url;
}

$(document).ready(function(){
	var url="${request.contextPath}/office/dutymanage/dutymanage-dutyQueryList.action";
	load("#myCarUseDetailListDiv", url);
});
</script>

<div class="popUp-layer" id="classLayer3" style="display:none;width:500px;"></div>
<div class="popUp-layer" id="bulletinLayer" style="display:none;top:100px;left:300px;width:700px;height:580px;"></div>
<div class="pub-table-wrap">
	<div class="pub-table-inner">
		<div class="query-builder-nobg mt-5" style="padding:0 0 5px 0;">  
    		<div class="query-part">
    			<div class="query-tt b ml-10"><span class="fn-left">值班名称：</span></div>
			 	<div class="select_box fn-left">
				<@common.select style="width:300px;float:left;" valName="itemId" valId="itemId" notNull="true" myfunchange="doSearch">
	                	<a var='' class="selected">请选择</a>
						<#if officeDutyInformationSets?exists && officeDutyInformationSets?size gt 0>
	                		<#list officeDutyInformationSets as item>
	                			<a val="${item.id!}">${item.dutyName!}</a>
	                		</#list>
	                	</#if>
				</@common.select>
				</div>
    		
    			<div class="query-tt ml-10"><span class="fn-left">日期：</span></div>
			    <@common.datepicker name="queryStartTime" id="queryStartTime" style="width:120px;" value="${queryStartTime!}"/>
			   	<div class="query-tt">&nbsp;-&nbsp;</div>
			    <@common.datepicker name="queryEndTime" id="queryEndTime" style="width:120px;" value="${queryEndTime!}"/>
			    <a href="javascript:void(0);" onclick="doSearch();" class="abtn-blue ml-20">查询</a>
			    <a href="javascript:void(0);" onclick="doExport();" class="abtn-blue ml-20">导出</a>
			</div>
    	</div>
    </div>
</div>
<div id="myCarUseDetailListDiv"></div>
<script type="text/javascript" src="${request.contextPath}/static/js/myscript.js"></script>
</@common.moduleDiv>