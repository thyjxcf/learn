<#import "/common/htmlcomponent.ftl" as htmlmacro>
<@htmlmacro.moduleDiv titleName="">
<div id="printDiv">
<table border="0" cellspacing="0" cellpadding="0" class="public-table table-list">
    <tr id="titleTr" style="display:none;">
   		<td colspan="${mcodedetails?size+2}" style="text-align:center;font-size:20px;">教师请假统计</td>
	</tr>
    <tr>
    	<th width="10%" style="text-align:center;">序号</th>
    	<th width="10%" style="text-align:center;">姓名</th>
    	<#list mcodedetails as detail>
	    	<th width="${80/mcodedetails?size}%" style="text-align:center;">${detail.content!}</th>
    	</#list>
    </tr>
    <#if users?exists && users?size gt 0>
    <#list users as user>
    	<tr>
    		<td style="text-align:center;">${user_index+1}</td>
    		<td style="text-align:center;">${user.realname!}</td>
    		<#list mcodedetails as detail>
	    		<td style="text-align:center;">${sumMap.get('${user.id!}_${detail.thisId!}')?default(0)}天</td>
    		</#list>
    	</tr>
	</#list>
	<#else>
     <tr>
   		<td colspan="${mcodedetails?size+2}" > <p class="no-data mt-50 mb-50">还没有数据哦！</p></td>
	 </tr>
     </#if>
</table>
</div>
<#if users?exists && users?size gt 0>
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
	LODOP=getLodop();
	LODOP.ADD_PRINT_HTM("10mm","5mm","RightMargin:5mm","BottomMargin:15mm",getPrintContent(jQuery('#printDiv')));
  	LODOP.PREVIEW();
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
	location.href="${request.contextPath}/office/teacherLeave/teacherLeave-applySummaryExport.action"+str;
}
</script>
<script type="text/javascript" src="${request.contextPath}/static/js/printarea.js"></script>
<script type="text/javascript" src="${request.contextPath}/static/js/myscript.js"></script>
</@htmlmacro.moduleDiv>
