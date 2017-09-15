<#import "/common/htmlcomponent.ftl" as htmlmacro>
<@htmlmacro.moduleDiv titleName="">
<script>
$(function(){
	vselect();
	
	$("#print").click(function(){
		LODOP=getLodop();
		LODOP.ADD_PRINT_HTM("10mm","5mm","RightMargin:5mm","BottomMargin:15mm",getPrintContent(jQuery('#printDiv')));
		LODOP.PREVIEW();	
	});
	
	$("#export").click(function(){
		var startTime=$("#queryStartTime").val();
		var endTime=$("#queryEndTime").val();
		if(startTime!=""&&endTime!=""){
			var re=compareDate(startTime,endTime);
			if(re==1){
				showMsgError("结束时间不能早于开始时间，请重新选择！");
			}
		}
		var str="?startTime="+startTime+"&endTime="+endTime;
		location.href="${request.contextPath}/office/teacherLeaveNh/teacherLeaveNh-leaveSummaryExport.action"+str;
	});
});
</script>
<script type="text/javascript" src="${request.contextPath}/static/js/myscript.js"></script>
<div id="printDiv">
<form id="teachLeaveList">
	<@htmlmacro.tableList id="tablelist">
		<#if leaveTypeList?exists && leaveTypeList?size gt 0>
		<tr>
			<th style="text-align:center;width:10%">序号</th>
			<th style="text-align:center;width:10%">姓名</th>
			<#list leaveTypeList as ltp>
			<th style="text-align:center;width:${80/(leaveTypeList?size)}%">${ltp.name!}</th>
			</#list>
		</tr>
		<#if users?exists && users?size gt 0>
		<#list users as user>
			<tr>
				<td style="text-align:center;">${user_index+1}</td>
				<td style="text-align:center;">
					${user.realname!}
				</td>
				<#list leaveTypeList as ltp>
				<td style="text-align:center;">${levMap.get('${user.id!}_${ltp.id!}')?default(0)}天</td>
				</#list>
			</tr>
		</#list>
		<#else>
		<tr>
			<td colspan="${leaveTypeList?size+2}"><p class="no-data mt-50 mb-50">还没有数据哦！</p></td>
		<tr>
		</#if>
		<#else>
		<tr>
			<td><p class="no-data mt-50 mb-50">请先维护请假类型！</p></td>
		<tr>
		</#if>
	</@htmlmacro.tableList>
</form>
</div>
<#if leaveTypeList?exists && leaveTypeList?size gt 0>
<#if users?exists && users?size gt 0>
	<p class="t-center pt-30">
	<a href="javascript:void(0)" class="abtn-blue-big" id="print">打印</a>
	<a href="javascript:void(0)" class="abtn-blue-big" id="export">导出</a>
	</p>
</#if>
</#if>
</@htmlmacro.moduleDiv>