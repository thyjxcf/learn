<#import "/common/htmlcomponent.ftl" as htmlmacro>
<@htmlmacro.moduleDiv titleName="">
<div id="printDiv">
<table border="0" cellspacing="0" cellpadding="0" class="public-table table-list">
    <tr id="titleTr" style="display:none;">
   		<td colspan="8" style="text-align:center;font-size:20px;">教师外出统计</td>
	</tr>
    <tr>
    	<th width="5%" rowspan="2"style="text-align:center;">序号</th>
    	<th width="10%"  rowspan="2"  style="text-align:center;">姓名</th>
    	<th width="30%"  colspan="2" style="text-align:center;">因公外出</th>
    	<th width="30%"  colspan="2" style="text-align:center;">因私外出</th>
    	<th width="25%"  colspan="2" style="text-align:center;">总计</th>
    </tr>
    <tr>
		<td width="8%"  style="text-align:center;">外出次数</td>
		<td width="9%"  style="text-align:center;">外出时间（小时）</td>
		<td width="8%"  style="text-align:center;">外出次数</td>
		<td width="9%"  style="text-align:center;">外出时间（小时）</td>
		<td width="8%"  style="text-align:center;">外出次数</td>
		<td width="9%"  style="text-align:center;">外出时间（小时）</td>
	</tr>
    <#if officeGoOutList?exists && officeGoOutList?size gt 0>
    <#list officeGoOutList as office>
    	<tr>
    		<td style="text-align:center;">${office_index+1}</td>
    		<td style="text-align:center;">${office.applyUserName!}</td>
    		<td style="text-align:center;">${(office.numJob)?default(0)}</td>
    		<td style="text-align:center;">${((office.hoursJob)?string('0.0'))?default(0)}</td>
    		<td style="text-align:center;">${(office.numSelf)?default(0)}</td>
    		<td style="text-align:center;">${((office.hoursSelf)?string('0.0'))?default(0)}</td>
    		<td style="text-align:center;">${(office.outNum)?default(0)}</td>
    		<td style="text-align:center;">${((office.sumHours)?string('0.0'))?default(0)}</td>
    	</tr>
	</#list>
	<#else>
     <tr>
   		<td colspan="8" > <p class="no-data mt-50 mb-50">还没有数据哦！</p></td>
	 </tr>
     </#if>
</table>
</div>
<#if officeGoOutList?exists && officeGoOutList?size gt 0> 
<p class="t-center pt-30">
	<a href="javaScript:void(0);" onclick="doPrint();" class="abtn-blue-big">打印</a>
	<a href="javaScript:void(0);" onclick="doExport();" class="abtn-blue-big">导出</a>
</p>
</#if>
<script>
$(document).ready(function(){
	vselect();
});
function doPrint(){
	$("#titleTr").attr("style","display:;");
	LODOP=getLodop();
	LODOP.ADD_PRINT_HTM("10mm","5mm","RightMargin:5mm","BottomMargin:15mm",getPrintContent(jQuery('#printDiv')));
  	LODOP.PREVIEW();
  	$("#titleTr").attr("style","display:none;");
}
function doExport(){
	var startTime=$("#startTime").val();
	var endTime=$("#endTime").val();
	if(startTime!=''&&endTime!=''){
		var re = compareDate(startTime,endTime);
		if(re==1){
			showMsgError("结束时间不能早于开始时间，请重新选择！");
			return;
		}
	}
	var deptId=$("#deptId").val();
	var str="?startTime="+startTime+"&endTime="+endTime+"&deptId="+deptId;
	location.href="${request.contextPath}/office/goout/goout-statisticsExport.action"+str;
}
</script>
<script type="text/javascript" src="${request.contextPath}/static/js/printarea.js"></script>
<script type="text/javascript" src="${request.contextPath}/static/js/myscript.js"></script>
</@htmlmacro.moduleDiv>