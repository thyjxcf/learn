<#import "/common/htmlcomponent.ftl" as htmlmacro>
<@htmlmacro.moduleDiv titleName="">
<script>
	function doInfo(id){
		var url="${request.contextPath}/office/studentLeave/studentLeave-queryView.action?applyId="+id;
		load("#studentAdminDiv",url);
	}
</script>
<script type="text/javascript" src="${request.contextPath}/static/js/myscript.js"></script>
<@htmlmacro.tableList id="tablelist">
	<tr>
		<th style="width:4%">序号</th>
		<th style="width:20%">请假起止时间</th>
		<th style="width:6%">共计天数</th>
		<#if leaveStatus?default('')==8>
		<th style="width:6%">作废人</th>	
		</#if>
		<th style="width:10%">申请人</th>
		<th style="width:8%">班级</th>
		<th style="width:10%">请假类型</th>
		<th style="width:6%">审核状态</th>
		<#if leaveStatus?default('')==8>
		<th style="width:24%">请假原因</th>
		<#else>
		<th style="width:30%">请假原因</th>
		</#if>
		<th style="text-align:center;width:6%">操作</th>
	</tr>
	<#if studentLeaveList?exists && studentLeaveList?size gt 0>
	<#list studentLeaveList as sl>
		<tr>
			<td>${sl_index+1}</td>
			<td>
				${(sl.startTime?string('yyyy-MM-dd HH:mm'))?if_exists}
				至
				${(sl.endTime?string('yyyy-MM-dd HH:mm'))?if_exists}
			</td>
			<td>${sl.days?string('0.#')!}</td>
			<#if leaveStatus?default('')==8>
			<td>${sl.invalidUserName!}</td>
			</#if>
			<td>${sl.stuName!}</td>
			<td>${sl.className!}</td>
			<td>${sl.leaveTypeName!}</td>
			<td>
				<#if sl.state == 1>
					未提交
				<#elseif sl.state == 2>
					待审核
				<#elseif sl.state == 3>
					已通过
				<#elseif sl.state==4>
					未通过
				<#else>
					已作废
				</#if>
			</td>
			<td>
				<#if sl.remark?exists>
					<span title='${sl.remark!}'>
					<#if (sl.remark?length>50)>
					${sl.remark?substring(0,50)}...
					<#else>
					${sl.remark!}
					</#if>
					</span>
				</#if>
			</td>
			<td style="text-align:center;">
				<a href="javascript:void(0);" onclick="doInfo('${sl.id!}');"><img src="${request.contextPath}/static/images/icon/view.png" alt="查看"></a>
			</td>		
		</tr>
	</#list>
	<#else>
		<#if leaveStatus?default('')==8>
		<tr>
			<td colspan="11"><p class="no-data mt-50 mb-50">还没有数据哦！</p></td>
		<tr>
		<#else>
		<tr>
			<td colspan="10"><p class="no-data mt-50 mb-50">还没有数据哦！</p></td>
		<tr>
		</#if>
	</#if>
</@htmlmacro.tableList>
<@htmlmacro.Toolbar container="#studentLeaveQuery">
</@htmlmacro.Toolbar>
</@htmlmacro.moduleDiv>