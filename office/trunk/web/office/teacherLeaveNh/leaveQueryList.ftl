<#import "/common/htmlcomponent.ftl" as htmlmacro>
<@htmlmacro.moduleDiv titleName="">
<script>
	function doInfo(id){
		load("#teacherLeaveQuery","${request.contextPath}/office/teacherLeaveNh/teacherLeaveNh-viewDetail.action?teacherLeaveNhId="+id);
	}
</script>
<script type="text/javascript" src="${request.contextPath}/static/js/myscript.js"></script>
<form id="teachLeaveList">
	<@htmlmacro.tableList id="tablelist">
		<tr>
			<th>序号</th>
			<th>申请人</th>
			<th>请假起止时间</th>
			<th>共计天数</th>
			<th>请假类型</th>
			<th>请假状态</th>
			<th style="text-align:center;">操作</th>
		</tr>
		<#if teacherLeaveNhList?exists && teacherLeaveNhList?size gt 0>
		<#list teacherLeaveNhList as tl>
			<tr>
				<td>${tl_index+1}</td>
				<td>${tl.userName!}</td>
				<td>
					${(tl.beginTime?string('yyyy-MM-dd HH:mm'))?if_exists}
					至
					${(tl.endTime?string('yyyy-MM-dd HH:mm'))?if_exists}
				</td>
				<td>${tl.days?string('0.#')!}</td>
				<td>${tl.leaveTypeName!}</td>
				<td>
					<#if tl.state == 3>
						已通过
					<#else>
						未通过
					</#if>
				</td>
				<td style="text-align:center;">
					<a href="javascript:void(0);" onclick="doInfo('${tl.id!}');"><img src="${request.contextPath}/static/images/icon/view.png" alt="查看"></a>
				</td>		
			</tr>
		</#list>
		<#else>
			<tr>
				<td colspan="8"><p class="no-data mt-50 mb-50">还没有数据哦！</p></td>
			<tr>
		</#if>
	</@htmlmacro.tableList>
	<@htmlmacro.Toolbar container="#teacherLeaveQuery"/>
</form>
</@htmlmacro.moduleDiv>