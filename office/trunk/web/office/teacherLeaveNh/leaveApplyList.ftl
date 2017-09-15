<#import "/common/htmlcomponent.ftl" as common />
<#assign UNSUBMIT = stack.findValue("@net.zdsoft.office.util.Constants@APPLY_STATE_SAVE") >
<#assign NEEDAUDIT = stack.findValue("@net.zdsoft.office.util.Constants@APPLY_STATE_NEED_AUDIT") >
<#assign PASS = stack.findValue("@net.zdsoft.office.util.Constants@APPLY_STATE_PASS") >
<#assign UNPASS = stack.findValue("@net.zdsoft.office.util.Constants@APPLY_STATE_NOPASS") >
<form>
<@common.moduleDiv titleName="">
<@common.tableList class="public-table table-list table-list-edit mt-20">
  	<tr>
  		<th >序号</th>
    	<th >请假起止时间</th>
    	<th >共计天数</th>
    	<th >请假类型</th>
    	<th >请假状态</th>
    	<th class="t-center">操作</th>
    </tr>
    <#if teacherLeaveNhList?exists && teacherLeaveNhList?size gt 0>
    	<#list teacherLeaveNhList as leave>
    		<tr>
    			<td>${leave_index+1}</td>
    			<td >${(leave.beginTime?string('yyyy-MM-dd HH:mm'))?if_exists}
    				至
    				${(leave.endTime?string('yyyy-MM-dd HH:mm'))?if_exists}</td>
		    	<td >${leave.days?string('0.#')!}天</td>
		    	<td >${leave.leaveTypeName!}</td>
		    	<td ><#if leave.state==1>
		    			未提交	
		    		<#elseif leave.state==2>
		    			待审核
		    		<#elseif leave.state==3>
		    			审核结束-通过
		    		<#elseif leave.state==4>
		    			审核结束-未通过
		    		</#if>
		    	</td>
		    	<td class="t-center">
		    		<#if leave.state==1>
	    			<a href="javascript:void(0);" onclick="editLeaveApply('${leave.id!}');"><img src="${request.contextPath}/static/images/icon/edit.png" alt="编辑"></a>
		    		<a href="javascript:void(0);" onclick="doDelete('${leave.id!}');"><img src="${request.contextPath}/static/images/icon/del2.png" alt="删除"></a>
		    		<#elseif leave.state==2>
		    		<a href="javascript:void(0);" onclick="viewInfo('${leave.id!}');"><img src="${request.contextPath}/static/images/icon/view.png" alt="查看"></a>
		    		<#elseif leave.state==3>
		    		<a href="javascript:void(0);" onclick="viewInfo('${leave.id!}');"><img src="${request.contextPath}/static/images/icon/view.png" alt="查看"></a>
		    		<#elseif leave.state==4>
		    		<a href="javascript:void(0);" onclick="viewInfo('${leave.id!}');"><img src="${request.contextPath}/static/images/icon/view.png" alt="查看"></a>
		    		<a href="javascript:void(0);" onclick="doDelete('${leave.id!}');"><img src="${request.contextPath}/static/images/icon/del2.png" alt="删除"></a>
		    		</#if>
		    		
		    	</td>
    		</tr>
    	</#list>
    <#else>
    	<tr>
    		<td colspan='10'><p class="no-data mt-50 mb-50">还没有任何记录哦！</p></td>
	 	</tr>
    </#if>
</@common.tableList>
<@common.Toolbar container="#leaveApplyListDiv"/>
</form>
<script>
	function viewInfo(id){
		load("#leaveApplyListDiv","${request.contextPath}/office/teacherLeaveNh/teacherLeaveNh-viewDetail.action?teacherLeaveNhId="+id);
	}
	
	function doDelete(id){
		if(showConfirm("确定要删除该请假申请")){
			$.getJSON("${request.contextPath}/office/teacherLeaveNh/teacherLeaveNh-delete.action",{teacherLeaveNhId:id},function(data){
				if(data.operateSuccess){
					showMsgSuccess(data.promptMessage,"",doSearch);
				}else{
					showMsgError(data.errorMessage);
					return;
				}
		   }).error(function(XMLHttpRequest, textStatus, errorThrown){alert(XMLHttpRequest.status);});
		}
	}
</script>
<script type="text/javascript" src="${request.contextPath}/static/js/myscript.js"></script>
</@common.moduleDiv>