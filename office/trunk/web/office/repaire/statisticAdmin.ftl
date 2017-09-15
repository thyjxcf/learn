<#import "/common/htmlcomponent.ftl" as common />
<@common.moduleDiv titleName="">
<script>
function sear(){
	var years = document.getElementById("years").value;
	var change= document.getElementById("change").value;
	var url="${request.contextPath}/office/repaire/repaire-statisticList.action?years="+years+"&change="+change;
	load("#myRepaireListDiv", url);
}
</script>
<div class="pub-table-wrap">
	<div class="pub-table-inner">
	<div class="query-builder-nobg mt-15" style="padding:0 0 5px 0;">  
	<div class="query-part fn-rel fn-clear">
	<div class="query-tt b ml-10"><span class="fn-left">学年：</span></div>
			 <div class="select_box fn-left">
				<@common.select style="width:100px;float:left;" valName="years" valId="years" notNull="true" myfunchange="sear">
						<#if yearList?exists && yearList?size gt 0>
	                		<#list yearList as yearl>
	                			<a val="${yearl!}" <#if year?default('') == yearl>class="selected"</#if>>${yearl!}</a>
	                		</#list>
	                	</#if>
					</@common.select>
	</div>
	<div class="query-tt b ml-10"><span class="fn-left">统计：</span></div>
			<div class="select_box fn-left">
				<@common.select style="width:80px;" valName="change" valId="change" myfunchange="sear">
					<a val="1"  <#if change?default("1")=="1">class="selected"</#if>><span>次数</span></a>
					<a val="2"  <#if change?default("1")=="2">class="selected"</#if>><span>评价</span></a>
				</@common.select>
	</div>	
		<a href="javascript:void(0)" onclick="doPrint();" class="abtn-blue fn-left ml-20">打印</a>
		<a href="javascript:void(0)" onclick="doExport();" class="abtn-blue fn-left ml-20">导出</a>
		<div class="fn-clear"></div>
	</div>
	</div>
	</div>
</div>
<div id="myRepaireListDiv"></div>
<script>
	$(document).ready(function(){
		$("#years").val(${year!});
		$("#change").val("1");
		sear();
	});
	
function doPrint(){
	LODOP=getLodop();
	LODOP.ADD_PRINT_HTM("10mm","5mm","RightMargin:5mm","BottomMargin:15mm",getPrintContent(jQuery('#printDiv')));
  	LODOP.PREVIEW();
}
function doExport(){
	var years = document.getElementById("years").value;
	var change= document.getElementById("change").value;
	var str="?years="+years+"&change="+change;
	location.href="${request.contextPath}/office/repaire/repaire-applySummaryExport.action"+str;
}
	
</script>
<script type="text/javascript" src="${request.contextPath}/static/js/printarea.js"></script>
</@common.moduleDiv>