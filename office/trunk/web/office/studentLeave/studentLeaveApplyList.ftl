<#import "/common/htmlcomponent.ftl" as common />
<form>
<@common.moduleDiv titleName="">
<@common.tableList id="tablelist">
	<tr>
		<th width="5%">序号</th>
		<th width="8%">请假类型</th>
		<th width="15%" >请假人</th>
		<th width="30%" >请假起止时间</th>
		<th width="">请假天数</th>
		<th width="15%">提交人</th>
		<th width="10%" >请假状态</th>
		<th width="8%" style="text-align:center;">操作</th>
	</tr>
	<#if studentLeaveList?exists && studentLeaveList?size gt 0>
		<#list studentLeaveList as stu>
			<tr>
				<td>${stu_index+1}</td>
				<td>${stu.leaveTypeName!}</td>
				<td>${stu.stuName!}</td>
				<td>
					${(stu.startTime?string('yyyy-MM-dd HH:mm'))?if_exists}
					至
					${(stu.endTime?string('yyyy-MM-dd HH:mm'))?if_exists}
				</td>
				<td>${stu.days?string('0.#')!}</td>
				<td>${stu.createUserName!}</td>
				<td>
					<#if stu.state == 1>
						未提交
					<#elseif stu.state == 2>
						待审核
					<#elseif stu.state == 3>
						已通过
					<#else>
						未通过
					</#if>
				</td>
				<td style="text-align:center;">
					<#if stu.state == 1>
					<a href="javascript:void(0)" onclick="doEdit('${stu.id}')">编辑</a>
					<a href="javascript:void(0)" onclick="doDelete('${stu.id}')">删除</a>
					<#elseif stu.state == 2>
					<a href="javascript:void(0);" onclick="doInfo('${stu.id!}');">查看</a>
					<a href="javascript:void(0);" onclick="doRevoke('${stu.id!}');">撤销</a>
					<#elseif stu.state == 3>
					<a href="javascript:void(0);" onclick="doInfo('${stu.id!}');">查看</a>
					<#elseif stu.state == 4>
					<a href="javascript:void(0);" onclick="doInfo('${stu.id!}');">查看</a>
		    		<a href="javascript:void(0);" onclick="doDelete('${stu.id!}');">删除</a>
					</#if>
				</td>
			</tr>
		</#list>
	<#else>
		<tr>
	        <td colspan="8"><p class="no-data mt-50 mb-50">还没有数据哦！</p></td>
	   	</tr>
	</#if>
</@common.tableList>
<@common.Toolbar container="#studentLeaveApplyListDiv"/>
</form>
<script>
	function doEdit(id){
		var str="?applyId="+id;
		var url="${request.contextPath}/office/studentLeave/studentLeave-applyEdit.action"+str;
		load("#studentAdminDiv",url);
	}
	
	function doInfo(id){
		var str="?applyId="+id+"&view=true";
		var url="${request.contextPath}/office/studentLeave/studentLeave-applyEdit.action"+str;
		load("#studentAdminDiv",url);
	}
	
	function doRevoke(id){
		if(confirm('确认要撤销吗？')){
			$.getJSON("${request.contextPath}/office/studentLeave/studentLeave-deleteApply.action?applyId="+id,null,function(data){
				if(!data.operateSuccess){
					showMsgError(data.promptMessage);
					return;
				}else{
					showMsgSuccess("撤销成功！","提示",function(){
						doSearch();
					});
				}
			});
		}
	}
	
	function doDelete(id){
		if(confirm('确认要删除吗？')){
			$.getJSON("${request.contextPath}/office/studentLeave/studentLeave-deleteApply.action?applyId="+id,null,function(data){
				if(!data.operateSuccess){
					showMsgError(data.promptMessage);
					return;
				}else{
					showMsgSuccess("删除成功！","提示",function(){
						doSearch();
					});
				}
			});
		}
	}


</script>
<script type="text/javascript" src="${request.contextPath}/static/js/myscript.js"></script>
</@common.moduleDiv>