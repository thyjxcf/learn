<#import "/common/htmlcomponent.ftl" as common>
<#assign NEEDAUDIT = stack.findValue("@net.zdsoft.office.util.Constants@APPLY_STATE_NEED_AUDIT") >
<#assign PASS = stack.findValue("@net.zdsoft.office.util.Constants@APPLY_STATE_PASS") >
<@common.moduleDiv titleName="">
<form id="auditList">
	<@common.tableList id="tablelist"> 
		<tr>
			<th>序号</th>
			<th>请假起止时间</th>
			<#if leaveStatus==8>
			<th>作废人</th>
			</#if>
			<th>提交人</th>
			<th>共计天数</th>
			<th>申请人</th>
			<th>请假类型</th>
			<th>审核状态</th>
			<th style="text-align:center;">操作</th>
		</tr>
		<#assign num=0>
		<#if studentLeaveList?exists && studentLeaveList?size gt 0>
		<#list  studentLeaveList as sl>
			<#assign num=num+1>
			<tr>
			<td>${num!}</td>
			<td>
				${(sl.startTime?string('yyyy-MM-dd HH:mm'))?if_exists}
				至
				${(sl.endTime?string('yyyy-MM-dd HH:mm'))?if_exists}
			</td>
			<#if leaveStatus==8>
			<td>
				${sl.invalidUserName!}
			</td>
			</#if>
			<td>${sl.createUserName!}</td>
			<td>${sl.days?string('0.#')!}</td>
			<td>${sl.stuName!}</td>
			<td>${sl.leaveTypeName!}</td>
			<td>
				<#if sl.state == 1>
					未提交
				<#elseif sl.state == 2>
					待审核
				<#elseif sl.state == 3>
					已通过
				<#elseif sl.state == 4>
					未通过
				<#else>
					已作废
				</#if>
			</td>
			<td style="text-align:center;">
				<#if sl.state == NEEDAUDIT>
				<a href="javascript:void(0)" onclick="doEdit('${sl.id}')">审核</a>
				<#else>
					<#if sl.state==PASS>
						<a href="javascript:void(0)" onclick="doInvalid('${sl.id!}')">作废</a>
					</#if>
				<a href="javascript:void(0)" onclick="doView('${sl.id!}')">查看</a>
				</#if>
			</td>
			</tr>
		</#list>
		<#else>
		<#if leaveStatus==8>
		<tr>
			<td colspan="9"><p class="no-data mt-50 mb-50">还没有数据哦！</p></td>
		<tr>
		<#else>
		<tr>
			<td colspan="8"><p class="no-data mt-50 mb-50">还没有数据哦！</p></td>
		<tr>
		</#if>
		</#if>
	</@common.tableList>
	<@common.Toolbar container="#studentLeaveAudit"/>
</form>
<script>
	function doInvalid(id){
		if(showConfirm("确定要作废该请假申请")){
			$.getJSON("${request.contextPath}/office/studentLeave/studentLeave-doInvalid.action",{applyId:id},function(data){
				if(data.operateSuccess){
					showMsgSuccess(data.promptMessage,"",doSearch);
				}else{
					showMsgError(data.promptMessage);
					return;
				}
		   }).error(function(XMLHttpRequest, textStatus, errorThrown){alert(XMLHttpRequest.status);});
		}
	}
	function doEdit(id){
		var url="${request.contextPath}/office/studentLeave/studentLeave-approveEdit.action?applyId="+id+"&view=false";
		load("#studentAdminDiv",url);
	}
	function doView(id){
		var url="${request.contextPath}/office/studentLeave/studentLeave-auditView.action?applyId="+id+"&view=true";
		load("#studentAdminDiv",url);
	}
</script>	
<script type="text/javascript" src="${request.contextPath}/static/js/myscript.js"></script>
</@common.moduleDiv>