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
    	<th width="5%">序号</th>
    	<th width="15%">姓名</th>
    	<th width="15%">所在部门</th>
    	<th width="20%">补卡班次</th>
    	<th width="25%">缺卡原因</th>
    	<#if auditStatus?default(0)==0>
    		<th class="t-center">操作</th>
    	<#else>
    		<th class="t-center">状态</th>
    	</#if>
    </tr>
    <#if attendanceColckApplyList?exists && attendanceColckApplyList?size gt 0>
    	<#list attendanceColckApplyList as attendanceAudit>
    		<tr>
    			<td >${attendanceAudit_index+1}</td>
    			<td >${attendanceAudit.userName!}</td>
    			<td >${attendanceAudit.deptName!}</td>
    			<td >${((attendanceAudit.attenceDate)?string('yyyy-MM-dd'))?if_exists}&nbsp;${attendanceAudit.typeWeekTime!}</td>
    			<td >${attendanceAudit.reason!}</td>
	    		<#if auditStatus?default(0)==0>
					<td class="t-center">
		    			<a href="javascript:void(0);" onclick="doAudit('${attendanceAudit.id!}','${attendanceAudit.taskId!}','true');">通过</a>
		    			<a href="javascript:void(0);" onclick="doNoPass('${attendanceAudit.id!}','${attendanceAudit.taskId!}');">不通过</a>
    				</td>
	    		<#else>
	    			<td class="t-center">
	    				<#if attendanceAudit.applyStatus?default(0)==3>通过<#elseif attendanceAudit.applyStatus?default(0)==4>不通过</#if>
	    			</td>
	    		</#if>
    		</tr>
    	</#list>
    <#else>
    	<tr>
    		<td colspan='10'><p class="no-data mt-50 mb-50">还没有任何记录哦！</p></td>
	 	</tr>
    </#if>
</@htmlmacro.tableList>
<#if attendanceColckApplyList?exists && attendanceColckApplyList?size gt 0>
	<@htmlmacro.Toolbar container="#contentDiv">
	</@htmlmacro.Toolbar>
</#if> 
<div class="popUp-layer" id="classLayer3" style="display:none;width:500px;"></div>
<script>
	$(document).ready(function(){
		vselect();
		$('.user-sList-radio span').click(function(){
			$(this).addClass('current').siblings('span').removeClass('current');
			var status=$('#resourceofficedocTypeList.user-sList-radio span.current').attr("key");
			var str = "?auditStatus="+status;
			load("#contentDiv","${request.contextPath}/office/teacherAttendance/teacherAttendance-auditList.action"+str)
		});
	});
	function doNoPass(id,taskId){
		var url="${request.contextPath}/office/teacherAttendance/teacherAttendance-clockAuditEdit.action?id="+id+"&taskId="+taskId; 
		openDiv("#classLayer3", "#classLayer3 .close,#classLayer3 .submit,#classLayer3 .reset", url, null, null, "900px");
	}
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
		   		if(!data.operateSuccess){
				   if(data.errorMessage!=null&&data.errorMessage!=""){
					   showMsgError(data.errorMessage);
					   isSubmit = false;
					   return;
				   }
				}else{
					showMsgSuccess(data.promptMessage,"",function(){
					  	load("#contentDiv","${request.contextPath}/office/teacherAttendance/teacherAttendance-auditList.action");
					});
					return;
				}
		   	}
		});
	}
</script>
</@htmlmacro.moduleDiv>