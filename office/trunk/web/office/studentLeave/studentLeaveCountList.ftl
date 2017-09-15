<#import "/common/htmlcomponent.ftl" as common>
<@common.moduleDiv titleName="">
<div id="printDiv">
<form id="auditList">
	<@common.tableList id="tablelist"> 
		<#if leaveTypeList?exists && leaveTypeList?size gt 0>
		<tr>
			<th style="text-align:center;width:10%">序号</th>
			<#if gradeId?default("")==""&&classId?default("")=="">
			<th style="text-align:center;width:10%">年级</th>
			<th style="text-align:center;width:10%">学生请假次数</th>
			</#if>
			<#if gradeId?default("")!=""&&classId?default("")=="">
			<th style="text-align:center;width:10%">班级</th>
			<th style="text-align:center;width:10%">学生请假次数</th>
			</#if>
			<#if gradeId?default("")!=""&&classId?default("")!="">
			<th style="text-align:center;width:10%">姓名</th>
			<th style="text-align:center;width:10%">请假次数</th>
			</#if>
			<#list leaveTypeList as ltp>
				<th style="text-align:center;width:${70/(leaveTypeList?size)}%">${ltp.name!}</th>
			</#list>
		</tr>
		<#if gradeId?default("")==""&&classId?default("")=="">
			<#if grades?exists && grades?size gt 0>
			<#list  grades as grade>
				<tr>
				<td style="text-align:center;">${grade_index+1}</td>
				<td style="text-align:center;">${grade.gradename!}</td>
				<td style="text-align:center;">${gradeTimeMap.get('${grade.id!}')?default(0)}</td>
				<#list leaveTypeList as ltp>
				<td style="text-align:center;">${gradeMap.get('${grade.id!}_${ltp.id!}')?default(0)}天</td>
				</#list>
				</tr>
			</#list>
			<#else>
			<tr>
				<td colspan="${leaveTypeList?size+3}"><p class="no-data mt-50 mb-50">还没有数据哦！</p></td>
			<tr>
			</#if>
		</#if>
		<#if gradeId?default("")!=""&&classId?default("")=="">
			<#if eisuClasss?exists && eisuClasss?size gt 0>
			<#list  eisuClasss as cla>
				<tr>
				<td style="text-align:center;">${cla_index+1}</td>
				<td style="text-align:center;">${cla.classname!}</td>
				<td style="text-align:center;">${classTimeMap.get('${cla.id!}')?default(0)}</td>
				<#list leaveTypeList as ltp>
				<td style="text-align:center;">${classMap.get('${cla.id!}_${ltp.id!}')?default(0)}天</td>
				</#list>
				</tr>
			</#list>
			<#else>
			<tr>
				<td colspan="${leaveTypeList?size+3}"><p class="no-data mt-50 mb-50">还没有数据哦！</p></td>
			<tr>
			</#if>
		</#if>
		<#if gradeId?default("")!=""&&classId?default("")!="">
			<#if students?exists && students?size gt 0>
			<#list  students as stu>
				<tr>
				<td style="text-align:center;">${stu_index+1}</td>
				<td style="text-align:center;">${stu.stuname!}</td>
				<td style="text-align:center;">${nameTimeMap.get('${stu.id}')}</td>
				<#list leaveTypeList as ltp>
				<td style="text-align:center;">${stuLevMap.get('${stu.id!}_${ltp.id!}')?default(0)}天</td>
				</#list>
				</tr>
			</#list>
			<#else>
			<tr>
				<td colspan="${leaveTypeList?size+3}"><p class="no-data mt-50 mb-50">还没有数据哦！</p></td>
			<tr>
			</#if>
		</#if>
		<#else>
		<tr>
			<td><p class="no-data mt-50 mb-50">请先维护请假类型！</p></td>
		<tr>
		</#if>
	</@common.tableList>
</form>
</div>
	<#if leaveTypeList?exists && leaveTypeList?size gt 0>
	<#--<#if students?exists && students?size gt 0>-->
	<p class="t-center pt-30">
	<a href="javaScript:void(0);" onclick="doPrint();" class="abtn-blue-big">打印</a>
	<a href="javaScript:void(0);" onclick="doExport();" class="abtn-blue-big">导出</a>
	</p>
	<#--</#if>-->
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
	var startTime=$("#queryStartTime").val();
	var endTime=$("#queryEndTime").val();
	if(startTime!=''&&endTime!=''){
		var re=compareDate(startTime,endTime);
		if(re==1){
			showMsgError("结束时间不能早于开始时间，请重新选择！");
		}
	}
	var gradeId=$("#gradeId").val();
	var classId=$("#classId").val();	
	var str="?startTime="+startTime+"&endTime="+endTime+"&classId="+classId+"&gradeId="+gradeId;
	location.href="${request.contextPath}/office/studentLeave/studentLeave-leaveCountExport.action"+str;
}
</script>
<script type="text/javascript" src="${request.contextPath}/static/js/printarea.js"></script>	
<script type="text/javascript" src="${request.contextPath}/static/js/myscript.js"></script>
</@common.moduleDiv>