<#import "/common/htmlcomponent.ftl" as htmlmacro>
<#include "/common/handlefielderror.ftl">
<@htmlmacro.moduleDiv titleName="">
<div class="query-builder-no pt-20">
	<div class="query-part fn-rel fn-clear promt-div">
		<span id="resourceofficedocTypeList" class="user-sList user-sList-radio">
	    	<span <#if auditStatus?default(0)==0> class="current"</#if> key="0">待处理</span>
	    	<span <#if auditStatus?default(0)==1> class="current"</#if> key="1">已处理</span>
	    </span>
    </div>
</div>
<@htmlmacro.tableList class="public-table table-list table-list-edit">
  	<tr>
    	<th >序号</th>
    	<th >姓名</th>
    	<th >所在部门</th>
    	<th >补卡班次</th>
    	<th >缺卡原因</th>
    	<#if auditStatus?default(0)==0>
    		<th class="t-center">操作1</th>
    	<#else>
    		<th >状态</th>
    		<th >审批人</th>
    		<th >审批时间</th>
    	</#if>
    </tr>
    <#if attendanceColckApplyList?exists && attendanceColckApplyList?size gt 0>
    	<#list attendanceColckApplyList as attendanceAudit>
    		<tr>
    			<td >${attendanceAudit_index+1}</td>
    			<td >${attendanceAudit.userName!}</td>
    			<td >${attendanceAudit.deptName!}</td>
    			<td >${attendanceAudit.attenceDate!}&nbsp;${attendanceAudit.typeWeekTime!}</td>
    			<td >${attendanceAudit.reason!}</td>
	    		<#if auditStatus?default(0)==0>
					<td class="t-center">
		    			<a href="javascript:void(0);" onclick="doAudit('${attendanceAudit.id!}','${attendanceAudit.taskId!}','true');">通过</a>
		    			<a href="javascript:void(0);" onclick="doAudit('${attendanceAudit.id!}','${attendanceAudit.taskId!}','false');">不通过</a>
    				</td>
	    		<#else>
	    			<td >
	    				<#if attendanceAudit.applyStatus?default(0)==3>通过<#elseif attendanceAudit.applyStatus?default(0)==4>不通过</#if>
	    			</td>
	    			<td >${attendanceAudit.clockStateTotal!}</td>
	    			<td >${attendanceAudit.clockStateTotal!}</td>
	    		</#if>
    		</tr>
    	</#list>
    <#else>
    	<tr>
    		<td colspan='10'><p class="no-data mt-50 mb-50">还没有任何记录哦！</p></td>
	 	</tr>
    </#if>
</@htmlmacro.tableList>
<#if officeAttendanceInfoList?exists && officeAttendanceInfoList?size gt 0>
	<@htmlmacro.Toolbar container="#showListDiv">
	</@htmlmacro.Toolbar>
</#if> 
<script type="text/javascript" src="${request.contextPath}/static/js/myscript.js"></script>
<script>
	$(document).ready(function(){
		vselect();
		$('.user-sList-radio span').click(function(){
			$(this).addClass('current').siblings('span').removeClass('current');
			var status=$('#resourceofficedocTypeList.user-sList-radio span.current').attr("key");
			var str = "?auditStatus="+status;
			load("#showListDiv","${request.contextPath}/office/teacherAttendance/teacherAttendance-auditList.action"+str)
		});
	});
	var isSubmit=false;
	function doAudit(id,taskId,pass){
		if(isSubmit){
			return;
		}
		isSubmit=true;
		jQuery.ajax({
			url:"${request.contextPath}/office/teacherAttendance/teacherAttendance-auditIsPassApply.action",
		   	type:"POST",
		   	dataType:"json",
		   	data:{"id":id,"taskId":taskId,"pass":pass},
		   	async:false,
		   	error:function(){
		   		showMsgError("打印出错！");
		   	},
		   	success:function(data){
		   		printValueDiv(data);
		   	}
		});
	}
</script>
</@htmlmacro.moduleDiv>